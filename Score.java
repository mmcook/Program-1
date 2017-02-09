/////////////////////////////////////////////////////////////////////////////
// Semester:         CS367 Spring 2016 
// PROJECT:          p0
// FILE:             Score.java
//
// Authors: Margaret Cook, mcook24@wisc.edu, 9074490211, Lecture002
//////////////////////////// 80 columns wide //////////////////////////////////

/**
 * This class controls each of the scores in the list. It contains the name
 * of the assignment, the points earned in the assignment and the maximum
 * points that is possible to get in the assignment. Additionally, it returns
 * the category of the assignment and the percentage earned.
 * @author Margaret Cook
 *
 */
public class Score {
	//The assignment name
	private String name;
	
	//The points earned in the assignment
	private double pointsEarned;
	
	//The points possible for the assignment
	private double pointsPossible;
	
	/**
	 * The constructor for the scores, and assigns the name, points earned and total points possible
	 * for this particular score
	 * @param name is the assignment name
	 * @param pointsEarned is the points earned
	 * @param pointsPossible is the possible points for the assignment
	 * @throws IllegalArgumentException
	 */
	public Score(String name, double pointsEarned, double pointsPossible) throws IllegalArgumentException{
		//If the name is null, the points earned are negative, the total points possible are negative
		//and/or the points earned are larger than the possible points, throw an exception
		if(name == null|| pointsEarned < 0|| pointsPossible < 0|| pointsEarned > pointsPossible)
			throw new IllegalArgumentException();
		
		this.name = name;
		this.pointsEarned = pointsEarned;
		this.pointsPossible = pointsPossible;
	}
	
	/**
	 * Gives the assignment name of the score
	 * @return the assignment name
	 */
	public String getName(){
		return name;
	}
	
	/**
	 * Returns the points that one has earned in the assignment
	 * @return
	 */
	public double getPoints(){
		return pointsEarned;
	}
	
	/**
	 * Returns the maximum points that one can earn in the assignment
	 * @return
	 */
	public double getMaxPossible(){
		return pointsPossible;
	}
	
	/**
	 * Returns the category of the assignment, I.E the first letter of the
	 * assignment name
	 * @return the first letter of name
	 */
	public String getCategory(){
		return name.substring(0, 1);
	}
	
	/**
	 * Returns the percentage of the score, which is calculated by dividing the
	 * points earned by the maximum point, and make it into a percentage
	 * @return
	 */
	public double getPercent(){
		return (pointsEarned / pointsPossible) * 100;
	}

}
