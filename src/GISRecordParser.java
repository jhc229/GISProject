import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.io.RandomAccessFile;

/**
 * Parse the data records with given offsets.
 * 
 * @author Jung Hyun Choi (jhc229)
 * @version 05.23.2016
 */
public class GISRecordParser {
	// ~ Fields
	private RandomAccessFile read = null;
	private long offset = 0;
	private long endOffset = 0;

	private GeoCoordinates geoCoord;
	private DMScoordinates dmsCoord;
	private GeoFeatures geoFeat;

	// ~ Constructor
	/**
	 * Create a new DataBase object.
	 * 
	 * @throws IOException
	 */
	public GISRecordParser(RandomAccessFile dataFile, long endoffset)
			throws IOException {
		read = dataFile;
		// stat = result;
		read.seek(0); // initialize the pointer in the record to the beginning.
		this.endOffset = endoffset;
		offset = read.readLine().length() + 1; // Start at the second line.

	}

	public void gisRecordsUpdate(long parserOffset) throws IOException, GISRecordException {
		if ((parserOffset >= 265) && (parserOffset <= endOffset)){ //&& ((int)read.readByte() == 10)
			read.seek(parserOffset);
			String line = read.readLine();
			String[] items = line.split("\\|");
			
			
			GeoFeatures.FEATURE_ID = Integer.parseInt(items[0]);
			GeoFeatures.FEATURE_NAME = items[1];
			GeoFeatures.FEATURE_CLASS = items[2];
			GeoFeatures.STATE_ALPHA= items[3];
			GeoFeatures.STATE_NUMERIC = Double.parseDouble(items[4]);
			GeoFeatures.COUNTY_NAME = items[5];
			GeoFeatures.COUNTY_NUMERIC = Double.parseDouble(items[6]);
			
			GeoFeatures.PRIMARY_LAT_DMS = new DMScoordinates(Integer.parseInt(items[7].substring(0, 2)), Integer.parseInt(items[7].substring(2, 4)), Integer.parseInt(items[7].substring(4, 6)), items[7].substring(6));
			GeoFeatures.PRIM_LONG_DMS = new DMScoordinates(Integer.parseInt(items[8].substring(0, 2)), Integer.parseInt(items[8].substring(2, 4)), Integer.parseInt(items[8].substring(4, 6)), items[8].substring(6));
			
			GeoFeatures.PRIM_LAT_DEC = Double.parseDouble(items[9]);
			GeoFeatures.PRIM_LONG_DEC =Double.parseDouble(items[10]);
			
			GeoFeatures.SOURCE_LAT_DMS = new DMScoordinates(Integer.parseInt(items[11].substring(0, 3)), Integer.parseInt(items[11].substring(3, 5)), Integer.parseInt(items[11].substring(5, 7)), items[11].substring(7));
			GeoFeatures.SOURCE_LONG_DMS = new DMScoordinates(Integer.parseInt(items[12].substring(0, 2)), Integer.parseInt(items[12].substring(2, 4)), Integer.parseInt(items[12].substring(4, 6)), items[12].substring(6));
			
			GeoFeatures.SOURCE_LAT_DEC =Double.parseDouble(items[13]);
			GeoFeatures.SOURCE_LONG_DEC =Double.parseDouble(items[14]);
			GeoFeatures.ELEV_IN_M = Integer.parseInt(items[15]);
			GeoFeatures.ELEV_IN_FT = Integer.parseInt(items[16]);
			GeoFeatures.MAP_NAME = items[17];
			GeoFeatures.DATE_CREATED = items[18];
			
			if (items.length == 20) GeoFeatures.DATE_EDITED = items[19];
			
			
		}
		else {
			if (((parserOffset >= 0) && (parserOffset < 265) ) || ((int)read.readByte() != 10)){
				
				throw new GISRecordException(" Unaligned offset");
			}
			else if (parserOffset > offset){
				throw new GISRecordException("Offset too large");
			}
			throw new GISRecordException("Offset is not positive");
		}

	//	int result = Integer.parseInt(items[0]);
	}

	/*
	 * Feature ID (FID) non-negative integer unique identifier for this
	 * geographic feature
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
	 * Feature name string standard name of feature
	 * 
	 * @param parserOffset
	 * @throws IOException
	 */
	public String name(long parserOffset) throws IOException {
		if ((parserOffset >= 265) && (parserOffset <= endOffset)) {
			read.seek(parserOffset);
			// String line = read.readLine();

			String[] items = read.readLine().split("\\|");
			// int result = Integer.parseInt(items[0]);
			// stat.append("\n " + items[1] + " items 7 and 8 " +
			// items[7].charAt(items[7].length() - 1) +" " + items[9]);
			return items[1];
		} else {
			if ((parserOffset >= 0) && (parserOffset < 265)) {
				// stat.append("\n Unaligned offset");
				return null;
			} else if (parserOffset > offset) {
				// stat.append("\n Offset too large");
				return null;
			}
			// stat.append("\n Offset is not positive");
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
		// read.seek(parserOffset);
		// String[] items = read.readLine().split("\\|");
		// if (decLatitude(items[9]) == 0) {
		// stat.append("\n Coordinate not given");
		// }
		if ((parserOffset >= 265) && (parserOffset <= endOffset)) {
			read.seek(parserOffset);
			String[] items = read.readLine().split("\\|");
			if (decLatitude(items[9]) == 0) {
				// stat.append("\n Coordinate not given");
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
			// stat.append("\n " + items[7].substring(0, 2) + "d " +
			// Integer.parseInt(items[7].substring(2, 4)) + "m " +
			// Integer.parseInt(items[7].substring(4, 6)) + "s " + direction +
			// "items[15] and items[16]@" + items[15]+ "@" + items[16] + "@");
			return "";
		} else {
			if ((parserOffset >= 0) && (parserOffset < 265)) {
				// stat.append("\n Unaligned offset");
				return null;
			} else if (parserOffset > offset) {
				// stat.append("\n Offset too large");
				return null;
			}
			// stat.append("\n Offset is not positive");
			return null;
		}

	}

	public String longtitude(long parserOffset) throws IOException {
		// read.seek(parserOffset);
		// String line = read.readLine();
		// String[] items = read.readLine().split("\\|");
		// if (decLatitude(items[10]) == 0) {
		// stat.append("\n Coordinate not given");
		// }
		if ((parserOffset >= 265) && (parserOffset <= endOffset)) {
			read.seek(parserOffset);
			// String line = read.readLine();
			String[] items = read.readLine().split("\\|");
			if (decLatitude(items[10]) == 0) {
				// stat.append("\n Coordinate not given");
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
			// stat.append("\n " + items[8].substring(0, 3) + "d " +
			// Integer.parseInt(items[8].substring(3, 5)) + "m " +
			// Integer.parseInt(items[8].substring(5, 7)) + "s " + direction);
			return "";
		} else {
			if ((parserOffset >= 0) && (parserOffset < 265)) {
				// stat.append("\n Unaligned offset");
				return null;
			} else if (parserOffset > offset) {
				// stat.append("\n Offset too large");
				return null;
			}
			// stat.append("\n Offset is not positive");
			return null;
		}

	}

	/**
	 * Feature elevation in feet integer altitude above/below sea level,
	 * optional
	 * 
	 * @param parserOffset
	 * @return
	 * @throws IOException
	 */
	public int elevation(long parserOffset) throws IOException {
		// stat.append("\n " + parserOffset);
		if ((parserOffset >= 265) && (parserOffset <= endOffset)) {
			read.seek(parserOffset);
			// String line = read.readLine();
			String[] items = read.readLine().split("\\|");
			if (items[16] == null) {
				// stat.append("\n Elevation not given");
				return -1;
			}
			// stat.append("\n " + items[16]);
			return Integer.parseInt(items[16]);

		}

		else {
			if ((parserOffset >= 0) && (parserOffset < 265)) {
				// stat.append("\n Unaligned offset");
				return -1;
			} else if (parserOffset > offset) {
				// stat.append("\n Offset too large");
				return -1;
			}
			// stat.append("\n Offset is not positive");
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
	 * Feature class string descriptive classification of feature
	 * 
	 * @param parserOffset
	 * @return
	 */
	public String clsses(long parserOffset) {
		return null;
		// list = new SkipList<String, Rectangle>();
	}

	/**
	 * State alphabetic code two-characters US postal code abbreviation
	 * 
	 * @param parserOffset
	 * @return
	 */
	public String postalCode(long parserOffset) {
		return null;
	}

	/**
	 * State numeric code non-negative integer numeric code for state
	 * 
	 * @param parserOffset
	 * @return
	 */
	public int stateCode(long parserOffset) {
		return 0;
	}

	/**
	 * County name string county in which feature occurs
	 * 
	 * @param parserOffset
	 * @return
	 */
	public String county(long parserOffset) {
		return null;
	}

	/**
	 * County numeric code non-negative integer numeric code for county
	 * 
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
		// stat.append("\n Exiting");
		read.close();
		// stat.close();

	}

}