/*
 * Decompiled with CFR 0.152.
 */
package org.h2.jdbc;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.Statement;
import java.util.ArrayList;
import org.h2.command.CommandInterface;
import org.h2.engine.Session;
import org.h2.engine.SysProperties;
import org.h2.jdbc.JdbcBatchUpdateException;
import org.h2.jdbc.JdbcConnection;
import org.h2.jdbc.JdbcResultSet;
import org.h2.jdbc.JdbcStatementBackwardsCompat;
import org.h2.message.DbException;
import org.h2.message.TraceObject;
import org.h2.result.ResultInterface;
import org.h2.result.ResultWithGeneratedKeys;
import org.h2.result.SimpleResult;
import org.h2.util.ParserUtil;
import org.h2.util.StringUtils;
import org.h2.util.Utils;

public class JdbcStatement
extends TraceObject
implements Statement,
JdbcStatementBackwardsCompat {
    protected JdbcConnection conn;
    protected Session session;
    protected JdbcResultSet resultSet;
    protected long maxRows;
    protected int fetchSize = SysProperties.SERVER_RESULT_SET_FETCH_SIZE;
    protected long updateCount;
    protected JdbcResultSet generatedKeys;
    protected final int resultSetType;
    protected final int resultSetConcurrency;
    private volatile CommandInterface executingCommand;
    private ArrayList<String> batchCommands;
    private boolean escapeProcessing = true;
    private volatile boolean cancelled;
    private boolean closeOnCompletion;

    JdbcStatement(JdbcConnection jdbcConnection, int n, int n2, int n3) {
        this.conn = jdbcConnection;
        this.session = jdbcConnection.getSession();
        this.setTrace(this.session.getTrace(), 8, n);
        this.resultSetType = n2;
        this.resultSetConcurrency = n3;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public ResultSet executeQuery(String string) throws SQLException {
        try {
            int n = JdbcStatement.getNextId(4);
            if (this.isDebugEnabled()) {
                this.debugCodeAssign("ResultSet", 4, n, "executeQuery(" + JdbcStatement.quote(string) + ')');
            }
            Session session = this.session;
            session.lock();
            try {
                ResultInterface resultInterface;
                this.checkClosed();
                this.closeOldResultSet();
                string = JdbcConnection.translateSQL(string, this.escapeProcessing);
                CommandInterface commandInterface = this.conn.prepareCommand(string, this.fetchSize);
                boolean bl = false;
                boolean bl2 = this.resultSetType != 1003;
                boolean bl3 = this.resultSetConcurrency == 1008;
                this.setExecutingStatement(commandInterface);
                try {
                    resultInterface = commandInterface.executeQuery(this.maxRows, bl2);
                    bl = resultInterface.isLazy();
                }
                finally {
                    if (!bl) {
                        this.setExecutingStatement(null);
                    }
                }
                if (!bl) {
                    commandInterface.close();
                }
                this.resultSet = new JdbcResultSet(this.conn, this, commandInterface, resultInterface, n, bl2, bl3, false);
            }
            finally {
                session.unlock();
            }
            return this.resultSet;
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    @Override
    public final int executeUpdate(String string) throws SQLException {
        try {
            this.debugCodeCall("executeUpdate", string);
            long l = this.executeUpdateInternal(string, null);
            return l <= Integer.MAX_VALUE ? (int)l : -2;
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    @Override
    public final long executeLargeUpdate(String string) throws SQLException {
        try {
            this.debugCodeCall("executeLargeUpdate", string);
            return this.executeUpdateInternal(string, null);
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private long executeUpdateInternal(String string, Object object) {
        if (this.getClass() != JdbcStatement.class) {
            throw DbException.get(90130);
        }
        this.checkClosed();
        this.closeOldResultSet();
        string = JdbcConnection.translateSQL(string, this.escapeProcessing);
        CommandInterface commandInterface = this.conn.prepareCommand(string, this.fetchSize);
        Session session = this.session;
        session.lock();
        try {
            this.setExecutingStatement(commandInterface);
            try {
                ResultWithGeneratedKeys resultWithGeneratedKeys = commandInterface.executeUpdate(object);
                this.updateCount = resultWithGeneratedKeys.getUpdateCount();
                ResultInterface resultInterface = resultWithGeneratedKeys.getGeneratedKeys();
                if (resultInterface != null) {
                    int n = JdbcStatement.getNextId(4);
                    this.generatedKeys = new JdbcResultSet(this.conn, this, commandInterface, resultInterface, n, true, false, false);
                }
            }
            finally {
                this.setExecutingStatement(null);
            }
        }
        finally {
            session.unlock();
        }
        commandInterface.close();
        return this.updateCount;
    }

    @Override
    public final boolean execute(String string) throws SQLException {
        try {
            this.debugCodeCall("execute", string);
            return this.executeInternal(string, false);
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private boolean executeInternal(String string, Object object) {
        boolean bl;
        if (this.getClass() != JdbcStatement.class) {
            throw DbException.get(90130);
        }
        int n = JdbcStatement.getNextId(4);
        this.checkClosed();
        this.closeOldResultSet();
        string = JdbcConnection.translateSQL(string, this.escapeProcessing);
        CommandInterface commandInterface = this.conn.prepareCommand(string, this.fetchSize);
        boolean bl2 = false;
        Session session = this.session;
        session.lock();
        try {
            this.setExecutingStatement(commandInterface);
            try {
                if (commandInterface.isQuery()) {
                    bl = true;
                    boolean bl3 = this.resultSetType != 1003;
                    boolean bl4 = this.resultSetConcurrency == 1008;
                    ResultInterface resultInterface = commandInterface.executeQuery(this.maxRows, bl3);
                    bl2 = resultInterface.isLazy();
                    this.resultSet = new JdbcResultSet(this.conn, this, commandInterface, resultInterface, n, bl3, bl4, false);
                } else {
                    bl = false;
                    ResultWithGeneratedKeys resultWithGeneratedKeys = commandInterface.executeUpdate(object);
                    this.updateCount = resultWithGeneratedKeys.getUpdateCount();
                    ResultInterface resultInterface = resultWithGeneratedKeys.getGeneratedKeys();
                    if (resultInterface != null) {
                        this.generatedKeys = new JdbcResultSet(this.conn, this, commandInterface, resultInterface, n, true, false, false);
                    }
                }
            }
            finally {
                if (!bl2) {
                    this.setExecutingStatement(null);
                }
            }
        }
        finally {
            session.unlock();
        }
        if (!bl2) {
            commandInterface.close();
        }
        return bl;
    }

    @Override
    public ResultSet getResultSet() throws SQLException {
        try {
            this.checkClosed();
            if (this.resultSet != null) {
                int n = this.resultSet.getTraceId();
                this.debugCodeAssign("ResultSet", 4, n, "getResultSet()");
            } else {
                this.debugCodeCall("getResultSet");
            }
            return this.resultSet;
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    @Override
    public final int getUpdateCount() throws SQLException {
        try {
            this.debugCodeCall("getUpdateCount");
            this.checkClosed();
            return this.updateCount <= Integer.MAX_VALUE ? (int)this.updateCount : -2;
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    @Override
    public final long getLargeUpdateCount() throws SQLException {
        try {
            this.debugCodeCall("getLargeUpdateCount");
            this.checkClosed();
            return this.updateCount;
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    @Override
    public void close() throws SQLException {
        try {
            this.debugCodeCall("close");
            this.closeInternal();
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    private void closeInternal() {
        Session session = this.session;
        session.lock();
        try {
            this.closeOldResultSet();
            if (this.conn != null) {
                this.conn = null;
            }
        }
        finally {
            session.unlock();
        }
    }

    @Override
    public Connection getConnection() {
        this.debugCodeCall("getConnection");
        return this.conn;
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
    public void setCursorName(String string) throws SQLException {
        try {
            this.debugCodeCall("setCursorName", string);
            this.checkClosed();
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    @Override
    public void setFetchDirection(int n) throws SQLException {
        try {
            this.debugCodeCall("setFetchDirection", n);
            this.checkClosed();
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    @Override
    public int getFetchDirection() throws SQLException {
        try {
            this.debugCodeCall("getFetchDirection");
            this.checkClosed();
            return 1000;
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    @Override
    public int getMaxRows() throws SQLException {
        try {
            this.debugCodeCall("getMaxRows");
            this.checkClosed();
            return this.maxRows <= Integer.MAX_VALUE ? (int)this.maxRows : 0;
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    @Override
    public long getLargeMaxRows() throws SQLException {
        try {
            this.debugCodeCall("getLargeMaxRows");
            this.checkClosed();
            return this.maxRows;
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    @Override
    public void setMaxRows(int n) throws SQLException {
        try {
            this.debugCodeCall("setMaxRows", n);
            this.checkClosed();
            if (n < 0) {
                throw DbException.getInvalidValueException("maxRows", n);
            }
            this.maxRows = n;
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    @Override
    public void setLargeMaxRows(long l) throws SQLException {
        try {
            this.debugCodeCall("setLargeMaxRows", l);
            this.checkClosed();
            if (l < 0L) {
                throw DbException.getInvalidValueException("maxRows", l);
            }
            this.maxRows = l;
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    @Override
    public void setFetchSize(int n) throws SQLException {
        try {
            this.debugCodeCall("setFetchSize", n);
            this.checkClosed();
            if (n < 0 || n > 0 && this.maxRows > 0L && (long)n > this.maxRows) {
                throw DbException.getInvalidValueException("rows", n);
            }
            if (n == 0) {
                n = SysProperties.SERVER_RESULT_SET_FETCH_SIZE;
            }
            this.fetchSize = n;
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    @Override
    public int getFetchSize() throws SQLException {
        try {
            this.debugCodeCall("getFetchSize");
            this.checkClosed();
            return this.fetchSize;
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    @Override
    public int getResultSetConcurrency() throws SQLException {
        try {
            this.debugCodeCall("getResultSetConcurrency");
            this.checkClosed();
            return this.resultSetConcurrency;
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    @Override
    public int getResultSetType() throws SQLException {
        try {
            this.debugCodeCall("getResultSetType");
            this.checkClosed();
            return this.resultSetType;
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    @Override
    public int getMaxFieldSize() throws SQLException {
        try {
            this.debugCodeCall("getMaxFieldSize");
            this.checkClosed();
            return 0;
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    @Override
    public void setMaxFieldSize(int n) throws SQLException {
        try {
            this.debugCodeCall("setMaxFieldSize", n);
            this.checkClosed();
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    @Override
    public void setEscapeProcessing(boolean bl) throws SQLException {
        try {
            if (this.isDebugEnabled()) {
                this.debugCode("setEscapeProcessing(" + bl + ')');
            }
            this.checkClosed();
            this.escapeProcessing = bl;
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    @Override
    public void cancel() throws SQLException {
        try {
            this.debugCodeCall("cancel");
            this.checkClosed();
            CommandInterface commandInterface = this.executingCommand;
            try {
                if (commandInterface != null) {
                    commandInterface.cancel();
                    this.cancelled = true;
                }
            }
            finally {
                this.setExecutingStatement(null);
            }
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    public boolean isCancelled() {
        return this.cancelled;
    }

    @Override
    public int getQueryTimeout() throws SQLException {
        try {
            this.debugCodeCall("getQueryTimeout");
            this.checkClosed();
            return this.conn.getQueryTimeout();
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    @Override
    public void setQueryTimeout(int n) throws SQLException {
        try {
            this.debugCodeCall("setQueryTimeout", n);
            this.checkClosed();
            if (n < 0) {
                throw DbException.getInvalidValueException("seconds", n);
            }
            this.conn.setQueryTimeout(n);
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    @Override
    public void addBatch(String string) throws SQLException {
        try {
            this.debugCodeCall("addBatch", string);
            this.checkClosed();
            string = JdbcConnection.translateSQL(string, this.escapeProcessing);
            if (this.batchCommands == null) {
                this.batchCommands = Utils.newSmallArrayList();
            }
            this.batchCommands.add(string);
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    @Override
    public void clearBatch() throws SQLException {
        try {
            this.debugCodeCall("clearBatch");
            this.checkClosed();
            this.batchCommands = null;
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    @Override
    public int[] executeBatch() throws SQLException {
        try {
            this.debugCodeCall("executeBatch");
            this.checkClosed();
            if (this.batchCommands == null) {
                this.batchCommands = new ArrayList();
            }
            int n = this.batchCommands.size();
            int[] nArray = new int[n];
            SQLException sQLException = new SQLException();
            for (int i = 0; i < n; ++i) {
                long l = this.executeBatchElement(this.batchCommands.get(i), sQLException);
                nArray[i] = l <= Integer.MAX_VALUE ? (int)l : -2;
            }
            this.batchCommands = null;
            if ((sQLException = sQLException.getNextException()) != null) {
                throw new JdbcBatchUpdateException(sQLException, nArray);
            }
            return nArray;
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    @Override
    public long[] executeLargeBatch() throws SQLException {
        try {
            this.debugCodeCall("executeLargeBatch");
            this.checkClosed();
            if (this.batchCommands == null) {
                this.batchCommands = new ArrayList();
            }
            int n = this.batchCommands.size();
            long[] lArray = new long[n];
            SQLException sQLException = new SQLException();
            for (int i = 0; i < n; ++i) {
                lArray[i] = this.executeBatchElement(this.batchCommands.get(i), sQLException);
            }
            this.batchCommands = null;
            if ((sQLException = sQLException.getNextException()) != null) {
                throw new JdbcBatchUpdateException(sQLException, lArray);
            }
            return lArray;
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    private long executeBatchElement(String string, SQLException sQLException) {
        long l;
        try {
            l = this.executeUpdateInternal(string, null);
        }
        catch (Exception exception) {
            sQLException.setNextException(this.logAndConvert(exception));
            l = -3L;
        }
        return l;
    }

    @Override
    public ResultSet getGeneratedKeys() throws SQLException {
        try {
            int n;
            int n2 = n = this.generatedKeys != null ? this.generatedKeys.getTraceId() : JdbcStatement.getNextId(4);
            if (this.isDebugEnabled()) {
                this.debugCodeAssign("ResultSet", 4, n, "getGeneratedKeys()");
            }
            this.checkClosed();
            if (this.generatedKeys == null) {
                this.generatedKeys = new JdbcResultSet(this.conn, this, null, (ResultInterface)new SimpleResult(), n, true, false, false);
            }
            return this.generatedKeys;
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    @Override
    public boolean getMoreResults() throws SQLException {
        try {
            this.debugCodeCall("getMoreResults");
            this.checkClosed();
            this.closeOldResultSet();
            return false;
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    @Override
    public boolean getMoreResults(int n) throws SQLException {
        try {
            this.debugCodeCall("getMoreResults", n);
            switch (n) {
                case 1: 
                case 3: {
                    this.checkClosed();
                    this.closeOldResultSet();
                    break;
                }
                case 2: {
                    break;
                }
                default: {
                    throw DbException.getInvalidValueException("current", n);
                }
            }
            return false;
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    @Override
    public final int executeUpdate(String string, int n) throws SQLException {
        try {
            if (this.isDebugEnabled()) {
                this.debugCode("executeUpdate(" + JdbcStatement.quote(string) + ", " + n + ')');
            }
            long l = this.executeUpdateInternal(string, n == 1);
            return l <= Integer.MAX_VALUE ? (int)l : -2;
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    @Override
    public final long executeLargeUpdate(String string, int n) throws SQLException {
        try {
            if (this.isDebugEnabled()) {
                this.debugCode("executeLargeUpdate(" + JdbcStatement.quote(string) + ", " + n + ')');
            }
            return this.executeUpdateInternal(string, n == 1);
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    @Override
    public final int executeUpdate(String string, int[] nArray) throws SQLException {
        try {
            long l;
            if (this.isDebugEnabled()) {
                this.debugCode("executeUpdate(" + JdbcStatement.quote(string) + ", " + JdbcStatement.quoteIntArray(nArray) + ')');
            }
            return (l = this.executeUpdateInternal(string, nArray)) <= Integer.MAX_VALUE ? (int)l : -2;
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    @Override
    public final long executeLargeUpdate(String string, int[] nArray) throws SQLException {
        try {
            if (this.isDebugEnabled()) {
                this.debugCode("executeLargeUpdate(" + JdbcStatement.quote(string) + ", " + JdbcStatement.quoteIntArray(nArray) + ')');
            }
            return this.executeUpdateInternal(string, nArray);
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    @Override
    public final int executeUpdate(String string, String[] stringArray) throws SQLException {
        try {
            long l;
            if (this.isDebugEnabled()) {
                this.debugCode("executeUpdate(" + JdbcStatement.quote(string) + ", " + JdbcStatement.quoteArray(stringArray) + ')');
            }
            return (l = this.executeUpdateInternal(string, stringArray)) <= Integer.MAX_VALUE ? (int)l : -2;
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    @Override
    public final long executeLargeUpdate(String string, String[] stringArray) throws SQLException {
        try {
            if (this.isDebugEnabled()) {
                this.debugCode("executeLargeUpdate(" + JdbcStatement.quote(string) + ", " + JdbcStatement.quoteArray(stringArray) + ')');
            }
            return this.executeUpdateInternal(string, stringArray);
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    @Override
    public final boolean execute(String string, int n) throws SQLException {
        try {
            if (this.isDebugEnabled()) {
                this.debugCode("execute(" + JdbcStatement.quote(string) + ", " + n + ')');
            }
            return this.executeInternal(string, n == 1);
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    @Override
    public final boolean execute(String string, int[] nArray) throws SQLException {
        try {
            if (this.isDebugEnabled()) {
                this.debugCode("execute(" + JdbcStatement.quote(string) + ", " + JdbcStatement.quoteIntArray(nArray) + ')');
            }
            return this.executeInternal(string, nArray);
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    @Override
    public final boolean execute(String string, String[] stringArray) throws SQLException {
        try {
            if (this.isDebugEnabled()) {
                this.debugCode("execute(" + JdbcStatement.quote(string) + ", " + JdbcStatement.quoteArray(stringArray) + ')');
            }
            return this.executeInternal(string, stringArray);
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    @Override
    public int getResultSetHoldability() throws SQLException {
        try {
            this.debugCodeCall("getResultSetHoldability");
            this.checkClosed();
            return 1;
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    @Override
    public void closeOnCompletion() throws SQLException {
        try {
            this.debugCodeCall("closeOnCompletion");
            this.checkClosed();
            this.closeOnCompletion = true;
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    @Override
    public boolean isCloseOnCompletion() throws SQLException {
        try {
            this.debugCodeCall("isCloseOnCompletion");
            this.checkClosed();
            return this.closeOnCompletion;
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    void closeIfCloseOnCompletion() {
        if (this.closeOnCompletion) {
            try {
                this.closeInternal();
            }
            catch (Exception exception) {
                this.logAndConvert(exception);
            }
        }
    }

    void checkClosed() {
        if (this.conn == null) {
            throw DbException.get(90007);
        }
        this.conn.checkClosed();
    }

    protected void closeOldResultSet() {
        try {
            if (this.resultSet != null) {
                this.resultSet.closeInternal(true);
            }
            if (this.generatedKeys != null) {
                this.generatedKeys.closeInternal(true);
            }
        }
        finally {
            this.cancelled = false;
            this.resultSet = null;
            this.updateCount = -1L;
            this.generatedKeys = null;
        }
    }

    void setExecutingStatement(CommandInterface commandInterface) {
        if (commandInterface == null) {
            this.conn.setExecutingStatement(null);
        } else {
            this.conn.setExecutingStatement(this);
        }
        this.executingCommand = commandInterface;
    }

    void onLazyResultSetClose(CommandInterface commandInterface, boolean bl) {
        this.setExecutingStatement(null);
        commandInterface.stop();
        if (bl) {
            commandInterface.close();
        }
    }

    @Override
    public boolean isClosed() throws SQLException {
        try {
            this.debugCodeCall("isClosed");
            return this.conn == null;
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

    @Override
    public boolean isPoolable() {
        this.debugCodeCall("isPoolable");
        return false;
    }

    @Override
    public void setPoolable(boolean bl) {
        if (this.isDebugEnabled()) {
            this.debugCode("setPoolable(" + bl + ')');
        }
    }

    @Override
    public String enquoteIdentifier(String string, boolean bl) throws SQLException {
        if (this.isSimpleIdentifier(string)) {
            return bl ? '\"' + string + '\"' : string;
        }
        try {
            int n = string.length();
            if (n > 0) {
                if (string.charAt(0) == '\"') {
                    JdbcStatement.checkQuotes(string, 1, n);
                    return string;
                }
                if (string.startsWith("U&\"") || string.startsWith("u&\"")) {
                    JdbcStatement.checkQuotes(string, 3, n);
                    StringUtils.decodeUnicodeStringSQL(string, 92);
                    return string;
                }
            }
            return StringUtils.quoteIdentifier(string);
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    private static void checkQuotes(String string, int n, int n2) {
        boolean bl = true;
        for (int i = n; i < n2; ++i) {
            if (string.charAt(i) == '\"') {
                bl = !bl;
                continue;
            }
            if (bl) continue;
            throw DbException.get(42602, string);
        }
        if (bl) {
            throw DbException.get(42602, string);
        }
    }

    @Override
    public boolean isSimpleIdentifier(String string) throws SQLException {
        Session.StaticSettings staticSettings;
        try {
            this.checkClosed();
            staticSettings = this.conn.getStaticSettings();
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
        return ParserUtil.isSimpleIdentifier(string, staticSettings.databaseToUpper, staticSettings.databaseToLower);
    }

    public String toString() {
        return this.getTraceObjectName();
    }
}

