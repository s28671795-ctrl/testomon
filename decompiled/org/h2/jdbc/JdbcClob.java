/*
 * Decompiled with CFR 0.152.
 */
package org.h2.jdbc;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.sql.Clob;
import java.sql.NClob;
import java.sql.SQLException;
import org.h2.jdbc.JdbcConnection;
import org.h2.jdbc.JdbcLob;
import org.h2.message.DbException;
import org.h2.store.RangeReader;
import org.h2.util.IOUtils;
import org.h2.value.Value;

public final class JdbcClob
extends JdbcLob
implements NClob {
    public JdbcClob(JdbcConnection jdbcConnection, Value value, JdbcLob.State state, int n) {
        super(jdbcConnection, value, state, 10, n);
    }

    @Override
    public long length() throws SQLException {
        try {
            long l;
            this.debugCodeCall("length");
            this.checkReadable();
            if (this.value.getValueType() == 3 && (l = this.value.getType().getPrecision()) > 0L) {
                return l;
            }
            return IOUtils.copyAndCloseInput(this.value.getReader(), null, Long.MAX_VALUE);
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
    public InputStream getAsciiStream() throws SQLException {
        try {
            this.debugCodeCall("getAsciiStream");
            this.checkReadable();
            String string = this.value.getString();
            return IOUtils.getInputStreamFromString(string);
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    @Override
    public OutputStream setAsciiStream(long l) throws SQLException {
        throw this.unsupported("LOB update");
    }

    @Override
    public Reader getCharacterStream() throws SQLException {
        return super.getCharacterStream();
    }

    @Override
    public Writer setCharacterStream(long l) throws SQLException {
        try {
            if (this.isDebugEnabled()) {
                this.debugCodeCall("setCharacterStream", l);
            }
            this.checkEditable();
            if (l != 1L) {
                throw DbException.getInvalidValueException("pos", l);
            }
            this.state = JdbcLob.State.SET_CALLED;
            return this.setCharacterStreamImpl();
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    @Override
    public String getSubString(long l, int n) throws SQLException {
        try {
            if (this.isDebugEnabled()) {
                this.debugCode("getSubString(" + l + ", " + n + ')');
            }
            this.checkReadable();
            if (l < 1L) {
                throw DbException.getInvalidValueException("pos", l);
            }
            if (n < 0) {
                throw DbException.getInvalidValueException("length", n);
            }
            StringWriter stringWriter = new StringWriter(Math.min(4096, n));
            try (Reader reader = this.value.getReader();){
                IOUtils.skipFully(reader, l - 1L);
                IOUtils.copyAndCloseInput(reader, stringWriter, n);
            }
            return stringWriter.toString();
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    @Override
    public int setString(long l, String string) throws SQLException {
        try {
            if (this.isDebugEnabled()) {
                this.debugCode("setString(" + l + ", " + JdbcClob.quote(string) + ')');
            }
            this.checkEditable();
            if (l != 1L) {
                throw DbException.getInvalidValueException("pos", l);
            }
            if (string == null) {
                throw DbException.getInvalidValueException("str", string);
            }
            this.completeWrite(this.conn.createClob(new StringReader(string), -1L));
            return string.length();
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    @Override
    public int setString(long l, String string, int n, int n2) throws SQLException {
        try {
            if (this.isDebugEnabled()) {
                this.debugCode("setString(" + l + ", " + JdbcClob.quote(string) + ", " + n + ", " + n2 + ')');
            }
            this.checkEditable();
            if (l != 1L) {
                throw DbException.getInvalidValueException("pos", l);
            }
            if (string == null) {
                throw DbException.getInvalidValueException("str", string);
            }
            this.completeWrite(this.conn.createClob(new RangeReader(new StringReader(string), n, n2), -1L));
            return (int)this.value.getType().getPrecision();
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    @Override
    public long position(String string, long l) throws SQLException {
        throw this.unsupported("LOB search");
    }

    @Override
    public long position(Clob clob, long l) throws SQLException {
        throw this.unsupported("LOB search");
    }

    @Override
    public Reader getCharacterStream(long l, long l2) throws SQLException {
        try {
            if (this.isDebugEnabled()) {
                this.debugCode("getCharacterStream(" + l + ", " + l2 + ')');
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
            return this.value.getReader(l, l2);
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }
}

