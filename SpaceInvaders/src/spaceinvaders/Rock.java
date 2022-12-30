/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spaceinvaders;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;

/**
 * classe responsável pela administração e organização das rochas que estão 
 * presentes no jogo. as rochas funcionam como barreiras de tiros, utilizada
 * pelo player para se proteger dos tiros dos aliens
 * @see <code>Spaceship</code>
 * @see <code>Alien</code>
 * @see <code>Bullet</code>
 * @author michel (nusp: 12609690)
 */
public class Rock {
 
    // CONFIGURAÇÕES LÓGICAS
    private double posX;
    private double posY;
    
    private int life = 20;
    
    private double IMAGE_WIDTH = 180;
    private double IMAGE_HEIGHT = 180;
    
    // CONFIGURAÇÕES GRÁFICAS
    private final double FONT_SIZE = 30;

    private final Image image = new Image("images/rock.png", IMAGE_WIDTH, IMAGE_HEIGHT, false, false);
    private Font DOGICA_PIXEL_BOLD = Font.loadFont("file:src/fonts/dogicapixelbold.ttf", FONT_SIZE);

    GraphicsContext gc;
    
    /**
     * construtor da classe Rock
     * @param gc parte gráfica 
     */
    Rock(GraphicsContext gc){
        this.gc = gc;
    }
    
    /**
     * retorna a largura da imagem da rocha
     * @return <code>double</code> indica a largura da rocha
     */
    public double getImageWidth(){
        return this.IMAGE_WIDTH;
    }
    
    /**
     * modifica a posição x da rocha
     * @param x nova posição x 
     */
    public void setPosX(double x){
        this.posX = x;
    }
    
    /**
     * retorna a posição x da rocha
     * @return <code>double</code> indica a posição x
     */
    public double getPosX(){
        return this.posX;
    }
    
    /**
     * modifica a posição y da rocha
     * @param y <code>double</code> indica a posição y
     */
    public void setPosY(double y){
        this.posY = y;
    }
    
    /**
     * retorna a posição y da rocha
     * @return <code>double</code> indica a posição y
     */
    public double getPosY(){
        return this.posY;
    }
    
    /**
     * retorna um retângulo que fica em volta da imagem da rocha. 
     * esse retângulo é útil para a verificação de colisões
     * @return <code>Rectangle</code> indica fronteira de colisão da rocha
     */
    public Rectangle getBounds() {
        return new Rectangle(posX, posY, IMAGE_WIDTH, IMAGE_HEIGHT);
    }
    
    /**
     * retorna a vida restante da rocha
     * @return <code>int</code> indica quantas vidas faltam
     */
    public int getLife(){
        return this.life;
    }
    
    /**
     * faz com que a rocha tome um tiro e, portanto, sua vida diminua uma unidade
     */
    public void hit(){
        life--;
    }
    
    /**
     * desenha a rocha no mapa
     */
    public void draw(){
        gc.drawImage(image, posX, posY);

        // DESENHA INT SCORE
        gc.setFont(DOGICA_PIXEL_BOLD);
        gc.setFill(Color.WHITE) ;
        String LIFE_TEXT = Integer.toString(life);
        gc.fillText(LIFE_TEXT, 
                posX + (IMAGE_WIDTH - DOGICA_PIXEL_BOLD.getSize() * LIFE_TEXT.length()) / 2, 
                posY + IMAGE_HEIGHT / 2);
    }
    
}
