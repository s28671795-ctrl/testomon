/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.binding.soap.interceptor;

import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.namespace.QName;
import javax.xml.stream.XMLStreamWriter;
import org.apache.cxf.binding.soap.Soap11;
import org.apache.cxf.binding.soap.SoapFault;
import org.apache.cxf.binding.soap.SoapMessage;
import org.apache.cxf.binding.soap.interceptor.AbstractSoapInterceptor;
import org.apache.cxf.binding.soap.interceptor.Soap11FaultOutInterceptor;
import org.apache.cxf.common.logging.LogUtils;
import org.apache.cxf.common.util.StringUtils;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.message.Message;
import org.apache.cxf.staxutils.StaxUtils;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class Soap12FaultOutInterceptor
extends AbstractSoapInterceptor {
    private static final Logger LOG = LogUtils.getL7dLogger(Soap12FaultOutInterceptor.class);

    public Soap12FaultOutInterceptor() {
        super("prepare-send");
    }

    @Override
    public void handleMessage(SoapMessage message) throws Fault {
        Fault f = (Fault)message.getContent(Exception.class);
        message.put(Message.RESPONSE_CODE, (Object)f.getStatusCode());
        if (message.getVersion() == Soap11.getInstance()) {
            message.getInterceptorChain().add(Soap11FaultOutInterceptor.Soap11FaultOutInterceptorInternal.INSTANCE);
        } else {
            message.getInterceptorChain().add(Soap12FaultOutInterceptorInternal.INSTANCE);
        }
    }

    static class Soap12FaultOutInterceptorInternal
    extends AbstractSoapInterceptor {
        static final Soap12FaultOutInterceptorInternal INSTANCE = new Soap12FaultOutInterceptorInternal();

        Soap12FaultOutInterceptorInternal() {
            super("marshal");
        }

        @Override
        public void handleMessage(SoapMessage message) throws Fault {
            LOG.info(this.getClass() + (String)message.get("Content-Type"));
            XMLStreamWriter writer = message.getContent(XMLStreamWriter.class);
            Fault f = (Fault)message.getContent(Exception.class);
            message.put(Message.RESPONSE_CODE, (Object)f.getStatusCode());
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
                writer.writeStartElement(defaultPrefix, "Code", ns);
                writer.writeStartElement(defaultPrefix, "Value", ns);
                writer.writeCharacters(fault.getCodeString(this.getFaultCodePrefix(writer, fault.getFaultCode()), defaultPrefix));
                writer.writeEndElement();
                if (fault.getSubCodes() != null) {
                    int fscCount = 0;
                    for (QName fsc : fault.getSubCodes()) {
                        writer.writeStartElement(defaultPrefix, "Subcode", ns);
                        writer.writeStartElement(defaultPrefix, "Value", ns);
                        writer.writeCharacters(Soap12FaultOutInterceptorInternal.getCodeString(this.getFaultCodePrefix(writer, fsc), defaultPrefix, fsc));
                        writer.writeEndElement();
                        ++fscCount;
                    }
                    while (fscCount-- > 0) {
                        writer.writeEndElement();
                    }
                }
                writer.writeEndElement();
                writer.writeStartElement(defaultPrefix, "Reason", ns);
                writer.writeStartElement(defaultPrefix, "Text", ns);
                String lang = f.getLang();
                if (StringUtils.isEmpty(lang)) {
                    lang = this.getLangCode();
                }
                writer.writeAttribute("xml", "http://www.w3.org/XML/1998/namespace", "lang", lang);
                writer.writeCharacters(Soap12FaultOutInterceptorInternal.getFaultMessage(message, fault));
                writer.writeEndElement();
                writer.writeEndElement();
                if (fault.getRole() != null) {
                    writer.writeStartElement(defaultPrefix, "Role", ns);
                    writer.writeCharacters(fault.getRole());
                    writer.writeEndElement();
                }
                this.prepareStackTrace(message, fault);
                if (fault.hasDetails()) {
                    Element detail = fault.getDetail();
                    writer.writeStartElement(defaultPrefix, "Detail", ns);
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

        private String getLangCode() {
            String code = LOG.getResourceBundle().getLocale().getLanguage();
            if (StringUtils.isEmpty(code)) {
                return "en";
            }
            return code;
        }

        private static String getCodeString(String prefix, String defaultPrefix, QName code) {
            String codePrefix = null;
            if (StringUtils.isEmpty(prefix)) {
                codePrefix = code.getPrefix();
                if (StringUtils.isEmpty(codePrefix)) {
                    codePrefix = defaultPrefix;
                }
            } else {
                codePrefix = prefix;
            }
            return codePrefix + ":" + code.getLocalPart();
        }
    }
}

