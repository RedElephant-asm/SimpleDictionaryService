package SimpleDictionaryService;

import org.SimpleEncodings.Symbol;

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
    private String world;

    /**
     * Разделитель, используемый в паре ключ - значение.
     */
    private String separator;

    public DictionaryRecord(String recordString, String separator){
        String[] keyWordPair = recordString.split(separator);
        this.key = keyWordPair[0];
        this.world = keyWordPair[1];
        this.separator = separator;
    }

    public DictionaryRecord(){

    }

    @Override
    public String toString() {
        return String.format("%s%s%s", key, separator, world);
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getWorld() {
        return world;
    }

    public void setWorld(String world) {
        this.world = world;
    }

    public String getSeparator() {
        return separator;
    }

    public void setSeparator(String separator) {
        this.separator = separator;
    }
}
