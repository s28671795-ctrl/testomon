/*
 * Decompiled with CFR 0.152.
 */
package com.opencsv;

import com.opencsv.AbstractCSVParser;
import com.opencsv.enums.CSVReaderNullFieldIndicator;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;
import java.util.ResourceBundle;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;

public class CSVParser
extends AbstractCSVParser {
    private static final int BEGINNING_OF_LINE = 3;
    private final char escape;
    private final String escapeAsString;
    private final String escapeDoubleAsString;
    private final boolean strictQuotes;
    private final boolean ignoreLeadingWhiteSpace;
    private final boolean ignoreQuotations;
    private int tokensOnLastCompleteLine = -1;
    private boolean inField = false;
    private Locale errorLocale;

    public CSVParser() {
        this(',', '\"', '\\', false, true, false, DEFAULT_NULL_FIELD_INDICATOR, Locale.getDefault());
    }

    CSVParser(char separator, char quotechar, char escape, boolean strictQuotes, boolean ignoreLeadingWhiteSpace, boolean ignoreQuotations, CSVReaderNullFieldIndicator nullFieldIndicator, Locale errorLocale) {
        super(separator, quotechar, nullFieldIndicator);
        this.errorLocale = ObjectUtils.defaultIfNull(errorLocale, Locale.getDefault());
        if (this.anyCharactersAreTheSame(separator, quotechar, escape)) {
            throw new UnsupportedOperationException(ResourceBundle.getBundle("opencsv", this.errorLocale).getString("special.characters.must.differ"));
        }
        if (separator == '\u0000') {
            throw new UnsupportedOperationException(ResourceBundle.getBundle("opencsv", this.errorLocale).getString("define.separator"));
        }
        this.escape = escape;
        this.escapeAsString = Character.toString(escape);
        this.escapeDoubleAsString = this.escapeAsString + this.escapeAsString;
        this.strictQuotes = strictQuotes;
        this.ignoreLeadingWhiteSpace = ignoreLeadingWhiteSpace;
        this.ignoreQuotations = ignoreQuotations;
    }

    public char getEscape() {
        return this.escape;
    }

    public boolean isStrictQuotes() {
        return this.strictQuotes;
    }

    public boolean isIgnoreLeadingWhiteSpace() {
        return this.ignoreLeadingWhiteSpace;
    }

    public boolean isIgnoreQuotations() {
        return this.ignoreQuotations;
    }

    private boolean anyCharactersAreTheSame(char separator, char quotechar, char escape) {
        return this.isSameCharacter(separator, quotechar) || this.isSameCharacter(separator, escape) || this.isSameCharacter(quotechar, escape);
    }

    private boolean isSameCharacter(char c1, char c2) {
        return c1 != '\u0000' && c1 == c2;
    }

    @Override
    protected String convertToCsvValue(String value, boolean applyQuotestoAll) {
        String testValue = value == null && !this.nullFieldIndicator.equals((Object)CSVReaderNullFieldIndicator.NEITHER) ? "" : value;
        StringBuilder builder = new StringBuilder(testValue == null ? 16 : testValue.length() * 2);
        boolean containsQuoteChar = StringUtils.contains((CharSequence)testValue, this.getQuotechar());
        boolean containsEscapeChar = StringUtils.contains((CharSequence)testValue, this.getEscape());
        boolean containsSeparatorChar = StringUtils.contains((CharSequence)testValue, this.getSeparator());
        boolean surroundWithQuotes = applyQuotestoAll || this.isSurroundWithQuotes(value, containsSeparatorChar);
        String convertedString = !containsQuoteChar ? testValue : this.quoteMatcherPattern.matcher(testValue).replaceAll(this.quoteDoubledAsString);
        String string = convertedString = !containsEscapeChar ? convertedString : convertedString.replace(this.escapeAsString, this.escapeDoubleAsString);
        if (surroundWithQuotes) {
            builder.append(this.getQuotechar());
        }
        builder.append(convertedString);
        if (surroundWithQuotes) {
            builder.append(this.getQuotechar());
        }
        return builder.toString();
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    protected String[] parseLine(String nextLine, boolean multi) throws IOException {
        if (!multi && this.pending != null) {
            this.pending = null;
        }
        if (nextLine == null) {
            if (this.pending == null) return null;
            String s2 = this.pending;
            this.pending = null;
            return new String[]{s2};
        }
        ArrayList<String> tokensOnThisLine = this.tokensOnLastCompleteLine <= 0 ? new ArrayList<String>() : new ArrayList((this.tokensOnLastCompleteLine + 1) * 2);
        StringFragmentCopier sfc = new StringFragmentCopier(nextLine);
        boolean inQuotes = false;
        boolean fromQuotedField = false;
        if (this.pending != null) {
            sfc.append(this.pending);
            this.pending = null;
            boolean bl = inQuotes = !this.ignoreQuotations;
        }
        while (!sfc.isEmptyInput()) {
            char c = sfc.takeInput();
            if (c == this.escape) {
                if (!this.strictQuotes) {
                    this.inField = true;
                }
                this.handleEscapeCharacter(nextLine, sfc, inQuotes);
                continue;
            }
            if (c == this.quotechar) {
                if (this.isNextCharacterEscapedQuote(nextLine, this.inQuotes(inQuotes), sfc.i - 1)) {
                    sfc.takeInput();
                    sfc.appendPrev();
                } else {
                    boolean bl = inQuotes = !inQuotes;
                    if (sfc.isEmptyOutput()) {
                        fromQuotedField = true;
                    }
                    this.handleQuoteCharButNotStrictQuotes(nextLine, sfc);
                }
                this.inField = !this.inField;
                continue;
            }
            if (c == this.separator && (!inQuotes || this.ignoreQuotations)) {
                tokensOnThisLine.add(this.convertEmptyToNullIfNeeded(sfc.takeOutput(), fromQuotedField));
                fromQuotedField = false;
                this.inField = false;
                continue;
            }
            if (this.strictQuotes && (!inQuotes || this.ignoreQuotations)) continue;
            sfc.appendPrev();
            this.inField = true;
            fromQuotedField = true;
        }
        if (inQuotes && !this.ignoreQuotations) {
            if (!multi) throw new IOException(String.format(ResourceBundle.getBundle("opencsv", this.errorLocale).getString("unterminated.quote"), sfc.peekOutput()));
            sfc.append('\n');
            this.pending = sfc.peekOutput();
        } else {
            this.inField = false;
            tokensOnThisLine.add(this.convertEmptyToNullIfNeeded(sfc.takeOutput(), fromQuotedField));
        }
        this.tokensOnLastCompleteLine = tokensOnThisLine.size();
        return tokensOnThisLine.toArray(ArrayUtils.EMPTY_STRING_ARRAY);
    }

    private void handleQuoteCharButNotStrictQuotes(String nextLine, StringFragmentCopier sfc) {
        int i;
        if (!this.strictQuotes && (i = sfc.i) > 3 && nextLine.charAt(i - 2) != this.separator && nextLine.length() > i && nextLine.charAt(i) != this.separator) {
            if (this.ignoreLeadingWhiteSpace && !sfc.isEmptyOutput() && StringUtils.isWhitespace(sfc.peekOutput())) {
                sfc.clearOutput();
            } else {
                sfc.appendPrev();
            }
        }
    }

    private void handleEscapeCharacter(String nextLine, StringFragmentCopier sfc, boolean inQuotes) {
        if (this.isNextCharacterEscapable(nextLine, this.inQuotes(inQuotes), sfc.i - 1)) {
            sfc.takeInput();
            sfc.appendPrev();
        }
    }

    private String convertEmptyToNullIfNeeded(String s2, boolean fromQuotedField) {
        if (s2.isEmpty() && this.shouldConvertEmptyToNull(fromQuotedField)) {
            return null;
        }
        return s2;
    }

    private boolean shouldConvertEmptyToNull(boolean fromQuotedField) {
        switch (this.nullFieldIndicator) {
            case BOTH: {
                return true;
            }
            case EMPTY_SEPARATORS: {
                return !fromQuotedField;
            }
            case EMPTY_QUOTES: {
                return fromQuotedField;
            }
        }
        return false;
    }

    private boolean inQuotes(boolean inQuotes) {
        return inQuotes && !this.ignoreQuotations || this.inField;
    }

    private boolean isNextCharacterEscapedQuote(String nextLine, boolean inQuotes, int i) {
        return inQuotes && nextLine.length() > i + 1 && this.isCharacterQuoteCharacter(nextLine.charAt(i + 1));
    }

    private boolean isCharacterQuoteCharacter(char c) {
        return c == this.quotechar;
    }

    private boolean isCharacterEscapeCharacter(char c) {
        return c == this.escape;
    }

    private boolean isCharacterSeparator(char c) {
        return c == this.separator;
    }

    private boolean isCharacterEscapable(char c) {
        return this.isCharacterQuoteCharacter(c) || this.isCharacterEscapeCharacter(c) || this.isCharacterSeparator(c);
    }

    protected boolean isNextCharacterEscapable(String nextLine, boolean inQuotes, int i) {
        return inQuotes && nextLine.length() > i + 1 && this.isCharacterEscapable(nextLine.charAt(i + 1));
    }

    @Override
    public void setErrorLocale(Locale errorLocale) {
        this.errorLocale = ObjectUtils.defaultIfNull(errorLocale, Locale.getDefault());
    }

    private static class StringFragmentCopier {
        private final String input;
        private int i = 0;
        private StringBuilder sb;
        private int pendingSubstrFrom = 0;
        private int pendingSubstrTo = 0;

        StringFragmentCopier(String input) {
            this.input = input;
        }

        public boolean isEmptyInput() {
            return this.i >= this.input.length();
        }

        public char takeInput() {
            return this.input.charAt(this.i++);
        }

        private StringBuilder materializeBuilder() {
            if (this.sb == null) {
                this.sb = new StringBuilder(this.input.length() + 128);
            }
            if (this.pendingSubstrFrom < this.pendingSubstrTo) {
                this.sb.append(this.input, this.pendingSubstrFrom, this.pendingSubstrTo);
                this.pendingSubstrFrom = this.pendingSubstrTo = this.i;
            }
            return this.sb;
        }

        public void append(String pending) {
            this.materializeBuilder().append(pending);
        }

        public void append(char pending) {
            this.materializeBuilder().append(pending);
        }

        public void appendPrev() {
            if (this.pendingSubstrTo == this.pendingSubstrFrom) {
                this.pendingSubstrFrom = this.i - 1;
                this.pendingSubstrTo = this.i;
            } else if (this.pendingSubstrTo == this.i - 1) {
                ++this.pendingSubstrTo;
            } else {
                this.materializeBuilder().append(this.input.charAt(this.i - 1));
            }
        }

        public boolean isEmptyOutput() {
            return this.pendingSubstrFrom >= this.pendingSubstrTo && (this.sb == null || this.sb.length() == 0);
        }

        public void clearOutput() {
            if (this.sb != null) {
                this.sb.setLength(0);
            }
            this.pendingSubstrFrom = this.pendingSubstrTo = this.i;
        }

        public String peekOutput() {
            if (this.sb == null || this.sb.length() == 0) {
                return this.input.substring(this.pendingSubstrFrom, this.pendingSubstrTo);
            }
            return this.materializeBuilder().toString();
        }

        public String takeOutput() {
            String result = this.peekOutput();
            this.clearOutput();
            return result;
        }
    }
}

