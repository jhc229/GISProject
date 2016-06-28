import java.util.ArrayList;

/**
 * Buffer Pool
 * 
 * @author sean
 *
 */
public class BufferPool {

	private ArrayList<Buffer> list; // records within the pool are kept in a
	private int size;

	public class Buffer{
		private final int offset;
		private final String name;
		private final String cName;
		
		public Buffer(int off, String name, String cName)	{
			offset = off;
			this.name = name;
			this.cName = cName;
			
		}
		
		public int getOff(){
			return offset;
		}
		public String getFeatureName(){
			return name;
			
		}		
		public String getCountyName(){
			return cName;
		}
	}
	/**
	 * construct a new BufferPool of size 20
	 * @return 
	 */
	public  BufferPool() {
		list = new ArrayList<Buffer>(10);
		size = 0;
	}

	/**
	 * check if the pool contain a record with the desired offset
	 * 
	 * @param offset
	 *            the location on database file
	 * @return the record if found, null otherwise
	 */
	public boolean checkRecord(int offset) {
		for (int i = 0; i < list.size(); i++) {
			Buffer founcRecord = list.get(i);
			if (founcRecord.getOff() == offset) {
				list.add(list.remove(i));
				
				return true;
			}
		}
		return false;
	}

	/**
	 * add a record into the pool if it not already in there
	 * 
	 * @param record
	 */
	public void add(String offset, String featureName, String countyName) {
		if (list.size() >= 10) {
			list.remove(0);
		}
		Buffer record = new Buffer(offset, featureName, countyName);
		list.add(record);
		size++;
	}
	public int size(){
		return size;
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
