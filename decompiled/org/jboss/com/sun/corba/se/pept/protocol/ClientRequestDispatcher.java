/*
 * Decompiled with CFR 0.152.
 */
package org.jboss.com.sun.corba.se.pept.protocol;

import org.jboss.com.sun.corba.se.pept.broker.Broker;
import org.jboss.com.sun.corba.se.pept.encoding.InputObject;
import org.jboss.com.sun.corba.se.pept.encoding.OutputObject;
import org.jboss.com.sun.corba.se.pept.transport.ContactInfo;
import org.omg.CORBA.portable.ApplicationException;
import org.omg.CORBA.portable.RemarshalException;

public interface ClientRequestDispatcher {
    public OutputObject beginRequest(Object var1, String var2, boolean var3, ContactInfo var4);

    public InputObject marshalingComplete(Object var1, OutputObject var2) throws ApplicationException, RemarshalException;

    public void endRequest(Broker var1, Object var2, InputObject var3);
}

