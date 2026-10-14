/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive.sasl;

import org.ldaptive.BindResponse;
import org.ldaptive.transport.TransportConnection;

public interface SaslClient<T> {
    public BindResponse bind(TransportConnection var1, T var2) throws Exception;
}

