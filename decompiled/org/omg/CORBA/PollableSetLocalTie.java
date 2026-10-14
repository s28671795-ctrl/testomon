/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CORBA;

import org.omg.CORBA.DIIPollable;
import org.omg.CORBA.Pollable;
import org.omg.CORBA.PollableSetOperations;
import org.omg.CORBA.PollableSetPackage.NoPossiblePollable;
import org.omg.CORBA.PollableSetPackage.UnknownPollable;
import org.omg.CORBA._PollableSetLocalBase;

public class PollableSetLocalTie
extends _PollableSetLocalBase {
    private static final long serialVersionUID = 1L;
    private PollableSetOperations _delegate;

    public PollableSetLocalTie(PollableSetOperations delegate) {
        this._delegate = delegate;
    }

    public PollableSetOperations _delegate() {
        return this._delegate;
    }

    public void _delegate(PollableSetOperations delegate) {
        this._delegate = delegate;
    }

    @Override
    public void add_pollable(Pollable potential) {
        this._delegate.add_pollable(potential);
    }

    @Override
    public Pollable get_ready_pollable(int timeout2) throws NoPossiblePollable {
        return this._delegate.get_ready_pollable(timeout2);
    }

    @Override
    public void remove(Pollable potential) throws UnknownPollable {
        this._delegate.remove(potential);
    }

    @Override
    public DIIPollable create_dii_pollable() {
        return this._delegate.create_dii_pollable();
    }

    @Override
    public short number_left() {
        return this._delegate.number_left();
    }
}

