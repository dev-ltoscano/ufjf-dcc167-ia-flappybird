package com.ltoscano.ia.genetic;

import com.ltoscano.ia.neural.Layer;
import com.ltoscano.ia.neural.MultiLayerPerceptron;
import com.ltoscano.ia.neural.Neuron;
import com.ltoscano.ia.neural.function.IActivationFunction;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Random;

/**
 *
 * @author ltosc
 */
public class Genome implements Serializable, Comparable
{
    private static final Random RANDOM_GENERATOR = new Random();
    
    private int id;
    
    private final double networkGenome[];
    private final double networkBias[];
    
    private int score;
    private double fitness;
    
    private final int neuronCount;
    private final int inputLayerSize;
    private final int hiddenLayerCount;
    private final List<Integer> hiddenLayerSizeList;
    private final int outputLayerSize;
    private final IActivationFunction activationFunction;
    private final double minWeight;
    private final double maxWeight;
    
    private boolean mutation;
    private boolean crossover;
    private boolean elite;
    private boolean bestGenome;
    
    public Genome(MultiLayerPerceptron neuralNetwork)
    {
        this.id = neuralNetwork.getId();
        
        this.networkGenome = buildGenome(neuralNetwork);
        this.networkBias = getNetworkBias(neuralNetwork);
        
        this.neuronCount = neuralNetwork.getTotalNeurons();
        this.inputLayerSize = neuralNetwork.getInputLayerSize();
        this.outputLayerSize = neuralNetwork.getOutputLayer().getLayerSize();
        
        this.hiddenLayerCount = neuralNetwork.getHiddenLayerList().size();
        this.hiddenLayerSizeList = new ArrayList<>();
        
        for(Layer hiddenLayer : neuralNetwork.getHiddenLayerList())
        {
            hiddenLayerSizeList.add(hiddenLayer.getLayerSize());
        }
        
        this.activationFunction = neuralNetwork.getActivationFunction();
        
        this.minWeight = MultiLayerPerceptron.getRangeMin();
        this.maxWeight = MultiLayerPerceptron.getRangeMax();
    }
    
    public Genome(double networkGenome[], double networkBias[], 
            int neuronCount, int inputLayerSize, int hiddenLayerCount,
            List<Integer> hiddenLayerSizeList, int outputLayerSize,
            IActivationFunction activationFunction) 
    {
        this.networkGenome = networkGenome;
        this.networkBias = networkBias;
        
        this.neuronCount = neuronCount;
        this.inputLayerSize = inputLayerSize;
        this.outputLayerSize = outputLayerSize;
        
        this.hiddenLayerCount = hiddenLayerCount;
        this.hiddenLayerSizeList = hiddenLayerSizeList;
        this.activationFunction = activationFunction;
        
        this.minWeight = MultiLayerPerceptron.getRangeMin();
        this.maxWeight = MultiLayerPerceptron.getRangeMax();
    }
    
    private double[] buildGenome(MultiLayerPerceptron neuralNetwork)
    {
        double genome[] = new double[neuralNetwork.getTotalWeights()];
        int index = 0;
        
        for(Layer hiddenLayer : neuralNetwork.getHiddenLayerList())
        {
            for(Neuron hiddenNeuron : hiddenLayer.getNeuronList())
            {
                for(double weight : hiddenNeuron.getWeights())
                {
                    genome[index++] = weight;
                }
            }
        }
        
        for(Neuron outputNeuron : neuralNetwork.getOutputLayer().getNeuronList())
        {
            for (double weight : outputNeuron.getWeights())
            {
                genome[index++] = weight;
            }
        }
        
        return genome;
    }
    
    private double[] getNetworkBias(MultiLayerPerceptron neuralNetwork)
    {
        double bias[] = new double[neuralNetwork.getTotalNeurons()];
        int index = 0;
        
        for(Layer hiddenLayer : neuralNetwork.getHiddenLayerList())
        {
            for(Neuron hiddenNeuron : hiddenLayer.getNeuronList())
            {
                bias[index++] = hiddenNeuron.getBias();
            }
        }
        
        for(Neuron outputNeuron : neuralNetwork.getOutputLayer().getNeuronList())
        {
            bias[index++] = outputNeuron.getBias();
        }
        
        return bias;
    }
    
    public MultiLayerPerceptron buildNeuralNetwork() 
    {
        // Reconstrói a estrutura da rede
        MultiLayerPerceptron network = new MultiLayerPerceptron(
                getInputLayerSize(), 
                getHiddenLayerSizeList().get(0), 
                getOutputLayerSize(), 
                activationFunction,
                minWeight, maxWeight);
        
        network.setId(id);
        
        for(int i = 1; i < hiddenLayerSizeList.size(); i++)
        {
            network.addHiddenLayer(hiddenLayerSizeList.get(i));
        }
        
        // Refaz os pesos da rede
        double neuronWeights[];
        int genomeIndex = 0;
        int biasIndex = 0;
        
        for(Layer hiddenLayer : network.getHiddenLayerList())
        {
            for(Neuron hiddenNeuron : hiddenLayer.getNeuronList())
            {
                neuronWeights = new double[hiddenNeuron.getWeights().length];
                
                for(int i = 0; i < neuronWeights.length; i++)
                {
                    neuronWeights[i] = getNetworkGenome()[genomeIndex++];
                }
                
                hiddenNeuron.setWeights(neuronWeights);
                hiddenNeuron.setBias(getNetworkBias()[biasIndex++]);
            }
        }
        
        for(Neuron outputNeuron : network.getOutputLayer().getNeuronList())
        {
            neuronWeights = new double[outputNeuron.getWeights().length];

            for (int i = 0; i < neuronWeights.length; i++) 
            {
                neuronWeights[i] = getNetworkGenome()[genomeIndex++];
            }
            
            outputNeuron.setWeights(neuronWeights);
            outputNeuron.setBias(getNetworkBias()[biasIndex++]);
        }
        
        return network;
    }
    
    public List<Genome> crossover(Genome otherGenome, double crossoverRate)
    {
        // Taxa de crossover
        if(Math.random() > crossoverRate)
        {
            return null;
        }
        
        if(!this.equals(otherGenome))
        {
            throw new IllegalArgumentException("Os genomas não possuem o mesmo tamanho");
        }
        
        List<Genome> childGenomeList = new ArrayList<>();
        
        int crossGenomePos = (int)(networkGenome.length * RANDOM_GENERATOR.nextDouble());
        int crossBiasPos = (int)(networkBias.length * RANDOM_GENERATOR.nextDouble());
        
        double newGenomeA[] = new double[networkGenome.length];
        double newBiasA[] = new double[networkBias.length];
        
        double newGenomeB[] = new double[networkGenome.length];
        double newBiasB[] = new double[networkBias.length];
        
        int index = 0;
        
        // ChildA
        for(int i = 0; i < crossGenomePos; i++)
        {
            newGenomeA[index++] = networkGenome[i];
        }
        
        for(int i = crossGenomePos; i < networkGenome.length; i++)
        {
            newGenomeA[index++] = otherGenome.getNetworkGenome()[i];
        }
        
        index = 0;
        
        for(int i = 0; i < crossBiasPos; i++)
        {
            newBiasA[index++] = networkBias[i];
        }
        
        for(int i = crossBiasPos; i < networkBias.length; i++)
        {
            newBiasA[index++] = otherGenome.getNetworkBias()[i];
        }
        
        index = 0;
        
        // ChildB
        for(int i = crossGenomePos; i < networkGenome.length; i++)
        {
            newGenomeB[index++] = networkGenome[i];
        }
        
        for(int i = 0; i < crossGenomePos; i++)
        {
            newGenomeB[index++] = otherGenome.getNetworkGenome()[i];
        }
        
        index = 0;
        
        for(int i = crossBiasPos; i < networkBias.length; i++)
        {
            newBiasB[index++] = networkBias[i];
        }
        
        for(int i = 0; i < crossBiasPos; i++)
        {
            newBiasB[index++] = otherGenome.getNetworkBias()[i];
        }
        
        Genome childGenomeA = new Genome(newGenomeA, newBiasA, neuronCount, inputLayerSize, 
                hiddenLayerCount, hiddenLayerSizeList, outputLayerSize,
                activationFunction);
        childGenomeA.setCrossover(true);
        
        Genome childGenomeB = new Genome(newGenomeB, newBiasB, neuronCount, inputLayerSize, 
                hiddenLayerCount, hiddenLayerSizeList, outputLayerSize,
                activationFunction);
        childGenomeB.setCrossover(true);
        
        childGenomeList.add(childGenomeA);
        childGenomeList.add(childGenomeB);

        return childGenomeList;
    }
    
    public void mutate(double mutateRate)
    {
        for (int i = 0; i < networkGenome.length; i++)
        {
            if (Math.random() > mutateRate) 
            {
                continue;
            }
            
            networkGenome[i] += MultiLayerPerceptron.getRandomGenerator().nextDouble() - 0.5;
            
//            networkGenome[i] = MultiLayerPerceptron.getRangeMin()
//                    + ((MultiLayerPerceptron.getRangeMax() - MultiLayerPerceptron.getRangeMin()) + 1)
//                    * MultiLayerPerceptron.getRandomGenerator().nextDouble();
            
            mutation = true;
        }
    }
    
    public Genome copyGenome()
    {
        return new Genome(networkGenome, networkBias, neuronCount, inputLayerSize,
                hiddenLayerCount, hiddenLayerSizeList, outputLayerSize,
                activationFunction);
    }
    
    public void saveGenome(String filePath, String fileName) throws FileNotFoundException, IOException
    {
        try (FileOutputStream fileOutputStream = new FileOutputStream(String.format("%s/genome_%s.dat", filePath, fileName))) 
        {
            try (ObjectOutputStream objOutputStream = new ObjectOutputStream(fileOutputStream))
            {
                objOutputStream.writeObject(this);
            }
        }
    }
    
    public static Genome loadGenome(String filePath) throws IOException, ClassNotFoundException
    {
        try (FileInputStream fileInputStream = new FileInputStream(filePath))
        {
            try (ObjectInputStream objInputStream = new ObjectInputStream(fileInputStream))
            {
                Object obj = objInputStream.readObject();
                
                if(obj instanceof Genome)
                {
                    return (Genome)obj;
                }
            }
        }
        
        return null;
    }

    /**
     * @return the networkGenome
     */
    public double[] getNetworkGenome() {
        return networkGenome;
    }

    /**
     * @return the networkBias
     */
    public double[] getNetworkBias() {
        return networkBias;
    }

    /**
     * @return the neuronCount
     */
    public int getNeuronCount() {
        return neuronCount;
    }

    /**
     * @return the inputLayerSize
     */
    public int getInputLayerSize() {
        return inputLayerSize;
    }

    /**
     * @return the hiddenLayerCount
     */
    public int getHiddenLayerCount() {
        return hiddenLayerCount;
    }

    /**
     * @return the hiddenLayerSizeList
     */
    public List<Integer> getHiddenLayerSizeList() {
        return hiddenLayerSizeList;
    }

    /**
     * @return the outputLayerSize
     */
    public int getOutputLayerSize() {
        return outputLayerSize;
    }
    
    /**
     * @return the mutation
     */
    public boolean isMutation() 
    {
        return mutation;
    }
    
    public void setMutation(boolean mutation)
    {
        this.mutation = mutation;
    }
    
    /**
     * @return the fitness
     */
    public double getFitness() {
        return fitness;
    }

    /**
     * @param fitness the fitness to set
     */
    public void setFitness(double fitness) {
        this.fitness = fitness;
    }
    
    @Override
    public int hashCode() 
    {
        int hash = 7;
        hash = 59 * hash + Arrays.hashCode(this.networkGenome);
        hash = 59 * hash + Arrays.hashCode(this.networkBias);
        hash = 59 * hash + this.neuronCount;
        hash = 59 * hash + this.inputLayerSize;
        hash = 59 * hash + this.hiddenLayerCount;
        hash = 59 * hash + Objects.hashCode(this.hiddenLayerSizeList);
        hash = 59 * hash + this.outputLayerSize;
        return hash;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (this == obj) 
        {
            return true;
        }
        
        if (obj == null) 
        {
            return false;
        }
        
        if (getClass() != obj.getClass()) 
        {
            return false;
        }
        
        final Genome other = (Genome) obj;
        
        if (this.neuronCount != other.neuronCount)
        {
            return false;
        }
        if (this.inputLayerSize != other.inputLayerSize)
        {
            return false;
        }
        if (this.hiddenLayerCount != other.hiddenLayerCount)
        {
            return false;
        }
        if (this.outputLayerSize != other.outputLayerSize) 
        {
            return false;
        }
        if (this.networkGenome.length != other.networkGenome.length)
        {
            return false;
        }
        if (this.networkBias.length != other.networkBias.length)
        {
            return false;
        }
        if (this.hiddenLayerSizeList.size() !=  other.hiddenLayerSizeList.size())
        {
            return false;
        }
        
        return true;
    }
    
    @Override
    public int compareTo(Object obj2) 
    {
        if((obj2 != null) && (obj2 instanceof Genome))
        {
            return Double.compare(fitness, ((Genome)obj2).getFitness());
        }
        
        return 0;
    }

    /**
     * @return the crossover
     */
    public boolean isCrossover() {
        return crossover;
    }

    /**
     * @param crossover the crossover to set
     */
    public void setCrossover(boolean crossover) {
        this.crossover = crossover;
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the score
     */
    public int getScore() {
        return score;
    }

    /**
     * @param score the score to set
     */
    public void setScore(int score) {
        this.score = score;
    }

    /**
     * @return the elite
     */
    public boolean isElite() {
        return elite;
    }

    /**
     * @param elite the elite to set
     */
    public void setElite(boolean elite) {
        this.elite = elite;
    }

    /**
     * @return the bestGenome
     */
    public boolean isBestGenome() {
        return bestGenome;
    }

    /**
     * @param bestGenome the bestGenome to set
     */
    public void setBestGenome(boolean bestGenome) {
        this.bestGenome = bestGenome;
    }
}
