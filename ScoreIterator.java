import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * 
 * @author Meggie Cook
 *
 */
public class ScoreIterator implements ScoreIteratorADT<Score>, Iterator<Score>{
	
	private ScoreList scorelist;
	private int currPos;
	private String category;
	
	public ScoreIterator(ScoreList scorelist, String category){
		this.scorelist = scorelist;
		this.category = category;
		currPos = 0;
	}
	/**
	 * 
	 */
	public boolean hasNext(){
		return currPos < scorelist.size();
	}
	/**
	 * 
	 */
	public Score next(){
		if(!hasNext()){
			throw new NoSuchElementException();
		}
		
		Score result = scorelist.get(currPos);
		currPos++;
		return result;
	}

}
