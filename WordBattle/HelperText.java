import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.awt.Color;

/**
 * HelperText lets a World display some text on the screen.
 * Only the String is needed, and the type of text is very simple.
 * 
 * @author Jason Yuen, Aaron Cheung
 * @version November 3, 2016
 */
public class HelperText extends Actor
{
    
    public HelperText(String text)
    {
        // make the image
        setImage(new GreenfootImage(text,24,Color.BLACK,new Color(0f,0f,0f,0f)));
    }
    
    /**
     * Do nothing.
     * This actor should not move at all
     */
    public void act() 
    {
        return;
    }
}
