/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spaceinvaders;

import java.net.URL;
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
    
    private int life = 3;
    private double posX = 500.0;
    private double posY = 500.0;
    private double velocity;
    private boolean dead;
    
    private int IMAGE_HEIGHT = 50;
    private int IMAGE_WIDTH = 50;

    GraphicsContext gc;
    Canvas canvas;
    
    @FXML
    final Image image = new Image("images/spaceship.png", IMAGE_WIDTH, IMAGE_HEIGHT, false, false);
    
    Spaceship(GraphicsContext gc, Canvas canvas) {
        image.isPreserveRatio();
        this.gc = gc;
        this.canvas = canvas;
        dead = false;
        draw();
    }
    
    public void draw() {
        gc.drawImage(image, posX, posY);
    }

    
}
