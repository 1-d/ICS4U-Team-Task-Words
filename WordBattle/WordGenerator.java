
import java.util.Scanner;

import java.util.Stack;
import java.util.Collections;

/**
 * This class deals with getting words from a file.
 * After initialization, it returns a random arrangement of words with the getWord() method.
 * 
 * @author Jason Yuen
 * @version November 4, 2016
 */
public class WordGenerator
{
    // file path to a list of words
    final String WORD_PATH = "nouns.txt";

    // stack of words
    Stack<String> newWords = new Stack<String>();
    Stack<String> oldWords = new Stack<String>();

    /**
     * Initializes the stack with the list of words
     */
    public WordGenerator()
    {
        // get the input in a suitable format
        Scanner sc = new Printer().getTextFile(WORD_PATH);

        // read all of the words and store in a stack
        while (sc.hasNext()) {
            newWords.add(sc.next());
        }

        // shuffle the stack
        Collections.shuffle(newWords);
    }

    /**
     * Gets a random word from a stack.
     * When the stack runs out, the words get shuffled and recycled.
     * This way, it is unlikely to draw the same word twice.
     * 
     * @return A randomly chosen words
     */
    public String getWord() {
        // possibly done with the list
        if (newWords.size() == 0) {
            // reset the stacks
            newWords = oldWords;
            oldWords = new Stack<String>();
            Collections.shuffle(newWords);
        }

        // get the word now
        String out = newWords.pop();
        oldWords.add(out);
        return out;
    }
}
