package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.TextureData;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.Hashtable;

public class Level {

    Texture level;
    int[][] LevelMap;
    public int StartX, StartY;
    public Hashtable<Point,Point> TriggerTarget = new Hashtable<Point, Point>();

    public int TriggerColor = (184 << 24) | (37 << 16) | (53 << 8) | 255;;
    public int Road = (0x12 << 24) | (0x79 << 16) | (0xae << 8) | 255;;

    SpriteBatch Sb;
    public int leftMargin;
    public int topMargin;

    Player player;

    public Level (int LevelNumber, int LevelPosX, int LevelPosY, LevelInfo lvlInfo, SpriteBatch sb) {

        if (lvlInfo != null) {
            LevelMap = lvlInfo.MapArray;
            level = new Texture("Level-" + LevelNumber + ".png");
            StartX = lvlInfo.StartX;
            StartY = lvlInfo.StartY;
            TriggerTarget = lvlInfo.TriggerTarget;
            for(Point prime : TriggerTarget.keySet())
            {
                SetPixel(prime.X, prime.Y, TriggerColor);
            }
        }

        leftMargin = (LevelPosX - level.getWidth() * PixelGame.PixelScale) / 2;
        topMargin = (LevelPosY - level.getHeight() * PixelGame.PixelScale) / 2;
        Sb = sb;
        player = new Player(Sb, this);
        Gdx.input.setInputProcessor(player);
    }

    public void SetPixel(int x, int y, int color)
    {
        TextureData data = level.getTextureData();
        if (!data.isPrepared())
            data.prepare();
        Pixmap map = data.consumePixmap();
        map.drawPixel(x,map.getHeight() - y - 1,color);
        level = new Texture(map);
    }

    public void Draw()
    {
        Sb.draw(level, leftMargin, topMargin, level.getWidth() * PixelGame.PixelScale, level.getHeight() * PixelGame.PixelScale);
        if (!player.Finished) {
            player.Draw();
        }
        else
        {
            PixelGame.Fade = true;
        }
    }


}
