package com.mygdx.game.android;

import com.mygdx.game.LevelInfo;
import com.mygdx.game.SaveFile;

public class AndroidSavesPeople implements SaveFile{
    private final SaveFileAPI service;

    public AndroidSavesPeople()
    {
        service = new SaveFileAPI();
    }

    public void SaveLevel(LevelInfo lvlInfo, int number)
    {
        service.SaveLevel(lvlInfo, number);
    }

    public LevelInfo LoadLevelInfo(String name) { return service.LoadLevelInfo(name);
    }
}
