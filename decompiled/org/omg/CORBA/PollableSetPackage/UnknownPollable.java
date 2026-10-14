/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CORBA.PollableSetPackage;

import org.omg.CORBA.PollableSetPackage.UnknownPollableHelper;
import org.omg.CORBA.UserException;

public final class UnknownPollable
extends UserException {
    private static final long serialVersionUID = 1L;

    public UnknownPollable() {
        super(UnknownPollableHelper.id());
    }

    public UnknownPollable(String value) {
        super(value);
    }
}

