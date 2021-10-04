package dev.cobblesword.penguinforest.entity;

import dev.cobblesword.penguinforest.entity.behaviour.IClickableEntity;
import dev.cobblesword.penguinforest.entity.behaviour.IHoverableEntity;
import dev.cobblesword.penguinforest.fx.Bitmap;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class EntityManager
{
    private List<Entity> entities = new ArrayList<>();

    public EntityManager() {}

    public void registerEntity(Entity entity)
    {
        this.entities.add(entity);
    }

    public List<Entity> getEntities()
    {
        return entities;
    }

    public void removeEntity(Entity entity)
    {
        this.entities.remove(entity);
    }

    public void update(int tick)
    {
        Iterator<Entity> iterator = this.entities.iterator();
        while (iterator.hasNext())
        {
            Entity entity = iterator.next();
            if(entity.removed)
            {
                iterator.remove();
                continue;
            }

            entity.update(tick);
        }
    }

    public void render(Bitmap screen)
    {
        for (int layerIndex = 0; layerIndex < 10; layerIndex++)
        {
            for (Entity entity : this.entities)
                {
                    if (layerIndex == entity.getLayer())
                        {
                            if (entity.isVisible())
                                {
                                    entity.render(screen);
                                }
                        }
                }
        }
    }

    public boolean handleClicks(int mouseX, int mouseY, boolean left, boolean right)
    {
        for (Entity entity : this.entities)
        {
            if(entity instanceof IClickableEntity)
            {
                if(Math.abs(entity.location.getX() - mouseX) < 10 && Math.abs(entity.location.getY() - mouseY) < 10)
                {
                    return ((IClickableEntity) entity).onClick();
                }
            }
        }

        return false;
    }

    public boolean handleHover(int mouseX, int mouseY)
    {
        for (Entity entity : this.entities) {
            if(entity instanceof IHoverableEntity)
            {
                if(Math.abs(entity.location.getX() - mouseX) < 10 && Math.abs(entity.location.getY() - mouseY) < 10)
                {
                    return ((IHoverableEntity) entity).onHover();
                }
            }
        }

        return true;
    }
}
