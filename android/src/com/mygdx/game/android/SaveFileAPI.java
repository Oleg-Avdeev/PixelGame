package com.mygdx.game.android;

import android.os.Environment;

import com.mygdx.game.LevelInfo;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class SaveFileAPI {
    public static void SaveLevel(LevelInfo info, int number) {
        File sdCard = Environment.getExternalStorageDirectory();
        File dir = new File (sdCard.getAbsolutePath() + "/PixelGame/levels");
        dir.mkdirs();
        File file = new File(dir, "level-"+number+".rbi");

        try {
            FileOutputStream FOT = new FileOutputStream(file);
            ObjectOutputStream OOS = new ObjectOutputStream(FOT);
            OOS.writeObject(info);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static LevelInfo LoadLevelInfo(String name)
    {
        File sdCard = Environment.getExternalStorageDirectory();
        File dir = new File (sdCard.getAbsolutePath() + "/PixelGame/levels");
        File file = new File(dir, name);

        try {
            FileInputStream FIT = new FileInputStream(file);
            ObjectInputStream OOS = new ObjectInputStream(FIT);
            return (LevelInfo)OOS.readObject();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }
}
