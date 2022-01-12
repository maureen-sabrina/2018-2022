/*
 * Maureen Lynch
 * Spring 2020
 */

public class TestMatrix {
	
	public static void main(String[] args) {
		boolean[][] array1 = new boolean[4][3];
		array1[0][0] = true;
		array1[0][1] = false;
		array1[0][2] = false;
		array1[1][0] = false;
		array1[1][1] = true;
		array1[1][2] = true;
		array1[2][0] = false;
		array1[2][1] = true;
		array1[2][2] = true;
		array1[3][0] = true;
		array1[3][1] = false;
		array1[3][2] = false;
		
		Matrix example1 = new Matrix(array1);
		
		boolean[][] array2 = new boolean[1][1];
		array2[0][0] = false;
		
		Matrix example2 = new Matrix(array2);
		
		System.out.println(example1.toString());
		System.out.println();
		System.out.println(example1.transposeMatrix().toString());
		System.out.println();
		System.out.println(example1.isSquareMatrix());
		System.out.println();
		System.out.println(example2.toString());
		System.out.println();
		System.out.println(example2.transposeMatrix().toString());
		System.out.println();
		System.out.println(example2.isSquareMatrix());
		System.out.println();
		System.out.println(example1.toString());
		System.out.println();
		System.out.println(example1.rotateClockwise().toString());
		System.out.println("------------------------------");
		System.out.println(example1.toString());
		System.out.println("counter clockwise example1 below >>");
		System.out.println(example1.rotateCounterClockwise().toString());
		System.out.println(example1.percentageTrue());
		System.out.println(example1.isEqual(example1));
		System.out.println(example1.isEqual(example2));
		System.out.println(example2.percentageTrue());
		System.out.println(example2);
		System.out.println("------------------------------");
		
		
		boolean[][] array3 = new boolean[2][2];
		array3[0][0] = true;
		array3[0][1] = false;
		array3[1][0] = true;
		array3[1][1] = true;
		
		Matrix example3 = new Matrix(array3);
		
		System.out.println(example3.toString());
		System.out.println(example3.rotateClockwise().toString());
		System.out.println(example3.rotateCounterClockwise().toString());
	
	}
}
