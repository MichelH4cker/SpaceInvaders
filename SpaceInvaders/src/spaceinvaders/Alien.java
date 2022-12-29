/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spaceinvaders;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
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
    private double velocityY = 30;
    private double UPGRADE_VELOCITY = 5;
    private boolean dead;
    private boolean isMovingToRight;
    private boolean frontLine;
    private boolean change_image = true;
    
    GraphicsContext gc;
    Bullet bullet;
    
    private int IMAGE_WIDTH = 70;
    private int IMAGE_HEIGHT = 70;
    
    private final Image alien0  = new Image("images/alien-0.png" , IMAGE_WIDTH, IMAGE_HEIGHT, false, false);
    private final Image alien1a = new Image("images/alien-1a.png", IMAGE_WIDTH, IMAGE_HEIGHT, false, false);
    private final Image alien1b = new Image("images/alien-1b.png", IMAGE_WIDTH, IMAGE_HEIGHT, false, false);
    private final Image alien2a = new Image("images/alien-2a.png", IMAGE_WIDTH, IMAGE_HEIGHT, false, false);
    private final Image alien2b = new Image("images/alien-2b.png", IMAGE_WIDTH, IMAGE_HEIGHT, false, false);
    
    private final Image special_alien_a = new Image("images/special-alien-a.png", IMAGE_WIDTH, IMAGE_HEIGHT, false, false);
    private final Image special_alien_b = new Image("images/special-alien-b.png", IMAGE_WIDTH, IMAGE_HEIGHT, false, false);

    private Image image_0;
    private Image image_1;
    
    private Image image;

    enum aliens {
        MIKE,
        GREEN,
        PURPLE,
        SPECIAL;
    }
    
    private aliens alien;
    
    public Alien(GraphicsContext gc) {
        this.frontLine = false;
        this.gc = gc;
        this.isMovingToRight = true;
        this.life = 3;
        bullet = new Bullet(gc);
    }
    
    public Alien(GraphicsContext gc, boolean special){
        this.gc = gc;
        dead = true;
        life = 1;
    }
    
    public aliens getAlienType(){
        return alien;
    }
    
    public Rectangle getBounds() {
        return new Rectangle(posX, posY, IMAGE_WIDTH, IMAGE_HEIGHT - 20);
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
    
    public void setImage(aliens alien){
        switch (alien){
            case MIKE:
                image_0 = alien0;
                image_1 = alien0;
                break;
            case GREEN:
                image_0 = alien1a;
                image_1 = alien1b;
                break;
            case PURPLE:
                image_0 = alien2a;
                image_1 = alien2b;
                break;
            case SPECIAL: 
                image_0 = special_alien_a;
                image_1 = special_alien_b;
            default:
                break;
        }
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
    
    public void spawn(){
        posX = 0;
        posY = 0;
    }
    
    public void setIsDead(boolean dead){
        this.dead = dead;
    }
    
    public boolean isDead(){
        return this.dead;
    }
    
    public void changeImage(){
        if (change_image){
            image = image_0;
        } else {
            image = image_1;
        }
        change_image = !change_image;
    }
    
    public void draw(){
        gc.drawImage(image, posX, posY);
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
