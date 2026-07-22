package Domain;

import Domain.Components.Explosion;
import Domain.Components.ShootingCooldown;

import java.awt.Color;
import java.util.List;

public abstract class Enemy extends Entity {

    private double angle;
    private final double velocity;
    private double rotationVelocity;

    protected Enemy(double x, double y, double radius, Color color,
                    double velocity, double angle, double rotationVelocity,
                    ShootingCooldown shootingCooldown) {
        super(x, y, radius, color, Explosion.enabled(500), shootingCooldown);
        this.velocity = velocity;
        this.angle = angle;
        this.rotationVelocity = rotationVelocity;
    }

    public double getAngle() {
        return angle;
    }

    protected void setAngle(double angle) {
        this.angle = angle;
    }

    public double getVelocity() {
        return velocity;
    }

    public double getRotationVelocity() {
        return rotationVelocity;
    }

    protected void setRotationVelocity(double rotationVelocity) {
        this.rotationVelocity = rotationVelocity;
    }

    @Override
    protected void moveActive(long delta, GameIO gameIO) {
        setX(getX() + velocity * Math.cos(angle) * delta);
        setY(getY() - velocity * Math.sin(angle) * delta);
        angle += rotationVelocity * delta;
    }

    public abstract List<Projectile> tryShoot(long currentTime, Player player);

    public abstract boolean isOutsideGameArea(GameIO gameIO);
}
