/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.advisory;

import org.apache.activemq.advisory.DestinationEvent;

public interface DestinationListener {
    public void onDestinationEvent(DestinationEvent var1);
}

