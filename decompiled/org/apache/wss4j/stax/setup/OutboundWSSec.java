/*
 * Decompiled with CFR 0.152.
 */
package org.apache.wss4j.stax.setup;

import java.io.OutputStream;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.X509Certificate;
import java.util.List;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.stream.XMLStreamWriter;
import org.apache.wss4j.common.crypto.Crypto;
import org.apache.wss4j.common.crypto.CryptoType;
import org.apache.wss4j.common.ext.WSPasswordCallback;
import org.apache.wss4j.common.ext.WSSecurityException;
import org.apache.wss4j.stax.ext.WSSConstants;
import org.apache.wss4j.stax.ext.WSSSecurityProperties;
import org.apache.wss4j.stax.impl.processor.output.BinarySecurityTokenOutputProcessor;
import org.apache.wss4j.stax.impl.processor.output.CustomTokenOutputProcessor;
import org.apache.wss4j.stax.impl.processor.output.DerivedKeyTokenOutputProcessor;
import org.apache.wss4j.stax.impl.processor.output.EncryptEndingOutputProcessor;
import org.apache.wss4j.stax.impl.processor.output.EncryptOutputProcessor;
import org.apache.wss4j.stax.impl.processor.output.EncryptedKeyOutputProcessor;
import org.apache.wss4j.stax.impl.processor.output.ReferenceListOutputProcessor;
import org.apache.wss4j.stax.impl.processor.output.SAMLTokenOutputProcessor;
import org.apache.wss4j.stax.impl.processor.output.SecurityContextTokenOutputProcessor;
import org.apache.wss4j.stax.impl.processor.output.SecurityHeaderOutputProcessor;
import org.apache.wss4j.stax.impl.processor.output.SecurityHeaderReorderProcessor;
import org.apache.wss4j.stax.impl.processor.output.SignatureConfirmationOutputProcessor;
import org.apache.wss4j.stax.impl.processor.output.TimestampOutputProcessor;
import org.apache.wss4j.stax.impl.processor.output.UsernameTokenOutputProcessor;
import org.apache.wss4j.stax.impl.processor.output.WSSSignatureOutputProcessor;
import org.apache.wss4j.stax.impl.securityToken.KerberosClientSecurityToken;
import org.apache.wss4j.stax.securityToken.WSSecurityTokenConstants;
import org.apache.wss4j.stax.utils.WSSUtils;
import org.apache.xml.security.exceptions.XMLSecurityException;
import org.apache.xml.security.stax.config.JCEAlgorithmMapper;
import org.apache.xml.security.stax.ext.OutboundSecurityContext;
import org.apache.xml.security.stax.ext.OutputProcessor;
import org.apache.xml.security.stax.ext.SecurityContext;
import org.apache.xml.security.stax.ext.XMLSecurityConstants;
import org.apache.xml.security.stax.impl.DocumentContextImpl;
import org.apache.xml.security.stax.impl.OutboundSecurityContextImpl;
import org.apache.xml.security.stax.impl.OutputProcessorChainImpl;
import org.apache.xml.security.stax.impl.XMLSecurityStreamWriter;
import org.apache.xml.security.stax.impl.processor.output.FinalOutputProcessor;
import org.apache.xml.security.stax.impl.securityToken.GenericOutboundSecurityToken;
import org.apache.xml.security.stax.impl.util.IDGenerator;
import org.apache.xml.security.stax.securityEvent.SecurityEvent;
import org.apache.xml.security.stax.securityEvent.SecurityEventListener;
import org.apache.xml.security.stax.securityEvent.TokenSecurityEvent;
import org.apache.xml.security.stax.securityToken.OutboundSecurityToken;
import org.apache.xml.security.stax.securityToken.SecurityTokenConstants;
import org.apache.xml.security.stax.securityToken.SecurityTokenProvider;

public class OutboundWSSec {
    private final WSSSecurityProperties securityProperties;

    public OutboundWSSec(WSSSecurityProperties securityProperties) {
        this.securityProperties = securityProperties;
    }

    public XMLStreamWriter processOutMessage(OutputStream outputStream2, String encoding, List<SecurityEvent> requestSecurityEvents) throws WSSecurityException {
        return this.processOutMessage(outputStream2, encoding, requestSecurityEvents, null);
    }

    public XMLStreamWriter processOutMessage(XMLStreamWriter xmlStreamWriter, String encoding, List<SecurityEvent> requestSecurityEvents) throws WSSecurityException {
        return this.processOutMessage(xmlStreamWriter, encoding, requestSecurityEvents, null);
    }

    public XMLStreamWriter processOutMessage(OutputStream outputStream2, String encoding, List<SecurityEvent> requestSecurityEvents, SecurityEventListener securityEventListener) throws WSSecurityException {
        OutboundSecurityContextImpl outboundSecurityContext = new OutboundSecurityContextImpl();
        outboundSecurityContext.putList(SecurityEvent.class, requestSecurityEvents);
        outboundSecurityContext.addSecurityEventListener(securityEventListener);
        return this.processOutMessage((Object)outputStream2, encoding, (OutboundSecurityContext)outboundSecurityContext);
    }

    public XMLStreamWriter processOutMessage(XMLStreamWriter xmlStreamWriter, String encoding, List<SecurityEvent> requestSecurityEvents, SecurityEventListener securityEventListener) throws WSSecurityException {
        OutboundSecurityContextImpl outboundSecurityContext = new OutboundSecurityContextImpl();
        outboundSecurityContext.putList(SecurityEvent.class, requestSecurityEvents);
        outboundSecurityContext.addSecurityEventListener(securityEventListener);
        return this.processOutMessage((Object)xmlStreamWriter, encoding, (OutboundSecurityContext)outboundSecurityContext);
    }

    public XMLStreamWriter processOutMessage(XMLStreamWriter xmlStreamWriter, String encoding, OutboundSecurityContext outbounSecurityContext) throws WSSecurityException {
        return this.processOutMessage((Object)xmlStreamWriter, encoding, outbounSecurityContext);
    }

    public XMLStreamWriter processOutMessage(Object output, String encoding, OutboundSecurityContext outboundSecurityContext) throws WSSecurityException {
        OutputProcessorChainImpl outputProcessorChain;
        block10: {
            DocumentContextImpl documentContext = new DocumentContextImpl();
            documentContext.setEncoding(encoding);
            outputProcessorChain = new OutputProcessorChainImpl(outboundSecurityContext, documentContext);
            try {
                String id;
                SecurityHeaderOutputProcessor securityHeaderOutputProcessor = new SecurityHeaderOutputProcessor();
                this.initializeOutputProcessor(outputProcessorChain, securityHeaderOutputProcessor, null);
                ConfiguredAction configuredAction = this.configureActions(outputProcessorChain);
                if (configuredAction.signatureAction) {
                    this.setupSignatureKey(outputProcessorChain, this.securityProperties, configuredAction.signedSAML);
                }
                if (configuredAction.encryptionAction) {
                    this.setupEncryptionKey(outputProcessorChain, this.securityProperties);
                }
                if (configuredAction.kerberos) {
                    this.setupKerberosKey(outputProcessorChain, this.securityProperties, configuredAction.signatureKerberos, configuredAction.encryptionKerberos);
                }
                if (configuredAction.derivedSignature) {
                    id = (String)outputProcessorChain.getSecurityContext().get("PROP_USE_THIS_TOKEN_ID_FOR_SIGNATURE");
                    this.setDerivedIdentifier(outputProcessorChain, id);
                }
                if (configuredAction.derivedEncryption) {
                    id = (String)outputProcessorChain.getSecurityContext().get("PROP_USE_THIS_TOKEN_ID_FOR_ENCRYPTED_KEY");
                    if (id == null) {
                        id = (String)outputProcessorChain.getSecurityContext().get("PROP_USE_THIS_TOKEN_ID_FOR_ENCRYPTION");
                    }
                    this.setDerivedIdentifier(outputProcessorChain, id);
                }
                SecurityHeaderReorderProcessor securityHeaderReorderProcessor = new SecurityHeaderReorderProcessor();
                this.initializeOutputProcessor(outputProcessorChain, securityHeaderReorderProcessor, null);
                if (output instanceof OutputStream) {
                    FinalOutputProcessor finalOutputProcessor = new FinalOutputProcessor((OutputStream)output, encoding);
                    this.initializeOutputProcessor(outputProcessorChain, finalOutputProcessor, null);
                    break block10;
                }
                if (output instanceof XMLStreamWriter) {
                    FinalOutputProcessor finalOutputProcessor = new FinalOutputProcessor((XMLStreamWriter)output);
                    this.initializeOutputProcessor(outputProcessorChain, finalOutputProcessor, null);
                    break block10;
                }
                throw new IllegalArgumentException(output + " is not supported as output");
            }
            catch (XMLSecurityException e) {
                throw new WSSecurityException(WSSecurityException.ErrorCode.FAILURE, (Exception)e);
            }
        }
        return new XMLSecurityStreamWriter(outputProcessorChain);
    }

    private void initializeOutputProcessor(OutputProcessorChainImpl outputProcessorChain, OutputProcessor outputProcessor, XMLSecurityConstants.Action action) throws XMLSecurityException {
        outputProcessor.setXMLSecurityProperties(this.securityProperties);
        outputProcessor.setAction(action);
        outputProcessor.init(outputProcessorChain);
    }

    private void setupSignatureKey(OutputProcessorChainImpl outputProcessorChain, WSSSecurityProperties securityProperties, boolean signedSAML) throws XMLSecurityException {
        X509Certificate[] x509Certificates;
        Key key;
        block10: {
            String signatureAlgorithm = securityProperties.getSignatureAlgorithm();
            GenericOutboundSecurityToken securityToken = this.getOutboundSecurityToken(outputProcessorChain, "PROP_USE_THIS_TOKEN_ID_FOR_SIGNATURE");
            if (securityToken != null && signatureAlgorithm != null) {
                if (signatureAlgorithm.contains("hmac-sha") && securityToken.getSecretKey(signatureAlgorithm) != null) {
                    return;
                }
                if (!signatureAlgorithm.contains("hmac-sha") && securityToken.getX509Certificates() != null) {
                    if (securityToken.getSecretKey(signatureAlgorithm) != null) {
                        return;
                    }
                    PrivateKey key2 = securityProperties.getSignatureCrypto().getPrivateKey(securityToken.getX509Certificates()[0], securityProperties.getCallbackHandler());
                    securityToken.setSecretKey(signatureAlgorithm, key2);
                    return;
                }
            }
            String alias = securityProperties.getSignatureUser();
            WSPasswordCallback pwCb = new WSPasswordCallback(alias, 3);
            WSSUtils.doPasswordCallback(securityProperties.getCallbackHandler(), pwCb);
            String password = pwCb.getPassword();
            byte[] secretKey = pwCb.getKey();
            key = null;
            x509Certificates = null;
            try {
                if (password != null && securityProperties.getSignatureCrypto() != null) {
                    key = securityProperties.getSignatureCrypto().getPrivateKey(alias, password);
                    CryptoType cryptoType = new CryptoType(CryptoType.TYPE.ALIAS);
                    cryptoType.setAlias(alias);
                    x509Certificates = securityProperties.getSignatureCrypto().getX509Certificates(cryptoType);
                    if (x509Certificates == null || x509Certificates.length == 0) {
                        throw new WSSecurityException(WSSecurityException.ErrorCode.FAILED_SIGNATURE, "noUserCertsFound", new Object[]{alias});
                    }
                    break block10;
                }
                if (secretKey != null) {
                    x509Certificates = null;
                    String algoFamily = JCEAlgorithmMapper.getJCEKeyAlgorithmFromURI(signatureAlgorithm);
                    key = new SecretKeySpec(secretKey, algoFamily);
                    break block10;
                }
                throw new WSSecurityException(WSSecurityException.ErrorCode.FAILED_SIGNATURE, "noPassword", new Object[]{alias});
            }
            catch (WSSecurityException ex) {
                if (signedSAML && securityProperties.getSamlCallbackHandler() != null) {
                    return;
                }
                throw ex;
            }
        }
        final String id = IDGenerator.generateID(null);
        final GenericOutboundSecurityToken binarySecurityToken = new GenericOutboundSecurityToken(id, WSSecurityTokenConstants.X509V3Token, key, x509Certificates);
        SecurityTokenProvider<OutboundSecurityToken> binarySecurityTokenProvider = new SecurityTokenProvider<OutboundSecurityToken>(){

            @Override
            public OutboundSecurityToken getSecurityToken() throws WSSecurityException {
                return binarySecurityToken;
            }

            @Override
            public String getId() {
                return id;
            }
        };
        outputProcessorChain.getSecurityContext().registerSecurityTokenProvider(id, binarySecurityTokenProvider);
        outputProcessorChain.getSecurityContext().put("PROP_USE_THIS_TOKEN_ID_FOR_SIGNATURE", id);
    }

    private void setupEncryptionKey(OutputProcessorChainImpl outputProcessorChain, WSSSecurityProperties securityProperties) throws XMLSecurityException {
        String symmetricEncryptionAlgorithm = securityProperties.getEncryptionSymAlgorithm();
        GenericOutboundSecurityToken securityToken = this.getOutboundSecurityToken(outputProcessorChain, "PROP_USE_THIS_TOKEN_ID_FOR_ENCRYPTION");
        if (securityToken == null || securityToken.getSecretKey(symmetricEncryptionAlgorithm) == null) {
            GenericOutboundSecurityToken symmetricSecurityToken;
            KeyGenerator keyGen;
            String keyAlgorithm = JCEAlgorithmMapper.getJCEKeyAlgorithmFromURI(securityProperties.getEncryptionSymAlgorithm());
            try {
                keyGen = KeyGenerator.getInstance(keyAlgorithm);
            }
            catch (NoSuchAlgorithmException e) {
                throw new WSSecurityException(WSSecurityException.ErrorCode.FAILURE, (Exception)e);
            }
            if (keyAlgorithm.contains("AES")) {
                int keyLength = JCEAlgorithmMapper.getKeyLengthFromURI(securityProperties.getEncryptionSymAlgorithm());
                keyGen.init(keyLength);
            }
            SecretKey symmetricKey = keyGen.generateKey();
            final String symmId = IDGenerator.generateID(null);
            securityToken = symmetricSecurityToken = new GenericOutboundSecurityToken(symmId, WSSecurityTokenConstants.EncryptedKeyToken, symmetricKey);
            SecurityTokenProvider<OutboundSecurityToken> securityTokenProvider = new SecurityTokenProvider<OutboundSecurityToken>(){

                @Override
                public OutboundSecurityToken getSecurityToken() throws XMLSecurityException {
                    return symmetricSecurityToken;
                }

                @Override
                public String getId() {
                    return symmId;
                }
            };
            outputProcessorChain.getSecurityContext().registerSecurityTokenProvider(symmId, securityTokenProvider);
            outputProcessorChain.getSecurityContext().put("PROP_USE_THIS_TOKEN_ID_FOR_ENCRYPTION", symmId);
        }
        if (!securityProperties.isEncryptSymmetricEncryptionKey()) {
            return;
        }
        X509Certificate[] x509Certificates = null;
        PublicKey publicKey = null;
        if (securityProperties.isUseReqSigCertForEncryption()) {
            X509Certificate x509Certificate = this.getReqSigCert(outputProcessorChain.getSecurityContext());
            if (x509Certificate == null) {
                publicKey = this.getReqSigPublicKey(outputProcessorChain.getSecurityContext());
                if (publicKey == null) {
                    throw new WSSecurityException(WSSecurityException.ErrorCode.FAILED_ENCRYPTION, "noCert");
                }
            } else {
                x509Certificates = new X509Certificate[]{x509Certificate};
            }
        } else if (securityProperties.getEncryptionUseThisCertificate() != null) {
            x509Certificates = new X509Certificate[]{securityProperties.getEncryptionUseThisCertificate()};
        } else {
            CryptoType cryptoType = new CryptoType(CryptoType.TYPE.ALIAS);
            cryptoType.setAlias(securityProperties.getEncryptionUser());
            Crypto crypto = securityProperties.getEncryptionCrypto();
            x509Certificates = crypto.getX509Certificates(cryptoType);
            if (x509Certificates == null || x509Certificates.length == 0) {
                throw new WSSecurityException(WSSecurityException.ErrorCode.FAILED_ENCRYPTION, "noUserCertsFound", new Object[]{securityProperties.getEncryptionUser(), "encryption"});
            }
        }
        if (securityProperties.isEnableRevocation() && x509Certificates != null) {
            Crypto crypto = securityProperties.getEncryptionCrypto();
            crypto.verifyTrust(x509Certificates, true, null, null);
        }
        final String id = IDGenerator.generateID(null);
        final GenericOutboundSecurityToken encryptedKeyToken = new GenericOutboundSecurityToken(id, WSSecurityTokenConstants.X509V3Token, publicKey, x509Certificates);
        encryptedKeyToken.addWrappedToken(securityToken);
        securityToken.setKeyWrappingToken(encryptedKeyToken);
        SecurityTokenProvider<OutboundSecurityToken> encryptedKeyTokenProvider = new SecurityTokenProvider<OutboundSecurityToken>(){

            @Override
            public OutboundSecurityToken getSecurityToken() throws WSSecurityException {
                return encryptedKeyToken;
            }

            @Override
            public String getId() {
                return id;
            }
        };
        outputProcessorChain.getSecurityContext().registerSecurityTokenProvider(id, encryptedKeyTokenProvider);
        outputProcessorChain.getSecurityContext().put("PROP_USE_THIS_TOKEN_ID_FOR_ENCRYPTED_KEY", id);
    }

    private void setupKerberosKey(OutputProcessorChainImpl outputProcessorChain, WSSSecurityProperties securityProperties, boolean signature, boolean encryption) throws XMLSecurityException {
        GenericOutboundSecurityToken securityToken = this.getOutboundSecurityToken(outputProcessorChain, "PROP_USE_THIS_TOKEN_ID_FOR_KERBEROS");
        String kerberosId = null;
        if (securityToken == null) {
            String id;
            kerberosId = id = IDGenerator.generateID(null);
            final KerberosClientSecurityToken kerberosClientSecurityToken = new KerberosClientSecurityToken(securityProperties.getCallbackHandler(), id);
            SecurityTokenProvider<OutboundSecurityToken> kerberosSecurityTokenProvider = new SecurityTokenProvider<OutboundSecurityToken>(){

                @Override
                public OutboundSecurityToken getSecurityToken() throws WSSecurityException {
                    return kerberosClientSecurityToken;
                }

                @Override
                public String getId() {
                    return id;
                }
            };
            outputProcessorChain.getSecurityContext().registerSecurityTokenProvider(id, kerberosSecurityTokenProvider);
            outputProcessorChain.getSecurityContext().put("PROP_USE_THIS_TOKEN_ID_FOR_KERBEROS", id);
        } else {
            kerberosId = securityToken.getId();
        }
        if (signature) {
            outputProcessorChain.getSecurityContext().put("PROP_USE_THIS_TOKEN_ID_FOR_SIGNATURE", kerberosId);
        }
        if (encryption) {
            outputProcessorChain.getSecurityContext().put("PROP_USE_THIS_TOKEN_ID_FOR_ENCRYPTION", kerberosId);
        }
    }

    private GenericOutboundSecurityToken getOutboundSecurityToken(OutputProcessorChainImpl outputProcessorChain, String id) throws XMLSecurityException {
        String tokenId = (String)outputProcessorChain.getSecurityContext().get(id);
        SecurityTokenProvider<OutboundSecurityToken> signatureTokenProvider = null;
        if (tokenId != null && (signatureTokenProvider = outputProcessorChain.getSecurityContext().getSecurityTokenProvider(tokenId)) != null) {
            return (GenericOutboundSecurityToken)signatureTokenProvider.getSecurityToken();
        }
        return null;
    }

    private X509Certificate getReqSigCert(SecurityContext securityContext) throws XMLSecurityException {
        List securityEventList = securityContext.getAsList(SecurityEvent.class);
        if (securityEventList != null) {
            for (int i = 0; i < securityEventList.size(); ++i) {
                X509Certificate[] x509Certificates;
                SecurityEvent securityEvent = (SecurityEvent)securityEventList.get(i);
                if (!(securityEvent instanceof TokenSecurityEvent)) continue;
                TokenSecurityEvent tokenSecurityEvent = (TokenSecurityEvent)securityEvent;
                SecurityTokenConstants.TokenUsage mainSig = WSSecurityTokenConstants.TOKENUSAGE_MAIN_SIGNATURE;
                if (!tokenSecurityEvent.getSecurityToken().getTokenUsages().contains(mainSig) || (x509Certificates = tokenSecurityEvent.getSecurityToken().getX509Certificates()) == null || x509Certificates.length <= 0) continue;
                return x509Certificates[0];
            }
        }
        return null;
    }

    private PublicKey getReqSigPublicKey(SecurityContext securityContext) throws XMLSecurityException {
        List securityEventList = securityContext.getAsList(SecurityEvent.class);
        if (securityEventList != null) {
            for (int i = 0; i < securityEventList.size(); ++i) {
                PublicKey publicKey;
                SecurityEvent securityEvent = (SecurityEvent)securityEventList.get(i);
                if (!(securityEvent instanceof TokenSecurityEvent)) continue;
                TokenSecurityEvent tokenSecurityEvent = (TokenSecurityEvent)securityEvent;
                SecurityTokenConstants.TokenUsage mainSig = WSSecurityTokenConstants.TOKENUSAGE_MAIN_SIGNATURE;
                if (!tokenSecurityEvent.getSecurityToken().getTokenUsages().contains(mainSig) || (publicKey = tokenSecurityEvent.getSecurityToken().getPublicKey()) == null) continue;
                return publicKey;
            }
        }
        return null;
    }

    private void setDerivedIdentifier(OutputProcessorChainImpl outputProcessorChain, String id) {
        WSSConstants.DerivedKeyTokenReference derivedKeyTokenReference = this.securityProperties.getDerivedKeyTokenReference();
        switch (derivedKeyTokenReference) {
            case DirectReference: {
                outputProcessorChain.getSecurityContext().put("PROP_USE_THIS_TOKEN_ID_FOR_DERIVED_KEY", id);
                break;
            }
            case EncryptedKey: {
                String symmId = (String)outputProcessorChain.getSecurityContext().get("PROP_USE_THIS_TOKEN_ID_FOR_ENCRYPTION");
                outputProcessorChain.getSecurityContext().put("PROP_USE_THIS_TOKEN_ID_FOR_DERIVED_KEY", symmId);
                outputProcessorChain.getSecurityContext().put("PROP_USE_THIS_TOKEN_ID_FOR_ENCRYPTED_KEY", id);
                break;
            }
            case SecurityContextToken: {
                outputProcessorChain.getSecurityContext().put("PROP_USE_THIS_TOKEN_ID_FOR_SECURITYCONTEXTTOKEN", id);
            }
        }
    }

    private ConfiguredAction configureActions(OutputProcessorChainImpl outputProcessorChain) throws XMLSecurityException {
        ConfiguredAction configuredAction = new ConfiguredAction();
        boolean derivedSignatureButNotDerivedEncryption = false;
        if (this.securityProperties.getDerivedKeyTokenReference() == WSSConstants.DerivedKeyTokenReference.EncryptedKey) {
            for (XMLSecurityConstants.Action action : this.securityProperties.getActions()) {
                if (WSSConstants.SIGNATURE_WITH_DERIVED_KEY.equals(action)) {
                    derivedSignatureButNotDerivedEncryption = true;
                    continue;
                }
                if (!WSSConstants.ENCRYPT_WITH_DERIVED_KEY.equals(action)) continue;
                derivedSignatureButNotDerivedEncryption = false;
                break;
            }
        }
        for (XMLSecurityConstants.Action action : this.securityProperties.getActions()) {
            BinarySecurityTokenOutputProcessor kerberosTokenOutputProcessor;
            UsernameTokenOutputProcessor usernameTokenOutputProcessor;
            EncryptOutputProcessor encryptOutputProcessor;
            EncryptedKeyOutputProcessor encryptedKeyOutputProcessor;
            WSSSignatureOutputProcessor signatureOutputProcessor;
            BinarySecurityTokenOutputProcessor binarySecurityTokenOutputProcessor;
            if (WSSConstants.TIMESTAMP.equals(action)) {
                TimestampOutputProcessor timestampOutputProcessor = new TimestampOutputProcessor();
                this.initializeOutputProcessor(outputProcessorChain, timestampOutputProcessor, action);
                continue;
            }
            if (WSSConstants.SIGNATURE.equals(action)) {
                configuredAction.signatureAction = true;
                binarySecurityTokenOutputProcessor = new BinarySecurityTokenOutputProcessor();
                this.initializeOutputProcessor(outputProcessorChain, binarySecurityTokenOutputProcessor, action);
                signatureOutputProcessor = new WSSSignatureOutputProcessor();
                this.initializeOutputProcessor(outputProcessorChain, signatureOutputProcessor, action);
                continue;
            }
            if (WSSConstants.ENCRYPT.equals(action)) {
                configuredAction.encryptionAction = true;
                encryptedKeyOutputProcessor = null;
                if (this.securityProperties.isEncryptSymmetricEncryptionKey()) {
                    BinarySecurityTokenOutputProcessor binarySecurityTokenOutputProcessor2 = new BinarySecurityTokenOutputProcessor();
                    this.initializeOutputProcessor(outputProcessorChain, binarySecurityTokenOutputProcessor2, action);
                    encryptedKeyOutputProcessor = new EncryptedKeyOutputProcessor();
                    this.initializeOutputProcessor(outputProcessorChain, encryptedKeyOutputProcessor, action);
                }
                encryptOutputProcessor = new EncryptOutputProcessor();
                this.initializeOutputProcessor(outputProcessorChain, encryptOutputProcessor, action);
                if (encryptedKeyOutputProcessor != null) continue;
                ReferenceListOutputProcessor referenceListOutputProcessor = new ReferenceListOutputProcessor();
                referenceListOutputProcessor.addAfterProcessor(EncryptEndingOutputProcessor.class.getName());
                this.initializeOutputProcessor(outputProcessorChain, referenceListOutputProcessor, action);
                continue;
            }
            if (WSSConstants.USERNAMETOKEN.equals(action)) {
                usernameTokenOutputProcessor = new UsernameTokenOutputProcessor();
                this.initializeOutputProcessor(outputProcessorChain, usernameTokenOutputProcessor, action);
                continue;
            }
            if (WSSConstants.USERNAMETOKEN_SIGNED.equals(action)) {
                usernameTokenOutputProcessor = new UsernameTokenOutputProcessor();
                this.initializeOutputProcessor(outputProcessorChain, usernameTokenOutputProcessor, action);
                signatureOutputProcessor = new WSSSignatureOutputProcessor();
                this.initializeOutputProcessor(outputProcessorChain, signatureOutputProcessor, action);
                continue;
            }
            if (WSSConstants.SIGNATURE_CONFIRMATION.equals(action)) {
                SignatureConfirmationOutputProcessor signatureConfirmationOutputProcessor = new SignatureConfirmationOutputProcessor();
                this.initializeOutputProcessor(outputProcessorChain, signatureConfirmationOutputProcessor, action);
                continue;
            }
            if (WSSConstants.SIGNATURE_WITH_DERIVED_KEY.equals(action)) {
                if (this.securityProperties.getDerivedKeyTokenReference() == WSSConstants.DerivedKeyTokenReference.EncryptedKey) {
                    if (derivedSignatureButNotDerivedEncryption) {
                        encryptedKeyOutputProcessor = new EncryptedKeyOutputProcessor();
                        this.initializeOutputProcessor(outputProcessorChain, encryptedKeyOutputProcessor, action);
                    }
                    configuredAction.encryptionAction = true;
                    configuredAction.derivedEncryption = true;
                } else if (this.securityProperties.getDerivedKeyTokenReference() == WSSConstants.DerivedKeyTokenReference.SecurityContextToken) {
                    SecurityContextTokenOutputProcessor securityContextTokenOutputProcessor = new SecurityContextTokenOutputProcessor();
                    this.initializeOutputProcessor(outputProcessorChain, securityContextTokenOutputProcessor, action);
                    configuredAction.signatureAction = true;
                    configuredAction.derivedSignature = true;
                } else {
                    configuredAction.signatureAction = true;
                    configuredAction.derivedSignature = true;
                }
                DerivedKeyTokenOutputProcessor derivedKeyTokenOutputProcessor = new DerivedKeyTokenOutputProcessor();
                this.initializeOutputProcessor(outputProcessorChain, derivedKeyTokenOutputProcessor, action);
                signatureOutputProcessor = new WSSSignatureOutputProcessor();
                this.initializeOutputProcessor(outputProcessorChain, signatureOutputProcessor, action);
                continue;
            }
            if (WSSConstants.ENCRYPT_WITH_DERIVED_KEY.equals(action)) {
                configuredAction.encryptionAction = true;
                configuredAction.derivedEncryption = true;
                encryptedKeyOutputProcessor = null;
                if (this.securityProperties.getDerivedKeyTokenReference() == WSSConstants.DerivedKeyTokenReference.EncryptedKey) {
                    encryptedKeyOutputProcessor = new EncryptedKeyOutputProcessor();
                    this.initializeOutputProcessor(outputProcessorChain, encryptedKeyOutputProcessor, action);
                } else if (this.securityProperties.getDerivedKeyTokenReference() == WSSConstants.DerivedKeyTokenReference.SecurityContextToken) {
                    SecurityContextTokenOutputProcessor securityContextTokenOutputProcessor = new SecurityContextTokenOutputProcessor();
                    this.initializeOutputProcessor(outputProcessorChain, securityContextTokenOutputProcessor, action);
                }
                DerivedKeyTokenOutputProcessor derivedKeyTokenOutputProcessor = new DerivedKeyTokenOutputProcessor();
                this.initializeOutputProcessor(outputProcessorChain, derivedKeyTokenOutputProcessor, action);
                EncryptOutputProcessor encryptOutputProcessor2 = new EncryptOutputProcessor();
                this.initializeOutputProcessor(outputProcessorChain, encryptOutputProcessor2, action);
                if (encryptedKeyOutputProcessor != null) continue;
                ReferenceListOutputProcessor referenceListOutputProcessor = new ReferenceListOutputProcessor();
                referenceListOutputProcessor.addAfterProcessor(EncryptEndingOutputProcessor.class.getName());
                this.initializeOutputProcessor(outputProcessorChain, referenceListOutputProcessor, action);
                continue;
            }
            if (WSSConstants.SAML_TOKEN_SIGNED.equals(action)) {
                configuredAction.signatureAction = true;
                configuredAction.signedSAML = true;
                binarySecurityTokenOutputProcessor = new BinarySecurityTokenOutputProcessor();
                this.initializeOutputProcessor(outputProcessorChain, binarySecurityTokenOutputProcessor, action);
                SAMLTokenOutputProcessor samlTokenOutputProcessor = new SAMLTokenOutputProcessor();
                this.initializeOutputProcessor(outputProcessorChain, samlTokenOutputProcessor, action);
                WSSSignatureOutputProcessor signatureOutputProcessor2 = new WSSSignatureOutputProcessor();
                this.initializeOutputProcessor(outputProcessorChain, signatureOutputProcessor2, action);
                continue;
            }
            if (WSSConstants.SAML_TOKEN_UNSIGNED.equals(action)) {
                SAMLTokenOutputProcessor samlTokenOutputProcessor = new SAMLTokenOutputProcessor();
                this.initializeOutputProcessor(outputProcessorChain, samlTokenOutputProcessor, action);
                continue;
            }
            if (WSSConstants.SIGNATURE_WITH_KERBEROS_TOKEN.equals(action)) {
                configuredAction.kerberos = true;
                configuredAction.signatureKerberos = true;
                kerberosTokenOutputProcessor = new BinarySecurityTokenOutputProcessor();
                this.initializeOutputProcessor(outputProcessorChain, kerberosTokenOutputProcessor, action);
                signatureOutputProcessor = new WSSSignatureOutputProcessor();
                this.initializeOutputProcessor(outputProcessorChain, signatureOutputProcessor, action);
                continue;
            }
            if (WSSConstants.ENCRYPT_WITH_KERBEROS_TOKEN.equals(action)) {
                configuredAction.kerberos = true;
                configuredAction.encryptionKerberos = true;
                kerberosTokenOutputProcessor = new BinarySecurityTokenOutputProcessor();
                this.initializeOutputProcessor(outputProcessorChain, kerberosTokenOutputProcessor, action);
                encryptOutputProcessor = new EncryptOutputProcessor();
                this.initializeOutputProcessor(outputProcessorChain, encryptOutputProcessor, action);
                continue;
            }
            if (WSSConstants.KERBEROS_TOKEN.equals(action)) {
                configuredAction.kerberos = true;
                kerberosTokenOutputProcessor = new BinarySecurityTokenOutputProcessor();
                this.initializeOutputProcessor(outputProcessorChain, kerberosTokenOutputProcessor, action);
                continue;
            }
            if (!WSSConstants.CUSTOM_TOKEN.equals(action)) continue;
            CustomTokenOutputProcessor unknownTokenOutputProcessor = new CustomTokenOutputProcessor();
            this.initializeOutputProcessor(outputProcessorChain, unknownTokenOutputProcessor, action);
        }
        return configuredAction;
    }

    private static class ConfiguredAction {
        boolean signatureAction = false;
        boolean encryptionAction = false;
        boolean signedSAML = false;
        boolean kerberos = false;
        boolean signatureKerberos = false;
        boolean encryptionKerberos = false;
        boolean derivedSignature = false;
        boolean derivedEncryption = false;

        private ConfiguredAction() {
        }
    }
}

