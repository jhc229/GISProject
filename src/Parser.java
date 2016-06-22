import java.io.BufferedReader;
import java.io.BufferedWriter;
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
 * This is the main handler class which will initiate the program.
 * It begins with creating a new files, appending an existing file, and moving data by the given instructions.
 * This class interacts with other classes to finalize the log file.
 * 
 * 
 * @author sean
 *
 */
public class Parser {
	// ~ Fields
	// ................................................................
	private File dataFile = null;
	private FileReader commandFile = null;
	private FileWriter stat = null;
	

	// private File theRecord = null;
	private DataBase db = null;

	// ~ Parser Constructor
	// ................................................................
	/**
	 *  Constructor receives three file names from the GIS which will use them 
	 *  to create files, move data, and append data.
	 *
	 * @param cmdFile
	 *            The file received.
	 */
	public Parser(String dataFile, File commandFile, String logFile) {
		// db = new DataBase();
		// Takes the input stream file from the main class parse through each
		// individual line.
		try {
			this.dataFile = new File(dataFile);
			this.commandFile = new FileReader(commandFile);
			stat = new FileWriter(logFile);
			
			//db = new DataBase(recordFile, stat);
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
		BufferedWriter brRecord = null;
		BufferedReader brCommand = null;

		String line = "";

		try {
			brRecord = new BufferedWriter(new FileWriter(dataFile));
			brCommand = new BufferedReader(commandFile);

			stat.write("GIS data file contains the following records:   \n" );
			//db.dataPrint();

			int cmdCount = 1;
			while ((line = brCommand.readLine()) != null) {

				// remove all spaces and indents
				String[] str = line.trim().split("\\s+");
				
				if (str[0].matches("world")) {  // 4 coordinates westlong eastlong southlat northlat
					
					//db.world(str[1], str[2], str[3], str[4]);
					stat.write("world: "+ str[1] +" " + str[2] +" " + str[3] +" " + str[4] + "\n" );
					System.out.println("world: "+ str[1] +" " + str[2] +" " + str[3] +" " + str[4] );
					cmdCount++;
					
				} 
				// append existing datafile
				else if (str[0].matches("import")) {
					
				//	dataFile = BufferedWriter(new File(str[1]));
					//dataFile
					db = new DataBase(dataFile, stat);
				
					//	db.dataPrint();

					//db.importFile(str[1]); //GIS record file>
					stat.write("import: "+ str[1]  + "\n");
					System.out.println("import: "+ str[1] );
					cmdCount++;
					
				}
				
				else if (str[0].matches("what_is_at")) {
					
					//db.whatIsAt(str[1]); // geographic coordinate "382812N	0793156W "
					System.out.println("what_is_at: "+ str[1] +" " + str[2] );
					stat.write("what_is_at: "+ str[1] +" " + str[2] + "\n" );
					cmdCount++;

				} 
				else if (str[0].matches("what_is")) {
					
					//db.whatIs(str[1], str[2]); //<feature name> and <state abbreviation>
					String featureName = "";
					for (int i =1; i<str.length-1; i++){
						featureName += str[i] + " ";
					}
					System.out.println("what_is: "+ featureName +" "+ str[str.length-1]);
					stat.write("what_is: "+ featureName +" "+ str[str.length-1] + "\n");
					cmdCount++;

				} 
				else if (str[0].matches("what_is_in")) {
					//			<geographic coordinate>		<half-height>		<half-width>
					// -l		<geographic coordinate>		<half-height>		<half-width>
					// -c		<geographic coordinate>		<half-height>		<half-width>
					if  (str[1].matches("-l")){
					//	db.whatIsInL(str[2], str[3],  str[4]);
						System.out.println("what_is_in : "+ str[1] +" " + str[2] +" " + str[3] +" " + str[4] );
						stat.write("what_is_in : "+ str[1] +" " + str[2] +" " + str[3] +" " + str[4] + "\n");

					}
					else if (str[1].matches("-c")) {
					//	db.whatIsInC(str[2], str[3],  str[4]);
						System.out.println("what_is_in : "+ str[1] +" " + str[2] +" " + str[3] +" " + str[4] );
						stat.write("what_is_in : "+ str[1] +" " + str[2] +" " + str[3] +" " + str[4] + "\n" );

					}
					else {
						//db.whatIsIn(str[1], str[2],  str[3]);
						System.out.println("what_is_in : "+ str[1] +" " + str[2] +" " + str[3] );
						stat.write("what_is_in : "+ str[1] +" " + str[2] +" " + str[3] + "\n");

					}
					cmdCount++;

				}
				
				else if (str[0].matches("debug")) {
					
					//db.debug(str[1]);
					System.out.println("debug: "+ str[1] );
					stat.write("debug: "+ str[1] + "\n");
					cmdCount++;

				}
				else if (str[0].matches("quit")) {
					
					System.out.println("quit: " );
					stat.write("quit \n");

					//stat.append("\n quit");
					//db.quit();
					stat.close();
					
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}