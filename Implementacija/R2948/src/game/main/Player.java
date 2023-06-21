package game.main;

import java.awt.*;
import java.util.Random;

public class Player extends GameObject {

    Random r = new Random();
    Handler handler;


    public Player(int x, int y, ID id, Handler handler) {
        super(x, y, id);
        this.handler = handler;
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, 70, 70);
    }

    public void tick() {
        x += velX;
        y += velY;

        x = Game.clamp(x, 150, 450 - 75, this);
        y = Game.clamp(y, 150, 450 - 75, this);

        collision();

    }

    private void collision() {
        for(int i = 0; i < handler.object.size(); i++) {
            GameObject tempObject = handler.object.get(i);
            if(tempObject != this) {
                if (tempObject.getId() == this.id) {
                    if (getBounds().intersects(tempObject.getBounds())) {
                        handler.kolizija();
                        handler.addObject(new Player(tempObject.x, tempObject.y, vratiVrstu(tempObject.id), handler));
                        handler.removeObject(this);
                        handler.removeObject(tempObject);
                        //System.out.println("Zajedno");
                    }
                } else {
                    if (getBounds().intersects(tempObject.getBounds())) {
                        kojiGdje(this, tempObject);
                        //System.out.println("dirale se sestre  "  + tempObject.velX + " " + tempObject.velY + " "+ this.velX + " "+ this.velY);
                    }
                }
            }
        }
    }

    private ID vratiVrstu(ID id) {
        String str = id.toString();
        String[] splt = str.split("\\s+");
        int br = Integer.parseInt(splt[1]);
        String strN = "player " + String.valueOf(br * 2);
        //System.out.println(strN);
        return ID.getOperationName(strN);
    }

    private void kojiGdje(GameObject prvi, GameObject drugi) {
        if (prvi.x <= drugi.x && prvi.y == drugi.y) {
            prvi.x = drugi.x - 75;
        } else if (prvi.x == drugi.x && prvi.y <= drugi.y) {
            prvi.y = drugi.y - 75;
        } else if (prvi.x >= drugi.x && prvi.y == drugi.y) {
            prvi.x = drugi.x + 75;
        } else if (prvi.x == drugi.x && prvi.y >= drugi.y) {
            prvi.y = drugi.y + 75;
        }
    }

    public void render(Graphics g) {

        if (id == ID.Player2) g.setColor(Color.decode("#00f6ff"));
        else if (id == ID.Player4) g.setColor(Color.decode("#0af"));
        else if (id == ID.Player8) g.setColor(Color.decode("#06f"));
        else if (id == ID.Player16) g.setColor(Color.decode("#9300b8"));
        else if (id == ID.Player32) g.setColor(Color.decode("#9e008e"));
        else if (id == ID.Player64) g.setColor(Color.decode("#f500d"));
        else if (id == ID.Player128) g.setColor(Color.decode("#d6007d"));
        else if (id == ID.Player256) g.setColor(Color.decode("#d60036"));
        else if (id == ID.Player512) g.setColor(Color.decode("#d60000"));
        else if (id == ID.Player1024) g.setColor(Color.decode("#d63200"));
        else if (id == ID.Player2048) g.setColor(Color.decode("#ffc800"));
        else if (id == ID.Player4096) g.setColor(Color.decode("#4fd600"));


        g.fillRect(x + 10, y + 10, 55, 55);

        String str = id.toString();
        String[] splt = str.split("\\s+");
        String br = splt[1];
        g.setColor(Color.black);
        g.drawString(br, x + 35, y + 42 );
    }

}
