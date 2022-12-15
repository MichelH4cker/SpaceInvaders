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
public class Bullet {

    private double posX;
    private double posY;
    private double velocity = 10.0;
    private boolean destroyed;
    
    private final int IMAGE_HEIGHT = 60;
    private final int IMAGE_WIDTH = 60;
    
    final Image image = new Image("images/bullet.png", IMAGE_WIDTH, IMAGE_HEIGHT, false, false);
    
    GraphicsContext gc;
    
    Bullet(GraphicsContext gc) {
        this.gc = gc;
        this.destroyed = true;
    }
    
    public boolean isDestroyed(){
        return this.destroyed;
    }
    
    public void draw(){
        gc.drawImage(image, posX, posY);
    }
    
    public void spawn(double initial_x, double initial_y){
        this.destroyed = false;
        posX = initial_x;        
        posY = initial_y;
        System.out.println("X: " + posX);
        System.out.println("Y:" + posY);
    }
    
    public void moveUp(){
        System.out.println("esta movendo para cima! e a posicao e " + posY);
        posY -= velocity;
        draw();
    }
    
    public void moveDown(){
        posY += velocity;
        draw();
    }
    
}
