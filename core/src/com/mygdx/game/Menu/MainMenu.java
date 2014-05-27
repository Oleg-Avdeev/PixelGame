package com.mygdx.game.Menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.mygdx.game.SoundFactory;



public class MainMenu {

    private final int leftMargin;
    private final int topMargin;
    public boolean Active;

    TextureAtlas buttons;
    Skin buttonSkin;
    Stage stage;

    Button PlayLevel;
    Button Levels;
    Button Exit;

    SpriteBatch batch;
    Texture Logo;

    public MainMenu(SpriteBatch sb){
        batch = sb;
        Logo = new Texture("Logo.png");
        Image logo = new Image(Logo);
        leftMargin = (Gdx.graphics.getWidth() - Logo.getWidth()) / 2;
        topMargin = 3 * (Gdx.graphics.getHeight() - Logo.getHeight()) / 4;

        logo.setPosition(leftMargin, topMargin);

        DefineButtons();
        stage = new Stage();
        stage.clear();
        stage.addActor(logo);
        stage.addActor(PlayLevel);
        stage.addActor(Exit);

        Gdx.input.setInputProcessor(stage);

        SoundFactory.StartMusic(SoundFactory.Menu);
    }

    private void DefineButtons() {
        buttons = new TextureAtlas("Buttons.pack");
        buttonSkin = new Skin();
        buttonSkin.addRegions(buttons);

        Button.ButtonStyle exitStyle = new Button.ButtonStyle();
        exitStyle.up = buttonSkin.getDrawable("exit");
        exitStyle.down = buttonSkin.getDrawable("exit_pressed");
        Exit = new Button(exitStyle);
        Exit.setPosition((Gdx.graphics.getWidth() - 143)/ 2, topMargin - 300);
        Exit.addListener(new InputListener() {
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                Gdx.app.exit();
            }
        });

        Button.ButtonStyle playStyle = new Button.ButtonStyle();
        playStyle.up = buttonSkin.getDrawable("continue");
        playStyle.down = buttonSkin.getDrawable("continue_pressed");
        PlayLevel = new Button(playStyle);
        PlayLevel.setPosition((Gdx.graphics.getWidth() - 349)/ 2, topMargin - 150);
        PlayLevel.addListener(new InputListener() {
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                Active = false;
            }
        });
    }

    public void Draw()
    {
        stage.act();
        stage.draw();
        //Exit.draw(batch, 1.0f);
        batch.draw(Logo, leftMargin, topMargin);
    }
}
