import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.awt.Color;

/**
 * The Word class is an actor which displays a word to the screen.
 * The Word can move smoothly to a new location on the World.
 * 
 * The fitness of the Word matters the most.
 * Whenever the fitness is too low, the Word will be forced to fade and eventually the actor gets a new String.
 * 
 * @author Jason Yuen
 * @version October 17, 2016
 */
public class Word extends Actor implements Comparable<Word>
{

    // information about the current action of the word
    // fadeNow == false     -- not fading to transparent
    // fadeNow == true      -- fade to transparent
    private boolean fadeNow;

    final double MOVEMENT = 0.95;       // ratio of movement
    private double oldX = 0;            // current actor X
    private double oldY = 0;            // current actor Y
    private int newX = 0;               // actor goes towards this new X
    private int newY = 0;               // actor goes towards this new X

    private String word;                // the actor's word
    private int fontSize;               // the word's font size
    private double fitness;             // how well a word is doing, higher is better
    final private double luckiness;     // luckiness of the actor -- a tiebreaker

    final float fadeFactor = 0.02f;     // how quickly this actor would fade away
    private float fade;                 // transparency -- decreases to 0f when action=1

    /**
     * Construct the actor.
     */
    public Word(String newWord)
    {
        luckiness = Math.random();
        setWord(newWord);
    }

    /**
     * Get the fitness of a word.
     * A higher fitness results in a better position in the list.
     */
    private void calculateFitness()
    {
        fitness = (this.getImage().getWidth() * this.getImage().getHeight())*10;
        fitness += luckiness;
    }

    /**
     * Reset the word that this object contains.
     */
    public void setWord(String newWord)
    {
        // reset variables
        fadeNow = false;
        word = newWord;
        fontSize = 24;
        fade = 1f;
        // reset more variables -- the image, the fitness
        redraw();
        calculateFitness();
    }

    /**
     * Set the final position of the actor.
     */
    public void setFinalPosition(int x, int y)
    {
        newX = x;
        newY = y;
    }

    /**
     * Move the actor slightly to the desired position.
     */
    private void calculatePosition()
    {
        // get new positions of X and Y
        // the idea is to calculate the weighted average
        oldX = (oldX * MOVEMENT + newX * (1-MOVEMENT));
        oldY = (oldY * MOVEMENT + newY * (1-MOVEMENT));
        // move the actor here
        setLocation((int)oldX, (int)oldY);
    }

    /**
     * Reduces the fitness of the organism.
     * The fitness is reduced by lowering the font size of the Word.
     */
    public void reduceFitness()
    {
        fontSize--;
        redraw();
        calculateFitness();
    }

    /**
     * Make the actor fade away now.
     */
    public void fadeAway()
    {
        fadeNow = true;
    }

    /**
     * Redraw the Greenfoot Image according to its variables.
     */
    private void redraw()
    {
        // get the image
        // new Greenfoot Image(word, fontSize, wordColor, backgroundColor)
        GreenfootImage tempImage = new GreenfootImage(word, fontSize, new Color(0f,0f,0f,fade), new Color(0f,0f,0f,0f));
        setImage(tempImage);
    }

    /**
     * Return the fitness of the Word.
     */
    public double getFitness()
    {
        return fitness;
    }

    /**
     * act() causes the actor to move slightly to the desired position.
     * In addition, if fadeNow is true, the actor is faded a little and redrawn.
     */
    public void act()
    {
        // move to new location
        calculatePosition();
        if (fadeNow) {
            // fade the actor
            // fade's value cannot be negative
            fade -= fadeFactor;
            if (fade < 0f) {
                fade = 0f;
            }
            // done fading, redraw now
            redraw();
        }
    }
    
    @Override
    public int compareTo(Word w2)
    {
        // w2 is better than this: return positive
        // w2 is equal to this:    return zero
        // w2 is worse than this:  return negative
        if (w2.fitness > this.fitness) {
            return 1;
        }
        else if (w2.fitness == this.fitness) {
            return 0;
        }
        else {
            return -1;
        }
    }
}
