/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.protocol.answers.ws.servers;

public enum WSPacketType {
    SERVER_TIME,
    SERVER;


    public String toString() {
        return this.name().toLowerCase();
    }
}

