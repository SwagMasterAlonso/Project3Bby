import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;




/**
Alonso
 */
public class xXFeatureExtractionXx {
	static String fileName = "res/testSet.csv";

	public static void main(String[] args) throws IOException {


		String[] string = new String[1];
		string[0] = "0";

		getFeatures();






	}

	public static void getFeatures() throws IOException{
		String fileName2 = "res/testSetTemp.csv";

		BufferedReader br = null;
		String line = "";
		String delimiter = ",";
		String filePath = "res/testSet.csv";
		String filePath2 = "res/testSetTesterino.csv";

		int rows = 6;
		int columns = 7;
		int[][] board = new int[rows][columns];

		Boolean hasSkipped = true;
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
						for(counteri = rows-1; counteri >=0;counteri--){
							//	System.out.println("Value is: "+Integer.parseInt(stateData[counterk]));
							board[counteri][counterj] = Integer.parseInt(stateData[counterk]);
							//	System.out.println(board[counteri][counterj]);
							counterk++;
						}
					}
					counterk = 0;
					System.out.println("Board State iS: ");
					for(int i = 0; i < 6;i++){
						System.out.println("\n");
						for(int j = 0; j < 7;j++){

							System.out.print(board[i][j]);
						
						}
					}
					
					String[] existingData = line.split(delimiter);
					String[] newData = new String[existingData.length+1];
					newData = copyDat(newData,existingData);
					System.out.println(Arrays.toString(existingData));
					newData[newData.length-1] = Integer.toString(bottomLeftCornerControl(board));
					System.out.println(Arrays.toString(newData));
					saveData(fileName2,newData);
					File fileTestSet = new File(filePath);
					File file3 = new File(filePath2);					//filePath = testSet
					//fileName2 is temp					
				}
				
				
				
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
		File fileTestSet = new File(filePath);
		File file2TempSet = new File(fileName2);
		//file2TempSet.renameTo(fileTestSet);

		Files.deleteIfExists(fileTestSet.toPath());
		file2TempSet.renameTo(new File(filePath));
	}




	public static int bottomLeftCornerControl (int[][] board) {
		
		
		
		
		
		
		int player = 0;
		int posNumb = board[5][0];
		System.out.println("Pos Is "+posNumb);
	
		switch(posNumb){
		case 0:
			player = 0;
			System.out.println("Yo man we wrong");
			break;
		
		case 1:
			player = 1;
			break;
		case 2:
			player = 2;
			break;
		default:
			System.out.println("Inccorrect player invaded the board.");
			break;
		}

		return player;
	}
	public int bottomCenterCellControl (int[][] board) {
		int player = 0;

		switch (board[6][3]) {
		case 1:
			player = 1;
			break;
		case 2:
			player = 2;
			break;
		default:
			System.out.println("Inccorrect player invaded the board.");
			break;
		}

		return player;
	}
	public int centerControl (int[][] board) {
		int finalValue = 0;
		int max1 = 0, max2 = 0;
		int i,j;

		for (i = 6; i >= 0; i--) {
			for (j = 2; j < 5; j++) {
				if (board[i][j] == 1) {
					max1++;
				} else if (board[i][j] == 2) {
					max2++;
				}
			}
		}
		finalValue = max1 - max2;
		return finalValue;
	}

	public int disjointGroups (int[][] board) {
		boolean connected = false;
		int finalValue = 0;
		int i,j;

		for (i = 6; i >= 0; i--) {
			for (j = 0; j < 7; j++) {
				if (board[i][j] == 1 && !connected) {
					connected = true;
				} else if ((board[i][j] == 2 || board[i][j] == 0) && connected) {
					connected = false;
					finalValue++;
				}
			}
		}
		return finalValue;
	}


	/**Returns the number of diagonal connections given n and the designated player.
	 * Search for connections in one section of the board.*/
	int diagControl(int[][] board, int n) {
		//check diagonally y=-x+k
		int max1=0;
		int max2=0;
		int counter1 = 0, counter2 = 0;
		int height = 7;
		int width = 6;
		int upper_bound=height-1+width-1-(n-1);

		for(int k=n-1;k<=upper_bound;k++){
			max1=0;
			max2=0;
			int x,y;
			if(k<width)
				x=k;
			else
				x=width-1;
			y=-x+k;

			while(x>=0  && y<height){
				// System.out.println("k: "+k+", x: "+x+", y: "+y);
				if(board[height-1-y][x]==1){
					if(max2==n) {
						counter2++;
					}
					max1++;
					max2=0;
				}
				else if(board[height-1-y][x]==2){
					if(max1==n) {
						counter1++;
					}
					max1=0;
					max2++;

				}
				else{
					if(max1==n) {
						counter1++;
					} else if(max2==n) {
						counter2++;
					}
					max1=0;
					max2=0;
				}
				x--;
				y++;
			}
			if(max1==n) {
				counter1++;
			} else if(max2==n) {
				counter2++;
			}
		}
		return counter1 - counter2;
	}

	/**Returns the number of diagonal connections given n and the designated player.
	 * Search for connections in other section of the board.*/
	int diagCounter2(int[][] board, int n) {
		//check diagonally y=x-k
		int max1=0;
		int max2=0;
		int counter1 = 0, counter2 = 0;
		int width = 6;
		int height = 7;
		int upper_bound=width-1-(n-1);
		int  lower_bound=-(height-1-(n-1));
		// System.out.println("lower: "+lower_bound+", upper_bound: "+upper_bound);
		for(int k=lower_bound;k<=upper_bound;k++){
			max1=0;
			max2=0;
			int x,y;
			if(k>=0)
				x=k;
			else
				x=0;
			y=x-k;
			while(x>=0 && x<width && y<height){
				// System.out.println("k: "+k+", x: "+x+", y: "+y);
				if(board[height-1-y][x]==1){
					if(max2==n) {
						counter2++;
					}
					max1++;
					max2=0;
				}
				else if(board[height-1-y][x]==2){
					if(max1==n) {
						counter1++;
					}
					max1=0;
					max2++;

				}
				else{
					if(max1==n) {
						counter1++;
					} else if(max2==n) {
						counter2++;
					}
					max1=0;
					max2=0;
				}
				x++;
				y++;
			}
			if(max1==n) {
				counter1++;
			} else if(max2==n) {
				counter2++;
			}
		}	 //end for y=x-k
		return counter1 - counter2;
	}

	/**Counts the N horizontal connections given the designated player.
	 * Returns the counts.*/
	int horizCounter(int n, int[][] board) {
		int max1;
		int max2;
		int counter1 = 0, counter2 = 0;

		//check each row, horizontally
		for(int i=0;i<7;i++){
			max1=0;
			max2=0;
			for(int j=0;j<6;j++){
				if(board[i][j]==1){
					if(max2==n) {
						counter2++;
					}
					max1++;
					max2=0;
				}
				else if(board[i][j]==2){
					if(max1==n) {
						counter1++;
					}
					max1=0;
					max2++;
				} else {
					if(max1==n) {
						counter1++;
					} else if(max2==n) {
						counter2++;
					}

					max1=0;
					max2=0;
				}
			}
			if(max1==n) {
				counter1++;
			} else if(max2==n) {
				counter2++;
			}

		}


		return counter1 - counter2;
	}


	private static void saveData(String fileName,String[] data){

		try
		{
			FileWriter writer = new FileWriter(fileName,true);

			for(String s:data){
				System.out.println(s);
				writer.append(s);
				writer.append(',');
			}

			writer.append("\n");
			writer.flush();
			writer.close();
		}
		catch(IOException e)
		{
			e.printStackTrace();
		} 
	}

//	private static void appendToFile(String filePath,String data)
//	{
//		BufferedReader br = null;
//		String line = "";
//		String delimiter = ",";
//
//		try {
//
//			br = new BufferedReader(new FileReader(filePath));
//			int i = 0;
//			while ((line = br.readLine()) != null && line.length() > 0) {
//
//
//				// use comma as separator
//				//System.out.println("i = " + i);
//				String[] existingData = line.split(delimiter);
//				String[] newData = new String[existingData.length+1];
//				newData = copyDat(newData,existingData);
//				System.out.println(Arrays.toString(existingData));
//				newData[newData.length-1] = data;
//				System.out.println(Arrays.toString(newData));
//				saveData(fileName2,newData);
//			}
//
//		} 
//		catch (FileNotFoundException e) {e.printStackTrace();} 
//		catch (IOException e) {e.printStackTrace();} 
//		finally {
//			if (br != null) {
//				try {
//					br.close();
//				} catch (IOException e) {e.printStackTrace();}
//			}
//		}
//	}


	static String[] copyDat(String[] one, String[] two){
		for(int i = 0; i < two.length;i++){
			one[i] = two[i];
		}

		return one;
	}



}
