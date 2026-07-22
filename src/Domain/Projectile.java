package Domain;

import java.awt.*;

public class Projectile extends Entity {

    private final double xVelocity;
    private final double yVelocity;

    private Projectile(double x, double y, double radius, Color color, double xVelocity, double yVelocity) {
        super(x, y, radius, color, -1, false, false, -1);
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
    public void draw(long currentTime) {
        Color color = getColor();
        double x = getX();
        double y = getY();

        GameLib.setColor(color);

        if (color == Color.GREEN) { // desenha projetil do player
            GameLib.drawLine(x, y - 5, x, y + 5);
            GameLib.drawLine(x - 1, y - 3, x - 1, y + 3);
            GameLib.drawLine(x + 1, y - 3, x + 1, y + 3);
        } else if (color == Color.RED) { // desenha projetil do player
            GameLib.drawCircle(x, y, getRadius());
        }
    }

    @Override
    public void move(long delta) {
        setX(getX() + xVelocity * delta);
        setY(getY() + yVelocity * delta);
    }
}
