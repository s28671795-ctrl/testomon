/*
 * Decompiled with CFR 0.152.
 */
package org.omg.SendingContext;

import org.omg.CORBA.portable.ObjectImpl;
import org.omg.SendingContext.RunTime;
import org.omg.SendingContext.RunTimeOperations;

public class _RunTimeStub
extends ObjectImpl
implements RunTime {
    private static final long serialVersionUID = 1L;
    private String[] ids = new String[]{"IDL:omg.org/SendingContext/RunTime:1.0"};
    public static final Class _opsClass = RunTimeOperations.class;

    @Override
    public String[] _ids() {
        return this.ids;
    }
}

