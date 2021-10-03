package dev.cobblesword.penguinforest.entity;

import dev.cobblesword.penguinforest.fx.Bitmap;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class EntityManager
{
    private List<Entity> entities = new ArrayList<>();

    public EntityManager()
    {

    }

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
        while (iterator.hasNext()) {
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
                if(layerIndex == entity.getLayer())
                {
                    entity.render(screen);
                }
            }
        }
    }
}
