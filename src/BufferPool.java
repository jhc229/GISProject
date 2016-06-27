/**
 * Buffer Pool
 * @author sean
 *
 */
public class BufferPool<K, V> {

    private static final class LinkedNode<K, V> {
    	
    	K key;
    	V value;
    	

    	LinkedNode<K, V> previous;

    	LinkedNode<K, V> next;

    	LinkedNode() {
            key = null;
            value = null;
            previous = null;
            next = null;
        }

    	LinkedNode(K k, V v) {
            key = k;
            value = v;
        }
    	
    }
    private LinkedNode<K, V> lru;
    private int maxBuf; 
	
    private LinkNode<K, V> head;  // The head node.
    private LinkNode<K, V> curr;
    private LinkNode<K, V> tail;
    
    
    // ~ Constructor
    // ---------------------------------------------------------------------------
	public BufferPool(){
		lru =  new LinkedList<K, V>(10);
	}
	

	
    // ~ Methods
    // ---------------------------------------------------------------------------
	/**
	 * get pool size
	 * @return pool size.
	 */
	public int poolSize(){
		return LRU.size();
	}
	
}
