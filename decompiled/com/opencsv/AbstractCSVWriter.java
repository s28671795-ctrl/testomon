/*
 * Decompiled with CFR 0.152.
 */
package com.opencsv;

import com.opencsv.ICSVWriter;
import com.opencsv.ResultSetHelper;
import com.opencsv.ResultSetHelperService;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class AbstractCSVWriter
implements ICSVWriter {
    protected final Writer writer;
    protected String lineEnd;
    protected ResultSetHelper resultService;
    protected volatile IOException exception;

    protected AbstractCSVWriter(Writer writer, String lineEnd) {
        this.writer = writer;
        this.lineEnd = lineEnd;
    }

    @Override
    public void writeAll(Iterable<String[]> allLines, boolean applyQuotesToAll) {
        StringBuilder sb = new StringBuilder(1024);
        try {
            for (String[] line : allLines) {
                this.writeNext(line, applyQuotesToAll, sb);
                sb.setLength(0);
            }
        }
        catch (IOException e) {
            this.exception = e;
        }
    }

    protected void writeColumnNames(ResultSet rs, boolean applyQuotesToAll) throws SQLException {
        this.writeNext(this.resultService().getColumnNames(rs), applyQuotesToAll);
    }

    @Override
    public int writeAll(ResultSet rs, boolean includeColumnNames, boolean trim, boolean applyQuotesToAll) throws SQLException, IOException {
        int linesWritten = 0;
        if (includeColumnNames) {
            this.writeColumnNames(rs, applyQuotesToAll);
            ++linesWritten;
        }
        while (rs.next()) {
            this.writeNext(this.resultService().getColumnValues(rs, trim), applyQuotesToAll);
            ++linesWritten;
        }
        return linesWritten;
    }

    @Override
    public void writeNext(String[] nextLine, boolean applyQuotesToAll) {
        try {
            this.writeNext(nextLine, applyQuotesToAll, new StringBuilder(1024));
        }
        catch (IOException e) {
            this.exception = e;
        }
    }

    protected abstract void writeNext(String[] var1, boolean var2, Appendable var3) throws IOException;

    @Override
    public void flush() throws IOException {
        this.writer.flush();
    }

    @Override
    public void close() throws IOException {
        this.flush();
        this.writer.close();
    }

    @Override
    public boolean checkError() {
        if (this.writer instanceof PrintWriter) {
            PrintWriter pw = (PrintWriter)this.writer;
            return pw.checkError();
        }
        if (this.exception != null) {
            this.flushQuietly();
        } else {
            try {
                this.flush();
            }
            catch (IOException ioe) {
                this.exception = ioe;
            }
        }
        return this.exception != null;
    }

    @Override
    public IOException getException() {
        return this.exception;
    }

    @Override
    public void resetError() {
        this.exception = null;
    }

    @Override
    public void setResultService(ResultSetHelper resultService) {
        this.resultService = resultService;
    }

    protected ResultSetHelper resultService() {
        if (this.resultService == null) {
            this.resultService = new ResultSetHelperService();
        }
        return this.resultService;
    }
}

