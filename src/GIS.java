import java.io.File;
import java.io.IOException;

//On my honor:
//
//- I have not discussed the Java language code in my program with
//anyone other than my instructor or the teaching assistants
//assigned to this course.
//
//- I have not used Java language code obtained from another student,
//or any other unauthorized source, either modified or unmodified.
//
//- If any Java language code or documentation used in my program
//was obtained from another source, such as a text book or course
//notes, that has been clearly noted with a proper citation in
//the comments of my program.
//
//- I have not designed this program in such a way as to defeat or
//interfere with the normal operation of the Curator System.
//
//<Jung H Choi>

/**
 * Executions command:
 *	java GIS <database file name> <command script file name> <log file name>
 *
 *
 * @author sean
 *
 */
public class GIS {
	
	/*
	 * The database file should be created as an empty file; note that the specified database file may already exist, in which case the
	 * existing file should be truncated or deleted and recreated. If the command script file is not found the program should write an
	 * error message to the console and exit. The log file should be rewritten every time the program is run, so if the file already
	 * exists it should be truncated or deleted and recreated.

	 */
    public static void main(String[] args) throws Exception
    {
        // This checks the number of arguments
        // to see if user typed in proper command; java Main {file-name}
        // trim ignores leading and trailing whitespace.
        String datFileName = args[0].trim();
        String commandFileName = args[1].trim();
        String LogFileName = args[2].trim();
        
      // File dataFile = new File(datFileName);
        //File commandFile = new File(commandFileName);
      //  File logFile = new File(LogFileName);

        // Takes the file to be parsed.
        Parser p = new Parser(datFileName, commandFileName, LogFileName);
        p.parsing(); // This begins the program
     }
}
