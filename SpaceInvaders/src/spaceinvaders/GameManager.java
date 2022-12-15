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
    
    private boolean GAME_OVER = false;
    Spaceship spaceship;
    GraphicsContext gc;

    GameManager(GraphicsContext gc){
        this.gc = gc;
    }
    
    public boolean getGameOver(){
        return this.GAME_OVER;
    }
    
    public void Start(){
        spaceship = new Spaceship(gc);
    }
    
    public void Update(long time, ArrayList<String> inputKeyboard){
        // SPACESHIP ACTION
        spaceship.handleAction(inputKeyboard);
        spaceship.draw();
    }
    
    public void Finish(){
        
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
