package com.ltoscano.ia.flappybird.game.base;

import com.ltoscano.ia.flappybird.game.base.FlappyBirdGame.GameMode;
import com.ltoscano.ia.flappybird.game.base.FlappyBirdGame.GameState;
import com.ltoscano.ia.flappybird.game.element.Bird;
import com.ltoscano.ia.util.KeyBoardController;
import com.ltoscano.ia.flappybird.game.element.Scoreboard;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import javax.swing.JPanel;
import com.ltoscano.ia.flappybird.game.config.GameConfig;
import com.ltoscano.ia.genetic.Generation;

/**
 *
 * @author ltosc
 */
public class FlappyBirdGamePanel extends JPanel implements Runnable
{
    // Thread do jogo
    private Thread gameThread;
    
    // Estados do jogo
    private boolean running;
    private boolean paused;
    
    // Controle do jogo
    private final KeyBoardController controller;
    
    public FlappyBirdGamePanel()
    {
        this.controller = KeyBoardController.getInstance();
    }
    
    /**
     * Inicia o jogo
     * 
     */
    public void startGame()
    {
        this.gameThread = new Thread(this);
        this.gameThread.start();
    }
    
    /**
     * Pausa o jogo
     * 
     */
    public void pauseGame()
    {
        paused = !paused;
    }
    
    /**
     * Reseta o jogo
     * 
     */
    public void resetGame()
    {
        if (FlappyBirdGame.getInstance().getGameState().equals(GameState.GameOver)) 
        {
            FlappyBirdGame.getInstance().resetGame();
        }
    }
    
    /**
     * Para o jogo
     * 
     */
    public void stopGame()
    {
        this.running = false;
    }
    
    /**
     * Aumenta o tempo de cada frame do jogo. Diminui o FPS
     * 
     */
    public void increaseGameFrameSpeed()
    {
        long frameDelay = FlappyBirdGame.getInstance().getFrameDelay();
        frameDelay += 1L;

        FlappyBirdGame.getInstance().setFrameDelay(frameDelay);
    }
    
    /**
     * Diminui o tempo de cada frame do jogo. Aumenta o FPS
     * 
     */
    public void decreaseGameFrameSpeed()
    {
        long frameDelay = FlappyBirdGame.getInstance().getFrameDelay();
        frameDelay -= 1L;

        if (frameDelay < 0) {
            frameDelay = 0;
        }

        FlappyBirdGame.getInstance().setFrameDelay(frameDelay);
    }
    
    /**
     * Laço principal do jogo
     */
    @Override
    public void run()
    {
        this.running = true;
        
        while(running) 
        {
            try
            {
                // Verifica se a tecla S foi pressionada para dar início ao jogo
                if (FlappyBirdGame.getInstance().getGameState().equals(GameState.NotStarted) 
                        && controller.isKeyDown(KeyEvent.VK_S)) 
                {
                    FlappyBirdGame.getInstance().startGame();
                }
                else if(FlappyBirdGame.getInstance().getGameMode().equals(GameMode.Automatic) && 
                        (GameConfig.SCORE_LIMIT != -1) &&
                        (FlappyBirdGame.getInstance().getMaxScoreToBirds() >= GameConfig.SCORE_LIMIT) &&
                        !FlappyBirdGame.getInstance().getGameState().equals(GameState.GameOver))
                {
                    for(Bird bird : FlappyBirdGame.getInstance().getBirds())
                    {
                        bird.setAlive(false);
                    }
                }
                else
                {
                    if (!paused) 
                    {
                        // Atualiza o jogo
                        updateUI();
                    }
                }
                
                Thread.sleep(FlappyBirdGame.getInstance().getFrameDelay());
            } 
            catch (Exception ex) { }
        }
    }
    
    /**
     * Desenha os elementos do jogo
     * 
     * @param graphics 
     */
    @Override
    protected void paintComponent(Graphics graphics) 
    {
        try
        {
            super.paintComponent(graphics);

            Graphics2D graphics2D = (Graphics2D) graphics;

            // Desenha os elementos do jogo
            for (GameElement element : FlappyBirdGame.getInstance().getGameElement().values()) 
            {
                // Caso o elemento seja o placar
                if (element instanceof Scoreboard) 
                {
                    // Desenha o placar
                    element.draw(graphics2D);
                }
                else 
                {
                    // Atualiza o movimento do elemento
                    if (!FlappyBirdGame.getInstance().getGameState().equals(GameState.NotStarted)) 
                    {
                        element.move();
                    }
                    
                    // Desenha o elemento
                    if (element.getTransform() != null)
                    {
                        graphics2D.drawImage(element.getCharacter(),
                                element.getTransform(),
                                null);
                    }
                    else 
                    {
                        graphics2D.drawImage(element.getCharacter(),
                                element.getPosition().getX(), element.getPosition().getY(),
                                null);
                    }
                }
            }

            graphics2D.setFont(new Font("TimesRoman", Font.BOLD, 22));
            graphics2D.setColor(Color.WHITE);

            if (FlappyBirdGame.getInstance().getGameMode().equals(GameMode.Automatic)) 
            {
                graphics2D.drawString("Birds: " + FlappyBirdGame.getInstance().getAliveBirds(),
                        ((GameConfig.GAME_WIDTH - 95) / 2), Scoreboard.SCOREBOARD_Y_POS + 80);

                graphics2D.drawString("Score: " + Generation.getInstance().getMaxScore(),
                        ((GameConfig.GAME_WIDTH - 95) / 2), Scoreboard.SCOREBOARD_Y_POS + 100);
                
                graphics2D.drawString(String.format("%s/%s",
                        (Generation.getInstance().getGeneration() + 1), (Generation.getInstance().getGenerationMatch() + 1)),
                        ((GameConfig.GAME_WIDTH - 95) / 2), Scoreboard.SCOREBOARD_Y_POS + 120);
            } 
            else 
            {
                // Caso o jogo esteja no estado NotStarted, escreve na tela a mensagem para iniciar o game
                if (FlappyBirdGame.getInstance().getGameState().equals(GameState.NotStarted)) {
                    graphics2D.drawString("Pressione 'S' para iniciar", ((GameConfig.GAME_WIDTH - 220) / 2), 100);
                } // Caso o jogo esteja no estado GameOver, escreve na tela a mensagem para resetar o game
                else if (FlappyBirdGame.getInstance().getGameState().equals(GameState.GameOver)) {
                    graphics2D.drawString("Pressione 'R' para resetar", ((GameConfig.GAME_WIDTH - 220) / 2), 100);
                }
            }
        }
        catch (Exception ex) { }
    }
}
