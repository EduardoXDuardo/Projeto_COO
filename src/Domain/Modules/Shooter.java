package Domain.Modules;

public class Shooter {

    private final boolean canShot;
    private final double shootingInterval;

    public Shooter(boolean canShoot, double shootingInterval) {
        this.canShot = canShoot;
        this.shootingInterval = shootingInterval;
    }

    public void shot(){
        if (canShot){

        }
    }
}
