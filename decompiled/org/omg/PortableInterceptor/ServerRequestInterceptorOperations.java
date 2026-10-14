/*
 * Decompiled with CFR 0.152.
 */
package org.omg.PortableInterceptor;

import org.omg.PortableInterceptor.ForwardRequest;
import org.omg.PortableInterceptor.InterceptorOperations;
import org.omg.PortableInterceptor.ServerRequestInfo;

public interface ServerRequestInterceptorOperations
extends InterceptorOperations {
    public void receive_request_service_contexts(ServerRequestInfo var1) throws ForwardRequest;

    public void receive_request(ServerRequestInfo var1) throws ForwardRequest;

    public void send_reply(ServerRequestInfo var1);

    public void send_exception(ServerRequestInfo var1) throws ForwardRequest;

    public void send_other(ServerRequestInfo var1) throws ForwardRequest;
}

