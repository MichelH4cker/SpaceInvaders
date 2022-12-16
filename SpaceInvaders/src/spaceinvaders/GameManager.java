/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spaceinvaders;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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
    Bullet alien_bullet;
    GraphicsContext gc;
    ArrayList<Alien> aliens;
    Cenario cenario;
    
    GameManager(GraphicsContext gc){
        this.gc = gc;
        cenario = new Cenario();
        alien_bullet = new Bullet(gc);
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
        moveAliens();
        
        // ALIENS SHOOT
        if (alien_bullet.isDestroyed()) {
            aliensShoot();
        }
        
        
        if (alien_bullet != null && !alien_bullet.isDestroyed()){
            alien_bullet.moveDown();
        }
        
        // DESENHA
        spaceship.draw();
        drawAliens();
    }
    
    public void Finish(){
        
    }
    
    public void aliensShoot(){
        Alien alien_shooter;
        
        int TOTAL_COLUMNS = cenario.getNumberAliensColumn();
        int jumped_lines = cenario.getNumberAliensLine();
        
        int minRange = 0;
        int maxRange = TOTAL_COLUMNS - 1;
        int column_random = (int) Math.floor(Math.random() * (maxRange - minRange + 1) + minRange);        
        
        boolean FIRE_IN_THE_HOLE = false;
        while (!FIRE_IN_THE_HOLE) {    
            // CALCULO PARA ENCONTRAR O INDEX PERFEITO
            int index = column_random + (TOTAL_COLUMNS * (jumped_lines - 1)); 
                    
            alien_shooter = aliens.get(index);
            
            if (alien_shooter != null){
                FIRE_IN_THE_HOLE = true;
                alien_bullet = alien_shooter.getBullet();
                alien_bullet.spawn(alien_shooter.getPosX(), alien_shooter.getPosY());
            } else if (jumped_lines == 0){
                break;
            } else {
                jumped_lines--;
            }
        }
    }
    
    public void moveAliens(){
        Alien alienMaxX = Collections.max(aliens, Comparator.comparing(Alien::getPosX));
        Alien alienMinX = Collections.min(aliens, Comparator.comparing(Alien::getPosX));
        Alien alienMaxY = Collections.max(aliens, Comparator.comparing(Alien::getPosY));
        
        if (cenario.itsOnTheLeftWall(alienMinX.getPosX())) {
           for (Alien alien : aliens){
               alien.moveDown();
               alien.moveRight();
               alien.setIsMovingToRight(!alien.getIsMovingToRight());
           }
        } else if (cenario.itsOnTheRightWall(alienMaxX.getPosX() + alienMaxX.getImageWidth())){
            for (Alien alien : aliens){
                alien.moveDown();
                alien.moveLeft();
                alien.setIsMovingToRight(!alien.getIsMovingToRight());
            }
        } else if (alienMaxY.getIsMovingToRight()){
            for (Alien alien : aliens) alien.moveRight();
        } else {
            for (Alien alien : aliens) alien.moveLeft();
        }
        
    }
    
    public void setsAliensInitialPosition(ArrayList<Alien> aliens){
        double initialX = aliens.get(0).getVelocityX();
        double initialY = 0;
        double posX = initialX, posY = initialY;
        int counter = 1;
        for (Alien alien : aliens){
            alien.setPosX(posX);
            alien.setPosY(posY);
            posX += alien.getOffsetX() + alien.getImageWidth();
            if (counter == cenario.getNumberAliensColumn()) {
                posY += alien.getOffsetY() + alien.getImageHeight();
                posX = initialX;
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
