/*
 * Decompiled with CFR 0.152.
 */
package org.jboss.com.sun.corba.se.pept.transport;

import java.util.Iterator;
import org.jboss.com.sun.corba.se.pept.transport.ContactInfo;
import org.jboss.com.sun.corba.se.pept.transport.ContactInfoList;

public interface ContactInfoListIterator
extends Iterator<ContactInfo> {
    public ContactInfoList getContactInfoList();

    public void reportSuccess(ContactInfo var1);

    public boolean reportException(ContactInfo var1, RuntimeException var2);

    public RuntimeException getFailureException();
}

