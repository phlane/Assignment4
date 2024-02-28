/**
 * 
 */
package accidentpack;

import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * 
 */
public class program4 {

	public void run(String filePath) {
		
	}
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
        if (args.length != 2) {
            System.err.println("Usage: program3 filePath");
            System.exit(1);
        }
        try {
            program4 driver = new program4();
            driver.run(args[0]);
        } catch (FileNotFoundException ex) {
            System.err.println("Unable to find file: " + args[0]);
            System.exit(2);
        } catch (IOException ex) {
            System.err.println("Read issues with: " + args[0]);
            System.exit(3);
        }
    }

}
