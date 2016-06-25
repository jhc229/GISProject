package Minor.P3.Client;

import Minor.P3.Client.*;
import Minor.P3.DS.Compare2D;


public class Point implements Compare2D<Point> {

	   private long xcoord;
	   private long ycoord;
	   
	   public Point() {
	      xcoord = 0;
	      ycoord = 0;
	   }

	   public Point(long x, long y) {
	      xcoord = x;
	      ycoord = y;
	   }

	   public long getX() {
	      return xcoord;
	   }

	   public long getY() {
	      return ycoord;
	   }
	   
	   public Direction directionFrom(long X, long Y) { 
	      return Direction.NOQUADRANT;
	   }
	   
	   public Direction inQuadrant(double xLo, double xHi, 
	                               double yLo, double yHi) { 
	      return Direction.NOQUADRANT;
	   }

	   public boolean inBox(double xLo, double xHi, 
	                          double yLo, double yHi) { 
	      return false;
	   }

	   public String toString() {
	      return null;
	   }
	   
	   public boolean equals(Object o) { 
	      return false;
	   }

}
