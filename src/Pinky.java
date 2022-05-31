public class Pinky extends Ghost{

    public Pinky(int x, int y, Direction direction, int section, int numSections, boolean released, boolean isExiting){
        super(x, y, direction, section, numSections, released, isExiting);
    }

    @Override
    protected void setTargetPos(Player player, Blinky blinky) {
        int[] offsets = DirectionHelp.getOffsets(player.getDirection());
        setTargetRow(player.getY() + offsets[0] * 4);
        setTargetCol(player.getX() + offsets[1] * 4);
    }

}
