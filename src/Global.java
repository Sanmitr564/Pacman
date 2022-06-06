import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

public class Global {
    public static final int WORLD_WIDTH = 1920;
    public static final int WORLD_HEIGHT = 1080;

    public static final int TILE_SIZE = 30;

    public static final int BOARD_ROWS = 31;
    public static final int BOARD_COLS = 28;

    public static final int BOARD_WIDTH = TILE_SIZE * BOARD_COLS;
    public static final int BOARD_HEIGHT = TILE_SIZE * BOARD_ROWS;

    public static final int FIELD_X = (WORLD_WIDTH - BOARD_WIDTH) / 2;
    public static final int FIELD_Y = (WORLD_HEIGHT - BOARD_HEIGHT) / 2;

    public static final int PLAYER_TILE_SECTIONS = 10;

    public static final int[][] GHOST_SCATTER_LOCATIONS = new int[][]{//{ghost, row/col}
            //{row,col}
            //Blinky
            {31, 28},
            //Pinky
            {31, 0},
            //Inky
            {0, 28},
            //Clyde
            {0, 0}
    };

    //<editor-fold desc="Player & Ghost Boards">
    public static final Tile[][] PLAYER_BOARD = new Tile[][]{
            {Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL},
            {Tile.WALL, Tile.SMALL_PELLET, Tile.SMALL_PELLET, Tile.SMALL_PELLET, Tile.SMALL_PELLET, Tile.SMALL_PELLET, Tile.SMALL_PELLET, Tile.SMALL_PELLET, Tile.SMALL_PELLET, Tile.SMALL_PELLET, Tile.SMALL_PELLET, Tile.SMALL_PELLET, Tile.SMALL_PELLET, Tile.SMALL_PELLET, Tile.SMALL_PELLET, Tile.SMALL_PELLET, Tile.SMALL_PELLET, Tile.SMALL_PELLET, Tile.SMALL_PELLET, Tile.SMALL_PELLET, Tile.SMALL_PELLET, Tile.SMALL_PELLET, Tile.SMALL_PELLET, Tile.SMALL_PELLET, Tile.SMALL_PELLET, Tile.SMALL_PELLET, Tile.SMALL_PELLET, Tile.WALL},
            {Tile.WALL, Tile.SMALL_PELLET, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.SMALL_PELLET, Tile.WALL, Tile.WALL, Tile.SMALL_PELLET, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.SMALL_PELLET, Tile.WALL},
            {Tile.WALL, Tile.SMALL_PELLET, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.SMALL_PELLET, Tile.WALL, Tile.WALL, Tile.SMALL_PELLET, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.SMALL_PELLET, Tile.WALL},
            {Tile.WALL, Tile.SMALL_PELLET, Tile.SMALL_PELLET, Tile.SMALL_PELLET, Tile.SMALL_PELLET, Tile.SMALL_PELLET, Tile.SMALL_PELLET, Tile.WALL, Tile.WALL, Tile.SMALL_PELLET, Tile.SMALL_PELLET, Tile.SMALL_PELLET, Tile.SMALL_PELLET, Tile.WALL, Tile.WALL, Tile.SMALL_PELLET, Tile.SMALL_PELLET, Tile.SMALL_PELLET, Tile.SMALL_PELLET, Tile.WALL, Tile.WALL, Tile.SMALL_PELLET, Tile.SMALL_PELLET, Tile.SMALL_PELLET, Tile.SMALL_PELLET, Tile.SMALL_PELLET, Tile.SMALL_PELLET, Tile.WALL},
            {Tile.WALL, Tile.WALL, Tile.WALL, Tile.SMALL_PELLET, Tile.WALL, Tile.WALL, Tile.SMALL_PELLET, Tile.WALL, Tile.WALL, Tile.SMALL_PELLET, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.SMALL_PELLET, Tile.WALL, Tile.WALL, Tile.SMALL_PELLET, Tile.WALL, Tile.WALL, Tile.SMALL_PELLET, Tile.WALL, Tile.WALL, Tile.WALL},
            {Tile.WALL, Tile.WALL, Tile.WALL, Tile.SMALL_PELLET, Tile.WALL, Tile.WALL, Tile.SMALL_PELLET, Tile.WALL, Tile.WALL, Tile.SMALL_PELLET, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.SMALL_PELLET, Tile.WALL, Tile.WALL, Tile.SMALL_PELLET, Tile.WALL, Tile.WALL, Tile.SMALL_PELLET, Tile.WALL, Tile.WALL, Tile.WALL},
            {Tile.WALL, Tile.BIG_PELLET, Tile.SMALL_PELLET, Tile.SMALL_PELLET, Tile.WALL, Tile.WALL, Tile.SMALL_PELLET, Tile.SMALL_PELLET, Tile.SMALL_PELLET, Tile.SMALL_PELLET, Tile.SMALL_PELLET, Tile.SMALL_PELLET, Tile.SMALL_PELLET, Tile.STRAIGHT, Tile.STRAIGHT, Tile.SMALL_PELLET, Tile.SMALL_PELLET, Tile.SMALL_PELLET, Tile.SMALL_PELLET, Tile.SMALL_PELLET, Tile.SMALL_PELLET, Tile.SMALL_PELLET, Tile.WALL, Tile.WALL, Tile.SMALL_PELLET, Tile.SMALL_PELLET, Tile.BIG_PELLET, Tile.WALL},
            {Tile.WALL, Tile.SMALL_PELLET, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.SMALL_PELLET, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.SMALL_PELLET, Tile.WALL, Tile.WALL, Tile.SMALL_PELLET, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.SMALL_PELLET, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.SMALL_PELLET, Tile.WALL},
            {Tile.WALL, Tile.SMALL_PELLET, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.SMALL_PELLET, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.SMALL_PELLET, Tile.WALL, Tile.WALL, Tile.SMALL_PELLET, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.SMALL_PELLET, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.SMALL_PELLET, Tile.WALL},
            {Tile.WALL, Tile.SMALL_PELLET, Tile.SMALL_PELLET, Tile.SMALL_PELLET, Tile.SMALL_PELLET, Tile.SMALL_PELLET, Tile.SMALL_PELLET, Tile.SMALL_PELLET, Tile.SMALL_PELLET, Tile.SMALL_PELLET, Tile.SMALL_PELLET, Tile.SMALL_PELLET, Tile.SMALL_PELLET, Tile.WALL, Tile.WALL, Tile.SMALL_PELLET, Tile.SMALL_PELLET, Tile.SMALL_PELLET, Tile.SMALL_PELLET, Tile.SMALL_PELLET, Tile.SMALL_PELLET, Tile.SMALL_PELLET, Tile.SMALL_PELLET, Tile.SMALL_PELLET, Tile.SMALL_PELLET, Tile.SMALL_PELLET, Tile.SMALL_PELLET, Tile.WALL},
            {Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.SMALL_PELLET, Tile.WALL, Tile.WALL, Tile.STRAIGHT, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.STRAIGHT, Tile.WALL, Tile.WALL, Tile.SMALL_PELLET, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL},
            {Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.SMALL_PELLET, Tile.WALL, Tile.WALL, Tile.STRAIGHT, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.STRAIGHT, Tile.WALL, Tile.WALL, Tile.SMALL_PELLET, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL},
            {Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.SMALL_PELLET, Tile.WALL, Tile.WALL, Tile.STRAIGHT, Tile.STRAIGHT, Tile.STRAIGHT, Tile.STRAIGHT, Tile.STRAIGHT, Tile.STRAIGHT, Tile.STRAIGHT, Tile.STRAIGHT, Tile.STRAIGHT, Tile.STRAIGHT, Tile.WALL, Tile.WALL, Tile.SMALL_PELLET, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL},
            {Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.SMALL_PELLET, Tile.WALL, Tile.WALL, Tile.STRAIGHT, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.STRAIGHT, Tile.WALL, Tile.WALL, Tile.SMALL_PELLET, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL},
            {Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.SMALL_PELLET, Tile.WALL, Tile.WALL, Tile.STRAIGHT, Tile.WALL, Tile.GHOST_AREA, Tile.GHOST_AREA, Tile.GHOST_AREA, Tile.GHOST_AREA, Tile.GHOST_AREA, Tile.GHOST_AREA, Tile.WALL, Tile.STRAIGHT, Tile.WALL, Tile.WALL, Tile.SMALL_PELLET, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL},
            {Tile.TELEPORT, Tile.STRAIGHT, Tile.STRAIGHT, Tile.STRAIGHT, Tile.STRAIGHT, Tile.STRAIGHT, Tile.SMALL_PELLET, Tile.STRAIGHT, Tile.STRAIGHT, Tile.STRAIGHT, Tile.WALL, Tile.GHOST_AREA, Tile.GHOST_AREA, Tile.GHOST_AREA, Tile.GHOST_AREA, Tile.GHOST_AREA, Tile.GHOST_AREA, Tile.WALL, Tile.STRAIGHT, Tile.STRAIGHT, Tile.STRAIGHT, Tile.SMALL_PELLET, Tile.STRAIGHT, Tile.STRAIGHT, Tile.STRAIGHT, Tile.STRAIGHT, Tile.STRAIGHT, Tile.TELEPORT},
            {Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.SMALL_PELLET, Tile.WALL, Tile.WALL, Tile.STRAIGHT, Tile.WALL, Tile.GHOST_AREA, Tile.GHOST_AREA, Tile.GHOST_AREA, Tile.GHOST_AREA, Tile.GHOST_AREA, Tile.GHOST_AREA, Tile.WALL, Tile.STRAIGHT, Tile.WALL, Tile.WALL, Tile.SMALL_PELLET, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL},
            {Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.SMALL_PELLET, Tile.WALL, Tile.WALL, Tile.STRAIGHT, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.STRAIGHT, Tile.WALL, Tile.WALL, Tile.SMALL_PELLET, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL},
            {Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.SMALL_PELLET, Tile.WALL, Tile.WALL, Tile.STRAIGHT, Tile.STRAIGHT, Tile.STRAIGHT, Tile.STRAIGHT, Tile.STRAIGHT, Tile.STRAIGHT, Tile.STRAIGHT, Tile.STRAIGHT, Tile.STRAIGHT, Tile.STRAIGHT, Tile.WALL, Tile.WALL, Tile.SMALL_PELLET, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL},
            {Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.SMALL_PELLET, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.STRAIGHT, Tile.WALL, Tile.WALL, Tile.STRAIGHT, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.SMALL_PELLET, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL},
            {Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.SMALL_PELLET, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.STRAIGHT, Tile.WALL, Tile.WALL, Tile.STRAIGHT, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.SMALL_PELLET, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL},
            {Tile.WALL, Tile.SMALL_PELLET, Tile.SMALL_PELLET, Tile.SMALL_PELLET, Tile.SMALL_PELLET, Tile.SMALL_PELLET, Tile.SMALL_PELLET, Tile.WALL, Tile.WALL, Tile.SMALL_PELLET, Tile.SMALL_PELLET, Tile.SMALL_PELLET, Tile.SMALL_PELLET, Tile.WALL, Tile.WALL, Tile.SMALL_PELLET, Tile.SMALL_PELLET, Tile.SMALL_PELLET, Tile.SMALL_PELLET, Tile.WALL, Tile.WALL, Tile.SMALL_PELLET, Tile.SMALL_PELLET, Tile.SMALL_PELLET, Tile.SMALL_PELLET, Tile.SMALL_PELLET, Tile.SMALL_PELLET, Tile.WALL},
            {Tile.WALL, Tile.SMALL_PELLET, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.SMALL_PELLET, Tile.WALL, Tile.WALL, Tile.SMALL_PELLET, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.SMALL_PELLET, Tile.WALL, Tile.WALL, Tile.SMALL_PELLET, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.SMALL_PELLET, Tile.WALL},
            {Tile.WALL, Tile.SMALL_PELLET, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.SMALL_PELLET, Tile.WALL, Tile.WALL, Tile.SMALL_PELLET, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.SMALL_PELLET, Tile.WALL, Tile.WALL, Tile.SMALL_PELLET, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.SMALL_PELLET, Tile.WALL},
            {Tile.WALL, Tile.SMALL_PELLET, Tile.SMALL_PELLET, Tile.SMALL_PELLET, Tile.SMALL_PELLET, Tile.SMALL_PELLET, Tile.SMALL_PELLET, Tile.SMALL_PELLET, Tile.SMALL_PELLET, Tile.SMALL_PELLET, Tile.SMALL_PELLET, Tile.SMALL_PELLET, Tile.SMALL_PELLET, Tile.SMALL_PELLET, Tile.SMALL_PELLET, Tile.SMALL_PELLET, Tile.SMALL_PELLET, Tile.SMALL_PELLET, Tile.SMALL_PELLET, Tile.SMALL_PELLET, Tile.SMALL_PELLET, Tile.SMALL_PELLET, Tile.SMALL_PELLET, Tile.SMALL_PELLET, Tile.SMALL_PELLET, Tile.SMALL_PELLET, Tile.SMALL_PELLET, Tile.WALL},
            {Tile.WALL, Tile.SMALL_PELLET, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.SMALL_PELLET, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.SMALL_PELLET, Tile.WALL, Tile.WALL, Tile.SMALL_PELLET, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.SMALL_PELLET, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.SMALL_PELLET, Tile.WALL},
            {Tile.WALL, Tile.BIG_PELLET, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.SMALL_PELLET, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.SMALL_PELLET, Tile.WALL, Tile.WALL, Tile.SMALL_PELLET, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.SMALL_PELLET, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.BIG_PELLET, Tile.WALL},
            {Tile.WALL, Tile.SMALL_PELLET, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.SMALL_PELLET, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.SMALL_PELLET, Tile.WALL, Tile.WALL, Tile.SMALL_PELLET, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.SMALL_PELLET, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.SMALL_PELLET, Tile.WALL},
            {Tile.WALL, Tile.SMALL_PELLET, Tile.SMALL_PELLET, Tile.SMALL_PELLET, Tile.SMALL_PELLET, Tile.SMALL_PELLET, Tile.SMALL_PELLET, Tile.SMALL_PELLET, Tile.SMALL_PELLET, Tile.SMALL_PELLET, Tile.SMALL_PELLET, Tile.SMALL_PELLET, Tile.SMALL_PELLET, Tile.WALL, Tile.WALL, Tile.SMALL_PELLET, Tile.SMALL_PELLET, Tile.SMALL_PELLET, Tile.SMALL_PELLET, Tile.SMALL_PELLET, Tile.SMALL_PELLET, Tile.SMALL_PELLET, Tile.SMALL_PELLET, Tile.SMALL_PELLET, Tile.SMALL_PELLET, Tile.SMALL_PELLET, Tile.SMALL_PELLET, Tile.WALL},
            {Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL},
    };

    public static final Tile[][] GHOST_MAP = new Tile[][]{
            {Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL},
            {Tile.WALL, Tile.STRAIGHT, Tile.STRAIGHT, Tile.STRAIGHT, Tile.STRAIGHT, Tile.STRAIGHT, Tile.STRAIGHT, Tile.STRAIGHT, Tile.STRAIGHT, Tile.STRAIGHT, Tile.STRAIGHT, Tile.STRAIGHT, Tile.JUNCTION, Tile.STRAIGHT, Tile.STRAIGHT, Tile.JUNCTION, Tile.STRAIGHT, Tile.STRAIGHT, Tile.STRAIGHT, Tile.STRAIGHT, Tile.STRAIGHT, Tile.STRAIGHT, Tile.STRAIGHT, Tile.STRAIGHT, Tile.STRAIGHT, Tile.STRAIGHT, Tile.STRAIGHT, Tile.WALL},
            {Tile.WALL, Tile.STRAIGHT, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.STRAIGHT, Tile.WALL, Tile.WALL, Tile.STRAIGHT, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.STRAIGHT, Tile.WALL},
            {Tile.WALL, Tile.STRAIGHT, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.STRAIGHT, Tile.WALL, Tile.WALL, Tile.STRAIGHT, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.STRAIGHT, Tile.WALL},
            {Tile.WALL, Tile.STRAIGHT, Tile.STRAIGHT, Tile.JUNCTION, Tile.STRAIGHT, Tile.STRAIGHT, Tile.STRAIGHT, Tile.WALL, Tile.WALL, Tile.STRAIGHT, Tile.STRAIGHT, Tile.STRAIGHT, Tile.STRAIGHT, Tile.WALL, Tile.WALL, Tile.STRAIGHT, Tile.STRAIGHT, Tile.STRAIGHT, Tile.STRAIGHT, Tile.WALL, Tile.WALL, Tile.STRAIGHT, Tile.STRAIGHT, Tile.STRAIGHT, Tile.JUNCTION, Tile.STRAIGHT, Tile.STRAIGHT, Tile.WALL},
            {Tile.WALL, Tile.WALL, Tile.WALL, Tile.STRAIGHT, Tile.WALL, Tile.WALL, Tile.STRAIGHT, Tile.WALL, Tile.WALL, Tile.STRAIGHT, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.STRAIGHT, Tile.WALL, Tile.WALL, Tile.STRAIGHT, Tile.WALL, Tile.WALL, Tile.STRAIGHT, Tile.WALL, Tile.WALL, Tile.WALL},
            {Tile.WALL, Tile.WALL, Tile.WALL, Tile.STRAIGHT, Tile.WALL, Tile.WALL, Tile.STRAIGHT, Tile.WALL, Tile.WALL, Tile.STRAIGHT, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.STRAIGHT, Tile.WALL, Tile.WALL, Tile.STRAIGHT, Tile.WALL, Tile.WALL, Tile.STRAIGHT, Tile.WALL, Tile.WALL, Tile.WALL},
            {Tile.WALL, Tile.STRAIGHT, Tile.STRAIGHT, Tile.STRAIGHT, Tile.WALL, Tile.WALL, Tile.JUNCTION, Tile.STRAIGHT, Tile.STRAIGHT, Tile.JUNCTION, Tile.STRAIGHT, Tile.STRAIGHT, Tile.SPECIAL_JUNCTION, Tile.STRAIGHT, Tile.STRAIGHT, Tile.SPECIAL_JUNCTION, Tile.STRAIGHT, Tile.STRAIGHT, Tile.JUNCTION, Tile.STRAIGHT, Tile.STRAIGHT, Tile.JUNCTION, Tile.WALL, Tile.WALL, Tile.STRAIGHT, Tile.STRAIGHT, Tile.STRAIGHT, Tile.WALL},
            {Tile.WALL, Tile.STRAIGHT, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.STRAIGHT, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.STRAIGHT, Tile.WALL, Tile.WALL, Tile.STRAIGHT, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.STRAIGHT, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.STRAIGHT, Tile.WALL},
            {Tile.WALL, Tile.STRAIGHT, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.STRAIGHT, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.STRAIGHT, Tile.WALL, Tile.WALL, Tile.STRAIGHT, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.STRAIGHT, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.STRAIGHT, Tile.WALL},
            {Tile.WALL, Tile.STRAIGHT, Tile.STRAIGHT, Tile.STRAIGHT, Tile.STRAIGHT, Tile.STRAIGHT, Tile.JUNCTION, Tile.STRAIGHT, Tile.STRAIGHT, Tile.JUNCTION, Tile.STRAIGHT, Tile.STRAIGHT, Tile.STRAIGHT, Tile.WALL, Tile.WALL, Tile.STRAIGHT, Tile.STRAIGHT, Tile.STRAIGHT, Tile.JUNCTION, Tile.STRAIGHT, Tile.STRAIGHT, Tile.JUNCTION, Tile.STRAIGHT, Tile.STRAIGHT, Tile.STRAIGHT, Tile.STRAIGHT, Tile.STRAIGHT, Tile.WALL},
            {Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.STRAIGHT, Tile.WALL, Tile.WALL, Tile.STRAIGHT, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.STRAIGHT, Tile.WALL, Tile.WALL, Tile.STRAIGHT, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL},
            {Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.STRAIGHT, Tile.WALL, Tile.WALL, Tile.STRAIGHT, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.STRAIGHT, Tile.WALL, Tile.WALL, Tile.STRAIGHT, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL},
            {Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.STRAIGHT, Tile.WALL, Tile.WALL, Tile.JUNCTION, Tile.STRAIGHT, Tile.STRAIGHT, Tile.STRAIGHT, Tile.STRAIGHT, Tile.STRAIGHT, Tile.STRAIGHT, Tile.STRAIGHT, Tile.STRAIGHT, Tile.JUNCTION, Tile.WALL, Tile.WALL, Tile.STRAIGHT, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL},
            {Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.STRAIGHT, Tile.WALL, Tile.WALL, Tile.STRAIGHT, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.STRAIGHT, Tile.WALL, Tile.WALL, Tile.STRAIGHT, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL},
            {Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.STRAIGHT, Tile.WALL, Tile.WALL, Tile.STRAIGHT, Tile.WALL, Tile.GHOST_AREA, Tile.GHOST_AREA, Tile.GHOST_AREA, Tile.GHOST_AREA, Tile.GHOST_AREA, Tile.GHOST_AREA, Tile.WALL, Tile.STRAIGHT, Tile.WALL, Tile.WALL, Tile.STRAIGHT, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL},
            {Tile.TELEPORT, Tile.STRAIGHT, Tile.STRAIGHT, Tile.STRAIGHT, Tile.STRAIGHT, Tile.STRAIGHT, Tile.JUNCTION, Tile.STRAIGHT, Tile.STRAIGHT, Tile.JUNCTION, Tile.WALL, Tile.GHOST_HOUSE_JUNCTION, Tile.GHOST_HOUSE_JUNCTION, Tile.GHOST_HOUSE_JUNCTION, Tile.GHOST_HOUSE_JUNCTION, Tile.GHOST_HOUSE_JUNCTION, Tile.GHOST_HOUSE_JUNCTION, Tile.WALL, Tile.JUNCTION, Tile.STRAIGHT, Tile.STRAIGHT, Tile.JUNCTION, Tile.STRAIGHT, Tile.STRAIGHT, Tile.STRAIGHT, Tile.STRAIGHT, Tile.STRAIGHT, Tile.TELEPORT},
            {Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.STRAIGHT, Tile.WALL, Tile.WALL, Tile.STRAIGHT, Tile.WALL, Tile.GHOST_AREA, Tile.GHOST_AREA, Tile.GHOST_AREA, Tile.GHOST_AREA, Tile.GHOST_AREA, Tile.GHOST_AREA, Tile.WALL, Tile.STRAIGHT, Tile.WALL, Tile.WALL, Tile.STRAIGHT, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL},
            {Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.STRAIGHT, Tile.WALL, Tile.WALL, Tile.STRAIGHT, Tile.WALL, Tile.WALL, Tile.GHOST_DOOR, Tile.GHOST_DOOR, Tile.GHOST_DOOR, Tile.GHOST_DOOR, Tile.WALL, Tile.WALL, Tile.STRAIGHT, Tile.WALL, Tile.WALL, Tile.STRAIGHT, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL},
            {Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.STRAIGHT, Tile.WALL, Tile.WALL, Tile.STRAIGHT, Tile.STRAIGHT, Tile.STRAIGHT, Tile.SPECIAL_JUNCTION, Tile.STRAIGHT, Tile.STRAIGHT, Tile.SPECIAL_JUNCTION, Tile.STRAIGHT, Tile.STRAIGHT, Tile.STRAIGHT, Tile.WALL, Tile.WALL, Tile.STRAIGHT, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL},
            {Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.STRAIGHT, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.STRAIGHT, Tile.WALL, Tile.WALL, Tile.STRAIGHT, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.STRAIGHT, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL},
            {Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.STRAIGHT, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.STRAIGHT, Tile.WALL, Tile.WALL, Tile.STRAIGHT, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.STRAIGHT, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL},
            {Tile.WALL, Tile.STRAIGHT, Tile.STRAIGHT, Tile.STRAIGHT, Tile.STRAIGHT, Tile.STRAIGHT, Tile.JUNCTION, Tile.WALL, Tile.WALL, Tile.STRAIGHT, Tile.STRAIGHT, Tile.STRAIGHT, Tile.STRAIGHT, Tile.WALL, Tile.WALL, Tile.STRAIGHT, Tile.STRAIGHT, Tile.STRAIGHT, Tile.STRAIGHT, Tile.WALL, Tile.WALL, Tile.JUNCTION, Tile.STRAIGHT, Tile.STRAIGHT, Tile.STRAIGHT, Tile.STRAIGHT, Tile.STRAIGHT, Tile.WALL},
            {Tile.WALL, Tile.STRAIGHT, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.STRAIGHT, Tile.WALL, Tile.WALL, Tile.STRAIGHT, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.STRAIGHT, Tile.WALL, Tile.WALL, Tile.STRAIGHT, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.STRAIGHT, Tile.WALL},
            {Tile.WALL, Tile.STRAIGHT, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.STRAIGHT, Tile.WALL, Tile.WALL, Tile.STRAIGHT, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.STRAIGHT, Tile.WALL, Tile.WALL, Tile.STRAIGHT, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.STRAIGHT, Tile.WALL},
            {Tile.WALL, Tile.JUNCTION, Tile.STRAIGHT, Tile.STRAIGHT, Tile.STRAIGHT, Tile.STRAIGHT, Tile.JUNCTION, Tile.STRAIGHT, Tile.STRAIGHT, Tile.JUNCTION, Tile.STRAIGHT, Tile.STRAIGHT, Tile.JUNCTION, Tile.STRAIGHT, Tile.STRAIGHT, Tile.JUNCTION, Tile.STRAIGHT, Tile.STRAIGHT, Tile.JUNCTION, Tile.STRAIGHT, Tile.STRAIGHT, Tile.JUNCTION, Tile.STRAIGHT, Tile.STRAIGHT, Tile.STRAIGHT, Tile.STRAIGHT, Tile.JUNCTION, Tile.WALL},
            {Tile.WALL, Tile.STRAIGHT, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.STRAIGHT, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.STRAIGHT, Tile.WALL, Tile.WALL, Tile.STRAIGHT, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.STRAIGHT, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.STRAIGHT, Tile.WALL},
            {Tile.WALL, Tile.STRAIGHT, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.STRAIGHT, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.STRAIGHT, Tile.WALL, Tile.WALL, Tile.STRAIGHT, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.STRAIGHT, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.STRAIGHT, Tile.WALL},
            {Tile.WALL, Tile.STRAIGHT, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.STRAIGHT, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.STRAIGHT, Tile.WALL, Tile.WALL, Tile.STRAIGHT, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.STRAIGHT, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.STRAIGHT, Tile.WALL},
            {Tile.WALL, Tile.STRAIGHT, Tile.STRAIGHT, Tile.STRAIGHT, Tile.STRAIGHT, Tile.STRAIGHT, Tile.JUNCTION, Tile.STRAIGHT, Tile.STRAIGHT, Tile.STRAIGHT, Tile.STRAIGHT, Tile.STRAIGHT, Tile.STRAIGHT, Tile.WALL, Tile.WALL, Tile.STRAIGHT, Tile.STRAIGHT, Tile.STRAIGHT, Tile.STRAIGHT, Tile.STRAIGHT, Tile.STRAIGHT, Tile.JUNCTION, Tile.STRAIGHT, Tile.STRAIGHT, Tile.STRAIGHT, Tile.STRAIGHT, Tile.STRAIGHT, Tile.WALL},
            {Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL, Tile.WALL},
    };

    //</editor-fold>

    public static final TextureRegion PACMAN_ICON_REGION = new TextureRegion(new Texture("Pacman.svg.png"));
    public static final TextureRegion TETRIS_ICON_REGION = new TextureRegion(new Texture("T_Tetromino.png"));

    public static final float PACMAN_ICON_SCALE = 7f;
    public static final float TETRIS_ICON_SCALE = 1.5f;

    public static final Rectangle PACMAN_ICON_RECTANGLE = new Rectangle(
            (Global.WORLD_WIDTH - Global.PACMAN_ICON_REGION.getRegionWidth() / Global.PACMAN_ICON_SCALE) / 2f,
            (Global.WORLD_HEIGHT - Global.PACMAN_ICON_REGION.getRegionHeight() / Global.PACMAN_ICON_SCALE) / 2f + 160,
            Global.PACMAN_ICON_REGION.getRegionWidth() / Global.PACMAN_ICON_SCALE,
            Global.PACMAN_ICON_REGION.getRegionHeight() / Global.PACMAN_ICON_SCALE
    );

    public static final Rectangle TETRIS_ICON_RECTANGLE = new Rectangle(
            (Global.WORLD_WIDTH - Global.TETRIS_ICON_REGION.getRegionWidth() / Global.TETRIS_ICON_SCALE) / 2f,
            (Global.WORLD_HEIGHT - Global.TETRIS_ICON_REGION.getRegionHeight() / Global.TETRIS_ICON_SCALE) / 2f - 145,
            Global.TETRIS_ICON_REGION.getRegionWidth() / Global.TETRIS_ICON_SCALE,
            Global.TETRIS_ICON_REGION.getRegionHeight() / Global.TETRIS_ICON_SCALE
    );
}