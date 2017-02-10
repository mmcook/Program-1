import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class GradeEstimator {
	
	private ScoreList scorelist;
	
//	private String[] letterGrades;
//	
//	private int[] minThresh;
//	
//	private String[] category;
//	
//	private int[] categoryWeight;
	
	private String[] letterGrades;
	private int[] thresholds;
	private String[] categories;
	private int[] categoryWeights;
	private ScoreList scoreList;
	private Scanner gradesIn;
	
	
	public static void main(String[] args) {
		
		String gradeInfo = "";
		
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
				
			}catch(FileNotFoundException e)
			{
				System.out.println("java.io.FileNotFoundException: " + gradeInfo + " (No such file or directory)");
			}
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
		
		// Initializes letterGrades as an Array with size 100 (just because)
		gradeEstimator.letterGrades = new String[100];
		
		// While there is data on the line, take it in, make sure it isn't a 
		// comment or the end of the line, and then add it to the array
		while(gradeEstimator.gradesIn.hasNext()) {
			String tempNext = gradeEstimator.gradesIn.next();
			if (!tempNext.equals("#") || tempNext.equals("\n")) {
				gradeEstimator.letterGrades[i] = tempNext;
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
		
		
		/*for (int k = 0; k < maxNumOfGrades; k++) {
			System.out.println(gradeEstimator.thresholds[k]);
		}*/
		
		gradeEstimator.categories = new String[10];
		i = 0;
		
		while(gradeEstimator.gradesIn.hasNext()) {
			String tempNext = gradeEstimator.gradesIn.next();
			if (!tempNext.equals("#")) {
				gradeEstimator.categories[i] = tempNext;
				i++;
			}
			else {
				gradeEstimator.gradesIn.nextLine();
				break;
			}
		}
		
		/*for (int k = 0; k < gradeEstimator.categories.length; k++) {
			System.out.println(gradeEstimator.categories[k]);
		}*/
		
		int maxCategories = i;
		i = 0;
		
		gradeEstimator.categoryWeights = new int[maxCategories + 1];
		
		while(gradeEstimator.gradesIn.hasNextInt()) {
			gradeEstimator.categoryWeights[i] = gradeEstimator.gradesIn.nextInt();
			i++;
		}
		
		/*for (int s = 0; s < maxCategories; s++) {
			System.out.println(gradeEstimator.categoryWeights[s]);
		}*/
		
		gradeEstimator.gradesIn.nextLine();
		
		while (gradeEstimator.gradesIn.hasNextLine()) {
			gradeEstimator.scoreList.add(
					new Score(gradeEstimator.gradesIn.next(), 
							gradeEstimator.gradesIn.nextInt(), 
							gradeEstimator.gradesIn.nextInt()));
			
			gradeEstimator.gradesIn.nextLine();
		}
	
		return gradeEstimator;
	}
	
	public String getEstimateReport() {
		String estimateReport = "";
		double[] weightedScore = new double[categories.length];
		double totalWeightedPercent = 0;
		
		for(int i = 0; i < scorelist.size(); i++)
		{
			String percentScore = scorelist.get(i).getName() + "\t" + scorelist.get(i).getPercent() + "\n";
			estimateReport = estimateReport.concat(percentScore);
		}
		
		estimateReport = estimateReport.concat("Grade estimate is based on " + scorelist.size() + " scores.\n");
		for(int i = 0; i < categories.length; i++)
		{
			ScoreIterator itr = new ScoreIterator(scoreList, categories[i]);
			String categoryLetter = categories[i].substring(0, 1);
			
			double totalScore = 0;
			int currPos = 0;
			
			while(itr.hasNext())
			{
				if(categoryLetter.equalsIgnoreCase(itr.next().getCategory()))
				{
					Score score = itr.next();
					totalScore += score.getPercent();
					currPos++;
				}
				else
				{
					itr.next();
				}
			}
			
			double unweightedScore = totalScore / currPos;
			weightedScore[i] = unweightedScore * (categoryWeights[i] / 100);
			
			String estimateScore = "\t" + weightedScore +"% = " + unweightedScore + "% of " + categoryWeights[i]
									+ "% for " + categories[i] + "\n"; 
			estimateReport = estimateReport.concat(estimateScore);
		}

		estimateReport = estimateReport.concat("--------------------------------\n");
		
		for(int i = 0; i < weightedScore.length; i++)
		{
			totalWeightedPercent += weightedScore[i];
		}
		
		String weightedPercent = "\t" + totalWeightedPercent + "% weighted percent\n";
		estimateReport = estimateReport.concat(weightedPercent);
		
		String letterGrade = "";
		
		for(int i = letterGrades.length; i > 0; i++)
		{
			if(totalWeightedPercent > thresholds[i - 1])
			{
				letterGrade = letterGrades[i - 1];
			}
				
		}
		
		estimateReport = estimateReport.concat("Letter Grade Estimate: " + letterGrade + "\n");
		
		
		return estimateReport;
	}
	
}
