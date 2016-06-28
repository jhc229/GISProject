package quadTree;

import java.util.Vector;

/**
 * Store a geographic coordinate and a collection of the file offsets of the
 * matching GIS records in the database file.
 * 
 * @author sean
 *
 */
public class Point implements Compare2D<Point> {

	private long xcoord;
	private long ycoord;
	private Vector<Integer> offsets;
	
	/**
	 * empty constructor
	 */
	public Point() {
		xcoord = 0;
		ycoord = 0;
	}

	/**
	 * constructor a new Point class
	 * @param x the longitude
	 * @param y the latitude
	 * @param o the offset
	 */
	public Point(float x, float y, int o) {
		xcoord = (long)x;
		ycoord = (long)y;
		offsets = new Vector<Integer>(0);
		if (o != -1) {
			offsets.add(o);
		}
	}

	/**
	 * @return the longitude coordinate
	 */
	public long getX() {
		return xcoord;
	}

	/**
	 * @return the latitude coordinate
	 */
	public long getY() {
		return ycoord;
	}

	/**
	 * insert a list of offsets into the container
	 */
	public void addOffsets(Vector<Integer> offs) {
		offsets.addAll(offs);
	}

	/**
	 * @return the list of offsets
	 */
	public Vector<Integer> getOffsets() {
		return offsets;
	}
	
	/**
	 * get direction from current position to desired position
	 */
	public Direction directionFrom(long xcoord2, long ycoord2) { 
		if ( xcoord <= xcoord2 && ycoord > ycoord2){
			return Direction.NW;
		}
		else if(xcoord > xcoord2 && ycoord >= ycoord2){
			return Direction.NE;
		}
		else if(xcoord < xcoord2 && ycoord <= ycoord2){
			return Direction.SW;
		}
		else if(xcoord >= xcoord2 && ycoord < ycoord2){
			return Direction.SE;
		}
        return Direction.NOQUADRANT;
   }
	
	/**
	 * determine which quadrant is the current position in
	 */
	public Direction inQuadrant(double  xLo, double xHi, 
			double yLo, double yHi) { 
		//if xcoord or ycoord is outside of the rectangle
		if ( !inBox(xLo, xHi, yLo, yHi)){
			return Direction.NOQUADRANT;
		}
		long xCenter = (long) ((xLo + xHi)/2);
		long yCenter =(long) ((yLo + yHi)/2);
		
		Direction dir = directionFrom(xCenter, yCenter);
		
		if(dir == Direction.NOQUADRANT){
			return Direction.NE;
		}
		return dir;
   }
	/**
	 * @return whether if current position is within a quadrant
	 */
	public boolean inBox(double xLo, double xHi, 
			double yLo, double yHi) { 
		if ( xcoord < xLo || xcoord > xHi || ycoord < yLo || ycoord > yHi){
			return false;
		}
		return true;
   }

	/**
	 * @return a formated string
	 */
	public String toString() {
      return "[(" + xcoord + "," +  ycoord + ")," + offsets.toString() + "] ";
   }
	/**
	 * @return whether is the 2 objects are equal
	 */
	public boolean equals(Object o) {
		// check if the Object o is an Point class
		if ( !o.getClass().equals(this.getClass())){
    		return false;
    	}
		// it is equal if the directionFrom returns an NOQUADRANT
		if (this.getClass().cast(o).directionFrom(xcoord, ycoord) == Direction.NOQUADRANT){
			return true;
		}
		return false;
   }
}