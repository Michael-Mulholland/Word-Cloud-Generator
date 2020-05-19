package ie.gmit.sw;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Scanner;

public class Menu {

	// declare variables
	String option;
	String filename;
	String wordCloudName;
	private boolean keepGoing = true;
	int maxWords;
	Scanner console = new Scanner(System.in);
	
	Parse fileParse = new Parse();
	
	public void show() throws IOException {
		while(keepGoing) {
			menu();
			option = console.next();
			handle(option);
		}
	}

	// menu
	public void menu() {
		System.out.println("\n == Word Cloud ==");
		System.out.println("Option 1: enter file name: ");
		System.out.println("Option 2: enter URL name: ");
		System.out.println("Option 3: to Quit");
		System.out.print("Users Choice: ");
	}

	public void handle(String option2) throws IOException {
		
		// parse a file
		if(option.equals("1")) {
			System.out.print("\nEnter maximum words to display: ");
			maxWords = console.nextInt();
			
			System.out.print("\nEnter the file name of the Word-Cloud to save: ");		
			wordCloudName = console.next();
			wordCloudName += ".png"; // add .png to the filename entered
			
			System.out.println("\nEnter file name");
			filename = console.next();
						
			//pass the filename or directory name to File object
			File filenameWords = new File(filename);
			File fileIgnoreWords = new File("ignoreWords.txt");

			InputStream inWords = new FileInputStream(filenameWords);
			InputStream ignoreWords = new FileInputStream(fileIgnoreWords);

			// pass the file, ignoreword, max words and word cloud image name to the Parse class
			fileParse.parse(inWords, ignoreWords, maxWords, wordCloudName);
		}
		// parse a url
		else if(option.equals("2")) {
			System.out.print("\nEnter maximum words to display: ");
			maxWords = console.nextInt();
			
			System.out.print("\nEnter the file name of the Word-Cloud to save: ");		
			wordCloudName = console.next();
			wordCloudName += ".png"; // add .png to the filename entered
			
			System.out.println("\nEnter URL name");
			filename = console.next();

			InputStream inWords = new URL(filename).openStream();
			
			//pass the filename or directory name to File object
	        File fileIgnoreWords = new File("./ignoreWords.txt");	        
			InputStream ignoreWords = new FileInputStream(fileIgnoreWords);

			// pass the file, ignoreword, max words and word cloud image name to the Parse class
			fileParse.parse(inWords, ignoreWords, maxWords, wordCloudName);
		}	
		// exit program
		else if(option.equals("3")) {
			keepGoing = false;
			System.out.println("\nQuiting Program.....");
		}		
	}
}
