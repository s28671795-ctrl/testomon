/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.filter;

import org.apache.activemq.filter.DestinationMapEntry;

public class DefaultDestinationMapEntry
extends DestinationMapEntry {
    private DestinationMapEntry value;

    public DestinationMapEntry getValue() {
        return this.value;
    }

    public void setValue(DestinationMapEntry value) {
        this.value = value;
    }
}

