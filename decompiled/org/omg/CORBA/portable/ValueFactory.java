/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CORBA.portable;

import java.io.Serializable;
import org.omg.CORBA_2_3.portable.InputStream;

public interface ValueFactory {
    public Serializable read_value(InputStream var1);
}

