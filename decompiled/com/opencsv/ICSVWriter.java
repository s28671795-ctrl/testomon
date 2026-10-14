/*
 * Decompiled with CFR 0.152.
 */
package com.opencsv;

import com.opencsv.ResultSetHelper;
import java.io.Closeable;
import java.io.Flushable;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public interface ICSVWriter
extends Closeable,
Flushable {
    public static final String DEFAULT_LINE_END = "\n";
    public static final String RFC4180_LINE_END = "\r\n";
    public static final int INITIAL_STRING_SIZE = 1024;
    public static final char DEFAULT_ESCAPE_CHARACTER = '\"';
    public static final char DEFAULT_SEPARATOR = ',';
    public static final char DEFAULT_QUOTE_CHARACTER = '\"';
    public static final char NO_QUOTE_CHARACTER = '\u0000';
    public static final char NO_ESCAPE_CHARACTER = '\u0000';

    public void writeAll(Iterable<String[]> var1, boolean var2);

    default public void writeAll(List<String[]> allLines, boolean applyQuotesToAll) {
        this.writeAll((Iterable<String[]>)allLines, applyQuotesToAll);
    }

    default public void writeAll(Iterable<String[]> allLines) {
        this.writeAll(allLines, true);
    }

    default public void writeAll(List<String[]> allLines) {
        this.writeAll((Iterable<String[]>)allLines);
    }

    default public int writeAll(ResultSet rs, boolean includeColumnNames) throws SQLException, IOException {
        return this.writeAll(rs, includeColumnNames, false, true);
    }

    default public int writeAll(ResultSet rs, boolean includeColumnNames, boolean trim) throws SQLException, IOException {
        return this.writeAll(rs, includeColumnNames, trim, true);
    }

    public int writeAll(ResultSet var1, boolean var2, boolean var3, boolean var4) throws SQLException, IOException;

    public void writeNext(String[] var1, boolean var2);

    default public void writeNext(String[] nextLine) {
        this.writeNext(nextLine, true);
    }

    public boolean checkError();

    public IOException getException();

    public void resetError();

    public void setResultService(ResultSetHelper var1);

    default public void flushQuietly() {
        try {
            this.flush();
        }
        catch (IOException iOException) {
            // empty catch block
        }
    }
}

