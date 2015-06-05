package codeguru.zombiebird;

import codeguru.zombiebird.helpers.AssetLoader;
import codeguru.zombiebird.screens.GameScreen;
import com.badlogic.gdx.Game;

public class ZombieBirdGame extends Game {
    @Override
    public void create() {
        AssetLoader.load();
        setScreen(new GameScreen());
    }

    @Override
    public void dispose() {
        super.dispose();
        AssetLoader.dispose();
    }
}
