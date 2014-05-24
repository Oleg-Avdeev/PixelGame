package com.mygdx.game.android;

import com.mygdx.game.GameObjects.LevelInfo;
import com.mygdx.game.SaveFile;

public class AndroidSaveFile implements SaveFile{

    public void SaveLevel(LevelInfo lvlInfo, int number)
    {
        SaveFileAPI.SaveLevel(lvlInfo, number);
    }

    public LevelInfo LoadLevelInfo(String name) { return SaveFileAPI.LoadLevelInfo(name);
    }
}
