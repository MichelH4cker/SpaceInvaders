/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spaceinvaders;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.shape.Rectangle;

/**
 * Classe Bullet responsável por ser o projétil de todos os personagens do jogo
 * esta função engloba a funcionalidade de colisão, muito importante para o
 * desenvolvimento do jogo
 * @author michel (nusp: 12609690)
 */
public class Bullet {

    // CONFIGURAÇÕES LÓGICAS DA CLASSE BALA
    private double posX;
    private double posY;
    private double velocity = 15.0;
    private boolean destroyed;
    
    private final int IMAGE_HEIGHT = 80;
    private final int IMAGE_WIDTH = 80;
    
    private final int IMAGE_ALIEN_HEIGHT = 50;
    private final int IMAGE_ALIEN_WIDTH = 50;

    // CONFIGURAÇÕES GRÁFICAS DA BALA
    final Image image = new Image("images/bullet.png", IMAGE_WIDTH, IMAGE_HEIGHT, false, false);
    
    GraphicsContext gc;
    Cenario cenario;
    
    /**
     * construtor da classe bala
     * @param gc parte gráfica da classe
     */
    Bullet(GraphicsContext gc) {
        this.gc = gc;
        this.destroyed = true;
        cenario = new Cenario(gc);
    }
    
    /**
     * retorna a posicação y da bala
     * @return <code>double</code> posição y da bala
     */
    public double getPosY(){
        return posY;
    }
    
    /**
     * retorna se a bala está destruída
     * @return <code>boolean</code> indica se a bala está destruída
     */
    public boolean isDestroyed(){
        return this.destroyed;
    }
    
    /**
     * responsável por administração a destruição lógica da bala
     */
    public void destroy(){
        posX = 0;
        posY = 0;
        this.destroyed = true;
    }
    
    /**
     * desenha bala no mapa
     */
    public void draw(){
        gc.drawImage(image, posX, posY);
    }
    
    /**
     * dada um parâmetro de posição x e parâmetro de posição y, as posições iniciais da bala no
     * jogo são dadas por ambos parâmetros
     * @param initial_x posição x inicial da bala no mapa
     * @param initial_y posição y inicial da bala no mapa
     */
    public void spawn(double initial_x, double initial_y){
        this.destroyed = false;
        posX = initial_x;        
        posY = initial_y;
    }
    
    /**
     * movimenta bala para cima
     * utilizado no tiro da spaceship
     * @see <code>Spaceship</code>
     */
    public void moveUp(){
        posY -= velocity;
        draw();
        destroyed = cenario.itsOnTheTop(posY + IMAGE_HEIGHT);
    }
    
    /**
     * movimenta bala para baixo
     * utilizado no tiro dos alien
     * @see <code>Alien</code>
     */
    public void moveDown(){
        posY += velocity;
        draw();
        destroyed = cenario.itsOnTheBotton(posY);
    }
    
    /**
     * retorna um retângulo que contorna a imagem da bala, responsável pela 
     * lógica de colisão
     * @return <code>Rectangle</code> responsável pela lógica de colisão
     */
    public Rectangle getBounds() {
        return new Rectangle(posX + IMAGE_WIDTH / 2, posY, 2, IMAGE_HEIGHT);
    }
    
    /**
     * dado um retângulo qualquer, esta função verifica se a bala colidiu com o
     * parâmetro passado. o retângulo passado como parâmetro pode ser tanto um
     * Alien, uma Rock ou até mesmo a Spacechip
     * @see <code>Alien</code>
     * @see <code>Rock</code>
     * @see <code>Spaceship</code>
     * @param target_rectangle retângulo a ser verificado se colidiu com bala
     * @return <code>boolean</code> indica se colidiu ou não
     */
    public boolean collided(Rectangle target_rectangle){
        Rectangle rec_bullet = getBounds();
        if (rec_bullet.getBoundsInParent().intersects(target_rectangle.getBoundsInParent())) {
            return true;
        }
        return false;
    }

}
