/*
 * Decompiled with CFR 0.152.
 */
package org.apache.wss4j.stax.impl.processor.output;

import java.util.ArrayList;
import javax.xml.namespace.QName;
import javax.xml.stream.XMLStreamException;
import org.apache.wss4j.stax.ext.WSSConstants;
import org.apache.wss4j.stax.ext.WSSSecurityProperties;
import org.apache.wss4j.stax.impl.processor.output.EncryptEndingOutputProcessor;
import org.apache.wss4j.stax.impl.processor.output.OutputProcessorUtils;
import org.apache.wss4j.stax.impl.processor.output.WSSSignatureOutputProcessor;
import org.apache.wss4j.stax.impl.securityToken.KerberosClientSecurityToken;
import org.apache.wss4j.stax.securityToken.WSSecurityTokenConstants;
import org.apache.wss4j.stax.utils.WSSUtils;
import org.apache.xml.security.exceptions.XMLSecurityException;
import org.apache.xml.security.stax.ext.AbstractOutputProcessor;
import org.apache.xml.security.stax.ext.OutputProcessorChain;
import org.apache.xml.security.stax.ext.XMLSecurityConstants;
import org.apache.xml.security.stax.ext.stax.XMLSecAttribute;
import org.apache.xml.security.stax.ext.stax.XMLSecEvent;
import org.apache.xml.security.stax.impl.securityToken.GenericOutboundSecurityToken;
import org.apache.xml.security.stax.securityToken.OutboundSecurityToken;
import org.apache.xml.security.stax.securityToken.SecurityTokenConstants;
import org.apache.xml.security.stax.securityToken.SecurityTokenProvider;
import org.apache.xml.security.utils.XMLUtils;

public class BinarySecurityTokenOutputProcessor
extends AbstractOutputProcessor {
    public BinarySecurityTokenOutputProcessor() throws XMLSecurityException {
        this.addBeforeProcessor(WSSSignatureOutputProcessor.class.getName());
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void processEvent(XMLSecEvent xmlSecEvent, OutputProcessorChain outputProcessorChain) throws XMLStreamException, XMLSecurityException {
        try {
            GenericOutboundSecurityToken securityToken = null;
            XMLSecurityConstants.Action action = this.getAction();
            String tokenId = null;
            if (WSSConstants.SIGNATURE.equals(action) || WSSConstants.SAML_TOKEN_SIGNED.equals(action)) {
                tokenId = (String)outputProcessorChain.getSecurityContext().get("PROP_USE_THIS_TOKEN_ID_FOR_SIGNATURE");
            } else if (WSSConstants.ENCRYPT.equals(action)) {
                tokenId = (String)outputProcessorChain.getSecurityContext().get("PROP_USE_THIS_TOKEN_ID_FOR_ENCRYPTED_KEY");
            } else if (WSSConstants.ENCRYPT_WITH_KERBEROS_TOKEN.equals(this.getAction()) || WSSConstants.SIGNATURE_WITH_KERBEROS_TOKEN.equals(this.getAction()) || WSSConstants.KERBEROS_TOKEN.equals(this.getAction())) {
                tokenId = (String)outputProcessorChain.getSecurityContext().get("PROP_USE_THIS_TOKEN_ID_FOR_KERBEROS");
            }
            SecurityTokenProvider<OutboundSecurityToken> tokenProvider = null;
            if (tokenId != null && (tokenProvider = outputProcessorChain.getSecurityContext().getSecurityTokenProvider(tokenId)) != null) {
                securityToken = (GenericOutboundSecurityToken)tokenProvider.getSecurityToken();
            }
            boolean includeToken = false;
            SecurityTokenConstants.KeyIdentifier keyIdentifier = null;
            if ((WSSConstants.SIGNATURE.equals(action) || WSSConstants.SAML_TOKEN_SIGNED.equals(action)) && !this.getSecurityProperties().getSignatureKeyIdentifiers().isEmpty()) {
                includeToken = ((WSSSecurityProperties)this.getSecurityProperties()).isIncludeSignatureToken();
                keyIdentifier = this.getSecurityProperties().getSignatureKeyIdentifiers().get(0);
            } else if (WSSConstants.ENCRYPT.equals(action)) {
                includeToken = ((WSSSecurityProperties)this.getSecurityProperties()).isIncludeEncryptionToken();
                keyIdentifier = this.getSecurityProperties().getEncryptionKeyIdentifier();
            }
            if (securityToken != null) {
                if (WSSConstants.SIGNATURE.equals(action) && (includeToken || WSSecurityTokenConstants.KEYIDENTIFIER_SECURITY_TOKEN_DIRECT_REFERENCE.equals(keyIdentifier)) && (securityToken.getTokenType() == null || WSSecurityTokenConstants.X509V3Token.equals(securityToken.getTokenType()))) {
                    FinalBinarySecurityTokenOutputProcessor finalBinarySecurityTokenOutputProcessor = new FinalBinarySecurityTokenOutputProcessor(securityToken);
                    finalBinarySecurityTokenOutputProcessor.setXMLSecurityProperties(this.getSecurityProperties());
                    finalBinarySecurityTokenOutputProcessor.setAction(this.getAction());
                    finalBinarySecurityTokenOutputProcessor.addBeforeProcessor(WSSSignatureOutputProcessor.class.getName());
                    finalBinarySecurityTokenOutputProcessor.init(outputProcessorChain);
                    securityToken.setProcessor(finalBinarySecurityTokenOutputProcessor);
                } else if (WSSConstants.SAML_TOKEN_SIGNED.equals(action) && includeToken && (securityToken.getTokenType() == null || WSSecurityTokenConstants.X509V3Token.equals(securityToken.getTokenType()))) {
                    FinalBinarySecurityTokenOutputProcessor finalBinarySecurityTokenOutputProcessor = new FinalBinarySecurityTokenOutputProcessor(securityToken);
                    finalBinarySecurityTokenOutputProcessor.setXMLSecurityProperties(this.getSecurityProperties());
                    finalBinarySecurityTokenOutputProcessor.setAction(this.getAction());
                    finalBinarySecurityTokenOutputProcessor.addBeforeProcessor(WSSSignatureOutputProcessor.class.getName());
                    finalBinarySecurityTokenOutputProcessor.init(outputProcessorChain);
                    securityToken.setProcessor(finalBinarySecurityTokenOutputProcessor);
                } else if (WSSConstants.ENCRYPT.equals(action) && (includeToken || WSSecurityTokenConstants.KEYIDENTIFIER_SECURITY_TOKEN_DIRECT_REFERENCE.equals(keyIdentifier)) && (securityToken.getTokenType() == null || WSSecurityTokenConstants.X509V3Token.equals(securityToken.getTokenType()))) {
                    FinalBinarySecurityTokenOutputProcessor finalBinarySecurityTokenOutputProcessor = new FinalBinarySecurityTokenOutputProcessor(securityToken);
                    finalBinarySecurityTokenOutputProcessor.setXMLSecurityProperties(this.getSecurityProperties());
                    finalBinarySecurityTokenOutputProcessor.setAction(this.getAction());
                    finalBinarySecurityTokenOutputProcessor.addAfterProcessor(EncryptEndingOutputProcessor.class.getName());
                    finalBinarySecurityTokenOutputProcessor.init(outputProcessorChain);
                    securityToken.setProcessor(finalBinarySecurityTokenOutputProcessor);
                } else if (WSSConstants.ENCRYPT_WITH_KERBEROS_TOKEN.equals(this.getAction()) || WSSConstants.SIGNATURE_WITH_KERBEROS_TOKEN.equals(this.getAction()) || WSSConstants.KERBEROS_TOKEN.equals(this.getAction())) {
                    FinalBinarySecurityTokenOutputProcessor finalBinarySecurityTokenOutputProcessor = new FinalBinarySecurityTokenOutputProcessor(securityToken);
                    finalBinarySecurityTokenOutputProcessor.setXMLSecurityProperties(this.getSecurityProperties());
                    finalBinarySecurityTokenOutputProcessor.setAction(this.getAction());
                    finalBinarySecurityTokenOutputProcessor.addBeforeProcessor(WSSSignatureOutputProcessor.class.getName());
                    finalBinarySecurityTokenOutputProcessor.addAfterProcessor(EncryptEndingOutputProcessor.class.getName());
                    finalBinarySecurityTokenOutputProcessor.init(outputProcessorChain);
                    securityToken.setProcessor(finalBinarySecurityTokenOutputProcessor);
                }
            }
        }
        finally {
            outputProcessorChain.removeProcessor(this);
        }
        outputProcessorChain.processEvent(xmlSecEvent);
    }

    static class FinalBinarySecurityTokenOutputProcessor
    extends AbstractOutputProcessor {
        private final OutboundSecurityToken securityToken;

        FinalBinarySecurityTokenOutputProcessor(OutboundSecurityToken securityToken) throws XMLSecurityException {
            this.addAfterProcessor(BinarySecurityTokenOutputProcessor.class.getName());
            this.securityToken = securityToken;
        }

        @Override
        public void processEvent(XMLSecEvent xmlSecEvent, OutputProcessorChain outputProcessorChain) throws XMLStreamException, XMLSecurityException {
            outputProcessorChain.processEvent(xmlSecEvent);
            if (WSSUtils.isSecurityHeaderElement(xmlSecEvent, ((WSSSecurityProperties)this.getSecurityProperties()).getActor())) {
                QName headerElementName = WSSConstants.TAG_WSSE_BINARY_SECURITY_TOKEN;
                if (WSSConstants.ENCRYPT_WITH_KERBEROS_TOKEN.equals(this.getAction()) || WSSConstants.SIGNATURE_WITH_KERBEROS_TOKEN.equals(this.getAction()) || WSSConstants.KERBEROS_TOKEN.equals(this.getAction())) {
                    OutputProcessorUtils.updateSecurityHeaderOrder(outputProcessorChain, headerElementName, this.getAction(), false);
                    OutputProcessorChain subOutputProcessorChain = outputProcessorChain.createSubChain(this);
                    ArrayList<XMLSecAttribute> attributes = new ArrayList<XMLSecAttribute>(3);
                    attributes.add(this.createAttribute(WSSConstants.ATT_NULL_ENCODING_TYPE, "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-soap-message-security-1.0#Base64Binary"));
                    attributes.add(this.createAttribute(WSSConstants.ATT_NULL_VALUE_TYPE, "http://docs.oasis-open.org/wss/oasis-wss-kerberos-token-profile-1.1#GSS_Kerberosv5_AP_REQ"));
                    attributes.add(this.createAttribute(WSSConstants.ATT_WSU_ID, this.securityToken.getId()));
                    this.createStartElementAndOutputAsEvent(subOutputProcessorChain, headerElementName, false, attributes);
                    this.createCharactersAndOutputAsEvent(subOutputProcessorChain, XMLUtils.encodeToString(((KerberosClientSecurityToken)this.securityToken).getTicket()));
                    this.createEndElementAndOutputAsEvent(subOutputProcessorChain, headerElementName);
                    if (WSSConstants.ENCRYPT_WITH_KERBEROS_TOKEN.equals(this.getAction())) {
                        OutputProcessorUtils.updateSecurityHeaderOrder(outputProcessorChain, WSSConstants.TAG_xenc_ReferenceList, this.getAction(), false);
                        WSSUtils.createReferenceListStructureForEncryption(this, subOutputProcessorChain);
                    }
                } else if (this.securityToken.getX509Certificates() != null && this.securityToken.getX509Certificates().length > 0) {
                    OutputProcessorUtils.updateSecurityHeaderOrder(outputProcessorChain, headerElementName, this.getAction(), false);
                    OutputProcessorChain subOutputProcessorChain = outputProcessorChain.createSubChain(this);
                    boolean useSingleCertificate = this.getSecurityProperties().isUseSingleCert();
                    WSSUtils.createBinarySecurityTokenStructure(this, subOutputProcessorChain, this.securityToken.getId(), this.securityToken.getX509Certificates(), useSingleCertificate);
                }
                outputProcessorChain.removeProcessor(this);
            }
        }
    }
}

