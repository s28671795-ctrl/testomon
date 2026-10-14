/*
 * Decompiled with CFR 0.152.
 */
package org.apache.wss4j.common;

public class WSSPolicyException
extends Exception {
    private static final long serialVersionUID = 5904800255533588133L;

    public WSSPolicyException(String message, Throwable e) {
        super(message, e);
    }

    public WSSPolicyException(String message) {
        super(message);
    }
}

