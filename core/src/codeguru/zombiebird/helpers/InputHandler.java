package codeguru.zombiebird.helpers;

import codeguru.zombiebird.gameobjects.Bird;
import com.badlogic.gdx.InputAdapter;

public class InputHandler extends InputAdapter {
    private Bird myBird;

    public InputHandler(Bird bird) {
        myBird = bird;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        myBird.onClick();
        return true;
    }
}
