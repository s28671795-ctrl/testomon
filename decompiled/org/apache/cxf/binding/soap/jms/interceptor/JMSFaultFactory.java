/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.binding.soap.jms.interceptor;

import java.util.logging.Logger;
import javax.xml.namespace.QName;
import org.apache.cxf.binding.soap.jms.interceptor.JMSFault;
import org.apache.cxf.binding.soap.jms.interceptor.JMSFaultType;
import org.apache.cxf.binding.soap.jms.interceptor.SoapJMSConstants;
import org.apache.cxf.common.i18n.Message;
import org.apache.cxf.common.logging.LogUtils;

public final class JMSFaultFactory {
    static final Logger LOG = LogUtils.getL7dLogger(JMSFaultFactory.class);

    private JMSFaultFactory() {
    }

    public static JMSFault createContentTypeMismatchFault(String contentType) {
        String m4 = new Message("CONTENTTYPE_MISMATCH", LOG, contentType).toString();
        return JMSFaultFactory.createFault(SoapJMSConstants.getContentTypeMismatchQName(), m4);
    }

    public static JMSFault createContentEncodingNotSupported(String contentEncoding) {
        String m4 = new Message("CONTENT_ENCODING_NOT_SUPPORTED", LOG, contentEncoding).toString();
        return JMSFaultFactory.createFault(SoapJMSConstants.getContentTypeMismatchQName(), m4);
    }

    public static JMSFault createMalformedRequestURIFault(String requestURI) {
        String m4 = new Message("MALFORMED_REQUESTURI", LOG, requestURI).toString();
        return JMSFaultFactory.createFault(SoapJMSConstants.getMalformedRequestURIQName(), m4);
    }

    public static JMSFault createMismatchedSoapActionFault(String soapAction) {
        String m4 = new Message("MISMATCHED_SOAPACTION", LOG, soapAction).toString();
        return JMSFaultFactory.createFault(SoapJMSConstants.getMismatchedSoapActionQName(), m4);
    }

    public static JMSFault createMissingContentTypeFault() {
        String m4 = new Message("MISSING_CONTENTTYPE", LOG, new Object[0]).toString();
        return JMSFaultFactory.createFault(SoapJMSConstants.getMissingContentTypeQName(), m4);
    }

    public static JMSFault createMissingSoapActionFault() {
        String m4 = new Message("MISSING_SOAPACTION", LOG, new Object[0]).toString();
        return JMSFaultFactory.createFault(SoapJMSConstants.getMissingSoapActionQName(), m4);
    }

    public static JMSFault createMissingRequestURIFault() {
        String m4 = new Message("MISSING_REQUESTURI", LOG, new Object[0]).toString();
        return JMSFaultFactory.createFault(SoapJMSConstants.getMissingRequestURIQName(), m4);
    }

    public static JMSFault createTargetServiceNotAllowedInRequestURIFault() {
        String m4 = new Message("TARGET_SERVICE_NOT_ALLOWED_IN_REQUESTURI", LOG, new Object[0]).toString();
        return JMSFaultFactory.createFault(SoapJMSConstants.getTargetServiceNotAllowedInRequestURIQName(), m4);
    }

    public static JMSFault createUnrecognizedBindingVerionFault(String bindingVersion) {
        String m4 = new Message("UNRECOGNIZED_BINDINGVERSION", LOG, bindingVersion).toString();
        return JMSFaultFactory.createFault(SoapJMSConstants.getUnrecognizedBindingVersionQName(), m4);
    }

    public static JMSFault createUnsupportedJMSMessageFormatFault(String messageFormat) {
        String m4 = new Message("UNSUPPORTED_JMSMESSAGEFORMAT", LOG, messageFormat).toString();
        return JMSFaultFactory.createFault(SoapJMSConstants.getUnsupportedJMSMessageFormatQName(), m4);
    }

    private static JMSFault createFault(QName faultCode, String message) {
        JMSFaultType jmsFaultType = new JMSFaultType();
        jmsFaultType.setFaultCode(faultCode);
        JMSFault jmsFault = new JMSFault(message);
        jmsFault.setJmsFaultType(jmsFaultType);
        jmsFault.setSender(true);
        return jmsFault;
    }
}

