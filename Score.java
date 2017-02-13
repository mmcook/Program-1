/////////////////////////////////////////////////////////////////////////////
// Semester:         CS367 Spring 2016 
// PROJECT:          program1
// FILE:             Score.java
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
/////////////////////////////////////////////////////////////////////////////


/**
 * This class is the class for the individual scores. It makes sure that
 * the parameters passed are valid, otherwise it throws an 
 * IllegalArgumentException
 * 
 * @author Jacob Vande Walle
 *
 */

public class Score {
	
	String assignmentName;
	double pointsEarned, pointsPossible;
	
	
	/**
	 * This is the constructor for the score object
	 * @param name is the name of the assignment
	 * @param earned is the number of points you earned
	 * @param possible is the number of points possible
	 * @throws IllegalArgumentException if the parameters are not within the 
	 * proper range
	 */
	public Score(String name, double earned, double possible) throws IllegalArgumentException {
		if (name == null) {
			throw new IllegalArgumentException();
		} else {
			assignmentName = name;
		}
		
		if (earned < 0 || earned > possible) {
			throw new IllegalArgumentException();
		} else {
			pointsEarned = earned;
		}
		
		if (possible < 0) {
			throw new IllegalArgumentException();
		} else {
			pointsPossible = possible;
		}
	}
	
	
	/**
	 * Returns the assignment name
	 * @return assignmentName
	 */
	public String getName() {
		return assignmentName;
	}
	
	
	/**
	 * Returns the points earned
	 * @return pointsEarned
	 */
	public double getPoints() {
		return pointsEarned;
	}
	
	
	/**
	 * Returns the maximum points you could have gotten 
	 * @return pointsPossible
	 */
	public double getMaxPossible() {
		return pointsPossible;
	}
	
	
	/**
	 * Returns the first letter of the assignment name
	 * @return first letter of the assignment name
	 */
	public String getCategory() {
		
		return assignmentName.substring(0,1);
	}
	
	
	/**
	 * 
	 * @return percentage on the assignment
	 */
	public double getPercent() {
		
		double percentage = pointsEarned / pointsPossible;
		percentage = percentage * 100;
		return percentage;
	}

}
