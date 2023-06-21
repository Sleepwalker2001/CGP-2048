package game.main;

import java.util.Arrays;
import java.util.Random;

public class Spavn {
    private Handler handler;
    private Score score;
    private Random r = new Random();
    private int tipkovnica = 0;
    CGP myCGP=null;
    int[] ploca= new int[16];

    public int final_score;

    public Spavn(Handler handler, Score score) {
        this.handler = handler;
        this.score = score;
    }

    public synchronized void tick() throws InterruptedException {
        if (tipkovnica == 1) {
            tipkovnica = 0;
            int kontrola = 1;

            //ploca
            Arrays.fill(ploca,0);

            while (kontrola == 1) {

                //Ovdje kraj igre
                if (handler.object.size()==16) {
                    System.out.println("Kraj igre");
                    final_score=score.getRez();
                    System.out.print(final_score+"\n");
                    handler.reset();
                    try {
                        System.out.println("zapeh");
                        Thread.sleep(1000);
                        System.out.println("pušten");
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }

                kontrola = 0;
                int rbrX = r.nextInt(0, 4);
                int rbrY = r.nextInt(0, 4);
                //System.out.println(rbrX + " " + rbrY);
                for (int i = 0; i < handler.object.size(); i++) {

                    GameObject tempObject = handler.object.get(i);

                    /*
                    Tu dodaj kod za dodavanje vrijednosti ploce u listu
                    */
                    //System.out.println("ID: "+ tempObject.getId());
                    switch (tempObject.id){
                        case Player2:ploca[(tempObject.y-150)/75*4+(tempObject.x-150)/75]=1;
                            break;
                        case Player4:ploca[(tempObject.y-150)/75*4+(tempObject.x-150)/75]=2;
                            break;
                        case Player8:ploca[(tempObject.y-150)/75*4+(tempObject.x-150)/75]=3;
                            break;
                        case Player16:ploca[(tempObject.y-150)/75*4+(tempObject.x-150)/75]=4;
                            break;
                        case Player32:ploca[(tempObject.y-150)/75*4+(tempObject.x-150)/75]=5;
                            break;
                        case Player64:ploca[(tempObject.y-150)/75*4+(tempObject.x-150)/75]=6;
                            break;
                        case Player128:ploca[(tempObject.y-150)/75*4+(tempObject.x-150)/75]=7;
                            break;
                        case Player256:ploca[(tempObject.y-150)/75*4+(tempObject.x-150)/75]=8;
                            break;
                        case Player512:ploca[(tempObject.y-150)/75*4+(tempObject.x-150)/75]=9;
                            break;
                        case Player1024:ploca[(tempObject.y-150)/75*4+(tempObject.x-150)/75]=10;
                            break;
                        case Player2048:ploca[(tempObject.y-150)/75*4+(tempObject.x-150)/75]=11;
                            break;
                        case Player4096:ploca[(tempObject.y-150)/75*4+(tempObject.x-150)/75]=12;
                            break;
                    }
                    if (tempObject.x == 150 + rbrX * 75 && tempObject.y == 150 + rbrY * 75) {
                        kontrola = 1;
                    }

                }
                if (kontrola == 0) {
                    int posto = r.nextInt(1, 11);
                    int plbr;
                    if (posto > 7) {
                        plbr = 4;
                    } else {
                        plbr = 2;
                    }
                    String ime = "player " + String.valueOf(plbr);
                    Player novi = new Player(150 + rbrX * 75, 150 + rbrY * 75, ID.getOperationName(ime), handler);
                    handler.addObject(novi);

                    switch (novi.id) {
                        case Player2:
                            ploca[(novi.y - 150) / 75 * 4 + (novi.x - 150) / 75] = 1;
                            break;
                        case Player4:
                            ploca[(novi.y - 150) / 75 * 4 + (novi.x - 150) / 75] = 2;
                            break;
                        case Player8:
                            ploca[(novi.y - 150) / 75 * 4 + (novi.x - 150) / 75] = 3;
                            break;
                        case Player16:
                            ploca[(novi.y - 150) / 75 * 4 + (novi.x - 150) / 75] = 4;
                            break;
                        case Player32:
                            ploca[(novi.y - 150) / 75 * 4 + (novi.x - 150) / 75] = 5;
                            break;
                        case Player64:
                            ploca[(novi.y - 150) / 75 * 4 + (novi.x - 150) / 75] = 6;
                            break;
                        case Player128:
                            ploca[(novi.y - 150) / 75 * 4 + (novi.x - 150) / 75] = 7;
                            break;
                        case Player256:
                            ploca[(novi.y - 150) / 75 * 4 + (novi.x - 150) / 75] = 8;
                            break;
                        case Player512:
                            ploca[(novi.y - 150) / 75 * 4 + (novi.x - 150) / 75] = 9;
                            break;
                        case Player1024:
                            ploca[(novi.y - 150) / 75 * 4 + (novi.x - 150) / 75] = 10;
                            break;
                        case Player2048:
                            ploca[(novi.y - 150) / 75 * 4 + (novi.x - 150) / 75] = 11;
                            break;
                        case Player4096:
                            ploca[(novi.y - 150) / 75 * 4 + (novi.x - 150) / 75] = 12;
                            break;
                    }
                }
                String board="";
                for(Integer i : ploca){
                    board+=i+" ";
                }
                System.out.println("Ploca: "+board+"\n");
            }

        }
    }


//    public void create_CGP(int width, int length,boolean reset) throws InterruptedException {
//        System.out.println("Making CGP");
//        myCGP=new CGP(width,length,handler,this,reset);
//        myCGP.seePopulation();
//        myCGP.CGP_thread(ploca);
//    };

    public int[] getPloca(){
        return ploca;
    }

    public void gore(){
        handler.kolizija();
        while(handler.kol) {
            handler.nema();
            for (int i = 0; i < handler.object.size(); i++) {
                GameObject tempObject = handler.object.get(i);
                tempObject.setVelY(-20);
            }
            try {
                Thread.sleep(0100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        setTipkovnica(1);
        for(int i = 0; i < handler.object.size(); i++) {
            GameObject tempObject = handler.object.get(i);
            tempObject.setVelY(0);
        }
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }
    public void dolje(){
        handler.kolizija();
        while(handler.kol) {
            handler.nema();
            for (int i = 0; i < handler.object.size(); i++) {
                GameObject tempObject = handler.object.get(i);
                tempObject.setVelY(20);
            }
            try {
                Thread.sleep(0100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        setTipkovnica(1);
        for(int i = 0; i < handler.object.size(); i++) {
            GameObject tempObject = handler.object.get(i);
            tempObject.setVelY(0);
        }
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
    public void lijevo(){
        handler.kolizija();
        while(handler.kol) {
            handler.nema();
            for (int i = 0; i < handler.object.size(); i++) {
                GameObject tempObject = handler.object.get(i);
                tempObject.setVelX(-20);
            }
            try {
                Thread.sleep(0100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        setTipkovnica(1);
        for(int i = 0; i < handler.object.size(); i++) {
            GameObject tempObject = handler.object.get(i);
            tempObject.setVelX(0);
        }
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
    public void desno(){
        handler.kolizija();
        while(handler.kol) {
            handler.nema();
            for (int i = 0; i < handler.object.size(); i++) {
                GameObject tempObject = handler.object.get(i);
                tempObject.setVelX(20);
            }
            try {
                Thread.sleep(0100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        setTipkovnica(1);
        for(int i = 0; i < handler.object.size(); i++) {
            GameObject tempObject = handler.object.get(i);
            tempObject.setVelX(0);
        }
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void setTipkovnica(int tipkovnica) {
        this.tipkovnica = tipkovnica;
    }

    public int getTipkovnica() {
        return tipkovnica;
    }
}
