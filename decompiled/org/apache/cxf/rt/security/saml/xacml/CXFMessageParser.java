/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.rt.security.saml.xacml;

import javax.xml.namespace.QName;
import org.apache.cxf.message.Message;
import org.apache.cxf.rt.security.saml.claims.SAMLSecurityContext;
import org.apache.cxf.security.SecurityContext;
import org.apache.wss4j.common.ext.WSSecurityException;
import org.apache.wss4j.common.saml.SamlAssertionWrapper;
import org.w3c.dom.Element;

public class CXFMessageParser {
    private Message message;

    public CXFMessageParser(Message message) {
        this.message = message;
    }

    public boolean isSOAPService() {
        return this.getWSDLOperation() != null;
    }

    public QName getWSDLOperation() {
        if (this.message != null && this.message.get("javax.xml.ws.wsdl.operation") != null) {
            return (QName)this.message.get("javax.xml.ws.wsdl.operation");
        }
        return null;
    }

    public QName getWSDLService() {
        if (this.message != null && this.message.get("javax.xml.ws.wsdl.service") != null) {
            return (QName)this.message.get("javax.xml.ws.wsdl.service");
        }
        return null;
    }

    public String getResourceURI(boolean fullRequestURL) {
        String property;
        String string = property = fullRequestURL ? "org.apache.cxf.request.url" : "org.apache.cxf.request.uri";
        if (this.message != null && this.message.get(property) != null) {
            return (String)this.message.get(property);
        }
        return null;
    }

    public String getAction(String defaultSOAPAction) {
        String actionToUse = defaultSOAPAction;
        if (this.message.get("javax.xml.ws.wsdl.operation") == null && this.message.get("org.apache.cxf.request.method") != null) {
            actionToUse = (String)this.message.get("org.apache.cxf.request.method");
        }
        return actionToUse;
    }

    public String getIssuer() throws WSSecurityException {
        Element assertionElement;
        SecurityContext sc = this.message.get(SecurityContext.class);
        if (sc instanceof SAMLSecurityContext && (assertionElement = ((SAMLSecurityContext)sc).getAssertionElement()) != null) {
            SamlAssertionWrapper wrapper = new SamlAssertionWrapper(assertionElement);
            return wrapper.getIssuerString();
        }
        return null;
    }
}

