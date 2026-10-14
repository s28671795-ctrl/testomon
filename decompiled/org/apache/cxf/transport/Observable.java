/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.transport;

import org.apache.cxf.transport.MessageObserver;

public interface Observable {
    public void setMessageObserver(MessageObserver var1);

    public MessageObserver getMessageObserver();
}

