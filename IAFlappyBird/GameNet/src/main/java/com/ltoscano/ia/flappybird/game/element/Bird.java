package com.ltoscano.ia.flappybird.game.element;

import com.ltoscano.ia.flappybird.game.base.ElementPosition;
import com.ltoscano.ia.flappybird.game.base.GameElement;
import com.ltoscano.ia.flappybird.game.base.FlappyBirdGame;
import com.ltoscano.ia.flappybird.game.base.FlappyBirdGame.GameState;
import com.ltoscano.ia.flappybird.game.config.BirdConfig;
import com.ltoscano.ia.flappybird.game.config.GameConfig;
import com.ltoscano.ia.flappybird.game.config.LandConfig;
import com.ltoscano.ia.flappybird.game.config.PipeConfig;
import com.ltoscano.ia.util.KeyBoardController;
import com.ltoscano.ia.flappybird.game.events.BirdDiedEventListener;
import com.ltoscano.ia.flappybird.game.events.BirdLandedEventListener;
import com.ltoscano.ia.flappybird.game.events.BirdMoveEventListener;
import com.ltoscano.ia.flappybird.game.events.IBirdDiedEvent;
import com.ltoscano.ia.flappybird.game.events.IBirdLandedEvent;
import com.ltoscano.ia.flappybird.game.events.IBirdMoveEvent;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.geom.AffineTransform;

/**
 *  Pássaro do jogo Flappy Bird
 * 
 * @author ltosc
 */
public class Bird extends GameElement
{
    // Velocidade atual de queda do pássaro
    private double fallSpeed;
    // Identificador do pássaro
    private final int birdId;
    // Define se o pássaro está vivo ou não
    private boolean alive;
    // Pontuação do pássaro
    private int birdScore;
    // Quantidade de batidas de asas do pássaro
    private int birdFlaps;
    // Distância percorrida pelo pássaro
    private int birdDistance;
    // Aptidão do pássaro
    private double fitness;
    // Identificador do próximo cano que o pássaro deverá passar
    private int nextPipeId;
    // Controle do pássaro
    private final KeyBoardController controller;
    // Tecla que será usada para controlar o pássaro
    private final int keyControl;
    // Define se o controle do pássaro é automático
    private boolean autoControl;
    
    // Indicadores para o algoritmo genético
    private boolean crossover;
    private boolean mutation;
    private boolean elite;
    
    // Ouvintes dos eventos do pássaro
    private BirdMoveEventListener birdMoveListener;
    private BirdDiedEventListener birdDiedListeners;
    private BirdLandedEventListener birdLandedListeners;
    
    public Bird(int id, Image character, ElementPosition initialPosition, int birdId)
    {
        this(id, character, initialPosition, birdId, KeyEvent.VK_SPACE);
    }
    
    public Bird(int id, Image character, ElementPosition initialPosition, int birdId, int keyControl)
    {
        super(id, BirdConfig.BIRD_WIDTH, BirdConfig.BIRD_HEIGHT, character, initialPosition);
        
        this.birdId = birdId;
        setControllable(true);
        
        this.fallSpeed = 0;
        setMoveSpeed(BirdConfig.MOVE_SPEED);
        
        this.alive = true;
        this.birdScore = 0;
        this.nextPipeId = 0;
        
        AffineTransform transform = new AffineTransform();
        transform.translate(initialPosition.getX(), initialPosition.getY());
        setTransform(transform);
        
        this.controller = KeyBoardController.getInstance();
        this.keyControl = keyControl;
        
        this.birdMoveListener = new BirdMoveEventListener();
        this.birdDiedListeners = new BirdDiedEventListener();
        this.birdLandedListeners = new BirdLandedEventListener();
    }
    
    private void move(boolean fly)
    {
        int xPos = getPosition().getX();
        int yPos = getPosition().getY();
        
        // Pássaro morreu e está caindo
        if(!alive && (xPos <= -(BirdConfig.BIRD_WIDTH + 10)) && (yPos <= (LandConfig.LAND_Y_POS - BirdConfig.BIRD_HEIGHT)))
        {
            return;
        }
        else if(!alive && (yPos >= (LandConfig.LAND_Y_POS - BirdConfig.BIRD_HEIGHT)))
        {
            if (!FlappyBirdGame.getInstance().getGameState().equals(GameState.GameOver)) 
            {
                xPos -= getMoveSpeed();
                getPosition().setX(xPos);
            }
            
            yPos = LandConfig.LAND_Y_POS - BirdConfig.BIRD_HEIGHT;
            getPosition().setY(yPos);
            
            setTransform(new AffineTransform());
            getTransform().translate(xPos, yPos);
            
            if(yPos <= (LandConfig.LAND_Y_POS - BirdConfig.BIRD_HEIGHT))
            {
                if (birdLandedListeners.size() > 0) 
                {
                    this.birdLandedListeners.fireEvent(this);
                    this.birdLandedListeners.clearAllListener();
                }
            }
            
            return;
        }
        
        // Pássaro ainda vivo, testa colisão com obstáculos
        checkCollision();
        
        if (alive)
        {
            // O pássaro voou por mais uma distância, sem colidir
            birdDistance++;
            
            if ((autoControl && fly) || (!autoControl && controller.isKeyDown(keyControl)))
            {
                fallSpeed = -BirdConfig.FLY_SPEED;
                birdFlaps++;
            }
            
            if(!elite)
            {
                fitness = (birdDistance - (birdFlaps * 0.1));
            }
        }

        fallSpeed += GameConfig.GRAVITY;
        yPos += (int) fallSpeed;
        getPosition().setY(yPos);

        setRotation(Math.min(fallSpeed * 7, 90) * (Math.PI / 180));
        setTransform(new AffineTransform());
        getTransform().translate(getPosition().getX() + 10, getPosition().getY() + 10);
        getTransform().rotate(getRotation());
        getTransform().translate(-10, -10);
    }
    
    /**
     * Faz o pássaro voar
     * @param isFly
     */
    public void fly(boolean isFly)
    {
        if(isFly)
        {
            this.move(true);
        }
        else
        {
            this.move(false);
        }
    }
    
    @Override
    public void move() 
    {
        // Verifica se o modo de controle é automático
        if(isAutoControl())
        {
            // Dispara evento para o ouvinte tomar a ação sobre o movimento do pássaro
            this.birdMoveListener.fireEvent(this);
        }
        else
        {
            this.move(false);
        }
    }
    
    @Override
    public boolean checkCollision()
    {
        boolean collision = false;
        
        // Obtém as coordenadas atuais do pássaro
        int xPos = getPosition().getX();
        int yPos = getPosition().getY();
        
        // Verifica se o pássaro colidiu com o solo
        if(yPos >= (LandConfig.LAND_Y_POS - BirdConfig.BIRD_HEIGHT))
        {
            this.alive = false;
            collision = true;
        }
        // Verifica se o pássaro colidiu com o topo da tela
        else if (yPos <= 0) 
        {
            this.alive = false;
            collision = true;
        }
        // Verifica se o pássaro colidiu com os canos
        else
        {
            // Obtém o próximo cano superior e inferior
            Pipe nextPipeUpper = FlappyBirdGame.getInstance().getNextPipeToBird(birdId);
            Pipe nextPipeDown = FlappyBirdGame.getInstance().getPipeById(nextPipeUpper.getPipeId() + 1);
            
            // Verifica se o pássaro transpassou os canos anterioriores
            if (nextPipeId != nextPipeUpper.getPipeId()) 
            {
                // Atualiza o identificador do próximo cano
                nextPipeId = nextPipeUpper.getPipeId();
                // Atualiza a pontuação do pássaro
                birdScore++;
            }
            
            // Verifica a colisão com o cano
            if (((xPos + BirdConfig.BIRD_WIDTH) >= nextPipeUpper.getPosition().getX()) && (xPos <= (nextPipeUpper.getPosition().getX() + PipeConfig.PIPE_WIDTH))) 
            {
                if (yPos <= (nextPipeDown.getPosition().getY() + PipeConfig.PIPE_HEIGHT))
                {
                    this.alive = false;
                    collision = true;
                }
                else if ((yPos + BirdConfig.BIRD_HEIGHT) >= nextPipeUpper.getPosition().getY()) 
                {
                    this.alive = false;
                    collision = true;
                }
            }
        }
        
        // Caso o pássaro tenha morrido, dispara o evento para os ouvintes
        if(!alive && (birdDiedListeners.size() > 0))
        {
            this.birdDiedListeners.fireEvent(this);
            this.birdDiedListeners.clearAllListener();
        }
        
        return collision;
    }
    
    public void addBirdDiedListener(IBirdDiedEvent listener)
    {
        this.birdDiedListeners.addListener(listener);
    }
    
    public void removeBirdDiedListener(IBirdDiedEvent listener)
    {
        this.birdDiedListeners.removeListener(listener);
    }
    
    public void addBirdMoveListener(IBirdMoveEvent listener)
    {
        this.birdMoveListener.addListener(listener);
    }
    
    public void removeBirdMoveListener()
    {
        this.birdMoveListener.removeListener();
    }
    
    public void addBirdLandedListener(IBirdLandedEvent listener)
    {
        this.birdLandedListeners.addListener(listener);
    }
    
    public void removeBirdLandedListener(IBirdLandedEvent listener)
    {
        this.birdLandedListeners.removeListener(listener);
    }

    public boolean isAlive() 
    {
        return alive;
    }

    public void setAlive(boolean alive)
    {
        this.alive = alive;
        
        if(birdDiedListeners.size() > 0)
        {
            this.birdDiedListeners.fireEvent(this);
            this.birdDiedListeners.clearAllListener();
        }
    }

    public int getBirdId() 
    {
        return birdId;
    }

    public int getBirdScore() 
    {
        return birdScore;
    }
    
    public double getFitness()
    {
        return fitness;
    }
    
    public void setFitness(double fitness)
    {
        this.fitness = fitness;
    }

    public void setBirdScore(int birdScore)
    {
        this.birdScore = birdScore;
    }

    public boolean isAutoControl()
    {
        return autoControl;
    }

    public void setAutoControl(boolean autoControl) 
    {
        this.autoControl = autoControl;
    }
    
    public int getNextPipeId()
    {
        return nextPipeId;
    }
    
    public boolean isCrossover() {
        return crossover;
    }
    
    public void setCrossover(boolean crossover) {
        this.crossover = crossover;
    }
    
    public boolean isMutation() {
        return mutation;
    }
    
    public void setMutation(boolean mutation) {
        this.mutation = mutation;
    }
    
    public boolean isElite() {
        return elite;
    }
    
    public void setElite(boolean elite) {
        this.elite = elite;
    }
}