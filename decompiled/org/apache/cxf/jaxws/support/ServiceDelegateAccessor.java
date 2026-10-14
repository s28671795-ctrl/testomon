/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.jaxws.support;

import java.lang.reflect.Field;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.ws.Service;
import javax.xml.ws.WebServiceException;
import org.apache.cxf.common.logging.LogUtils;
import org.apache.cxf.common.util.ReflectionUtil;
import org.apache.cxf.jaxws.ServiceImpl;

public final class ServiceDelegateAccessor {
    private static final Logger LOG = LogUtils.getL7dLogger(ServiceDelegateAccessor.class);
    private static final String DELEGATE_FIELD_NAME = "delegate";
    private static final String DELEGATE_FIELD_NAME2 = "_delegate";

    private ServiceDelegateAccessor() {
    }

    public static ServiceImpl get(Service service2) {
        ServiceImpl delegate = null;
        try {
            Field delegateField = Service.class.getDeclaredField(DELEGATE_FIELD_NAME);
            ReflectionUtil.setAccessible(delegateField);
            delegate = (ServiceImpl)delegateField.get(service2);
        }
        catch (Exception e) {
            try {
                Field delegateField = Service.class.getDeclaredField(DELEGATE_FIELD_NAME2);
                ReflectionUtil.setAccessible(delegateField);
                delegate = (ServiceImpl)delegateField.get(service2);
            }
            catch (Exception e2) {
                WebServiceException wse = new WebServiceException("Failed to access Field named delegate of Service instance " + service2, e);
                LOG.log(Level.SEVERE, e.getMessage(), e);
                throw wse;
            }
        }
        return delegate;
    }
}

