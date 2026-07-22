package Domain;

import java.awt.*;

public class Player extends Entity {
    private final double velocity = 0.25;

    public Player(GameIO gameIO) {
        super(gameIO.getWidth() / 2.0, gameIO.getHeight() * 0.90, 12, Color.BLUE, 2000, true, true, 100);
    }

    public double getVelocity() {
        return velocity;
    }

    @Override
    public void draw(long currentTime, GameIO gameIO) {
        gameIO.setColor(getColor());
        gameIO.drawPlayer(getX(), getY(), getRadius());
    }

    @Override
    public void move(long delta, GameIO gameIO) {
        if(gameIO.isKeyPressed(GameIO.Key.UP)) setY(getY() - velocity * delta);
        if(gameIO.isKeyPressed(GameIO.Key.DOWN)) setY(getY() + velocity * delta);
        if(gameIO.isKeyPressed(GameIO.Key.LEFT)) setX(getX() - velocity * delta);
        if(gameIO.isKeyPressed(GameIO.Key.RIGHT)) setX(getX() + velocity * delta);
    }
}
