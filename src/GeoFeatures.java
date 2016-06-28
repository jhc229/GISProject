/**
 * Contain all geographic features at a given location.
 * FEATURE_ID|FEATURE_NAME|FEATURE_CLASS|STATE_ALPHA|STATE_NUMERIC|COUNTY_NAME|COUNTY_NUMERIC|PRIMARY_LAT_DMS|PRIM_LONG_DMS|PRIM_LAT_DEC|PRIM_LONG_DEC|SOURCE_LAT_DMS|SOURCE_LONG_DMS|SOURCE_LAT_DEC|SOURCE_LONG_DEC|ELEV_IN_M|ELEV_IN_FT|MAP_NAME|DATE_CREATED|DATE_EDITED
 * 1481469|Barney Run|Stream|VA|51|Bath|017|380145N|0794500W|38.0292932|-79.7500496|380146N|0794621W|38.0294444|-79.7725|568|1863|Warm Springs|09/28/1979|

 * @author sean
 *
 */
public class GeoFeatures {
	public final  int 						FEATURE_ID = 0;
	public  String					FEATURE_NAME;
	public  String 					FEATURE_CLASS;
	public  String 					STATE_ALPHA;
	public  double  				STATE_NUMERIC = 0.0;
	public  String 					COUNTY_NAME;
	public  double  				COUNTY_NUMERIC = 0.0;
	public  DMScoordinates PRIMARY_LAT_DMS;
	public  DMScoordinates PRIM_LONG_DMS;
	public  double 				PRIM_LAT_DEC = 0.0;
	public  double 				PRIM_LONG_DEC = 0.0;
	public  DMScoordinates SOURCE_LAT_DMS;
	public  DMScoordinates SOURCE_LONG_DMS;
	public  double  				SOURCE_LAT_DEC = 0.0;
	public  double 				SOURCE_LONG_DEC = 0.0;
	public static  int 						ELEV_IN_M = 0;
	public static  int 						ELEV_IN_FT = 0;
	public  String  				MAP_NAME;
	public  String  				DATE_CREATED;
	public static  String  				DATE_EDITED;
	public  int 						OFFSET = 0;
	public  String					LINE = "";

	

}
