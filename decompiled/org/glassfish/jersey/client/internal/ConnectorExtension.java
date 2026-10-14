/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.client.internal;

import org.glassfish.jersey.client.ClientRequest;

interface ConnectorExtension<T, E extends Exception> {
    public void invoke(ClientRequest var1, T var2);

    public void postConnectionProcessing(T var1);

    public boolean handleException(ClientRequest var1, T var2, E var3) throws E;
}

