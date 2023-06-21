package game.main;

import java.util.ArrayList;
import java.util.List;

public class Individual {
    public List<Integer> program=new ArrayList<>();
    public List<List<Integer>> nodes=new ArrayList<>();
    public List<Integer> scores=new ArrayList<>();
    public double mean=0;
    public boolean[] active;
    public int width;
    public int length;
    public int node_size;
    private int outputs=4;
    private int inputs=16;
    public int gen;

    public Individual (List<Integer> program,int width,int length,boolean[] active,List<List<Integer>> nodes,int gen){
        this.program=program;
        this.width=width;
        this.length=length;
        this.active=active;
        this.nodes=nodes;
        this.gen=gen;
        node_size=width*length;


    }

    public void calc_mean(){
        double sum=0;
        for(Integer score:scores){
            sum+=score;
        }
        mean=sum/scores.size();
    }

    public void clear_score(){
        scores.clear();
        mean=0;
    }
}
