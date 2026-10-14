/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.binding.soap.interceptor;

import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.stream.XMLStreamWriter;
import org.apache.cxf.binding.soap.Soap11;
import org.apache.cxf.binding.soap.SoapFault;
import org.apache.cxf.binding.soap.SoapMessage;
import org.apache.cxf.binding.soap.interceptor.AbstractSoapInterceptor;
import org.apache.cxf.binding.soap.interceptor.Soap12FaultOutInterceptor;
import org.apache.cxf.common.logging.LogUtils;
import org.apache.cxf.common.util.StringUtils;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.message.Message;
import org.apache.cxf.staxutils.StaxUtils;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class Soap11FaultOutInterceptor
extends AbstractSoapInterceptor {
    private static final Logger LOG = LogUtils.getL7dLogger(Soap11FaultOutInterceptor.class);

    public Soap11FaultOutInterceptor() {
        super("prepare-send");
    }

    @Override
    public void handleMessage(SoapMessage message) throws Fault {
        Fault f = (Fault)message.getContent(Exception.class);
        message.put(Message.RESPONSE_CODE, (Object)f.getStatusCode());
        if (message.getVersion() == Soap11.getInstance()) {
            message.getInterceptorChain().add(Soap11FaultOutInterceptorInternal.INSTANCE);
        } else {
            message.getInterceptorChain().add(Soap12FaultOutInterceptor.Soap12FaultOutInterceptorInternal.INSTANCE);
        }
    }

    static class Soap11FaultOutInterceptorInternal
    extends AbstractSoapInterceptor {
        static final Soap11FaultOutInterceptorInternal INSTANCE = new Soap11FaultOutInterceptorInternal();

        Soap11FaultOutInterceptorInternal() {
            super("marshal");
        }

        @Override
        public void handleMessage(SoapMessage message) throws Fault {
            XMLStreamWriter writer = message.getContent(XMLStreamWriter.class);
            Fault f = (Fault)message.getContent(Exception.class);
            SoapFault fault = SoapFault.createFault(f, message.getVersion());
            try {
                Map<String, String> namespaces = fault.getNamespaces();
                for (Map.Entry<String, String> e : namespaces.entrySet()) {
                    writer.writeNamespace(e.getKey(), e.getValue());
                }
                String ns = message.getVersion().getNamespace();
                String defaultPrefix = writer.getPrefix(ns);
                if (defaultPrefix == null) {
                    defaultPrefix = StaxUtils.getUniquePrefix(writer, ns, false);
                    writer.writeStartElement(defaultPrefix, "Fault", ns);
                    writer.writeNamespace(defaultPrefix, ns);
                } else {
                    writer.writeStartElement(defaultPrefix, "Fault", ns);
                }
                writer.writeStartElement("faultcode");
                String codeString = fault.getCodeString(this.getFaultCodePrefix(writer, fault.getFaultCode()), defaultPrefix);
                writer.writeCharacters(codeString);
                writer.writeEndElement();
                writer.writeStartElement("faultstring");
                String lang = f.getLang();
                if (!StringUtils.isEmpty(lang)) {
                    writer.writeAttribute("xml", "http://www.w3.org/XML/1998/namespace", "lang", lang);
                }
                writer.writeCharacters(Soap11FaultOutInterceptorInternal.getFaultMessage(message, fault));
                writer.writeEndElement();
                this.prepareStackTrace(message, fault);
                if (fault.getRole() != null) {
                    writer.writeStartElement("faultactor");
                    writer.writeCharacters(fault.getRole());
                    writer.writeEndElement();
                }
                if (fault.hasDetails()) {
                    Element detail = fault.getDetail();
                    writer.writeStartElement("detail");
                    for (Node node = detail.getFirstChild(); node != null; node = node.getNextSibling()) {
                        StaxUtils.writeNode(node, writer, true);
                    }
                    writer.writeEndElement();
                }
                writer.writeEndElement();
            }
            catch (Exception xe) {
                LOG.log(Level.WARNING, "XML_WRITE_EXC", xe);
                throw f;
            }
        }
    }
}

