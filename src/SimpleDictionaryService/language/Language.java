package SimpleDictionaryService.language;

import org.SimpleEncodings.Encoding;
import org.SimpleEncodings.Symbol;

/**
 * @author Savchenko Kirill
 * @version 1.0
 *
 * Класс, являющий собой модель языка.
 */
public class Language {

    /**
     * Пример модели латинского языка.
     */
    public static final Language UNICODE_LATIN =
            new Language(
                    new EncodingIntervalTable(Encoding.DEFAULT_UTF8, new int[]{65, 90}, new int[]{97, 122}),
                    new EncodingIntervalTable(Encoding.DEFAULT_UTF16LE, new int[]{65, 90}, new int[]{97, 122}),
                    new EncodingIntervalTable(Encoding.DEFAULT_UTF16BE, new int[]{65, 90}, new int[]{97, 122}),
                    new EncodingIntervalTable(Encoding.DEFAULT_ASCII, new int[]{65, 90}, new int[]{97, 122}));

    /**
     * Пример модели языка, состоящего из цифр.
     */
    public static final Language UNICODE_DIGITS =
            new Language(
                    new EncodingIntervalTable(Encoding.DEFAULT_UTF8, new int[]{48, 57}),
                    new EncodingIntervalTable(Encoding.DEFAULT_UTF16LE, new int[]{48, 57}),
                    new EncodingIntervalTable(Encoding.DEFAULT_UTF16BE, new int[]{48, 57}),
                    new EncodingIntervalTable(Encoding.DEFAULT_ASCII, new int[]{48, 57}));

    /**
     * Пример модели русского языка.
     */
    public static final Language UNICODE_RUSSIAN =
            new Language(
                    new EncodingIntervalTable(Encoding.DEFAULT_UTF8, new int[]{1040, 1103}, new int[]{1025, 1025}),
                    new EncodingIntervalTable(Encoding.DEFAULT_UTF16LE, new int[]{1040, 1103}, new int[]{1025, 1025}),
                    new EncodingIntervalTable(Encoding.DEFAULT_UTF16BE, new int[]{1040, 1103}, new int[]{1025, 1025}));

    public static final Language UNKNOWN_LANGUAGE =
            new Language();

    public static final double      MINIMAL_SYMBOL_GROUP_MATCH_RATIO =  0.8;

    Language(EncodingIntervalTable... intervalTables){
        this.intervalTables = intervalTables;
    }

    /**
     * Массив таблиц интервалов кодировок.
     */
    private EncodingIntervalTable[] intervalTables;

    /**
     * Функция предназначена для получения таблицы интервалов символов данного языка для определенной кодировки.
     * @param encoding
     * Кодировка, таблицу интервалов данного языка требуется получить.
     * @return
     * Таблица интервалов данного языка для аргумента-кодировки в том случае, если таблица интервалов данной кодировки
     * существует в массиве таблиц intervalTables, в противном случае UNDEFINED_INTERVAL_TABLE.
     */
    public EncodingIntervalTable getEncodingIntervalTable(Encoding encoding){
        for (EncodingIntervalTable currentIntervalTable : this.intervalTables) {
            if (currentIntervalTable.getEncoding() == encoding){
                return currentIntervalTable;
            }
        }
        return EncodingIntervalTable.UNDEFINED_INTERVAL_TABLE;
    }

    /**
     * Назначением функции является определение, принадлежат ли символы, содержащиеся в массиве, указатель на который передается
     * функции, к данному языку.
     * @param symbols
     * Указатель на массив символов.
     * @param encoding
     * Кодировка символов, содержащихся в передаваемом массиве.
     * @return
     * true, если процент символов, которые содержатся в массиве, больше или равен MINIMAL_SYMBOL_GROUP_MATCH_RATIO, в противном случае false.
     */
    public boolean isArrayOfSymbolsMatchTheLanguage(Symbol[] symbols, Encoding encoding){
        return isArrayOfSymbolsMatchTheLanguage(symbols, MINIMAL_SYMBOL_GROUP_MATCH_RATIO, encoding);
    }

    /**
     * Назначением функции является определение, принадлежат ли символы, содержащиеся в массиве, указатель на который передается
     * функции, к данному языку.
     * @param symbols
     * Указатель на массив символов.
     * @param ratio
     * Минимальный процент соответствующих символов.
     * @param encoding
     * Кодировка символов, содержащихся в передаваемом массиве.
     * @return
     * true, если процент символов, которые содержатся в массиве, больше или равен ratio, в противном случае false.
     */
    public boolean isArrayOfSymbolsMatchTheLanguage(Symbol[] symbols, double ratio, Encoding encoding){
        int matchCount = 0;
        for (Symbol currentSymbol : symbols) {
            if (isSymbolMatchTheLanguage(currentSymbol, encoding)){
                matchCount++;
            }
        }
        return ((double)matchCount) / symbols.length >= ratio;
    }

    /**
     * Назначением функции является определение, принадлежит ли символ, передаваемый функции, к данному языку.
     * @param symbol
     * Передаваемый символ.
     * @param symbolEncoding
     * Кодировка передаваемого символа.
     * @return
     * true, если передаваемый символ принадлежит данному языку, в противном случае false.
     */
    public boolean isSymbolMatchTheLanguage(Symbol symbol, Encoding symbolEncoding){
        EncodingIntervalTable symbolEncodingIntervalTable = getEncodingIntervalTable(symbolEncoding);
        if (symbolEncodingIntervalTable == EncodingIntervalTable.UNDEFINED_INTERVAL_TABLE){
            return false;
        }
        int symbolNumericEquivalent = symbolEncoding.getSymbolValuablePart(symbol);
        for (int[] currentInterval : symbolEncodingIntervalTable.getIntervals()) {
            if ( (symbolNumericEquivalent >= currentInterval[0] && symbolNumericEquivalent <= currentInterval[1]) || Encoding.isASCIIServiceSymbolCode(symbolNumericEquivalent)){
                return true;
            }
        }
        return false;
    }

    public void setIntervalTables(EncodingIntervalTable[] intervalTables) {
        this.intervalTables = intervalTables;
    }

    public EncodingIntervalTable[] getIntervalTables() {
        return intervalTables;
    }
}
