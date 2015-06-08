package codeguru.zombiebird.gameworld;

import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenEquations;
import aurelienribon.tweenengine.TweenManager;
import codeguru.zombiebird.gameobjects.Bird;
import codeguru.zombiebird.gameobjects.Grass;
import codeguru.zombiebird.gameobjects.Pipe;
import codeguru.zombiebird.gameobjects.ScrollHandler;
import codeguru.zombiebird.helpers.AssetLoader;
import codeguru.zombiebird.helpers.InputHandler;
import codeguru.zombiebird.tween.Value;
import codeguru.zombiebird.tween.ValueAccessor;
import codeguru.zombiebird.ui.SimpleButton;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import java.util.List;

public class GameRenderer {
    private final GameWorld myWorld;
    private final ShapeRenderer shapeRenderer;
    private SpriteBatch batcher;
    private int midPointY;
    private Bird bird;
    private Grass frontGrass, backGrass;
    private Pipe pipe1, pipe2, pipe3;
    private TweenManager manager;
    private Value alpha = new Value();
    private List<SimpleButton> menuButtons;

    public GameRenderer(GameWorld world, int midPointY) {
        myWorld = world;
        this.midPointY = midPointY;
        this.menuButtons = ((InputHandler) Gdx.input.getInputProcessor()).getMenuButtons();

        OrthographicCamera cam = new OrthographicCamera();
        cam.setToOrtho(true, 136, 204);

        shapeRenderer = new ShapeRenderer();
        shapeRenderer.setProjectionMatrix(cam.combined);

        batcher = new SpriteBatch();
        batcher.setProjectionMatrix(cam.combined);
        initGameObjects();
        setupTweens();
    }

    private void setupTweens() {
        Tween.registerAccessor(Value.class, new ValueAccessor());
        manager = new TweenManager();
        Tween.to(alpha, -1, .5f).target(0).ease(TweenEquations.easeOutQuad).start(manager);
    }

    private void initGameObjects() {
        bird = myWorld.getBird();
        ScrollHandler scroller = myWorld.getScroller();
        frontGrass = scroller.getFrontGrass();
        backGrass = scroller.getBackGrass();
        pipe1 = scroller.getPipe1();
        pipe2 = scroller.getPipe2();
        pipe3 = scroller.getPipe3();
    }

    public void render(float delta, float runTime) {
        Gdx.gl.glClearColor(55 / 255.0f, 80 / 255.0f, 100 / 255.0f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Draw Dirt
        shapeRenderer.begin(ShapeType.Filled);
        shapeRenderer.setColor(147 / 255.0f, 80 / 255.0f, 27 / 255.0f, 1);
        shapeRenderer.rect(0, midPointY + 77, 136, 52);
        shapeRenderer.end();

        batcher.begin();
        batcher.disableBlending();
        batcher.draw(AssetLoader.bg, 0, midPointY + 23, 136, 43);

        drawGrass();
        drawPipes();
        batcher.enableBlending();
        drawSkulls();

        if (myWorld.isRunning()) {
            drawBird(runTime);
            drawScore();
        } else if (myWorld.isReady()) {
            drawBird(runTime);
            drawScore();
        } else if (myWorld.isMenu()) {
            drawBirdCentered(runTime);
            drawMenuUI();
        } else if (myWorld.isGameOver()) {
            drawBird(runTime);
            drawScore();
        } else if (myWorld.isHighScore()) {
            drawBird(runTime);
            drawScore();
        }
        batcher.end();
        drawTransition(delta);
    }

    private void drawGrass() {
        // Draw the grass
        batcher.draw(AssetLoader.grass, frontGrass.getX(), frontGrass.getY(),
                frontGrass.getWidth(), frontGrass.getHeight());
        batcher.draw(AssetLoader.grass, backGrass.getX(), backGrass.getY(),
                backGrass.getWidth(), backGrass.getHeight());
    }

    private void drawSkulls() {
        batcher.draw(AssetLoader.skullUp, pipe1.getX() - 1,
                pipe1.getY() + pipe1.getHeight() - 14, 24, 14);
        batcher.draw(AssetLoader.skullDown, pipe1.getX() - 1,
                pipe1.getY() + pipe1.getHeight() + 45, 24, 14);

        batcher.draw(AssetLoader.skullUp, pipe2.getX() - 1,
                pipe2.getY() + pipe2.getHeight() - 14, 24, 14);
        batcher.draw(AssetLoader.skullDown, pipe2.getX() - 1,
                pipe2.getY() + pipe2.getHeight() + 45, 24, 14);

        batcher.draw(AssetLoader.skullUp, pipe3.getX() - 1,
                pipe3.getY() + pipe3.getHeight() - 14, 24, 14);
        batcher.draw(AssetLoader.skullDown, pipe3.getX() - 1,
                pipe3.getY() + pipe3.getHeight() + 45, 24, 14);
    }

    private void drawPipes() {
        batcher.draw(AssetLoader.bar, pipe1.getX(), pipe1.getY(), pipe1.getWidth(),
                pipe1.getHeight());
        batcher.draw(AssetLoader.bar, pipe1.getX(), pipe1.getY() + pipe1.getHeight() + 45,
                pipe1.getWidth(), midPointY + 66 - (pipe1.getHeight() + 45));

        batcher.draw(AssetLoader.bar, pipe2.getX(), pipe2.getY(), pipe2.getWidth(),
                pipe2.getHeight());
        batcher.draw(AssetLoader.bar, pipe2.getX(), pipe2.getY() + pipe2.getHeight() + 45,
                pipe2.getWidth(), midPointY + 66 - (pipe2.getHeight() + 45));

        batcher.draw(AssetLoader.bar, pipe3.getX(), pipe3.getY(), pipe3.getWidth(),
                pipe3.getHeight());
        batcher.draw(AssetLoader.bar, pipe3.getX(), pipe3.getY() + pipe3.getHeight() + 45,
                pipe3.getWidth(), midPointY + 66 - (pipe3.getHeight() + 45));
    }

    private void drawBirdCentered(float runTime) {
        batcher.draw(AssetLoader.birdAnimation.getKeyFrame(runTime), 59, bird.getY() - 15,
                bird.getWidth() / 2.0f, bird.getHeight() / 2.0f,
                bird.getWidth(), bird.getHeight(), 1, 1, bird.getRotation());
    }

    private void drawBird(float runTime) {

        if (bird.shouldntFlap()) {
            batcher.draw(AssetLoader.bird, bird.getX(), bird.getY(),
                    bird.getWidth() / 2.0f, bird.getHeight() / 2.0f,
                    bird.getWidth(), bird.getHeight(), 1, 1, bird.getRotation());

        } else {
            batcher.draw(AssetLoader.birdAnimation.getKeyFrame(runTime), bird.getX(),
                    bird.getY(), bird.getWidth() / 2.0f,
                    bird.getHeight() / 2.0f, bird.getWidth(), bird.getHeight(),
                    1, 1, bird.getRotation());
        }

    }

    private void drawMenuUI() {
        batcher.draw(AssetLoader.zbLogo, 136 / 2 - 56, midPointY - 50,
                AssetLoader.zbLogo.getRegionWidth() / 1.2f,
                AssetLoader.zbLogo.getRegionHeight() / 1.2f);

        for (SimpleButton button : menuButtons) {
            button.draw(batcher);
        }

    }

    private void drawScore() {
        int length = ("" + myWorld.getScore()).length();
        AssetLoader.shadow.draw(batcher, "" + myWorld.getScore(),
                68 - (3 * length), midPointY - 82);
        AssetLoader.font.draw(batcher, "" + myWorld.getScore(),
                68 - (3 * length), midPointY - 83);
    }

    private void drawTransition(float delta) {
        if (alpha.getValue() > 0) {
            manager.update(delta);
            Gdx.gl.glEnable(GL20.GL_BLEND);
            Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
            shapeRenderer.begin(ShapeType.Filled);
            shapeRenderer.setColor(1, 1, 1, alpha.getValue());
            shapeRenderer.rect(0, 0, 136, 300);
            shapeRenderer.end();
            Gdx.gl.glDisable(GL20.GL_BLEND);

        }
    }
}
