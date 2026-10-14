/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.util;

import java.util.HashMap;
import org.apache.activemq.util.Trace;

class Tracker
extends HashMap<Long, Trace> {
    Tracker() {
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void track() {
        Trace current = new Trace();
        Tracker tracker = this;
        synchronized (tracker) {
            Trace exist = (Trace)this.get(current.id);
            if (exist != null) {
                ++exist.count;
            } else {
                this.put(current.id, current);
            }
        }
    }
}

