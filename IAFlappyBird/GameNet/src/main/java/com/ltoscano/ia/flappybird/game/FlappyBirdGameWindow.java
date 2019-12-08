package com.ltoscano.ia.flappybird.game;

import com.ltoscano.ia.flappybird.game.base.FlappyBirdGamePanel;
import com.ltoscano.ia.flappybird.game.base.FlappyBirdGame;
import com.ltoscano.ia.flappybird.game.config.GameConfig;
import com.ltoscano.ia.util.KeyBoardController;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;

/**
 *
 * @author ltosc
 */
public class FlappyBirdGameWindow 
{   
    private static FlappyBirdGamePanel GAME_PANEL;
    
    public static void main(String[] args) 
    {
        try 
        {
            GAME_PANEL = new FlappyBirdGamePanel();
            
            JFrame frame = new JFrame();
            frame.setTitle("IAFlappyBird");
            frame.setSize(GameConfig.GAME_WIDTH, GameConfig.GAME_HEIGHT);
            frame.setResizable(false);
            frame.setVisible(true);
            frame.setLocationRelativeTo(null);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            
            // Eventos de teclado para o jogo
            KeyBoardController controller = KeyBoardController.getInstance();
            frame.addKeyListener(controller);
            
            frame.addKeyListener(new KeyListener() {
                @Override
                public void keyPressed(KeyEvent keyEvt) 
                {
                    // Tecla P pausa o jogo
                    if(keyEvt.getKeyCode() == KeyEvent.VK_P)
                    {
                        GAME_PANEL.pauseGame();
                    }
                    
                    // Tecla I incrementa a velocidade dos frames
                    if(keyEvt.getKeyCode() == KeyEvent.VK_I)
                    {
                        GAME_PANEL.increaseGameFrameSpeed();
                    }
                    
                    // Tecla D decrementa a velocidade dos frames
                    if(keyEvt.getKeyCode() == KeyEvent.VK_D)
                    {
                        GAME_PANEL.decreaseGameFrameSpeed();
                    }
                    
                    // Tecla R reinicia a partida
                    if(keyEvt.getKeyCode() == KeyEvent.VK_R)
                    {
                        GAME_PANEL.resetGame();
                    }
                }

                @Override
                public void keyTyped(KeyEvent e) {
                    
                }

                @Override
                public void keyReleased(KeyEvent e) {
                    
                }
            });
            
            frame.add(GAME_PANEL);
            
            FlappyBirdGame.newInstance();
            GAME_PANEL.startGame();
        }
        catch (Exception ex)
        {
            Logger.getLogger(GeneticLearning.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
