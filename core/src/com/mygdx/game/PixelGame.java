package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.GameObjects.Level;
import com.mygdx.game.GameObjects.LevelInfo;


public class PixelGame extends ApplicationAdapter {
	static SpriteBatch batch;
    static SaveFile savefile;

    static int LevelNumber = 0;
    static Level MainLevel;

    public static int PixelScale = 10;


    public PixelGame(SaveFile saveFile){
        savefile = saveFile;
    }

    @Override
	public void create () {
		batch = new SpriteBatch();
        SoundFactory.StartMusic();
        NextLevelPlease();
    }

	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
            if (Fade) {
                overlayT += 0.01f;
                overlay.lerp(new Color(Color.BLACK), overlayT);
                if (overlayT >= 1.0f) {
                    Fade = false;
                    overlayT = 0.0f;
                    overlay = new Color(1,1,1,1);
                    NextLevelPlease();
                }
            }
        batch.setColor(overlay);
        batch.enableBlending();
        batch.begin();
            MainLevel.Draw();
		batch.end();
	}

    public static void NextLevelPlease()
    {
        LevelNumber++;
        LevelParser.ParseNextLevel(LevelNumber, savefile);
        RunLevel();
    }

    public static boolean Fade;
    private float overlayT = 0.0f;
    private Color overlay = new Color(Color.WHITE);

    public static void RunLevel()
    {
        LevelInfo lvlInfo = savefile.LoadLevelInfo("level-"+LevelNumber+".rbi");
        int levelPosX = Gdx.graphics.getWidth();
        int levelPosY = Gdx.graphics.getHeight();
        MainLevel = new Level(LevelNumber, levelPosX, levelPosY, lvlInfo, batch);
    }
}
