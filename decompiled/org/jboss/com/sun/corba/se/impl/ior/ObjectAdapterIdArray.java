/*
 * Decompiled with CFR 0.152.
 */
package org.jboss.com.sun.corba.se.impl.ior;

import java.util.Arrays;
import java.util.Iterator;
import org.jboss.com.sun.corba.se.impl.ior.ObjectAdapterIdBase;

public class ObjectAdapterIdArray
extends ObjectAdapterIdBase {
    private final String[] objectAdapterId;

    public ObjectAdapterIdArray(String[] objectAdapterId) {
        this.objectAdapterId = objectAdapterId;
    }

    public ObjectAdapterIdArray(String name1, String name2) {
        this.objectAdapterId = new String[2];
        this.objectAdapterId[0] = name1;
        this.objectAdapterId[1] = name2;
    }

    @Override
    public int getNumLevels() {
        return this.objectAdapterId.length;
    }

    @Override
    public Iterator<String> iterator() {
        return Arrays.asList(this.objectAdapterId).iterator();
    }

    @Override
    public String[] getAdapterName() {
        return (String[])this.objectAdapterId.clone();
    }
}

