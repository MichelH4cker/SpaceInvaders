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
import javafx.scene.text.Font;

/**
 *
 * @author michel
 */
public class Rock {
 
    private double IMAGE_WIDTH = 180;
    private double IMAGE_HEIGHT = 180;
    
    private double posX;
    private double posY;
    
    private int life = 20;
    
    private final double FONT_SIZE = 30;

    private final Image image = new Image("images/rock.png", IMAGE_WIDTH, IMAGE_HEIGHT, false, false);
    private Font DOGICA_PIXEL_BOLD = Font.loadFont("file:src/fonts/dogicapixelbold.ttf", FONT_SIZE);

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
        return new Rectangle(posX + 30, posY + 10, IMAGE_WIDTH - 60, IMAGE_HEIGHT - 10);
    }
    
    public int getLife(){
        return this.life;
    }
    
    public void hit(){
        life--;
    }
    
    public void draw(){
        gc.drawImage(image, posX, posY);
        // (WIDTH / 2) - (DOGICA_PIXEL_BOLD.getSize() * RESULT.length()) / 2
        // DESENHA INT SCORE
        gc.setFont(DOGICA_PIXEL_BOLD);
        gc.setFill(Color.WHITE) ;
        String LIFE_TEXT = Integer.toString(life);
        gc.fillText(LIFE_TEXT, 
                posX + (IMAGE_WIDTH - DOGICA_PIXEL_BOLD.getSize() * LIFE_TEXT.length()) / 2, 
                posY + IMAGE_HEIGHT / 2);
    }
    
}
