/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.transport;

import java.io.IOException;
import org.apache.activemq.transport.Transport;
import org.apache.activemq.transport.TransportFilter;

public class ThreadNameFilter
extends TransportFilter {
    public ThreadNameFilter(Transport next) {
        super(next);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void oneway(Object command) throws IOException {
        String address;
        String string = address = this.next != null ? this.next.getRemoteAddress() : null;
        if (address != null) {
            String name = Thread.currentThread().getName();
            try {
                String sendname = name + " - SendTo:" + address;
                Thread.currentThread().setName(sendname);
                super.oneway(command);
            }
            finally {
                Thread.currentThread().setName(name);
            }
        } else {
            super.oneway(command);
        }
    }
}

