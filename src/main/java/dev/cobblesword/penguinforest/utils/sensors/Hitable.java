package dev.cobblesword.penguinforest.utils.sensors;

import dev.cobblesword.penguinforest.entity.projectile.ProjectileEntity;

public interface Hitable
{
    public void onHit(ProjectileEntity projectile);
}
