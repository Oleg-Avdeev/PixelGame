package com.mygdx.game;


public interface SaveFile
{
    public void SaveLevel(LevelInfo lvlInfo, int number);
    public LevelInfo LoadLevelInfo(String name);
}
