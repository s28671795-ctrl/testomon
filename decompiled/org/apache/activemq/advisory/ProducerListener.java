/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.advisory;

import org.apache.activemq.advisory.ProducerEvent;

public interface ProducerListener {
    public void onProducerEvent(ProducerEvent var1);
}

