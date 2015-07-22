package com.mygdx.game.GameObjects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.PixelGame;
import com.mygdx.game.Point;
import com.mygdx.game.SoundFactory;

import java.util.ArrayList;
import java.util.List;


public class Player {

    Texture PlayerTexture;
    Texture PhantomTexture;
    SpriteBatch Sb;
    Level level;

    private int x, y;
    private int Xshift = 0, Yshift = 0;

    private int lastXshift = 0, lastYshift = 0;

    public boolean Finished = false;

    //Phantom-related
    private List<Point> moves = new ArrayList<Point>();
    private List<Point> newMoves = new ArrayList<Point>();

    private int phantomStep, phantomDelta;

    public Player(SpriteBatch sb, Level lev, Point start)
    {
        PlayerTexture = new Texture("Player.png");
        PhantomTexture = new Texture("Phantom.png");
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

        if (PixelGame.GetCurrentLevelNumber() != PixelGame.GetMaxLevelNumber())
        {
            //Phantom code
            if (moves != null)
                if (moves.size() > 0)
                {
                    int pX = moves.get(phantomStep).X;
                    int pY = moves.get(phantomStep).Y;

                    int hDelta = moves.get(phantomStep + 1).X - pX;
                    int vDelta = moves.get(phantomStep + 1).Y - pY;

                    Sb.draw(PhantomTexture,
                            level.leftMargin + hDelta * phantomDelta + pX * PixelGame.PixelScale,
                            level.topMargin + vDelta * phantomDelta + pY * PixelGame.PixelScale,
                            PhantomTexture.getWidth() * PixelGame.PixelScale,
                            PhantomTexture.getHeight() * PixelGame.PixelScale);

                    //Progress to next move
                    phantomDelta += 5;
                    if (phantomDelta >= PixelGame.PixelScale)
                    {
                        phantomDelta = 0; phantomStep += 1;
                        if (phantomStep >= moves.size() - 2)
                            phantomStep = 0;
                    }
                }
        }
    }

    public void Release() {
        if (Math.abs(Xshift) > 20) {
            x += Math.abs(Xshift) / Xshift;
            Xshift = 0;
        } else if (Math.abs(Xshift) <= 20) {
            Xshift = 0;
        }

        if (Yshift > 20) {
            y += Math.abs(Yshift) / Yshift;
            Yshift = 0;
        } else if (Yshift <= 20) {
            Yshift = 0;
        }
        lastXshift = lastYshift = 0;

    }

    public void MakeMovement(int shiftX, int shiftY) {
        lastXshift = shiftX;
        lastYshift = shiftY;

        if (shiftX != 0 && Yshift == 0) {

            if (level.LevelMap[x + shiftX / Math.abs(shiftX)][y] == 1) {
                MakeXmove(shiftX);
            } else if (level.LevelMap[x + shiftX / Math.abs(shiftX)][y] == 2) {
                MakeXmove(shiftX);
                Finish();
            } else if (level.LevelMap[x + shiftX / Math.abs(shiftX)][y] == 3) {
                MakeXmove(shiftX);
            }
        }

        if (shiftY != 0 && Xshift == 0) {
            if (level.LevelMap[x][y - shiftY / Math.abs(shiftY)] == 1) {
                MakeYmove(shiftY);
            }
            if (level.LevelMap[x][y - shiftY / Math.abs(shiftY)] == 2) {
                MakeYmove(shiftY);
                Finish();
            } else if (level.LevelMap[x][y - shiftY / Math.abs(shiftY)] == 3) {
                MakeYmove(shiftY);
            }
        }

        if (level.LevelMap[x][y] == 3) {
            level.LevelMap[x][y] = 0;
            level.SetPixel(x, y, Level.Block);
        }

        Point point = new Point(x, y);
        if (level.TriggerTarget.containsKey(point)) {
            ActivateTrigger(point);
            level.TriggerTarget.remove(point);

        }
    }

    private void ActivateTrigger(Point point) {
        Point target = level.TriggerTarget.get(point);
        Point bulb = level.Bulbs.get(point);
        SoundFactory.PlaySound(SoundFactory.Trigger);

        level.LevelMap[target.X][target.Y] = 1;

        level.SetPixel(target.X,target.Y, Level.Road);
        level.SetPixel(point.X,point.Y, Level.Road);
        if (bulb != null)
        level.SetPixel(bulb.X, bulb.Y, Level.Active);
    }


    private void MakeXmove(int shift)
    {
        if (Math.abs(Xshift) < PixelGame.PixelScale) {
            Xshift += shift;
        } else {
            Xshift = 0;
            x += shift / Math.abs(shift);
            newMoves.add(new Point(x, y));
        }
    }

    private void MakeYmove(int shift)
    {
        if (Math.abs(Yshift) < PixelGame.PixelScale) {
            Yshift -= shift;
        } else {
            Yshift = 0;
            y -= shift / Math.abs(shift);
            newMoves.add(new Point(x, y));
        }
    }

    private void Finish()
    {
        level.FinishedPlayers++;
        Finished = true;
        moves = newMoves; //copy new phantom info
    }

    public void RepeatLastMovement()
    {
        if (Finished)
            return;

        if (lastXshift != 0 || lastYshift != 0)
            MakeMovement(lastXshift, lastYshift);
    }

    public List<Point> GetMoves()
    {
        return new ArrayList<Point>(moves);
    }

    public void SetPhantomData(List<Point> savedMoves)
    {
        moves = savedMoves;
    }
}
