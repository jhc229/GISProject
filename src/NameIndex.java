/**
 *  Feature Name and state
 *  This is the Key for the hash table
 * @author sean
 *
 */
public class NameIndex {

	private final String featureName;
	private final String state; 
	private final String str;

	/*
	 * 
	 */
	public NameIndex(String featureName, String state) {
		this.featureName = featureName;
		this.state = state;
		str =featureName + "	:" + state;
	}

	/*
	 *  return feature name.
	 */
	public String getName() {
		return featureName;
	}

	/*
	 * return state (abbreviation)
	 */
	public String getState() {
		return state;
	}
	
	public String nameIndexToString(){
		return str;
	}



}
