/*
 * Decompiled with CFR 0.152.
 */
package org.omg.PortableInterceptor;

import org.omg.CORBA.Any;
import org.omg.PortableInterceptor.InvalidSlot;

public interface CurrentOperations
extends org.omg.CORBA.CurrentOperations {
    public Any get_slot(int var1) throws InvalidSlot;

    public void set_slot(int var1, Any var2) throws InvalidSlot;
}

