/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.binding.soap.jms.interceptor;

import java.util.List;
import java.util.Map;
import org.apache.cxf.binding.Binding;
import org.apache.cxf.binding.soap.SoapMessage;
import org.apache.cxf.binding.soap.interceptor.AbstractSoapInterceptor;
import org.apache.cxf.binding.soap.jms.interceptor.JMSFault;
import org.apache.cxf.binding.soap.jms.interceptor.JMSFaultFactory;
import org.apache.cxf.binding.soap.jms.interceptor.SoapFaultFactory;
import org.apache.cxf.endpoint.Endpoint;
import org.apache.cxf.helpers.CastUtils;
import org.apache.cxf.interceptor.AttachmentInInterceptor;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.message.Message;

public class SoapJMSInInterceptor
extends AbstractSoapInterceptor {
    public static final String JMS_SOAP_ACTION_VALUE = "jms.soap.action.value";

    public SoapJMSInInterceptor() {
        super("receive");
        this.addAfter(AttachmentInInterceptor.class.getName());
    }

    @Override
    public void handleMessage(SoapMessage message) throws Fault {
        Map<String, List<String>> headers = CastUtils.cast((Map)message.get(Message.PROTOCOL_HEADERS));
        if (headers != null) {
            this.checkContentType(message, headers);
            this.checkContentEncoding(message, headers);
            this.checkRequestURI(message, headers);
            this.checkSoapAction(message, headers);
            this.checkBindingVersion(message, headers);
            this.checkJMSMessageFormat(message, headers);
        }
    }

    private void checkContentEncoding(SoapMessage message, Map<String, List<String>> headers) {
        Fault f;
        String contentEncoding;
        List<String> contentEncodingList = headers.get("SOAPJMS_contentEncoding");
        JMSFault jmsFault = null;
        if (contentEncodingList != null && !contentEncodingList.isEmpty() && !"gzip".equals(contentEncoding = contentEncodingList.get(0))) {
            jmsFault = JMSFaultFactory.createContentEncodingNotSupported(contentEncoding);
        }
        if (jmsFault != null && (f = this.createFault(message, jmsFault)) != null) {
            throw f;
        }
    }

    private void checkJMSMessageFormat(SoapMessage message, Map<String, List<String>> headers) {
        JMSFault jmsFault;
        Fault f;
        String messageType;
        List<String> mt = headers.get("JMSMessageType");
        if (!(mt == null || mt.isEmpty() || "text".equals(messageType = mt.get(0)) || "byte".equals(messageType) || (f = this.createFault(message, jmsFault = JMSFaultFactory.createUnsupportedJMSMessageFormatFault(messageType))) == null)) {
            throw f;
        }
    }

    private void checkSoapAction(SoapMessage message, Map<String, List<String>> headers) {
        Fault f;
        JMSFault jmsFault = null;
        String soapAction = null;
        String contentType = null;
        String contentTypeAction = null;
        List<String> ct = headers.get("SOAPJMS_contentType");
        List<String> sa = headers.get("SOAPJMS_soapAction");
        if (sa != null && !sa.isEmpty() && (soapAction = sa.get(0)) != null && soapAction.startsWith("\"")) {
            soapAction = soapAction.substring(1, soapAction.lastIndexOf(34));
        }
        if (ct != null && !ct.isEmpty()) {
            contentType = ct.get(0);
        }
        if (contentType != null && contentType.indexOf("action=") != -1) {
            contentTypeAction = contentType.substring(contentType.indexOf("action=") + 7);
            if (contentTypeAction.indexOf(59) != -1) {
                contentTypeAction = contentTypeAction.substring(0, contentTypeAction.indexOf(59));
            }
            if (contentTypeAction.startsWith("\"")) {
                contentTypeAction = contentTypeAction.substring(1, contentTypeAction.lastIndexOf(34));
            }
        }
        if (contentTypeAction != null) {
            if (sa == null) {
                jmsFault = JMSFaultFactory.createMissingSoapActionFault();
            }
            if (soapAction != null && !soapAction.equals(contentTypeAction)) {
                jmsFault = JMSFaultFactory.createMismatchedSoapActionFault(contentTypeAction);
            }
        }
        if (jmsFault != null && (f = this.createFault(message, jmsFault)) != null) {
            throw f;
        }
        if (soapAction != null) {
            message.put(JMS_SOAP_ACTION_VALUE, (Object)soapAction);
        }
    }

    private void checkRequestURI(SoapMessage message, Map<String, List<String>> headers) {
        Fault f;
        List<String> ru = headers.get("SOAPJMS_requestURI");
        JMSFault jmsFault = null;
        if (ru != null && !ru.isEmpty()) {
            List<String> trn;
            String requestURI = ru.get(0);
            List<String> mr = headers.get("malformed.requesturi");
            if (mr != null && !mr.isEmpty() && "true".equals(mr.get(0))) {
                jmsFault = JMSFaultFactory.createMalformedRequestURIFault(requestURI);
            }
            if ((trn = headers.get("target.service.inrequesturi")) != null && !trn.isEmpty() && "true".equals(trn.get(0))) {
                jmsFault = JMSFaultFactory.createTargetServiceNotAllowedInRequestURIFault();
            }
        } else {
            jmsFault = JMSFaultFactory.createMissingRequestURIFault();
        }
        if (jmsFault != null && (f = this.createFault(message, jmsFault)) != null) {
            throw f;
        }
    }

    private void checkContentType(SoapMessage message, Map<String, List<String>> headers) {
        Fault f;
        List<String> ct = headers.get("SOAPJMS_contentType");
        JMSFault jmsFault = null;
        if (ct != null && !ct.isEmpty()) {
            String contentType = ct.get(0);
            if (!(contentType.startsWith("text/xml") || contentType.startsWith("application/soap+xml") || contentType.startsWith("application/fastinfoset") || contentType.startsWith("application/soap+fastinfoset") || contentType.startsWith("multipart/related"))) {
                jmsFault = JMSFaultFactory.createContentTypeMismatchFault(contentType);
            }
        } else {
            jmsFault = JMSFaultFactory.createMissingContentTypeFault();
        }
        if (jmsFault != null && (f = this.createFault(message, jmsFault)) != null) {
            throw f;
        }
    }

    private void checkBindingVersion(SoapMessage message, Map<String, List<String>> headers) {
        JMSFault jmsFault;
        Fault f;
        String bindingVersion;
        List<String> bv = headers.get("SOAPJMS_bindingVersion");
        if (bv != null && !bv.isEmpty() && !"1.0".equals(bindingVersion = bv.get(0)) && (f = this.createFault(message, jmsFault = JMSFaultFactory.createUnrecognizedBindingVerionFault(bindingVersion))) != null) {
            throw f;
        }
    }

    private Fault createFault(SoapMessage message, JMSFault jmsFault) {
        Fault f = null;
        Endpoint e = message.getExchange().getEndpoint();
        Binding b = null;
        if (null != e) {
            b = e.getBinding();
        }
        if (null != b) {
            SoapFaultFactory sff = new SoapFaultFactory(b);
            f = sff.createFault(jmsFault);
        }
        return f;
    }
}

