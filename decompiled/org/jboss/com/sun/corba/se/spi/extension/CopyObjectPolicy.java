/*
 * Decompiled with CFR 0.152.
 */
package org.jboss.com.sun.corba.se.spi.extension;

import org.omg.CORBA.LocalObject;
import org.omg.CORBA.Policy;

public class CopyObjectPolicy
extends LocalObject
implements Policy {
    private final int value;

    public CopyObjectPolicy(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }

    @Override
    public int policy_type() {
        return 1398079490;
    }

    @Override
    public Policy copy() {
        return this;
    }

    @Override
    public void destroy() {
    }

    public String toString() {
        return "CopyObjectPolicy[" + this.value + "]";
    }
}

