package com.ltoscano.ia.flappybird.game.base;

/**
 * Representa a localização (X, Y) de um elemento do jogo na tela
 *
 * @author ltosc
 */
public class ElementPosition 
{
    private int x;
    private int y;
    
    public ElementPosition()
    {
        this.x = 0;
        this.y = 0;
    }
    
    /**
     * 
     * @param x Coordenada X do elemento na tela
     * @param y Coordenada Y do elemento na tela
     */
    public ElementPosition(int x, int y)
    {
        this.x = x;
        this.y = y;
    }
    
    public int getX() {
        return x;
    }
    
    public void setX(int x) {
        this.x = x;
    }
    
    public int getY() {
        return y;
    }
    
    public void setY(int y) {
        this.y = y;
    }
}
