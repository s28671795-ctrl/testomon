/*
 * Decompiled with CFR 0.152.
 */
package org.jboss.com.sun.corba.se.pept.encoding;

import java.io.IOException;
import org.jboss.com.sun.corba.se.pept.protocol.MessageMediator;

public interface InputObject {
    public void setMessageMediator(MessageMediator var1);

    public MessageMediator getMessageMediator();

    public void close() throws IOException;
}

