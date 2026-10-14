/*
 * Decompiled with CFR 0.152.
 */
package org.jboss.com.sun.corba.se.spi.copyobject;

public class ReflectiveCopyException
extends Exception {
    private static final long serialVersionUID = 5674305390503429316L;

    public ReflectiveCopyException() {
    }

    public ReflectiveCopyException(String msg) {
        super(msg);
    }

    public ReflectiveCopyException(String msg, Throwable t2) {
        super(msg, t2);
    }
}

