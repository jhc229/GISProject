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
	private File data = null;
	
	private long offset;
	private long endOffset = 0;
	private BufferedWriter stat = null;
	private GISRecordParser gisRecords =null; // GIS  record parts
	
	private int wLong, eLong , sLat , nLat; //boundary
	
	private GeoCoordinates geoCoord;
	private DMScoordinates coord;
	// private Stats stat = null;

	//public prQuadTree<Point> quadTree;
//	public BufferPool pool;
	public HashTable<NameIndex, Integer> table;
	
	// ~ Constructor
	/**
	 * Create a new dataParser object.
	 * 
	 * @throws IOException
	 */
	public DataParser(File dataFile, BufferedWriter result)  {
		
		
		try {
			data = dataFile;
			this.dataFile = new RandomAccessFile(dataFile, "rw");
			stat = result;
			offset = 265;
			//endOffset = 265;
			//pool = new BufferPool();
			//table = new HashTable<NameIndex, Integer>(1024);
			//offset  = 0;

			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		table = new HashTable<NameIndex, Integer>(1024);

	}

	
	public void appendFile(String gisRecordFile, int count) throws IOException {
		
		BufferedReader gisRecord = new BufferedReader(new FileReader(gisRecordFile)); 
		//dataFile = new RandomAccessFile(data, "rw");
		//RandomAccessFile gisRecord = new RandomAccessFile(gisRecordFile, "r");
		String str = "";
		if (count > 0){
			gisRecord.readLine();
			dataFile.seek(endOffset);
		}

		while ((str =gisRecord.readLine()) != null) { 
			//dataFile.write((str +"\n").getBytes());
			//System.out.println(str);
			dataFile.write((str + "\n").getBytes());
			//System.out.println(str.length());

		}
		endOffset = dataFile.getFilePointer();
		dataFile.seek(265);

		gisRecord.close();
		stat.write("\n");
		
	}
	
	//One degree is equal to 60 minutes and equal to 3600 seconds:
	public void world(String westLong, String eastLong, String southLat, String northLat) throws IOException {
				
		wLong = toCoord(Integer.parseInt(westLong.substring(0, 3)), Integer.parseInt(westLong.substring(3, 5)), Integer.parseInt(westLong.substring(5, 7)), westLong.substring(7)).toSeconds();
		eLong = toCoord(Integer.parseInt(eastLong.substring(0, 3)), Integer.parseInt(eastLong.substring(3, 5)), Integer.parseInt(eastLong.substring(5, 7)), eastLong.substring(7)).toSeconds();
		sLat =  toCoord(Integer.parseInt(southLat.substring(0, 2)), Integer.parseInt(southLat.substring(2, 4)), Integer.parseInt(southLat.substring(4, 6)), southLat.substring(6)).toSeconds();
		nLat = toCoord(Integer.parseInt(northLat.substring(0, 2)), Integer.parseInt(northLat.substring(2, 4)), Integer.parseInt(northLat.substring(4, 6)), northLat.substring(6)).toSeconds();

	//	quadTree = new prQuadTree<Point>(wLong, eLong , sLat , nLat);
		System.out.println("world to seconds:" + wLong+ " "+ eLong +" "+ sLat +" "+ nLat);
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

	/**
	 * When insert is completed log the number of entries added to each index, and the longest probe sequence that was needed when
	 * inserting to the hash table. (A valid record is one that lies within the specified world boundaries.)
	 * @param string
	 * @throws IOException
	 */
	public void importFile() throws IOException, GISRecordException {
		
		gisRecords = new GISRecordParser(dataFile, endOffset); // Begin at the second line where records start.
		int countIdx = 0;
		while (dataFile.readLine() != null) {
			//System.out.println(gisRecords.gisRecordsUpdate());
			dataFile.seek(offset);
			//System.out.println("offset: " + offset);

			gisRecords.gisRecordsUpdate(offset);

			//System.out.println("offset: " + offset);
			
			NameIndex names = new NameIndex(GeoFeatures.FEATURE_NAME, GeoFeatures.STATE_ALPHA);
			Point pos = new Point(GeoFeatures.PRIM_LONG_DMS.toSeconds(), GeoFeatures.PRIMARY_LAT_DMS.toSeconds(), (int) offset);
			
			if (pos.inBox(wLong, eLong , sLat , nLat)){
				//table.insertHash(names, (int) offset);
				table.insertHash(names, (int) offset);
				//System.out.println("Number of elements: " + table.getNumElements());
				//System.out.println("Number of probes: " + table.getProbe());
			//System.out.println("Current table size: " + table.getCurrentSize());
				//quadTree.insert(pos);
				
				countIdx++;
			}
			
			dataFile.seek(offset); //Bring the pointer back to beginning of the line after reading from the gisRecordsUpdate
			
			//System.out.println("name: " + gisRecords.name(offset));

			//System.out.println("offset: " + offset);
			//System.out.println(GeoFeatures.COUNTY_NAME);
			offset += dataFile.readLine().length() +1; // Next line
			//NameIndex name = new NameIndex(gisRecords.name(offset), gisRecords.s)
			
		}
		System.out.println("current pointer : " + dataFile.getFilePointer());
		System.out.println("Number of elements: " + table.getNumElements());
		System.out.println("Number of probes: " + table.getProbe());
		System.out.println("Current table size: " + table.getCurrentSize());
		System.out.println("toString:     \n" + table.hashToString());
		stat.write("\n");
		// dataFile.close();
		//dataFile.seek(265);
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