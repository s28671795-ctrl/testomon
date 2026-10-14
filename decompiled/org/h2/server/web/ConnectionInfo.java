/*
 * Decompiled with CFR 0.152.
 */
package org.h2.server.web;

import org.h2.util.StringUtils;

public class ConnectionInfo
implements Comparable<ConnectionInfo> {
    public String driver;
    public String url;
    public String user;
    String name;
    int lastAccess;

    ConnectionInfo() {
    }

    public ConnectionInfo(String string) {
        String[] stringArray = StringUtils.arraySplit(string, '|', false);
        this.name = ConnectionInfo.get(stringArray, 0);
        this.driver = ConnectionInfo.get(stringArray, 1);
        this.url = ConnectionInfo.get(stringArray, 2);
        this.user = ConnectionInfo.get(stringArray, 3);
    }

    private static String get(String[] stringArray, int n) {
        return stringArray != null && stringArray.length > n ? stringArray[n] : "";
    }

    String getString() {
        return StringUtils.arrayCombine(new String[]{this.name, this.driver, this.url, this.user}, '|');
    }

    @Override
    public int compareTo(ConnectionInfo connectionInfo) {
        return Integer.compare(connectionInfo.lastAccess, this.lastAccess);
    }
}

