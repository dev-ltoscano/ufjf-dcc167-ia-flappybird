package com.ltoscano.ia.genetic.events;

import com.ltoscano.ia.genetic.Generation;

/**
 *
 * @author ltosc
 */
public class GenerationTerminatedEventListener 
{
    private IGenerationTerminatedEvent listener;
    
    public void addEventListener(IGenerationTerminatedEvent listener)
    {
        this.listener = listener;
    }
    
    public void removeEventListener()
    {
        this.listener = null;
    }
    
    public void fireEvent(Generation generation)
    {
        if(listener != null)
        {
            listener.onGenerationTerminated(generation);
        }
    }
}
