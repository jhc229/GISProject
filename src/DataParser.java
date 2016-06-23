import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.io.RandomAccessFile;
import java.io.Reader;

/**
 *
 * @author Jung Hyun Choi (jhc229)
 * @version 05.23.2016
 */
public class DataParser {
	// ~ Fields
	private RandomAccessFile dataFile = null;
	private long offset = 0;
	private long endOffset = 0;
	private BufferedWriter stat = null;
	// private Stats stat = null;

	// ~ Constructor
	/**
	 * Create a new dataParser object.
	 * 
	 * @throws IOException
	 */
	public DataParser(File gis, BufferedWriter result) throws IOException {
		dataFile = new RandomAccessFile(gis, "rw");
		stat = result;
		//offset = dataFile.readLine().length() + 1;
		
	}

	public void dataPrint() throws IOException {
		// offset = parserOffset;
		// System.out.println(" " + offset);
		while (dataFile.readLine() != null) {

			//System.out.println("	" + offset + "   " + id(offset));
			stat.write("\n    "+ offset + "   " + id(offset));
			dataFile.seek(offset);
			endOffset = offset;
			offset += dataFile.readLine().length() + 1;
			// stat.append("\nshow classes: " + id);
		}
		stat.write("\n");
		// dataFile.close();
	}

	/*
	 * Feature ID (FID) 
	 * non-negative integer
	 * unique identifier for this geographic feature
	 */
	public int id(long parserOffset) throws IOException {
		// offset = parserOffset;
		dataFile.seek(parserOffset);
		String line = dataFile.readLine();

		String[] items = line.split("\\|");
		int result = Integer.parseInt(items[0]);
		// System.out.println("delimiter: " + s.delimiter());
		// String token1 = s.next();
		return result;

	}
	
	public void appendFile(String gisRecordFile, int count) throws IOException {
		
		BufferedReader gisRecord = new BufferedReader(new FileReader(gisRecordFile)); 
		if (count == 0){
			gisRecord.readLine();
		}
		String str = "";
		while ((str= gisRecord.readLine()) != null) {
			System.out.println((str).getBytes());
			dataFile.write((str +"\n").getBytes());
		}
		gisRecord.close();
		stat.write("\n");
		// dataFile.close();
		
	}
	
	public void world(String westLong, String eastLong, String southLat, String northLat) {
		
		
	}

	public void importFile(String string) {
		// TODO Auto-generated method stub
		
	}

	public void whatIsAt(String string) {
		// TODO Auto-generated method stub
		
	}

	public void whatIs(String string, String string2) {
		// TODO Auto-generated method stub
		
	}

	public void whatIsInL(String string, String string2, String string3) {
		// TODO Auto-generated method stub
		
	}

	public void whatIsInC(String string, String string2, String string3) {
		// TODO Auto-generated method stub
		
	}
	
	public void whatIsIn(String string, String string2, String string3) {
		// TODO Auto-generated method stub
		
	}

	public void debug(String string) {
		// TODO Auto-generated method stub
		
	}



	/**
	 * 
	 * @throws IOException
	 */
	public void quit() throws IOException {
		stat.write("\n   Exiting");
		dataFile.close();
		stat.close();

	}




}