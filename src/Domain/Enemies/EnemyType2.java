package Domain.Enemies;

import Domain.Enemy;
import Domain.GameIO;

import java.awt.*;

public class EnemyType2 extends Enemy {

    public EnemyType2(double x, double y, Color color, double velocity, double angle, double rotationVelocity, double radius, long nextShoot, long nextEnemy) {
        super(x, y, color, velocity, angle, rotationVelocity, radius, nextShoot, nextEnemy);
    }

    @Override
    public void draw(long currentTime, GameIO gameIO) {

    }

    @Override
    public void shoot() {

    }
}
