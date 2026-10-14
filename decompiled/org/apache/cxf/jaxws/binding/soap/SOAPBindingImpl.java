/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.jaxws.binding.soap;

import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.logging.Logger;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPFactory;
import javax.xml.ws.WebServiceException;
import javax.xml.ws.soap.SOAPBinding;
import org.apache.cxf.binding.soap.Soap11;
import org.apache.cxf.binding.soap.Soap12;
import org.apache.cxf.binding.soap.model.SoapBindingInfo;
import org.apache.cxf.binding.soap.saaj.SAAJFactoryResolver;
import org.apache.cxf.common.logging.LogUtils;
import org.apache.cxf.jaxws.binding.AbstractBindingImpl;
import org.apache.cxf.jaxws.support.JaxWsEndpointImpl;
import org.apache.cxf.service.model.BindingInfo;

public class SOAPBindingImpl
extends AbstractBindingImpl
implements SOAPBinding {
    private static final Logger LOG = LogUtils.getL7dLogger(SOAPBindingImpl.class);
    private static final ResourceBundle BUNDLE = LOG.getResourceBundle();
    private BindingInfo soapBinding;
    private Set<String> roles;

    public SOAPBindingImpl(BindingInfo sb, JaxWsEndpointImpl endpoint) {
        super(endpoint);
        this.soapBinding = sb;
        this.addRequiredRoles();
    }

    private void addRequiredRoles() {
        if (this.roles == null) {
            this.roles = new HashSet<String>();
        }
        if (this.soapBinding instanceof SoapBindingInfo) {
            SoapBindingInfo bindingInfo = (SoapBindingInfo)this.soapBinding;
            if (bindingInfo.getSoapVersion() instanceof Soap11) {
                this.roles.add(bindingInfo.getSoapVersion().getNextRole());
            } else if (bindingInfo.getSoapVersion() instanceof Soap12) {
                this.roles.add(bindingInfo.getSoapVersion().getNextRole());
                this.roles.add(bindingInfo.getSoapVersion().getUltimateReceiverRole());
            }
        }
    }

    @Override
    public Set<String> getRoles() {
        return this.roles;
    }

    @Override
    public void setRoles(Set<String> set) {
        if (set != null && (set.contains(Soap11.getInstance().getNoneRole()) || set.contains(Soap12.getInstance().getNoneRole()))) {
            throw new WebServiceException(BUNDLE.getString("NONE_ROLE_ERR"));
        }
        this.roles = set;
        this.addRequiredRoles();
    }

    @Override
    public boolean isMTOMEnabled() {
        return Boolean.TRUE.equals(this.soapBinding.getProperty("mtom-enabled"));
    }

    @Override
    public void setMTOMEnabled(boolean flag) {
        this.soapBinding.setProperty("mtom-enabled", flag);
    }

    @Override
    public MessageFactory getMessageFactory() {
        if (this.soapBinding instanceof SoapBindingInfo) {
            SoapBindingInfo bindingInfo = (SoapBindingInfo)this.soapBinding;
            try {
                return SAAJFactoryResolver.createMessageFactory(bindingInfo.getSoapVersion());
            }
            catch (SOAPException e) {
                throw new WebServiceException(BUNDLE.getString("SAAJ_FACTORY_ERR"), e);
            }
        }
        return null;
    }

    @Override
    public SOAPFactory getSOAPFactory() {
        if (this.soapBinding instanceof SoapBindingInfo) {
            SoapBindingInfo bindingInfo = (SoapBindingInfo)this.soapBinding;
            try {
                return SAAJFactoryResolver.createSOAPFactory(bindingInfo.getSoapVersion());
            }
            catch (SOAPException e) {
                throw new WebServiceException(BUNDLE.getString("SAAJ_FACTORY_ERR"), e);
            }
        }
        return null;
    }

    public static boolean isSoapBinding(String bindingID) {
        return bindingID.equals("http://schemas.xmlsoap.org/soap/") || bindingID.equals("http://schemas.xmlsoap.org/wsdl/soap12/") || bindingID.equals("http://schemas.xmlsoap.org/wsdl/soap/http") || bindingID.equals("http://schemas.xmlsoap.org/wsdl/soap/http?mtom=true") || bindingID.equals("http://www.w3.org/2003/05/soap/bindings/HTTP/") || bindingID.equals("http://www.w3.org/2003/05/soap/bindings/HTTP/?mtom=true");
    }

    @Override
    public String getBindingID() {
        SoapBindingInfo bindingInfo;
        if (this.soapBinding instanceof SoapBindingInfo && (bindingInfo = (SoapBindingInfo)this.soapBinding).getSoapVersion() instanceof Soap12) {
            return "http://www.w3.org/2003/05/soap/bindings/HTTP/";
        }
        return "http://schemas.xmlsoap.org/wsdl/soap/http";
    }
}

