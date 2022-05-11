import com.badlogic.gdx.graphics.Color;

public class Ghost {

    private int x;
    private int y;
    private Color color;

    public Ghost(int x, int y, Color color) {
        this.x = x;
        this.y = y;
        this.color = color;
    }

    public Color getColor() {
        return color;
    }

    protected Direction chooseDirection(int targetX, int targetY){
        return null;
    }
}
