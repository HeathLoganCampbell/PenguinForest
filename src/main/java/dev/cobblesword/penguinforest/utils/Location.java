package dev.cobblesword.penguinforest.utils;

public class Location
{
    private double x, y;

    public Location(double x, double y)
    {
        this.x = x;
        this.y = y;
    }

    public double getX()
    {
        return x;
    }

    public void setX(double x)
    {
        this.x = x;
    }

    public double getY()
    {
        return y;
    }

    public void setY(double y)
    {
        this.y = y;
    }

    public void setLocation(Location location)
    {
        this.x = location.getX();
        this.y = location.getY();
    }

    public double distanceX(Location location)
    {
        return (location.x - this.x);
    }

    public double distanceY(Location location)
    {
        return (location.y - this.y);
    }

    public double distanceSquared(Location location)
    {
        double x = this.distanceX(location);
        double y = this.distanceY(location);

        return (x * x) + (y * y);
    }

    public double distance(Location location)
    {
       return Math.sqrt(this.distanceSquared(location));
    }

    @Override
    public Location clone()
    {
        try
            {
                super.clone();
            }
        catch (CloneNotSupportedException ex)
            {
                throw new RuntimeException("Superclass messed up", ex);
            }
        return new Location(this.x, this.y);
    }
}
