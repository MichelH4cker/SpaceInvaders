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
public class Bullet extends Sprite {

    // CONFIGURAÇÕES LÓGICAS DA CLASSE BALA
    private boolean destroyed;
    
    private final int IMAGE_HEIGHT = 80;
    private final int IMAGE_WIDTH = 80;
    
    // CONFIGURAÇÕES GRÁFICAS DA BALA
    final Image image = new Image("images/bullet.png", IMAGE_WIDTH, IMAGE_HEIGHT, false, false);
    
    Cenario cenario;
    
    /**
     * construtor da classe bala
     * @param gc parte gráfica da classe
     */
    Bullet(GraphicsContext gc) {
        cenario = new Cenario(gc);
        this.destroyed = true;
        super.gc = gc;
        super.image = image;
        super.WidthImage = IMAGE_WIDTH;
        super.HeightImage = IMAGE_HEIGHT;
        super.velocityX = 0;
        super.velocityY = 15;
    }
    
    /**
     * muda o parâmetro que indica se bala está destruída
     * @param destroyed indica se a bala está destruída
     */
    public void setIsDestroyed(boolean destroyed){
        this.destroyed = destroyed;
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
        positionX = -100;
        positionY = -100;
        this.destroyed = true;
    }
    
    /**
     * dada um parâmetro de posição x e parâmetro de posição y, 
     * as posições iniciais da bala no jogo são dadas por ambos parâmetros
     * @param initial_x posição x inicial da bala no mapa
     * @param initial_y posição y inicial da bala no mapa
     */
    public void spawn(double initial_x, double initial_y){
        this.destroyed = false;
        positionX = initial_x;        
        positionY = initial_y;
    }
    
    /**
     * retorna um retângulo que contorna a imagem da bala, responsável pela 
     * lógica de colisão. este método é diferente do método da classe pai, 
     * por isso foi optado deixar ele separado. para utilizar o método 
     * <code>getBounds</code> da classe pai, teria que haver uma manipulação
     * muito desnecessária nele. serndo assim, fica melhor repetir um método
     * do que deixar o método pai 'feio'
     * @return <code>Rectangle</code> responsável pela lógica de colisão
     */
    @Override
    public Rectangle getBounds() {
        return new Rectangle(positionX + IMAGE_WIDTH / 2, positionY, 2, IMAGE_HEIGHT);
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
