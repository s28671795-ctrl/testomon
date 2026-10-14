/*
 * Decompiled with CFR 0.152.
 */
package org.h2.message;

import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.Writer;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.util.Locale;
import java.util.concurrent.atomic.AtomicReferenceArray;
import org.h2.api.ErrorCode;
import org.h2.jdbc.JdbcException;
import org.h2.message.DbException;
import org.h2.message.Trace;
import org.h2.message.TraceWriter;
import org.h2.store.fs.FileUtils;
import org.h2.util.IOUtils;

public class TraceSystem
implements TraceWriter {
    public static final int PARENT = -1;
    public static final int OFF = 0;
    public static final int ERROR = 1;
    public static final int INFO = 2;
    public static final int DEBUG = 3;
    public static final int ADAPTER = 4;
    public static final int DEFAULT_TRACE_LEVEL_SYSTEM_OUT = 0;
    public static final int DEFAULT_TRACE_LEVEL_FILE = 1;
    private static final int DEFAULT_MAX_FILE_SIZE = 0x4000000;
    private static final int CHECK_SIZE_EACH_WRITES = 4096;
    private static DateTimeFormatter DATE_TIME_FORMATTER;
    private int levelSystemOut = 0;
    private int levelFile = 1;
    private int levelMax;
    private int maxFileSize = 0x4000000;
    private String fileName;
    private final AtomicReferenceArray<Trace> traces = new AtomicReferenceArray(Trace.MODULE_NAMES.length);
    private Writer fileWriter;
    private PrintWriter printWriter;
    private int checkSize = -1;
    private boolean closed;
    private boolean writingErrorLogged;
    private TraceWriter writer = this;
    private PrintStream sysOut = System.out;

    public TraceSystem(String string) {
        this.fileName = string;
        this.updateLevel();
    }

    private void updateLevel() {
        this.levelMax = Math.max(this.levelSystemOut, this.levelFile);
    }

    public void setSysOut(PrintStream printStream) {
        this.sysOut = printStream;
    }

    public Trace getTrace(int n) {
        Trace trace = this.traces.get(n);
        if (trace == null && !this.traces.compareAndSet(n, null, trace = new Trace(this.writer, n))) {
            trace = this.traces.get(n);
        }
        return trace;
    }

    public Trace getTrace(String string) {
        return new Trace(this.writer, string);
    }

    @Override
    public boolean isEnabled(int n) {
        if (this.levelMax == 4) {
            return this.writer.isEnabled(n);
        }
        return n <= this.levelMax;
    }

    public void setFileName(String string) {
        this.fileName = string;
    }

    public void setMaxFileSize(int n) {
        this.maxFileSize = n;
    }

    public void setLevelSystemOut(int n) {
        if (n < -1 || n > 3) {
            throw DbException.getInvalidValueException("TRACE_LEVEL_SYSTEM_OUT", n);
        }
        this.levelSystemOut = n;
        this.updateLevel();
    }

    public void setLevelFile(int n) {
        if (n == 4) {
            String string = "org.h2.message.TraceWriterAdapter";
            try {
                this.writer = (TraceWriter)Class.forName(string).getDeclaredConstructor(new Class[0]).newInstance(new Object[0]);
            }
            catch (Throwable throwable) {
                DbException dbException = DbException.get(90086, throwable, string);
                this.write(1, 2, string, (Throwable)dbException);
                return;
            }
            String string2 = this.fileName;
            if (string2 != null) {
                int n2;
                if (string2.endsWith(".trace.db")) {
                    string2 = string2.substring(0, string2.length() - ".trace.db".length());
                }
                if ((n2 = Math.max(string2.lastIndexOf(47), string2.lastIndexOf(92))) >= 0) {
                    string2 = string2.substring(n2 + 1);
                }
                this.writer.setName(string2);
            }
        } else if (n < -1 || n > 3) {
            throw DbException.getInvalidValueException("TRACE_LEVEL_FILE", n);
        }
        this.levelFile = n;
        this.updateLevel();
    }

    public int getLevelFile() {
        return this.levelFile;
    }

    private static String format(String string, String string2) {
        DateTimeFormatter dateTimeFormatter = DATE_TIME_FORMATTER;
        if (dateTimeFormatter == null) {
            dateTimeFormatter = TraceSystem.initTimeFormatter();
        }
        return dateTimeFormatter.format(OffsetDateTime.now()) + ' ' + string + ": " + string2;
    }

    private static DateTimeFormatter initTimeFormatter() {
        DATE_TIME_FORMATTER = new DateTimeFormatterBuilder().append(DateTimeFormatter.ISO_LOCAL_DATE).appendLiteral(' ').appendValue(ChronoField.HOUR_OF_DAY, 2).appendLiteral(':').appendValue(ChronoField.MINUTE_OF_HOUR, 2).appendLiteral(':').appendValue(ChronoField.SECOND_OF_MINUTE, 2).appendFraction(ChronoField.NANO_OF_SECOND, 6, 6, true).appendOffsetId().toFormatter(Locale.ROOT);
        return DATE_TIME_FORMATTER;
    }

    @Override
    public void write(int n, int n2, String string, Throwable throwable) {
        this.write(n, Trace.MODULE_NAMES[n2], string, throwable);
    }

    @Override
    public void write(int n, String string, String string2, Throwable throwable) {
        boolean bl;
        boolean bl2 = n <= this.levelSystemOut || n > this.levelMax;
        boolean bl3 = bl = this.fileName != null && n <= this.levelFile;
        if (bl2 || bl) {
            String string3 = TraceSystem.format(string, string2);
            if (bl2) {
                this.sysOut.println(string3);
                if (throwable != null && this.levelSystemOut == 3) {
                    throwable.printStackTrace(this.sysOut);
                }
            }
            if (bl) {
                this.writeFile(string3, throwable);
            }
        }
    }

    private synchronized void writeFile(String string, Throwable throwable) {
        try {
            Object object;
            this.checkSize = (this.checkSize + 1) % 4096;
            if (this.checkSize == 0) {
                this.closeWriter();
                if (this.maxFileSize > 0 && FileUtils.size(this.fileName) > (long)this.maxFileSize) {
                    object = this.fileName + ".old";
                    FileUtils.delete((String)object);
                    FileUtils.move(this.fileName, (String)object);
                }
            }
            if (!this.openWriter()) {
                return;
            }
            this.printWriter.println(string);
            if (throwable != null) {
                if (this.levelFile == 1 && throwable instanceof JdbcException) {
                    object = (JdbcException)((Object)throwable);
                    int n = object.getErrorCode();
                    if (ErrorCode.isCommon(n)) {
                        this.printWriter.println(throwable);
                    } else {
                        throwable.printStackTrace(this.printWriter);
                    }
                } else {
                    throwable.printStackTrace(this.printWriter);
                }
            }
            this.printWriter.flush();
            if (this.closed) {
                this.closeWriter();
            }
        }
        catch (Exception exception) {
            this.logWritingError(exception);
        }
    }

    private void logWritingError(Exception exception) {
        if (this.writingErrorLogged) {
            return;
        }
        this.writingErrorLogged = true;
        DbException dbException = DbException.get(90034, exception, this.fileName, exception.toString());
        this.fileName = null;
        this.sysOut.println(dbException);
        dbException.printStackTrace();
    }

    private boolean openWriter() {
        if (this.printWriter == null) {
            try {
                FileUtils.createDirectories(FileUtils.getParent(this.fileName));
                if (FileUtils.exists(this.fileName) && !FileUtils.canWrite(this.fileName)) {
                    return false;
                }
                this.fileWriter = IOUtils.getBufferedWriter(FileUtils.newOutputStream(this.fileName, true));
                this.printWriter = new PrintWriter(this.fileWriter, true);
            }
            catch (Exception exception) {
                this.logWritingError(exception);
                return false;
            }
        }
        return true;
    }

    private synchronized void closeWriter() {
        if (this.printWriter != null) {
            this.printWriter.flush();
            this.printWriter.close();
            this.printWriter = null;
        }
        if (this.fileWriter != null) {
            try {
                this.fileWriter.close();
            }
            catch (IOException iOException) {
                // empty catch block
            }
            this.fileWriter = null;
        }
    }

    public void close() {
        this.closeWriter();
        this.closed = true;
    }

    @Override
    public void setName(String string) {
    }
}

