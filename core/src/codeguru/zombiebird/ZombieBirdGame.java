package codeguru.zombiebird;

import codeguru.zombiebird.helpers.AssetLoader;
import codeguru.zombiebird.screens.SplashScreen;
import com.badlogic.gdx.Game;

public class ZombieBirdGame extends Game {
    @Override
    public void create() {
        AssetLoader.load();
        setScreen(new SplashScreen(this));
    }

    @Override
    public void dispose() {
        super.dispose();
        AssetLoader.dispose();
    }
}
