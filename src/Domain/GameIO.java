package Domain;

import java.awt.Color;

public interface GameIO {

    enum Key {
        UP,
        DOWN,
        LEFT,
        RIGHT,
        CONTROL,
        ESCAPE
    }

    int getWidth();

    int getHeight();

    boolean isKeyPressed(Key key);

    void setColor(Color color);

    void drawPlayer(double x, double y, double radius);

    void drawLine(double x1, double y1, double x2, double y2);

    void drawCircle(double x, double y, double radius);

    void drawExplosion(double x, double y, double alpha);
}
