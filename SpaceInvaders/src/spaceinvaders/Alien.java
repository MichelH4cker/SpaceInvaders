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

/**
 * essa classe é responsável pela administração de todos os aliens presentes na 
 * minha versão do jogo <u>Space Invaders</u>. tanto os aliens normais como os
 * especiais são configurados nessa classe
 * @author michel (nusp: 12609690)
 */
public class Alien extends Sprite {
    
    // CONFIGURAÇÕES LÓGICAS PARA O ALIEN
    private boolean dead;
    private boolean isMovingToRight;
    private boolean frontLine;
    
    Bullet bullet;
    
    // CONFIGURAÇÕES GRÁFICAS
    private boolean change_image = true;
    
    private double OFFSET_X = 23.0;
    private double OFFSET_Y = 10.0;
    
    private int IMAGE_WIDTH = 70;
    private int IMAGE_HEIGHT = 70;
    
    private final Image alien0  = new Image("images/alien-0.png" , IMAGE_WIDTH, IMAGE_HEIGHT, false, false);
    private final Image alien1a = new Image("images/alien-1a.png", IMAGE_WIDTH, IMAGE_HEIGHT, false, false);
    private final Image alien1b = new Image("images/alien-1b.png", IMAGE_WIDTH, IMAGE_HEIGHT, false, false);
    private final Image alien2a = new Image("images/alien-2a.png", IMAGE_WIDTH, IMAGE_HEIGHT, false, false);
    private final Image alien2b = new Image("images/alien-2b.png", IMAGE_WIDTH, IMAGE_HEIGHT, false, false);
    
    private final Image special_alien_a = new Image("images/special-alien-a.png", IMAGE_WIDTH, IMAGE_HEIGHT, false, false);
    private final Image special_alien_b = new Image("images/special-alien-b.png", IMAGE_WIDTH, IMAGE_HEIGHT, false, false);

    private Image image_0;
    private Image image_1;
    
    private Image image;

    enum aliens {
        MIKE,
        GREEN,
        PURPLE,
        SPECIAL;
    }
    
    private aliens alien;
    
    /**
     * Construtor do alien normal
     * @param gc parte gráfica do jogo
     */
    public Alien(GraphicsContext gc) {
        bullet = new Bullet(gc);
        this.frontLine = false;
        this.isMovingToRight = true;
        super.gc = gc;
        super.image = image;
        super.WidthImage = IMAGE_WIDTH;
        super.HeightImage = IMAGE_HEIGHT;
        super.velocityX = 20;
        super.velocityY = 30;
    }
    
    /**
     * Construtor do alien especial
     * @param gc parte gráfica do jogo
     * @param special segundo parâmetro para permitir um segundo construtor
     */
    public Alien(GraphicsContext gc, boolean special){
        dead = true;
        super.gc = gc;
        super.positionX = -100; // só para setar uma posição inicial
        super.positionY = -100; // e a memória não 'chutar' um valor
        super.image = image;
        super.WidthImage = IMAGE_WIDTH;
        super.HeightImage = IMAGE_HEIGHT;
        super.velocityX = 20;
        super.velocityY = 30;
    }
    
    /**
     * retorna qual o é o alien a ser desenhado
     * @return <code>aliens</code> tipo do alien
     */
    public aliens getAlienType(){
        return alien;
    }
    
    /**
     * retorna se o alien está se movendo para direita
     * @return <code>boolean</code> que indica se está se movendo para direita
     */
    public boolean getIsMovingToRight(){
        return this.isMovingToRight;
    }
    
    /**
     * muda o atributo que indica se o alien está se movendo para direita
     * @param isMovingToRight indica se o alien está se movendo para direita
     */
    public void setIsMovingToRight(boolean isMovingToRight){
        this.isMovingToRight = isMovingToRight;
    }
    
    /**
     * retorna se o alien está na linha de frente do exército de aliens
     * @return <code>boolean</code> indica se é linha de frente
     */
    public boolean isFrontLine(){
        return this.frontLine;
    }
    
    /**
     * muda o atributo que indica se o alien está na linha de frente 
     * @param frontLine indica se o alien é linha de frente
     */
    public void setFrontLine(boolean frontLine){
        this.frontLine = frontLine;
    }
    
    /**
     * retorna o espaçamento em x, útil para gráfico
     * @return <code>double</code> espaçamento em x
     */
    public double getOffsetX(){
        return this.OFFSET_X;
    }
    
    /**
     * retorna o espaçamento em y, útil para gráfico
     * @return <code>double</code> espaçamento em y
     */
    public double getOffsetY(){
        return this.OFFSET_Y;        
    }
    
    /**
     * de acordo com o tipo de alien passado como parâmetro, os atributos 
     * <code>image_0</code> e <code>image_1</code> são modificados
     * estes atributos são responsáveis por serem a parte gráfica de um alien, 
     * além de dar noção de movimento.
     * @param alien qual o tipo de alien
     */
    public void setImage(aliens alien){
        switch (alien){
            case MIKE:
                image_0 = alien0;
                image_1 = alien0;
                break;
            case GREEN:
                image_0 = alien1a;
                image_1 = alien1b;
                break;
            case PURPLE:
                image_0 = alien2a;
                image_1 = alien2b;
                break;
            case SPECIAL: 
                image_0 = special_alien_a;
                image_1 = special_alien_b;
            default:
                break;
        }
    }
    

    /**
     * retorna uma classe <code>Bullet</code> do alien 
     * @see <code>Bullet</code>
     * @return <code>Bullet</code> 
     */
    public Bullet getBullet(){
        return this.bullet;
    }
    
    /**
     * seta as configurações necessárias para o alien ser destruído do jogo
     */
    public void destroy(){
        positionX = 50;
        positionY = 0;
        dead = true;
    }
    
    /**
     * coloca o alien no jogo
     */
    public void spawn(){
        positionX = 0;
        positionY = 0;
    }
    
    /**
     * muda o atributo que indica se o alien está morto
     * @param dead atributo que indica se o alien está morto
     */
    public void setIsDead(boolean dead){
        this.dead = dead;
    }
    
    /**
     * retorna se o alien está morto
     * @return <code>boolean</code> indica se o alien está morto
     */
    public boolean isDead(){
        return this.dead;
    }
    
    /**
     * para dar noção de movimento para os aliens, esta função é muito útil.
     * a cada passada, essa função é chamada para trocar as imagens dos aliens,
     * dando uma leve noção de movimento
     */
    public void changeImage(){
        if (change_image){
            image = image_0;
        } else {
            image = image_1;
        }
        change_image = !change_image;
        super.image = image;
    }
    
}
