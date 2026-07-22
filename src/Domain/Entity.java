package Domain;

import Domain.Components.Explosion;
import Domain.Components.ShootingCooldown;

import java.awt.Color;
import java.util.Objects;

public abstract class Entity {

    private double x;
    private double y;
    private final double radius;
    private final Color color;
    private final Explosion explosion;
    private final ShootingCooldown shootingCooldown;

    protected Entity(double x, double y, double radius, Color color,
                     Explosion explosion, ShootingCooldown shootingCooldown) {
        if (radius < 0) {
            throw new IllegalArgumentException("Radius cannot be negative");
        }

        this.x = x;
        this.y = y;
        this.radius = radius;
        this.color = Objects.requireNonNull(color, "color");
        this.explosion = Objects.requireNonNull(explosion, "explosion");
        this.shootingCooldown = Objects.requireNonNull(shootingCooldown, "shootingCooldown");
    }

    public double getX() {
        return x;
    }

    protected void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    protected void setY(double y) {
        this.y = y;
    }

    public double getRadius() {
        return radius;
    }

    public Color getColor() {
        return color;
    }

    public boolean isExploding() {
        return explosion.isRunning();
    }

    public boolean isExplosionFinished(long currentTime) {
        return explosion.isFinished(currentTime);
    }

    public boolean startExplosion(long currentTime) {
        return explosion.start(currentTime);
    }

    public void resetExplosion() {
        explosion.reset();
    }

    protected boolean canShoot(long currentTime) {
        return !isExploding() && shootingCooldown.isReady(currentTime);
    }

    protected void registerShot(long currentTime) {
        shootingCooldown.recordShot(currentTime);
    }

    protected void scheduleNextShot(long nextShotTime) {
        shootingCooldown.scheduleNextShot(nextShotTime);
    }

    public final void draw(long currentTime, GameIO gameIO) {
        if (isExploding()) {
            explosion.draw(x, y, currentTime, gameIO);
            return;
        }

        drawActive(gameIO);
    }

    public final void move(long delta, GameIO gameIO) {
        if (!isExploding()) {
            moveActive(delta, gameIO);
        }
    }

    protected abstract void drawActive(GameIO gameIO);

    protected abstract void moveActive(long delta, GameIO gameIO);
}
