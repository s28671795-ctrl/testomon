/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.jaxws;

import org.apache.cxf.common.i18n.Message;
import org.apache.cxf.common.i18n.UncheckedException;

public class JaxWsConfigurationException
extends UncheckedException {
    private static final long serialVersionUID = -7657280729669754145L;

    public JaxWsConfigurationException(Message msg) {
        super(msg);
    }

    public JaxWsConfigurationException(Message msg, Throwable t2) {
        super(msg, t2);
    }

    public JaxWsConfigurationException(Throwable cause) {
        super(cause);
    }
}

