/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.ws.addressing;

import org.apache.cxf.common.i18n.Exception;
import org.apache.cxf.common.i18n.Message;

public class EndpointUtilsException
extends Exception {
    private static final long serialVersionUID = -7816054333019642525L;

    public EndpointUtilsException(Message msg) {
        super(msg);
    }

    public EndpointUtilsException(Message msg, Throwable t2) {
        super(msg, t2);
    }
}

