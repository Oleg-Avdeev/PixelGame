package com.mygdx.game.GameObjects;

import com.mygdx.game.Point;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

public class LevelInfo implements Serializable
{
    int[][] MapArray;
    public List<Point> Starts = new ArrayList<Point>();
    Hashtable<Point,Point> TriggerTarget;

    public LevelInfo(int[][] map, List<Point> starts, Hashtable<Point, Point> triggerTarget)
    {
        MapArray = map;
        Starts = starts;
        TriggerTarget = triggerTarget;
    }
}

