/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.jaxws;

import java.lang.reflect.Field;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebServiceException;
import javax.xml.ws.WebServiceFeature;
import javax.xml.ws.spi.ServiceDelegate;
import org.apache.cxf.Bus;
import org.apache.cxf.common.util.ReflectionUtil;
import org.apache.cxf.jaxws.ServiceImpl;

public abstract class CXFService
extends Service {
    ServiceImpl impl = this.findDelegate();

    protected CXFService(URL wsdlURL, QName serviceName) {
        super(wsdlURL, serviceName);
        this.impl.initialize(null, wsdlURL, new WebServiceFeature[0]);
    }

    protected CXFService(Bus b, URL wsdlURL, QName serviceName) {
        super(wsdlURL, serviceName);
        this.impl.initialize(b, wsdlURL, new WebServiceFeature[0]);
    }

    protected CXFService(URL wsdlURL, QName serviceName, WebServiceFeature ... f) {
        super(wsdlURL, serviceName);
        this.impl.initialize(null, wsdlURL, f);
    }

    protected CXFService(Bus b, URL wsdlURL, QName serviceName, WebServiceFeature ... f) {
        super(wsdlURL, serviceName);
        this.impl.initialize(b, wsdlURL, f);
    }

    private ServiceImpl findDelegate() {
        for (Field f : ReflectionUtil.getDeclaredFields(Service.class)) {
            if (!ServiceDelegate.class.equals(f.getType())) continue;
            ServiceDelegate del = ReflectionUtil.accessDeclaredField(f, this, ServiceDelegate.class);
            if (del instanceof ServiceImpl) {
                return (ServiceImpl)del;
            }
            throw new WebServiceException("Delegate of class " + del.getClass() + " is not a CXF delegate.   Check the classpath to make sure CXF is loaded first.");
        }
        throw new WebServiceException("Could not find CXF service delegate");
    }
}

