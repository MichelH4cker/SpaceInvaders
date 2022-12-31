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
 * toda a administração do jogo está presente nessa classe. em termos práticos,
 * essa é a classe mais importante do jogo, responsável por controlar as posições
 * iniciais de todos os objetos, comandar o que irá acontecer, além de possuir
 * todo o esquema de colisão, tiro, movimentação, etc.
 * @author michel (nusp: 12609690)
 */
public class GameManager implements Initializable {

    // CONFIGURAÇÕES LÓGICAS
    private int ALIENS_LEFT = 55;
    private int ROCKS = 3;
    private boolean GAME_OVER = false;
    private boolean guided_shooting = false;
    private boolean PLAYER_WON = false;
    private boolean MOVED_DOWN = false;
    private int spaceship_total_blink = 5;
    private int spaceship_blink_times = 0;
    
    // CONFIGURAÇÕES DE TEMPO
    private long PREV_SPECIAL_ALIEN_SPAWN = 0;
    private long SPECIAL_ALIEN_SPAWN_DELAY = (long) 30e9;
    private long PREV_SPECIAL_ALIEN_MOVEMENT;
    private long SPECIAL_ALIEN_MOVEMENT_DELAY = (long) 0.1e9;
    
    private long PREV_ALIEN_MOVEMENT;
    private long ALIEN_MOVEMENT_DELAY = (long) 1.2e9;
    private long ALIEN_MOVEMENT_UPGRADE_DELAY = (long) 0.2e9;
    
    private long PREV_ALIEN_SHOOT;
    private long ALIEN_SHOOT_DELAY = (long) 1e9; 
    private long ALIEN_SHOOT_UPGRADE_DELAY = (long) 0.1e9;
    
    private long PREV_SPACESHIP_BLINK_DELAY;
    private long SPACESHIP_BLINK_DELAY = (long) 0.4e9; 

    // CONFIGURAÇÕES DE SOM
    private boolean CHANGE_SOUND = false;
   
    // CONDIGURAÇÕES GRÁFICAS
    int WIDTH = 1600;
    int HEIGHT = 900;
    int FONT_SIZE = 70;
    private final Image BACKGROUND_IMAGE = new Image("images/background-image.png", WIDTH, HEIGHT, false, false);
    Font DOGICA_PIXEL_BOLD = Font.loadFont("file:src/fonts/dogicapixelbold.ttf", FONT_SIZE);
    
    // INSTÂNCIA DE OUTRAS CLASSES
    Spaceship spaceship;
    Bullet alien_bullet;
    GraphicsContext gc;
    Alien special_alien;
    ArrayList<Alien> aliens;
    ArrayList<Rock> rocks;
    Cenario cenario;

    /**
     * construtor da classe <code>GameManager</code>
     * @param gc 
     */
    GameManager(GraphicsContext gc){
        this.gc = gc;
        cenario = new Cenario(gc);
        alien_bullet = new Bullet(gc);
        special_alien = new Alien(gc, true);
        special_alien.setImage(Alien.aliens.SPECIAL);
    }
    
    /**
     * retorna parâmetro que indica se o jogo acabou
     * @return <code>boolean</code> indica se o jogo acabou
     */
    public boolean getGameOver(){
        return this.GAME_OVER;
    }
    
    /**
     * incializa o jogo, configura todas as informações iniciais, cria
     * objetos, seleciona qual alien é linha de frente, posiciona rochas
     * em suas posições iniciais, etc. em resumo essa função cuida de todas as 
     * configurações inciais necessárias para que o jogo comece
     */
    public void Start(){
        // INSTANCIA SPACESHIP
        spaceship = new Spaceship(gc);
        
        // CREATE ALIENS
        aliens = new ArrayList<Alien>();
        for (int i = 0; i < ALIENS_LEFT; i++) {
            Alien alien = new Alien(gc);
            aliens.add(alien);
        }
        
        // SET POSICOES DOS ALIENS
        setAliensInitialPosition();
        
        // SET IMAGENS DOS ALIENS
        setAliensImages();
        
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

    /**
     * atualiza o jogo. de maneira geral essa função é responsável por atualizar
     * tudo que precisa ser atualizado instantaneamente, tal como a movimentação
     * das classes.
     * @param TIME tempo atual da animação
     * @param inputKeyboard leitura do teclado
     */
    public void Update(long TIME, ArrayList<String> inputKeyboard){
        checkGameOver();
        Bullet bullet_spaceship = spaceship.getBullet();
        
        // SPACESHIP ACTION
        spaceship.handleAction(inputKeyboard, TIME);
        
        // TIRO DA SPACESHIP
        if (!bullet_spaceship.isDestroyed()){
            // MOVE BALA
            bullet_spaceship.moveUp();
            
            // DESENHA
            bullet_spaceship.draw();
            
            // DESTRÓI SE ESTÁ NA PAREDE SUPERIOR
            if (cenario.itsOnTheTop(bullet_spaceship.getPositionY() + bullet_spaceship.getHeightImage())){
                bullet_spaceship.setIsDestroyed(true);
            }
            
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
            
            // SOM
            Sound sound = new Sound();
            sound.selectSound(Sound.sounds.SPECIAL_ALIEN_MOVE);
            sound.mediaPlayer.setCycleCount(3);
            sound.play();
        }
        
        // DESTROY SPECIAL ALIEN
        if (cenario.itsOnTheRightWall(special_alien.getPositionX())){
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
        
        // HANDLE ALIENS BULLET
        if (alien_bullet != null && !alien_bullet.isDestroyed()){
            // MOVE TIRO DOS ALIENS
            alien_bullet.moveDown();
            
            // DESENHA
            alien_bullet.draw();
            
            // DESTROI SE ACERTOU PAREDE INFERIOR
            if(cenario.itsOnTheBotton(alien_bullet.getPositionY())) {
                alien_bullet.setIsDestroyed(true);
            }
            
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
        
        // COLISÃO ENTRE PROJÉTEIS
        if (bullet_spaceship.collided(alien_bullet.getBounds())){
            bullet_spaceship.destroy();
            alien_bullet.destroy();
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
    
    /**
     * finaliza o jogo. mostra se o player ganhou ou perdeu, além de mostrar
     * qual foi a pontuação final do jogador
     */
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
      
    /**
     * verifica se o jogo acabou. caso a vida da spaceship seja zero ou 
     * os aliens restantes também seja zero, o jogo acaba
     */
    public void checkGameOver(){
        if (ALIENS_LEFT == 0){
            PLAYER_WON = true;
            GAME_OVER = true;
        } else if (spaceship.getLife() == 0){
            PLAYER_WON = false;
            GAME_OVER = true;
        }
    }
    
    /**
     * posiciona as rochas presentes no jogo
     */
    public void setRocks(){
        double POS_X = cenario.getOffsetRock();
        for (Rock rock : rocks){
            rock.setPositionX(POS_X);
            rock.setPositionY(cenario.getCanvasHeight() - 400);
            POS_X += cenario.getWidthRock() + cenario.getOffsetRock();
        }
    }
    
    /**
     * dado um alien que foi atingido, essa função coloca o alien atrás dele 
     * para ser a nova linha de frente 
     * @see <code>Alien</code>
     * @param dead_alien alien atingido
     */
    public void changeFrontLine(Alien dead_alien){
        double x = dead_alien.getPositionX();
        double greater_y = -1;
        for (Alien alien : aliens){
            if (x == alien.getPositionX() && greater_y < alien.getPositionY()){
                greater_y = alien.getPositionY();
            }
        }
        for (Alien alien : aliens){
            if (x == alien.getPositionX() && greater_y == alien.getPositionY()){
                alien.setFrontLine(true);
            }
        }
    }
    
    /**
     * método responsável por fazer os aliens atirarem. esta função decide se o 
     * tiro será um tiro teleguiado, mirando no player, ou se será um tiro aleatório
     */
    public void aliensShoot(){
        if (guided_shooting){
            guidedShoot();
        } else {
            randomShoot();
        }
        guided_shooting = !guided_shooting;
    }
    
    /**
     * faz com que um alien aleatório atire
     */
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
                    alien_bullet.spawn(alien_shooter.getPositionX(), alien_shooter.getPositionY());
                }
            }
            lines_traveled++;
        }
    }
    
    /**
     * faz com que o alien mais próximo do player, em relação a direção x, atire
     */
    public void guidedShoot(){
        double pos_x = findClosestX(spaceship.getPositionX());
        double greater_y = -1;
        for (Alien alien : aliens){
            if (pos_x == alien.getPositionX() && greater_y < alien.getPositionY()){
                greater_y = alien.getPositionY();
            }
        }
        for (Alien alien : aliens){
            if (pos_x == alien.getPositionX() && greater_y == alien.getPositionY()){
                alien_bullet = alien.getBullet();
                alien_bullet.spawn(alien.getPositionX(), alien.getPositionY());
            }
        }
        
    }
    
    /**
     * acha qual é o alien que possui a posição x mais próxima do player
     * @param target posição x a ser comparada
     * @return <code>double</code> indica qual o x mais próximo do x do parâmetro
     */
    public double findClosestX(double target){
        double posX = aliens.get(0).getPositionX();
        double dist = Math.abs(aliens.get(0).getPositionX() - target);
        
        for (int i = 1; i < aliens.size(); i++) {
            double cdist = Math.abs(aliens.get(i).getPositionX() - target);
            if (cdist < dist) {
                dist = cdist;
                posX = aliens.get(i).getPositionX();
            }
        }
        return posX;
    }
    
    /**
     * move alien por alien da lista de aliens
     * @see <code>Alien</code>
     */
    public void moveAliens(){
        Alien alienMaxX = Collections.max(aliens, Comparator.comparing(Alien::getPositionX));
        Alien alienMinX = Collections.min(aliens, Comparator.comparing(Alien::getPositionX));
        Alien alienMaxY = Collections.max(aliens, Comparator.comparing(Alien::getPositionY));
        
        if (cenario.itsOnTheLeftWall(alienMinX.getPositionX())) {
            for (Alien alien : aliens){
                alien.changeImage();
                alien.moveDown();
                alien.moveRight();
                alien.setIsMovingToRight(!alien.getIsMovingToRight());
            }
            ALIEN_MOVEMENT_DELAY -= ALIEN_MOVEMENT_UPGRADE_DELAY;
            ALIEN_SHOOT_DELAY -= ALIEN_SHOOT_UPGRADE_DELAY;
        } else if (cenario.itsOnTheRightWall(alienMaxX.getPositionX() + alienMaxX.getWidthImage())){
            for (Alien alien : aliens){
                alien.changeImage();
                alien.moveDown();
                alien.moveLeft();
                alien.setIsMovingToRight(!alien.getIsMovingToRight());
                MOVED_DOWN = true;
            }
            ALIEN_MOVEMENT_DELAY -= ALIEN_MOVEMENT_UPGRADE_DELAY;
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
    
    /**
     * arranja as posições inicias de todos os aliens do jogo
     * @see <code>Alien</code>
     */
    public void setAliensInitialPosition(){
        double initialX = aliens.get(0).getVelocityX();
        double initialY = 0;
        double posX = initialX, posY = initialY;
        int counter = 1;
        for (Alien alien : aliens){
            alien.setPositionX(posX);
            alien.setPositionY(posY);
            posX += 10 + alien.getOffsetX() + alien.getWidthImage();
            if (counter == cenario.getNumberAliensColumn()) {
                posY += alien.getOffsetY() + alien.getHeightImage();
                posX = initialX;
                counter = 0;
            }
            counter++;
        }
    }
    
    /**
     * dependendo da linha da 'matriz' de aliens, os aliens terão imagens 
     * diferentes, ou seja, serão aliens diferentes um do outro. essa função
     * é responsável por fazer essa varidade de aliens
     * @see <code>Alien</code>
     */
    public void setAliensImages(){
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
    
    /**
     * coloca todos os aliens que estão na frente como linha de frente do 
     * exército de aliens
     * @see <code>Alien</code>
     */
    public void setAliensFrontLine(){
        int TOTAL_COLUMNS = cenario.getNumberAliensColumn();
        for (int index = ALIENS_LEFT - cenario.getNumberAliensColumn(); 
                index < ALIENS_LEFT; index++){
            aliens.get(index).setFrontLine(true);
        }
    }
    
    /**
     * chama a função de desenhar para todos os aliens presentes na lista de alien
     * @see <code>Alien</code>
     */
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
