import java.util.ArrayList;

import javax.swing.text.StyledEditorKit.ForegroundAction;

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


	public  BufferPool() {
		list = new ArrayList<GeoFeatures>(10);
		size = 0;
	}


	/*
	 * Checks inside the pool
	 * If true remove the element and place it in to the MRU slot.
	 * 
	 */
	public GeoFeatures checkPool(int offset) {
		for (int i = 0; i < list.size(); i++) {
		GeoFeatures foundRecord = list.get(i);
			if (foundRecord.OFFSET == offset) {
				list.add(list.remove(i));
				return foundRecord;
			}
		}
		return null;
	}

	/*
	 * Add method
	 */
	public void add(GeoFeatures record) {
		if (list.size() >= 10) list.remove(0);
		//GeoFeatures record1 = new GeoFeatures();
		// Buffer(offset, featureName, countyName, stateName);
		list.add(record);
		size++;
	}

	/*
	 * Returns pool current size.
	 */
	public int size() {
		return size;
	}

	/*
	 * Buffer lists to string in MRU to LRU
	 */
	public String toString() {
		String out = "MRU   \n";
		for (int i = list.size() - 1; i >= 0; i--) {
			GeoFeatures record = list.get(i);
			out += record.OFFSET + ":  " + record.LINE +"\n";
		}
		out += "LRU \n";
		return out;
	}
}
