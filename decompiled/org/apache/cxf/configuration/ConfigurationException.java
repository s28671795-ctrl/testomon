/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.configuration;

import org.apache.cxf.common.i18n.Message;
import org.apache.cxf.common.i18n.UncheckedException;

public class ConfigurationException
extends UncheckedException {
    private static final long serialVersionUID = 1L;

    public ConfigurationException(Message msg) {
        super(msg);
    }

    public ConfigurationException(Message msg, Throwable cause) {
        super(msg, cause);
    }

    public ConfigurationException(Throwable cause) {
        super(cause);
    }
}

