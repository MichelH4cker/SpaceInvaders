/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spaceinvaders;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

/**
 *
 * @author michel
 */
public class Spaceship {
    
    private long PREV_SHOOT = 0;
    private long SHOOT_DELAY = (long) 0.65e9;
    
    private final int IMAGE_HEIGHT = 80;
    private final int IMAGE_WIDTH = 80;

    private int CANVAS_WIDTH = 1600;
    private int CANVAS_HEIGHT = 900;
    
    private int OFFSET_SPAWN = 60;
    
    private final double posY;
    private double posX = 500.0;
    private int life = 3;
    private double velocity = 7.0;
    private boolean dead;
    
    private Bullet bullet;
    
    GraphicsContext gc;
    Cenario cenario;
    
    @FXML
    final Image image = new Image("images/spaceship.png", IMAGE_WIDTH, IMAGE_HEIGHT, false, false);
    
    Spaceship(GraphicsContext gc) {
        this.gc = gc;
        cenario = new Cenario(gc);
        image.isPreserveRatio();
        bullet = new Bullet(gc);
        posY = CANVAS_HEIGHT - OFFSET_SPAWN - cenario.getSizeBottonMenu();
        dead = false;
    }
    
    public double getPosX(){
        return this.posX;
    }
    
    public double getPosY(){
        return this.posY;
    }
    
    public Rectangle getBounds() {
        return new Rectangle(posX, posY, IMAGE_WIDTH, IMAGE_HEIGHT);
    }
    
    public int  getLife(){
        return life;
    }
    
    public void hit(){
        life -= 1;
    }
    
    public Bullet getBullet(){
        return this.bullet;
    }
    
    public void draw() {
        gc.drawImage(image, posX, posY);
    }
    
    public void handleAction(ArrayList<String> inputKeyboard, long TIME){
        if (inputKeyboard.contains("SPACE") && bullet.isDestroyed() && TIME - PREV_SHOOT > SHOOT_DELAY) {
            bullet.spawn(posX, posY);
            PREV_SHOOT = TIME;
        } 
        if (inputKeyboard.contains("LEFT") && !cenario.itsOnTheLeftWall(posX)) {
            moveLeft();
        } else if (inputKeyboard.contains("RIGHT") && !cenario.itsOnTheRightWall(posX + IMAGE_WIDTH)) {
            moveRight();
        }
    }
    
    public void moveRight() {
        posX += velocity;
    }

    public void moveLeft(){
        posX -= velocity;
    }
    
}
