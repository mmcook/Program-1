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
/**
 * 
 * @authors Aseel Albeshri, Cory Burich, Margaret Cook, Jessica Fernandes, 
 * Cody Kairis, Jacob Vande Walle
 *
 */
public interface ScoreIteratorADT<E>{
	
	
	/**
	 * Indicates if there is another score. Returns true if there is 
	 * another score and false after the last score.
	 *
	 * PRECONDITIONS: (i.e. the incoming list is assumed to be non-null)
	 * 
	 * POSTCONDITIONS: (i.e. the incoming list has been reordered)
	 * 
	 * @return true if there is another score
	 */
	boolean hasNext(String category);
	
	/**
	 * Generic type, returns next input before a space. 
	 *
	 * PRECONDITIONS: (i.e. the incoming list is assumed to be non-null)
	 * 
	 * POSTCONDITIONS: (i.e. the incoming list has been reordered)
	 *
	 * @return next input of a generic type or type specified
	 */
	E next(String category);
	
	
}
