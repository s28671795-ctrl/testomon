/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.jaxws.handler.soap;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.namespace.QName;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPHeader;
import javax.xml.soap.SOAPHeaderElement;
import javax.xml.soap.SOAPMessage;
import javax.xml.ws.WebServiceException;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPMessageContext;
import org.apache.cxf.binding.soap.SoapMessage;
import org.apache.cxf.binding.soap.saaj.SAAJInInterceptor;
import org.apache.cxf.common.jaxb.JAXBUtils;
import org.apache.cxf.helpers.CastUtils;
import org.apache.cxf.jaxws.context.WrappedMessageContext;
import org.apache.cxf.message.Message;

public class SOAPMessageContextImpl
extends WrappedMessageContext
implements SOAPMessageContext {
    private static final SAAJInInterceptor SAAJ_IN = new SAAJInInterceptor();
    private Set<String> roles = new HashSet<String>();

    public SOAPMessageContextImpl(Message m4) {
        super(m4, MessageContext.Scope.HANDLER);
        this.roles.add(this.getWrappedSoapMessage().getVersion().getNextRole());
    }

    @Override
    public void setMessage(SOAPMessage message) {
        if (this.getWrappedMessage().getContent(Object.class) instanceof SOAPMessage) {
            this.getWrappedMessage().setContent(Object.class, message);
        } else {
            this.getWrappedMessage().setContent(SOAPMessage.class, message);
        }
    }

    @Override
    public SOAPMessage getMessage() {
        Boolean outboundProperty;
        SOAPMessage message = null;
        message = this.getWrappedMessage().getContent(Object.class) instanceof SOAPMessage ? (SOAPMessage)this.getWrappedMessage().getContent(Object.class) : this.getWrappedMessage().getContent(SOAPMessage.class);
        if (!(null != message || (outboundProperty = (Boolean)this.get("javax.xml.ws.handler.message.outbound")) != null && outboundProperty.booleanValue())) {
            SAAJ_IN.handleMessage(this.getWrappedSoapMessage());
            message = this.getWrappedSoapMessage().getContent(SOAPMessage.class);
        }
        return message;
    }

    @Override
    public Object[] getHeaders(QName name, JAXBContext context, boolean allRoles) {
        SOAPMessage msg = this.getMessage();
        try {
            SOAPHeader header = msg.getSOAPPart().getEnvelope().getHeader();
            if (header == null || !header.hasChildNodes()) {
                return new Object[0];
            }
            ArrayList<Object> ret = new ArrayList<Object>();
            Iterator it = CastUtils.cast(header.examineAllHeaderElements());
            while (it.hasNext()) {
                SOAPHeaderElement she = (SOAPHeaderElement)it.next();
                if (!allRoles && !this.roles.contains(she.getActor()) || !name.equals(she.getElementQName())) continue;
                ret.add(JAXBUtils.unmarshall(context, she));
            }
            return ret.toArray(new Object[0]);
        }
        catch (JAXBException | SOAPException e) {
            throw new WebServiceException(e);
        }
    }

    @Override
    public Set<String> getRoles() {
        return this.roles;
    }

    private SoapMessage getWrappedSoapMessage() {
        return (SoapMessage)this.getWrappedMessage();
    }

    @Override
    public Object get(Object key) {
        Map mp;
        Object o = super.get(key);
        if (("javax.xml.ws.http.response.headers".equals(key) || "javax.xml.ws.http.request.headers".equals(key)) && (mp = (Map)o) != null) {
            if (mp.isEmpty()) {
                return null;
            }
            if (!this.isRequestor() && this.isOutbound() && "javax.xml.ws.http.response.headers".equals(key)) {
                return null;
            }
            if (this.isRequestor() && !this.isOutbound() && "javax.xml.ws.http.request.headers".equals(key)) {
                return null;
            }
        }
        return o;
    }
}

