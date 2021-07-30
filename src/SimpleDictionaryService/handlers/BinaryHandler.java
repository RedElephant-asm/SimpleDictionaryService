package SimpleDictionaryService.handlers;

/**
 * @author Savchenko Kirill
 * @version 1.0
 *
 * Класс представляет собой контейнер для абстрактных функциональных частей проекта, связанных с работой с
 * двоичными данными.
 */
public class BinaryHandler {

    /**
     * Назначением функции является получение бинарно - строкового эквивалента массива байтов.
     * @param bytes
     * Массив байт, бинарно-строковый эквивалент которого требуется получить.
     * @return
     * Бинарно-строковый эквивалент массива байтов(аргумента).
     */
    public static String getByteArrayBinaryStringEquivalent(byte[] bytes){
        StringBuilder binaryStringEquivalent = new StringBuilder("");
        for (Byte currentByte : bytes) {
            binaryStringEquivalent.append(getByteBinaryStringEquivalent(currentByte));
        }
        return binaryStringEquivalent.toString();
    }

    /**
     * Назначением функции является получиение бинарно-строкового эквивалента байта.
     * @param byteArg
     * Байт, бинарно-строковый эквивалент которого требуется получить.
     * @return
     * Бинарно - строковый эквивалент байта - аргумента.
     */
    public static String getByteBinaryStringEquivalent(byte byteArg){
        return String.format("%8s", Integer.toBinaryString(byteArg & 0xFF)).replace(' ', '0');
    }
}
