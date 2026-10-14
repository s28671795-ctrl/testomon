/*
 * Decompiled with CFR 0.152.
 */
package org.h2.jdbc;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PipedInputStream;
import java.sql.Blob;
import java.sql.SQLException;
import org.h2.jdbc.JdbcConnection;
import org.h2.jdbc.JdbcLob;
import org.h2.message.DbException;
import org.h2.util.IOUtils;
import org.h2.util.Task;
import org.h2.value.Value;

public final class JdbcBlob
extends JdbcLob
implements Blob {
    public JdbcBlob(JdbcConnection jdbcConnection, Value value, JdbcLob.State state, int n) {
        super(jdbcConnection, value, state, 9, n);
    }

    @Override
    public long length() throws SQLException {
        try {
            long l;
            this.debugCodeCall("length");
            this.checkReadable();
            if (this.value.getValueType() == 7 && (l = this.value.getType().getPrecision()) > 0L) {
                return l;
            }
            return IOUtils.copyAndCloseInput(this.value.getInputStream(), null);
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    @Override
    public void truncate(long l) throws SQLException {
        throw this.unsupported("LOB update");
    }

    @Override
    public byte[] getBytes(long l, int n) throws SQLException {
        try {
            if (this.isDebugEnabled()) {
                this.debugCode("getBytes(" + l + ", " + n + ')');
            }
            this.checkReadable();
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            try (InputStream inputStream2 = this.value.getInputStream();){
                IOUtils.skipFully(inputStream2, l - 1L);
                IOUtils.copy(inputStream2, (OutputStream)byteArrayOutputStream, (long)n);
            }
            return byteArrayOutputStream.toByteArray();
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    @Override
    public int setBytes(long l, byte[] byArray) throws SQLException {
        if (byArray == null) {
            throw new NullPointerException();
        }
        try {
            if (this.isDebugEnabled()) {
                this.debugCode("setBytes(" + l + ", " + JdbcBlob.quoteBytes(byArray) + ')');
            }
            this.checkEditable();
            if (l != 1L) {
                throw DbException.getInvalidValueException("pos", l);
            }
            this.completeWrite(this.conn.createBlob(new ByteArrayInputStream(byArray), -1L));
            return byArray.length;
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    @Override
    public int setBytes(long l, byte[] byArray, int n, int n2) throws SQLException {
        if (byArray == null) {
            throw new NullPointerException();
        }
        try {
            if (this.isDebugEnabled()) {
                this.debugCode("setBytes(" + l + ", " + JdbcBlob.quoteBytes(byArray) + ", " + n + ", " + n2 + ')');
            }
            this.checkEditable();
            if (l != 1L) {
                throw DbException.getInvalidValueException("pos", l);
            }
            this.completeWrite(this.conn.createBlob(new ByteArrayInputStream(byArray, n, n2), -1L));
            return (int)this.value.getType().getPrecision();
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    @Override
    public InputStream getBinaryStream() throws SQLException {
        return super.getBinaryStream();
    }

    @Override
    public OutputStream setBinaryStream(long l) throws SQLException {
        try {
            if (this.isDebugEnabled()) {
                this.debugCodeCall("setBinaryStream", l);
            }
            this.checkEditable();
            if (l != 1L) {
                throw DbException.getInvalidValueException("pos", l);
            }
            final PipedInputStream pipedInputStream2 = new PipedInputStream();
            Task task = new Task(){

                @Override
                public void call() {
                    JdbcBlob.this.completeWrite(JdbcBlob.this.conn.createBlob(pipedInputStream2, -1L));
                }
            };
            JdbcLob.LobPipedOutputStream lobPipedOutputStream = new JdbcLob.LobPipedOutputStream(pipedInputStream2, task);
            task.execute();
            this.state = JdbcLob.State.SET_CALLED;
            return new BufferedOutputStream(lobPipedOutputStream);
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    @Override
    public long position(byte[] byArray, long l) throws SQLException {
        if (this.isDebugEnabled()) {
            this.debugCode("position(" + JdbcBlob.quoteBytes(byArray) + ", " + l + ')');
        }
        throw this.unsupported("LOB search");
    }

    @Override
    public long position(Blob blob, long l) throws SQLException {
        if (this.isDebugEnabled()) {
            this.debugCode("position(blobPattern, " + l + ')');
        }
        throw this.unsupported("LOB subset");
    }

    @Override
    public InputStream getBinaryStream(long l, long l2) throws SQLException {
        try {
            if (this.isDebugEnabled()) {
                this.debugCode("getBinaryStream(" + l + ", " + l2 + ')');
            }
            this.checkReadable();
            if (this.state == JdbcLob.State.NEW) {
                if (l != 1L) {
                    throw DbException.getInvalidValueException("pos", l);
                }
                if (l2 != 0L) {
                    throw DbException.getInvalidValueException("length", l);
                }
            }
            return this.value.getInputStream(l, l2);
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }
}

