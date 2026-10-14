/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CORBA;

import org.omg.CORBA.PollableSet;
import org.omg.CORBA.portable.ValueBase;

public interface Pollable
extends ValueBase {
    public boolean is_ready(int var1);

    public PollableSet create_pollable_set();
}

