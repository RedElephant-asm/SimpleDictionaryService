package SimpleDictionaryService.encoding;

import SimpleDictionaryService.Symbol;
import SimpleDictionaryService.handlers.BinaryHandler;

/**
 * @author Savchenko Kirill
 * @version 1.0
 *
 * Класс, являющий собой модель шаблона символа.
 */
public class SymbolTemplate {

    /**
     * Стандартный шаблон для символов кодировки ASCII 128.
     */
    public static final SymbolTemplate ASCII128_DEFAULT_TEMPLATE = new SymbolTemplate("0[01]{7}", 1);

    /**
     * Неопределенный шаблон (помогает избежать использования null при построении циклов поиска в массивах).
     */
    public static final SymbolTemplate UNDEFINED_TEMPLATE = new SymbolTemplate("", 0);

    /**
     * Строковое регулярное выряажение(regex).
     */
    private String regex;

    /**
     * Количество байтов, из которых может состоять символ, описываемый строковым регулярным выражением.
     */
    private int byteCount;

    public SymbolTemplate(String regex, int byteCount){
        this.regex = regex;
        this.byteCount = byteCount;
    }

    /**
     * Назначением функции является проверка символа на соответствие данному шаблону.
     * @param symbol
     * Проверяемый символ.
     * @return
     * true, если проверяемый символ соответствует данному шаблону, в противном случае false.
     */
    public boolean isSymbolMatchTheTemplate(Symbol symbol){
        String symbolBinaryStringEquivalent = BinaryHandler.getByteArrayBinaryStringEquivalent(symbol.getBytes());
        return symbolBinaryStringEquivalent.matches(this.regex);
    }

    /**
     * Назначением функции является получение идентификационных двоичных строк кодировки.
     * @return
     * Массив строк, представляющих собой идентификационные двоичные строки кодировки.
     */
    public String[] getEncodingIdentificationBitSets(){
        return this.regex.split("\\[[01]{1,2}]\\{[1-9]{1,2}}");
    }


    public String getTemplate() {
        return regex;
    }

    public void setTemplate(String template) {
        this.regex = template;
    }

    public int getByteCount() {
        return byteCount;
    }

    public void setByteCount(int byteCount) {
        this.byteCount = byteCount;
    }
}
