/*
 * Decompiled with CFR 0.152.
 */
package org.jboss.com.sun.corba.se.pept.protocol;

import org.jboss.com.sun.corba.se.pept.broker.Broker;
import org.jboss.com.sun.corba.se.pept.encoding.InputObject;
import org.jboss.com.sun.corba.se.pept.encoding.OutputObject;
import org.jboss.com.sun.corba.se.pept.transport.Connection;
import org.jboss.com.sun.corba.se.pept.transport.ContactInfo;

public interface MessageMediator {
    public Broker getBroker();

    public ContactInfo getContactInfo();

    public Connection getConnection();

    public void initializeMessage();

    public void finishSendingRequest();

    @Deprecated
    public InputObject waitForResponse();

    public void setOutputObject(OutputObject var1);

    public OutputObject getOutputObject();

    public void setInputObject(InputObject var1);

    public InputObject getInputObject();
}

