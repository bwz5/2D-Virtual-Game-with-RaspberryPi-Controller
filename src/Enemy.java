import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Random;

public class Enemy {
    boolean isAlive = true;
    int health ;

    int damage = 1;
    int worldRow;
    int worldCol;

    int graphicsX ;
    int graphicsY;

    String name;

    String direction = "down";

    int gameRow;
    int gameCol;

    int phase = 1;


    Image enemyImage;
    public Enemy(int r, int c, String n, int health, GameComponent game){
        this.health = health;
        worldRow = r;
        worldCol = c;
        name = n;

        gameRow = game.worldRow;
        gameCol = game.worldCol;

        int yDiff = this.worldRow - game.worldRow;
        int xDiff = this.worldCol - game.worldCol;
        graphicsX = 25*xDiff;
        graphicsY = 25*yDiff;

    }

    @Override
    public boolean equals(Object o ){
        if (o instanceof  Enemy){
            Enemy e = (Enemy)o;
            if (e.name.equals( name) && e.worldRow == worldRow && e.worldCol == worldCol){
                return true;
            }
        }
        return false;
    }

    public void takeDamage(int d){
        this.health -= d;
        if (this.health <= 0){
            isAlive = false;
        }
    }

    public void update(GameComponent game){
        if (gameCol  < game.worldCol){
            graphicsX -= 25;
        } else if (gameCol > game.worldCol){
            graphicsX += 25;
        } else if (gameRow < game.worldRow){
            graphicsY -= 25;
        } else if (gameRow > game.worldRow){
            graphicsY += 25;
        }
        gameCol = game.worldCol;
        gameRow = game.worldRow;

        if (game.regen % 20 == 0){
            moveRandom();
        }
        if (game.regen % 8 == 0) {
            if (phase == 2) {
                phase = 1;
            } else {
                phase += 1;
            }
        }
        String temp;

        int tempRow = graphicsY / 25;
        int tempCol = graphicsX  / 25;

        if (this.direction.equals("up")){
            temp = game.records.get(game.worldRow + tempRow).get( game.worldCol + tempCol);
            if (temp.equals("3") || temp.equals("4")){
                return;
            }
            graphicsY -= 1;
        } else if (this.direction.equals("down")){
            temp = game.records.get(game.worldRow + tempRow+1).get( game.worldCol + tempCol);
            if (temp.equals("3") || temp.equals("4")){
                return;
            }
            graphicsY += 1;
        } else if (this.direction.equals("left")){
            temp = game.records.get(game.worldRow + tempRow).get( game.worldCol + tempCol-1);
            if (temp.equals("3") || temp.equals("4")){
                return;
            }
            graphicsX -= 1;
        } else if (this.direction.equals("right")){
            temp = game.records.get(game.worldRow + tempRow).get( game.worldCol + tempCol+1);
            if (temp.equals("3") || temp.equals("4")){
                return;
            }
            graphicsX += 1;
        }
        if (isAlive) {
            if (graphicsX / 25 == game.player.graphicsX / 25 && graphicsY / 25 == game.player.graphicsY / 25) {
                if (game.regen % 2 == 0) {
                    game.player.takeDamage(damage, game);
                }
            }
        }
    }

    public void moveRandom(){
        Random t = new Random();
        int test = Math.abs(t.nextInt()) % 101;
        if (test <= 25){
            this.direction = "up";
        } else if (test <= 50){
            this.direction = "down";
        } else if (test <= 75){
            this.direction = "left";
        } else if (test <= 100){
            this.direction = "right";
        }

    }

    public void drawEnemy(Graphics g){


        if (isAlive) {
            String temp = "img/" + name + phase + ".png";
            try {
                BufferedImage myPicture = ImageIO.read(new File(temp));
                enemyImage = myPicture.getScaledInstance(25, 25, Image.SCALE_DEFAULT);
            } catch (Exception ignored) {
            }
            g.drawImage(enemyImage, graphicsX, graphicsY, null);
        }
    }
}
