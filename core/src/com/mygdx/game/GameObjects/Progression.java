package com.mygdx.game.GameObjects;

import com.mygdx.game.Point;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Progression implements Serializable{

    public int LevelNumber;
    //Level number, player number, move
    public List<List<List<Point>>> Moves = new ArrayList<List<List<Point>>>();
    public Progression(){

    }

}
