package com.ltoscano.ia.flappybird.game;

import com.ltoscano.ia.flappybird.game.base.FlappyBirdGamePanel;
import com.ltoscano.ia.flappybird.game.base.FlappyBirdPlayerAgent;
import com.ltoscano.ia.flappybird.game.base.FlappyBirdGame;
import com.ltoscano.ia.flappybird.game.base.FlappyBirdGame.GameMode;
import com.ltoscano.ia.flappybird.game.config.GameConfig;
import com.ltoscano.ia.flappybird.game.element.Bird;
import com.ltoscano.ia.flappybird.game.events.IGameOverEvent;
import com.ltoscano.ia.flappybird.game.gui.FlappyBirdNeuralConfigWindow;
import com.ltoscano.ia.genetic.Generation;
import com.ltoscano.ia.neural.MultiLayerPerceptron;
import com.ltoscano.ia.neural.function.HyperbolicTangentFunction;
import com.ltoscano.ia.util.FileHelper;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author ltosc
 */
public class NeuralLearning implements IGameOverEvent
{
    private static FlappyBirdGamePanel GAME_PANEL;
    
    // Quantidade de neurônios da camada de entrada, escondida e saída da rede neural
    public static final int NEURAL_INPUT_NEURON_COUNT = 2;
    public static int NEURAL_HIDDEN_LAYER_NEURON_COUNT = 3;
    public static final int NEURAL_OUTPUT_NEURON_COUNT = 1;
    
    // Valores mínimo e máximo dos pesos dos neurônios da rede neural
    public static double NEURAL_MIN_WEIGHT_VALUE = -1.0;
    public static double NEURAL_MAX_WEIGHT_VALUE = 1.0;
    // Valor de bias da rede neural
    public static double NEURAL_BIAS_VALUE = 0.0;
    
    // Funções de ativação da rede neural
    public static enum NeuronActivationFunction { Sigmoid, Hyperbolic }
    
    // Função de ativação da rede neural
    public static NeuronActivationFunction NEURAL_ACTIVATION_FUNCTION = NeuronActivationFunction.Hyperbolic;
    // Limiar de ativação da rede neural
    public static double NEURAL_ACTIVATION_FUNCTION_LIMIAR = 0.0;
    
    // Parâmetros do treinamento da rede
    public static double NEURAL_LEARN_RATE = 0.7;
    public static double NEURAL_ERROR_LIMIAR = 0.00001;
    public static int NEURAL_MAX_ITERATION = 100000;
    
    private MultiLayerPerceptron birdNeuralNetwork;
    
    public void newInstance() throws IOException
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
        frame.addKeyListener(new KeyListener() 
        {
            @Override
            public void keyPressed(KeyEvent keyEvt) {
                // Tecla C configura o jogo
                if (keyEvt.getKeyCode() == KeyEvent.VK_C) {
                    java.awt.EventQueue.invokeLater(new Runnable() 
                    {
                        @Override
                        public void run() 
                        {
                            new FlappyBirdNeuralConfigWindow().setVisible(true);
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

        start();
        GAME_PANEL.startGame();
    }
    
    private void start() throws IOException
    {
        FlappyBirdGame.newInstance();
        FlappyBirdGame.getInstance().setGameMode(GameMode.Automatic);
        
        Bird bird = FlappyBirdGame.getInstance().getBirdById(0);
        bird.setAutoControl(true);
        
        birdNeuralNetwork = createNeuralNetwork();

        FlappyBirdPlayerAgent birdController = new FlappyBirdPlayerAgent(bird);
        birdController.setNetwork(birdNeuralNetwork);
        
        bird.addBirdMoveListener(birdController);

        FlappyBirdGame.getInstance().addGameOverListener(this);
        FlappyBirdGame.getInstance().startGame();
    }
    
    private void restart()
    {
        FlappyBirdGame.getInstance().resetGame();
        FlappyBirdGame.getInstance().setGameMode(GameMode.Automatic);
        
        Bird bird = FlappyBirdGame.getInstance().getBirdById(0);
        bird.setAutoControl(true);
        
        FlappyBirdPlayerAgent birdController = new FlappyBirdPlayerAgent(bird);
        birdController.setNetwork(birdNeuralNetwork);
        
        bird.addBirdMoveListener(birdController);
        
        FlappyBirdGame.getInstance().addGameOverListener(this);
        FlappyBirdGame.getInstance().startGame();
    }
    
    @Override
    public void onGameOver(FlappyBirdGame flappyGame) 
    {
        System.out.println("Score: " + flappyGame.getMaxScoreToBirds());
        restart();
    }
    
    public static void main(String[] args) 
    {
        try 
        {
            NeuralLearning neuralLearning = new NeuralLearning();
            neuralLearning.newInstance();
        }
        catch (Exception ex)
        {
            Logger.getLogger(GeneticLearning.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private MultiLayerPerceptron createNeuralNetwork()
    {
        MultiLayerPerceptron network = null;
        
        if (FileHelper.showFileDialog(JFileChooser.OPEN_DIALOG, JFileChooser.FILES_ONLY, "Escolha uma pasta", FileHelper.WORK_DIR, FileHelper.TRAIN_SET_FILE_FILTER)
                == JFileChooser.APPROVE_OPTION) 
        {
            ArrayList<double[]> inputTrainList = new ArrayList<>();
            ArrayList<double[]> outputTrainList = new ArrayList<>();
            
            try (BufferedReader buffReader = new BufferedReader(new FileReader(FileHelper.getSelectedFilePath())))
            {

		String line;
                
                while ((line = buffReader.readLine()) != null) 
                {
                    if(line.contains("#"))
                    {
                        if (line.contains("1#")) 
                        {
                            line = line.replace("1#", "");
                            FlappyBirdPlayerAgent.DISTANCE_INPUT_AVERAGE = Double.valueOf(line);
                        } 
                        else if (line.contains("2#")) 
                        {
                            line = line.replace("2#", "");
                            FlappyBirdPlayerAgent.DISTANCE_INPUT_DEVIATION = Double.valueOf(line);
                        } 
                        else if (line.contains("3#")) 
                        {
                            line = line.replace("3#", "");
                            FlappyBirdPlayerAgent.HEIGHT_INPUT_AVERAGE = Double.valueOf(line);
                        } 
                        else if (line.contains("4#")) 
                        {
                            line = line.replace("4#", "");
                            FlappyBirdPlayerAgent.HEIGHT_INPUT_DEVIATION = Double.valueOf(line);
                        }
                    }
                    else
                    {
                        String splitLine[] = line.split(";");

                        if (splitLine.length == 3) {
                            double inputTrain[] = new double[2];
                            double outputTrain[] = new double[1];

                            inputTrain[0] = Double.valueOf(splitLine[0]);
                            inputTrain[1] = Double.valueOf(splitLine[1]);

                            outputTrain[0] = Double.valueOf(splitLine[2]);

                            inputTrainList.add(inputTrain);
                            outputTrainList.add(outputTrain);
                        }
                    }
                }
                
                JOptionPane.showMessageDialog(null, "Arquivo de treinamento carregado!");
            } 
            catch (IOException ex) 
            {
                Logger.getLogger(NeuralLearning.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            network = new MultiLayerPerceptron(
                        NEURAL_INPUT_NEURON_COUNT, 
                        NEURAL_HIDDEN_LAYER_NEURON_COUNT,
                        NEURAL_OUTPUT_NEURON_COUNT, 
                        new HyperbolicTangentFunction(), 
                        NEURAL_MIN_WEIGHT_VALUE, NEURAL_MAX_WEIGHT_VALUE);
            
            network.setBias(0);
            network.train(inputTrainList, outputTrainList, NEURAL_LEARN_RATE, NEURAL_ERROR_LIMIAR, NEURAL_MAX_ITERATION);
        }
        
        return network;
    }
}
