import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class Interactable {
    int worldRow;
    int worldCol;

    String name;

    int graphicsX;
    int graphicsY;

    boolean hasTouched = false;

    Image img;

    public Interactable(int r, int c, String name){
        worldRow = r;
        worldCol = c;
        this.name = name;

        String temp = "img/" + name  + ".png";
        try {
            BufferedImage myPicture = ImageIO.read(new File(temp));
            img = myPicture.getScaledInstance(25, 25, Image.SCALE_DEFAULT);
        } catch (Exception ignored) {
        }
    }

    @Override
    public boolean equals(Object o){
        if (o instanceof  Interactable){
            Interactable i = (Interactable) o;
            if (i.name.equals(name) && i.worldCol == worldCol && i.worldRow == worldRow){
                return true;
            }
        }
        return false;
    }
    public void update(GameComponent game){
        int yDiff = this.worldRow - game.worldRow;
        int xDiff = this.worldCol - game.worldCol;
        graphicsX = 25*xDiff;
        graphicsY = 25*yDiff;
        if (collided(game)){
            hasTouched = true;
        }
    }

    public void draw(Graphics g, GameComponent game){
        update(game);
        g.drawImage(img, graphicsX, graphicsY, null);

    }

    public boolean collided(GameComponent game){
        return (this.graphicsY + 15 >= game.player.graphicsY && this.graphicsY -15 <= game.player.graphicsY) && ( this.graphicsX + 15 >= game.player.graphicsX && this.graphicsX -15 <= game.player.graphicsX);
    }
}
