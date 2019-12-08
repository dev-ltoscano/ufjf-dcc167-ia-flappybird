package com.ltoscano.ia.genetic;

import com.ltoscano.ia.flappybird.game.base.FlappyBirdPlayerAgent;
import com.ltoscano.ia.flappybird.game.base.FlappyBirdGame;
import com.ltoscano.ia.flappybird.game.base.FlappyBirdGame.GameMode;
import com.ltoscano.ia.flappybird.game.config.GeneticConfig;
import com.ltoscano.ia.flappybird.game.element.Bird;
import com.ltoscano.ia.flappybird.game.events.IGameOverEvent;
import com.ltoscano.ia.util.ImageHelper;
import com.ltoscano.ia.util.sort.QuickSort;
import com.ltoscano.ia.genetic.events.GenerationTerminatedEventListener;
import com.ltoscano.ia.genetic.events.IGenerationTerminatedEvent;
import com.ltoscano.ia.genetic.selection.INaturalSelection;
import com.ltoscano.ia.genetic.selection.TournamentSelection;
import com.ltoscano.ia.util.sort.SortHelper;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ltosc
 */
public class Generation implements IGameOverEvent
{
    private static Generation instance;
    
    private final int POPULATION_SIZE = GeneticConfig.POPULATION_SIZE;
    private final int ELITISM_SIZE = GeneticConfig.ELITISM_SIZE;
    private final double CROSSOVER_RATE = GeneticConfig.CROSSOVER_RATE;
    private final double MUTATION_RATE = GeneticConfig.MUTATION_RATE;
    
    private final int GENERATION_MATCH_COUNT = GeneticConfig.GENERATION_MATCH_COUNT;
    private int generationMatch;
    
    private final GenerationTerminatedEventListener generationTerminatedListener;
    
    private int genomeIndex = POPULATION_SIZE;
    
    private ArrayList<FlappyBirdPlayerAgent> birdControllerList;
    private HashMap<Integer, Genome> eliteGenomeList;
    private Genome bestEliteGenome;
    
    private final HashMap<Integer, Double> sumGenomeFitnessList;
    
    private int generation = 0;
    
    private int maxScore = 0;
    private int sumScore = 0;
    private double sumAvgFitness = 0;
    
    public Generation()
    {
        this.generationMatch = 0;
        this.generationTerminatedListener = new GenerationTerminatedEventListener();
        this.eliteGenomeList = new HashMap<>();
        
        sumGenomeFitnessList = new HashMap<>();
        
        for(int i = 0; i < POPULATION_SIZE; i++)
        {
            this.sumGenomeFitnessList.put(i, 0.0);
        }
    }
    
    public static void newInstance()
    {
        instance = new Generation();
    }
    
    public static Generation getInstance()
    {
        if (instance == null) 
        {
            instance = new Generation();
        }

        return instance;
    }
    
    public void addGenerationTerminatedListener(IGenerationTerminatedEvent listener)
    {
        this.generationTerminatedListener.addEventListener(listener);
    }
    
    public void startGeneration() throws IOException
    {
        birdControllerList = new ArrayList<>();
        FlappyBirdPlayerAgent flappyGameController;
        
        FlappyBirdGame.newInstance();
        FlappyBirdGame.getInstance().setGameMode(GameMode.Automatic);
        FlappyBirdGame.getInstance().removeFirstBird();

        for (int i = 0; i < POPULATION_SIZE; i++)
        {
            FlappyBirdGame.getInstance().addBird();

            Bird bird = FlappyBirdGame.getInstance().getBirdById(i);
            bird.setAutoControl(true);

            flappyGameController = new FlappyBirdPlayerAgent(bird);
            birdControllerList.add(flappyGameController);

            bird.addBirdMoveListener(flappyGameController);
        }

        FlappyBirdGame.getInstance().addGameOverListener(this);
        FlappyBirdGame.getInstance().startGame();
    }
    
    private void restartGeneration(List<Genome> genomeList)
    {
        FlappyBirdPlayerAgent birdController;
        this.birdControllerList = new ArrayList<>();
        
        FlappyBirdGame.getInstance().resetGame();
        FlappyBirdGame.getInstance().setGameMode(FlappyBirdGame.GameMode.Automatic);
        FlappyBirdGame.getInstance().removeFirstBird();
        
        for (int i = 0; i < genomeList.size(); i++) 
        {
            try 
            {
                FlappyBirdGame.getInstance().addBird();
                
                Bird bird = FlappyBirdGame.getInstance().getBirdById(i);
                bird.setAutoControl(true);
                
                Genome birdGenome = genomeList.get(i);
                
                if(birdGenome.isElite())
                {
                    bird.setCharacter(ImageHelper.loadImage("assets/img/birdGreen0.png"));
                    bird.setElite(true);
                    bird.setFitness(birdGenome.getFitness());
                }
                else if (birdGenome.isMutation())
                {
                    bird.setCharacter(ImageHelper.loadImage("assets/img/birdPurple0.png"));
                    bird.setMutation(true);
                    bird.setFitness(0);
                }
                else if (birdGenome.isCrossover())
                {
                    bird.setCharacter(ImageHelper.loadImage("assets/img/birdBlue0.png"));
                    bird.setCrossover(true);
                    bird.setFitness(0);
                }
                
                birdController = new FlappyBirdPlayerAgent(bird);
                birdController.setNetwork(birdGenome.buildNeuralNetwork());
                birdController.setBestPlayer(birdGenome.isBestGenome());
                
                birdControllerList.add(birdController);
                
                bird.addBirdMoveListener(birdController);
            } 
            catch (IOException ex) 
            {
                Logger.getLogger(Generation.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        FlappyBirdGame.getInstance().addGameOverListener(this);
        FlappyBirdGame.getInstance().startGame();
    }
    
    @Override
    public void onGameOver(FlappyBirdGame flappyGame)
    {
        try
        {
            if(generationMatch < (GENERATION_MATCH_COUNT - 1))
            {
                double sumFitness;
                int indexFitness = 0;
                
                // Lista com os genomas da geração atual
                List<Genome> genomeList = new ArrayList<>();
                
                // Acumulando o fitness de cada um dos pássaros para o total de rodadas
                for (FlappyBirdPlayerAgent controller : birdControllerList) 
                {
                    sumFitness = sumGenomeFitnessList.get(indexFitness);
                    sumFitness += controller.getBird().getFitness();
                    
                    sumGenomeFitnessList.put(indexFitness, sumFitness);
                    
                    // Guardando genomas
                    Genome genome = new Genome(controller.getNetwork());
                    genome.setElite(controller.getBird().isElite());
                    genome.setCrossover(controller.getBird().isCrossover());
                    genome.setMutation(controller.getBird().isMutation());
                    genomeList.add(genome);
                    
                    indexFitness++;
                }
                
                // Cria uma nova partida do game
                restartGeneration(genomeList);
                generationMatch++;
            }
            else
            {
                // Dispara evento de término de geração
                generationTerminatedListener.fireEvent(this);
                
                double avgBirdFitness;
                int indexBirdFitness = 0;
                
                // Lista com os genomas da geração atual
                List<Genome> genomeList = new ArrayList<>();
                
                // Pontuação máxima alcançada
                maxScore = (FlappyBirdGame.getInstance().getMaxScoreToBirds() > maxScore) ? FlappyBirdGame.getInstance().getMaxScoreToBirds() : maxScore;
                
                // Fitness de cada um dos pássaros para o total de rodadas
                for (FlappyBirdPlayerAgent controller : birdControllerList) 
                {
                    avgBirdFitness = ((sumGenomeFitnessList.get(indexBirdFitness++) + controller.getBird().getFitness()) / GENERATION_MATCH_COUNT);
                    
                    sumScore += controller.getBird().getBirdScore();
                    sumAvgFitness += avgBirdFitness;
                    
                    // Guardando genomas
                    Genome genome = new Genome(controller.getNetwork());
                    genome.setElite(controller.getBird().isElite());
                    genome.setCrossover(controller.getBird().isCrossover());
                    genome.setMutation(controller.getBird().isMutation());
                    genome.setScore(controller.getBird().getBirdScore());
                    genome.setFitness(avgBirdFitness);
                    genomeList.add(genome);
                }
                
                // Ordenando os genomas pelo fitness
                genomeList = QuickSort.sort(genomeList, QuickSort.SortType.Descending);
                Genome genome;
                
                // Lista com os genomas da próxima geração
                List<Genome> nextGenomeList = new ArrayList<>();
                
                for (int i = 0; (i < ELITISM_SIZE) && (i < genomeList.size()); i++) 
                {
                    genome = genomeList.get(i);
                    
                    if(generation == 0)
                    {
                        genome.setElite(true);
                        eliteGenomeList.put(genome.getId(), genome);
                    }
                    else
                    {
                        Genome eliteGenome;
                        Object eliteGenomeArray[] = eliteGenomeList.values().toArray();
                        
                        for(int j = 0; j < eliteGenomeArray.length; j++)
                        {
                            eliteGenome = (Genome)eliteGenomeArray[j];
                            
                            if (!eliteGenomeList.containsKey(genome.getId()) && (eliteGenome.getFitness() < genome.getFitness())) 
                            {
                                eliteGenome.setElite(false);
                                eliteGenomeList.remove(eliteGenome.getId());

                                genome.setElite(true);
                                eliteGenomeList.put(genome.getId(), genome);
                                
                                break;
                            }
                        }
                    }
                }
                
                eliteGenomeList = (HashMap)SortHelper.sortMap(eliteGenomeList);
                
                // Atualiza o melhor genoma
                if(bestEliteGenome != null)
                {
                    bestEliteGenome.setBestGenome(false);
                }
                
                bestEliteGenome = (Genome) eliteGenomeList.values().toArray()[eliteGenomeList.size() - 1];
                bestEliteGenome.setBestGenome(true);
                
                // Adicionando elite na próxima geração
                for(Genome eliteGenome : eliteGenomeList.values())
                {
                    nextGenomeList.add(eliteGenome);
                }

                // Seleção por torneio
                INaturalSelection naturalSelection = new TournamentSelection(3);

                // Crossover e Mutação
                while (nextGenomeList.size() < POPULATION_SIZE)
                {
                    Genome genomeA = naturalSelection.selection(genomeList);
                    Genome genomeB = naturalSelection.selection(genomeList);

                    List<Genome> genomeChildList = genomeA.crossover(genomeB, CROSSOVER_RATE);
                    
                    if(genomeChildList != null)
                    {
                        Genome childA = genomeChildList.get(0);
                        Genome childB = genomeChildList.get(1);
                        
                        childA.setId(genomeIndex++);
                        childA.mutate(MUTATION_RATE);
                        
                        childB.setId(genomeIndex++);
                        childB.mutate(MUTATION_RATE);
                        
                        nextGenomeList.add(childA);
                        nextGenomeList.add(childB);
                    }
                    else
                    {
                        genomeA.mutate(MUTATION_RATE);
                        genomeB.mutate(MUTATION_RATE);
                        
                        nextGenomeList.add(genomeA);
                        nextGenomeList.add(genomeB);
                    }
                }
                
                nextGenomeList = QuickSort.sort(nextGenomeList, QuickSort.SortType.Ascending);
                
                while(nextGenomeList.size() != POPULATION_SIZE)
                {
                    nextGenomeList.remove(0);
                }
                
                // Imprimindo informações da geração
                generation++;
                
                System.out.println(
                        generation + "	"
                        + (sumScore / POPULATION_SIZE) + "	"
                        + String.valueOf(sumAvgFitness / POPULATION_SIZE).replaceAll("\\.", ",") + "	"
                        + bestEliteGenome.getScore() + "	"
                        + String.valueOf(bestEliteGenome.getFitness()).replaceAll("\\.", ",") +  "	"
                        + new Date().toString());
                
                // Reseta o estado da partida de uma geração 
                generationMatch = 0;
                sumScore = 0;
                sumAvgFitness = 0;
                
                Iterator<Integer> sumGenomeFitnessListIt = sumGenomeFitnessList.keySet().iterator();
                Integer sumGenomeKey;
                
                while(sumGenomeFitnessListIt.hasNext())
                {
                    sumGenomeKey = sumGenomeFitnessListIt.next();
                    sumGenomeFitnessList.put(sumGenomeKey, 0.0);
                }
                
                // Cria uma nova partida do game
                restartGeneration(nextGenomeList);
            }
        }
        catch (Exception ex) 
        {
            Logger.getLogger(Generation.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void saveGeneration(String filePath)
    {
        // Lista com os genomas da geração atual
        List<Genome> genomeList = new ArrayList<>();

        // Criando genomas
        for (FlappyBirdPlayerAgent controller : birdControllerList) 
        {
            Genome genome = new Genome(controller.getNetwork());
            genome.setFitness(controller.getBird().getFitness());
            genome.setScore(controller.getBird().getBirdScore());
            genomeList.add(genome);
        }

        // Ordenando os genomas pelo fitness
        genomeList = QuickSort.sort(genomeList, QuickSort.SortType.Descending);
        
        for(int i = 0; i < genomeList.size(); i++)
        {
            try
            {
                genomeList.get(i).saveGenome(filePath, String.valueOf(i));
            } 
            catch (IOException ex)
            {
                Logger.getLogger(Generation.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public void loadGeneration(String filePath)
    {
        // Lista com os genomas
        List<Genome> genomeList = new ArrayList<>();
        
        File dir = new File(filePath);
        
        File[] genomeFiles = dir.listFiles(new FilenameFilter() 
        {
            @Override
            public boolean accept(File dir, String filename) 
            {
                return filename.endsWith(".dat");
            }
        });
        
        for(int i = 0; i < genomeFiles.length; i++)
        {
            try 
            {
                genomeList.add(Genome.loadGenome(genomeFiles[i].getAbsolutePath()));
            }
            catch (IOException | ClassNotFoundException ex) 
            {
                Logger.getLogger(Generation.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        FlappyBirdPlayerAgent flappyGameController;
        this.birdControllerList = new ArrayList<>();
        
        FlappyBirdGame.getInstance().resetGame();
        FlappyBirdGame.getInstance().setGameMode(FlappyBirdGame.GameMode.Automatic);
        FlappyBirdGame.getInstance().removeFirstBird();

        for (int i = 0; i < genomeList.size(); i++)
        {
            try
            {
                FlappyBirdGame.getInstance().addBird();

                Bird bird = FlappyBirdGame.getInstance().getBirdById(i);
                bird.setAutoControl(true);
                
                if (genomeList.get(i).isCrossover())
                {
                    bird.setCharacter(ImageHelper.loadImage("assets/img/birdBlue0.png"));
                } 
                else if (genomeList.get(i).isMutation())
                {
                    bird.setCharacter(ImageHelper.loadImage("assets/img/birdPurple0.png"));
                }
                
                flappyGameController = new FlappyBirdPlayerAgent(bird);
                flappyGameController.setNetwork(genomeList.get(i).buildNeuralNetwork());
                birdControllerList.add(flappyGameController);

                bird.addBirdMoveListener(flappyGameController);
            }
            catch (IOException ex)
            {
                Logger.getLogger(Generation.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        FlappyBirdGame.getInstance().addGameOverListener(this);
        FlappyBirdGame.getInstance().startGame();
    }
    
    public File[] finder( String dirName)
    {
        File dir = new File(dirName);

        return dir.listFiles(new FilenameFilter() 
        { 
                 @Override
                 public boolean accept(File dir, String filename)
                      { return filename.endsWith(".dat"); }
        } );

    }

    public int getGeneration() {
        return generation;
    }

    public int getGenerationMatch() {
        return generationMatch;
    }

    public int getMaxScore() {
        return maxScore;
    }
}
