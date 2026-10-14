/*
 * Decompiled with CFR 0.152.
 */
package org.omg.PortableInterceptor;

import org.omg.CORBA.portable.ValueBase;
import org.omg.PortableInterceptor.ObjectReferenceFactory;

public interface ObjectReferenceTemplate
extends ValueBase,
ObjectReferenceFactory {
    public String server_id();

    public String orb_id();

    public String[] adapter_name();
}

