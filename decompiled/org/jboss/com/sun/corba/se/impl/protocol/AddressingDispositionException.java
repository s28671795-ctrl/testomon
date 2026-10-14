/*
 * Decompiled with CFR 0.152.
 */
package org.jboss.com.sun.corba.se.impl.protocol;

public class AddressingDispositionException
extends RuntimeException {
    private static final long serialVersionUID = 8886744792771299936L;
    private short expectedAddrDisp = 0;

    public AddressingDispositionException(short expectedAddrDisp) {
        this.expectedAddrDisp = expectedAddrDisp;
    }

    public short expectedAddrDisp() {
        return this.expectedAddrDisp;
    }
}

