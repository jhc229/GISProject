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
	
	
	// Constructor
	public HashTable(int size){
		
		tableList = new KVpair[size];
	}

	public void insertHash(Key k, E e){
		
		int home;
		int pos = home = availSlot(k);
		
	}
	
	public int availSlot(Key, k){
		int index = key.hashCode() % capacity;
		int offset = 1;

		int count = 0;

		// keep looking if the space exists and the key is not already in there
		while (list[index] != null && !list[index].getKey().equals(key)) {

			index += offset;
			offset += 2;
			index %= capacity;
			count++;

		}
		// helper code for getting the length of time spending on find the
		// correct space
		if (count > longestProbe) {
			longestProbe = count;
		}
		return index;
	}
	
	
}
