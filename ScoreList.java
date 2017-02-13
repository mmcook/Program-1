/////////////////////////////////////////////////////////////////////////////
// Semester:         CS367 Spring 2016 
// PROJECT:          program1
// FILE:             ScoreIteratorADT.java
//
// Authors: (Team 42) Aseel Albeshri, Cory Burich, Margaret Cook, 
//          Jessica Fernandes, Cody Kairis, Jacob Vande Walle
// Author1: Aseel Albeshri, albeshri@wisc.edu, albeshri,lecture 002
// Author2: Cory Burich, cburich@wisc.edu, cburich, lecture 002
// Author3: Margaret Cook, mcook24@wisc.edu, mcook24, lecture 002
// Author4: Jessica Fernandes, jfernandes2@wisc.edu, jfernandes2, lecture 002
// Author5: Cody Kairis, kairis@wisc.edu, kairis, lecture 002
// Author6: Jacob Vande Walle, jvandewalle@wisc.edu, jvandewalle, lecture 002
//
//////////////////////////// 80 columns wide //////////////////////////////////
/**
 * This class controls the list of scores by implementing the ScoreListADT.
 * The methods that are allowed in this list are add(), which adds a score to
 * the end of the list, remove(), which removes a score from the certain position,
 * get(), which returns a specified score from the list, and size(), which 
 * returns the number of scores in the list.
 * @author Margaret Cook
 *
 */
public class ScoreList implements ScoreListADT{

	//The total number of scores
	private int numScores;
	
	//Keeps each of the scores
	private Score[] scores;
	
	/**
	 * The constructor of the Scorelist. There will be 100 elements in the scores
	 * array to decrease the need to expand the array
	 */
	public ScoreList(){
		numScores = 0;
		
		scores = new Score[100];
	}
	
	/**
	 * Gives the number of scores in the list
	 * @return the number of scores in the list
	 */
	public int size(){
		return numScores;
	}
	
	/**
	 * Adds a new score to the bottom of the list
	 * @param s a non-null score that is added to the list
	 * @throws IllegalArgumentException
	 */
	public void add(Score s) throws IllegalArgumentException {
		//Prevent null scores from being added
		if(s == null)
		{
			throw new IllegalArgumentException();
		}
		
		//Expand the array
		if(scores.length == numScores)
		{
			Score[] scores2 = new Score[2*scores.length];
			for(int i = 0; i < numScores - 1; i++)
			{
				scores2[i] = scores[i];
			}
			
			scores = scores2;
		}
		
		scores[numScores] = s;
		
		numScores++;
	}
	
	/**
	 * Removes a score on a certain position from the list.
	 * @param i is the position of the score that should be removed
	 * @throws IndexOutOfBoundsException
	 * @return the removed score
	 */
	public Score remove(int i) throws IndexOutOfBoundsException{
		//Prevent when the position is less than 0 or larger than the number of scores
		if(i < 0 || i >= numScores)
			throw new IndexOutOfBoundsException();
		
		Score removed = scores[i];
		
		//Reassign the scores after the removed scores to the position before
		for(int j = i; j < numScores - 1; j++)
		{
			scores[j] = scores[j + 1];
		}
		
		numScores--;
		
		return removed;
	}
	
	/**
	 * Gives a certain score from the list
	 * @param the position of the score the user wants
	 * @throws IndexOutOfBoundsException
	 * @return the score the user wanted
	 */
	public Score get(int i) throws IndexOutOfBoundsException{
		//Prevent when the position is less than 0 or larger than the number of scores
		if(i < 0 || i >= numScores)
			throw new IndexOutOfBoundsException();
		return scores[i];
	}

}
