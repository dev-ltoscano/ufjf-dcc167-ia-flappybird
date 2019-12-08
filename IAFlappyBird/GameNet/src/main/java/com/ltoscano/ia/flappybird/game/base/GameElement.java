package com.ltoscano.ia.flappybird.game.base;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;

/**
 *  Representa um elemento do jogo
 * 
 * @author ltosc
 */
public abstract class GameElement 
{
    // Identificador do elemento
    private int id;
    
    // Dimensões do elemento
    private int width;
    private int height;
    
    // Imagem do elemento
    private Image character;
    // Posição do inicial do elemento
    private ElementPosition initialPosition;
    // Posição atual do elemento
    private ElementPosition position;
    // Ângulo atual de rotação do elemento
    private double rotation;
    // Velocidade horizontal com que o elemento se move
    private int moveSpeed;
    
    private AffineTransform transform;
    
    // Define se o elemento é visível ou não
    private boolean visible;
    // Define se o elemento pode ser controlado pelo jogador ou não
    private boolean controllable;
    
    /**
     * 
     * @param id Identificador do elemento
     * @param width Largura do elemento de fundo de tela
     * @param height Altura do elemento de fundo de tela
     * @param character Imagem que será usada de fundo de tela
     * @param initialPosition Posição do fundo de tela
     */
    public GameElement(int id, int width, int height, Image character, ElementPosition initialPosition)
    {
        this.id = id;
        this.width = width;
        this.height = height;
        this.character = character;
        this.initialPosition = initialPosition;
        this.position = initialPosition;
        this.rotation = 0;
        this.moveSpeed = 0;
        this.transform = null;
        this.visible = true;
        this.controllable = false;
    }
    
    // Movimenta o elemento
    public abstract void move();
    
    // Verifica se o elemento colidiu com algum outro
    public abstract boolean checkCollision();
    
    // Forma personalizada de desenhar o elemento
    public void draw(Graphics2D graphics) { }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public Image getCharacter() {
        return character;
    }

    public void setCharacter(Image character) {
        this.character = character;
    }

    public ElementPosition getPosition() {
        return position;
    }

    public void setPosition(ElementPosition position) {
        this.position = position;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public int getMoveSpeed() {
        return moveSpeed;
    }

    public void setMoveSpeed(int moveSpeed) {
        this.moveSpeed = moveSpeed;
    }

    public ElementPosition getInitialPosition() {
        return initialPosition;
    }

    public void setInitialPosition(ElementPosition initialPosition) {
        this.initialPosition = initialPosition;
    }

    public double getRotation() {
        return rotation;
    }

    public void setRotation(double rotation) {
        this.rotation = rotation;
    }

    public AffineTransform getTransform() {
        return transform;
    }

    public void setTransform(AffineTransform transform) {
        this.transform = transform;
    }

    public boolean isControllable() {
        return controllable;
    }

    public void setControllable(boolean controllable) {
        this.controllable = controllable;
    }
}
