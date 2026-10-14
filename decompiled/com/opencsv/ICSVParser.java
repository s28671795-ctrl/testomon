/*
 * Decompiled with CFR 0.152.
 */
package com.opencsv;

import com.opencsv.enums.CSVReaderNullFieldIndicator;
import java.io.IOException;
import java.util.Locale;

public interface ICSVParser {
    public static final char DEFAULT_SEPARATOR = ',';
    public static final int INITIAL_READ_SIZE = 1024;
    public static final int READ_BUFFER_SIZE = 128;
    public static final char DEFAULT_QUOTE_CHARACTER = '\"';
    public static final char DEFAULT_ESCAPE_CHARACTER = '\\';
    public static final boolean DEFAULT_STRICT_QUOTES = false;
    public static final boolean DEFAULT_IGNORE_LEADING_WHITESPACE = true;
    public static final boolean DEFAULT_IGNORE_QUOTATIONS = false;
    public static final char NULL_CHARACTER = '\u0000';
    public static final CSVReaderNullFieldIndicator DEFAULT_NULL_FIELD_INDICATOR = CSVReaderNullFieldIndicator.NEITHER;
    public static final String DEFAULT_BUNDLE_NAME = "opencsv";
    public static final int MAX_SIZE_FOR_EMPTY_FIELD = 16;
    public static final String NEWLINE = "\n";

    public char getSeparator();

    public char getQuotechar();

    public boolean isPending();

    public String[] parseLineMulti(String var1) throws IOException;

    public String[] parseLine(String var1) throws IOException;

    public String parseToLine(String[] var1, boolean var2);

    public void parseToLine(String[] var1, boolean var2, Appendable var3) throws IOException;

    public CSVReaderNullFieldIndicator nullFieldIndicator();

    public String getPendingText();

    public void setErrorLocale(Locale var1);
}

