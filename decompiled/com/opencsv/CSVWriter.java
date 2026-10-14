/*
 * Decompiled with CFR 0.152.
 */
package com.opencsv;

import com.opencsv.AbstractCSVWriter;
import java.io.IOException;
import java.io.Writer;

public class CSVWriter
extends AbstractCSVWriter {
    protected final char separator;
    protected final char quotechar;
    protected final char escapechar;

    public CSVWriter(Writer writer) {
        this(writer, ',', '\"', '\"', "\n");
    }

    public CSVWriter(Writer writer, char separator, char quotechar, char escapechar, String lineEnd) {
        super(writer, lineEnd);
        this.escapechar = escapechar;
        this.quotechar = quotechar;
        this.separator = separator;
    }

    @Override
    protected void writeNext(String[] nextLine, boolean applyQuotesToAll, Appendable appendable) throws IOException {
        if (nextLine == null) {
            return;
        }
        for (int i = 0; i < nextLine.length; ++i) {
            String nextElement;
            if (i != 0) {
                appendable.append(this.separator);
            }
            if ((nextElement = nextLine[i]) == null) continue;
            Boolean stringContainsSpecialCharacters = this.stringContainsSpecialCharacters(nextElement);
            this.appendQuoteCharacterIfNeeded(applyQuotesToAll, appendable, stringContainsSpecialCharacters);
            if (stringContainsSpecialCharacters.booleanValue()) {
                this.processLine(nextElement, appendable);
            } else {
                appendable.append(nextElement);
            }
            this.appendQuoteCharacterIfNeeded(applyQuotesToAll, appendable, stringContainsSpecialCharacters);
        }
        appendable.append(this.lineEnd);
        this.writer.write(appendable.toString());
    }

    private void appendQuoteCharacterIfNeeded(boolean applyQuotesToAll, Appendable appendable, Boolean stringContainsSpecialCharacters) throws IOException {
        if ((applyQuotesToAll || stringContainsSpecialCharacters.booleanValue()) && this.quotechar != '\u0000') {
            appendable.append(this.quotechar);
        }
    }

    protected boolean stringContainsSpecialCharacters(String line) {
        return line.indexOf(this.quotechar) != -1 || line.indexOf(this.escapechar) != -1 || line.indexOf(this.separator) != -1 || line.contains("\n") || line.contains("\r");
    }

    protected void processLine(String nextElement, Appendable appendable) throws IOException {
        for (int j = 0; j < nextElement.length(); ++j) {
            char nextChar = nextElement.charAt(j);
            this.processCharacter(appendable, nextChar);
        }
    }

    protected void processCharacter(Appendable appendable, char nextChar) throws IOException {
        if (this.escapechar != '\u0000' && this.checkCharactersToEscape(nextChar)) {
            appendable.append(this.escapechar);
        }
        appendable.append(nextChar);
    }

    protected boolean checkCharactersToEscape(char nextChar) {
        return this.quotechar == '\u0000' ? nextChar == this.quotechar || nextChar == this.escapechar || nextChar == this.separator || nextChar == '\n' : nextChar == this.quotechar || nextChar == this.escapechar;
    }
}

