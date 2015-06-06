package codeguru.zombiebird.gameobjects;

import codeguru.zombiebird.helpers.AssetLoader;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;

public class Bird {
    private Vector2 position;
    private Vector2 velocity;
    private Vector2 acceleration;
    private Circle boundingCircle;

    private float rotation;
    private int width;
    private int height;
    private boolean isAlive = true;

    public Bird(float x, float y, int width, int height) {
        this.width = width;
        this.height = height;
        position = new Vector2(x, y);
        velocity = new Vector2(0, 0);
        acceleration = new Vector2(0, 460);
        boundingCircle = new Circle();
    }

    public void update(float delta) {

        velocity.add(acceleration.cpy().scl(delta));
        boundingCircle.set(position.x + 9, position.y + 6, 6.5f);

        if (velocity.y > 200) {
            velocity.y = 200;
        }

        // CEILING CHECK
        if (position.y < -13) {
            position.y = -13;
            velocity.y = 0;
        }

        position.add(velocity.cpy().scl(delta));

        // Rotate counterclockwise
        if (velocity.y < 0) {
            rotation -= 600 * delta;

            if (rotation < -20) {
                rotation = -20;
            }
        }

        // Rotate clockwise
        if (isFalling() || !isAlive) {
            rotation += 480 * delta;
            if (rotation > 90) {
                rotation = 90;
            }

        }
    }

    public void onClick() {
        if (isAlive) {
            AssetLoader.flap.play();
            velocity.y = -140;
        }
    }

    public void die() {
        isAlive = false;
        velocity.y = 0;
    }

    public void decelerate() {
        // We want the bird to stop accelerating downwards once it is dead.
        acceleration.y = 0;
    }

    public float getX() {
        return position.x;
    }

    public float getY() {
        return position.y;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    public float getRotation() {
        return rotation;
    }

    public boolean isFalling() {
        return velocity.y > 110;
    }

    public boolean shouldntFlap() {
        return velocity.y > 70 || !isAlive;
    }

    public Circle getBoundingCircle() {
        return boundingCircle;
    }

    public boolean isAlive() {
        return isAlive;
    }
}
