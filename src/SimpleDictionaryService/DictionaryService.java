package SimpleDictionaryService;

import SimpleDictionaryService.throwable.WrongEncodingException;
import SimpleDictionaryService.throwable.WrongKeyLanguageException;
import SimpleDictionaryService.throwable.WrongWordLanguageException;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author Savchenko Kirill
 * @version 1.0
 *
 * Класс, являющий собой модель сервиса словарей.
 */
public class DictionaryService {

    /**
     * Указатель на текущий словарь.
     */
    private Dictionary currentDictionary;

    public DictionaryService(Dictionary dictionary){
        setCurrentDictionary(dictionary);
    }

    public DictionaryService(){

    }

    /**
     * Назначением функции является проверка кодировки текущего словаря.
     */
    private void validateDictionaryEncoding(byte[] dictionaryBytes) throws WrongEncodingException {
        if (! currentDictionary.getEncoding().isArrayOfBytesMatchTheEncoding(dictionaryBytes)){
            throw new WrongEncodingException();
        }
    }

    /**
     * Назначением функции является проверка языка данных типа "слово"
     * из пар "ключ - слово", содержащихся в словаре.
     */
    private void checkDictionaryWordLanguage(byte[] dictionaryBytes) throws WrongWordLanguageException{
        Symbol[] wordSymbols = currentDictionary.getEncoding().convertEncodedByteArrayToEncodedSymbolArray(dictionaryBytes);
        if (! currentDictionary.getWordLanguage().isArrayOfSymbolsMatchTheLanguage(wordSymbols, Dictionary.WORD_ENCODING_MINIMAL_RATIO, currentDictionary.getEncoding())){
            throw new WrongWordLanguageException();
        }
    }

    /**
     * Назначением функции является проверка языка данных типа "ключ"
     * из пар "ключ - слово", содержащихся в словаре.
     */
    private void checkDictionaryKeyLanguage(byte[] dictionaryBytes) throws WrongKeyLanguageException{
        Symbol[] keySymbols = currentDictionary.getEncoding().convertEncodedByteArrayToEncodedSymbolArray(dictionaryBytes);
        if (! currentDictionary.getWordLanguage().isArrayOfSymbolsMatchTheLanguage(keySymbols, Dictionary.KEY_ENCODING_MINIMAL_RATIO, currentDictionary.getEncoding())){
            throw new WrongKeyLanguageException();
        }
    }

    /**
     * Назначением функции является считывание всех байтов из текущего словаря.
     * @return
     * Указатель на массив считанных из текущего словаря байтов.
     */
    private byte[] readAllDictionaryBytes(){
        byte[] bytes = new byte[]{};
        try {
            InputStream inputStream = new FileInputStream(currentDictionary);
            bytes = new byte[inputStream.available()];
            inputStream.read(bytes);
        }catch (IOException exception){
            exception.printStackTrace();
        }
        return bytes;
    }


    public Dictionary getCurrentDictionary() {
        return currentDictionary;
    }

    public void setCurrentDictionary(Dictionary currentDictionary) {
        this.currentDictionary = currentDictionary;
        byte[] dictionaryBytes = readAllDictionaryBytes();
        try {
            validateDictionaryEncoding(dictionaryBytes);
            checkDictionaryKeyLanguage(dictionaryBytes);
            checkDictionaryWordLanguage(dictionaryBytes);
        }catch (WrongEncodingException | WrongKeyLanguageException | WrongWordLanguageException exception){
            exception.printStackTrace();
        }
    }
}
