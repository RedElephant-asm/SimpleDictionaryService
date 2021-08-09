package SimpleDictionaryService;

import SimpleDictionaryService.language.Language;
import org.SimpleEncodings.Encoding;

import java.io.File;

/**
 * @author Savchenko Kirill
 * @version 1.0
 * Класс, являющий соборй модель дневника.
 */
public class Dictionary extends File{

    public static final double WORD_LANGUAGE_MINIMAL_RATIO    = 0.8;
    public static final double KEY_LANGUAGE_MINIMAL_RATIO     = 0.8;
    public static final String DEFAULT_SEPARATOR              = " ";

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

    /**
     * Указатель на строку, которая является разделителем в паре "ключ - значение".
     */
    private String separator = DEFAULT_SEPARATOR;

    public Dictionary(String fullFileName, Encoding encoding, Language wordLanguage, Language keyLanguage, String separator){
        super(fullFileName);
        this.fullFileName = fullFileName;
        this.encoding = encoding;
        this.wordLanguage = wordLanguage;
        this.keyLanguage = keyLanguage;
        setSeparator(separator);

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

    public String getSeparator() {
        return separator;
    }

    public void setSeparator(String separator) {
        if (separator.length() > 0){
            this.separator = separator;
        }
    }
}
