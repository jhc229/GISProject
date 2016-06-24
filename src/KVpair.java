/**
 * Source code from spring 2016 CS3114.
 * @author sean
 *
 * @param <Key>
 * @param <E>
 */
class KVpair<Key, E> {

	private Key k;
	private E e;
	
	// Constructor
	KVpair(){
		k = null;
		e = null;
		
	}
	
	KVpair(Key k, E e){
		this.k = k;
		this.e =e;
	}
	
	public Key key(){
		return k;
	}
	public E value(){
		return e;
	}
	
}
