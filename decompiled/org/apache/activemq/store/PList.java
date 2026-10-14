/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.store;

import java.io.IOException;
import java.util.Iterator;
import org.apache.activemq.store.PListEntry;
import org.apache.activemq.util.ByteSequence;

public interface PList {
    public String getName();

    public void destroy() throws IOException;

    public Object addFirst(String var1, ByteSequence var2) throws IOException;

    public Object addLast(String var1, ByteSequence var2) throws IOException;

    public boolean remove(Object var1) throws IOException;

    public boolean isEmpty();

    public PListIterator iterator() throws IOException;

    public long size();

    public long messageSize();

    public static interface PListIterator
    extends Iterator<PListEntry> {
        public void release();
    }
}

