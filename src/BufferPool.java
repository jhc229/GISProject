import java.util.ArrayList;

import javax.swing.text.StyledEditorKit.ForegroundAction;

/**
 * Buffer Pool
 * 
 * @author sean
 *
 */
public class BufferPool {

	// Since list is small, array list would do the best job.
	private ArrayList<GeoFeatures> bufferList; 
	private static final int defaultSize = 10;
	private int size; // current size in the list.


	/*
	 * Constructor 
	 */
	public  BufferPool() {
		bufferList = new ArrayList<GeoFeatures>(defaultSize);
		size = 0;
	}


	/*
	 * Checks inside the pool
	 * If true remove the element and place it in to the MRU slot.
	 * 
	 */
	public GeoFeatures contains(int offset) {
		for (int i = 0; i < bufferList.size(); i++) {
		GeoFeatures foundRecord = bufferList.get(i);
			if (foundRecord.OFFSET == offset) {
				GeoFeatures removedRecord = bufferList.remove(i);
				bufferList.add(removedRecord);
				return foundRecord;
			}
		}
		return null;
	}

	/*
	 * Add method
	 */
	public void add(GeoFeatures record) {
		if (bufferList.size() >= 10) bufferList.remove(0);
		//GeoFeatures record1 = new GeoFeatures();
		// Buffer(offset, featureName, countyName, stateName);
		bufferList.add(record);
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
		String out = "MRU\n";
		int indexSize = bufferList.size() - 1;
		for (int i =indexSize; i >= 0; i--) {
			GeoFeatures record = bufferList.get(i);
			out += "    " + record.OFFSET + ":  " + record.LINE +"\n";
		}
		out += "LRU \n";
		return out;
	}
}
