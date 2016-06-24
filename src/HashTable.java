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
	 * insert element into hashtable
	 */
	public void insertHash(Key k, E e){
		
	//	int home;
		//int pos = home = k.hashCode() % size;  
		if ((numbElements/tableList.length) > .7){
			rehash();
		}
		fInsertHash(tableList, k, e);
		numbElements++;
		}
	
	/*
		for (int i =1; tableList[pos] != null; i++){
			pos = (home + p(k, i)) % size;
			assert tableList[pos].key().compareTo(k) != 0: "duplicates not allowed";
		}
		tableList[pos] = new KVpair<Key, E>(k, e);
	}*/
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void rehash(){
	
		size *= 2;
		KVpair[] newTable  = new KVpair[size];
		//tableList = new KVpair[size];
		for (int i = 0; tableList[i] != null; i++){
			fInsertHash(newTable, tableList[i].key(), tableList[i]	.value());
		}
		tableList = newTable;
		
	}
	
	private void fInsertHash(KVpair[] table, Key k, E e){
		int home; 
		int count = 0;
		int pos = home = Math.abs(e.hashCode() % table.length); 
		for (int i =1; tableList[pos] != null; i++){ // check duplicates?
			pos = (home + step(k, i)) % size;
			count++;
		}
		longestProbe = Math.max(count, longestProbe);
		table[pos] = new KVpair<Key, E>(k, e);
	}
	
	private int step(Key k, int a){
		return  (a * a + a)/2;
	}

	/**
	 * Source code from course notes.
	 * 
	 * @param str
	 * @return
	 */
	public static long elfHash(String str) {
		long hashValue = 0;
		for (int Pos = 0; Pos < str.length(); Pos++) { // use all elements

			hashValue = (hashValue << 4) + str.charAt(Pos); // shift/mix

			long hiBits = hashValue & 0xF000000000000000; // get high nybble

			if (hiBits != 0)
				hashValue ^= hiBits >> 56; // xor high nybble with second nybble

			hashValue &= ~hiBits; // clear high nybble
		}
		return hashValue;
	}
	
	
}
