/*
 * Decompiled with CFR 0.152.
 */
package org.apache.wss4j.stax.validate;

import java.io.Serializable;
import java.util.Base64;
import javax.xml.bind.JAXBElement;
import org.apache.wss4j.binding.wss10.BinarySecurityTokenType;
import org.apache.wss4j.common.crypto.Crypto;
import org.apache.wss4j.common.ext.WSSecurityException;
import org.apache.wss4j.common.util.AttachmentUtils;
import org.apache.wss4j.stax.ext.WSSConfigurationException;
import org.apache.wss4j.stax.ext.WSSSecurityProperties;
import org.apache.wss4j.stax.impl.securityToken.KerberosServiceSecurityTokenImpl;
import org.apache.wss4j.stax.impl.securityToken.X509PKIPathv1SecurityTokenImpl;
import org.apache.wss4j.stax.impl.securityToken.X509V3SecurityTokenImpl;
import org.apache.wss4j.stax.securityToken.WSSecurityTokenConstants;
import org.apache.wss4j.stax.validate.BinarySecurityTokenValidator;
import org.apache.wss4j.stax.validate.TokenContext;
import org.apache.xml.security.binding.xop.Include;
import org.apache.xml.security.exceptions.XMLSecurityException;
import org.apache.xml.security.stax.ext.XMLSecurityConstants;
import org.apache.xml.security.stax.securityToken.InboundSecurityToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BinarySecurityTokenValidatorImpl
implements BinarySecurityTokenValidator {
    private static final transient Logger LOG = LoggerFactory.getLogger(BinarySecurityTokenValidatorImpl.class);

    @Override
    public InboundSecurityToken validate(BinarySecurityTokenType binarySecurityTokenType, TokenContext tokenContext) throws WSSecurityException {
        if (!"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-soap-message-security-1.0#Base64Binary".equals(binarySecurityTokenType.getEncodingType())) {
            throw new WSSecurityException(WSSecurityException.ErrorCode.INVALID_SECURITY_TOKEN, "badEncoding", new Object[]{binarySecurityTokenType.getEncodingType()});
        }
        try {
            byte[] securityTokenData = this.getBinarySecurityTokenBytes(binarySecurityTokenType, tokenContext.getWssSecurityProperties());
            if ("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-x509-token-profile-1.0#X509v3".equals(binarySecurityTokenType.getValueType())) {
                Crypto crypto = this.getCrypto(tokenContext.getWssSecurityProperties());
                X509V3SecurityTokenImpl x509V3SecurityToken = new X509V3SecurityTokenImpl(tokenContext.getWsSecurityContext(), crypto, tokenContext.getWssSecurityProperties().getCallbackHandler(), securityTokenData, binarySecurityTokenType.getId(), tokenContext.getWssSecurityProperties());
                x509V3SecurityToken.setElementPath(tokenContext.getElementPath());
                x509V3SecurityToken.setXMLSecEvent(tokenContext.getFirstXMLSecEvent());
                return x509V3SecurityToken;
            }
            if ("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-x509-token-profile-1.0#X509PKIPathv1".equals(binarySecurityTokenType.getValueType())) {
                Crypto crypto = this.getCrypto(tokenContext.getWssSecurityProperties());
                X509PKIPathv1SecurityTokenImpl x509PKIPathv1SecurityToken = new X509PKIPathv1SecurityTokenImpl(tokenContext.getWsSecurityContext(), crypto, tokenContext.getWssSecurityProperties().getCallbackHandler(), securityTokenData, binarySecurityTokenType.getId(), WSSecurityTokenConstants.KEYIDENTIFIER_SECURITY_TOKEN_DIRECT_REFERENCE, tokenContext.getWssSecurityProperties());
                x509PKIPathv1SecurityToken.setElementPath(tokenContext.getElementPath());
                x509PKIPathv1SecurityToken.setXMLSecEvent(tokenContext.getFirstXMLSecEvent());
                return x509PKIPathv1SecurityToken;
            }
            if ("http://docs.oasis-open.org/wss/oasis-wss-kerberos-token-profile-1.1#GSS_Kerberosv5_AP_REQ".equals(binarySecurityTokenType.getValueType())) {
                KerberosServiceSecurityTokenImpl kerberosServiceSecurityToken = new KerberosServiceSecurityTokenImpl(tokenContext.getWsSecurityContext(), tokenContext.getWssSecurityProperties().getCallbackHandler(), securityTokenData, binarySecurityTokenType.getValueType(), binarySecurityTokenType.getId(), WSSecurityTokenConstants.KEYIDENTIFIER_SECURITY_TOKEN_DIRECT_REFERENCE);
                kerberosServiceSecurityToken.setElementPath(tokenContext.getElementPath());
                kerberosServiceSecurityToken.setXMLSecEvent(tokenContext.getFirstXMLSecEvent());
                return kerberosServiceSecurityToken;
            }
            throw new WSSecurityException(WSSecurityException.ErrorCode.INVALID_SECURITY_TOKEN, "invalidValueType", new Object[]{binarySecurityTokenType.getValueType()});
        }
        catch (XMLSecurityException e) {
            throw new WSSecurityException(WSSecurityException.ErrorCode.INVALID_SECURITY_TOKEN, (Exception)e);
        }
    }

    private byte[] getBinarySecurityTokenBytes(BinarySecurityTokenType binarySecurityTokenType, WSSSecurityProperties wssSecurityProperties) throws XMLSecurityException {
        StringBuilder sb = new StringBuilder();
        for (Serializable obj : binarySecurityTokenType.getContent()) {
            Include include;
            JAXBElement element;
            if (obj instanceof String) {
                sb.append((String)((Object)obj));
                continue;
            }
            if (!(obj instanceof JAXBElement) || !XMLSecurityConstants.TAG_XOP_INCLUDE.equals((element = (JAXBElement)obj).getName()) || (include = (Include)element.getValue()) == null || include.getHref() == null || !include.getHref().startsWith("cid:")) continue;
            return AttachmentUtils.getBytesFromAttachment(include.getHref(), wssSecurityProperties.getAttachmentCallbackHandler(), true);
        }
        return Base64.getMimeDecoder().decode(sb.toString());
    }

    protected Crypto getCrypto(WSSSecurityProperties securityProperties) throws WSSConfigurationException {
        Crypto crypto = null;
        try {
            crypto = securityProperties.getSignatureVerificationCrypto();
        }
        catch (WSSConfigurationException e) {
            LOG.debug(e.getMessage(), e);
        }
        if (crypto == null) {
            crypto = securityProperties.getDecryptionCrypto();
        }
        return crypto;
    }
}

