/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spaceinvaders;

import com.sun.org.apache.bcel.internal.generic.F2D;
import java.io.InputStream;
import javafx.scene.text.Font;
import java.util.ArrayList;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.FontWeight;

/**
 *
 * @author michel
 */
public class Cenario {
    
    private int ALIENS_LEFT = 40;
    
    private final int WIDTH_SCREEN = 1600;
    private final int HEIGHT_SCREEN = 900;
    private final int WIDTH_HEART = 50;
    private final int HEIGHT_HEART = 50;
    private final int NUMBER_ALIENS_LINE = 5;
    private final int NUMBER_ALIENS_COLUMN = 11;
    private double WIDTH_ROCK = 50;
    private final double OFFSET_ROCK = (WIDTH_SCREEN - (3 * WIDTH_ROCK)) / 4;
    private final double OFFSET_X = 50;
    private final double OFFSET_Y = 20;
    private final double FONT_SIZE = 36;
    private final double WIDTH_LINE = 5;
    private final double SIZE_BOTTON_MENU = 4 * OFFSET_Y + FONT_SIZE + WIDTH_LINE;
    private final double END_OF_LIFE_TEXT = 220; // chute
    private final double OFFSET_HEART_IMAGE = 60;
    
    String LIFE_TEXT = "LIFE: ";
    Font DOGICA_PIXEL_BOLD = Font.loadFont("file:src/fonts/dogicapixelbold.ttf", FONT_SIZE);

    Line line;
    
    final Image BACKGROUND_IMAGE = new Image("images/background-image.jpg", WIDTH_SCREEN, HEIGHT_SCREEN, false, false);
    final Image HEART_IMAGE = new Image("images/heart.png", WIDTH_HEART, HEIGHT_HEART, false, false);
    ArrayList<Image> heart_images;
    
    GraphicsContext gc;
            
    Cenario (GraphicsContext gc){
        this.gc = gc;
        heart_images = new ArrayList<Image>();
        for (int i = 0; i < 3; i++){
            heart_images.add(HEART_IMAGE);
        }
    }
    
    public double getCanvasWidth(){
        return this.WIDTH_SCREEN;
    }
    
    public double getCanvasHeight(){
        return this.HEIGHT_SCREEN;
    }
    
    public double getWidthRock(){
        return this.WIDTH_ROCK;
    }
    
    public double getOffsetRock(){
        return this.OFFSET_ROCK;
    }
    
    public double getSizeBottonMenu(){
        return SIZE_BOTTON_MENU;
    }
    
    public int getNumberAliensLine(){
        return this.NUMBER_ALIENS_LINE;
    }
    
    public int getNumberAliensColumn(){
        return this.NUMBER_ALIENS_COLUMN;
    }
    
    public boolean itsOnTheLeftWall(double x){
        return x <= 0;
    }

    public boolean itsOnTheRightWall(double x) {
        return x >= WIDTH_SCREEN;
    }
    
    public boolean itsOnTheTop(double y){
        return y <= 0;
    }
    
    public boolean itsOnTheBotton(double y){
        return y >= HEIGHT_SCREEN;
    }
    
    public void drawMenu(){
        // DESENHA LINHA
        gc.setStroke(Color.WHITE);
        gc.setLineWidth(WIDTH_LINE);
        gc.strokeLine(OFFSET_X, HEIGHT_SCREEN - FONT_SIZE - (2 * OFFSET_Y), WIDTH_SCREEN - OFFSET_X, HEIGHT_SCREEN - FONT_SIZE -  2 * OFFSET_Y);        
        
        // DESENHA TEXTO VIDA
        gc.setFont(DOGICA_PIXEL_BOLD);
        gc.setFill(Color.WHITE) ;
        gc.fillText(LIFE_TEXT, OFFSET_X, HEIGHT_SCREEN - OFFSET_Y);
        
        // DESENHA CORAÇÕES
        int mulitply_space = 0;
        for (int i = 0; i < heart_images.size(); i++){
            gc.drawImage(HEART_IMAGE, END_OF_LIFE_TEXT + mulitply_space * OFFSET_HEART_IMAGE, HEIGHT_SCREEN - SIZE_BOTTON_MENU / 2);
            mulitply_space++;
        }
    }
}
