package com.mygdx.game;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;


public class Player implements InputProcessor{

    Texture PlayerTexture;
    SpriteBatch Sb;
    Level level;

    public boolean Finished;
    private int x, y;
    private int Xshift = 0, Yshift = 0;

    class TouchInfo {
        public float touchX = 0;
        public float touchY = 0;
        public boolean touched = false;
    }

    private TouchInfo touch = new TouchInfo();

    public Player(SpriteBatch sb, Level lev)
    {
        PlayerTexture = new Texture("Player.png");
        Sb = sb;
        level = lev;
        UpdatePlayer();
    }

    public void UpdatePlayer()
    {
        Finished = false;
        x = level.StartX;
        y = level.level.getHeight() - 1 - level.StartY;
    }

    public void Draw()
    {
        if (!Finished)
        Sb.draw(PlayerTexture,
                level.leftMargin + Xshift + (x * PixelGame.PixelScale),
                level.topMargin + Yshift + (y * PixelGame.PixelScale),
                (PlayerTexture.getWidth() * PixelGame.PixelScale),
                (PlayerTexture.getHeight() * PixelGame.PixelScale)
        );
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        touch.touched = true;
        touch.touchX = screenX;
        touch.touchY = screenY;
        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        touch.touched = false;
        touch.touchX = 0;
        touch.touchY = 0;

        if (Math.abs(Xshift) > 5)
        {x += Math.abs(Xshift)/Xshift;
        Xshift = 0;}
        else if (Math.abs(Xshift) <= 5)
        {Xshift = 0;}

        if (Yshift > 5)
        {y -= Math.abs(Yshift)/Yshift;
        Yshift = 0;}
        else if (Yshift <= 5)
        {Yshift = 0;}
        return true;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {

        boolean Moved = false;

        float deltaX = screenX - touch.touchX;
        float deltaY = screenY - touch.touchY;

        int shiftX = getSpeed(deltaX);
        int shiftY = getSpeed(deltaY);

        if (shiftX != 0) {
            Point point = new Point(x + shiftX / Math.abs(shiftX),y);
            if (level.LevelMap[x + shiftX / Math.abs(shiftX)][y] == 1) {
                MakeXmove(shiftX); Moved = true;
            } else if (level.LevelMap[x + shiftX / Math.abs(shiftX)][y] == 2) {
                MakeXmove(shiftX);
                Moved = true;
                Finished = true;
            } else if (level.TriggerTarget.containsKey(point))
            {
                MakeXmove(shiftX);
                Moved = true;
                ActivateTrigger(point);
                level.TriggerTarget.remove(point);
            }
        }

        if (shiftY != 0) {
            Point point = new Point(x,y - shiftY / Math.abs(shiftY));
            if (level.LevelMap[x][y - shiftY / Math.abs(shiftY)] == 1) {
                if (Moved)
                {Xshift = 0;}
                MakeYmove(shiftY);
            }
            if (level.LevelMap[x][y - shiftY / Math.abs(shiftY)] == 2) {
                MakeYmove(shiftY);
                Finished = true;
            }
            else if (level.TriggerTarget.containsKey(point))
            {
                if (Moved)
                {Xshift = 0;}
                MakeYmove(shiftY);
                ActivateTrigger(new Point(x,y - shiftY / Math.abs(shiftY)));
                level.TriggerTarget.remove(point);
            }
        }
        return true;
    }

    private void ActivateTrigger(Point point) {
        Point target = level.TriggerTarget.get(point);
        level.LevelMap[target.X][target.Y] = 1;
        level.LevelMap[point.X][point.Y] = 1;
        level.SetPixel(target.X,target.Y, level.Road);
        level.SetPixel(point.X,point.Y, level.Road);
    }

    private int getSpeed(float delta){
        if (Math.abs(delta) >= 5d) {
            return (int)(5*delta/Math.abs(delta));
        }
        return 0;
    }

    private void MakeXmove(int shift)
    {
        if (Math.abs(Xshift) < PixelGame.PixelScale) {
            Xshift += shift;
        } else {
            Xshift = 0;
            x += shift / Math.abs(shift);
        }
    }

    private void MakeYmove(int shift)
    {
        if (Math.abs(Yshift) < PixelGame.PixelScale) {
            Yshift -= shift;
        } else {
            Yshift = 0;
            y -= shift / Math.abs(shift);
        }
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
