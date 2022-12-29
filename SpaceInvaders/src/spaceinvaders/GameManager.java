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

    private int ALIENS_LEFT = 55;
    private int ROCKS = 3;
    private boolean GAME_OVER = false;
    
    private long PREV_SPECIAL_ALIEN_SPAWN = 0;
    private long SPECIAL_ALIEN_SPAWN_DELAY = (long) 30e9;
    private long PREV_SPECIAL_ALIEN_MOVEMENT;
    private long SPECIAL_ALIEN_MOVEMENT_DELAY = (long) 0.1e9;
    
    private long PREV_ALIEN_MOVEMENT;
    private long ALIEN_MOVEMENT_DELAY = (long) 1.2e9;
    
    private long PREV_ALIEN_SHOOT;
    private long ALIEN_SHOOT_DELAY = (long) 1e9; 
    private long ALIEN_SHOOT_UPGRADE_DELAY = (long) 0.1e9;
    
    private long PREV_SPACESHIP_BLINK_DELAY;
    private long SPACESHIP_BLINK_DELAY = (long) 0.4e9; 

    private int spaceship_total_blink = 5;
    private int spaceship_blink_times = 0;
    private boolean guided_shooting = false;
    private boolean PLAYER_WON = false;
    private boolean CHANGE_SOUND = false;
    
    private boolean MOVED_DOWN = false;
    
    int WIDTH = 1600;
    int HEIGHT = 900;
    int FONT_SIZE = 70;
    private final Image BACKGROUND_IMAGE = new Image("images/background-image.png", WIDTH, HEIGHT, false, false);
    Font DOGICA_PIXEL_BOLD = Font.loadFont("file:src/fonts/dogicapixelbold.ttf", FONT_SIZE);
    
    Spaceship spaceship;
    Bullet alien_bullet;
    GraphicsContext gc;
    Alien special_alien;
    ArrayList<Alien> aliens;
    ArrayList<Rock> rocks;
    Cenario cenario;

    GameManager(GraphicsContext gc){
        this.gc = gc;
        cenario = new Cenario(gc);
        alien_bullet = new Bullet(gc);
        special_alien = new Alien(gc, true);
        special_alien.setImage(Alien.aliens.SPECIAL);
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
        setAliensInitialPosition(aliens);
        
        // SET IMAGENS DOS ALIENS
        setAliensImages(aliens);
        
        // MARCA QUAIS ALIENS SÃO DA LINHA DE FRENTE
        setAliensFrontLine();

        // CREATE ROCKS
        rocks = new ArrayList<Rock>();
        for (int i = 0; i < ROCKS; i++) {
            rocks.add(new Rock(gc));
        }
        
        // SET ROCKS
        setRocks();
    }

    public void Update(long TIME, ArrayList<String> inputKeyboard){
        checkGameOver();
        Bullet bullet_spaceship = spaceship.getBullet();
        
        // SPACESHIP ACTION
        spaceship.handleAction(inputKeyboard, TIME);
        
        // TIRO DA SPACESHIP
        if (!bullet_spaceship.isDestroyed()){
            bullet_spaceship.moveUp();
            
            // ACERTOU UM ALIEN
            for (Alien alien : aliens) {
                if (bullet_spaceship.collided(alien.getBounds())) {
                    cenario.scoreNormalAlien();
                    bullet_spaceship.destroy();
                    changeFrontLine(alien);
                    ALIENS_LEFT--;
                    aliens.remove(alien);
                    // SOM
                    Sound sound = new Sound();
                    sound.selectSound(sound.getSound().ALIEN_HIT);
                    sound.play();
                    break;
                }
            }
            
            // ACERTOU UM ALIEN ESPECIAL
            if (bullet_spaceship.collided(special_alien.getBounds())){
                cenario.scoreSpecialAlien();
                bullet_spaceship.destroy();
                special_alien.destroy();
                
                // SOM
                Sound sound = new Sound();
                sound.selectSound(sound.getSound().ALIEN_HIT);
                sound.play();
            }
            
            // ACERTOU UMA ROCHA
            for (Rock rock : rocks){
                if (bullet_spaceship.collided(rock.getBounds())) {
                    rock.hit();
                    System.out.println("acertou");
                    bullet_spaceship.destroy();
                    if (rock.getLife() == 0){
                        rocks.remove(rock);
                    }
                    break;
                }
            }
        }
        
        // MOVE SPECIAL ALIEN WITH DELAY
        if (!special_alien.isDead()) {
            if (TIME - PREV_SPECIAL_ALIEN_MOVEMENT > SPECIAL_ALIEN_MOVEMENT_DELAY) {
                PREV_SPECIAL_ALIEN_MOVEMENT = TIME;
                special_alien.changeImage();
                special_alien.moveRight();
            }
        }
        
        // SPAWN SPECIAL ALIEN
        if (MOVED_DOWN && TIME - PREV_SPECIAL_ALIEN_SPAWN > SPECIAL_ALIEN_SPAWN_DELAY) {
            PREV_SPECIAL_ALIEN_SPAWN = TIME;
            special_alien.setIsDead(false);
            special_alien.spawn();
        }
        
        // DESTROY SPECIAL ALIEN
        if (cenario.itsOnTheRightWall(special_alien.getPosX())){
            special_alien.destroy();
        }
        
        // MOVE ALIENS WITH DELAY
        if (TIME - PREV_ALIEN_MOVEMENT > ALIEN_MOVEMENT_DELAY) {
            moveAliens();
            PREV_ALIEN_MOVEMENT = TIME;
            
            // SOM
            Sound sound = new Sound();
            // SOM DIFERERENTE PARA CADA PASSADA
            if (CHANGE_SOUND){
                sound.selectSound(sound.getSound().ALIEN_MOVE_0);
            } else {
                sound.selectSound(sound.getSound().ALIEN_MOVE_1);
            }
            CHANGE_SOUND = !CHANGE_SOUND;
            sound.play();
        }
        
        // ALIENS SHOOT
        if (TIME - PREV_ALIEN_SHOOT > ALIEN_SHOOT_DELAY) {
            aliensShoot();
            PREV_ALIEN_SHOOT = TIME;
        }
        
        if (alien_bullet != null && !alien_bullet.isDestroyed()){
            // MOVE TIRO DOS ALIENS
            alien_bullet.moveDown();
            
            // VERIFICA SE TIRO DO ALIEN ACERTOU ROCHA
            for (Rock rock : rocks){
                if (alien_bullet.collided(rock.getBounds())){
                    rock.hit();
                    if (rock.getLife() == 0) {
                        rocks.remove(rock);
                    }
                    alien_bullet.destroy();
                    break;
                }
            }
            
            // VERIFICA SE TIRO ACERTOU SPACESHIP
            if (alien_bullet.collided(spaceship.getBounds())){
                alien_bullet.destroy();
                spaceship.hit();
                spaceship.setHit(true);
                spaceship.setVisible(false);
                cenario.heart_images.remove(cenario.heart_images.size() - 1);
                // SOM
                Sound sound = new Sound();
                sound.selectSound(sound.getSound().SPACESHIP_HIT);
                sound.play();
            }
        }
        
        // DESENHA
        
        // SPACESHIP BLINK IF WAS HIT
        if (spaceship.getHit()){
            if (TIME - PREV_SPACESHIP_BLINK_DELAY > SPACESHIP_BLINK_DELAY) {
                PREV_SPACESHIP_BLINK_DELAY = TIME;
                spaceship.setVisible(!spaceship.getVisible());
                
                if (spaceship_total_blink == spaceship_blink_times){
                    spaceship_blink_times = 0;
                    spaceship.setHit(false);
                }
                spaceship_blink_times++;
            } else {
                if (spaceship.getVisible()) spaceship.draw();
            }
            
        } else {
            PREV_SPACESHIP_BLINK_DELAY = TIME;
            spaceship.draw();
        }
        drawAliens();
        cenario.drawMenu();
        for (Rock rock : rocks) rock.draw();
        if (!special_alien.isDead()) special_alien.draw();
    }
    
    public void Finish(){
        gc.clearRect(0, 0, WIDTH, HEIGHT);
        gc.drawImage(cenario.BACKGROUND_IMAGE, 0, 0);
        
        gc.setFont(DOGICA_PIXEL_BOLD);
        gc.setFill(Color.WHITE) ;
        
        String RESULT;
        if (PLAYER_WON){
            RESULT = "YOU WIN!";
        } else {
            RESULT = "GAME OVER!";
        }
        
        gc.fillText(RESULT, (WIDTH / 2) - (DOGICA_PIXEL_BOLD.getSize() * RESULT.length()) / 2, HEIGHT / 2 - 50);

        // DESENHA SCORE FINAL
        String SCORE_TEXT = "SCORE: " + Integer.toString(cenario.getTotalScore());
        gc.fillText(SCORE_TEXT, (WIDTH / 2) - (DOGICA_PIXEL_BOLD.getSize() * SCORE_TEXT.length()) / 2, HEIGHT / 2 + 50);
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
            rock.setPosY(cenario.getCanvasHeight() - 400);
            POS_X += cenario.getWidthRock() + cenario.getOffsetRock();
        }
    }
    
    public void changeFrontLine(Alien dead_alien){
        double x = dead_alien.getPosX();
        double greater_y = -1;
        for (Alien alien : aliens){
            if (x == alien.getPosX() && greater_y < alien.getPosY()){
                greater_y = alien.getPosY();
            }
        }
        for (Alien alien : aliens){
            if (x == alien.getPosX() && greater_y == alien.getPosY()){
                alien.setFrontLine(true);
            }
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
        double pos_x = findClosestX(spaceship.getPosX());
        double greater_y = -1;
        for (Alien alien : aliens){
            if (pos_x == alien.getPosX() && greater_y < alien.getPosY()){
                greater_y = alien.getPosY();
            }
        }
        for (Alien alien : aliens){
            if (pos_x == alien.getPosX() && greater_y == alien.getPosY()){
                alien_bullet = alien.getBullet();
                alien_bullet.spawn(alien.getPosX(), alien.getPosY());
            }
        }
        
    }
    
    public double findClosestX(double target){
        double posX = aliens.get(0).getPosX();
        double dist = Math.abs(aliens.get(0).getPosX() - target);
        
        for (int i = 1; i < aliens.size(); i++) {
            double cdist = Math.abs(aliens.get(i).getPosX() - target);
            if (cdist < dist) {
                dist = cdist;
                posX = aliens.get(i).getPosX();
            }
        }
        return posX;
    }
    
    public void moveAliens(){
        Alien alienMaxX = Collections.max(aliens, Comparator.comparing(Alien::getPosX));
        Alien alienMinX = Collections.min(aliens, Comparator.comparing(Alien::getPosX));
        Alien alienMaxY = Collections.max(aliens, Comparator.comparing(Alien::getPosY));
        
        if (cenario.itsOnTheLeftWall(alienMinX.getPosX())) {
            for (Alien alien : aliens){
                alien.increaseVelocity();
                alien.changeImage();
                alien.moveDown();
                alien.moveRight();
                alien.setIsMovingToRight(!alien.getIsMovingToRight());
            }
            ALIEN_SHOOT_DELAY -= ALIEN_SHOOT_UPGRADE_DELAY;
        } else if (cenario.itsOnTheRightWall(alienMaxX.getPosX() + alienMaxX.getImageWidth())){
            for (Alien alien : aliens){
                alien.changeImage();
                alien.increaseVelocity();
                alien.moveDown();
                alien.moveLeft();
                alien.setIsMovingToRight(!alien.getIsMovingToRight());
                MOVED_DOWN = true;
            }
            ALIEN_SHOOT_DELAY -= ALIEN_SHOOT_UPGRADE_DELAY;
        } else if (alienMaxY.getIsMovingToRight()){
            for (Alien alien : aliens) {
                alien.changeImage();
                alien.moveRight();
            }
        } else {
            for (Alien alien : aliens) {
                alien.changeImage();
                alien.moveLeft();
            }
        }
        
    }
    
    public void setAliensInitialPosition(ArrayList<Alien> aliens){
        double initialX = aliens.get(0).getVelocityX();
        double initialY = 0;
        double posX = initialX, posY = initialY;
        int counter = 1;
        for (Alien alien : aliens){
            alien.setPosX(posX);
            alien.setPosY(posY);
            posX += 10 + alien.getOffsetX() + alien.getImageWidth();
            if (counter == cenario.getNumberAliensColumn()) {
                posY += alien.getOffsetY() + alien.getImageHeight();
                posX = initialX;
                counter = 0;
            }
            counter++;
        }
    }
    
    public void setAliensImages(ArrayList<Alien> aliens){
        int index = 0;
        for (int i = index; i < 2 * cenario.getNumberAliensColumn(); i++){
            aliens.get(index).setImage(Alien.aliens.GREEN);
            index++;
        }
        for (int i = 0; i < 2 * cenario.getNumberAliensColumn(); i++){
            aliens.get(index).setImage(Alien.aliens.PURPLE);
            index++;
        }
        for (int i = 0; i < cenario.getNumberAliensColumn(); i++){
            aliens.get(index).setImage(Alien.aliens.MIKE);
            index++;
        }
    }
    
    public void setAliensFrontLine(){
        int TOTAL_COLUMNS = cenario.getNumberAliensColumn();
        for (int index = ALIENS_LEFT - cenario.getNumberAliensColumn(); 
                index < ALIENS_LEFT; index++){
            aliens.get(index).setFrontLine(true);
        }
    }
    
    public void drawAliens(){
        for (Alien alien : aliens) {
            alien.draw();
        }
    }
     
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
