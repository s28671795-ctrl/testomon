/*
 * Decompiled with CFR 0.152.
 */
package org.h2.engine;

import org.h2.command.Prepared;

public class Procedure {
    private final String name;
    private final Prepared prepared;

    public Procedure(String string, Prepared prepared) {
        this.name = string;
        this.prepared = prepared;
    }

    public String getName() {
        return this.name;
    }

    public Prepared getPrepared() {
        return this.prepared;
    }
}

