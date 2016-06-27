/**
 *	Geographic coordinates latitude and longitude, which
 * will allow us to deal with geographic features at any location on earth. 
 * FEATURE_ID|FEATURE_NAME|FEATURE_CLASS|STATE_ALPHA|STATE_NUMERIC|COUNTY_NAME|COUNTY_NUMERIC|PRIMARY_LAT_DMS|PRIM_LONG_DMS|PRIM_LAT_DEC|PRIM_LONG_DEC|SOURCE_LAT_DMS|SOURCE_LONG_DMS|SOURCE_LAT_DEC|SOURCE_LONG_DEC|ELEV_IN_M|ELEV_IN_FT|MAP_NAME|DATE_CREATED|DATE_EDITED
 * 1481469|Barney Run|Stream|VA|51|Bath|017|380145N|0794500W|38.0292932|-79.7500496|380146N|0794621W|38.0294444|-79.7725|568|1863|Warm Springs|09/28/1979|

 * @author sean
 *
 */
public class GeoCoordinates implements Point{
	
	
	private DMScoordinates latitude; // latitude
	private DMScoordinates longitude;// longitude

	
	public GeoCoordinates(DMScoordinates dmsLatitude, DMScoordinates dmsLongitude) {
		latitude = dmsLatitude;
		longitude = dmsLongitude;
	}

	public DMScoordinates getlatitude(){
		return latitude;
	}
	
	public DMScoordinates getlongitude(){
		return longitude;
	}
	
	public String toString(){
		return "(" + latitude + ", " + longitude + ")";
		
		
	}

	public boolean equals(Object o) {
		if (o == null) return false;
		if (this.getClass().equals(o)) {
			GeoCoordinates temp = (GeoCoordinates) o;
			return (temp.getlatitude().equals( latitude) && temp.getlongitude().equals( longitude));
		}
		return false;
	}
	



}
