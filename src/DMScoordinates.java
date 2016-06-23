/**
 * DMS coordinates
 * @author sean
 *
 */
public class DMScoordinates {
	
	private int degree;
	private int minute;
	private int second;
	private String direction;
	
	public DMScoordinates(){
		degree =0;
		minute=0;
		second =0;
		direction = "";
	}
	
	public DMScoordinates(int degree, int minute, int second, String direction){
		
		this.degree =  degree;
		this.minute = minute;
		this.second = second;
		this.direction =direction;
		
	}

	
	public int getDegree(){
		return degree;
		
	}
	
	public int getMinute(){
		return minute;
	}
	
	public int second(){
		return second;
	}
	
	public String direction(){
		return direction;
	}
	/**
	 * Finally, do not interpret longitude/latitude values sloppily. In particular, do not represent a value given in DMS format, like
	 * 1214322W, as the integer -1214322. (The negative sign comes from the fact that it's west longitude, and that's fine.) Some
	 * students have encountered problems in the past when using that approach. Instead, convert the given value to total seconds;
	 * in this case that would be -438202 seconds.

	 * @return seconds;
	 */
	//One degree is equal to 60 minutes and equal to 3600 seconds:
	public int toSeconds(){
		switch(direction){
		case "N" || "E":
			return degree*3600 + minute * 60 + second;
		
		}
		
		
		
	}
		
		
}
