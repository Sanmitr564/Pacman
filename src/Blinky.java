public class Blinky extends Ghost{

    public Blinky(int x, int y, Direction direction, int section, int numSections, boolean released, boolean isExiting){
        super(x, y, direction, section, numSections, released, isExiting);
    }

    @Override
    protected void setTargetPos(Player player, Blinky blinky) {
        setTargetCol(player.getX());
        setTargetRow(player.getY());
    }


}
