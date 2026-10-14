/*
 * Decompiled with CFR 0.152.
 */
package org.simpleframework.xml.strategy;

import org.simpleframework.xml.strategy.Contract;
import org.simpleframework.xml.strategy.WriteGraph;
import org.simpleframework.xml.util.WeakCache;

class WriteState
extends WeakCache<WriteGraph> {
    private Contract contract;

    public WriteState(Contract contract) {
        this.contract = contract;
    }

    public WriteGraph find(Object map) {
        WriteGraph write = (WriteGraph)this.fetch(map);
        if (write == null) {
            write = new WriteGraph(this.contract);
            this.cache(map, write);
        }
        return write;
    }
}

