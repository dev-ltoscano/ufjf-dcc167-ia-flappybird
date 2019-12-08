package com.ltoscano.ia.flappybird.game.events;

import com.ltoscano.ia.flappybird.game.element.Bird;
import java.util.ArrayList;

/**
 *
 * @author ltosc
 */
public class BirdLandedEventListener 
{
    private final ArrayList<IBirdLandedEvent> eventListeners;
    
    public BirdLandedEventListener()
    {
        this.eventListeners = new ArrayList<>();
    }
    
    public int size()
    {
        return this.eventListeners.size();
    }
    
    public void addListener(IBirdLandedEvent listener)
    {
        this.eventListeners.add(listener);
    }
    
    public void removeListener(IBirdLandedEvent listener)
    {
        this.eventListeners.remove(listener);
    }
    
    public void clearAllListener()
    {
        this.eventListeners.clear();
    }
    
    public void fireEvent(Bird bird)
    {
        for(IBirdLandedEvent listener : eventListeners)
        {
            listener.onBirdLanded(bird);
        }
    }
}
