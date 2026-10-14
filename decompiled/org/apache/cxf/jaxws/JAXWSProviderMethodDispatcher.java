/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.jaxws;

import java.lang.reflect.Method;
import javax.xml.ws.Provider;
import org.apache.cxf.common.util.ReflectionUtil;
import org.apache.cxf.endpoint.Endpoint;
import org.apache.cxf.jaxws.support.JaxWsImplementorInfo;
import org.apache.cxf.service.factory.ServiceConstructionException;
import org.apache.cxf.service.invoker.MethodDispatcher;
import org.apache.cxf.service.model.BindingOperationInfo;
import org.apache.cxf.service.model.OperationInfo;

public class JAXWSProviderMethodDispatcher
implements MethodDispatcher {
    Method invoke;

    public JAXWSProviderMethodDispatcher(JaxWsImplementorInfo implInfo) {
        try {
            this.invoke = ReflectionUtil.getMethod(implInfo.getImplementorClass(), "invoke", implInfo.getProviderParameterType());
            ReflectionUtil.setAccessible(this.invoke);
        }
        catch (Exception e1) {
            try {
                this.invoke = Provider.class.getMethod("invoke", Object.class);
            }
            catch (Exception e) {
                throw new ServiceConstructionException(e);
            }
        }
    }

    @Override
    public BindingOperationInfo getBindingOperation(Method m4, Endpoint endpoint) {
        return null;
    }

    @Override
    public Method getMethod(BindingOperationInfo op) {
        return this.invoke;
    }

    @Override
    public void bind(OperationInfo o, Method ... methods) {
    }
}

