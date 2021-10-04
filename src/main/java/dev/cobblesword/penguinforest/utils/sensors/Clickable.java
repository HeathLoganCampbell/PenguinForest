package dev.cobblesword.penguinforest.utils.sensors;

import dev.cobblesword.penguinforest.entity.Entity;
import dev.cobblesword.penguinforest.utils.ClickType;

public interface Clickable
{
    void onClick(Entity entity, ClickType clickType, int mouseX, int mouseY);
}
