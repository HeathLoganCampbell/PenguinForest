package dev.cobblesword.penguinforest.entity.projectile;

import dev.cobblesword.penguinforest.assets.Asset;
import dev.cobblesword.penguinforest.fx.Bitmap;
import dev.cobblesword.penguinforest.utils.Location;
import dev.cobblesword.penguinforest.utils.MathUtil;

public class SnowballEntity extends ProjectileEntity
{
    public static int SIZE = 40;
    public static int SIZE_HALF = SIZE / 2;
    private int animationFrame = 0;
    public Location startLocation;
    public Location targetLocation;
    public boolean hitGround = false;
    public float throwCount = 0;

    public Location shadowLocation;

    public SnowballEntity(Location targetLocation)
    {
        this.targetLocation = targetLocation;
    }

    // Turns out they should be queued before adding :) because input is on it's own thread
    @Override
    public void spawn(double posX, double posY) {
        super.spawn(posX, posY);

        this.startLocation = new Location(posX, posY);
        this.shadowLocation = startLocation.clone();
    }

    @Override
    public void update(int tick) {
        super.update(tick);

        if (livingTicks > 30 * 20) {
            this.removed = true;
            return;
        }

        if (hitGround)
        {
            return;
        }

        double xToTravel = startLocation.distanceX(targetLocation);
        double yToTravel = startLocation.distanceY(targetLocation);

        double distance = startLocation.distance(targetLocation);
        float timeToTake = 0.4f;

        double x = MathUtil.easeLinear(throwCount, startLocation.getX(), xToTravel, timeToTake);
        double y = MathUtil.easeLinear(throwCount, startLocation.getY(), yToTravel, timeToTake);

        double snowballY = 10;
        float halfTime = timeToTake / 2;

        double peakHeight = 250;

        if (throwCount < halfTime) {
            snowballY += MathUtil.easeOutQuad(throwCount,
                    startLocation.getY(),
                    -peakHeight - startLocation.getY(),
                    halfTime);
        } else {
            snowballY += MathUtil.easeInQuad(throwCount - (halfTime),
                    -peakHeight,
                    peakHeight,
                    halfTime);
        }

        if (snowballY >= 0)
        {
            this.visible = false;
        }
        else
        {
            this.visible = true;
        }

        this.location.setX(x);
        this.location.setY(y + snowballY);

        this.shadowLocation.setX(x);
        this.shadowLocation.setY(y);

        if(throwCount >= timeToTake)
        {
            this.livingTicks = 0;
            this.hitGround = true;
            this.visible = true;

            this.location = this.shadowLocation;

            this.animationFrame = 3;
        }
        else
        {
            throwCount += (0.01);
        }
    }

    @Override
    public void render(Bitmap screen)
    {
        screen.drawSegment(Asset.SNOWBALL,  animationFrame * SIZE, 0, SIZE, SIZE,  (int) shadowLocation.getX() - SIZE_HALF, (int) (shadowLocation.getY()) - SIZE_HALF, 0x000000);

        screen.drawSegment(Asset.SNOWBALL,  animationFrame * SIZE, 0, SIZE, SIZE,  (int) location.getX() - SIZE_HALF, (int) (location.getY()) - SIZE_HALF);
    }
}
