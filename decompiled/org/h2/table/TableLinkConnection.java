/*
 * Decompiled with CFR 0.152.
 */
package org.h2.table;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Objects;
import org.h2.message.DbException;
import org.h2.util.JdbcUtils;

public class TableLinkConnection {
    private final HashMap<TableLinkConnection, TableLinkConnection> map;
    private final String driver;
    private final String url;
    private final String user;
    private final String password;
    private Connection conn;
    private int useCounter;
    private boolean autocommit = true;

    private TableLinkConnection(HashMap<TableLinkConnection, TableLinkConnection> hashMap, String string, String string2, String string3, String string4) {
        this.map = hashMap;
        this.driver = string;
        this.url = string2;
        this.user = string3;
        this.password = string4;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static TableLinkConnection open(HashMap<TableLinkConnection, TableLinkConnection> hashMap, String string, String string2, String string3, String string4, boolean bl) {
        TableLinkConnection tableLinkConnection = new TableLinkConnection(hashMap, string, string2, string3, string4);
        if (!bl) {
            tableLinkConnection.open();
            return tableLinkConnection;
        }
        HashMap<TableLinkConnection, TableLinkConnection> hashMap2 = hashMap;
        synchronized (hashMap2) {
            TableLinkConnection tableLinkConnection2 = hashMap.get(tableLinkConnection);
            if (tableLinkConnection2 == null) {
                tableLinkConnection.open();
                hashMap.put(tableLinkConnection, tableLinkConnection);
                tableLinkConnection2 = tableLinkConnection;
            }
            ++tableLinkConnection2.useCounter;
            return tableLinkConnection2;
        }
    }

    private void open() {
        try {
            this.conn = JdbcUtils.getConnection(this.driver, this.url, this.user, this.password);
        }
        catch (SQLException sQLException) {
            throw DbException.convert(sQLException);
        }
    }

    public int hashCode() {
        return Objects.hashCode(this.driver) ^ Objects.hashCode(this.url) ^ Objects.hashCode(this.user) ^ Objects.hashCode(this.password);
    }

    public boolean equals(Object object) {
        if (object instanceof TableLinkConnection) {
            TableLinkConnection tableLinkConnection = (TableLinkConnection)object;
            return Objects.equals(this.driver, tableLinkConnection.driver) && Objects.equals(this.url, tableLinkConnection.url) && Objects.equals(this.user, tableLinkConnection.user) && Objects.equals(this.password, tableLinkConnection.password);
        }
        return false;
    }

    Connection getConnection() {
        return this.conn;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    void close(boolean bl) {
        boolean bl2 = false;
        HashMap<TableLinkConnection, TableLinkConnection> hashMap = this.map;
        synchronized (hashMap) {
            if (--this.useCounter <= 0 || bl) {
                bl2 = true;
                this.map.remove(this);
            }
        }
        if (bl2) {
            JdbcUtils.closeSilently(this.conn);
        }
    }

    public void setAutoCommit(boolean bl) {
        this.autocommit = bl;
    }

    public boolean getAutocommit() {
        return this.autocommit;
    }
}

