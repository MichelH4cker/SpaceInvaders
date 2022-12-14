/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spaceinvaders;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

/**
 *
 * @author michel
 */
public class GameManager implements Initializable {
    
    Spaceship spaceship;
    Canvas canvas;
    GraphicsContext gc;

    GameManager(GraphicsContext gc, Canvas canvas){
        this.gc = gc;
        this.canvas = canvas; 
    }
    
    public void Start(){
        spaceship = new Spaceship(gc, canvas);
    }
    
    public void Update(){
        
    }
    
    public void Finish(){
        
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
