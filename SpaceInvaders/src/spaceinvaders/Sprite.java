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
 * classe pai de todos objetos que possuem movimentação, tiro, imagem e vida 
 * @author michel (nusp: 12609690)
 */
public class Sprite {
    
    protected Image image;
    protected int life;
    protected double positionX;
    protected double positionY;    
    protected double velocityX;
    protected double velocityY;
    protected double WidthImage;
    protected double HeightImage;

    GraphicsContext gc;
    
    /**
     * retorna a imagem do sprite
     * @return <code>Image</code> indica a imagem que representa o sprite
     */
    public Image getImage() {
        return image;
    }

    /**
     * muda a imagem do sprite
     * @param image indica a imagem que representa o sprite
     */
    public void setImage(Image image) {
        this.image = image;
    }

    /**
     * retorna a vida restante deste sprite
     * @return <code>int</code> indica quantidade de vidas restantes
     */
    public int getLife(){
        return life;
    }
    
    /**
     * tira uma unidade da vida do sprite
     */
    public void hit(){
        life--;
    }
    
    /**
     * retorna a posição x do sprite
     * @return <code>double</code> indica qual a posição x do sprite
     */
    public double getPositionX() {
        return positionX;
    }

    /**
     * muda a posição x
     * @param positionX indica posição x do sprite 
     */
    public void setPositionX(double positionX) {
        this.positionX = positionX;
    }

    /**
     * retorna a posição y do sprite
     * @return <code>double</code> indica qual a posição y do sprite
     */
    public double getPositionY() {
        return positionY;
    }

    /**
     * muda a posição y
     * @param positionY indica posição y do sprite 
     */
    public void setPositionY(double positionY) {
        this.positionY = positionY;
    }

    /**
     * retorna a velocidade x do sprite
     * @return <code>double</code> indica qual a velocidade x do sprite
     */
    public double getVelocityX() {
        return velocityX;
    }

    /**
     * muda a velocidade x
     * @param velocityX indica a velocidade y do sprite 
     */
    public void setVelocityX(double velocityX) {
        this.velocityX = velocityX;
    }

    /**
     * retorna a velocidade y do sprite
     * @return <code>double</code> indica qual a velocidade y do sprite
     */
    public double getVelocityY() {
        return velocityY;
    }

    /**
     * muda a velocidade y
     * @param velocityY indica a velocidade y do sprite
     */
    public void setVelocityY(double velocityY) {
        this.velocityY = velocityY;
    }

    /**
     * retorna a largura da imagem
     * @return <code>double</code> indica a largura da imagem
     */
    public double getWidthImage() {
        return WidthImage;
    }

    /**
     * retorna a altura da imagem
     * @return <code>double</code> indica a altura da imagem
     */
    public double getHeightImage() {
        return HeightImage;
    }

    /**
     * move sprite para direita com base em sua velocidade x
     */
    public void moveRight() {
        positionX += velocityX;
    }

    /**
     * move sprite para esquerda com base em sua velocidade x
     */
    public void moveLeft(){
        positionX -= velocityX;
    }
    
    /**
     * movimenta sprite para cima com base em sua velocidade y
     */
    public void moveUp(){
        positionY -= velocityY;
    }
    
    /**
     * movimenta sprite para baixo com base em sua velocidade y
     */
    public void moveDown(){
        positionY += velocityY;
    }
    
    /**
     * retorna um retângulo que cerca o sprite. esse retângulo repreenta qual é
     * a área de colisão do sprite
     * @return <code>Rectangle</code> indica o retângulo de colisão do sprite
     */
    public Rectangle getBounds() {
        return new Rectangle(positionX, positionY, WidthImage, HeightImage);
    }
    
    /**
     * desenha sprite na tela
     */
    public void draw() {
        gc.drawImage(image, positionX, positionY);
    }
}
