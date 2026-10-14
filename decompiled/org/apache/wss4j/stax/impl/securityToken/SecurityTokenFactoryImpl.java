/*
 * Decompiled with CFR 0.152.
 */
package org.apache.wss4j.stax.impl.securityToken;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateEncodingException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.x500.X500Principal;
import org.apache.wss4j.binding.wss10.KeyIdentifierType;
import org.apache.wss4j.binding.wss10.ReferenceType;
import org.apache.wss4j.binding.wss10.SecurityTokenReferenceType;
import org.apache.wss4j.common.bsp.BSPRule;
import org.apache.wss4j.common.crypto.Crypto;
import org.apache.wss4j.common.ext.WSSecurityException;
import org.apache.wss4j.stax.ext.WSInboundSecurityContext;
import org.apache.wss4j.stax.ext.WSSConstants;
import org.apache.wss4j.stax.ext.WSSSecurityProperties;
import org.apache.wss4j.stax.impl.securityToken.DsaKeyValueSecurityTokenImpl;
import org.apache.wss4j.stax.impl.securityToken.ECKeyValueSecurityTokenImpl;
import org.apache.wss4j.stax.impl.securityToken.EncryptedKeySha1SecurityTokenImpl;
import org.apache.wss4j.stax.impl.securityToken.ExternalSecurityTokenImpl;
import org.apache.wss4j.stax.impl.securityToken.RsaKeyValueSecurityTokenImpl;
import org.apache.wss4j.stax.impl.securityToken.SamlSecurityTokenImpl;
import org.apache.wss4j.stax.impl.securityToken.X509DefaultSecurityTokenImpl;
import org.apache.wss4j.stax.impl.securityToken.X509IssuerSerialTokenImpl;
import org.apache.wss4j.stax.impl.securityToken.X509PKIPathv1SecurityTokenImpl;
import org.apache.wss4j.stax.impl.securityToken.X509SKISecurityTokenImpl;
import org.apache.wss4j.stax.impl.securityToken.X509ThumbprintSHA1SecurityTokenImpl;
import org.apache.wss4j.stax.impl.securityToken.X509V3SecurityTokenImpl;
import org.apache.wss4j.stax.securityToken.KerberosServiceSecurityToken;
import org.apache.wss4j.stax.securityToken.SamlSecurityToken;
import org.apache.wss4j.stax.securityToken.SecurityTokenReference;
import org.apache.wss4j.stax.securityToken.UsernameSecurityToken;
import org.apache.wss4j.stax.securityToken.WSSecurityTokenConstants;
import org.apache.wss4j.stax.securityToken.X509SecurityToken;
import org.apache.wss4j.stax.utils.WSSUtils;
import org.apache.xml.security.binding.xmldsig.DSAKeyValueType;
import org.apache.xml.security.binding.xmldsig.KeyInfoType;
import org.apache.xml.security.binding.xmldsig.KeyValueType;
import org.apache.xml.security.binding.xmldsig.RSAKeyValueType;
import org.apache.xml.security.binding.xmldsig.X509DataType;
import org.apache.xml.security.binding.xmldsig.X509IssuerSerialType;
import org.apache.xml.security.binding.xmldsig11.ECKeyValueType;
import org.apache.xml.security.exceptions.XMLSecurityException;
import org.apache.xml.security.stax.ext.InboundSecurityContext;
import org.apache.xml.security.stax.ext.SecurityContext;
import org.apache.xml.security.stax.ext.XMLSecurityConstants;
import org.apache.xml.security.stax.ext.XMLSecurityProperties;
import org.apache.xml.security.stax.ext.XMLSecurityUtils;
import org.apache.xml.security.stax.impl.util.IDGenerator;
import org.apache.xml.security.stax.securityToken.InboundSecurityToken;
import org.apache.xml.security.stax.securityToken.SecurityTokenConstants;
import org.apache.xml.security.stax.securityToken.SecurityTokenFactory;
import org.apache.xml.security.stax.securityToken.SecurityTokenProvider;
import org.apache.xml.security.utils.XMLUtils;

public class SecurityTokenFactoryImpl
extends SecurityTokenFactory {
    @Override
    public InboundSecurityToken getSecurityToken(KeyInfoType keyInfoType, SecurityTokenConstants.KeyUsage keyInfoUsage, XMLSecurityProperties securityProperties, InboundSecurityContext inboundSecurityContext) throws XMLSecurityException {
        Crypto crypto = null;
        if (WSSecurityTokenConstants.KeyUsage_Signature_Verification.equals(keyInfoUsage)) {
            crypto = ((WSSSecurityProperties)securityProperties).getSignatureVerificationCrypto();
        } else if (WSSecurityTokenConstants.KeyUsage_Decryption.equals(keyInfoUsage)) {
            crypto = ((WSSSecurityProperties)securityProperties).getDecryptionCrypto();
        }
        if (keyInfoType != null) {
            SecurityTokenReferenceType securityTokenReferenceType = (SecurityTokenReferenceType)XMLSecurityUtils.getQNameType(keyInfoType.getContent(), WSSConstants.TAG_WSSE_SECURITY_TOKEN_REFERENCE);
            if (securityTokenReferenceType != null) {
                return SecurityTokenFactoryImpl.getSecurityToken(securityTokenReferenceType, crypto, ((WSSSecurityProperties)securityProperties).getCallbackHandler(), inboundSecurityContext, (WSSSecurityProperties)securityProperties);
            }
            KeyValueType keyValueType = (KeyValueType)XMLSecurityUtils.getQNameType(keyInfoType.getContent(), WSSConstants.TAG_dsig_KeyValue);
            if (keyValueType != null) {
                return SecurityTokenFactoryImpl.getSecurityToken(keyValueType, crypto, ((WSSSecurityProperties)securityProperties).getCallbackHandler(), (SecurityContext)inboundSecurityContext, (WSSSecurityProperties)securityProperties);
            }
        } else if (crypto != null && crypto.getDefaultX509Identifier() != null) {
            return new X509DefaultSecurityTokenImpl((WSInboundSecurityContext)inboundSecurityContext, crypto, ((WSSSecurityProperties)securityProperties).getCallbackHandler(), crypto.getDefaultX509Identifier(), crypto.getDefaultX509Identifier(), WSSecurityTokenConstants.KeyIdentifier_NoKeyInfo, (WSSSecurityProperties)securityProperties);
        }
        throw new WSSecurityException(WSSecurityException.ErrorCode.INVALID_SECURITY, "noKeyinfo");
    }

    public static InboundSecurityToken getSecurityToken(SecurityTokenReferenceType securityTokenReferenceType, Crypto crypto, CallbackHandler callbackHandler, InboundSecurityContext inboundSecurityContext, WSSSecurityProperties securityProperties) throws XMLSecurityException {
        try {
            InboundSecurityToken securityToken;
            InboundSecurityToken securityToken2;
            X509DataType x509DataType;
            if (securityTokenReferenceType == null) {
                throw new WSSecurityException(WSSecurityException.ErrorCode.INVALID_SECURITY, "noSecTokRef");
            }
            if (securityTokenReferenceType.getAny().size() > 1) {
                ((WSInboundSecurityContext)inboundSecurityContext).handleBSPRule(BSPRule.R3061);
            }
            if (securityTokenReferenceType.getId() == null) {
                securityTokenReferenceType.setId(IDGenerator.generateID(null));
            }
            if ((x509DataType = (X509DataType)XMLSecurityUtils.getQNameType(securityTokenReferenceType.getAny(), WSSConstants.TAG_dsig_X509Data)) != null && (securityToken2 = SecurityTokenFactoryImpl.getSecurityToken(x509DataType, securityTokenReferenceType.getId(), crypto, callbackHandler, inboundSecurityContext, securityProperties)) != null) {
                InboundSecurityToken inboundSecurityToken = securityToken2;
                return inboundSecurityToken;
            }
            String tokenType = XMLSecurityUtils.getQNameAttribute(securityTokenReferenceType.getOtherAttributes(), WSSConstants.ATT_WSSE11_TOKEN_TYPE);
            KeyIdentifierType keyIdentifierType = (KeyIdentifierType)XMLSecurityUtils.getQNameType(securityTokenReferenceType.getAny(), WSSConstants.TAG_WSSE_KEY_IDENTIFIER);
            if (keyIdentifierType != null && (securityToken = SecurityTokenFactoryImpl.getSecurityToken(keyIdentifierType, securityTokenReferenceType.getId(), tokenType, crypto, callbackHandler, inboundSecurityContext, securityProperties)) != null) {
                InboundSecurityToken inboundSecurityToken = securityToken;
                return inboundSecurityToken;
            }
            ReferenceType referenceType = (ReferenceType)XMLSecurityUtils.getQNameType(securityTokenReferenceType.getAny(), WSSConstants.TAG_WSSE_REFERENCE);
            if (referenceType != null) {
                InboundSecurityToken inboundSecurityToken = SecurityTokenFactoryImpl.getSecurityToken(referenceType, tokenType, inboundSecurityContext, securityProperties);
                return inboundSecurityToken;
            }
            throw new WSSecurityException(WSSecurityException.ErrorCode.INVALID_SECURITY, "noKeyinfo");
        }
        finally {
            inboundSecurityContext.remove("" + Thread.currentThread().hashCode());
        }
    }

    private static InboundSecurityToken getSecurityToken(X509DataType x509DataType, String securityTokenReferenceId, Crypto crypto, CallbackHandler callbackHandler, InboundSecurityContext inboundSecurityContext, WSSSecurityProperties securityProperties) throws XMLSecurityException {
        X509IssuerSerialType x509IssuerSerialType = (X509IssuerSerialType)XMLSecurityUtils.getQNameType(x509DataType.getX509IssuerSerialOrX509SKIOrX509SubjectName(), WSSConstants.TAG_dsig_X509IssuerSerial);
        if (x509IssuerSerialType != null) {
            List<SecurityTokenProvider<? extends InboundSecurityToken>> securityTokenProviders = inboundSecurityContext.getRegisteredSecurityTokenProviders();
            for (int i = 0; i < securityTokenProviders.size(); ++i) {
                SecurityTokenProvider<? extends InboundSecurityToken> tokenProvider = securityTokenProviders.get(i);
                InboundSecurityToken inboundSecurityToken = tokenProvider.getSecurityToken();
                if (!(inboundSecurityToken instanceof X509SecurityToken)) continue;
                X509SecurityToken x509SecurityToken = (X509SecurityToken)((Object)inboundSecurityToken);
                X509Certificate x509Certificate = x509SecurityToken.getX509Certificates()[0];
                X500Principal principal = new X500Principal(x509IssuerSerialType.getX509IssuerName());
                if (x509Certificate.getSerialNumber().compareTo(x509IssuerSerialType.getX509SerialNumber()) != 0 || !x509Certificate.getIssuerX500Principal().equals(principal)) continue;
                return SecurityTokenFactoryImpl.createSecurityTokenProxy(inboundSecurityToken, WSSecurityTokenConstants.KeyIdentifier_IssuerSerial);
            }
            return new X509IssuerSerialTokenImpl((WSInboundSecurityContext)inboundSecurityContext, crypto, callbackHandler, x509IssuerSerialType, securityTokenReferenceId, securityProperties);
        }
        byte[] skiBytes = (byte[])XMLSecurityUtils.getQNameType(x509DataType.getX509IssuerSerialOrX509SKIOrX509SubjectName(), XMLSecurityConstants.TAG_dsig_X509SKI);
        if (skiBytes != null) {
            return new X509SKISecurityTokenImpl((WSInboundSecurityContext)inboundSecurityContext, crypto, callbackHandler, skiBytes, securityTokenReferenceId, securityProperties);
        }
        byte[] x509CertificateBytes = (byte[])XMLSecurityUtils.getQNameType(x509DataType.getX509IssuerSerialOrX509SKIOrX509SubjectName(), WSSConstants.TAG_dsig_X509Certificate);
        if (x509CertificateBytes != null) {
            return new X509V3SecurityTokenImpl((WSInboundSecurityContext)inboundSecurityContext, crypto, callbackHandler, x509CertificateBytes, securityTokenReferenceId, securityProperties);
        }
        return null;
    }

    private static InboundSecurityToken getSecurityToken(KeyIdentifierType keyIdentifierType, String securityTokenReferenceId, String tokenType, Crypto crypto, CallbackHandler callbackHandler, InboundSecurityContext inboundSecurityContext, WSSSecurityProperties securityProperties) throws XMLSecurityException {
        String valueType = keyIdentifierType.getValueType();
        if (valueType == null) {
            ((WSInboundSecurityContext)inboundSecurityContext).handleBSPRule(BSPRule.R3054);
        }
        String encodingType = keyIdentifierType.getEncodingType();
        byte[] binaryContent = null;
        if ("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-soap-message-security-1.0#Base64Binary".equals(encodingType)) {
            binaryContent = XMLUtils.decode(keyIdentifierType.getValue());
        } else if (!"http://docs.oasis-open.org/wss/oasis-wss-saml-token-profile-1.0#SAMLAssertionID".equals(valueType) && !"http://docs.oasis-open.org/wss/oasis-wss-saml-token-profile-1.1#SAMLID".equals(valueType)) {
            if (encodingType == null) {
                ((WSInboundSecurityContext)inboundSecurityContext).handleBSPRule(BSPRule.R3070);
            } else {
                ((WSInboundSecurityContext)inboundSecurityContext).handleBSPRule(BSPRule.R3071);
            }
        } else if (encodingType != null && ("http://docs.oasis-open.org/wss/oasis-wss-saml-token-profile-1.0#SAMLAssertionID".equals(valueType) || "http://docs.oasis-open.org/wss/oasis-wss-saml-token-profile-1.1#SAMLID".equals(valueType))) {
            ((WSInboundSecurityContext)inboundSecurityContext).handleBSPRule(BSPRule.R6604);
        }
        if ("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-x509-token-profile-1.0#X509v3".equals(valueType)) {
            return new X509V3SecurityTokenImpl((WSInboundSecurityContext)inboundSecurityContext, crypto, callbackHandler, binaryContent, securityTokenReferenceId, securityProperties);
        }
        if ("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-x509-token-profile-1.0#X509SubjectKeyIdentifier".equals(valueType)) {
            return new X509SKISecurityTokenImpl((WSInboundSecurityContext)inboundSecurityContext, crypto, callbackHandler, binaryContent, securityTokenReferenceId, securityProperties);
        }
        if ("http://docs.oasis-open.org/wss/oasis-wss-soap-message-security-1.1#ThumbprintSHA1".equals(valueType)) {
            try {
                MessageDigest messageDigest = MessageDigest.getInstance("SHA-1");
                List<SecurityTokenProvider<? extends InboundSecurityToken>> securityTokenProviders = inboundSecurityContext.getRegisteredSecurityTokenProviders();
                for (int i = 0; i < securityTokenProviders.size(); ++i) {
                    X509SecurityToken x509SecurityToken;
                    byte[] tokenDigest;
                    SecurityTokenProvider<? extends InboundSecurityToken> tokenProvider = securityTokenProviders.get(i);
                    InboundSecurityToken inboundSecurityToken = tokenProvider.getSecurityToken();
                    if (!(inboundSecurityToken instanceof X509SecurityToken) || !Arrays.equals(tokenDigest = messageDigest.digest((x509SecurityToken = (X509SecurityToken)((Object)inboundSecurityToken)).getX509Certificates()[0].getEncoded()), binaryContent)) continue;
                    return SecurityTokenFactoryImpl.createSecurityTokenProxy(inboundSecurityToken, WSSecurityTokenConstants.KEYIDENTIFIER_THUMBPRINT_IDENTIFIER);
                }
            }
            catch (NoSuchAlgorithmException e) {
                throw new WSSecurityException(WSSecurityException.ErrorCode.FAILURE, (Exception)e);
            }
            catch (CertificateEncodingException e) {
                throw new WSSecurityException(WSSecurityException.ErrorCode.INVALID_SECURITY_TOKEN);
            }
            return new X509ThumbprintSHA1SecurityTokenImpl((WSInboundSecurityContext)inboundSecurityContext, crypto, callbackHandler, binaryContent, securityTokenReferenceId, securityProperties);
        }
        if ("http://docs.oasis-open.org/wss/oasis-wss-soap-message-security-1.1#EncryptedKeySHA1".equals(valueType)) {
            return new EncryptedKeySha1SecurityTokenImpl((WSInboundSecurityContext)inboundSecurityContext, callbackHandler, keyIdentifierType.getValue(), securityTokenReferenceId);
        }
        if ("http://docs.oasis-open.org/wss/oasis-wss-saml-token-profile-1.0#SAMLAssertionID".equals(valueType) || "http://docs.oasis-open.org/wss/oasis-wss-saml-token-profile-1.1#SAMLID".equals(valueType)) {
            if ("http://docs.oasis-open.org/wss/oasis-wss-saml-token-profile-1.1#SAMLID".equals(valueType) && !"http://docs.oasis-open.org/wss/oasis-wss-saml-token-profile-1.1#SAMLV2.0".equals(tokenType)) {
                ((WSInboundSecurityContext)inboundSecurityContext).handleBSPRule(BSPRule.R6617);
            } else if ("http://docs.oasis-open.org/wss/oasis-wss-saml-token-profile-1.0#SAMLAssertionID".equals(valueType) && !"http://docs.oasis-open.org/wss/oasis-wss-saml-token-profile-1.1#SAMLV1.1".equals(tokenType)) {
                ((WSInboundSecurityContext)inboundSecurityContext).handleBSPRule(BSPRule.R6611);
            }
            SecurityTokenProvider<? extends InboundSecurityToken> securityTokenProvider = inboundSecurityContext.getSecurityTokenProvider(keyIdentifierType.getValue());
            if (securityTokenProvider != null) {
                return SecurityTokenFactoryImpl.createSecurityTokenProxy(securityTokenProvider.getSecurityToken(), WSSecurityTokenConstants.KEYIDENTIFIER_SECURITY_TOKEN_DIRECT_REFERENCE);
            }
            return new SamlSecurityTokenImpl((WSInboundSecurityContext)inboundSecurityContext, keyIdentifierType.getValue(), WSSecurityTokenConstants.KEYIDENTIFIER_EXTERNAL_REFERENCE, securityProperties);
        }
        if ("http://docs.oasis-open.org/wss/oasis-wss-kerberos-token-profile-1.1#Kerberosv5APREQSHA1".equals(valueType)) {
            SecurityTokenProvider<? extends InboundSecurityToken> securityTokenProvider = inboundSecurityContext.getSecurityTokenProvider(keyIdentifierType.getValue());
            if (securityTokenProvider != null) {
                return SecurityTokenFactoryImpl.createSecurityTokenProxy(securityTokenProvider.getSecurityToken(), WSSecurityTokenConstants.KEYIDENTIFIER_SECURITY_TOKEN_DIRECT_REFERENCE);
            }
            try {
                MessageDigest messageDigest = MessageDigest.getInstance("SHA-1");
                List<SecurityTokenProvider<? extends InboundSecurityToken>> securityTokenProviders = inboundSecurityContext.getRegisteredSecurityTokenProviders();
                for (int i = 0; i < securityTokenProviders.size(); ++i) {
                    KerberosServiceSecurityToken kerberosSecurityToken;
                    byte[] tokenDigest;
                    SecurityTokenProvider<? extends InboundSecurityToken> tokenProvider = securityTokenProviders.get(i);
                    InboundSecurityToken inboundSecurityToken = tokenProvider.getSecurityToken();
                    if (!(inboundSecurityToken instanceof KerberosServiceSecurityToken) || !Arrays.equals(tokenDigest = messageDigest.digest((kerberosSecurityToken = (KerberosServiceSecurityToken)((Object)inboundSecurityToken)).getBinaryContent()), binaryContent)) continue;
                    return SecurityTokenFactoryImpl.createSecurityTokenProxy(inboundSecurityToken, WSSecurityTokenConstants.KEYIDENTIFIER_THUMBPRINT_IDENTIFIER);
                }
            }
            catch (NoSuchAlgorithmException e) {
                throw new WSSecurityException(WSSecurityException.ErrorCode.FAILURE, (Exception)e);
            }
            return new EncryptedKeySha1SecurityTokenImpl((WSInboundSecurityContext)inboundSecurityContext, callbackHandler, keyIdentifierType.getValue(), securityTokenReferenceId);
        }
        ((WSInboundSecurityContext)inboundSecurityContext).handleBSPRule(BSPRule.R3063);
        return null;
    }

    private static InboundSecurityToken getSecurityToken(ReferenceType referenceType, String tokenType, InboundSecurityContext inboundSecurityContext, WSSSecurityProperties securityProperties) throws XMLSecurityException {
        String valueType;
        String uri = referenceType.getURI();
        if (uri == null) {
            ((WSInboundSecurityContext)inboundSecurityContext).handleBSPRule(BSPRule.R3062);
            throw new WSSecurityException(WSSecurityException.ErrorCode.INVALID_SECURITY, "badReferenceURI");
        }
        boolean included = true;
        if (!uri.startsWith("#")) {
            included = false;
            try {
                return new ExternalSecurityTokenImpl((WSInboundSecurityContext)inboundSecurityContext, uri, WSSecurityTokenConstants.KEYIDENTIFIER_EXTERNAL_REFERENCE, securityProperties, false);
            }
            catch (WSSecurityException wSSecurityException) {
                ((WSInboundSecurityContext)inboundSecurityContext).handleBSPRule(BSPRule.R5204);
            }
        }
        uri = WSSUtils.dropReferenceMarker(uri);
        Integer invokeCount = (Integer)inboundSecurityContext.get("" + Thread.currentThread().hashCode());
        if (invokeCount == null) {
            invokeCount = 0;
        }
        Integer n = invokeCount;
        Integer n2 = invokeCount = Integer.valueOf(invokeCount + 1);
        if (invokeCount == 10) {
            throw new WSSecurityException(WSSecurityException.ErrorCode.INVALID_SECURITY_TOKEN);
        }
        inboundSecurityContext.put("" + Thread.currentThread().hashCode(), invokeCount);
        SecurityTokenProvider<? extends InboundSecurityToken> securityTokenProvider = inboundSecurityContext.getSecurityTokenProvider(uri);
        if (securityTokenProvider == null) {
            return new ExternalSecurityTokenImpl((WSInboundSecurityContext)inboundSecurityContext, uri, WSSecurityTokenConstants.KEYIDENTIFIER_EXTERNAL_REFERENCE, securityProperties, included);
        }
        if (securityTokenProvider.getSecurityToken() instanceof SecurityTokenReference) {
            ((WSInboundSecurityContext)inboundSecurityContext).handleBSPRule(BSPRule.R3057);
        } else if (securityTokenProvider.getSecurityToken() instanceof X509PKIPathv1SecurityTokenImpl) {
            valueType = referenceType.getValueType();
            if (!"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-x509-token-profile-1.0#X509PKIPathv1".equals(valueType)) {
                ((WSInboundSecurityContext)inboundSecurityContext).handleBSPRule(BSPRule.R3058);
            }
            if (!"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-x509-token-profile-1.0#X509PKIPathv1".equals(tokenType)) {
                ((WSInboundSecurityContext)inboundSecurityContext).handleBSPRule(BSPRule.R5215);
            }
        } else if (securityTokenProvider.getSecurityToken() instanceof X509SecurityToken) {
            valueType = referenceType.getValueType();
            if (!"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-x509-token-profile-1.0#X509v3".equals(valueType)) {
                ((WSInboundSecurityContext)inboundSecurityContext).handleBSPRule(BSPRule.R3058);
            }
        } else if (securityTokenProvider.getSecurityToken() instanceof UsernameSecurityToken) {
            valueType = referenceType.getValueType();
            if (!"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0#UsernameToken".equals(valueType)) {
                ((WSInboundSecurityContext)inboundSecurityContext).handleBSPRule(BSPRule.R4214);
            }
        } else if (securityTokenProvider.getSecurityToken() instanceof SamlSecurityToken) {
            SecurityTokenConstants.TokenType samlTokenType = securityTokenProvider.getSecurityToken().getTokenType();
            if (WSSecurityTokenConstants.SAML_20_TOKEN.equals(samlTokenType)) {
                String valueType2 = referenceType.getValueType();
                if (valueType2 != null && !"".equals(valueType2)) {
                    ((WSInboundSecurityContext)inboundSecurityContext).handleBSPRule(BSPRule.R6614);
                }
                if (!"http://docs.oasis-open.org/wss/oasis-wss-saml-token-profile-1.1#SAMLV2.0".equals(tokenType)) {
                    ((WSInboundSecurityContext)inboundSecurityContext).handleBSPRule(BSPRule.R6617);
                }
            } else if (WSSecurityTokenConstants.SAML_10_TOKEN.equals(samlTokenType) && !"http://docs.oasis-open.org/wss/oasis-wss-saml-token-profile-1.1#SAMLV1.1".equals(tokenType)) {
                ((WSInboundSecurityContext)inboundSecurityContext).handleBSPRule(BSPRule.R6611);
            }
        }
        return SecurityTokenFactoryImpl.createSecurityTokenProxy(securityTokenProvider.getSecurityToken(), WSSecurityTokenConstants.KEYIDENTIFIER_SECURITY_TOKEN_DIRECT_REFERENCE);
    }

    public static InboundSecurityToken getSecurityToken(KeyValueType keyValueType, Crypto crypto, CallbackHandler callbackHandler, SecurityContext securityContext, WSSSecurityProperties securityProperties) throws XMLSecurityException {
        RSAKeyValueType rsaKeyValueType = (RSAKeyValueType)XMLSecurityUtils.getQNameType(keyValueType.getContent(), WSSConstants.TAG_dsig_RSAKeyValue);
        if (rsaKeyValueType != null) {
            return new RsaKeyValueSecurityTokenImpl(rsaKeyValueType, (WSInboundSecurityContext)securityContext, crypto, callbackHandler, securityProperties);
        }
        DSAKeyValueType dsaKeyValueType = (DSAKeyValueType)XMLSecurityUtils.getQNameType(keyValueType.getContent(), WSSConstants.TAG_dsig_DSAKeyValue);
        if (dsaKeyValueType != null) {
            return new DsaKeyValueSecurityTokenImpl(dsaKeyValueType, (WSInboundSecurityContext)securityContext, crypto, callbackHandler, securityProperties);
        }
        ECKeyValueType ecKeyValueType = (ECKeyValueType)XMLSecurityUtils.getQNameType(keyValueType.getContent(), WSSConstants.TAG_dsig11_ECKeyValue);
        if (ecKeyValueType != null) {
            return new ECKeyValueSecurityTokenImpl(ecKeyValueType, (WSInboundSecurityContext)securityContext, crypto, callbackHandler, securityProperties);
        }
        throw new WSSecurityException(WSSecurityException.ErrorCode.INVALID_SECURITY, "unsupportedKeyInfo");
    }

    private static InboundSecurityToken createSecurityTokenProxy(final InboundSecurityToken inboundSecurityToken, final SecurityTokenConstants.KeyIdentifier keyIdentifier) {
        ArrayList implementedInterfaces = new ArrayList();
        SecurityTokenFactoryImpl.getImplementedInterfaces(inboundSecurityToken.getClass(), implementedInterfaces);
        Class[] interfaces = implementedInterfaces.toArray(new Class[implementedInterfaces.size()]);
        return (InboundSecurityToken)Proxy.newProxyInstance(inboundSecurityToken.getClass().getClassLoader(), interfaces, new InvocationHandler(){

            @Override
            public Object invoke(Object proxy, Method method2, Object[] args2) throws Throwable {
                if (method2.getName().equals("getKeyIdentifier")) {
                    return keyIdentifier;
                }
                try {
                    return method2.invoke((Object)inboundSecurityToken, args2);
                }
                catch (InvocationTargetException e) {
                    throw e.getTargetException();
                }
            }
        });
    }

    private static void getImplementedInterfaces(Class<?> clazz, List<Class<?>> interfaceList) {
        if (clazz == null) {
            return;
        }
        Class<?>[] interfaces = clazz.getInterfaces();
        for (int i = 0; i < interfaces.length; ++i) {
            Class<?> anInterface = interfaces[i];
            if (!interfaceList.contains(anInterface)) {
                interfaceList.add(anInterface);
            }
            SecurityTokenFactoryImpl.getImplementedInterfaces(anInterface, interfaceList);
        }
        SecurityTokenFactoryImpl.getImplementedInterfaces(clazz.getSuperclass(), interfaceList);
    }
}

