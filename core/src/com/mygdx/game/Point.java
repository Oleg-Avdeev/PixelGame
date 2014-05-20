package com.mygdx.game;

import java.io.Serializable;

public class Point implements Serializable
{

    public int X,Y;

    public Point(int x, int y)
    {
        X = x;
        Y = y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Point point = (Point) o;

        if (X != point.X) return false;
        if (Y != point.Y) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = X;
        result = 31 * result + Y;
        return result;
    }
}
