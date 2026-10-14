/*
 * Decompiled with CFR 0.152.
 */
package org.apache.commons.csv;

import java.io.Closeable;
import java.io.Flushable;
import java.io.IOException;
import java.sql.Clob;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Stream;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.IOUtils;

public final class CSVPrinter
implements Flushable,
Closeable {
    private final Appendable appendable;
    private final CSVFormat format;
    private boolean newRecord = true;

    public CSVPrinter(Appendable appendable, CSVFormat format) throws IOException {
        Objects.requireNonNull(appendable, "appendable");
        Objects.requireNonNull(format, "format");
        this.appendable = appendable;
        this.format = format.copy();
        String[] headerComments = format.getHeaderComments();
        if (headerComments != null) {
            for (String line : headerComments) {
                this.printComment(line);
            }
        }
        if (format.getHeader() != null && !format.getSkipHeaderRecord()) {
            this.printRecord(format.getHeader());
        }
    }

    @Override
    public void close() throws IOException {
        this.close(false);
    }

    public void close(boolean flush) throws IOException {
        if (flush || this.format.getAutoFlush()) {
            this.flush();
        }
        if (this.appendable instanceof Closeable) {
            ((Closeable)((Object)this.appendable)).close();
        }
    }

    @Override
    public void flush() throws IOException {
        if (this.appendable instanceof Flushable) {
            ((Flushable)((Object)this.appendable)).flush();
        }
    }

    public Appendable getOut() {
        return this.appendable;
    }

    public synchronized void print(Object value) throws IOException {
        this.format.print(value, this.appendable, this.newRecord);
        this.newRecord = false;
    }

    public synchronized void printComment(String comment) throws IOException {
        if (comment == null || !this.format.isCommentMarkerSet()) {
            return;
        }
        if (!this.newRecord) {
            this.println();
        }
        this.appendable.append(this.format.getCommentMarker().charValue());
        this.appendable.append(' ');
        block4: for (int i = 0; i < comment.length(); ++i) {
            char c = comment.charAt(i);
            switch (c) {
                case '\r': {
                    if (i + 1 < comment.length() && comment.charAt(i + 1) == '\n') {
                        ++i;
                    }
                }
                case '\n': {
                    this.println();
                    this.appendable.append(this.format.getCommentMarker().charValue());
                    this.appendable.append(' ');
                    continue block4;
                }
                default: {
                    this.appendable.append(c);
                }
            }
        }
        this.println();
    }

    public synchronized void printHeaders(ResultSet resultSet) throws IOException, SQLException {
        this.printRecord(this.format.builder().setHeader(resultSet).build().getHeader());
    }

    public synchronized void println() throws IOException {
        this.format.println(this.appendable);
        this.newRecord = true;
    }

    public synchronized void printRecord(Iterable<?> values2) throws IOException {
        for (Object value : values2) {
            this.print(value);
        }
        this.println();
    }

    public void printRecord(Object ... values2) throws IOException {
        this.printRecord(Arrays.asList(values2));
    }

    public synchronized void printRecord(Stream<?> values2) throws IOException {
        values2.forEachOrdered(t2 -> {
            try {
                this.print(t2);
            }
            catch (IOException e) {
                throw IOUtils.rethrow(e);
            }
        });
        this.println();
    }

    private void printRecordObject(Object value) throws IOException {
        if (value instanceof Object[]) {
            this.printRecord((Object[])value);
        } else if (value instanceof Iterable) {
            this.printRecord((Iterable)value);
        } else {
            this.printRecord(value);
        }
    }

    public void printRecords(Iterable<?> values2) throws IOException {
        for (Object value : values2) {
            this.printRecordObject(value);
        }
    }

    public void printRecords(Object ... values2) throws IOException {
        this.printRecords(Arrays.asList(values2));
    }

    public void printRecords(ResultSet resultSet) throws SQLException, IOException {
        int columnCount = resultSet.getMetaData().getColumnCount();
        while (resultSet.next()) {
            for (int i = 1; i <= columnCount; ++i) {
                Object object = resultSet.getObject(i);
                this.print(object instanceof Clob ? ((Clob)object).getCharacterStream() : object);
            }
            this.println();
        }
    }

    public void printRecords(ResultSet resultSet, boolean printHeader) throws SQLException, IOException {
        if (printHeader) {
            this.printHeaders(resultSet);
        }
        this.printRecords(resultSet);
    }

    public void printRecords(Stream<?> values2) throws IOException {
        values2.forEachOrdered(t2 -> {
            try {
                this.printRecordObject(t2);
            }
            catch (IOException e) {
                throw IOUtils.rethrow(e);
            }
        });
    }
}

