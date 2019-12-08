package com.ltoscano.ia.flappybird.game.config;

/**
 * Configurações usadas pelo agente inteligente
 * 
 * @author ltosc
 */
public class AgentConfig 
{
    // Quantidade de neurônios da camada de entrada, escondida e saída da rede neural
    public static final int NEURAL_INPUT_NEURON_COUNT = 2;
    public static int NEURAL_HIDDEN_LAYER_NEURON_COUNT = 3;
    public static final int NEURAL_OUTPUT_NEURON_COUNT = 1;
    
    // Valores mínimo e máximo dos pesos dos neurônios da rede neural
    public static double NEURAL_MIN_WEIGHT_VALUE = -10.0;
    public static double NEURAL_MAX_WEIGHT_VALUE = 10.0;
    // Valor de bias da rede neural
    public static double NEURAL_BIAS_VALUE = 0.0;
    
    // Funções de ativação da rede neural
    public static enum NeuronActivationFunction { Sigmoid, Hyperbolic }
    
    // Função de ativação da rede neural
    public static NeuronActivationFunction NEURAL_ACTIVATION_FUNCTION = NeuronActivationFunction.Hyperbolic;
    // Limiar de ativação da rede neural
    public static double NEURAL_ACTIVATION_FUNCTION_LIMIAR = 0.0;
}
