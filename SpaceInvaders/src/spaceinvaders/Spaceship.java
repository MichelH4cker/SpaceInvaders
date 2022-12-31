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
 * essa classe administra todas as funcionalidades da nave 
 * do jogo, controlado pelo jogador
 * @author michel (nusp: 12609690)
 */
public class Spaceship extends Sprite {
    
    // CONFIGURAÇÕES DE TEMPO
    private long PREV_SHOOT = 0;
    private long SHOOT_DELAY = (long) 0.65e9;
    
    // CONFIGURAÇÕES GRÁFICAS
    private final int IMAGE_HEIGHT = 80;
    private final int IMAGE_WIDTH = 80;
    private int OFFSET_SPAWN = 60;
    
    // CONFIGURAÇÕES LÓGICAS
    private boolean hit = false;
    private boolean visible = true;
    
    private Bullet bullet;
    
    Cenario cenario;
    
    @FXML
    final Image image = new Image("images/spaceship.png", IMAGE_WIDTH, IMAGE_HEIGHT, false, false);
    
    /**
     * construtor da classe Spaceship
     * @param gc parte gráfica
     */
    Spaceship(GraphicsContext gc) {
        cenario = new Cenario(gc);
        bullet = new Bullet(gc);
        image.isPreserveRatio();
        super.image = image;
        super.WidthImage = 80;
        super.HeightImage = 80;
        super.positionX = 500;
        super.positionY = cenario.getCanvasHeight() - OFFSET_SPAWN - cenario.getSizeBottonMenu();
        super.velocityX = 7;
        super.life = 3;
        super.gc = gc;
    }
     
    /**
     * muda a visibilidade da nave
     * @param visible indica se a nave será visível(<u>true</u>) ou invísivel (<u>false</u>)
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
     * marca se a nave foi acertada por um tiro ou não
     * @param hit indica se foi acertada 
     */
    public void setHit(boolean hit){
        this.hit = hit;
    }
    
    /**
     * verifica se, recentemente, a nave foi acertada por um tiro
     * @return <code>boolean</code> indica se a nave foi acertada por um tiro
     */
    public boolean getHit(){
        return this.hit;
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
     * administra as ações da bala. se o usuário apertar 'x' a nave irá 
     * disparar um tiro; se o usuário apertar 'left' a nave se moverá para a esquerda
     * e se o usuário apertar 'right' a nave se moverá para direita
     * @param inputKeyboard guarda quais teclas foram apertadas
     * @param TIME tempo atual do jogo
     */
    public void handleAction(ArrayList<String> inputKeyboard, long TIME){
        if (inputKeyboard.contains("X") && bullet.isDestroyed() && TIME - PREV_SHOOT > SHOOT_DELAY) {
            bullet.spawn(positionX, positionY);
            PREV_SHOOT = TIME;
            // SOM
            Sound sound = new Sound();
            sound.selectSound(sound.getSound().SPACESHIP_SHOOT);
            sound.play();
        } 
        if (inputKeyboard.contains("LEFT") && !cenario.itsOnTheLeftWall(positionX)) {
            moveLeft();
        } else if (inputKeyboard.contains("RIGHT") && !cenario.itsOnTheRightWall(positionX + WidthImage)) {
            moveRight();
        }
    }
    
}
