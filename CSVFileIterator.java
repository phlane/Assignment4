package accidentpack;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class CSVFileIterator implements Iterator<String[]>, Closeable {

	BufferedReader reader;
	String current, next;
	String[] header;
	Boolean end;

	/**
	 * Constructor that accepts a file path and iterates across each line of a CSV
	 * file.
	 * 
	 * @param filePath
	 * @throws FileNotFoundException
	 * @throws IOException
	 */

	public CSVFileIterator(String filePath) throws FileNotFoundException, IOException {
		reader = new BufferedReader(new FileReader(filePath));
		next = reader.readLine();
		header = next();
		if (!hasNext()) {
			throw new IllegalStateException("File does not have items to iterate over");
		}
	}

	@Override
	public boolean hasNext() {
		return (next != null);
	}

	/**
	 * Returns next line of CSV as a String array
	 *
	 * @return next line as array, split by ,
	 */
	@Override
	public String[] next() {
		if (next == null) {
			throw new NoSuchElementException("No next line");
		} else {
			try {
				current = next;
				next = reader.readLine();
				return current.split(",");
			} catch (IOException ex) {
				throw new IllegalStateException(ex);
			}
		}
	}

	public void close() {
		try {
			reader.close();
		} catch (IOException ex) {
			System.err.println("Unable to close: " + ex.getMessage());
		}
	}

}
