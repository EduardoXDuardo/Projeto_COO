package Domain;

import Domain.Components.Explosion;
import Domain.Components.ShootingCooldown;

import java.awt.*;

public class Player extends Entity {
    private final double velocity = 0.25;

    public Player(GameIO gameIO) {
        super(gameIO.getWidth() / 2.0, gameIO.getHeight() * 0.90, 12, Color.BLUE,
                Explosion.enabled(2000), ShootingCooldown.enabled(100, 0));
    }

    public double getVelocity() {
        return velocity;
    }

    @Override
    protected void drawActive(GameIO gameIO) {
        gameIO.setColor(getColor());
        gameIO.drawPlayer(getX(), getY(), getRadius());
    }

    @Override
    protected void moveActive(long delta, GameIO gameIO) {
        if(gameIO.isKeyPressed(GameIO.Key.UP)) setY(getY() - velocity * delta);
        if(gameIO.isKeyPressed(GameIO.Key.DOWN)) setY(getY() + velocity * delta);
        if(gameIO.isKeyPressed(GameIO.Key.LEFT)) setX(getX() - velocity * delta);
        if(gameIO.isKeyPressed(GameIO.Key.RIGHT)) setX(getX() + velocity * delta);
    }

}
