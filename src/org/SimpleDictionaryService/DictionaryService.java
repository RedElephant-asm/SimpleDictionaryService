package org.SimpleDictionaryService;

import org.SimpleDictionaryService.language.Language;
import org.SimpleDictionaryService.throwable.UnknownEncodingException;
import org.SimpleDictionaryService.throwable.UnknownLanguageException;
import org.SimpleDictionaryService.throwable.WrongKeyLanguageException;
import org.SimpleDictionaryService.throwable.WrongWordLanguageException;
import org.SimpleEncodings.Encoding;
import org.SimpleEncodings.throwable.WrongEncodingException;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.LinkedHashSet;

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

    /**
     * Загружаемые данные файла - словаря.
     */
    private LinkedHashSet<DictionaryRecord> dictionaryData;

    /**
     * Тип выполнения операций сервисов словаря.
     */
    private ExecutionStyle executionStyle;

    public DictionaryService(Dictionary dictionary, ExecutionStyle executionStyle) throws UnknownEncodingException, UnknownLanguageException {
        this.executionStyle = executionStyle;
        setCurrentDictionary(dictionary);
    }

    public DictionaryService(){

    }

    /**
     * Назначением функции является добавление в текущее состояние словаря новое вхождение(запись).
     * @param record
     * Указатель на обьект(запись) информация которого будет добавлена в текущее состояние словаря.
     */
    public void createRecord(DictionaryRecord record){
        if(dictionaryData.stream().noneMatch(currentRecord -> currentRecord.getKey().equals(record.getKey()))){
            dictionaryData.add(record);
        }
        finalizeOperation();
    }

    /**
     * Назначением функции является поиск вхождения в текущем состоянии словаря.
     * @param key
     * Ключ, по которому будет производиться поиск.
     * @return
     * Указатель на новое вхождление текущего состояния словаря.
     */
    public DictionaryRecord readRecord(String key){
        DictionaryRecord currentRecord = DictionaryRecord.UNKNOWN_RECORD;
        for (DictionaryRecord record : dictionaryData) {
             if (record.getKey().equals(key)){
                 currentRecord = record;
                 break;
             }
        }
        finalizeOperation();
        return currentRecord;
    }

    /**
     * Назначением функции является обновление текущей части записи, на которую указывает передаваемый
     * ключ, в соответствии с значением передаваемой строки.
     * @param key
     * Передаваемая строка.
     */
    public void updateRecord(String key, String newWord){
        for (DictionaryRecord record : dictionaryData) {
            if (record.getKey().equals(key)) {
                record.setWord(newWord);
            }
        }
        finalizeOperation();
    }

    /**
     * Назначением функции является удаление запсиси из текущего состояния словаря по ключу.
     * @param key
     * Ключ удаляемой записи.
     */
    public void deleteRecord(String key){
        dictionaryData.removeIf(record -> record.getKey().equals(key));
        finalizeOperation();
    }

    /**
     * Назначением функции является запись т екущего состояния словаря на диск(сохраннение).
     */
    public void writeDictionary(){
        try {
            PrintWriter printWriter = new PrintWriter(new OutputStreamWriter(new FileOutputStream(currentDictionary), StandardCharsets.UTF_8));
            for (DictionaryRecord record : dictionaryData) {
                printWriter.println(String.format("%s%s%s", record.getKey(), currentDictionary.getSeparator(), record.getWord()));
            }
            printWriter.close();
        }catch (IOException exception){
            exception.printStackTrace();
        }
    }

    /**
     * Назначением функции является выполнение перечня операций при завершении редактирования
     * текущего состояния словаря.
     */
    private void finalizeOperation(){
        switch (executionStyle){
            case HARD:
                writeDictionary();
                break;
            case LAZY:
        }
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

    public boolean isDictionarySelected(){
        return this.currentDictionary != null;
    }

    public Dictionary getCurrentDictionary() {
        return currentDictionary;
    }

    public void setCurrentDictionary(Dictionary currentDictionary) throws UnknownEncodingException, UnknownLanguageException {
        if (currentDictionary.getEncoding() == Encoding.UNKNOWN_ENCODING){
            throw new UnknownEncodingException();
        }
        if (currentDictionary.getKeyLanguage() == Language.UNKNOWN_LANGUAGE || currentDictionary.getWordLanguage() == Language.UNKNOWN_LANGUAGE){
            throw new UnknownLanguageException();
        }
        this.currentDictionary = currentDictionary;
        this.dictionaryData = new LinkedHashSet<>();
        try {
            byte[] dictionaryBytes = readAllDictionaryBytes();
            if(dictionaryBytes.length > 20){
                validateDictionaryEncoding(dictionaryBytes);
            }
            int keySymbolsCount = 0, keySymbolsLanguageMatches = 0, wordSymbolsCount = 0, worldSymbolsLanguageMatches = 0;
            String currentLine;
            DictionaryRecord currentRecord;
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(currentDictionary), StandardCharsets.UTF_8));
            while ( (currentLine = bufferedReader.readLine()) != null){
                String[] keyWordPair = currentLine.split(currentDictionary.getSeparator());
                currentRecord = new DictionaryRecord(keyWordPair[0], keyWordPair[1]);
                keySymbolsCount += currentRecord.getKey().length();
                wordSymbolsCount += currentRecord.getWord().length();
                keySymbolsLanguageMatches += currentDictionary.getKeyLanguage().countOfMatches(currentRecord.getKey(), currentDictionary.getEncoding());
                worldSymbolsLanguageMatches += currentDictionary.getWordLanguage().countOfMatches(currentRecord.getWord(), currentDictionary.getEncoding());
                dictionaryData.add(currentRecord);
            }

            if ((double)keySymbolsLanguageMatches / keySymbolsCount < Dictionary.KEY_LANGUAGE_MINIMAL_RATIO){
                throw new WrongKeyLanguageException();
            }
            else if ((double)worldSymbolsLanguageMatches / wordSymbolsCount < Dictionary.WORD_LANGUAGE_MINIMAL_RATIO){
                throw new WrongWordLanguageException();
            }
        }catch (IOException | WrongKeyLanguageException | WrongWordLanguageException | WrongEncodingException exception){
            exception.printStackTrace();
        }
    }

    public LinkedHashSet<DictionaryRecord> getDictionaryData() {
        return dictionaryData;
    }

    public ExecutionStyle getExecutionStyle() {
        return executionStyle;
    }

    public void setExecutionStyle(ExecutionStyle executionStyle) {
        this.executionStyle = executionStyle;
    }
}
