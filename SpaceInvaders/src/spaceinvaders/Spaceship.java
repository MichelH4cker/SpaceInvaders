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
import javafx.scene.text.Text;

/**
 *
 * @author michel
 */
public class Spaceship {
    
    private final double posY = 500.0;
    private double posX = 500.0;
    private int life = 3;
    private double velocity = 5.0;
    private boolean dead;
    
    private final int IMAGE_HEIGHT = 65;
    private final int IMAGE_WIDTH = 65;

    private int WIDTH = 1600;
    private int HEIGHT = 900;
    
    private double X_BULLET_SPAWN;
    private double y_BULLET_SPAWN;
    
    private Bullet bullet;
    
    GraphicsContext gc;
    
    @FXML
    final Image image = new Image("images/spaceship.png", IMAGE_WIDTH, IMAGE_HEIGHT, false, false);
    
    Spaceship(GraphicsContext gc) {
        cenario = new Cenario();
        image.isPreserveRatio();
        this.gc = gc;
        dead = false;
        bullet = new Bullet(gc);
        draw();
    }
    
    public void draw() {
        gc.drawImage(image, posX, posY);
    }
    
    public void handleAction(ArrayList<String> inputKeyboard){
        if (inputKeyboard.contains("SPACE") && !bullet.isDestroyed()) {
            bullet.spawn((posX + X_BULLET_SPAWN) / 2, (posY + y_BULLET_SPAWN) / 2);
        } 
        if (inputKeyboard.contains("LEFT") && !isOnLeftTheWall()) {
            moveLeft();
        } else if (inputKeyboard.contains("RIGHT") && !cenario.itsOnTheRightWall(posX + IMAGE_WIDTH)) {
            moveRight();
        }
    }

    public boolean isOnLeftTheWall() {
        return posX == 0;
    }
    
    public boolean isOntheRightWall(){
        return (posX == WIDTH - IMAGE_WIDTH);
    }
    
    public void moveRight() {
        posX += velocity;
    }

    public void moveLeft(){
        posX -= velocity;
    }
    
}
