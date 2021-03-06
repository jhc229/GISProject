import java.util.Vector;

/**
 * Source code from spring 2016 CS3114.
 * @author sean
 *
 * @param <Key>
 * @param <E>
 */
class KVpair<Key, E> {

	private Key k; // NameIndex 
	private Vector<E> offset; // int variable
	
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
	public void addValue(E e) {
		offset.add(e);
	}
	public void setValues(Vector<E> e) {
		offset = e;
	}
	
	public Key getKey(){
		return k;
	}
	public Vector<E> getValue(){
		return offset;
	}
	
	public String kvPairtoString(){
		String output = "";
		return output += ((NameIndex) getKey()).nameIndexToString() + ", " + getValue();
	}
	
}
