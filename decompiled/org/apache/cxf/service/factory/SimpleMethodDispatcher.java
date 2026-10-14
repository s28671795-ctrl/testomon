/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.service.factory;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.apache.cxf.endpoint.Endpoint;
import org.apache.cxf.service.invoker.MethodDispatcher;
import org.apache.cxf.service.model.BindingInfo;
import org.apache.cxf.service.model.BindingOperationInfo;
import org.apache.cxf.service.model.OperationInfo;

public class SimpleMethodDispatcher
implements MethodDispatcher {
    private Map<Method, Map<BindingInfo, BindingOperationInfo>> infoMap = new ConcurrentHashMap<Method, Map<BindingInfo, BindingOperationInfo>>(16, 0.75f, 2);
    private Map<OperationInfo, Method> opToMethod = new ConcurrentHashMap<OperationInfo, Method>(16, 0.75f, 2);
    private Map<Method, OperationInfo> methodToOp = new ConcurrentHashMap<Method, OperationInfo>(16, 0.75f, 2);

    @Override
    public void bind(OperationInfo o, Method ... methods) {
        Method primary = methods[0];
        for (Method m4 : methods) {
            this.methodToOp.put(m4, o);
            ConcurrentHashMap biToBop = new ConcurrentHashMap(4, 0.75f, 2);
            this.infoMap.put(m4, biToBop);
        }
        this.opToMethod.put(o, primary);
        if (o.isUnwrappedCapable()) {
            this.opToMethod.put(o.getUnwrappedOperation(), primary);
        }
    }

    @Override
    public BindingOperationInfo getBindingOperation(Method method2, Endpoint endpoint) {
        Map<BindingInfo, BindingOperationInfo> bops = this.infoMap.get(method2);
        if (bops == null) {
            return null;
        }
        BindingOperationInfo bop = bops.get(endpoint.getEndpointInfo().getBinding());
        if (bop == null) {
            OperationInfo o = this.methodToOp.get(method2);
            if (o == null) {
                return null;
            }
            BindingInfo b = endpoint.getEndpointInfo().getBinding();
            for (BindingOperationInfo bop2 : b.getOperations()) {
                if (!bop2.getOperationInfo().equals(o)) continue;
                BindingOperationInfo realBop = this.getRealOperation(o, bop2);
                bops.put(b, realBop);
                return realBop;
            }
        }
        return bop;
    }

    private BindingOperationInfo getRealOperation(OperationInfo o, BindingOperationInfo bop) {
        BindingOperationInfo unwrappedOp = bop.getUnwrappedOperation();
        if (unwrappedOp != null && unwrappedOp.getOperationInfo().equals(o.getUnwrappedOperation())) {
            return unwrappedOp;
        }
        return bop;
    }

    @Override
    public Method getMethod(BindingOperationInfo op) {
        return this.opToMethod.get(op.getOperationInfo());
    }

    public Method getPrimaryMethod(OperationInfo op) {
        return this.opToMethod.get(op);
    }
}

