package Runner;

import Domain.Enemy;
import Domain.Entity;
import Domain.Player;
import Domain.Projectile;

import java.util.Iterator;
import java.util.List;

public class CollisionDetector {

    private static final double PLAYER_COLLISION_SCALE = 0.8;

    public void detect(Player player,
                       List<Enemy> enemies,
                       List<Projectile> playerProjectiles,
                       List<Projectile> enemyProjectiles,
                       long currentTime) {
        detectPlayerCollisions(player, enemies, enemyProjectiles, currentTime);
        detectEnemyCollisions(enemies, playerProjectiles, currentTime);
    }

    private void detectPlayerCollisions(Player player,
                                        List<Enemy> enemies,
                                        List<Projectile> enemyProjectiles,
                                        long currentTime) {
        if (player.isExploding()) {
            return;
        }

        Iterator<Projectile> projectileIterator = enemyProjectiles.iterator();
        while (projectileIterator.hasNext()) {
            Projectile projectile = projectileIterator.next();
            if (collides(player, projectile, PLAYER_COLLISION_SCALE)) {
                player.startExplosion(currentTime);
                projectileIterator.remove();
                return;
            }
        }

        for (Enemy enemy : enemies) {
            if (!enemy.isExploding() && collides(player, enemy, PLAYER_COLLISION_SCALE)) {
                player.startExplosion(currentTime);
                return;
            }
        }
    }

    private void detectEnemyCollisions(List<Enemy> enemies,
                                       List<Projectile> playerProjectiles,
                                       long currentTime) {
        Iterator<Projectile> projectileIterator = playerProjectiles.iterator();

        while (projectileIterator.hasNext()) {
            Projectile projectile = projectileIterator.next();

            for (Enemy enemy : enemies) {
                if (!enemy.isExploding() && collides(enemy, projectile, 1.0)) {
                    enemy.startExplosion(currentTime);
                    projectileIterator.remove();
                    break;
                }
            }
        }
    }

    private boolean collides(Entity first, Entity second, double radiusScale) {
        double deltaX = first.getX() - second.getX();
        double deltaY = first.getY() - second.getY();
        double collisionRadius = (first.getRadius() + second.getRadius()) * radiusScale;
        return deltaX * deltaX + deltaY * deltaY < collisionRadius * collisionRadius;
    }
}
