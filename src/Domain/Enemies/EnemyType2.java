package Domain.Enemies;

import Domain.Enemy;
import Domain.GameIO;
import Domain.Player;
import Domain.Projectile;
import Domain.Components.ShootingCooldown;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class EnemyType2 extends Enemy {

    private static final double TURN_SPEED = 0.003;
    private static final double PROJECTILE_SPEED = 0.30;

    private boolean turnStarted;
    private boolean shotPending;

    public EnemyType2(double x, double y, Color color, double velocity,
                      double angle, double rotationVelocity, double radius,
                      long firstShotTime) {
        super(x, y, radius, color, velocity, angle, rotationVelocity,
                ShootingCooldown.enabled(0, firstShotTime));
    }

    @Override
    protected void moveActive(long delta, GameIO gameIO) {
        double previousY = getY();
        super.moveActive(delta, gameIO);

        double threshold = gameIO.getHeight() * 0.30;
        if (!turnStarted && previousY < threshold && getY() >= threshold) {
            setRotationVelocity(getX() < gameIO.getWidth() / 2.0
                    ? TURN_SPEED
                    : -TURN_SPEED);
            turnStarted = true;
        }

        if (getRotationVelocity() > 0 && getAngle() >= 3 * Math.PI - 0.05) {
            setRotationVelocity(0);
            setAngle(3 * Math.PI);
            shotPending = true;
        } else if (getRotationVelocity() < 0 && getAngle() <= 0.05) {
            setRotationVelocity(0);
            setAngle(0);
            shotPending = true;
        }
    }

    @Override
    protected void drawActive(GameIO gameIO) {
        double x = getX();
        double y = getY();
        double radius = getRadius();

        gameIO.setColor(getColor());
        gameIO.drawLine(x, y - radius, x + radius, y);
        gameIO.drawLine(x + radius, y, x, y + radius);
        gameIO.drawLine(x, y + radius, x - radius, y);
        gameIO.drawLine(x - radius, y, x, y - radius);
    }

    @Override
    public List<Projectile> tryShoot(long currentTime, Player player) {
        if (!shotPending || !canShoot(currentTime)) {
            return Collections.emptyList();
        }

        double[] angles = {
                Math.PI / 2 + Math.PI / 8,
                Math.PI / 2,
                Math.PI / 2 - Math.PI / 8
        };
        List<Projectile> projectiles = new ArrayList<>(angles.length);

        for (double baseAngle : angles) {
            double spread = ThreadLocalRandom.current().nextDouble(-Math.PI / 12, Math.PI / 12);
            double angle = baseAngle + spread;
            projectiles.add(Projectile.createEnemyProjectile(
                    getX(),
                    getY(),
                    Math.cos(angle) * PROJECTILE_SPEED,
                    Math.sin(angle) * PROJECTILE_SPEED));
        }

        shotPending = false;
        registerShot(currentTime);
        return projectiles;
    }

    @Override
    public boolean isOutsideGameArea(GameIO gameIO) {
        return getX() < -10 || getX() > gameIO.getWidth() + 10;
    }
}
