package Runner;

import Domain.Enemy;
import Domain.Entity;
import Domain.GameIO;
import Domain.Player;
import Domain.Projectile;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

public class Game {

    private static final long TARGET_FRAME_TIME = 3;

    private final GameIO gameIO;
    private final Player player;
    private final List<Enemy> enemies = new ArrayList<>();
    private final List<Projectile> playerProjectiles = new ArrayList<>();
    private final List<Projectile> enemyProjectiles = new ArrayList<>();
    private final CollisionDetector collisionDetector = new CollisionDetector();
    private final StarField starField;

    public Game(GameIO gameIO) {
        this.gameIO = Objects.requireNonNull(gameIO, "gameIO");
        player = new Player(gameIO);
        starField = new StarField(gameIO);
    }

    public void run() {
        gameIO.initializeGraphics();

        long currentTime = System.currentTimeMillis();
        EnemySpawner enemySpawner = new EnemySpawner(gameIO, currentTime);
        boolean running = true;

        while (running) {
            long previousTime = currentTime;
            currentTime = System.currentTimeMillis();
            long delta = currentTime - previousTime;

            collisionDetector.detect(
                    player, enemies, playerProjectiles, enemyProjectiles, currentTime);

            updateProjectiles(playerProjectiles, delta);
            updateProjectiles(enemyProjectiles, delta);
            updateEnemies(currentTime, delta);
            enemies.addAll(enemySpawner.spawn(currentTime));
            updatePlayer(currentTime, delta);

            if (gameIO.isKeyPressed(GameIO.Key.ESCAPE)) {
                running = false;
            }

            draw(currentTime, delta);
            gameIO.display();
            busyWait(currentTime + TARGET_FRAME_TIME);
        }
    }

    private void updateProjectiles(List<Projectile> projectiles, long delta) {
        Iterator<Projectile> iterator = projectiles.iterator();

        while (iterator.hasNext()) {
            Projectile projectile = iterator.next();
            if (projectile.isOutsideGameArea(gameIO)) {
                iterator.remove();
            } else {
                projectile.move(delta, gameIO);
            }
        }
    }

    private void updateEnemies(long currentTime, long delta) {
        Iterator<Enemy> iterator = enemies.iterator();

        while (iterator.hasNext()) {
            Enemy enemy = iterator.next();

            if (enemy.isExploding()) {
                if (enemy.isExplosionFinished(currentTime)) {
                    iterator.remove();
                }
                continue;
            }

            if (enemy.isOutsideGameArea(gameIO)) {
                iterator.remove();
                continue;
            }

            enemy.move(delta, gameIO);
            enemyProjectiles.addAll(enemy.tryShoot(currentTime, player));
        }
    }

    private void updatePlayer(long currentTime, long delta) {
        if (player.isExplosionFinished(currentTime)) {
            player.resetExplosion();
        }

        player.move(delta, gameIO);
        player.tryShoot(currentTime, gameIO).ifPresent(playerProjectiles::add);
    }

    private void draw(long currentTime, long delta) {
        starField.draw(delta);
        player.draw(currentTime, gameIO);
        drawEntities(playerProjectiles, currentTime);
        drawEntities(enemyProjectiles, currentTime);
        drawEntities(enemies, currentTime);
    }

    private void drawEntities(List<? extends Entity> entities, long currentTime) {
        for (Entity entity : entities) {
            entity.draw(currentTime, gameIO);
        }
    }

    private void busyWait(long targetTime) {
        while (System.currentTimeMillis() < targetTime) {
            Thread.yield();
        }
    }
}
