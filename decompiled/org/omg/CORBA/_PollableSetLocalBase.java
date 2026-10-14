/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CORBA;

import org.omg.CORBA.LocalObject;
import org.omg.CORBA.PollableSet;

public abstract class _PollableSetLocalBase
extends LocalObject
implements PollableSet {
    private static final long serialVersionUID = 1L;
    private String[] _type_ids = new String[]{"IDL:omg.org/CORBA/PollableSet:1.0"};

    @Override
    public String[] _ids() {
        return (String[])this._type_ids.clone();
    }
}

