package ie.gmit.sw;

public class WordFrequency implements Comparable<WordFrequency> {

	private String word;
	private int frequency;

	public WordFrequency() {}
	
	public WordFrequency(String word, Integer frequency) {
		setWord(word);
		setFrequency(frequency);
	}

	public String getWord() {
		return word;
	}

	public void setWord(String word) {
		this.word = word;
	}

	public int getFrequency() {
		return frequency;
	}

	public void setFrequency(int frequency) {
		this.frequency = frequency;
	}
	
	@Override
	public String toString() {
		return super.toString();
	}

	@Override
	public int compareTo(WordFrequency word) {
		return word.frequency - this.frequency;
	}	
	
/*	@Override
	public int compareTo(final WordFrequency word) {
		return word.frequency - frequency;
	}*/
}
