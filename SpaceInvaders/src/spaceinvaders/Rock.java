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
public class Rock {
 
    private double IMAGE_WIDTH = 150;
    private double IMAGE_HEIGHT = 150;
    
    private double posX;
    private double posY;
    
    private int life = 6;
    
    private final Image image = new Image("images/rock.png", IMAGE_WIDTH, IMAGE_HEIGHT, false, false);
    
    GraphicsContext gc;
    Rock(GraphicsContext gc){
        this.gc = gc;
    }
    
    public double getImageWidth(){
        return this.IMAGE_WIDTH;
    }
    
    public void setPosX(double x){
        this.posX = x;
    }
    
    public double getPosX(){
        return this.posX;
    }
    
    public void setPosY(double y){
        this.posY = y;
    }
    
    public double getPosY(){
        return this.posY;
    }
    
    public Rectangle getBounds() {
        return new Rectangle(posX, posY, IMAGE_WIDTH, IMAGE_HEIGHT);
    }
    
    public int getLife(){
        return this.life;
    }
    
    public void hit(){
        life--;
    }
    
    public void draw(){
        gc.drawImage(image, posX, posY);
    }
    
}
