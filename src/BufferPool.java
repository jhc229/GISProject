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
    	LinkedNode<K, V> curr;
    	LinkedNode<K, V> next;

    	LinkedNode() {
            key = null;
            value = null;
            
            previous = null;
            curr = null;
            next = null;
        }

    	LinkedNode(K k, V v) {
            key = k;
            value = v;
        }

        
        public K getKey()
        {
            return key;
        }
        
        public void setKey(K k){
        	key = k;
        }

        public LinkedNode<K, V> getNext()
        {
            return next;
        }


        public void setNext(LinkedNode<K, V> nextNode)
        {
            next = nextNode;
        }
    }
    
    
  //  private LinkedNode<K, V> lru;
    private final int MAX_BUF = 10; 
	private int currentSize;
    private LinkedNode<K, V> head;  // The head node.
    private LinkedNode<K, V> curr;
    private LinkedNode<K, V> tail;
    
    
    // ~ Constructor
    // ---------------------------------------------------------------------------
	public BufferPool(){
		
	    head = new LinkedNode<K, V>();
	    curr = new LinkedNode<K, V>();
	    tail = new LinkedNode<K, V>();
	    head.next = tail;
	    tail.previous = head;
	    currentSize = 0;
	    
	
	}
	

	
    // ~ Methods
    // ---------------------------------------------------------------------------


	/**
	 * 
	 * @param key
	 * @return
	 */
	public V get(K key) {

		curr = head.next;
		
		while (curr.getNext() != null) {
			if (curr.key.equals(key)) {
				curr.previous.next = curr.next;
				curr.next.previous = curr.previous;
				
				curr.next = head.next;
				curr.previous = head;
				
				head.next.previous = curr;
				head.next = curr;
				
			} else {
				curr = curr.next;

				curr.next = head.next;
				curr.previous = head;

				head.next.previous = curr;
				head.next = curr;
				return curr.value;
			}
			curr = curr.next;
		}
		return null;

	}

	public void insert(K key, V value) {
		LinkedNode<K, V> temp = new LinkedNode<K, V>(key, value);
		temp.next = head.next;
		temp.previous = head;
		head.next.previous = temp;
		head.next = temp;
		currentSize++;

		if (currentSize > MAX_BUF) {
			LinkedNode<K, V> last = tail.previous;
			tail.previous = last.previous;
			tail.previous.next = tail;
			last.previous = null;
			last.next = null;
			currentSize--;

		}
	}
	
	/**
	 * get pool size
	 * @return pool size.
	 */
	public int poolSize(){
		return currentSize;
	}

	public String toString() {
		StringBuilder sb = new StringBuilder("[Most Recently Used]\n");
		LinkedNode<K, V> current = head.next;

		while (current != tail) {
			sb.append(String.format("%10s:  ", current.key.toString()));
			sb.append(current.value + "\n");
			current = current.next;
		}
		sb.append("[Least Recently Used]");
		return sb.toString();
	}
	
}
