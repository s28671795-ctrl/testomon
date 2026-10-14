/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.bus.extension;

import org.apache.cxf.common.i18n.Message;
import org.apache.cxf.common.i18n.UncheckedException;

public class ExtensionException
extends UncheckedException {
    private static final long serialVersionUID = 1L;

    public ExtensionException(Message msg) {
        super(msg);
    }

    public ExtensionException(Message msg, Throwable cause) {
        super(msg, cause);
    }

    public ExtensionException(Throwable cause) {
        super(cause);
    }
}

