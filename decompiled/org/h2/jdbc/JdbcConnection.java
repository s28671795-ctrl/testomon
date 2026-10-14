/*
 * Decompiled with CFR 0.152.
 */
package org.h2.jdbc;

import java.io.InputStream;
import java.io.Reader;
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
import java.sql.Statement;
import java.sql.Struct;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;
import java.util.concurrent.Executor;
import java.util.concurrent.locks.ReentrantLock;
import java.util.regex.Pattern;
import org.h2.api.JavaObjectSerializer;
import org.h2.command.CommandInterface;
import org.h2.engine.CastDataProvider;
import org.h2.engine.ConnectionInfo;
import org.h2.engine.IsolationLevel;
import org.h2.engine.Mode;
import org.h2.engine.Session;
import org.h2.engine.SessionRemote;
import org.h2.engine.SysProperties;
import org.h2.jdbc.JdbcArray;
import org.h2.jdbc.JdbcBlob;
import org.h2.jdbc.JdbcCallableStatement;
import org.h2.jdbc.JdbcClob;
import org.h2.jdbc.JdbcConnectionBackwardsCompat;
import org.h2.jdbc.JdbcDatabaseMetaData;
import org.h2.jdbc.JdbcLob;
import org.h2.jdbc.JdbcPreparedStatement;
import org.h2.jdbc.JdbcSQLXML;
import org.h2.jdbc.JdbcSavepoint;
import org.h2.jdbc.JdbcStatement;
import org.h2.message.DbException;
import org.h2.message.TraceObject;
import org.h2.result.ResultInterface;
import org.h2.util.CloseWatcher;
import org.h2.util.TimeZoneProvider;
import org.h2.value.CompareMode;
import org.h2.value.Value;
import org.h2.value.ValueInteger;
import org.h2.value.ValueNull;
import org.h2.value.ValueTimestampTimeZone;
import org.h2.value.ValueToObjectConverter;
import org.h2.value.ValueVarbinary;
import org.h2.value.ValueVarchar;

public class JdbcConnection
extends TraceObject
implements Connection,
JdbcConnectionBackwardsCompat,
CastDataProvider {
    private static final String NUM_SERVERS = "numServers";
    private static final String PREFIX_SERVER = "server";
    private static boolean keepOpenStackTrace;
    private final ReentrantLock lock = new ReentrantLock();
    private final String url;
    private final String user;
    private int holdability = 1;
    private Session session;
    private CommandInterface commit;
    private CommandInterface rollback;
    private CommandInterface getReadOnly;
    private CommandInterface getGeneratedKeys;
    private CommandInterface setQueryTimeout;
    private CommandInterface getQueryTimeout;
    private int savepointId;
    private String catalog;
    private Statement executingStatement;
    private final CloseWatcher watcher;
    private int queryTimeoutCache = -1;
    private Map<String, String> clientInfo;

    public JdbcConnection(String string, Properties properties, String string2, Object object, boolean bl) throws SQLException {
        try {
            String string3;
            ConnectionInfo connectionInfo = new ConnectionInfo(string, properties, string2, object);
            if (bl) {
                connectionInfo.setProperty("FORBID_CREATION", "TRUE");
            }
            if ((string3 = SysProperties.getBaseDir()) != null) {
                connectionInfo.setBaseDir(string3);
            }
            this.session = new SessionRemote(connectionInfo).connectEmbeddedOrServer(false);
            this.setTrace(this.session.getTrace(), 1, JdbcConnection.getNextId(1));
            this.user = connectionInfo.getUserName();
            if (this.isInfoEnabled()) {
                this.trace.infoCode("Connection " + this.getTraceObjectName() + " = DriverManager.getConnection(" + JdbcConnection.quote(connectionInfo.getOriginalURL()) + ", " + JdbcConnection.quote(this.user) + ", \"\");");
            }
            this.url = connectionInfo.getURL();
            this.closeOld();
            this.watcher = CloseWatcher.register(this, this.session, keepOpenStackTrace);
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    public JdbcConnection(JdbcConnection jdbcConnection) {
        this.session = jdbcConnection.session;
        this.setTrace(this.session.getTrace(), 1, JdbcConnection.getNextId(1));
        this.user = jdbcConnection.user;
        this.url = jdbcConnection.url;
        this.catalog = jdbcConnection.catalog;
        this.commit = jdbcConnection.commit;
        this.getGeneratedKeys = jdbcConnection.getGeneratedKeys;
        this.getQueryTimeout = jdbcConnection.getQueryTimeout;
        this.getReadOnly = jdbcConnection.getReadOnly;
        this.rollback = jdbcConnection.rollback;
        this.watcher = null;
        if (jdbcConnection.clientInfo != null) {
            this.clientInfo = new HashMap<String, String>(jdbcConnection.clientInfo);
        }
    }

    public JdbcConnection(Session session, String string, String string2) {
        this.session = session;
        this.setTrace(session.getTrace(), 1, JdbcConnection.getNextId(1));
        this.user = string;
        this.url = string2;
        this.watcher = null;
    }

    protected final void lock() {
        this.lock.lock();
    }

    protected final void unlock() {
        this.lock.unlock();
    }

    private void closeOld() {
        CloseWatcher closeWatcher;
        while ((closeWatcher = CloseWatcher.pollUnclosed()) != null) {
            try {
                closeWatcher.getCloseable().close();
            }
            catch (Exception exception) {
                this.trace.error(exception, "closing session");
            }
            keepOpenStackTrace = true;
            String string = closeWatcher.getOpenStackTrace();
            DbException dbException = DbException.get(90018);
            this.trace.error(dbException, string);
        }
    }

    @Override
    public Statement createStatement() throws SQLException {
        try {
            int n = JdbcConnection.getNextId(8);
            this.debugCodeAssign("Statement", 8, n, "createStatement()");
            this.checkClosed();
            return new JdbcStatement(this, n, 1003, 1007);
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    @Override
    public Statement createStatement(int n, int n2) throws SQLException {
        try {
            int n3 = JdbcConnection.getNextId(8);
            if (this.isDebugEnabled()) {
                this.debugCodeAssign("Statement", 8, n3, "createStatement(" + n + ", " + n2 + ')');
            }
            JdbcConnection.checkTypeConcurrency(n, n2);
            this.checkClosed();
            return new JdbcStatement(this, n3, n, n2);
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    @Override
    public Statement createStatement(int n, int n2, int n3) throws SQLException {
        try {
            int n4 = JdbcConnection.getNextId(8);
            if (this.isDebugEnabled()) {
                this.debugCodeAssign("Statement", 8, n4, "createStatement(" + n + ", " + n2 + ", " + n3 + ')');
            }
            JdbcConnection.checkTypeConcurrency(n, n2);
            JdbcConnection.checkHoldability(n3);
            this.checkClosed();
            return new JdbcStatement(this, n4, n, n2);
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    @Override
    public PreparedStatement prepareStatement(String string) throws SQLException {
        try {
            int n = JdbcConnection.getNextId(3);
            if (this.isDebugEnabled()) {
                this.debugCodeAssign("PreparedStatement", 3, n, "prepareStatement(" + JdbcConnection.quote(string) + ')');
            }
            this.checkClosed();
            string = JdbcConnection.translateSQL(string);
            return new JdbcPreparedStatement(this, string, n, 1003, 1007, null);
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    @Override
    public DatabaseMetaData getMetaData() throws SQLException {
        try {
            int n = JdbcConnection.getNextId(2);
            this.debugCodeAssign("DatabaseMetaData", 2, n, "getMetaData()");
            this.checkClosed();
            return new JdbcDatabaseMetaData(this, this.trace, n);
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    public Session getSession() {
        return this.session;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void close() throws SQLException {
        block22: {
            this.lock();
            try {
                this.debugCodeCall("close");
                Session session = this.session;
                if (session == null) {
                    return;
                }
                CloseWatcher.unregister(this.watcher);
                session.cancel();
                session.lock();
                try {
                    if (this.executingStatement != null) {
                        try {
                            this.executingStatement.cancel();
                        }
                        catch (NullPointerException | SQLException exception) {
                            // empty catch block
                        }
                    }
                    try {
                        if (session.isClosed()) break block22;
                        try {
                            block23: {
                                if (session.hasPendingTransaction()) {
                                    try {
                                        this.rollbackInternal();
                                    }
                                    catch (DbException dbException) {
                                        if (dbException.getErrorCode() == 90067 || dbException.getErrorCode() == 90098) break block23;
                                        throw dbException;
                                    }
                                }
                            }
                            this.closePreparedCommands();
                        }
                        finally {
                            session.close();
                        }
                    }
                    finally {
                        this.session = null;
                    }
                }
                finally {
                    session.unlock();
                }
            }
            catch (Throwable throwable) {
                throw this.logAndConvert(throwable);
            }
            finally {
                this.unlock();
            }
        }
    }

    private void closePreparedCommands() {
        this.commit = JdbcConnection.closeAndSetNull(this.commit);
        this.rollback = JdbcConnection.closeAndSetNull(this.rollback);
        this.getReadOnly = JdbcConnection.closeAndSetNull(this.getReadOnly);
        this.getGeneratedKeys = JdbcConnection.closeAndSetNull(this.getGeneratedKeys);
        this.getQueryTimeout = JdbcConnection.closeAndSetNull(this.getQueryTimeout);
        this.setQueryTimeout = JdbcConnection.closeAndSetNull(this.setQueryTimeout);
    }

    private static CommandInterface closeAndSetNull(CommandInterface commandInterface) {
        if (commandInterface != null) {
            commandInterface.close();
        }
        return null;
    }

    @Override
    public void setAutoCommit(boolean bl) throws SQLException {
        this.lock();
        try {
            if (this.isDebugEnabled()) {
                this.debugCode("setAutoCommit(" + bl + ')');
            }
            this.checkClosed();
            Session session = this.session;
            session.lock();
            try {
                if (bl && !session.getAutoCommit()) {
                    this.commit();
                }
                session.setAutoCommit(bl);
            }
            finally {
                session.unlock();
            }
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
        finally {
            this.unlock();
        }
    }

    @Override
    public boolean getAutoCommit() throws SQLException {
        this.lock();
        try {
            this.checkClosed();
            this.debugCodeCall("getAutoCommit");
            boolean bl = this.session.getAutoCommit();
            return bl;
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
        finally {
            this.unlock();
        }
    }

    @Override
    public void commit() throws SQLException {
        this.lock();
        try {
            this.debugCodeCall("commit");
            this.checkClosed();
            if (SysProperties.FORCE_AUTOCOMMIT_OFF_ON_COMMIT && this.getAutoCommit()) {
                throw DbException.get(90147, "commit()");
            }
            this.commit = this.prepareCommand("COMMIT", this.commit);
            this.commit.executeUpdate(null);
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
        finally {
            this.unlock();
        }
    }

    @Override
    public void rollback() throws SQLException {
        this.lock();
        try {
            this.debugCodeCall("rollback");
            this.checkClosed();
            if (SysProperties.FORCE_AUTOCOMMIT_OFF_ON_COMMIT && this.getAutoCommit()) {
                throw DbException.get(90147, "rollback()");
            }
            this.rollbackInternal();
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
        finally {
            this.unlock();
        }
    }

    @Override
    public boolean isClosed() throws SQLException {
        try {
            this.debugCodeCall("isClosed");
            return this.session == null || this.session.isClosed();
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    @Override
    public String nativeSQL(String string) throws SQLException {
        try {
            this.debugCodeCall("nativeSQL", string);
            this.checkClosed();
            return JdbcConnection.translateSQL(string);
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    @Override
    public void setReadOnly(boolean bl) throws SQLException {
        try {
            if (this.isDebugEnabled()) {
                this.debugCode("setReadOnly(" + bl + ')');
            }
            this.checkClosed();
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    @Override
    public boolean isReadOnly() throws SQLException {
        try {
            this.debugCodeCall("isReadOnly");
            this.checkClosed();
            this.getReadOnly = this.prepareCommand("CALL READONLY()", this.getReadOnly);
            ResultInterface resultInterface = this.getReadOnly.executeQuery(0L, false);
            resultInterface.next();
            return resultInterface.currentRow()[0].getBoolean();
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    @Override
    public void setCatalog(String string) throws SQLException {
        try {
            this.debugCodeCall("setCatalog", string);
            this.checkClosed();
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    @Override
    public String getCatalog() throws SQLException {
        try {
            this.debugCodeCall("getCatalog");
            this.checkClosed();
            if (this.catalog == null) {
                CommandInterface commandInterface = this.prepareCommand("CALL DATABASE()", Integer.MAX_VALUE);
                ResultInterface resultInterface = commandInterface.executeQuery(0L, false);
                resultInterface.next();
                this.catalog = resultInterface.currentRow()[0].getString();
                commandInterface.close();
            }
            return this.catalog;
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    @Override
    public SQLWarning getWarnings() throws SQLException {
        try {
            this.debugCodeCall("getWarnings");
            this.checkClosed();
            return null;
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    @Override
    public void clearWarnings() throws SQLException {
        try {
            this.debugCodeCall("clearWarnings");
            this.checkClosed();
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    @Override
    public PreparedStatement prepareStatement(String string, int n, int n2) throws SQLException {
        try {
            int n3 = JdbcConnection.getNextId(3);
            if (this.isDebugEnabled()) {
                this.debugCodeAssign("PreparedStatement", 3, n3, "prepareStatement(" + JdbcConnection.quote(string) + ", " + n + ", " + n2 + ')');
            }
            JdbcConnection.checkTypeConcurrency(n, n2);
            this.checkClosed();
            string = JdbcConnection.translateSQL(string);
            return new JdbcPreparedStatement(this, string, n3, n, n2, null);
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    @Override
    public void setTransactionIsolation(int n) throws SQLException {
        try {
            this.debugCodeCall("setTransactionIsolation", n);
            this.checkClosed();
            if (!this.getAutoCommit()) {
                this.commit();
            }
            this.session.setIsolationLevel(IsolationLevel.fromJdbc(n));
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    void setQueryTimeout(int n) throws SQLException {
        try {
            this.debugCodeCall("setQueryTimeout", n);
            this.checkClosed();
            this.setQueryTimeout = this.prepareCommand("SET QUERY_TIMEOUT ?", this.setQueryTimeout);
            this.setQueryTimeout.getParameters().get(0).setValue(ValueInteger.get(n * 1000), false);
            this.setQueryTimeout.executeUpdate(null);
            this.queryTimeoutCache = n;
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    int getQueryTimeout() throws SQLException {
        try {
            if (this.queryTimeoutCache == -1) {
                this.checkClosed();
                this.getQueryTimeout = this.prepareCommand(!this.session.isOldInformationSchema() ? "SELECT SETTING_VALUE FROM INFORMATION_SCHEMA.SETTINGS WHERE SETTING_NAME=?" : "SELECT `VALUE` FROM INFORMATION_SCHEMA.SETTINGS WHERE NAME=?", this.getQueryTimeout);
                this.getQueryTimeout.getParameters().get(0).setValue(ValueVarchar.get("QUERY_TIMEOUT"), false);
                ResultInterface resultInterface = this.getQueryTimeout.executeQuery(0L, false);
                resultInterface.next();
                int n = resultInterface.currentRow()[0].getInt();
                resultInterface.close();
                if (n != 0) {
                    n = (n + 999) / 1000;
                }
                this.queryTimeoutCache = n;
            }
            return this.queryTimeoutCache;
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    @Override
    public int getTransactionIsolation() throws SQLException {
        try {
            this.debugCodeCall("getTransactionIsolation");
            this.checkClosed();
            return this.session.getIsolationLevel().getJdbc();
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    @Override
    public void setHoldability(int n) throws SQLException {
        try {
            this.debugCodeCall("setHoldability", n);
            this.checkClosed();
            JdbcConnection.checkHoldability(n);
            this.holdability = n;
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    @Override
    public int getHoldability() throws SQLException {
        try {
            this.debugCodeCall("getHoldability");
            this.checkClosed();
            return this.holdability;
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    @Override
    public Map<String, Class<?>> getTypeMap() throws SQLException {
        try {
            this.debugCodeCall("getTypeMap");
            this.checkClosed();
            return null;
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    @Override
    public void setTypeMap(Map<String, Class<?>> map) throws SQLException {
        try {
            if (this.isDebugEnabled()) {
                this.debugCode("setTypeMap(" + JdbcConnection.quoteMap(map) + ')');
            }
            JdbcConnection.checkMap(map);
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    @Override
    public CallableStatement prepareCall(String string) throws SQLException {
        try {
            int n = JdbcConnection.getNextId(0);
            if (this.isDebugEnabled()) {
                this.debugCodeAssign("CallableStatement", 0, n, "prepareCall(" + JdbcConnection.quote(string) + ')');
            }
            this.checkClosed();
            string = JdbcConnection.translateSQL(string);
            return new JdbcCallableStatement(this, string, n, 1003, 1007);
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    @Override
    public CallableStatement prepareCall(String string, int n, int n2) throws SQLException {
        try {
            int n3 = JdbcConnection.getNextId(0);
            if (this.isDebugEnabled()) {
                this.debugCodeAssign("CallableStatement", 0, n3, "prepareCall(" + JdbcConnection.quote(string) + ", " + n + ", " + n2 + ')');
            }
            JdbcConnection.checkTypeConcurrency(n, n2);
            this.checkClosed();
            string = JdbcConnection.translateSQL(string);
            return new JdbcCallableStatement(this, string, n3, n, n2);
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    @Override
    public CallableStatement prepareCall(String string, int n, int n2, int n3) throws SQLException {
        try {
            int n4 = JdbcConnection.getNextId(0);
            if (this.isDebugEnabled()) {
                this.debugCodeAssign("CallableStatement", 0, n4, "prepareCall(" + JdbcConnection.quote(string) + ", " + n + ", " + n2 + ", " + n3 + ')');
            }
            JdbcConnection.checkTypeConcurrency(n, n2);
            JdbcConnection.checkHoldability(n3);
            this.checkClosed();
            string = JdbcConnection.translateSQL(string);
            return new JdbcCallableStatement(this, string, n4, n, n2);
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    @Override
    public Savepoint setSavepoint() throws SQLException {
        try {
            int n = JdbcConnection.getNextId(6);
            this.debugCodeAssign("Savepoint", 6, n, "setSavepoint()");
            this.checkClosed();
            CommandInterface commandInterface = this.prepareCommand("SAVEPOINT " + JdbcSavepoint.getName(null, this.savepointId), Integer.MAX_VALUE);
            commandInterface.executeUpdate(null);
            JdbcSavepoint jdbcSavepoint = new JdbcSavepoint(this, this.savepointId, null, this.trace, n);
            ++this.savepointId;
            return jdbcSavepoint;
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    @Override
    public Savepoint setSavepoint(String string) throws SQLException {
        try {
            int n = JdbcConnection.getNextId(6);
            if (this.isDebugEnabled()) {
                this.debugCodeAssign("Savepoint", 6, n, "setSavepoint(" + JdbcConnection.quote(string) + ')');
            }
            this.checkClosed();
            CommandInterface commandInterface = this.prepareCommand("SAVEPOINT " + JdbcSavepoint.getName(string, 0), Integer.MAX_VALUE);
            commandInterface.executeUpdate(null);
            return new JdbcSavepoint(this, 0, string, this.trace, n);
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    @Override
    public void rollback(Savepoint savepoint) throws SQLException {
        try {
            JdbcSavepoint jdbcSavepoint = JdbcConnection.convertSavepoint(savepoint);
            if (this.isDebugEnabled()) {
                this.debugCode("rollback(" + jdbcSavepoint.getTraceObjectName() + ')');
            }
            this.checkClosed();
            jdbcSavepoint.rollback();
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    @Override
    public void releaseSavepoint(Savepoint savepoint) throws SQLException {
        try {
            this.debugCode("releaseSavepoint(savepoint)");
            this.checkClosed();
            JdbcConnection.convertSavepoint(savepoint).release();
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    private static JdbcSavepoint convertSavepoint(Savepoint savepoint) {
        if (!(savepoint instanceof JdbcSavepoint)) {
            throw DbException.get(90063, String.valueOf(savepoint));
        }
        return (JdbcSavepoint)savepoint;
    }

    @Override
    public PreparedStatement prepareStatement(String string, int n, int n2, int n3) throws SQLException {
        try {
            int n4 = JdbcConnection.getNextId(3);
            if (this.isDebugEnabled()) {
                this.debugCodeAssign("PreparedStatement", 3, n4, "prepareStatement(" + JdbcConnection.quote(string) + ", " + n + ", " + n2 + ", " + n3 + ')');
            }
            JdbcConnection.checkTypeConcurrency(n, n2);
            JdbcConnection.checkHoldability(n3);
            this.checkClosed();
            string = JdbcConnection.translateSQL(string);
            return new JdbcPreparedStatement(this, string, n4, n, n2, null);
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    @Override
    public PreparedStatement prepareStatement(String string, int n) throws SQLException {
        try {
            int n2 = JdbcConnection.getNextId(3);
            if (this.isDebugEnabled()) {
                this.debugCodeAssign("PreparedStatement", 3, n2, "prepareStatement(" + JdbcConnection.quote(string) + ", " + n + ')');
            }
            this.checkClosed();
            string = JdbcConnection.translateSQL(string);
            return new JdbcPreparedStatement(this, string, n2, 1003, 1007, n == 1);
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    @Override
    public PreparedStatement prepareStatement(String string, int[] nArray) throws SQLException {
        try {
            int n = JdbcConnection.getNextId(3);
            if (this.isDebugEnabled()) {
                this.debugCodeAssign("PreparedStatement", 3, n, "prepareStatement(" + JdbcConnection.quote(string) + ", " + JdbcConnection.quoteIntArray(nArray) + ')');
            }
            this.checkClosed();
            string = JdbcConnection.translateSQL(string);
            return new JdbcPreparedStatement(this, string, n, 1003, 1007, nArray);
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    @Override
    public PreparedStatement prepareStatement(String string, String[] stringArray) throws SQLException {
        try {
            int n = JdbcConnection.getNextId(3);
            if (this.isDebugEnabled()) {
                this.debugCodeAssign("PreparedStatement", 3, n, "prepareStatement(" + JdbcConnection.quote(string) + ", " + JdbcConnection.quoteArray(stringArray) + ')');
            }
            this.checkClosed();
            string = JdbcConnection.translateSQL(string);
            return new JdbcPreparedStatement(this, string, n, 1003, 1007, stringArray);
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    CommandInterface prepareCommand(String string, int n) {
        return this.session.prepareCommand(string, n);
    }

    private CommandInterface prepareCommand(String string, CommandInterface commandInterface) {
        return commandInterface == null ? this.session.prepareCommand(string, Integer.MAX_VALUE) : commandInterface;
    }

    private static int translateGetEnd(String string, int n, char c) {
        int n2 = string.length();
        switch (c) {
            case '$': {
                if (n < n2 - 1 && string.charAt(n + 1) == '$' && (n == 0 || string.charAt(n - 1) <= ' ')) {
                    int n3 = string.indexOf("$$", n + 2);
                    if (n3 < 0) {
                        throw DbException.getSyntaxError(string, n);
                    }
                    return n3 + 1;
                }
                return n;
            }
            case '\'': {
                int n4 = string.indexOf(39, n + 1);
                if (n4 < 0) {
                    throw DbException.getSyntaxError(string, n);
                }
                return n4;
            }
            case '\"': {
                int n5 = string.indexOf(34, n + 1);
                if (n5 < 0) {
                    throw DbException.getSyntaxError(string, n);
                }
                return n5;
            }
            case '/': {
                JdbcConnection.checkRunOver(n + 1, n2, string);
                if (string.charAt(n + 1) == '*') {
                    int n6 = string.indexOf("*/", n + 2);
                    if (n6 < 0) {
                        throw DbException.getSyntaxError(string, n);
                    }
                    n = n6 + 1;
                } else if (string.charAt(n + 1) == '/') {
                    n += 2;
                    while (n < n2 && (c = string.charAt(n)) != '\r' && c != '\n') {
                        ++n;
                    }
                }
                return n;
            }
            case '-': {
                JdbcConnection.checkRunOver(n + 1, n2, string);
                if (string.charAt(n + 1) == '-') {
                    n += 2;
                    while (n < n2 && (c = string.charAt(n)) != '\r' && c != '\n') {
                        ++n;
                    }
                }
                return n;
            }
        }
        throw DbException.getInternalError("c=" + c);
    }

    private static String translateSQL(String string) {
        return JdbcConnection.translateSQL(string, true);
    }

    static String translateSQL(String string, boolean bl) {
        if (string == null) {
            throw DbException.getInvalidValueException("SQL", null);
        }
        if (!bl || string.indexOf(123) < 0) {
            return string;
        }
        return JdbcConnection.translateSQLImpl(string);
    }

    private static String translateSQLImpl(String string) {
        int n = string.length();
        char[] cArray = null;
        int n2 = 0;
        block9: for (int i = 0; i < n; ++i) {
            char c = string.charAt(i);
            switch (c) {
                case '\"': 
                case '\'': 
                case '-': 
                case '/': {
                    i = JdbcConnection.translateGetEnd(string, i, c);
                    continue block9;
                }
                case '{': {
                    ++n2;
                    if (cArray == null) {
                        cArray = string.toCharArray();
                    }
                    cArray[i] = 32;
                    while (Character.isSpaceChar(cArray[i])) {
                        JdbcConnection.checkRunOver(++i, n, string);
                    }
                    int n3 = i;
                    if (cArray[i] >= '0' && cArray[i] <= '9') {
                        cArray[i - 1] = 123;
                        while (true) {
                            JdbcConnection.checkRunOver(i, n, string);
                            c = cArray[i];
                            if (c == '}') break;
                            switch (c) {
                                case '\"': 
                                case '\'': 
                                case '-': 
                                case '/': {
                                    i = JdbcConnection.translateGetEnd(string, i, c);
                                    break;
                                }
                            }
                            ++i;
                        }
                        --n2;
                        continue block9;
                    }
                    if (cArray[i] == '?') {
                        JdbcConnection.checkRunOver(++i, n, string);
                        while (Character.isSpaceChar(cArray[i])) {
                            JdbcConnection.checkRunOver(++i, n, string);
                        }
                        if (string.charAt(i) != '=') {
                            throw DbException.getSyntaxError(string, i, "=");
                        }
                        JdbcConnection.checkRunOver(++i, n, string);
                        while (Character.isSpaceChar(cArray[i])) {
                            JdbcConnection.checkRunOver(++i, n, string);
                        }
                    }
                    while (!Character.isSpaceChar(cArray[i])) {
                        JdbcConnection.checkRunOver(++i, n, string);
                    }
                    int n4 = 0;
                    if (JdbcConnection.found(string, n3, "fn")) {
                        n4 = 2;
                    } else {
                        if (JdbcConnection.found(string, n3, "escape") || JdbcConnection.found(string, n3, "call")) continue block9;
                        if (JdbcConnection.found(string, n3, "oj")) {
                            n4 = 2;
                        } else {
                            if (JdbcConnection.found(string, n3, "ts") || JdbcConnection.found(string, n3, "t") || JdbcConnection.found(string, n3, "d")) continue block9;
                            if (JdbcConnection.found(string, n3, "params")) {
                                n4 = "params".length();
                            }
                        }
                    }
                    i = n3;
                    while (n4 > 0) {
                        cArray[i] = 32;
                        ++i;
                        --n4;
                    }
                    continue block9;
                }
                case '}': {
                    if (--n2 < 0) {
                        throw DbException.getSyntaxError(string, i);
                    }
                    cArray[i] = 32;
                    continue block9;
                }
                case '$': {
                    i = JdbcConnection.translateGetEnd(string, i, c);
                    continue block9;
                }
            }
        }
        if (n2 != 0) {
            throw DbException.getSyntaxError(string, string.length() - 1);
        }
        if (cArray != null) {
            string = new String(cArray);
        }
        return string;
    }

    private static void checkRunOver(int n, int n2, String string) {
        if (n >= n2) {
            throw DbException.getSyntaxError(string, n);
        }
    }

    private static boolean found(String string, int n, String string2) {
        return string.regionMatches(true, n, string2, 0, string2.length());
    }

    private static void checkTypeConcurrency(int n, int n2) {
        switch (n) {
            case 1003: 
            case 1004: 
            case 1005: {
                break;
            }
            default: {
                throw DbException.getInvalidValueException("resultSetType", n);
            }
        }
        switch (n2) {
            case 1007: 
            case 1008: {
                break;
            }
            default: {
                throw DbException.getInvalidValueException("resultSetConcurrency", n2);
            }
        }
    }

    private static void checkHoldability(int n) {
        if (n != 1 && n != 2) {
            throw DbException.getInvalidValueException("resultSetHoldability", n);
        }
    }

    protected void checkClosed() {
        if (this.session == null) {
            throw DbException.get(90007);
        }
        if (this.session.isClosed()) {
            throw DbException.get(90121);
        }
    }

    String getURL() {
        this.checkClosed();
        return this.url;
    }

    String getUser() {
        this.checkClosed();
        return this.user;
    }

    private void rollbackInternal() {
        this.rollback = this.prepareCommand("ROLLBACK", this.rollback);
        this.rollback.executeUpdate(null);
    }

    void setExecutingStatement(Statement statement) {
        this.executingStatement = statement;
    }

    @Override
    public Clob createClob() throws SQLException {
        try {
            int n = JdbcConnection.getNextId(10);
            this.debugCodeAssign("Clob", 10, n, "createClob()");
            this.checkClosed();
            return new JdbcClob(this, ValueVarchar.EMPTY, JdbcLob.State.NEW, n);
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    @Override
    public Blob createBlob() throws SQLException {
        try {
            int n = JdbcConnection.getNextId(9);
            this.debugCodeAssign("Blob", 9, n, "createClob()");
            this.checkClosed();
            return new JdbcBlob(this, ValueVarbinary.EMPTY, JdbcLob.State.NEW, n);
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    @Override
    public NClob createNClob() throws SQLException {
        try {
            int n = JdbcConnection.getNextId(10);
            this.debugCodeAssign("NClob", 10, n, "createNClob()");
            this.checkClosed();
            return new JdbcClob(this, ValueVarchar.EMPTY, JdbcLob.State.NEW, n);
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    @Override
    public SQLXML createSQLXML() throws SQLException {
        try {
            int n = JdbcConnection.getNextId(17);
            this.debugCodeAssign("SQLXML", 17, n, "createSQLXML()");
            this.checkClosed();
            return new JdbcSQLXML(this, ValueVarchar.EMPTY, JdbcLob.State.NEW, n);
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    @Override
    public Array createArrayOf(String string, Object[] objectArray) throws SQLException {
        try {
            int n = JdbcConnection.getNextId(16);
            this.debugCodeAssign("Array", 16, n, "createArrayOf()");
            this.checkClosed();
            Value value = ValueToObjectConverter.objectToValue(this.session, objectArray, 40);
            return new JdbcArray(this, value, n);
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    @Override
    public Struct createStruct(String string, Object[] objectArray) throws SQLException {
        throw this.unsupported("Struct");
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public boolean isValid(int n) {
        this.lock();
        try {
            this.debugCodeCall("isValid", n);
            if (this.session == null || this.session.isClosed()) {
                boolean bl = false;
                return bl;
            }
            this.getTransactionIsolation();
            boolean bl = true;
            return bl;
        }
        catch (Exception exception) {
            this.logAndConvert(exception);
            boolean bl = false;
            return bl;
        }
        finally {
            this.unlock();
        }
    }

    @Override
    public void setClientInfo(String string, String string2) throws SQLClientInfoException {
        try {
            if (this.isDebugEnabled()) {
                this.debugCode("setClientInfo(" + JdbcConnection.quote(string) + ", " + JdbcConnection.quote(string2) + ')');
            }
            this.checkClosed();
            if (Objects.equals(string2, this.getClientInfo(string))) {
                return;
            }
            if (JdbcConnection.isInternalProperty(string)) {
                throw new SQLClientInfoException("Property name '" + string + " is used internally by H2.", Collections.emptyMap());
            }
            Pattern pattern = this.getMode().supportedClientInfoPropertiesRegEx;
            if (pattern != null && pattern.matcher(string).matches()) {
                if (this.clientInfo == null) {
                    this.clientInfo = new HashMap<String, String>();
                }
            } else {
                throw new SQLClientInfoException("Client info name '" + string + "' not supported.", Collections.emptyMap());
            }
            this.clientInfo.put(string, string2);
        }
        catch (Exception exception) {
            throw JdbcConnection.convertToClientInfoException(this.logAndConvert(exception));
        }
    }

    private static boolean isInternalProperty(String string) {
        return NUM_SERVERS.equals(string) || string.startsWith(PREFIX_SERVER);
    }

    private static SQLClientInfoException convertToClientInfoException(SQLException sQLException) {
        if (sQLException instanceof SQLClientInfoException) {
            return (SQLClientInfoException)sQLException;
        }
        return new SQLClientInfoException(sQLException.getMessage(), sQLException.getSQLState(), sQLException.getErrorCode(), null, null);
    }

    @Override
    public void setClientInfo(Properties properties) throws SQLClientInfoException {
        try {
            if (this.isDebugEnabled()) {
                this.debugCode("setClientInfo(properties)");
            }
            this.checkClosed();
            if (this.clientInfo == null) {
                this.clientInfo = new HashMap<String, String>();
            } else {
                this.clientInfo.clear();
            }
            for (Map.Entry<Object, Object> entry : properties.entrySet()) {
                this.setClientInfo((String)entry.getKey(), (String)entry.getValue());
            }
        }
        catch (Exception exception) {
            throw JdbcConnection.convertToClientInfoException(this.logAndConvert(exception));
        }
    }

    @Override
    public Properties getClientInfo() throws SQLException {
        try {
            this.debugCodeCall("getClientInfo");
            this.checkClosed();
            ArrayList<String> arrayList = this.session.getClusterServers();
            Properties properties = new Properties();
            if (this.clientInfo != null) {
                for (Map.Entry<String, String> entry : this.clientInfo.entrySet()) {
                    properties.setProperty(entry.getKey(), entry.getValue());
                }
            }
            properties.setProperty(NUM_SERVERS, Integer.toString(arrayList.size()));
            for (int i = 0; i < arrayList.size(); ++i) {
                properties.setProperty(PREFIX_SERVER + i, arrayList.get(i));
            }
            return properties;
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    @Override
    public String getClientInfo(String string) throws SQLException {
        try {
            if (this.isDebugEnabled()) {
                this.debugCodeCall("getClientInfo", string);
            }
            this.checkClosed();
            if (string == null) {
                throw DbException.getInvalidValueException("name", null);
            }
            return this.getClientInfo().getProperty(string);
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
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

    Value createClob(Reader reader, long l) {
        if (reader == null) {
            return ValueNull.INSTANCE;
        }
        if (l <= 0L) {
            l = -1L;
        }
        return this.session.addTemporaryLob(this.session.getDataHandler().getLobStorage().createClob(reader, l));
    }

    Value createBlob(InputStream inputStream2, long l) {
        if (inputStream2 == null) {
            return ValueNull.INSTANCE;
        }
        if (l <= 0L) {
            l = -1L;
        }
        return this.session.addTemporaryLob(this.session.getDataHandler().getLobStorage().createBlob(inputStream2, l));
    }

    @Override
    public void setSchema(String string) throws SQLException {
        try {
            if (this.isDebugEnabled()) {
                this.debugCodeCall("setSchema", string);
            }
            this.checkClosed();
            this.session.setCurrentSchemaName(string);
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    @Override
    public String getSchema() throws SQLException {
        try {
            this.debugCodeCall("getSchema");
            this.checkClosed();
            return this.session.getCurrentSchemaName();
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    @Override
    public void abort(Executor executor2) {
    }

    @Override
    public void setNetworkTimeout(Executor executor2, int n) {
    }

    @Override
    public int getNetworkTimeout() {
        return 0;
    }

    static void checkMap(Map<String, Class<?>> map) {
        if (map != null && map.size() > 0) {
            throw DbException.getUnsupportedException("map.size > 0");
        }
    }

    public String toString() {
        return this.getTraceObjectName() + ": url=" + this.url + " user=" + this.user;
    }

    CompareMode getCompareMode() {
        return this.session.getDataHandler().getCompareMode();
    }

    @Override
    public Mode getMode() {
        return this.session.getMode();
    }

    public Session.StaticSettings getStaticSettings() {
        this.checkClosed();
        return this.session.getStaticSettings();
    }

    @Override
    public ValueTimestampTimeZone currentTimestamp() {
        Session session = this.session;
        if (session == null) {
            throw DbException.get(90007);
        }
        return session.currentTimestamp();
    }

    @Override
    public TimeZoneProvider currentTimeZone() {
        Session session = this.session;
        if (session == null) {
            throw DbException.get(90007);
        }
        return session.currentTimeZone();
    }

    @Override
    public JavaObjectSerializer getJavaObjectSerializer() {
        Session session = this.session;
        if (session == null) {
            throw DbException.get(90007);
        }
        return session.getJavaObjectSerializer();
    }

    @Override
    public boolean zeroBasedEnums() {
        Session session = this.session;
        if (session == null) {
            throw DbException.get(90007);
        }
        return session.zeroBasedEnums();
    }
}

