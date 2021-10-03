package dev.cobblesword.penguinforest.entity.living;

import dev.cobblesword.penguinforest.Main;
import dev.cobblesword.penguinforest.PneguinForestClient;
import dev.cobblesword.penguinforest.assets.Asset;
import dev.cobblesword.penguinforest.entity.behaviour.IClickableEntity;
import dev.cobblesword.penguinforest.entity.projectile.SnowballEntity;
import dev.cobblesword.penguinforest.fx.Bitmap;
import dev.cobblesword.penguinforest.utils.Location;
import dev.cobblesword.penguinforest.utils.MathUtil;

import javax.swing.*;

public class PenguinEntity extends LivingEntity implements IClickableEntity
{
    protected Location targetLocation;
    protected int animationFrame = 0;
    public boolean walking = false;
    private Location originalWaddlingLocation;
    private float waddleCounter = 0.0f;

    public PenguinEntity() {
        super();
    }

    private void handleXMovement(double duration)
    {
        double distToTravelX = originalWaddlingLocation.distanceX(targetLocation);
        double startingX = originalWaddlingLocation.getX();

        double newX = MathUtil.easeLinear(waddleCounter, startingX, distToTravelX, duration);

        if(waddleCounter <= duration)
        {
            this.location.setX(newX);
            this.walking = true;
        }
    }

    private void handleYMovement(double duration) {
        double distToTravelY = originalWaddlingLocation.distanceY(targetLocation);
        double startingY = originalWaddlingLocation.getY();

        double newY = MathUtil.easeLinear(waddleCounter, startingY, distToTravelY, duration);

        if(waddleCounter <= duration)
        {
            this.location.setY(newY);
            this.walking = true;
        }
    }

    @Override
    public void update(int tick) {
        this.walking = false;
        super.update(tick);

        if(targetLocation == null) return;

        double dist = originalWaddlingLocation.distance(targetLocation);
        double durationPerUnit = 0.08;
        double duration = Math.abs(dist) * durationPerUnit;

        handleXMovement(duration);
        handleYMovement(duration);

        if(walking)
        {
            animationFrame = ((tick / 3) % 2) + 1;
            waddleCounter += 0.4;
        }
        else
        {
            animationFrame = 0;
        }
    }

    @Override
    public void render(Bitmap screen)
    {
        screen.drawSegment(Asset.PENGUIN,  animationFrame * 80, 0, 80, 80,  (int) location.getX() - 40, (int) (location.getY()) - 40);
    }

    @Override
    public void spawn(double posX, double posY) {
        super.spawn(posX, posY);

        this.originalWaddlingLocation = this.location.clone();
    }

    public void onClicked(int mouseX, int mouseY, boolean leftClick, boolean rightClick)
    {
        // Movement
        if(leftClick)
        {
            if((Asset.DESERT_COLLISION.pixels[mouseX + mouseY * Main.WIDTH] & 0xFFFFFF) == 0xFFFFFF)
            {
                return;
            }

            SpawnCheck();

            if(this.targetLocation == null)
            {
                this.targetLocation = new Location(mouseX, mouseY);
                return;
            }

            this.originalWaddlingLocation = this.location.clone();
            this.waddleCounter = 0;

            this.targetLocation.setX(mouseX);
            this.targetLocation.setY(mouseY);
        }

        // Snow ball
        if(rightClick)
        {
            SnowballEntity snowball = new SnowballEntity(new Location(mouseX, mouseY));
            snowball.spawn(location.getX(), location.getY());
            PneguinForestClient.entityManager.registerEntity(snowball);
        }
    }

    @Override
    public void onClick()
    {
        JOptionPane.showMessageDialog(null, "You clicked a penguin");
    }
}
