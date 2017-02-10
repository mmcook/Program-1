import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class GradeEstimator {
	
	private String[] letterGrades;
	private int[] thresholds;
	private String[] categories;
	private int[] categoryWeights;
	private ScoreList scoreList;
	private Scanner gradesIn;
	
	public GradeEstimator(String gradeInfo) {
		gradesIn = null;
		scoreList = new ScoreList();
	}
	
	
	public static void main(String[] args) {
		
		GradeEstimator gradeEst = null;
		
		if (args.length != 1) {
			System.out.println(Config.USAGE_MESSAGE);
		} 
		
		else {
			try {
				gradeEst = createGradeEstimatorFromFile(args[0]);
			}
			catch (FileNotFoundException e) {
				System.out.println("File Not Found");
			}
			catch (GradeFileFormatException g) {
				System.out.println("GradeFileFormatException");
			}
		}
		
		gradeEst.getEstimateReport(gradeEst.scoreList);
	}
	
	public static GradeEstimator createGradeEstimatorFromFile( String gradeInfo ) 
		      throws FileNotFoundException, GradeFileFormatException {
	
		// Create new GradeEstimator instance
		GradeEstimator gradeEstimator = new GradeEstimator(gradeInfo);
		
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

	public String getEstimateReport(ScoreList scoreList) {
		
		String report = "Grade estimate is based on " + scoreList.size() + 
				" scores.\n";
		
		
		return report;
	}

}
