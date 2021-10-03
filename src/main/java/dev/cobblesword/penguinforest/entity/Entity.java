package dev.cobblesword.penguinforest.entity;

import dev.cobblesword.penguinforest.fx.Bitmap;
import dev.cobblesword.penguinforest.fx.Render;
import dev.cobblesword.penguinforest.utils.Location;
import jdk.nashorn.internal.objects.annotations.Getter;

public abstract class Entity
{
    protected boolean removed = false;
    protected boolean spawned = false;
    protected Location location, lastLocation;
    protected long livingTicks = 0;
    private int layer = 5;
    protected boolean visible = true;

    public Entity()
    {
    }

    public void spawn(double posX, double posY)
    {
        this.location = new Location(posX, posY);
        this.lastLocation = new Location(posX, posY);
        this.spawned = true;
    }

    public abstract void render(Bitmap screen);

    public void update(int tick)
    {
        if(removed)
        {
            return;
        }

        SpawnCheck();

        this.lastLocation.setLocation(location);
//        this.location.setX(1);
//        this.location.setY(1);
        livingTicks++;
    }

    protected void SpawnCheck()
    {
        if(!this.spawned)
        {
            throw new RuntimeException("Entity is not spawned");
        }
    }

    public int getLayer()
    {
        return layer;
    }

    protected void setLayer(int layer)
    {
        this.layer = layer;
    }

    public boolean isVisible()
    {
        return visible;
    }
}
