package game.main;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class KeyInput extends KeyAdapter {

    private Handler handler;
    private Spavn spavn;

    private Boolean dopusti;

    private AI ai;

    boolean block=false;


    public KeyInput(Handler handler, Spavn spavn) {
        this.handler = handler;
        this.spavn = spavn;
        this.dopusti=true;

    }

    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();

        if (dopusti){
            for (int i = 0; i < handler.object.size(); i++) {
                GameObject tempObject = handler.object.get(i);


                if (key == KeyEvent.VK_UP) {
                    tempObject.setVelY(-20);
                }
                if (key == KeyEvent.VK_DOWN) {
                    tempObject.setVelY(20);
                }
                if (key == KeyEvent.VK_RIGHT) {
                    tempObject.setVelX(20);
                }
                if (key == KeyEvent.VK_LEFT) {
                    tempObject.setVelX(-20);
                }

            }
        }

        if(key == KeyEvent.VK_ESCAPE) System.exit(1);
    }

    public void keyReleased(KeyEvent e) {
        if(dopusti){
            int key = e.getKeyCode();

            spavn.setTipkovnica(1);

            for (int i = 0; i < handler.object.size(); i++) {
                GameObject tempObject = handler.object.get(i);


                if (key == KeyEvent.VK_UP) tempObject.setVelY(0);
                if (key == KeyEvent.VK_DOWN) tempObject.setVelY(0);
                if (key == KeyEvent.VK_LEFT) tempObject.setVelX(0);
                if (key == KeyEvent.VK_RIGHT) tempObject.setVelX(0);
                if (key == KeyEvent.VK_1) {
                    dopusti=false;
                    ai = new AI(handler, spavn,1);
                }
                if (key == KeyEvent.VK_2) {
                    dopusti=false;
                    ai = new AI(handler, spavn,2);
                }
                if (key == KeyEvent.VK_3) {
                    dopusti=false;
                    ai = new AI(handler, spavn,3);
                }



                //MOJE
                if (key == KeyEvent.VK_4 && !block) {
                    System.out.println("Pritisnut 4");
                    block=true;
                    dopusti=false;
                    try {
                        CGP myCGP=new CGP(1,8,handler,spavn,4,3,false);
                    } catch (InterruptedException ex) {
                        throw new RuntimeException(ex);
                    }
                }
                if (key == KeyEvent.VK_0 && !block) {
                    System.out.println("Pritisnut 0");
                    block=true;
                    dopusti=false;
                    try {
                        CGP myCGP=new CGP(1,8,handler,spavn,4,3,true);
                    } catch (InterruptedException ex) {
                        throw new RuntimeException(ex);
                    }
                }

            }
        }

    }
}
