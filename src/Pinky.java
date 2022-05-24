public class Pinky extends Ghost{

    public Pinky(int x, int y, Direction direction, int section, int numSections, boolean released){
        super(x, y, direction, section, numSections, released);
    }

    public void update(Tile[][] board, Player player){
        int[] offsets = DirectionHelp.getOffsets(player.getDirection());
        int targetRow = player.getY() + offsets[0] * 4;
        int targetCol = player.getX() + offsets[1] * 4;
        updateDirection(board, targetRow, targetCol);
        if(isReleased()){
            move();
        }
    }
}
