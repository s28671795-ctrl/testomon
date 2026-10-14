/*
 * Decompiled with CFR 0.152.
 */
package org.h2.jdbc;

import java.io.IOException;
import java.io.InputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.io.Reader;
import java.io.Writer;
import java.sql.SQLException;
import org.h2.jdbc.JdbcConnection;
import org.h2.message.DbException;
import org.h2.message.TraceObject;
import org.h2.mvstore.DataUtils;
import org.h2.util.IOUtils;
import org.h2.util.Task;
import org.h2.value.Value;

public abstract class JdbcLob
extends TraceObject {
    final JdbcConnection conn;
    Value value;
    State state;

    JdbcLob(JdbcConnection jdbcConnection, Value value, State state, int n, int n2) {
        this.setTrace(jdbcConnection.getSession().getTrace(), n, n2);
        this.conn = jdbcConnection;
        this.value = value;
        this.state = state;
    }

    void checkClosed() {
        this.conn.checkClosed();
        if (this.state == State.CLOSED) {
            throw DbException.get(90007);
        }
    }

    void checkEditable() {
        this.checkClosed();
        if (this.state != State.NEW) {
            throw DbException.getUnsupportedException("Allocate a new object to set its value.");
        }
    }

    void checkReadable() throws SQLException, IOException {
        this.checkClosed();
        if (this.state == State.SET_CALLED) {
            throw DbException.getUnsupportedException("Stream setter is not yet closed.");
        }
    }

    void completeWrite(Value value) {
        this.checkClosed();
        this.state = State.WITH_VALUE;
        this.value = value;
    }

    public void free() {
        this.debugCodeCall("free");
        this.state = State.CLOSED;
        this.value = null;
    }

    InputStream getBinaryStream() throws SQLException {
        try {
            this.debugCodeCall("getBinaryStream");
            this.checkReadable();
            return this.value.getInputStream();
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    Reader getCharacterStream() throws SQLException {
        try {
            this.debugCodeCall("getCharacterStream");
            this.checkReadable();
            return this.value.getReader();
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    Writer setCharacterStreamImpl() throws IOException {
        return IOUtils.getBufferedWriter(this.setClobOutputStreamImpl());
    }

    LobPipedOutputStream setClobOutputStreamImpl() throws IOException {
        final PipedInputStream pipedInputStream2 = new PipedInputStream();
        Task task = new Task(){

            @Override
            public void call() {
                JdbcLob.this.completeWrite(JdbcLob.this.conn.createClob(IOUtils.getReader(pipedInputStream2), -1L));
            }
        };
        LobPipedOutputStream lobPipedOutputStream = new LobPipedOutputStream(pipedInputStream2, task);
        task.execute();
        return lobPipedOutputStream;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder().append(this.getTraceObjectName()).append(": ");
        if (this.state == State.SET_CALLED) {
            stringBuilder.append("<setter_in_progress>");
        } else if (this.state == State.CLOSED) {
            stringBuilder.append("<closed>");
        } else {
            stringBuilder.append(this.value.getTraceSQL());
        }
        return stringBuilder.toString();
    }

    public static enum State {
        NEW,
        SET_CALLED,
        WITH_VALUE,
        CLOSED;

    }

    static final class LobPipedOutputStream
    extends PipedOutputStream {
        private final Task task;

        LobPipedOutputStream(PipedInputStream pipedInputStream2, Task task) throws IOException {
            super(pipedInputStream2);
            this.task = task;
        }

        @Override
        public void close() throws IOException {
            super.close();
            try {
                this.task.get();
            }
            catch (Exception exception) {
                throw DataUtils.convertToIOException(exception);
            }
        }
    }
}

