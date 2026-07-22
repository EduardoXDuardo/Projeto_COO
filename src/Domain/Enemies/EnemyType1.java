package Domain.Enemies;

import Domain.Enemy;
import Domain.GameIO;
import Domain.Player;
import Domain.Projectile;
import Domain.Components.ShootingCooldown;

import java.awt.Color;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class EnemyType1 extends Enemy {

    private static final double PROJECTILE_SPEED = 0.45;

    public EnemyType1(double x, double y, Color color, double velocity,
                      double angle, double rotationVelocity, double radius,
                      long firstShotTime) {
        super(x, y, radius, color, velocity, angle, rotationVelocity,
                ShootingCooldown.enabled(500, firstShotTime));
    }

    @Override
    protected void drawActive(GameIO gameIO) {
        gameIO.setColor(getColor());
        gameIO.drawCircle(getX(), getY(), getRadius());
    }

    @Override
    public List<Projectile> tryShoot(long currentTime, Player player) {
        if (!canShoot(currentTime) || player == null || getY() >= player.getY()) {
            return Collections.emptyList();
        }

        double xVelocity = Math.cos(getAngle()) * PROJECTILE_SPEED;
        double yVelocity = -Math.sin(getAngle()) * PROJECTILE_SPEED;
        Projectile projectile = Projectile.createEnemyProjectile(
                getX(), getY(), xVelocity, yVelocity);

        long delay = ThreadLocalRandom.current().nextLong(200, 700);
        scheduleNextShot(currentTime + delay);
        return Collections.singletonList(projectile);
    }

    @Override
    public boolean isOutsideGameArea(GameIO gameIO) {
        return getY() > gameIO.getHeight() + 10;
    }
}
