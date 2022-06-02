public class Clyde extends Ghost{

    public Clyde(int x, int y, Direction direction, int section, int numSections, boolean released, boolean isExiting){
        super(x, y, direction, section, numSections, released, isExiting);
    }

    @Override
    protected void setTargetPos(Player player, Blinky blinky) {
        if(Math.abs(player.getX() - getX()) + Math.abs(player.getY() - getY()) < 8){
            setTargetRow(0);
            setTargetCol(0);
        }else{
            int[] offsets = DirectionHelp.getOffsets(player.getDirection());
            int targetRow = (player.getY() + offsets[0] - blinky.getY()) + player.getY();
            int targetCol = (player.getX() + offsets[1] - blinky.getX()) + player.getX();
            setTargetRow(player.getY());
            setTargetCol(player.getX());
        }
    }

    @Override
    protected void scatter(){
        setTargetRow(Global.GHOST_SCATTER_LOCATIONS[3][0]);
        setTargetCol(Global.GHOST_SCATTER_LOCATIONS[3][1]);
    }
}
