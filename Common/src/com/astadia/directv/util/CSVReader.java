package src.com.astadia.directv.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

/**
 * This class is used to read the CSV file content (Headers and Records).
 * 
 * @author jkannan
 *
 */

public class CSVReader {

	private static Logger LOGGER = Logger.getLogger(CSVReader.class);
	
	private BufferedReader br;
	private boolean hasNext = true;
	private char separator;
	private char quotechar;
	private int skipLines;
	private boolean linesSkiped;
	public static final char DEFAULT_SEPARATOR = '|';
	public static final char DEFAULT_QUOTE_CHARACTER = '"';
	public static final int DEFAULT_SKIP_LINES = 0;

	/**
	 * the default separator.
	 * 
	 * @param reader
	 */
	public CSVReader(Reader reader) {
		this(reader, DEFAULT_SEPARATOR);
	}

	/**
	 * quote separator.
	 * 
	 * @param reader
	 * @param separator
	 */
	public CSVReader(Reader reader, char separator) {
		this(reader, separator, DEFAULT_QUOTE_CHARACTER);
	}

	/**
	 * initialize the CSV reader.
	 * 
	 * @param reader
	 * @param separator
	 * @param quotechar
	 */
	public CSVReader(Reader reader, char separator, char quotechar) {
		this(reader, separator, quotechar, 0);
	}

	/**
	 * initialize the CSV reader.
	 * @param reader
	 * @param separator
	 * @param quotechar
	 * @param line
	 */
	public CSVReader(Reader reader, char separator, char quotechar, int line) {
		this.br = new BufferedReader(reader);
		this.separator = separator;
		this.quotechar = quotechar;
		this.skipLines = line;
	}

	/**
	 * Read all the elements from the CSV file.
	 *  
	 * @return
	 * @throws IOException
	 */
	public List<Object> readAll() throws IOException {
		List allElements = new ArrayList();
		while (this.hasNext) {
			String[] nextLineAsTokens = readNext();
			if (nextLineAsTokens != null)
				allElements.add(nextLineAsTokens);
		}
		return allElements;
	}

	/**
	 * get the next row of the data in CSV
	 * @return
	 * @throws IOException
	 */
	public String[] readNext() throws IOException {
		String nextLine = getNextLine();
		return (this.hasNext) ? parseLine(nextLine) : null;
	}

	/**
	 * get the next row of the data from CSV
	 * @return
	 * @throws IOException
	 */
	private String getNextLine() throws IOException {
		if (!this.linesSkiped) {
			for (int i = 0; i < this.skipLines; ++i) {
				this.br.readLine();
			}
			this.linesSkiped = true;
		}
		String nextLine = this.br.readLine();
		if (nextLine == null) {
			this.hasNext = false;
		}
		return (this.hasNext) ? nextLine : null;
	}

	/**
	 * Parse the line.
	 * @param nextLine
	 * @return
	 * @throws IOException
	 */
	private String[] parseLine(String nextLine) throws IOException {
		if (nextLine == null) {
			return null;
		}

		List tokensOnThisLine = new ArrayList();
		StringBuffer sb = new StringBuffer();
		boolean inQuotes = false;
		do {
			if (inQuotes) {
				sb.append("\n");
				nextLine = getNextLine();
				if (nextLine == null)
					break;
			}
			for (int i = 0; i < nextLine.length(); ++i) {
				char c = nextLine.charAt(i);
				if (c == this.quotechar) {
					if ((inQuotes) && (nextLine.length() > i + 1)
							&& (nextLine.charAt(i + 1) == this.quotechar)) {
						sb.append(nextLine.charAt(i + 1));
						++i;
					} else {
						inQuotes = !inQuotes;
						if ((i <= 2)
								|| (nextLine.charAt(i - 1) == this.separator)
								|| (nextLine.length() <= i + 1)
								|| (nextLine.charAt(i + 1) == this.separator))
							continue;
						sb.append(c);
					}
				} else if ((c == this.separator) && (!inQuotes)) {
					tokensOnThisLine.add(sb.toString());
					sb = new StringBuffer();
				} else {
					sb.append(c);
				}
			}
		} while (inQuotes);
		tokensOnThisLine.add(sb.toString());
		return (String[]) tokensOnThisLine.toArray(new String[0]);
	}

	/**
	 * close Buffered Reader.
	 *
	 * @throws IOException
	 */
	public void close() throws IOException {
		LOGGER.debug("Close Buffered Reader");
		this.br.close();
	}
}