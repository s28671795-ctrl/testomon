/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.wsdl11;

import java.util.logging.Logger;
import org.apache.cxf.common.i18n.Message;
import org.apache.cxf.common.i18n.UncheckedException;

public class WSDLRuntimeException
extends UncheckedException {
    private static final long serialVersionUID = 969542255108690960L;

    public WSDLRuntimeException(Message msg, Throwable t2) {
        super(msg, t2);
    }

    public WSDLRuntimeException(Message msg) {
        super(msg);
    }

    public WSDLRuntimeException(Throwable cause) {
        super(cause);
    }

    public WSDLRuntimeException(Logger log, String msg, Object ... params) {
        super(log, msg, params);
    }
}

