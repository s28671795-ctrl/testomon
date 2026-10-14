/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CORBA;

import org.omg.CORBA.NO_IMPLEMENT;
import org.omg.CORBA.ServerRequest;
import org.omg.CORBA.portable.ObjectImpl;

public abstract class DynamicImplementation
extends ObjectImpl {
    public void invoke(ServerRequest request) {
        throw new NO_IMPLEMENT();
    }
}

