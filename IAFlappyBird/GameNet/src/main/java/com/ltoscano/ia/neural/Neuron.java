package com.ltoscano.ia.neural;

import com.ltoscano.ia.neural.function.IActivationFunction;

/**
 *  Representa um neurônio artificial
 * 
 * @author ltosc
 */
public class Neuron 
{
    // Identificador do neurônio
    private final int id;
    // Tamanho da entrada do neurônio 
    private final int inputLenght;
    // Peso das entradas do neurônio
    private double weights[];
    // Bias
    private double bias;
    // Função de ativação do neurônio
    private IActivationFunction activationFunction;
    
    // Últimas entradas e saídas
    private double lastInput;
    private double lastOutput;
    
    public Neuron(int id, int inputLenght, IActivationFunction activationFunction)
    {
        if(inputLenght < 1)
        {
            throw new IllegalArgumentException("O tamanho da entrada deve ser maior ou igual a um");
        }
        
        if(activationFunction == null)
        {
            throw new IllegalArgumentException("A função de ativação do neurônio não foi definida");
        }
        
        this.id = id;
        this.inputLenght = inputLenght;
        this.weights = new double[inputLenght];
        this.activationFunction = activationFunction;
        
        for(int i = 0; i < inputLenght; i++)
        {
            this.weights[i] = MultiLayerPerceptron.getRangeMin()
                    + ((MultiLayerPerceptron.getRangeMax() - MultiLayerPerceptron.getRangeMin()) + 1)
                    * MultiLayerPerceptron.getRandomGenerator().nextDouble();
        }
        
        this.bias = 0;
    }
    
    public double activate(double input)
    {
        lastInput = input;
        lastOutput = activationFunction.calculate(lastInput);
        
        return lastOutput;
    }
    
    public double activate(double input[])
    {
        if(input.length != inputLenght)
        {
            throw new IllegalArgumentException("O tamanho da entrada não "
                    + "corresponde ao tamanho de entrada do neurônio");
        }
        
        return this.activate(getInputValue(input));
    }
    
    public int getId()
    {
        return this.id;
    }

    public int getInputLenght()
    {
        return this.inputLenght;
    }
    
    public double getInputValue(double input[])
    {
        double value = 0;
        
        for(int i = 0; i < inputLenght; i++)
        {
            value += input[i] * weights[i];
        }
        
        return value + bias;
    }

    public double[] getWeights() 
    {
        return weights;
    }
    
    public void setWeights(double weights[]) 
    {
        if(weights.length != inputLenght)
        {
            throw new IllegalArgumentException(
                    "O tamanho do novo vetor de pesos não corresponde "
                    + "ao tamanho de entradas do neurônio");
        }
        
        this.weights = weights;
    }
    
    public double getBias()
    {
        return this.bias;
    }
    
    public void setBias(double bias)
    {
        this.bias = bias;
    }
    
    public IActivationFunction getActivationFunction()
    {
        return this.activationFunction;
    }
    
    public void setActivationFunction(IActivationFunction activationFunction)
    {
        this.activationFunction = activationFunction;
    }
    
    public double getLastInput()
    {
        return this.lastInput;
    }
    
    public double getLastOutput()
    {
        return this.lastOutput;
    }
}
