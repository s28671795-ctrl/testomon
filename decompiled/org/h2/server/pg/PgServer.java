/*
 * Decompiled with CFR 0.152.
 */
package org.h2.server.pg;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import org.h2.message.DbException;
import org.h2.server.Service;
import org.h2.server.pg.PgServerThread;
import org.h2.util.NetUtils;
import org.h2.util.Tool;
import org.h2.util.Utils;
import org.h2.util.Utils10;
import org.h2.util.Utils21;
import org.h2.value.TypeInfo;
import org.h2.value.Value;

public class PgServer
implements Service {
    public static final int DEFAULT_PORT = 5435;
    public static final int PG_TYPE_VARCHAR = 1043;
    public static final int PG_TYPE_BOOL = 16;
    public static final int PG_TYPE_BYTEA = 17;
    public static final int PG_TYPE_BPCHAR = 1042;
    public static final int PG_TYPE_INT8 = 20;
    public static final int PG_TYPE_INT2 = 21;
    public static final int PG_TYPE_INT4 = 23;
    public static final int PG_TYPE_TEXT = 25;
    public static final int PG_TYPE_FLOAT4 = 700;
    public static final int PG_TYPE_FLOAT8 = 701;
    public static final int PG_TYPE_UNKNOWN = 705;
    public static final int PG_TYPE_INT2_ARRAY = 1005;
    public static final int PG_TYPE_INT4_ARRAY = 1007;
    public static final int PG_TYPE_VARCHAR_ARRAY = 1015;
    public static final int PG_TYPE_DATE = 1082;
    public static final int PG_TYPE_TIME = 1083;
    public static final int PG_TYPE_TIMETZ = 1266;
    public static final int PG_TYPE_TIMESTAMP = 1114;
    public static final int PG_TYPE_TIMESTAMPTZ = 1184;
    public static final int PG_TYPE_NUMERIC = 1700;
    private final HashSet<Integer> typeSet = new HashSet();
    private int port = 5435;
    private boolean portIsSet;
    private boolean stop;
    private boolean trace;
    private ServerSocket serverSocket;
    private final Set<PgServerThread> running = Collections.synchronizedSet(new HashSet());
    private final AtomicInteger pid = new AtomicInteger();
    private String baseDir;
    private boolean allowOthers;
    private boolean isDaemon;
    private boolean ifExists = true;
    private boolean virtualThreads;
    private String key;
    private String keyDatabase;

    @Override
    public void init(String ... stringArray) {
        this.port = 5435;
        for (int i = 0; stringArray != null && i < stringArray.length; ++i) {
            String string = stringArray[i];
            if (Tool.isOption(string, "-trace")) {
                this.trace = true;
                continue;
            }
            if (Tool.isOption(string, "-pgPort")) {
                this.port = Integer.decode(stringArray[++i]);
                this.portIsSet = true;
                continue;
            }
            if (Tool.isOption(string, "-baseDir")) {
                this.baseDir = stringArray[++i];
                continue;
            }
            if (Tool.isOption(string, "-pgAllowOthers")) {
                this.allowOthers = true;
                continue;
            }
            if (Tool.isOption(string, "-pgDaemon")) {
                this.isDaemon = true;
                continue;
            }
            if (Tool.isOption(string, "-pgVirtualThreads")) {
                this.virtualThreads = Utils.parseBoolean(stringArray[++i], this.virtualThreads, true);
                continue;
            }
            if (Tool.isOption(string, "-ifExists")) {
                this.ifExists = true;
                continue;
            }
            if (Tool.isOption(string, "-ifNotExists")) {
                this.ifExists = false;
                continue;
            }
            if (!Tool.isOption(string, "-key")) continue;
            this.key = stringArray[++i];
            this.keyDatabase = stringArray[++i];
        }
    }

    boolean getTrace() {
        return this.trace;
    }

    void trace(String string) {
        if (this.trace) {
            System.out.println(string);
        }
    }

    synchronized void remove(PgServerThread pgServerThread) {
        this.running.remove(pgServerThread);
    }

    void traceError(Exception exception) {
        if (this.trace) {
            exception.printStackTrace();
        }
    }

    @Override
    public String getURL() {
        return "pg://" + NetUtils.getLocalAddress() + ":" + this.port;
    }

    @Override
    public int getPort() {
        return this.port;
    }

    private boolean allow(Socket socket) {
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
    public void start() {
        this.stop = false;
        try {
            this.serverSocket = NetUtils.createServerSocket(this.port, false);
        }
        catch (DbException dbException) {
            if (!this.portIsSet) {
                this.serverSocket = NetUtils.createServerSocket(0, false);
            }
            throw dbException;
        }
        this.port = this.serverSocket.getLocalPort();
    }

    @Override
    public void listen() {
        block6: {
            String string = Thread.currentThread().getName();
            try {
                while (!this.stop) {
                    Thread thread2;
                    Socket socket = this.serverSocket.accept();
                    if (!this.allow(socket)) {
                        this.trace("Connection not allowed");
                        socket.close();
                        continue;
                    }
                    Utils10.setTcpQuickack(socket, true);
                    PgServerThread pgServerThread = new PgServerThread(socket, this);
                    this.running.add(pgServerThread);
                    int n = this.pid.incrementAndGet();
                    pgServerThread.setProcessId(n);
                    if (this.virtualThreads) {
                        thread2 = Utils21.newVirtualThread(pgServerThread);
                    } else {
                        thread2 = new Thread(pgServerThread);
                        thread2.setDaemon(this.isDaemon);
                    }
                    thread2.setName(string + " thread-" + n);
                    pgServerThread.setThread(thread2);
                    thread2.start();
                }
            }
            catch (Exception exception) {
                if (this.stop) break block6;
                exception.printStackTrace();
            }
        }
    }

    @Override
    public void stop() {
        if (!this.stop) {
            this.stop = true;
            if (this.serverSocket != null) {
                try {
                    this.serverSocket.close();
                }
                catch (IOException iOException) {
                    iOException.printStackTrace();
                }
                this.serverSocket = null;
            }
        }
        for (PgServerThread pgServerThread : new ArrayList<PgServerThread>(this.running)) {
            pgServerThread.close();
            try {
                Thread thread2 = pgServerThread.getThread();
                if (thread2 == null) continue;
                thread2.join(100L);
            }
            catch (Exception exception) {
                exception.printStackTrace();
            }
        }
    }

    @Override
    public boolean isRunning(boolean bl) {
        if (this.serverSocket == null) {
            return false;
        }
        try {
            Socket socket = NetUtils.createLoopbackSocket(this.serverSocket.getLocalPort(), false);
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

    PgServerThread getThread(int n) {
        for (PgServerThread pgServerThread : new ArrayList<PgServerThread>(this.running)) {
            if (pgServerThread.getProcessId() != n) continue;
            return pgServerThread;
        }
        return null;
    }

    String getBaseDir() {
        return this.baseDir;
    }

    @Override
    public boolean getAllowOthers() {
        return this.allowOthers;
    }

    @Override
    public String getType() {
        return "PG";
    }

    @Override
    public String getName() {
        return "H2 PG Server";
    }

    boolean getIfExists() {
        return this.ifExists;
    }

    public static String formatType(int n) {
        int n2;
        switch (n) {
            case 0: {
                return "-";
            }
            case 16: {
                n2 = 8;
                break;
            }
            case 17: {
                n2 = 6;
                break;
            }
            case 18: {
                return "char";
            }
            case 19: {
                return "name";
            }
            case 20: {
                n2 = 12;
                break;
            }
            case 21: {
                n2 = 10;
                break;
            }
            case 22: {
                return "int2vector";
            }
            case 23: {
                n2 = 11;
                break;
            }
            case 24: {
                return "regproc";
            }
            case 25: {
                n2 = 3;
                break;
            }
            case 700: {
                n2 = 14;
                break;
            }
            case 701: {
                n2 = 15;
                break;
            }
            case 1005: {
                return "smallint[]";
            }
            case 1007: {
                return "integer[]";
            }
            case 1015: {
                return "character varying[]";
            }
            case 1042: {
                n2 = 1;
                break;
            }
            case 1043: {
                n2 = 2;
                break;
            }
            case 1082: {
                n2 = 17;
                break;
            }
            case 1083: {
                n2 = 18;
                break;
            }
            case 1266: {
                n2 = 19;
                break;
            }
            case 1114: {
                n2 = 20;
                break;
            }
            case 1184: {
                n2 = 21;
                break;
            }
            case 1700: {
                n2 = 13;
                break;
            }
            case 2205: {
                return "regclass";
            }
            default: {
                return "???";
            }
        }
        return Value.getTypeName(n2);
    }

    public static int convertType(TypeInfo typeInfo) {
        switch (typeInfo.getValueType()) {
            case 8: {
                return 16;
            }
            case 2: {
                return 1043;
            }
            case 0: 
            case 3: {
                return 25;
            }
            case 1: {
                return 1042;
            }
            case 10: {
                return 21;
            }
            case 11: {
                return 23;
            }
            case 12: {
                return 20;
            }
            case 13: 
            case 16: {
                return 1700;
            }
            case 14: {
                return 700;
            }
            case 15: {
                return 701;
            }
            case 18: {
                return 1083;
            }
            case 19: {
                return 1266;
            }
            case 17: {
                return 1082;
            }
            case 20: {
                return 1114;
            }
            case 21: {
                return 1184;
            }
            case 5: 
            case 6: {
                return 17;
            }
            case 40: {
                typeInfo = (TypeInfo)typeInfo.getExtTypeInfo();
                switch (typeInfo.getValueType()) {
                    case 10: {
                        return 1005;
                    }
                    case 11: {
                        return 1007;
                    }
                    case 2: {
                        return 1015;
                    }
                }
                return 1015;
            }
        }
        return 705;
    }

    HashSet<Integer> getTypeSet() {
        return this.typeSet;
    }

    void checkType(int n) {
        if (!this.typeSet.contains(n)) {
            this.trace("Unsupported type: " + n);
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

