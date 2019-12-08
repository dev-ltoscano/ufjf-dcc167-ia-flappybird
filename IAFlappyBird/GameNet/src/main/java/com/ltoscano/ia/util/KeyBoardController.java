package com.ltoscano.ia.util;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 *
 * @author ltosc
 */
public class KeyBoardController implements KeyListener
{
    private static KeyBoardController instance;
    private final boolean[] keyState;
    
    private KeyBoardController() 
    {
        keyState = new boolean[256];
    }

    public static KeyBoardController getInstance()
    {
        if (instance == null) 
        {
            instance = new KeyBoardController();
        }

        return instance;
    }
    
    @Override
    public void keyPressed(KeyEvent event)
    {
        if ((event.getKeyCode() >= 0) && (event.getKeyCode() < keyState.length))
        {
            keyState[event.getKeyCode()] = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent event)
    {
        if ((event.getKeyCode() >= 0) && (event.getKeyCode() < keyState.length))
        {
            keyState[event.getKeyCode()] = false;
        }
    }

    @Override
    public void keyTyped(KeyEvent event) { }

    public boolean isKeyDown(int key) 
    {
        if ((key >= 0) && (key < keyState.length))
        {
            return keyState[key];
        }

        return false;
    }
}
