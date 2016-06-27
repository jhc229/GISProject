import java.util.ArrayList;

/**
 * Buffer Pool
 * 
 * @author sean
 *
 */
public class BufferPool {

	private ArrayList<GeoFeatures> list; // records within the pool are kept in a
	// arraylist

	/**
	 * construct a new BufferPool of size 20
	 * @return 
	 */
	public  BufferPool() {
		list = new ArrayList<GeoFeatures>(10);
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
		list.add(record);
	}

	/**
	 * print the pool in a nice looking format
	 */
	public String toString() {
		String out = "MRU\n";
		for (int i = list.size() - 1; i >= 0; i--) {
			GeoFeatures record = list.get(i);
			out += record.OFFSET + ":  " + record.MAP_NAME + "\n";
		}
		out += "LRU \n";
		return out;
	}
}
