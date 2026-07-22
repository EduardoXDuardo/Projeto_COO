package Domain;

import Domain.Modules.Explosive;
import Domain.Modules.Shooter;

import java.awt.Color;

public abstract class Entity {

    private double x;
    private double y;
    private final double radius;
    private final Color color;

    private final Explosive explosive;
    private final Shooter shooter;

    // algo para spawnar

    public Entity(double x, double y, double radius, Color color,
                  double explosionDuration, boolean canExplode, // explosive
                  boolean canShoot, double shootingInterval) { // shooter
        this.x = x;
        this.y = y;
        this.radius = radius;
        this.color = color;
        this.explosive = new Explosive(explosionDuration, canExplode);
        this.shooter = new Shooter(canShoot, shootingInterval);
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

    public void explode(long currentTime){
        explosive.drawExplosion(x, y, currentTime);
    }

    public void shoot(){
        shooter.shot();
    }

    public abstract void draw(long currentTime);

    public abstract void move(long delta);
}
