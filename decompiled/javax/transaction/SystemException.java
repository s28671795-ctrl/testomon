/*
 * Decompiled with CFR 0.152.
 */
package javax.transaction;

public class SystemException
extends Exception {
    public int errorCode;

    public SystemException() {
    }

    public SystemException(String message) {
        super(message);
    }

    public SystemException(int errorCode) {
        this.errorCode = errorCode;
    }
}

