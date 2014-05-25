package com.mygdx.game.Menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.mygdx.game.SoundFactory;

public class MainMenu {

    private final int leftMargin;
    private final int topMargin;
    public boolean Active;

    Button PlayLevel;

    SpriteBatch batch;
    Texture Logo;

    public MainMenu(SpriteBatch sb){
        batch = sb;
        Logo = new Texture("Logo.png");
        leftMargin = (Gdx.graphics.getWidth() - Logo.getWidth()) / 2;
        topMargin = 3 * (Gdx.graphics.getHeight() - Logo.getHeight()) / 4;


        SoundFactory.StartMusic(SoundFactory.Menu);
    }

    public void Draw()
    {
        batch.draw(Logo, leftMargin, topMargin);
    }
}
