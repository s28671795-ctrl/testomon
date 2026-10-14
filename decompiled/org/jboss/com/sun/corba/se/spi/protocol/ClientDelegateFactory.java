/*
 * Decompiled with CFR 0.152.
 */
package org.jboss.com.sun.corba.se.spi.protocol;

import org.jboss.com.sun.corba.se.spi.protocol.CorbaClientDelegate;
import org.jboss.com.sun.corba.se.spi.transport.CorbaContactInfoList;

public interface ClientDelegateFactory {
    public CorbaClientDelegate create(CorbaContactInfoList var1);
}

