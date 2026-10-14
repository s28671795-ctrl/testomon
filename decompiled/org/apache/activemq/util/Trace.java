/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.util;

class Trace
extends Throwable {
    public int count = 1;
    public final long id = this.calculateIdentifier();

    Trace() {
    }

    private long calculateIdentifier() {
        int len = 0;
        for (int i = 0; i < this.getStackTrace().length; ++i) {
            len += this.getStackTrace()[i].toString().intern().hashCode();
        }
        return len;
    }
}

