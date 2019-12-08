package com.ltoscano.ia.neural.function;

import java.io.Serializable;

/**
 *
 * @author ltosc
 */
public interface IActivationFunction extends Serializable
{
    public double calculate(double input);
    public double calculateDerivative(double input);
}
