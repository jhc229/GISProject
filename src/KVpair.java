import java.util.Vector;

/**
 * Source code from spring 2016 CS3114.
 * @author sean
 *
 * @param <Key>
 * @param <E>
 */
class KVpair<Key, E> {

	private Key k;
	private Vector<E> offset;
	
	// Constructor
	KVpair(){
		k = null;
		offset = null;
	}
	
	KVpair(Key k, E offset){
		this.k = k;
		this.offset = new Vector<E>();
		this.offset.add(offset);
	}
	
	public Key getKey(){
		return k;
	}
	public Vector<E> getValue(){
		return offset;
	}
	
	public String kvPairtoString(){
		String output = "";
		return output = this.getKey() + ", " + this.getValue();
	}
	
}
