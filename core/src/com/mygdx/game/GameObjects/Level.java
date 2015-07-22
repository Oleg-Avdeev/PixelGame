package com.mygdx.game.GameObjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.TextureData;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.PixelGame;
import com.mygdx.game.PlayerController;
import com.mygdx.game.Point;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

public class Level {

    Texture level;
    Texture Restart;
    int[][] LevelMap;
    public Hashtable<Point,Point> TriggerTarget = new Hashtable<Point, Point>();
    Hashtable<Point, Point> Bulbs = new Hashtable<Point, Point>();

    public static int TriggerColor = (184 << 24) | (37 << 16) | (53 << 8) | 255;
    public static int Active = (0x54 << 24) | (0xea << 16) | (0x2b << 8) | 255;
    public static int Road = (0x12 << 24) | (0x79 << 16) | (0xae << 8) | 255;
    public static int Block = (0x4f << 24) | (0x4f << 16) | (0x4f << 8) | 255;

    SpriteBatch Sb;
    public int leftMargin;
    public int topMargin;

    PlayerController Controller;
    int FinishedPlayers = 0;
    public List<Player> Players;

    public Texture Joystick = new Texture("joystick.png");
    public Texture JoystickCenter = new Texture("center.png");
    public boolean DrawJoystick= false;

    public Level (int LevelNumber, int LevelPosX, int LevelPosY, LevelInfo lvlInfo, SpriteBatch sb) {

        if (lvlInfo != null) {
            LevelMap = lvlInfo.MapArray;
            level = new Texture("Level-" + LevelNumber + ".png");

            Players = new ArrayList<Player>();
            for (Point start : lvlInfo.Starts)
            {
                Players.add(new Player(sb, this, start));
            }
            TriggerTarget = lvlInfo.TriggerTarget;
            Bulbs = lvlInfo.Bulbs;
            for(Point prime : TriggerTarget.keySet())
            {
                SetPixel(prime.X, prime.Y, TriggerColor);
            }
            for(Point point : Bulbs.values())
            {
                SetPixel(point.X, point.Y, TriggerColor);
            }
        }

        Restart = new Texture("Restart.png");

        leftMargin = (LevelPosX - level.getWidth() * PixelGame.PixelScale) / 2;
        topMargin = (LevelPosY - level.getHeight() * PixelGame.PixelScale) / 2;
        Sb = sb;


        Controller = new PlayerController(this);
        Progression curProg = PixelGame.GetProgression();
        if (curProg.Moves.size() >= PixelGame.GetCurrentLevelNumber())
            SetPhantomData(curProg.Moves.get(PixelGame.GetCurrentLevelNumber() - 1));

        EnableInput();
    }

    public void SetPhantomData(List<List<Point>> data)
    {
        for (int i = 0; i < Players.size(); i++)
            Players.get(i).SetPhantomData(data.get(i));
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
        Sb.draw(Restart, Gdx.graphics.getWidth() - 64, Gdx.graphics.getHeight() - 64);
        for(Player player : Players)
        {
            if (!player.Finished)
                player.Draw();
        }
        if (DrawJoystick)
        {
            Controller.DrawJoystick(Sb);
        }
        if (FinishedPlayers == Players.size())
            PixelGame.Fade = true;
    }

    public void EnableInput()
    {
        Gdx.input.setInputProcessor(Controller);
    }
}
