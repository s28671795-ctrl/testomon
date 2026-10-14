/*
 * Decompiled with CFR 0.152.
 */
package org.jboss.com.sun.corba.se.spi.transport;

import java.util.List;
import org.jboss.com.sun.corba.se.pept.transport.ContactInfo;

public interface IIOPPrimaryToContactInfo {
    public void reset(ContactInfo var1);

    public boolean hasNext(ContactInfo var1, ContactInfo var2, List<ContactInfo> var3);

    public ContactInfo next(ContactInfo var1, ContactInfo var2, List<ContactInfo> var3);
}

