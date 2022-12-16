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
    private long PREV_ALIEN_MOVEMENT;
    private long ALIEN_MOVEMENT_DELAY = (long) 1e9;
    
    private boolean guided_shooting = false;
    
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

    public void Update(long TIME, ArrayList<String> inputKeyboard){
        Bullet bullet_spaceship = spaceship.getBullet();
        
        // SPACESHIP ACTION
        spaceship.handleAction(inputKeyboard);
        if (!bullet_spaceship.isDestroyed()){
            bullet_spaceship.moveUp();
            
            // COLISÃO COM ALIEN
            for (Alien alien : aliens) {
                if (bullet_spaceship.collided(alien.getBounds())) {
                    bullet_spaceship.destroy();
                    changeFrontLine(alien);
                    aliens.remove(alien);
                    break;
                }
            }
        }

        // MOVE ALIENS WITH DELAY
        if (TIME - PREV_ALIEN_MOVEMENT > ALIEN_MOVEMENT_DELAY) {
            moveAliens();
            PREV_ALIEN_MOVEMENT = TIME;
        }
        
        // ALIENS SHOOT
        if (alien_bullet.isDestroyed()) {
            aliensShoot();
        }        
        
        if (alien_bullet != null && !alien_bullet.isDestroyed()){
            alien_bullet.moveDown();
            if (alien_bullet.collided(spaceship.getBounds())){
                System.out.println("colidiu");
            }
        }
        
        // DESENHA
        spaceship.draw();
        drawAliens();
    }
    
    public void Finish(){
        
    }
    
    public void changeFrontLine(Alien dead_alien){
        int dead_alien_index = aliens.indexOf(dead_alien);
        int new_front_alien_index = dead_alien_index - cenario.getNumberAliensColumn();
        if (new_front_alien_index > 0){
            aliens.get(new_front_alien_index).setFrontLine(true);
        } 
    }
    
    public void aliensShoot(){
        if (guided_shooting){
            guidedShoot();
        } else {
            randomShoot();
        }
        guided_shooting = !guided_shooting;
    }
    
    public void randomShoot(){
        Alien alien_shooter;
        
        int total_columns = cenario.getNumberAliensColumn();
        
        int minRange = 0;
        int maxRange = total_columns - 1;
        int column_random = (int) Math.floor(Math.random() * (maxRange - minRange + 1) + minRange);        
        int lines_traveled = 0;
        for (int i = column_random; lines_traveled < cenario.getNumberAliensLine(); i += cenario.getNumberAliensColumn()) {
            if (aliens.get(i).isFrontLine()){
                alien_shooter = aliens.get(i);
                alien_bullet = alien_shooter.getBullet();
                alien_bullet.spawn(alien_shooter.getPosX(), alien_shooter.getPosY());
            }
            lines_traveled++;
        }
    }
    
    public void guidedShoot(){
        Alien alien_shooter;
        int index_closest = findClosest(spaceship.getPosX());
        int lines_traveled = 0;
        for (int i = index_closest; lines_traveled < cenario.getNumberAliensLine(); i += cenario.getNumberAliensColumn()) {
            if (aliens.get(i).isFrontLine()){
                alien_shooter = aliens.get(i);
                alien_bullet = alien_shooter.getBullet();
                alien_bullet.spawn(alien_shooter.getPosX(), alien_shooter.getPosY());
            }
            lines_traveled++;
        }
    }

    public int findClosest(double target){
        int idx = 0;
        double dist = Math.abs(aliens.get(0).getPosX() - target);
        
        for (int i = 1; i < aliens.size(); i++) {
            double cdist = Math.abs(aliens.get(i).getPosX() - target);
            if (cdist < dist) {
                idx = i;
                dist = cdist;
            }
        }
        return idx;
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
        setAliensFrontLine();
    }
    
    public void setAliensFrontLine(){
        int TOTAL_COLUMNS = cenario.getNumberAliensColumn();
        aliens.get(5 + (ALIENS_LEFT - TOTAL_COLUMNS)).setFrontLine(true);

        for (int i = 0; i < ALIENS_LEFT - 3 * TOTAL_COLUMNS; i++){
            aliens.get(i + (ALIENS_LEFT - TOTAL_COLUMNS)).setFrontLine(true);
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
