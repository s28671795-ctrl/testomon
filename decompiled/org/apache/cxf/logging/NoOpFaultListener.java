/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.logging;

import org.apache.cxf.logging.FaultListener;
import org.apache.cxf.message.Message;

public class NoOpFaultListener
implements FaultListener {
    @Override
    public boolean faultOccurred(Exception exception, String description, Message message) {
        return false;
    }
}

