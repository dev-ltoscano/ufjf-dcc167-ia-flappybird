package com.ltoscano.ia.flappybird.game.base;

import com.ltoscano.ia.flappybird.game.config.BirdConfig;
import com.ltoscano.ia.flappybird.game.config.PipeConfig;
import com.ltoscano.ia.flappybird.game.element.Bird;
import com.ltoscano.ia.flappybird.game.element.Pipe;
import com.ltoscano.ia.flappybird.game.events.IBirdMoveEvent;
import com.ltoscano.ia.neural.MultiLayerPerceptron;
import com.ltoscano.ia.neural.function.HyperbolicTangentFunction;
import com.ltoscano.ia.neural.function.SigmoideFunction;
import com.ltoscano.ia.flappybird.game.config.AgentConfig;

/**
 * Agente inteligente que controlará um pássaro no jogo
 * 
 * @author ltosc
 */
public class FlappyBirdPlayerAgent implements IBirdMoveEvent
{
    // Pássaro que será controlado pelo agente
    private final Bird bird;
    // Rede neural que será usada pelo agente
    private MultiLayerPerceptron network;
    
    // Indica se este agente é o melhor jogador
    private boolean bestPlayer;
    
    // Usado para a normalização dos dados
    public static double DISTANCE_INPUT_AVERAGE = 0;
    public static double DISTANCE_INPUT_DEVIATION = 1;
    public static double HEIGHT_INPUT_AVERAGE = 0;
    public static double HEIGHT_INPUT_DEVIATION = 1;
    
    /**
     * Construtor do FlappyBirdPlayer
     * 
     * @param bird Pássaro que será controlado pelo agente
     */
    public FlappyBirdPlayerAgent(Bird bird)
    {
        // Define o pássaro do agente
        this.bird = bird;
        
        // Constrói a rede neural do agente
        switch(AgentConfig.NEURAL_ACTIVATION_FUNCTION)
        {
            case Sigmoid:
                this.network = new MultiLayerPerceptron(
                        AgentConfig.NEURAL_INPUT_NEURON_COUNT, 
                        AgentConfig.NEURAL_HIDDEN_LAYER_NEURON_COUNT, 
                        AgentConfig.NEURAL_OUTPUT_NEURON_COUNT, 
                        new SigmoideFunction(1), 
                        AgentConfig.NEURAL_MIN_WEIGHT_VALUE, AgentConfig.NEURAL_MAX_WEIGHT_VALUE);
                break;
            case Hyperbolic:
                this.network = new MultiLayerPerceptron(
                        AgentConfig.NEURAL_INPUT_NEURON_COUNT, 
                        AgentConfig.NEURAL_HIDDEN_LAYER_NEURON_COUNT, 
                        AgentConfig.NEURAL_OUTPUT_NEURON_COUNT, 
                        new HyperbolicTangentFunction(), 
                        AgentConfig.NEURAL_MIN_WEIGHT_VALUE, AgentConfig.NEURAL_MAX_WEIGHT_VALUE);
                break;
            default:
                this.network = new MultiLayerPerceptron(2, 3, 1, new SigmoideFunction(1), -1.0, 1.0);
        }
        
        // Define as propriedades da rede neural
        this.network.setId(bird.getBirdId());
        this.network.setBias(AgentConfig.NEURAL_BIAS_VALUE);
    }
    
    @Override
    public void onBirdMove(Bird bird)
    {
        // Obtém o próximo cano que deve ser ultrapassado pelo pássaro controlado pelo agente
        Pipe nextPipe = FlappyBirdGame.getInstance().getPipeById(bird.getNextPipeId());
        
        // Parâmetros de entrada para a rede neural do agente
        double distance = (nextPipe.getPosition().getX() - (bird.getPosition().getX() +  BirdConfig.BIRD_WIDTH));
        double height = ((nextPipe.getPosition().getY() - (PipeConfig.PIPE_SPACE_SIZE / 2)) - bird.getPosition().getY());
        
        // Obtém a saída da rede para as entradas normalizadas
        double output = getNetwork().evaluate(
                new double[] 
                { 
                    ((distance - DISTANCE_INPUT_AVERAGE) / DISTANCE_INPUT_DEVIATION), 
                    ((height - HEIGHT_INPUT_AVERAGE) / HEIGHT_INPUT_DEVIATION) 
                }
        )[0];
        
        // Define a ação que será tomada de acordo com a saída da rede
        if(output > AgentConfig.NEURAL_ACTIVATION_FUNCTION_LIMIAR)
        {
            // Caso a saída da rede ultrapasse o limiar de ativação, faz o pássaro voar
            bird.fly(true);
        }
        else
        {
            // Caso contrário, não faz nada
            bird.fly(false);
        }
    }
    
    public Bird getBird() 
    {
        return bird;
    }
    
    public MultiLayerPerceptron getNetwork() 
    {
        return network;
    }
    
    public void setNetwork(MultiLayerPerceptron network) 
    {
        this.network = network;
    }
    
    public boolean isBestPlayer() {
        return bestPlayer;
    }
    
    public void setBestPlayer(boolean bestPlayer) {
        this.bestPlayer = bestPlayer;
    }
}
