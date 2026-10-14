/*
 * Decompiled with CFR 0.152.
 */
package org.h2.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import org.h2.jdbc.JdbcConnection;
import org.h2.message.DbException;
import org.h2.server.Service;
import org.h2.server.ShutdownHandler;
import org.h2.server.TcpServerThread;
import org.h2.util.MathUtils;
import org.h2.util.NetUtils;
import org.h2.util.StringUtils;
import org.h2.util.Tool;
import org.h2.util.Utils;
import org.h2.util.Utils10;
import org.h2.util.Utils21;

public class TcpServer
implements Service {
    private static final int SHUTDOWN_NORMAL = 0;
    private static final int SHUTDOWN_FORCE = 1;
    private static final String MANAGEMENT_DB_PREFIX = "management_db_";
    private static final ConcurrentHashMap<Integer, TcpServer> SERVERS = new ConcurrentHashMap();
    private int port;
    private boolean portIsSet;
    private boolean trace;
    private boolean ssl;
    private boolean stop;
    private ShutdownHandler shutdownHandler;
    private ServerSocket serverSocket;
    private final Set<TcpServerThread> running = Collections.synchronizedSet(new HashSet());
    private String baseDir;
    private boolean allowOthers;
    private boolean isDaemon;
    private boolean ifExists = true;
    private boolean virtualThreads;
    private JdbcConnection managementDb;
    private PreparedStatement managementDbAdd;
    private PreparedStatement managementDbRemove;
    private String managementPassword = "";
    private Thread listenerThread;
    private int nextThreadId;
    private String key;
    private String keyDatabase;

    public static String getManagementDbName(int n) {
        return "mem:management_db_" + n;
    }

    private void initManagementDb() throws SQLException {
        JdbcConnection jdbcConnection;
        if (this.managementPassword.isEmpty()) {
            this.managementPassword = StringUtils.convertBytesToHex(MathUtils.secureRandomBytes(32));
        }
        this.managementDb = jdbcConnection = new JdbcConnection("jdbc:h2:" + TcpServer.getManagementDbName(this.port), null, "", this.managementPassword, false);
        try (Statement statement = jdbcConnection.createStatement();){
            statement.execute("CREATE ALIAS IF NOT EXISTS STOP_SERVER FOR '" + TcpServer.class.getName() + ".stopServer'");
            statement.execute("CREATE TABLE IF NOT EXISTS SESSIONS(ID INT PRIMARY KEY, URL VARCHAR, `USER` VARCHAR, CONNECTED TIMESTAMP(9) WITH TIME ZONE)");
            this.managementDbAdd = jdbcConnection.prepareStatement("INSERT INTO SESSIONS VALUES(?, ?, ?, CURRENT_TIMESTAMP(9))");
            this.managementDbRemove = jdbcConnection.prepareStatement("DELETE FROM SESSIONS WHERE ID=?");
        }
        SERVERS.put(this.port, this);
    }

    void shutdown() {
        if (this.shutdownHandler != null) {
            this.shutdownHandler.shutdown();
        }
    }

    public void setShutdownHandler(ShutdownHandler shutdownHandler) {
        this.shutdownHandler = shutdownHandler;
    }

    synchronized void addConnection(int n, String string, String string2) {
        try {
            this.managementDbAdd.setInt(1, n);
            this.managementDbAdd.setString(2, string);
            this.managementDbAdd.setString(3, string2);
            this.managementDbAdd.execute();
        }
        catch (SQLException sQLException) {
            DbException.traceThrowable(sQLException);
        }
    }

    synchronized void removeConnection(int n) {
        try {
            this.managementDbRemove.setInt(1, n);
            this.managementDbRemove.execute();
        }
        catch (SQLException sQLException) {
            DbException.traceThrowable(sQLException);
        }
    }

    private synchronized void stopManagementDb() {
        if (this.managementDb != null) {
            try {
                this.managementDb.close();
            }
            catch (SQLException sQLException) {
                DbException.traceThrowable(sQLException);
            }
            this.managementDb = null;
        }
    }

    @Override
    public void init(String ... stringArray) {
        this.port = 9092;
        for (int i = 0; stringArray != null && i < stringArray.length; ++i) {
            String string = stringArray[i];
            if (Tool.isOption(string, "-trace")) {
                this.trace = true;
                continue;
            }
            if (Tool.isOption(string, "-tcpSSL")) {
                this.ssl = true;
                continue;
            }
            if (Tool.isOption(string, "-tcpPort")) {
                this.port = Integer.decode(stringArray[++i]);
                this.portIsSet = true;
                continue;
            }
            if (Tool.isOption(string, "-tcpPassword")) {
                this.managementPassword = stringArray[++i];
                continue;
            }
            if (Tool.isOption(string, "-baseDir")) {
                this.baseDir = stringArray[++i];
                continue;
            }
            if (Tool.isOption(string, "-key")) {
                this.key = stringArray[++i];
                this.keyDatabase = stringArray[++i];
                continue;
            }
            if (Tool.isOption(string, "-tcpAllowOthers")) {
                this.allowOthers = true;
                continue;
            }
            if (Tool.isOption(string, "-tcpDaemon")) {
                this.isDaemon = true;
                continue;
            }
            if (Tool.isOption(string, "-tcpVirtualThreads")) {
                this.virtualThreads = Utils.parseBoolean(stringArray[++i], this.virtualThreads, true);
                continue;
            }
            if (Tool.isOption(string, "-ifExists")) {
                this.ifExists = true;
                continue;
            }
            if (!Tool.isOption(string, "-ifNotExists")) continue;
            this.ifExists = false;
        }
    }

    @Override
    public String getURL() {
        return (this.ssl ? "ssl" : "tcp") + "://" + NetUtils.getLocalAddress() + ":" + this.port;
    }

    @Override
    public int getPort() {
        return this.port;
    }

    public boolean getSSL() {
        return this.ssl;
    }

    boolean allow(Socket socket) {
        if (this.allowOthers) {
            return true;
        }
        try {
            return NetUtils.isLocalAddress(socket);
        }
        catch (UnknownHostException unknownHostException) {
            this.traceError(unknownHostException);
            return false;
        }
    }

    @Override
    public synchronized void start() throws SQLException {
        this.stop = false;
        try {
            this.serverSocket = NetUtils.createServerSocket(this.port, this.ssl);
        }
        catch (DbException dbException) {
            if (!this.portIsSet) {
                this.serverSocket = NetUtils.createServerSocket(0, this.ssl);
            }
            throw dbException;
        }
        this.port = this.serverSocket.getLocalPort();
        this.initManagementDb();
    }

    @Override
    public void listen() {
        block5: {
            this.listenerThread = Thread.currentThread();
            String string = this.listenerThread.getName();
            try {
                while (!this.stop) {
                    Thread thread2;
                    int n;
                    Socket socket = this.serverSocket.accept();
                    Utils10.setTcpQuickack(socket, true);
                    ++this.nextThreadId;
                    TcpServerThread tcpServerThread = new TcpServerThread(socket, this, n);
                    this.running.add(tcpServerThread);
                    if (this.virtualThreads) {
                        thread2 = Utils21.newVirtualThread(tcpServerThread);
                    } else {
                        thread2 = new Thread(tcpServerThread);
                        thread2.setDaemon(this.isDaemon);
                    }
                    thread2.setName(string + " thread-" + n);
                    tcpServerThread.setThread(thread2);
                    thread2.start();
                }
                this.serverSocket = NetUtils.closeSilently(this.serverSocket);
            }
            catch (Exception exception) {
                if (this.stop) break block5;
                DbException.traceThrowable(exception);
            }
        }
        this.stopManagementDb();
    }

    @Override
    public synchronized boolean isRunning(boolean bl) {
        if (this.serverSocket == null) {
            return false;
        }
        try {
            Socket socket = NetUtils.createLoopbackSocket(this.port, this.ssl);
            socket.close();
            return true;
        }
        catch (Exception exception) {
            if (bl) {
                this.traceError(exception);
            }
            return false;
        }
    }

    @Override
    public void stop() {
        SERVERS.remove(this.port);
        if (!this.stop) {
            this.stopManagementDb();
            this.stop = true;
            if (this.serverSocket != null) {
                try {
                    this.serverSocket.close();
                }
                catch (IOException iOException) {
                    DbException.traceThrowable(iOException);
                }
                catch (NullPointerException nullPointerException) {
                    // empty catch block
                }
                this.serverSocket = null;
            }
            if (this.listenerThread != null) {
                try {
                    this.listenerThread.join(1000L);
                }
                catch (InterruptedException interruptedException) {
                    DbException.traceThrowable(interruptedException);
                }
            }
        }
        for (TcpServerThread tcpServerThread : new ArrayList<TcpServerThread>(this.running)) {
            if (tcpServerThread == null) continue;
            tcpServerThread.close();
            try {
                tcpServerThread.getThread().join(100L);
            }
            catch (Exception exception) {
                DbException.traceThrowable(exception);
            }
        }
    }

    public static void stopServer(int n, String string, int n2) {
        if (n == 0) {
            Integer[] integerArray = ((ConcurrentHashMap.CollectionView)((Object)SERVERS.keySet())).toArray(new Integer[0]);
            int n3 = integerArray.length;
            for (int i = 0; i < n3; ++i) {
                int n4 = integerArray[i];
                if (n4 == 0) continue;
                TcpServer.stopServer(n4, string, n2);
            }
            return;
        }
        TcpServer tcpServer = SERVERS.get(n);
        if (tcpServer == null) {
            return;
        }
        if (!tcpServer.managementPassword.equals(string)) {
            return;
        }
        if (n2 == 0) {
            tcpServer.stopManagementDb();
            tcpServer.stop = true;
            try {
                Socket socket = NetUtils.createLoopbackSocket(n, false);
                socket.close();
            }
            catch (Exception exception) {}
        } else if (n2 == 1) {
            tcpServer.stop();
        }
        tcpServer.shutdown();
    }

    void remove(TcpServerThread tcpServerThread) {
        this.running.remove(tcpServerThread);
    }

    String getBaseDir() {
        return this.baseDir;
    }

    void trace(String string) {
        if (this.trace) {
            System.out.println(string);
        }
    }

    void traceError(Throwable throwable) {
        if (this.trace) {
            throwable.printStackTrace();
        }
    }

    @Override
    public boolean getAllowOthers() {
        return this.allowOthers;
    }

    @Override
    public String getType() {
        return "TCP";
    }

    @Override
    public String getName() {
        return "H2 TCP Server";
    }

    boolean getIfExists() {
        return this.ifExists;
    }

    public static synchronized void shutdown(String string, String string2, boolean bl, boolean bl2) throws SQLException {
        try {
            String string3;
            int n = 9092;
            int n2 = string.lastIndexOf(58);
            if (n2 >= 0 && StringUtils.isNumber(string3 = string.substring(n2 + 1))) {
                n = Integer.decode(string3);
            }
            string3 = TcpServer.getManagementDbName(n);
            for (int i = 0; i < 2; ++i) {
                try (JdbcConnection jdbcConnection = new JdbcConnection("jdbc:h2:" + string + '/' + string3, null, "", string2, true);){
                    PreparedStatement preparedStatement = jdbcConnection.prepareStatement("CALL STOP_SERVER(?, ?, ?)");
                    preparedStatement.setInt(1, bl2 ? 0 : n);
                    preparedStatement.setString(2, string2);
                    preparedStatement.setInt(3, bl ? 1 : 0);
                    try {
                        preparedStatement.execute();
                        break;
                    }
                    catch (SQLException sQLException) {
                        if (bl) {
                        } else if (sQLException.getErrorCode() != 90067) {
                            throw sQLException;
                        }
                        break;
                    }
                }
                catch (SQLException sQLException) {
                    if (i != 1) continue;
                    throw sQLException;
                }
            }
        }
        catch (Exception exception) {
            throw DbException.toSQLException(exception);
        }
    }

    void cancelStatement(String string, int n) {
        for (TcpServerThread tcpServerThread : new ArrayList<TcpServerThread>(this.running)) {
            if (tcpServerThread == null) continue;
            tcpServerThread.cancelStatement(string, n);
        }
    }

    public String checkKeyAndGetDatabaseName(String string) {
        if (this.key == null) {
            return string;
        }
        if (this.key.equals(string)) {
            return this.keyDatabase;
        }
        throw DbException.get(28000);
    }

    @Override
    public boolean isDaemon() {
        return this.isDaemon;
    }
}

