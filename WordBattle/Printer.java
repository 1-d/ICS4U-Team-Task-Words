import java.util.Scanner;
import java.io.InputStream;

/**
* A printer.
*/
public class Printer 
{
    /**
     * Opens a text file inside the package folder and creates a scanner to read it which is returned. Works for text files inside jar file.
     * 
     * @param name The name of the text file
     * @return A Scanner that is reading the contents of the text file.
     */
    public Scanner getTextFile(String fileName){
        InputStream myFile = getClass().getResourceAsStream(fileName);
        if(myFile != null){
            return new Scanner(myFile);
        }
        return null;
    }
}