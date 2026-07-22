package Domain.Modules;

import java.awt.*;

public class Explosive {

    private final boolean canExplode;
    private double explosionStart = -1;
    private final double explosionDuration;

    public Explosive(double explosionDuration, boolean canExplode) {
        this.explosionDuration = explosionDuration;
        this.canExplode = canExplode;
    }

    public void setExplosionStart(double explosionStart) {
        this.explosionStart = explosionStart;
    }

    /*
    isso ta muito ruim
    public Explosion tryExplode(long currentTime){

        if (explosionStart > 0) {
            if ((explosionStart + explosionDuration) < currentTime) {
                return Explosion.Finished;
            }

            drawExplosion(currentTime);
            return Explosion.Exploding;
        }

        return Explosion.NotExplodedYet;
    }
    */

    public void drawExplosion(double x, double y, long currentTime){
        if (canExplode) {
            double alpha = (currentTime - explosionStart) / (explosionDuration);
            GameLib.drawExplosion(x, y, alpha);
        }
    }
}
