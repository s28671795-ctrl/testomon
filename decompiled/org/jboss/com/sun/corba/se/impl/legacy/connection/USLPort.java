/*
 * Decompiled with CFR 0.152.
 */
package org.jboss.com.sun.corba.se.impl.legacy.connection;

public class USLPort {
    private String type;
    private int port;

    public USLPort(String type, int port) {
        this.type = type;
        this.port = port;
    }

    public String getType() {
        return this.type;
    }

    public int getPort() {
        return this.port;
    }

    public String toString() {
        return this.type + ":" + this.port;
    }
}

