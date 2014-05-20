package com.mygdx.game;

import java.io.Serializable;
import java.util.Hashtable;

public class LevelInfo implements Serializable
{
    //Сумки, найду, убью.
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

