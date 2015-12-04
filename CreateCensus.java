import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
public class CreateCensus {

	public static void main(String[] args) throws FileNotFoundException, IOException {
		// TODO Auto-generated method stub
		BufferedWriter writer = null; // BufferedWriter Object creation
		
		String string = "census.txt"; // File name
		
		String filedirectory = new File("").getAbsolutePath();	// Relative file path
		
		File file = new File(filedirectory + "\\" + string	); // File creation on a particular file path
		
		writer = new BufferedWriter(new FileWriter(file)); // Creating an instance of the bufferedwriter to allow file manipulation
				
		int MIN_VALUE = 1900; // Setting the initial year for the census
		
		int MAX_VALUE = 2000; // Setting the last year of the census
		
		
		// Generating 1 million census entries
		for (int i = 0; i < 1000000; i++){
			
			int birthyear = 0;
			int endyear = 0;		
			
			birthyear = MIN_VALUE + (int)(Math.random() * (MAX_VALUE - MIN_VALUE)); // Random Number Generation for birthyear
			
			endyear = MIN_VALUE + (int)(Math.random() * (MAX_VALUE - MIN_VALUE)); // Random Number Generation for endyear
			
			while(endyear < birthyear ||
					endyear > MAX_VALUE){ // Endyear must be greater than Birthyear and should be less than the MAX_VALUE
				
				endyear = MIN_VALUE + (int)(Math.random() * (MAX_VALUE - MIN_VALUE));
				
			}		
			
			
			writer.write(birthyear +":"+endyear); // Writing the data to text file
			
			writer.newLine(); // Creating a new line to add the next entry on to the file
			
		}
		
		writer.close(); // Closing the Buffer
		
		System.out.println("File Entry Completed");
		
	}

}
