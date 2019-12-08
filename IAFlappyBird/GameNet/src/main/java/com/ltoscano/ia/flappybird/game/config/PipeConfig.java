package com.ltoscano.ia.flappybird.game.config;

/**
 * Configurações usadas pelos canos do jogo
 * 
 * @author ltosc
 */
public class PipeConfig
{
    // Dimensões do cano
    public static final int PIPE_WIDTH = 52;
    public static final int PIPE_HEIGHT = 500;
    
    // Posição Y mínima e máxima que o cano pode assumir
    public static final int PIPE_MIN_Y = 240;
    public static final int PIPE_MAX_Y = 360;
    
    // Tamanho da abertura entre os canos
    public static int PIPE_SPACE_SIZE = 60;
    
    // Velocidade do movimento
    public static final int MOVE_SPEED = 3;
}
