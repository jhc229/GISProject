package quadTree;

import java.util.Vector;

//On my honor:
//
//- I have not discussed the Java language code in my program with
//anyone other than my instructor or the teaching assistants
//assigned to this course.
//
//- I have not used Java language code obtained from another student,
//or any other unauthorized source, either modified or unmodified.
//
//- If any Java language code or documentation used in my program
//was obtained from another source, such as a text book or course
//notes, that has been clearly noted with a proper citation in
//the comments of my program.
//
//- I have not designed this program in such a way as to defeat or
//interfere with the normal operation of the Curator System.
//
//<Jung H Choi>

// The test harness will belong to the following package; the quadtree
// implementation must belong to it as well.  In addition, the quadtree
// implementation must specify package access for the node types and tree
// members so that the test harness may have access to it.
//

public class prQuadTree< T extends Compare2D<? super T> > {
   
   // You must use a hierarchy of node types with an abstract base
   // class.  You may use different names for the node types if
   // you like (change displayHelper() accordingly).
   abstract class prQuadNode { }

   
   // ~ prQuadLeaf
   // .........................................................................
   // hierarchy of nodes
   class prQuadLeaf extends prQuadNode {
   
	  Vector<T> Elements;
      
      public prQuadLeaf(T elem) { 
    	  Elements = new Vector<T>();
    	  Elements.add(elem);
      }
      public prQuadLeaf(){
    	  Elements = new Vector<T>();
      }
   }

   // ~ prQuadInternal
   // .........................................................................  
   class prQuadInternal extends prQuadNode {
    
	  prQuadNode NW, NE, SE, SW;
	  
	  
   }
   // ~ prQuadTree
   // .........................................................................  
   prQuadNode root;
   int xMin, xMax, yMin, yMax;
   boolean found;
   boolean insert_Flag;
   int bucketSize =4;
   
   
   
   
   // Initialize quadtree to empty state, representing the specified region.
   public prQuadTree(int xMin, int xMax, int yMin, int yMax) {
	   this.xMin = xMin;
	   this.xMax = xMax;
	   this.yMin = yMin;
	   this.yMax = yMax;
	   root = null;
      
   }
    
   // Pre:   elem != null
   // Post:  If elem lies within the tree's region, and elem is not already 
   //        present in the tree, elem has been inserted into the tree.
   // Return true iff elem is inserted into the tree. 
   public boolean insert(T elem) {
	   if (!elem.inBox(xMin, xMax, yMin, yMax)) return false; //duplicates allowed
		//System.out.println("point:  " +elem.getX() +" " + elem.getY() + " " + elem.getOffset());

	   root = fInsert(root, elem, xMin, xMax, yMin, yMax);
	   return true;
   }
   
   /*
    * private helper insert.
    */
   @SuppressWarnings("unchecked")
private prQuadNode fInsert(prQuadNode rt, T elem, int xMin, int xMax,
		int yMin, int yMax) {
		
		if (rt == null) return new prQuadLeaf(elem); // when it's empty
		
		if (rt.getClass().equals(prQuadInternal.class)){ // when it's internal
			
			prQuadInternal	internalNode = (prQuadInternal) rt;
			Direction inQuadrant = elem.inQuadrant(xMin, xMax, yMin, yMax); // Receives the direction from inquadrant method.
			
			switch (inQuadrant){ // Recursive the element to appropriate region.
			case NW:
				internalNode.NW = fInsert(internalNode.NW, elem, xMin, ((xMin + xMax)/2), ((yMin+yMax)/2), yMax);
				return internalNode;
			case NE:
				internalNode.NE = fInsert(internalNode.NE, elem, ((xMin + xMax)/2), xMax, ((yMin+yMax)/2), yMax);
				return internalNode;
			case SW:
				internalNode.SW = fInsert(internalNode.SW, elem, xMin, ((xMin + xMax)/2), yMin, ((yMin+yMax)/2));
				return internalNode;
			case SE:
				internalNode.SE = fInsert(internalNode.SE, elem, ((xMin + xMax)/2), xMax, yMin, ((yMin+yMax)/2));
				return internalNode;
			default: break;
				
			}
			return internalNode;
			
		}// when it's leaf
		else{
			/*	prQuadInternal	internalNode =  new prQuadInternal(); // create an internal node
				prQuadLeaf leafNode = (prQuadLeaf) rt;
				
				if (leafNode.Elements.size() < bucketSize){ // Check the number of leafs
					insert_Flag =true;
					return bucketSplit(leafNode, elem);
				}
				else{
				// For Leaf splitting, the original leafnode will be inserted into
				// current internal node then the new element will be added.			
					for (T element : leafNode.Elements) {
						internalNode = (prQuadInternal) fInsert(internalNode,
								element, xMin, xMax, yMin, yMax);
					}
				return internalNode = (prQuadInternal) fInsert(internalNode, elem , xMin, xMax, yMin, yMax);
			}*/
			
			
			prQuadLeaf leaf = (prQuadLeaf) rt;

			// see if the leaf is full
			if (leaf.Elements.size() < bucketSize) {

				// check for repeats
				boolean same = false;
				for (int i = 0; i < leaf.Elements.size(); i++) {
					// if the location exists
					if (leaf.Elements.get(i).equals(elem)) {
						same = true;
						leaf.Elements.get(i).addOffset(elem.getOffset());
					}
				}
				// add the new element into leaf if its not full
				if (!same) {
					leaf.Elements.addElement(elem);
				}
				return leaf;
			}
			// leaf is full
			// create a new internal node
			prQuadNode internal = new prQuadInternal();

			for (int i = 0; i < leaf.Elements.size(); i++) {
				// add the original leaf node into the internal node
				internal = fInsert(internal, leaf.Elements.get(i), xMin, xMax, yMin, yMax);
			}
			// add the new leaf node with element elem into the internal node
			internal = fInsert(internal, elem, xMin, xMax, yMin, yMax);
			return internal;
		}
	}
   
   /*
    * private helper when duplicates found or leafNodes still < bucketSize
    */
   private prQuadLeaf bucketSplit(prQuadLeaf leafNode, T elem){
	  //  prQuadLeaf leaf= new prQuadLeaf();
	   // leaf = leafNode;
		int i = 0;
		if (elem == null) {
			return null;
		}
	   do {
		   if ( leafNode.Elements.get(i).equals(elem)){
			   leafNode.Elements.get(i).addOffset(elem.getOffset());
			   insert_Flag = true;
			   i++;
		   }
		   else{
			   insert_Flag = false;
			   i++;
		   }
	   }while ( leafNode.Elements.size() > i);
		   leafNode.Elements.addElement(elem);
		   return leafNode;
   }

   // Pre:  elem != null
   // Post: If elem lies in the tree's region, and a matching element occurs
   //       in the tree, then that element has been removed.
   // Returns true iff a matching element has been removed from the tree.
   public boolean delete(T Elem) {
	   if (Elem.inBox(xMin, xMax, yMin, yMax)){
		   found = false;
		   root = fDelete(root, Elem, xMin, xMax, yMin, yMax);
		   return found == true;
	   }
	   return false;
   }
   
   /*
    * private helper delete.
    */
   @SuppressWarnings("unchecked")
   private prQuadNode fDelete(prQuadNode rt, T elem, int xMin, int xMax,
		   int yMin, int yMax){
	  
	   if (rt == null) return null;  //When nothing found

	   if (rt.getClass().equals(prQuadInternal.class)){
		   
			prQuadInternal	internalNode = (prQuadInternal) rt;
			Direction inQuadrant = elem.inQuadrant(xMin, xMax, yMin, yMax);
			
			switch (inQuadrant){
			case NW:
				 internalNode.NW = fDelete(internalNode.NW, elem, xMin, ((xMin + xMax)/2), ((yMin+yMax)/2), yMax);
				 break;
			case NE:
				 internalNode.NE = fDelete(internalNode.NE, elem, ((xMin + xMax)/2), xMax, ((yMin+yMax)/2), yMax);
				 break;
			case SW:
				 internalNode.SW = fDelete(internalNode.SW, elem, xMin, ((xMin + xMax)/2), yMin, ((yMin+yMax)/2));
				 break;
			case SE:
				 internalNode.SE = fDelete(internalNode.SE, elem, ((xMin + xMax)/2), xMax, yMin, ((yMin+yMax)/2));
				 break;
			default: break;
				
			}
			int pos = moveUp(internalNode); // receive the position of the region
			//parent node has only one nonempty child so the parent node will be
			//replaced with remaining child (leaf).
			if (found && pos >= 1){
				
				switch (pos){
					
					case 1:
						return internalNode.NW;
					case 2:
						return internalNode.NE;
					case 3:
						return internalNode.SW;
					case 4:
						return internalNode.SE;
					default: break;
				}
			}
			return internalNode;
	   }
	   
	   else if (rt.getClass().equals(prQuadLeaf.class)){
		   
			prQuadLeaf leafNode = (prQuadLeaf) rt;
			for (int i = 0; i < leafNode.Elements.size(); i++){
				
				if (leafNode.Elements.get(i).equals(elem)){ // element found
						if (leafNode.Elements.size() ==1){
							leafNode.Elements.remove(i);
							found = true;
							return null;
						}
						found = true;
						leafNode.Elements.remove(i);
						break;
				}
				found = false;
			}
	   }
	   return null;
   }

   /*
    * This method is designed to check all four regions number of leaves and
    * internal nodes. 
    * Return the specific region only when the node contains 0 internal node
    * and leaf either 0 or 1.
    * Since the requirement for the quadtree bucket size is 1. There is a split
    * every node greater than 1.
    * The condition at the last stament simply contracts the branch.
    */
   private int moveUp(prQuadInternal node){
	   int leafct = 0;
	   int internalct = 0;
	   int pos = 0;
	   if (node.NW != null ){ // Checks every quadrants and mark them with pos number
		   pos =1;			
		   if(node.NW.getClass().equals(prQuadLeaf.class)) leafct++;
		   else internalct++;
	   }// This checks both leaf and internal nodes and with slight modification, it can be used for more complicate task.
	   if (node.NE != null ){
		   pos = 2;
		   if(node.NE.getClass().equals(prQuadLeaf.class)) leafct++;
		   else internalct++;
	   }
	   if (node.SW != null ){
		   pos =3;
		   if(node.SW.getClass().equals(prQuadLeaf.class)) leafct++;
		   else internalct++;
	   }
	   if (node.SE != null ){
		   pos =4;
		   if(node.SE.getClass().equals(prQuadLeaf.class)) leafct++;
		   else internalct++;
	   }
	   if (internalct == 0 && leafct <=1) return pos;
	   return 0;
	   
   }
   
   // Pre:  elem != null
   // Returns reference to an element x within the tree such that 
   // elem.equals(x)is true, provided such a matching element occurs within
   // the tree; returns null otherwise.
   public T find(T Elem) {
      return fFind(root, Elem, xMin, xMax, yMin, yMax);
   }
   
   /*
    * private helper find element.
    */
   @SuppressWarnings("unchecked")
private T fFind(prQuadNode rt, T elem, int xMin, int xMax,
		int yMin, int yMax){
	   if (rt == null) return null;
	   if (rt.getClass().equals(prQuadInternal.class)){
		   
			prQuadInternal	internalNode = (prQuadInternal) rt;
			Direction inQuadrant = elem.inQuadrant(xMin, xMax, yMin, yMax);
			
			switch (inQuadrant){
			case NW:
				return fFind(internalNode.NW, elem, xMin, ((xMin + xMax)/2), ((yMin+yMax)/2), yMax);
			case NE:
				return fFind(internalNode.NE, elem, ((xMin + xMax)/2), xMax, ((yMin+yMax)/2), yMax);
			case SW:
				return fFind(internalNode.SW, elem, xMin, ((xMin + xMax)/2), yMin, ((yMin+yMax)/2));
			case SE:
				return fFind(internalNode.SE, elem, ((xMin + xMax)/2), xMax, yMin, ((yMin+yMax)/2));
			default: break;
				
			}
	   }
	   else{// when the node reaches leaf node, simply returns that element
		    // and rewind the recursive.
			prQuadLeaf leafNode = (prQuadLeaf) rt;
			
			for (T element : leafNode.Elements){
				if (element.equals(elem)){
					return element;
				}
			}
	   }
		return null;
   }
 
   // Pre:  xLo, xHi, yLo and yHi define a rectangular region
   // Returns a collection of (references to) all elements x such that x is 
   //in the tree and x lies at coordinates within the defined rectangular 
   // region, including the boundary of the region.
   public Vector<T> find(int xLo, int xHi, int yLo, int yHi) {
      //return ffindElements(rt, )      
	   return fFindRegion(root, xMin, xMax, yMin, yMax, xLo, xHi, yLo, yHi);
   }
   
   /*
    *  private helper find region.
    */
	@SuppressWarnings("unchecked")
	private Vector<T> fFindRegion(prQuadNode rt, 
			int xMin, int xMax, int yMin, int yMax, 
			int x1, int x2, int y1, int y2) {
	   
		Vector<T> res = new Vector<T>();
		if (rt == null) return res;
		
		// recurisve would run through all 4 regions to search for all the coordinates
		// within the boundary.
		if (rt.getClass().equals(prQuadInternal.class)){
			
			prQuadInternal	internalNode = (prQuadInternal) rt;
			
			// Sort of same concept as the inBox method. 
			// Where this one compares new argument of minimum x and y values with
			// the original maximum x and y values. If true, recursive through
			// the relevent region for further search.
			if (x1 <= ((xMin + xMax)/2) || x2 >= xMin || y1 <= yMax || y2 >= ((yMin+yMax)/2)){ 
				res.addAll(fFindRegion(internalNode.NW, xMin, ((xMin + xMax)/2), ((yMin+yMax)/2), yMax, x1, x2, y1, y2));
			}
			
			if (x1 <= xMax || x2 >= ((xMin + xMax)/2) || y1 <= yMax || y2 >= ((yMin+yMax)/2)){ 
				res.addAll(fFindRegion(internalNode.NE, ((xMin + xMax)/2), xMax, ((yMin+yMax)/2), yMax, x1, x2, y1, y2));
			}
			
			if (x1 <= ((xMin + xMax)/2) || x2 >= xMin || y1 <= ((yMin+yMax)/2) || y2 >= yMin){ 
				res.addAll(fFindRegion(internalNode.SW, xMin, ((xMin + xMax)/2), yMin, ((yMin+yMax)/2), x1, x2, y1, y2));
			}
			
			if (x1 <= xMax || x2 >= ((xMin + xMax)/2) || y1 <= ((yMin+yMax)/2) || y2 >= yMin){
				res.addAll(fFindRegion(internalNode.SE, ((xMin + xMax)/2), xMax, yMin, ((yMin+yMax)/2), x1, x2, y1, y2));
			}
			return res;
		}
		
		else { // when the node reaches leaf node, simply adds the element and
			   // and rewind the recursive.
			
			prQuadLeaf leafNode = (prQuadLeaf) rt;
			for (int i = 0; i < leafNode.Elements.size(); i++) {

				// check if elem is within the region wanted
				if (leafNode.Elements.get(i).inBox(x1, x2, y1, y2) ){
					res.add(leafNode.Elements.get(i));
				}
			}
			
			/*
			for (T element : leafNode.Elements){
				if (element.inBox(x1, x2, y1, y2)){
					 res.add(leafNode.Elements.firstElement());
				}
			}*/
			return res;
		}
   }
	int count = 0;
	String Out = "";
	
	
		public String treeToString(){
			count = 0;
			Out = "";
			if (root == null) return "";
			printTree(root, "");
			return Out;
		}
		
		private String printTree(prQuadNode rt, String pad ){
			return Pad;
			
			if (rt == null) {
				Out += (" " + pad + "*L*\n");
				return Out;
			}
			// Check for and process SW and SE subtrees
			if (!rt.isLeaf()) {
				prQuadInternal p = (prQuadInternal) rt;
				printTreeHelper(p.SW, pad + "---");
				printTreeHelper(p.SE, pad + "---");
			}

			// Determine if at leaf or internal and display accordingly
			if (rt.isLeaf()) {
				prQuadLeaf p = (prQuadLeaf) rt;
				Out += pad;
				for (int pos = 0; pos < p.Elements.size(); pos++) {
					Out += p.Elements.get(pos);

					// prints an output every 100 leaf found
					count++;
					if (count % 100 == 0) {
						System.out.println(count + " records added to tree");
					}
				}
				Out += "\n";

			} else if (!rt.isLeaf())
				Out += (pad + "@I@\n");
			else
				Out += (rt.getClass().getName() + "#\n");

			// Check for and process NE and NW subtrees
			if (!rt.isLeaf()) {
				prQuadInternal p = (prQuadInternal) rt;
				printTreeHelper(p.NE, pad + "---");
				printTreeHelper(p.NW, pad + "---");
			}
			return Out;
		}
			
		
	
}
