package com.mygdx.game.GameObjects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.PixelGame;
import com.mygdx.game.Point;


public class Player {

    Texture PlayerTexture;
    SpriteBatch Sb;
    Level level;

    private int x, y;
    private int Xshift = 0, Yshift = 0;

    public boolean Finished = false;

    public Player(SpriteBatch sb, Level lev, Point start)
    {
        PlayerTexture = new Texture("Player.png");
        Sb = sb;
        level = lev;
        x = start.X;
        y = level.level.getHeight() - 1 - start.Y;
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

    public void Release() {
        if (Math.abs(Xshift) > 5) {
            x += Math.abs(Xshift) / Xshift;
            Xshift = 0;
        } else if (Math.abs(Xshift) <= 5) {
            Xshift = 0;
        }

        if (Yshift > 5) {
            y += Math.abs(Yshift) / Yshift;
            Yshift = 0;
        } else if (Yshift <= 5) {
            Yshift = 0;
        }
    }

    public void MakeMovement(int shiftX, int shiftY) {

        if (shiftX != 0 && Yshift == 0) {

            if (level.LevelMap[x + shiftX / Math.abs(shiftX)][y] == 1) {
                MakeXmove(shiftX);
            } else if (level.LevelMap[x + shiftX / Math.abs(shiftX)][y] == 2) {
                MakeXmove(shiftX);
                Finish();
            }
        }

        if (shiftY != 0 && Xshift == 0) {
            if (level.LevelMap[x][y - shiftY / Math.abs(shiftY)] == 1) {
                MakeYmove(shiftY);
            }
            if (level.LevelMap[x][y - shiftY / Math.abs(shiftY)] == 2) {
                MakeYmove(shiftY);
                Finish();
            }
        }

        Point point = new Point(x, y);
        if (level.TriggerTarget.containsKey(point)) {
            ActivateTrigger(point);
            level.TriggerTarget.remove(point);

        }
    }

    private void ActivateTrigger(Point point) {
        Point target = level.TriggerTarget.get(point);
        level.LevelMap[target.X][target.Y] = 1;
        level.LevelMap[point.X][point.Y] = 1;
        level.SetPixel(target.X,target.Y, level.Road);
        level.SetPixel(point.X,point.Y, level.Road);
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

    private void Finish()
    {
        level.FinishedPlayers++;
        Finished = true;
    }

}
