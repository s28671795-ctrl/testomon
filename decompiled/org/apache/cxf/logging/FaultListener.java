/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.logging;

import org.apache.cxf.message.Message;

public interface FaultListener {
    public boolean faultOccurred(Exception var1, String var2, Message var3);
}

