import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.Vector;
import java.io.RandomAccessFile;
import java.io.Reader;
import quadTree.*;


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

	public prQuadTree<Point> quadTree;
	public BufferPool pool;
	public HashTable<NameIndex, Integer> table;
	
	// ~ Constructor
	/**
	 * Create a new dataParser object.
	 * 
	 * @throws IOException
	 */
	public DataParser(File dataFile, BufferedWriter result)  {
		
		// endOffset = 265;
		// pool = new BufferPool();
		// table = new HashTable<NameIndex, Integer>(1024);
		// offset = 0;
		offset = 265;
		try {
			data = dataFile;
			this.dataFile = new RandomAccessFile(dataFile, "rw");
			stat = result;


			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		table = new HashTable<NameIndex, Integer>(1024);
		pool = new BufferPool();
		

	}


	
	public void appendFile(String gisRecordFile, int count) throws IOException {
		
		BufferedReader gisRecord = new BufferedReader(new FileReader(gisRecordFile)); 
		String str = "";
		if (count == 0){
			dataFile.setLength(0);
		}
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
		System.out.println(dataFile.getFilePointer());
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

		quadTree = new prQuadTree<Point>(wLong, eLong , sLat , nLat);
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

			gisRecords.gisRecordsUpdate(offset);

			NameIndex names = new NameIndex(GeoFeatures.FEATURE_NAME, GeoFeatures.STATE_ALPHA);
			Point dmsPoints = new Point(GeoFeatures.PRIM_LONG_DMS.toSeconds(), GeoFeatures.PRIMARY_LAT_DMS.toSeconds(), offset);
			
			if (dmsPoints.inBox(wLong, eLong , sLat , nLat)){
				table.insertHash(names, (int) offset);

				quadTree.insert(dmsPoints);

				countIdx++;
			}
			dataFile.seek(offset); //Bring the pointer back to beginning of the line after reading from the gisRecordsUpdate
			offset += dataFile.readLine().length() +1; // Next line
		}
		System.out.println("current pointer : " + dataFile.getFilePointer());
		System.out.println("Number of elements: " + table.getNumElements());
		//System.out.println("Number of probes: " + table.getProbe());
		System.out.println("Current table size: " + table.getCurrentSize());
		System.out.println("toString:     \n" + table.hashToString());
		stat.write("\n");
		// dataFile.close();
		//dataFile.seek(265);
	//	System.out.println("Pointer: "+ dataFile.getFilePointer());
	}

	/**
	 * Find record using the coordinates.
	 * For every GIS record in the database file that matches the given <geographic coordinate>, log the offset at
		which the record was found, and the feature name, county name, and state abbreviation. Do not log any other data
		from the matching records.

	 * @param x is lat
	 * @param y is long
	 */
	public void whatIsAt(String x, String y) {
		// geographic coordinate "382812N	0793156W "
		// create new Coords class for latitude and longitude
		DMScoordinates latitude =  toCoord(Integer.parseInt(x.substring(0, 2)), Integer.parseInt(x.substring(2, 4)), Integer.parseInt(x.substring(4, 6)), x.substring(6));
		DMScoordinates longitude = toCoord(Integer.parseInt(y.substring(0, 3)), Integer.parseInt(y.substring(3, 5)), Integer.parseInt(y.substring(5, 7)), y.substring(7));
		//System.out.println("whatisat???????" + longitude.);
		GeoCoordinates geo = new GeoCoordinates(latitude, longitude);
		System.out.println("whatisat???????" + quadTree.find(latitude.toSeconds(), longitude.toSeconds(), -1));
		 Point p = quadTree.find(new Point(geo.getlongitude().toSeconds(),  geo.getlongitude().toSeconds()));
		 System.out.println("whatisat???????" );
		// Vector<Records> records = new Vector<Records>(0);
		 if (p != null){
			 Vector<Long> offset = p.getOffset();

			System.out.println("whatisat???????");
		 }
	}
	

	/*
	 * what_is<tab><feature name><tab><state abbreviation> 
	 * 	For every GIS record in the database file that matches the given <feature name> and <state
	 * abbreviation>, log the offset at which the record was found, and the
	 * county name, the primary latitude, and the primary longitude. Do not log
	 * any other data from the matching records.
	 */
	//public void whatIs(String string, String string2) {
		//Vector<GeoFeatures> records = new Vector<GeoFeatures>(0);

	//	Point p = 
		// find the record within the HashTable
		/*Vector<Integer> offsets = (Vector<Integer>) table.find(nameIn);

		gPar = new GISParser(dataFile);
		// check if the offset exists
		if (offsets != null) {
			records = poolOffset(offsets);
		}
		return records		*?
	}
/*
 * what_is_in<tab>-l<tab><geographic coordinate><tab><half-height><tab><half-width>
 * For every GIS record in the database file whose coordinates fall within the closed rectangle with the specified height
	and width, centered at the <geographic coordinate>,  
	empty 
	log every important non-empty field, nicely formatted
	and labeled. See the posted log files for an example. Do not log any empty fields. The half-height and half-width are
	specified as seconds.
	-l
	 log every important non-empty field, nicely formatted
	and labeled. See the posted log files for an example. Do not log any empty fields. The half-height and half-width are
	specified as seconds.
	-c
	Do not log any data from the records themselves. The half-height and half-width are specified as seconds.

 */
				/*
	public void whatIsIn(String string, String string2, String string3,) {
		
		int lat =  toCoord(Integer.parseInt(x.substring(0, 2)), Integer.parseInt(x.substring(2, 4)), Integer.parseInt(x.substring(4, 6)), x.substring(6)).toSeconds();
		int lon = toCoord(Integer.parseInt(y.substring(0, 3)), Integer.parseInt(y.substring(3, 5)), Integer.parseInt(y.substring(5, 7)), y.substring(7)).toSeconds();
		
		 Point p = quadTree.find(new Point(lat, lon, -1));
		
		Vector<Records> records = new Vector<Records>(0);
		Vector<Integer> offsets = new Vector<Integer>(0);
		// create new holder class for storing
		Point p = new Point(coord.getLong().getTotalSeconds(), coord.getLat()
				.getTotalSeconds(), -1);
		// call the find function from PRQuadTree, finding all records within
		// the boundary
		Vector<Point> points = tree.find(p.getX() - width, p.getX() + width,
				p.getY() - height, p.getY() + height);

		if (points != null) {
			// get all the offset of those records
			for (int i = 0; i < points.size(); i++) {
				offsets.addAll(points.get(i).getOffsets());
			}

			gPar = new GISParser(dataFile);
			records = poolOffset(offsets);
		}
		return records;		
	}

	public void whatIsInC(String string, String string2, String string3) {
		// TODO Auto-generated method stub
		
	}
	
	public void whatIsIn(String string, String string2, String string3) {
		// TODO Auto-generated method stub
		
	}

	public void debug(String string) {
		// TODO Auto-generated method stub
		
	}*/



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