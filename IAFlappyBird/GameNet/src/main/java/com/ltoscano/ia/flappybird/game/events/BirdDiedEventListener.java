package com.ltoscano.ia.flappybird.game.events;

import com.ltoscano.ia.flappybird.game.element.Bird;
import java.util.ArrayList;

/**
 *
 * @author ltosc
 */
public class BirdDiedEventListener
{
    private final ArrayList<IBirdDiedEvent> eventListeners;
    
    public BirdDiedEventListener()
    {
        this.eventListeners = new ArrayList<>();
    }
    
    public int size()
    {
        return this.eventListeners.size();
    }
    
    public void addListener(IBirdDiedEvent listener)
    {
        this.eventListeners.add(listener);
    }
    
    public void removeListener(IBirdDiedEvent listener)
    {
        this.eventListeners.remove(listener);
    }
    
    public void clearAllListener()
    {
        this.eventListeners.clear();
    }
    
    public void fireEvent(Bird bird)
    {
        for(IBirdDiedEvent listener : eventListeners)
        {
            listener.onBirdDied(bird);
        }
    }
}
