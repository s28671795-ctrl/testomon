/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CORBA.PollableSetPackage;

import org.omg.CORBA.PollableSetPackage.NoPossiblePollableHelper;
import org.omg.CORBA.UserException;

public final class NoPossiblePollable
extends UserException {
    private static final long serialVersionUID = 1L;

    public NoPossiblePollable() {
        super(NoPossiblePollableHelper.id());
    }

    public NoPossiblePollable(String value) {
        super(value);
    }
}

