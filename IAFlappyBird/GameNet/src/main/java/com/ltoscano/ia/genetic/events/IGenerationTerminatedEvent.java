package com.ltoscano.ia.genetic.events;

import com.ltoscano.ia.genetic.Generation;

/**
 *
 * @author ltosc
 */
public interface IGenerationTerminatedEvent 
{
    public void onGenerationTerminated(Generation generation);
}
