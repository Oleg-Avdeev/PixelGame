package com.mygdx.game;

import com.badlogic.gdx.InputProcessor;
import com.mygdx.game.GameObjects.Level;
import com.mygdx.game.GameObjects.Player;

public class PlayerController implements InputProcessor {

    Level level;

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

        for(Player player : level.Players)
        {
            if (!player.Finished)
                player.Release();
        }

        return true;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {

        float deltaX = screenX - touch.touchX;
        float deltaY = screenY - touch.touchY;

        int shiftX = getSpeed(deltaX);
        int shiftY = getSpeed(deltaY);

        for(Player player : level.Players)
        {
            if (!player.Finished)
                player.MakeMovement(shiftX, shiftY);
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
