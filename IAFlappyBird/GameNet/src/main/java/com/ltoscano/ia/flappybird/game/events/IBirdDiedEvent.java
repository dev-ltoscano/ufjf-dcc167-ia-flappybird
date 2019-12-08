package com.ltoscano.ia.flappybird.game.events;

import com.ltoscano.ia.flappybird.game.element.Bird;

/**
 *
 * @author ltosc
 */
public interface IBirdDiedEvent
{
    public void onBirdDied(Bird bird);
}
