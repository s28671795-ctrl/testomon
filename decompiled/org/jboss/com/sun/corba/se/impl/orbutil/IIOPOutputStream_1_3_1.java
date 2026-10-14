/*
 * Decompiled with CFR 0.152.
 */
package org.jboss.com.sun.corba.se.impl.orbutil;

import java.io.IOException;
import java.io.ObjectOutputStream;
import org.jboss.com.sun.corba.se.impl.io.IIOPOutputStream;
import org.jboss.com.sun.corba.se.impl.orbutil.LegacyHookPutFields;

public class IIOPOutputStream_1_3_1
extends IIOPOutputStream {
    private ObjectOutputStream.PutField putFields_1_3_1;

    @Override
    public ObjectOutputStream.PutField putFields() throws IOException {
        this.putFields_1_3_1 = new LegacyHookPutFields();
        return this.putFields_1_3_1;
    }

    @Override
    public void writeFields() throws IOException {
        this.putFields_1_3_1.write(this);
    }
}

