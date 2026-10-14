/*
 * Decompiled with CFR 0.152.
 */
package org.omg.SendingContext;

import org.omg.CORBA.ORB;
import org.omg.CORBA.Object;
import org.omg.PortableServer.POA;
import org.omg.SendingContext.RunTime;
import org.omg.SendingContext.RunTimeHelper;
import org.omg.SendingContext.RunTimeOperations;
import org.omg.SendingContext.RunTimePOA;

public class RunTimePOATie
extends RunTimePOA {
    private RunTimeOperations _delegate;
    private POA _poa;

    public RunTimePOATie(RunTimeOperations delegate) {
        this._delegate = delegate;
    }

    public RunTimePOATie(RunTimeOperations delegate, POA poa) {
        this._delegate = delegate;
        this._poa = poa;
    }

    @Override
    public RunTime _this() {
        Object __o = this._this_object();
        RunTime __r = RunTimeHelper.narrow(__o);
        return __r;
    }

    @Override
    public RunTime _this(ORB orb) {
        Object __o = this._this_object(orb);
        RunTime __r = RunTimeHelper.narrow(__o);
        return __r;
    }

    public RunTimeOperations _delegate() {
        return this._delegate;
    }

    public void _delegate(RunTimeOperations delegate) {
        this._delegate = delegate;
    }

    @Override
    public POA _default_POA() {
        if (this._poa != null) {
            return this._poa;
        }
        return super._default_POA();
    }
}

