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
import com.mygdx.game.Menu.MainMenu;


public class PixelGame extends ApplicationAdapter {
	static SpriteBatch batch;
    static SaveFile savefile;

    static int LevelNumber = 0;
    static Level MainLevel;
    static Progression progress;

    public static int PixelScale = 10;

    long timeStep = 16; //time step between frames in milliseconds

    MainMenu mainMenu;
    long lastFrameTime;

    public PixelGame(SaveFile saveFile){
        savefile = saveFile;
    }

    @Override
	public void create () {
		batch = new SpriteBatch();
        SoundFactory.StartMusic(SoundFactory.Main);
        mainMenu = new MainMenu(batch);
        mainMenu.Active = true;
        progress = new Progression();
    }

	@Override
	public void render () {
        lastFrameTime = System.currentTimeMillis();

		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if (!mainMenu.Active) {
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
            for(Player player : MainLevel.Players)
            {
                if (!player.Finished)
                    player.RepeatLastMovement();
            }

            batch.end();
        }
        else {
            batch.begin();
            mainMenu.Draw();
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

    public static void NextLevelPlease()
    {
        try {
            LevelNumber++;
            LevelParser.ParseNextLevel(LevelNumber, savefile);
            RunLevel();
        }
        catch (Exception e)
        {
            LevelNumber--;
            LevelParser.ParseNextLevel(LevelNumber, savefile);
            RunLevel();
        }
    }

    public static boolean Fade;
    private float overlayT = 0.0f;
    private Color overlay = new Color(Color.WHITE);

    public static void RunLevel()
    {
        progress.LevelNumber = LevelNumber;
        savefile.SaveProgression(progress);
        LevelInfo lvlInfo = savefile.LoadLevelInfo("level-"+LevelNumber+".rbi");
        int levelPosX = Gdx.graphics.getWidth();
        int levelPosY = Gdx.graphics.getHeight();
        MainLevel = new Level(LevelNumber, levelPosX, levelPosY, lvlInfo, batch);
    }

    public static Progression GetProgression(){
        return savefile.LoadProgression();
    }

    public static void RunLevel(int number)
    {
        LevelNumber = number;
        LevelParser.ParseNextLevel(LevelNumber, savefile);
        RunLevel();
    }
}
