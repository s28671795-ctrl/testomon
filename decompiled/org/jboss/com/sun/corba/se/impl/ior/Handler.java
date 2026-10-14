/*
 * Decompiled with CFR 0.152.
 */
package org.jboss.com.sun.corba.se.impl.ior;

import org.jboss.com.sun.corba.se.spi.ior.ObjectKeyTemplate;
import org.omg.CORBA.OctetSeqHolder;
import org.omg.CORBA_2_3.portable.InputStream;

interface Handler {
    public ObjectKeyTemplate handle(int var1, int var2, InputStream var3, OctetSeqHolder var4);
}

