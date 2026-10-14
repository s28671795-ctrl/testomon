/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.wsdl.service.factory;

import java.lang.reflect.Method;
import org.apache.cxf.common.util.StringUtils;
import org.apache.cxf.service.model.OperationInfo;
import org.apache.cxf.wsdl.service.factory.AbstractServiceConfiguration;

public class MethodNameSoapActionServiceConfiguration
extends AbstractServiceConfiguration {
    @Override
    public String getAction(OperationInfo op, Method method2) {
        String action = op.getName().getLocalPart();
        if (StringUtils.isEmpty(action)) {
            action = method2.getName();
        }
        return action;
    }
}

