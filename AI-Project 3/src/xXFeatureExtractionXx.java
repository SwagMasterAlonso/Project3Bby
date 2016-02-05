import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;


/**
Alonso
 */
public class xXFeatureExtractionXx {

	public static void main(String[] args) {

		getFeatures();





	}

	public static void getFeatures(){


		BufferedReader br = null;
		String line = "";
		String delimiter = ",";
		String filePath = "res/testSet.csv";
		int rows = 6;
		int columns = 7;
		int[][] board = new int[rows][columns];
		board[0][0] = 6;
		board[5][0] = 1;
		board[5][6] = 37;
		board[0][6] = 42;


		//{{6,12,18,24,30,36,42},{5,11,17,23,29,35,41},{4,10,16,22,28,34,40},{3,9,15,21,27,33,39},{2,8,14,20,26,32,38},{1,7,13,19,25,31,37}};
		Boolean hasSkipped = false;
		int counteri = 0;
		int counterj = 0;
		int counterk =0;
		File p = new File("res/testSet.csv");

		try {

			br = new BufferedReader(new FileReader(filePath));
			while ((line = br.readLine()) != null) {

				// use comma as separator
				if(hasSkipped){
					String[] stateData = line.split(delimiter);
					for(counterj = 0; counterj<columns;counterj++){
						System.out.println("\n");
						for(counteri = rows-1; counteri >=0;counteri--){
							System.out.print(board[counteri][counterj]);
							//	System.out.println("Value is: "+Integer.parseInt(stateData[counterk]));
							board[counteri][counterj] = Integer.parseInt(stateData[counterk]);
							//	System.out.println(board[counteri][counterj]);
							System.out.println("Setting board pos at: "+counteri+" "+counterj+" with val " +Integer.parseInt(stateData[counterk]));
							counterk++;
						}
					}		


				} else {
					hasSkipped = true;
				}


				System.out.println("");
				System.out.println("BOARD STATE IS");
				System.out.println("");

				for(counteri = 0; counteri <rows;counteri++){
					System.out.println("\n");


					for(counterj = 0; counterj<columns;counterj++){

						System.out.print(board[counteri][counterj] +" ");
					}
				}	
			}



			System.out.println(board[3][2]);
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
