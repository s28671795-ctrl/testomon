/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.endpoint;

import org.apache.cxf.common.i18n.Exception;
import org.apache.cxf.common.i18n.Message;

public class EndpointException
extends Exception {
    private static final long serialVersionUID = 2718396758999944442L;

    public EndpointException(Message msg) {
        super(msg);
    }

    public EndpointException(Message msg, Throwable t2) {
        super(msg, t2);
    }

    public EndpointException(Throwable cause) {
        super(cause);
    }
}

