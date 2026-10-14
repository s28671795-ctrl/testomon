/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.broker;

import org.apache.activemq.broker.MutableBrokerFilter;

public class InsertableMutableBrokerFilter
extends MutableBrokerFilter {
    MutableBrokerFilter parent;

    public InsertableMutableBrokerFilter(MutableBrokerFilter parent) {
        super(parent.getNext());
        this.parent = parent;
        parent.setNext(this);
    }

    public void remove() {
        this.parent.setNext(this.getNext());
    }
}

