/*
 * Decompiled with CFR 0.152.
 */
package org.jboss.com.sun.corba.se.pept.protocol;

import java.util.Iterator;
import org.jboss.com.sun.corba.se.pept.protocol.ClientRequestDispatcher;
import org.jboss.com.sun.corba.se.pept.protocol.MessageMediator;

public interface ClientInvocationInfo {
    public Iterator<Object> getContactInfoListIterator();

    public void setContactInfoListIterator(Iterator<Object> var1);

    public boolean isRetryInvocation();

    public void setIsRetryInvocation(boolean var1);

    public int getEntryCount();

    public void incrementEntryCount();

    public void decrementEntryCount();

    public void setClientRequestDispatcher(ClientRequestDispatcher var1);

    public ClientRequestDispatcher getClientRequestDispatcher();

    public void setMessageMediator(MessageMediator var1);

    public MessageMediator getMessageMediator();
}

