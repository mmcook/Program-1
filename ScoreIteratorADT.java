/**
 * 
 * @author Margaret Cook
 *
 */
public interface ScoreIteratorADT<E>{
	
	
	/**
	 * Indicates if there is another score 
	 * @return true if there is another score 
	 */
	boolean hasNext();
	
	/**
	 * 
	 * @return
	 */
	E next();
	
	
}
