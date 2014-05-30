package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.GameObjects.Level;
import com.mygdx.game.GameObjects.Player;

public class PlayerController implements InputProcessor {

    Level level;
    int fingers = 0;

    public PlayerController(Level _level){
        level = _level;
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

    class TouchInfo {
        public float touchX = 0;
        public float touchY = 0;
        public boolean touched = false;
    }

    private TouchInfo touch = new TouchInfo();


    private int getSpeed(float delta){
        if (Math.abs(delta) >= 5d) {
            return (int)(5*delta/Math.abs(delta));
        }
        return 0;
    }

    public void DrawJoystick(SpriteBatch sb){
        sb.draw(level.Joystick, touch.touchX - level.Joystick.getWidth()/2, (Gdx.graphics.getHeight() - touch.touchY) - level.Joystick.getHeight()/2);
        sb.draw(level.JoystickCenter, FingerX - level.JoystickCenter.getWidth()/2, Gdx.graphics.getHeight() - FingerY - level.JoystickCenter.getHeight()/2);
    }


    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        touch.touched = true;
        touch.touchX = screenX;
        touch.touchY = screenY;
        fingers++;

        FingerX = screenX;
        FingerY = screenY;

        level.DrawJoystick = true;

        if (screenX >= Gdx.graphics.getWidth()-64 && screenY <= 64)
        PixelGame.RunLevel();

        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        touch.touched = false;
        touch.touchX = 0;
        touch.touchY = 0;
        fingers--;

        level.DrawJoystick = false;

        for(Player player : level.Players)
        {
            if (!player.Finished)
                player.Release();
        }

        return true;
    }

    int FingerX;
    int FingerY;

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
    if (fingers <= 2) {

        FingerX = screenX;
        FingerY = screenY;

        float deltaX = screenX - touch.touchX;
        float deltaY = screenY - touch.touchY;

        int shiftX = getSpeed(deltaX);
        int shiftY = getSpeed(deltaY);

        for (Player player : level.Players) {
            if (!player.Finished)
                player.MakeMovement(shiftX, shiftY);
        }
    }
        return true;
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
