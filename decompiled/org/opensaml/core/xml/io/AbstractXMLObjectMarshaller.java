/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.core.xml.io;

import com.google.common.base.Strings;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import javax.annotation.Nonnull;
import javax.xml.namespace.QName;
import net.shibboleth.utilities.java.support.primitive.StringSupport;
import net.shibboleth.utilities.java.support.xml.ElementSupport;
import net.shibboleth.utilities.java.support.xml.NamespaceSupport;
import net.shibboleth.utilities.java.support.xml.QNameSupport;
import net.shibboleth.utilities.java.support.xml.XMLParserException;
import org.opensaml.core.xml.AttributeExtensibleXMLObject;
import org.opensaml.core.xml.Namespace;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.config.XMLObjectProviderRegistrySupport;
import org.opensaml.core.xml.io.Marshaller;
import org.opensaml.core.xml.io.MarshallerFactory;
import org.opensaml.core.xml.io.MarshallingException;
import org.opensaml.core.xml.util.XMLObjectSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public abstract class AbstractXMLObjectMarshaller
implements Marshaller {
    @Nonnull
    private final Logger log = LoggerFactory.getLogger(AbstractXMLObjectMarshaller.class);
    @Nonnull
    private final MarshallerFactory marshallerFactory = XMLObjectProviderRegistrySupport.getMarshallerFactory();

    protected AbstractXMLObjectMarshaller() {
    }

    @Override
    @Nonnull
    public Element marshall(@Nonnull XMLObject xmlObject) throws MarshallingException {
        try {
            Document document = XMLObjectProviderRegistrySupport.getParserPool().newDocument();
            return this.marshall(xmlObject, document);
        }
        catch (XMLParserException e) {
            throw new MarshallingException("Unable to create Document to place marshalled elements in", e);
        }
    }

    @Override
    @Nonnull
    public Element marshall(@Nonnull XMLObject xmlObject, @Nonnull Document document) throws MarshallingException {
        this.log.trace("Starting to marshall {}", (Object)xmlObject.getElementQName());
        if (document == null) {
            throw new MarshallingException("Given document may not be null");
        }
        this.log.trace("Checking if {} contains a cached DOM representation", (Object)xmlObject.getElementQName());
        Element domElement = xmlObject.getDOM();
        if (domElement != null) {
            this.prepareForAdoption(xmlObject);
            if (domElement.getOwnerDocument() != document) {
                this.log.trace("Adopting DOM of XMLObject into given Document");
                ElementSupport.adoptElement(document, domElement);
            }
            this.log.trace("Setting DOM of XMLObject as document element of given Document");
            this.setDocumentElement(document, domElement);
            return domElement;
        }
        this.log.trace("{} does not contain a cached DOM representation. Creating Element to marshall into.", (Object)xmlObject.getElementQName());
        domElement = ElementSupport.constructElement(document, xmlObject.getElementQName());
        this.log.trace("Setting created element as document root");
        this.setDocumentElement(document, domElement);
        domElement = this.marshallInto(xmlObject, domElement);
        this.log.trace("Setting created element to DOM cache for XMLObject {}", (Object)xmlObject.getElementQName());
        xmlObject.setDOM(domElement);
        xmlObject.releaseParentDOM(true);
        return domElement;
    }

    @Override
    @Nonnull
    public Element marshall(@Nonnull XMLObject xmlObject, @Nonnull Element parentElement) throws MarshallingException {
        this.log.trace("Starting to marshall {} as child of {}", (Object)xmlObject.getElementQName(), (Object)QNameSupport.getNodeQName(parentElement));
        if (parentElement == null) {
            throw new MarshallingException("Given parent element is null");
        }
        this.log.trace("Checking if {} contains a cached DOM representation", (Object)xmlObject.getElementQName());
        Element domElement = xmlObject.getDOM();
        if (domElement != null) {
            this.log.trace("{} contains a cached DOM representation", (Object)xmlObject.getElementQName());
            this.prepareForAdoption(xmlObject);
            this.log.trace("Appending DOM of XMLObject {} as child of parent element {}", (Object)xmlObject.getElementQName(), (Object)QNameSupport.getNodeQName(parentElement));
            ElementSupport.appendChildElement(parentElement, domElement);
            return domElement;
        }
        this.log.trace("{} does not contain a cached DOM representation. Creating Element to marshall into.", (Object)xmlObject.getElementQName());
        Document owningDocument = parentElement.getOwnerDocument();
        domElement = ElementSupport.constructElement(owningDocument, xmlObject.getElementQName());
        this.log.trace("Appending newly created element to given parent element");
        ElementSupport.appendChildElement(parentElement, domElement);
        domElement = this.marshallInto(xmlObject, domElement);
        this.log.trace("Setting created element to DOM cache for XMLObject {}", (Object)xmlObject.getElementQName());
        xmlObject.setDOM(domElement);
        xmlObject.releaseParentDOM(true);
        return domElement;
    }

    protected void setDocumentElement(@Nonnull Document document, @Nonnull Element element) {
        Element documentRoot = document.getDocumentElement();
        if (documentRoot != null) {
            document.replaceChild(element, documentRoot);
        } else {
            document.appendChild(element);
        }
    }

    @Nonnull
    protected Element marshallInto(@Nonnull XMLObject xmlObject, @Nonnull Element targetElement) throws MarshallingException {
        this.log.trace("Setting namespace prefix for {} for XMLObject {}", (Object)xmlObject.getElementQName().getPrefix(), (Object)xmlObject.getElementQName());
        this.marshallNamespacePrefix(xmlObject, targetElement);
        this.marshallSchemaInstanceAttributes(xmlObject, targetElement);
        this.marshallNamespaces(xmlObject, targetElement);
        this.marshallAttributes(xmlObject, targetElement);
        this.marshallChildElements(xmlObject, targetElement);
        this.marshallElementContent(xmlObject, targetElement);
        return targetElement;
    }

    protected void marshallNamespacePrefix(@Nonnull XMLObject xmlObject, @Nonnull Element domElement) {
        String prefix = StringSupport.trimOrNull(xmlObject.getElementQName().getPrefix());
        if (prefix != null) {
            domElement.setPrefix(prefix);
        }
    }

    protected void marshallChildElements(@Nonnull XMLObject xmlObject, @Nonnull Element domElement) throws MarshallingException {
        this.log.trace("Marshalling child elements for XMLObject {}", (Object)xmlObject.getElementQName());
        List<XMLObject> childXMLObjects = xmlObject.getOrderedChildren();
        if (childXMLObjects != null && childXMLObjects.size() > 0) {
            for (XMLObject childXMLObject : childXMLObjects) {
                if (childXMLObject == null) continue;
                this.log.trace("Getting marshaller for child XMLObject {}", (Object)childXMLObject.getElementQName());
                Marshaller marshaller = this.marshallerFactory.getMarshaller(childXMLObject);
                if (marshaller == null) {
                    marshaller = this.marshallerFactory.getMarshaller(XMLObjectProviderRegistrySupport.getDefaultProviderQName());
                    if (marshaller == null) {
                        String errorMsg = "No marshaller available for " + childXMLObject.getElementQName() + ", child of " + xmlObject.getElementQName();
                        this.log.error(errorMsg);
                        throw new MarshallingException(errorMsg);
                    }
                    this.log.trace("No marshaller was registered for {}, child of {}. Using default marshaller", (Object)childXMLObject.getElementQName(), (Object)xmlObject.getElementQName());
                }
                this.log.trace("Marshalling {} and adding it to DOM", (Object)childXMLObject.getElementQName());
                marshaller.marshall(childXMLObject, domElement);
            }
        } else {
            this.log.trace("No child elements to marshall for XMLObject {}", (Object)xmlObject.getElementQName());
        }
    }

    protected void marshallNamespaces(@Nonnull XMLObject xmlObject, @Nonnull Element domElement) {
        this.log.trace("Marshalling namespace attributes for XMLObject {}", (Object)xmlObject.getElementQName());
        Set<Namespace> namespaces = xmlObject.getNamespaces();
        for (Namespace namespace : namespaces) {
            this.log.trace("Candidate namespace from getNamespaces(): {}", (Object)namespace.toString());
            if (!xmlObject.getNamespaceManager().getNamespaceDeclarations().contains(namespace)) {
                this.log.trace("NamespaceManager getNamespaceDeclarations() did NOT contain namespace: {}", (Object)namespace.toString());
                if (Objects.equals(namespace.getNamespacePrefix(), "xml") || Objects.equals(namespace.getNamespaceURI(), "http://www.w3.org/XML/1998/namespace")) continue;
                String declared = NamespaceSupport.lookupNamespaceURI(domElement, null, namespace.getNamespacePrefix());
                this.log.trace("Lookup of prefix '{}' returned '{}'", (Object)namespace.getNamespacePrefix(), (Object)declared);
                if (declared != null && namespace.getNamespaceURI().equals(declared)) {
                    this.log.trace("Namespace {} has already been declared on an ancestor of {} no need to add it here", (Object)namespace, (Object)xmlObject.getElementQName());
                    continue;
                }
            }
            this.log.trace("Adding namespace declaration {} to {}", (Object)namespace, (Object)xmlObject.getElementQName());
            String nsURI = StringSupport.trimOrNull(namespace.getNamespaceURI());
            String nsPrefix = StringSupport.trimOrNull(namespace.getNamespacePrefix());
            NamespaceSupport.appendNamespaceDeclaration(domElement, nsURI, nsPrefix);
        }
    }

    protected void marshallSchemaInstanceAttributes(@Nonnull XMLObject xmlObject, @Nonnull Element domElement) throws MarshallingException {
        QName type;
        if (!Strings.isNullOrEmpty(xmlObject.getSchemaLocation())) {
            this.log.trace("Setting xsi:schemaLocation for XMLObject {} to {}", (Object)xmlObject.getElementQName(), (Object)xmlObject.getSchemaLocation());
            domElement.setAttributeNS("http://www.w3.org/2001/XMLSchema-instance", "xsi:schemaLocation", xmlObject.getSchemaLocation());
        }
        if (!Strings.isNullOrEmpty(xmlObject.getNoNamespaceSchemaLocation())) {
            this.log.trace("Setting xsi:noNamespaceSchemaLocation for XMLObject {} to {}", (Object)xmlObject.getElementQName(), (Object)xmlObject.getNoNamespaceSchemaLocation());
            domElement.setAttributeNS("http://www.w3.org/2001/XMLSchema-instance", "xsi:noNamespaceSchemaLocation", xmlObject.getNoNamespaceSchemaLocation());
        }
        if (xmlObject.isNilXSBoolean() != null && xmlObject.isNil().booleanValue()) {
            this.log.trace("Setting xsi:nil for XMLObject {} to true", (Object)xmlObject.getElementQName());
            domElement.setAttributeNS("http://www.w3.org/2001/XMLSchema-instance", "xsi:nil", xmlObject.isNilXSBoolean().toString());
        }
        if ((type = xmlObject.getSchemaType()) == null) {
            return;
        }
        this.log.trace("Setting xsi:type attribute with for XMLObject {}", (Object)xmlObject.getElementQName());
        String typeLocalName = StringSupport.trimOrNull(type.getLocalPart());
        String typePrefix = StringSupport.trimOrNull(type.getPrefix());
        if (typeLocalName == null) {
            throw new MarshallingException("The type QName on XMLObject " + xmlObject.getElementQName() + " may not have a null local name");
        }
        if (type.getNamespaceURI() == null) {
            throw new MarshallingException("The type URI QName on XMLObject " + xmlObject.getElementQName() + " may not have a null namespace URI");
        }
        String attributeValue = typePrefix == null ? typeLocalName : typePrefix + ":" + typeLocalName;
        domElement.setAttributeNS("http://www.w3.org/2001/XMLSchema-instance", "xsi:type", attributeValue);
    }

    protected void marshallAttributes(@Nonnull XMLObject xmlObject, @Nonnull Element domElement) throws MarshallingException {
    }

    protected void marshallElementContent(@Nonnull XMLObject xmlObject, @Nonnull Element domElement) throws MarshallingException {
    }

    private void prepareForAdoption(@Nonnull XMLObject domCachingObject) throws MarshallingException {
        if (domCachingObject.getParent() != null) {
            this.log.trace("Rooting all visible namespaces of XMLObject {} before adding it to new parent Element", (Object)domCachingObject.getElementQName());
            try {
                NamespaceSupport.rootNamespaces(domCachingObject.getDOM());
            }
            catch (DOMException e) {
                String errorMsg = "Unable to root namespaces of cached DOM element, " + domCachingObject.getElementQName();
                this.log.error(errorMsg, e);
                throw new MarshallingException(errorMsg, e);
            }
            this.log.trace("Release DOM of XMLObject parent");
            domCachingObject.releaseParentDOM(true);
        }
    }

    protected void marshallUnknownAttributes(@Nonnull AttributeExtensibleXMLObject xmlObject, @Nonnull Element domElement) {
        XMLObjectSupport.marshallAttributeMap(xmlObject.getUnknownAttributes(), domElement);
    }
}

