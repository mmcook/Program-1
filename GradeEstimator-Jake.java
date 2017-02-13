import java.io.File;
import java.io.FileNotFoundException;
import java.util.InputMismatchException;
import java.util.Scanner;

import javax.swing.plaf.synth.SynthSeparatorUI;

public class GradeEstimator {
	
	private ScoreList scorelist;
	
	private String[] letterGrades;
	private String[] fileLines;
	private int[] thresholds;
	private String[] categories;
	private double[] categoryWeights;
	private ScoreList scoreList;
	private Scanner gradesIn;
	private int numLG, numCat, numWeighted;
	
	
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
	
	public static GradeEstimator createGradeEstimatorFromFile( String gradeInfo ) 
		      throws FileNotFoundException, GradeFileFormatException {
		
		
			// Create new GradeEstimator instance
			GradeEstimator gradeEstimator = new GradeEstimator();
			
			// Create new File with given filename
			File file = new File(gradeInfo);
			
			// Create new Scanner reference to read said file
			gradeEstimator.gradesIn = new Scanner(file);
			
			// Serves as a counter for the Array initializing
			int i = 0;
			
			gradeEstimator.fileLines = new String[100];
			
			while(gradeEstimator.gradesIn.hasNextLine()) {
			
				gradeEstimator.fileLines[i] = gradeEstimator.gradesIn.nextLine();
				i++;
			}
			
			while (i < 100) {
				gradeEstimator.fileLines[i] = "null";
				i++;
			}
			
			
			// Initializes letterGrades as an Array with size 100 (just because)
			gradeEstimator.letterGrades = gradeEstimator.fileLines[0].split(" ");
			
			// While there is data on the line, take it in, make sure it isn't a 
			// comment or the end of the line, and then add it to the array
			
			
			
			
			// Initialize the array thresholds to be the size of the number of 
			// letter grades
			String[] tempThresholds = gradeEstimator.fileLines[3].split(" ");
			
			gradeEstimator.thresholds = new int[tempThresholds.length];
					
			for (int z = 0; z < tempThresholds.length; z++) {
				if (tempThresholds[z].equals("#") || tempThresholds[z].equals("")) {
					break;
				}
				gradeEstimator.thresholds[z] = Integer.parseInt(tempThresholds[z]);
				
			}
			
			
			gradeEstimator.categories = gradeEstimator.fileLines[2].split(" ");
			
			int maxCategories = gradeEstimator.categories.length;
			
			
			
			String[] tempWeights = gradeEstimator.fileLines[3].split(" ");
			
			gradeEstimator.categoryWeights = new double[tempWeights.length];
					
			for (int y = 0; y < tempWeights.length; y++) {
				if (tempThresholds[y].equals("#") || tempThresholds[y].equals("")) {
					break;
				}
				
				gradeEstimator.categoryWeights[y] = Double.parseDouble(tempWeights[y]);
			}
			 
			
			gradeEstimator.scoreList = new ScoreList();
			
			i = 4;
			while (gradeEstimator.fileLines[i] != "null") {
				
				String[] tempScoreInfo = gradeEstimator.fileLines[i].split(" ");	
				
				
				gradeEstimator.scoreList.add(
						new Score(tempScoreInfo[0], 
								Integer.parseInt(tempScoreInfo[1]), 
								Integer.parseInt(tempScoreInfo[2])));
				
				
			}
	
			return gradeEstimator;
			
		
		
	}
	
	/**
	 * This method returns an estimate report based on the GradeEstimator instance that was created
	 * from createGradeEstimatorFromFile(). It calculates the weighted and unweighted percentage from
	 * each of the assignment scores and determines the letter grade of the class from the average 
	 * percentage.
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
			
			String estimateScore = "\t" + String.format("%7.2f", weightedScore[i])
					+"% = " + String.format("%5.2f", unweightedScore) 
									+ "% of " + 
						  String.format("%2.0f", categoryWeights[i])
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
