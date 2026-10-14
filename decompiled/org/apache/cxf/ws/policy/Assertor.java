/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.ws.policy;

import javax.xml.namespace.QName;
import org.apache.cxf.message.Message;

public interface Assertor {
    public void assertMessage(Message var1);

    public boolean canAssert(QName var1);
}

