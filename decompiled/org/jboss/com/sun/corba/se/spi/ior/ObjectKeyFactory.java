/*
 * Decompiled with CFR 0.152.
 */
package org.jboss.com.sun.corba.se.spi.ior;

import org.jboss.com.sun.corba.se.spi.ior.ObjectKey;
import org.jboss.com.sun.corba.se.spi.ior.ObjectKeyTemplate;
import org.omg.CORBA_2_3.portable.InputStream;

public interface ObjectKeyFactory {
    public ObjectKey create(byte[] var1);

    public ObjectKeyTemplate createTemplate(InputStream var1);
}

