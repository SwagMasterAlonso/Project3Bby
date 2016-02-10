import static org.junit.Assert.*;

import org.junit.Test;

public class testFeatureExtraction {
	public int[][] createBoard (int[] stateData) {
		int[][] board = new int[6][7];
		int counterj, counteri;
		int counterk = 0;
		for(counterj = 0; counterj<7;counterj++){
			for(counteri = 5; counteri >=0;counteri--){
				board[counteri][counterj] = stateData[counterk];
				counterk++;
			}
		}
		return board;
	}
	@Test
	public void testHorizontal() {
		int i,j;
		int[] stateData = {1,0,0,0,0,1,1,1,0,0,0,0,2,1,2,0,0,0,1,1,1,0,0,0,2,1,2,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1};
		int[][] board = this.createBoard(stateData);

//		for (i = 0; i < 6; i++) {
//			System.out.println("\n");
//			for (j = 0; j < 7; j++) {
//				
//				System.out.print(" "+board[i][j]+" ");
//			}
//			
//		}
		assertEquals(2, xXFeatureExtractionXx.centerControl(board));
	}

}
