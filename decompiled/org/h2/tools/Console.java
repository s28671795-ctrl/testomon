/*
 * Decompiled with CFR 0.152.
 */
package org.h2.tools;

import java.sql.Connection;
import java.sql.SQLException;
import org.h2.server.ShutdownHandler;
import org.h2.tools.Server;
import org.h2.util.JdbcUtils;
import org.h2.util.MathUtils;
import org.h2.util.StringUtils;
import org.h2.util.Tool;
import org.h2.util.Utils;

public class Console
extends Tool
implements ShutdownHandler {
    Server web;
    private Server tcp;
    private Server pg;
    boolean isWindows;

    public static void main(String ... stringArray) throws SQLException {
        Console console;
        try {
            console = (Console)Utils.newInstance("org.h2.tools.GUIConsole", new Object[0]);
        }
        catch (Exception | NoClassDefFoundError throwable) {
            console = new Console();
        }
        console.runTool(stringArray);
    }

    @Override
    public void runTool(String ... stringArray) throws SQLException {
        SQLException sQLException;
        block47: {
            boolean bl;
            boolean bl2;
            block46: {
                Connection connection;
                this.isWindows = Utils.getProperty("os.name", "").startsWith("Windows");
                boolean bl3 = false;
                bl2 = false;
                boolean bl4 = false;
                boolean bl5 = false;
                boolean bl6 = false;
                boolean bl7 = true;
                bl = stringArray != null && stringArray.length > 0;
                String string = null;
                String string2 = null;
                String string3 = null;
                String string4 = null;
                boolean bl8 = false;
                boolean bl9 = false;
                String string5 = "";
                String string6 = "";
                boolean bl10 = false;
                boolean bl11 = false;
                for (int i = 0; stringArray != null && i < stringArray.length; ++i) {
                    String string7 = stringArray[i];
                    if (string7 == null) continue;
                    if ("-?".equals(string7) || "-help".equals(string7)) {
                        this.showUsage();
                        return;
                    }
                    if ("-url".equals(string7)) {
                        bl7 = false;
                        string2 = stringArray[++i];
                        continue;
                    }
                    if ("-driver".equals(string7)) {
                        string = stringArray[++i];
                        continue;
                    }
                    if ("-user".equals(string7)) {
                        string3 = stringArray[++i];
                        continue;
                    }
                    if ("-password".equals(string7)) {
                        string4 = stringArray[++i];
                        continue;
                    }
                    if (string7.startsWith("-web")) {
                        if ("-web".equals(string7)) {
                            bl7 = false;
                            bl4 = true;
                            continue;
                        }
                        if ("-webAllowOthers".equals(string7)) {
                            bl11 = true;
                            continue;
                        }
                        if ("-webExternalNames".equals(string7)) {
                            ++i;
                            continue;
                        }
                        if ("-webDaemon".equals(string7)) continue;
                        if ("-webVirtualThreads".equals(string7)) {
                            ++i;
                            continue;
                        }
                        if ("-webSSL".equals(string7)) continue;
                        if ("-webPort".equals(string7)) {
                            ++i;
                            continue;
                        }
                        this.showUsageAndThrowUnsupportedOption(string7);
                        continue;
                    }
                    if ("-tool".equals(string7)) {
                        bl7 = false;
                        bl4 = true;
                        bl5 = true;
                        continue;
                    }
                    if ("-browser".equals(string7)) {
                        bl7 = false;
                        bl4 = true;
                        bl6 = true;
                        continue;
                    }
                    if (string7.startsWith("-tcp")) {
                        if ("-tcp".equals(string7)) {
                            bl7 = false;
                            bl3 = true;
                            continue;
                        }
                        if ("-tcpAllowOthers".equals(string7) || "-tcpDaemon".equals(string7)) continue;
                        if ("-tcpVirtualThreads".equals(string7)) {
                            ++i;
                            continue;
                        }
                        if ("-tcpSSL".equals(string7)) continue;
                        if ("-tcpPort".equals(string7)) {
                            ++i;
                            continue;
                        }
                        if ("-tcpPassword".equals(string7)) {
                            string5 = stringArray[++i];
                            continue;
                        }
                        if ("-tcpShutdown".equals(string7)) {
                            bl7 = false;
                            bl8 = true;
                            string6 = stringArray[++i];
                            continue;
                        }
                        if ("-tcpShutdownForce".equals(string7)) {
                            bl9 = true;
                            continue;
                        }
                        this.showUsageAndThrowUnsupportedOption(string7);
                        continue;
                    }
                    if (string7.startsWith("-pg")) {
                        if ("-pg".equals(string7)) {
                            bl7 = false;
                            bl2 = true;
                            continue;
                        }
                        if ("-pgAllowOthers".equals(string7) || "-pgDaemon".equals(string7)) continue;
                        if ("-pgVirtualThreads".equals(string7)) {
                            ++i;
                            continue;
                        }
                        if ("-pgPort".equals(string7)) {
                            ++i;
                            continue;
                        }
                        this.showUsageAndThrowUnsupportedOption(string7);
                        continue;
                    }
                    if ("-properties".equals(string7)) {
                        ++i;
                        continue;
                    }
                    if ("-trace".equals(string7)) continue;
                    if ("-ifExists".equals(string7)) {
                        bl10 = true;
                        continue;
                    }
                    if ("-baseDir".equals(string7)) {
                        ++i;
                        continue;
                    }
                    this.showUsageAndThrowUnsupportedOption(string7);
                }
                if (bl7) {
                    bl4 = true;
                    bl5 = true;
                    bl6 = true;
                    bl3 = true;
                    bl2 = true;
                }
                if (bl8) {
                    this.out.println("Shutting down TCP Server at " + string6);
                    Server.shutdownTcpServer(string6, string5, bl9, false);
                }
                sQLException = null;
                boolean bl12 = false;
                if (string2 != null) {
                    connection = JdbcUtils.getConnection(string, string2, string3, string4);
                    Server.startWebServer(connection);
                }
                if (bl4) {
                    try {
                        connection = bl11 ? null : StringUtils.convertBytesToHex(MathUtils.secureRandomBytes(32));
                        this.web = Server.createWebServer(stringArray, (String)((Object)connection), !bl10);
                        this.web.setShutdownHandler(this);
                        this.web.start();
                        if (bl) {
                            this.out.println(this.web.getStatus());
                        }
                        bl12 = true;
                    }
                    catch (SQLException sQLException2) {
                        this.printProblem(sQLException2, this.web);
                        sQLException = sQLException2;
                    }
                }
                if (bl5 && bl12) {
                    this.show();
                }
                if (bl6 && this.web != null) {
                    this.openBrowser(this.web.getURL());
                }
                if (bl3) {
                    try {
                        this.tcp = Server.createTcpServer(stringArray);
                        this.tcp.start();
                        if (bl) {
                            this.out.println(this.tcp.getStatus());
                        }
                        this.tcp.setShutdownHandler(this);
                    }
                    catch (SQLException sQLException3) {
                        this.printProblem(sQLException3, this.tcp);
                        if (sQLException != null) break block46;
                        sQLException = sQLException3;
                    }
                }
            }
            if (bl2) {
                try {
                    this.pg = Server.createPgServer(stringArray);
                    this.pg.start();
                    if (bl) {
                        this.out.println(this.pg.getStatus());
                    }
                }
                catch (SQLException sQLException4) {
                    this.printProblem(sQLException4, this.pg);
                    if (sQLException != null) break block47;
                    sQLException = sQLException4;
                }
            }
        }
        if (sQLException != null) {
            this.shutdown();
            throw sQLException;
        }
    }

    void show() {
    }

    private void printProblem(Exception exception, Server server) {
        if (server == null) {
            exception.printStackTrace();
        } else {
            this.out.println(server.getStatus());
            this.out.println("Root cause: " + exception.getMessage());
        }
    }

    @Override
    public void shutdown() {
        if (this.web != null && this.web.isRunning(false)) {
            this.web.stop();
            this.web = null;
        }
        if (this.tcp != null && this.tcp.isRunning(false)) {
            this.tcp.stop();
            this.tcp = null;
        }
        if (this.pg != null && this.pg.isRunning(false)) {
            this.pg.stop();
            this.pg = null;
        }
    }

    void openBrowser(String string) {
        try {
            Server.openBrowser(string);
        }
        catch (Exception exception) {
            this.out.println(exception.getMessage());
        }
    }
}

