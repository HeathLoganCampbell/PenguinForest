package dev.cobblesword.penguinforest.utils;

public class MathUtil
{
    /*
    t = 0 - Animation is just started. Zero time has passed [0 to 1]
    b = 200 - The starting position of the objects x-coordinate is 200
    c = 300 - The object has to move 300 to the right, ending at 500
    d = 1 - The object has one second to perform this motion from 200 to 500
     */
    public static double easeLinear(float t, double b, double c, double d)
    {
        return c * t / d + b;
    }
}
