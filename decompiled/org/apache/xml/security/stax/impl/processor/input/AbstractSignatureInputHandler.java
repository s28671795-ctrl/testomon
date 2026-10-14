/*
 * Decompiled with CFR 0.152.
 */
package org.apache.xml.security.stax.impl.processor.input;

import java.io.IOException;
import java.io.OutputStream;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.Iterator;
import javax.xml.bind.JAXBElement;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import org.apache.xml.security.binding.excc14n.InclusiveNamespaces;
import org.apache.xml.security.binding.xmldsig.CanonicalizationMethodType;
import org.apache.xml.security.binding.xmldsig.SignatureType;
import org.apache.xml.security.binding.xmldsig.SignedInfoType;
import org.apache.xml.security.exceptions.XMLSecurityException;
import org.apache.xml.security.stax.ext.AbstractInputSecurityHeaderHandler;
import org.apache.xml.security.stax.ext.InboundSecurityContext;
import org.apache.xml.security.stax.ext.InputProcessorChain;
import org.apache.xml.security.stax.ext.Transformer;
import org.apache.xml.security.stax.ext.XMLSecurityConstants;
import org.apache.xml.security.stax.ext.XMLSecurityProperties;
import org.apache.xml.security.stax.ext.XMLSecurityUtils;
import org.apache.xml.security.stax.ext.stax.XMLSecEvent;
import org.apache.xml.security.stax.ext.stax.XMLSecEventFactory;
import org.apache.xml.security.stax.impl.algorithms.SignatureAlgorithm;
import org.apache.xml.security.stax.impl.algorithms.SignatureAlgorithmFactory;
import org.apache.xml.security.stax.impl.util.IDGenerator;
import org.apache.xml.security.stax.impl.util.SignerOutputStream;
import org.apache.xml.security.stax.securityToken.InboundSecurityToken;
import org.apache.xml.security.utils.UnsyncBufferedOutputStream;
import org.apache.xml.security.utils.UnsyncByteArrayInputStream;
import org.apache.xml.security.utils.UnsyncByteArrayOutputStream;

public abstract class AbstractSignatureInputHandler
extends AbstractInputSecurityHeaderHandler {
    @Override
    public void handle(InputProcessorChain inputProcessorChain, XMLSecurityProperties securityProperties, Deque<XMLSecEvent> eventQueue, Integer index) throws XMLSecurityException {
        SignatureType signatureType = (SignatureType)((JAXBElement)this.parseStructure(eventQueue, index, securityProperties)).getValue();
        if (signatureType.getSignedInfo() == null) {
            throw new XMLSecurityException("stax.signature.signedInfoMissing");
        }
        if (signatureType.getSignedInfo().getSignatureMethod() == null) {
            throw new XMLSecurityException("stax.signature.signatureMethodMissing");
        }
        if (signatureType.getSignedInfo().getCanonicalizationMethod() == null) {
            throw new XMLSecurityException("stax.signature.canonicalizationMethodMissing");
        }
        if (signatureType.getSignatureValue() == null) {
            throw new XMLSecurityException("stax.signature.signatureValueMissing");
        }
        if (signatureType.getId() == null) {
            signatureType.setId(IDGenerator.generateID(null));
        }
        InboundSecurityToken inboundSecurityToken = this.verifySignedInfo(inputProcessorChain, securityProperties, signatureType, eventQueue, index);
        this.addSignatureReferenceInputProcessorToChain(inputProcessorChain, securityProperties, signatureType, inboundSecurityToken);
    }

    protected abstract void addSignatureReferenceInputProcessorToChain(InputProcessorChain var1, XMLSecurityProperties var2, SignatureType var3, InboundSecurityToken var4) throws XMLSecurityException;

    protected InboundSecurityToken verifySignedInfo(InputProcessorChain inputProcessorChain, XMLSecurityProperties securityProperties, SignatureType signatureType, Deque<XMLSecEvent> eventDeque, int index) throws XMLSecurityException {
        Iterator<XMLSecEvent> iterator2;
        String c14NMethod = signatureType.getSignedInfo().getCanonicalizationMethod().getAlgorithm();
        if ("http://www.w3.org/TR/2001/REC-xml-c14n-20010315".equals(c14NMethod) || "http://www.w3.org/TR/2001/REC-xml-c14n-20010315#WithComments".equals(c14NMethod) || "http://www.w3.org/2001/10/xml-exc-c14n#".equals(c14NMethod) || "http://www.w3.org/2001/10/xml-exc-c14n#WithComments".equals(c14NMethod) || "http://www.w3.org/2006/12/xml-c14n11".equals(c14NMethod) || "http://www.w3.org/2006/12/xml-c14n11#WithComments".equals(c14NMethod)) {
            iterator2 = eventDeque.descendingIterator();
            for (int i = 0; i < index; ++i) {
                iterator2.next();
            }
        } else {
            iterator2 = this.reparseSignedInfo(inputProcessorChain, securityProperties, signatureType, eventDeque, index).descendingIterator();
            index = 0;
        }
        SignatureVerifier signatureVerifier = this.newSignatureVerifier(inputProcessorChain, securityProperties, signatureType);
        try {
            XMLSecEvent xmlSecEvent;
            block9: while (iterator2.hasNext()) {
                xmlSecEvent = iterator2.next();
                switch (xmlSecEvent.getEventType()) {
                    case 1: {
                        if (xmlSecEvent.asStartElement().getName().equals(XMLSecurityConstants.TAG_dsig_SignedInfo)) {
                            signatureVerifier.processEvent(xmlSecEvent);
                            break block9;
                        }
                    }
                    default: {
                        continue block9;
                    }
                }
            }
            block10: while (iterator2.hasNext()) {
                xmlSecEvent = iterator2.next();
                signatureVerifier.processEvent(xmlSecEvent);
                switch (xmlSecEvent.getEventType()) {
                    case 2: {
                        if (xmlSecEvent.asEndElement().getName().equals(XMLSecurityConstants.TAG_dsig_SignedInfo)) break;
                    }
                    default: {
                        continue block10;
                    }
                }
                break;
            }
        }
        catch (XMLStreamException e) {
            throw new XMLSecurityException(e);
        }
        signatureVerifier.doFinal();
        return signatureVerifier.getInboundSecurityToken();
    }

    /*
     * Enabled aggressive exception aggregation
     */
    protected Deque<XMLSecEvent> reparseSignedInfo(InputProcessorChain inputProcessorChain, XMLSecurityProperties securityProperties, SignatureType signatureType, Deque<XMLSecEvent> eventDeque, int index) throws XMLSecurityException {
        ArrayDeque<XMLSecEvent> signedInfoDeque = new ArrayDeque<XMLSecEvent>();
        try {
            Throwable throwable = null;
            try (UnsyncByteArrayOutputStream unsynchronizedByteArrayOutputStream = new UnsyncByteArrayOutputStream();){
                ArrayDeque<XMLSecEvent> arrayDeque;
                XMLSecEvent xmlSecEvent;
                Transformer transformer = XMLSecurityUtils.getTransformer(null, unsynchronizedByteArrayOutputStream, null, signatureType.getSignedInfo().getCanonicalizationMethod().getAlgorithm(), XMLSecurityConstants.DIRECTION.IN);
                Iterator<XMLSecEvent> iterator2 = eventDeque.descendingIterator();
                for (int i = 0; i < index; ++i) {
                    iterator2.next();
                }
                block19: while (iterator2.hasNext()) {
                    xmlSecEvent = iterator2.next();
                    switch (xmlSecEvent.getEventType()) {
                        case 1: {
                            if (xmlSecEvent.asStartElement().getName().equals(XMLSecurityConstants.TAG_dsig_SignedInfo)) {
                                transformer.transform(xmlSecEvent);
                                break block19;
                            }
                        }
                        default: {
                            continue block19;
                        }
                    }
                }
                block20: while (iterator2.hasNext()) {
                    xmlSecEvent = iterator2.next();
                    transformer.transform(xmlSecEvent);
                    switch (xmlSecEvent.getEventType()) {
                        case 2: {
                            if (xmlSecEvent.asEndElement().getName().equals(XMLSecurityConstants.TAG_dsig_SignedInfo)) break block20;
                        }
                        default: {
                            continue block20;
                        }
                    }
                }
                transformer.doFinal();
                UnsyncByteArrayInputStream is = new UnsyncByteArrayInputStream(unsynchronizedByteArrayOutputStream.toByteArray());
                Throwable throwable2 = null;
                try {
                    XMLStreamReader xmlStreamReader = ((XMLInputFactory)inputProcessorChain.getSecurityContext().get("XMLInputFactory")).createXMLStreamReader(is);
                    while (xmlStreamReader.hasNext()) {
                        XMLSecEvent xmlSecEvent2 = XMLSecEventFactory.allocate(xmlStreamReader, null);
                        signedInfoDeque.push(xmlSecEvent2);
                        xmlStreamReader.next();
                    }
                    SignedInfoType signedInfoType = (SignedInfoType)((JAXBElement)this.parseStructure(signedInfoDeque, 0, securityProperties)).getValue();
                    signatureType.setSignedInfo(signedInfoType);
                    arrayDeque = signedInfoDeque;
                }
                catch (Throwable throwable3) {
                    try {
                        try {
                            throwable2 = throwable3;
                            throw throwable3;
                        }
                        catch (Throwable throwable4) {
                            AbstractSignatureInputHandler.$closeResource(throwable2, is);
                            throw throwable4;
                        }
                    }
                    catch (Throwable throwable5) {
                        throwable = throwable5;
                        throw throwable5;
                    }
                }
                AbstractSignatureInputHandler.$closeResource(throwable2, is);
                return arrayDeque;
            }
        }
        catch (IOException | XMLStreamException e) {
            throw new XMLSecurityException(e);
        }
    }

    protected abstract SignatureVerifier newSignatureVerifier(InputProcessorChain var1, XMLSecurityProperties var2, SignatureType var3) throws XMLSecurityException;

    public abstract class SignatureVerifier {
        private final SignatureType signatureType;
        private final InboundSecurityToken inboundSecurityToken;
        private SignerOutputStream signerOutputStream;
        private OutputStream bufferedSignerOutputStream;
        private Transformer transformer;

        public SignatureVerifier(SignatureType signatureType, InboundSecurityContext inboundSecurityContext, XMLSecurityProperties securityProperties) throws XMLSecurityException {
            InboundSecurityToken inboundSecurityToken;
            this.signatureType = signatureType;
            this.inboundSecurityToken = inboundSecurityToken = this.retrieveSecurityToken(signatureType, securityProperties, inboundSecurityContext);
            this.createSignatureAlgorithm(inboundSecurityToken, signatureType);
        }

        protected abstract InboundSecurityToken retrieveSecurityToken(SignatureType var1, XMLSecurityProperties var2, InboundSecurityContext var3) throws XMLSecurityException;

        public InboundSecurityToken getInboundSecurityToken() {
            return this.inboundSecurityToken;
        }

        protected void createSignatureAlgorithm(InboundSecurityToken inboundSecurityToken, SignatureType signatureType) throws XMLSecurityException {
            Key verifyKey;
            String algorithmURI = signatureType.getSignedInfo().getSignatureMethod().getAlgorithm();
            if (inboundSecurityToken.isAsymmetric()) {
                verifyKey = inboundSecurityToken.getPublicKey(algorithmURI, XMLSecurityConstants.Asym_Sig, signatureType.getId());
            } else {
                verifyKey = inboundSecurityToken.getSecretKey(algorithmURI, XMLSecurityConstants.Sym_Sig, signatureType.getId());
                if (verifyKey != null) {
                    verifyKey = XMLSecurityUtils.prepareSecretKey(algorithmURI, verifyKey.getEncoded());
                }
            }
            if (verifyKey == null) {
                throw new XMLSecurityException("KeyInfo.nokey", new Object[]{"the inbound security token"});
            }
            try {
                SignatureAlgorithm signatureAlgorithm = SignatureAlgorithmFactory.getInstance().getSignatureAlgorithm(algorithmURI);
                signatureAlgorithm.engineInitVerify(verifyKey);
                this.signerOutputStream = new SignerOutputStream(signatureAlgorithm);
                this.bufferedSignerOutputStream = new UnsyncBufferedOutputStream(this.signerOutputStream);
                CanonicalizationMethodType canonicalizationMethodType = signatureType.getSignedInfo().getCanonicalizationMethod();
                InclusiveNamespaces inclusiveNamespacesType = (InclusiveNamespaces)XMLSecurityUtils.getQNameType(canonicalizationMethodType.getContent(), XMLSecurityConstants.TAG_c14nExcl_InclusiveNamespaces);
                HashMap<String, Object> transformerProperties = null;
                if (inclusiveNamespacesType != null) {
                    transformerProperties = new HashMap<String, Object>();
                    transformerProperties.put("inclusiveNamespacePrefixList", inclusiveNamespacesType.getPrefixList());
                }
                this.transformer = XMLSecurityUtils.getTransformer(null, this.bufferedSignerOutputStream, transformerProperties, canonicalizationMethodType.getAlgorithm(), XMLSecurityConstants.DIRECTION.IN);
            }
            catch (NoSuchAlgorithmException e) {
                throw new XMLSecurityException(e);
            }
            catch (NoSuchProviderException e) {
                throw new XMLSecurityException(e);
            }
        }

        protected void processEvent(XMLSecEvent xmlSecEvent) throws XMLStreamException {
            this.transformer.transform(xmlSecEvent);
        }

        protected void doFinal() throws XMLSecurityException {
            try {
                this.transformer.doFinal();
                this.bufferedSignerOutputStream.close();
            }
            catch (IOException e) {
                throw new XMLSecurityException(e);
            }
            catch (XMLStreamException e) {
                throw new XMLSecurityException(e);
            }
            if (!this.signerOutputStream.verify(this.signatureType.getSignatureValue().getValue())) {
                throw new XMLSecurityException("errorMessages.InvalidSignatureValueException");
            }
        }
    }
}

