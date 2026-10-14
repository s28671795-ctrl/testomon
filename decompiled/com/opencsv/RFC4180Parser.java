/*
 * Decompiled with CFR 0.152.
 */
package com.opencsv;

import com.opencsv.AbstractCSVParser;
import com.opencsv.enums.CSVReaderNullFieldIndicator;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

public class RFC4180Parser
extends AbstractCSVParser {
    public RFC4180Parser() {
        this('\"', ',', CSVReaderNullFieldIndicator.NEITHER);
    }

    RFC4180Parser(char quoteChar, char separator, CSVReaderNullFieldIndicator nullFieldIndicator) {
        super(separator, quoteChar, nullFieldIndicator);
    }

    @Override
    protected String convertToCsvValue(String value, boolean applyQuotesToAll) {
        String convertedString;
        String testValue = value == null && !this.nullFieldIndicator.equals((Object)CSVReaderNullFieldIndicator.NEITHER) ? "" : value;
        StringBuilder builder = new StringBuilder(testValue == null ? 16 : testValue.length() * 2);
        boolean containsQuoteChar = testValue != null && testValue.contains(this.getQuotecharAsString());
        boolean surroundWithQuotes = applyQuotesToAll || this.isSurroundWithQuotes(value, containsQuoteChar);
        String string = convertedString = !containsQuoteChar ? testValue : this.quoteMatcherPattern.matcher(testValue).replaceAll(this.quoteDoubledAsString);
        if (surroundWithQuotes) {
            builder.append(this.getQuotechar());
        }
        builder.append(convertedString);
        if (surroundWithQuotes) {
            builder.append(this.getQuotechar());
        }
        return builder.toString();
    }

    @Override
    protected String[] parseLine(String nextLine, boolean multi) {
        String[] elements;
        if (!multi && this.pending != null) {
            this.pending = null;
        }
        if (nextLine == null) {
            if (this.pending != null) {
                String s2 = this.pending;
                this.pending = null;
                return new String[]{s2};
            }
            return null;
        }
        String lineToProcess = multi && this.pending != null ? this.pending + nextLine : nextLine;
        this.pending = null;
        if (!StringUtils.contains((CharSequence)lineToProcess, this.quotechar)) {
            elements = this.handleEmptySeparators(this.tokenizeStringIntoArray(lineToProcess));
        } else {
            elements = this.handleEmptySeparators(this.splitWhileNotInQuotes(lineToProcess, multi));
            for (int i = 0; i < elements.length; ++i) {
                if (!StringUtils.contains((CharSequence)elements[i], this.quotechar)) continue;
                elements[i] = this.handleQuotes(elements[i]);
            }
        }
        return elements;
    }

    private String[] tokenizeStringIntoArray(String nextLine) {
        return nextLine.split(this.separatorAsString, -1);
    }

    private String[] handleEmptySeparators(String[] strings) {
        if (this.nullFieldIndicator == CSVReaderNullFieldIndicator.EMPTY_SEPARATORS || this.nullFieldIndicator == CSVReaderNullFieldIndicator.BOTH) {
            for (int i = 0; i < strings.length; ++i) {
                if (!strings[i].isEmpty()) continue;
                strings[i] = null;
            }
        }
        return strings;
    }

    private String[] splitWhileNotInQuotes(String nextLine, boolean multi) {
        int currentPosition = 0;
        ArrayList<String> elements = new ArrayList<String>();
        while (currentPosition < nextLine.length()) {
            int nextSeparator = nextLine.indexOf(this.separator, currentPosition);
            int nextQuote = nextLine.indexOf(this.quotechar, currentPosition);
            if (nextSeparator == -1) {
                elements.add(nextLine.substring(currentPosition));
                currentPosition = nextLine.length();
                continue;
            }
            if (nextQuote == -1 || nextQuote > nextSeparator || nextQuote != currentPosition) {
                elements.add(nextLine.substring(currentPosition, nextSeparator));
                currentPosition = nextSeparator + 1;
                continue;
            }
            int fieldEnd = this.findEndOfFieldFromPosition(nextLine, currentPosition);
            elements.add(fieldEnd >= nextLine.length() ? nextLine.substring(currentPosition) : nextLine.substring(currentPosition, fieldEnd));
            currentPosition = fieldEnd + 1;
        }
        if (multi && this.lastElementStartedWithQuoteButDidNotEndInOne(elements)) {
            this.pending = (String)elements.get(elements.size() - 1) + "\n";
            elements.remove(elements.size() - 1);
        } else if (nextLine.lastIndexOf(this.separator) == nextLine.length() - 1) {
            elements.add("");
        }
        return elements.toArray(ArrayUtils.EMPTY_STRING_ARRAY);
    }

    private boolean lastElementStartedWithQuoteButDidNotEndInOne(List<String> elements) {
        String lastElement = elements.get(elements.size() - 1);
        return this.startsButDoesNotEndWithQuote(lastElement) || this.hasOnlyOneQuote(lastElement) || this.hasOddQuotes(lastElement);
    }

    private boolean hasOddQuotes(String lastElement) {
        return StringUtils.countMatches((CharSequence)lastElement, this.quotechar) % 2 != 0;
    }

    private boolean hasOnlyOneQuote(String lastElement) {
        return StringUtils.countMatches((CharSequence)lastElement, this.quotechar) == 1;
    }

    private boolean startsButDoesNotEndWithQuote(String lastElement) {
        return lastElement.startsWith(this.getQuotecharAsString()) && !lastElement.endsWith(this.getQuotecharAsString());
    }

    private int findEndOfFieldFromPosition(String nextLine, int currentPosition) {
        int nextQuote = nextLine.indexOf(this.quotechar, currentPosition + 1);
        boolean inQuote = false;
        while (this.haveNotFoundLastQuote(nextLine, nextQuote)) {
            if (!inQuote && nextLine.charAt(nextQuote + 1) == this.separator) {
                return nextQuote + 1;
            }
            do {
                nextQuote = nextLine.indexOf(this.quotechar, nextQuote + 1);
                boolean bl = inQuote = !inQuote;
            } while (this.haveNotFoundLastQuote(nextLine, nextQuote) && nextLine.charAt(nextQuote + 1) == this.quotechar);
        }
        return nextLine.length();
    }

    private boolean haveNotFoundLastQuote(String nextLine, int nextQuote) {
        return nextQuote != -1 && nextQuote < nextLine.length() - 1;
    }

    private String handleQuotes(String element) {
        String ret = element;
        if (!this.hasOnlyOneQuote(ret) && ret.startsWith(this.getQuotecharAsString())) {
            ret = StringUtils.removeStart(ret, this.getQuotecharAsString());
            ret = StringUtils.removeEnd(ret, this.getQuotecharAsString());
        }
        if ((ret = StringUtils.replace(ret, this.getQuotecharAsString() + this.getQuotecharAsString(), this.getQuotecharAsString())).isEmpty() && (this.nullFieldIndicator == CSVReaderNullFieldIndicator.BOTH || this.nullFieldIndicator == CSVReaderNullFieldIndicator.EMPTY_QUOTES)) {
            ret = null;
        }
        return ret;
    }

    @Override
    public void setErrorLocale(Locale errorLocale) {
    }
}

