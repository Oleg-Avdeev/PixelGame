package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;

public class SoundFactory {
    static Music Main = Gdx.audio.newMusic(Gdx.files.internal("Main.mp3"));
    static float Volume = 1.0f;

    public static void StartMusic() {
        Main.play();
    }
}
