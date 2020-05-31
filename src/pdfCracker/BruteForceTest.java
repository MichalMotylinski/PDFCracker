package pdfCracker;

import java.util.Scanner;

public class BruteForceTest {
	// Static variables
	// Scanner variable used for user input
	private static Scanner input = new Scanner(System.in);

	public static void main(String[] args) {
		// Repeating sub menu with options
		String choice = "";
		do {
			System.out.print(
					"Please be aware that decoding time depends on couple variables like: password length, characters used, speed of users PC");
			System.out.println("\nEstimated times given below are based on testing done on student's PC");
			System.out.println("--TESTING FILES MENU--");
			System.out.println("1 - File1.pdf - 1 character password - Test took less than a second");
			System.out.println("2 - File2.pdf - 2 character password - Test took less than a second");
			System.out.println("3 - File3.pdf - 3 character password - Test took about 1 minute");
			System.out.println("4 - File4.pdf - 4 character password - Test took about 2 hours");
			System.out.println("5 - File5.pdf - 5 character password - Test will take about 7 days");
			System.out.println("6 - File6.pdf - 6 character password - Test will take more than 2 years");
			System.out.println("7 - File7.pdf - 7 character password - Test will take about 187 years");
			System.out.println("Q - Back to PDFCracker Menu");
			System.out.println("Pick: ");
			choice = input.next().toUpperCase();
			// Switch statement for menu
			switch (choice) {
			case "1": {
				fileChoice(choice);
				break;
			}
			case "2": {
				fileChoice(choice);
				break;
			}
			case "3": {
				fileChoice(choice);
				break;
			}
			case "4": {
				fileChoice(choice);
				break;
			}
			case "5": {
				fileChoice(choice);
				break;
			}
			case "6": {
				fileChoice(choice);
				break;
			}
			case "7": {
				fileChoice(choice);
				break;
			}
			}
		} while (!choice.equals("Q"));
	}
	private static void fileChoice(String choice) {
		String filePath = "test\\File" + choice + ".pdf";
		BruteForceDecrypt.startAttack(filePath);
	}
}