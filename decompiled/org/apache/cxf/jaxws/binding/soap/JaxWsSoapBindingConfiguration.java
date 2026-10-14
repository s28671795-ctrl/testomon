/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.jaxws.binding.soap;

import java.lang.reflect.Method;
import javax.jws.soap.SOAPBinding;
import org.apache.cxf.binding.soap.SoapBindingConfiguration;
import org.apache.cxf.jaxws.support.JaxWsServiceFactoryBean;
import org.apache.cxf.service.model.OperationInfo;

public class JaxWsSoapBindingConfiguration
extends SoapBindingConfiguration {
    JaxWsServiceFactoryBean serviceFactory;

    public JaxWsSoapBindingConfiguration(JaxWsServiceFactoryBean sifb) {
        this.serviceFactory = sifb;
    }

    public void setJaxWsServiceFactoryBean(JaxWsServiceFactoryBean b) {
        this.serviceFactory = b;
    }

    @Override
    public String getStyle(OperationInfo op) {
        Method m4 = op.getProperty("operation.method", Method.class);
        if (m4 != null) {
            return this.serviceFactory.isRPC(m4) ? "rpc" : "document";
        }
        return this.getStyle();
    }

    @Override
    public String getStyle() {
        SOAPBinding sb = this.getServiceClass().getAnnotation(SOAPBinding.class);
        if (sb != null) {
            if (sb.style().equals((Object)SOAPBinding.Style.DOCUMENT)) {
                return "document";
            }
            if (sb.style().equals((Object)SOAPBinding.Style.RPC)) {
                return "rpc";
            }
        }
        return super.getStyle();
    }

    Class<?> getServiceClass() {
        return this.getJaxWsServiceFactory().getJaxWsImplementorInfo().getEndpointClass();
    }

    private JaxWsServiceFactoryBean getJaxWsServiceFactory() {
        return this.serviceFactory;
    }

    @Override
    public String getUse() {
        SOAPBinding sb = this.getServiceClass().getAnnotation(SOAPBinding.class);
        if (sb != null) {
            if (sb.use().equals((Object)SOAPBinding.Use.LITERAL)) {
                return "literal";
            }
            if (sb.use().equals((Object)SOAPBinding.Use.ENCODED)) {
                return "encoded";
            }
        }
        return super.getUse();
    }
}

