/*
 * Decompiled with CFR 0.152.
 */
package org.h2.tools;

import java.net.URI;
import java.sql.Connection;
import java.sql.SQLException;
import org.h2.message.DbException;
import org.h2.server.Service;
import org.h2.server.ShutdownHandler;
import org.h2.server.TcpServer;
import org.h2.server.pg.PgServer;
import org.h2.server.web.WebServer;
import org.h2.util.StringUtils;
import org.h2.util.Tool;
import org.h2.util.Utils;

public class Server
extends Tool
implements Runnable,
ShutdownHandler {
    private final Service service;
    private Server web;
    private Server tcp;
    private Server pg;
    private ShutdownHandler shutdownHandler;
    private boolean fromCommandLine;
    private boolean started;

    public Server() {
        this.service = null;
    }

    public Server(Service service2, String ... stringArray) throws SQLException {
        this.verifyArgs(stringArray);
        this.service = service2;
        try {
            service2.init(stringArray);
        }
        catch (Exception exception) {
            throw DbException.toSQLException(exception);
        }
    }

    public static void main(String ... stringArray) throws SQLException {
        Server server = new Server();
        server.fromCommandLine = true;
        server.runTool(stringArray);
    }

    private void verifyArgs(String ... stringArray) throws SQLException {
        for (int i = 0; stringArray != null && i < stringArray.length; ++i) {
            String string = stringArray[i];
            if (string == null || "-?".equals(string) || "-help".equals(string)) continue;
            if (string.startsWith("-web")) {
                if ("-web".equals(string) || "-webAllowOthers".equals(string)) continue;
                if ("-webExternalNames".equals(string)) {
                    ++i;
                    continue;
                }
                if ("-webDaemon".equals(string)) continue;
                if ("-webVirtualThreads".equals(string)) {
                    ++i;
                    continue;
                }
                if ("-webSSL".equals(string)) continue;
                if ("-webPort".equals(string)) {
                    ++i;
                    continue;
                }
                if ("-webAdminPassword".equals(string)) {
                    if (this.fromCommandLine) {
                        this.throwUnsupportedOption(string);
                    }
                    ++i;
                    continue;
                }
                this.throwUnsupportedOption(string);
                continue;
            }
            if ("-browser".equals(string)) continue;
            if (string.startsWith("-tcp")) {
                if ("-tcp".equals(string) || "-tcpAllowOthers".equals(string) || "-tcpDaemon".equals(string)) continue;
                if ("-tcpVirtualThreads".equals(string)) {
                    ++i;
                    continue;
                }
                if ("-tcpSSL".equals(string)) continue;
                if ("-tcpPort".equals(string)) {
                    ++i;
                    continue;
                }
                if ("-tcpPassword".equals(string)) {
                    ++i;
                    continue;
                }
                if ("-tcpShutdown".equals(string)) {
                    ++i;
                    continue;
                }
                if ("-tcpShutdownForce".equals(string)) continue;
                this.throwUnsupportedOption(string);
                continue;
            }
            if (string.startsWith("-pg")) {
                if ("-pg".equals(string) || "-pgAllowOthers".equals(string) || "-pgDaemon".equals(string)) continue;
                if ("-pgVirtualThreads".equals(string)) {
                    ++i;
                    continue;
                }
                if ("-pgPort".equals(string)) {
                    ++i;
                    continue;
                }
                this.throwUnsupportedOption(string);
                continue;
            }
            if (string.startsWith("-ftp")) {
                if ("-ftpPort".equals(string)) {
                    ++i;
                    continue;
                }
                if ("-ftpDir".equals(string)) {
                    ++i;
                    continue;
                }
                if ("-ftpRead".equals(string)) {
                    ++i;
                    continue;
                }
                if ("-ftpWrite".equals(string)) {
                    ++i;
                    continue;
                }
                if ("-ftpWritePassword".equals(string)) {
                    ++i;
                    continue;
                }
                if ("-ftpTask".equals(string)) continue;
                this.throwUnsupportedOption(string);
                continue;
            }
            if ("-properties".equals(string)) {
                ++i;
                continue;
            }
            if ("-trace".equals(string) || "-ifExists".equals(string) || "-ifNotExists".equals(string)) continue;
            if ("-baseDir".equals(string)) {
                ++i;
                continue;
            }
            if ("-key".equals(string)) {
                i += 2;
                continue;
            }
            if ("-tool".equals(string)) continue;
            this.throwUnsupportedOption(string);
        }
    }

    @Override
    public void runTool(String ... stringArray) throws SQLException {
        block35: {
            boolean bl = false;
            boolean bl2 = false;
            boolean bl3 = false;
            boolean bl4 = false;
            boolean bl5 = false;
            boolean bl6 = false;
            String string = "";
            String string2 = "";
            boolean bl7 = true;
            for (int i = 0; stringArray != null && i < stringArray.length; ++i) {
                String string3 = stringArray[i];
                if (string3 == null) continue;
                if ("-?".equals(string3) || "-help".equals(string3)) {
                    this.showUsage();
                    return;
                }
                if (string3.startsWith("-web")) {
                    if ("-web".equals(string3)) {
                        bl7 = false;
                        bl3 = true;
                        continue;
                    }
                    if ("-webAllowOthers".equals(string3)) continue;
                    if ("-webExternalNames".equals(string3)) {
                        ++i;
                        continue;
                    }
                    if ("-webDaemon".equals(string3) || "-webSSL".equals(string3)) continue;
                    if ("-webPort".equals(string3)) {
                        ++i;
                        continue;
                    }
                    if ("-webAdminPassword".equals(string3)) {
                        if (this.fromCommandLine) {
                            this.throwUnsupportedOption(string3);
                        }
                        ++i;
                        continue;
                    }
                    this.showUsageAndThrowUnsupportedOption(string3);
                    continue;
                }
                if ("-browser".equals(string3)) {
                    bl7 = false;
                    bl4 = true;
                    continue;
                }
                if (string3.startsWith("-tcp")) {
                    if ("-tcp".equals(string3)) {
                        bl7 = false;
                        bl = true;
                        continue;
                    }
                    if ("-tcpAllowOthers".equals(string3) || "-tcpDaemon".equals(string3) || "-tcpSSL".equals(string3)) continue;
                    if ("-tcpPort".equals(string3)) {
                        ++i;
                        continue;
                    }
                    if ("-tcpPassword".equals(string3)) {
                        string = stringArray[++i];
                        continue;
                    }
                    if ("-tcpShutdown".equals(string3)) {
                        bl7 = false;
                        bl5 = true;
                        string2 = stringArray[++i];
                        continue;
                    }
                    if ("-tcpShutdownForce".equals(string3)) {
                        bl6 = true;
                        continue;
                    }
                    this.showUsageAndThrowUnsupportedOption(string3);
                    continue;
                }
                if (string3.startsWith("-pg")) {
                    if ("-pg".equals(string3)) {
                        bl7 = false;
                        bl2 = true;
                        continue;
                    }
                    if ("-pgAllowOthers".equals(string3) || "-pgDaemon".equals(string3)) continue;
                    if ("-pgPort".equals(string3)) {
                        ++i;
                        continue;
                    }
                    this.showUsageAndThrowUnsupportedOption(string3);
                    continue;
                }
                if ("-properties".equals(string3)) {
                    ++i;
                    continue;
                }
                if ("-trace".equals(string3) || "-ifExists".equals(string3) || "-ifNotExists".equals(string3)) continue;
                if ("-baseDir".equals(string3)) {
                    ++i;
                    continue;
                }
                if ("-key".equals(string3)) {
                    i += 2;
                    continue;
                }
                this.showUsageAndThrowUnsupportedOption(string3);
            }
            this.verifyArgs(stringArray);
            if (bl7) {
                bl = true;
                bl2 = true;
                bl3 = true;
                bl4 = true;
            }
            if (bl5) {
                this.out.println("Shutting down TCP Server at " + string2);
                Server.shutdownTcpServer(string2, string, bl6, false);
            }
            try {
                if (bl) {
                    this.tcp = Server.createTcpServer(stringArray);
                    this.tcp.start();
                    this.out.println(this.tcp.getStatus());
                    this.tcp.setShutdownHandler(this);
                }
                if (bl2) {
                    this.pg = Server.createPgServer(stringArray);
                    this.pg.start();
                    this.out.println(this.pg.getStatus());
                }
                if (bl3) {
                    this.web = Server.createWebServer(stringArray);
                    this.web.setShutdownHandler(this);
                    SQLException sQLException = null;
                    try {
                        this.web.start();
                    }
                    catch (Exception exception) {
                        sQLException = DbException.toSQLException(exception);
                    }
                    this.out.println(this.web.getStatus());
                    if (bl4) {
                        try {
                            Server.openBrowser(this.web.getURL());
                        }
                        catch (Exception exception) {
                            this.out.println(exception.getMessage());
                        }
                    }
                    if (sQLException != null) {
                        throw sQLException;
                    }
                    break block35;
                }
                if (bl4) {
                    this.out.println("The browser can only start if a web server is started (-web)");
                }
            }
            catch (SQLException sQLException) {
                this.stopAll();
                throw sQLException;
            }
        }
    }

    public static void shutdownTcpServer(String string, String string2, boolean bl, boolean bl2) throws SQLException {
        TcpServer.shutdown(string, string2, bl, bl2);
    }

    public String getStatus() {
        StringBuilder stringBuilder = new StringBuilder();
        if (!this.started) {
            stringBuilder.append("Not started");
        } else if (this.isRunning(false)) {
            stringBuilder.append(this.service.getType()).append(" server running at ").append(this.service.getURL()).append(" (");
            if (this.service.getAllowOthers()) {
                stringBuilder.append("others can connect");
            } else {
                stringBuilder.append("only local connections");
            }
            stringBuilder.append(')');
        } else {
            stringBuilder.append("The ").append(this.service.getType()).append(" server could not be started. Possible cause: another server is already running at ").append(this.service.getURL());
        }
        return stringBuilder.toString();
    }

    public static Server createWebServer(String ... stringArray) throws SQLException {
        return Server.createWebServer(stringArray, null, false);
    }

    static Server createWebServer(String[] stringArray, String string, boolean bl) throws SQLException {
        WebServer webServer = new WebServer();
        webServer.setKey(string);
        webServer.setAllowSecureCreation(bl);
        Server server = new Server(webServer, stringArray);
        webServer.setShutdownHandler(server);
        return server;
    }

    public static Server createTcpServer(String ... stringArray) throws SQLException {
        TcpServer tcpServer = new TcpServer();
        Server server = new Server(tcpServer, stringArray);
        tcpServer.setShutdownHandler(server);
        return server;
    }

    public static Server createPgServer(String ... stringArray) throws SQLException {
        return new Server(new PgServer(), stringArray);
    }

    public Server start() throws SQLException {
        try {
            this.started = true;
            this.service.start();
            String string = this.service.getURL();
            int n = string.indexOf(63);
            if (n >= 0) {
                string = string.substring(0, n);
            }
            String string2 = this.service.getName() + " (" + string + ')';
            Thread thread2 = new Thread(this, string2);
            thread2.setDaemon(this.service.isDaemon());
            thread2.start();
            for (int i = 1; i < 64; i += i) {
                Server.wait(i);
                if (!this.isRunning(false)) continue;
                return this;
            }
            if (this.isRunning(true)) {
                return this;
            }
            throw DbException.get(90061, string2, "timeout; please check your network configuration, specially the file /etc/hosts");
        }
        catch (DbException dbException) {
            throw DbException.toSQLException(dbException);
        }
    }

    private static void wait(int n) {
        try {
            long l = (long)n * (long)n;
            Thread.sleep(l);
        }
        catch (InterruptedException interruptedException) {
            // empty catch block
        }
    }

    private void stopAll() {
        Server server = this.web;
        if (server != null && server.isRunning(false)) {
            server.stop();
            this.web = null;
        }
        if ((server = this.tcp) != null && server.isRunning(false)) {
            server.stop();
            this.tcp = null;
        }
        if ((server = this.pg) != null && server.isRunning(false)) {
            server.stop();
            this.pg = null;
        }
    }

    public boolean isRunning(boolean bl) {
        return this.service.isRunning(bl);
    }

    public void stop() {
        this.started = false;
        if (this.service != null) {
            this.service.stop();
        }
    }

    public String getURL() {
        return this.service.getURL();
    }

    public int getPort() {
        return this.service.getPort();
    }

    @Override
    public void run() {
        try {
            this.service.listen();
        }
        catch (Exception exception) {
            DbException.traceThrowable(exception);
        }
    }

    public void setShutdownHandler(ShutdownHandler shutdownHandler) {
        this.shutdownHandler = shutdownHandler;
    }

    @Override
    public void shutdown() {
        if (this.shutdownHandler != null) {
            this.shutdownHandler.shutdown();
        } else {
            this.stopAll();
        }
    }

    public Service getService() {
        return this.service;
    }

    public static void openBrowser(String string) throws Exception {
        try {
            String[] stringArray;
            String string2 = StringUtils.toLowerEnglish(Utils.getProperty("os.name", "linux"));
            Runtime runtime = Runtime.getRuntime();
            String string3 = Utils.getProperty("h2.browser", null);
            if (string3 == null) {
                try {
                    string3 = System.getenv("BROWSER");
                }
                catch (SecurityException securityException) {
                    // empty catch block
                }
            }
            if (string3 != null) {
                if (string3.startsWith("call:")) {
                    string3 = string3.substring("call:".length());
                    Utils.callStaticMethod(string3, string);
                } else if (string3.contains("%url")) {
                    String[] stringArray2 = StringUtils.arraySplit(string3, ',', false);
                    for (int i = 0; i < stringArray2.length; ++i) {
                        stringArray2[i] = StringUtils.replaceAll(stringArray2[i], "%url", string);
                    }
                    runtime.exec(stringArray2);
                } else if (string2.contains("windows")) {
                    runtime.exec(new String[]{"cmd.exe", "/C", string3, string});
                } else {
                    runtime.exec(new String[]{string3, string});
                }
                return;
            }
            try {
                stringArray = Class.forName("java.awt.Desktop");
                Boolean bl = (Boolean)stringArray.getMethod("isDesktopSupported", new Class[0]).invoke(null, new Object[0]);
                String[] stringArray3 = new URI(string);
                if (bl.booleanValue()) {
                    Object object = stringArray.getMethod("getDesktop", new Class[0]).invoke(null, new Object[0]);
                    stringArray.getMethod("browse", URI.class).invoke(object, new Object[]{stringArray3});
                    return;
                }
            }
            catch (Exception exception) {
                // empty catch block
            }
            if (string2.contains("windows")) {
                runtime.exec(new String[]{"rundll32", "url.dll,FileProtocolHandler", string});
            } else if (string2.contains("mac") || string2.contains("darwin")) {
                Runtime.getRuntime().exec(new String[]{"open", string});
            } else {
                stringArray = new String[]{"xdg-open", "chromium", "google-chrome", "firefox", "mozilla-firefox", "mozilla", "konqueror", "netscape", "opera", "midori"};
                boolean bl = false;
                for (String string4 : stringArray) {
                    try {
                        runtime.exec(new String[]{string4, string});
                        bl = true;
                        break;
                    }
                    catch (Exception exception) {
                    }
                }
                if (!bl) {
                    throw new Exception("Browser detection failed, and java property 'h2.browser' and environment variable BROWSER are not set to a browser executable.");
                }
            }
        }
        catch (Exception exception) {
            throw new Exception("Failed to start a browser to open the URL " + string + ": " + exception.getMessage());
        }
    }

    public static void startWebServer(Connection connection) throws SQLException {
        Server.startWebServer(connection, false);
    }

    public static void startWebServer(Connection connection, boolean bl) throws SQLException {
        WebServer webServer = new WebServer();
        String[] stringArray = bl ? new String[]{"-webPort", "0", "-properties", "null"} : new String[]{"-webPort", "0"};
        Server server = new Server(webServer, stringArray);
        server.start();
        Server server2 = new Server();
        server2.web = server;
        webServer.setShutdownHandler(server2);
        String string = webServer.addSession(connection);
        try {
            Server.openBrowser(string);
            while (!webServer.isStopped()) {
                Thread.sleep(1000L);
            }
        }
        catch (Exception exception) {
            // empty catch block
        }
    }
}

