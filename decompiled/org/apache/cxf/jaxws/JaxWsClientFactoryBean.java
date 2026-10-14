/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.jaxws;

import org.apache.cxf.binding.soap.SoapBindingConfiguration;
import org.apache.cxf.frontend.ClientFactoryBean;
import org.apache.cxf.jaxws.binding.soap.JaxWsSoapBindingConfiguration;
import org.apache.cxf.jaxws.support.JaxWsImplementorInfo;
import org.apache.cxf.jaxws.support.JaxWsServiceFactoryBean;

public class JaxWsClientFactoryBean
extends ClientFactoryBean {
    public JaxWsClientFactoryBean() {
        super(new JaxWsServiceFactoryBean());
    }

    @Override
    public void setServiceClass(Class<?> serviceClass) {
        super.setServiceClass(serviceClass);
        if (((JaxWsServiceFactoryBean)this.getServiceFactory()).getJaxWsImplementorInfo() == null) {
            JaxWsImplementorInfo implInfo = new JaxWsImplementorInfo(serviceClass);
            ((JaxWsServiceFactoryBean)this.getServiceFactory()).setJaxWsImplementorInfo(implInfo);
        }
    }

    @Override
    protected SoapBindingConfiguration createSoapBindingConfig() {
        JaxWsSoapBindingConfiguration bc = new JaxWsSoapBindingConfiguration((JaxWsServiceFactoryBean)this.getServiceFactory());
        if (this.transportId != null) {
            bc.setTransportURI(this.transportId);
        }
        return bc;
    }

    @Override
    public void setBindingId(String bind) {
        if ("http://schemas.xmlsoap.org/wsdl/soap/http".equals(bind) || "http://schemas.xmlsoap.org/wsdl/soap/http?mtom=true".equals(bind)) {
            super.setBindingId("http://schemas.xmlsoap.org/wsdl/soap/");
        } else if ("http://www.w3.org/2003/05/soap/bindings/HTTP/".equals(bind) || "http://www.w3.org/2003/05/soap/bindings/HTTP/?mtom=true".equals(bind)) {
            super.setBindingId("http://schemas.xmlsoap.org/wsdl/soap12/");
        } else {
            super.setBindingId(bind);
        }
        if ("http://schemas.xmlsoap.org/wsdl/soap/http".equals(bind) || "http://www.w3.org/2003/05/soap/bindings/HTTP/".equals(bind)) {
            this.setBindingConfig(new JaxWsSoapBindingConfiguration((JaxWsServiceFactoryBean)this.getServiceFactory()));
        } else if ("http://schemas.xmlsoap.org/wsdl/soap/http?mtom=true".equals(bind) || "http://www.w3.org/2003/05/soap/bindings/HTTP/?mtom=true".equals(bind)) {
            this.setBindingConfig(new JaxWsSoapBindingConfiguration((JaxWsServiceFactoryBean)this.getServiceFactory()));
            ((JaxWsSoapBindingConfiguration)this.getBindingConfig()).setMtomEnabled(true);
        }
    }
}

