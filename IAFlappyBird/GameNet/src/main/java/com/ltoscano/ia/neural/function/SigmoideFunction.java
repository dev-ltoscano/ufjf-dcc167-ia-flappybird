package com.ltoscano.ia.neural.function;

/**
 *
 * @author ltosc
 */
public class SigmoideFunction implements IActivationFunction
{
    private final double lambda;
    
    public SigmoideFunction(double lambda)
    {
        this.lambda = lambda;
    }
    
    @Override
    public double calculate(double input) 
    {
        return Math.pow((1 + Math.exp(-lambda * input)), -1);
    }

    @Override
    public double calculateDerivative(double input)
    {
        return ((lambda * Math.exp(lambda * input)) / Math.pow((Math.exp(lambda * input) + 1), 2));
    }
}
