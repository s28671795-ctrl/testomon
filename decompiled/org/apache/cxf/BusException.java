/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf;

import org.apache.cxf.common.i18n.Exception;
import org.apache.cxf.common.i18n.Message;

public class BusException
extends Exception {
    private static final long serialVersionUID = 1L;

    public BusException(Message msg) {
        super(msg);
    }

    public BusException(Message msg, Throwable cause) {
        super(msg, cause);
    }

    public BusException(Throwable cause) {
        super(cause);
    }
}

