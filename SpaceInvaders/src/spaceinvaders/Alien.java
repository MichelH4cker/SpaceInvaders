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
public class Alien {
    
    private int life;
    private double OFFSET_X = 23.0;
    private double OFFSET_Y = 10.0;
    private double posX;
    private double posY;
    private double velocityX = 20;
    private double velocityY = 20;
    private double UPGRADE_VELOCITY = 5;
    private boolean dead;
    private boolean isMovingToRight;
    private boolean frontLine;
    
    GraphicsContext gc;
    Bullet bullet;
    
    private int IMAGE_WIDTH = 70;
    private int IMAGE_HEIGHT = 70;
    
    final Image image = new Image("images/alien-1.png", IMAGE_WIDTH, IMAGE_HEIGHT, false, false);
    
    Alien(GraphicsContext gc) {
        this.frontLine = false;
        this.gc = gc;
        this.isMovingToRight = true;
        this.life = 3;
        bullet = new Bullet(gc);
    }
    
    public Rectangle getBounds() {
        return new Rectangle(posX, posY, IMAGE_WIDTH, IMAGE_HEIGHT);
    }
    
    public double getVelocityX(){
        return this.velocityX;
    }
    
    public double getVelocityY(){
        return this.velocityY;
    }
    
    public boolean getIsMovingToRight(){
        return this.isMovingToRight;
    }
    
    public void setIsMovingToRight(boolean isMovingToRight){
        this.isMovingToRight = isMovingToRight;
    }
    
    public boolean isFrontLine(){
        return this.frontLine;
    }
    
    public void setFrontLine(boolean frontLine){
        this.frontLine = frontLine;
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
    
    public double getPosX(){
        return this.posX;        
    }
    
    public double getPosY(){
        return this.posY;
    }
    
    public void setPosX(double posX){
        this.posX = posX;
    }
    
    public void setPosY(double posY){
        this.posY = posY;
    }

    public Bullet getBullet(){
        return this.bullet;
    }
    
    public void increaseVelocity(){
        velocityX += UPGRADE_VELOCITY;
    }
    
    public void destroy(){
        posX = 50;
        posY = 0;
        dead = true;
    }
    
    public boolean isDead(){
        return this.dead;
    }
    
    public void draw(double pos_x, double pos_y){
        gc.drawImage(image, pos_x, pos_y);
    }
    
    public void moveRight(){
        posX += velocityX;
    }

    public void moveLeft(){
        posX -= velocityX;
    }
    
    public void moveDown(){
        posY += velocityY;
    }
}
