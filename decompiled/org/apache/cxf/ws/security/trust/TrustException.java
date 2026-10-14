/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.ws.security.trust;

import java.util.logging.Logger;
import org.apache.cxf.common.i18n.Message;
import org.apache.cxf.common.i18n.UncheckedException;

public class TrustException
extends UncheckedException {
    private static final long serialVersionUID = -2957463932630164766L;

    public TrustException(Message msg) {
        super(msg);
    }

    public TrustException(Message msg, Throwable t2) {
        super(msg, t2);
    }

    public TrustException(Logger log, String msg, Object ... params) {
        super(log, msg, params);
    }

    public TrustException(Throwable cause) {
        super(cause);
    }

    public TrustException(String msg, Logger log) {
        super(new Message(msg, log, new Object[0]));
    }

    public TrustException(String msg, Throwable t2, Logger log) {
        super(new Message(msg, log, new Object[0]), t2);
    }
}

