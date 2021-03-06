import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.ArrayList;
import java.util.Collections;

/**
 * This World generates a word battle scene.
 * Many words appear on the screen and compete to live as long as possible.
 * The top word will occupy the most space.
 * However, as a word ages, its font size will decrease!
 * The resulting movement is unpredictable and fun to watch.
 * 
 * @author Jason Yuen, Aaron Cheung
 * @version November 3, 2016
 */
public class WordBattle extends World
{

    WordGenerator w = new WordGenerator();          // get the word generator

    // constants for the word table
    final int topX = 100;                           // the X position of the best Word
    final int topY = 20;                            // the Y position of the best Word
    final int rowSize = 6;                          // number of Words in a row
    final int rowSep = 40;                          // each row is separated by this many units
    final int colSep = 150;                         // the words in a row are separated by this many units
    int wordCount = 78;                             // number of Word actors in total
    int wordCutoff = 66;                            // all Word actors after this rank get recycled

    ArrayList<Word> words = new ArrayList<Word>();  // the Word actors are conveniently stored here

    // each phase is a period of rest where Words are not being handled
    // all of the action happens in a phase transition
    // 0 to 1   -- the Words are ranked according to their fitness
    // 1 to 2   -- the Words that don't make the cutoff are fading now
    // 2 to 0   -- new Words replace the faded Words
    int phase = 0;

    final int PHASE_0_LENGTH = 100;                 // length of phase 0
    final int PHASE_1_LENGTH = 100;                 // length of phase 1
    final int PHASE_2_LENGTH = 100;                 // length of phase 2
    int timeLeft = 0;                               // amount of time until the next phase transition

    /**
     * The constructor initializes the Word actors that will be used.
     */
    public WordBattle()
    {    
        // Create a new world with 960x640 cells with a cell size of 1x1 pixels.
        super(960, 640, 1);
        
        prepare();
    }

    /**
     * Prepare the world for the start of the program. That is: create the initial
     * objects and add them to the world.
     */
    private void prepare()
    {
        // create the helper text
        addObject(new HelperText("Welcome to WordBattle!"), 480, 545);
        addObject(new HelperText("Left: Speed up the animation"), 480, 570);
        addObject(new HelperText("Right: Freeze the animation"), 480, 595);
        addObject(new HelperText("Please enjoy the project!"), 480, 620);

        // create the list
        for (int i=0; i<wordCount; i++) {
            Word tempWord = new Word(w.getWord());
            addObject(tempWord,0,0);
            words.add(tempWord);
        }
    }

    /**
     * Sort the words according to their fitness.
     * Each word has a luckiness value which prevents ties from happening
     */
    private void sortWords()
    {
        // call a Java sorting algorithm
        Collections.sort(words);
    }

    /**
     * Move the words to their appropriate locations.
     * The locations depend on the position in the sorted ArrayList.
     */
    private void moveWords()
    {
        for (int i=0; i<wordCount; i++) {
            // get the row and column of the word at this index
            int row=i%rowSize;
            int col=i/rowSize;
            // get the x and y positions
            // x = topX+colSep*row
            // y = topY+rowSep*col
            words.get(i).setFinalPosition(topX+colSep*row, topY+rowSep*col);
        }
    }

    /**
     * Calls fadeAway() on every Word not making the cutoff.
     */
    private void filterWords()
    {
        // reduce fitness of surviving words
        for (int i=0; i<wordCutoff; i++) {
            words.get(i).reduceFitness();
        }
        // fades away words that don't make it
        for (int i=wordCutoff; i<wordCount; i++) {
            words.get(i).fadeAway();
        }
    }

    /**
     * Resets every faded Word and supplies a new word.
     */
    private void resetFadedWords()
    {
        // reset all of the words that were faded
        for (int i=wordCutoff; i<wordCount; i++) {
            words.get(i).setWord(w.getWord());
        }
    }

    /**
     * Counts down the timeLeft.
     * When timeLeft is 0, a phase transition occurs.
     * The "left" key speeds up the countdown.
     * The "right" key freezes the countdown.
     */
    public void act()
    {
        // action depends on "phase" and "timeLeft"
        if (timeLeft > 0) {
            // wait until the next phase
            timeLeft--;
            if (Greenfoot.isKeyDown("left")) {
                timeLeft-=2;
            }
            if (Greenfoot.isKeyDown("right")) {
                // freeze by undoing the decrement
                timeLeft++;
            }
        }
        else {
            // change of phase
            if (phase == 0) {
                // do sorting and moving
                sortWords();
                moveWords();
                // done
                phase = 1;
                timeLeft = PHASE_1_LENGTH;
            }
            else if (phase == 1) {
                // remove certain words
                filterWords();
                // done
                phase = 2;
                timeLeft = PHASE_2_LENGTH;
            }
            else if (phase == 2) {
                // replace with new words
                resetFadedWords();
                // done
                phase = 0;
                timeLeft = PHASE_0_LENGTH;
            }
        }
    }
}
