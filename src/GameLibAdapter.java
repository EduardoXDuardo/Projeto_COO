import Domain.GameIO;

import java.awt.Color;

public final class GameLibAdapter implements GameIO {

    @Override
    public int getWidth() {
        return GameLib.WIDTH;
    }

    @Override
    public int getHeight() {
        return GameLib.HEIGHT;
    }

    @Override
    public boolean isKeyPressed(Key key) {
        int gameLibKey;

        switch (key) {
            case UP:
                gameLibKey = GameLib.KEY_UP;
                break;
            case DOWN:
                gameLibKey = GameLib.KEY_DOWN;
                break;
            case LEFT:
                gameLibKey = GameLib.KEY_LEFT;
                break;
            case RIGHT:
                gameLibKey = GameLib.KEY_RIGHT;
                break;
            case CONTROL:
                gameLibKey = GameLib.KEY_CONTROL;
                break;
            case ESCAPE:
                gameLibKey = GameLib.KEY_ESCAPE;
                break;
            default:
                throw new IllegalArgumentException("Unsupported key: " + key);
        }

        return GameLib.iskeyPressed(gameLibKey);
    }

    @Override
    public void setColor(Color color) {
        GameLib.setColor(color);
    }

    @Override
    public void drawPlayer(double x, double y, double radius) {
        GameLib.drawPlayer(x, y, radius);
    }

    @Override
    public void drawLine(double x1, double y1, double x2, double y2) {
        GameLib.drawLine(x1, y1, x2, y2);
    }

    @Override
    public void drawCircle(double x, double y, double radius) {
        GameLib.drawCircle(x, y, radius);
    }

    @Override
    public void drawExplosion(double x, double y, double alpha) {
        GameLib.drawExplosion(x, y, alpha);
    }
}
