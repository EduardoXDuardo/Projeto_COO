package Domain;

import java.awt.*;

public abstract class Enemy extends Entity   {

    private double angle;
    private final double velocity;
    private final double rotationVelocity;

    public Enemy(double x, double y, double radius, Color color, double shootingInterval, double velocity, double rotationVelocity, double angle) {
        super(x, y, radius, color, 500, true, true, shootingInterval);
        this.velocity = velocity;
        this.rotationVelocity = rotationVelocity;
        this.angle = angle;
    }

    public double getAngle() {
        return angle;
    }

    public double getVelocity() {
        return velocity;
    }

    public double getRotationVelocity() {
        return rotationVelocity;
    }

    public void move(long delta) {
        setX(getX() + velocity * Math.cos(angle) * delta);
        setY(getY() + velocity * Math.sin(angle) * delta * (-1.0));
        angle += rotationVelocity * delta;
    }
}
