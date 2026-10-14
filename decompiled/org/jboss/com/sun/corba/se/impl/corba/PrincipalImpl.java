/*
 * Decompiled with CFR 0.152.
 */
package org.jboss.com.sun.corba.se.impl.corba;

import org.omg.CORBA.Principal;

public class PrincipalImpl
extends Principal {
    private byte[] value;

    @Override
    public void name(byte[] value) {
        this.value = value;
    }

    @Override
    public byte[] name() {
        return this.value;
    }
}

