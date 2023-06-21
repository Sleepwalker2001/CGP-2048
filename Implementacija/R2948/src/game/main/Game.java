package game.main;

import java.awt.*;
import java.awt.image.BufferStrategy;
import java.util.Random;

public class Game extends Canvas implements Runnable {

    public static final int WIDTH = 600, HEIGHT = 600;//WIDTH / 12 * 9;
    private Thread thread;
    private Thread bipbup;
    private boolean running = false;

    private Random r;
    private Handler handler;
    private Spavn spavner;
    private Score score;

    private AI ai;

    public Game() {
        handler = new Handler();
        score = new Score(handler);
        spavner = new Spavn(handler,score);
        this.addKeyListener(new KeyInput(handler, spavner));

        new Window(WIDTH, HEIGHT, "2048A", this);


        r = new Random();

        int rbrX = r.nextInt(0, 4);
        int rbrY = r.nextInt(0, 4);

        handler.addObject(new Player(150 + rbrX * 75, 150 + rbrY * 75, ID.Player2, handler));
        //handler.addObject(new Player(150, 150, ID.Player2, handler));
        start();
    }
    public synchronized void start() {
        thread = new Thread(this);
        thread.start();
        running = true;
    }

    public synchronized void stop() {
        try {
            thread.join();
            running = false;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void run() {
        this.requestFocus();
        long lastTime = System.nanoTime();
        double amountOfTicks = 60.0;
        double ns = 1000000000 / amountOfTicks;
        double delta = 0;
        long timer = System.currentTimeMillis();
        int frames = 0;
        while(running) {
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;
            while (delta >= 1) {
                try {
                    tick();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                delta--;
            }
            if(running)
                rander();
            frames++;

            if(System.currentTimeMillis() - timer > 1000) {
                timer += 1000;
                //System.out.println("FPS: " + frames);
                frames = 0;
            }
        }
        stop();
    }

    private void tick() throws InterruptedException {
        handler.tick();
        spavner.tick();
        score.tick();
    }

    private void rander() {
        BufferStrategy bs = this.getBufferStrategy();
        if (bs == null) {
            this.createBufferStrategy(3);
            return;
        }

        Graphics g = bs.getDrawGraphics();
        g.setColor(Color.lightGray);
        g.fillRect(0, 0, WIDTH, HEIGHT);

        handler.render(g);
        score.render(g);

        g.dispose();
        bs.show();
    }

    public static int clamp(int var, int min, int max, Player pl) {
        if(var >= max) {
            //pl.setVelX(0);
            //pl.setVelY(0);
            return var = max;
        } else if (var <= min) {
            //pl.setVelX(0);
            //pl.setVelY(0);

            return var = min;
        } else {
            return var;
        }
    }

    public static void main(String args[]) {
        new Game();
    }
}
