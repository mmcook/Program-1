import java.util.Iterator;
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
* 
* @authors Aseel Albeshri, Cory Burich, Margaret Cook, Jessica Fernandes, 
* Cody Kairis, Jacob Vande Walle

/**
 * This class acts as an indirect access iterator. 
 * It implements the ScoreIteratorADT and iterates through the given scores in
 *  ScoreList that match a specified category.
 *
 */
public class ScoreIterator implements ScoreIteratorADT<Score>, Iterator<Score>{
	//Variable of type ScoreList to store the list of scores
	private ScoreList scorelist;
	//Variable of type into so store the current position
	private int currPos;
	//Variable of type string to store a category
	private String category;

	/**
	 * Initializes the instance fields to the passed values 
	 * and sets the current position to 0.
	 * PRECONDITIONS: instance fields are null
	 * POSTCONDITIONS: instance fields are initialized 
	 * @param scorelist the given list of scores
	 * @param category the given category
	 */
	public ScoreIterator(ScoreList scorelist, String category){
		this.scorelist = scorelist;
		this.category = category;
		currPos = 0;
	}
	/**
	 * Method used to check if scorelist has a next item
	 * @return true if scoreList is bigger than current position, false otherwise
	 */
	public boolean hasNext(){
		return currPos < scorelist.size();
	}
	/**
	 * Method used to get the next item in scorelist 
	 * @throws NoSuchElementException if scoreList does not have a next item
	 * @return next item in scorelist
	 */
	public Score next(){
		if (!hasNext()) {
			throw new NoSuchElementException();
		}

		Score result = scorelist.get(currPos);
		currPos++;
		return result;
	}

}
