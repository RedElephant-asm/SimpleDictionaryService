package SimpleDictionaryService.handlers;

import SimpleDictionaryService.Symbol;

/**
 * @author Savchenko Kirill
 * @version 1.0
 *
 * Класс представляет собой контейнер для абстрактных функциональных частей проекта, связанных
 * с кодировками.
 */
public class EncodingHandler {

    /**
     * Назначением функции является определение, является ли символ, численное значение которого передан в качестве аргумента,
     * служебным символом ASCII.
     * @param symbolNumericEquivalent
     * Численное значение символа.
     * @return
     * true, если символ, численное значение которого передано в качестве аргумента, является служебным символом ASCII,
     * в противном случае false.
     */
    public static boolean isASCIIServiceSymbolCode(int symbolNumericEquivalent){
        return symbolNumericEquivalent >= 0 && symbolNumericEquivalent < 65;
    }
}
