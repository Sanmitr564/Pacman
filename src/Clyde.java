public class Clyde extends Ghost{

    public Clyde(int x, int y, Direction direction, int section, int numSections){
        super(x, y, direction, section, numSections);
    }


    public void update(Tile[][] board, Player player, Blinky blinky) {
        System.out.println(Math.abs(player.getX() - getX()) + Math.abs(player.getY() - getY()));
        if(Math.abs(player.getX() - getX()) + Math.abs(player.getY() - getY()) < 4){
            updateDirection(board, 0, 0);
        }else{
            int[] offsets = DirectionHelp.getOffsets(player.getDirection());
            int targetRow = (player.getY() + offsets[0] - blinky.getY()) + player.getY();
            int targetCol = (player.getX() + offsets[1] - blinky.getX()) + player.getX();
            updateDirection(board, targetRow, targetCol);
        }
        move();
    }
}
