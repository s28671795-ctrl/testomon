/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.network;

import java.net.URI;

public interface ConnectionFilter {
    public boolean connectTo(URI var1);
}

