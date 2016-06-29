import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.Scanner;
import java.io.RandomAccessFile;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;

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
	private File commmandFile = null;
	private File log = null;
	private FileReader commandReader = null;
	private BufferedWriter stat = null;
	

	// private File theRecord = null;
	private DataParser db = null;
	DateFormat date;
	Date newDate;

	// ~ Parser Constructor
	// ................................................................
	/**
	 *  Constructor receives three file names from the GIS which will use them 
	 *  to create files, move data, and append data.
	 *
	 * @param cmdFile
	 *            The file received.
	 */
	public Parser(String dataFile, String commandFile, String logFile) {
		// db = new DataBase();
		// Takes the input stream file from the main class parse through each
		// individual line.
		try {
			date = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			 
			this.dataFile				= new File(dataFile);
			this.commmandFile = new File(commandFile);
			log 								= new File(logFile);
			this.commandReader = new FileReader(commandFile);
			stat = new BufferedWriter(new FileWriter(log, false));
			
			db = new DataParser(this.dataFile, stat);
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
	 * @throws Exception 
	 */
	public void parsing() throws Exception {
	//	BufferedWriter brRecord = null;
		BufferedReader brCommand = null;

		String line = "";
		try {
			//brRecord = new BufferedWriter(new FileWriter(dataFile));
			brCommand = new BufferedReader(commandReader);
			newDate = new Date();

			int cmdCount = 1;
			int count = 0;
			while ((line = brCommand.readLine()) != null) {

				// remove all spaces and indents
				String[] str = line.trim().split("\\s+");
				
				if (str[0].matches(";")){
					
					stat.write(line + "\n" );
					System.out.println(line);
				}
				
				else if (str[0].matches("world")) {  // 4 coordinates westlong eastlong southlat northlat
					System.out.println("world: "+ str[1] +" " + str[2] +" " + str[3] +" " + str[4] );
					stat.write("world: "+ str[1] +" " + str[2] +" " + str[3] +" " + str[4] + "\n" );
					
					System.out.println("\nGIS Program\n");
					System.out.println("dbFile:\t"  +dataFile.getName());
					System.out.println("script:\t" + commmandFile.getName() );
					System.out.println("log:\t" +  log.getName());
					System.out.println("Start time: " + date.format(newDate) );
					System.out.println("Quadtree children are printed in the order SW  SE  NE  NW");
					System.out.println("--------------------------------------------------------------------------------\n");
					System.out.println("Latitude/longitude values in index entries are shown as signed integers, in total seconds.\n");
					System.out.println("World boundaries are set to:");
				
					stat.write("\nGIS Program\n\n");
					stat.write("dbFile:\t"  +dataFile.getName() + "\n");
					stat.write("script:\t" + commmandFile.getName() + "\n");
					stat.write("log:\t" + log.getName()+ "\n");
					stat.write("Start time: " + date.format(newDate) + "\n");
					stat.write("Quadtree children are printed in the order SW  SE  NE  NW"+ "\n");
					stat.write("--------------------------------------------------------------------------------\n"+ "\n");
					stat.write("Latitude/longitude values in index entries are shown as signed integers, in total seconds.\n"+ "\n");
					stat.write("World boundaries are set to:"+ "\n");
					db.world(str[1], str[2], str[3], str[4]);

					
					
				} 
				// append existing datafile
				else if (str[0].matches("import")) {
					
					stat.write("Command  "+ cmdCount+":	" + "import: "+ str[1]  + "\n"  );
					System.out.println("Command  "+ cmdCount+":	" + "import: "+ str[1] );
					db.appendFile((str[1]), count); // Add all the valid GIS records in the specified file to the database file.
					db.importFile(str[1]);
					count++; // # of import calls.
					cmdCount++;
					
				}
				
				else if (str[0].matches("what_is_at")) {
					
					System.out.println("Command  "+ cmdCount+":	" +"what_is_at	"+ str[1] +" " + str[2] +"\n" );
					stat.write("Command  "+ cmdCount+":	" +"what_is_at "+ str[1] +" " + str[2] + "\n" + "\n");
					db.whatIsAt(str[1], str[2]); // geographic coordinate "382812N	0793156W "
					
					cmdCount++;

				} 
				else if (str[0].matches("what_is")) {
					
					//<feature name> and <state abbreviation>
					String featureName = str[1];
					for (int i =2; i<str.length-1; i++){
						featureName += " "+ str[i];
					}
					System.out.println("Command  "+ cmdCount+":	"+ "what_is  " + featureName+" "+ str[str.length-1]);
					stat.write("Command  "+ cmdCount+":	"+ "what_is "+ featureName +" "+ str[str.length-1] + "\n" );

					db.whatIs(featureName, str[str.length-1]); // <feature name> and <state abbreviation> in string
					cmdCount++;

				} 
				else if (str[0].matches("what_is_in")) {
					//			<geographic coordinate>		<half-height>		<half-width>
					// -l		<geographic coordinate>		<half-height>		<half-width>
					// -c		<geographic coordinate>		<half-height>		<half-width>
					if  (str[1].matches("-l")){
						System.out.println("Command  "+ cmdCount+":	" + "what_is_in  "+ str[1] +" " + str[2] +" " + str[3] +" " + str[4] + " "+  str[5]  +"\n");
						stat.write("Command  "+ cmdCount+":	" +"what_is_in  "+ str[1] +" " + str[2] +" " + (str[3]) +" " + (str[4]) + " "+  str[5]   + "\n" + "\n");
						db.whatIsInL(str[2], str[3], str[4], str[5]);

					}
					else if (str[1].matches("-c")) {
						System.out.println("Command  "+ cmdCount+":	" + "what_is_in  "+ str[1] +" " + str[2] +" " + str[3] +" " + str[4] + " "+  str[5]  +"\n") ;
						stat.write("Command  "+ cmdCount+":	" +  "what_is_in  "+ str[1] +" " + str[2] +" " + str[3] +" " + str[4]+ " "+  str[5]  + "\n" + "\n");
						db.whatIsInC(str[2], str[3], str[4], str[5]);

					}
					else {
					//	db.whatIsIn(str[1], str[2],  Integer.parseInt(str[3]), Integer.parseInt(str[4]));
						System.out.println("Command  "+ cmdCount+":	" + "what_is_in  "+ str[1] +" " + str[2] +" " + str[3] +" " + str[4] +"\n");
						stat.write("Command  "+ cmdCount+":	" + "what_is_in  "+ str[1] +" " + str[2] +" " + str[3] +" " + str[4]+ "\n" + "\n");
						db.whatIsIn(str[1], str[2], str[3], str[4]);

					}
					cmdCount++;

				}
				
				else if (str[0].matches("debug")) {
					
					System.out.println("debug: "+ str[1] + "\n\n"  );
					db.debug(str[1]);
					stat.write("debug: "+ str[1] + "\n" );
					cmdCount++;

				}
				else if (str[0].matches("quit")) {
					
					System.out.println("Command "+ cmdCount + ":  quit\n" );
					System.out.println("Terminating execution of commands.\n" +LocalDateTime.now());

					newDate = new Date();
					System.out.println("End time:  " + date.format(newDate) );
					db.quit();
					
					//stat.append("\n quit");
					//db.quit();
					cmdCount++;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}