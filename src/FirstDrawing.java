import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.*;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.util.ArrayList;
import java.util.Arrays;


//NOTE: Always reset the JVM before compiling (it is the small loop arrow in the
//bottom right corner of the project window)!!

public class FirstDrawing extends ApplicationAdapter implements InputProcessor {
    private OrthographicCamera camera; //the camera to our world
    private Viewport viewport; //maintains the ratios of your world
    private ShapeRenderer renderer; //used to draw textures and fonts
    private BitmapFont font; //used to draw fonts (text)
    private SpriteBatch batch; //also needed to draw fonts (text)

    //<editor-fold desc="Tetris variables">
    public static Color[][] board;
    private Tetromino piece;

    private ArrayList<Pieces> tetrominos;
    private static final ArrayList<Pieces> tetrominoList = new ArrayList<>(Arrays.asList(
            Pieces.JPiece,
            Pieces.LPiece,
            Pieces.SPiece,
            Pieces.ZPiece,
            Pieces.TPiece,
            Pieces.IPiece,
            Pieces.OPiece
    ));

    private Pieces hold;

    private int stickTimer;
    private int dropTimer;

    private int rightTimer;
    private int leftTimer;
    private int downTimer;

    private float fieldYOffset;

    private boolean canHold;
    //</editor-fold>
    private PacMan pacman;

    private GameState gameState;

    @Override//called once when we start the game
    public void create() {

        camera = new OrthographicCamera();
        viewport = new FitViewport(Global.WORLD_WIDTH, Global.WORLD_HEIGHT, camera);
        renderer = new ShapeRenderer();
        font = new BitmapFont();
        batch = new SpriteBatch();//if you want to use images instead of using ShapeRenderer

        pacman = new PacMan();
        gameState = GameState.MENU;

        Gdx.input.setInputProcessor(this);
    }

    @Override//called 60 times a second
    public void render() {
        preRender();
        if (gameState == GameState.MENU) {
            renderMenu();
        } else if (gameState == GameState.PACMAN) {
            renderBoard();
            renderPlayer();
            renderGhost();
            if(!pacman.isGameOver()){
            pacman.update();
            }else{
                renderPacmanGameOver();
            }

        }else if(gameState == GameState.TETRIS){
            fieldYOffset = fieldYOffset < 1 ? 0 : fieldYOffset * 17 / 20;
            preRender();
            updatePiece();
            control();
            checkStick();
            drawGrid();
            drawShadow();
            drawNextAndHold();
        }
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }

    @Override
    public void dispose() {
        renderer.dispose();
        batch.dispose();
    }

    public void preRender() {
        viewport.apply();

        Gdx.gl.glClearColor(0, 0, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        float delta = Gdx.graphics.getDeltaTime();//1/60

        //draw everything on the screen
        renderer.setProjectionMatrix(viewport.getCamera().combined);
    }

    //<editor-fold desc="Pacman methods">

    private void renderBoard() {
        renderer.begin(ShapeRenderer.ShapeType.Filled);
        for (int row = 0; row < Global.BOARD_ROWS; row++) {
            for (int col = 0; col < Global.BOARD_COLS; col++) {
                switch (pacman.getPlayerBoard()[row][col]) {
                    case WALL -> renderer.setColor(Color.BLUE);
                    case STRAIGHT, GHOST_AREA, TELEPORT -> renderer.setColor(Color.BLACK);
                    case SMALL_PELLET -> renderer.setColor(Color.OLIVE);
                    case BIG_PELLET -> renderer.setColor(Color.TEAL);
                    default -> renderer.setColor(Color.GREEN);
                }
                renderer.rect(Global.FIELD_X + col * (Global.TILE_SIZE), Global.FIELD_Y + row * (Global.TILE_SIZE), Global.TILE_SIZE, Global.TILE_SIZE);
            }
        }
        renderer.end();
        batch.begin();
        font.draw(batch, "Score: " + pacman.getPlayer().getScore(), 625, 1030);
        font.draw(batch, "Lives remaining: " + pacman.getPlayer().getNumLives(), 900, 1030);
        batch.end();
    }

    private void renderPlayer() {
        renderer.begin(ShapeRenderer.ShapeType.Filled);
        renderer.setColor(Color.YELLOW);
        if (pacman.getPlayer().isEnergized()) {
            renderer.setColor(Color.GREEN);
        }
        //Gdx.gl.glLineWidth(3f);
        float[] tileOffsets = pacman.getPlayer().getTileOffsets();
        renderer.circle(
                pacman.getPlayer().getX() * Global.TILE_SIZE + Global.FIELD_X + tileOffsets[0],
                pacman.getPlayer().getY() * Global.TILE_SIZE + Global.FIELD_Y + tileOffsets[1],
                Global.TILE_SIZE / 2f
        );
        renderer.end();
    }

    private void renderGhost() {
        renderer.begin(ShapeRenderer.ShapeType.Filled);
        renderer.setColor(Color.RED);
        if(pacman.getPlayer().isEnergized()){
            renderer.setColor(Color.BLUE);
        }
        //Gdx.gl.glLineWidth(3f);
        float[] tileOffsets = pacman.getBlinky().getTileOffsets();
        renderer.circle(
                pacman.getBlinky().getX() * Global.TILE_SIZE + Global.FIELD_X + tileOffsets[0],
                pacman.getBlinky().getY() * Global.TILE_SIZE + Global.FIELD_Y + tileOffsets[1],
                Global.TILE_SIZE / 2f
        );
        renderer.setColor(Color.CYAN);
        if(pacman.getPlayer().isEnergized()){
            renderer.setColor(Color.BLUE);
        }
        tileOffsets = pacman.getInky().getTileOffsets();
        renderer.circle(
                pacman.getInky().getX() * Global.TILE_SIZE + Global.FIELD_X + tileOffsets[0],
                pacman.getInky().getY() * Global.TILE_SIZE + Global.FIELD_Y + tileOffsets[1],
                Global.TILE_SIZE / 2f
        );
        renderer.setColor(Color.PINK);
        if(pacman.getPlayer().isEnergized()){
            renderer.setColor(Color.BLUE);
        }
        tileOffsets = pacman.getPinky().getTileOffsets();
        renderer.circle(
                pacman.getPinky().getX() * Global.TILE_SIZE + Global.FIELD_X + tileOffsets[0],
                pacman.getPinky().getY() * Global.TILE_SIZE + Global.FIELD_Y + tileOffsets[1],
                Global.TILE_SIZE / 2f
        );
        renderer.setColor(Color.ORANGE);
        if(pacman.getPlayer().isEnergized()){
            renderer.setColor(Color.BLUE);
        }
        tileOffsets = pacman.getClyde().getTileOffsets();
        renderer.circle(
                pacman.getClyde().getX() * Global.TILE_SIZE + Global.FIELD_X + tileOffsets[0],
                pacman.getClyde().getY() * Global.TILE_SIZE + Global.FIELD_Y + tileOffsets[1],
                Global.TILE_SIZE / 2f
        );
        renderer.end();
    }

    private void renderPacmanGameOver(){
        batch.begin();
        batch.setColor(0,0,0,.5f);
        batch.draw(Global.WHITE_PIXEL, 0, 0, Global.WORLD_WIDTH, Global.WORLD_HEIGHT);
        batch.setColor(Color.WHITE);
        GlyphLayout gameOver = new GlyphLayout(font, "GAME OVER");
        GlyphLayout scoreIndicator = new GlyphLayout(font, "Final Score:");
        GlyphLayout score = new GlyphLayout(font, "" + pacman.getPlayer().getScore());
        font.draw(batch, gameOver, (Global.WORLD_WIDTH - gameOver.width)/2f, (Global.WORLD_HEIGHT - gameOver.height)/2f+20);
        font.draw(batch, scoreIndicator, (Global.WORLD_WIDTH - scoreIndicator.width)/2f, (Global.WORLD_HEIGHT - scoreIndicator.height)/2f);
        font.draw(batch, score, (Global.WORLD_WIDTH - score.width)/2f, (Global.WORLD_HEIGHT - score.height)/2f-15);
        batch.end();
    }



    //</editor-fold>

    //<editor-fold desc="Universal methods">
    private void renderMenu() {
        renderPacmanIcon();
        renderTetrisIcon();
    }

    private void renderPacmanIcon() {
        batch.begin();
        int mouseX = Gdx.input.getX();
        int mouseY = Gdx.input.getY();
        Vector2 mousePos = new Vector2(mouseX, mouseY);
        mousePos = viewport.unproject(mousePos);
        if (Global.PACMAN_ICON_RECTANGLE.contains(mousePos)) {
            batch.setColor(Color.WHITE);
        } else {
            batch.setColor(Color.GRAY);
        }
        batch.draw(Global.PACMAN_ICON_REGION,
                (Global.WORLD_WIDTH - Global.PACMAN_ICON_REGION.getRegionWidth()) / 2f,
                (Global.WORLD_HEIGHT - Global.PACMAN_ICON_REGION.getRegionHeight()) / 2f + 150,
                Global.PACMAN_ICON_REGION.getRegionWidth() / 2f,
                Global.PACMAN_ICON_REGION.getRegionHeight() / 2f,
                Global.PACMAN_ICON_REGION.getRegionWidth(),
                Global.PACMAN_ICON_REGION.getRegionHeight(),
                1 / Global.PACMAN_ICON_SCALE,
                1 / Global.PACMAN_ICON_SCALE,
                0
        );
        batch.end();
    }

    private void renderTetrisIcon() {
        batch.begin();
        int mouseX = Gdx.input.getX();
        int mouseY = Gdx.input.getY();
        Vector2 mousePos = new Vector2(mouseX, mouseY);
        mousePos = viewport.unproject(mousePos);
        if (Global.TETRIS_ICON_RECTANGLE.contains(mousePos)) {
            batch.setColor(Color.WHITE);
        } else {
            batch.setColor(Color.GRAY);
        }
        batch.draw(Global.TETRIS_ICON_REGION,
                (Global.WORLD_WIDTH - Global.TETRIS_ICON_REGION.getRegionWidth()) / 2f,
                (Global.WORLD_HEIGHT - Global.TETRIS_ICON_REGION.getRegionHeight()) / 2f - 150,
                Global.TETRIS_ICON_REGION.getRegionWidth() / 2f,
                Global.TETRIS_ICON_REGION.getRegionHeight() / 2f,
                Global.TETRIS_ICON_REGION.getRegionWidth(),
                Global.TETRIS_ICON_REGION.getRegionHeight(),
                1 / Global.TETRIS_ICON_SCALE,
                1 / Global.TETRIS_ICON_SCALE,
                0
        );

        batch.end();
    }

    @Override
    public boolean keyDown(int i) {
        if (gameState == GameState.MENU) {

        } else if (gameState == GameState.PACMAN) {

            switch (i) {
                case Input.Keys.UP -> pacman.getPlayer().setQueuedDirection(Direction.UP);
                case Input.Keys.LEFT -> pacman.getPlayer().setQueuedDirection(Direction.LEFT);
                case Input.Keys.RIGHT -> pacman.getPlayer().setQueuedDirection(Direction.RIGHT);
                case Input.Keys.DOWN -> pacman.getPlayer().setQueuedDirection(Direction.DOWN);
                case Input.Keys.ESCAPE -> {
                    gameState = GameState.MENU;
                    pacman = new PacMan();
                    return false;
                }
            }
            pacman.start();
        } else if (gameState == GameState.TETRIS) {
            if(i == Input.Keys.ESCAPE){
                gameState = GameState.MENU;
                return false;
            }
        }
        return false;
    }

    @Override
    public boolean touchDown(int i, int i1, int i2, int i3) {
        Vector2 mousePos = new Vector2(i, i1);
        mousePos = viewport.unproject(mousePos);
        if(Global.PACMAN_ICON_RECTANGLE.contains(mousePos)){
            gameState = GameState.PACMAN;
        }else if(Global.TETRIS_ICON_RECTANGLE.contains(mousePos)){
            gameState = GameState.TETRIS;
            tetrisStart();
        }
        return false;
    }
    //</editor-fold>

    //<editor-fold desc="Tetris methods">
    private void control() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.Z)) {
            piece.rotate(Rotate.counterClockwise);
            stickTimer = 0;
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.X)) {
            piece.rotate(Rotate.clockwise);
            stickTimer = 0;
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.DOWN)) {
            piece.down();
            dropTimer = 0;
            downTimer = Final.FIRST_MOVE_DELAY;
        } else if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            if (downTimer % Final.MOVE_DELAY == 0 && downTimer >= 0) {
                piece.down();
                dropTimer = 0;
            }
            downTimer++;
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.UP)) {
            piece.up();
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.LEFT)) {
            piece.left();
            leftTimer = Final.FIRST_MOVE_DELAY;
        } else if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            if (leftTimer % Final.MOVE_DELAY == 0 && leftTimer >= 0) {
                piece.left();
            }
            leftTimer++;
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.RIGHT)) {
            piece.right();
            rightTimer = Final.FIRST_MOVE_DELAY;
        } else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            if (rightTimer % Final.MOVE_DELAY == 0 && rightTimer >= 0) {
                piece.right();
            }
            rightTimer++;
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            piece.drop();
            newPiece();
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.C)) {
            storePiece();
        }
    }

    private void drawNextAndHold() {
        for (int i = 0; i < Final.FUTURE_DEPTH; i++) {
            batch.begin();
            Pieces temp = tetrominos.get(i);
            Texture t = Final.TEXTURES[temp.ordinal()];
            batch.draw(
                    t,
                    Final.NEXT_CENTER - t.getWidth() * Final.TEXTURE_SCALE / 2f,
                    Final.NEXT_BEGIN_Y - (t.getHeight() * Final.TEXTURE_SCALE / 2f + (i + 1) * Final.NEXT_GAP) - fieldYOffset,
                    t.getWidth() * Final.TEXTURE_SCALE,
                    t.getHeight() * Final.TEXTURE_SCALE
            );
            batch.end();
        }

        if (hold == null) {
            return;
        }

        batch.begin();
        Texture t = Final.TEXTURES[hold.ordinal()];
        if(!canHold){
            batch.setColor(Color.GRAY);
        }
        batch.draw(
                t,
                Final.HOLD_CENTER_X - t.getWidth() * Final.TEXTURE_SCALE / 2f,
                Final.HOLD_CENTER_Y - t.getHeight() * Final.TEXTURE_SCALE / 2f - fieldYOffset,
                t.getWidth() * Final.TEXTURE_SCALE,
                t.getHeight() * Final.TEXTURE_SCALE
        );
        batch.setColor(Color.WHITE);
        batch.end();
    }

    private void drawGrid() {
        renderer.begin(ShapeRenderer.ShapeType.Filled);
        renderer.setColor(Color.WHITE);
        //Field outline
        renderer.rect(Final.FIELD_X - Final.OUTLINE_SIZE,
                Final.FIELD_Y - fieldYOffset - Final.OUTLINE_SIZE,
                Final.FIELD_WIDTH + 2 * Final.OUTLINE_SIZE,
                Final.FIELD_HEIGHT + Final.OUTLINE_SIZE
        );
        //Upcoming blocks outline
        renderer.rect(Final.FIELD_X + Final.FIELD_WIDTH + Final.OUTLINE_SIZE,
                Final.FIELD_Y + Final.NEXT_HEIGHT_OFFSET - (fieldYOffset + Final.OUTLINE_SIZE),
                Final.NEXT_WIDTH + Final.OUTLINE_SIZE,
                Final.FIELD_HEIGHT - Final.NEXT_HEIGHT_OFFSET + Final.OUTLINE_SIZE
        );
        //Held block outline
        renderer.rect(Final.FIELD_X - (Final.OUTLINE_SIZE * 2 + Final.NEXT_WIDTH),
                Final.FIELD_Y + Final.HOLD_HEIGHT_OFFSET - (fieldYOffset + Final.OUTLINE_SIZE),
                Final.NEXT_WIDTH + Final.OUTLINE_SIZE,
                Final.FIELD_HEIGHT - Final.HOLD_HEIGHT_OFFSET + Final.OUTLINE_SIZE
        );
        renderer.setColor(Color.BLACK);
        //Field surface
        renderer.rect(Final.FIELD_X,
                Final.FIELD_Y - fieldYOffset,
                Final.FIELD_WIDTH,
                Final.FIELD_HEIGHT
        );
        //Next block surface
        renderer.rect(Final.FIELD_X + Final.FIELD_WIDTH + Final.OUTLINE_SIZE,
                Final.FIELD_Y + Final.NEXT_HEIGHT_OFFSET - fieldYOffset,
                Final.NEXT_WIDTH,
                Final.FIELD_HEIGHT - Final.NEXT_HEIGHT_OFFSET - Final.SIDE_TOP_BORDER
        );
        //Held block surface
        renderer.rect(Final.FIELD_X - (Final.NEXT_WIDTH + Final.OUTLINE_SIZE),
                Final.FIELD_Y + Final.HOLD_HEIGHT_OFFSET - fieldYOffset,
                Final.NEXT_WIDTH,
                Final.FIELD_HEIGHT - Final.HOLD_HEIGHT_OFFSET - Final.SIDE_TOP_BORDER
        );
        renderer.end();
        Gdx.gl.glLineWidth(1 / 3f);
        for (int y = 0; y < Final.VISIBLE_ROWS; y++) {
            for (int x = 0; x < Final.COLS; x++) {
                if (board[y][x] == Color.WHITE) {
                    renderer.begin(ShapeRenderer.ShapeType.Line);
                    renderer.setColor(30 / 255f, 30 / 255f, 30 / 255f, 1);
                } else {
                    renderer.begin(ShapeRenderer.ShapeType.Filled);
                    renderer.setColor(board[y][x]);
                }
                renderer.rect(
                        Final.FIELD_X + Final.GAP + x * (Final.SQUARE_SIZE + Final.GAP * 2),
                        Final.FIELD_Y + Final.GAP + y * (Final.SQUARE_SIZE + Final.GAP * 2) - fieldYOffset,
                        Final.SQUARE_SIZE,
                        Final.SQUARE_SIZE
                );
                renderer.end();
            }
        }
        renderer.begin(ShapeRenderer.ShapeType.Filled);
        for (int y = Final.VISIBLE_ROWS; y < Final.ROWS; y++) {
            for (int x = 0; x < Final.COLS; x++) {
                if (board[y][x] != Color.WHITE) {
                    renderer.setColor(board[y][x]);
                    renderer.rect(
                            Final.FIELD_X + Final.GAP + x * (Final.SQUARE_SIZE + Final.GAP * 2),
                            Final.FIELD_Y + Final.GAP + y * (Final.SQUARE_SIZE + Final.GAP * 2) - fieldYOffset,
                            Final.SQUARE_SIZE,
                            Final.SQUARE_SIZE
                    );
                }
            }
        }
        renderer.end();
    }

    private void drawShadow() {
        Tetromino t = new Tetromino(piece);
        while (t.checkDown()) {
            t.setCenter(new int[]{t.getCenter()[0] - 1, t.getCenter()[1]});
        }

        Gdx.gl.glLineWidth(1.3f);

        renderer.begin(ShapeRenderer.ShapeType.Line);
        renderer.setColor(t.getColor());
        for (int[] b : t.getBlocks()) {
            renderer.rect(
                    Final.FIELD_X + Final.GAP + (b[1] + t.getCenter()[1]) * ((Final.SQUARE_SIZE + Final.GAP * 2)),
                    Final.FIELD_Y + Final.GAP + (b[0] + t.getCenter()[0]) * ((Final.SQUARE_SIZE + Final.GAP * 2)) - fieldYOffset,
                    Final.SQUARE_SIZE,
                    Final.SQUARE_SIZE
            );
        }
        renderer.end();
    }

    private void checkStick() {
        if (!piece.canMoveDown()) {
            stickTimer++;
            dropTimer = 0;
            if (stickTimer % 40 == 0) {
                scanRows();
                newPiece();
            }
        } else {
            stickTimer = 0;
            dropTimer++;
            if (dropTimer % 60 == 0) {
                piece.down();
            }
        }
    }

    private void updatePiece() {
        piece.updateGrid(piece.getColor());
    }

    private int scanRows() {
        int rowsShifted = 0;
        for (int row = board.length - 1; row >= 0; row--) {
            boolean isFull = true;
            for (int col = 0; col < board[row].length; col++) {
                if (board[row][col] == Color.WHITE) {
                    isFull = false;
                    break;
                }
            }
            if (isFull) {
                shiftDown(row);
                rowsShifted++;
            }
        }
        return rowsShifted;
    }

    private void shiftDown(int delRow) {
        for (int row = delRow; row < board.length - 1; row++) {
            System.arraycopy(board[row + 1], 0, board[row], 0, board[row].length);
        }
        Arrays.fill(board[board.length - 1], Color.WHITE);
    }

    private void randomTetrominos() {
        ArrayList<Pieces> tetrominoListClone = new ArrayList<>(tetrominoList);
        while (tetrominoListClone.size() > 0) {
            Pieces temp = tetrominoListClone.remove((int) (Math.random()/*random.nextDouble()*/ * tetrominoListClone.size()));
            if (tetrominos.size() > 2 && tetrominos.get(tetrominos.size() - 1) == temp) {
                tetrominoListClone.add(temp);
                continue;
            }
            tetrominos.add(temp);
        }
    }

    private void newPiece() {
        int rows = scanRows();
        if (rows == 0) {
            fieldYOffset = 5;
        } else {
            fieldYOffset = 5 * (rows + 1);
        }

        piece = new Tetromino(tetrominos.remove(0));
        if (tetrominos.size() < 7) {
            randomTetrominos();
        }

        canHold = true;
    }

    private void storePiece() {
        if (canHold) {
            piece.updateGrid(Color.WHITE);
            if (hold == null) {
                hold = piece.getPiece();
                piece = new Tetromino(tetrominos.remove(0));
            } else {
                Pieces temp = hold;
                hold = piece.getPiece();
                piece = new Tetromino(temp);
            }
        }
        canHold = false;
    }

    private void tetrisStart(){
        board = new Color[Final.ROWS][Final.COLS];
        for (Color[] colors : board) {
            Arrays.fill(colors, Color.WHITE);
        }
        tetrominos = new ArrayList<>();

        fieldYOffset = 0;

        //random = new Random(91781961L);

        randomTetrominos();
        randomTetrominos();

        piece = new Tetromino(tetrominos.remove(0));
        hold = null;
        canHold = true;
    }
    //</editor-fold>

    //<editor-fold desc="unused InputProcessor methods">
    @Override
    public boolean keyUp(int i) {
        return false;
    }

    @Override
    public boolean keyTyped(char c) {
        return false;
    }

    @Override
    public boolean touchUp(int i, int i1, int i2, int i3) {
        return false;
    }

    @Override
    public boolean touchDragged(int i, int i1, int i2) {
        return false;
    }

    @Override
    public boolean mouseMoved(int i, int i1) {
        return false;
    }

    @Override
    public boolean scrolled(int i) {
        return false;
    }
    //</editor-fold>
}
