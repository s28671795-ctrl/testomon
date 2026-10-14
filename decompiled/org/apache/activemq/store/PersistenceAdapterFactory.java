/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.store;

import java.io.IOException;
import org.apache.activemq.store.PersistenceAdapter;

public interface PersistenceAdapterFactory {
    public PersistenceAdapter createPersistenceAdapter() throws IOException;
}

