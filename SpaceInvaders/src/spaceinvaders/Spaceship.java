/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spaceinvaders;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

/**
 *
 * @author michel
 */
public class Spaceship {
    
    // CONFIGURAÇÕES DE TEMPO
    private long PREV_SHOOT = 0;
    private long SHOOT_DELAY = (long) 0.65e9;
    
    // CONFIGURAÇÕES GRÁFICAS
    private final int IMAGE_HEIGHT = 80;
    private final int IMAGE_WIDTH = 80;

    private int CANVAS_WIDTH = 1600;
    private int CANVAS_HEIGHT = 900;
    
    private int OFFSET_SPAWN = 60;
    
    // CONFIGURAÇÕES LÓGICAS
    private final double posY;
    private double posX = 500.0;
    private int life = 3;
    private double velocity = 7.0;
    private boolean dead;
    private boolean hit = false;
    private boolean visible = true;
    
    private Bullet bullet;
    
    GraphicsContext gc;
    Cenario cenario;
    
    @FXML
    final Image image = new Image("images/spaceship.png", IMAGE_WIDTH, IMAGE_HEIGHT, false, false);
    
    /**
     * construtor da classe Spaceship
     * @param gc parte gráfica
     */
    Spaceship(GraphicsContext gc) {
        this.gc = gc;
        cenario = new Cenario(gc);
        image.isPreserveRatio();
        bullet = new Bullet(gc);
        posY = CANVAS_HEIGHT - OFFSET_SPAWN - cenario.getSizeBottonMenu();
        dead = false;
    }
    
    /**
     * retorna a posição x da nave
     * @return <code>double</code> indica qual a posição x da nave
     */
    public double getPosX(){
        return this.posX;
    }
    
    /**
     * retorna a posição y da nave
     * @return <code>double</code> indica qual a posição y da nave
     */
    public double getPosY(){
        return this.posY;
    }
    
    /**
     * retorna um retângulo que cerca a nave. esse retângulo repreenta qual é
     * a área de colisão da nave
     * @return <code>Rectangle</code> indica o retângulo de colisão da nave
     */
    public Rectangle getBounds() {
        return new Rectangle(posX, posY, IMAGE_WIDTH, IMAGE_HEIGHT);
    }
    
    /**
     * retorna qual a vida restante da nave
     * @return <code>int</code> indica quantas vidas restam para a nave
     */
    public int  getLife(){
        return life;
    }
    
    /**
     * muda a visibilidade da nave
     * @param visible indica se a nave ser visível(<u>true</u>) ou invísivel (<u>false</u>)
     */
    public void setVisible(boolean visible) {
       this.visible = visible;
    }
    
    /**
     * retorna se a nave está visível(<b>true</b>) ou invisível (<b>false</b>)
     * @return 
     */
    public boolean getVisible(){
        return this.visible;
    }
    
    /**
     * marca de a nave foi acertada por um tiro ou não
     * @param hit indica se foi acertada 
     */
    public void setHit(boolean hit){
        this.hit = hit;
    }
    
    /**
     * verifica se recenetemente a nave foi acertada por um tiro
     * @return <code>boolean</code> indica se a nave foi acertada por um tiro
     */
    public boolean getHit(){
        return this.hit;
    }
    
    /**
     * faz com que a nave sofra dano e, como consequência, sua vida seja diminuída
     */
    public void hit(){
        life -= 1;
    }
    
    /**
     * pega o atributo <code>Bullet</code> da nave
     * @see <code>Bullet</code>
     * @return <code>Bullet</code> indica a bala pertencente à nave
     */
    public Bullet getBullet(){
        return this.bullet;
    }
    
    /**
     * desenha na tela a nave
     */
    public void draw() {
        gc.drawImage(image, posX, posY);
    }
    
    /**
     * administra as ações da bala. se o usuário apertar 'x' a nave irá 
     * disparar um tiro; se o usuário apertar 'left' a nave se moverá para a esquerda
     * e se o usuário apertar 'right' a nave se moverá para direita
     * @param inputKeyboard guarda quais teclas foram apertadas
     * @param TIME tempo atual do jogo
     */
    public void handleAction(ArrayList<String> inputKeyboard, long TIME){
        if (inputKeyboard.contains("X") && bullet.isDestroyed() && TIME - PREV_SHOOT > SHOOT_DELAY) {
            bullet.spawn(posX, posY);
            PREV_SHOOT = TIME;
            // SOM
            Sound sound = new Sound();
            sound.selectSound(sound.getSound().SPACESHIP_SHOOT);
            sound.play();
        } 
        if (inputKeyboard.contains("LEFT") && !cenario.itsOnTheLeftWall(posX)) {
            moveLeft();
        } else if (inputKeyboard.contains("RIGHT") && !cenario.itsOnTheRightWall(posX + IMAGE_WIDTH)) {
            moveRight();
        }
    }
    
    /**
     * move nave para direita com base em sua velocidade x
     */
    public void moveRight() {
        posX += velocity;
    }

    /**
     * move nava para esquerda com base em sua velocidade y
     */
    public void moveLeft(){
        posX -= velocity;
    }
    
}
