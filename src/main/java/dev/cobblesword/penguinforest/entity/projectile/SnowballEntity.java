package dev.cobblesword.penguinforest.entity.projectile;

import dev.cobblesword.penguinforest.assets.Asset;
import dev.cobblesword.penguinforest.fx.Bitmap;
import dev.cobblesword.penguinforest.utils.Location;

public class SnowballEntity extends ProjectileEntity
{
    public static int SIZE = 40;
    public static int SIZE_HALF = SIZE / 2;
    private int animationFrame = 0;
    public Location startLocation;
    public Location targetLocation;
    public boolean hitGround = false;

    public SnowballEntity(Location targetLocation)
    {
        this.targetLocation = targetLocation;
    }

    @Override
    public void spawn(double posX, double posY) {
        super.spawn(posX, posY);

        this.startLocation = new Location(posX, posY);
    }

    @Override
    public void update(int tick)
    {
        super.update(tick);

        if(livingTicks > 30 * 20)
        {
            this.removed = true;
            return;
        }

        int peakX = (int) ((this.startLocation.distanceX(this.targetLocation) / 2));

        for (int i = 0; i < 8; i++) {
            if(!hitGround) {
                int diffTilPeak = (int) Math.abs(this.location.distanceX(targetLocation));

                double diffX = location.distanceX(targetLocation);
                double diffY = location.distanceY(targetLocation);

                if (Math.abs(diffX) > 2) {
                    this.location.setX(this.location.getX() + 2 * (diffX / Math.abs(diffX)));
                }

                if (Math.abs(peakX) < Math.abs(diffTilPeak)) {
                    this.location.setY(this.location.getY() - 2);
                } else {
                    this.location.setY(this.location.getY() + 2);
                }
            }

            // when hit ground
            if(Math.abs(targetLocation.distanceSqured(this.location)) < 4)
            {
                if(!hitGround)
                {
                    livingTicks = 0;
                    hitGround = true;
                }

                if(tick % 5 == 0 && animationFrame <= 2)
                {
                    animationFrame++;
                }
            }
        }
    }

    @Override
    public void render(Bitmap screen)
    {
        screen.drawSegment(Asset.SNOWBALL,  animationFrame * SIZE, 0, SIZE, SIZE,  (int) location.getX() - SIZE_HALF, (int) (location.getY()) - SIZE_HALF);
    }
}
