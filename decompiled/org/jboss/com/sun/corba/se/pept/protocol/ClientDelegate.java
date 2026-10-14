/*
 * Decompiled with CFR 0.152.
 */
package org.jboss.com.sun.corba.se.pept.protocol;

import org.jboss.com.sun.corba.se.pept.broker.Broker;
import org.jboss.com.sun.corba.se.pept.transport.ContactInfoList;

public interface ClientDelegate {
    public Broker getBroker();

    public ContactInfoList getContactInfoList();
}

