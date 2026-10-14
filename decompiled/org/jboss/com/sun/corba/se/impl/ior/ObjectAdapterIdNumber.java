/*
 * Decompiled with CFR 0.152.
 */
package org.jboss.com.sun.corba.se.impl.ior;

import org.jboss.com.sun.corba.se.impl.ior.ObjectAdapterIdArray;

public class ObjectAdapterIdNumber
extends ObjectAdapterIdArray {
    private int poaid;

    public ObjectAdapterIdNumber(int poaid) {
        super("OldRootPOA", Integer.toString(poaid));
        this.poaid = poaid;
    }

    public int getOldPOAId() {
        return this.poaid;
    }
}

