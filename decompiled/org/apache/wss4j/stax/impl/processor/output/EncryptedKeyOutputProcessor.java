/*
 * Decompiled with CFR 0.152.
 */
package org.apache.wss4j.stax.impl.processor.output;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.cert.X509Certificate;
import java.security.spec.MGF1ParameterSpec;
import java.util.ArrayList;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.OAEPParameterSpec;
import javax.crypto.spec.PSource;
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.UnsupportedCallbackException;
import javax.xml.namespace.QName;
import javax.xml.stream.XMLStreamException;
import org.apache.wss4j.common.ext.WSPasswordCallback;
import org.apache.wss4j.common.ext.WSSecurityException;
import org.apache.wss4j.stax.ext.WSSConstants;
import org.apache.wss4j.stax.ext.WSSSecurityProperties;
import org.apache.wss4j.stax.impl.processor.output.EncryptEndingOutputProcessor;
import org.apache.wss4j.stax.impl.processor.output.OutputProcessorUtils;
import org.apache.wss4j.stax.impl.processor.output.ReferenceListOutputProcessor;
import org.apache.wss4j.stax.impl.processor.output.WSSSignatureOutputProcessor;
import org.apache.wss4j.stax.securityToken.WSSecurityTokenConstants;
import org.apache.wss4j.stax.utils.WSSUtils;
import org.apache.xml.security.exceptions.XMLSecurityException;
import org.apache.xml.security.stax.config.JCEAlgorithmMapper;
import org.apache.xml.security.stax.ext.AbstractOutputProcessor;
import org.apache.xml.security.stax.ext.OutputProcessorChain;
import org.apache.xml.security.stax.ext.XMLSecurityConstants;
import org.apache.xml.security.stax.ext.stax.XMLSecAttribute;
import org.apache.xml.security.stax.ext.stax.XMLSecEvent;
import org.apache.xml.security.stax.impl.securityToken.GenericOutboundSecurityToken;
import org.apache.xml.security.stax.impl.util.IDGenerator;
import org.apache.xml.security.stax.securityToken.OutboundSecurityToken;
import org.apache.xml.security.stax.securityToken.SecurityTokenConstants;
import org.apache.xml.security.stax.securityToken.SecurityTokenProvider;
import org.apache.xml.security.utils.XMLUtils;

public class EncryptedKeyOutputProcessor
extends AbstractOutputProcessor {
    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void processEvent(XMLSecEvent xmlSecEvent, OutputProcessorChain outputProcessorChain) throws XMLStreamException, XMLSecurityException {
        try {
            String tokenId = (String)outputProcessorChain.getSecurityContext().get("PROP_USE_THIS_TOKEN_ID_FOR_ENCRYPTED_KEY");
            if (tokenId == null) {
                throw new WSSecurityException(WSSecurityException.ErrorCode.FAILURE);
            }
            SecurityTokenProvider<OutboundSecurityToken> wrappingSecurityTokenProvider = outputProcessorChain.getSecurityContext().getSecurityTokenProvider(tokenId);
            if (wrappingSecurityTokenProvider == null) {
                throw new WSSecurityException(WSSecurityException.ErrorCode.FAILURE);
            }
            OutboundSecurityToken wrappingSecurityToken = wrappingSecurityTokenProvider.getSecurityToken();
            if (wrappingSecurityToken == null) {
                throw new WSSecurityException(WSSecurityException.ErrorCode.FAILURE);
            }
            SecurityTokenProvider<OutboundSecurityToken> encryptedKeySecurityTokenProvider = null;
            GenericOutboundSecurityToken encryptedKeySecurityToken = null;
            String sigTokenId = (String)outputProcessorChain.getSecurityContext().get("PROP_USE_THIS_TOKEN_ID_FOR_SIGNATURE");
            String encTokenId = (String)outputProcessorChain.getSecurityContext().get("PROP_USE_THIS_TOKEN_ID_FOR_ENCRYPTION");
            if (encTokenId == null) {
                throw new WSSecurityException(WSSecurityException.ErrorCode.FAILURE);
            }
            encryptedKeySecurityTokenProvider = outputProcessorChain.getSecurityContext().getSecurityTokenProvider(encTokenId);
            if (encryptedKeySecurityTokenProvider == null) {
                throw new WSSecurityException(WSSecurityException.ErrorCode.FAILURE);
            }
            encryptedKeySecurityToken = (GenericOutboundSecurityToken)encryptedKeySecurityTokenProvider.getSecurityToken();
            boolean sharedToken = encTokenId.equals(sigTokenId);
            FinalEncryptedKeyOutputProcessor finalEncryptedKeyOutputProcessor = new FinalEncryptedKeyOutputProcessor(encryptedKeySecurityToken);
            finalEncryptedKeyOutputProcessor.setXMLSecurityProperties(this.getSecurityProperties());
            finalEncryptedKeyOutputProcessor.setAction(this.getAction());
            XMLSecurityConstants.Action action = this.getAction();
            if (WSSConstants.ENCRYPT.equals(action)) {
                if (wrappingSecurityToken.getProcessor() != null) {
                    finalEncryptedKeyOutputProcessor.addBeforeProcessor(wrappingSecurityToken.getProcessor());
                    finalEncryptedKeyOutputProcessor.init(outputProcessorChain);
                } else if (sharedToken) {
                    finalEncryptedKeyOutputProcessor.addAfterProcessor(EncryptEndingOutputProcessor.class.getName());
                    if (this.getSecurityProperties().getActions().indexOf(WSSConstants.ENCRYPT) < this.getSecurityProperties().getActions().indexOf(WSSConstants.SIGNATURE)) {
                        finalEncryptedKeyOutputProcessor.addBeforeProcessor(WSSSignatureOutputProcessor.class.getName());
                        finalEncryptedKeyOutputProcessor.setAction(WSSConstants.SIGNATURE);
                    }
                    finalEncryptedKeyOutputProcessor.setOutputReferenceList(false);
                    finalEncryptedKeyOutputProcessor.init(outputProcessorChain);
                    ReferenceListOutputProcessor referenceListOutputProcessor = new ReferenceListOutputProcessor();
                    referenceListOutputProcessor.addBeforeProcessor(finalEncryptedKeyOutputProcessor);
                    referenceListOutputProcessor.setXMLSecurityProperties(this.getSecurityProperties());
                    referenceListOutputProcessor.setAction(this.getAction());
                    referenceListOutputProcessor.init(outputProcessorChain);
                } else {
                    finalEncryptedKeyOutputProcessor.addAfterProcessor(EncryptEndingOutputProcessor.class.getName());
                    finalEncryptedKeyOutputProcessor.init(outputProcessorChain);
                }
            } else if (WSSConstants.SIGNATURE_WITH_DERIVED_KEY.equals(action)) {
                if (wrappingSecurityToken.getProcessor() != null) {
                    finalEncryptedKeyOutputProcessor.addBeforeProcessor(wrappingSecurityToken.getProcessor());
                } else {
                    finalEncryptedKeyOutputProcessor.addBeforeProcessor(WSSSignatureOutputProcessor.class.getName());
                }
                finalEncryptedKeyOutputProcessor.init(outputProcessorChain);
            } else if (WSSConstants.ENCRYPT_WITH_DERIVED_KEY.equals(action)) {
                if (wrappingSecurityToken.getProcessor() != null) {
                    finalEncryptedKeyOutputProcessor.addBeforeProcessor(wrappingSecurityToken.getProcessor());
                    finalEncryptedKeyOutputProcessor.init(outputProcessorChain);
                } else if (sharedToken) {
                    finalEncryptedKeyOutputProcessor.addBeforeProcessor(WSSSignatureOutputProcessor.class.getName());
                    finalEncryptedKeyOutputProcessor.addAfterProcessor(EncryptEndingOutputProcessor.class.getName());
                    if (this.getSecurityProperties().getActions().indexOf(WSSConstants.ENCRYPT_WITH_DERIVED_KEY) < this.getSecurityProperties().getActions().indexOf(WSSConstants.SIGNATURE_WITH_DERIVED_KEY)) {
                        finalEncryptedKeyOutputProcessor.setAction(WSSConstants.SIGNATURE_WITH_DERIVED_KEY);
                    }
                    finalEncryptedKeyOutputProcessor.setOutputReferenceList(false);
                    finalEncryptedKeyOutputProcessor.init(outputProcessorChain);
                } else {
                    finalEncryptedKeyOutputProcessor.addAfterProcessor(EncryptEndingOutputProcessor.class.getName());
                    finalEncryptedKeyOutputProcessor.init(outputProcessorChain);
                }
                ReferenceListOutputProcessor referenceListOutputProcessor = new ReferenceListOutputProcessor();
                referenceListOutputProcessor.addBeforeProcessor(finalEncryptedKeyOutputProcessor);
                referenceListOutputProcessor.setXMLSecurityProperties(this.getSecurityProperties());
                referenceListOutputProcessor.setAction(this.getAction());
                referenceListOutputProcessor.init(outputProcessorChain);
            } else {
                finalEncryptedKeyOutputProcessor.init(outputProcessorChain);
            }
            outputProcessorChain.getSecurityContext().registerSecurityTokenProvider(encryptedKeySecurityToken.getId(), encryptedKeySecurityTokenProvider);
            encryptedKeySecurityToken.setProcessor(finalEncryptedKeyOutputProcessor);
        }
        finally {
            outputProcessorChain.removeProcessor(this);
        }
        outputProcessorChain.processEvent(xmlSecEvent);
    }

    class FinalEncryptedKeyOutputProcessor
    extends AbstractOutputProcessor {
        private final OutboundSecurityToken securityToken;
        private boolean outputReferenceList = true;

        FinalEncryptedKeyOutputProcessor(OutboundSecurityToken securityToken) throws XMLSecurityException {
            this.addAfterProcessor(FinalEncryptedKeyOutputProcessor.class.getName());
            this.securityToken = securityToken;
        }

        protected void setOutputReferenceList(boolean outputReferenceList) {
            this.outputReferenceList = outputReferenceList;
        }

        @Override
        public void processEvent(XMLSecEvent xmlSecEvent, OutputProcessorChain outputProcessorChain) throws XMLStreamException, XMLSecurityException {
            outputProcessorChain.processEvent(xmlSecEvent);
            if (WSSUtils.isSecurityHeaderElement(xmlSecEvent, ((WSSSecurityProperties)this.getSecurityProperties()).getActor())) {
                QName headerElementName = WSSConstants.TAG_xenc_EncryptedKey;
                OutputProcessorUtils.updateSecurityHeaderOrder(outputProcessorChain, headerElementName, this.getAction(), false);
                OutputProcessorChain subOutputProcessorChain = outputProcessorChain.createSubChain(this);
                PublicKey publicKey = null;
                publicKey = this.securityToken.getKeyWrappingToken().getX509Certificates() != null && this.securityToken.getKeyWrappingToken().getX509Certificates().length > 0 ? this.securityToken.getKeyWrappingToken().getX509Certificates()[0].getPublicKey() : this.securityToken.getKeyWrappingToken().getPublicKey();
                if (publicKey == null) {
                    throw new WSSecurityException(WSSecurityException.ErrorCode.FAILURE, "failedCredentialLoad");
                }
                String encryptionKeyTransportAlgorithm = this.getSecurityProperties().getEncryptionKeyTransportAlgorithm();
                ArrayList<XMLSecAttribute> attributes = new ArrayList<XMLSecAttribute>(1);
                attributes.add(this.createAttribute(WSSConstants.ATT_NULL_Id, this.securityToken.getId()));
                this.createStartElementAndOutputAsEvent(subOutputProcessorChain, headerElementName, true, attributes);
                attributes = new ArrayList(1);
                attributes.add(this.createAttribute(WSSConstants.ATT_NULL_Algorithm, encryptionKeyTransportAlgorithm));
                this.createStartElementAndOutputAsEvent(subOutputProcessorChain, WSSConstants.TAG_xenc_EncryptionMethod, false, attributes);
                String encryptionKeyTransportMGFAlgorithm = this.getSecurityProperties().getEncryptionKeyTransportMGFAlgorithm();
                if ("http://www.w3.org/2009/xmlenc11#rsa-oaep".equals(encryptionKeyTransportAlgorithm) || "http://www.w3.org/2001/04/xmlenc#rsa-oaep-mgf1p".equals(encryptionKeyTransportAlgorithm)) {
                    String encryptionKeyTransportDigestAlgorithm;
                    byte[] oaepParams = this.getSecurityProperties().getEncryptionKeyTransportOAEPParams();
                    if (oaepParams != null) {
                        this.createStartElementAndOutputAsEvent(subOutputProcessorChain, XMLSecurityConstants.TAG_xenc_OAEPparams, false, null);
                        this.createCharactersAndOutputAsEvent(subOutputProcessorChain, XMLUtils.encodeToString(oaepParams));
                        this.createEndElementAndOutputAsEvent(subOutputProcessorChain, XMLSecurityConstants.TAG_xenc_OAEPparams);
                    }
                    if ((encryptionKeyTransportDigestAlgorithm = this.getSecurityProperties().getEncryptionKeyTransportDigestAlgorithm()) != null) {
                        attributes = new ArrayList(1);
                        attributes.add(this.createAttribute(XMLSecurityConstants.ATT_NULL_Algorithm, encryptionKeyTransportDigestAlgorithm));
                        this.createStartElementAndOutputAsEvent(subOutputProcessorChain, XMLSecurityConstants.TAG_dsig_DigestMethod, true, attributes);
                        this.createEndElementAndOutputAsEvent(subOutputProcessorChain, XMLSecurityConstants.TAG_dsig_DigestMethod);
                    }
                    if (encryptionKeyTransportMGFAlgorithm != null) {
                        attributes = new ArrayList(1);
                        attributes.add(this.createAttribute(XMLSecurityConstants.ATT_NULL_Algorithm, encryptionKeyTransportMGFAlgorithm));
                        this.createStartElementAndOutputAsEvent(subOutputProcessorChain, XMLSecurityConstants.TAG_xenc11_MGF, true, attributes);
                        this.createEndElementAndOutputAsEvent(subOutputProcessorChain, XMLSecurityConstants.TAG_xenc11_MGF);
                    }
                }
                this.createEndElementAndOutputAsEvent(subOutputProcessorChain, WSSConstants.TAG_xenc_EncryptionMethod);
                this.createStartElementAndOutputAsEvent(subOutputProcessorChain, WSSConstants.TAG_dsig_KeyInfo, true, null);
                this.createSecurityTokenReferenceStructureForEncryptedKey(subOutputProcessorChain, this.securityToken, ((WSSSecurityProperties)this.getSecurityProperties()).getEncryptionKeyIdentifier(), this.getSecurityProperties().isUseSingleCert());
                this.createEndElementAndOutputAsEvent(subOutputProcessorChain, WSSConstants.TAG_dsig_KeyInfo);
                this.createStartElementAndOutputAsEvent(subOutputProcessorChain, WSSConstants.TAG_xenc_CipherData, false, null);
                this.createStartElementAndOutputAsEvent(subOutputProcessorChain, WSSConstants.TAG_xenc_CipherValue, false, null);
                try {
                    String jceid = JCEAlgorithmMapper.translateURItoJCEID(encryptionKeyTransportAlgorithm);
                    Cipher cipher = Cipher.getInstance(jceid);
                    OAEPParameterSpec algorithmParameterSpec = null;
                    if ("http://www.w3.org/2009/xmlenc11#rsa-oaep".equals(encryptionKeyTransportAlgorithm) || "http://www.w3.org/2001/04/xmlenc#rsa-oaep-mgf1p".equals(encryptionKeyTransportAlgorithm)) {
                        String jceDigestAlgorithm = "SHA-1";
                        String encryptionKeyTransportDigestAlgorithm = this.getSecurityProperties().getEncryptionKeyTransportDigestAlgorithm();
                        if (encryptionKeyTransportDigestAlgorithm != null) {
                            jceDigestAlgorithm = JCEAlgorithmMapper.translateURItoJCEID(encryptionKeyTransportDigestAlgorithm);
                        }
                        PSource.PSpecified pSource = PSource.PSpecified.DEFAULT;
                        byte[] oaepParams = this.getSecurityProperties().getEncryptionKeyTransportOAEPParams();
                        if (oaepParams != null) {
                            pSource = new PSource.PSpecified(oaepParams);
                        }
                        MGF1ParameterSpec mgfParameterSpec = new MGF1ParameterSpec("SHA-1");
                        if (encryptionKeyTransportMGFAlgorithm != null) {
                            String jceMGFAlgorithm = JCEAlgorithmMapper.translateURItoJCEID(encryptionKeyTransportMGFAlgorithm);
                            mgfParameterSpec = new MGF1ParameterSpec(jceMGFAlgorithm);
                        }
                        algorithmParameterSpec = new OAEPParameterSpec(jceDigestAlgorithm, "MGF1", mgfParameterSpec, pSource);
                    }
                    cipher.init(3, (Key)publicKey, algorithmParameterSpec);
                    Key secretKey = this.securityToken.getSecretKey("");
                    int blockSize = cipher.getBlockSize();
                    if (blockSize > 0 && blockSize < secretKey.getEncoded().length) {
                        throw new WSSecurityException(WSSecurityException.ErrorCode.FAILURE, "unsupportedKeyTransp", new Object[]{"public key algorithm too weak to encrypt symmetric key"});
                    }
                    byte[] encryptedEphemeralKey = cipher.wrap(secretKey);
                    if (((WSSSecurityProperties)this.getSecurityProperties()).getCallbackHandler() != null) {
                        WSPasswordCallback callback = new WSPasswordCallback(this.securityToken.getId(), 9);
                        callback.setKey(encryptedEphemeralKey);
                        try {
                            ((WSSSecurityProperties)this.getSecurityProperties()).getCallbackHandler().handle(new Callback[]{callback});
                        }
                        catch (IOException | UnsupportedCallbackException exception) {
                            // empty catch block
                        }
                    }
                    this.createCharactersAndOutputAsEvent(subOutputProcessorChain, XMLUtils.encodeToString(encryptedEphemeralKey));
                }
                catch (InvalidAlgorithmParameterException | InvalidKeyException | NoSuchAlgorithmException | IllegalBlockSizeException | NoSuchPaddingException e) {
                    throw new WSSecurityException(WSSecurityException.ErrorCode.FAILURE, (Exception)e);
                }
                this.createEndElementAndOutputAsEvent(subOutputProcessorChain, WSSConstants.TAG_xenc_CipherValue);
                this.createEndElementAndOutputAsEvent(subOutputProcessorChain, WSSConstants.TAG_xenc_CipherData);
                if (this.outputReferenceList && WSSConstants.ENCRYPT.equals(this.getAction())) {
                    WSSUtils.createReferenceListStructureForEncryption(this, subOutputProcessorChain);
                }
                this.createEndElementAndOutputAsEvent(subOutputProcessorChain, headerElementName);
                outputProcessorChain.removeProcessor(this);
            }
        }

        protected void createSecurityTokenReferenceStructureForEncryptedKey(OutputProcessorChain outputProcessorChain, OutboundSecurityToken securityToken, SecurityTokenConstants.KeyIdentifier keyIdentifier, boolean useSingleCertificate) throws XMLStreamException, XMLSecurityException {
            if (securityToken.getCustomTokenReference() != null) {
                this.outputDOMElement(securityToken.getCustomTokenReference(), outputProcessorChain);
                return;
            }
            X509Certificate[] x509Certificates = securityToken.getKeyWrappingToken().getX509Certificates();
            if ((x509Certificates == null || x509Certificates.length == 0) && securityToken.getKeyWrappingToken().getPublicKey() != null) {
                WSSUtils.createKeyValueTokenStructure((AbstractOutputProcessor)this, outputProcessorChain, securityToken.getKeyWrappingToken().getPublicKey());
                return;
            }
            ArrayList<XMLSecAttribute> attributes = new ArrayList<XMLSecAttribute>(2);
            attributes.add(this.createAttribute(WSSConstants.ATT_WSU_ID, IDGenerator.generateID(null)));
            if (WSSecurityTokenConstants.KEYIDENTIFIER_SECURITY_TOKEN_DIRECT_REFERENCE.equals(keyIdentifier) && !useSingleCertificate) {
                attributes.add(this.createAttribute(WSSConstants.ATT_WSSE11_TOKEN_TYPE, "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-x509-token-profile-1.0#X509PKIPathv1"));
            }
            this.createStartElementAndOutputAsEvent(outputProcessorChain, WSSConstants.TAG_WSSE_SECURITY_TOKEN_REFERENCE, false, attributes);
            String tokenId = securityToken.getKeyWrappingToken().getId();
            if (WSSecurityTokenConstants.KeyIdentifier_IssuerSerial.equals(keyIdentifier)) {
                WSSUtils.createX509IssuerSerialStructure(this, outputProcessorChain, x509Certificates);
            } else if (WSSecurityTokenConstants.KeyIdentifier_SkiKeyIdentifier.equals(keyIdentifier)) {
                WSSUtils.createX509SubjectKeyIdentifierStructure(this, outputProcessorChain, x509Certificates);
            } else if (WSSecurityTokenConstants.KeyIdentifier_X509KeyIdentifier.equals(keyIdentifier)) {
                WSSUtils.createX509KeyIdentifierStructure(this, outputProcessorChain, x509Certificates);
            } else if (WSSecurityTokenConstants.KEYIDENTIFIER_THUMBPRINT_IDENTIFIER.equals(keyIdentifier)) {
                WSSUtils.createThumbprintKeyIdentifierStructure(this, outputProcessorChain, x509Certificates);
            } else if (WSSecurityTokenConstants.KEYIDENTIFIER_ENCRYPTED_KEY_SHA1_IDENTIFIER.equals(keyIdentifier)) {
                WSSUtils.createThumbprintKeyIdentifierStructure(this, outputProcessorChain, x509Certificates);
            } else if (WSSecurityTokenConstants.KEYIDENTIFIER_SECURITY_TOKEN_DIRECT_REFERENCE.equals(keyIdentifier)) {
                String valueType = useSingleCertificate ? "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-x509-token-profile-1.0#X509v3" : "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-x509-token-profile-1.0#X509PKIPathv1";
                WSSUtils.createBSTReferenceStructure(this, outputProcessorChain, tokenId, valueType, true);
            } else {
                throw new WSSecurityException(WSSecurityException.ErrorCode.FAILURE, "unsupportedSecurityToken");
            }
            this.createEndElementAndOutputAsEvent(outputProcessorChain, WSSConstants.TAG_WSSE_SECURITY_TOKEN_REFERENCE);
        }
    }
}

