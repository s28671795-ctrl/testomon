/*
 * Decompiled with CFR 0.152.
 */
package org.simpleframework.xml.strategy;

import org.simpleframework.xml.strategy.Contract;
import org.simpleframework.xml.strategy.Loader;
import org.simpleframework.xml.strategy.ReadGraph;
import org.simpleframework.xml.util.WeakCache;

class ReadState
extends WeakCache<ReadGraph> {
    private final Contract contract;
    private final Loader loader = new Loader();

    public ReadState(Contract contract) {
        this.contract = contract;
    }

    public ReadGraph find(Object map) throws Exception {
        ReadGraph read = (ReadGraph)this.fetch(map);
        if (read != null) {
            return read;
        }
        return this.create(map);
    }

    private ReadGraph create(Object map) throws Exception {
        ReadGraph read = (ReadGraph)this.fetch(map);
        if (read == null) {
            read = new ReadGraph(this.contract, this.loader);
            this.cache(map, read);
        }
        return read;
    }
}

