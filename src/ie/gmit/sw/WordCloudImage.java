package ie.gmit.sw;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.Queue;
import java.util.concurrent.ThreadLocalRandom;
import javax.imageio.ImageIO;

public class WordCloudImage {
	
	private static final int HEIGHT = 300;
	private static final int WIDTH = 600;
	private static final int MAX_FONT_SIZE = 80;
	private static final int MIN_FONT_SIZE = 11;
	
	private static int[] fontStyle = {Font.PLAIN, Font.BOLD, Font.ITALIC};
	private static String[] fontFamily = {Font.SERIF, Font.SANS_SERIF};
	private static Color[] fontColor = {Color.red, Color.green, Color.blue, Color.black};
	
	public void placeWords(Queue<WordFrequency> wordQue, String wordCloudName, int numOfWords) throws IOException {

		int j, counter = 0, max = MAX_FONT_SIZE, min = MIN_FONT_SIZE;
		
		 // Calling iterator() is constant time.
		Iterator<WordFrequency> i = wordQue.iterator(); 
		
		BufferedImage image = new BufferedImage(800, 400, BufferedImage.TYPE_4BYTE_ABGR); 
		Graphics graphics = image.getGraphics();		

		// Background Colour
/*		graphics.setColor(Color.decode("#3b3539"));
		graphics.fillRect(0,0,800,400);*/
		
		WordFrequency wordFreq = new WordFrequency();
		
		// iterating over the elements in i is O(n)
		while (i.hasNext() && counter < numOfWords) { 
			j = i.next().getFrequency();
			if (j > max) max = j;
			if (j < min) min = j;
			
			counter++;
		}
		
		counter = 0;
		
		System.out.println("DEBUD - MAX: " + max);
		System.out.println("DEBUD - MIN: " + min);
		
		while ((!wordQue.isEmpty()) && counter < numOfWords) { // isEmpty() - constant time O(1).
			wordFreq = wordQue.poll(); // Calling poll() returns the item with the most occurrences (freq) in the queue. O(log n)
					
			// get a random font and colour using randomNumber()
			graphics.setFont(new Font(fontFamily[randomNumber(0, fontFamily.length - 1)], fontStyle[randomNumber(0, fontStyle.length - 1)], (int) scale(wordFreq.getFrequency(), min, max)));
			graphics.setColor(fontColor[randomNumber(0, fontColor.length - 1)]);
			graphics.drawString(wordFreq.getWord(), randomNumber(50, WIDTH-50), randomNumber(50, HEIGHT-50));
			counter++;	
		}
		
		ImageIO.write(image, "png", new File(wordCloudName));
		graphics.dispose();
	}
	
	// Generate a random number between a given range
	public int randomNumber(int min, int max) {
		return ThreadLocalRandom.current().nextInt(min, max + 1);
	}
	
	// Scale a word going by its frequency
	public int scale(double inFreq, double inMin, double inMax) {
		double freq = Double.valueOf(inFreq);
		double min = Double.valueOf(inMin);
		double max = Double.valueOf(inMax);
	
		return (int)Math.ceil((MAX_FONT_SIZE - MIN_FONT_SIZE) * (freq - min) / (max - min) + MIN_FONT_SIZE);
	}
}
