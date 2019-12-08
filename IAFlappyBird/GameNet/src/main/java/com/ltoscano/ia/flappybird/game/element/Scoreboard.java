package com.ltoscano.ia.flappybird.game.element;

import com.ltoscano.ia.flappybird.game.base.ElementPosition;
import com.ltoscano.ia.flappybird.game.base.GameElement;
import com.ltoscano.ia.flappybird.game.base.FlappyBirdGame;
import com.ltoscano.ia.util.ImageHelper;
import java.awt.Graphics2D;
import java.awt.Image;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.ltoscano.ia.flappybird.game.config.GameConfig;

/**
 * Representa o placar do jogo
 *
 * @author ltosc
 */
public class Scoreboard extends GameElement
{
    // Dimensões do placar
    public static final int SCOREBOARD_WIDTH = 24;
    public static final int SCOREBOARD_HEIGHT = 44;
    
    // Posição Y do placar
    public static final int SCOREBOARD_Y_POS = 20;
    // Espaço entre os dígitos do placar
    public static final int SCOREBOARD_SPACE = 2;
    
    // Lista com cada dígito (0 a 9) do placar
    private ArrayList<Image> scoreImageList;
    
    /**
     * 
     * @param id Identificador do elemento
     * @param scoreAssetsPath Caminho da pasta que contém as imagens dos dígitos
     * @param initialPosition Posição do fundo de tela
     */
    public Scoreboard(int id, String scoreAssetsPath, ElementPosition initialPosition)
    {
        super(id, SCOREBOARD_WIDTH, SCOREBOARD_HEIGHT, null, initialPosition);
        
        this.scoreImageList = new ArrayList<>();
        
        // Carrega cada imagem de dígito do placar
        for(int i = 0; i < 10; i++)
        {
            try 
            {
                this.scoreImageList.add(ImageHelper.loadImage(String.format("%s/%s.png", scoreAssetsPath, String.valueOf(i))));
            } 
            catch (IOException ex)
            {
                Logger.getLogger(Scoreboard.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        // Define que o placar inicial é o zero
        setCharacter(scoreImageList.get(0));
    }
    
    public Image getScoreImage(int score)
    {
        return this.scoreImageList.get(score);
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
    
    @Override
    public void draw(Graphics2D graphics)
    {
        int score = FlappyBirdGame.getInstance().getMaxScoreToBirds();
        int scoreWidth = 0;
        int scoreX;

        if (score == 0)
        {
            graphics.drawImage(getScoreImage(0),
                    ((GameConfig.GAME_WIDTH - Scoreboard.SCOREBOARD_WIDTH) / 2),
                    Scoreboard.SCOREBOARD_Y_POS,
                    null);
        } 
        else 
        {
            while (score > 0) 
            {
                scoreWidth += SCOREBOARD_WIDTH + SCOREBOARD_SPACE;
                score = (int) Math.floor(score / 10);
            }

            scoreWidth -= SCOREBOARD_SPACE;
            score = FlappyBirdGame.getInstance().getMaxScoreToBirds();
            scoreX = (GameConfig.GAME_WIDTH + scoreWidth) / 2 - SCOREBOARD_WIDTH;

            while (score > 0)
            {
                graphics.drawImage(getScoreImage(score % 10),
                        scoreX,
                        SCOREBOARD_Y_POS,
                        null);

                scoreX -= SCOREBOARD_WIDTH + SCOREBOARD_SPACE;
                score = (int) Math.floor(score / 10);
            }
        }
    }
}