/*
 * Decompiled with CFR 0.152.
 */
package org.h2.server;

import java.io.ByteArrayInputStream;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.Socket;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Objects;
import org.h2.command.Command;
import org.h2.engine.ConnectionInfo;
import org.h2.engine.Engine;
import org.h2.engine.Session;
import org.h2.engine.SessionLocal;
import org.h2.engine.SysProperties;
import org.h2.expression.Parameter;
import org.h2.expression.ParameterInterface;
import org.h2.expression.ParameterRemote;
import org.h2.jdbc.JdbcException;
import org.h2.jdbc.meta.DatabaseMetaServer;
import org.h2.message.DbException;
import org.h2.result.ResultColumn;
import org.h2.result.ResultInterface;
import org.h2.result.ResultWithGeneratedKeys;
import org.h2.server.TcpServer;
import org.h2.util.IOUtils;
import org.h2.util.NetUtils;
import org.h2.util.NetworkConnectionInfo;
import org.h2.util.SmallLRUCache;
import org.h2.util.SmallMap;
import org.h2.util.TimeZoneProvider;
import org.h2.value.Transfer;
import org.h2.value.Value;
import org.h2.value.ValueLob;

public class TcpServerThread
implements Runnable {
    protected final Transfer transfer;
    private final TcpServer server;
    private SessionLocal session;
    private boolean stop;
    private Thread thread;
    private Command commit;
    private final SmallMap cache = new SmallMap(SysProperties.SERVER_CACHED_OBJECTS);
    private final SmallLRUCache<Long, CachedInputStream> lobs = SmallLRUCache.newInstance(Math.max(SysProperties.SERVER_CACHED_OBJECTS, SysProperties.SERVER_RESULT_SET_FETCH_SIZE * 5));
    private final int threadId;
    private int clientVersion;
    private String sessionId;
    private long lastRemoteSettingsId;

    TcpServerThread(Socket socket, TcpServer tcpServer, int n) {
        this.server = tcpServer;
        this.threadId = n;
        this.transfer = new Transfer(null, socket);
    }

    private void trace(String string) {
        this.server.trace(this + " " + string);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void run() {
        try {
            Socket socket;
            block29: {
                this.transfer.init();
                this.trace("Connect");
                socket = this.transfer.getSocket();
                if (socket != null) break block29;
                return;
            }
            try {
                int n;
                String string;
                if (!this.server.allow(this.transfer.getSocket())) {
                    throw DbException.get(90117);
                }
                int n2 = this.transfer.readInt();
                if (n2 < 6) {
                    throw DbException.get(90047, Integer.toString(n2), "17");
                }
                int n3 = this.transfer.readInt();
                if (n3 < 17) {
                    throw DbException.get(90047, Integer.toString(n3), "17");
                }
                if (n2 > 20) {
                    throw DbException.get(90047, Integer.toString(n2), "20");
                }
                this.clientVersion = n3 >= 20 ? 20 : n3;
                this.transfer.setVersion(this.clientVersion);
                String string2 = this.transfer.readString();
                String string3 = this.transfer.readString();
                if (string2 == null && string3 == null) {
                    string = this.transfer.readString();
                    int n4 = this.transfer.readInt();
                    this.stop = true;
                    if (n4 == 13) {
                        n = this.transfer.readInt();
                        this.server.cancelStatement(string, n);
                    } else if (n4 == 14) {
                        string2 = this.server.checkKeyAndGetDatabaseName(string);
                        if (!string.equals(string2)) {
                            this.transfer.writeInt(1);
                        } else {
                            this.transfer.writeInt(0);
                        }
                    }
                }
                if ((string = this.server.getBaseDir()) == null) {
                    string = SysProperties.getBaseDir();
                }
                string2 = this.server.checkKeyAndGetDatabaseName(string2);
                ConnectionInfo connectionInfo = new ConnectionInfo(string2);
                connectionInfo.setOriginalURL(string3);
                connectionInfo.setUserName(this.transfer.readString());
                connectionInfo.setUserPasswordHash(this.transfer.readBytes());
                connectionInfo.setFilePasswordHash(this.transfer.readBytes());
                n = this.transfer.readInt();
                for (int i = 0; i < n; ++i) {
                    connectionInfo.setProperty(this.transfer.readString(), this.transfer.readString());
                }
                if (string != null) {
                    connectionInfo.setBaseDir(string);
                }
                if (this.server.getIfExists()) {
                    connectionInfo.setProperty("FORBID_CREATION", "TRUE");
                }
                this.transfer.writeInt(1);
                this.transfer.writeInt(this.clientVersion);
                this.transfer.flush();
                if (connectionInfo.getFilePasswordHash() != null) {
                    connectionInfo.setFileEncryptionKey(this.transfer.readBytes());
                }
                connectionInfo.setNetworkConnectionInfo(new NetworkConnectionInfo(NetUtils.ipToShortForm(new StringBuilder(this.server.getSSL() ? "ssl://" : "tcp://"), socket.getLocalAddress().getAddress(), true).append(':').append(socket.getLocalPort()).toString(), socket.getInetAddress().getAddress(), socket.getPort(), "" + 'P' + this.clientVersion));
                if (this.clientVersion < 20) {
                    connectionInfo.setProperty("OLD_INFORMATION_SCHEMA", "TRUE");
                    connectionInfo.setProperty("NON_KEYWORDS", "VALUE");
                }
                this.session = Engine.createSession(connectionInfo);
                this.transfer.setSession(this.session);
                this.server.addConnection(this.threadId, string3, connectionInfo.getUserName());
                this.trace("Connected");
                this.lastRemoteSettingsId = this.session.getDatabase().getRemoteSettingsId();
            }
            catch (OutOfMemoryError outOfMemoryError) {
                this.server.traceError(outOfMemoryError);
                this.sendError(outOfMemoryError, true);
                this.stop = true;
            }
            catch (Throwable throwable) {
                this.sendError(throwable, true);
                this.stop = true;
            }
            while (!this.stop) {
                try {
                    this.process();
                }
                catch (Throwable throwable) {
                    this.sendError(throwable, true);
                }
            }
            this.trace("Disconnect");
        }
        catch (Throwable throwable) {
            this.server.traceError(throwable);
        }
        finally {
            this.close();
        }
    }

    private void closeSession() {
        if (this.session != null) {
            RuntimeException runtimeException = null;
            try {
                this.session.close();
                this.server.removeConnection(this.threadId);
            }
            catch (RuntimeException runtimeException2) {
                runtimeException = runtimeException2;
                this.server.traceError(runtimeException2);
            }
            catch (Exception exception) {
                this.server.traceError(exception);
            }
            finally {
                this.session = null;
            }
            if (runtimeException != null) {
                throw runtimeException;
            }
        }
    }

    void close() {
        try {
            this.stop = true;
            this.closeSession();
        }
        catch (Exception exception) {
            this.server.traceError(exception);
        }
        finally {
            this.transfer.close();
            this.trace("Close");
            this.server.remove(this);
        }
    }

    private void sendError(Throwable throwable, boolean bl) {
        try {
            String string;
            String string2;
            SQLException sQLException = DbException.convert(throwable).getSQLException();
            StringWriter stringWriter = new StringWriter();
            sQLException.printStackTrace(new PrintWriter(stringWriter));
            String string3 = stringWriter.toString();
            if (sQLException instanceof JdbcException) {
                JdbcException jdbcException = (JdbcException)((Object)sQLException);
                string2 = jdbcException.getOriginalMessage();
                string = jdbcException.getSQL();
            } else {
                string2 = sQLException.getMessage();
                string = null;
            }
            if (bl) {
                this.transfer.writeInt(0);
            }
            this.transfer.writeString(sQLException.getSQLState()).writeString(string2).writeString(string).writeInt(sQLException.getErrorCode()).writeString(string3).flush();
        }
        catch (Exception exception) {
            if (!this.transfer.isClosed()) {
                this.server.traceError(exception);
            }
            this.stop = true;
        }
    }

    private void setParameters(Command command) throws IOException {
        int n = this.transfer.readInt();
        ArrayList<? extends ParameterInterface> arrayList = command.getParameters();
        for (int i = 0; i < n; ++i) {
            Parameter parameter = (Parameter)arrayList.get(i);
            parameter.setValue(this.transfer.readValue(null));
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void process() throws IOException {
        SessionLocal sessionLocal = this.session;
        int n = this.transfer.readInt();
        switch (n) {
            case 0: 
            case 18: {
                int n2 = this.transfer.readInt();
                String string = this.transfer.readString();
                int n3 = sessionLocal.getModificationId();
                Command command = sessionLocal.prepareLocal(string);
                boolean bl = command.isReadOnly();
                this.cache.addObject(n2, command);
                boolean bl2 = command.isQuery();
                this.transfer.writeInt(this.getState(n3)).writeBoolean(bl2).writeBoolean(bl);
                if (n != 0) {
                    this.transfer.writeInt(command.getCommandType());
                }
                ArrayList<? extends ParameterInterface> arrayList = command.getParameters();
                this.transfer.writeInt(arrayList.size());
                if (n != 0) {
                    for (ParameterInterface parameterInterface : arrayList) {
                        ParameterRemote.writeMetaData(this.transfer, parameterInterface);
                    }
                }
                this.transfer.flush();
                break;
            }
            case 1: {
                this.stop = true;
                this.closeSession();
                this.transfer.writeInt(1).flush();
                this.close();
                break;
            }
            case 8: {
                if (this.commit == null) {
                    this.commit = sessionLocal.prepareLocal("COMMIT");
                }
                int n4 = sessionLocal.getModificationId();
                this.commit.executeUpdate(null);
                this.transfer.writeInt(this.getState(n4)).flush();
                break;
            }
            case 10: {
                int n5 = this.transfer.readInt();
                int n6 = this.transfer.readInt();
                Command command = (Command)this.cache.getObject(n5, false);
                ResultInterface resultInterface = command.getMetaData();
                this.cache.addObject(n6, resultInterface);
                int n7 = resultInterface.getVisibleColumnCount();
                this.transfer.writeInt(1).writeInt(n7).writeRowCount(0L);
                for (int i = 0; i < n7; ++i) {
                    ResultColumn.writeColumn(this.transfer, resultInterface, i);
                }
                this.transfer.flush();
                break;
            }
            case 2: {
                ResultInterface resultInterface;
                int n8 = this.transfer.readInt();
                int n9 = this.transfer.readInt();
                long l = this.transfer.readRowCount();
                int n10 = this.transfer.readInt();
                Command command = (Command)this.cache.getObject(n8, false);
                this.setParameters(command);
                int n11 = sessionLocal.getModificationId();
                sessionLocal.lock();
                try {
                    resultInterface = command.executeQuery(l, false);
                }
                finally {
                    sessionLocal.unlock();
                }
                this.cache.addObject(n9, resultInterface);
                int n12 = resultInterface.getVisibleColumnCount();
                int n13 = this.getState(n11);
                this.transfer.writeInt(n13).writeInt(n12);
                long l2 = resultInterface.isLazy() ? -1L : resultInterface.getRowCount();
                this.transfer.writeRowCount(l2);
                for (int i = 0; i < n12; ++i) {
                    ResultColumn.writeColumn(this.transfer, resultInterface, i);
                }
                this.sendRows(resultInterface, l2 >= 0L ? Math.min(l2, (long)n10) : (long)n10);
                this.transfer.flush();
                break;
            }
            case 3: {
                int n14;
                Object object;
                int n15;
                Object object2;
                int n16 = this.transfer.readInt();
                Command command = (Command)this.cache.getObject(n16, false);
                this.setParameters(command);
                boolean bl = true;
                int n17 = this.transfer.readInt();
                switch (n17) {
                    case 0: {
                        object2 = false;
                        bl = false;
                        break;
                    }
                    case 1: {
                        object2 = true;
                        break;
                    }
                    case 2: {
                        n15 = this.transfer.readInt();
                        object = new int[n15];
                        for (n14 = 0; n14 < n15; ++n14) {
                            object[n14] = this.transfer.readInt();
                        }
                        object2 = object;
                        break;
                    }
                    case 3: {
                        n15 = this.transfer.readInt();
                        object = new String[n15];
                        for (n14 = 0; n14 < n15; ++n14) {
                            object[n14] = (int)this.transfer.readString();
                        }
                        object2 = object;
                        break;
                    }
                    default: {
                        throw DbException.get(90067, "Unsupported generated keys' mode " + n17);
                    }
                }
                n15 = sessionLocal.getModificationId();
                sessionLocal.lock();
                try {
                    object = command.executeUpdate(object2);
                }
                finally {
                    sessionLocal.unlock();
                }
                if (sessionLocal.isClosed()) {
                    n14 = 2;
                    this.stop = true;
                } else {
                    n14 = this.getState(n15);
                }
                this.transfer.writeInt(n14);
                this.transfer.writeRowCount(((ResultWithGeneratedKeys)object).getUpdateCount());
                this.transfer.writeBoolean(sessionLocal.getAutoCommit());
                if (bl) {
                    ResultInterface resultInterface = ((ResultWithGeneratedKeys)object).getGeneratedKeys();
                    int n18 = resultInterface.getVisibleColumnCount();
                    this.transfer.writeInt(n18);
                    long l = resultInterface.getRowCount();
                    this.transfer.writeRowCount(l);
                    for (int i = 0; i < n18; ++i) {
                        ResultColumn.writeColumn(this.transfer, resultInterface, i);
                    }
                    this.sendRows(resultInterface, l);
                    resultInterface.close();
                }
                this.transfer.flush();
                break;
            }
            case 4: {
                int n19 = this.transfer.readInt();
                Command command = (Command)this.cache.getObject(n19, true);
                if (command == null) break;
                command.close();
                this.cache.freeObject(n19);
                break;
            }
            case 5: {
                int n20 = this.transfer.readInt();
                int n21 = this.transfer.readInt();
                ResultInterface resultInterface = (ResultInterface)this.cache.getObject(n20, false);
                this.transfer.writeInt(1);
                this.sendRows(resultInterface, n21);
                this.transfer.flush();
                break;
            }
            case 6: {
                int n22 = this.transfer.readInt();
                ResultInterface resultInterface = (ResultInterface)this.cache.getObject(n22, false);
                resultInterface.reset();
                break;
            }
            case 7: {
                int n23 = this.transfer.readInt();
                ResultInterface resultInterface = (ResultInterface)this.cache.getObject(n23, true);
                if (resultInterface == null) break;
                resultInterface.close();
                this.cache.freeObject(n23);
                break;
            }
            case 9: {
                int n24 = this.transfer.readInt();
                int n25 = this.transfer.readInt();
                Object object = this.cache.getObject(n24, false);
                this.cache.freeObject(n24);
                this.cache.addObject(n25, object);
                break;
            }
            case 12: {
                this.sessionId = this.transfer.readString();
                if (this.clientVersion >= 20) {
                    sessionLocal.setTimeZone(TimeZoneProvider.ofId(this.transfer.readString()));
                }
                this.transfer.writeInt(1).writeBoolean(sessionLocal.getAutoCommit()).flush();
                break;
            }
            case 15: {
                boolean bl = this.transfer.readBoolean();
                sessionLocal.setAutoCommit(bl);
                this.transfer.writeInt(1).flush();
                break;
            }
            case 16: {
                this.transfer.writeInt(1).writeInt(sessionLocal.hasPendingTransaction() ? 1 : 0).flush();
                break;
            }
            case 17: {
                Object object;
                long l = this.transfer.readLong();
                byte[] byArray = this.transfer.readBytes();
                long l3 = this.transfer.readLong();
                int n26 = this.transfer.readInt();
                this.transfer.verifyLobMac(byArray, l);
                CachedInputStream cachedInputStream = (CachedInputStream)this.lobs.get(l);
                if (cachedInputStream == null || cachedInputStream.getPos() != l3) {
                    object = sessionLocal.getDataHandler().getLobStorage();
                    InputStream inputStream2 = object.getInputStream(l, -1L);
                    cachedInputStream = new CachedInputStream(inputStream2);
                    this.lobs.put(l, cachedInputStream);
                    inputStream2.skip(l3);
                }
                n26 = Math.min(65536, n26);
                object = new byte[n26];
                n26 = IOUtils.readFully(cachedInputStream, (byte[])object, n26);
                this.transfer.writeInt(1);
                this.transfer.writeInt(n26);
                this.transfer.writeBytes((byte[])object, 0, n26);
                this.transfer.flush();
                break;
            }
            case 19: {
                ResultInterface resultInterface;
                int n27;
                int n28 = this.transfer.readInt();
                int n29 = this.transfer.readInt();
                Value[] valueArray = new Value[n29];
                for (n27 = 0; n27 < n29; ++n27) {
                    valueArray[n27] = this.transfer.readValue(null);
                }
                n27 = sessionLocal.getModificationId();
                sessionLocal.lock();
                try {
                    resultInterface = DatabaseMetaServer.process(sessionLocal, n28, valueArray);
                }
                finally {
                    sessionLocal.unlock();
                }
                int n30 = resultInterface.getVisibleColumnCount();
                int n31 = this.getState(n27);
                this.transfer.writeInt(n31).writeInt(n30);
                long l = resultInterface.getRowCount();
                this.transfer.writeRowCount(l);
                for (int i = 0; i < n30; ++i) {
                    ResultColumn.writeColumn(this.transfer, resultInterface, i);
                }
                this.sendRows(resultInterface, l);
                this.transfer.flush();
                break;
            }
            default: {
                this.trace("Unknown operation: " + n);
                this.close();
            }
        }
    }

    private int getState(int n) {
        if (this.session == null) {
            return 2;
        }
        if (this.session.getModificationId() == n) {
            long l = this.session.getDatabase().getRemoteSettingsId();
            if (this.lastRemoteSettingsId == l) {
                return 1;
            }
            this.lastRemoteSettingsId = l;
        }
        return 3;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void sendRows(ResultInterface resultInterface, long l) throws IOException {
        int n = resultInterface.getVisibleColumnCount();
        boolean bl = resultInterface.isLazy();
        Session session = bl ? this.session.setThreadLocalSession() : null;
        try {
            while (l-- > 0L) {
                boolean bl2;
                try {
                    bl2 = resultInterface.next();
                }
                catch (Exception exception) {
                    this.transfer.writeByte((byte)-1);
                    this.sendError(exception, false);
                    break;
                }
                if (bl2) {
                    this.transfer.writeByte((byte)1);
                    Value[] valueArray = resultInterface.currentRow();
                    for (int i = 0; i < n; ++i) {
                        ValueLob valueLob;
                        Value value = valueArray[i];
                        if (bl && value instanceof ValueLob && (valueLob = ((ValueLob)value).copyToResult()) != value) {
                            value = this.session.addTemporaryLob(valueLob);
                        }
                        this.transfer.writeValue(value);
                    }
                    continue;
                }
                this.transfer.writeByte((byte)0);
                break;
            }
        }
        finally {
            if (bl) {
                this.session.resetThreadLocalSession(session);
            }
        }
    }

    void setThread(Thread thread2) {
        this.thread = thread2;
    }

    Thread getThread() {
        return this.thread;
    }

    void cancelStatement(String string, int n) {
        if (Objects.equals(string, this.sessionId)) {
            Command command = (Command)this.cache.getObject(n, false);
            command.cancel();
        }
    }

    static class CachedInputStream
    extends FilterInputStream {
        private static final ByteArrayInputStream DUMMY = new ByteArrayInputStream(new byte[0]);
        private long pos;

        CachedInputStream(InputStream inputStream2) {
            super(inputStream2 == null ? DUMMY : inputStream2);
            if (inputStream2 == null) {
                this.pos = -1L;
            }
        }

        @Override
        public int read(byte[] byArray, int n, int n2) throws IOException {
            if ((n2 = super.read(byArray, n, n2)) > 0) {
                this.pos += (long)n2;
            }
            return n2;
        }

        @Override
        public int read() throws IOException {
            int n = this.in.read();
            if (n >= 0) {
                ++this.pos;
            }
            return n;
        }

        @Override
        public long skip(long l) throws IOException {
            if ((l = super.skip(l)) > 0L) {
                this.pos += l;
            }
            return l;
        }

        public long getPos() {
            return this.pos;
        }
    }
}

