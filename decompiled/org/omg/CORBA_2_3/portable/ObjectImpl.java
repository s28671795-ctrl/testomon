/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CORBA_2_3.portable;

import org.omg.CORBA_2_3.portable.Delegate;

public abstract class ObjectImpl
extends org.omg.CORBA.portable.ObjectImpl {
    public String _get_codebase() {
        org.omg.CORBA.portable.Delegate delegate = this._get_delegate();
        if (delegate instanceof Delegate) {
            return ((Delegate)delegate).get_codebase(this);
        }
        return null;
    }
}

