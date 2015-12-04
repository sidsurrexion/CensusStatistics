import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel; 
import org.jfree.chart.JFreeChart; 
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset; 
import org.jfree.data.category.DefaultCategoryDataset; 
import org.jfree.ui.ApplicationFrame; 
import org.jfree.ui.RefineryUtilities; 

public class CensusAnalysis extends ApplicationFrame {
	
	// Constructor call to create the chartpanel
	public CensusAnalysis(String applicationTitle , String chartTitle, String[] all_years, int[] all_counts) {
		super(applicationTitle);
		// TODO Auto-generated constructor stub
		JFreeChart barChart = ChartFactory.createBarChart( chartTitle, "Years", "Number of People Alive",  
		         								createDataset(all_years, all_counts), PlotOrientation.VERTICAL, true, true, false);
		         
		ChartPanel chartPanel = new ChartPanel( barChart );        
		chartPanel.setPreferredSize(new java.awt.Dimension( 1000 , 767 ) );        
		setContentPane( chartPanel );
	}

	public static void main(String[] args) throws FileNotFoundException {
		// TODO Auto-generated method stub
		String string = "census.txt"; // File name
		
		String filedirectory = new File("").getAbsolutePath();	// Relative file path
		
		File file = new File(filedirectory + "\\" + string	); // File path of the census file
		
		// Code kept in try block to avoid any runtime dumps
		try{
			
			BufferedReader buffer = new BufferedReader(new FileReader(file)); // Reading the file
			
			String line;
			
			Map<String, Integer> birth_data = new HashMap<String, Integer>(); // HashMap for Birth Data
			
			Map<String, Integer> end_data = new HashMap<String, Integer>();// HashMap for Death Data
			
			while ((line = buffer.readLine()) != null) { // Reading until the last entry of the text file
				
				String[] input_data = line.trim().split(":"); // Adding input data to the string array
				
				// Populating data into the respective HashMaps				
				if (birth_data.isEmpty()){
					
					birth_data.put(input_data[0], 1);
					
					end_data.put(input_data[1], 1);
					
				} else {
					
					if(birth_data.containsKey(input_data[0])){
						
						birth_data.put(input_data[0], birth_data.get(input_data[0]) + 1);
						
					} else {
						
						birth_data.put(input_data[0], 1);
						
					}
					
					if(end_data.containsKey(input_data[1])){
						
						end_data.put(input_data[1], end_data.get(input_data[1]) + 1);
						
					} else {
						
						end_data.put(input_data[1], 1);
						
					}
					
					
				}			
								
			}
			
			buffer.close();
			
			Map<String, Integer> statistical_data = compute_census_analytics(birth_data, end_data); // Computing the statistics for each year
			
			// Generate years and the respective counts
			
			String[] all_years = new String[statistical_data.size()];
			
			int[] all_counts = new int[statistical_data.size()];
			
			int i = 0;
			
			int max_num = 0;
			
			String year = "";
			
			// Collecting the string and integer arrays for chart panel creation
			for(String key: statistical_data.keySet()){
				
				if(statistical_data.get(key) > max_num){
					
					max_num = statistical_data.get(key);
					
					year = key;
					
				}
				
				all_years[i] = key;
				
				all_counts[i] = statistical_data.get(key);
				
				i++;
				
			}
			
			// To frame GUI Output
			CensusAnalysis censusanalysis = new CensusAnalysis("Census", "Growth", all_years, all_counts);
			
			censusanalysis.pack( );        
			
		    RefineryUtilities.centerFrameOnScreen(censusanalysis);        
		    
		    censusanalysis.setVisible(true); 
		    
		    System.out.println("Year: "+year);
		    
		    System.out.println("Number of people Alive: "+max_num); // Output in command Line
			
		} catch (Exception e){
			// Print error trace
			e.printStackTrace();
			
		}		
		
		
	}
	
	// Method to compute yearly statistics to determine the count of people alive in a given year//
	private static TreeMap<String, Integer> compute_census_analytics(Map<String, Integer> birth_data, Map<String, Integer> end_data){
		
		TreeMap<String, Integer> yearly_statistics = new TreeMap<String, Integer>();
		
		int count = 0;
		
		int MIN_VALUE = 1900;
		
		int MAX_VALUE = 2000;		

				
		for (int i = MIN_VALUE; i <= MAX_VALUE; i++){
			
			if (birth_data.containsKey(String.valueOf(i))){
				
				count += birth_data.get(String.valueOf(i));
				
			}
			
			if (end_data.containsKey(String.valueOf(i))){
				
				count -= end_data.get(String.valueOf(i));
				
			}
			
			yearly_statistics.put(String.valueOf(i), count);
			
		}
		
		return yearly_statistics;
		
	}
	
	// Creating X and Y axis values
	private CategoryDataset createDataset( String[] all_years, int[] all_counts )
	   {
	            
	      final DefaultCategoryDataset dataset = new DefaultCategoryDataset( );  
	      
	      for(int i = 0; i < all_years.length; i++){
	    	  
	    	  dataset.addValue(all_counts[i], all_years[i], "Growth");
	    	  
	      }               

	      return dataset; 
	   }
	
}
