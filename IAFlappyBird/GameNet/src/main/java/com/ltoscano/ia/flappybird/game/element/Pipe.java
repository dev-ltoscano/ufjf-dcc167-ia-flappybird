package com.ltoscano.ia.flappybird.game.element;

import com.ltoscano.ia.flappybird.game.base.ElementPosition;
import com.ltoscano.ia.flappybird.game.base.GameElement;
import com.ltoscano.ia.flappybird.game.base.FlappyBirdGame;
import com.ltoscano.ia.flappybird.game.base.FlappyBirdGame.GameState;
import java.awt.Image;
import com.ltoscano.ia.flappybird.game.config.GameConfig;
import com.ltoscano.ia.flappybird.game.config.PipeConfig;

/**
 *  Representa os canos do jogo
 * 
 * @author ltosc
 */
public class Pipe extends GameElement
{
    // Identificador do cano
    private final int pipeId;
    
    // Tipo de cano (Acima ou abaixo)
    public enum PipeType { Up, Down };
    private final PipeType pipeType;
    
    /**
     * 
     * @param id Identificador do elemento
     * @param character Imagem que será usada de fundo de tela
     * @param initialPosition Posição do fundo de tela
     * @param pipeId Identificador do cano
     * @param pipeType Tipo do cano
     */
    public Pipe(int id, Image character, ElementPosition initialPosition, int pipeId, PipeType pipeType)
    {
        super(id, PipeConfig.PIPE_WIDTH, PipeConfig.PIPE_HEIGHT, character, initialPosition);
        
        this.pipeId = pipeId;
        this.pipeType = pipeType;
        
        // Define a velocidade de movimento do cano
        setMoveSpeed(PipeConfig.MOVE_SPEED);
    }
    
    /**
     * Cria uma nova coordenada X para o cano
     * 
     * @param gameWidth Largura da tela do jogo
     * @param index Índice do cano
     * @return Nova coordenada X para o cano
     */
    public static double getNewPipeX(int gameWidth, int index)
    {
        return ((gameWidth + PipeConfig.PIPE_WIDTH) * (1 + index * 0.5));
    }
    
    /**
     * Cria uma nova coordenada Y para o cano
     * 
     * @return Nova coordenada Y para o cano
     */
    public static double getNewPipeY()
    {
        return (Math.floor(Math.random() * (PipeConfig.PIPE_MAX_Y - PipeConfig.PIPE_MIN_Y)) + PipeConfig.PIPE_MIN_Y);
    }
    
    @Override
    public void move() 
    {
        // Verifica se o estado do jogo é GameOver
        if(FlappyBirdGame.getInstance().getGameState().equals(GameState.GameOver))
        {
            // Caso seja, não se mover mais
            return;
        }
        
        // Obtém as novas coordenadas para o cano
        int xPos = getPosition().getX() - getMoveSpeed();
        int yPos = getPosition().getY();
        
        // Verifica se a posição X do cano já está fora da área visível do jogo
        if(xPos <= -PipeConfig.PIPE_WIDTH)
        {
            // Caso esteja, coloca o cano mais à esquerda da tela, ainda fora da área visível
            xPos = GameConfig.GAME_WIDTH;
            
            /**
             * Define uma nova coordenada Y para o par de canos superior e inferior.
             * O cano superior é tido como principal em relação ao inferior
             */
            if(pipeType.equals(PipeType.Up))
            {
                // Gera uma nova coordenada Y para o cano superior
                yPos = (int)this.getNewPipeY();
                
                // Obtém a instância do jogo
                FlappyBirdGame flappyGame = FlappyBirdGame.getInstance();
                
                // Busca pelo cano inferior que é par do cano atual
                int pipe = this.pipeId + 1;
                Pipe pipeDown = flappyGame.getPipeById(pipe);
                
                // Verifica se o cano inferior foi encontrado
                if(pipeDown != null)
                {
                    /**
                     * Define uma nova coordenada Y para o cano inferior, com base
                     * na coordenada Y do cano superior.
                     */
                    int yDownPos = yPos;
                    yDownPos -= (PipeConfig.PIPE_HEIGHT + PipeConfig.PIPE_SPACE_SIZE);
                    pipeDown.getPosition().setY(yDownPos);
                    flappyGame.setPipeById(pipe, pipeDown);
                }
            }
        }
        
        // Define as novas coordenadas do cano atual
        getPosition().setX(xPos);
        getPosition().setY(yPos);
    }
    
    @Override
    public boolean checkCollision()
    {
        return false;
    }
    
    public int getPipeId()
    {
        return pipeId;
    }
}
