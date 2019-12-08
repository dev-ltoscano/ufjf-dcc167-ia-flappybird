package com.ltoscano.ia.flappybird.game;

import com.ltoscano.ia.flappybird.game.gui.FlappyBirdGeneticConfigWindow;
import com.ltoscano.ia.flappybird.game.base.FlappyBirdGamePanel;
import com.ltoscano.ia.flappybird.game.base.FlappyBirdGame;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import com.ltoscano.ia.flappybird.game.config.GameConfig;
import com.ltoscano.ia.genetic.Generation;
import com.ltoscano.ia.util.FileHelper;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

/**
 *
 * @author ltosc
 */
public class GeneticLearning 
{
    private static FlappyBirdGamePanel GAME_PANEL;
    
    public void newInstance() throws IOException
    {
        GAME_PANEL = new FlappyBirdGamePanel();
        Generation.newInstance();

        JFrame frame = new JFrame();
        frame.setTitle("IAFlappyBird");
        frame.setSize(GameConfig.GAME_WIDTH, GameConfig.GAME_HEIGHT);
        frame.setResizable(false);
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Eventos de teclado para o jogo
        frame.addKeyListener(new KeyListener() {
            @Override
            public void keyPressed(KeyEvent keyEvt) {
                // Tecla C configura o jogo
                if (keyEvt.getKeyCode() == KeyEvent.VK_C) {
                    java.awt.EventQueue.invokeLater(new Runnable() 
                    {
                        @Override
                        public void run() 
                        {
                            new FlappyBirdGeneticConfigWindow().setVisible(true);
                        }
                    });
                    
                    frame.dispose();
                }
                
                // Tecla P pausa o jogo
                if (keyEvt.getKeyCode() == KeyEvent.VK_P) {
                    GAME_PANEL.pauseGame();
                }

                // Tecla I incrementa a velocidade dos frames
                if (keyEvt.getKeyCode() == KeyEvent.VK_I) {
                    GAME_PANEL.increaseGameFrameSpeed();
                }

                // Tecla D decrementa a velocidade dos frames
                if (keyEvt.getKeyCode() == KeyEvent.VK_D) {
                    GAME_PANEL.decreaseGameFrameSpeed();
                }

                // Tecla S salva genomas da geração atual
                if (FlappyBirdGame.getInstance().getGameMode().equals(FlappyBirdGame.GameMode.Automatic)
                        && (keyEvt.getKeyCode() == KeyEvent.VK_S)) {
                    GAME_PANEL.pauseGame();

                    if (FileHelper.showFileDialog(JFileChooser.SAVE_DIALOG, JFileChooser.DIRECTORIES_ONLY, "Escolha uma pasta", FileHelper.GENOME_PATH, FileHelper.GENOME_FILE_FILTER)
                            == JFileChooser.APPROVE_OPTION) {
                        Generation.getInstance().saveGeneration(FileHelper.getSelectedFilePath());
                        JOptionPane.showMessageDialog(frame, "A geração foi salva!");
                    }

                    GAME_PANEL.pauseGame();
                }

                // Tecla L carrega genomas salvos
                if (FlappyBirdGame.getInstance().getGameMode().equals(FlappyBirdGame.GameMode.Automatic)
                        && (keyEvt.getKeyCode() == KeyEvent.VK_L)) {
                    GAME_PANEL.pauseGame();

                    if (FileHelper.showFileDialog(JFileChooser.OPEN_DIALOG, JFileChooser.DIRECTORIES_ONLY, "Escolha uma pasta", FileHelper.GENOME_PATH, FileHelper.GENOME_FILE_FILTER)
                            == JFileChooser.APPROVE_OPTION) {
                        Generation.getInstance().loadGeneration(FileHelper.getSelectedFilePath());
                        JOptionPane.showMessageDialog(frame, "A geração foi carregada!");
                    }

                    GAME_PANEL.pauseGame();
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

        Generation.getInstance().startGeneration();
        GAME_PANEL.startGame();
    }
    
    public static void main(String[] args) 
    {
        try 
        {
            GeneticLearning geneticLearning = new GeneticLearning();
            geneticLearning.newInstance();
        }
        catch (Exception ex)
        {
            Logger.getLogger(GeneticLearning.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
