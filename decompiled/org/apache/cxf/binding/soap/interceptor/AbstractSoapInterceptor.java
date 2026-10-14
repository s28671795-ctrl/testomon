/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.binding.soap.interceptor;

import java.net.URI;
import java.util.Collections;
import java.util.Set;
import javax.xml.namespace.QName;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import org.apache.cxf.binding.soap.SoapFault;
import org.apache.cxf.binding.soap.SoapMessage;
import org.apache.cxf.binding.soap.interceptor.SoapInterceptor;
import org.apache.cxf.common.util.StringUtils;
import org.apache.cxf.helpers.DOMUtils;
import org.apache.cxf.message.MessageUtils;
import org.apache.cxf.phase.AbstractPhaseInterceptor;
import org.apache.cxf.staxutils.StaxUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public abstract class AbstractSoapInterceptor
extends AbstractPhaseInterceptor<SoapMessage>
implements SoapInterceptor {
    public AbstractSoapInterceptor(String p) {
        super(p);
    }

    public AbstractSoapInterceptor(String i, String p) {
        super(i, p);
    }

    @Override
    public Set<URI> getRoles() {
        return Collections.emptySet();
    }

    @Override
    public Set<QName> getUnderstoodHeaders() {
        return Collections.emptySet();
    }

    protected String getFaultCodePrefix(XMLStreamWriter writer, QName faultCode) throws XMLStreamException {
        String codeNs = faultCode.getNamespaceURI();
        String prefix = null;
        if (codeNs.length() > 0) {
            prefix = faultCode.getPrefix();
            if (!StringUtils.isEmpty(prefix)) {
                String boundNS = writer.getNamespaceContext().getNamespaceURI(prefix);
                if (StringUtils.isEmpty(boundNS)) {
                    writer.writeNamespace(prefix, codeNs);
                } else if (!codeNs.equals(boundNS)) {
                    prefix = null;
                }
            }
            if (StringUtils.isEmpty(prefix)) {
                prefix = StaxUtils.getUniquePrefix(writer, codeNs, true);
            }
        }
        return prefix;
    }

    protected void prepareStackTrace(SoapMessage message, SoapFault fault) throws Exception {
        boolean config = MessageUtils.getContextualBoolean(message, "faultStackTraceEnabled", false);
        if (config && fault.getCause() != null) {
            StringBuilder sb = new StringBuilder();
            Throwable throwable = fault.getCause();
            sb.append("Caused by: ").append(throwable.getClass().getCanonicalName()).append(": " + throwable.getMessage() + "\n").append("#*#");
            while (throwable != null) {
                for (StackTraceElement ste : throwable.getStackTrace()) {
                    sb.append(ste.getClassName() + "!" + ste.getMethodName() + "!" + ste.getFileName() + "!" + ste.getLineNumber() + "#*#");
                }
                if ((throwable = throwable.getCause()) == null) continue;
                sb.append("Caused by: " + throwable.getClass().getCanonicalName() + " : " + throwable.getMessage() + "#*#");
            }
            Element detail = fault.getDetail();
            String soapNamespace = message.getVersion().getNamespace();
            if (detail == null) {
                Document doc = DOMUtils.getEmptyDocument();
                Element stackTrace = doc.createElementNS("http://cxf.apache.org/fault", "stackTrace");
                stackTrace.setTextContent(sb.toString());
                detail = doc.createElementNS(soapNamespace, "detail");
                fault.setDetail(detail);
                detail.appendChild(stackTrace);
            } else {
                Element stackTrace = detail.getOwnerDocument().createElementNS("http://cxf.apache.org/fault", "stackTrace");
                stackTrace.setTextContent(sb.toString());
                detail.appendChild(stackTrace);
            }
        }
    }

    static String getFaultMessage(SoapMessage message, SoapFault fault) {
        if (message.get("forced.faultstring") != null) {
            return (String)message.get("forced.faultstring");
        }
        boolean config = MessageUtils.getContextualBoolean(message, "exceptionMessageCauseEnabled", false);
        if (fault.getMessage() != null) {
            if (config && fault.getCause() != null && fault.getCause().getMessage() != null && !fault.getMessage().equals(fault.getCause().getMessage())) {
                return fault.getMessage() + " Caused by: " + fault.getCause().getMessage();
            }
            return fault.getMessage();
        }
        if (config && fault.getCause() != null) {
            if (fault.getCause().getMessage() != null) {
                return fault.getCause().getMessage();
            }
            return fault.getCause().toString();
        }
        return "Fault occurred while processing.";
    }
}

