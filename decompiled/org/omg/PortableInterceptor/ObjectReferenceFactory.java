/*
 * Decompiled with CFR 0.152.
 */
package org.omg.PortableInterceptor;

import org.omg.CORBA.Object;
import org.omg.CORBA.portable.ValueBase;

public interface ObjectReferenceFactory
extends ValueBase {
    public Object make_object(String var1, byte[] var2);
}

