package dev.cobblesword.penguinforest.utils.sensors;

import dev.cobblesword.penguinforest.entity.projectile.ProjectileEntity;

public interface Hittable
{
    void onHit(ProjectileEntity projectile);
}
