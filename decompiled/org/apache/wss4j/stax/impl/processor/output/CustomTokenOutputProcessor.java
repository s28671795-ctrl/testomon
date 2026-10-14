/*
 * Decompiled with CFR 0.152.
 */
package org.apache.wss4j.stax.impl.processor.output;

import java.util.ArrayList;
import javax.xml.namespace.QName;
import javax.xml.stream.XMLStreamException;
import org.apache.wss4j.common.ext.WSPasswordCallback;
import org.apache.wss4j.common.ext.WSSecurityException;
import org.apache.wss4j.stax.ext.WSSSecurityProperties;
import org.apache.wss4j.stax.impl.processor.output.EncryptedKeyOutputProcessor;
import org.apache.wss4j.stax.impl.processor.output.OutputProcessorUtils;
import org.apache.wss4j.stax.impl.processor.output.WSSSignatureOutputProcessor;
import org.apache.wss4j.stax.utils.WSSUtils;
import org.apache.xml.security.exceptions.XMLSecurityException;
import org.apache.xml.security.stax.ext.AbstractOutputProcessor;
import org.apache.xml.security.stax.ext.OutputProcessorChain;
import org.apache.xml.security.stax.ext.stax.XMLSecAttribute;
import org.apache.xml.security.stax.ext.stax.XMLSecEvent;
import org.apache.xml.security.stax.ext.stax.XMLSecNamespace;
import org.w3c.dom.Attr;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.Text;

public class CustomTokenOutputProcessor
extends AbstractOutputProcessor {
    public CustomTokenOutputProcessor() throws XMLSecurityException {
        this.addBeforeProcessor(WSSSignatureOutputProcessor.class.getName());
        this.addBeforeProcessor(EncryptedKeyOutputProcessor.class.getName());
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void processEvent(XMLSecEvent xmlSecEvent, OutputProcessorChain outputProcessorChain) throws XMLStreamException, XMLSecurityException {
        try {
            String tokenId = (String)outputProcessorChain.getSecurityContext().get("PROP_USE_THIS_TOKEN_ID_FOR_CUSTOM_TOKEN");
            if (tokenId == null) {
                throw new WSSecurityException(WSSecurityException.ErrorCode.FAILURE);
            }
            WSPasswordCallback wsPasswordCallback = new WSPasswordCallback(tokenId, 7);
            WSSUtils.doPasswordCallback(((WSSSecurityProperties)this.getSecurityProperties()).getCallbackHandler(), wsPasswordCallback);
            Element customToken = wsPasswordCallback.getCustomToken();
            if (customToken == null) {
                throw new WSSecurityException(WSSecurityException.ErrorCode.FAILURE);
            }
            FinalUnknownTokenOutputProcessor outputProcessor = new FinalUnknownTokenOutputProcessor(customToken);
            outputProcessor.setXMLSecurityProperties(this.getSecurityProperties());
            outputProcessor.setAction(this.getAction());
            outputProcessor.addBeforeProcessor(WSSSignatureOutputProcessor.class.getName());
            outputProcessor.addBeforeProcessor(EncryptedKeyOutputProcessor.class.getName());
            outputProcessor.init(outputProcessorChain);
        }
        finally {
            outputProcessorChain.removeProcessor(this);
        }
        outputProcessorChain.processEvent(xmlSecEvent);
    }

    static class FinalUnknownTokenOutputProcessor
    extends AbstractOutputProcessor {
        private final Element token;

        FinalUnknownTokenOutputProcessor(Element token) throws XMLSecurityException {
            this.addAfterProcessor(CustomTokenOutputProcessor.class.getName());
            this.token = token;
        }

        @Override
        public void processEvent(XMLSecEvent xmlSecEvent, OutputProcessorChain outputProcessorChain) throws XMLStreamException, XMLSecurityException {
            outputProcessorChain.processEvent(xmlSecEvent);
            if (WSSUtils.isSecurityHeaderElement(xmlSecEvent, ((WSSSecurityProperties)this.getSecurityProperties()).getActor())) {
                QName headerElementName = new QName(this.token.getNamespaceURI(), this.token.getLocalName());
                OutputProcessorUtils.updateSecurityHeaderOrder(outputProcessorChain, headerElementName, this.getAction(), false);
                OutputProcessorChain subOutputProcessorChain = outputProcessorChain.createSubChain(this);
                this.outputToken(this.token, subOutputProcessorChain);
                outputProcessorChain.removeProcessor(this);
            }
        }

        private void outputToken(Element element, OutputProcessorChain outputProcessorChain) throws XMLStreamException, XMLSecurityException {
            NamedNodeMap namedNodeMap = element.getAttributes();
            ArrayList<XMLSecAttribute> attributes = new ArrayList<XMLSecAttribute>(namedNodeMap.getLength());
            ArrayList<XMLSecNamespace> namespaces = new ArrayList<XMLSecNamespace>(namedNodeMap.getLength());
            for (int i = 0; i < namedNodeMap.getLength(); ++i) {
                Attr attribute = (Attr)namedNodeMap.item(i);
                if (attribute.getPrefix() == null) {
                    attributes.add(this.createAttribute(new QName(attribute.getNamespaceURI(), attribute.getLocalName()), attribute.getValue()));
                    continue;
                }
                if ("xmlns".equals(attribute.getPrefix()) || "xmlns".equals(attribute.getLocalName())) {
                    namespaces.add(this.createNamespace(attribute.getLocalName(), attribute.getValue()));
                    continue;
                }
                attributes.add(this.createAttribute(new QName(attribute.getNamespaceURI(), attribute.getLocalName(), attribute.getPrefix()), attribute.getValue()));
            }
            QName elementName = new QName(element.getNamespaceURI(), element.getLocalName(), element.getPrefix());
            this.createStartElementAndOutputAsEvent(outputProcessorChain, elementName, namespaces, attributes);
            block5: for (Node firstChild = element.getFirstChild(); firstChild != null; firstChild = firstChild.getNextSibling()) {
                switch (firstChild.getNodeType()) {
                    case 1: {
                        this.outputToken((Element)firstChild, outputProcessorChain);
                        continue block5;
                    }
                    case 3: {
                        this.createCharactersAndOutputAsEvent(outputProcessorChain, ((Text)firstChild).getData());
                    }
                }
            }
            this.createEndElementAndOutputAsEvent(outputProcessorChain, elementName);
        }
    }
}

