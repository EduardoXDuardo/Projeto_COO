package Domain;

import Domain.Components.Explosion;
import Domain.Components.ShootingCooldown;

import java.awt.Color;

public class Projectile extends Entity {

    private final double xVelocity;
    private final double yVelocity;

    private Projectile(double x, double y, double radius, Color color, double xVelocity, double yVelocity) {
        super(x, y, radius, color, Explosion.disabled(), ShootingCooldown.disabled());
        this.xVelocity = xVelocity;
        this.yVelocity = yVelocity;
    }

    public static Projectile createPlayerProjectile(double x, double y, double playerRadius){
        return new Projectile(x,y - 2 * playerRadius, 0, Color.GREEN, 0, -1);
    }

    public static Projectile createEnemyProjectile(double x, double y, double xVelocity, double yVelocity){
        return new Projectile(x,y, 2, Color.RED, xVelocity, yVelocity);
    }

    @Override
    protected void drawActive(GameIO gameIO) {
        Color color = getColor();
        double x = getX();
        double y = getY();

        gameIO.setColor(color);

        if (color == Color.GREEN) { // desenha projetil do player
            gameIO.drawLine(x, y - 5, x, y + 5);
            gameIO.drawLine(x - 1, y - 3, x - 1, y + 3);
            gameIO.drawLine(x + 1, y - 3, x + 1, y + 3);
        } else if (color == Color.RED) { // desenha projetil do player
            gameIO.drawCircle(x, y, getRadius());
        }
    }

    @Override
    protected void moveActive(long delta, GameIO gameIO) {
        setX(getX() + xVelocity * delta);
        setY(getY() + yVelocity * delta);
    }

    public boolean isOutsideGameArea(GameIO gameIO) {
        double margin = Math.max(1.0, getRadius());
        return getX() < -margin
                || getX() > gameIO.getWidth() + margin
                || getY() < -margin
                || getY() > gameIO.getHeight() + margin;
    }
}
