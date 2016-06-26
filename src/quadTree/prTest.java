package quadTree;


import java.util.Vector;



public class prTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		prQuadTree<Point> tree = new prQuadTree<Point>(-8, 8, -8, 8);
		
		System.out.println("insertion:");
		System.out.println(	tree.insert(new Point(-4,4)) ); //true
		System.out.println(	tree.insert(new Point(-2,4)) ); //true
		System.out.println(	tree.insert(new Point(1,7)) ); //true
		System.out.println(	tree.insert(new Point(2,2)) ); //true
		System.out.println(	tree.insert(new Point(3,7)) ); //true
		System.out.println(	tree.insert(new Point(5,6)) ); //true
		System.out.println(	tree.insert(new Point(-4,-2)) ); //true
		System.out.println(	tree.insert(new Point(2,-4)) ); //true
		System.out.println(	tree.insert(new Point(4,-6)) ); //true
		System.out.println(	tree.insert(new Point(-1,-2)) ); //true
		System.out.println(	tree.insert(new Point(1,7, 12123)) ); //false
		System.out.println("");
		System.out.println("find:");
		System.out.println( tree.find(new Point(-4,4)) ); // null
		System.out.println( tree.find(new Point(-2,4)) ); //X:-2 Y:4
		System.out.println(	tree.find(new Point(1,7)) ); //true
		System.out.println(	tree.find(new Point(2,2)) ); //true
		System.out.println(	tree.find(new Point(3,7)) ); //true
		System.out.println(	tree.find(new Point(5,6)) ); //true
		System.out.println(	tree.find(new Point(-4,-2)) ); //true
		System.out.println(	tree.find(new Point(2,-4)) ); //true
		System.out.println(	tree.find(new Point(4,-6)) ); //true
		System.out.println(	tree.find(new Point(-1,-2)) ); //true
		System.out.println("");
		System.out.println("delete:");
		System.out.println( tree.delete(new Point(-4,4)) ); // true
		System.out.println( tree.delete(new Point(-2,4)) ); // true
		System.out.println(	tree.delete(new Point(1,7)) ); //true
		System.out.println(	tree.delete(new Point(2,2)) ); //true
		System.out.println(	tree.delete(new Point(3,7)) ); //true
		System.out.println(	tree.delete(new Point(5,6)) ); //true
		System.out.println(	tree.delete(new Point(-4,-2)) ); //true
		System.out.println(	tree.delete(new Point(2,-4)) ); //true
		System.out.println(	tree.delete(new Point(4,-6)) ); //true
		System.out.println(	tree.delete(new Point(-1,-2)) ); //true
		System.out.println(	tree.delete(new Point(3,7)) ); //true
		System.out.println(	tree.delete(new Point(1,1)) ); //true


		System.out.println("");

		//System.out.println( tree.delete(new Point(-2,5)) );	//false
		//System.out.println( tree.delete(new Point(-2,9)) );		// false
		System.out.println( tree.find(new Point(-4,4)) ); // null
		System.out.println( tree.find(new Point(-2,4)) ); // null
		System.out.println(	tree.find(new Point(1,7)) ); //true
		System.out.println(	tree.find(new Point(2,2)) ); //true
		System.out.println(	tree.find(new Point(3,7)) ); //true
		System.out.println(	tree.find(new Point(5,6)) ); //true
		System.out.println(	tree.find(new Point(-4,-2)) ); //true
		System.out.println(	tree.find(new Point(2,-4)) ); //true
		System.out.println(	tree.find(new Point(4,-6)) ); //true
		System.out.println(	tree.find(new Point(-1,-2)) ); //true
		System.out.println("");
		System.out.println("findAll:");
		System.out.println(	tree.insert(new Point(-4,4)) ); //true
		System.out.println(	tree.insert(new Point(4,4)) ); //true
		System.out.println(	tree.find(0, 8, 0, 8)); //true
		System.out.println(	tree.find(-8, 0, 0, 8)); //true
		System.out.println(	tree.find(-8, 0, -8, 0)); //true


		boolean a = true;
        boolean b = true;
        boolean c = false;
        boolean d = false;
		boolean e = a || b || c || d;
       // System.out.println("" + e);
        
		/*
		rectangle ne = rec.getNE();
		rectangle nw = rec.getNW();
		rectangle sw = rec.getSW();
		rectangle se = rec.getSE();
		private boolean sharePoint(rectangle rec) {

			if (x1 > rec.x2 || x2 < rec.x1 || y1 > rec.y2 || y2 < rec.y1) {
				return false;
			}
			return true;
		}*/
	}

}