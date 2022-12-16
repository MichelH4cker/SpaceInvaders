/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spaceinvaders;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.shape.Rectangle;

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
    
    private final int IMAGE_ALIEN_HEIGHT = 50;
    private final int IMAGE_ALIEN_WIDTH = 50;

    
    final Image image = new Image("images/bullet.png", IMAGE_WIDTH, IMAGE_HEIGHT, false, false);
    
    GraphicsContext gc;
    Cenario cenario;
    
    Bullet(GraphicsContext gc) {
        this.gc = gc;
        this.destroyed = true;
        cenario = new Cenario();
    }
    
    public double getPosY(){
        return posY;
    }
    
    public boolean isDestroyed(){
        return this.destroyed;
    }
    
    public void destroy(){
        posX = 0;
        posY = 0;
        this.destroyed = true;
    }
    
    public void draw(){
        gc.drawImage(image, posX, posY);
    }
    
    public void spawn(double initial_x, double initial_y){
        this.destroyed = false;
        posX = initial_x;        
        posY = initial_y;
    }
    
    public void moveUp(){
        posY -= velocity;
        draw();
        destroyed = cenario.itsOnTheTop(posY + IMAGE_HEIGHT);
    }
    
    public void moveDown(){
        posY += velocity;
        draw();
        destroyed = cenario.itsOnTheBotton(posY);
    }
    
    public Rectangle getBounds() {
        return new Rectangle(posX, posY, IMAGE_WIDTH, IMAGE_HEIGHT);
    }
    
    public boolean collidedWithAlien(Alien alien){
        Rectangle rec_bullet = getBounds();
        Rectangle rec_alien = alien.getBounds();
        
        if (rec_bullet.getBoundsInParent().intersects(rec_alien.getBoundsInParent())) {
            return true;
        }
        return false;
    }
    
    public boolean collidedWithSpaceship(double x, double y){
        return false;
    }

}
