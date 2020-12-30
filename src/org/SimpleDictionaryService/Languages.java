package org.SimpleDictionaryService;

public enum Languages {
    UTF16FOURLATINLETTERS(4, new int[][]{new int[]{65, 90}, new int[]{97, 122}}),
    UTF16FIVEDIGITS(5, new int[][]{new int[]{48, 57}}),
    UTF16RUSSIAN(20, new int[][]{new int[]{1040, 1103}, new int[]{1025, 1025}});

    private final int[][] UTF16CharacterIntervals;
    private final int wordLength;

    Languages(final int wordLength, final int[][] UTF16CharacterIntervals){
        this.wordLength = wordLength;
        this.UTF16CharacterIntervals = UTF16CharacterIntervals;
    }

    public int getWordLength() {
        return wordLength;
    }

    public int[][] getUTF16CharacterIntervals() {
        return UTF16CharacterIntervals;
    }
}
