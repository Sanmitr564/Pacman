public class Inky extends Ghost{

    public Inky(int x, int y, Direction direction, int section, int numSections){
        super(x, y, direction, section, numSections);
    }

    public void update(Tile[][] board, Player player, Blinky blinky){
        int[] offsets = DirectionHelp.getOffsets(player.getDirection());
        int targetRow = (player.getY() + offsets[0] - blinky.getY()) + player.getY();
        int targetCol = (player.getX() + offsets[1] - blinky.getX()) + player.getX();
        System.out.println("TargetRow: " + targetRow);
        System.out.println("TargetCol: " + targetCol);
        updateDirection(board, targetRow, targetCol);
        move();
    }
}
