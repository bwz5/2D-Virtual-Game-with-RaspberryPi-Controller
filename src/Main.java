import javax.swing.*;
import java.awt.*;

public class Main {

    public static GameComponent game;
    public static Button startButton;

    public static JFrame frame;
    public static void createAndShowGUI(){
        frame = new JFrame("FireBoy");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setResizable(false);

        game = new GameComponent();
        frame.add(game);

        startButton = new Button("Start Game");
        startButton.addActionListener((e)->
                startGame()
        );
        startButton.setFocusable(false);

        frame.add(startButton, BorderLayout.SOUTH);

        frame.pack();
        frame.setVisible(true);
    }

    public static void startGame(){
        game.gameActive = true;
        game.gameThread.start();
        frame.remove(startButton);
        frame.pack();
    }


    public static void main (String[] args){
        SwingUtilities.invokeLater(()-> createAndShowGUI());
    }
}
