/*
 * Decompiled with CFR 0.152.
 */
package org.apache.wss4j.stax.impl.processor.output;

import java.util.ArrayList;
import java.util.List;
import javax.xml.namespace.QName;
import javax.xml.stream.XMLStreamException;
import org.apache.wss4j.common.ext.WSSecurityException;
import org.apache.wss4j.stax.ext.WSSConstants;
import org.apache.wss4j.stax.ext.WSSSecurityProperties;
import org.apache.wss4j.stax.utils.WSSUtils;
import org.apache.xml.security.exceptions.XMLSecurityException;
import org.apache.xml.security.stax.ext.AbstractOutputProcessor;
import org.apache.xml.security.stax.ext.OutputProcessorChain;
import org.apache.xml.security.stax.ext.SecurePart;
import org.apache.xml.security.stax.ext.XMLSecurityConstants;
import org.apache.xml.security.stax.ext.stax.XMLSecAttribute;
import org.apache.xml.security.stax.ext.stax.XMLSecEndElement;
import org.apache.xml.security.stax.ext.stax.XMLSecEvent;
import org.apache.xml.security.stax.ext.stax.XMLSecStartElement;

public class SecurityHeaderOutputProcessor
extends AbstractOutputProcessor {
    public SecurityHeaderOutputProcessor() throws XMLSecurityException {
        this.setPhase(XMLSecurityConstants.Phase.PREPROCESSING);
    }

    @Override
    public void processEvent(XMLSecEvent xmlSecEvent, OutputProcessorChain outputProcessorChain) throws XMLStreamException, XMLSecurityException {
        boolean eventHandled = false;
        switch (xmlSecEvent.getEventType()) {
            case 1: {
                XMLSecStartElement xmlSecStartElement = xmlSecEvent.asStartElement();
                String soapMessageVersion = WSSUtils.getSOAPMessageVersionNamespace(xmlSecStartElement);
                int level = xmlSecStartElement.getDocumentLevel();
                if (level == 1 && soapMessageVersion == null) {
                    throw new WSSecurityException(WSSecurityException.ErrorCode.FAILURE, "notASOAPMessage");
                }
                if (level == 1) {
                    List<SecurePart> signatureParts;
                    List<SecurePart> encryptionParts = this.securityProperties.getEncryptionSecureParts();
                    if (!encryptionParts.isEmpty()) {
                        for (int i = 0; i < encryptionParts.size(); ++i) {
                            SecurePart convertedPart;
                            SecurePart securePart = encryptionParts.get(i);
                            if (securePart != (convertedPart = this.convertSecurePart(securePart, soapMessageVersion))) {
                                securePart = convertedPart;
                                encryptionParts.set(i, securePart);
                            }
                            if (securePart.getIdToSign() == null) {
                                outputProcessorChain.getSecurityContext().putAsMap("encryptionParts", securePart.getName(), securePart);
                                continue;
                            }
                            outputProcessorChain.getSecurityContext().putAsMap("encryptionParts", securePart.getIdToSign(), securePart);
                        }
                    }
                    if ((signatureParts = this.securityProperties.getSignatureSecureParts()).isEmpty()) break;
                    for (int i = 0; i < signatureParts.size(); ++i) {
                        SecurePart convertedPart;
                        SecurePart securePart = signatureParts.get(i);
                        if (securePart != (convertedPart = this.convertSecurePart(securePart, soapMessageVersion))) {
                            securePart = convertedPart;
                            signatureParts.set(i, securePart);
                        }
                        if (securePart.getIdToSign() == null) {
                            outputProcessorChain.getSecurityContext().putAsMap("signatureParts", securePart.getName(), securePart);
                            continue;
                        }
                        outputProcessorChain.getSecurityContext().putAsMap("signatureParts", securePart.getIdToSign(), securePart);
                    }
                    break;
                }
                if (WSSUtils.isSecurityHeaderElement(xmlSecEvent, ((WSSSecurityProperties)this.getSecurityProperties()).getActor())) {
                    outputProcessorChain.removeProcessor(this);
                    break;
                }
                if (level != 2 || !"Body".equals(xmlSecStartElement.getName().getLocalPart()) || !xmlSecStartElement.getName().getNamespaceURI().equals(soapMessageVersion)) break;
                OutputProcessorChain subOutputProcessorChain = outputProcessorChain.createSubChain(this, xmlSecStartElement.getParentXMLSecStartElement());
                this.createStartElementAndOutputAsEvent(subOutputProcessorChain, new QName(soapMessageVersion, "Header", "soap"), true, null);
                boolean mustUnderstand = ((WSSSecurityProperties)this.getSecurityProperties()).isMustUnderstand();
                this.buildSecurityHeader(soapMessageVersion, subOutputProcessorChain, mustUnderstand);
                this.createEndElementAndOutputAsEvent(subOutputProcessorChain, new QName(soapMessageVersion, "Header", "soap"));
                outputProcessorChain.processEvent(xmlSecEvent);
                outputProcessorChain.removeProcessor(this);
                eventHandled = true;
                break;
            }
            case 2: {
                XMLSecEndElement xmlSecEndElement = xmlSecEvent.asEndElement();
                int documentLevel = xmlSecEndElement.getDocumentLevel();
                String soapMessageVersionNS = WSSUtils.getSOAPMessageVersionNamespace(xmlSecEndElement.getParentXMLSecStartElement());
                if (documentLevel != 2 || !"Header".equals(xmlSecEndElement.getName().getLocalPart()) || !xmlSecEndElement.getName().getNamespaceURI().equals(soapMessageVersionNS)) break;
                OutputProcessorChain subOutputProcessorChain = outputProcessorChain.createSubChain(this);
                boolean mustUnderstand = ((WSSSecurityProperties)this.getSecurityProperties()).isMustUnderstand();
                this.buildSecurityHeader(xmlSecEndElement.getName().getNamespaceURI(), subOutputProcessorChain, mustUnderstand);
                outputProcessorChain.processEvent(xmlSecEvent);
                outputProcessorChain.removeProcessor(this);
                eventHandled = true;
            }
        }
        if (!eventHandled) {
            outputProcessorChain.processEvent(xmlSecEvent);
        }
    }

    private SecurePart convertSecurePart(SecurePart securePart, String soapVersion) {
        QName secureName = securePart.getName();
        if (secureName == null) {
            return securePart;
        }
        QName newName = secureName;
        if ("http://schemas.xmlsoap.org/soap/envelope/".equals(secureName.getNamespaceURI()) && "http://www.w3.org/2003/05/soap-envelope".equals(soapVersion)) {
            newName = new QName(soapVersion, secureName.getLocalPart(), secureName.getPrefix());
        } else if ("http://www.w3.org/2003/05/soap-envelope".equals(secureName.getNamespaceURI()) && "http://schemas.xmlsoap.org/soap/envelope/".equals(soapVersion)) {
            newName = new QName(soapVersion, secureName.getLocalPart(), secureName.getPrefix());
        }
        if (!secureName.equals(newName)) {
            SecurePart newPart = new SecurePart(newName, securePart.isGenerateXPointer(), securePart.getModifier(), securePart.getTransforms(), securePart.getDigestMethod());
            newPart.setExternalReference(securePart.getExternalReference());
            newPart.setIdToReference(securePart.getIdToReference());
            newPart.setIdToSign(securePart.getIdToSign());
            return newPart;
        }
        return securePart;
    }

    private void buildSecurityHeader(String soapMessageVersion, OutputProcessorChain subOutputProcessorChain, boolean mustUnderstand) throws XMLStreamException, XMLSecurityException {
        ArrayList<XMLSecAttribute> attributes = new ArrayList<XMLSecAttribute>(1);
        String actor = ((WSSSecurityProperties)this.getSecurityProperties()).getActor();
        if (actor != null && !actor.isEmpty()) {
            if ("http://schemas.xmlsoap.org/soap/envelope/".equals(soapMessageVersion)) {
                attributes.add(this.createAttribute(WSSConstants.ATT_SOAP11_ACTOR, actor));
            } else {
                attributes.add(this.createAttribute(WSSConstants.ATT_SOAP12_ROLE, actor));
            }
        }
        if (mustUnderstand) {
            if ("http://schemas.xmlsoap.org/soap/envelope/".equals(soapMessageVersion)) {
                attributes.add(this.createAttribute(WSSConstants.ATT_SOAP11_MUST_UNDERSTAND, "1"));
            } else {
                attributes.add(this.createAttribute(WSSConstants.ATT_SOAP12_MUST_UNDERSTAND, "true"));
            }
        }
        this.createStartElementAndOutputAsEvent(subOutputProcessorChain, WSSConstants.TAG_WSSE_SECURITY, true, attributes);
        this.createEndElementAndOutputAsEvent(subOutputProcessorChain, WSSConstants.TAG_WSSE_SECURITY);
    }
}

