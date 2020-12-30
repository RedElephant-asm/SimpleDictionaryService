package org.SimpleDictionaryService;

/**
 * @author RedElephant
 */
public class DictionaryService {

    private String fullFileName;
    private Languages keyLanguage;
    private static final Languages wordLanguage = Languages.UTF16RUSSIAN;

    public DictionaryService(){}

    public DictionaryService(String fullFileName, Languages keyLanguage) {
        this.fullFileName = fullFileName;
        this.keyLanguage = keyLanguage;
    }

    public String getFullFileName() {
        return fullFileName;
    }

    public void setFullFileName(String fullFileName) {
        this.fullFileName = fullFileName;
    }

    public Languages getKeyLanguage() {
        return keyLanguage;
    }

    public void setKeyLanguage(Languages keyLanguage) {
        this.keyLanguage = keyLanguage;
    }
}
