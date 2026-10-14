/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.common.util;

import org.apache.cxf.common.i18n.Exception;
import org.apache.cxf.common.i18n.Message;

public class Base64Exception
extends Exception {
    private static final long serialVersionUID = -4692237798562339250L;

    public Base64Exception(Message msg) {
        super(msg);
    }

    public Base64Exception(Message msg, Throwable t2) {
        super(msg, t2);
    }

    public Base64Exception(Throwable cause) {
        super(cause);
    }
}

