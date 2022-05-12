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

    //<editor-fold desc="standard pacman board layout">
    public static final Tile[][] DEFAULT_BOARD =
            {
                    {}
            };
    //</editor-fold>

    public static final int TILE_SECTIONS = 50;
    public static final float TILE_OFFSETS = (float) TILE_SIZE / TILE_SECTIONS;

}