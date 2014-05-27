package com.mygdx.game;


import com.mygdx.game.GameObjects.LevelInfo;
import com.mygdx.game.GameObjects.Progression;

public interface SaveFile
{
    public void SaveLevel(LevelInfo lvlInfo, int number);
    public void SaveProgression(Progression progress);
    public Progression LoadProgression();
    public LevelInfo LoadLevelInfo(String name);
}
