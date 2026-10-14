/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.binding.soap.jms.interceptor;

import javax.xml.namespace.QName;

public final class SoapJMSConstants {
    public static final String SOAP_JMS_PREFIX = "SOAPJMS_";
    public static final String JMS_MESSAGE_TYPE = "JMSMessageType";
    public static final String TARGET_SERVICE_IN_REQUESTURI = "target.service.inrequesturi";
    public static final String MALFORMED_REQUESTURI = "malformed.requesturi";
    public static final String REQUESTURI_PARAMETER_NAME = "requestURI";
    public static final String BINDINGVERSION_PARAMETER_NAME = "bindingVersion";
    public static final String SOAPACTION_PARAMETER_NAME = "soapAction";
    public static final String TARGETSERVICE_PARAMETER_NAME = "targetService";
    public static final String CONTENTTYPE_PARAMETER_NAME = "contentType";
    public static final String CONTENTENCODING_PARAMETER_NAME = "contentEncoding";
    public static final String ISFAULT_PARAMETER_NAME = "isFault";
    public static final String REQUESTURI_FIELD = "SOAPJMS_requestURI";
    public static final String BINDINGVERSION_FIELD = "SOAPJMS_bindingVersion";
    public static final String SOAPACTION_FIELD = "SOAPJMS_soapAction";
    public static final String TARGETSERVICE_FIELD = "SOAPJMS_targetService";
    public static final String CONTENTTYPE_FIELD = "SOAPJMS_contentType";
    public static final String CONTENTENCODING_FIELD = "SOAPJMS_contentEncoding";
    public static final String ISFAULT_FIELD = "SOAPJMS_isFault";
    public static final String SOAP_JMS_SPECIFICIATION_TRANSPORTID = "http://www.w3.org/2010/soapjms/";
    public static final String SOAP_JMS_NAMESPACE = "http://www.w3.org/2010/soapjms/";
    private static final String JMS_CONTENTTYPEMISMATCH_FAULT_CODE = "contentTypeMismatch";
    private static final String JMS_CONTENTENCODINGNOTSUPPORTED_FAULT_CODE = "contentEncodingNotSupported";
    private static final String JMS_MALFORMEDREQUESTURI_FAULT_CODE = "malformedRequestURI";
    private static final String JMS_MISMATCHEDSOAPACTION_FAULT_CODE = "mismatchedSoapAction";
    private static final String JMS_MISSINGCONTENTTYPE_FAULT_CODE = "missingContentType";
    private static final String JMS_MISSINGSOAPACTION_FAULT_CODE = "missingSoapAction";
    private static final String JMS_MISSINGREQUESTURI_FAULT_CODE = "missingRequestURI";
    private static final String JMS_TARGETSERVICENOTALLOWEDINREQUESTURI_FAULT_CODE = "targetServiceNotAllowedInRequestURI";
    private static final String JMS_UNRECOGNIZEDBINDINGVERSION_FAULT_CODE = "unrecognizedBindingVersion";
    private static final String JMS_UNSUPPORTEDJMSMESSAGEFORMAT_FAULT_CODE = "unsupportedJMSMessageFormat";

    private SoapJMSConstants() {
    }

    public static QName getContentTypeMismatchQName() {
        return new QName("http://www.w3.org/2010/soapjms/", JMS_CONTENTTYPEMISMATCH_FAULT_CODE);
    }

    public static QName getContentEncodingNotSupportedQName() {
        return new QName("http://www.w3.org/2010/soapjms/", JMS_CONTENTENCODINGNOTSUPPORTED_FAULT_CODE);
    }

    public static QName getMalformedRequestURIQName() {
        return new QName("http://www.w3.org/2010/soapjms/", JMS_MALFORMEDREQUESTURI_FAULT_CODE);
    }

    public static QName getMismatchedSoapActionQName() {
        return new QName("http://www.w3.org/2010/soapjms/", JMS_MISMATCHEDSOAPACTION_FAULT_CODE);
    }

    public static QName getMissingContentTypeQName() {
        return new QName("http://www.w3.org/2010/soapjms/", JMS_MISSINGCONTENTTYPE_FAULT_CODE);
    }

    public static QName getMissingSoapActionQName() {
        return new QName("http://www.w3.org/2010/soapjms/", JMS_MISSINGSOAPACTION_FAULT_CODE);
    }

    public static QName getMissingRequestURIQName() {
        return new QName("http://www.w3.org/2010/soapjms/", JMS_MISSINGREQUESTURI_FAULT_CODE);
    }

    public static QName getTargetServiceNotAllowedInRequestURIQName() {
        return new QName("http://www.w3.org/2010/soapjms/", JMS_TARGETSERVICENOTALLOWEDINREQUESTURI_FAULT_CODE);
    }

    public static QName getUnrecognizedBindingVersionQName() {
        return new QName("http://www.w3.org/2010/soapjms/", JMS_UNRECOGNIZEDBINDINGVERSION_FAULT_CODE);
    }

    public static QName getUnsupportedJMSMessageFormatQName() {
        return new QName("http://www.w3.org/2010/soapjms/", JMS_UNSUPPORTEDJMSMESSAGEFORMAT_FAULT_CODE);
    }
}

