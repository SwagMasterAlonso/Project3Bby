import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;




/**
Alonso
 */
public class xXFeatureExtractionXx {
	static String fileName = "res/trainDataSet.csv";
	static String filePath = "res/trainDataSet.csv";
	static String fileName2 = "res/testSetTemp.csv";

	public static void main(String[] args) throws IOException {


		String[] string = new String[1];
		string[0] = "0";



		getAllFeatures();



	}



	public static void getAllFeatures() throws IOException{

		getFeatures();
		File fileTestSet = new File(fileName);
		File file2TempSet = new File(fileName2);
		Files.deleteIfExists(fileTestSet.toPath());
		file2TempSet.renameTo(new File(fileName));


	}



	public static void getFeatures() throws IOException{
		String fileName2 = "res/testSetTemp.csv";

		BufferedReader br = null;
		String line = "";
		String delimiter = ",";

		int rows = 6;
		int columns = 7;
		int[][] board = new int[rows][columns];

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
						for(counteri = rows-1; counteri >=0;counteri--){
							board[counteri][counterj] = Integer.parseInt(stateData[counterk]);
							counterk++;
						}
					}
					counterk = 0;


					String[] existingData = line.split(delimiter);
					String[] newData = new String[existingData.length+6];

					newData = copyDat(newData,existingData);
					newData[newData.length-6] = Integer.toString(bottomLeftCornerControl(board));
					newData[newData.length-5] = Integer.toString(bottomCenterCellControl(board));
					newData[newData.length-4] = Integer.toString(centerControl(board));
					newData[newData.length-3] = Integer.toString(disjointGroups(board));
					newData[newData.length-2] = Integer.toString(diagControl(board, 3)+diagCounter2(board,3)+diagControl(board, 2)+diagCounter2(board,2));
					newData[newData.length-1] = Integer.toString(horizCounter(3,board)+horizCounter(2, board));

					saveData(fileName2,newData);





				} else {
					try
					{
						FileWriter writer = new FileWriter(fileName2);
						String[] headers = {"f1","f2","f3","f4","f5","f6","f7","f8","f9","f10","f11","f12","f13","f14","f15","f16","f17","f18","f19","f20","f21v","f22","f23","f24","f25","f26","f27","f28","f29","f30","f31","f32","f33","f34","f35","f36","f37","f38","f39","f40","f41","f42","winner","fLC","fBCC","fcc","fdg","fdc","fhc"};

						for(String s: headers){
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
					hasSkipped = true;
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

	}







	public static int disjointGroups (int[][] board) {
		boolean connected = false;
		int finalValue = 0;
		int i,j;

		for (i = 5; i >= 0; i--) {
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

	public static int bottomLeftCornerControl (int[][] board) {
		int player = 0;
		int posNumb = board[5][0];

		switch(posNumb){
		case 0:
			player = 0;
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
	public static int bottomCenterCellControl (int[][] board) {
		int player = 0;

		switch (board[5][3]) {
		case 0:
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
	public static int centerControl (int[][] board) {
		int finalValue = 0;
		int max1 = 0, max2 = 0;
		int i,j;

		for (i = 5; i >= 0; i--) {
			for (j = 2; j < 5; j++) {
				if (board[i][j] == 1) {
					max1++;
				} else if (board[i][j] == 2) {
					max2++;
				}
			}
		}
		
		finalValue = max1-max2;
		System.out.println(finalValue);
		return finalValue;
	}




	/**Returns the number of diagonal connections given n and the designated player.
	 * Search for connections in other section of the board.*/


	/**Counts the N horizontal connections given the designated player.
	 * Returns the counts.*/



	/**Returns the number of diagonal connections given n and the designated player.
	 * Search for connections in one section of the board.*/
	static int diagControl(int[][] board, int n) {
		//check diagonally y=-x+k
		int max1=0;
		int max2=0;
		int counter1 = 0, counter2 = 0;
		int height = 6;
		int width = 7;
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
	static int diagCounter2(int[][] board, int n) {
		//check diagonally y=x-k
		int max1=0;
		int max2=0;
		int counter1 = 0, counter2 = 0;
		int width = 7;
		int height = 6;
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
	static int horizCounter(int n, int[][] board) {
		int max1;
		int max2;
		int counter1 = 0, counter2 = 0;

		//check each row, horizontally
		for(int i=0;i<6;i++){
			max1=0;
			max2=0;
			for(int j=0;j<7;j++){
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





	static String[] copyDat(String[] one, String[] two){
		for(int i = 0; i < two.length;i++){
			one[i] = two[i];
		}

		return one;
	}



}
