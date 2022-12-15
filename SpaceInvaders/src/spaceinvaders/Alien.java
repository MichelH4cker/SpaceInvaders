/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spaceinvaders;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

/**
 *
 * @author michel
 */
public class Alien {
    
    // 4 linhas de aliens
    // 10 colunas de aliens
    
    private int life;
    private double OFFSET_X = 15.0;
    private double OFFSET_Y = 10.0;
    private double posX;
    private double posY;
    private double velocity = 5.0;
    private boolean dead;
    
    GraphicsContext gc;
    Bullet bullet;
    
    private int IMAGE_WIDTH = 50;
    private int IMAGE_HEIGHT = 50;
    
    final Image image = new Image("images/alien.png", IMAGE_WIDTH, IMAGE_HEIGHT, false, false);
    
    Alien(GraphicsContext gc) {
        this.gc = gc;
        life = 3;
    }
    
    public double getOffsetX(){
        return this.OFFSET_X;
    }
    
    public double getOffsetY(){
        return this.OFFSET_Y;        
    }
    
    public int getImageWidth(){
        return this.IMAGE_WIDTH;
    }
    
    public int getImageHeight(){
        return this.IMAGE_HEIGHT;
    }
    
    public void draw(int pos_x, int pos_y){
        gc.drawImage(image, pos_x, pos_y);
    }
    
    public void moveRight(){
        
    }

    public void moveLeft(){
        
    }
}
