package com.mygdx.game.GameObjects;

import com.mygdx.game.Point;

import java.io.Serializable;
import java.util.Hashtable;

public class LevelInfo implements Serializable
{
    int[][] MapArray;
    int StartX, StartY;
    Hashtable<Point,Point> TriggerTarget;

    public LevelInfo(int[][] map, int startX, int startY, Hashtable<Point, Point> triggerTarget)
    {
        MapArray = map;
        StartX = startX;
        StartY = startY;
        TriggerTarget = triggerTarget;
    }
}

