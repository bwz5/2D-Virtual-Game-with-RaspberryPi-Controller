import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.*;
import java.util.List;

public class GameComponent extends JLabel implements MouseListener, KeyListener, Runnable {

    public final Thread gameThread;

    boolean gameLost = false;

    boolean gameActive;

    Player player = new Player();

    int worldRow = 8;  // + 10
    int worldCol = 16; // + 14

    int regen = 0;

    int currentLevel = 0;
    public List<List<String>> records = new ArrayList<>();

    List<Enemy> enemies = new ArrayList<>();

    List<NPC> npcs = new ArrayList<>();

    List<Interactable> interactables = new ArrayList<>();

    public GameComponent(){
        gameActive = false;

        addMouseListener(this);
        addKeyListener(this);
        setFocusable(true);
        requestFocus();

        setVisible(true);
        setPreferredSize(new Dimension(700,500));
        gameThread = new Thread(this);

        loadMap("maps/level" + currentLevel + ".csv");
    }

    public void loadMap(String fileName){
        records.clear();
        try {
            BufferedReader br = new BufferedReader(new FileReader(fileName));
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                records.add(Arrays.asList(values));
            }
        } catch (Exception e) {}
    }

    public void drawNPCS(Graphics g){
        for (NPC n : npcs){
            n.update(this);
            n.drawNPC(g);
            if (n.isNear(this)){
                n.showDialogue(g);
            }
        }
    }

    @Override
    public void paintComponent(Graphics g){
        if (gameActive) {
            try {
                gameThread.sleep(50);
            } catch (Exception ignored){}

            if (regen % 20 == 0){
                player.addMana();
            }
            super.paintComponent(g);
            tileDrawer.drawTiles(g, this);

            drawEnemies(g);
            drawInteractables(g, this);

            player.drawPlayer(g, worldRow + 10, worldCol + 14, this);

            drawNPCS(g);


            repaint();
            regen += 1;
        } else {
            super.paintComponent(g);

            if (!gameLost) { // game has not started
                try {
                    BufferedImage myPicture = ImageIO.read(new File("img/fireboystart.png"));
                    Image temp = myPicture.getScaledInstance(700, 500, Image.SCALE_DEFAULT);
                    g.drawImage(temp,0,0,null);
                } catch (Exception ignored) {
                }
            } else { // game has been lost
                try {
                    BufferedImage myPicture = ImageIO.read(new File("img/gameover.png"));
                    Image temp = myPicture.getScaledInstance(700, 500, Image.SCALE_DEFAULT);
                    g.drawImage(temp,0,0,null);
                } catch (Exception ignored) {
                }
            }
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    public void drawInteractables(Graphics g, GameComponent game){
        for (Interactable i : interactables){
            i.draw(g, game);
        }
    }

    public void drawEnemies( Graphics g){
        for (Enemy e : enemies){
            e.update(this);
            if (e.isAlive){
                e.drawEnemy(g);
            }
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (player.phase == 1){
            player.phase = 0;
        } else {
            player.phase = 1;
        }
        if (e.getKeyCode() == KeyEvent.VK_UP){
            player.direction = "up";
            if (!records.get(player.worldRow-1).get(player.worldCol).equals("3") && !records.get(player.worldRow-1).get(player.worldCol).equals("4")){
                worldRow -= 1;
            }
        } else if (e.getKeyCode() == KeyEvent.VK_DOWN){
            player.direction = "down";
            if (!records.get(player.worldRow+1).get(player.worldCol).equals("3") && !records.get(player.worldRow+1).get(player.worldCol).equals("4")){
                worldRow += 1;
            }
        } else if (e.getKeyCode() == KeyEvent.VK_LEFT){
            player.direction = "left";
            if (!records.get(player.worldRow).get(player.worldCol-1).equals("3") && !records.get(player.worldRow).get(player.worldCol-1).equals("4")){
                worldCol -= 1;
            }

        } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            player.direction = "right";
            if (!records.get(player.worldRow).get(player.worldCol+1).equals("3") && !records.get(player.worldRow).get(player.worldCol+1).equals("4")){
                worldCol += 1;
            }
        } else if (e.getKeyCode() == KeyEvent.VK_X){
            if (player.mana == 0){
                return;
            }
            player.projectile = new Projectile(player.direction);
            player.mana -= 1;
        } else if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            for (NPC n : npcs){
                if (n.isTalking){
                    n.nextDialogue();
                }
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void run() {
        repaint();
    }
}
