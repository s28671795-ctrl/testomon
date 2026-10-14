/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CSIIOP;

import org.omg.CORBA.portable.IDLEntity;

public final class ServiceConfiguration
implements IDLEntity {
    private static final long serialVersionUID = 1L;
    public int syntax;
    public byte[] name;

    public ServiceConfiguration() {
    }

    public ServiceConfiguration(int syntax, byte[] name) {
        this.syntax = syntax;
        this.name = name;
    }
}

