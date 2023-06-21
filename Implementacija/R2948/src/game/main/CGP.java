package game.main;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;


public class CGP {
    private Handler handler;
    private Spavn spavn;
    private Thread bipbup;
    private int numInputs=16;
    private int numOutputs=4;
    private int trys=3;
    private int pop_size=4;
    private int width;
    private int length;
    private int numVar=2;
    private int gen;
    private int counter;
    private int which;
    private List<Integer> scores=new ArrayList<>();
    private Random random;
    public List<Individual> population=new ArrayList<>();
    private List<boolean[]> identified;
    private int gen_limit=100;
    private int numFunc=6;
    private int numOfSinMut=2;



    //public List<List<Integer>> population= new ArrayList<>();

    ///////////////////////////////////////INITIALIZATION
    public CGP(int width, int length, Handler handler, Spavn spavn,int pop_size,int trys,boolean reset) throws InterruptedException {
        this.handler=handler;
        this.spavn=spavn;
        this.width=width;
        this.length=length;
        this.pop_size=pop_size;
        this.trys=trys;
        random=new Random();
        which=0;

        if(reset){
            freshStart();
        }else {
            extractPop();
        }
        seePopulation();
        CGP_thread();
    }
    ///////////////////////////////////////

    ///////////////////////////////////////RESET ON ALL OF POPULATION

    public void freshStart(){
        gen=0;
        for (int i=0;i<pop_size;i++){
            List<Integer> individual=new ArrayList<>();
            int upper=numInputs;
            for(int j=0;j<width*length;j++){
                individual.add(getRandom(numFunc));
                if(j%width==0 && j!=0){
                    upper+=width;
                }
                for(int k=0;k<numVar;k++){
                    individual.add(getRandom(upper));
                }
            }
            for(int j=0;j<numOutputs;j++) {
                individual.add(getRandom(numInputs + width * length));
            }
            population.add(make_ind(individual));
        }
         recordPop(population);
    }
    ///////////////////////////////////////

    ///////////////////////////////////////MAKE INDIVIDUAL

    private Individual make_ind(List<Integer> individual){
        Individual new_ind=new Individual(individual,width,length,identify(individual),node_split(individual),gen);
        return new_ind;
    }

    ///////////////////////////////////////

    ///////////////////////////////////////POPULATION RECORDING AND STORING
    private void extractPop(){
        try {
            File myObj = new File("population.txt");
            Scanner myReader = new Scanner(myObj);
            if (myReader.hasNextLine()){
                String[] data=myReader.nextLine().split(" ");
                length=Integer.parseInt(data[0]);
                width=Integer.parseInt(data[1]);
            }
            if (myReader.hasNextLine()){
                String data=myReader.nextLine();
                gen=Integer.parseInt(data);
            }
            List<Individual> pop=new ArrayList<>();
            while (myReader.hasNextLine()) {
                List<Integer> individual=new ArrayList<>();
                String[] data = myReader.nextLine().split(" ");
                for (String number:data){
                    individual.add(Integer.parseInt(number));
                }
                pop.add(make_ind(individual));
            }
            pop_size=pop.size();
            population=pop;
            myReader.close();
            System.out.println("Population restored");
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    private void recordPop(List<Individual> population){
        try{
            File myFile=new File("population.txt");
            if (myFile.createNewFile()) {
                System.out.println("Populations will be recorded in " + myFile.getName()+".");
            } else {
                System.out.println("Population will be rewritten.");
            }
            FileWriter myWriter=new FileWriter("population.txt");
            myWriter.write(length+ " "+width+"\n");
            myWriter.write(gen+"\n");
            for(Individual individual:population){
                for (Integer number:individual.program){
                    myWriter.write(number+" ");
                }
                myWriter.write("\n");
            }
            myWriter.close();
            System.out.println("Population saved.");
        }catch (IOException e){
            System.out.println("An error occurred.");
        }
    }

    ///////////////////////////////////////

    ///////////////////////////////////////SCORE RECORDING

    private void record_scores(){
        try{
            File myFile=new File("scores.txt");
            myFile.createNewFile();
            FileWriter myWriter = new FileWriter(myFile, true);
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
            LocalDateTime now = LocalDateTime.now();
            myWriter.write(dtf.format(now)+"\n");
            myWriter.write("Generation: "+gen+"   Length: "+length+"   Width: "+width+"   Population size: "+pop_size+"   Number of functions: "+numFunc+"   Number of single mutations: "+numOfSinMut+"\n");
            for(Individual ind:population){
                myWriter.write("Individual: ");
                for (Integer number:ind.program){
                    myWriter.write(number+" ");
                }
                myWriter.write("\nScores: ");
                for (Integer score:ind.scores){
                    myWriter.write(score+" ");
                }
                myWriter.write("\nMean score: "+ind.mean);
                myWriter.write("\n");
                myWriter.write("\n");
            }
            myWriter.close();

        }catch (IOException e){
            System.out.println("An error occurred.");
        }
    }

    ///////////////////////////////////////

    ///////////////////////////////////////NODE SPLITTING
    private List<List<Integer>> node_split(List<Integer> individual){
        List<Integer> nodes=individual.subList(0,individual.size()-numOutputs);

        int nodeSize=numVar+1;                      //inputs plus function
        int numOfNodes=(nodes.size())/nodeSize;     //Possible substitution with width*length
        List<List<Integer>> nodesSplit=new ArrayList<>();
        for(int i=0;i<numOfNodes;i++){
            int start=i*nodeSize;
            int end=i*nodeSize+nodeSize;
            nodesSplit.add(nodes.subList(start, end));
        }
        return nodesSplit;
    }
    ///////////////////////////////////////

    ///////////////////////////////////////ACTIVE NODE FINDING

    private void identify_all(){
        List<boolean[]> all=new ArrayList<>();
        for(int i=0;i<pop_size;i++){
            all.add(identify(population.get(i).program));
        }
        identified=all;
    }
    private boolean[] identify(List<Integer> individual){
        List<Integer> nodes=individual.subList(0,individual.size()-numOutputs);
        List<Integer> outputs=individual.subList(individual.size()-numOutputs,individual.size());

        int nodeSize=numVar+1;                      //inputs plus function
        int numOfNodes=(nodes.size())/nodeSize;     //Possible substitution with width*length

        List<List<Integer>> nodesSplit=node_split(individual);

        boolean toEvaluate[]=new boolean[numOfNodes];

        for (int i=0; i<numOutputs; i++){
            System.out.println("output needs: "+outputs.get(i));
            if (outputs.get(i) >= numInputs){
                toEvaluate[outputs.get(i)-numInputs]=true;
            }
        }
        for(int i=numOfNodes-1; i>=0; i--){
            if(toEvaluate[i]){
                String s="To evaluate node: "+i+" Uses inputs: "+nodesSplit.get(i).get(1)+" and "+nodesSplit.get(i).get(2);
                System.out.println(s);
                for(int j=1; j<numVar+1; j++){
                    if (nodesSplit.get(i).get(j)>=numInputs){
                        toEvaluate[nodesSplit.get(i).get(j)-numInputs]=true;
                    }
                }
            }
        }
        return toEvaluate;
    }
    ///////////////////////////////////////

    ///////////////////////////////////////CROSSOVER
    private void crossover(){
        int pairs_2x2=2;
        int num=pairs_2x2*4;

        List<Integer> range=new ArrayList<>();
        for (int i=0; i<population.size();i++){
            range.add(i);
        }

        int[] picks=new int[num];
        for(int i=0;i<num;i++){
            int position=getRandom(range.size());
            picks[i]=range.remove(position);
        }
        Individual[] best=new Individual[num/2];
        Individual[] losers=new Individual[num/2];
        for(int i=0;i<num;i+=2){
            if(population.get(picks[i]).mean>population.get(picks[i+1]).mean){
                best[i/2]=population.get(picks[i]);
                losers[i/2]=population.get(picks[i+1]);
            } else if (population.get(picks[i]).mean<population.get(picks[i+1]).mean) {
                best[i/2]=population.get(picks[i+1]);
                losers[i/2]=population.get(picks[i]);
            } else if (population.get(picks[i]).mean==population.get(picks[i+1]).mean && population.get(picks[i]).gen>population.get(picks[i+1]).gen) {
                best[i/2]=population.get(picks[i]);
                losers[i/2]=population.get(picks[i+1]);
            } else if (population.get(picks[i]).mean==population.get(picks[i+1]).mean && population.get(picks[i]).gen<population.get(picks[i+1]).gen) {
                best[i/2]=population.get(picks[i+1]);
                losers[i/2]=population.get(picks[i]);
            } else if (population.get(picks[i]).mean==population.get(picks[i+1]).mean && population.get(picks[i]).gen==population.get(picks[i+1]).gen) {
                best[i/2]=population.get(picks[i]);
                losers[i/2]=population.get(picks[i+1]);
            }
            else{
                System.out.println("SOMETHING WENT WRONG");
            }
        }
        for (Individual loser:losers){
            population.remove(loser);
        }

        for(int i=0;i<num/2;i+=2){
            for (int k=0;k<2;k++){
                List<List<Integer>> child=new ArrayList<>();
                List<Integer> child_ind=new ArrayList<>();
                //////////////////////////////////////////////////////////ONE WIDTH GENOMES CROSSOVER
                if(width==1){
                    int rand=getRandom(best[0].program.size());
                    if(k==0){
                        for(int j=0;j<rand;j++){
                            child_ind.add(best[i].program.get(j));
                        }
                        for(int j=rand;j<best[i+1].program.size();j++){
                            child_ind.add(best[i+1].program.get(j));
                        }

                    } else if (k==1) {
                        for(int j=0;j<rand;j++){
                            child_ind.add(best[i+1].program.get(j));
                        }
                        for(int j=rand;j<best[i].program.size();j++){
                            child_ind.add(best[i].program.get(j));
                        }
                    }
                }
                /////////////////////////////////////////////////////////CROSSOVER FOR THE REST
                else{
                    /////////////////////////////////////////////////////SPLICING OF GENES
                    for(int j=0;j<best[0].node_size;j++){
                        int rand=getRandom(2);
                        if(rand==0){
                            child.add(List.copyOf(best[i].nodes.get(j)));
                        } else if (rand==1){
                            child.add(List.copyOf(best[i+1].nodes.get(j)));
                        }
                    }
                    ////////////////////////////////////////////////////ALL GENES TOGETHER
                    for(List<Integer> node:child){
                        for(Integer gene:node){
                            child_ind.add(gene);
                        }
                    }
                    ////////////////////////////////////////////////////ADDING OUTPUT GENES
                    for (int j=numOutputs;j>0;j--){
                        child_ind.add(best[k].program.get(best[k].program.size()-j));
                    }
                }
                ///////////////////////////////////////////////////COMPLETE CHILD
                Individual complete_child=make_ind(child_ind);
                ///////////////////////////////////////////////////COMPLETE MUTATED CHILD
                for (int z=0;z<numOfSinMut;z++){
                    mutateSingle(complete_child);
                }
                ///////////////////////////////////////////////////INTRODUCED INTO POPULATION
                population.add(complete_child);
            }
        }
    }
    ///////////////////////////////////////

    ///////////////////////////////////////MUTATIONS
    //PERCENTAGE MUTATION
    private void mutateNormal(Individual individual){
        int num;
        if(width*length<=100){
            num=1;
        }
        else {
            //stopa mutacije se treba povećavati s brojem gena jer samo dio njih će biti aktivan
            double mutrate=0.01+0.005*(width*length/100);
            num=(int)mutrate*individual.program.size();
        }

        List<Integer> range=new ArrayList<>();
        for (int i=0; i<individual.program.size();i++){
            range.add(i);
        }

        int[] picks=new int[num];
        for(int i=0;i<num;i++){
            int position=getRandom(range.size());
            picks[i]=range.remove(position);
        }

        for(int pick:picks){
            if(pick<=(width*length*(numVar+1))){
                if(pick % (numVar+1)==0){
                    individual.program.set(pick,getRandom(numFunc));
                }
                else{
                    //could be source of trouble
                    int upper=numInputs+(width)*(pick/(width*(numVar+1)));
                    individual.program.set(pick,getRandom(upper));
                }
            }
            else {
                individual.program.set(pick,getRandom(numInputs+width*length));
            }
        }
        individual.active=identify(individual.program);
        individual.nodes=node_split(individual.program);
    }
    ///////////////////////////////////////

    ///////////////////////////////////////SINGLE MUTATION
    private void mutateSingle(Individual individual){
        List<Integer> range=new ArrayList<>();
        for (int i=0; i<individual.program.size();i++){
            range.add(i);
        }
        List<Integer> picks=new ArrayList<>();
        int position;
        int gene;
        boolean active=false;
        do{
            position=getRandom(range.size());
            gene=range.remove(position);
            picks.add(gene);
        }while (!is_gene_active(individual,gene));
        for(int pick:picks){
            if(pick<=(width*length*(numVar+1))){
                if(pick % (numVar+1)==0){
                    individual.program.set(pick,getRandom(numFunc));
                }
                else{
                    int upper=numInputs+(width)*(pick/(width*(numVar+1)));
                    individual.program.set(pick,getRandom(upper));
                }
            }
            else {
                individual.program.set(pick,getRandom(numInputs+width*length));
            }
        }
        individual.active=identify(individual.program);
        individual.nodes=node_split(individual.program);
    }
    ///////////////////////////////////////

    ///////////////////////////////////////IS A GENE IN AN ACTIVE NODE

    public boolean is_gene_active(Individual ind, int pos){
        boolean active=false;
        List active_nodes=Arrays.asList(ind.active);
        if((pos>=ind.program.size()-numOutputs || active_nodes.contains(pos/(numVar+1))) && pos<ind.program.size()){
            active=true;
        }
        return active;
    }
    ///////////////////////////////////////

    ///////////////////////////////////////FINDING THE BEST INDIVIDUAL IN POPULATION
    //ONLY FOR 1+4
    private int best(){
        double greatest=0;
        int best=0;
        for(int i=0; i<pop_size; i++){
            double sum=0;
            for(int j=0; j<trys;j++){
                sum+=scores.get(i*trys+j);
            }
            double mean=(sum/trys);
            System.out.println("Individual "+i+" mean score: "+mean);
            if(mean>=greatest){
                greatest=mean;
                best=i;
            }
        }
        System.out.println("Best individual is: "+best);
        return best;
    }

    ///////////////////////////////////////

    ///////////////////////////////////////MOVE PREDICTION
    public int predict(int[] ploca){
        Individual individual=population.get(which);
        seeIndividual(individual);
        List<Integer> outputGenes=individual.program.subList(individual.program.size()-numOutputs,individual.program.size());

        List<List<Integer>> nodesSplit=individual.nodes;

        HashMap<Integer,Integer> outputs=new HashMap<>();
        boolean toEvaluate[]=individual.active;

        for (int i=0;i<numInputs;i++){
            outputs.put(i,ploca[i]);
        }
        for (int i=0;i<individual.node_size;i++){
            if(toEvaluate[i]){
                int var1=outputs.get(nodesSplit.get(i).get(1));
                int var2=outputs.get(nodesSplit.get(i).get(2));
                if(nodesSplit.get(i).get(0)==0){
                    outputs.put(i+numInputs,var1+var2);
                } else if (nodesSplit.get(i).get(0)==1) {
                    outputs.put(i+numInputs,var1-var2);
                } else if (nodesSplit.get(i).get(0)==2) {
                    outputs.put(i+numInputs,var1*var2);
                } else if (nodesSplit.get(i).get(0)==3) {
                    if(var2==0){
                        outputs.put(i+numInputs,var1/1);
                    }else {
                        outputs.put(i+numInputs,var1/var2);
                    }
                } else if (nodesSplit.get(i).get(0)==4) {
                    outputs.put(i+numInputs,Math.max(var1,var2));
                } else if (nodesSplit.get(i).get(0)==5) {
                    outputs.put(i+numInputs,Math.min(var1,var2));
                }
            }
        }
        int greatest=outputs.get(outputGenes.get(0));
        int greatestOutput=0;
        for(int i=1;i<numOutputs;i++){
            if(outputs.get(outputGenes.get(i))>greatest){
                greatest=outputs.get(outputGenes.get(i));
                greatestOutput=i;
            }
        }
        return greatestOutput;
    }

    ///////////////////////////////////////

    ///////////////////////////////////////MOVES FOR BOARD STATE AND EACH INDIVIDUAL

    public void CGP_thread() throws InterruptedException {
        bipbup=new Thread(() -> {
            try {
                CGP_play();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        bipbup.start();
    }
    public synchronized void CGP_play() throws InterruptedException {
        int[] ploca;
        String vidploce;
        identify_all();
        while(true || gen<gen_limit){
            ploca=spavn.getPloca();
            vidploce="";
            for (int i:ploca){
                vidploce+=i;
            }
            if(vidploce.equals("0000000000000000")){
                population.get(which).scores.add(spavn.final_score);
                population.get(which).calc_mean();
                scores.add(spavn.final_score);
                keep_count();
                Thread.sleep(1000);
            }
            else {
                int decision = predict(ploca);
                switch(decision){
                    case 0: spavn.gore();
                        break;
                    case 1: spavn.desno();
                        break;
                    case 2: spavn.dolje();
                        break;
                    case 3: spavn.lijevo();
                        break;
                }
            }
        }
    }

    ///////////////////////////////////////

    ///////////////////////////////////////HELPFUL FUNCTIONS
    public void seePopulation(){
        for(int i=0;i< population.size();i++){
            String line=new String();
            for (int j=0;j<population.get(i).program.size();j++){
                line+=population.get(i).program.get(j)+", ";
            }
            System.out.println(line+"\n");
        }
    }
    public void seeIndividual(Individual individual){
        String line=new String();
        for (int i=0;i<individual.program.size();i++){
            line+=individual.program.get(i)+", ";
        }
        System.out.println(line+"\n");
    }


    private void keep_count(){
        if(counter<trys-1 && which<pop_size){
            counter++;
        } else if (counter>=trys-1 && which<pop_size-1) {
            which++;
            counter=0;
        }else{
            System.out.println("Generation done");
            record_scores();
            scores.clear();

            gen++;

            crossover();

            for(Individual ind:population){
                ind.clear_score();
            }
            System.out.println("\nNew population:\n");
            seePopulation();

            recordPop(population);

            counter=0;
            which=0;
        }
    }

    private int getRandom(int upper){
        int num=random.nextInt(upper);
        return num;
    }
    ///////////////////////////////////////
    public void recordScore(){}

    public int getLength(){return length;}

    public int getWidth(){ return width;}

    public int getNumInputs(){return numInputs;}

    public int getNumOutputs(){return numOutputs;}

    public int getNumVar(){return numVar;}
}
