/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.service.invoker;

import java.lang.reflect.Method;
import org.apache.cxf.endpoint.Endpoint;
import org.apache.cxf.service.model.BindingOperationInfo;
import org.apache.cxf.service.model.OperationInfo;

public interface MethodDispatcher {
    public Method getMethod(BindingOperationInfo var1);

    public BindingOperationInfo getBindingOperation(Method var1, Endpoint var2);

    public void bind(OperationInfo var1, Method ... var2);
}

