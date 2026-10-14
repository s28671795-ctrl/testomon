/*
 * Decompiled with CFR 0.152.
 */
package org.h2.server.web;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import org.h2.bnf.Bnf;
import org.h2.bnf.context.DbContents;
import org.h2.bnf.context.DbContextRule;
import org.h2.message.DbException;
import org.h2.server.web.WebServer;

class WebSession {
    private static final int MAX_HISTORY = 1000;
    long lastAccess;
    final HashMap<String, Object> map = new HashMap();
    Locale locale;
    Statement executingStatement;
    ResultSet result;
    private final WebServer server;
    private final ArrayList<String> commandHistory;
    private Connection conn;
    private DatabaseMetaData meta;
    private DbContents contents = new DbContents();
    private Bnf bnf;
    private boolean shutdownServerOnDisconnect;

    WebSession(WebServer webServer) {
        this.server = webServer;
        this.commandHistory = webServer.getCommandHistoryList();
    }

    void put(String string, Object object) {
        this.map.put(string, object);
    }

    Object get(String string) {
        if ("sessions".equals(string)) {
            return this.server.getSessions();
        }
        return this.map.get(string);
    }

    Object remove(String string) {
        return this.map.remove(string);
    }

    Bnf getBnf() {
        return this.bnf;
    }

    void loadBnf() {
        try {
            Bnf bnf = Bnf.getInstance(null);
            DbContextRule dbContextRule = new DbContextRule(this.contents, 0);
            DbContextRule dbContextRule2 = new DbContextRule(this.contents, 3);
            DbContextRule dbContextRule3 = new DbContextRule(this.contents, 2);
            DbContextRule dbContextRule4 = new DbContextRule(this.contents, 1);
            DbContextRule dbContextRule5 = new DbContextRule(this.contents, 5);
            DbContextRule dbContextRule6 = new DbContextRule(this.contents, 4);
            DbContextRule dbContextRule7 = new DbContextRule(this.contents, 6);
            bnf.updateTopic("procedure", dbContextRule7);
            bnf.updateTopic("column_name", dbContextRule);
            bnf.updateTopic("new_table_alias", dbContextRule2);
            bnf.updateTopic("table_alias", dbContextRule3);
            bnf.updateTopic("column_alias", dbContextRule6);
            bnf.updateTopic("table_name", dbContextRule4);
            bnf.updateTopic("schema_name", dbContextRule5);
            bnf.linkStatements();
            this.bnf = bnf;
        }
        catch (Exception exception) {
            this.server.traceError(exception);
        }
    }

    String getCommand(int n) {
        return this.commandHistory.get(n);
    }

    void addCommand(String string) {
        int n;
        if (string == null) {
            return;
        }
        if ((string = string.trim()).isEmpty()) {
            return;
        }
        if (this.commandHistory.size() > 1000) {
            this.commandHistory.remove(0);
        }
        if ((n = this.commandHistory.indexOf(string)) >= 0) {
            this.commandHistory.remove(n);
        }
        this.commandHistory.add(string);
        if (this.server.isCommandHistoryAllowed()) {
            this.server.saveCommandHistoryList(this.commandHistory);
        }
    }

    ArrayList<String> getCommandHistory() {
        return this.commandHistory;
    }

    HashMap<String, Object> getInfo() {
        HashMap<String, Object> hashMap = new HashMap<String, Object>();
        hashMap.putAll(this.map);
        hashMap.put("lastAccess", new Timestamp(this.lastAccess).toString());
        try {
            hashMap.put("url", this.conn == null ? "${text.admin.notConnected}" : this.conn.getMetaData().getURL());
            hashMap.put("user", this.conn == null ? "-" : this.conn.getMetaData().getUserName());
            hashMap.put("lastQuery", this.commandHistory.isEmpty() ? "" : this.commandHistory.get(0));
            hashMap.put("executing", this.executingStatement == null ? "${text.admin.no}" : "${text.admin.yes}");
        }
        catch (SQLException sQLException) {
            DbException.traceThrowable(sQLException);
        }
        return hashMap;
    }

    void setConnection(Connection connection) throws SQLException {
        this.conn = connection;
        this.meta = connection == null ? null : connection.getMetaData();
        this.contents = new DbContents();
    }

    DatabaseMetaData getMetaData() {
        return this.meta;
    }

    Connection getConnection() {
        return this.conn;
    }

    DbContents getContents() {
        return this.contents;
    }

    void setShutdownServerOnDisconnect() {
        this.shutdownServerOnDisconnect = true;
    }

    boolean getShutdownServerOnDisconnect() {
        return this.shutdownServerOnDisconnect;
    }

    void close() {
        if (this.executingStatement != null) {
            try {
                this.executingStatement.cancel();
            }
            catch (Exception exception) {
                // empty catch block
            }
        }
        if (this.conn != null) {
            try {
                this.conn.close();
            }
            catch (Exception exception) {
                // empty catch block
            }
        }
    }
}

