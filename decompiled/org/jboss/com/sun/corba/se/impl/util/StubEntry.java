/*
 * Decompiled with CFR 0.152.
 */
package org.jboss.com.sun.corba.se.impl.util;

import org.omg.CORBA.Object;

class StubEntry {
    Object stub;
    boolean mostDerived;

    StubEntry(Object stub, boolean mostDerived) {
        this.stub = stub;
        this.mostDerived = mostDerived;
    }
}

