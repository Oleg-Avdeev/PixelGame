package com.mygdx.game.Menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.*;
import com.mygdx.game.GameObjects.Progression;
import com.mygdx.game.PixelGame;
import com.mygdx.game.SoundFactory;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import java.util.*;
import java.util.List;

public class LevelMenu {
    SpriteBatch batch;
    List<SpriteDrawable> levelTex = new ArrayList<SpriteDrawable>();

    TextureAtlas buttons;
    Skin buttonSkin;
    Stage stage;

    public boolean Active;
    Button prev;
    Button next;
    TextButton resetBtn;
    TextButton playBtn;
    TextButton backBtn;

    Label lvlName;
    Image levelPicture;

    int curLevel = 1;

    public LevelMenu(SpriteBatch sb)
    {
        batch = sb;
        PreloadTextures();
        DefineButtons();
        stage = new Stage();
        stage.clear();
        stage.addActor(levelPicture);
        stage.addActor(prev);
        stage.addActor(next);
        stage.addActor(lvlName);
        stage.addActor(resetBtn);
        stage.addActor(playBtn);
        stage.addActor(backBtn);
    }

    private void PreloadTextures()
    {
        for (int i = 1; i <= PixelGame.MaxLevels; i++)
            levelTex.add(new SpriteDrawable(new Sprite(new Texture("Level-" + i + ".png"))));
    }

    private void DefineButtons()
    {
        buttons = new TextureAtlas("LevelButtons.pack");
        buttonSkin = new Skin();
        buttonSkin.addRegions(buttons);

        Button.ButtonStyle prevStyle = new Button.ButtonStyle();
        prevStyle.up = buttonSkin.getDrawable("prev");
        prev = new Button(prevStyle);
        prev.setPosition(100, (Gdx.graphics.getHeight() - 110) / 2);
        prev.addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if (curLevel <= 1)
                    curLevel = PixelGame.GetProgression().LevelNumber;
                else
                    curLevel--;

                ScrollToLevel(curLevel);
                return true;
            }
        });

        Button.ButtonStyle nextStyle = new Button.ButtonStyle();
        nextStyle.up = buttonSkin.getDrawable("next");
        next = new Button(nextStyle);
        next.setPosition((Gdx.graphics.getWidth() - 189), (Gdx.graphics.getHeight() - 110) / 2);
        next.addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if (curLevel >= PixelGame.GetProgression().LevelNumber)
                    curLevel = 1;
                else
                    curLevel++;

                ScrollToLevel(curLevel);
                return true;
            }
        });

        curLevel = PixelGame.GetProgression().LevelNumber;

        lvlName = new Label("Level ## of ##",
                new Skin(Gdx.files.internal("LabelStyle.pack")));
        lvlName.setPosition((Gdx.graphics.getWidth() - lvlName.getWidth()) / 2.0f,
                Gdx.graphics.getHeight() - 120.0f);

        resetBtn = new TextButton("Reset",
                new Skin(Gdx.files.internal("TextButtonStyle.pack")));
        resetBtn.setPosition((Gdx.graphics.getWidth() - resetBtn.getWidth()) - 5.0f,
               5.0f);
        resetBtn.addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                PixelGame.ResetProgress();
                curLevel = 1;
                ScrollToLevel(curLevel);
                return true;
            }
        });

        playBtn = new TextButton("Play",
                new Skin(Gdx.files.internal("TextButtonStyle.pack")));
        playBtn.setPosition((Gdx.graphics.getWidth() - playBtn.getWidth()) / 2.0f,
                5.0f);
        playBtn.addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                PixelGame.SetState(PixelGame.ScreenState.Game);
                PixelGame.RunLevel(curLevel);
                return true;
            }
        });

        backBtn = new TextButton("Back",
                new Skin(Gdx.files.internal("TextButtonStyle.pack")));
        backBtn.setPosition(5.0f, 5.0f);
        backBtn.addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                PixelGame.SetState(PixelGame.ScreenState.MainMenu);
                return true;
            }
        });
        
        levelPicture = new Image();
        ScrollToLevel(curLevel);
    }

    private void ScrollToLevel(int number)
    {
        number--;
        levelPicture.setDrawable(levelTex.get(number));
        levelPicture.setSize(levelTex.get(number).getSprite().getTexture().getWidth() * PixelGame.PixelScale,
                levelTex.get(number).getSprite().getTexture().getHeight() * PixelGame.PixelScale);

        levelPicture.setPosition((Gdx.graphics.getWidth() - levelPicture.getWidth()) / 2.0f,
                (Gdx.graphics.getHeight() - levelPicture.getHeight()) / 2.0f);
        number++;
        lvlName.setText("Level " + String.format("%2s", number) + " of "
                + Integer.toString(PixelGame.MaxLevels));
        lvlName.setPosition((Gdx.graphics.getWidth() - lvlName.getWidth()) / 2.0f,
                Gdx.graphics.getHeight() - 120.0f);
    }

    public void Draw()
    {
        stage.act();
        stage.draw();

        //Exit.draw(batch, 1.0f);
        //batch.draw(Logo, leftMargin, topMargin);
    }


    public void EnableInput()
    {
        Gdx.input.setInputProcessor(stage);
    }
}
