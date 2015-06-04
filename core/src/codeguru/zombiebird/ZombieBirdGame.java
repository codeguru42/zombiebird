package codeguru.zombiebird;

import codeguru.zombiebird.helpers.AssetLoader;
import codeguru.zombiebird.screens.GameScreen;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;

public class ZombieBirdGame extends Game {
    @Override
    public void create() {
        Gdx.app.log("ZBGame", "created");
        AssetLoader.load();
        setScreen(new GameScreen());
    }

    @Override
    public void dispose() {
        super.dispose();
        AssetLoader.dispose();
    }
}
