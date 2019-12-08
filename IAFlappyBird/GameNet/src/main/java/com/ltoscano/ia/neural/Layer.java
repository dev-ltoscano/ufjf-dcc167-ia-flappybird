package com.ltoscano.ia.neural;

import com.ltoscano.ia.neural.function.IActivationFunction;
import java.util.ArrayList;

/**
 *  Representa uma camada de neurônios artificiais
 * 
 * @author ltosc
 */
public class Layer
{
    private final int id;
    private final int inputSize;
    private final ArrayList<Neuron> neuronList;
    
    public Layer(int id, int inputSize, int neuronSize, IActivationFunction activationFunction)
    {
        this.id = id;
        this.inputSize = inputSize;
        
        int neuronId = 0;
        this.neuronList = new ArrayList<>();
        
        for(int i = 0; i < neuronSize; i++)
        {
            this.neuronList.add(new Neuron(neuronId++, inputSize, activationFunction));
        }
    }
    
    public int getInputSize()
    {
        return inputSize;
    }
    
    public int getLayerSize()
    {
        return neuronList.size();
    }
    
    public ArrayList<Neuron> getNeuronList()
    {
        return neuronList;
    }
    
    public void setActivationFunction(IActivationFunction activationFunction)
    {
        for(Neuron neuron : neuronList)
        {
            neuron.setActivationFunction(activationFunction);
        }
    }
    
    public void setBias(double bias)
    {
        for(Neuron neuron : neuronList)
        {
            neuron.setBias(bias);
        }
    }
    
    public double[] evaluate(double input[]) 
    {
        double output[] = new double[neuronList.size()];
        
        for(int i = 0; i < neuronList.size(); i++)
        {
            output[i] = neuronList.get(i).activate(input);
        }
        
        return output;
    }
}
