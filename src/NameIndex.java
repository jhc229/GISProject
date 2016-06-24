
public class NameIndex {

	
	private final String fName; // the name
	private final String state; //the state

	/**
	 * Constructor
	 * @param fName the feature name
	 * @param state the state
	 */
	public NameIndex(String fName, String state) {
		this.fName = fName;
		this.state = state;
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
		return HashTable.elfHash(fName + ":" + state);
	}

}
