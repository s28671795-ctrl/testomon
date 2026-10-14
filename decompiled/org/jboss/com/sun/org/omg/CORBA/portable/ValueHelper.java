/*
 * Decompiled with CFR 0.152.
 */
package org.jboss.com.sun.org.omg.CORBA.portable;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.BoxedValueHelper;

@Deprecated
public interface ValueHelper
extends BoxedValueHelper {
    public Class<?> get_class();

    public String[] get_truncatable_base_ids();

    public TypeCode get_type();
}

