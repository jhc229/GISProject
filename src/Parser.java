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
	public Parser(File recordFile, File commandFile, File logFile) {
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

			stat.append("GIS data file contains the following records:");
			stat.append("\n");
			db.dataPrint();

			int cmdCount = 1;
			while ((line = brCommand.readLine()) != null) {

				// remove all spaces and indents
				String[] str = line.trim().split("\\s+");
				
				if (str[0].matches("world")) {  // 4 coordinates westlong eastlong southlat northlat
					
					//db.world(str[1], str[2], str[3], str[4]);
					//stat.append("\n show_name:		" + x);
					System.out.println("world: "+ str[1] +" " + str[2] +" " + str[3] +" " + str[4] );
					cmdCount++;
					
				} 
				
				else if (str[0].matches("import")) {
					
					//db.importFile(str[1]); //GIS record file>
					System.out.println("import: "+ str[1] );
					cmdCount++;
					
				}
				
				else if (str[0].matches("what_is_at")) {
					
					//db.whatIsAt(str[1]); // geographic coordinate "382812N	0793156W "
					System.out.println("what_is_at: "+ str[1] +" " + str[2] );

					//stat.append("\n show_longitude:		" + x);
					cmdCount++;

				} 
				else if (str[0].matches("what_is")) {
					
					//db.whatIs(str[1], str[2]); //<feature name> and <state abbreviation>
					String featureName = "";
					for (int i =1; i<str.length-1; i++){
						featureName += str[i] + " ";
					}
					System.out.println("what_is: "+ featureName +" "+ str[str.length-1]);
					//stat.append("\n show_longitude:		" + x);
					cmdCount++;

				} 
				else if (str[0].matches("what_is_in")) {
					//			<geographic coordinate>		<half-height>		<half-width>
					// -l		<geographic coordinate>		<half-height>		<half-width>
					// -c		<geographic coordinate>		<half-height>		<half-width>
					if  (str[1].equals("-l")){
						//db.whatIsInL(str[2], str[3],  str[4]);
						System.out.println("what_is_in : "+ str[1] +" " + str[2] +" " + str[3] +" " + str[4] );

					}
					
					else if (str[1].equals("-c")) {
						//db.whatIsInC(str[2], str[3],  str[4]);
						System.out.println("what_is_in : "+ str[1] +" " + str[2] +" " + str[3] +" " + str[4] );
					}
					else {
						//db.whatIsIn(str[1], str[2],  str[3]);
						System.out.println("what_is_in : "+ str[1] +" " + str[2] +" " + str[3] );

					}

					
					//stat.append("\n show_latitude:		" + x);
					cmdCount++;

				}
				
				else if (str[0].matches("debug")) {
					
					//db.debug(str[1]);
					System.out.println("debug: "+ str[1] );

					//stat.append("\n show_latitude:		" + x);
					cmdCount++;

				}
				else if (str[0].matches("quit")) {
					
					System.out.println("quit: " );
					//stat.append("\n quit");
					//db.quit();
					//stat.close();
					
				}

			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}