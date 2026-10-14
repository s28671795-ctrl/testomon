/*
 * Decompiled with CFR 0.152.
 */
package org.h2.engine;

import java.io.IOException;
import java.net.Socket;
import java.sql.SQLException;
import java.util.ArrayList;
import org.h2.api.DatabaseEventListener;
import org.h2.api.JavaObjectSerializer;
import org.h2.command.CommandInterface;
import org.h2.command.CommandRemote;
import org.h2.engine.ConnectionInfo;
import org.h2.engine.Engine;
import org.h2.engine.IsolationLevel;
import org.h2.engine.Mode;
import org.h2.engine.Session;
import org.h2.engine.SysProperties;
import org.h2.expression.ParameterInterface;
import org.h2.jdbc.JdbcException;
import org.h2.jdbc.meta.DatabaseMeta;
import org.h2.jdbc.meta.DatabaseMetaLegacy;
import org.h2.jdbc.meta.DatabaseMetaRemote;
import org.h2.message.DbException;
import org.h2.message.Trace;
import org.h2.message.TraceSystem;
import org.h2.result.ResultInterface;
import org.h2.store.DataHandler;
import org.h2.store.FileStore;
import org.h2.store.LobStorageFrontend;
import org.h2.store.fs.FileUtils;
import org.h2.util.DateTimeUtils;
import org.h2.util.JdbcUtils;
import org.h2.util.MathUtils;
import org.h2.util.NetUtils;
import org.h2.util.NetworkConnectionInfo;
import org.h2.util.SmallLRUCache;
import org.h2.util.StringUtils;
import org.h2.util.TempFileDeleter;
import org.h2.util.TimeZoneProvider;
import org.h2.util.Utils;
import org.h2.value.CompareMode;
import org.h2.value.Transfer;
import org.h2.value.Value;
import org.h2.value.ValueInteger;
import org.h2.value.ValueLob;
import org.h2.value.ValueTimestampTimeZone;
import org.h2.value.ValueVarchar;

public final class SessionRemote
extends Session
implements DataHandler {
    public static final int SESSION_PREPARE = 0;
    public static final int SESSION_CLOSE = 1;
    public static final int COMMAND_EXECUTE_QUERY = 2;
    public static final int COMMAND_EXECUTE_UPDATE = 3;
    public static final int COMMAND_CLOSE = 4;
    public static final int RESULT_FETCH_ROWS = 5;
    public static final int RESULT_RESET = 6;
    public static final int RESULT_CLOSE = 7;
    public static final int COMMAND_COMMIT = 8;
    public static final int CHANGE_ID = 9;
    public static final int COMMAND_GET_META_DATA = 10;
    public static final int SESSION_SET_ID = 12;
    public static final int SESSION_CANCEL_STATEMENT = 13;
    public static final int SESSION_CHECK_KEY = 14;
    public static final int SESSION_SET_AUTOCOMMIT = 15;
    public static final int SESSION_HAS_PENDING_TRANSACTION = 16;
    public static final int LOB_READ = 17;
    public static final int SESSION_PREPARE_READ_PARAMS2 = 18;
    public static final int GET_JDBC_META = 19;
    public static final int STATUS_ERROR = 0;
    public static final int STATUS_OK = 1;
    public static final int STATUS_CLOSED = 2;
    public static final int STATUS_OK_STATE_CHANGED = 3;
    private TraceSystem traceSystem;
    private Trace trace;
    private ArrayList<Transfer> transferList = Utils.newSmallArrayList();
    private int nextId;
    private boolean autoCommit = true;
    private ConnectionInfo connectionInfo;
    private String databaseName;
    private String cipher;
    private byte[] fileEncryptionKey;
    private final Object lobSyncObject = new Object();
    private String sessionId;
    private int clientVersion;
    private boolean autoReconnect;
    private int lastReconnect;
    private Session embedded;
    private DatabaseEventListener eventListener;
    private LobStorageFrontend lobStorage;
    private boolean cluster;
    private TempFileDeleter tempFileDeleter;
    private JavaObjectSerializer javaObjectSerializer;
    private final CompareMode compareMode = CompareMode.getInstance(null, 0);
    private final boolean oldInformationSchema;
    private String currentSchemaName;
    private volatile Session.DynamicSettings dynamicSettings;

    public SessionRemote(ConnectionInfo connectionInfo) {
        this.connectionInfo = connectionInfo;
        this.oldInformationSchema = connectionInfo.getProperty("OLD_INFORMATION_SCHEMA", false);
    }

    @Override
    public ArrayList<String> getClusterServers() {
        ArrayList<String> arrayList = new ArrayList<String>();
        for (Transfer transfer : this.transferList) {
            arrayList.add(transfer.getSocket().getInetAddress().getHostAddress() + ":" + transfer.getSocket().getPort());
        }
        return arrayList;
    }

    private Transfer initTransfer(ConnectionInfo connectionInfo, String string, String string2) throws IOException {
        Socket socket = NetUtils.createSocket(string2, 9092, connectionInfo.isSSL(), connectionInfo.getProperty("NETWORK_TIMEOUT", 0));
        Transfer transfer = new Transfer(this, socket);
        transfer.setSSL(connectionInfo.isSSL());
        transfer.init();
        transfer.writeInt(17);
        transfer.writeInt(20);
        transfer.writeString(string);
        transfer.writeString(connectionInfo.getOriginalURL());
        transfer.writeString(connectionInfo.getUserName());
        transfer.writeBytes(connectionInfo.getUserPasswordHash());
        transfer.writeBytes(connectionInfo.getFilePasswordHash());
        String[] stringArray = connectionInfo.getKeys();
        transfer.writeInt(stringArray.length);
        for (String string3 : stringArray) {
            transfer.writeString(string3).writeString(connectionInfo.getProperty(string3));
        }
        try {
            this.done(transfer);
            this.clientVersion = transfer.readInt();
            transfer.setVersion(this.clientVersion);
            if (connectionInfo.getFileEncryptionKey() != null) {
                transfer.writeBytes(connectionInfo.getFileEncryptionKey());
            }
            transfer.writeInt(12);
            transfer.writeString(this.sessionId);
            if (this.clientVersion >= 20) {
                Object object = connectionInfo.getTimeZone();
                if (object == null) {
                    object = DateTimeUtils.getTimeZone();
                }
                transfer.writeString(((TimeZoneProvider)object).getId());
            }
            this.done(transfer);
            this.autoCommit = transfer.readBoolean();
            return transfer;
        }
        catch (DbException dbException) {
            transfer.close();
            throw dbException;
        }
    }

    @Override
    public boolean hasPendingTransaction() {
        int n = 0;
        for (int i = 0; i < this.transferList.size(); ++i) {
            Transfer transfer = this.transferList.get(i);
            try {
                this.traceOperation("SESSION_HAS_PENDING_TRANSACTION", 0);
                transfer.writeInt(16);
                this.done(transfer);
                return transfer.readInt() != 0;
            }
            catch (IOException iOException) {
                this.removeServer(iOException, i--, ++n);
                continue;
            }
        }
        return true;
    }

    @Override
    public void cancel() {
    }

    public void cancelStatement(int n) {
        for (Transfer transfer : this.transferList) {
            try {
                Transfer transfer2 = transfer.openNewConnection();
                transfer2.init();
                transfer2.writeInt(this.clientVersion);
                transfer2.writeInt(this.clientVersion);
                transfer2.writeString(null);
                transfer2.writeString(null);
                transfer2.writeString(this.sessionId);
                transfer2.writeInt(13);
                transfer2.writeInt(n);
                transfer2.close();
            }
            catch (IOException iOException) {
                this.trace.debug(iOException, "could not cancel statement");
            }
        }
    }

    private void checkClusterDisableAutoCommit(String string) {
        if (this.autoCommit && this.transferList.size() > 1) {
            this.setAutoCommitSend(false);
            CommandInterface commandInterface = this.prepareCommand("SET CLUSTER " + string, Integer.MAX_VALUE);
            commandInterface.executeUpdate(null);
            this.autoCommit = true;
            this.cluster = true;
        }
    }

    public int getClientVersion() {
        return this.clientVersion;
    }

    @Override
    public boolean getAutoCommit() {
        return this.autoCommit;
    }

    @Override
    public void setAutoCommit(boolean bl) {
        if (!this.cluster) {
            this.setAutoCommitSend(bl);
        }
        this.autoCommit = bl;
    }

    public void setAutoCommitFromServer(boolean bl) {
        if (this.cluster) {
            if (bl) {
                this.setAutoCommitSend(false);
                this.autoCommit = true;
            }
        } else {
            this.autoCommit = bl;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void setAutoCommitSend(boolean bl) {
        this.lock();
        try {
            int n = 0;
            for (int i = 0; i < this.transferList.size(); ++i) {
                Transfer transfer = this.transferList.get(i);
                try {
                    this.traceOperation("SESSION_SET_AUTOCOMMIT", bl ? 1 : 0);
                    transfer.writeInt(15).writeBoolean(bl);
                    this.done(transfer);
                    continue;
                }
                catch (IOException iOException) {
                    this.removeServer(iOException, i--, ++n);
                }
            }
        }
        finally {
            this.unlock();
        }
    }

    public void autoCommitIfCluster() {
        if (this.autoCommit && this.cluster) {
            int n = 0;
            for (int i = 0; i < this.transferList.size(); ++i) {
                Transfer transfer = this.transferList.get(i);
                try {
                    this.traceOperation("COMMAND_COMMIT", 0);
                    transfer.writeInt(8);
                    this.done(transfer);
                    continue;
                }
                catch (IOException iOException) {
                    this.removeServer(iOException, i--, ++n);
                }
            }
        }
    }

    private String getFilePrefix(String string) {
        StringBuilder stringBuilder = new StringBuilder(string);
        stringBuilder.append('/');
        for (int i = 0; i < this.databaseName.length(); ++i) {
            char c = this.databaseName.charAt(i);
            if (Character.isLetterOrDigit(c)) {
                stringBuilder.append(c);
                continue;
            }
            stringBuilder.append('_');
        }
        return stringBuilder.toString();
    }

    public Session connectEmbeddedOrServer(boolean bl) {
        ConnectionInfo connectionInfo = this.connectionInfo;
        if (connectionInfo.isRemote()) {
            this.connectServer(connectionInfo);
            return this;
        }
        boolean bl2 = connectionInfo.getProperty("AUTO_SERVER", false);
        ConnectionInfo connectionInfo2 = null;
        try {
            if (bl2) {
                connectionInfo2 = connectionInfo.clone();
                this.connectionInfo = connectionInfo.clone();
            }
            if (bl) {
                connectionInfo.setProperty("OPEN_NEW", "true");
            }
            return Engine.createSession(connectionInfo);
        }
        catch (Exception exception) {
            String string;
            DbException dbException = DbException.convert(exception);
            if (dbException.getErrorCode() == 90020 && bl2 && (string = ((JdbcException)((Object)dbException.getSQLException())).getSQL()) != null) {
                connectionInfo2.setServerKey(string);
                connectionInfo2.removeProperty("OPEN_NEW", null);
                this.connectServer(connectionInfo2);
                return this;
            }
            throw dbException;
        }
    }

    private void connectServer(ConnectionInfo connectionInfo) {
        String[] stringArray;
        String string;
        int n;
        String string2 = connectionInfo.getName();
        if (string2.startsWith("//")) {
            string2 = string2.substring("//".length());
        }
        if ((n = string2.indexOf(47)) < 0) {
            throw connectionInfo.getFormatException();
        }
        this.databaseName = string2.substring(n + 1);
        String string3 = string2.substring(0, n);
        this.traceSystem = new TraceSystem(null);
        String string4 = connectionInfo.getProperty(9, null);
        if (string4 != null) {
            int n2 = Integer.parseInt(string4);
            String string5 = this.getFilePrefix(SysProperties.CLIENT_TRACE_DIRECTORY);
            try {
                this.traceSystem.setLevelFile(n2);
                if (n2 > 0 && n2 < 4) {
                    String string6 = FileUtils.createTempFile(string5, ".trace.db", false);
                    this.traceSystem.setFileName(string6);
                }
            }
            catch (IOException iOException) {
                throw DbException.convertIOException(iOException, string5);
            }
        }
        if ((string = connectionInfo.getProperty(8, null)) != null) {
            int n3 = Integer.parseInt(string);
            this.traceSystem.setLevelSystemOut(n3);
        }
        this.trace = this.traceSystem.getTrace(6);
        String string7 = null;
        if (string3.indexOf(44) >= 0) {
            string7 = StringUtils.quoteStringSQL(string3);
            connectionInfo.setProperty("CLUSTER", "TRUE");
        }
        this.autoReconnect = connectionInfo.getProperty("AUTO_RECONNECT", false);
        boolean bl = connectionInfo.getProperty("AUTO_SERVER", false);
        if (bl && string7 != null) {
            throw DbException.getUnsupportedException("autoServer && serverList != null");
        }
        this.autoReconnect |= bl;
        if (this.autoReconnect && (stringArray = connectionInfo.getProperty("DATABASE_EVENT_LISTENER")) != null) {
            stringArray = StringUtils.trim((String)stringArray, true, true, '\'');
            try {
                this.eventListener = (DatabaseEventListener)JdbcUtils.loadUserClass((String)stringArray).getDeclaredConstructor(new Class[0]).newInstance(new Object[0]);
            }
            catch (Throwable throwable) {
                throw DbException.convert(throwable);
            }
        }
        this.cipher = connectionInfo.getProperty("CIPHER");
        if (this.cipher != null) {
            this.fileEncryptionKey = MathUtils.secureRandomBytes(32);
        }
        stringArray = StringUtils.arraySplit(string3, ',', true);
        int n4 = stringArray.length;
        this.transferList.clear();
        this.sessionId = StringUtils.convertBytesToHex(MathUtils.secureRandomBytes(32));
        boolean bl2 = false;
        try {
            for (String string8 : stringArray) {
                try {
                    Transfer transfer = this.initTransfer(connectionInfo, this.databaseName, string8);
                    this.transferList.add(transfer);
                }
                catch (IOException iOException) {
                    if (n4 == 1) {
                        throw DbException.get(90067, iOException, iOException + ": " + string8);
                    }
                    bl2 = true;
                }
            }
            this.checkClosed();
            if (bl2) {
                this.switchOffCluster();
            }
            this.checkClusterDisableAutoCommit(string7);
        }
        catch (DbException dbException) {
            this.traceSystem.close();
            throw dbException;
        }
        this.getDynamicSettings();
    }

    private void switchOffCluster() {
        CommandInterface commandInterface = this.prepareCommand("SET CLUSTER ''", Integer.MAX_VALUE);
        commandInterface.executeUpdate(null);
    }

    public void removeServer(IOException iOException, int n, int n2) {
        this.trace.debug(iOException, "removing server because of exception");
        this.transferList.remove(n);
        if (this.transferList.isEmpty() && this.autoReconnect(n2)) {
            return;
        }
        this.checkClosed();
        this.switchOffCluster();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public CommandInterface prepareCommand(String string, int n) {
        this.lock();
        try {
            this.checkClosed();
            CommandRemote commandRemote = new CommandRemote(this, this.transferList, string, n);
            return commandRemote;
        }
        finally {
            this.unlock();
        }
    }

    private boolean autoReconnect(int n) {
        if (!this.isClosed()) {
            return false;
        }
        if (!this.autoReconnect) {
            return false;
        }
        if (!this.cluster && !this.autoCommit) {
            return false;
        }
        if (n > SysProperties.MAX_RECONNECT) {
            return false;
        }
        ++this.lastReconnect;
        while (true) {
            try {
                this.embedded = this.connectEmbeddedOrServer(false);
            }
            catch (DbException dbException) {
                if (dbException.getErrorCode() != 90135) {
                    throw dbException;
                }
                try {
                    Thread.sleep(500L);
                }
                catch (Exception exception) {}
                continue;
            }
            break;
        }
        if (this.embedded == this) {
            this.embedded = null;
        } else {
            this.connectEmbeddedOrServer(true);
        }
        this.recreateSessionState();
        if (this.eventListener != null) {
            this.eventListener.setProgress(4, this.databaseName, n, SysProperties.MAX_RECONNECT);
        }
        return true;
    }

    public void checkClosed() {
        if (this.isClosed()) {
            throw DbException.get(90067, "session closed");
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void close() {
        RuntimeException runtimeException = null;
        if (this.transferList != null) {
            this.lock();
            try {
                for (Transfer transfer : this.transferList) {
                    try {
                        this.traceOperation("SESSION_CLOSE", 0);
                        transfer.writeInt(1);
                        this.done(transfer);
                        transfer.close();
                    }
                    catch (RuntimeException runtimeException2) {
                        this.trace.error(runtimeException2, "close");
                        runtimeException = runtimeException2;
                    }
                    catch (Exception exception) {
                        this.trace.error(exception, "close");
                    }
                }
            }
            finally {
                this.unlock();
            }
            this.transferList = null;
        }
        this.traceSystem.close();
        if (this.embedded != null) {
            this.embedded.close();
            this.embedded = null;
        }
        if (runtimeException != null) {
            throw runtimeException;
        }
    }

    @Override
    public Trace getTrace() {
        return this.traceSystem.getTrace(6);
    }

    public int getNextId() {
        return this.nextId++;
    }

    public int getCurrentId() {
        return this.nextId;
    }

    public void done(Transfer transfer) throws IOException {
        transfer.flush();
        int n = transfer.readInt();
        switch (n) {
            case 0: {
                throw SessionRemote.readException(transfer);
            }
            case 1: {
                break;
            }
            case 2: {
                this.transferList = null;
                break;
            }
            case 3: {
                this.sessionStateChanged = true;
                this.currentSchemaName = null;
                this.dynamicSettings = null;
                break;
            }
            default: {
                throw DbException.get(90067, "unexpected status " + n);
            }
        }
    }

    public static DbException readException(Transfer transfer) throws IOException {
        String string = transfer.readString();
        String string2 = transfer.readString();
        String string3 = transfer.readString();
        int n = transfer.readInt();
        String string4 = transfer.readString();
        SQLException sQLException = DbException.getJdbcSQLException(string2, string3, string, n, null, string4);
        if (n == 90067) {
            throw new IOException(sQLException.toString(), sQLException);
        }
        return DbException.convert(sQLException);
    }

    public boolean isClustered() {
        return this.cluster;
    }

    @Override
    public boolean isClosed() {
        return this.transferList == null || this.transferList.isEmpty();
    }

    public void traceOperation(String string, int n) {
        if (this.trace.isDebugEnabled()) {
            this.trace.debug("{0} {1}", string, n);
        }
    }

    @Override
    public void checkPowerOff() {
    }

    @Override
    public void checkWritingAllowed() {
    }

    @Override
    public String getDatabasePath() {
        return "";
    }

    @Override
    public int getMaxLengthInplaceLob() {
        return SysProperties.LOB_CLIENT_MAX_SIZE_MEMORY;
    }

    @Override
    public FileStore openFile(String string, String string2, boolean bl) {
        if (bl && !FileUtils.exists(string)) {
            throw DbException.get(90124, string);
        }
        FileStore fileStore = this.cipher == null ? FileStore.open(this, string, string2) : FileStore.open(this, string, string2, this.cipher, this.fileEncryptionKey, 0);
        fileStore.setCheckedWriting(false);
        try {
            fileStore.init();
        }
        catch (DbException dbException) {
            fileStore.closeSilently();
            throw dbException;
        }
        return fileStore;
    }

    @Override
    public DataHandler getDataHandler() {
        return this;
    }

    @Override
    public Object getLobSyncObject() {
        return this.lobSyncObject;
    }

    @Override
    public SmallLRUCache<String, String[]> getLobFileListCache() {
        return null;
    }

    public int getLastReconnect() {
        return this.lastReconnect;
    }

    @Override
    public TempFileDeleter getTempFileDeleter() {
        if (this.tempFileDeleter == null) {
            this.tempFileDeleter = TempFileDeleter.getInstance();
        }
        return this.tempFileDeleter;
    }

    @Override
    public LobStorageFrontend getLobStorage() {
        if (this.lobStorage == null) {
            this.lobStorage = new LobStorageFrontend(this);
        }
        return this.lobStorage;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public int readLob(long l, byte[] byArray, long l2, byte[] byArray2, int n, int n2) {
        this.lock();
        try {
            int n3;
            this.checkClosed();
            int n4 = 0;
            for (n3 = 0; n3 < this.transferList.size(); ++n3) {
                Transfer transfer;
                block8: {
                    transfer = this.transferList.get(n3);
                    try {
                        this.traceOperation("LOB_READ", (int)l);
                        transfer.writeInt(17);
                        transfer.writeLong(l);
                        transfer.writeBytes(byArray);
                        transfer.writeLong(l2);
                        transfer.writeInt(n2);
                        this.done(transfer);
                        n2 = transfer.readInt();
                        if (n2 > 0) break block8;
                        int n5 = n2;
                        return n5;
                    }
                    catch (IOException iOException) {
                        this.removeServer(iOException, n3--, ++n4);
                        continue;
                    }
                }
                transfer.readBytes(byArray2, n, n2);
                int n6 = n2;
                return n6;
            }
            n3 = 1;
            return n3;
        }
        finally {
            this.unlock();
        }
    }

    @Override
    public JavaObjectSerializer getJavaObjectSerializer() {
        if (this.dynamicSettings == null) {
            this.getDynamicSettings();
        }
        return this.javaObjectSerializer;
    }

    @Override
    public ValueLob addTemporaryLob(ValueLob valueLob) {
        return valueLob;
    }

    @Override
    public CompareMode getCompareMode() {
        return this.compareMode;
    }

    @Override
    public boolean isRemote() {
        return true;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public String getCurrentSchemaName() {
        String string = this.currentSchemaName;
        if (string == null) {
            this.lock();
            try (CommandInterface commandInterface = this.prepareCommand("CALL SCHEMA()", 1);
                 ResultInterface resultInterface = commandInterface.executeQuery(1L, false);){
                resultInterface.next();
                this.currentSchemaName = string = resultInterface.currentRow()[0].getString();
            }
            finally {
                this.unlock();
            }
        }
        return string;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void setCurrentSchemaName(String string) {
        this.lock();
        try {
            this.currentSchemaName = null;
            try (CommandInterface commandInterface = this.prepareCommand(StringUtils.quoteIdentifier(new StringBuilder("SET SCHEMA "), string).toString(), 0);){
                commandInterface.executeUpdate(null);
                this.currentSchemaName = string;
            }
        }
        finally {
            this.unlock();
        }
    }

    @Override
    public void setNetworkConnectionInfo(NetworkConnectionInfo networkConnectionInfo) {
    }

    /*
     * Exception decompiling
     */
    @Override
    public IsolationLevel getIsolationLevel() {
        /*
         * This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
         * 
         * org.benf.cfr.reader.util.ConfusedCFRException: Started 2 blocks at once
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.getStartingBlocks(Op04StructuredStatement.java:412)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.buildNestedBlocks(Op04StructuredStatement.java:487)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op03SimpleStatement.createInitialStructuredBlock(Op03SimpleStatement.java:736)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:850)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:278)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:201)
         *     at org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:94)
         *     at org.benf.cfr.reader.entities.Method.analyse(Method.java:531)
         *     at org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:1055)
         *     at org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:942)
         *     at org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:257)
         *     at org.benf.cfr.reader.Driver.doJar(Driver.java:139)
         *     at org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:76)
         *     at org.benf.cfr.reader.Main.main(Main.java:54)
         */
        throw new IllegalStateException("Decompilation failed");
    }

    @Override
    public void setIsolationLevel(IsolationLevel isolationLevel) {
        if (this.clientVersion >= 19) {
            try (CommandInterface commandInterface = this.prepareCommand("SET SESSION CHARACTERISTICS AS TRANSACTION ISOLATION LEVEL " + isolationLevel.getSQL(), 0);){
                commandInterface.executeUpdate(null);
            }
        }
        try (CommandInterface commandInterface = this.prepareCommand("SET LOCK_MODE ?", 0);){
            commandInterface.getParameters().get(0).setValue(ValueInteger.get(isolationLevel.getLockMode()), false);
            commandInterface.executeUpdate(null);
        }
    }

    @Override
    public Session.StaticSettings getStaticSettings() {
        Session.StaticSettings staticSettings = this.staticSettings;
        if (staticSettings == null) {
            boolean bl = true;
            boolean bl2 = false;
            boolean bl3 = false;
            try (CommandInterface commandInterface = this.getSettingsCommand(" IN (?, ?, ?)");){
                ArrayList<? extends ParameterInterface> arrayList = commandInterface.getParameters();
                arrayList.get(0).setValue(ValueVarchar.get("DATABASE_TO_UPPER"), false);
                arrayList.get(1).setValue(ValueVarchar.get("DATABASE_TO_LOWER"), false);
                arrayList.get(2).setValue(ValueVarchar.get("CASE_INSENSITIVE_IDENTIFIERS"), false);
                try (ResultInterface resultInterface = commandInterface.executeQuery(0L, false);){
                    while (resultInterface.next()) {
                        Value[] valueArray = resultInterface.currentRow();
                        String string = valueArray[1].getString();
                        switch (valueArray[0].getString()) {
                            case "DATABASE_TO_UPPER": {
                                bl = Boolean.valueOf(string);
                                break;
                            }
                            case "DATABASE_TO_LOWER": {
                                bl2 = Boolean.valueOf(string);
                                break;
                            }
                            case "CASE_INSENSITIVE_IDENTIFIERS": {
                                bl3 = Boolean.valueOf(string);
                            }
                        }
                    }
                }
            }
            if (this.clientVersion < 18) {
                bl3 = !bl;
            }
            this.staticSettings = staticSettings = new Session.StaticSettings(bl, bl2, bl3);
        }
        return staticSettings;
    }

    @Override
    public Session.DynamicSettings getDynamicSettings() {
        Session.DynamicSettings dynamicSettings = this.dynamicSettings;
        if (dynamicSettings == null) {
            String string = Mode.ModeEnum.REGULAR.name();
            TimeZoneProvider timeZoneProvider = DateTimeUtils.getTimeZone();
            String string2 = null;
            try (Object object = this.getSettingsCommand(" IN (?, ?, ?)");){
                ArrayList<? extends ParameterInterface> arrayList = object.getParameters();
                arrayList.get(0).setValue(ValueVarchar.get("MODE"), false);
                arrayList.get(1).setValue(ValueVarchar.get("TIME ZONE"), false);
                arrayList.get(2).setValue(ValueVarchar.get("JAVA_OBJECT_SERIALIZER"), false);
                try (ResultInterface resultInterface = object.executeQuery(0L, false);){
                    while (resultInterface.next()) {
                        Value[] valueArray = resultInterface.currentRow();
                        String string3 = valueArray[1].getString();
                        switch (valueArray[0].getString()) {
                            case "MODE": {
                                string = string3;
                                break;
                            }
                            case "TIME ZONE": {
                                timeZoneProvider = TimeZoneProvider.ofId(string3);
                                break;
                            }
                            case "JAVA_OBJECT_SERIALIZER": {
                                string2 = string3;
                            }
                        }
                    }
                }
            }
            object = Mode.getInstance(string);
            if (object == null) {
                object = Mode.getRegular();
            }
            this.dynamicSettings = dynamicSettings = new Session.DynamicSettings((Mode)object, timeZoneProvider);
            if (string2 != null && !(string2 = string2.trim()).isEmpty() && !string2.equals("null")) {
                try {
                    this.javaObjectSerializer = (JavaObjectSerializer)JdbcUtils.loadUserClass(string2).getDeclaredConstructor(new Class[0]).newInstance(new Object[0]);
                }
                catch (Exception exception) {
                    throw DbException.convert(exception);
                }
            } else {
                this.javaObjectSerializer = null;
            }
        }
        return dynamicSettings;
    }

    private CommandInterface getSettingsCommand(String string) {
        return this.prepareCommand((!this.isOldInformationSchema() ? "SELECT SETTING_NAME, SETTING_VALUE FROM INFORMATION_SCHEMA.SETTINGS WHERE SETTING_NAME" : "SELECT NAME, `VALUE` FROM INFORMATION_SCHEMA.SETTINGS WHERE NAME") + string, Integer.MAX_VALUE);
    }

    @Override
    public ValueTimestampTimeZone currentTimestamp() {
        return DateTimeUtils.currentTimestamp(this.getDynamicSettings().timeZone);
    }

    @Override
    public TimeZoneProvider currentTimeZone() {
        return this.getDynamicSettings().timeZone;
    }

    @Override
    public Mode getMode() {
        return this.getDynamicSettings().mode;
    }

    @Override
    public DatabaseMeta getDatabaseMeta() {
        return this.clientVersion >= 20 ? new DatabaseMetaRemote(this, this.transferList) : new DatabaseMetaLegacy(this);
    }

    @Override
    public boolean isOldInformationSchema() {
        return this.oldInformationSchema || this.clientVersion < 20;
    }

    @Override
    public boolean zeroBasedEnums() {
        return false;
    }
}

