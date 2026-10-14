/*
 * Decompiled with CFR 0.152.
 */
package org.omg.Messaging;

import org.omg.CORBA.Object;
import org.omg.CORBA.Pollable;
import org.omg.CORBA.portable.ValueBase;
import org.omg.Messaging.ReplyHandler;

public interface Poller
extends ValueBase,
Pollable {
    public Object operation_target();

    public String operation_name();

    public ReplyHandler associated_handler();

    public void associated_handler(ReplyHandler var1);

    public boolean is_from_poller();
}

