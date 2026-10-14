/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.common.i18n;

import org.apache.cxf.common.i18n.Message;

public class Exception
extends java.lang.Exception {
    private static final long serialVersionUID = 1L;
    private final Message message;

    public Exception(Message msg) {
        this.message = msg;
    }

    public Exception(Message msg, Throwable t2) {
        super(t2);
        this.message = msg;
    }

    public Exception(Throwable cause) {
        super(cause);
        this.message = null;
    }

    public String getCode() {
        if (null != this.message) {
            return this.message.getCode();
        }
        return null;
    }

    @Override
    public String getMessage() {
        if (null != this.message) {
            return this.message.toString();
        }
        return null;
    }
}

