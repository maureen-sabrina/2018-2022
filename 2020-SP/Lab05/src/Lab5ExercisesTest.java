/*
 * Maureen Lynch
 * Spring 2020
 */
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

class Lab5ExercisesTest {

	@Test
	void test() {
		
		// Part 1: Longest Run 
		ArrayList<Integer> randomNumList = new ArrayList<>();
		randomNumList.add(6);
		randomNumList.add(3);
		randomNumList.add(3);
		randomNumList.add(2);
		randomNumList.add(1);
		randomNumList.add(2);
		randomNumList.add(1);
		randomNumList.add(1);
		randomNumList.add(6);
		randomNumList.add(6);
		randomNumList.add(6);
		randomNumList.add(5);
		randomNumList.add(3);
		randomNumList.add(3);
		randomNumList.add(3);
		randomNumList.add(5);
		randomNumList.add(2);
		randomNumList.add(3);
		randomNumList.add(1);
		randomNumList.add(6);
		
		String output3 = Lab5Exercises.longestRun(randomNumList);
		assertEquals(output3, "6 3 3 2 1 2 1 1 (6 6 6) 5 3 3 3 5 2 3 1 6 ");
		
		randomNumList.remove(8);
		randomNumList.add(1);
		output3 = Lab5Exercises.longestRun(randomNumList);
		assertEquals(output3, "6 3 3 2 1 2 1 1 6 6 5 (3 3 3) 5 2 3 1 6 1 ");
		
		randomNumList.remove(12);
		randomNumList.add(1);
		output3 = Lab5Exercises.longestRun(randomNumList);
		assertEquals(output3, "6 (3 3) 2 1 2 1 1 6 6 5 3 3 5 2 3 1 6 1 1 ");
		
		
		// Part 2: Bulgarian Solitare
		ArrayList<Integer> output1 = Lab5Exercises.bulgarianSolitare();
		assertEquals(output1.size(), 9);
		assertEquals(output1.contains(1), true);
		assertEquals(output1.contains(2), true);
		assertEquals(output1.contains(3), true);
		assertEquals(output1.contains(4), true);
		assertEquals(output1.contains(5), true);
		assertEquals(output1.contains(6), true);
		assertEquals(output1.contains(7), true);
		assertEquals(output1.contains(8), true);
		assertEquals(output1.contains(9), true);
		
		// Part 3: Matrix Library
		boolean[][] array1 = new boolean[4][3];
		array1[0][0] = true;
		array1[0][1] = false;
		array1[0][2] = false;
		//
		array1[1][0] = false;
		array1[1][1] = true;
		array1[1][2] = true;
		//
		array1[2][0] = false;
		array1[2][1] = true;
		array1[2][2] = true;
		//
		array1[3][0] = true;
		array1[3][1] = false;
		array1[3][2] = false;
		
		Matrix example1 = new Matrix(array1);
		
		boolean[][] array2 = new boolean[3][4];
		array2[0][0] = true;
		array2[0][1] = false;
		array2[0][2] = false;
		array2[0][3] = true;
		//
		array2[1][0] = false;
		array2[1][1] = true;
		array2[1][2] = true;
		array2[1][3] = false;
		//
		array2[2][0] = false;
		array2[2][1] = true;
		array2[2][2] = true;
		array2[2][3] = false;
		
		Matrix example1Transposed = new Matrix(array2);
		
		boolean[][] array3 = new boolean[2][2];
		array3[0][0] = true;
		array3[0][1] = false;
		array3[1][0] = true;
		array3[1][1] = true;
		
		Matrix example2 = new Matrix(array3);
		
		boolean[][] array4 = new boolean[2][2];
		array4[0][0] = true;
		array4[0][1] = true;
		array4[1][0] = false;
		array4[1][1] = true;
		
		Matrix example2Transposed = new Matrix(array4);
		
		boolean[][] array5 = new boolean[1][1];
		
		array5[0][0] = true;
		
		Matrix example3 = new Matrix(array5);
		Matrix example3Transposed = new Matrix(array5);
	
		
			// toString() method
		String output = example1.toString();
		assertEquals(output, "[1, 0, 0\n "
							+ "0, 1, 1\n "
							+ "0, 1, 1\n "
							+ "1, 0, 0]");
		output = example2.toString();
		assertEquals(output, "[1, 0\n "
							+ "1, 1]");
		output = example3.toString();
		assertEquals(output, "[1]");
		
		
		
			// transposeMatrix() method
		output = example1.transposeMatrix().toString();
		assertEquals(output, example1Transposed.toString());
		output = example2.transposeMatrix().toString();
		assertEquals(output, example2Transposed.toString());
		output = example3.transposeMatrix().toString();
		assertEquals(output, example3Transposed.toString());
		
		
			// isSquareMatrix() method
		boolean output2 = example1.isSquareMatrix();
		assertEquals(output2, false);
		output2 = example2.isSquareMatrix();
		assertEquals(output2, true);
		output2 = example3.isSquareMatrix();
		assertEquals(output2, true);
		
		
			// rotateClockwise() method
		output = example1.rotateClockwise().toString();
		assertEquals(output, "[1, 0, 0, 1\n "
							+ "0, 1, 1, 0\n "
							+ "0, 1, 1, 0]" );
		output = example2.rotateClockwise().toString();
		assertEquals(output, "[1, 1\n "
							+ "1, 0]");
		output = example3.rotateClockwise().toString();
		assertEquals(output, "[1]");
			
		
		
		// rotateCounterClockwise() method
		output = example1.rotateCounterClockwise().toString();
		assertEquals(output, "[0, 1, 1, 0\n "
							+ "0, 1, 1, 0\n "
							+ "1, 0, 0, 1]" );
		output = example2.rotateCounterClockwise().toString();
		assertEquals(output, "[0, 1\n "
							+ "1, 1]");
		output = example3.rotateCounterClockwise().toString();
		assertEquals(output, "[1]");

		
			// percentageTrue() method
		output = example1.percentageTrue();
		assertEquals(output, "50%");
		output = example2.percentageTrue();
		assertEquals(output, "75%");
		output = example3.percentageTrue();
		assertEquals(output, "100%");
		
		
			// isEqual() method
		output2 = example1.isEqual(example1);
		assertEquals(output2, true);
		output2 = example1.isEqual(example2);
		assertEquals(output2, false);
		output2 = example2.isEqual(example2);
		assertEquals(output2, true);
		
	}

}

















