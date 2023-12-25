
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class tileDrawer {

    static Image pathImage;

    static Image walkableShrubs;

    static Image bushBarrier;

    static Image blockBarrier;

    static Image grass;

    public static void drawTiles(Graphics g, GameComponent game){
        try {
            BufferedImage myPicture = ImageIO.read(new File("img/path00.png"));
            pathImage = myPicture.getScaledInstance(25,25,Image.SCALE_DEFAULT); // transform it

            BufferedImage myPicture2 = ImageIO.read(new File("img/shrubs0.png"));
            walkableShrubs = myPicture2.getScaledInstance(25,25,Image.SCALE_DEFAULT);

            BufferedImage myPicture3 = ImageIO.read(new File("img/bush0.png"));
            bushBarrier = myPicture3.getScaledInstance(25,25,Image.SCALE_DEFAULT);

            BufferedImage myPicture4 = ImageIO.read(new File("img/block0.png"));
            blockBarrier = myPicture4.getScaledInstance(25,25,Image.SCALE_DEFAULT);

            BufferedImage myPicture5 = ImageIO.read(new File("img/grass0.png"));
            grass = myPicture5.getScaledInstance(25,25,Image.SCALE_DEFAULT);
        } catch (Exception ignored){}

        int currentX = 0;
        int currentY = 0;

        for (int i = game.worldRow; i < game.worldRow + 20; i++){
            for (int j = game.worldCol; j < game.worldCol + 28; j++){

                String temp = game.records.get(i).get(j);
                if (temp.equals("1")){
                    g.drawImage(pathImage,currentX,currentY,null);
                } else if (temp.equals("2")){
                    g.drawImage(grass,currentX,currentY,null);
                    g.drawImage(walkableShrubs,currentX,currentY,null);
                } else if (temp.equals("3")){
                    g.drawImage(grass,currentX,currentY,null);
                    g.drawImage(bushBarrier,currentX,currentY,null);
                } else if (temp.equals("4")){
                    g.drawImage(grass,currentX,currentY,null);
                    g.drawImage(blockBarrier,currentX,currentY,null);
                } else if (temp.equals("0")){
                    g.drawImage(grass,currentX,currentY,null);
                } else if (temp.equals("B")){
                    Enemy e = new Enemy(i,j,"blob", 10, game);
                    int tempCount = 0;
                    for (Enemy temper : game.enemies){
                        if (temper.equals(e)){
                            tempCount += 1;
                        }
                    }
                    if (tempCount == 0) {
                        game.enemies.add(e);
                    }
                    g.drawImage(grass,currentX,currentY,null);

                } else if (temp.equals("K")){
                    Interactable k = new Key(i,j);
                    int tempCount = 0;
                    for (Interactable temper : game.interactables){
                        if (temper.equals(k)){
                            tempCount += 1;
                        }
                    }
                    if (tempCount == 0){
                        game.interactables.add(k);
                    }
                    g.drawImage(grass,currentX,currentY,null);

                } else if (temp.equals("D")){
                    Interactable k = new Door(i,j);
                    int tempCount = 0;
                    for (Interactable temper : game.interactables){
                        if (temper.equals(k)){
                            tempCount += 1;
                        }
                    }
                    if (tempCount == 0){
                        game.interactables.add(k);
                    }
                    g.drawImage(grass,currentX,currentY,null);

                } else if (temp.equals("O")){
                    NPC n = new OldMan(i,j,game);
                    int tempCount = 0;
                    for (NPC temper : game.npcs){
                        if (temper.equals(n)){
                            tempCount += 1;
                        }
                    }
                    if (tempCount == 0){
                        game.npcs.add(n);
                    }
                    g.drawImage(grass,currentX,currentY,null);
                }
                currentX += 25;
            }
            currentX = 0;
            currentY += 25;
        }
    }
}
