import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import AStar.Node;

/**
Alonso
*/
public class xXFeatureExtractionXx {

    public static void main(String[] args) {
	
	
	
	
	
	
	
    }
    
    public static void getFeatures(){
		BufferedReader br = null;
		String line = "";
		String delimiter = ",";
		String filePath;
		
		try {

			br = new BufferedReader(new FileReader(filePath));
			while ((line = br.readLine()) != null) {

				// use comma as separator
				String[] stateData = line.split(delimiter);
				
				
			}

		} 
		catch (FileNotFoundException e) {e.printStackTrace();} 
		catch (IOException e) {e.printStackTrace();} 
		finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {e.printStackTrace();}
			}
		}
    }
    
    
    
    
	
	
	
}
