/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.service.factory;

import java.util.logging.Logger;
import org.apache.cxf.common.i18n.Message;
import org.apache.cxf.common.i18n.UncheckedException;

public class ServiceConstructionException
extends UncheckedException {
    private static final long serialVersionUID = -4242545895784708313L;

    public ServiceConstructionException(Message msg, Throwable t2) {
        super(msg, t2);
    }

    public ServiceConstructionException(Message msg) {
        super(msg);
    }

    public ServiceConstructionException(Throwable cause) {
        super(cause);
    }

    public ServiceConstructionException(String msg, Logger log) {
        super(new Message(msg, log, new Object[0]));
    }

    public ServiceConstructionException(String msg, Logger log, Throwable cause) {
        super(new Message(msg, log, new Object[0]), cause);
    }
}

