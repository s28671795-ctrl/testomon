/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.util;

public class WrappedException
extends RuntimeException {
    private static final long serialVersionUID = 3257290240212217905L;

    public WrappedException(Throwable original) {
        super(original);
    }
}

