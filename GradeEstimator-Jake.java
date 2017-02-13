import java.io.File;
import java.io.FileNotFoundException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class GradeEstimator {
	
	private ScoreList scorelist;
	
	private String[] letterGrades;
	private int[] thresholds;
	private String[] categories;
	private int[] categoryWeights;
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
		
		//try
		//{
			// Create new GradeEstimator instance
			GradeEstimator gradeEstimator = new GradeEstimator();
			
			// Create new File with given filename
			File file = new File(gradeInfo);
			
			// Create new Scanner reference to read said file
			gradeEstimator.gradesIn = new Scanner(file);
			
			// Serves as a counter for the Array initializing
			int i = 0;
			
			// Initializes letterGrades as an Array with size 100 (just because)
			gradeEstimator.letterGrades = new String[100];
			
			// While there is data on the line, take it in, make sure it isn't a 
			// comment or the end of the line, and then add it to the array
			while(gradeEstimator.gradesIn.hasNext()) {
				
				String tempNext = gradeEstimator.gradesIn.next();
				
				if (!tempNext.equals("#") || tempNext.equals("\n")) {
					gradeEstimator.letterGrades[i] = tempNext;
					gradeEstimator.numLG++;
					i++;
				}
				else {
					gradeEstimator.gradesIn.nextLine();
					break;
				}
			}
			
			// This keeps track of the number of letter grades, that way thresholds
			// doesn't need to be huge as well
			int maxNumOfGrades = i;
			
			// Reset counter i
			i = 0;
			
			// Initialize the array thresholds to be the size of the number of 
			// letter grades
			gradeEstimator.thresholds = new int[maxNumOfGrades + 1];
			
			// While there are still numbers on the line add them to the array
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
				
				//Add the name, points earned and total points possible into the ScoreList
				//for the rest of the input files
				gradeEstimator.scoreList.add(
						new Score(gradeEstimator.gradesIn.next(), 
								gradeEstimator.gradesIn.nextInt(), 
								gradeEstimator.gradesIn.nextInt()));
				
				gradeEstimator.gradesIn.nextLine();
			}
	
			return gradeEstimator;
			
		/*}catch(RuntimeException e)
		{
			throw new GradeFileFormatException();
		}*/
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
						  String.foramt("%2.0f", categoryWeights[i])
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
