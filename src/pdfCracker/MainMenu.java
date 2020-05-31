package pdfCracker;

import java.util.Scanner;

public class MainMenu {
	// Static variable used for user input
	private static Scanner input = new Scanner(System.in);

	public static void main(String[] args) {
		// Repeating main menu with options
		String choice = "";
		do {
			System.out.println("--MAIN MENU--");
			System.out.println("1 - Chose a PDF file for decryption");
			System.out.println("2 - Attack testing PDF file");
			System.out.println("Q - Quit PDFCracker Menu");
			System.out.println("Pick: ");
			choice = input.next().toUpperCase();
			// Switch statement for menu
			switch (choice) {
			case "1": {
				BruteForceDecrypt.main(args);
				break;
			}
			case "2": {
				BruteForceTest.main(args);
				break;
			}
			}
		} while (!choice.equals("Q"));
		System.out.println("GOODBYE");
	}
}