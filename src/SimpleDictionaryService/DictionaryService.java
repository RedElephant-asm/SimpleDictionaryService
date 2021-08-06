package SimpleDictionaryService;

import SimpleDictionaryService.throwable.WrongKeyLanguageException;
import SimpleDictionaryService.throwable.WrongWordLanguageException;
import org.SimpleEncodings.Symbol;
import org.SimpleEncodings.throwable.WrongEncodingException;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;

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

    private HashMap<String, String> dictionaryData;

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
        this.dictionaryData = new HashMap<>();
        try {
            byte[] dictionaryBytes = readAllDictionaryBytes();
            validateDictionaryEncoding(dictionaryBytes);
            int keySymbolsCount = 0, keySymbolsLanguageMatches = 0, wordSymbolsCount = 0, worldSymbolsLanguageMatches = 0;
            String currentLine;
            String[] currentKeyWorldPair;
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(currentDictionary), StandardCharsets.UTF_8));
            while ( (currentLine = bufferedReader.readLine()) != null){
                currentKeyWorldPair = currentLine.split(" ");
                keySymbolsCount += currentKeyWorldPair[0].length();
                wordSymbolsCount += currentKeyWorldPair[1].length();
                keySymbolsLanguageMatches += currentDictionary.getKeyLanguage().countOfMatches(currentKeyWorldPair[0], currentDictionary.getEncoding());
                worldSymbolsLanguageMatches += currentDictionary.getWordLanguage().countOfMatches(currentKeyWorldPair[1], currentDictionary.getEncoding());
                dictionaryData.put(currentKeyWorldPair[0], currentKeyWorldPair[1]);
            }
            System.out.printf("keySymbolsCount = %d, keySymbolsLanguageMatches = %d, wordSymbolsCount = %d, worldSymbolsLanguageMatches = %d\n", keySymbolsCount, keySymbolsLanguageMatches, wordSymbolsCount, worldSymbolsLanguageMatches);
            if ((double)keySymbolsLanguageMatches / keySymbolsCount < Dictionary.KEY_LANGUAGE_MINIMAL_RATIO) throw new WrongKeyLanguageException();
            else if ((double)worldSymbolsLanguageMatches / wordSymbolsCount < Dictionary.WORD_LANGUAGE_MINIMAL_RATIO) throw new WrongWordLanguageException();

        }catch (IOException | WrongKeyLanguageException | WrongWordLanguageException | WrongEncodingException exception){
            exception.printStackTrace();
        }
    }
}
