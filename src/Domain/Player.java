package Domain;

import java.awt.*;

public class Player extends Entity {
    private final double velocity = 0.25;

    public Player() {
        super(GameLib.WIDTH / 2, GameLib.HEIGHT * 0.90, 12, Color.BLUE, 2000, true, true, 100);
    }

    public double getVelocity() {
        return velocity;
    }

    @Override
    public void draw(long currentTime) {
        GameLib.setColor(getColor());
        GameLib.drawPlayer(getX(), getY(), getRadius());
    }

    @Override
    public void move(long delta) {
        if(GameLib.iskeyPressed(GameLib.KEY_UP)) setY(getY() - velocity * delta);
        if(GameLib.iskeyPressed(GameLib.KEY_DOWN)) setY(getY() + velocity * delta);
        if(GameLib.iskeyPressed(GameLib.KEY_LEFT)) setX(getX() - velocity * delta);
        if(GameLib.iskeyPressed(GameLib.KEY_RIGHT)) setX(getX() + velocity * delta);
    }
}
