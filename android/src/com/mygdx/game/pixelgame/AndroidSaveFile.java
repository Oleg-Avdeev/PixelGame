package com.mygdx.game.pixelgame;

import com.mygdx.game.GameObjects.LevelInfo;
import com.mygdx.game.GameObjects.Progression;
import com.mygdx.game.SaveFile;

public class AndroidSaveFile implements SaveFile {

    public void SaveLevel(LevelInfo lvlInfo, int number) {
        SaveFileAPI.SaveLevel(lvlInfo, number);
    }

    public void SaveProgression(Progression progress) {
        SaveFileAPI.SaveProgression(progress);
    }

    public LevelInfo LoadLevelInfo(String name) {
        return SaveFileAPI.LoadLevelInfo(name);
    }

    public Progression LoadProgression() {
        return SaveFileAPI.LoadProgression();
    }
}

