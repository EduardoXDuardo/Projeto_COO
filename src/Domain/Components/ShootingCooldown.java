package Domain.Components;

public final class ShootingCooldown {

    private final boolean enabled;
    private final long interval;
    private long nextShotTime;

    private ShootingCooldown(boolean enabled, long interval, long nextShotTime) {
        this.enabled = enabled;
        this.interval = interval;
        this.nextShotTime = nextShotTime;
    }

    public static ShootingCooldown enabled(long interval, long firstShotTime) {
        if (interval < 0) {
            throw new IllegalArgumentException("Shooting interval cannot be negative");
        }

        return new ShootingCooldown(true, interval, firstShotTime);
    }

    public static ShootingCooldown disabled() {
        return new ShootingCooldown(false, 0, Long.MAX_VALUE);
    }

    public boolean isReady(long currentTime) {
        return enabled && currentTime >= nextShotTime;
    }

    public void recordShot(long currentTime) {
        nextShotTime = currentTime + interval;
    }

    public void scheduleNextShot(long nextShotTime) {
        this.nextShotTime = nextShotTime;
    }
}
