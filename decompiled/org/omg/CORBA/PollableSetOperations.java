/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CORBA;

import org.omg.CORBA.DIIPollable;
import org.omg.CORBA.Pollable;
import org.omg.CORBA.PollableSetPackage.NoPossiblePollable;
import org.omg.CORBA.PollableSetPackage.UnknownPollable;

public interface PollableSetOperations {
    public DIIPollable create_dii_pollable();

    public void add_pollable(Pollable var1);

    public Pollable get_ready_pollable(int var1) throws NoPossiblePollable;

    public void remove(Pollable var1) throws UnknownPollable;

    public short number_left();
}

