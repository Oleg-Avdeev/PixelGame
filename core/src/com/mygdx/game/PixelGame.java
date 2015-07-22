package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.GameObjects.Level;
import com.mygdx.game.GameObjects.LevelInfo;
import com.mygdx.game.GameObjects.Player;
import com.mygdx.game.GameObjects.Progression;
import com.mygdx.game.Menu.*;

import java.util.ArrayList;
import java.util.List;


public class PixelGame extends ApplicationAdapter {
    public enum ScreenState {
        Game, MainMenu, LevelsMenu
    }

    static SpriteBatch batch;
    static SaveFile savefile;

    static int LevelNumber;
    static int MaxLevelNumber;
    static Level MainLevel;
    static Progression progress;

    public static int PixelScale = 10;
    public static int MaxLevels = 20;

    long timeStep = 16; //time step between frames in milliseconds

    static MainMenu mainMenu;
    static LevelMenu levelMenu;

    long lastFrameTime;

    public PixelGame(SaveFile saveFile) {
        savefile = saveFile;
        MaxLevelNumber = GetProgression().LevelNumber;
    }

    @Override
    public void create() {
        batch = new SpriteBatch();
        SoundFactory.StartMusic(SoundFactory.Main);
        mainMenu = new MainMenu(batch);
        mainMenu.Active = true;
        mainMenu.EnableInput();
        levelMenu = new LevelMenu(batch);
        levelMenu.Active = false;
        progress = new Progression();
    }

    @Override
    public void render() {
        lastFrameTime = System.currentTimeMillis();

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if (!mainMenu.Active && !levelMenu.Active) {
            if (Fade) {
                overlayT += 0.01f;
                overlay.lerp(new Color(Color.BLACK), overlayT);
                if (overlayT >= 1.0f) {
                    Fade = false;
                    overlayT = 0.0f;
                    overlay = new Color(1, 1, 1, 1);
                    NextLevelPlease();
                }
            }
            batch.setColor(overlay);
            batch.enableBlending();
            batch.begin();
            MainLevel.Draw();
            for (Player player : MainLevel.Players) {
                if (!player.Finished)
                    player.RepeatLastMovement();
            }

            batch.end();
        } else if (mainMenu.Active) {
            batch.begin();
            mainMenu.Draw();
            batch.end();
        } else if (levelMenu.Active) {
            batch.begin();
            levelMenu.Draw();
            batch.end();
        }

        long delta = System.currentTimeMillis() - lastFrameTime;
        if (delta < timeStep) {
            try {
                Thread.sleep(timeStep - delta);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void NextLevelPlease() {
        try {
            List<List<Point>> newMoves = new ArrayList<List<Point>>();
            for (int i = 0; i < MainLevel.Players.size(); i++)
                newMoves.add(MainLevel.Players.get(i).GetMoves());

            Progression curProg = savefile.LoadProgression();
            if (curProg.Moves != null)
                if (curProg.Moves.size() >= LevelNumber)
                    curProg.Moves.set(LevelNumber - 1, newMoves);
                else
                    curProg.Moves.add(newMoves);
            else
            {
                curProg.Moves = new ArrayList<List<List<Point>>>();
                curProg.Moves.add(LevelNumber - 1, newMoves);
            }

            savefile.SaveProgression(curProg);

            LevelNumber++;
            LevelParser.ParseNextLevel(LevelNumber, savefile);
            RunLevel();
        } catch (Exception e) {
            LevelNumber--;
            LevelParser.ParseNextLevel(LevelNumber, savefile);
            RunLevel();
        }
    }

    public static boolean Fade;
    private float overlayT = 0.0f;
    private Color overlay = new Color(Color.WHITE);

    public static void RunLevel() {
        progress = savefile.LoadProgression();
        progress.LevelNumber = Math.max(LevelNumber, progress.LevelNumber);
        MaxLevelNumber = progress.LevelNumber;
        savefile.SaveProgression(progress);
        LevelInfo lvlInfo = savefile.LoadLevelInfo("level-" + LevelNumber + ".rbi");
        int levelPosX = Gdx.graphics.getWidth();
        int levelPosY = Gdx.graphics.getHeight();
        MainLevel = new Level(LevelNumber, levelPosX, levelPosY, lvlInfo, batch);
    }

    public static Progression GetProgression() {
        return savefile.LoadProgression();
    }

    public static void RunLevel(int number) {
        MaxLevelNumber = GetProgression().LevelNumber;
        LevelNumber = number;
        LevelParser.ParseNextLevel(LevelNumber, savefile);
        RunLevel();
    }

    //quick 'n dirty
    public static void SetState(ScreenState state) {
        switch (state) {
            case Game:
                mainMenu.Active = false;
                levelMenu.Active = false;
                //MainLevel.EnableInput();
                break;
            case MainMenu:
                mainMenu.Active = true;
                levelMenu.Active = false;
                mainMenu.EnableInput();
                break;
            case LevelsMenu:
                mainMenu.Active = false;
                levelMenu.Active = true;
                levelMenu.EnableInput();
                break;

        }
    }

    public static void ResetProgress() {
        progress.LevelNumber = 1;
        savefile.SaveProgression(progress);
    }

    public static int GetCurrentLevelNumber() {
        return LevelNumber;
    }

    public static int GetMaxLevelNumber()
    {
        return MaxLevelNumber;
    }
}
