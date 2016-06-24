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
	//private long offset= 0 ;
	//private long endOffset = 0;
	private BufferedWriter stat = null;
	private GISRecordParser gisRecords =null;
	
	private int wLong, eLong , sLat , nLat; //boundary
	
	private GeoCoordinates geoCoord;
	private DMScoordinates coord;
	// private Stats stat = null;

	//public prQuadTree<Point> quadTree;
//	public BufferPool pool;
	//public HashTable<>
	
	// ~ Constructor
	/**
	 * Create a new dataParser object.
	 * 
	 * @throws IOException
	 */
	public DataParser(File gis, BufferedWriter result)  {
		
		
		try {
			dataFile = new RandomAccessFile(gis, "rw");
			stat = result;
			//pool = new BufferPool();
			//table = new HashTable<NameIndex, Integer>(1024);
			//offset  = 0;

			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

	}

	
	public void appendFile(String gisRecordFile, int count) throws IOException {
		
		BufferedReader gisRecord = new BufferedReader(new FileReader(gisRecordFile)); 
		if (count >0){
			gisRecord.readLine(); // Moves the pointer to next line
		}
		String str = "";
		while ((str= gisRecord.readLine()) != null) { 
			System.out.println((str).getBytes());
			dataFile.write((str +"\n").getBytes());
		}
		gisRecord.close();
		stat.write("\n");
		
	}
	
	//One degree is equal to 60 minutes and equal to 3600 seconds:
	public void world(String westLong, String eastLong, String southLat, String northLat) throws IOException {
				
		wLong = toCoord(Integer.parseInt(westLong.substring(0, 2)), Integer.parseInt(westLong.substring(2, 4)), Integer.parseInt(westLong.substring(4, 6)), westLong.substring(6)).toSeconds();
		eLong = toCoord(Integer.parseInt(westLong.substring(0, 2)), Integer.parseInt(westLong.substring(2, 4)), Integer.parseInt(westLong.substring(4, 6)), westLong.substring(6)).toSeconds();
		sLat =  toCoord(Integer.parseInt(westLong.substring(0, 3)), Integer.parseInt(westLong.substring(3, 5)), Integer.parseInt(westLong.substring(5, 7)), westLong.substring(7)).toSeconds();
		nLat = toCoord(Integer.parseInt(westLong.substring(0, 3)), Integer.parseInt(westLong.substring(3, 5)), Integer.parseInt(westLong.substring(5, 7)), westLong.substring(7)).toSeconds();

	//	quadTree = new prQuadTree<Point>(wLong, eLong , sLat , nLat);
		stat.write(wLong+ " "+ eLong +" "+ sLat +" "+ nLat);
		
	}
	
	/**
	 * 
	 * @param degree
	 * @param Minute
	 * @param Second
	 * @param direction
	 * @return
	 */
	private DMScoordinates toCoord(int degree, int Minute, int Second, String direction){
		return coord = new DMScoordinates(degree, Minute, Second, direction);
	}

	public void importFile(String string) throws IOException {
		
		
		
		while (dataFile.readLine() != null) {

			NameIndex name = new NameIndex( )
			
			
		}
		stat.write("\n");
		// dataFile.close();
		
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