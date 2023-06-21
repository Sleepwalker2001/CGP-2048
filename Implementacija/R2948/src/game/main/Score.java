package game.main;

import java.awt.*;

public class Score {

    private Handler handler;
    private int rez = 0;

    public Score(Handler handler) {
        this.handler = handler;
    }

    public void tick() {
        counter();

    }

    private void counter() {
        rez = 0;
        for (int i = 0; i < handler.object.size(); i++) {
            GameObject tempObject = handler.object.get(i);
            String str = tempObject.getId().toString();
            String[] splt = str.split("\\s+");
            int br = Integer.parseInt(splt[1]);
            rez = rez + br;
        }
    }

    public void render(Graphics g) {
        g.setColor(Color.black);
        g.drawString("Score: " + String.valueOf(rez), 270, 100 );

    }

    public int getRez(){
        return rez;
    }
}
