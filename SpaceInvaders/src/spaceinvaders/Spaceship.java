/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spaceinvaders;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.text.Text;

/**
 *
 * @author michel
 */
public class Spaceship {
    
    private int posX, posY;
    private int life;
    private boolean dead;
    
    @FXML
    Text text = new Text();
    
    @FXML
    final Image image = new Image("images/spaceship.png");
    
    Spaceship() {
        posX = 0;
        posY = 0;
        life = 3;
        dead = false;
        draw();
    }
    
    public void draw() {
        text.setX(50);
        text.setY(50);
        text.setText("MICHEL");
    }

    
}
