/**
 *  Feature Name and stat
 * @author sean
 *
 */
public class NameIndex {

	private String featureName;
	private String state; 
	private String str;


	/*
	 * 
	 */
	public NameIndex(String featureName, String state) {
		this.featureName = featureName;
		this.state = state;
		str =featureName + ":" + state;
	}

	/*
	 * 
	 */
	public String getName() {
		return fName;
	}

	/*
	 * 
	 */
	public String getState() {
		return state;
	}


	/*
	 * Source code from course notes.
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		
		long hashValue = 0;
		for (int Pos = 0; Pos < str.length(); Pos++) { // use all elements

			hashValue = (hashValue << 4) + str.charAt(Pos); // shift/mix

			long hiBits = hashValue &  0xF000000000000000L; // get high nybble

			if (hiBits != 0)
				hashValue ^= hiBits >> 56; // xor high nybble with second nybble

			hashValue &= ~hiBits; // clear high nybble
		}
		return (int) hashValue;
	}

}
