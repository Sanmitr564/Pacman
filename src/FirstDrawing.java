import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.viewport.*;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.io.FileNotFoundException;

//NOTE: Always reset the JVM before compiling (it is the small loop arrow in the
//bottom right corner of the project window)!!

public class FirstDrawing extends ApplicationAdapter {
    private OrthographicCamera camera; //the camera to our world
    private Viewport viewport; //maintains the ratios of your world
    private ShapeRenderer renderer; //used to draw textures and fonts
    private BitmapFont font; //used to draw fonts (text)
    private SpriteBatch batch; //also needed to draw fonts (text)

    private PacMan pacman;

    @Override//called once when we start the game
    public void create() {

        camera = new OrthographicCamera();
        viewport = new FitViewport(Global.WORLD_WIDTH, Global.WORLD_HEIGHT, camera);
        renderer = new ShapeRenderer();
        font = new BitmapFont();
        batch = new SpriteBatch();//if you want to use images instead of using ShapeRenderer

        try {
            pacman = new PacMan();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override//called 60 times a second
    public void render() {
        input();
        preRender();
        renderBoard();
        renderPlayer();
        pacman.update();
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

                switch(pacman.getBoard()[row][col]){
                    case WALL -> renderer.setColor(Color.BLUE);
                    case STRAIGHT -> renderer.setColor(Color.BLACK);
                    case JUNCTION -> renderer.setColor(Color.CYAN);
                    case SPECIAL_JUNCTION -> renderer.setColor(Color.PINK);
                    case TELEPORT -> renderer.setColor(Color.WHITE);
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
        //Gdx.gl.glLineWidth(3f);
        float[] tileOffsets = pacman.getPlayer().getTileOffsets();
        renderer.circle(
                pacman.getPlayer().getX() * Global.TILE_SIZE + Global.FIELD_X +  /* 2 * pacman.getPlayer().getX() +*/ tileOffsets[0],
                pacman.getPlayer().getY() * Global.TILE_SIZE + Global.FIELD_Y + tileOffsets[1] /* + 2 * pacman.getPlayer().getY()*/,
                Global.TILE_SIZE / 2f
        );
        renderer.end();
    }

    private void input() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.UP)) {
            pacman.getPlayer().setQueuedDirection(Direction.UP);
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.LEFT)) {
            pacman.getPlayer().setQueuedDirection(Direction.LEFT);
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.RIGHT)) {
            pacman.getPlayer().setQueuedDirection(Direction.RIGHT);
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.DOWN)) {
            pacman.getPlayer().setQueuedDirection(Direction.DOWN);
        }
    }
}
