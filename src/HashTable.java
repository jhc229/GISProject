import java.util.ArrayList;
import java.util.Vector;


/**
 * Your table will use quadratic probing to resolve collisions, with the quadratic function (n^2+ n)/2 to compute the step size. 
 * The hash table must use a contiguous physical structure (array). The initial size of the table will be 1024, and the table will
 * resize itself automatically, by doubling its size whenever the table becomes 70% full.
 * Since the specified table sizes given above are powers of 2, an empty slot will always be found unless the table is full.
 * You will use the elfhash() function from the course notes, and apply it to the concatenation of the feature name and state
 * (abbreviation) field of the data records. Precisely how you form the concatenation is up to you.
 * @author sean
 *
 * @param <Key>
 * @param <E>
 */
public class HashTable<Key, E> {
	
	private KVpair<Key, E>[] tableList;
	private int index_flag;
	private int longestProbe = 0;
	private int size = 0;
	private int numbElements = 0;
	
	/*
	 *  Constructor
	 */
	public HashTable(int size){
		
		tableList = new KVpair[size];
		this.size = size;
	}
	
	
	/*
	 * quadratic function (n^2+ n)/2
	 */
	private int step(Key k, int a){
		return  (a * a + a)/2;
	}
	
	/*
	 * insert element into hashtable
	 */
	public void insertHash(Key k, E e){
		longestProbe =  Math.max(fInsertHash(tableList, k, e), longestProbe); 
		numbElements++;
		if ( ((double)numbElements/ (double)size) >= .70){
			rehash(); // Rehash the table when it reaches 70% full
		}
	}
	
	/*
	 *  (n^2  + n) /2
	 *  
	 */
	@SuppressWarnings("rawtypes")
	private int fInsertHash(KVpair[] table, Key k, E e){
		int home; // initial position
		int probeCount = 0; //update probe
		int pos = home = Math.abs((elfHash( ((NameIndex) k).nameIndexToString() )) % table.length); 
							//System.out.println("home: " + home + ",size:  " + table.length );
		for (int i = 0; i < table.length; i++){
			//int pos = Math.abs((elfHash (((NameIndex) k).nameIndexToString())) + (i *i + i)/2) % size;
		//	pos = (home + (probeCount * probeCount + probeCount) / 2) % size;
								//System.out.println("pos:" + pos +" ");
			if (table[pos] == null){
				table[pos] = new KVpair<Key, E>(k, e);
				return probeCount;
			}
			if ((((NameIndex) tableList[pos].getKey()).nameIndexToString()).equals( ((NameIndex) k).nameIndexToString())){ // Duplicates
				table[pos].addValue(e);
				return probeCount;
			}
			probeCount++;
			pos = home + step(k, probeCount);
		    if (pos >= table.length){
				pos = pos % table.length; 
			}
		}
		return -1;
	}

	
	/*
	 * Linear time, inserting N items costs
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void rehash() {

		ArrayList<KVpair<Key, E>> oldTable = new ArrayList<KVpair<Key, E>>();

		for(int i = 0; i < size; i++){
			if(tableList[i] != null){
				oldTable.add(tableList[i]);
			}
		}
		// Create new table for the function
		size *= 2;
		tableList = new KVpair[size];
		
		// Rehash all the elements into the new table
		for(KVpair<Key, E> x : oldTable){
			if(x != null){
				fInsertHash(tableList, x.getKey(), x.getValue().firstElement());
			}
		}
	}
	
	/*
	 * index_flag holds the index of the given key.
	 * Return 0 when nothing is found but its available for storage.
	 * 				 1 when there is key found given key entry.
	 * @param k
	 * @return
	 */
	public int findEntry(Key key) {
		int home;
		int index = home = Math.abs((elfHash( ((NameIndex) key).nameIndexToString() )) % size); 
		int probeCount = 0;
		
		while (tableList[index] != null) {
			// Concatnate both keys so that they compare correct values
			if ( (((NameIndex) tableList[index].getKey()).nameIndexToString()).equals( ((NameIndex) key).nameIndexToString())) {
				index_flag =index;
				return 1;
			}
			probeCount++;
			index = home + ((probeCount * probeCount + probeCount)/2);;
			if (index >= size){
				index = index % size;
			}
		}
		return 0;
	}

	/*
	 *  Get Key
	 */
	public NameIndex getNameIndex(Key k){
		int a = findEntry(k);
		if (a <= 0) return null;
		return (NameIndex) tableList[index_flag].getKey();
	}
	
	/*
	 * Get Vector<E>
	 */
	public Vector<E> getEntries(Key k){
		int a = findEntry(k);
		if (a <= 0) return null;
		return tableList[index_flag].getValue();
	}

	
	/**
	 * Get the number of probes
	 * @return
	 */
	public int getProbe(){
		return longestProbe;
	}

	/**
	 * Get the number of elements in the list.
	 * @return
	 */
	public int getNumElements(){
		return numbElements;
	}
	
	/**
	 * Get the current table size.
	 * @return
	 */
	public int getCurrentSize(){
		return size;
	}
	
	/*
	 * Source code from course notes.
	 * (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	private static int elfHash(String str) {
		long hashValue = 0;
		for (int Pos = 0; Pos < str.length(); Pos++) { // use all elements

			hashValue = (hashValue << 4) + str.charAt(Pos); // shift/mix

			long hiBits = hashValue &  0xF000000000000000L; // get high nybble

			if (hiBits != 0)
				hashValue ^= hiBits >> 56; // xor high nybble with second nybble

			hashValue &= ~hiBits; // clear high nybble
		}
		return (int) hashValue;
	}

	/*
	 * This is used to print out all hash values in the following format.
	 */
	public String hashToString(){
		
		String output = "";
		//((NameIndex) tableList[i]. getKey()).nameIndexToString()
		for (int i = 0; i<size; i++){
			if (tableList[i] != null) output+= i +":  [" + tableList[i].kvPairtoString()+ "] "+" \n";
			
		}
		return output;
		
		
	}
	
}
