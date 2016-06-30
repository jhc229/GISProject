import java.util.ArrayList;

import javax.swing.text.StyledEditorKit.ForegroundAction;

/**
 * Buffer Pool
 * 
 * @author sean
 *
 */
public class BufferPool {

	
	private ArrayList<GeoFeatures> bufferList; 
	private int size;


	public  BufferPool() {
		bufferList = new ArrayList<GeoFeatures>(10);
		size = 0;
	}


	/*
	 * Checks inside the pool
	 * If true remove the element and place it in to the MRU slot.
	 * 
	 */
	public GeoFeatures checkPool(int offset) {
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
		String out = "MRU   \n";
		int indexSize = bufferList.size() - 1;
		for (int i =indexSize; i >= 0; i--) {
			GeoFeatures record = bufferList.get(i);
			out += " " + record.OFFSET + ":  " + record.LINE +"\n";
		}
		out += "LRU \n";
		return out;
	}
}
