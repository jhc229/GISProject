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
		
		int home;
		int pos = home = availSlot(k);  //size -1
		
		for (int i = 1; tableList[pos] != null; i++){
			pos = (home + p(k, i)) % size;
			assert tableList[pos].key().compareTo(k) != 0: "duplicates not allowed";
		}
		tableList[pos] = new KVpair<Key, E>(k, e);
	}
	
	public int availSlot(Key k){
		int index = k.hashCode() % size;
		int offset = 1;
		int count = 0;

		while (tableList[index] != null && !tableList[index].getKey().equals(k)) {
			index += offset; // +1, +3, +5, +7, +9
			offset += 2;
			index %= size;
			count++;

		}
		if (count > longestProbe) {
			longestProbe = count;
		}
		return index;
	}
	//xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
	public static int elfHash(String str) {
		long hashCode = 0;
		for (int Pos = 0; Pos < str.length(); Pos++) { // use all elements

			hashCode = (hashCode << 4) + str.charAt(Pos); // shift/mix

			long hiBits = hashCode & 0xF0000000L; // get high nybble

			if (hiBits != 0)
				hashCode ^= hiBits >> 24; // xor high nybble with second nybble

			hashCode &= ~hiBits; // clear high nybble
		}

		return (int) (hashCode);
	}
	
	
}
