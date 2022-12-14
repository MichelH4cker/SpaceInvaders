/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spaceinvaders;

import javafx.scene.image.Image;

/**
 *
 * @author michel
 */
public class Cenario {
    
    private int WIDTH_SCREEN = 1600;
    private int HEIGHT_SCREEN = 900;
    final Image BACKGROUND_IMAGE = new Image("images/background-image.jpg", WIDTH_SCREEN, HEIGHT_SCREEN, false, false);

    public boolean itsOnTheLeftWall(double x){
        return x == 0;
    }

    public boolean itsOnTheRightWall(double x) {
        return x == WIDTH_SCREEN;
    }
    
    public boolean itsOnTheTop(double y){
        return y == 0;
    }
    
    public boolean itsOnTheBotton(double y){
        return y == HEIGHT_SCREEN;
    }
    
}
