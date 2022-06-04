public class Blinky extends Ghost{

    public Blinky(int x, int y, Direction direction, int section, int numSections, boolean released, boolean isExiting){
        super(x, y, direction, section, numSections, released, isExiting);
    }

    @Override
    protected void setTargetPos(Player player, Blinky blinky) {
        setTargetCol(player.getX());
        setTargetRow(player.getY());
    }

    @Override
    protected void scatter(){
        setTargetRow(Global.GHOST_SCATTER_LOCATIONS[0][0]);
        setTargetCol(Global.GHOST_SCATTER_LOCATIONS[0][1]);
    }

    @Override
    protected void eatReset(){
        setX(14);
        setY(16);
        setSection(0);
        setDirection(Direction.UP);
        setExiting(true);
    }
}
