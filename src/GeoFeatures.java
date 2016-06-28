/**
 * Contain all geographic features at a given location.
 * FEATURE_ID|FEATURE_NAME|FEATURE_CLASS|STATE_ALPHA|STATE_NUMERIC|COUNTY_NAME|COUNTY_NUMERIC|PRIMARY_LAT_DMS|PRIM_LONG_DMS|PRIM_LAT_DEC|PRIM_LONG_DEC|SOURCE_LAT_DMS|SOURCE_LONG_DMS|SOURCE_LAT_DEC|SOURCE_LONG_DEC|ELEV_IN_M|ELEV_IN_FT|MAP_NAME|DATE_CREATED|DATE_EDITED
 * 1481469|Barney Run|Stream|VA|51|Bath|017|380145N|0794500W|38.0292932|-79.7500496|380146N|0794621W|38.0294444|-79.7725|568|1863|Warm Springs|09/28/1979|

 * @author sean
 *
 */
public class GeoFeatures {
	public static int 						FEATURE_ID = 0;
	public static String					FEATURE_NAME;
	public static String 					FEATURE_CLASS;
	public static String 					STATE_ALPHA;
	public static double  				STATE_NUMERIC = 0.0;
	public static String 					COUNTY_NAME;
	public static double  				COUNTY_NUMERIC = 0.0;
	public static DMScoordinates PRIMARY_LAT_DMS;
	public static DMScoordinates PRIM_LONG_DMS;
	public static double 				PRIM_LAT_DEC = 0.0;
	public static double 				PRIM_LONG_DEC = 0.0;
	public static DMScoordinates SOURCE_LAT_DMS;
	public static DMScoordinates SOURCE_LONG_DMS;
	public static double  				SOURCE_LAT_DEC = 0.0;
	public static double 				SOURCE_LONG_DEC = 0.0;
	public static int 						ELEV_IN_M = 0;
	public static int 						ELEV_IN_FT = 0;
	public static String  				MAP_NAME;
	public static String  				DATE_CREATED;
	public static String  				DATE_EDITED;
	public static int 						OFFSET = 0;
	public static Sring					line = "";



}
