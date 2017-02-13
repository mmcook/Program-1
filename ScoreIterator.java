import java.util.NoSuchElementException;

/////////////////////////////////////////////////////////////////////////////
//Semester:         CS367 Spring 2016 
//PROJECT:          p1
//FILE:            ScoreIterator.java
//
//Authors: (Team 42) Aseel Albeshri, Cory Burich, Margaret Cook, 
//Jessica Fernandes, Cody Kairis, Jacob Vande Walle
//Author1: Aseel Albeshri, albeshri@wisc.edu, albeshri,lecture 002
//Author2: Cory Burich, cburich@wisc.edu, cburich, lecture 002
//Author3: Margaret Cook, mcook24@wisc.edu, mcook24, lecture 002
//Author4: Jessica Fernandes, jfernandes2@wisc.edu, jfernandes2, lecture 002
//Author5: Cody Kairis, kairis@wisc.edu, kairis, lecture 002
//Author6: Jacob Vande Walle, jvandewalle@wisc.edu, jvandewalle, lecture 002
//

/**
 * This class acts as an indirect access iterator. 
 * It implements the ScoreIteratorADT and iterates through the given scores in
 *  ScoreList that match a specified category.
 *
 */
public class ScoreIterator implements ScoreIteratorADT<Score> {
	//Variable of type ScoreList to store the list of scores
	private ScoreList scoreList;
	//Variable of type int to store the current position
	private int currPos;
	//Variable of type string to store a category
	private Score tempScore = null;

	/**
	 * Initializes the instance fields to the passed values 
	 * and sets the current position to 0.
	 * PRECONDITIONS: instance fields are null
	 * POSTCONDITIONS: instance fields are initialized 
	 * @param scorelist the given list of scores
	 * @param category the given category
	 */
	public ScoreIterator(ScoreList scorelist){
		this.scoreList = scorelist;
		currPos = 0;
	}
	/**
	 * Method used to check if scoreList has a next item with the given category
	 * 
	 * @return true if scoreList has a Score with the given category, otherwise
	 * return false
	 */
	
	public boolean hasNext(String category){
		
		
		
		for (int i = currPos; i < scoreList.size(); i++) {
			
			if (scoreList.get(i).getCategory().equals(category.substring(0,1))) {
				return true;
			}
		}
		return false;
	}
	/**
	 * Method used to get the next item in scoreList 
	 * @throws NoSuchElementException if scoreList does not have a next item 
	 * with the given category
	 * 
	 * @return next item in scoreList with the given category
	 */
	public Score next(String category){
		if (!hasNext(category)) {
			throw new NoSuchElementException();
		}
		
		/* This is what we had before
		
		Score result = scorelist.get(currPos);
		currPos++;
		return result;
		*/
		
		while (hasNext(category)) {
			tempScore = scoreList.get(currPos);
			currPos++;
			
			if (tempScore.getCategory().equals(category.substring(0,1))) {
				break;
			}
		}
		return tempScore;
	}
}
