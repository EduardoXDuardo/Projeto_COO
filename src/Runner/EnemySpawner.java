package Runner;

import Domain.Enemy;
import Domain.GameIO;
import Domain.Enemies.EnemyType1;
import Domain.Enemies.EnemyType2;
import Domain.Enemies.EnemyType3;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class EnemySpawner {

    private static final long ENEMY_TYPE_1_INITIAL_DELAY = 2_000;
    private static final long ENEMY_TYPE_1_INTERVAL = 500;
    private static final long ENEMY_TYPE_2_INITIAL_DELAY = 7_000;
    private static final long ENEMY_TYPE_2_FORMATION_INTERVAL = 120;
    private static final int ENEMY_TYPE_2_FORMATION_SIZE = 10;
    private static final long ENEMY_TYPE_3_INITIAL_DELAY = 12_000;

    private final GameIO gameIO;

    private long nextEnemyType1;
    private long nextEnemyType2;
    private long nextEnemyType3;
    private int enemyType2FormationCount;
    private double enemyType2SpawnX;

    public EnemySpawner(GameIO gameIO, long initialTime) {
        this.gameIO = gameIO;
        nextEnemyType1 = initialTime + ENEMY_TYPE_1_INITIAL_DELAY;
        nextEnemyType2 = initialTime + ENEMY_TYPE_2_INITIAL_DELAY;
        nextEnemyType3 = initialTime + ENEMY_TYPE_3_INITIAL_DELAY;
        enemyType2SpawnX = gameIO.getWidth() * 0.20;
    }

    public List<Enemy> spawn(long currentTime) {
        List<Enemy> spawnedEnemies = new ArrayList<>(3);

        if (currentTime > nextEnemyType1) {
            spawnedEnemies.add(createEnemyType1(currentTime));
            nextEnemyType1 = currentTime + ENEMY_TYPE_1_INTERVAL;
        }

        if (currentTime > nextEnemyType2) {
            spawnedEnemies.add(createEnemyType2());
            scheduleNextEnemyType2(currentTime);
        }

        if (currentTime > nextEnemyType3) {
            spawnedEnemies.add(createEnemyType3(currentTime));
            nextEnemyType3 = currentTime + randomLong(4_000, 6_001);
        }

        return spawnedEnemies;
    }

    private Enemy createEnemyType1(long currentTime) {
        double x = randomDouble(10.0, gameIO.getWidth() - 10.0);
        double velocity = randomDouble(0.20, 0.35);

        return new EnemyType1(
                x, -10.0, Color.CYAN, velocity,
                3 * Math.PI / 2, 0.0, 9.0,
                currentTime + 500);
    }

    private Enemy createEnemyType2() {
        return new EnemyType2(
                enemyType2SpawnX, -10.0, Color.MAGENTA, 0.42,
                3 * Math.PI / 2, 0.0, 12.0,
                0);
    }

    private void scheduleNextEnemyType2(long currentTime) {
        enemyType2FormationCount++;

        if (enemyType2FormationCount < ENEMY_TYPE_2_FORMATION_SIZE) {
            nextEnemyType2 = currentTime + ENEMY_TYPE_2_FORMATION_INTERVAL;
            return;
        }

        enemyType2FormationCount = 0;
        enemyType2SpawnX = ThreadLocalRandom.current().nextBoolean()
                ? gameIO.getWidth() * 0.20
                : gameIO.getWidth() * 0.80;
        nextEnemyType2 = currentTime + randomLong(3_000, 6_000);
    }

    private Enemy createEnemyType3(long currentTime) {
        double horizontalMargin = 75.0;
        double x = randomDouble(horizontalMargin, gameIO.getWidth() - horizontalMargin);

        return new EnemyType3(
                x, -12.0, Color.ORANGE, 0.18,
                Math.PI / 2, 0.0, 10.0,
                currentTime + 700);
    }

    private double randomDouble(double origin, double bound) {
        return ThreadLocalRandom.current().nextDouble(origin, bound);
    }

    private long randomLong(long origin, long bound) {
        return ThreadLocalRandom.current().nextLong(origin, bound);
    }
}
