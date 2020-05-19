package ie.gmit.sw;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

public class Parse {

	private Integer frequency; // Integer can be null, int can't

	public void parse(InputStream inWords, InputStream inIgnoreWords, int maxWords, String wordCloudName) throws IOException {
		
		WordCloudImage image = new WordCloudImage();
		Queue<WordFrequency> wordQue = new PriorityQueue<>();
		
		double startTime = System.currentTimeMillis();
		
		// Add each word into a Set - set don't have duplicates
		// HashMap/HashSet tables - average O(1) for insertion.
		Map<String, Integer> map = new HashMap<>();
		Set<String> ignoreWordsSet = new HashSet<>();
		
		BufferedReader brInWords = new BufferedReader(new InputStreamReader(inWords));
		BufferedReader brIgnoreInWords = new BufferedReader(new InputStreamReader(inIgnoreWords));
				
		// reading ignore words
		String nextIgnore = null;
		while((nextIgnore = brIgnoreInWords.readLine()) != null) {		
			
			if (nextIgnore.isEmpty()) {
				continue;
			}
			
			// Adding to a HashSet is O(1)
			ignoreWordsSet.add(nextIgnore.toLowerCase());
			
		}// while loop - inFileWords
		
		// read in words 
		String next = null;
		
		while((next = brInWords.readLine()) != null) {	// O(n^2)

			// add words to an array
			String [] words = next.split(" ");
				
			for(String word : words) {				

				word = word.toLowerCase().replaceAll("[^A-Za-z0-9 ]", "");
				
				// searching a HashSet is O(1).
				if(!ignoreWordsSet.contains(word) && (!word.equals(""))) { // add words that are not in the ignorewords file	
					frequency = map.get(word);
						
					// if the word is in the Map add a 1 to it
					if(frequency == null) {
						map.put(word, 1);
					}
					else
					{ 
						// word is put into the Map for the 1st time, set the frequency to 1
						map.put(word, frequency + 1);
					}
				}	
			}
				
		}// while loop - inFileWords

		// add words to a PriorityQueue. 
		Set<String> keys = map.keySet(); // Add each key contained in the Map to a Set. O(1)
		
		// go over each of the keys in the Set and add it to the PriorityQueue. 
		// O(n log n)
		for(String key : keys) {
			wordQue.offer(new WordFrequency(key, map.get(key)));  // Offering to a PriorityQueue - time complexity of O(log n) 
		}
		
		System.out.println(map.toString());
		
		image.placeWords(wordQue, wordCloudName, maxWords);
				
		double stopTime = ((System.currentTimeMillis() - startTime) / 1000);
		
		System.out.print("Running Time: " + stopTime);
		
		brInWords.close();
		brIgnoreInWords.close();
	}
}
