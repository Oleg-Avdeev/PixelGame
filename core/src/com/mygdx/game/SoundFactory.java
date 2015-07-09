package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

public class SoundFactory {

    public static Music Main = null; //Gdx.audio.newMusic(Gdx.files.internal("Main.mp3"));
    public static Music Menu = null; //Gdx.audio.newMusic(Gdx.files.internal("Menu.mp3"));
    public static Sound Trigger = null; //Gdx.audio.newSound(Gdx.files.internal("trigger.mp3"));

    private static Music music = Main;

    static float Volume = 1.0f;

    public static void StartMusic(Music _music) {
        if (_music == null)
            return;

        if (music.isPlaying())
        music.stop();
        music = _music;
        music.setLooping(true);
        music.play();
    }

    public static void PlaySound(Sound sound) {
        if (sound != null)
            sound.play();
    }
}
