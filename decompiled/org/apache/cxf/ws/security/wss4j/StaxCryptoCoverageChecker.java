/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.ws.security.wss4j;

import java.util.ArrayList;
import java.util.List;
import javax.xml.namespace.QName;
import org.apache.cxf.binding.soap.SoapFault;
import org.apache.cxf.binding.soap.SoapMessage;
import org.apache.cxf.binding.soap.SoapVersion;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.phase.AbstractPhaseInterceptor;
import org.apache.cxf.ws.addressing.AddressingProperties;
import org.apache.wss4j.common.ext.WSSecurityException;
import org.apache.wss4j.stax.securityEvent.WSSecurityEventConstants;
import org.apache.xml.security.stax.securityEvent.AbstractSecuredElementSecurityEvent;
import org.apache.xml.security.stax.securityEvent.SecurityEvent;
import org.apache.xml.security.stax.securityEvent.SecurityEventConstants;

public class StaxCryptoCoverageChecker
extends AbstractPhaseInterceptor<SoapMessage> {
    public static final String SOAP_NS = "http://schemas.xmlsoap.org/soap/envelope/";
    public static final String SOAP12_NS = "http://www.w3.org/2003/05/soap-envelope";
    public static final String WSU_NS = "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd";
    public static final String WSSE_NS = "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd";
    public static final String WSA_NS = "http://www.w3.org/2005/08/addressing";
    private boolean signBody;
    private boolean signTimestamp;
    private boolean encryptBody;
    private boolean signAddressingHeaders;
    private boolean signUsernameToken;
    private boolean encryptUsernameToken;

    public StaxCryptoCoverageChecker() {
        super("pre-protocol");
        this.setSignBody(true);
        this.setSignTimestamp(true);
        this.setSignAddressingHeaders(true);
        this.setEncryptUsernameToken(true);
    }

    @Override
    public void handleMessage(SoapMessage soapMessage) throws Fault {
        List incomingSecurityEventList = (List)soapMessage.get(SecurityEvent.class.getName() + ".in");
        ArrayList<SecurityEvent> results = new ArrayList<SecurityEvent>();
        if (incomingSecurityEventList != null) {
            results.addAll(this.getEventFromResults(WSSecurityEventConstants.SIGNED_PART, incomingSecurityEventList));
            results.addAll(this.getEventFromResults(WSSecurityEventConstants.SignedElement, incomingSecurityEventList));
            if (this.encryptBody || this.encryptUsernameToken) {
                results.addAll(this.getEventFromResults(WSSecurityEventConstants.ENCRYPTED_PART, incomingSecurityEventList));
                results.addAll(this.getEventFromResults(WSSecurityEventConstants.EncryptedElement, incomingSecurityEventList));
            }
        }
        try {
            List<SecurityEvent> usernameTokenResults;
            List<SecurityEvent> timestampResults;
            this.checkSignedBody(results);
            this.checkEncryptedBody(results);
            if (this.signTimestamp && !(timestampResults = this.getEventFromResults(WSSecurityEventConstants.TIMESTAMP, incomingSecurityEventList)).isEmpty()) {
                this.checkSignedTimestamp(results);
            }
            if (this.signAddressingHeaders) {
                AddressingProperties addressingProperties = (AddressingProperties)soapMessage.get("javax.xml.ws.addressing.context.inbound");
                this.checkSignedAddressing(results, addressingProperties);
            }
            if ((this.signUsernameToken || this.encryptUsernameToken) && !(usernameTokenResults = this.getEventFromResults(WSSecurityEventConstants.USERNAME_TOKEN, incomingSecurityEventList)).isEmpty()) {
                if (this.signUsernameToken) {
                    this.checkSignedUsernameToken(results);
                }
                if (this.encryptUsernameToken) {
                    this.checkEncryptedUsernameToken(results);
                }
            }
        }
        catch (WSSecurityException e) {
            throw this.createSoapFault(soapMessage.getVersion(), e);
        }
    }

    private List<SecurityEvent> getEventFromResults(SecurityEventConstants.Event event, List<SecurityEvent> incomingSecurityEventList) {
        ArrayList<SecurityEvent> results = new ArrayList<SecurityEvent>();
        for (SecurityEvent incomingEvent : incomingSecurityEventList) {
            if (event != incomingEvent.getSecurityEventType()) continue;
            results.add(incomingEvent);
        }
        return results;
    }

    private void checkSignedBody(List<SecurityEvent> results) throws WSSecurityException {
        if (!this.signBody) {
            return;
        }
        boolean isBodySigned = false;
        for (SecurityEvent signedEvent : results) {
            List<QName> signedPath;
            AbstractSecuredElementSecurityEvent securedEvent = (AbstractSecuredElementSecurityEvent)signedEvent;
            if (!securedEvent.isSigned() || !this.isBody(signedPath = securedEvent.getElementPath())) continue;
            isBodySigned = true;
            break;
        }
        if (!isBodySigned) {
            throw new WSSecurityException(WSSecurityException.ErrorCode.FAILURE, new Exception("The SOAP Body is not signed"));
        }
    }

    private void checkEncryptedBody(List<SecurityEvent> results) throws WSSecurityException {
        if (!this.encryptBody) {
            return;
        }
        boolean isBodyEncrypted = false;
        for (SecurityEvent signedEvent : results) {
            List<QName> encryptedPath;
            AbstractSecuredElementSecurityEvent securedEvent = (AbstractSecuredElementSecurityEvent)signedEvent;
            if (!securedEvent.isEncrypted() || !this.isBody(encryptedPath = securedEvent.getElementPath())) continue;
            isBodyEncrypted = true;
            break;
        }
        if (!isBodyEncrypted) {
            throw new WSSecurityException(WSSecurityException.ErrorCode.FAILURE, new Exception("The SOAP Body is not encrypted"));
        }
    }

    private void checkSignedTimestamp(List<SecurityEvent> results) throws WSSecurityException {
        if (!this.signTimestamp) {
            return;
        }
        boolean isTimestampSigned = false;
        for (SecurityEvent signedEvent : results) {
            List<QName> signedPath;
            AbstractSecuredElementSecurityEvent securedEvent = (AbstractSecuredElementSecurityEvent)signedEvent;
            if (!securedEvent.isSigned() || !this.isTimestamp(signedPath = securedEvent.getElementPath())) continue;
            isTimestampSigned = true;
            break;
        }
        if (!isTimestampSigned) {
            throw new WSSecurityException(WSSecurityException.ErrorCode.FAILURE, new Exception("The Timestamp is not signed"));
        }
    }

    private void checkSignedAddressing(List<SecurityEvent> results, AddressingProperties addressingProperties) throws WSSecurityException {
        if (!this.signAddressingHeaders || addressingProperties == null || addressingProperties.getReplyTo() == null && addressingProperties.getFaultTo() == null) {
            return;
        }
        boolean isReplyToSigned = false;
        boolean isFaultToSigned = false;
        for (SecurityEvent signedEvent : results) {
            AbstractSecuredElementSecurityEvent securedEvent = (AbstractSecuredElementSecurityEvent)signedEvent;
            if (!securedEvent.isSigned()) continue;
            List<QName> signedPath = securedEvent.getElementPath();
            if (this.isReplyTo(signedPath)) {
                isReplyToSigned = true;
            }
            if (this.isFaultTo(signedPath)) {
                isFaultToSigned = true;
            }
            if (!isReplyToSigned || !isFaultToSigned) continue;
            break;
        }
        if (!isReplyToSigned && addressingProperties.getReplyTo() != null) {
            throw new WSSecurityException(WSSecurityException.ErrorCode.FAILURE, new Exception("The Addressing headers are not signed"));
        }
        if (!isFaultToSigned && addressingProperties.getFaultTo() != null) {
            throw new WSSecurityException(WSSecurityException.ErrorCode.FAILURE, new Exception("The Addressing headers are not signed"));
        }
    }

    private void checkSignedUsernameToken(List<SecurityEvent> results) throws WSSecurityException {
        if (!this.signUsernameToken) {
            return;
        }
        boolean isUsernameTokenSigned = false;
        for (SecurityEvent signedEvent : results) {
            List<QName> signedPath;
            AbstractSecuredElementSecurityEvent securedEvent = (AbstractSecuredElementSecurityEvent)signedEvent;
            if (!securedEvent.isSigned() || !this.isUsernameToken(signedPath = securedEvent.getElementPath())) continue;
            isUsernameTokenSigned = true;
            break;
        }
        if (!isUsernameTokenSigned) {
            throw new WSSecurityException(WSSecurityException.ErrorCode.FAILURE, new Exception("The UsernameToken is not signed"));
        }
    }

    private void checkEncryptedUsernameToken(List<SecurityEvent> results) throws WSSecurityException {
        if (!this.encryptUsernameToken) {
            return;
        }
        boolean isUsernameTokenEncrypted = false;
        for (SecurityEvent encryptedEvent : results) {
            List<QName> encryptedPath;
            AbstractSecuredElementSecurityEvent securedEvent = (AbstractSecuredElementSecurityEvent)encryptedEvent;
            if (!securedEvent.isEncrypted() || !this.isUsernameToken(encryptedPath = securedEvent.getElementPath())) continue;
            isUsernameTokenEncrypted = true;
            break;
        }
        if (!isUsernameTokenEncrypted) {
            throw new WSSecurityException(WSSecurityException.ErrorCode.FAILURE, new Exception("The UsernameToken is not encrypted"));
        }
    }

    private boolean isEnvelope(QName qname) {
        return "Envelope".equals(qname.getLocalPart()) && (SOAP_NS.equals(qname.getNamespaceURI()) || SOAP12_NS.equals(qname.getNamespaceURI()));
    }

    private boolean isSoapHeader(QName qname) {
        return "Header".equals(qname.getLocalPart()) && (SOAP_NS.equals(qname.getNamespaceURI()) || SOAP12_NS.equals(qname.getNamespaceURI()));
    }

    private boolean isSecurityHeader(QName qname) {
        return "Security".equals(qname.getLocalPart()) && WSSE_NS.equals(qname.getNamespaceURI());
    }

    private boolean isTimestamp(List<QName> qnames) {
        return qnames != null && qnames.size() == 4 && this.isEnvelope(qnames.get(0)) && this.isSoapHeader(qnames.get(1)) && this.isSecurityHeader(qnames.get(2)) && "Timestamp".equals(qnames.get(3).getLocalPart()) && WSU_NS.equals(qnames.get(3).getNamespaceURI());
    }

    private boolean isReplyTo(List<QName> qnames) {
        return qnames != null && qnames.size() == 3 && this.isEnvelope(qnames.get(0)) && this.isSoapHeader(qnames.get(1)) && "ReplyTo".equals(qnames.get(2).getLocalPart()) && WSA_NS.equals(qnames.get(2).getNamespaceURI());
    }

    private boolean isFaultTo(List<QName> qnames) {
        return qnames != null && qnames.size() == 3 && this.isEnvelope(qnames.get(0)) && this.isSoapHeader(qnames.get(1)) && "FaultTo".equals(qnames.get(2).getLocalPart()) && WSA_NS.equals(qnames.get(2).getNamespaceURI());
    }

    private boolean isBody(List<QName> qnames) {
        return qnames != null && qnames.size() == 2 && this.isEnvelope(qnames.get(0)) && "Body".equals(qnames.get(1).getLocalPart()) && (SOAP_NS.equals(qnames.get(1).getNamespaceURI()) || SOAP12_NS.equals(qnames.get(1).getNamespaceURI()));
    }

    private boolean isUsernameToken(List<QName> qnames) {
        return qnames != null && qnames.size() == 4 && this.isEnvelope(qnames.get(0)) && this.isSoapHeader(qnames.get(1)) && this.isSecurityHeader(qnames.get(2)) && "UsernameToken".equals(qnames.get(3).getLocalPart()) && WSSE_NS.equals(qnames.get(3).getNamespaceURI());
    }

    public boolean isSignBody() {
        return this.signBody;
    }

    public final void setSignBody(boolean signBody) {
        this.signBody = signBody;
    }

    public boolean isSignTimestamp() {
        return this.signTimestamp;
    }

    public final void setSignTimestamp(boolean signTimestamp) {
        this.signTimestamp = signTimestamp;
    }

    public boolean isEncryptBody() {
        return this.encryptBody;
    }

    public final void setEncryptBody(boolean encryptBody) {
        this.encryptBody = encryptBody;
    }

    public boolean isSignAddressingHeaders() {
        return this.signAddressingHeaders;
    }

    public final void setSignAddressingHeaders(boolean signAddressingHeaders) {
        this.signAddressingHeaders = signAddressingHeaders;
    }

    private SoapFault createSoapFault(SoapVersion version, WSSecurityException e) {
        SoapFault fault;
        QName faultCode = e.getFaultCode();
        if (version.getVersion() == 1.1 && faultCode != null) {
            fault = new SoapFault(e.getMessage(), (Throwable)e, faultCode);
        } else {
            fault = new SoapFault(e.getMessage(), (Throwable)e, version.getSender());
            if (version.getVersion() != 1.1 && faultCode != null) {
                fault.setSubCode(faultCode);
            }
        }
        return fault;
    }

    public boolean isSignUsernameToken() {
        return this.signUsernameToken;
    }

    public void setSignUsernameToken(boolean signUsernameToken) {
        this.signUsernameToken = signUsernameToken;
    }

    public boolean isEncryptUsernameToken() {
        return this.encryptUsernameToken;
    }

    public final void setEncryptUsernameToken(boolean encryptUsernameToken) {
        this.encryptUsernameToken = encryptUsernameToken;
    }
}

