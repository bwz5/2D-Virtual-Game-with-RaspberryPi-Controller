public class Door extends Interactable{
    boolean unlocked;
    public Door(int r, int c){
        super(r,c,"door");
    }
    @Override
    public void update(GameComponent game){
        int yDiff = this.worldRow - game.worldRow;
        int xDiff = this.worldCol - game.worldCol;
        graphicsX = 25*xDiff;
        graphicsY = 25*yDiff;
        if (collided(game)){
            if (game.player.hasKey){
                unlocked = true;
//
            }
        }
    }
}
