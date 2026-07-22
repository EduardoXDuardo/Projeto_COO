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

public class EnemyType3 extends Enemy {

    private static final double HORIZONTAL_AMPLITUDE = 60.0;
    private static final double OSCILLATION_SPEED = 0.004;
    private static final double PROJECTILE_SPEED = 0.25;
    private static final int PROJECTILE_AMOUNT = 5;

    private final double initialX;
    private long elapsedTime;

    public EnemyType3(double x, double y, Color color, double velocity,
                      double angle, double rotationVelocity, double radius,
                      long firstShotTime) {
        super(x, y, radius, color, velocity, angle, rotationVelocity,
                ShootingCooldown.enabled(900, firstShotTime));
        this.initialX = x;
    }

    @Override
    protected void moveActive(long delta, GameIO gameIO) {
        elapsedTime += delta;
        setX(initialX + Math.sin(elapsedTime * OSCILLATION_SPEED) * HORIZONTAL_AMPLITUDE);
        setY(getY() + getVelocity() * delta);
    }

    @Override
    protected void drawActive(GameIO gameIO) {
        double x = getX();
        double y = getY();
        double radius = getRadius();

        gameIO.setColor(getColor());
        gameIO.drawCircle(x, y, radius);
        gameIO.drawLine(x - radius, y, x + radius, y);
        gameIO.drawLine(x, y - radius, x, y + radius);
    }

    @Override
    public List<Projectile> tryShoot(long currentTime, Player player) {
        if (!canShoot(currentTime) || player == null || getY() >= player.getY()) {
            return Collections.emptyList();
        }

        double targetAngle = Math.atan2(player.getY() - getY(), player.getX() - getX());
        double angleStep = Math.PI / 12;
        List<Projectile> projectiles = new ArrayList<>(PROJECTILE_AMOUNT);

        for (int i = 0; i < PROJECTILE_AMOUNT; i++) {
            double offset = (i - (PROJECTILE_AMOUNT - 1) / 2.0) * angleStep;
            double angle = targetAngle + offset;
            projectiles.add(Projectile.createEnemyProjectile(
                    getX(),
                    getY(),
                    Math.cos(angle) * PROJECTILE_SPEED,
                    Math.sin(angle) * PROJECTILE_SPEED));
        }

        registerShot(currentTime);
        return projectiles;
    }

    @Override
    public boolean isOutsideGameArea(GameIO gameIO) {
        return getY() > gameIO.getHeight() + getRadius();
    }
}
