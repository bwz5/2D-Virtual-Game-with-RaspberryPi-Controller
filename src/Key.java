import java.awt.*;

public class Key extends Interactable {
    public Key(int r, int c){
        super(r,c,"key");
    }
    @Override
    public void update(GameComponent game){
        int yDiff = this.worldRow - game.worldRow;
        int xDiff = this.worldCol - game.worldCol;
        graphicsX = 25*xDiff;
        graphicsY = 25*yDiff;
        if (collided(game)){
            hasTouched = true;
            game.player.hasKey  = true;
        }
    }
    @Override
    public void draw(Graphics g, GameComponent game){
        update(game);
        if (!hasTouched) {
            g.drawImage(img, graphicsX, graphicsY, null);
        }
    }
}
