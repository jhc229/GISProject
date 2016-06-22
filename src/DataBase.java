import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.io.RandomAccessFile;

/**
 *
 * @author Jung Hyun Choi (jhc229)
 * @version 05.23.2016
 */
public class DataBase {
	// ~ Fields
	private RandomAccessFile read = null;
	private long offset = 0;
	private long endOffset = 0;
	private FileWriter stat = null;
	// private Stats stat = null;

	// ~ Constructor
	/**
	 * Create a new DataBase object.
	 * 
	 * @throws IOException
	 */
	public DataBase(File gis, FileWriter result) throws IOException {
		read = new RandomAccessFile(gis, "r");
		stat = result;
		offset = read.readLine().length() + 1;
		
	}

	public void dataPrint() throws IOException {
		// offset = parserOffset;
		// System.out.println(" " + offset);
		while (read.readLine() != null) {

			//System.out.println("	" + offset + "   " + id(offset));
			stat.append("\n    "+ offset + "   " + id(offset));
			read.seek(offset);
			endOffset = offset;
			offset += read.readLine().length() + 1;
			// stat.append("\nshow classes: " + id);
		}
		stat.append("\n");
		// read.close();
	}

	/*
	 * Feature ID (FID) 
	 * non-negative integer
	 * unique identifier for this geographic feature
	 */
	public int id(long parserOffset) throws IOException {
		// offset = parserOffset;
		read.seek(parserOffset);
		String line = read.readLine();

		String[] items = line.split("\\|");
		int result = Integer.parseInt(items[0]);
		// System.out.println("delimiter: " + s.delimiter());
		// String token1 = s.next();
		return result;

	}

	/**
	 * Feature name
	 * string
	 * standard name of feature
	 * @param parserOffset
	 * @throws IOException 
	 */
	public String name(long parserOffset) throws IOException {
		if ((parserOffset >= 265) && (parserOffset <= endOffset)) {
			read.seek(parserOffset);
			// String line = read.readLine();

			String[] items = read.readLine().split("\\|");
			// int result = Integer.parseInt(items[0]);
			stat.append("\n   " + items[1] + "  items 7 and 8  " + items[7].charAt(items[7].length() - 1) +" " + items[9]);
			return items[1];
		} else {
			if ((parserOffset >= 0) && (parserOffset < 265)){
				stat.append("\n   Unaligned offset");
				return null;
			}
			else if (parserOffset > offset){
				stat.append("\n   Offset too large");
				return null;
			}
			stat.append("\n   Offset is not positive");
			return null;
		}
	}

	/**
	 * Primary latitude (DMS) DDMMSS['N' | 'S'] feature latitude in DMS format
	 * or UNKNOWN
	 * 
	 * @param parserOffset
	 * @throws IOException
	 */
	public String latitude(long parserOffset) throws IOException {
		//read.seek(parserOffset);
		//String[] items = read.readLine().split("\\|");
		//if (decLatitude(items[9]) == 0) {
		//	stat.append("\n   Coordinate not given");
		//} 
		if ((parserOffset >= 265) && (parserOffset <= endOffset)) {
			read.seek(parserOffset);
			String[] items = read.readLine().split("\\|");
			if (decLatitude(items[9]) == 0) {
				stat.append("\n   Coordinate not given");
				return null;
			} 
			String direction = "" + items[7].charAt(items[7].length() - 1);
			switch (direction) {
			case "N":
				direction = "North";
				break;
			case "S":
				direction = "South";
				break;
			}
			// int result = Integer.parseInt(items[0]);
			stat.append("\n   " + items[7].substring(0, 2) + "d " + Integer.parseInt(items[7].substring(2, 4)) + "m " + Integer.parseInt(items[7].substring(4, 6)) + "s " + direction + "items[15] and items[16]@" + items[15]+ "@" + items[16] + "@");
			return "";
		} 
		else {
			if ((parserOffset >= 0) && (parserOffset < 265)){
				stat.append("\n   Unaligned offset");
				return null;
			}
			else if (parserOffset > offset){
				stat.append("\n   Offset too large");
				return null;
			}
			stat.append("\n   Offset is not positive");
			return null;
		}

	}

	public String longtitude(long parserOffset) throws IOException {
		//read.seek(parserOffset);
		// String line = read.readLine();
		//String[] items = read.readLine().split("\\|");
		//if (decLatitude(items[10]) == 0) {
		//	stat.append("\n   Coordinate not given");
		//} 
		if ((parserOffset >= 265) && (parserOffset <= endOffset)) {
			 read.seek(parserOffset);
			// String line = read.readLine();
			 String[] items = read.readLine().split("\\|");
			if (decLatitude(items[10]) == 0) {
				stat.append("\n   Coordinate not given");
				return null;
			} 
			String direction = "" + items[8].charAt(items[8].length() - 1);
			switch (direction) {
			case "E":
				direction = "East";
				break;
			case "W":
				direction = "West";
				break;
			}
			// int result = Integer.parseInt(items[0]);
			stat.append("\n   " + items[8].substring(0, 3) + "d " + Integer.parseInt(items[8].substring(3, 5)) + "m " + Integer.parseInt(items[8].substring(5, 7)) + "s " + direction);
			return "";
		} 
		else {
			if ((parserOffset >= 0) && (parserOffset < 265)){
				stat.append("\n   Unaligned offset");
				return null;
			}
			else if (parserOffset > offset){
				stat.append("\n   Offset too large");
				return null;			
			}
			stat.append("\n   Offset is not positive");
			return null;
		}

	}

	/**
	 * Feature elevation in feet
	 * integer 
	 * altitude above/below sea level, optional
	 * @param parserOffset
	 * @return
	 * @throws IOException 
	 */
	public int elevation(long parserOffset) throws IOException {
		//stat.append("\n   " + parserOffset);		
		if ((parserOffset >= 265) && (parserOffset <= endOffset)) {
			 read.seek(parserOffset);
			// String line = read.readLine();
			 String[] items = read.readLine().split("\\|");
			if (items[16] == null) {
				stat.append("\n   Elevation not given");
				return -1;
			}
			stat.append("\n   " + items[16]);
			return Integer.parseInt(items[16]);

		}
		
		else {
			if ((parserOffset >= 0) && (parserOffset < 265)){
				stat.append("\n   Unaligned offset");
				return -1;
			}
			else if (parserOffset > offset){
				stat.append("\n   Offset too large");
				return -1;			
			}
			stat.append("\n   Offset is not positive");
		}
		return -1;
	}
	
	public double decLongitude(String lo) {
		return Double.parseDouble(lo);
	}
	
	public double decLatitude(String lat) {
		return Double.parseDouble(lat);
	}



	/**
	 * Feature class
	 * string
	 * descriptive classification of feature
	 * @param parserOffset
	 * @return
	 */
	public String clsses(long parserOffset) {
		return null;
		// list = new SkipList<String, Rectangle>();
	}

	/**
	 * State alphabetic code
	 * two-characters
	 * US postal code abbreviation
	 * @param parserOffset
	 * @return
	 */
	public String postalCode(long parserOffset) {
		return null;
	}

	/**
	 * State numeric code
	 * non-negative integer
	 * numeric code for state
	 * @param parserOffset
	 * @return
	 */
	public int stateCode(long parserOffset) {
		return 0;
	}

	/**
	 * County name 
	 * string 
	 * county in which feature occurs
	 * @param parserOffset
	 * @return
	 */
	public String county(long parserOffset) {
		return null;
	}

	/**
	 * County numeric code 
	 * non-negative integer 
	 * numeric code for county
	 * @param parserOffset
	 * @return
	 */
	public String countyCode(long parserOffset) {
		return null;
	}

	
	/**
	 * 
	 * @throws IOException
	 */
	public void quit() throws IOException {
		stat.append("\n   Exiting");
		read.close();
		stat.close();

	}

}