package pdfCracker;

import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import org.pdfbox.exceptions.CryptographyException;
import org.pdfbox.exceptions.InvalidPasswordException;
import org.pdfbox.pdmodel.PDDocument;

public class BruteForceDecrypt {
	// Static variables
	// Scanner variable used for user input
	private static Scanner input = new Scanner(System.in);
	// Maximum length of the password
	private static final int passwordMaxLength = 20;
	// Minimum length of the password
	private static final int passwordMinLength = 1;
	// Array with characters to use in Brute Force Algorithm.
	private static char dictionary[] = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p',
			'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K',
			'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', '0', '1', '2', '3', '4', '5',
			'6', '7', '8', '9', ' ', '!', '"', '#', '$', '%', '&', '\'', '(', ')', '*', '+', ',', '-', '.', '/', ':',
			';', '<', '=', '>', '?', '@', '[', '\\', ']', '^', '_', '`', '{', '|', '}', '¬' };

	public static void main(String[] args) {
		// User needs to define path to the file that will be decrypted
		System.out.println(
				"Please provide a path to password protected PDF file in an appriopriate format i.e. C:\\Users\\User\\Documents\\File.pdf");
		System.out.println("or press 'q' to quit to main menu");
		String filePath = input.next();
		// if statement allowing to go back to main menu instead of typing in
		// file directory
		if (filePath.equals("q") || (filePath.equals("Q"))) {
		} else {
			startAttack(filePath);
		}
	}
	public static void startAttack(String filePath) {
			// Timer variable
			long startTime = System.currentTimeMillis();
			PDDocument file = null;
			boolean found = false;
			try {
				// Reading pdf file from specified path
				file = PDDocument.load(filePath);
				// Program will loop from minimum length to maximum which is
				// from 1
				// to 20. If password is longer than 20 characters then program
				// will
				// not find it
				for (int i = passwordMinLength; i <= passwordMaxLength; i++) {
					try {
						startBruteForce(i, file);
						// If the correct password is found program stops and
						// prints out an appropriate message
					} catch (successInterrupter password) {
						found = true;
						System.out.println("SUCCESS. Passwod is: " + password.getMessage());
						break;
					} catch (Exception except) {
						except.printStackTrace();
					}
				}
				// In case that password was not found a proper message will be
				// printed
				if (!found) {
					System.out.println("Failure - Password not found");
				}
				file.close();
				// After closing file the time is being processed on proper
				// value
				long timeTaken = System.currentTimeMillis() - startTime;
				long days = TimeUnit.MILLISECONDS.toDays(timeTaken);
				timeTaken -= TimeUnit.DAYS.toMillis(days);
				long hours = TimeUnit.MILLISECONDS.toHours(timeTaken);
				timeTaken -= TimeUnit.HOURS.toMillis(hours);
				long minutes = TimeUnit.MILLISECONDS.toMinutes(timeTaken);
				timeTaken -= TimeUnit.MINUTES.toMillis(minutes);
				long seconds = TimeUnit.MILLISECONDS.toSeconds(timeTaken);
				long millisec = TimeUnit.MILLISECONDS.toMillis(timeTaken);
				Float milliFloat = Float.valueOf(millisec);
				milliFloat = milliFloat / 1000;
				String milliString = String.valueOf(milliFloat).replaceAll(".*\\.", "").replaceFirst("^0+(?!$)", "");
				StringBuilder time = new StringBuilder(64);
				time.append(days);
				if (days == 1) {
					time.append(" Day ");
				} else {
					time.append(" Days ");
				}
				time.append(hours);
				if (hours == 1) {
					time.append(" Hour ");
				} else {
					time.append(" Hours ");
				}
				time.append(minutes);
				if (minutes == 1) {
					time.append(" Minute ");
				} else {
					time.append(" Minutes ");
				}
				time.append(seconds);
				if (seconds == 1) {
					time.append(" Second ");
				} else {
					time.append(" Seconds ");
				}
				time.append(milliString);
				if (milliString.equals(1)) {
					time.append(" Millisecond ");
				} else {
					time.append(" Milliseconds");
				}
				// In the end total time is printed out
				System.out.println("TOTAL TIME TAKEN - " + time);
				// Error message in case if program will not find file in the
				// given path
			} catch (IOException except) {
				System.out.println("PDF file not found at - " + filePath);
			}
		}

	// Decoding method
	public static void startBruteForce(int length, PDDocument document) {
		// Loading StringBuffer method length variable
		StringBuffer password = new StringBuffer(length);
		char currentChar = dictionary[0];
		// Comparing password character with current dictionary character (First
		// procedure)
		for (int i = 1; i <= length; i++) {
			password.append(currentChar);
		}
		changeCharacters(0, password, length, document);
	}

	// Method changing position of characters in algorithm (Next procedure)
	private static StringBuffer changeCharacters(int position, StringBuffer password, int length, PDDocument document) {
		for (int i = 0; i <= dictionary.length - 1; i++) {
			password.setCharAt(position, dictionary[i]);
			// if statements validating whether the password was found
			if (position == length - 1) {
				if (validation(password.toString(), document)) {
					throw new BruteForceDecrypt.successInterrupter(password.toString());
				}
				// If password was not found algorithm will move to another
				// position
			} else {
				changeCharacters(position + 1, password, length, document);
			}
		}
		return password;
	}

	// Validation method checking if password was found (Valid procedure)
	public static boolean validation(String password, PDDocument document) {
		System.out.println(password);
		// Program will read a document from the file system, decrypt it and and
		// then write the result
		try {
			document.decrypt(password);
			return true;
		} catch (IOException except) {
			throw new RuntimeException(except);
			// Exception indicating that something has gone wrong during
			// cryptography operation
		} catch (CryptographyException except) {
			return false;
			// Indicates that an invalid password was supplied.
		} catch (InvalidPasswordException except) {
			return false;
		}
	}

	// Interrupting method run when password was found (Output procedure)
	@SuppressWarnings("serial")
	private static class successInterrupter extends RuntimeException {
		public successInterrupter(String message) {
			super(message);
		}
	}
}