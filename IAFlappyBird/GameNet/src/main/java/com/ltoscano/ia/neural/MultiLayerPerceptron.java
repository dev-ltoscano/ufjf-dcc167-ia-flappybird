package com.ltoscano.ia.neural;

import com.ltoscano.ia.neural.function.IActivationFunction;
import com.ltoscano.ia.neural.function.SigmoideFunction;
import java.util.ArrayList;
import java.util.Random;

/**
 * Rede neural de múltiplas camadas
 * 
 * @author ltosc
 */
public class MultiLayerPerceptron 
{
    private static final Random RANDOM_GENERATOR = new Random();
    private static double RANGE_MAX = 1;
    private static double RANGE_MIN = -1;
    
    private int id;
    
    private int idLayer;
    private ArrayList<Layer> hiddenLayerList;
    private Layer outputLayer;
    
    private IActivationFunction activationFunction;
    
    public MultiLayerPerceptron(int inputLayerSize, int hiddenLayerSize, int outputLayerSize)
    {
        // Define a função sigmóide para ativação dos neurônios da rede
        this(inputLayerSize, hiddenLayerSize, outputLayerSize, new SigmoideFunction(1), -1, 1);
    }
    
    public MultiLayerPerceptron(int inputLayerSize, int hiddenLayerSize, int outputLayerSize, double minWeight, double maxWeight)
    {
        // Define a função sigmóide para ativação dos neurônios da rede
        this(inputLayerSize, hiddenLayerSize, outputLayerSize, new SigmoideFunction(1), minWeight, maxWeight);
    }
    
    public MultiLayerPerceptron(
            int inputLayerSize, int hiddenLayerSize, int outputLayerSize, 
            IActivationFunction activationFunction, 
            double minWeight, double maxWeight)
    {
        RANGE_MAX = maxWeight;
        RANGE_MIN = minWeight;
        
        // Define a função de ativação que será usada pelos neurônios da rede
        this.activationFunction = activationFunction;
        
        this.idLayer = 0;
        this.hiddenLayerList = new ArrayList<>();
        
        // Adiciona uma camada oculta
        this.hiddenLayerList.add(new Layer(idLayer++, inputLayerSize, hiddenLayerSize, activationFunction));
        
        // Adiciona a camada de saída
        this.outputLayer = new Layer(idLayer, hiddenLayerSize, outputLayerSize, activationFunction);
    }
    
    public double[] evaluate(double[] input)
    {
        for(int i = 0; i < hiddenLayerList.size(); i++)
        {
            input = hiddenLayerList.get(i).evaluate(input);
        }
        
        return outputLayer.evaluate(input);
    }
    
    public int getInputLayerSize()
    {
        return this.hiddenLayerList.get(0).getInputSize();
    }
    
    public int getTotalNeurons()
    {
        int dimension = 0;
        
        for(Layer hiddenLayer : hiddenLayerList)
        {
            dimension += hiddenLayer.getLayerSize();
        }
        
        dimension += outputLayer.getLayerSize();
        
        return dimension;
    }
    
    public int getTotalWeights()
    {
        int dimension = 0;
        
        for(Layer hiddenLayer : hiddenLayerList)
        {
            for(Neuron hiddenNeuron : hiddenLayer.getNeuronList())
            {
                dimension += hiddenNeuron.getWeights().length;
            }
        }
        
        for(Neuron outputNeuron : outputLayer.getNeuronList())
        {
            dimension += outputNeuron.getWeights().length;
        }
        
        return dimension;
    }
    
    public void addHiddenLayer(int hiddenLayerSize)
    {
        if(hiddenLayerSize <= 0)
        {
            throw new IllegalArgumentException("O tamanho da camada é inválido");
        }
        
        int newInputLayerSize = this.hiddenLayerList.get(idLayer - 1).getLayerSize();
        int outputLayerSize = outputLayer.getLayerSize();
        
        this.hiddenLayerList.add(new Layer(idLayer++, newInputLayerSize, hiddenLayerSize, activationFunction));
        this.outputLayer = new Layer(idLayer, hiddenLayerSize, outputLayerSize, activationFunction);
    }
    
    public void removeHiddenLayer()
    {
        this.hiddenLayerList.remove(idLayer - 1);
        idLayer--;
        
        int hiddenLayerSize = this.hiddenLayerList.get(idLayer - 1).getLayerSize();
        int outputLayerSize = outputLayer.getLayerSize();
        this.outputLayer = new Layer(idLayer, hiddenLayerSize, outputLayerSize, activationFunction);
    }
    
    public ArrayList<Layer> getHiddenLayerList()
    {
        return this.hiddenLayerList;
    }
    
    public Layer getOutputLayer()
    {
        return this.outputLayer;
    }
    
    public double[] calculateCurrentOutputError(double currentOutput[], double outputExpected[])
    {
        if(currentOutput.length != outputExpected.length)
        {
            throw new IllegalArgumentException(
                    "O tamanho conjunto de saída da rede não corresponde ao "
                    + "tamanho do conjunto de saída esperada");
        }
        
        double[] currentError = new double[outputExpected.length];
        
        for(int i = 0; i < currentOutput.length; i++)
        {
            currentError[i] = outputExpected[i] - currentOutput[i];
        }
        
        return currentError;
    }
    
    public double calculateGlobalError(double currentError[])
    {
        double globalError = 0;
        
        for(int i = 0; i < currentError.length; i++)
        {
            globalError += Math.pow(currentError[i], 2);
        }
        
        return (globalError / 2);
    }
    
    public void train(ArrayList<double[]> inputTestList, ArrayList<double[]> outputExpectedList, double learningRate, double limiarError, int maxIteration)
    {
        int trainIteration = 0;
        double globalError = 0;
        ArrayList<double[]> trainError = new ArrayList<>();
        
        do
        {
            for (int z = 0; z < inputTestList.size(); z++)
            {
                globalError = 0;
                trainError.clear();
                
                double inputTest[] = inputTestList.get(z);
                double outputExpected[] = outputExpectedList.get(z);

                // Calcula a saída atual da rede para a entrada de dados
                double currentOutput[] = evaluate(inputTest);

                // Calcula o erro atual de cada neurônio de saída da rede
                double currentError[] = calculateCurrentOutputError(currentOutput, outputExpected);
                
                trainError.add(currentError);

                // Ajusta os pesos da camada de saída
                double neuronWeights[];
                double neuronBias;
                double delta;
                double outputGradient;

                ArrayList<Double> layerGradientList = new ArrayList<>();

                for (Neuron outputNeuron : outputLayer.getNeuronList()) {
                    neuronWeights = outputNeuron.getWeights();
                    outputGradient = -(currentError[outputNeuron.getId()] * activationFunction.calculateDerivative(outputNeuron.getLastInput()));

                    for (int i = 0; i < neuronWeights.length; i++) {
                        layerGradientList.add(outputGradient * neuronWeights[i]);

                        delta = -(learningRate * outputGradient * hiddenLayerList.get(hiddenLayerList.size() - 1).getNeuronList().get(i).getLastOutput());
                        neuronWeights[i] = neuronWeights[i] + delta;
                    }

                    delta = -(learningRate * outputGradient);
                    neuronBias = outputNeuron.getBias() + delta;

                    outputNeuron.setWeights(neuronWeights);
                    outputNeuron.setBias(neuronBias);
                }

                // Ajusta o peso das camadas ocultas
                Layer layer;
                double hiddenGradient;

                for (int i = (hiddenLayerList.size() - 1); i >= 0; i--) {
                    Layer previousLayer = ((i == 0) ? null : hiddenLayerList.get(i - 1));
                    layer = hiddenLayerList.get(i);
                    Layer nextLayer = (i == (hiddenLayerList.size() - 1)) ? outputLayer : hiddenLayerList.get(i + 1);

                    ArrayList<Double> newLayerGradient = new ArrayList<>();

                    for (Neuron hiddenNeuron : layer.getNeuronList()) {
                        neuronWeights = hiddenNeuron.getWeights();

                        double sumGradient = 0;
                        int index = hiddenNeuron.getId();

                        for (int j = 0; j < nextLayer.getLayerSize(); j++) {
                            sumGradient += layerGradientList.get(index);
                            index += nextLayer.getInputSize();
                        }

                        hiddenGradient = activationFunction.calculateDerivative(hiddenNeuron.getLastInput()) * sumGradient;

                        for (int j = 0; j < neuronWeights.length; j++) {
                            newLayerGradient.add(hiddenGradient * neuronWeights[j]);

                            double x = ((i == 0) ? inputTest[j] : previousLayer.getNeuronList().get(j % previousLayer.getLayerSize()).getLastOutput());
                            delta = -(learningRate * hiddenGradient * x);
                            neuronWeights[j] = neuronWeights[j] + delta;
                        }

                        delta = -(learningRate * hiddenGradient);
                        neuronBias = hiddenNeuron.getBias() + delta;

                        hiddenNeuron.setWeights(neuronWeights);
                        hiddenNeuron.setBias(neuronBias);
                    }

                    layerGradientList = newLayerGradient;
                }
            }
            
            for(int i = 0; i < trainError.size(); i++)
            {
                // Calcula o erro global da rede
                globalError += calculateGlobalError(trainError.get(i));
            }
            
            System.out.println();
            System.out.println("Train iteration: " + trainIteration);
            System.out.println("Global error: " + globalError);
            System.out.println("Limiar error: " + limiarError);
            System.out.println();
            
            trainIteration++;
        }
        while((trainIteration < maxIteration) && (globalError > limiarError));
    }
    
    public IActivationFunction getActivationFunction()
    {
        return this.activationFunction;
    }
    
    public void setActivationFunction(IActivationFunction activationFunction)
    {
        this.activationFunction = activationFunction;
        
        for(Layer layer : hiddenLayerList)
        {
            layer.setActivationFunction(activationFunction);
        }
        
        outputLayer.setActivationFunction(activationFunction);
    }
    
    public void setBias(double bias)
    {
        for(Layer layer : hiddenLayerList)
        {
            layer.setBias(bias);
        }
        
        outputLayer.setBias(bias);
    }
    
    public static Random getRandomGenerator() 
    {
        return RANDOM_GENERATOR;
    }
    
    /**
     * @return the RANGE_MAX
     */
    public static double getRangeMax() {
        return RANGE_MAX;
    }

    /**
     * @return the RANGE_MIN
     */
    public static double getRangeMin() {
        return RANGE_MIN;
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
}
