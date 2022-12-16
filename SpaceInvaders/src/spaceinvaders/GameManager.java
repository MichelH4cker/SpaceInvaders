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
        aliens = new ArrayList<Alien>();
        for (int i = 0; i < ALIENS_LEFT; i++) {
            Alien alien = new Alien(gc);
            aliens.add(alien);
        }
    }
    
    public void Update(long time, ArrayList<String> inputKeyboard){
        drawAliens();
        
        // SPACESHIP ACTION
        spaceship.handleAction(inputKeyboard);
        if (!spaceship.getBullet().isDestroyed()){
            spaceship.getBullet().moveUp();
        }
        spaceship.draw();
    }
    
    public void Finish(){
        
    }

    public void drawAliens(){
        int pos_x = 0, pos_y = 0;
        for (int line = 0; line < cenario.getNumberAliensLine(); line++) {
            for (int column = 0; column < cenario.getNumberAliensColumn(); column++) {
                aliens.get(column).draw(pos_x, pos_y);
                pos_x += aliens.get(column).getOffsetX() + aliens.get(column).getImageWidth();
            }
            pos_x = 0;
            pos_y += aliens.get(line).getOffsetY() + aliens.get(line).getImageHeight();
        }
    }
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
