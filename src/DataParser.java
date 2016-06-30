import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.Buffer;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import quadTree.Point;
import quadTree.prQuadTree;


/**
 *
 * Parse the and process the records by the given instructions.
 * @author Jung Hyun Choi (jhc229)
 * @version 05.23.2016
 */
public class DataParser {
	// ~ Fields
	private File data = null;
	
	private long offset;
	private long endOffset = 0;
	private BufferedWriter stat = null;
	private GISRecordParser gisRecords =null; // GIS  record parts
	
	private int wLong, eLong , sLat , nLat; //boundary
	
	private GeoCoordinates geoCoord;
	private DMScoordinates coord;

	public prQuadTree<Point> quadTree;
	public BufferPool pool;
	public HashTable<NameIndex, Integer> table;
	
	// ~ Constructor
	/**
	 * Create a new dataParser object.
	 * 
	 * @throws IOException
	 */
	public DataParser(File dataFile, BufferedWriter result) throws FileNotFoundException  {
		
		offset = 265;
		data = dataFile;
		//this.dataFile = new RandomAccessFile(dataFile, "rw");
		stat = result;
		table = new HashTable<NameIndex, Integer>(1024);
		pool = new BufferPool();
		
	}

	/**
	 *  This method  will appened a data file.
	 * @param gisRecordFile
	 * @param count
	 * @throws IOException
	 */
	@SuppressWarnings("resource")
	public void appendFile(String gisRecordFile, int count) throws IOException {
		
		BufferedReader gisRecord = new BufferedReader(new FileReader(gisRecordFile)); 
		RandomAccessFile dataFile = new RandomAccessFile(data, "rw");
		
		String str = "";
		if (count == 0){
			dataFile.seek(0);
			dataFile.setLength(0);
		}
		if (count > 0){
			System.out.println("count???: " + count);

			gisRecord.readLine();
			dataFile.seek(endOffset);
			offset = endOffset;
		}

		while ((str =gisRecord.readLine()) != null) { 
			dataFile.write((str + "\n").getBytes());

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
		System.out.println("\t\t\t" +  sLat + "\n\t" + wLong + "\t\t\t" + eLong + "\n\t\t\t"+ nLat);
		System.out.println("--------------------------------------------------------------------------------\n");
		stat.write("\t\t\t" +  sLat + "\n\t" + wLong + "\t\t\t" + eLong + "\n\t\t\t"+ nLat + "\n");
		stat.write("--------------------------------------------------------------------------------\n\n");
		
	}
	
	/*
	 *  Returns coordinate.
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
	@SuppressWarnings("resource")
	public void importFile(String filePath) throws IOException, GISRecordException {
		
		int countIdx = 0;
		
		BufferedReader br = new BufferedReader(new FileReader(filePath));
		gisRecords = new GISRecordParser(data, endOffset);

		br.readLine();
		String line = "";
		try {
			while ((line = br.readLine()) != null) {
				GeoFeatures newRec = gisRecords.gisUpdate(line , (int) offset);
				if (newRec !=null){
					NameIndex names = new NameIndex(newRec.FEATURE_NAME, newRec.STATE_ALPHA);
					Point dmsPoints = new Point(newRec.PRIM_LONG_DMS.toSeconds(), newRec.PRIMARY_LAT_DMS.toSeconds(), offset);
				
						if (dmsPoints.inBox(wLong, eLong , sLat , nLat)){
							
							table.insertHash(names, (int) offset);
							quadTree.insert(dmsPoints);
							countIdx++;
						}	
		        }
				
				offset += line.length() +1; // Next line
			} 
		}catch (Exception e) {
		    e.printStackTrace();
		}
		//dataFile.seek(265);
		System.out.println("");
		System.out.println("Imported Features by name: " + countIdx);
		System.out.println("Longest probe sequence:     " + table.getProbe());
		System.out.println("Imported Locations:         " + countIdx);
		System.out.println("--------------------------------------------------------------------------------");
		stat.write("\nImported Features by name: " + countIdx + "\n");
		stat.write("Longest probe sequence:     " + table.getProbe() + "\n");
		stat.write("Imported Locations:         " + countIdx + "\n");
		stat.write("--------------------------------------------------------------------------------");

	}

	/**
	 * Find record using the coordinates.
	 * For every GIS record in the database file that matches the given <geographic coordinate>, log the offset at
		which the record was found, and the feature name, county name, and state abbreviation. Do not log any other data
		from the matching records.

	 * @param x is lat
	 * @param y is long
	 * @throws Exception 
	 */
	public void whatIsAt(String x, String y) throws Exception {
		// geographic coordinate "382812N	0793156W "
		// create new Coords class for latitude and longitude
		DMScoordinates latitude=  toCoord(Integer.parseInt(x.substring(0, 2)), Integer.parseInt(x.substring(2, 4)), Integer.parseInt(x.substring(4, 6)), x.substring(6));
		DMScoordinates longitude = toCoord(Integer.parseInt(y.substring(0, 3)), Integer.parseInt(y.substring(3, 5)), Integer.parseInt(y.substring(5, 7)), y.substring(7));
		
		GeoCoordinates geo = new GeoCoordinates(latitude, longitude);
		
		Point p = quadTree.find(new Point(geo.getlongitude().toSeconds(),  geo.getlatitude().toSeconds()));

		// Vector<Records> records = new Vector<Records>(0);
		 if (p != null){
			 Vector<Integer> offset = p.getOffset();
			System.out.println("   The following features were found at	" + y +" " + x);
			stat.write("   The following features were found at	" + y +" " + x + "\n");
			 for(GeoFeatures a : poolOffset(offset)){
				 System.out.println(a.OFFSET + ":  " + a.FEATURE_NAME + " " + a.COUNTY_NAME + " "+a.STATE_ALPHA);
				 stat.write(a.OFFSET + ":  " + a.FEATURE_NAME + " " + a.COUNTY_NAME + " "+a.STATE_ALPHA + "\n");
			 }

		 }
		 else{
				System.out.println("     Nothing was found at " + y + " "  + x );
				stat.write("     Nothing was found at " + y + " "  + x + "\n");
		 }
		System.out.println("--------------------------------------------------------------------------------");
		stat.write("--------------------------------------------------------------------------------\n");

	}
	

	/*
	 * what_is<tab><feature name><tab><state abbreviation> For every GIS record
	 * in the database file that matches the given <feature name> and <state
	 * abbreviation>, log the offset at which the record was found, and the
	 * county name, the primary latitude, and the primary longitude. Do not log
	 * any other data from the matching records.
	 */
	public void whatIs(String fName, String sState) throws IOException {
	//	Vector<GeoFeatures> records = new Vector<GeoFeatures>(0);
		NameIndex names = new NameIndex(fName, sState);
		Vector<Integer> off = (Vector<Integer>) table.getEntries(names);
		//Vector<Integer> off = table.getEntries(names);
		
		if (off != null){
			try {
				 for(GeoFeatures a : poolOffset(off)){
						 System.out.println(a.OFFSET + ":  " + a.COUNTY_NAME + " " + a.PRIM_LONG_DMS.toString() + " "+a.PRIMARY_LAT_DMS.toString());
						 stat.write(a.OFFSET + ":  " + a.COUNTY_NAME + " " + a.PRIM_LONG_DMS.toString() + " "+a.PRIMARY_LAT_DMS.toString() + "\n");
				 }
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		 else{
				System.out.println("No records match  " + fName + " and "  + sState );
				stat.write("No records match  " + fName + " and "  + sState + "\n");
		 }
		System.out.println("--------------------------------------------------------------------------------");
		stat.write("--------------------------------------------------------------------------------\n");
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
	public void whatIsIn(String x, String y, String halfHeight, String halfWidth) throws IOException {
		
		Vector<Point> pts = whatIsInHelper(x, y, Integer.parseInt(halfHeight), Integer.parseInt(halfWidth));

		if (pts.size() > 0) {
			try {
				Vector<Integer> newSets = new Vector<Integer>(0);
				//System.out.println("	The following " + pts.size() + " features were found in (" + y + " +/-" + halfHeight+", " + x + " +/-" + halfWidth + ")");
				//System.out.println("tree coordinates:   " + pts);
					for (int i = 0; i < pts.size(); ++i) {
						newSets.addAll(pts.get(i).getOffset());
					}
					System.out.println("	The following " + newSets.size()+ " features were found in (" + y + " +/-" + halfWidth + ", " + x + " +/-" + halfHeight + ")");
					stat.write("	The following " + newSets.size()+ " features were found in (" + y + " +/-" + halfWidth + ", " + x + " +/-" + halfHeight + ")+\n");
					for (GeoFeatures a : poolOffset(newSets)) {
	
						System.out.println(a.OFFSET + ":  " + a.FEATURE_NAME + " " + a.STATE_ALPHA + " " + a.PRIMARY_LAT_DMS.toString() + " " + a.PRIM_LONG_DMS.toString());
						stat.write(a.OFFSET + ":  " + a.FEATURE_NAME + " " + a.STATE_ALPHA + " " + a.PRIMARY_LAT_DMS.toString() + " " + a.PRIM_LONG_DMS.toString() + "\n");
					}
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		else{
			System.out.println("     Nothing was found in (" + y + " +/-" + halfWidth + ", " + x + " +/-" + halfHeight + ")");
			stat.write("     Nothing was found in (" + y + " +/-" + halfWidth + ", " + x + " +/-" + halfHeight + ")\n");
		}
		System.out.println("--------------------------------------------------------------------------------");
		stat.write("--------------------------------------------------------------------------------\n");
	}

	public void whatIsInC(String x, String y, String halfHeight, String halfWidth) throws IOException {

		Vector<Point> pts = whatIsInHelper(x, y, Integer.parseInt(halfHeight), Integer.parseInt(halfWidth));
		
		if (pts.size() > 0) {
			try {
				Vector<Integer> newSets = new Vector<Integer>(0);
				for (int i = 0; i < pts.size(); ++i) {
					newSets.addAll(pts.get(i).getOffset());
				}
				System.out.println( newSets.size() + " features were found in (" + y + " +/-" + halfWidth + ", " + x + " +/-" + halfHeight + ")");
				stat.write( newSets.size() + " features were found in (" + y + " +/-" + halfWidth + ", " + x + " +/-" + halfHeight + ")\n");
				poolOffset(newSets);

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		else{
			System.out.println("     Nothing was found in (" + y + " +/-" + halfWidth + ", " + x + " +/-" + halfHeight + ")");
			stat.write("     Nothing was found in (" + y + " +/-" + halfWidth + ", " + x + " +/-" + halfHeight + ")\n");
		}
		System.out.println("--------------------------------------------------------------------------------");
		stat.write("--------------------------------------------------------------------------------\n");

	}
	
	public void whatIsInL(String x, String y, String halfHeight, String halfWidth) throws IOException {
		Vector<Point> pts = whatIsInHelper(x, y, Integer.parseInt(halfHeight), Integer.parseInt(halfWidth));
		
		if (pts.size() > 0) {
			try {
				Vector<Integer> newSets = new Vector<Integer>(0);
					int i;
					for (i = 0; i < pts.size(); ++i) {
						newSets.addAll(pts.get(i).getOffset());
					}
					i++;
					System.out.println("	The following " +  newSets.size()+ " features were found in (" + y + " +/-" + halfWidth + ", " + x + " +/-" + halfHeight + ")");
					stat.write("	The following " +newSets.size()+ " features were found in (" + y + " +/-" + halfWidth + ", " + x + " +/-" + halfHeight + ")\n");
					for (GeoFeatures a : poolOffset(newSets)) {
	
						System.out.println("  Feature ID   : "+ a.FEATURE_ID);
						System.out.println("  Feature Name : "+ a.FEATURE_NAME);
						System.out.println("  Feature Cat  : "+ a.FEATURE_CLASS);
						System.out.println("  State        : "+ a.STATE_ALPHA);
						System.out.println("  County       : "+ a.COUNTY_NAME);
						System.out.println("  Latitude     : "+ a.PRIMARY_LAT_DMS.toString());
						System.out.println("  Longitude    : "+ a.PRIM_LONG_DMS.toString());
						System.out.println("  Elev in ft   : "+ a.ELEV_IN_FT);
						System.out.println("  USGS Quad    : "+ a.MAP_NAME);
						System.out.println("  Date created : "+ a.DATE_CREATED);
						stat.write("  Feature ID   : "+ a.FEATURE_ID + "\n");
						stat.write("  Feature Name : "+ a.FEATURE_NAME+ "\n");
						stat.write("  Feature Cat  : "+ a.FEATURE_CLASS+ "\n");
						stat.write("  State        : "+ a.STATE_ALPHA+ "\n");
						stat.write("  County       : "+ a.COUNTY_NAME+ "\n");
						stat.write("  Latitude     : "+ a.PRIMARY_LAT_DMS.toString()+ "\n");
						stat.write("  Longitude    : "+ a.PRIM_LONG_DMS.toString()+ "\n");
						stat.write("  Elev in ft   : "+ a.ELEV_IN_FT+ "\n");
						stat.write("  USGS Quad    : "+ a.MAP_NAME+ "\n");
						stat.write("  Date created : "+ a.DATE_CREATED+ "\n");

						if (a.DATE_EDITED != null){
							System.out.println("  Date Mod     : "+ a.DATE_EDITED);
							stat.write("  Date Mod     : "+ a.DATE_EDITED+ "\n");
						}
						System.out.println("");

					}
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		else{
			System.out.println("     Nothing was found in (" + y + " +/-" + halfWidth + ", " + x + " +/-" + halfHeight + ")");
			stat.write("     Nothing was found in (" + y + " +/-" + halfWidth + ", " + x + " +/-" + halfHeight + ")");
		}
		System.out.println("--------------------------------------------------------------------------------");
		stat.write("--------------------------------------------------------------------------------\n");

	}
		
		
	/**
	 * 
	 * The following 1 features were found in (803133W +/- 5, 371105N +/- 5)
	 * 41632: Radford AAP Heliport VA 371105N 803133W
	 * 
	 * 
	 * (37115N, 803133W) 
	 * tree coordinates: [[( -289886, 133815),[27941]]] The
	 * 
	 * following 1 features were found in (0803133W +/-5, 371105N +/-5) 
	 * tree coordinates: [[( -289886, 133815),[27941]]] 
	 * 27941: New River Church VA 371015N 803126W
	 * 
	 * @param x
	 * @param y
	 * @param halfHeight
	 * @param halfWidth
	 * @return
	 */
	private Vector<Point> whatIsInHelper(String x, String y, int halfHeight, int halfWidth){
		
		DMScoordinates latitude=  toCoord(Integer.parseInt(x.substring(0, 2)), Integer.parseInt(x.substring(2, 4)), Integer.parseInt(x.substring(4, 6)), x.substring(6));
		DMScoordinates longitude = toCoord(Integer.parseInt(y.substring(0, 3)), Integer.parseInt(y.substring(3, 5)), Integer.parseInt(y.substring(5, 7)), y.substring(7));

		Point p = new Point(longitude.toSeconds(), latitude.toSeconds());
		int minX = p.getX() - halfWidth ;
		int maxX = p.getX() + halfWidth ;
		int minY = p.getY() -halfHeight ;
		int maxY = p.getY() +halfHeight;
		/* System.out.println("" + minX+" " + maxX+" " + minY+" " + maxY);
		//371352N  802513W
		// -289519 -289509 134022 134032
		int y1 = toCoord(37, 05, 21, "N").toSeconds() - halfWidth; //370521N
		int y2 = toCoord(37, 05, 21, "N").toSeconds() + halfWidth; //370521N
		int x1 =  toCoord(80, 30, 20, "W").toSeconds()- halfWidth; //0803020W
		int x2 =  toCoord(80, 30, 20, "W").toSeconds()+ halfWidth; //0803020W*/
		 Vector<Point>pts = quadTree.find(minX, maxX, minY, maxY);
		
		return pts;
		
	}
 
	public void debug(String arg) throws IOException {
		if (arg.matches("pool")){
			System.out.println(pool.toString());
			stat.write("\n" + pool.toString());
		}
		else if(arg.matches("hash")){
			System.out.println("Number of probes: " + table.getProbe());
			System.out.println("Current table size: " + table.getCurrentSize());
			System.out.println("Number of elements: " + table.getNumElements());
			System.out.println("toString:     \n" + table.hashToString());
			
			stat.write("Format of display is: \n");
			stat.write("Slot number: data record \n");
			stat.write("Current table size: " + table.getCurrentSize() + "\n");
			stat.write("Number of elements in table is  :" + table.getNumElements() + "\n");
			stat.write("\n"  + table.hashToString());
		}
		else if(arg.matches("quad")){
			System.out.println(quadTree.treeToString());
			stat.write(quadTree.treeToString());
		}
		stat.write("--------------------------------------------------------------------------------\n");
		System.out.println("--------------------------------------------------------------------------------");

	}

	/*
	 * Buffer helper method to bring data from the pool or add the data into the pool
	 */
	private Vector<GeoFeatures> poolOffset(Vector<Integer> off)
			throws Exception {
		//System.out.println("current pointer" + dataFile.getFilePointer());
		Vector<GeoFeatures> temp = new Vector<GeoFeatures>();
		//gisRecords = new GISRecordParser(dataFile, endOffset);
		GISRecordParser gisRecord = new GISRecordParser(data, endOffset);
		for (int i = 0; i < off.size(); i++) {
		
			int currentOffset = off.get(i);
			
			GeoFeatures newRecord = pool.checkPool(currentOffset); 
			if ( newRecord != null) { // is found
				temp.add(newRecord); 
			} 
			else {
				GeoFeatures dataRec = new GeoFeatures();
				dataRec= gisRecord.gisRecordsUpdate(currentOffset);
				
				temp.add(dataRec); // add to the temp file for return
				pool.add(dataRec);	// add to the pool
				
				//str +=dataRec.OFFSET +  ":	" + dataRec.FEATURE_NAME + " " + dataRec.COUNTY_NAME + " "+dataRec.STATE_ALPHA + "\n";
				//pool.add(dataRec.OFFSET, dataRec.FEATURE_NAME, dataRec.COUNTY_NAME, dataRec.STATE_ALPHA);
			}
		}
		return temp;
	}

	/**
	 * Close all the files
	 * @throws IOException
	 */
	public void quit() throws IOException {
		System.out.println("--------------------------------------------------------------------------------");
		stat.write("\n--------------------------------------------------------------------------------\n");
		stat.close();
	}


}