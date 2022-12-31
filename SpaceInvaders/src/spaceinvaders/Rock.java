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
public class Rock extends Sprite {
    
    // CONFIGURAÇÕES GRÁFICAS
    private double IMAGE_WIDTH = 180;
    private double IMAGE_HEIGHT = 180;
    
    private final double FONT_SIZE = 30;

    private final Image image = new Image("images/rock.png", IMAGE_WIDTH, IMAGE_HEIGHT, false, false);
    private Font DOGICA_PIXEL_BOLD = Font.loadFont("file:src/fonts/dogicapixelbold.ttf", FONT_SIZE);

    /**
     * construtor da classe Rock
     * @param gc parte gráfica 
     */
    Rock(GraphicsContext gc){
        super.gc = gc;
        super.image = image;
        super.WidthImage = IMAGE_WIDTH;
        super.HeightImage = IMAGE_HEIGHT;
        super.life = 20;
    }
    
    
    /**
     * desenha a rocha no mapa. o método de desenhar da rocha é diferente de todos
     * os outros sprites devido à sua vida ser no interior da sua imagem.
     * dessa forma, é mais prático manter um método diferente de desenho 
     * do que mudar desnecessariamente o método da classe pai.
     */
    @Override
    public void draw(){
        gc.drawImage(image, positionX, positionY);

        // DESENHA INT SCORE
        gc.setFont(DOGICA_PIXEL_BOLD);
        gc.setFill(Color.WHITE) ;
        String LIFE_TEXT = Integer.toString(life);
        gc.fillText(LIFE_TEXT, 
                positionX + (IMAGE_WIDTH - DOGICA_PIXEL_BOLD.getSize() * LIFE_TEXT.length()) / 2, 
                positionY + IMAGE_HEIGHT / 2);
    }
    
}
