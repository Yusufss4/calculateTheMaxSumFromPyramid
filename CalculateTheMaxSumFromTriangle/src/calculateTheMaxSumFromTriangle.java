import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;
/* Algorithm for finding the maximum sum of the proper path with the rules below in orthogonal triangle.
   1 - You will start from the top and move downwards to an adjacent number as in below.
   2 - You are only allowed to walk downwards and diagonally.
   3 - You can only walk over NON PRIME NUMBERS.
   4 - You have to reach at the end of the pyramid as much as possible.
   5 - You have to treat the input as pyramid..
*/
public class calculateTheMaxSumFromTriangle {

	public static void main(String[] args) {

		
		final String inputString = "215\r\n"
				+ "193 124\r\n"
				+ "117 237 442\r\n"
				+ "218 935 347 235\r\n"
				+ "320 804 522 417 345\r\n"
				+ "229 601 723 835 133 124\r\n"
				+ "248 202 277 433 207 263 257\r\n"
				+ "359 464 504 528 516 716 871 182\r\n"
				+ "461 441 426 656 863 560 380 171 923\r\n"
				+ "381 348 573 533 447 632 387 176 975 449\r\n"
				+ "223 711 445 645 245 543 931 532 937 541 444\r\n"
				+ "330 131 333 928 377 733 017 778 839 168 197 197\r\n"
				+ "131 171 522 137 217 224 291 413 528 520 227 229 928\r\n"
				+ "223 626 034 683 839 053 627 310 713 999 629 817 410 121\r\n"
				+ "924 622 911 233 325 139 721 218 253 223 107 233 230 124 233";
		
		//Read text from constant input
		int[][] matrixFormOfTriangle = convertStringToTheIntMatrix(inputString);
		printMatrix(matrixFormOfTriangle);

		matrixFormOfTriangle = removePrimeNumbersFromMatrix(matrixFormOfTriangle);
		printMatrix(matrixFormOfTriangle);

		System.out.println("Maximum sum of the proper path for the given input is:");
		System.out.println(traverseTheMatrixAndModifyIt(matrixFormOfTriangle));

		//Read text from file
		Scanner sc = new Scanner(System.in);
		try {

			System.out.print("Enter the file path that contains triangle Ex:C:\\Users\\Yusuf\\Desktop\\filename.txt: ");
			String filePathFromUser = sc.nextLine();

			byte[] contentInFile = Files.readAllBytes(Paths.get(filePathFromUser));
			System.out.println("Input in the file is: \n");
			String stringInFile = new String(contentInFile); // byte to string
			System.out.println(stringInFile);

			int[][] matrixFormOfTriangleInputFromText = convertStringToTheIntMatrix(stringInFile);
			// printMatrix(matrixFormOfTriangleInputFromText);

			matrixFormOfTriangleInputFromText = removePrimeNumbersFromMatrix(matrixFormOfTriangleInputFromText);
			// printMatrix(matrixFormOfTriangleInputFromText);

			System.out.println("Maximum sum of the proper path for the given input is:");
			System.out.println(traverseTheMatrixAndModifyIt(matrixFormOfTriangleInputFromText));

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			sc.close();
		}

	}

	public static int[][] convertStringToTheIntMatrix(String inputTriangle) {

		String[] linesInTriange = inputTriangle.split("\\r?\\n");

		int[][] matrixFormOfTriangle = new int[linesInTriange.length][linesInTriange.length];

		for (var row = 0; row < linesInTriange.length; row++) {
			String[] lineInRowFormatString = linesInTriange[row].split(" "); // Divide one line into smaller parts
			int[] lineInRowFormatInt = new int[lineInRowFormatString.length];

			for (int i = 0; i < lineInRowFormatString.length; i++) {

				try {
					// Parse string number to integer number.
					lineInRowFormatInt[i] = Integer.parseInt(lineInRowFormatString[i]);

				} catch (NumberFormatException ex) {
					ex.printStackTrace();
				}
			}
			for (var column = 0; column < lineInRowFormatInt.length; column++) {
				matrixFormOfTriangle[row][column] = lineInRowFormatInt[column];
			}

		}

		return matrixFormOfTriangle;
	}

	public static int[][] removePrimeNumbersFromMatrix(int[][] matrixFormOfTriangle) {

		for (int i = 0; i < matrixFormOfTriangle.length; i++) {
			for (int j = 0; j < matrixFormOfTriangle[i].length; j++) {
				if (matrixFormOfTriangle[i][j] == 0) {
					continue;
				} else if (isPrime(matrixFormOfTriangle[i][j])) // replacing prime numbers with -1 in matrix
				{
					matrixFormOfTriangle[i][j] = -1;
				}
			}
		}

		return matrixFormOfTriangle;
	}

	public static boolean isPrime(int n) {
		// Corner cases
		if (n <= 1)
			return false;
		if (n <= 3)
			return true;

		if (n % 2 == 0 || n % 3 == 0)
			return false;

		for (int i = 5; i * i <= n; i = i + 6)
			if (n % i == 0 || n % (i + 2) == 0)
				return false;

		return true;
	}

	public static int traverseTheMatrixAndModifyIt(int[][] matrix) {

		int m = matrix.length;
		m = m - 1;
		for (int i = m - 1; i >= 0; i--) {
			for (int j = 0; j <= i; j++) {
				// for each element, check both
				// elements just below the number
				// and below right to the number
				// add the maximum of them to it
				if (matrix[i][j] == -1) {
					// it is prime
					continue;
				} else if (matrix[i + 1][j] == -1 && matrix[i + 1][j + 1] == -1) {
					continue; // Controls for if the number is prime or not.
				} else if (matrix[i + 1][j] == -1 && matrix[i + 1][j + 1] != -1) {
					matrix[i][j] += matrix[i + 1][j + 1];
				} else if (matrix[i + 1][j] != -1 && matrix[i + 1][j + 1] == -1) {
					matrix[i][j] += matrix[i + 1][j];
				}

				else if (matrix[i + 1][j] > matrix[i + 1][j + 1])
					matrix[i][j] += matrix[i + 1][j];
				else
					matrix[i][j] += matrix[i + 1][j + 1];

			}

		}

		// return the top element
		// which stores the maximum sum

		return matrix[0][0];
	}

	public static void printMatrix(int[][] matrix) {
		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix[i].length; j++) {
				System.out.print(matrix[i][j] + " ");
			}
			System.out.println();
		}
	}

}
