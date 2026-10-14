/*
 * Decompiled with CFR 0.152.
 */
package org.apache.wss4j.dom.processor;

import java.security.cert.X509Certificate;
import java.util.Collections;
import java.util.List;
import javax.xml.namespace.QName;
import org.apache.wss4j.common.crypto.Crypto;
import org.apache.wss4j.common.ext.WSSecurityException;
import org.apache.wss4j.common.principal.SAMLTokenPrincipalImpl;
import org.apache.wss4j.common.token.BinarySecurity;
import org.apache.wss4j.common.token.PKIPathSecurity;
import org.apache.wss4j.common.token.X509Security;
import org.apache.wss4j.common.util.XMLUtils;
import org.apache.wss4j.dom.engine.WSSecurityEngineResult;
import org.apache.wss4j.dom.handler.RequestData;
import org.apache.wss4j.dom.message.token.KerberosSecurity;
import org.apache.wss4j.dom.processor.Processor;
import org.apache.wss4j.dom.util.WSSecurityUtil;
import org.apache.wss4j.dom.validate.Credential;
import org.apache.wss4j.dom.validate.Validator;
import org.w3c.dom.Element;

public class BinarySecurityTokenProcessor
implements Processor {
    @Override
    public List<WSSecurityEngineResult> handleToken(Element elem, RequestData data) throws WSSecurityException {
        String id = elem.getAttributeNS("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd", "Id");
        if (!"".equals(id)) {
            Element foundElement = data.getWsDocInfo().getTokenElement(id);
            if (elem.equals(foundElement)) {
                WSSecurityEngineResult result = data.getWsDocInfo().getResult(id);
                return Collections.singletonList(result);
            }
            if (foundElement != null) {
                throw new WSSecurityException(WSSecurityException.ErrorCode.INVALID_SECURITY_TOKEN, "duplicateError");
            }
        }
        BinarySecurity token = this.createSecurityToken(elem, data);
        X509Certificate[] certs = null;
        Validator validator = data.getValidator(new QName(elem.getNamespaceURI(), elem.getLocalName()));
        certs = data.getSigVerCrypto() == null ? this.getCertificatesTokenReference(token, data.getDecCrypto()) : this.getCertificatesTokenReference(token, data.getSigVerCrypto());
        WSSecurityEngineResult result = new WSSecurityEngineResult(4096, token, certs);
        data.getWsDocInfo().addTokenElement(elem);
        if (!"".equals(id)) {
            result.put("id", id);
        }
        if (validator != null) {
            Credential credential = new Credential();
            credential.setBinarySecurityToken(token);
            credential.setCertificates(certs);
            Credential returnedCredential = validator.validate(credential, data);
            result.put("validated-token", Boolean.TRUE);
            result.put("secret", returnedCredential.getSecretKey());
            if (returnedCredential.getTransformedToken() != null) {
                result.put("transformed-token", returnedCredential.getTransformedToken());
                if (credential.getPrincipal() != null) {
                    result.put("principal", credential.getPrincipal());
                } else {
                    SAMLTokenPrincipalImpl samlPrincipal = new SAMLTokenPrincipalImpl(credential.getTransformedToken());
                    result.put("principal", samlPrincipal);
                }
            } else if (credential.getPrincipal() != null) {
                result.put("principal", credential.getPrincipal());
            } else if (certs != null && certs[0] != null) {
                result.put("principal", certs[0].getSubjectX500Principal());
            }
            result.put("subject", credential.getSubject());
            if (credential.getDelegationCredential() != null) {
                result.put("delegation-credential", credential.getDelegationCredential());
            }
        }
        data.getWsDocInfo().addResult(result);
        return Collections.singletonList(result);
    }

    private X509Certificate[] getCertificatesTokenReference(BinarySecurity token, Crypto crypto) throws WSSecurityException {
        if (token instanceof PKIPathSecurity) {
            return ((PKIPathSecurity)token).getX509Certificates(crypto);
        }
        if (token instanceof X509Security) {
            X509Certificate cert = ((X509Security)token).getX509Certificate(crypto);
            return new X509Certificate[]{cert};
        }
        return null;
    }

    private BinarySecurity createSecurityToken(Element element, RequestData data) throws WSSecurityException {
        String xopUri;
        String type = element.getAttributeNS(null, "ValueType");
        BinarySecurity token = null;
        token = "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-x509-token-profile-1.0#X509v3".equals(type) ? new X509Security(element, data.getBSPEnforcer()) : (PKIPathSecurity.getType().equals(type) ? new PKIPathSecurity(element, data.getBSPEnforcer()) : (KerberosSecurity.isKerberosToken(type) ? new KerberosSecurity(element, data.getBSPEnforcer()) : new BinarySecurity(element, data.getBSPEnforcer())));
        Element elementChild = XMLUtils.getDirectChildElement(element, "Include", "http://www.w3.org/2004/08/xop/include");
        if (elementChild != null && elementChild.hasAttributeNS(null, "href") && (xopUri = elementChild.getAttributeNS(null, "href")) != null && xopUri.startsWith("cid:")) {
            byte[] content = WSSecurityUtil.getBytesFromAttachment(xopUri, data);
            token.setRawToken(content);
        }
        return token;
    }
}

