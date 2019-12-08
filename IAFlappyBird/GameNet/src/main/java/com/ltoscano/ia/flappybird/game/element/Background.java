package com.ltoscano.ia.flappybird.game.element;

import com.ltoscano.ia.flappybird.game.base.ElementPosition;
import com.ltoscano.ia.flappybird.game.base.GameElement;
import java.awt.Image;

/**
 * Fundo de tela para o jogo Flappy Bird
 * 
 * @author ltosc
 */
public class Background extends GameElement
{
    public Background(int id, int width, int height, Image character, ElementPosition initialPosition) 
    {
        super(id, width, height, character, initialPosition);
    }

    @Override
    public void move() 
    {
        
    }
    
    @Override
    public boolean checkCollision() 
    {
        return false;
    }
}