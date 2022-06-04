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



    public static final int[][] GHOST_SCATTER_LOCATIONS = new int[][] {//{ghost, row/col}
            //{row,col}
            //Blinky
            {31, 28},
            //Pinky
            {31,0},
            //Inky
            {0,28},
            //Clyde
            {0,0}
    };
}