/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.frontend;

import org.apache.cxf.common.i18n.Message;
import org.apache.cxf.common.i18n.UncheckedException;

public class WSDLQueryException
extends UncheckedException {
    private static final long serialVersionUID = -3254386739668987981L;

    public WSDLQueryException(Message msg, Throwable t2) {
        super(msg, t2);
    }
}

