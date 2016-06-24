
public class NameIndex {

	
	private final String fName; // the name
	private final String state; //the state
	private String str;

	/**
	 * Constructor
	 * @param fName the feature name
	 * @param state the state
	 */
	public NameIndex(String fName, String state) {
		this.fName = fName;
		this.state = state;
		str =fName + ":" + state;
	}

	/**
	 * @return the feature name
	 */
	public String getName() {
		return fName;
	}

	/**
	 * @return the state
	 */
	public String getState() {
		return state;
	}

	/**
	 *@return a hashcode for this specific container
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
		return hashValue;
	}

}
