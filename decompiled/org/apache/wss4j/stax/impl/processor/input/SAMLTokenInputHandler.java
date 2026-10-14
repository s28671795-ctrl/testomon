/*
 * Decompiled with CFR 0.152.
 */
package org.apache.wss4j.stax.impl.processor.input;

import java.security.Key;
import java.security.PublicKey;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Deque;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.namespace.QName;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Namespace;
import org.apache.wss4j.binding.wss10.SecurityTokenReferenceType;
import org.apache.wss4j.common.ext.WSSecurityException;
import org.apache.wss4j.common.saml.OpenSAMLUtil;
import org.apache.wss4j.common.saml.SamlAssertionWrapper;
import org.apache.wss4j.stax.ext.WSInboundSecurityContext;
import org.apache.wss4j.stax.ext.WSSConstants;
import org.apache.wss4j.stax.ext.WSSSecurityProperties;
import org.apache.wss4j.stax.impl.processor.input.OperationInputProcessor;
import org.apache.wss4j.stax.impl.processor.input.WSSEncryptedKeyInputHandler;
import org.apache.wss4j.stax.securityEvent.SamlTokenSecurityEvent;
import org.apache.wss4j.stax.securityEvent.SignedPartSecurityEvent;
import org.apache.wss4j.stax.securityEvent.WSSecurityEventConstants;
import org.apache.wss4j.stax.securityToken.SamlSecurityToken;
import org.apache.wss4j.stax.securityToken.WSSecurityTokenConstants;
import org.apache.wss4j.stax.utils.WSSUtils;
import org.apache.wss4j.stax.validate.SamlTokenValidator;
import org.apache.wss4j.stax.validate.SamlTokenValidatorImpl;
import org.apache.wss4j.stax.validate.TokenContext;
import org.apache.xml.security.binding.xmldsig.KeyInfoType;
import org.apache.xml.security.binding.xmldsig.KeyValueType;
import org.apache.xml.security.binding.xmldsig.ObjectFactory;
import org.apache.xml.security.binding.xmldsig.X509DataType;
import org.apache.xml.security.binding.xmlenc.EncryptedKeyType;
import org.apache.xml.security.exceptions.XMLSecurityException;
import org.apache.xml.security.stax.config.JCEAlgorithmMapper;
import org.apache.xml.security.stax.ext.AbstractInputProcessor;
import org.apache.xml.security.stax.ext.AbstractInputSecurityHeaderHandler;
import org.apache.xml.security.stax.ext.InputProcessorChain;
import org.apache.xml.security.stax.ext.XMLSecurityConstants;
import org.apache.xml.security.stax.ext.XMLSecurityProperties;
import org.apache.xml.security.stax.ext.stax.XMLSecAttribute;
import org.apache.xml.security.stax.ext.stax.XMLSecEvent;
import org.apache.xml.security.stax.ext.stax.XMLSecNamespace;
import org.apache.xml.security.stax.ext.stax.XMLSecStartElement;
import org.apache.xml.security.stax.impl.XMLSecurityEventReader;
import org.apache.xml.security.stax.impl.securityToken.AbstractInboundSecurityToken;
import org.apache.xml.security.stax.impl.util.IDGenerator;
import org.apache.xml.security.stax.securityEvent.AbstractSecuredElementSecurityEvent;
import org.apache.xml.security.stax.securityEvent.SecurityEvent;
import org.apache.xml.security.stax.securityEvent.SecurityEventListener;
import org.apache.xml.security.stax.securityEvent.SignedElementSecurityEvent;
import org.apache.xml.security.stax.securityToken.InboundSecurityToken;
import org.apache.xml.security.stax.securityToken.SecurityToken;
import org.apache.xml.security.stax.securityToken.SecurityTokenConstants;
import org.apache.xml.security.stax.securityToken.SecurityTokenFactory;
import org.apache.xml.security.stax.securityToken.SecurityTokenProvider;
import org.apache.xml.security.utils.XMLUtils;
import org.opensaml.security.credential.BasicCredential;
import org.opensaml.security.credential.Credential;
import org.opensaml.security.x509.BasicX509Credential;
import org.opensaml.xmlsec.signature.Signature;
import org.opensaml.xmlsec.signature.support.SignatureException;
import org.opensaml.xmlsec.signature.support.SignatureValidator;
import org.w3c.dom.Attr;
import org.w3c.dom.Comment;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.ProcessingInstruction;
import org.w3c.dom.Text;

public class SAMLTokenInputHandler
extends AbstractInputSecurityHeaderHandler {
    private static final List<QName> SAML1_TOKEN_PATH = new ArrayList<QName>(WSSConstants.WSSE_SECURITY_HEADER_PATH);
    private static final List<QName> SAML2_TOKEN_PATH = new ArrayList<QName>(WSSConstants.WSSE_SECURITY_HEADER_PATH);

    @Override
    public void handle(InputProcessorChain inputProcessorChain, XMLSecurityProperties securityProperties, Deque<XMLSecEvent> eventQueue, Integer index) throws XMLSecurityException {
        InboundSecurityToken subjectSecurityToken;
        Document samlTokenDocument = (Document)this.parseStructure(eventQueue, index, securityProperties);
        WSSSecurityProperties wssSecurityProperties = (WSSSecurityProperties)securityProperties;
        WSInboundSecurityContext wsInboundSecurityContext = (WSInboundSecurityContext)inputProcessorChain.getSecurityContext();
        Element samlElement = samlTokenDocument.getDocumentElement();
        final SamlAssertionWrapper samlAssertionWrapper = new SamlAssertionWrapper(samlElement);
        SamlTokenValidator samlTokenValidator = (SamlTokenValidator)wssSecurityProperties.getValidator(new QName(samlElement.getNamespaceURI(), samlElement.getLocalName()));
        if (samlTokenValidator == null) {
            samlTokenValidator = new SamlTokenValidatorImpl();
        }
        if (samlAssertionWrapper.isSigned()) {
            Signature signature = samlAssertionWrapper.getSignature();
            if (signature == null) {
                throw new WSSecurityException(WSSecurityException.ErrorCode.INVALID_SECURITY_TOKEN, "empty", new Object[]{"no signature to validate"});
            }
            int sigKeyInfoIdx = this.getSignatureKeyInfoIndex(eventQueue);
            if (sigKeyInfoIdx < 0) {
                throw new WSSecurityException(WSSecurityException.ErrorCode.INVALID_SECURITY_TOKEN, "noKeyInSAMLToken");
            }
            InboundSecurityToken sigSecurityToken = this.parseKeyInfo(inputProcessorChain, securityProperties, eventQueue, sigKeyInfoIdx);
            if (sigSecurityToken == null) {
                throw new WSSecurityException(WSSecurityException.ErrorCode.INVALID_SECURITY_TOKEN, "noKeyInSAMLToken");
            }
            samlTokenValidator.validate(sigSecurityToken, wssSecurityProperties);
            Object credential = null;
            if (sigSecurityToken.getX509Certificates() != null) {
                credential = new BasicX509Credential(sigSecurityToken.getX509Certificates()[0]);
            } else if (sigSecurityToken.getPublicKey() != null) {
                credential = new BasicCredential(sigSecurityToken.getPublicKey());
            } else {
                throw new WSSecurityException(WSSecurityException.ErrorCode.FAILURE, "invalidSAMLsecurity", new Object[]{"cannot get certificate or key"});
            }
            try {
                SignatureValidator.validate(signature, (Credential)credential);
            }
            catch (SignatureException ex) {
                throw new WSSecurityException(WSSecurityException.ErrorCode.FAILURE, ex, "empty", new Object[]{"SAML signature validation failed"});
            }
        }
        List<String> methods = samlAssertionWrapper.getConfirmationMethods();
        boolean holderOfKey = false;
        if (methods != null) {
            for (String method2 : methods) {
                if (!OpenSAMLUtil.isMethodHolderOfKey(method2)) continue;
                holderOfKey = true;
                break;
            }
        }
        if (holderOfKey) {
            int subjectKeyInfoIndex = this.getSubjectKeyInfoIndex(eventQueue);
            if (subjectKeyInfoIndex < 0) {
                throw new WSSecurityException(WSSecurityException.ErrorCode.INVALID_SECURITY_TOKEN, "noKeyInSAMLToken");
            }
            subjectSecurityToken = this.parseKeyInfo(inputProcessorChain, securityProperties, eventQueue, subjectKeyInfoIndex);
            if (subjectSecurityToken == null) {
                throw new WSSecurityException(WSSecurityException.ErrorCode.INVALID_SECURITY_TOKEN, "noKeyInSAMLToken");
            }
        } else {
            subjectSecurityToken = null;
        }
        List<XMLSecEvent> xmlSecEvents = this.getResponsibleXMLSecEvents(eventQueue, index);
        List<QName> elementPath = this.getElementPath(eventQueue);
        TokenContext tokenContext = new TokenContext(wssSecurityProperties, wsInboundSecurityContext, xmlSecEvents, elementPath);
        final Object samlSecurityToken = samlTokenValidator.validate(samlAssertionWrapper, subjectSecurityToken, tokenContext);
        SecurityTokenProvider<InboundSecurityToken> subjectSecurityTokenProvider = new SecurityTokenProvider<InboundSecurityToken>(){

            @Override
            public InboundSecurityToken getSecurityToken() throws XMLSecurityException {
                return (InboundSecurityToken)((Object)samlSecurityToken);
            }

            @Override
            public String getId() {
                return samlAssertionWrapper.getId();
            }
        };
        wsInboundSecurityContext.registerSecurityTokenProvider(samlAssertionWrapper.getId(), (SecurityTokenProvider<? extends InboundSecurityToken>)subjectSecurityTokenProvider);
        SamlTokenSecurityEvent samlTokenSecurityEvent = new SamlTokenSecurityEvent();
        samlTokenSecurityEvent.setSecurityToken((SamlSecurityToken)subjectSecurityTokenProvider.getSecurityToken());
        samlTokenSecurityEvent.setCorrelationID(samlAssertionWrapper.getId());
        wsInboundSecurityContext.registerSecurityEvent(samlTokenSecurityEvent);
        if (wssSecurityProperties.isValidateSamlSubjectConfirmation()) {
            SAMLTokenVerifierInputProcessor samlTokenVerifierInputProcessor = new SAMLTokenVerifierInputProcessor(securityProperties, samlAssertionWrapper, subjectSecurityTokenProvider, subjectSecurityToken);
            wsInboundSecurityContext.addSecurityEventListener(samlTokenVerifierInputProcessor);
            inputProcessorChain.addProcessor(samlTokenVerifierInputProcessor);
        }
    }

    private int getSubjectKeyInfoIndex(Deque<XMLSecEvent> eventQueue) {
        int idx = -1;
        Iterator<XMLSecEvent> xmlSecEventIterator = eventQueue.descendingIterator();
        while (xmlSecEventIterator.hasNext()) {
            XMLSecEvent xmlSecEvent = xmlSecEventIterator.next();
            ++idx;
            switch (xmlSecEvent.getEventType()) {
                case 1: {
                    List<QName> elementPath;
                    QName elementName = xmlSecEvent.asStartElement().getName();
                    if (!WSSConstants.TAG_dsig_KeyInfo.equals(elementName) || (elementPath = xmlSecEvent.asStartElement().getElementPath()).size() < 4) break;
                    int lastIndex = elementPath.size() - 2;
                    if ("SubjectConfirmationData".equals(elementPath.get(lastIndex).getLocalPart()) && "SubjectConfirmation".equals(elementPath.get(lastIndex - 1).getLocalPart()) && "Subject".equals(elementPath.get(lastIndex - 2).getLocalPart())) {
                        return idx;
                    }
                    if (!"SubjectConfirmation".equals(elementPath.get(lastIndex).getLocalPart()) || !"Subject".equals(elementPath.get(lastIndex - 1).getLocalPart())) break;
                    return idx;
                }
            }
        }
        return idx;
    }

    private int getSignatureKeyInfoIndex(Deque<XMLSecEvent> eventQueue) {
        int idx = -1;
        Iterator<XMLSecEvent> xmlSecEventIterator = eventQueue.descendingIterator();
        while (xmlSecEventIterator.hasNext()) {
            XMLSecEvent xmlSecEvent = xmlSecEventIterator.next();
            ++idx;
            switch (xmlSecEvent.getEventType()) {
                case 1: {
                    int lastIndex;
                    List<QName> elementPath;
                    QName elementName = xmlSecEvent.asStartElement().getName();
                    if (!WSSConstants.TAG_dsig_KeyInfo.equals(elementName) || (elementPath = xmlSecEvent.asStartElement().getElementPath()).size() < 4 || !"Signature".equals(elementPath.get(lastIndex = elementPath.size() - 2).getLocalPart()) || !"Assertion".equals(elementPath.get(lastIndex - 1).getLocalPart())) break;
                    return idx;
                }
            }
        }
        return idx;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private InboundSecurityToken parseKeyInfo(InputProcessorChain inputProcessorChain, XMLSecurityProperties securityProperties, Deque<XMLSecEvent> eventQueue, int index) throws XMLSecurityException {
        int idx;
        XMLSecEvent xmlSecEvent = null;
        Iterator<XMLSecEvent> xmlSecEventIterator = eventQueue.descendingIterator();
        for (idx = 0; xmlSecEventIterator.hasNext() && idx <= index; ++idx) {
            xmlSecEvent = xmlSecEventIterator.next();
        }
        while (xmlSecEventIterator.hasNext() && !(xmlSecEvent = xmlSecEventIterator.next()).isStartElement()) {
            ++idx;
        }
        if (xmlSecEvent == null) throw new WSSecurityException(WSSecurityException.ErrorCode.INVALID_SECURITY_TOKEN, "noKeyInSAMLToken");
        if (!xmlSecEvent.isStartElement()) {
            throw new WSSecurityException(WSSecurityException.ErrorCode.INVALID_SECURITY_TOKEN, "noKeyInSAMLToken");
        }
        XMLSecStartElement xmlSecStartElement = xmlSecEvent.asStartElement();
        QName elementName = xmlSecStartElement.getName();
        if (!WSSConstants.TAG_WST_BINARY_SECRET.equals(elementName) && !WSSConstants.TAG_WST0512_BINARY_SECRET.equals(elementName)) {
            Object object = null;
            try {
                Unmarshaller unmarshaller = WSSConstants.getJaxbUnmarshaller(securityProperties.isDisableSchemaValidation());
                object = unmarshaller.unmarshal(new XMLSecurityEventReader(eventQueue, idx));
            }
            catch (JAXBException e) {
                throw new WSSecurityException(WSSecurityException.ErrorCode.UNSUPPORTED_SECURITY_TOKEN, (Exception)e);
            }
            if (object instanceof JAXBElement) {
                object = ((JAXBElement)object).getValue();
            }
            KeyInfoType keyInfoType = null;
            if (object instanceof X509DataType) {
                JAXBElement<X509DataType> x509DataTypeJAXBElement = new ObjectFactory().createX509Data((X509DataType)object);
                keyInfoType = new KeyInfoType();
                SecurityTokenReferenceType securityTokenReferenceType = new SecurityTokenReferenceType();
                securityTokenReferenceType.getAny().add(x509DataTypeJAXBElement);
                JAXBElement<SecurityTokenReferenceType> securityTokenReferenceTypeJAXBElement = new org.apache.wss4j.binding.wss10.ObjectFactory().createSecurityTokenReference(securityTokenReferenceType);
                keyInfoType.getContent().add(securityTokenReferenceTypeJAXBElement);
                return SecurityTokenFactory.getInstance().getSecurityToken(keyInfoType, WSSecurityTokenConstants.KeyUsage_Signature_Verification, securityProperties, inputProcessorChain.getSecurityContext());
            }
            if (object instanceof EncryptedKeyType) {
                EncryptedKeyType encryptedKeyType = (EncryptedKeyType)object;
                WSSEncryptedKeyInputHandler encryptedKeyInputHandler = new WSSEncryptedKeyInputHandler();
                encryptedKeyInputHandler.handle(inputProcessorChain, encryptedKeyType, xmlSecStartElement, securityProperties);
                SecurityTokenProvider<? extends InboundSecurityToken> securityTokenProvider = inputProcessorChain.getSecurityContext().getSecurityTokenProvider(encryptedKeyType.getId());
                if (securityTokenProvider == null) return SecurityTokenFactory.getInstance().getSecurityToken(keyInfoType, WSSecurityTokenConstants.KeyUsage_Signature_Verification, securityProperties, inputProcessorChain.getSecurityContext());
                return securityTokenProvider.getSecurityToken();
            }
            if (object instanceof SecurityTokenReferenceType) {
                JAXBElement<SecurityTokenReferenceType> securityTokenReferenceTypeJAXBElement = new org.apache.wss4j.binding.wss10.ObjectFactory().createSecurityTokenReference((SecurityTokenReferenceType)object);
                keyInfoType = new KeyInfoType();
                keyInfoType.getContent().add(securityTokenReferenceTypeJAXBElement);
                return SecurityTokenFactory.getInstance().getSecurityToken(keyInfoType, WSSecurityTokenConstants.KeyUsage_Signature_Verification, securityProperties, inputProcessorChain.getSecurityContext());
            }
            if (!(object instanceof KeyValueType)) throw new WSSecurityException(WSSecurityException.ErrorCode.UNSUPPORTED_SECURITY_TOKEN, "unsupportedKeyInfo");
            JAXBElement<KeyValueType> keyValueTypeJAXBElement = new ObjectFactory().createKeyValue((KeyValueType)object);
            keyInfoType = new KeyInfoType();
            keyInfoType.getContent().add(keyValueTypeJAXBElement);
            return SecurityTokenFactory.getInstance().getSecurityToken(keyInfoType, WSSecurityTokenConstants.KeyUsage_Signature_Verification, securityProperties, inputProcessorChain.getSecurityContext());
        }
        final StringBuilder stringBuilder = new StringBuilder();
        while (xmlSecEventIterator.hasNext()) {
            xmlSecEvent = xmlSecEventIterator.next();
            switch (xmlSecEvent.getEventType()) {
                case 2: {
                    if (!xmlSecEvent.asEndElement().getName().equals(elementName)) break;
                    return new AbstractInboundSecurityToken(inputProcessorChain.getSecurityContext(), IDGenerator.generateID(null), WSSecurityTokenConstants.KeyIdentifier_NoKeyInfo, true){

                        @Override
                        public SecurityTokenConstants.TokenType getTokenType() {
                            return WSSecurityTokenConstants.DefaultToken;
                        }

                        @Override
                        public boolean isAsymmetric() throws XMLSecurityException {
                            return false;
                        }

                        @Override
                        protected Key getKey(String algorithmURI, XMLSecurityConstants.AlgorithmUsage algorithmUsage, String correlationID) throws XMLSecurityException {
                            Key key = super.getKey(algorithmURI, algorithmUsage, correlationID);
                            if (key == null) {
                                String algoFamily = JCEAlgorithmMapper.getJCEKeyAlgorithmFromURI(algorithmURI);
                                key = new SecretKeySpec(XMLUtils.decode(stringBuilder.toString()), algoFamily);
                                this.setSecretKey(algorithmURI, key);
                            }
                            return key;
                        }
                    };
                }
                case 4: {
                    stringBuilder.append(xmlSecEvent.asCharacters().getText());
                }
            }
        }
        return new /* invalid duplicate definition of identical inner class */;
    }

    @Override
    protected <T> T parseStructure(Deque<XMLSecEvent> eventDeque, int index, XMLSecurityProperties securityProperties) throws XMLSecurityException {
        Document document = null;
        try {
            document = XMLUtils.newDocument();
        }
        catch (ParserConfigurationException e) {
            throw new WSSecurityException(WSSecurityException.ErrorCode.INVALID_SECURITY_TOKEN, (Exception)e);
        }
        Iterator<XMLSecEvent> xmlSecEventIterator = eventDeque.descendingIterator();
        int curIdx = 0;
        while (curIdx++ < index) {
            xmlSecEventIterator.next();
        }
        Node currentNode = document;
        while (xmlSecEventIterator.hasNext()) {
            XMLSecEvent next = xmlSecEventIterator.next();
            currentNode = this.parseXMLEvent(next, currentNode, document);
        }
        return (T)document;
    }

    public Node parseXMLEvent(XMLSecEvent xmlSecEvent, Node currentNode, Document document) throws WSSecurityException {
        switch (xmlSecEvent.getEventType()) {
            case 1: {
                XMLSecStartElement xmlSecStartElement = xmlSecEvent.asStartElement();
                Element element = document.createElementNS(xmlSecStartElement.getName().getNamespaceURI(), xmlSecStartElement.getName().getLocalPart());
                if (xmlSecStartElement.getName().getPrefix() != null && !xmlSecStartElement.getName().getPrefix().isEmpty()) {
                    element.setPrefix(xmlSecStartElement.getName().getPrefix());
                }
                currentNode = currentNode.appendChild(element);
                Iterator namespaceIterator = xmlSecStartElement.getNamespaces();
                while (namespaceIterator.hasNext()) {
                    XMLSecNamespace next = (XMLSecNamespace)namespaceIterator.next();
                    this.parseXMLEvent(next, currentNode, document);
                }
                Iterator attributesIterator = xmlSecStartElement.getAttributes();
                while (attributesIterator.hasNext()) {
                    XMLSecAttribute next = (XMLSecAttribute)attributesIterator.next();
                    this.parseXMLEvent(next, currentNode, document);
                }
                String elementNs = document.lookupNamespaceURI(xmlSecStartElement.getName().getPrefix());
                if (elementNs != null) break;
                this.parseXMLEvent(xmlSecStartElement.getElementNamespace(), currentNode, document);
                break;
            }
            case 2: {
                if (currentNode.getParentNode() == null) break;
                currentNode = currentNode.getParentNode();
                break;
            }
            case 3: {
                ProcessingInstruction piNode = document.createProcessingInstruction(((javax.xml.stream.events.ProcessingInstruction)((Object)xmlSecEvent)).getTarget(), ((javax.xml.stream.events.ProcessingInstruction)((Object)xmlSecEvent)).getTarget());
                currentNode.appendChild(piNode);
                break;
            }
            case 4: {
                Text characterNode = document.createTextNode(xmlSecEvent.asCharacters().getData());
                currentNode.appendChild(characterNode);
                break;
            }
            case 5: {
                Comment commentNode = document.createComment(((javax.xml.stream.events.Comment)((Object)xmlSecEvent)).getText());
                currentNode.appendChild(commentNode);
                break;
            }
            case 7: {
                break;
            }
            case 8: {
                return currentNode;
            }
            case 10: {
                XMLSecAttribute xmlSecAttribute = (XMLSecAttribute)xmlSecEvent;
                Attr attributeNode = document.createAttributeNS(xmlSecAttribute.getName().getNamespaceURI(), xmlSecAttribute.getName().getLocalPart());
                attributeNode.setPrefix(xmlSecAttribute.getName().getPrefix());
                attributeNode.setValue(xmlSecAttribute.getValue());
                ((Element)currentNode).setAttributeNodeNS(attributeNode);
                String attrNs = document.lookupNamespaceURI(xmlSecAttribute.getName().getPrefix());
                if (attrNs != null) break;
                this.parseXMLEvent(xmlSecAttribute.getAttributeNamespace(), currentNode, document);
                break;
            }
            case 11: {
                break;
            }
            case 13: {
                Namespace namespace = (Namespace)((Object)xmlSecEvent);
                String prefix = namespace.getPrefix();
                Attr namespaceNode = prefix == null || prefix.isEmpty() ? document.createAttributeNS("http://www.w3.org/2000/xmlns/", "xmlns") : document.createAttributeNS("http://www.w3.org/2000/xmlns/", "xmlns:" + prefix);
                namespaceNode.setValue(namespace.getNamespaceURI());
                ((Element)currentNode).setAttributeNodeNS(namespaceNode);
                break;
            }
            default: {
                throw new WSSecurityException(WSSecurityException.ErrorCode.INVALID_SECURITY_TOKEN, "empty", new Object[]{"Illegal XMLEvent received: " + xmlSecEvent.getEventType()});
            }
        }
        return currentNode;
    }

    static {
        SAML1_TOKEN_PATH.add(WSSConstants.TAG_SAML_ASSERTION);
        SAML2_TOKEN_PATH.add(WSSConstants.TAG_SAML2_ASSERTION);
    }

    static class SAMLTokenVerifierInputProcessor
    extends AbstractInputProcessor
    implements SecurityEventListener {
        private SamlAssertionWrapper samlAssertionWrapper;
        private SecurityTokenProvider<InboundSecurityToken> securityTokenProvider;
        private InboundSecurityToken subjectSecurityToken;
        private List<SignedElementSecurityEvent> samlTokenSignedElementSecurityEvents = new ArrayList<SignedElementSecurityEvent>();
        private SignedPartSecurityEvent bodySignedPartSecurityEvent;

        SAMLTokenVerifierInputProcessor(XMLSecurityProperties securityProperties, SamlAssertionWrapper samlAssertionWrapper, SecurityTokenProvider<InboundSecurityToken> securityTokenProvider, InboundSecurityToken subjectSecurityToken) {
            super(securityProperties);
            this.setPhase(XMLSecurityConstants.Phase.POSTPROCESSING);
            this.addAfterProcessor(OperationInputProcessor.class.getName());
            this.samlAssertionWrapper = samlAssertionWrapper;
            this.securityTokenProvider = securityTokenProvider;
            this.subjectSecurityToken = subjectSecurityToken;
        }

        @Override
        public void registerSecurityEvent(SecurityEvent securityEvent) throws XMLSecurityException {
            if (WSSecurityEventConstants.SIGNED_PART.equals(securityEvent.getSecurityEventType())) {
                SignedPartSecurityEvent signedPartSecurityEvent = (SignedPartSecurityEvent)securityEvent;
                List<QName> elementPath = signedPartSecurityEvent.getElementPath();
                if (WSSUtils.pathMatches(WSSConstants.SOAP_11_BODY_PATH, elementPath, true, false)) {
                    this.bodySignedPartSecurityEvent = signedPartSecurityEvent;
                }
            } else if (WSSecurityEventConstants.SignedElement.equals(securityEvent.getSecurityEventType())) {
                SignedElementSecurityEvent signedPartSecurityEvent = (SignedElementSecurityEvent)securityEvent;
                List<QName> elementPath = signedPartSecurityEvent.getElementPath();
                if (WSSUtils.pathMatches(SAML2_TOKEN_PATH, elementPath, true, false) || WSSUtils.pathMatches(SAML1_TOKEN_PATH, elementPath, true, false)) {
                    this.samlTokenSignedElementSecurityEvents.add(signedPartSecurityEvent);
                }
            }
        }

        @Override
        public XMLSecEvent processNextHeaderEvent(InputProcessorChain inputProcessorChain) throws XMLStreamException, XMLSecurityException {
            return inputProcessorChain.processHeaderEvent();
        }

        @Override
        public XMLSecEvent processNextEvent(InputProcessorChain inputProcessorChain) throws XMLStreamException, XMLSecurityException {
            XMLSecStartElement xmlSecStartElement;
            List<QName> elementPath;
            XMLSecEvent xmlSecEvent = inputProcessorChain.processEvent();
            if (xmlSecEvent.getEventType() == 1 && (elementPath = (xmlSecStartElement = xmlSecEvent.asStartElement()).getElementPath()).size() == 3 && WSSUtils.isInSOAPBody(elementPath)) {
                inputProcessorChain.removeProcessor(this);
                this.checkPossessionOfKey(inputProcessorChain, this.samlAssertionWrapper, this.subjectSecurityToken);
            }
            return xmlSecEvent;
        }

        private void checkPossessionOfKey(InputProcessorChain inputProcessorChain, SamlAssertionWrapper samlAssertionWrapper, InboundSecurityToken subjectSecurityToken) throws WSSecurityException {
            boolean methodNotSatisfied = false;
            try {
                SecurityToken httpsSecurityToken = this.getHttpsSecurityToken(inputProcessorChain);
                List<SecurityTokenProvider<? extends InboundSecurityToken>> securityTokenProviders = inputProcessorChain.getSecurityContext().getRegisteredSecurityTokenProviders();
                List<String> confirmationMethods = samlAssertionWrapper.getConfirmationMethods();
                for (int i = 0; i < confirmationMethods.size(); ++i) {
                    String confirmationMethod = confirmationMethods.get(i);
                    if (OpenSAMLUtil.isMethodHolderOfKey(confirmationMethod)) {
                        X509Certificate[] subjectCertificates = subjectSecurityToken.getX509Certificates();
                        PublicKey subjectPublicKey = subjectSecurityToken.getPublicKey();
                        Key subjectSecretKey = null;
                        Map<String, Key> subjectKeyMap = subjectSecurityToken.getSecretKey();
                        if (!subjectKeyMap.isEmpty()) {
                            subjectSecretKey = subjectKeyMap.values().toArray(new Key[subjectKeyMap.size()])[0];
                        }
                        if (httpsSecurityToken != null && httpsSecurityToken.getX509Certificates() != null && httpsSecurityToken.getX509Certificates().length > 0) {
                            X509Certificate httpsCertificate = httpsSecurityToken.getX509Certificates()[0];
                            if (subjectCertificates != null && subjectCertificates.length > 0 && httpsCertificate.equals(subjectCertificates[0])) {
                                return;
                            }
                            if (httpsCertificate.getPublicKey().equals(subjectPublicKey)) {
                                return;
                            }
                        }
                        for (int j = 0; j < securityTokenProviders.size(); ++j) {
                            SecurityTokenProvider<? extends InboundSecurityToken> securityTokenProvider = securityTokenProviders.get(j);
                            InboundSecurityToken securityToken = securityTokenProvider.getSecurityToken();
                            if (securityToken == httpsSecurityToken || securityToken == subjectSecurityToken || !this.containsSignature(securityToken.getTokenUsages())) continue;
                            X509Certificate[] x509Certificates = securityToken.getX509Certificates();
                            PublicKey publicKey = securityToken.getPublicKey();
                            Map<String, Key> keyMap = securityToken.getSecretKey();
                            if (x509Certificates != null && x509Certificates.length > 0 && subjectCertificates != null && subjectCertificates.length > 0 && subjectCertificates[0].equals(x509Certificates[0])) {
                                return;
                            }
                            if (publicKey != null && publicKey.equals(subjectPublicKey)) {
                                return;
                            }
                            for (Map.Entry<String, Key> next : keyMap.entrySet()) {
                                if (!next.getValue().equals(subjectSecretKey)) continue;
                                return;
                            }
                        }
                        methodNotSatisfied = true;
                        continue;
                    }
                    if (!OpenSAMLUtil.isMethodSenderVouches(confirmationMethod)) continue;
                    if (httpsSecurityToken != null && httpsSecurityToken.getX509Certificates() != null && httpsSecurityToken.getX509Certificates().length > 0) {
                        return;
                    }
                    AbstractSecuredElementSecurityEvent samlTokenSignedElementSecurityEvent = null;
                    for (int j = 0; j < this.samlTokenSignedElementSecurityEvents.size(); ++j) {
                        SignedElementSecurityEvent signedElementSecurityEvent = this.samlTokenSignedElementSecurityEvents.get(j);
                        if (this.securityTokenProvider.getSecurityToken().getXMLSecEvent() != signedElementSecurityEvent.getXmlSecEvent()) continue;
                        samlTokenSignedElementSecurityEvent = signedElementSecurityEvent;
                    }
                    if (this.bodySignedPartSecurityEvent != null && samlTokenSignedElementSecurityEvent != null && this.bodySignedPartSecurityEvent.getSecurityToken() == samlTokenSignedElementSecurityEvent.getSecurityToken()) {
                        return;
                    }
                    methodNotSatisfied = true;
                }
            }
            catch (XMLSecurityException e) {
                throw new WSSecurityException(WSSecurityException.ErrorCode.INVALID_SECURITY, (Exception)e);
            }
            if (methodNotSatisfied) {
                throw new WSSecurityException(WSSecurityException.ErrorCode.FAILED_AUTHENTICATION, "empty", new Object[]{"SAML proof-of-possession of the private/secret key failed"});
            }
        }

        private SecurityToken getHttpsSecurityToken(InputProcessorChain inputProcessorChain) throws XMLSecurityException {
            List<SecurityTokenProvider<? extends InboundSecurityToken>> securityTokenProviders = inputProcessorChain.getSecurityContext().getRegisteredSecurityTokenProviders();
            for (int i = 0; i < securityTokenProviders.size(); ++i) {
                SecurityTokenProvider<? extends InboundSecurityToken> securityTokenProvider = securityTokenProviders.get(i);
                SecurityToken securityToken = securityTokenProvider.getSecurityToken();
                if (!WSSecurityTokenConstants.HTTPS_TOKEN.equals(securityToken.getTokenType())) continue;
                return securityToken;
            }
            return null;
        }

        private boolean containsSignature(List<SecurityTokenConstants.TokenUsage> tokenUses) {
            return tokenUses.contains(WSSecurityTokenConstants.TOKENUSAGE_MAIN_SIGNATURE) || tokenUses.contains(WSSecurityTokenConstants.TokenUsage_Signature) || tokenUses.contains(WSSecurityTokenConstants.TOKENUSAGE_ENDORSING_ENCRYPTED_SUPPORTING_TOKENS) || tokenUses.contains(WSSecurityTokenConstants.TOKENUSAGE_ENDORSING_SUPPORTING_TOKENS) || tokenUses.contains(WSSecurityTokenConstants.TOKENUSAGE_SIGNED_ENDORSING_ENCRYPTED_SUPPORTING_TOKENS) || tokenUses.contains(WSSecurityTokenConstants.TOKENUSAGE_SIGNED_ENDORSING_SUPPORTING_TOKENS);
        }
    }
}

