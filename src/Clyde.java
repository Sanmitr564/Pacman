public class Clyde extends Ghost{

    public Clyde(int x, int y, Direction direction, int section, int numSections, boolean released){
        super(x, y, direction, section, numSections, released);
    }


    public void update(Tile[][] board, Player player, Blinky blinky) {
        if(Math.abs(player.getX() - getX()) + Math.abs(player.getY() - getY()) < 8){
            updateDirection(board, 0, 0);
        }else{
            int[] offsets = DirectionHelp.getOffsets(player.getDirection());
            int targetRow = (player.getY() + offsets[0] - blinky.getY()) + player.getY();
            int targetCol = (player.getX() + offsets[1] - blinky.getX()) + player.getX();
            updateDirection(board, targetRow, targetCol);
        }
        if(isReleased()){
            move();
        }
    }
}
