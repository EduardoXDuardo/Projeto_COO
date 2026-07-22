package Domain.Components;

import Domain.GameIO;

public final class Explosion {

    private final boolean enabled;
    private final long duration;
    private long startTime = -1;

    private Explosion(boolean enabled, long duration) {
        this.enabled = enabled;
        this.duration = duration;
    }

    public static Explosion enabled(long duration) {
        if (duration <= 0) {
            throw new IllegalArgumentException("Explosion duration must be positive");
        }

        return new Explosion(true, duration);
    }

    public static Explosion disabled() {
        return new Explosion(false, 0);
    }

    public boolean start(long currentTime) {
        if (!enabled || isRunning()) {
            return false;
        }

        startTime = currentTime;
        return true;
    }

    public boolean isRunning() {
        return startTime >= 0;
    }

    public boolean isFinished(long currentTime) {
        return isRunning() && currentTime >= startTime + duration;
    }

    public void draw(double x, double y, long currentTime, GameIO gameIO) {
        if (!isRunning()) {
            return;
        }

        double progress = (double) (currentTime - startTime) / duration;
        progress = Math.max(0.0, Math.min(1.0, progress));
        gameIO.drawExplosion(x, y, progress);
    }

    public void reset() {
        startTime = -1;
    }
}
