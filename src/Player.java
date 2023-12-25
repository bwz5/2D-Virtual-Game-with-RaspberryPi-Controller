
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class Player {
    int graphicsX = 350;
    int graphicsY = 250;

    int health = 5;

    int mana = 6;

    int worldRow;
    int worldCol;

    Image healthImage;
    Image manaImage;

    int phase = 1;

    String direction = "down";

    Image playerImage;
    Projectile projectile = null;

    boolean hasKey = false;

    public void addMana(){
        if (mana < 6){
            mana += 1;
        }
    }

    public void takeDamage(int damage, GameComponent game){
        health -= damage;
        if (health < 0){
            game.gameActive = false;
            game.gameLost = true;
        }
    }

    public void drawPlayer(Graphics g, int r, int c, GameComponent game){
        worldRow = r;
        worldCol = c;

        String temp = "img/player" + direction + phase + ".png";
        try {
            BufferedImage myPicture = ImageIO.read(new File(temp));
            playerImage = myPicture.getScaledInstance(25, 25, Image.SCALE_DEFAULT);

            BufferedImage myPicture2 = ImageIO.read(new File("img/heart0.png"));
            healthImage = myPicture2.getScaledInstance(50,50,Image.SCALE_DEFAULT);

            BufferedImage myPicture3 = ImageIO.read(new File("img/mana0.png"));
            manaImage = myPicture3.getScaledInstance(25,25,Image.SCALE_DEFAULT);
        } catch (Exception ignored) {
        }
        g.drawImage(playerImage, graphicsX, graphicsY, null);

        if (projectile != null){
            projectile.drawProjectile(g);
            if (projectile.aliveTime < 0 || projectile.collided(game)){
                projectile = null;
            }
        }
        int currentX = 15;
        int currentY = 435;
        for (int i = 0; i < health; i++){
            g.drawImage(healthImage, currentX, currentY, 35, 35, null);
            currentX += 35;
        }
        currentX = 30;
        currentY = 415;
        for (int i = 0; i < mana; i++){
            g.drawImage(manaImage, currentX, currentY, 25, 25, null);
            currentX += 25;
        }


    }
}
