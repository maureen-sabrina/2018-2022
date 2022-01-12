/*
 * Maureen Lynch
 * Spring 2020
 */
public class Matrix {
	
	private boolean[][] matrix; 
	private final int NUMROW; 
	private final int NUMCOL; 
	 
	//
	public Matrix(boolean[][] m) { 
		this.NUMROW = m.length;
		this.NUMCOL = m[0].length;
		this.matrix = m;
	} 
	
	//
	public String toString() { 
		String endString = "";
		for(int i=0; i<NUMROW; i++) {
			String currentString = "";
			for(int j=0; j<NUMCOL; j++) {
				if(matrix[i][j] == true) {
					currentString = currentString + "1, ";
				}
				else {
					currentString = currentString + "0, ";
				}
			}
			endString = endString 
						+ currentString.substring(0, currentString.length()-2) 
						+ "\n ";
		}
		endString = "[" + endString.substring(0, endString.length()-2) + "]";
		return endString;
	} 
	
	//
	public Matrix transposeMatrix() { 
		Matrix transposedMatrix = new Matrix(new boolean[NUMCOL][NUMROW]);
		for(int i=0; i<NUMCOL; i++) {
			for(int j=0; j<NUMROW; j++) {
				transposedMatrix.matrix[i][j] = matrix[j][i];
			}
		}
		return transposedMatrix;
	} 
	
	//
	public boolean isSquareMatrix() {
		if(NUMROW == NUMCOL) {
			return true;
		}
		else {
			return false;
		}
	} 
	
	//
	public Matrix rotateClockwise() { 
		Matrix rotatedClockwise = new Matrix(new boolean[NUMCOL][NUMROW]);
		for(int i=0; i<NUMCOL; i++) {
			int index = 0;
			for(int j=NUMROW-1; j>=0; j--) {
				rotatedClockwise.matrix[i][index++] = matrix[j][i];
			}
			for(int m=0; m<NUMCOL; m++) {
			}
		}
		return rotatedClockwise;
	}  
	
	//
	public Matrix rotateCounterClockwise() {
		Matrix rotatedCounter = new Matrix(new boolean[NUMCOL][NUMROW]);
		for(int i=0; i<NUMROW; i++) {
			int index = 0;
			for(int j=NUMCOL-1; j>=0; j--) {
				rotatedCounter.matrix[index++][i] = matrix[i][j];
			}
		}
		return rotatedCounter;
	}  
	
	//
	public String percentageTrue() { 
		double numberTrue = 0;
		for (int i=0; i<NUMROW; i++) {
			for(int j=0; j<NUMCOL; j++) {
				if(matrix[i][j] == true) {
					numberTrue++;
				}
			}
		}
		double percentage = 100*(numberTrue/(NUMROW*NUMCOL));
		return (int)percentage + "%";
	} 
	
	//
	public boolean isEqual (Matrix m) {
		boolean equality = true;
		if(m.NUMROW == NUMROW && m.NUMCOL == NUMCOL) {
			for(int i=0; i<NUMROW; i++) {
				for(int j=0; j<NUMCOL; j++) {
					if(!(m.matrix[i][j] == matrix[i][j])) {
						equality = false;
					}
				}
			}
		}
		else {equality = false;}
		return equality;
	}
}














