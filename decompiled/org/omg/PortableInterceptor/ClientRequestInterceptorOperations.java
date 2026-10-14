/*
 * Decompiled with CFR 0.152.
 */
package org.omg.PortableInterceptor;

import org.omg.PortableInterceptor.ClientRequestInfo;
import org.omg.PortableInterceptor.ForwardRequest;
import org.omg.PortableInterceptor.InterceptorOperations;

public interface ClientRequestInterceptorOperations
extends InterceptorOperations {
    public void send_request(ClientRequestInfo var1) throws ForwardRequest;

    public void send_poll(ClientRequestInfo var1);

    public void receive_reply(ClientRequestInfo var1);

    public void receive_exception(ClientRequestInfo var1) throws ForwardRequest;

    public void receive_other(ClientRequestInfo var1) throws ForwardRequest;
}

