/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spaceinvaders;

import java.net.URL;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

/**
 *
 * @author michel
 */
public class Sound {
    
    Clip clip;
    URL soundURL[] = new URL[30];
    
    private static final int __SOUNDTRACK__      = 0;
    private static final int __SPACESHIP_SHOOT__ = 1;
    private static final int __ALIEN_SHOOT__     = 2;
    private static final int __SPACESHIP_HIT__   = 3;
    private static final int __ALIEN_HIT__       = 4;
    private static final int __ALIEN_MOVE_0__    = 5;
    private static final int __ALIEN_MOVE_1__    = 6;
    
    enum sounds {
        SOUNDTRACK,
        SPACESHIP_SHOOT,
        ALIEN_SHOOT,
        ALIEN_HIT,
        SPACESHIP_HIT,
        ALIEN_MOVE_0,
        ALIEN_MOVE_1;
    }
    
    private sounds sound;
    
    public Sound(){
        soundURL[__SOUNDTRACK__] = getClass().getResource("/sounds/acdc.wav");
        soundURL[__SPACESHIP_SHOOT__] = getClass().getResource("/sounds/shoot-2.wav");
        soundURL[__ALIEN_SHOOT__] = getClass().getResource("/sounds/shoot-3.wav");
        soundURL[__SPACESHIP_HIT__] = getClass().getResource("/sounds/hit-1.wav");
        soundURL[__ALIEN_HIT__] = getClass().getResource("/sounds/score-0.wav");
        soundURL[__ALIEN_MOVE_0__] = getClass().getResource("/sounds/move-3.wav");
        soundURL[__ALIEN_MOVE_1__] = getClass().getResource("/sounds/move-4.wav");
    }
    
    public sounds getSound(){
        return sound;
    }
    
    public int getKey(sounds sound){
        int key = 0;
        switch (sound) {
        case SOUNDTRACK:
            key = __SOUNDTRACK__;
            break;
        case SPACESHIP_SHOOT:
            key = __SPACESHIP_SHOOT__;
            break;
        case ALIEN_SHOOT:
            key = __ALIEN_SHOOT__;
            break;    
        case SPACESHIP_HIT:
            key = __SPACESHIP_HIT__;
            break;
        case ALIEN_HIT:
            key = __ALIEN_HIT__;
            break;
        case ALIEN_MOVE_0:
            key = __ALIEN_MOVE_0__;
            break;
        case ALIEN_MOVE_1:
            key = __ALIEN_MOVE_1__;
            break;
        default:
            break;
        }
        return key;
    }
    
    public void selectSound(sounds sound){
        int i = getKey(sound);
        try {
            AudioInputStream audio_input_stream = AudioSystem.getAudioInputStream(soundURL[i]);
            clip = AudioSystem.getClip();
            clip.open(audio_input_stream);
        } catch (Exception e){
            System.out.println("ERRO em SoundManager: " + e.getMessage());
        }
    }
    
    public void play(){
        clip.start();
    }
    
    public void loop(){
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }
    
    public void stop(){
        clip.stop();
    }
    
}
