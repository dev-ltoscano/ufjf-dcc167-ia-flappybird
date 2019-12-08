package com.ltoscano.ia.flappybird.game.events;

import com.ltoscano.ia.flappybird.game.base.FlappyBirdGame;

/**
 *
 * @author ltosc
 */
public class GameOverEventListener
{
    private IGameOverEvent eventListener;
    
    public void addListener(IGameOverEvent listener)
    {
        this.eventListener = listener;
    }
    
    public void removeListener()
    {
        this.eventListener = null;
    }
    
    public void fireEvent(FlappyBirdGame flappyGame)
    {
        if(eventListener != null)
        {
            this.eventListener.onGameOver(flappyGame);
        }
    }
}