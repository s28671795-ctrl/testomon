/*
 * Decompiled with CFR 0.152.
 */
package org.jboss.com.sun.corba.se.impl.encoding;

import org.jboss.com.sun.corba.se.impl.corba.TypeCodeImpl;
import org.jboss.com.sun.corba.se.impl.encoding.MarshalInputStream;
import org.omg.CORBA_2_3.portable.InputStream;

public interface TypeCodeReader
extends MarshalInputStream {
    public void addTypeCodeAtPosition(TypeCodeImpl var1, int var2);

    public TypeCodeImpl getTypeCodeAtPosition(int var1);

    public void setEnclosingInputStream(InputStream var1);

    public TypeCodeReader getTopLevelStream();

    public int getTopLevelPosition();

    @Override
    public int getPosition();

    public void printTypeMap();
}

