/*
 * Decompiled with CFR 0.152.
 */
package org.h2.server.web;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import org.h2.engine.SysProperties;
import org.h2.message.DbException;
import org.h2.security.SHA256;
import org.h2.server.Service;
import org.h2.server.ShutdownHandler;
import org.h2.server.web.ConnectionInfo;
import org.h2.server.web.WebSession;
import org.h2.server.web.WebThread;
import org.h2.store.fs.FileUtils;
import org.h2.util.JdbcUtils;
import org.h2.util.MathUtils;
import org.h2.util.NetUtils;
import org.h2.util.NetworkConnectionInfo;
import org.h2.util.SortedProperties;
import org.h2.util.StringUtils;
import org.h2.util.Tool;
import org.h2.util.Utils;

public class WebServer
implements Service {
    static final String[][] LANGUAGES = new String[][]{{"cs", "\u010ce\u0161tina"}, {"de", "Deutsch"}, {"en", "English"}, {"es", "Espa\u00f1ol"}, {"fr", "Fran\u00e7ais"}, {"hi", "Hindi \u0939\u093f\u0902\u0926\u0940"}, {"hu", "Magyar"}, {"ko", "\ud55c\uad6d\uc5b4"}, {"in", "Indonesia"}, {"it", "Italiano"}, {"ja", "\u65e5\u672c\u8a9e"}, {"nl", "Nederlands"}, {"pl", "Polski"}, {"pt_BR", "Portugu\u00eas (Brasil)"}, {"pt_PT", "Portugu\u00eas (Europeu)"}, {"ru", "\u0440\u0443\u0441\u0441\u043a\u0438\u0439"}, {"sk", "Slovensky"}, {"tr", "T\u00fcrk\u00e7e"}, {"uk", "\u0423\u043a\u0440\u0430\u0457\u043d\u0441\u044c\u043a\u0430"}, {"zh_CN", "\u4e2d\u6587 (\u7b80\u4f53)"}, {"zh_TW", "\u4e2d\u6587 (\u7e41\u9ad4)"}};
    private static final String COMMAND_HISTORY = "commandHistory";
    private static final String DEFAULT_LANGUAGE = "en";
    private static final String[] GENERIC = new String[]{"Generic JNDI Data Source|javax.naming.InitialContext|java:comp/env/jdbc/Test|sa", "Generic Teradata|com.teradata.jdbc.TeraDriver|jdbc:teradata://whomooz/|", "Generic Snowflake|com.snowflake.client.jdbc.SnowflakeDriver|jdbc:snowflake://accountName.snowflakecomputing.com|", "Generic Redshift|com.amazon.redshift.jdbc42.Driver|jdbc:redshift://endpoint:5439/database|", "Generic Impala|org.cloudera.impala.jdbc41.Driver|jdbc:impala://clustername:21050/default|", "Generic Hive 2|org.apache.hive.jdbc.HiveDriver|jdbc:hive2://clustername:10000/default|", "Generic Hive|org.apache.hadoop.hive.jdbc.HiveDriver|jdbc:hive://clustername:10000/default|", "Generic Azure SQL|com.microsoft.sqlserver.jdbc.SQLServerDriver|jdbc:sqlserver://name.database.windows.net:1433|", "Generic Firebird Server|org.firebirdsql.jdbc.FBDriver|jdbc:firebirdsql:localhost:c:/temp/firebird/test|sysdba", "Generic SQLite|org.sqlite.JDBC|jdbc:sqlite:test|sa", "Generic DB2|com.ibm.db2.jcc.DB2Driver|jdbc:db2://localhost/test|", "Generic Oracle|oracle.jdbc.driver.OracleDriver|jdbc:oracle:thin:@localhost:1521:XE|sa", "Generic MS SQL Server 2000|com.microsoft.jdbc.sqlserver.SQLServerDriver|jdbc:microsoft:sqlserver://localhost:1433;DatabaseName=sqlexpress|sa", "Generic MS SQL Server 2005|com.microsoft.sqlserver.jdbc.SQLServerDriver|jdbc:sqlserver://localhost;DatabaseName=test|sa", "Generic PostgreSQL|org.postgresql.Driver|jdbc:postgresql:test|", "Generic MySQL|com.mysql.cj.jdbc.Driver|jdbc:mysql://localhost:3306/test|", "Generic MariaDB|org.mariadb.jdbc.Driver|jdbc:mariadb://localhost:3306/test|", "Generic HSQLDB|org.hsqldb.jdbcDriver|jdbc:hsqldb:test;hsqldb.default_table_type=cached|sa", "Generic Derby (Server)|org.apache.derby.client.ClientAutoloadedDriver|jdbc:derby://localhost:1527/test;create=true|sa", "Generic Derby (Embedded)|org.apache.derby.iapi.jdbc.AutoloadedDriver|jdbc:derby:test;create=true|sa", "Generic H2 (Server)|org.h2.Driver|jdbc:h2:tcp://localhost/~/test|sa", "Generic H2 (Embedded)|org.h2.Driver|jdbc:h2:~/test|sa"};
    private static int ticker;
    private static final long SESSION_TIMEOUT;
    private int port;
    private boolean allowOthers;
    private String externalNames;
    private boolean isDaemon;
    private final Set<WebThread> running = Collections.synchronizedSet(new HashSet());
    private boolean ssl;
    private byte[] adminPassword;
    private final HashMap<String, ConnectionInfo> connInfoMap = new HashMap();
    private long lastTimeoutCheck;
    private final HashMap<String, WebSession> sessions = new HashMap();
    private final HashSet<String> languages = new HashSet();
    private String startDateTime;
    private ServerSocket serverSocket;
    private String host;
    private String url;
    private ShutdownHandler shutdownHandler;
    private Thread listenerThread;
    private boolean ifExists = true;
    boolean virtualThreads;
    private String key;
    private boolean allowSecureCreation;
    private boolean trace;
    private TranslateThread translateThread;
    private boolean allowChunked = true;
    private String serverPropertiesDir = "~";
    private String commandHistoryString;

    byte[] getFile(String string) throws IOException {
        this.trace("getFile <" + string + ">");
        byte[] byArray = Utils.getResource("/org/h2/server/web/res/" + string);
        if (byArray == null) {
            this.trace(" null");
        } else {
            this.trace(" size=" + byArray.length);
        }
        return byArray;
    }

    synchronized void remove(WebThread webThread) {
        this.running.remove(webThread);
    }

    private static String generateSessionId() {
        byte[] byArray = MathUtils.secureRandomBytes(16);
        return StringUtils.convertBytesToHex(byArray);
    }

    WebSession getSession(String string) {
        Object object;
        long l = System.currentTimeMillis();
        if (this.lastTimeoutCheck + SESSION_TIMEOUT < l) {
            object = new ArrayList<String>(this.sessions.keySet()).iterator();
            while (object.hasNext()) {
                String string2 = (String)object.next();
                WebSession webSession = this.sessions.get(string2);
                if (webSession.lastAccess + SESSION_TIMEOUT >= l) continue;
                this.trace("timeout for " + string2);
                this.sessions.remove(string2);
            }
            this.lastTimeoutCheck = l;
        }
        if ((object = this.sessions.get(string)) != null) {
            ((WebSession)object).lastAccess = System.currentTimeMillis();
        }
        return object;
    }

    WebSession createNewSession(String string) {
        String string2;
        while (this.sessions.get(string2 = WebServer.generateSessionId()) != null) {
        }
        WebSession webSession = new WebSession(this);
        webSession.lastAccess = System.currentTimeMillis();
        webSession.put("sessionId", string2);
        webSession.put("ip", string);
        webSession.put("language", DEFAULT_LANGUAGE);
        webSession.put("frame-border", "0");
        webSession.put("frameset-border", "4");
        this.sessions.put(string2, webSession);
        this.readTranslations(webSession, DEFAULT_LANGUAGE);
        return this.getSession(string2);
    }

    String getStartDateTime() {
        if (this.startDateTime == null) {
            this.startDateTime = DateTimeFormatter.ofPattern("EEE, d MMM yyyy HH:mm:ss z", Locale.ENGLISH).format(ZonedDateTime.now(ZoneId.of("UTC")));
        }
        return this.startDateTime;
    }

    String getKey() {
        return this.key;
    }

    public void setKey(String string) {
        if (!this.allowOthers) {
            this.key = string;
        }
    }

    public void setAllowSecureCreation(boolean bl) {
        if (!this.allowOthers) {
            this.allowSecureCreation = bl;
        }
    }

    @Override
    public void init(String ... stringArray) {
        for (int i = 0; stringArray != null && i < stringArray.length; ++i) {
            if (!"-properties".equals(stringArray[i])) continue;
            this.serverPropertiesDir = stringArray[++i];
        }
        Properties properties = this.loadProperties();
        this.port = SortedProperties.getIntProperty(properties, "webPort", 8082);
        this.ssl = SortedProperties.getBooleanProperty(properties, "webSSL", false);
        this.allowOthers = SortedProperties.getBooleanProperty(properties, "webAllowOthers", false);
        this.setExternalNames(SortedProperties.getStringProperty(properties, "webExternalNames", null));
        this.setAdminPassword(SortedProperties.getStringProperty(properties, "webAdminPassword", null));
        this.commandHistoryString = properties.getProperty(COMMAND_HISTORY);
        for (int i = 0; stringArray != null && i < stringArray.length; ++i) {
            String string = stringArray[i];
            if (Tool.isOption(string, "-webPort")) {
                this.port = Integer.decode(stringArray[++i]);
                continue;
            }
            if (Tool.isOption(string, "-webSSL")) {
                this.ssl = true;
                continue;
            }
            if (Tool.isOption(string, "-webAllowOthers")) {
                this.allowOthers = true;
                continue;
            }
            if (Tool.isOption(string, "-webExternalNames")) {
                this.setExternalNames(stringArray[++i]);
                continue;
            }
            if (Tool.isOption(string, "-webDaemon")) {
                this.isDaemon = true;
                continue;
            }
            if (Tool.isOption(string, "-webVirtualThreads")) {
                this.virtualThreads = Utils.parseBoolean(stringArray[++i], this.virtualThreads, true);
                continue;
            }
            if (Tool.isOption(string, "-baseDir")) {
                String string2 = stringArray[++i];
                SysProperties.setBaseDir(string2);
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
            if (Tool.isOption(string, "-webAdminPassword")) {
                this.setAdminPassword(stringArray[++i]);
                continue;
            }
            if (Tool.isOption(string, "-properties")) {
                ++i;
                continue;
            }
            if (!Tool.isOption(string, "-trace")) continue;
            this.trace = true;
        }
        for (String[] stringArray2 : LANGUAGES) {
            this.languages.add(stringArray2[0]);
        }
        if (this.allowOthers) {
            this.key = null;
        }
        this.updateURL();
    }

    @Override
    public String getURL() {
        this.updateURL();
        return this.url;
    }

    public String getHost() {
        if (this.host == null) {
            this.updateURL();
        }
        return this.host;
    }

    private void updateURL() {
        try {
            this.host = StringUtils.toLowerEnglish(NetUtils.getLocalAddress());
            StringBuilder stringBuilder = new StringBuilder(this.ssl ? "https" : "http").append("://").append(this.host).append(':').append(this.port);
            if (this.key != null && this.serverSocket != null) {
                stringBuilder.append("?key=").append(this.key);
            }
            this.url = stringBuilder.toString();
        }
        catch (NoClassDefFoundError noClassDefFoundError) {
            // empty catch block
        }
    }

    @Override
    public void start() {
        this.serverSocket = NetUtils.createServerSocket(this.port, this.ssl);
        this.port = this.serverSocket.getLocalPort();
        this.updateURL();
    }

    @Override
    public void listen() {
        this.listenerThread = Thread.currentThread();
        try {
            while (this.serverSocket != null) {
                Socket socket = this.serverSocket.accept();
                WebThread webThread = new WebThread(socket, this);
                this.running.add(webThread);
                webThread.start();
            }
        }
        catch (Exception exception) {
            this.trace(exception.toString());
        }
    }

    @Override
    public boolean isRunning(boolean bl) {
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

    public boolean isStopped() {
        return this.serverSocket == null;
    }

    @Override
    public void stop() {
        if (this.serverSocket != null) {
            try {
                this.serverSocket.close();
            }
            catch (IOException iOException) {
                this.traceError(iOException);
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
        for (WebSession object : new ArrayList<WebSession>(this.sessions.values())) {
            object.close();
        }
        for (WebThread webThread : new ArrayList<WebThread>(this.running)) {
            try {
                webThread.stopNow();
                webThread.join(100);
            }
            catch (Exception exception) {
                this.traceError(exception);
            }
        }
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

    boolean supportsLanguage(String string) {
        return this.languages.contains(string);
    }

    void readTranslations(WebSession webSession, String string) {
        Properties properties = new Properties();
        try {
            this.trace("translation: " + string);
            byte[] byArray = this.getFile("_text_" + string + ".prop");
            String string2 = new String(byArray, StandardCharsets.UTF_8);
            this.trace("  " + string2);
            properties = SortedProperties.fromLines(string2);
            for (Map.Entry<Object, Object> entry : properties.entrySet()) {
                String string3 = (String)entry.getValue();
                if (!string3.startsWith("#")) continue;
                entry.setValue(string3.substring(1));
            }
        }
        catch (IOException iOException) {
            DbException.traceThrowable(iOException);
        }
        webSession.put("text", new HashMap<Object, Object>(properties));
    }

    ArrayList<HashMap<String, Object>> getSessions() {
        ArrayList<HashMap<String, Object>> arrayList = new ArrayList<HashMap<String, Object>>(this.sessions.size());
        for (WebSession webSession : this.sessions.values()) {
            arrayList.add(webSession.getInfo());
        }
        return arrayList;
    }

    @Override
    public String getType() {
        return "Web Console";
    }

    @Override
    public String getName() {
        return "H2 Console Server";
    }

    void setAllowOthers(boolean bl) {
        if (bl) {
            this.key = null;
        }
        this.allowOthers = bl;
    }

    @Override
    public boolean getAllowOthers() {
        return this.allowOthers;
    }

    void setExternalNames(String string) {
        this.externalNames = string != null ? StringUtils.toLowerEnglish(string) : null;
    }

    String getExternalNames() {
        return this.externalNames;
    }

    void setSSL(boolean bl) {
        this.ssl = bl;
    }

    void setPort(int n) {
        this.port = n;
    }

    boolean getSSL() {
        return this.ssl;
    }

    @Override
    public int getPort() {
        return this.port;
    }

    public boolean isCommandHistoryAllowed() {
        return this.commandHistoryString != null;
    }

    public void setCommandHistoryAllowed(boolean bl) {
        if (bl) {
            if (this.commandHistoryString == null) {
                this.commandHistoryString = "";
            }
        } else {
            this.commandHistoryString = null;
        }
    }

    public ArrayList<String> getCommandHistoryList() {
        ArrayList<String> arrayList = new ArrayList<String>();
        if (this.commandHistoryString == null) {
            return arrayList;
        }
        StringBuilder stringBuilder = new StringBuilder();
        int n = 0;
        while (true) {
            if (n == this.commandHistoryString.length() || this.commandHistoryString.charAt(n) == ';') {
                if (stringBuilder.length() > 0) {
                    arrayList.add(stringBuilder.toString());
                    stringBuilder.delete(0, stringBuilder.length());
                }
                if (n == this.commandHistoryString.length()) {
                    break;
                }
            } else if (this.commandHistoryString.charAt(n) == '\\' && n < this.commandHistoryString.length() - 1) {
                stringBuilder.append(this.commandHistoryString.charAt(++n));
            } else {
                stringBuilder.append(this.commandHistoryString.charAt(n));
            }
            ++n;
        }
        return arrayList;
    }

    public void saveCommandHistoryList(ArrayList<String> arrayList) {
        StringBuilder stringBuilder = new StringBuilder();
        for (String string : arrayList) {
            if (stringBuilder.length() > 0) {
                stringBuilder.append(';');
            }
            stringBuilder.append(string.replace("\\", "\\\\").replace(";", "\\;"));
        }
        this.commandHistoryString = stringBuilder.toString();
        this.saveProperties(null);
    }

    ConnectionInfo getSetting(String string) {
        return this.connInfoMap.get(string);
    }

    void updateSetting(ConnectionInfo connectionInfo) {
        this.connInfoMap.put(connectionInfo.name, connectionInfo);
        connectionInfo.lastAccess = ticker++;
    }

    void removeSetting(String string) {
        this.connInfoMap.remove(string);
    }

    private Properties loadProperties() {
        try {
            if ("null".equals(this.serverPropertiesDir)) {
                return new Properties();
            }
            return SortedProperties.loadProperties(this.serverPropertiesDir + "/" + ".h2.server.properties");
        }
        catch (Exception exception) {
            DbException.traceThrowable(exception);
            return new Properties();
        }
    }

    String[] getSettingNames() {
        ArrayList<ConnectionInfo> arrayList = this.getSettings();
        String[] stringArray = new String[arrayList.size()];
        for (int i = 0; i < arrayList.size(); ++i) {
            stringArray[i] = arrayList.get((int)i).name;
        }
        return stringArray;
    }

    synchronized ArrayList<ConnectionInfo> getSettings() {
        ArrayList<ConnectionInfo> arrayList = new ArrayList<ConnectionInfo>();
        if (this.connInfoMap.size() == 0) {
            Properties properties = this.loadProperties();
            if (properties.size() == 0) {
                for (String string : GENERIC) {
                    ConnectionInfo connectionInfo = new ConnectionInfo(string);
                    arrayList.add(connectionInfo);
                    this.updateSetting(connectionInfo);
                }
            } else {
                String string;
                int n = 0;
                while ((string = properties.getProperty(Integer.toString(n))) != null) {
                    ConnectionInfo connectionInfo = new ConnectionInfo(string);
                    arrayList.add(connectionInfo);
                    this.updateSetting(connectionInfo);
                    ++n;
                }
            }
        } else {
            arrayList.addAll(this.connInfoMap.values());
        }
        Collections.sort(arrayList);
        return arrayList;
    }

    synchronized void saveProperties(Properties properties) {
        try {
            Cloneable cloneable;
            if (properties == null) {
                cloneable = this.loadProperties();
                properties = new SortedProperties();
                properties.setProperty("webPort", Integer.toString(SortedProperties.getIntProperty((Properties)cloneable, "webPort", this.port)));
                properties.setProperty("webAllowOthers", Boolean.toString(SortedProperties.getBooleanProperty((Properties)cloneable, "webAllowOthers", this.allowOthers)));
                if (this.externalNames != null) {
                    properties.setProperty("webExternalNames", this.externalNames);
                }
                properties.setProperty("webSSL", Boolean.toString(SortedProperties.getBooleanProperty((Properties)cloneable, "webSSL", this.ssl)));
                if (this.adminPassword != null) {
                    properties.setProperty("webAdminPassword", StringUtils.convertBytesToHex(this.adminPassword));
                }
                if (this.commandHistoryString != null) {
                    properties.setProperty(COMMAND_HISTORY, this.commandHistoryString);
                }
            }
            cloneable = this.getSettings();
            int n = ((ArrayList)cloneable).size();
            for (int i = 0; i < n; ++i) {
                ConnectionInfo connectionInfo = (ConnectionInfo)((ArrayList)cloneable).get(i);
                if (connectionInfo == null) continue;
                properties.setProperty(Integer.toString(n - i - 1), connectionInfo.getString());
            }
            if (!"null".equals(this.serverPropertiesDir)) {
                OutputStream outputStream2 = FileUtils.newOutputStream(this.serverPropertiesDir + "/" + ".h2.server.properties", false);
                properties.store(outputStream2, "H2 Server Properties");
                outputStream2.close();
            }
        }
        catch (Exception exception) {
            DbException.traceThrowable(exception);
        }
    }

    Connection getConnection(String string, String string2, String string3, String string4, String string5, NetworkConnectionInfo networkConnectionInfo) throws SQLException {
        string = string.trim();
        string2 = string2.trim();
        return JdbcUtils.getConnection(string, string2, string3.trim(), string4, networkConnectionInfo, this.ifExists && (!this.allowSecureCreation || this.key == null || !this.key.equals(string5)));
    }

    void shutdown() {
        if (this.shutdownHandler != null) {
            this.shutdownHandler.shutdown();
        }
    }

    public void setShutdownHandler(ShutdownHandler shutdownHandler) {
        this.shutdownHandler = shutdownHandler;
    }

    public String addSession(Connection connection) throws SQLException {
        WebSession webSession = this.createNewSession("local");
        webSession.setShutdownServerOnDisconnect();
        webSession.setConnection(connection);
        webSession.put("url", connection.getMetaData().getURL());
        String string = (String)webSession.get("sessionId");
        return this.url + "/frame.jsp?jsessionid=" + string;
    }

    String startTranslate(Map<Object, Object> map) {
        if (this.translateThread != null) {
            this.translateThread.stopNow();
        }
        this.translateThread = new TranslateThread(map);
        this.translateThread.setDaemon(true);
        this.translateThread.start();
        return this.translateThread.getFileName();
    }

    @Override
    public boolean isDaemon() {
        return this.isDaemon;
    }

    void setAllowChunked(boolean bl) {
        this.allowChunked = bl;
    }

    boolean getAllowChunked() {
        return this.allowChunked;
    }

    byte[] getAdminPassword() {
        return this.adminPassword;
    }

    void setAdminPassword(String string) {
        if (string == null || string.isEmpty()) {
            this.adminPassword = null;
            return;
        }
        if (string.length() != 128) {
            throw new IllegalArgumentException("Use result of org.h2.server.web.WebServer.encodeAdminPassword(String)");
        }
        this.adminPassword = StringUtils.convertHexToBytes(string);
    }

    public static String encodeAdminPassword(String string) {
        if (string.length() < 12) {
            throw new IllegalArgumentException("Min length: 12");
        }
        byte[] byArray = MathUtils.secureRandomBytes(32);
        byte[] byArray2 = SHA256.getHashWithSalt(string.getBytes(StandardCharsets.UTF_8), byArray);
        byte[] byArray3 = Arrays.copyOf(byArray, 64);
        System.arraycopy(byArray2, 0, byArray3, 32, 32);
        return StringUtils.convertBytesToHex(byArray3);
    }

    boolean checkAdminPassword(String string) {
        if (this.adminPassword == null) {
            return false;
        }
        byte[] byArray = Arrays.copyOf(this.adminPassword, 32);
        byte[] byArray2 = new byte[32];
        System.arraycopy(this.adminPassword, 32, byArray2, 0, 32);
        return Utils.compareSecure(byArray2, SHA256.getHashWithSalt(string.getBytes(StandardCharsets.UTF_8), byArray));
    }

    static {
        SESSION_TIMEOUT = SysProperties.CONSOLE_TIMEOUT;
    }

    private class TranslateThread
    extends Thread {
        private final Path file = Paths.get("translation.properties", new String[0]);
        private final Map<Object, Object> translation;
        private volatile boolean stopNow;

        TranslateThread(Map<Object, Object> map) {
            this.translation = map;
        }

        public String getFileName() {
            return this.file.toAbsolutePath().toString();
        }

        public void stopNow() {
            this.stopNow = true;
            try {
                this.join();
            }
            catch (InterruptedException interruptedException) {
                // empty catch block
            }
        }

        @Override
        public void run() {
            while (!this.stopNow) {
                try {
                    Closeable closeable;
                    SortedProperties sortedProperties = new SortedProperties();
                    if (Files.exists(this.file, new LinkOption[0])) {
                        closeable = Files.newInputStream(this.file, new OpenOption[0]);
                        sortedProperties.load((InputStream)closeable);
                        this.translation.putAll(sortedProperties);
                    } else {
                        closeable = Files.newOutputStream(this.file, new OpenOption[0]);
                        sortedProperties.putAll(this.translation);
                        sortedProperties.store((OutputStream)closeable, "Translation");
                    }
                    Thread.sleep(1000L);
                }
                catch (Exception exception) {
                    WebServer.this.traceError(exception);
                }
            }
        }
    }
}

