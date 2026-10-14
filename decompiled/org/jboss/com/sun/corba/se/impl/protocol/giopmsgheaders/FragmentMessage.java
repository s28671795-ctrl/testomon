/*
 * Decompiled with CFR 0.152.
 */
package org.jboss.com.sun.corba.se.impl.protocol.giopmsgheaders;

import org.jboss.com.sun.corba.se.impl.protocol.giopmsgheaders.Message;

public interface FragmentMessage
extends Message {
    public int getRequestId();

    public int getHeaderLength();
}

