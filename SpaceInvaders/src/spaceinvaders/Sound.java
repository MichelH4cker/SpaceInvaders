/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spaceinvaders;

import java.io.File;
import java.net.URL;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

/**
 *
 * @author michel
 */
public class Sound {
    
    MediaPlayer mediaPlayer;
    String soundString[] = new String[30];
    
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
        soundString[__SOUNDTRACK__] = "src/sounds/soundtrack-0.wav";
        soundString[__SPACESHIP_SHOOT__] = "src/sounds/shoot-2.wav";
        soundString[__ALIEN_SHOOT__] = "src/sounds/shoot-3.wav";
        soundString[__SPACESHIP_HIT__] = "src/sounds/hit-1.wav";
        soundString[__ALIEN_HIT__] = "src/sounds/score-0.wav";
        soundString[__ALIEN_MOVE_0__] = "src/sounds/move-3.wav";
        soundString[__ALIEN_MOVE_1__] = "src/sounds/move-4.wav";
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
            String path = soundString[i];  
            Media media = new Media(new File(path).toURI().toString());  
            mediaPlayer = new MediaPlayer(media);  
        } catch (Exception e){
            System.out.println("ERRO em SoundManager: " + e.getMessage());
        }
    }
    
    public void play(){
        mediaPlayer.setAutoPlay(true);  
    }
    
    public void loop(){
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
    }
    
    public void stop(){
        mediaPlayer.stop();
    }
    
}
