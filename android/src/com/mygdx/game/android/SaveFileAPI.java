package com.mygdx.game.android;

import android.os.Environment;
import android.util.Log;

import com.mygdx.game.GameObjects.LevelInfo;
import com.mygdx.game.GameObjects.Progression;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class SaveFileAPI {

    static File sdCard = Environment.getExternalStorageDirectory();

    public static void SaveLevel(LevelInfo info, int number) {
        if (isExternalStorageWritable()) {

            File dir = new File(sdCard.getAbsolutePath() + "/PixelGame/levels");
            dir.mkdirs();
            File file = new File(dir, "level-" + number + ".rbi");
            SaveObject(info, file);
        }
    }

    public static void SaveProgression(Progression progress)
    {
        if (isExternalStorageWritable()) {
            File dir = new File(sdCard.getAbsolutePath() + "/PixelGame");
            dir.mkdirs();
            File file = new File(dir, "progress.prg");
            SaveObject(progress, file);
        }
    }

    public static LevelInfo LoadLevelInfo(String name) {
        if (isExternalStorageWritable()) {
            File dir = new File(sdCard.getAbsolutePath() + "/PixelGame/levels");
            File file = new File(dir, name);
            return LoadObject(file);
        }
            return null;
    }

    public static Progression LoadProgression()
    {
        if (isExternalStorageWritable()) {
            File dir = new File(sdCard.getAbsolutePath() + "/PixelGame");
            File file = new File(dir, "progress.prg");
            if (!file.exists()) {
                Log.v("PixelGame", "No save file found, creating a new one");
                Progression newGame = new Progression();
                newGame.LevelNumber = 1;
                SaveProgression(newGame);
            }
            return LoadObject(file);
        }
        return null;
    }

    static <T> void SaveObject(T object, File file)
    {
        try {
            FileOutputStream FOT = new FileOutputStream(file);
            ObjectOutputStream OOS = new ObjectOutputStream(FOT);
            OOS.writeObject(object);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static <T> T LoadObject(File file)
    {
        try {
            FileInputStream FIT = new FileInputStream(file);
            ObjectInputStream OOS = new ObjectInputStream(FIT);
            return (T) OOS.readObject();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED.equals(state);
    }
}
