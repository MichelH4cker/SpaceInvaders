/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spaceinvaders;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;

/**
 *
 * @author michel
 */
public class GameManager implements Initializable {
    
    private int ALIENS_LEFT = 40;
    private boolean GAME_OVER = false;
    
    Spaceship spaceship;
    GraphicsContext gc;
    ArrayList<Alien> aliens;
    Cenario cenario;
    
    GameManager(GraphicsContext gc){
        this.gc = gc;
        cenario = new Cenario();
    }
    
    public boolean getGameOver(){
        return this.GAME_OVER;
    }
    
    public void Start(){
        spaceship = new Spaceship(gc);
        
        // CREATE ALIENS
        aliens = new ArrayList<Alien>();
        for (int i = 0; i < ALIENS_LEFT; i++) {
            Alien alien = new Alien(gc);
            aliens.add(alien);
        }
        
        // SET POSICOES DOS ALIENS
        setsAliensInitialPosition(aliens);
        
    }
    
    public void Update(long time, ArrayList<String> inputKeyboard){
        // SPACESHIP ACTION
        spaceship.handleAction(inputKeyboard);
        if (!spaceship.getBullet().isDestroyed()){
            spaceship.getBullet().moveUp();
        }
        
        // MOVE ALIENS
        for (int i = 0; i < ALIENS_LEFT; i++) {
            aliens.get(i).moveRight();
            System.out.println("Novo x: " + aliens.get(i).getPosX());
        }
        
        // DESENHA
        spaceship.draw();
        drawAliens();
    }
    
    public void Finish(){
        
    }

    public void setsAliensInitialPosition(ArrayList<Alien> aliens){
        double posX = 0, posY = 0;
        int counter = 1;
        for (Alien alien : aliens){
            alien.setPosX(posX);
            alien.setPosY(posY);
            posX += alien.getOffsetX() + alien.getImageWidth();
            if (counter == cenario.getNumberAliensColumn()) {
                posY += alien.getOffsetY() + alien.getImageHeight();
                posX = 0;
                counter = 0;
            }
            counter++;
        }
    }
    
    public void drawAliens(){
        for (Alien alien : aliens) {
            alien.draw(alien.getPosX(), alien.getPosY());
        }
    }
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
