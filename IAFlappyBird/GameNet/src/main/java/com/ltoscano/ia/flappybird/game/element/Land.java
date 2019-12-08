package com.ltoscano.ia.flappybird.game.element;

import com.ltoscano.ia.flappybird.game.base.ElementPosition;
import com.ltoscano.ia.flappybird.game.base.GameElement;
import com.ltoscano.ia.flappybird.game.base.FlappyBirdGame;
import com.ltoscano.ia.flappybird.game.base.FlappyBirdGame.GameState;
import com.ltoscano.ia.flappybird.game.config.LandConfig;
import java.awt.Image;

/**
 * Representa o elemento de solo do jogo
 *
 * @author ltosc
 */
public class Land extends GameElement
{
    public Land(int id, Image character, ElementPosition initialPosition)
    {
        super(id, LandConfig.LAND_WIDTH, LandConfig.LAND_HEIGHT, character, initialPosition);
        
        // Define a velocidade de movimento do elemento
        setMoveSpeed(LandConfig.MOVE_SPEED);
    }
    
    @Override
    public void move()
    {
        // Verifica se o jogo está no estado GameOver
        if(FlappyBirdGame.getInstance().getGameState().equals(GameState.GameOver))
        {
            // Caso esteja, não se mover mais
            return;
        }
        
        // Calcula a nova coordenada X para o solo de acordo com a velocidade do elemento
        int newX = this.getPosition().getX() - this.getMoveSpeed();
        
        // Verifica se o elemento já está totalmente fora da área visível.
        if (newX <= -LandConfig.LAND_WIDTH) 
        {
            // Caso esteja, recoloca o objeto na posição mais à esquerda fora da área visível
            newX = (LandConfig.LAND_WIDTH - 1);
        }
        
        // Define a nova posição X para o elemento
        getPosition().setX(newX);
    }
    
    @Override
    public boolean checkCollision() 
    {
        return false;
    }
}
