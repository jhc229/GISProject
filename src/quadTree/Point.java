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

	public Point() {
		xcoord = 0;
		ycoord = 0;
	}
	
	public Point(int x, int y){
		xcoord = x;
		ycoord = y;
	}

	public Point(int x, int y, long z) {
		xcoord = x;
		ycoord = y;
		offsets = new Vector<Integer>();
		offsets.add((int) z);
	}

	public long getX() {
		return xcoord;
	}

	public long getY() {
		return ycoord;
	}

	public void addOffset(Vector<Integer> offset) {
		this.offsets.addAll(offset);
	}

	public Vector<Integer> getOffset() {
		return offsets;
	}

	public Direction directionFrom(long X, long Y) {
		if (xcoord <= X && ycoord > Y) return Direction.NW;

		else if (xcoord > X && ycoord >= Y) return Direction.NE;

		else if (xcoord < X && ycoord <= Y) return Direction.SW;
		
		else if (xcoord >= X && ycoord < Y ) return Direction.SE;


		return Direction.NOQUADRANT;
	}

	public Direction inQuadrant(double xLo, double xHi, double yLo, double 

yHi) {
		if (!inBox(xLo, xHi, yLo, yHi)) return Direction.NOQUADRANT;
		else {
			Direction dir = directionFrom((long) (xLo + xHi) / 2, 

(long) (yLo + yHi) / 2);
			if (dir == Direction.NOQUADRANT) return Direction.NE;
			return dir;
		}
	}

	public boolean inBox(double xLo, double xHi, double yLo, double yHi) {
		/*
		 * if ( xcoord < xLo || xcoord > xHi || ycoord < yLo || ycoord 

> yHi){
		 * return false; } return true;
		 */
		return xcoord >= xLo && xcoord <= xHi && ycoord >= yLo && 

ycoord <= yHi;
	}

	public boolean equals(Object o) {
		if (o.getClass().equals(this.getClass())) {
			return this.getClass().cast(o).directionFrom(xcoord,
					ycoord) == Direction.NOQUADRANT;
		}
		return false;
		/*
		 * if ( !o.getClass().equals(this.getClass())) return false; if
		 * (this.getClass().cast(o).directionFrom(xcoord, ycoord) ==
		 * Direction.NOQUADRANT){ return true; } return false;
		 */

	}

	public String toString() {
		return "[( " + xcoord + ", " + ycoord + ")," + 

offsets.toString() + "]";
	}

}
