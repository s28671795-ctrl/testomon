/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.jaxws.support;

import java.lang.reflect.Method;
import javax.activation.DataSource;
import javax.xml.namespace.QName;
import javax.xml.soap.SOAPMessage;
import javax.xml.transform.Source;
import org.apache.cxf.jaxws.support.JaxWsImplementorInfo;
import org.apache.cxf.jaxws.support.JaxWsServiceConfiguration;
import org.apache.cxf.jaxws.support.JaxWsServiceFactoryBean;
import org.apache.cxf.wsdl.service.factory.ReflectionServiceFactoryBean;

public class WebServiceProviderConfiguration
extends JaxWsServiceConfiguration {
    private JaxWsImplementorInfo implInfo;

    @Override
    public Boolean isOperation(Method method2) {
        return "invoke".equals(method2.getName()) && method2.getParameterTypes().length == 1 && (Source.class.isAssignableFrom(method2.getParameterTypes()[0]) || SOAPMessage.class.isAssignableFrom(method2.getParameterTypes()[0]) || DataSource.class.isAssignableFrom(method2.getParameterTypes()[0]));
    }

    @Override
    public void setServiceFactory(ReflectionServiceFactoryBean serviceFactory) {
        super.setServiceFactory(serviceFactory);
        this.implInfo = ((JaxWsServiceFactoryBean)serviceFactory).getJaxWsImplementorInfo();
    }

    @Override
    public String getServiceName() {
        QName service2 = this.implInfo.getServiceName();
        return service2.getLocalPart();
    }

    @Override
    public String getServiceNamespace() {
        QName service2 = this.implInfo.getServiceName();
        return service2.getNamespaceURI();
    }

    @Override
    public QName getEndpointName() {
        return this.implInfo.getEndpointName();
    }

    @Override
    public String getWsdlURL() {
        String wsdlLocation = this.implInfo.getWsdlLocation();
        if (wsdlLocation != null && wsdlLocation.length() > 0) {
            return wsdlLocation;
        }
        return null;
    }

    @Override
    public Boolean isWrapped(Method m4) {
        return Boolean.FALSE;
    }
}

