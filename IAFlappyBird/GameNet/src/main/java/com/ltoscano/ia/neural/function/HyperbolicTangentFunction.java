package com.ltoscano.ia.neural.function;

/**
 *
 * @author ltosc
 */
public class HyperbolicTangentFunction implements IActivationFunction
{
    @Override
    public double calculate(double input) 
    {
        return Math.tanh(input);
    }

    @Override
    public double calculateDerivative(double input)
    {
        return ((4 * Math.pow(Math.cosh(input), 2)) / Math.pow((1 + Math.cosh(2 * input)), 2));
    }
}
