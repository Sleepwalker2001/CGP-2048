package game.main;

import javax.swing.*;
import java.awt.*;

public class Window extends Canvas {

    public Window(int width, int heighr, String title, Game game) {
        JFrame frame = new JFrame(title);

        frame.setPreferredSize(new Dimension(width, heighr));
        frame.setMaximumSize(new Dimension(width, heighr));
        frame.setMinimumSize(new Dimension(width, heighr));

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.add(game);
        frame.setVisible(true);
        //game.start();
    }
}
