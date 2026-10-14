/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.binding.soap.interceptor;

import java.util.logging.Logger;
import javax.xml.namespace.QName;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import org.apache.cxf.binding.soap.Soap11;
import org.apache.cxf.binding.soap.SoapFault;
import org.apache.cxf.binding.soap.SoapMessage;
import org.apache.cxf.binding.soap.interceptor.AbstractSoapInterceptor;
import org.apache.cxf.common.i18n.Message;
import org.apache.cxf.common.logging.LogUtils;
import org.apache.cxf.common.util.StringUtils;
import org.apache.cxf.interceptor.ClientFaultConverter;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.staxutils.StaxUtils;
import org.w3c.dom.Element;

public class Soap11FaultInInterceptor
extends AbstractSoapInterceptor {
    private static final Logger LOG = LogUtils.getL7dLogger(Soap11FaultInInterceptor.class);

    public Soap11FaultInInterceptor() {
        super("unmarshal");
        this.addBefore(ClientFaultConverter.class.getName());
    }

    @Override
    public void handleMessage(SoapMessage message) throws Fault {
        XMLStreamReader reader = message.getContent(XMLStreamReader.class);
        message.setContent(Exception.class, Soap11FaultInInterceptor.unmarshalFault(message, reader));
    }

    public static SoapFault unmarshalFault(SoapMessage message, XMLStreamReader reader) {
        String exMessage = "";
        QName faultCode = null;
        String role = null;
        Element detail = null;
        String lang = null;
        try {
            while (reader.nextTag() == 1) {
                if ("faultcode".equals(reader.getLocalName())) {
                    faultCode = StaxUtils.readQName(reader);
                    continue;
                }
                if ("faultstring".equals(reader.getLocalName())) {
                    lang = reader.getAttributeValue("http://www.w3.org/XML/1998/namespace", "lang");
                    exMessage = reader.getElementText();
                    continue;
                }
                if ("faultactor".equals(reader.getLocalName())) {
                    role = reader.getElementText();
                    continue;
                }
                if (!"detail".equals(reader.getLocalName())) continue;
                detail = StaxUtils.read(reader).getDocumentElement();
            }
        }
        catch (XMLStreamException e) {
            throw new SoapFault("Could not parse message.", (Throwable)e, message.getVersion().getSender());
        }
        if (faultCode == null) {
            faultCode = Soap11.getInstance().getReceiver();
            exMessage = new Message("INVALID_FAULT", LOG, new Object[0]).toString();
        }
        SoapFault fault = new SoapFault(exMessage, faultCode);
        fault.setDetail(detail);
        fault.setRole(role);
        if (!StringUtils.isEmpty(lang)) {
            fault.setLang(lang);
        }
        return fault;
    }
}

