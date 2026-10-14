/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.jetty.server;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.TimeZone;
import org.eclipse.jetty.server.RequestLog;
import org.eclipse.jetty.util.RolloverFileOutputStream;
import org.eclipse.jetty.util.annotation.ManagedAttribute;
import org.eclipse.jetty.util.annotation.ManagedObject;
import org.eclipse.jetty.util.component.AbstractLifeCycle;
import org.eclipse.jetty.util.thread.AutoLock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ManagedObject(value="Request Log writer which writes to file")
public class RequestLogWriter
extends AbstractLifeCycle
implements RequestLog.Writer {
    private static final Logger LOG = LoggerFactory.getLogger(RequestLogWriter.class);
    private final AutoLock _lock = new AutoLock();
    private String _filename;
    private boolean _append;
    private int _retainDays;
    private boolean _closeOut;
    private String _timeZone = "GMT";
    private String _filenameDateFormat = null;
    private transient OutputStream _out;
    private transient OutputStream _fileOut;
    private transient Writer _writer;

    public RequestLogWriter() {
        this(null);
    }

    public RequestLogWriter(String filename) {
        this.setAppend(true);
        this.setRetainDays(31);
        if (filename != null) {
            this.setFilename(filename);
        }
    }

    public void setFilename(String filename) {
        if (filename != null && (filename = filename.trim()).length() == 0) {
            filename = null;
        }
        this._filename = filename;
    }

    @ManagedAttribute(value="filename")
    public String getFileName() {
        return this._filename;
    }

    @ManagedAttribute(value="dated filename")
    public String getDatedFilename() {
        if (this._fileOut instanceof RolloverFileOutputStream) {
            return ((RolloverFileOutputStream)this._fileOut).getDatedFilename();
        }
        return null;
    }

    public void setRetainDays(int retainDays) {
        this._retainDays = retainDays;
    }

    @ManagedAttribute(value="number of days to keep a log file")
    public int getRetainDays() {
        return this._retainDays;
    }

    public void setAppend(boolean append) {
        this._append = append;
    }

    @ManagedAttribute(value="if request log file will be appended after restart")
    public boolean isAppend() {
        return this._append;
    }

    public void setFilenameDateFormat(String logFileDateFormat) {
        this._filenameDateFormat = logFileDateFormat;
    }

    @ManagedAttribute(value="log file name date format")
    public String getFilenameDateFormat() {
        return this._filenameDateFormat;
    }

    @Override
    public void write(String requestEntry) throws IOException {
        try (AutoLock l = this._lock.lock();){
            if (this._writer == null) {
                return;
            }
            this._writer.write(requestEntry);
            this._writer.write(System.lineSeparator());
            this._writer.flush();
        }
    }

    @Override
    protected void doStart() throws Exception {
        try (AutoLock l = this._lock.lock();){
            if (this._filename != null) {
                this._fileOut = new RolloverFileOutputStream(this._filename, this._append, this._retainDays, TimeZone.getTimeZone(this.getTimeZone()), this._filenameDateFormat, null);
                this._closeOut = true;
                LOG.info("Opened {}", (Object)this.getDatedFilename());
            } else {
                this._fileOut = System.err;
            }
            this._out = this._fileOut;
            this._writer = new OutputStreamWriter(this._out);
            super.doStart();
        }
    }

    public void setTimeZone(String timeZone) {
        this._timeZone = timeZone;
    }

    @ManagedAttribute(value="timezone of the log")
    public String getTimeZone() {
        return this._timeZone;
    }

    @Override
    protected void doStop() throws Exception {
        try (AutoLock ignored = this._lock.lock();){
            super.doStop();
            try {
                if (this._writer != null) {
                    this._writer.flush();
                }
            }
            catch (IOException e) {
                LOG.trace("IGNORED", e);
            }
            if (this._out != null && this._closeOut) {
                try {
                    this._out.close();
                }
                catch (IOException e) {
                    LOG.trace("IGNORED", e);
                }
            }
            this._out = null;
            this._fileOut = null;
            this._closeOut = false;
            this._writer = null;
        }
    }
}

