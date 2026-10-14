/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.transport;

import java.net.URI;
import org.apache.activemq.transport.Transport;

public interface CompositeTransport
extends Transport {
    public void add(boolean var1, URI[] var2);

    public void remove(boolean var1, URI[] var2);
}

