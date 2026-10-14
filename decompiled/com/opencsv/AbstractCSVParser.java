/*
 * Decompiled with CFR 0.152.
 */
package com.opencsv;

import com.opencsv.ICSVParser;
import com.opencsv.enums.CSVReaderNullFieldIndicator;
import java.io.IOException;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.apache.commons.lang3.StringUtils;

public abstract class AbstractCSVParser
implements ICSVParser {
    protected static final Pattern SPECIAL_REGEX_CHARS = Pattern.compile("[{}()\\[\\].+*?^$\\\\|]");
    protected static final StringBuilder EMPTY_STRINGBUILDER = new StringBuilder("");
    protected final char separator;
    protected final String separatorAsString;
    protected final char quotechar;
    protected final String quotecharAsString;
    protected final String quoteDoubledAsString;
    protected final Pattern quoteMatcherPattern;
    protected final CSVReaderNullFieldIndicator nullFieldIndicator;
    protected String pending;

    public AbstractCSVParser(char separator, char quotechar, CSVReaderNullFieldIndicator nullFieldIndicator) {
        this.separator = separator;
        this.separatorAsString = SPECIAL_REGEX_CHARS.matcher(Character.toString(separator)).replaceAll("\\\\$0");
        this.quotechar = quotechar;
        this.quotecharAsString = Character.toString(quotechar);
        this.quoteDoubledAsString = this.quotecharAsString + this.quotecharAsString;
        this.quoteMatcherPattern = Pattern.compile(this.quotecharAsString);
        this.nullFieldIndicator = nullFieldIndicator;
    }

    @Override
    public char getSeparator() {
        return this.separator;
    }

    public String getSeparatorAsString() {
        return this.separatorAsString;
    }

    @Override
    public char getQuotechar() {
        return this.quotechar;
    }

    public String getQuotecharAsString() {
        return this.quotecharAsString;
    }

    @Override
    public boolean isPending() {
        return this.pending != null;
    }

    @Override
    public String[] parseLineMulti(String nextLine) throws IOException {
        return this.parseLine(nextLine, true);
    }

    @Override
    public String[] parseLine(String nextLine) throws IOException {
        return this.parseLine(nextLine, false);
    }

    @Override
    public String parseToLine(String[] values2, boolean applyQuotesToAll) {
        return Stream.of(values2).map(v -> this.convertToCsvValue((String)v, applyQuotesToAll)).collect(Collectors.joining(this.getSeparatorAsString()));
    }

    @Override
    public void parseToLine(String[] values2, boolean applyQuotesToAll, Appendable appendable) throws IOException {
        boolean first = true;
        for (String value : values2) {
            if (!first) {
                appendable.append(this.getSeparator());
            } else {
                first = false;
            }
            this.convertToCsvValue(value, applyQuotesToAll, appendable);
        }
    }

    protected abstract String convertToCsvValue(String var1, boolean var2);

    protected void convertToCsvValue(String value, boolean applyQuotesToAll, Appendable appendable) throws IOException {
        appendable.append(this.convertToCsvValue(value, applyQuotesToAll));
    }

    protected boolean isSurroundWithQuotes(String value, boolean forceSurround) {
        if (value == null) {
            return this.nullFieldIndicator.equals((Object)CSVReaderNullFieldIndicator.EMPTY_QUOTES);
        }
        if (value.isEmpty() && this.nullFieldIndicator.equals((Object)CSVReaderNullFieldIndicator.EMPTY_SEPARATORS)) {
            return true;
        }
        return forceSurround || value.contains(this.getSeparatorAsString()) || value.contains("\n");
    }

    protected abstract String[] parseLine(String var1, boolean var2) throws IOException;

    @Override
    public CSVReaderNullFieldIndicator nullFieldIndicator() {
        return this.nullFieldIndicator;
    }

    @Override
    public String getPendingText() {
        return StringUtils.defaultString(this.pending);
    }
}

