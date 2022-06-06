import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.*;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;


//NOTE: Always reset the JVM before compiling (it is the small loop arrow in the
//bottom right corner of the project window)!!

public class FirstDrawing extends ApplicationAdapter implements InputProcessor {
    private OrthographicCamera camera; //the camera to our world
    private Viewport viewport; //maintains the ratios of your world
    private ShapeRenderer renderer; //used to draw textures and fonts
    private BitmapFont font; //used to draw fonts (text)
    private SpriteBatch batch; //also needed to draw fonts (text)

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
            pacman.update();
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
            System.out.println("Tetris");
        }
        return false;
    }

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
