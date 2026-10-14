/*
 * Decompiled with CFR 0.152.
 */
package org.jboss.com.sun.corba.se.impl.orbutil;

import java.io.IOException;
import java.io.NotActiveException;
import java.io.ObjectInputStream;
import java.util.Hashtable;
import org.jboss.com.sun.corba.se.impl.io.IIOPInputStream;
import org.jboss.com.sun.corba.se.impl.orbutil.LegacyHookGetFields;

public class IIOPInputStream_1_3_1
extends IIOPInputStream {
    @Override
    public ObjectInputStream.GetField readFields() throws IOException, ClassNotFoundException, NotActiveException {
        Hashtable fields = (Hashtable)this.readObject();
        return new LegacyHookGetFields(fields);
    }
}

