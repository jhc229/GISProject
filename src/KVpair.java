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
	
	KVpair(Key k, E e){
		this.k = k;
		offset = new Vector<E>();
		offset.add(e)
		this.e =e;
	}
	
	public Key key(){
		return k;
	}
	public E value(){
		return e;
	}
	
}
