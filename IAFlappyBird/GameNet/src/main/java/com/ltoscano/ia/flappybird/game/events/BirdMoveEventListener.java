package com.ltoscano.ia.flappybird.game.events;

import com.ltoscano.ia.flappybird.game.element.Bird;

/**
 *
 * @author ltosc
 */
public class BirdMoveEventListener 
{
    private IBirdMoveEvent listener;

    public void addListener(IBirdMoveEvent listener)
    {
        this.listener = listener;
    }
    
    public void removeListener()
    {
        this.listener = null;
    }
    
    public void fireEvent(Bird bird)
    {
        if(listener != null)
        {
            listener.onBirdMove(bird);
        }
    }
}
