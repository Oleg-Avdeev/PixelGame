package com.mygdx.game;


import com.mygdx.game.GameObjects.LevelInfo;

public interface SaveFile
{
    public void SaveLevel(LevelInfo lvlInfo, int number);
    public LevelInfo LoadLevelInfo(String name);
}
