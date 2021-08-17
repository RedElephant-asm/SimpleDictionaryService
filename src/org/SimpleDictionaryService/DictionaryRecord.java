package org.SimpleDictionaryService;

/**
 * @author Savchenko Kirill
 * @version 1.0
 *
 * Класс, являющий собой модель записи словаря.
 */
public class DictionaryRecord {

    public static final DictionaryRecord UNKNOWN_RECORD = new DictionaryRecord();

    /**
     * Данные типа "ключ" в паре "ключ - значение".
     */
    private String key;

    /**
     * Данные типа
     */
    private String word;

    public DictionaryRecord(String key, String word){
        this.key = key;
        this.word = word;
    }

    public DictionaryRecord(){

    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }
}
