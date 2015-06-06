package codeguru.zombiebird.helpers;

import codeguru.zombiebird.gameobjects.Bird;
import codeguru.zombiebird.gameworld.GameWorld;
import com.badlogic.gdx.InputAdapter;

public class InputHandler extends InputAdapter {
    private GameWorld myWorld;
    private Bird myBird;

    public InputHandler(GameWorld myWorld) {
        this.myWorld = myWorld;
        myBird = myWorld.getBird();
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        if (myWorld.isReady()) {
            myWorld.start();
        }

        myBird.onClick();

        if (myWorld.isGameOver()) {
            myWorld.restart();
        }

        return true;
    }
}
