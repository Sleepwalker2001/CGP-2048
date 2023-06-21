package game.main;
import java.io.*;
import java.util.Random;

public class AI {

    private Handler handler;

    private Spavn spavn;

    private Thread bipbup;

    private int koji;

    public AI(Handler handler,Spavn spavn, int koji){
        this.handler=handler;
        this.spavn=spavn;
        this.koji=koji;
        if (koji==1) {
            this.bipbup = new Thread(() -> igraj1());
            this.bipbup.start();
        }
        if (koji==2) {
            this.bipbup = new Thread(() -> igraj2());
            this.bipbup.start();
        }
        if (koji==3) {
            this.bipbup = new Thread(() -> igraj3());
            this.bipbup.start();
        }
    }

    public void igraj1(){
        System.out.println("AI1");
        boolean defa=false;
        while(true) {
            int xos=0;
            int yos=0;
            for (int i = 0; i < handler.object.size()-1; i++) {
                int xplus=0;
                int yplus=0;
                GameObject object1 = handler.object.get(i);
                boolean u= object1.y != 150;
                boolean d= object1.y != 375;
                boolean l= object1.x != 150;
                boolean r= object1.x != 375;
                boolean uu= object1.y != 150 && object1.y != 225;
                boolean dd= object1.y != 375 && object1.y != 300;
                boolean ll= object1.x != 150 && object1.x != 225;
                boolean rr= object1.x != 375 && object1.y != 300;
                boolean uuu= object1.y != 150 && object1.y != 225 && object1.y != 150;
                boolean ddd= object1.y != 375 && object1.y != 300 && object1.y != 225;
                boolean lll= object1.x != 150 && object1.x != 225 && object1.x != 150;
                boolean rrr= object1.x != 375 && object1.y != 300 && object1.y != 225;

                for (int j = 0; j < handler.object.size(); j++){
                    GameObject object2=handler.object.get(j);
                    if (object2.y==object1.y) {
                        if (object2.x > object1.x) {
                            if (object2.x == object1.x + 75 && object2.id != object1.id && r) {
                                r = false;
                            } else if (object2.x == object1.x + 150 && object2.id != object1.id && r && rr) {
                                rr = false;
                            }else if (object2.x == object1.x + 225 && object2.id != object1.id && r && rr && rrr) {
                                rrr= false;
                            }
                        }
                        else if(object2.x < object1.x) {
                            if (object2.x == object1.x - 75 && object2.id != object1.id && l) {
                                l = false;
                            } else if (object2.x == object1.x - 150 && object2.id != object1.id && l && ll) {
                                ll = false;
                            }else if (object2.x == object1.x - 225 && object2.id != object1.id && l && ll && lll) {
                                lll= false;
                            }
                        }
                    }

                    else if (object2.x==object1.x) {
                        if (object2.y > object1.y) {
                            if (object2.y == object1.y + 75 && object2.id != object1.id && d) {
                                d = false;
                            } else if (object2.y == object1.y + 150 && object2.id != object1.id && d && dd) {
                                dd = false;
                            }else if (object2.y == object1.y + 225 && object2.id != object1.id && d && dd && ddd) {
                                ddd= false;
                            }
                        } else if (object2.y < object1.y) {
                            if (object2.y == object1.y - 75 && object2.id != object1.id && u) {
                                u = false;
                            } else if (object2.y == object1.y - 150 && object2.id != object1.id && u && uu) {
                                uu = false;
                            }else if (object2.y == object1.y - 225 && object2.id != object1.id && u && uu && uuu) {
                                uuu= false;
                            }
                        }
                    }
                }


                //prvi do
                for (int j = i+1; j < handler.object.size(); j++){
                    GameObject object2=handler.object.get(j);
                    //System.out.println(object1.id+" "+object1.x+" "+object1.y +" "+object2.id+" "+object2.x+" "+object2.y);
                    if (object2.y==object1.y){
                        if(object2.x==object1.x + 75 && r){
                            xplus=xplus+(object2.id==object1.id?1:0);
                            //System.out.println(object1.id + "desno1 " + xplus);
                            r=false;
                        }
                        else if(object2.x==object1.x - 75 && l){
                            xplus=xplus+(object2.id==object1.id?-1:0);
                            //System.out.println(object1.id + "lijevo1 " + xplus);
                            l=false;
                        }
                    }

                    else if (object2.x==object1.x) {
                        if (object2.y == object1.y - 75 && u) {
                            yplus = yplus + (object2.id == object1.id ? 1 : 0);
                            //System.out.println(object1.id + "gore1 " + yplus);
                            u = false;
                        } else if (object2.y == object1.y + 75 && d) {
                            yplus = yplus + (object2.id == object1.id ? -1 : 0);
                            //System.out.println(object1.id + "dolje1 " + yplus);
                            d = false;
                        }
                    }
                }
                //drugi do
                for (int j = i+1; j < handler.object.size(); j++){
                    GameObject object2=handler.object.get(j);

                    if (object2.y==object1.y){
                        if(object2.x==object1.x + 150 && r && rr){
                            xplus=xplus+(object2.id==object1.id?1:0);
                            //System.out.println(object1.id + "desno2 " + xplus);
                            r=false;
                        }
                        else if(object2.x==object1.x - 150 && l && ll){
                            xplus=xplus+(object2.id==object1.id?-1:0);
                            //System.out.println(object1.id + "lijevo2 " + xplus);
                            l=false;
                        }
                    }

                    else if (object2.x==object1.x) {
                        if (object2.y == object1.y - 150 && u && uu) {
                            yplus = yplus + (object2.id == object1.id ? 1 : 0);
                            //System.out.println(object1.id + "gore2 " + yplus);
                            u = false;
                        } else if (object2.y == object1.y + 150 && d && dd) {
                            yplus = yplus + (object2.id == object1.id ? -1 : 0);
                            //System.out.println(object1.id + "dolje2 " + yplus);
                            d = false;
                        }
                    }
                }
                //treci do
                for (int j = i+1; j < handler.object.size(); j++){
                    GameObject object2=handler.object.get(j);

                    if (object2.y==object1.y){
                        if(object2.x==object1.x + 225 && r && rr && rrr){
                            xplus=xplus+(object2.id==object1.id?1:0);
                            //System.out.println(object1.id + "desno3 " + xplus);
                            r=false;
                        }
                        else if(object2.x==object1.x - 225 && l && ll && lll){
                            xplus=xplus+(object2.id==object1.id?-1:0);
                            //System.out.println(object1.id + "lijevo3 " + xplus);
                            l=false;
                        }
                    }

                    else if (object2.x==object1.x) {
                        if (object2.y == object1.y - 225 && u && uu && uuu) {
                            yplus = yplus + (object2.id == object1.id ? 1 : 0);
                            //System.out.println(object1.id + "gore3 " + yplus);
                            u = false;
                        } else if (object2.y == object1.y + 225 && d && dd && ddd) {
                            yplus = yplus + (object2.id == object1.id ? -1 : 0);
                            //System.out.println(object1.id + "dolje3 " + yplus);
                            d = false;
                        }
                    }
                }
                xos=xos+xplus;
                yos=yos+yplus;
                //System.out.println("x: "+ xos +", y: " + yos);
            }
            if(xos==0 && yos==0){
                if(defa){
                    defa=false;
                    spavn.dolje();
                }
                else {
                    defa=true;
                    spavn.lijevo();
                }
            }
            else if(Math.abs(xos)>Math.abs(yos)){
                if(xos>0){System.out.println("desno");spavn.desno(); }
                else {System.out.println("lijevo");spavn.lijevo();}
            }
            else if(Math.abs(xos)<Math.abs(yos)) {
                if (yos>0){System.out.println("gore");spavn.gore();}
                else {System.out.println("dolje");spavn.dolje();}
            }
            else {
                Random random=new Random();
                int coin=random.nextInt(2);
                if(coin==0){
                    if(xos>0){System.out.println("desno");spavn.desno(); }
                    else {System.out.println("lijevo");spavn.lijevo();}
                }
                else {
                    if (yos>0){System.out.println("gore");spavn.gore();}
                    else {System.out.println("dolje");spavn.dolje();}
                }
            }
        }
    }

    public void igraj2(){
        System.out.println("AI2");
        boolean defa=false;
        while(true) {
            int xos=0;
            int yos=0;
            for (int i = 0; i < handler.object.size(); i++) {
                int xplus=0;
                int yplus=0;
                GameObject object1 = handler.object.get(i);
                boolean u= object1.y != 150;
                boolean d= object1.y != 375;
                boolean l= object1.x != 150;
                boolean r= object1.x != 375;
                boolean uu= object1.y != 150 && object1.y != 225;
                boolean dd= object1.y != 375 && object1.y != 300;
                boolean ll= object1.x != 150 && object1.x != 225;
                boolean rr= object1.x != 375 && object1.y != 300;
                boolean uuu= object1.y != 150 && object1.y != 225 && object1.y != 150;
                boolean ddd= object1.y != 375 && object1.y != 300 && object1.y != 225;
                boolean lll= object1.x != 150 && object1.x != 225 && object1.x != 150;
                boolean rrr= object1.x != 375 && object1.y != 300 && object1.y != 225;

                for (int j = 0; j < handler.object.size(); j++){
                    GameObject object2=handler.object.get(j);
                    if (object2.y==object1.y) {
                        if (object2.x > object1.x) {
                            if (object2.x == object1.x + 75 && object2.id != object1.id && r) {
                                r = false;
                            } else if (object2.x == object1.x + 150 && object2.id != object1.id && r && rr) {
                                rr = false;
                            }else if (object2.x == object1.x + 225 && object2.id != object1.id && r && rr && rrr) {
                                rrr= false;
                            }
                        }
                        else if(object2.x < object1.x) {
                            if (object2.x == object1.x - 75 && object2.id != object1.id && l) {
                                l = false;
                            } else if (object2.x == object1.x - 150 && object2.id != object1.id && l && ll) {
                                ll = false;
                            }else if (object2.x == object1.x - 225 && object2.id != object1.id && l && ll && lll) {
                                lll= false;
                            }
                        }
                    }

                    else if (object2.x==object1.x) {
                        if (object2.y > object1.y) {
                            if (object2.y == object1.y + 75 && object2.id != object1.id && d) {
                                d = false;
                            } else if (object2.y == object1.y + 150 && object2.id != object1.id && d && dd) {
                                dd = false;
                            }else if (object2.y == object1.y + 225 && object2.id != object1.id && d && dd && ddd) {
                                ddd= false;
                            }
                        } else if (object2.y < object1.y) {
                            if (object2.y == object1.y - 75 && object2.id != object1.id && u) {
                                u = false;
                            } else if (object2.y == object1.y - 150 && object2.id != object1.id && u && uu) {
                                uu = false;
                            }else if (object2.y == object1.y - 225 && object2.id != object1.id && u && uu && uuu) {
                                uuu= false;
                            }
                        }
                    }
                }


                //prvi do
                for (int j = 0; j < handler.object.size(); j++){
                    GameObject object2=handler.object.get(j);
                    //System.out.println(object1.id+" "+object1.x+" "+object1.y +" "+object2.id+" "+object2.x+" "+object2.y);
                    if (object2.y==object1.y){
                        if(object2.x==object1.x + 75 && r){
                            xplus=xplus+(object2.id==object1.id?1:0);
                            //System.out.println(object1.id + "desno1 " + xplus);
                            r=false;
                        }
                        else if(object2.x==object1.x - 75 && l){
                            xplus=xplus+(object2.id==object1.id?1:0);
                            //System.out.println(object1.id + "lijevo1 " + xplus);
                            l=false;
                        }
                    }

                    else if (object2.x==object1.x) {
                        if (object2.y == object1.y - 75 && u) {
                            yplus = yplus + (object2.id == object1.id ? 1 : 0);
                            //System.out.println(object1.id + "gore1 " + yplus);
                            u = false;
                        } else if (object2.y == object1.y + 75 && d) {
                            yplus = yplus + (object2.id == object1.id ? 1 : 0);
                            //System.out.println(object1.id + "dolje1 " + yplus);
                            d = false;
                        }
                    }
                }
                //drugi do
                for (int j = i+1; j < handler.object.size(); j++){
                    GameObject object2=handler.object.get(j);

                    if (object2.y==object1.y){
                        if(object2.x==object1.x + 150 && r && rr){
                            xplus=xplus+(object2.id==object1.id?1:0);
                            //System.out.println(object1.id + "desno2 " + xplus);
                            r=false;
                        }
                        else if(object2.x==object1.x - 150 && l && ll){
                            xplus=xplus+(object2.id==object1.id?1:0);
                            //System.out.println(object1.id + "lijevo2 " + xplus);
                            l=false;
                        }
                    }

                    else if (object2.x==object1.x) {
                        if (object2.y == object1.y - 150 && u && uu) {
                            yplus = yplus + (object2.id == object1.id ? 1 : 0);
                            //System.out.println(object1.id + "gore2 " + yplus);
                            u = false;
                        } else if (object2.y == object1.y + 150 && d && dd) {
                            yplus = yplus + (object2.id == object1.id ? 1 : 0);
                            //System.out.println(object1.id + "dolje2 " + yplus);
                            d = false;
                        }
                    }
                }
                //treci do
                for (int j = i+1; j < handler.object.size(); j++){
                    GameObject object2=handler.object.get(j);

                    if (object2.y==object1.y){
                        if(object2.x==object1.x + 225 && r && rr && rrr){
                            xplus=xplus+(object2.id==object1.id?1:0);
                            //System.out.println(object1.id + "desno3 " + xplus);
                            r=false;
                        }
                        else if(object2.x==object1.x - 225 && l && ll && lll){
                            xplus=xplus+(object2.id==object1.id?1:0);
                            //System.out.println(object1.id + "lijevo3 " + xplus);
                            l=false;
                        }
                    }

                    else if (object2.x==object1.x) {
                        if (object2.y == object1.y - 225 && u && uu && uuu) {
                            yplus = yplus + (object2.id == object1.id ? 1 : 0);
                            //System.out.println(object1.id + "gore3 " + yplus);
                            u = false;
                        } else if (object2.y == object1.y + 225 && d && dd && ddd) {
                            yplus = yplus + (object2.id == object1.id ? 1 : 0);
                            //System.out.println(object1.id + "dolje3 " + yplus);
                            d = false;
                        }
                    }
                }
                xos=xos+xplus;
                yos=yos+yplus;
                //System.out.println("x: "+ xos +", y: " + yos);
            }
            Random random=new Random();
            int coin=random.nextInt(2);
            if(xos==0 && yos==0){
                if(defa){
                    defa=false;
                    spavn.dolje();
                }
                else {
                    defa=true;
                    spavn.lijevo();
                }
            }
            else if(Math.abs(xos)>Math.abs(yos)){
                if(coin==0){System.out.println("desno");spavn.desno(); }
                else {System.out.println("lijevo");spavn.lijevo();}
            }
            else if(Math.abs(xos)<Math.abs(yos)) {
                if (coin==0){System.out.println("gore");spavn.gore();}
                else {System.out.println("dolje");spavn.dolje();}
            }
            else {
                int coin2=random.nextInt(2);
                if(coin==0){
                    if(coin2==0){System.out.println("desno");spavn.desno(); }
                    else {System.out.println("lijevo");spavn.lijevo();}
                }
                else {
                    if (coin2==0){System.out.println("gore");spavn.gore();}
                    else {System.out.println("dolje");spavn.dolje();}
                }
            }
        }
    }
    public void igraj3(){
        System.out.println("AI3");
        while(true){
            spavn.dolje();
            spavn.lijevo();
        }
    }
}
