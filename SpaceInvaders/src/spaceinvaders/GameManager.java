/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spaceinvaders;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;

/**
 *
 * @author michel
 */
public class GameManager implements Initializable {
    
    Spaceship spacecraft;
    
    public void Start(){
        spacecraft = new Spaceship();
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
