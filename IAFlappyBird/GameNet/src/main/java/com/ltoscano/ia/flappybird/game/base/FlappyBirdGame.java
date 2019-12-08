package com.ltoscano.ia.flappybird.game.base;

import com.ltoscano.ia.flappybird.game.element.Background;
import com.ltoscano.ia.flappybird.game.element.Bird;
import com.ltoscano.ia.flappybird.game.element.Land;
import com.ltoscano.ia.flappybird.game.element.Pipe;
import com.ltoscano.ia.flappybird.game.element.Pipe.PipeType;
import com.ltoscano.ia.flappybird.game.element.Scoreboard;
import com.ltoscano.ia.flappybird.game.events.GameOverEventListener;
import com.ltoscano.ia.flappybird.game.events.IBirdDiedEvent;
import com.ltoscano.ia.flappybird.game.events.IBirdLandedEvent;
import com.ltoscano.ia.flappybird.game.events.IGameOverEvent;
import com.ltoscano.ia.util.ImageHelper;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.ltoscano.ia.flappybird.game.config.GameConfig;
import com.ltoscano.ia.flappybird.game.config.LandConfig;
import com.ltoscano.ia.flappybird.game.config.PipeConfig;

/**
 * Implementação do jogo Flappy Bird
 * 
 * @author ltosc
 */
public class FlappyBirdGame implements IBirdDiedEvent, IBirdLandedEvent
{
    // Instância atual do jogo
    private static FlappyBirdGame instance;
    
    // Dimensões da janela do jogo
    private int gameWidth;
    private int gameHeight;
    
    // Lista com todos os objetos do jogo
    private HashMap<Integer, GameElement> gameElement;
    // Id que será atribuído ao próximo novo elemento
    private int nextIdElement;

    // Lista com todos os pássaros do jogo
    private HashMap<Integer, Bird> birdList;
    // Quantidade de pássaros ainda vivos
    private int birdAliveCount;
    // Quantidade de pássaros ainda no ar
    private int birdFlyingCount;
    // Id que será atribuído ao próximo pássaro
    private int nextBirdId;
    
    // Lista com todos os canos do jogo
    private HashMap<Integer, Pipe> pipeList;
    // Quantidade de pares (Topo, Baixo) de canos que será usado
    private final int PIPE_COUNT = 2;
    
    // Estados que o jogo pode assumir
    public enum GameState { NotStarted, Running, GameOver };
    // Estado atual do jogo
    private GameState gameState;
    
    // Modos de jogo
    public enum GameMode { Manual, Automatic };
    // Modo atual do jogo
    private GameMode gameMode;
    
    // Placar máximo até o momento
    private int currentMaxScore;
    
    // Delay entre cada frame do jogo
    private long frameDelay;
    
    // Ouvintes do evento de Game Over
    private GameOverEventListener gameOverListeners;
    
    public FlappyBirdGame()
    {
        // Constrói os elementos do jogo
        buildGame();
    }
    
    /**
     * Cria uma nova instância do jogo
     * 
     */
    public static void newInstance()
    {
        instance = new FlappyBirdGame();
    }
    
    /**
     * Retorna a instância atual do jogo
     * 
     * @return Instância atual do jogo
     */
    public static FlappyBirdGame getInstance()
    {
        return instance;
    }
    
    /**
     * Constrói os elementos usados no jogo
     * 
     */
    public void buildGame()
    {
        try 
        {
            // Inicializando variáveis
            this.gameWidth = GameConfig.GAME_WIDTH;
            this.gameHeight = GameConfig.GAME_HEIGHT;
            
            this.gameElement = new HashMap<>();
            this.nextIdElement = 0;
            
            this.birdList = new HashMap<>();
            this.birdAliveCount = 0;
            
            this.pipeList = new HashMap<>();
            
            this.gameState = GameState.NotStarted;
            this.gameMode = GameMode.Manual;
            
            this.frameDelay = 15;
            
            this.gameOverListeners = new GameOverEventListener();
            
            // Adicionando fundo do jogo
            Background bg = new Background(nextIdElement++,
                    gameWidth, gameHeight,
                    ImageHelper.loadImage("assets/img/bgDay.png"),
                    new ElementPosition());
            
            this.gameElement.put(bg.getId(), bg);
            
            // Adicionando canos do jogo
            int xPos;
            int yPos;
            int pipeId = 0;
            
            for(int i = 0; i < PIPE_COUNT; i++)
            {
                xPos = (int) Pipe.getNewPipeX(gameWidth, i);
                yPos = (int) Pipe.getNewPipeY();
                
                Pipe pipeUp = new Pipe(nextIdElement++,
                        ImageHelper.loadImage("assets/img/pipeUp.png"),
                        new ElementPosition(xPos, yPos),
                        pipeId++,
                        PipeType.Up);
                
                yPos -= PipeConfig.PIPE_HEIGHT + PipeConfig.PIPE_SPACE_SIZE;
                
                Pipe pipeDown = new Pipe(nextIdElement++,
                        ImageHelper.loadImage("assets/img/pipeDown.png"),
                        new ElementPosition(xPos, yPos),
                        pipeId++,
                        PipeType.Down);
                
                this.pipeList.put(pipeUp.getPipeId(), pipeUp);
                this.pipeList.put(pipeDown.getPipeId(), pipeDown);
                
                this.gameElement.put(pipeUp.getId(), pipeUp);
                this.gameElement.put(pipeDown.getId(), pipeDown);
            }
            
            // Adicionando placar do jogo
            Scoreboard scoreBoard = new Scoreboard(nextIdElement++,
                    "assets/img", 
                    new ElementPosition(((gameWidth - Scoreboard.SCOREBOARD_WIDTH) / 2), Scoreboard.SCOREBOARD_Y_POS));
            
            this.gameElement.put(scoreBoard.getId(), scoreBoard);
            
            // Adicionando pássaro do jogo
            nextBirdId = 0;
            
            Bird bird = new Bird(nextIdElement++,
                    ImageHelper.loadImage("assets/img/birdRed0.png"),
                    new ElementPosition(50, (gameHeight / 2)),
                    nextBirdId++);
            
            bird.addBirdDiedListener(this);
            bird.addBirdLandedListener(this);
            
            this.birdList.put(bird.getBirdId(), bird);
            this.birdAliveCount++;
            this.birdFlyingCount++;
            
            this.gameElement.put(bird.getId(), bird);
            
            // Adicionando grama do jogo
            Land land = new Land(nextIdElement++,
                    ImageHelper.loadImage("assets/img/land.png"),
                    new ElementPosition(0, LandConfig.LAND_Y_POS));
            
            Land land2 = new Land(nextIdElement++,
                    ImageHelper.loadImage("assets/img/land.png"),
                    new ElementPosition(gameWidth - 1, LandConfig.LAND_Y_POS));
            
            this.gameElement.put(land.getId(), land);
            this.gameElement.put(land2.getId(), land2);
        }
        catch (IOException ex)
        {
            Logger.getLogger(FlappyBirdGame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Dá início a partida do jogo
     * 
     */
    public void startGame()
    {
        this.gameState = GameState.Running;
    }
    
    /**
     * Reseta o estado do jogo e inicia a nova instância do jogo
     * 
     */
    public void resetGame()
    {
        long currentFrameDelay = instance.getFrameDelay();
        instance = new FlappyBirdGame();
        instance.setFrameDelay(currentFrameDelay);
        instance.startGame();
    }
    
    /**
     * Retorna um pássaro a partir do Id do pássaro informado
     * 
     * @param birdId Id do pássaro
     * @return Um pássaro caso o pássaro com o Id exista ou nulo, caso contrário
     */
    public Bird getBirdById(int birdId)
    {
        return this.birdList.get(birdId);
    }
    
    /**
     * Retorna a lista de pássaros
     * 
     * @return Lista de pássaros
     */
    public Collection<Bird> getBirds()
    {
        return this.birdList.values();
    }
    
    /**
     * Retorna a quantidade de pássaros ainda vivos
     * 
     * @return Quantidade de pássaros vivos
     */
    public int getAliveBirds()
    {
        return this.birdAliveCount;
    }
    
    /**
     * Remove o primeiro pássaro da lista de pássaros
     * 
     */
    public void removeFirstBird()
    {
        if(!birdList.isEmpty())
        {
            Bird firstBird = this.birdList.get(0);
            this.gameElement.remove(firstBird.getId());
            this.birdList.remove(firstBird.getBirdId());
            
            nextBirdId--;
            birdAliveCount--;
            birdFlyingCount--;
        }
    }
    
    /**
     * Adiciona um novo pássaro ao jogo
     * 
     * @throws IOException 
     */
    public void addBird() throws IOException
    {
        Bird bird = new Bird(nextIdElement++,
                ImageHelper.loadImage("assets/img/birdRed0.png"),
                new ElementPosition(50, (gameHeight / 2)),
                nextBirdId++);

        bird.addBirdDiedListener(this);
        bird.addBirdLandedListener(this);

        this.birdList.put(bird.getBirdId(), bird);
        this.birdAliveCount++;
        this.birdFlyingCount++;

        this.gameElement.put(bird.getId(), bird);
    }
    
    /**
     * A partir do Id do pássaro, obtém qual é o próximo cano que o pássaro deve
     * ultrapassar
     * 
     * @param birdId Id do pássaro
     * @return Próximo cano superior que o pássaro deverá ultrapassar
     */
    public Pipe getNextPipeToBird(int birdId)
    {
        Bird bird = this.getBirdById(birdId);
        
        ArrayList<Pipe> nextPipeList = new ArrayList<>();
        Pipe currPipe;
        
        // Percorrendo apenas os canos superiores
        for(int i = 0; i < pipeList.size(); i += 2)
        {
            currPipe = pipeList.get(i);
            
            if(((currPipe.getPosition().getX() + PipeConfig.PIPE_WIDTH)) > bird.getPosition().getX())
            {
                nextPipeList.add(currPipe);
            }
        }
        
        Pipe nextPipe = nextPipeList.get(0);
        
        for (int i = 1; i < nextPipeList.size(); i++) 
        {
            currPipe = nextPipeList.get(i);
            
            if (currPipe.getPosition().getX() < nextPipe.getPosition().getX())
            {
                nextPipe = currPipe;
            }
        }
        
        return nextPipe;
    }
    
    /**
     * Retorna a pontuação máxima obtida pelos pássaros atuais
     * 
     * @return Pontuação máxima atual
     */
    public int getMaxScoreToBirds()
    {
        int maxScore = 0;
        
        for(Bird bird : birdList.values())
        {
            if(maxScore < bird.getBirdScore())
            {
                maxScore = bird.getBirdScore();
            }
        }
        
        return maxScore;
    }
    
    /**
     * Retorna um cano a partir do Id do cano
     * 
     * @param pipeId Id do cano
     * @return Cano para o Id informado caso exista, nulo caso contrário
     */
    public Pipe getPipeById(int pipeId)
    {
        return this.pipeList.get(pipeId);
    }
    
    /**
     * Substitui um cano caso já exista o Id ou então adiciona um novo cano na
     * lista de canos do jogo
     * 
     * @param pipeId Id do cano
     * @param pipe Cano
     */
    public void setPipeById(int pipeId, Pipe pipe)
    {
        this.pipeList.put(pipeId, pipe);
    }
    
    /**
     * Tratamento quando o evento de que um pássaro morreu é disparado
     * 
     * @param bird Pássaro que morreu
     */
    @Override
    public void onBirdDied(Bird bird)
    {
        this.birdAliveCount--;
        
        if(birdAliveCount == 0)
        {
            this.gameState = GameState.GameOver;
        }
    }
    
    /**
     * Tratamento quando o evento de que um pássaro tocou o solo é disparado
     * 
     * @param bird Pássaro que tocou o solo
     */
    @Override
    public void onBirdLanded(Bird bird) 
    {
        this.birdFlyingCount--;
        
        if(birdFlyingCount == 0)
        {
            int maxScore = this.getMaxScoreToBirds();
            
            if(maxScore > currentMaxScore)
            {
                currentMaxScore = maxScore;
            }
            
            this.gameOverListeners.fireEvent(this);
            this.gameOverListeners.removeListener();
        }
    }
    
    /**
     * Adiciona um ouvinte para o evento de Game Over
     * 
     * @param listener 
     */
    public void addGameOverListener(IGameOverEvent listener)
    {
        this.gameOverListeners.addListener(listener);
    }
    
    /**
     * Remove um ouvinte do evento de Game Over
     * 
     */
    public void removeGameOverListener()
    {
        this.gameOverListeners.removeListener();
    }

    /**
     * @return the gameElement
     */
    public HashMap<Integer, GameElement> getGameElement() {
        return gameElement;
    }

    /**
     * @param gameElement the gameElement to set
     */
    public void setGameElement(HashMap<Integer, GameElement> gameElement) {
        this.gameElement = gameElement;
    }
    
     /**
     * @return the gameState
     */
    public GameState getGameState() {
        return gameState;
    }

    /**
     * @param gameState the gameState to set
     */
    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }
    
    /**
     * @return the gameMode
     */
    public GameMode getGameMode() {
        return gameMode;
    }

    /**
     * @param gameMode the gameMode to set
     */
    public void setGameMode(GameMode gameMode) {
        this.gameMode = gameMode;
    }
    
    /**
     * @return the frameDelay
     */
    public long getFrameDelay() {
        return frameDelay;
    }

    /**
     * @param frameDelay the frameDelay to set
     */
    public void setFrameDelay(long frameDelay) {
        this.frameDelay = frameDelay;
    }
    
    /**
     * @return the currentMaxScore
     */
    public int getCurrentMaxScore() {
        return currentMaxScore;
    }

    /**
     * @param currentMaxScore the currentMaxScore to set
     */
    public void setCurrentMaxScore(int currentMaxScore) {
        this.currentMaxScore = currentMaxScore;
    }
}
