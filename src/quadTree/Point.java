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

	private int xcoord;
	private int ycoord;
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

	public int getX() {
		return xcoord;
	}

	public int getY() {
		return ycoord;
	}

	public void addOffset(Vector<Integer> offset) {
		this.offsets.addAll(offset);
	}

	public Vector<Integer> getOffset() {
		return offsets;
	}

	public Direction directionFrom(int X, int Y) {
		if (xcoord <= X && ycoord > Y) return Direction.NW;

		else if (xcoord > X && ycoord >= Y) return Direction.NE;

		else if (xcoord < X && ycoord <= Y) return Direction.SW;
		
		else if (xcoord >= X && ycoord < Y ) return Direction.SE;


		return Direction.NOQUADRANT;
	}

	public Direction inQuadrant(int xLo, int xHi, int yLo, int yHi) {
		if (!inBox(xLo, xHi, yLo, yHi)) return Direction.NOQUADRANT;
		else {
			Direction dir = directionFrom((xLo + xHi) / 2,  (yLo + yHi) / 2);
			if (dir == Direction.NOQUADRANT) return Direction.NE;
			return dir;
		}
	}

	public boolean inBox(int xLo, int xHi, int yLo, int yHi) {
		/*
		 if ( xcoord < xLo || xcoord > xHi || ycoord < yLo || ycoord > yHi){
		  		return false; 
		  } 
		  	return true;*/
		 
		return xcoord >= xLo && xcoord <= xHi && ycoord >= yLo && ycoord <= yHi;
	}

	public boolean equals(Object o) {
		
		if (!o.getClass().equals(this.getClass()))
			return false;
		if (this.getClass().cast(o).directionFrom(xcoord,
				ycoord) == Direction.NOQUADRANT) {
			return true;
		}
		return false;

	}

	public String toString() {
		return "[( " + xcoord + ", " + ycoord + ")," + offsets.toString() + "]";
	}

}
