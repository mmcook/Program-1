import java.io.File;
import java.io.FileNotFoundException;
import java.util.InputMismatchException;
import java.util.Scanner;
/////////////////////////////////////////////////////////////////////////////
//Semester:         CS367 Spring 2016 
//PROJECT:          p1
//FILE:           GradeEstimator.java
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
 *Is the main class of the program. It contains the main method and is
 *responsible for reading a grade file and giving an estimated course
 *grade.
 *
 *@authors Aseel Albeshri, Cory Burich, Margaret Cook, Jessica Fernandes, 
* Cody Kairis, Jacob Vande Walle
 */
public class GradeEstimator {
	
	 /** List of scores given from file. */
	private ScoreList scorelist;
	
	/** Array of letter grades that can be obtained in this estimate.*/
	private String[] letterGrades;
	
	/** Array of Minimum percentages for letter grades respectfully. */
	private int[] thresholds;
	
	/** Array of the assignment categories.*/
	private String[] categories;
	
	/** Array of the weights of each category on the final grade 
	 * respectfully. */
	private int[] categoryWeights;
	
	/** Scanner of the text file */
	private Scanner gradesIn;
	
	/** Counting variables for letter grades, categories and weights */
	private int numLG, numCat, numWeighted;
	
	
	/**
	 *This is the starting point of the program. It looks for one command line
	 *argument and tries to create a new grade estimate.
	 *
	 *PRECONDITIONS: there should only be one command line argument file and 
	 *it should be in the GradeFileFormat
	 *
	 *@param args should be a text file
	 */
	public static void main(String[] args) {
		
		String gradeInfo = "";
		
		//If the number of elements in the array isn't 1, print out default message
		if(args.length != 1)
		{
			System.out.println(Config.USAGE_MESSAGE);
		}
		
		else
		{
			gradeInfo = args[0];
			try
			{
				GradeEstimator grd = createGradeEstimatorFromFile(gradeInfo);
				System.out.println(grd.getEstimateReport());
				
			}
			//
			catch(FileNotFoundException e)
			{
				System.out.println("java.io.FileNotFoundException: " + gradeInfo + " (No such file or directory)");
			}
			//
			catch(GradeFileFormatException e)
			{
				System.out.println("GradeFileFormatException");
			}
		}
	}
	
	/**
	 *This method is responsible for creating a grade estimator from a file
	 *given in the specified format
	 *
	 *@param gradeInfo Should be a string in the specified format
	 *@return GradeEstimator object created from file
	 */
	public static GradeEstimator createGradeEstimatorFromFile( String gradeInfo ) 
		      throws FileNotFoundException, GradeFileFormatException {
		
			GradeEstimator gradeEstimator = new GradeEstimator();
			File file = new File(gradeInfo);
			
		
			gradeEstimator.gradesIn = new Scanner(file);
			int i = 0;
			gradeEstimator.letterGrades = new String[100];
			
			while(gradeEstimator.gradesIn.hasNext() && !(gradeEstimator.gradesIn.hasNextInt())) {
				String tempNext = gradeEstimator.gradesIn.next();
				
				if (!tempNext.equals("#")) {
					gradeEstimator.letterGrades[i] = tempNext;
					gradeEstimator.numLG++;
					i++;
				}
				else {
					gradeEstimator.gradesIn.nextLine();
					break;
				}
			}
			
			
			
			int maxNumOfGrades = i;
			i = 0;
			gradeEstimator.thresholds = new int[maxNumOfGrades + 1];
			
			
			while(gradeEstimator.gradesIn.hasNextInt()) {
				gradeEstimator.thresholds[i] = gradeEstimator.gradesIn.nextInt();
				i++;
			}
			
			gradeEstimator.gradesIn.nextLine();
			
			gradeEstimator.categories = new String[10];
			i = 0;
			
			while(gradeEstimator.gradesIn.hasNext()) {
				
				String tempNext = gradeEstimator.gradesIn.next();
				
				if (!tempNext.equals("#") || tempNext.equals("\n")) {
					gradeEstimator.categories[i] = tempNext;
					gradeEstimator.numCat++;
					i++;
				}
				
				else {
					gradeEstimator.gradesIn.nextLine();
					break;
				}
			}
			
			
			
			int maxCategories = i;
			i = 0;
			gradeEstimator.categoryWeights = new int[maxCategories + 1];
			
			while(gradeEstimator.gradesIn.hasNextInt()) {
				gradeEstimator.categoryWeights[i] = gradeEstimator.gradesIn.nextInt();
				i++;
			}
			
			
			gradeEstimator.gradesIn.nextLine();
			
			gradeEstimator.scoreList = new ScoreList();
			
			while (gradeEstimator.gradesIn.hasNextLine()) {
				
				gradeEstimator.scoreList.add(
						new Score(gradeEstimator.gradesIn.next(), 
								gradeEstimator.gradesIn.nextInt(), 
								gradeEstimator.gradesIn.nextInt()));
				
				gradeEstimator.gradesIn.nextLine();
			}
	
			return gradeEstimator;
			
		
	}
	
	/**
	 * This method returns an estimate report based on the GradeEstimator instance that was created
	 * from createGradeEstimatorFromFile(). It calculates the weighted and unweighted percentage from
	 * each of the assignment scores and determines the letter grade of the class from the average 
	 * percentage.
	 * 
	 * @return estimateReport, a string variable that contains the estimate report for the grades
	 */
	public String getEstimateReport() {
		
		ScoreIterator itr = new ScoreIterator(scoreList);
		
		//The report that will be printed
		String estimateReport = "";
		
		//The weighted percentage for each assignment
		double[] weightedScore = new double[numCat];
		//The total score of the weighted percentage
		double totalWeightedPercent = 0;
		
		//Show the percentage for each assignment
		for(int i = 0; i < scoreList.size(); i++) 
		{
			String percentScore = scoreList.get(i).getName() + "\t" + 
									scoreList.get(i).getPercent() + "\n";
			estimateReport = estimateReport.concat(percentScore);
		}
		
		estimateReport = estimateReport.concat("Grade estimate is based on " + 
						scoreList.size() + " scores.\n");
		
		//Show the weighted and unweighted percentage of each category
		for(int i = 0; i < numCat; i++)
		{
			//Create iterator for each category
			
			double totalScore = 0;
			int numScores = 0;
			
			
			//If the category matches, add the percentage to the total score
			//and update the number of scores
			while(itr.hasNext(categories[i]))
			{
				
				Score score = itr.next(categories[i]);
				totalScore += score.getPercent();
				numScores++;
			}
			
			//The unweighted score of this assignment
			double unweightedScore = totalScore / numScores;
			//System.out.println(unweightedScore);
			weightedScore[i] = unweightedScore * (categoryWeights[i] / 100.0);
			
			String estimateScore = "\t" + weightedScore[i] +"% = " + unweightedScore 
									+ "% of " + categoryWeights[i]
									+ "% for " + categories[i] + "\n"; 
			estimateReport = estimateReport.concat(estimateScore);
		}

		estimateReport = estimateReport.concat("--------------------------------\n");
		
		//Add up the weighted scores to determine the total weighted percentage
		for(int i = 0; i < weightedScore.length; i++)
		{
			totalWeightedPercent += weightedScore[i];
		}
		
		String weightedPercent = "\t" + totalWeightedPercent + "% weighted percent\n";
		estimateReport = estimateReport.concat(weightedPercent);
		
		String letterGrade = "";
		
		//Determine the letter grade by the weighted percentage
		for(int i = numLG; i > 0; i--)
		{
			if(totalWeightedPercent > thresholds[i - 1])
			{
				letterGrade = letterGrades[i - 1];
			}
				
		}
		
		estimateReport = estimateReport.concat("Letter Grade Estimate:\n");
		estimateReport = estimateReport.concat(letterGrade);
		
		
		return estimateReport;
	}
	
}
