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
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

/**
 *
 * @author michel
 */
public class GameManager implements Initializable {
        
    private int ALIENS_LEFT = 40;
    private int ROCKS = 3;
    private boolean GAME_OVER = false;
    
    private long PREV_ALIEN_MOVEMENT;
    private long ALIEN_MOVEMENT_DELAY = (long) 1e9;
    
    private boolean guided_shooting = false;
    private boolean PLAYER_WON = false;
    
    int WIDTH = 1600;
    int HEIGHT = 900;
    int FONT_SIZE = 70;
    private final Image BACKGROUND_IMAGE = new Image("images/background-image.jpg", WIDTH, HEIGHT, false, false);
    Font DOGICA_PIXEL_BOLD = Font.loadFont("file:src/fonts/dogicapixelbold.ttf", FONT_SIZE);

    
    Spaceship spaceship;
    Bullet alien_bullet;
    GraphicsContext gc;
    ArrayList<Alien> aliens;
    ArrayList<Rock> rocks;
    Cenario cenario;
    
    GameManager(GraphicsContext gc){
        this.gc = gc;
        cenario = new Cenario(gc);
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
        
        // CREATE ROCKS
        rocks = new ArrayList<Rock>();
        for (int i = 0; i < ROCKS; i++) {
            rocks.add(new Rock(gc));
        }
        
        // SET ROCKS
        setRocks();
    }

    public void Update(long TIME, ArrayList<String> inputKeyboard){
        Bullet bullet_spaceship = spaceship.getBullet();
        
        // SPACESHIP ACTION
        spaceship.handleAction(inputKeyboard);
        
        // TIRO DA SPACESHIP
        if (!bullet_spaceship.isDestroyed()){
            bullet_spaceship.moveUp();
            
            // ACERTOU UM ALIEN
            for (Alien alien : aliens) {
                if (bullet_spaceship.collided(alien.getBounds())) {
                    bullet_spaceship.destroy();
                    changeFrontLine(alien);
                    ALIENS_LEFT--;
                    aliens.remove(alien);
                    break;
                }
            }
            
            // ACERTOU UMA ROCHA
            for (Rock rock : rocks){
                if (bullet_spaceship.collided(rock.getBounds())) {
                    rock.hit();
                    if (rock.getLife() == 0) rocks.remove(rock);
                    bullet_spaceship.destroy();
                    System.out.println("spaceship acertou alien");
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
            // MOVE TIRO DOS ALIENS
            alien_bullet.moveDown();
            
            // VERIFICA SE TIRO DO ALIEN ACERTOU ROCHA
            for (Rock rock : rocks){
                if (alien_bullet.collided(rock.getBounds())){
                    rock.hit();
                    if (rock.getLife() == 0) rocks.remove(rock);
                    alien_bullet.destroy();
                    System.out.println("Alien acertou rocha");
                    break;
                }
            }
            
            // VERIFICA SE TIRO ACERTOU SPACESHIP
            if (alien_bullet.collided(spaceship.getBounds())){
                alien_bullet.destroy();
                spaceship.hit();
                cenario.heart_images.remove(cenario.heart_images.size() - 1);
            }
        }
        
        // DESENHA
        spaceship.draw();
        drawAliens();
        cenario.drawMenu();
        for (Rock rock : rocks) rock.draw();
        
        checkGameOver();
    }
    
    public void Finish(){
        gc.clearRect(0, 0, WIDTH, HEIGHT);
        gc.drawImage(BACKGROUND_IMAGE, 0, 0);
        
        gc.setFont(DOGICA_PIXEL_BOLD);
        gc.setFill(Color.WHITE) ;
        
        String RESULT;
        if (PLAYER_WON){
            RESULT = "VOCÊ GANHOU!";
        } else {
            RESULT = "VOCÊ PERDEU!";
        }
            
        gc.fillText(RESULT, (WIDTH / 2) - 380, HEIGHT / 2);
    }
      
    public void checkGameOver(){
        if (ALIENS_LEFT == 0){
            PLAYER_WON = true;
            GAME_OVER = true;
        } else if (spaceship.getLife() == 0){
            PLAYER_WON = false;
            GAME_OVER = true;
        }
    }
    
    public void setRocks(){
        double POS_X = cenario.getOffsetRock();
        for (Rock rock : rocks){
            rock.setPosX(POS_X);
            rock.setPosY(cenario.getCanvasHeight() - 350);
            POS_X += cenario.getWidthRock() + cenario.getOffsetRock();
        }
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
            if (i < aliens.size()){
                if (aliens.get(i).isFrontLine()){
                    alien_shooter = aliens.get(i);
                    alien_bullet = alien_shooter.getBullet();
                    alien_bullet.spawn(alien_shooter.getPosX(), alien_shooter.getPosY());
                }
            }
            lines_traveled++;
        }
    }
    
    public void guidedShoot(){
        Alien alien_shooter;
        int index_closest = findClosest(spaceship.getPosX());
        int lines_traveled = 0;
        for (int i = index_closest; lines_traveled < cenario.getNumberAliensLine(); i += cenario.getNumberAliensColumn()) {
            if (i < aliens.size()){
                if (aliens.get(i).isFrontLine()){
                    alien_shooter = aliens.get(i);
                    alien_bullet = alien_shooter.getBullet();
                    alien_bullet.spawn(alien_shooter.getPosX(), alien_shooter.getPosY());
                }
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
