/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CORBA_3_0;

import org.omg.CORBA.NO_IMPLEMENT;
import org.omg.CORBA.SystemException;

public abstract class ORB
extends org.omg.CORBA_2_5.ORB {
    @Override
    public String exception_to_string(SystemException exc) {
        throw new NO_IMPLEMENT();
    }
}

