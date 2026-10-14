/*
 * Decompiled with CFR 0.152.
 */
package org.h2.jdbcx;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javax.sql.ConnectionEvent;
import javax.sql.ConnectionEventListener;
import javax.sql.StatementEventListener;
import javax.sql.XAConnection;
import javax.transaction.xa.XAException;
import javax.transaction.xa.XAResource;
import javax.transaction.xa.Xid;
import org.h2.jdbc.JdbcConnection;
import org.h2.jdbcx.JdbcDataSourceFactory;
import org.h2.jdbcx.JdbcXid;
import org.h2.message.DbException;
import org.h2.message.TraceObject;
import org.h2.util.Utils;

public final class JdbcXAConnection
extends TraceObject
implements XAConnection,
XAResource {
    private final JdbcDataSourceFactory factory;
    private JdbcConnection physicalConn;
    private volatile Connection handleConn;
    private final ArrayList<ConnectionEventListener> listeners = Utils.newSmallArrayList();
    private Xid currentTransaction;
    private boolean prepared;

    JdbcXAConnection(JdbcDataSourceFactory jdbcDataSourceFactory, int n, JdbcConnection jdbcConnection) {
        this.factory = jdbcDataSourceFactory;
        this.setTrace(jdbcDataSourceFactory.getTrace(), 13, n);
        this.physicalConn = jdbcConnection;
    }

    @Override
    public XAResource getXAResource() {
        this.debugCodeCall("getXAResource");
        return this;
    }

    @Override
    public void close() throws SQLException {
        this.debugCodeCall("close");
        Connection connection = this.handleConn;
        if (connection != null) {
            this.listeners.clear();
            connection.close();
        }
        if (this.physicalConn != null) {
            try {
                this.physicalConn.close();
            }
            finally {
                this.physicalConn = null;
            }
        }
    }

    @Override
    public Connection getConnection() throws SQLException {
        this.debugCodeCall("getConnection");
        Connection connection = this.handleConn;
        if (connection != null) {
            connection.close();
        }
        this.physicalConn.rollback();
        this.handleConn = new PooledJdbcConnection(this.physicalConn);
        return this.handleConn;
    }

    @Override
    public void addConnectionEventListener(ConnectionEventListener connectionEventListener) {
        this.debugCode("addConnectionEventListener(listener)");
        this.listeners.add(connectionEventListener);
    }

    @Override
    public void removeConnectionEventListener(ConnectionEventListener connectionEventListener) {
        this.debugCode("removeConnectionEventListener(listener)");
        this.listeners.remove(connectionEventListener);
    }

    void closedHandle() {
        this.debugCodeCall("closedHandle");
        ConnectionEvent connectionEvent = new ConnectionEvent(this);
        for (int i = this.listeners.size() - 1; i >= 0; --i) {
            ConnectionEventListener connectionEventListener = this.listeners.get(i);
            connectionEventListener.connectionClosed(connectionEvent);
        }
        this.handleConn = null;
    }

    @Override
    public int getTransactionTimeout() {
        this.debugCodeCall("getTransactionTimeout");
        return 0;
    }

    @Override
    public boolean setTransactionTimeout(int n) {
        this.debugCodeCall("setTransactionTimeout", n);
        return false;
    }

    @Override
    public boolean isSameRM(XAResource xAResource) {
        this.debugCode("isSameRM(xares)");
        return xAResource == this;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public Xid[] recover(int n) throws XAException {
        this.debugCodeCall("recover", JdbcXAConnection.quoteFlags(n));
        this.checkOpen();
        try (Statement statement = this.physicalConn.createStatement();){
            Xid[] xidArray;
            ResultSet resultSet = statement.executeQuery("SELECT * FROM INFORMATION_SCHEMA.IN_DOUBT ORDER BY TRANSACTION_NAME");
            ArrayList<Xid> arrayList = Utils.newSmallArrayList();
            while (resultSet.next()) {
                xidArray = resultSet.getString("TRANSACTION_NAME");
                int xidArray2 = JdbcXAConnection.getNextId(15);
                JdbcXid jdbcXid = new JdbcXid(this.factory, xidArray2, (String)xidArray);
                arrayList.add(jdbcXid);
            }
            resultSet.close();
            xidArray = arrayList.toArray(new Xid[0]);
            if (!arrayList.isEmpty()) {
                this.prepared = true;
            }
            Xid[] xidArray2 = xidArray;
            return xidArray2;
        }
        catch (SQLException sQLException) {
            XAException xAException = new XAException(-3);
            xAException.initCause(sQLException);
            throw xAException;
        }
    }

    @Override
    public int prepare(Xid xid) throws XAException {
        if (this.isDebugEnabled()) {
            this.debugCode("prepare(" + JdbcXAConnection.quoteXid(xid) + ')');
        }
        this.checkOpen();
        if (!this.currentTransaction.equals(xid)) {
            throw new XAException(-5);
        }
        try (Statement statement = this.physicalConn.createStatement();){
            statement.execute(JdbcXid.toString(new StringBuilder("PREPARE COMMIT \""), xid).append('\"').toString());
            this.prepared = true;
        }
        catch (SQLException sQLException) {
            throw JdbcXAConnection.convertException(sQLException);
        }
        return 0;
    }

    @Override
    public void forget(Xid xid) {
        if (this.isDebugEnabled()) {
            this.debugCode("forget(" + JdbcXAConnection.quoteXid(xid) + ')');
        }
        this.prepared = false;
    }

    @Override
    public void rollback(Xid xid) throws XAException {
        if (this.isDebugEnabled()) {
            this.debugCode("rollback(" + JdbcXAConnection.quoteXid(xid) + ')');
        }
        try {
            if (this.prepared) {
                try (Statement statement = this.physicalConn.createStatement();){
                    statement.execute(JdbcXid.toString(new StringBuilder("ROLLBACK TRANSACTION \""), xid).append('\"').toString());
                }
                this.prepared = false;
            } else {
                this.physicalConn.rollback();
            }
            this.physicalConn.setAutoCommit(true);
        }
        catch (SQLException sQLException) {
            throw JdbcXAConnection.convertException(sQLException);
        }
        this.currentTransaction = null;
    }

    @Override
    public void end(Xid xid, int n) throws XAException {
        if (this.isDebugEnabled()) {
            this.debugCode("end(" + JdbcXAConnection.quoteXid(xid) + ", " + JdbcXAConnection.quoteFlags(n) + ')');
        }
        if (n == 0x2000000) {
            return;
        }
        if (!this.currentTransaction.equals(xid)) {
            throw new XAException(-9);
        }
        this.prepared = false;
    }

    @Override
    public void start(Xid xid, int n) throws XAException {
        if (this.isDebugEnabled()) {
            this.debugCode("start(" + JdbcXAConnection.quoteXid(xid) + ", " + JdbcXAConnection.quoteFlags(n) + ')');
        }
        if (n == 0x8000000) {
            return;
        }
        if (n == 0x200000) {
            if (this.currentTransaction != null && !this.currentTransaction.equals(xid)) {
                throw new XAException(-3);
            }
        } else if (this.currentTransaction != null) {
            throw new XAException(-4);
        }
        try {
            this.physicalConn.setAutoCommit(false);
        }
        catch (SQLException sQLException) {
            throw JdbcXAConnection.convertException(sQLException);
        }
        this.currentTransaction = xid;
        this.prepared = false;
    }

    @Override
    public void commit(Xid xid, boolean bl) throws XAException {
        if (this.isDebugEnabled()) {
            this.debugCode("commit(" + JdbcXAConnection.quoteXid(xid) + ", " + bl + ')');
        }
        try {
            if (bl) {
                this.physicalConn.commit();
            } else {
                try (Statement statement = this.physicalConn.createStatement();){
                    statement.execute(JdbcXid.toString(new StringBuilder("COMMIT TRANSACTION \""), xid).append('\"').toString());
                    this.prepared = false;
                }
            }
            this.physicalConn.setAutoCommit(true);
        }
        catch (SQLException sQLException) {
            throw JdbcXAConnection.convertException(sQLException);
        }
        this.currentTransaction = null;
    }

    @Override
    public void addStatementEventListener(StatementEventListener statementEventListener) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void removeStatementEventListener(StatementEventListener statementEventListener) {
        throw new UnsupportedOperationException();
    }

    public String toString() {
        return this.getTraceObjectName() + ": " + this.physicalConn;
    }

    private static XAException convertException(SQLException sQLException) {
        XAException xAException = new XAException(sQLException.getMessage());
        xAException.initCause(sQLException);
        return xAException;
    }

    private static String quoteXid(Xid xid) {
        return JdbcXid.toString(new StringBuilder(), xid).toString().replace('-', '$');
    }

    private static String quoteFlags(int n) {
        StringBuilder stringBuilder = new StringBuilder();
        if ((n & 0x800000) != 0) {
            stringBuilder.append("|XAResource.TMENDRSCAN");
        }
        if ((n & 0x20000000) != 0) {
            stringBuilder.append("|XAResource.TMFAIL");
        }
        if ((n & 0x200000) != 0) {
            stringBuilder.append("|XAResource.TMJOIN");
        }
        if ((n & 0x40000000) != 0) {
            stringBuilder.append("|XAResource.TMONEPHASE");
        }
        if ((n & 0x8000000) != 0) {
            stringBuilder.append("|XAResource.TMRESUME");
        }
        if ((n & 0x1000000) != 0) {
            stringBuilder.append("|XAResource.TMSTARTRSCAN");
        }
        if ((n & 0x4000000) != 0) {
            stringBuilder.append("|XAResource.TMSUCCESS");
        }
        if ((n & 0x2000000) != 0) {
            stringBuilder.append("|XAResource.TMSUSPEND");
        }
        if ((n & 3) != 0) {
            stringBuilder.append("|XAResource.XA_RDONLY");
        }
        if (stringBuilder.length() == 0) {
            stringBuilder.append("|XAResource.TMNOFLAGS");
        }
        return stringBuilder.substring(1);
    }

    private void checkOpen() throws XAException {
        if (this.physicalConn == null) {
            throw new XAException(-3);
        }
    }

    final class PooledJdbcConnection
    extends JdbcConnection {
        private boolean isClosed;

        public PooledJdbcConnection(JdbcConnection jdbcConnection) {
            super(jdbcConnection);
        }

        @Override
        public void close() throws SQLException {
            block5: {
                this.lock();
                try {
                    if (this.isClosed) break block5;
                    try {
                        this.rollback();
                        this.setAutoCommit(true);
                    }
                    catch (SQLException sQLException) {
                        // empty catch block
                    }
                    JdbcXAConnection.this.closedHandle();
                    this.isClosed = true;
                }
                finally {
                    this.unlock();
                }
            }
        }

        @Override
        public boolean isClosed() throws SQLException {
            this.lock();
            try {
                boolean bl = this.isClosed || super.isClosed();
                return bl;
            }
            finally {
                this.unlock();
            }
        }

        @Override
        protected void checkClosed() {
            this.lock();
            try {
                if (this.isClosed) {
                    throw DbException.get(90007);
                }
                super.checkClosed();
            }
            finally {
                this.unlock();
            }
        }
    }
}

