/*
 * Decompiled with CFR 0.152.
 */
package org.jboss.com.sun.corba.se.impl.corba;

import org.omg.CORBA_2_3.portable.ObjectImpl;

public class CORBAObjectImpl
extends ObjectImpl {
    @Override
    public String[] _ids() {
        String[] typeids = new String[]{"IDL:omg.org/CORBA/Object:1.0"};
        return typeids;
    }
}

