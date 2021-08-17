package org.SimpleDictionaryService.language;

import org.SimpleEncodings.Encoding;

/**
 * @author Savchenko Kirill
 * @version 1.0
 *
 * Класс, который являет собой модель таблицы интервалов для кодировки.
 */
public class EncodingIntervalTable {

    public static final EncodingIntervalTable UNDEFINED_INTERVAL_TABLE = new EncodingIntervalTable();

    /**
     * Целевая кодировка таблицы.
     */
    private Encoding encoding;

    /**
     * Массив интервалов, описывающих расположение символов языка в символьной таблице целевой кодировки.
     */
    private int[][] intervals;

    public EncodingIntervalTable(Encoding encoding, int[]... intervals){
        this.encoding = encoding;
        this.intervals = intervals;
    }

    public EncodingIntervalTable(){

    }

    public Encoding getEncoding() {
        return encoding;
    }

    public void setEncoding(Encoding encoding) {
        this.encoding = encoding;
    }

    public int[][] getIntervals() {
        return intervals;
    }

    public void setIntervals(int[][] intervals) {
        this.intervals = intervals;
    }
}
