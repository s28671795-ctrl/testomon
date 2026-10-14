/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.tools.jdbc;

import java.sql.Array;
import java.sql.Blob;
import java.sql.CallableStatement;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.NClob;
import java.sql.PreparedStatement;
import java.sql.SQLClientInfoException;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.SQLXML;
import java.sql.Savepoint;
import java.sql.ShardingKey;
import java.sql.Statement;
import java.sql.Struct;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.Executor;
import org.jooq.tools.jdbc.JDBC41Connection;

public class DefaultConnection
extends JDBC41Connection
implements Connection {
    private final Connection delegate;

    public DefaultConnection(Connection delegate) {
        this.delegate = delegate;
    }

    public Connection getDelegate() {
        return this.delegate;
    }

    @Override
    public <T> T unwrap(Class<T> iface) throws SQLException {
        return this.getDelegate().unwrap(iface);
    }

    @Override
    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        return this.getDelegate().isWrapperFor(iface);
    }

    @Override
    public Statement createStatement() throws SQLException {
        return this.getDelegate().createStatement();
    }

    @Override
    public PreparedStatement prepareStatement(String sql) throws SQLException {
        return this.getDelegate().prepareStatement(sql);
    }

    @Override
    public CallableStatement prepareCall(String sql) throws SQLException {
        return this.getDelegate().prepareCall(sql);
    }

    @Override
    public String nativeSQL(String sql) throws SQLException {
        return this.getDelegate().nativeSQL(sql);
    }

    @Override
    public void setAutoCommit(boolean autoCommit) throws SQLException {
        this.getDelegate().setAutoCommit(autoCommit);
    }

    @Override
    public boolean getAutoCommit() throws SQLException {
        return this.getDelegate().getAutoCommit();
    }

    @Override
    public void commit() throws SQLException {
        this.getDelegate().commit();
    }

    @Override
    public void rollback() throws SQLException {
        this.getDelegate().rollback();
    }

    @Override
    public void close() throws SQLException {
        this.getDelegate().close();
    }

    @Override
    public boolean isClosed() throws SQLException {
        return this.getDelegate().isClosed();
    }

    @Override
    public DatabaseMetaData getMetaData() throws SQLException {
        return this.getDelegate().getMetaData();
    }

    @Override
    public void setReadOnly(boolean readOnly) throws SQLException {
        this.getDelegate().setReadOnly(readOnly);
    }

    @Override
    public boolean isReadOnly() throws SQLException {
        return this.getDelegate().isReadOnly();
    }

    @Override
    public void setCatalog(String catalog) throws SQLException {
        this.getDelegate().setCatalog(catalog);
    }

    @Override
    public String getCatalog() throws SQLException {
        return this.getDelegate().getCatalog();
    }

    @Override
    public void setTransactionIsolation(int level) throws SQLException {
        this.getDelegate().setTransactionIsolation(level);
    }

    @Override
    public int getTransactionIsolation() throws SQLException {
        return this.getDelegate().getTransactionIsolation();
    }

    @Override
    public SQLWarning getWarnings() throws SQLException {
        return this.getDelegate().getWarnings();
    }

    @Override
    public void clearWarnings() throws SQLException {
        this.getDelegate().clearWarnings();
    }

    @Override
    public Statement createStatement(int resultSetType, int resultSetConcurrency) throws SQLException {
        return this.getDelegate().createStatement(resultSetType, resultSetConcurrency);
    }

    @Override
    public PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency) throws SQLException {
        return this.getDelegate().prepareStatement(sql, resultSetType, resultSetConcurrency);
    }

    @Override
    public CallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurrency) throws SQLException {
        return this.getDelegate().prepareCall(sql, resultSetType, resultSetConcurrency);
    }

    @Override
    public Map<String, Class<?>> getTypeMap() throws SQLException {
        return this.getDelegate().getTypeMap();
    }

    @Override
    public void setTypeMap(Map<String, Class<?>> map) throws SQLException {
        this.getDelegate().setTypeMap(map);
    }

    @Override
    public void setHoldability(int holdability) throws SQLException {
        this.getDelegate().setHoldability(holdability);
    }

    @Override
    public int getHoldability() throws SQLException {
        return this.getDelegate().getHoldability();
    }

    @Override
    public Savepoint setSavepoint() throws SQLException {
        return this.getDelegate().setSavepoint();
    }

    @Override
    public Savepoint setSavepoint(String name) throws SQLException {
        return this.getDelegate().setSavepoint(name);
    }

    @Override
    public void rollback(Savepoint savepoint) throws SQLException {
        this.getDelegate().rollback(savepoint);
    }

    @Override
    public void releaseSavepoint(Savepoint savepoint) throws SQLException {
        this.getDelegate().releaseSavepoint(savepoint);
    }

    @Override
    public Statement createStatement(int resultSetType, int resultSetConcurrency, int resultSetHoldability) throws SQLException {
        return this.getDelegate().createStatement(resultSetType, resultSetConcurrency, resultSetHoldability);
    }

    @Override
    public PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency, int resultSetHoldability) throws SQLException {
        return this.getDelegate().prepareStatement(sql, resultSetType, resultSetConcurrency, resultSetHoldability);
    }

    @Override
    public CallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurrency, int resultSetHoldability) throws SQLException {
        return this.getDelegate().prepareCall(sql, resultSetType, resultSetConcurrency, resultSetHoldability);
    }

    @Override
    public PreparedStatement prepareStatement(String sql, int autoGeneratedKeys) throws SQLException {
        return this.getDelegate().prepareStatement(sql, autoGeneratedKeys);
    }

    @Override
    public PreparedStatement prepareStatement(String sql, int[] columnIndexes) throws SQLException {
        return this.getDelegate().prepareStatement(sql, columnIndexes);
    }

    @Override
    public PreparedStatement prepareStatement(String sql, String[] columnNames) throws SQLException {
        return this.getDelegate().prepareStatement(sql, columnNames);
    }

    @Override
    public Clob createClob() throws SQLException {
        return this.getDelegate().createClob();
    }

    @Override
    public Blob createBlob() throws SQLException {
        return this.getDelegate().createBlob();
    }

    @Override
    public NClob createNClob() throws SQLException {
        return this.getDelegate().createNClob();
    }

    @Override
    public SQLXML createSQLXML() throws SQLException {
        return this.getDelegate().createSQLXML();
    }

    @Override
    public boolean isValid(int timeout2) throws SQLException {
        return this.getDelegate().isValid(timeout2);
    }

    @Override
    public void setClientInfo(String name, String value) throws SQLClientInfoException {
        this.getDelegate().setClientInfo(name, value);
    }

    @Override
    public void setClientInfo(Properties properties) throws SQLClientInfoException {
        this.getDelegate().setClientInfo(properties);
    }

    @Override
    public String getClientInfo(String name) throws SQLException {
        return this.getDelegate().getClientInfo(name);
    }

    @Override
    public Properties getClientInfo() throws SQLException {
        return this.getDelegate().getClientInfo();
    }

    @Override
    public Array createArrayOf(String typeName, Object[] elements) throws SQLException {
        return this.getDelegate().createArrayOf(typeName, elements);
    }

    @Override
    public Struct createStruct(String typeName, Object[] attributes) throws SQLException {
        return this.getDelegate().createStruct(typeName, attributes);
    }

    @Override
    public void setSchema(String s2) throws SQLException {
        this.getDelegate().setSchema(s2);
    }

    @Override
    public String getSchema() throws SQLException {
        return this.getDelegate().getSchema();
    }

    @Override
    public void abort(Executor executor2) throws SQLException {
        this.getDelegate().abort(executor2);
    }

    @Override
    public void setNetworkTimeout(Executor executor2, int milliseconds) throws SQLException {
        this.getDelegate().setNetworkTimeout(executor2, milliseconds);
    }

    @Override
    public int getNetworkTimeout() throws SQLException {
        return this.getDelegate().getNetworkTimeout();
    }

    @Override
    public void beginRequest() throws SQLException {
        this.getDelegate().beginRequest();
    }

    @Override
    public void endRequest() throws SQLException {
        this.getDelegate().endRequest();
    }

    @Override
    public boolean setShardingKeyIfValid(ShardingKey shardingKey, ShardingKey superShardingKey, int timeout2) throws SQLException {
        return this.getDelegate().setShardingKeyIfValid(shardingKey, superShardingKey, timeout2);
    }

    @Override
    public boolean setShardingKeyIfValid(ShardingKey shardingKey, int timeout2) throws SQLException {
        return this.getDelegate().setShardingKeyIfValid(shardingKey, timeout2);
    }

    @Override
    public void setShardingKey(ShardingKey shardingKey, ShardingKey superShardingKey) throws SQLException {
        this.getDelegate().setShardingKey(shardingKey, superShardingKey);
    }

    @Override
    public void setShardingKey(ShardingKey shardingKey) throws SQLException {
        this.getDelegate().setShardingKey(shardingKey);
    }
}

