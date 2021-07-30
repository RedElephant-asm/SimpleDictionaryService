package SimpleDictionaryService;

import SimpleDictionaryService.encoding.Encoding;
import SimpleDictionaryService.language.Language;

import java.io.File;

/**
 * @author Savchenko Kirill
 * @version 1.0
 * Класс, являющий соборй модель дневника.
 */
public class Dictionary extends File{

    public static double WORD_ENCODING_MINIMAL_RATIO    = 0.66 * Encoding.MINIMAL_BYTE_GROUP_MATCH_RATIO;
    public static double KEY_ENCODING_MINIMAL_RATIO     = 0.33 * Encoding.MINIMAL_BYTE_GROUP_MATCH_RATIO;

    /**
     * Полное имя файла, который является словарем.
     * Файл должен содержать данные в формате "ключ - значение".
     */
    private String fullFileName;

    /**
     * Указатель на кодировку файла, который является словарем.
     */
    private Encoding encoding;

    /**
     * Указатель на язык значения типа "значение" из пары "ключ - значение".
     */
    private Language wordLanguage;

    /**
     * Указатель на язык значения типа "ключ" из пары "ключ - значение".
     */
    private Language keyLanguage;

    public Dictionary(String fullFileName, Encoding encoding, Language wordLanguage, Language keyLanguage){
        super(fullFileName);
        this.fullFileName = fullFileName;
        this.encoding = encoding;
        this.wordLanguage = wordLanguage;
        this.keyLanguage = keyLanguage;

    }

    public Dictionary(String fullFileName){
        super(fullFileName);
        this.fullFileName = fullFileName;
    }

    public String getFullFileName() {
        return fullFileName;
    }

    public void setFullFileName(String fullFileName) {
        this.fullFileName = fullFileName;
    }

    public Encoding getEncoding() {
        return encoding;
    }

    public void setEncoding(Encoding encoding) {
        this.encoding = encoding;
    }

    public Language getWordLanguage() {
        return wordLanguage;
    }

    public void setWordLanguage(Language wordLanguage) {
        this.wordLanguage = wordLanguage;
    }

    public Language getKeyLanguage() {
        return keyLanguage;
    }

    public void setKeyLanguage(Language keyLanguage) {
        this.keyLanguage = keyLanguage;
    }
}