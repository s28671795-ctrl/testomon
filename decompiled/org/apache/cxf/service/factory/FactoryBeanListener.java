/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.service.factory;

import org.apache.cxf.service.factory.AbstractServiceFactoryBean;

public interface FactoryBeanListener {
    public void handleEvent(Event var1, AbstractServiceFactoryBean var2, Object ... var3);

    public static enum Event {
        START_CREATE,
        END_CREATE,
        CREATE_FROM_WSDL,
        CREATE_FROM_CLASS,
        WSDL_LOADED,
        SERVICE_SET,
        INTERFACE_OPERATION_BOUND,
        OPERATIONINFO_IN_MESSAGE_SET,
        OPERATIONINFO_OUT_MESSAGE_SET,
        OPERATIONINFO_FAULT,
        INTERFACE_CREATED,
        DATABINDING_INITIALIZED,
        ENDPOINT_CREATED,
        PRE_SERVER_CREATE,
        SERVER_CREATED,
        BINDING_OPERATION_CREATED,
        BINDING_CREATED,
        PRE_CLIENT_CREATE,
        CLIENT_CREATED,
        ENDPOINT_SELECTED,
        ENDPOINTINFO_CREATED,
        PROXY_CREATED;

    }
}

