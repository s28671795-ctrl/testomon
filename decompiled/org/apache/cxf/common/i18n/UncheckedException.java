/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.common.i18n;

import java.util.ResourceBundle;
import java.util.logging.Logger;
import org.apache.cxf.common.i18n.Message;

public class UncheckedException
extends RuntimeException {
    private static final long serialVersionUID = 1L;
    protected final Message message;

    public UncheckedException(Message msg) {
        this.message = msg;
    }

    public UncheckedException(Message msg, Throwable t2) {
        super(t2);
        this.message = msg;
    }

    public UncheckedException(Throwable cause) {
        super(cause);
        this.message = null;
    }

    public UncheckedException(Logger log, String msg, Object ... params) {
        this.message = new Message(msg, log, params);
    }

    public UncheckedException(ResourceBundle bundle2, String msg, Object ... params) {
        this.message = new Message(msg, bundle2, params);
    }

    public UncheckedException(Logger log, String msg, Throwable t2, Object ... params) {
        super(t2);
        this.message = new Message(msg, log, params);
    }

    public UncheckedException(ResourceBundle bundle2, String msg, Throwable t2, Object ... params) {
        super(t2);
        this.message = new Message(msg, bundle2, params);
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

