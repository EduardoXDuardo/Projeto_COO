package Domain.Enemies;

import Domain.Enemy;
import Domain.GameIO;

import java.awt.*;

public class EnemyType3 extends Enemy {

    public EnemyType3(double x, double y, Color color, double velocity, double angle, double rotationVelocity, double radius, long nextShoot, long nextEnemy) {
        super(x, y, color, velocity, angle, rotationVelocity, radius, nextShoot, nextEnemy);
    }

    @Override
    public void draw(long currentTime, GameIO gameIO) {

    }

    @Override
    public void shoot() {

    }
}
