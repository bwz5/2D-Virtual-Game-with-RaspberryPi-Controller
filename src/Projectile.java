import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

class Projectile {
    int graphicsX = 350;
    int graphicsY = 250;

    int aliveTime = 10;

    int damage = 5;
    String direction;

    int speed = 20;

    public Projectile(String direction){
        this.direction = direction;
    }

    public void drawProjectile(Graphics g){
        Image projectileImage = null;
        try {
            String temp = "img/projectile" + direction + ".png";
            BufferedImage myPicture = ImageIO.read(new File(temp));
            projectileImage = myPicture.getScaledInstance(25, 25, Image.SCALE_DEFAULT); // transform it
        } catch (Exception ignored) {
        }
        g.drawImage(projectileImage, graphicsX, graphicsY, null);
        if (direction == "up"){
            graphicsY -= speed;
        } else if (direction == "left"){
            graphicsX -= speed;
        } else if (direction == "right"){
            graphicsX += speed;
        } else if (direction == "down"){
            graphicsY += speed;
        }
        aliveTime -= 1;
    }

    public boolean collided(GameComponent game){
        for (Enemy e : game.enemies){
            if (!e.isAlive){
                continue;
            }

            boolean Cond = (this.graphicsY + 24 >= e.graphicsY && this.graphicsY -24 <= e.graphicsY) && ( this.graphicsX + 24 >= e.graphicsX && this.graphicsX -24 <= e.graphicsX);
            if (Cond){

                e.takeDamage(this.damage);

                return true;
            }
        }
        return false;
    }
}