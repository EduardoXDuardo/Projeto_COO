package Runner;

import Domain.GameIO;

import java.awt.Color;
import java.util.concurrent.ThreadLocalRandom;

class StarField {

    private final GameIO gameIO;
    private final StarLayer distantLayer;
    private final StarLayer nearLayer;

    StarField(GameIO gameIO) {
        this.gameIO = gameIO;
        distantLayer = new StarLayer(50, 0.045, 2.0, Color.DARK_GRAY, gameIO);
        nearLayer = new StarLayer(20, 0.070, 3.0, Color.GRAY, gameIO);
    }

    void draw(long delta) {
        distantLayer.draw(delta, gameIO);
        nearLayer.draw(delta, gameIO);
    }

    private static class StarLayer {

        private final double[] xPositions;
        private final double[] yPositions;
        private final double speed;
        private final double size;
        private final Color color;
        private double offset;

        StarLayer(int starCount, double speed, double size, Color color, GameIO gameIO) {
            this.speed = speed;
            this.size = size;
            this.color = color;
            xPositions = new double[starCount];
            yPositions = new double[starCount];

            ThreadLocalRandom random = ThreadLocalRandom.current();
            for (int index = 0; index < starCount; index++) {
                xPositions[index] = random.nextDouble(gameIO.getWidth());
                yPositions[index] = random.nextDouble(gameIO.getHeight());
            }
        }

        void draw(long delta, GameIO gameIO) {
            offset += speed * delta;
            gameIO.setColor(color);

            for (int index = 0; index < xPositions.length; index++) {
                double y = (yPositions[index] + offset) % gameIO.getHeight();
                gameIO.fillRect(xPositions[index], y, size, size);
            }
        }
    }
}
