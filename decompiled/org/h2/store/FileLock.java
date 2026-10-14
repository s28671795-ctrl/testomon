/*
 * Decompiled with CFR 0.152.
 */
package org.h2.store;

import java.io.IOException;
import java.io.OutputStream;
import java.net.BindException;
import java.net.ConnectException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Properties;
import org.h2.Driver;
import org.h2.message.DbException;
import org.h2.message.Trace;
import org.h2.message.TraceSystem;
import org.h2.store.FileLockMethod;
import org.h2.store.fs.FilePath;
import org.h2.store.fs.FileUtils;
import org.h2.util.MathUtils;
import org.h2.util.NetUtils;
import org.h2.util.SortedProperties;
import org.h2.util.StringUtils;
import org.h2.value.Transfer;

public class FileLock
implements Runnable {
    private static final String MAGIC = "FileLock";
    private static final String FILE = "file";
    private static final String SOCKET = "socket";
    private static final int RANDOM_BYTES = 16;
    private static final int SLEEP_GAP = 25;
    private static final int TIME_GRANULARITY = 2000;
    private volatile String fileName;
    private volatile ServerSocket serverSocket;
    private volatile boolean locked;
    private final int sleep;
    private final Trace trace;
    private long lastWrite;
    private String method;
    private Properties properties;
    private String uniqueId;
    private Thread watchdog;

    public FileLock(TraceSystem traceSystem, String string, int n) {
        this.trace = traceSystem == null ? null : traceSystem.getTrace(4);
        this.fileName = string;
        this.sleep = n;
    }

    public synchronized void lock(FileLockMethod fileLockMethod) {
        this.checkServer();
        if (this.locked) {
            throw DbException.getInternalError("already locked");
        }
        switch (fileLockMethod) {
            case FILE: {
                this.lockFile();
                break;
            }
            case SOCKET: {
                this.lockSocket();
                break;
            }
        }
        this.locked = true;
    }

    public synchronized void unlock() {
        if (!this.locked) {
            return;
        }
        this.locked = false;
        try {
            if (this.watchdog != null) {
                this.watchdog.interrupt();
            }
        }
        catch (Exception exception) {
            this.trace.debug(exception, "unlock");
        }
        try {
            if (this.fileName != null && this.load().equals(this.properties)) {
                FileUtils.delete(this.fileName);
            }
            if (this.serverSocket != null) {
                this.serverSocket.close();
            }
        }
        catch (Exception exception) {
            this.trace.debug(exception, "unlock");
        }
        finally {
            this.fileName = null;
            this.serverSocket = null;
        }
        try {
            if (this.watchdog != null) {
                this.watchdog.join();
            }
        }
        catch (Exception exception) {
            this.trace.debug(exception, "unlock");
        }
        finally {
            this.watchdog = null;
        }
    }

    public void setProperty(String string, String string2) {
        if (string2 == null) {
            this.properties.remove(string);
        } else {
            this.properties.put(string, string2);
        }
    }

    public Properties save() {
        try {
            try (OutputStream outputStream2 = FileUtils.newOutputStream(this.fileName, false);){
                this.properties.store(outputStream2, MAGIC);
            }
            this.lastWrite = FileLock.aggressiveLastModified(this.fileName);
            if (this.trace.isDebugEnabled()) {
                this.trace.debug("save " + this.properties);
            }
            return this.properties;
        }
        catch (IOException iOException) {
            throw FileLock.getExceptionFatal("Could not save properties " + this.fileName, iOException);
        }
    }

    private static long aggressiveLastModified(String string) {
        try (FileChannel fileChannel = FilePath.get(string).open("rws");){
            ByteBuffer byteBuffer = ByteBuffer.wrap(new byte[1]);
            fileChannel.read(byteBuffer);
        }
        catch (IOException iOException) {
            // empty catch block
        }
        return FileUtils.lastModified(string);
    }

    private void checkServer() {
        Object object;
        Properties properties = this.load();
        String string = properties.getProperty("server");
        if (string == null) {
            return;
        }
        boolean bl = false;
        String string2 = properties.getProperty("id");
        try {
            object = NetUtils.createSocket(string, 9092, false);
            Transfer transfer = new Transfer(null, (Socket)object);
            transfer.init();
            transfer.writeInt(17);
            transfer.writeInt(20);
            transfer.writeString(null);
            transfer.writeString(null);
            transfer.writeString(string2);
            transfer.writeInt(14);
            transfer.flush();
            int n = transfer.readInt();
            if (n == 1) {
                bl = true;
            }
            transfer.close();
            ((Socket)object).close();
        }
        catch (IOException iOException) {
            return;
        }
        if (bl) {
            object = DbException.get(90020, "Server is running");
            throw ((DbException)object).addSQL(string + "/" + string2);
        }
    }

    public Properties load() {
        IOException iOException = null;
        for (int i = 0; i < 5; ++i) {
            try {
                SortedProperties sortedProperties = SortedProperties.loadProperties(this.fileName);
                if (this.trace.isDebugEnabled()) {
                    this.trace.debug("load " + sortedProperties);
                }
                return sortedProperties;
            }
            catch (IOException iOException2) {
                iOException = iOException2;
                continue;
            }
        }
        throw FileLock.getExceptionFatal("Could not load properties " + this.fileName, iOException);
    }

    private void waitUntilOld() {
        for (int i = 0; i < 160; ++i) {
            long l = FileLock.aggressiveLastModified(this.fileName);
            long l2 = System.currentTimeMillis() - l;
            if (l2 < -2000L) {
                try {
                    Thread.sleep(2L * (long)this.sleep);
                }
                catch (Exception exception) {
                    this.trace.debug(exception, "sleep");
                }
                return;
            }
            if (l2 > 2000L) {
                return;
            }
            try {
                Thread.sleep(25L);
                continue;
            }
            catch (Exception exception) {
                this.trace.debug(exception, "sleep");
            }
        }
        throw FileLock.getExceptionFatal("Lock file recently modified", null);
    }

    private void setUniqueId() {
        byte[] byArray = MathUtils.secureRandomBytes(16);
        String string = StringUtils.convertBytesToHex(byArray);
        this.uniqueId = Long.toHexString(System.currentTimeMillis()) + string;
        this.properties.setProperty("id", this.uniqueId);
    }

    private void lockFile() {
        this.method = FILE;
        this.properties = new SortedProperties();
        this.properties.setProperty("method", String.valueOf(this.method));
        this.setUniqueId();
        FileUtils.createDirectories(FileUtils.getParent(this.fileName));
        if (!FileUtils.createFile(this.fileName)) {
            this.waitUntilOld();
            String string = this.load().getProperty("method", FILE);
            if (!string.equals(FILE)) {
                throw FileLock.getExceptionFatal("Unsupported lock method " + string, null);
            }
            this.save();
            FileLock.sleep(2 * this.sleep);
            if (!this.load().equals(this.properties)) {
                throw this.getExceptionAlreadyInUse("Locked by another process: " + this.fileName);
            }
            FileUtils.delete(this.fileName);
            if (!FileUtils.createFile(this.fileName)) {
                throw FileLock.getExceptionFatal("Another process was faster", null);
            }
        }
        this.save();
        FileLock.sleep(25);
        if (!this.load().equals(this.properties)) {
            this.fileName = null;
            throw FileLock.getExceptionFatal("Concurrent update", null);
        }
        this.locked = true;
        this.watchdog = new Thread(this, "H2 File Lock Watchdog " + this.fileName);
        Driver.setThreadContextClassLoader(this.watchdog);
        this.watchdog.setDaemon(true);
        this.watchdog.setPriority(9);
        this.watchdog.start();
    }

    private void lockSocket() {
        this.method = SOCKET;
        this.properties = new SortedProperties();
        this.properties.setProperty("method", String.valueOf(this.method));
        this.setUniqueId();
        String string = NetUtils.getLocalAddress();
        FileUtils.createDirectories(FileUtils.getParent(this.fileName));
        if (!FileUtils.createFile(this.fileName)) {
            InetAddress inetAddress;
            this.waitUntilOld();
            long l = FileLock.aggressiveLastModified(this.fileName);
            Properties properties = this.load();
            String string2 = properties.getProperty("method", SOCKET);
            if (string2.equals(FILE)) {
                this.lockFile();
                return;
            }
            if (!string2.equals(SOCKET)) {
                throw FileLock.getExceptionFatal("Unsupported lock method " + string2, null);
            }
            String string3 = properties.getProperty("ipAddress", string);
            if (!string.equals(string3)) {
                throw this.getExceptionAlreadyInUse("Locked by another computer: " + string3);
            }
            String string4 = properties.getProperty("port", "0");
            int n = Integer.parseInt(string4);
            try {
                inetAddress = InetAddress.getByName(string3);
            }
            catch (UnknownHostException unknownHostException) {
                throw FileLock.getExceptionFatal("Unknown host " + string3, unknownHostException);
            }
            for (int i = 0; i < 3; ++i) {
                try {
                    Socket socket = new Socket(inetAddress, n);
                    socket.close();
                    throw this.getExceptionAlreadyInUse("Locked by another process");
                }
                catch (BindException bindException) {
                    throw FileLock.getExceptionFatal("Bind Exception", null);
                }
                catch (ConnectException connectException) {
                    this.trace.debug(connectException, "socket not connected to port " + string4);
                    continue;
                }
                catch (IOException iOException) {
                    throw FileLock.getExceptionFatal("IOException", null);
                }
            }
            if (l != FileLock.aggressiveLastModified(this.fileName)) {
                throw FileLock.getExceptionFatal("Concurrent update", null);
            }
            FileUtils.delete(this.fileName);
            if (!FileUtils.createFile(this.fileName)) {
                throw FileLock.getExceptionFatal("Another process was faster", null);
            }
        }
        try {
            this.serverSocket = NetUtils.createServerSocket(0, false);
            int n = this.serverSocket.getLocalPort();
            this.properties.setProperty("ipAddress", string);
            this.properties.setProperty("port", Integer.toString(n));
        }
        catch (Exception exception) {
            this.trace.debug(exception, "lock");
            this.serverSocket = null;
            this.lockFile();
            return;
        }
        this.save();
        this.locked = true;
        this.watchdog = new Thread(this, "H2 File Lock Watchdog (Socket) " + this.fileName);
        this.watchdog.setDaemon(true);
        this.watchdog.start();
    }

    private static void sleep(int n) {
        try {
            Thread.sleep(n);
        }
        catch (InterruptedException interruptedException) {
            throw FileLock.getExceptionFatal("Sleep interrupted", interruptedException);
        }
    }

    private static DbException getExceptionFatal(String string, Throwable throwable) {
        return DbException.get(8000, throwable, string);
    }

    private DbException getExceptionAlreadyInUse(String string) {
        DbException dbException = DbException.get(90020, string);
        if (this.fileName != null) {
            try {
                Properties properties = this.load();
                String string2 = properties.getProperty("server");
                if (string2 != null) {
                    String string3 = string2 + "/" + properties.getProperty("id");
                    dbException = dbException.addSQL(string3);
                }
            }
            catch (DbException dbException2) {
                // empty catch block
            }
        }
        return dbException;
    }

    public static FileLockMethod getFileLockMethod(String string) {
        if (string == null || string.equalsIgnoreCase("FILE")) {
            return FileLockMethod.FILE;
        }
        if (string.equalsIgnoreCase("NO")) {
            return FileLockMethod.NO;
        }
        if (string.equalsIgnoreCase("SOCKET")) {
            return FileLockMethod.SOCKET;
        }
        if (string.equalsIgnoreCase("FS")) {
            return FileLockMethod.FS;
        }
        throw DbException.get(90060, string);
    }

    public String getUniqueId() {
        return this.uniqueId;
    }

    @Override
    public void run() {
        try {
            ServerSocket serverSocket;
            while (this.locked && this.fileName != null) {
                try {
                    if (!FileUtils.exists(this.fileName) || FileLock.aggressiveLastModified(this.fileName) != this.lastWrite) {
                        this.save();
                    }
                    Thread.sleep(this.sleep);
                }
                catch (InterruptedException | NullPointerException | OutOfMemoryError throwable) {
                }
                catch (Exception exception) {
                    this.trace.debug(exception, "watchdog");
                }
            }
            while ((serverSocket = this.serverSocket) != null) {
                try {
                    this.trace.debug("watchdog accept");
                    Socket socket = serverSocket.accept();
                    socket.close();
                }
                catch (Exception exception) {
                    this.trace.debug(exception, "watchdog");
                }
            }
        }
        catch (Exception exception) {
            this.trace.debug(exception, "watchdog");
        }
        this.trace.debug("watchdog end");
    }
}

