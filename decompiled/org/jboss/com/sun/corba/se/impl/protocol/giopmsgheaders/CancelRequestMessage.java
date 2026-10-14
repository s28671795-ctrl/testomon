/*
 * Decompiled with CFR 0.152.
 */
package org.jboss.com.sun.corba.se.impl.protocol.giopmsgheaders;

import org.jboss.com.sun.corba.se.impl.protocol.giopmsgheaders.Message;

public interface CancelRequestMessage
extends Message {
    public static final int CANCEL_REQ_MSG_SIZE = 4;

    public int getRequestId();
}

