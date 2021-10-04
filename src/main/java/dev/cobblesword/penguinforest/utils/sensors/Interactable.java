package dev.cobblesword.penguinforest.utils.sensors;

import dev.cobblesword.penguinforest.entity.Entity;

public interface Interactable
{
    void onStand(Entity entityStandingOn);
}
