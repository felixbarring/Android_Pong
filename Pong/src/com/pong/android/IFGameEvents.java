package com.pong.android;

/* 
 *  Designed to be implemented so that classes from the modell
 *  can notify the implementing Activity that some specific
 *  event that need to be handled has occurred.
 */

public interface IFGameEvents {
    
    void playerWin();
    
    void playerLose(int number);

}
