/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.ws.security.wss4j;

import java.util.List;
import java.util.Map;
import org.apache.cxf.ws.security.wss4j.CryptoCoverageChecker;
import org.apache.cxf.ws.security.wss4j.CryptoCoverageUtil;

public class DefaultCryptoCoverageChecker
extends CryptoCoverageChecker {
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

    public DefaultCryptoCoverageChecker() {
        super((Map<String, String>)null, (List<CryptoCoverageChecker.XPathExpression>)null);
        this.prefixMap.put("soapenv", SOAP_NS);
        this.prefixMap.put("soapenv12", SOAP12_NS);
        this.prefixMap.put("wsu", WSU_NS);
        this.prefixMap.put("wsse", WSSE_NS);
        this.prefixMap.put("wsa", WSA_NS);
        this.setSignBody(true);
        this.setSignTimestamp(true);
        this.setSignAddressingHeaders(true);
        this.setEncryptUsernameToken(true);
    }

    public boolean isSignBody() {
        return this.signBody;
    }

    public final void setSignBody(boolean signBody) {
        this.signBody = signBody;
        CryptoCoverageChecker.XPathExpression soap11Expression = new CryptoCoverageChecker.XPathExpression("/soapenv:Envelope/soapenv:Body", CryptoCoverageUtil.CoverageType.SIGNED);
        CryptoCoverageChecker.XPathExpression soap12Expression = new CryptoCoverageChecker.XPathExpression("/soapenv12:Envelope/soapenv12:Body", CryptoCoverageUtil.CoverageType.SIGNED);
        if (signBody) {
            if (!this.xPaths.contains(soap11Expression)) {
                this.xPaths.add(soap11Expression);
            }
            if (!this.xPaths.contains(soap12Expression)) {
                this.xPaths.add(soap12Expression);
            }
        } else {
            if (this.xPaths.contains(soap11Expression)) {
                this.xPaths.remove(soap11Expression);
            }
            if (this.xPaths.contains(soap12Expression)) {
                this.xPaths.remove(soap12Expression);
            }
        }
    }

    public boolean isSignTimestamp() {
        return this.signTimestamp;
    }

    public final void setSignTimestamp(boolean signTimestamp) {
        this.signTimestamp = signTimestamp;
        CryptoCoverageChecker.XPathExpression soap11Expression = new CryptoCoverageChecker.XPathExpression("/soapenv:Envelope/soapenv:Header/wsse:Security/wsu:Timestamp", CryptoCoverageUtil.CoverageType.SIGNED);
        CryptoCoverageChecker.XPathExpression soap12Expression = new CryptoCoverageChecker.XPathExpression("/soapenv12:Envelope/soapenv12:Header/wsse:Security/wsu:Timestamp", CryptoCoverageUtil.CoverageType.SIGNED);
        if (signTimestamp) {
            if (!this.xPaths.contains(soap11Expression)) {
                this.xPaths.add(soap11Expression);
            }
            if (!this.xPaths.contains(soap12Expression)) {
                this.xPaths.add(soap12Expression);
            }
        } else {
            if (this.xPaths.contains(soap11Expression)) {
                this.xPaths.remove(soap11Expression);
            }
            if (this.xPaths.contains(soap12Expression)) {
                this.xPaths.remove(soap12Expression);
            }
        }
    }

    public boolean isEncryptBody() {
        return this.encryptBody;
    }

    public final void setEncryptBody(boolean encryptBody) {
        this.encryptBody = encryptBody;
        CryptoCoverageChecker.XPathExpression soap11Expression = new CryptoCoverageChecker.XPathExpression("/soapenv:Envelope/soapenv:Body", CryptoCoverageUtil.CoverageType.ENCRYPTED, CryptoCoverageUtil.CoverageScope.CONTENT);
        CryptoCoverageChecker.XPathExpression soap12Expression = new CryptoCoverageChecker.XPathExpression("/soapenv12:Envelope/soapenv12:Body", CryptoCoverageUtil.CoverageType.ENCRYPTED, CryptoCoverageUtil.CoverageScope.CONTENT);
        if (encryptBody) {
            if (!this.xPaths.contains(soap11Expression)) {
                this.xPaths.add(soap11Expression);
            }
            if (!this.xPaths.contains(soap12Expression)) {
                this.xPaths.add(soap12Expression);
            }
        } else {
            if (this.xPaths.contains(soap11Expression)) {
                this.xPaths.remove(soap11Expression);
            }
            if (this.xPaths.contains(soap12Expression)) {
                this.xPaths.remove(soap12Expression);
            }
        }
    }

    public boolean isSignAddressingHeaders() {
        return this.signAddressingHeaders;
    }

    public final void setSignAddressingHeaders(boolean signAddressingHeaders) {
        this.signAddressingHeaders = signAddressingHeaders;
        CryptoCoverageChecker.XPathExpression soap11Expression = new CryptoCoverageChecker.XPathExpression("/soapenv:Envelope/soapenv:Header/wsa:ReplyTo", CryptoCoverageUtil.CoverageType.SIGNED);
        CryptoCoverageChecker.XPathExpression soap11Expression2 = new CryptoCoverageChecker.XPathExpression("/soapenv:Envelope/soapenv:Header/wsa:FaultTo", CryptoCoverageUtil.CoverageType.SIGNED);
        CryptoCoverageChecker.XPathExpression soap12Expression = new CryptoCoverageChecker.XPathExpression("/soapenv12:Envelope/soapenv12:Header/wsa:ReplyTo", CryptoCoverageUtil.CoverageType.SIGNED);
        CryptoCoverageChecker.XPathExpression soap12Expression2 = new CryptoCoverageChecker.XPathExpression("/soapenv12:Envelope/soapenv12:Header/wsa:FaultTo", CryptoCoverageUtil.CoverageType.SIGNED);
        if (signAddressingHeaders) {
            if (!this.xPaths.contains(soap11Expression)) {
                this.xPaths.add(soap11Expression);
            }
            if (!this.xPaths.contains(soap11Expression2)) {
                this.xPaths.add(soap11Expression2);
            }
            if (!this.xPaths.contains(soap12Expression)) {
                this.xPaths.add(soap12Expression);
            }
            if (!this.xPaths.contains(soap12Expression2)) {
                this.xPaths.add(soap12Expression2);
            }
        } else {
            if (this.xPaths.contains(soap11Expression)) {
                this.xPaths.remove(soap11Expression);
            }
            if (this.xPaths.contains(soap11Expression2)) {
                this.xPaths.remove(soap11Expression2);
            }
            if (this.xPaths.contains(soap12Expression)) {
                this.xPaths.remove(soap12Expression);
            }
            if (this.xPaths.contains(soap12Expression2)) {
                this.xPaths.remove(soap12Expression2);
            }
        }
    }

    public boolean isEncryptUsernameToken() {
        return this.encryptUsernameToken;
    }

    public final void setEncryptUsernameToken(boolean encryptUsernameToken) {
        this.encryptUsernameToken = encryptUsernameToken;
        CryptoCoverageChecker.XPathExpression soap11Expression = new CryptoCoverageChecker.XPathExpression("/soapenv:Envelope/soapenv:Header/wsse:Security/wsse:UsernameToken", CryptoCoverageUtil.CoverageType.ENCRYPTED);
        CryptoCoverageChecker.XPathExpression soap12Expression = new CryptoCoverageChecker.XPathExpression("/soapenv12:Envelope/soapenv12:Header/wsse:Security/wsse:UsernameToken", CryptoCoverageUtil.CoverageType.ENCRYPTED);
        if (encryptUsernameToken) {
            if (!this.xPaths.contains(soap11Expression)) {
                this.xPaths.add(soap11Expression);
            }
            if (!this.xPaths.contains(soap12Expression)) {
                this.xPaths.add(soap12Expression);
            }
        } else {
            if (this.xPaths.contains(soap11Expression)) {
                this.xPaths.remove(soap11Expression);
            }
            if (this.xPaths.contains(soap12Expression)) {
                this.xPaths.remove(soap12Expression);
            }
        }
    }

    public boolean isSignUsernameToken() {
        return this.signUsernameToken;
    }

    public void setSignUsernameToken(boolean signUsernameToken) {
        this.signUsernameToken = signUsernameToken;
        CryptoCoverageChecker.XPathExpression soap11Expression = new CryptoCoverageChecker.XPathExpression("/soapenv:Envelope/soapenv:Header/wsse:Security/wsse:UsernameToken", CryptoCoverageUtil.CoverageType.SIGNED);
        CryptoCoverageChecker.XPathExpression soap12Expression = new CryptoCoverageChecker.XPathExpression("/soapenv12:Envelope/soapenv12:Header/wsse:Security/wsse:UsernameToken", CryptoCoverageUtil.CoverageType.SIGNED);
        if (signUsernameToken) {
            if (!this.xPaths.contains(soap11Expression)) {
                this.xPaths.add(soap11Expression);
            }
            if (!this.xPaths.contains(soap12Expression)) {
                this.xPaths.add(soap12Expression);
            }
        } else {
            if (this.xPaths.contains(soap11Expression)) {
                this.xPaths.remove(soap11Expression);
            }
            if (this.xPaths.contains(soap12Expression)) {
                this.xPaths.remove(soap12Expression);
            }
        }
    }
}

