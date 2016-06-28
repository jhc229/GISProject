import java.util.ArrayList;

/**
 * Buffer Pool
 * 
 * @author sean
 *
 */
public class BufferPool {

	
	/*
	public class Buffer {
		private final int offset;
		private final String name;
		private final String cName;
		private final String sName;

		public Buffer(int off, String name, String cName, String sName) {
			offset = off;
			this.name = name;
			this.cName = cName;
			this.sName = sName;

		}

		public int getOff() {
			return offset;
		}

		public String getFeatureName() {
			return name;

		}

		public String getCountyName() {
			return cName;
		}
		
		public String getStateName() {
			return sName;
		}
	}
*/
	private ArrayList<GeoFeatures> list; 
	private int size;

	/**
	 * construct a new BufferPool of size 20
	 * @return 
	 */
	public  BufferPool() {
		list = new ArrayList<GeoFeatures>(10);
		size = 0;
	}

	/**
	 * check if the pool contain a record with the desired offset
	 * 
	 * @param offset
	 *            the location on database file
	 * @return the record if found, null otherwise
	 */
	public GeoFeatures checkRecord(int offset) {
		for (int i = 0; i < list.size(); i++) {
			GeoFeatures founcRecord = list.get(i);
			if (founcRecord.OFFSET == offset) {
				list.add(list.remove(i));
				
				return founcRecord;
			}
		}
		return null;
	}

	/**
	 * add a record into the pool if it not already in there
	 * 
	 * @param record
	 */
	public void add(GeoFeatures record) {
		if (list.size() >= 10) {
			list.remove(0);
		}
		//GeoFeatures record1 = new GeoFeatures();
		// Buffer(offset, featureName, countyName, stateName);
		list.add(record);
		size++;
	}

	public int size() {
		return size;
	}

	/**
	 * print the pool in a nice looking format
	 */
	public String toString() {
		String out = "MRU\n";
		for (int i = list.size() - 1; i >= 0; i--) {
			GeoFeatures record = list.get(i);
			out += record.OFFSET + ":  " + record.LINE +"\n";
		}
		out += "LRU \n";
		return out;
	}
}
