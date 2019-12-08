package com.ltoscano.ia.flappybird.game.config;

/**
 * Configurações do algoritmo genético
 *
 * @author ltosc
 */
public class GeneticConfig 
{
    // Tamanho da população
    public static int POPULATION_SIZE = 100;
    // Taxa de crossover
    public static double CROSSOVER_RATE = 0.85;
    // Taxa de mutação
    public static double MUTATION_RATE = 0.1;
    // Taxa de elitismo
    public static double ELITISM_RATE = 0.1;
    // Quantidade de indivíduos da elite, considerando o tamanho da população e a taxa de elitismo
    public static int ELITISM_SIZE = ((POPULATION_SIZE * ELITISM_RATE) >= 1) ? ((int)(POPULATION_SIZE * ELITISM_RATE)) : 1;
    // Quantidade de rodadas por geração
    public static int GENERATION_MATCH_COUNT = 1;
}
