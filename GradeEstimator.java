import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class GradeEstimator {
	
	private ScoreList scorelist;
	
	private String[] letterGrades;
	
	private int[] minThresh;
	
	private String[] category;
	
	private int[] categoryWeight;
	
	
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
		GradeEstimator grd = new GradeEstimator();
		Scanner scnr = new Scanner(new File(gradeInfo));
		
		grd.letterGrades = scnr.nextLine().split(" ");
		String[] minThreshString = scnr.nextLine().split(" ");
		
		grd.minThresh = grd.parseIntArray(minThreshString, grd.minThresh);

		grd.category = scnr.nextLine().split(" ");
		String[] categoryWeightString = scnr.nextLine().split(" ");
		
		grd.categoryWeight = grd.parseIntArray(categoryWeightString, grd.categoryWeight);
		
		if(grd.minThresh.length != grd.letterGrades.length || grd.categoryWeight.length != grd.category.length){
			throw new GradeFileFormatException();
		}

		grd.scorelist = new ScoreList();
		while(scnr.hasNextLine())
		{
			String[] score = scnr.nextLine().split(" ");
			grd.scorelist.add(new Score(score[0], Double.parseDouble(score[1]), Double.parseDouble(score[2])));
		}
		
		return grd;
	}
	
	public String getEstimateReport() {
		String estimateReport = "";
		estimateReport = estimateReport.concat("Grade estimate is based on " + scorelist.size() + " scores.\n");
		
		for(int i = 0; i < scorelist.size(); i++)
		{
			ScoreIterator itr = new ScoreIterator(scorelist, category[i]);
			
			
		}
		
		estimateReport = estimateReport.concat("--------------------------------\n");
		estimateReport = estimateReport.concat("Letter Grade Estimate:\n");
		
		
		return estimateReport;
	}
	
	private int[] parseIntArray(String[] string, int[] intarray)
	{
		intarray = new int[string.length];
		
		for(int i = 0; i < string.length; i++)
			intarray[i] = Integer.parseInt(string[i]);
		return intarray;
	}
}
