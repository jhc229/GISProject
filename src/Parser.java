import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.io.RandomAccessFile;

/**
 * 
 */
// On my honor:
//
// - I have not discussed the Java language code in my program with
// anyone other than my instructor or the teaching assistants
// assigned to this course.
//
// - I have not used Java language code obtained from another student,
// or any other unauthorized source, either modified or unmodified.
//
// - If any Java language code or documentation used in my program
// was obtained from another source, such as a text book or course
// notes, that has been clearly noted with a proper citation in
// the comments of my program.
//
// - I have not designed this program in such a way as to defeat or
// interfere with the normal operation of the Curator System.
//
// <Jung H Choi>

/**
 * This is the main class that will do most of the parsing from the text files.
 * 
 * @author sean
 *
 */
public class Parser {
	// ~ Fields
	// ................................................................
	private FileReader theRecordFile = null;
	private FileReader theCommandFile = null;
	// private File theRecord = null;
	private DataBase db = null;
	private FileWriter stat = null;

	// ~ Parser Constructor
	// ................................................................
	/**
	 * Constructor receives file command line.
	 *
	 * @param cmdFile
	 *            The file received.
	 */
	public Parser(File recordFile, File commandFile,File logFile) {
		// db = new DataBase();
		// Takes the input stream file from the main class parse through each
		// individual line.
		try {
			// theRecord = recordFile;
			theRecordFile = new FileReader(recordFile);
			theCommandFile = new FileReader(commandFile);
			stat = new java.io.FileWriter("Results.txt", true);
			db = new DataBase(recordFile, stat);
		}

		// I/O exception checks for any failure or interruption during the
		// operation
		catch (IOException e) {
			e.printStackTrace();
		}
	}

	// ................................................................
	/**
	 * Parses the file and execute commands. Throws the exception when user
	 * tries to access unspecified elements for instance, when user enters
	 * search "someName" "x y w h".
	 * 
	 * @throws NumberFormatException
	 *             Format error.
	 */
	public void parsing() throws NumberFormatException {
		BufferedReader brRecord = null;
		BufferedReader brCommand = null;

		String line = "";

		try {
			brRecord = new BufferedReader(theRecordFile);
			brCommand = new BufferedReader(theCommandFile);
			// Scanner read = new Scanner(theRecord);
			// read.useDelimiter("|");
			// RandomAccessFile read = new RandomAccessFile(theRecord, "r");
			// long offset = read.readLine().length() + 1;

			stat.append("GIS data file contains the following records:");
			stat.append("\n");
			//System.out.println("record      : ");
			db.dataPrint();

			// while (read.readLine() != null){

			// System.out.println(" " + db.offset() + " " + db.id());

			// read.seek(offset);
			// offset += read.readLine().length() + 1;
			// stat.append("\nshow classes: " + id);
			// }
			// read.close();
			while ((line = brCommand.readLine()) != null) {

				// remove all spaces and indents
				String[] str = line.trim().split("\\s+");

				int count = str.length;
				// first element for each line would always start with a command
				if (str[0].matches("show_name")) {
					int x = Integer.parseInt(str[1]);
					// db.showName(x); // parameter will take one for now
					stat.append("\n show_name:		" + x);
					db.name(x);
				} else if (str[0].matches("show_elevation")) {
					int x = Integer.parseInt(str[1]);
					// db.showElevation(x); // parameter will take one for now
					stat.append("\n show_elevation:		" + x);
					db.elevation(x);

				} else if (str[0].matches("show_longitude")) {
					int x = Integer.parseInt(str[1]);
					// db.showLongitude(x);
					stat.append("\n show_longitude:		" + x);
					db.longtitude(x);

				} else if (str[0].matches("show_latitude")) {
					int x = Integer.parseInt(str[1]);
					// db.showLatitude(x);
					stat.append("\n show_latitude:		" + x);
					db.latitude(x);

				} else if (str[0].matches("quit")) {
					// db.quit();
					stat.append("\n quit");
					db.quit();
					stat.close();
				}

			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}