public class Pinky extends Ghost{

    public Pinky(int x, int y, Direction direction, int section, int numSections, boolean released, boolean isExiting){
        super(x, y, direction, section, numSections, released, isExiting);
    }

    @Override
    protected void setTargetPos(Player player, Blinky blinky) {
        int[] offsets = player.getDirection().getOffsets();
        setTargetRow(player.getY() + offsets[0] * 4);
        setTargetCol(player.getX() + offsets[1] * 4);
    }

    @Override
    protected void scatter(){
        setTargetRow(Global.GHOST_SCATTER_LOCATIONS[1][0]);
        setTargetCol(Global.GHOST_SCATTER_LOCATIONS[1][1]);
    }
}
