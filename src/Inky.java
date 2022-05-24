public class Inky extends Ghost{

    public Inky(int x, int y, Direction direction, int section, int numSections, boolean released){
        super(x, y, direction, section, numSections, released);
    }

    public void update(Tile[][] board, Player player, Blinky blinky){
        int[] offsets = DirectionHelp.getOffsets(player.getDirection());
        int targetRow = (player.getY() + 2*offsets[0] - blinky.getY()) + player.getY();
        int targetCol = (player.getX() + 2*offsets[1] - blinky.getX()) + player.getX();
        updateDirection(board, targetRow, targetCol);
        if(isReleased()){
            move();
        }
    }
}
