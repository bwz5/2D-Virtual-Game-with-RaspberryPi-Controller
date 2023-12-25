import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Random;

public class NPC {
    int worldRow;
    int worldCol;

    int graphicsX ;
    int graphicsY;

    String name;

    String direction;

    boolean isTalking = false;

    int gameRow;
    int gameCol;

    int phase = 1;
    String[] dialogue = new String[20] ;

    int dialogueIdx = 0;

    Image npcImage;
    public NPC(int r, int c, String n, GameComponent game){
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
        if (o instanceof  NPC){
            NPC e = (NPC)o;
            if (e.name.equals( name) && e.worldRow == worldRow && e.worldCol == worldCol){
                return true;
            }
        }
        return false;
    }

    public void nextDialogue(){
        if (dialogue[dialogueIdx + 1] != null){
            dialogueIdx += 1;
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
        if (game.regen % 2 == 0) {
            if (phase == 2) {
                phase = 1;
            } else {
                phase += 1;
            }
        }
        String temp;

        int tempRow = graphicsY / 25;
        int tempCol = graphicsX  / 25;

        if (!isTalking) {
            if (this.direction.equals("up")) {
                temp = game.records.get(game.worldRow + tempRow - 1).get(game.worldCol + tempCol);
                if (temp.equals("3") || temp.equals("4")) {
                    return;
                }
                graphicsY -= 1;
            } else if (this.direction.equals("down")) {
                temp = game.records.get(game.worldRow + tempRow + 1).get(game.worldCol + tempCol);
                if (temp.equals("3") || temp.equals("4")) {
                    return;
                }
                graphicsY += 1;
            } else if (this.direction.equals("left")) {
                temp = game.records.get(game.worldRow + tempRow).get(game.worldCol + tempCol - 1);
                if (temp.equals("3") || temp.equals("4")) {
                    return;
                }
                graphicsX -= 1;
            } else if (this.direction.equals("right")) {
                temp = game.records.get(game.worldRow + tempRow).get(game.worldCol + tempCol + 1);
                if (temp.equals("3") || temp.equals("4")) {
                    return;
                }
                graphicsX += 1;
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

    public void drawNPC(Graphics g){
        String temp = "img/" + name + direction +  phase + ".png";
        try {
            BufferedImage myPicture = ImageIO.read(new File(temp));
            npcImage = myPicture.getScaledInstance(25, 25, Image.SCALE_DEFAULT);
        } catch (Exception ignored) {
        }
        g.drawImage(npcImage, graphicsX, graphicsY, null);


    }

    public boolean isNear(GameComponent game){
        if ((this.graphicsY + 25 >= game.player.graphicsY && this.graphicsY -25 <= game.player.graphicsY) && ( this.graphicsX + 25 >= game.player.graphicsX && this.graphicsX -25 <= game.player.graphicsX)){
            isTalking = true;
            return true;
        }
        isTalking = false;
        dialogueIdx = 0;
        return false;
    }
    public void showDialogue(Graphics g){
        g.setColor(new Color(0,0,0,205));
        g.fillRoundRect(50,25,600,150,35,35);

        g.setColor(Color.white);
        g.setFont(new Font(Font.SANS_SERIF,Font.ITALIC,25));
        g.drawString(dialogue[dialogueIdx], 100,100);
    }

}
