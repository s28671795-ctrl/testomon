/*
 * Decompiled with CFR 0.152.
 */
package org.h2.jdbcx;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Logger;
import javax.naming.Reference;
import javax.naming.Referenceable;
import javax.naming.StringRefAddr;
import javax.sql.ConnectionPoolDataSource;
import javax.sql.DataSource;
import javax.sql.PooledConnection;
import javax.sql.XAConnection;
import javax.sql.XADataSource;
import org.h2.jdbc.JdbcConnection;
import org.h2.jdbcx.JdbcDataSourceBackwardsCompat;
import org.h2.jdbcx.JdbcDataSourceFactory;
import org.h2.jdbcx.JdbcXAConnection;
import org.h2.message.DbException;
import org.h2.message.TraceObject;
import org.h2.util.StringUtils;

public final class JdbcDataSource
extends TraceObject
implements XADataSource,
DataSource,
ConnectionPoolDataSource,
Serializable,
Referenceable,
JdbcDataSourceBackwardsCompat {
    private static final long serialVersionUID = 1288136338451857771L;
    private transient JdbcDataSourceFactory factory;
    private transient PrintWriter logWriter;
    private int loginTimeout;
    private String userName = "";
    private char[] passwordChars = new char[0];
    private String url = "";
    private String description;

    public JdbcDataSource() {
        this.initFactory();
        int n = JdbcDataSource.getNextId(12);
        this.setTrace(this.factory.getTrace(), 12, n);
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        this.initFactory();
        objectInputStream.defaultReadObject();
    }

    private void initFactory() {
        this.factory = new JdbcDataSourceFactory();
    }

    @Override
    public int getLoginTimeout() {
        this.debugCodeCall("getLoginTimeout");
        return this.loginTimeout;
    }

    @Override
    public void setLoginTimeout(int n) {
        this.debugCodeCall("setLoginTimeout", n);
        this.loginTimeout = n;
    }

    @Override
    public PrintWriter getLogWriter() {
        this.debugCodeCall("getLogWriter");
        return this.logWriter;
    }

    @Override
    public void setLogWriter(PrintWriter printWriter) {
        this.debugCodeCall("setLogWriter(out)");
        this.logWriter = printWriter;
    }

    @Override
    public Connection getConnection() throws SQLException {
        this.debugCodeCall("getConnection");
        return new JdbcConnection(this.url, null, this.userName, StringUtils.cloneCharArray(this.passwordChars), false);
    }

    @Override
    public Connection getConnection(String string, String string2) throws SQLException {
        if (this.isDebugEnabled()) {
            this.debugCode("getConnection(" + JdbcDataSource.quote(string) + ", \"\")");
        }
        return new JdbcConnection(this.url, null, string, string2, false);
    }

    public String getURL() {
        this.debugCodeCall("getURL");
        return this.url;
    }

    public void setURL(String string) {
        this.debugCodeCall("setURL", string);
        this.url = string;
    }

    public String getUrl() {
        this.debugCodeCall("getUrl");
        return this.url;
    }

    public void setUrl(String string) {
        this.debugCodeCall("setUrl", string);
        this.url = string;
    }

    public void setPassword(String string) {
        this.debugCodeCall("setPassword", "");
        this.passwordChars = string == null ? null : string.toCharArray();
    }

    public void setPasswordChars(char[] cArray) {
        if (this.isDebugEnabled()) {
            this.debugCode("setPasswordChars(new char[0])");
        }
        this.passwordChars = cArray;
    }

    private static String convertToString(char[] cArray) {
        return cArray == null ? null : new String(cArray);
    }

    public String getPassword() {
        this.debugCodeCall("getPassword");
        return JdbcDataSource.convertToString(this.passwordChars);
    }

    public String getUser() {
        this.debugCodeCall("getUser");
        return this.userName;
    }

    public void setUser(String string) {
        this.debugCodeCall("setUser", string);
        this.userName = string;
    }

    public String getDescription() {
        this.debugCodeCall("getDescription");
        return this.description;
    }

    public void setDescription(String string) {
        this.debugCodeCall("getDescription", string);
        this.description = string;
    }

    @Override
    public Reference getReference() {
        this.debugCodeCall("getReference");
        String string = JdbcDataSourceFactory.class.getName();
        Reference reference = new Reference(this.getClass().getName(), string, null);
        reference.add(new StringRefAddr("url", this.url));
        reference.add(new StringRefAddr("user", this.userName));
        reference.add(new StringRefAddr("password", JdbcDataSource.convertToString(this.passwordChars)));
        reference.add(new StringRefAddr("loginTimeout", Integer.toString(this.loginTimeout)));
        reference.add(new StringRefAddr("description", this.description));
        return reference;
    }

    @Override
    public XAConnection getXAConnection() throws SQLException {
        this.debugCodeCall("getXAConnection");
        return new JdbcXAConnection(this.factory, JdbcDataSource.getNextId(13), new JdbcConnection(this.url, null, this.userName, StringUtils.cloneCharArray(this.passwordChars), false));
    }

    @Override
    public XAConnection getXAConnection(String string, String string2) throws SQLException {
        if (this.isDebugEnabled()) {
            this.debugCode("getXAConnection(" + JdbcDataSource.quote(string) + ", \"\")");
        }
        return new JdbcXAConnection(this.factory, JdbcDataSource.getNextId(13), new JdbcConnection(this.url, null, string, string2, false));
    }

    @Override
    public PooledConnection getPooledConnection() throws SQLException {
        this.debugCodeCall("getPooledConnection");
        return this.getXAConnection();
    }

    @Override
    public PooledConnection getPooledConnection(String string, String string2) throws SQLException {
        if (this.isDebugEnabled()) {
            this.debugCode("getPooledConnection(" + JdbcDataSource.quote(string) + ", \"\")");
        }
        return this.getXAConnection(string, string2);
    }

    @Override
    public <T> T unwrap(Class<T> clazz) throws SQLException {
        try {
            if (this.isWrapperFor(clazz)) {
                return (T)this;
            }
            throw DbException.getInvalidValueException("iface", clazz);
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    @Override
    public boolean isWrapperFor(Class<?> clazz) throws SQLException {
        return clazz != null && clazz.isAssignableFrom(this.getClass());
    }

    @Override
    public Logger getParentLogger() {
        return null;
    }

    public String toString() {
        return this.getTraceObjectName() + ": url=" + this.url + " user=" + this.userName;
    }
}

