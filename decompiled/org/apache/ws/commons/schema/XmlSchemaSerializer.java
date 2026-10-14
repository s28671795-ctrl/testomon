/*
 * Decompiled with CFR 0.152.
 */
package org.apache.ws.commons.schema;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.apache.ws.commons.schema.XmlSchema;
import org.apache.ws.commons.schema.XmlSchemaAll;
import org.apache.ws.commons.schema.XmlSchemaAllMember;
import org.apache.ws.commons.schema.XmlSchemaAnnotated;
import org.apache.ws.commons.schema.XmlSchemaAnnotation;
import org.apache.ws.commons.schema.XmlSchemaAnnotationItem;
import org.apache.ws.commons.schema.XmlSchemaAny;
import org.apache.ws.commons.schema.XmlSchemaAnyAttribute;
import org.apache.ws.commons.schema.XmlSchemaAppInfo;
import org.apache.ws.commons.schema.XmlSchemaAttribute;
import org.apache.ws.commons.schema.XmlSchemaAttributeGroup;
import org.apache.ws.commons.schema.XmlSchemaAttributeGroupMember;
import org.apache.ws.commons.schema.XmlSchemaAttributeGroupRef;
import org.apache.ws.commons.schema.XmlSchemaAttributeOrGroupRef;
import org.apache.ws.commons.schema.XmlSchemaChoice;
import org.apache.ws.commons.schema.XmlSchemaChoiceMember;
import org.apache.ws.commons.schema.XmlSchemaComplexContent;
import org.apache.ws.commons.schema.XmlSchemaComplexContentExtension;
import org.apache.ws.commons.schema.XmlSchemaComplexContentRestriction;
import org.apache.ws.commons.schema.XmlSchemaComplexType;
import org.apache.ws.commons.schema.XmlSchemaContentProcessing;
import org.apache.ws.commons.schema.XmlSchemaDerivationMethod;
import org.apache.ws.commons.schema.XmlSchemaDocumentation;
import org.apache.ws.commons.schema.XmlSchemaElement;
import org.apache.ws.commons.schema.XmlSchemaEnumerationFacet;
import org.apache.ws.commons.schema.XmlSchemaException;
import org.apache.ws.commons.schema.XmlSchemaFacet;
import org.apache.ws.commons.schema.XmlSchemaForm;
import org.apache.ws.commons.schema.XmlSchemaFractionDigitsFacet;
import org.apache.ws.commons.schema.XmlSchemaGroup;
import org.apache.ws.commons.schema.XmlSchemaGroupRef;
import org.apache.ws.commons.schema.XmlSchemaIdentityConstraint;
import org.apache.ws.commons.schema.XmlSchemaImport;
import org.apache.ws.commons.schema.XmlSchemaInclude;
import org.apache.ws.commons.schema.XmlSchemaKey;
import org.apache.ws.commons.schema.XmlSchemaKeyref;
import org.apache.ws.commons.schema.XmlSchemaLengthFacet;
import org.apache.ws.commons.schema.XmlSchemaMaxExclusiveFacet;
import org.apache.ws.commons.schema.XmlSchemaMaxInclusiveFacet;
import org.apache.ws.commons.schema.XmlSchemaMaxLengthFacet;
import org.apache.ws.commons.schema.XmlSchemaMinExclusiveFacet;
import org.apache.ws.commons.schema.XmlSchemaMinInclusiveFacet;
import org.apache.ws.commons.schema.XmlSchemaMinLengthFacet;
import org.apache.ws.commons.schema.XmlSchemaObject;
import org.apache.ws.commons.schema.XmlSchemaParticle;
import org.apache.ws.commons.schema.XmlSchemaPatternFacet;
import org.apache.ws.commons.schema.XmlSchemaRedefine;
import org.apache.ws.commons.schema.XmlSchemaSequence;
import org.apache.ws.commons.schema.XmlSchemaSequenceMember;
import org.apache.ws.commons.schema.XmlSchemaSimpleContent;
import org.apache.ws.commons.schema.XmlSchemaSimpleContentExtension;
import org.apache.ws.commons.schema.XmlSchemaSimpleContentRestriction;
import org.apache.ws.commons.schema.XmlSchemaSimpleType;
import org.apache.ws.commons.schema.XmlSchemaSimpleTypeList;
import org.apache.ws.commons.schema.XmlSchemaSimpleTypeRestriction;
import org.apache.ws.commons.schema.XmlSchemaSimpleTypeUnion;
import org.apache.ws.commons.schema.XmlSchemaTotalDigitsFacet;
import org.apache.ws.commons.schema.XmlSchemaUnique;
import org.apache.ws.commons.schema.XmlSchemaUse;
import org.apache.ws.commons.schema.XmlSchemaWhiteSpaceFacet;
import org.apache.ws.commons.schema.XmlSchemaXPath;
import org.apache.ws.commons.schema.extensions.ExtensionRegistry;
import org.apache.ws.commons.schema.utils.NamespacePrefixList;
import org.w3c.dom.Attr;
import org.w3c.dom.CDATASection;
import org.w3c.dom.Comment;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;

public class XmlSchemaSerializer {
    public static final String XSD_NAMESPACE = "http://www.w3.org/2001/XMLSchema";
    private static final String XMLNS_NAMESPACE_URI = "http://www.w3.org/2000/xmlns/";
    String xsdPrefix = "xs";
    List<Document> docs = new ArrayList<Document>();
    Element schemaElement;
    private ExtensionRegistry extReg;
    private Map<String, String> schemaNamespace = Collections.synchronizedMap(new HashMap());

    private static String[] getParts(String name) {
        String[] parts = new String[2];
        int index = name.indexOf(":");
        if (index > -1) {
            parts[0] = name.substring(0, index);
            parts[1] = name.substring(index + 1);
        } else {
            parts[0] = "";
            parts[1] = name;
        }
        return parts;
    }

    public ExtensionRegistry getExtReg() {
        return this.extReg;
    }

    public Document[] serializeSchema(XmlSchema schemaObj, boolean serializeIncluded) throws XmlSchemaSerializerException {
        return this.serializeSchemaElement(schemaObj, serializeIncluded);
    }

    public void setExtReg(ExtensionRegistry extReg) {
        this.extReg = extReg;
    }

    Element serializeAll(Document doc, XmlSchemaAll allObj, XmlSchema schema) throws XmlSchemaSerializerException {
        List<XmlSchemaAllMember> itemColl;
        Element allEl = this.createNewElement(doc, "all", schema.getSchemaNamespacePrefix(), XSD_NAMESPACE);
        this.serializeMaxMinOccurs(allObj, allEl);
        if (allObj.getAnnotation() != null) {
            Element annotation = this.serializeAnnotation(doc, allObj.getAnnotation(), schema);
            allEl.appendChild(annotation);
        }
        if ((itemColl = allObj.getItems()) != null) {
            int itemLength = itemColl.size();
            for (int i = 0; i < itemLength; ++i) {
                XmlSchemaAllMember obj = itemColl.get(i);
                if (obj instanceof XmlSchemaElement) {
                    Element el = this.serializeElement(doc, (XmlSchemaElement)obj, schema);
                    allEl.appendChild(el);
                    continue;
                }
                if (obj instanceof XmlSchemaGroupRef) {
                    Element group = this.serializeGroupRef(doc, (XmlSchemaGroupRef)obj, schema);
                    allEl.appendChild(group);
                    continue;
                }
                if (obj instanceof XmlSchemaAny) {
                    Element any = this.serializeAny(doc, (XmlSchemaAny)obj, schema);
                    allEl.appendChild(any);
                    continue;
                }
                throw new XmlSchemaSerializerException("Only element allowed as child of all model type");
            }
        }
        this.processExtensibilityComponents(allObj, allEl);
        return allEl;
    }

    Element serializeAnnotation(Document doc, XmlSchemaAnnotation annotationObj, XmlSchema schema) {
        Element annotation = this.createNewElement(doc, "annotation", schema.getSchemaNamespacePrefix(), XSD_NAMESPACE);
        List<XmlSchemaAnnotationItem> contents = annotationObj.getItems();
        int contentLength = contents.size();
        for (int i = 0; i < contentLength; ++i) {
            XmlSchemaObject obj = contents.get(i);
            if (obj instanceof XmlSchemaAppInfo) {
                XmlSchemaAppInfo appinfo = (XmlSchemaAppInfo)obj;
                Element appInfoEl = this.serializeAppInfo(doc, appinfo, schema);
                annotation.appendChild(appInfoEl);
                continue;
            }
            if (!(obj instanceof XmlSchemaDocumentation)) continue;
            XmlSchemaDocumentation documentation = (XmlSchemaDocumentation)obj;
            Element documentationEl = this.serializeDocumentation(doc, documentation, schema);
            annotation.appendChild(documentationEl);
        }
        this.processExtensibilityComponents(annotationObj, annotation);
        return annotation;
    }

    Element serializeAny(Document doc, XmlSchemaAny anyObj, XmlSchema schema) {
        Element anyEl = this.createNewElement(doc, "any", schema.getSchemaNamespacePrefix(), XSD_NAMESPACE);
        if (anyObj.getId() != null && anyObj.getId().length() > 0) {
            anyEl.setAttributeNS(null, "id", anyObj.getId());
        }
        this.serializeMaxMinOccurs(anyObj, anyEl);
        if (anyObj.getNamespace() != null) {
            anyEl.setAttributeNS(null, "namespace", anyObj.getNamespace());
        }
        if (anyObj.getProcessContent() != null && anyObj.getProcessContent() != XmlSchemaContentProcessing.NONE) {
            anyEl.setAttributeNS(null, "processContents", anyObj.getProcessContent().toString());
        }
        if (anyObj.getAnnotation() != null) {
            Element annotation = this.serializeAnnotation(doc, anyObj.getAnnotation(), schema);
            anyEl.appendChild(annotation);
        }
        this.processExtensibilityComponents(anyObj, anyEl);
        return anyEl;
    }

    Element serializeAnyAttribute(Document doc, XmlSchemaAnyAttribute anyAttributeObj, XmlSchema schema) {
        Element anyAttribute = this.createNewElement(doc, "anyAttribute", schema.getSchemaNamespacePrefix(), XSD_NAMESPACE);
        if (anyAttributeObj.namespace != null) {
            anyAttribute.setAttributeNS(null, "namespace", anyAttributeObj.namespace);
        }
        if (anyAttributeObj.getId() != null) {
            anyAttribute.setAttributeNS(null, "id", anyAttributeObj.getId());
        }
        if (anyAttributeObj.processContent != null && anyAttributeObj.processContent != XmlSchemaContentProcessing.NONE) {
            anyAttribute.setAttributeNS(null, "processContents", anyAttributeObj.processContent.toString());
        }
        if (anyAttributeObj.getAnnotation() != null) {
            Element annotation = this.serializeAnnotation(doc, anyAttributeObj.getAnnotation(), schema);
            anyAttribute.appendChild(annotation);
        }
        this.processExtensibilityComponents(anyAttributeObj, anyAttribute);
        return anyAttribute;
    }

    Element serializeAppInfo(Document doc, XmlSchemaAppInfo appInfoObj, XmlSchema schema) {
        Element appInfoEl = this.createNewElement(doc, "appinfo", schema.getSchemaNamespacePrefix(), XSD_NAMESPACE);
        if (appInfoObj.source != null) {
            appInfoEl.setAttributeNS(null, "source", appInfoObj.source);
        }
        if (appInfoObj.markup != null) {
            int markupLength = appInfoObj.markup.getLength();
            for (int j = 0; j < markupLength; ++j) {
                Node n = appInfoObj.markup.item(j);
                appInfoEl.appendChild(doc.importNode(n, true));
            }
        }
        this.processExtensibilityComponents(appInfoObj, appInfoEl);
        return appInfoEl;
    }

    Element serializeAttribute(Document doc, XmlSchemaAttribute attributeObj, XmlSchema schema) throws XmlSchemaSerializerException {
        boolean refPresent = attributeObj.getRef().getTargetQName() != null;
        Element attribute = this.createNewElement(doc, "attribute", schema.getSchemaNamespacePrefix(), XSD_NAMESPACE);
        if (refPresent) {
            String refName = this.resolveQName(attributeObj.getRef().getTargetQName(), schema);
            attribute.setAttributeNS(null, "ref", refName);
        } else if (!attributeObj.isAnonymous()) {
            attribute.setAttributeNS(null, "name", attributeObj.getName());
        }
        if (attributeObj.getSchemaTypeName() != null && !refPresent) {
            String typeName = this.resolveQName(attributeObj.getSchemaTypeName(), schema);
            attribute.setAttributeNS(null, "type", typeName);
        }
        if (attributeObj.getDefaultValue() != null) {
            attribute.setAttributeNS(null, "default", attributeObj.getDefaultValue());
        }
        if (attributeObj.getFixedValue() != null) {
            attribute.setAttributeNS(null, "fixed", attributeObj.getFixedValue());
        }
        if (attributeObj.isFormSpecified() && !refPresent) {
            attribute.setAttributeNS(null, "form", attributeObj.getForm().toString());
        }
        if (attributeObj.getId() != null) {
            attribute.setAttributeNS(null, "id", attributeObj.getId());
        }
        if (attributeObj.getUse() != null && attributeObj.getUse() != XmlSchemaUse.NONE) {
            attribute.setAttributeNS(null, "use", attributeObj.getUse().toString());
        }
        if (attributeObj.getAnnotation() != null) {
            Element annotation = this.serializeAnnotation(doc, attributeObj.getAnnotation(), schema);
            attribute.appendChild(annotation);
        }
        if (attributeObj.getSchemaType() != null && !refPresent) {
            try {
                XmlSchemaSimpleType simpleType = attributeObj.getSchemaType();
                Element simpleTypeEl = this.serializeSimpleType(doc, simpleType, schema);
                attribute.appendChild(simpleTypeEl);
            }
            catch (ClassCastException e) {
                throw new XmlSchemaSerializerException("Only an inline simple type is allowed as an attribute's inline type");
            }
        }
        Attr[] unhandled = attributeObj.getUnhandledAttributes();
        Map<String, String> namespaces = Collections.synchronizedMap(new HashMap());
        if (unhandled != null) {
            for (Attr element : unhandled) {
                String name = element.getNodeName();
                String value = element.getNodeValue();
                if ("xmlns".equals(name)) {
                    namespaces.put("", value);
                    continue;
                }
                if (!name.startsWith("xmlns")) continue;
                namespaces.put(name.substring(name.indexOf(":") + 1), value);
            }
            for (Attr element : unhandled) {
                String prefix;
                String oldNamespace;
                String value = element.getNodeValue();
                String nodeName = element.getNodeName();
                if (value.indexOf(":") > -1 && !nodeName.startsWith("xmlns") && (oldNamespace = (String)namespaces.get(prefix = value.substring(0, value.indexOf(":")))) != null) {
                    String[] prefixes;
                    value = value.substring(value.indexOf(":") + 1);
                    NamespacePrefixList ctx = schema.getNamespaceContext();
                    for (String pref : prefixes = ctx.getDeclaredPrefixes()) {
                        String uri = ctx.getNamespaceURI(pref);
                        if (!uri.equals(oldNamespace)) continue;
                        value = prefix + ":" + value;
                    }
                }
                if (element.getNamespaceURI() != null) {
                    attribute.setAttributeNS(element.getNamespaceURI(), nodeName, value);
                    continue;
                }
                attribute.setAttributeNS(null, nodeName, value);
            }
        }
        this.processExtensibilityComponents(attributeObj, attribute);
        return attribute;
    }

    Element serializeAttributeGroup(Document doc, XmlSchemaAttributeGroup attributeGroupObj, XmlSchema schema) throws XmlSchemaSerializerException {
        Element attributeGroup = this.createNewElement(doc, "attributeGroup", schema.getSchemaNamespacePrefix(), XSD_NAMESPACE);
        if (attributeGroupObj.isAnonymous()) {
            throw new XmlSchemaSerializerException("Attribute group must have name");
        }
        String attGroupName = attributeGroupObj.getName();
        attributeGroup.setAttributeNS(null, "name", attGroupName);
        if (attributeGroupObj.getId() != null) {
            attributeGroup.setAttributeNS(null, "id", attributeGroupObj.getId());
        }
        if (attributeGroupObj.getAnnotation() != null) {
            Element annotation = this.serializeAnnotation(doc, attributeGroupObj.getAnnotation(), schema);
            attributeGroup.appendChild(annotation);
        }
        int attributesLength = attributeGroupObj.getAttributes().size();
        for (int i = 0; i < attributesLength; ++i) {
            XmlSchemaAttributeGroupMember obj = attributeGroupObj.getAttributes().get(i);
            if (obj instanceof XmlSchemaAttribute) {
                Element attr = this.serializeAttribute(doc, (XmlSchemaAttribute)obj, schema);
                attributeGroup.appendChild(attr);
                continue;
            }
            if (!(obj instanceof XmlSchemaAttributeGroupRef)) continue;
            Element attrGroup = this.serializeAttributeGroupRef(doc, (XmlSchemaAttributeGroupRef)obj, schema);
            attributeGroup.appendChild(attrGroup);
        }
        if (attributeGroupObj.getAnyAttribute() != null) {
            Element anyAttribute = this.serializeAnyAttribute(doc, attributeGroupObj.getAnyAttribute(), schema);
            attributeGroup.appendChild(anyAttribute);
        }
        this.processExtensibilityComponents(attributeGroupObj, attributeGroup);
        return attributeGroup;
    }

    Element serializeAttributeGroupRef(Document doc, XmlSchemaAttributeGroupRef attributeGroupObj, XmlSchema schema) throws XmlSchemaSerializerException {
        Element attributeGroupRef = this.createNewElement(doc, "attributeGroup", schema.getSchemaNamespacePrefix(), XSD_NAMESPACE);
        if (attributeGroupObj.getRef().getTarget() == null) {
            throw new XmlSchemaSerializerException("Attribute group must have ref name set");
        }
        String refName = this.resolveQName(attributeGroupObj.getRef().getTargetQName(), schema);
        attributeGroupRef.setAttributeNS(null, "ref", refName);
        if (attributeGroupObj.getId() != null) {
            attributeGroupRef.setAttributeNS(null, "id", attributeGroupObj.getId());
        }
        if (attributeGroupObj.getAnnotation() != null) {
            Element annotation = this.serializeAnnotation(doc, attributeGroupObj.getAnnotation(), schema);
            attributeGroupRef.appendChild(annotation);
        }
        this.processExtensibilityComponents(attributeGroupObj, attributeGroupRef);
        return attributeGroupRef;
    }

    Element serializeChoice(Document doc, XmlSchemaChoice choiceObj, XmlSchema schema) throws XmlSchemaSerializerException {
        List<XmlSchemaChoiceMember> itemColl;
        Element choice = this.createNewElement(doc, "choice", schema.getSchemaNamespacePrefix(), XSD_NAMESPACE);
        if (choiceObj.getId() != null && choiceObj.getId().length() > 0) {
            choice.setAttributeNS(null, "id", choiceObj.getId());
        }
        this.serializeMaxMinOccurs(choiceObj, choice);
        if (choiceObj.getAnnotation() != null) {
            Element annotation = this.serializeAnnotation(doc, choiceObj.getAnnotation(), schema);
            choice.appendChild(annotation);
        }
        if ((itemColl = choiceObj.getItems()) != null) {
            int itemLength = itemColl.size();
            for (int i = 0; i < itemLength; ++i) {
                XmlSchemaChoiceMember obj = itemColl.get(i);
                if (obj instanceof XmlSchemaElement) {
                    Element el = this.serializeElement(doc, (XmlSchemaElement)obj, schema);
                    choice.appendChild(el);
                    continue;
                }
                if (obj instanceof XmlSchemaGroupRef) {
                    Element group = this.serializeGroupRef(doc, (XmlSchemaGroupRef)obj, schema);
                    choice.appendChild(group);
                    continue;
                }
                if (obj instanceof XmlSchemaChoice) {
                    Element inlineChoice = this.serializeChoice(doc, (XmlSchemaChoice)obj, schema);
                    choice.appendChild(inlineChoice);
                    continue;
                }
                if (obj instanceof XmlSchemaSequence) {
                    Element inlineSequence = this.serializeSequence(doc, (XmlSchemaSequence)obj, schema);
                    choice.appendChild(inlineSequence);
                    continue;
                }
                if (!(obj instanceof XmlSchemaAny)) continue;
                Element any = this.serializeAny(doc, (XmlSchemaAny)obj, schema);
                choice.appendChild(any);
            }
        }
        this.processExtensibilityComponents(choiceObj, choice);
        return choice;
    }

    Element serializeComplexContent(Document doc, XmlSchemaComplexContent complexContentObj, XmlSchema schema) throws XmlSchemaSerializerException {
        Element content;
        Element complexContent = this.createNewElement(doc, "complexContent", schema.getSchemaNamespacePrefix(), XSD_NAMESPACE);
        if (complexContentObj.getAnnotation() != null) {
            Element annotation = this.serializeAnnotation(doc, complexContentObj.getAnnotation(), schema);
            complexContent.appendChild(annotation);
        }
        if (complexContentObj.isMixed()) {
            complexContent.setAttributeNS(null, "mixed", "true");
        }
        if (complexContentObj.getId() != null) {
            complexContent.setAttributeNS(null, "id", complexContentObj.getId());
        }
        if (complexContentObj.content instanceof XmlSchemaComplexContentRestriction) {
            content = this.serializeComplexContentRestriction(doc, (XmlSchemaComplexContentRestriction)complexContentObj.content, schema);
        } else if (complexContentObj.content instanceof XmlSchemaComplexContentExtension) {
            content = this.serializeComplexContentExtension(doc, (XmlSchemaComplexContentExtension)complexContentObj.content, schema);
        } else {
            throw new XmlSchemaSerializerException("content of complexContent must be restriction or extension");
        }
        complexContent.appendChild(content);
        this.processExtensibilityComponents(complexContentObj, complexContent);
        return complexContent;
    }

    Element serializeComplexContentExtension(Document doc, XmlSchemaComplexContentExtension extensionObj, XmlSchema schema) throws XmlSchemaSerializerException {
        Element extension = this.createNewElement(doc, "extension", schema.getSchemaNamespacePrefix(), XSD_NAMESPACE);
        if (extensionObj.getBaseTypeName() != null) {
            String baseType = this.resolveQName(extensionObj.getBaseTypeName(), schema);
            extension.setAttributeNS(null, "base", baseType);
        }
        if (extensionObj.getAnnotation() != null) {
            Element annotation = this.serializeAnnotation(doc, extensionObj.getAnnotation(), schema);
            extension.appendChild(annotation);
        }
        if (extensionObj.getParticle() instanceof XmlSchemaSequence) {
            Element sequenceParticle = this.serializeSequence(doc, (XmlSchemaSequence)extensionObj.getParticle(), schema);
            extension.appendChild(sequenceParticle);
        } else if (extensionObj.getParticle() instanceof XmlSchemaChoice) {
            Element choiceParticle = this.serializeChoice(doc, (XmlSchemaChoice)extensionObj.getParticle(), schema);
            extension.appendChild(choiceParticle);
        } else if (extensionObj.getParticle() instanceof XmlSchemaAll) {
            Element allParticle = this.serializeAll(doc, (XmlSchemaAll)extensionObj.getParticle(), schema);
            extension.appendChild(allParticle);
        } else if (extensionObj.getParticle() instanceof XmlSchemaGroupRef) {
            Element groupRefParticle = this.serializeGroupRef(doc, (XmlSchemaGroupRef)extensionObj.getParticle(), schema);
            extension.appendChild(groupRefParticle);
        }
        int attributesLength = extensionObj.getAttributes().size();
        for (int i = 0; i < attributesLength; ++i) {
            XmlSchemaObject obj = extensionObj.getAttributes().get(i);
            if (obj instanceof XmlSchemaAttribute) {
                Element attr = this.serializeAttribute(doc, (XmlSchemaAttribute)obj, schema);
                extension.appendChild(attr);
                continue;
            }
            if (!(obj instanceof XmlSchemaAttributeGroupRef)) continue;
            Element attrGroup = this.serializeAttributeGroupRef(doc, (XmlSchemaAttributeGroupRef)obj, schema);
            extension.appendChild(attrGroup);
        }
        if (extensionObj.getAnyAttribute() != null) {
            Element anyAttribute = this.serializeAnyAttribute(doc, extensionObj.getAnyAttribute(), schema);
            extension.appendChild(anyAttribute);
        }
        this.processExtensibilityComponents(extensionObj, extension);
        return extension;
    }

    Element serializeComplexContentRestriction(Document doc, XmlSchemaComplexContentRestriction restrictionObj, XmlSchema schema) throws XmlSchemaSerializerException {
        Element restriction = this.createNewElement(doc, "restriction", schema.getSchemaNamespacePrefix(), XSD_NAMESPACE);
        if (restrictionObj.getBaseTypeName() != null) {
            String baseTypeName = this.resolveQName(restrictionObj.getBaseTypeName(), schema);
            restriction.setAttributeNS(null, "base", baseTypeName);
        }
        if (restrictionObj.getId() != null) {
            restriction.setAttributeNS(null, "id", restrictionObj.getId());
        }
        if (restrictionObj.getAnnotation() != null) {
            Element annotation = this.serializeAnnotation(doc, restrictionObj.getAnnotation(), schema);
            restriction.appendChild(annotation);
        }
        if (restrictionObj.getParticle() instanceof XmlSchemaSequence) {
            Element sequenceParticle = this.serializeSequence(doc, (XmlSchemaSequence)restrictionObj.getParticle(), schema);
            restriction.appendChild(sequenceParticle);
        } else if (restrictionObj.getParticle() instanceof XmlSchemaChoice) {
            Element choiceParticle = this.serializeChoice(doc, (XmlSchemaChoice)restrictionObj.getParticle(), schema);
            restriction.appendChild(choiceParticle);
        } else if (restrictionObj.getParticle() instanceof XmlSchemaAll) {
            Element allParticle = this.serializeAll(doc, (XmlSchemaAll)restrictionObj.getParticle(), schema);
            restriction.appendChild(allParticle);
        } else if (restrictionObj.getParticle() instanceof XmlSchemaGroupRef) {
            Element groupRefParticle = this.serializeGroupRef(doc, (XmlSchemaGroupRef)restrictionObj.getParticle(), schema);
            restriction.appendChild(groupRefParticle);
        }
        int attributesLength = restrictionObj.getAttributes().size();
        for (int i = 0; i < attributesLength; ++i) {
            XmlSchemaAttributeOrGroupRef obj = restrictionObj.getAttributes().get(i);
            if (obj instanceof XmlSchemaAttribute) {
                Element attr = this.serializeAttribute(doc, (XmlSchemaAttribute)obj, schema);
                restriction.appendChild(attr);
                continue;
            }
            if (!(obj instanceof XmlSchemaAttributeGroupRef)) continue;
            Element attrGroup = this.serializeAttributeGroupRef(doc, (XmlSchemaAttributeGroupRef)obj, schema);
            restriction.appendChild(attrGroup);
        }
        if (restrictionObj.getAnyAttribute() != null) {
            Element anyAttribute = this.serializeAnyAttribute(doc, restrictionObj.getAnyAttribute(), schema);
            restriction.appendChild(anyAttribute);
        }
        this.processExtensibilityComponents(restrictionObj, restriction);
        return restriction;
    }

    Element serializeComplexType(Document doc, XmlSchemaComplexType complexTypeObj, XmlSchema schema) throws XmlSchemaSerializerException {
        XmlSchemaAnyAttribute anyAttribute;
        List<XmlSchemaAttributeOrGroupRef> attrColl;
        Element serializedComplexType = this.createNewElement(doc, "complexType", schema.getSchemaNamespacePrefix(), XSD_NAMESPACE);
        if (!complexTypeObj.isAnonymous()) {
            serializedComplexType.setAttributeNS(null, "name", complexTypeObj.getName());
        }
        if (complexTypeObj.isMixed()) {
            serializedComplexType.setAttributeNS(null, "mixed", "true");
        }
        if (complexTypeObj.isAbstract()) {
            serializedComplexType.setAttributeNS(null, "abstract", "true");
        }
        if (complexTypeObj.getId() != null) {
            serializedComplexType.setAttributeNS(null, "id", complexTypeObj.getId());
        }
        if (complexTypeObj.getAnnotation() != null) {
            Element annotationEl = this.serializeAnnotation(doc, complexTypeObj.getAnnotation(), schema);
            serializedComplexType.appendChild(annotationEl);
        }
        if (complexTypeObj.getContentModel() instanceof XmlSchemaSimpleContent) {
            Element simpleContent = this.serializeSimpleContent(doc, (XmlSchemaSimpleContent)complexTypeObj.getContentModel(), schema);
            serializedComplexType.appendChild(simpleContent);
        } else if (complexTypeObj.getContentModel() instanceof XmlSchemaComplexContent) {
            Element complexContent = this.serializeComplexContent(doc, (XmlSchemaComplexContent)complexTypeObj.getContentModel(), schema);
            serializedComplexType.appendChild(complexContent);
        }
        if (complexTypeObj.getParticle() instanceof XmlSchemaSequence) {
            Element sequence2 = this.serializeSequence(doc, (XmlSchemaSequence)complexTypeObj.getParticle(), schema);
            serializedComplexType.appendChild(sequence2);
        } else if (complexTypeObj.getParticle() instanceof XmlSchemaChoice) {
            Element choice = this.serializeChoice(doc, (XmlSchemaChoice)complexTypeObj.getParticle(), schema);
            serializedComplexType.appendChild(choice);
        } else if (complexTypeObj.getParticle() instanceof XmlSchemaAll) {
            Element all = this.serializeAll(doc, (XmlSchemaAll)complexTypeObj.getParticle(), schema);
            serializedComplexType.appendChild(all);
        } else if (complexTypeObj.getParticle() instanceof XmlSchemaGroupRef) {
            Element group = this.serializeGroupRef(doc, (XmlSchemaGroupRef)complexTypeObj.getParticle(), schema);
            serializedComplexType.appendChild(group);
        }
        if (complexTypeObj.getBlock() != null && complexTypeObj.getBlock() != XmlSchemaDerivationMethod.NONE) {
            serializedComplexType.setAttributeNS(null, "block", complexTypeObj.toString());
        }
        if (complexTypeObj.getFinalDerivation() != null && complexTypeObj.getFinalDerivation() != XmlSchemaDerivationMethod.NONE) {
            serializedComplexType.setAttributeNS(null, "final", complexTypeObj.getFinalDerivation().toString());
        }
        if ((attrColl = complexTypeObj.getAttributes()).size() > 0) {
            this.setupAttr(doc, attrColl, schema, serializedComplexType);
        }
        if ((anyAttribute = complexTypeObj.getAnyAttribute()) != null) {
            serializedComplexType.appendChild(this.serializeAnyAttribute(doc, anyAttribute, schema));
        }
        this.processExtensibilityComponents(complexTypeObj, serializedComplexType);
        return serializedComplexType;
    }

    Element serializeDocumentation(Document doc, XmlSchemaDocumentation documentationObj, XmlSchema schema) {
        Element documentationEl = this.createNewElement(doc, "documentation", schema.getSchemaNamespacePrefix(), XSD_NAMESPACE);
        if (documentationObj.source != null) {
            documentationEl.setAttributeNS(null, "source", documentationObj.source);
        }
        if (documentationObj.language != null) {
            documentationEl.setAttributeNS("http://www.w3.org/XML/1998/namespace", "xml:lang", documentationObj.language);
        }
        if (documentationObj.markup != null) {
            int markupLength = documentationObj.markup.getLength();
            block6: for (int j = 0; j < markupLength; ++j) {
                Node n = documentationObj.markup.item(j);
                switch (n.getNodeType()) {
                    case 1: {
                        this.appendElement(doc, documentationEl, n, schema);
                        continue block6;
                    }
                    case 3: {
                        Text t2 = doc.createTextNode(n.getNodeValue());
                        documentationEl.appendChild(t2);
                        continue block6;
                    }
                    case 4: {
                        CDATASection s2 = doc.createCDATASection(n.getNodeValue());
                        documentationEl.appendChild(s2);
                        continue block6;
                    }
                    case 8: {
                        Comment c = doc.createComment(n.getNodeValue());
                        documentationEl.appendChild(c);
                        continue block6;
                    }
                }
            }
        }
        this.processExtensibilityComponents(documentationObj, documentationEl);
        return documentationEl;
    }

    Element serializeElement(Document doc, XmlSchemaElement elementObj, XmlSchema schema) throws XmlSchemaSerializerException {
        String resolvedName;
        Element serializedEl = this.createNewElement(doc, "element", schema.getSchemaNamespacePrefix(), XSD_NAMESPACE);
        if (elementObj.getRef().getTargetQName() != null) {
            resolvedName = this.resolveQName(elementObj.getRef().getTargetQName(), schema);
            serializedEl.setAttributeNS(null, "ref", resolvedName);
        } else if (!elementObj.isAnonymous()) {
            serializedEl.setAttributeNS(null, "name", elementObj.getName());
        }
        if (elementObj.isAbstractElement()) {
            serializedEl.setAttributeNS(null, "abstract", "true");
        }
        if (elementObj.getBlock() != null && elementObj.getBlock() != XmlSchemaDerivationMethod.NONE) {
            serializedEl.setAttributeNS(null, "block", elementObj.getBlock().toString());
        }
        if (elementObj.getDefaultValue() != null) {
            serializedEl.setAttributeNS(null, "default", elementObj.getDefaultValue());
        }
        if (elementObj.getFinalDerivation() != null && elementObj.getFinalDerivation() != XmlSchemaDerivationMethod.NONE) {
            serializedEl.setAttributeNS(null, "final", elementObj.getFinalDerivation().toString());
        }
        if (elementObj.getFixedValue() != null) {
            serializedEl.setAttributeNS(null, "fixed", elementObj.getFixedValue());
        }
        if (elementObj.isFormSpecified()) {
            serializedEl.setAttributeNS(null, "form", elementObj.getForm().toString());
        }
        if (elementObj.getId() != null) {
            serializedEl.setAttributeNS(null, "id", elementObj.getId());
        }
        this.serializeMaxMinOccurs(elementObj, serializedEl);
        if (elementObj.getSubstitutionGroup() != null) {
            String resolvedQName = this.resolveQName(elementObj.getSubstitutionGroup(), schema);
            serializedEl.setAttributeNS(null, "substitutionGroup", resolvedQName);
        }
        if (elementObj.getSchemaTypeName() != null) {
            resolvedName = this.resolveQName(elementObj.getSchemaTypeName(), schema);
            serializedEl.setAttributeNS(null, "type", resolvedName);
        }
        if (elementObj.getAnnotation() != null) {
            Element annotationEl = this.serializeAnnotation(doc, elementObj.getAnnotation(), schema);
            serializedEl.appendChild(annotationEl);
        }
        if (elementObj.getSchemaType() != null && elementObj.getSchemaTypeName() == null) {
            if (elementObj.getSchemaType() instanceof XmlSchemaComplexType) {
                Element complexType = this.serializeComplexType(doc, (XmlSchemaComplexType)elementObj.getSchemaType(), schema);
                serializedEl.appendChild(complexType);
            } else if (elementObj.getSchemaType() instanceof XmlSchemaSimpleType) {
                Element simpleType = this.serializeSimpleType(doc, (XmlSchemaSimpleType)elementObj.getSchemaType(), schema);
                serializedEl.appendChild(simpleType);
            }
        }
        if (elementObj.getConstraints().size() > 0) {
            for (int i = 0; i < elementObj.getConstraints().size(); ++i) {
                Element constraint = this.serializeIdentityConstraint(doc, elementObj.getConstraints().get(i), schema);
                serializedEl.appendChild(constraint);
            }
        }
        if (elementObj.isNillable()) {
            serializedEl.setAttributeNS(null, "nillable", "true");
        }
        this.processExtensibilityComponents(elementObj, serializedEl);
        return serializedEl;
    }

    Element serializeFacet(Document doc, XmlSchemaFacet facetObj, XmlSchema schema) throws XmlSchemaSerializerException {
        Element serializedFacet;
        if (facetObj instanceof XmlSchemaMinExclusiveFacet) {
            serializedFacet = this.constructFacet(facetObj, doc, schema, "minExclusive");
        } else if (facetObj instanceof XmlSchemaMinInclusiveFacet) {
            serializedFacet = this.constructFacet(facetObj, doc, schema, "minInclusive");
        } else if (facetObj instanceof XmlSchemaMaxExclusiveFacet) {
            serializedFacet = this.constructFacet(facetObj, doc, schema, "maxExclusive");
        } else if (facetObj instanceof XmlSchemaMaxInclusiveFacet) {
            serializedFacet = this.constructFacet(facetObj, doc, schema, "maxInclusive");
        } else if (facetObj instanceof XmlSchemaTotalDigitsFacet) {
            serializedFacet = this.constructFacet(facetObj, doc, schema, "totalDigits");
        } else if (facetObj instanceof XmlSchemaFractionDigitsFacet) {
            serializedFacet = this.constructFacet(facetObj, doc, schema, "fractionDigits");
        } else if (facetObj instanceof XmlSchemaLengthFacet) {
            serializedFacet = this.constructFacet(facetObj, doc, schema, "length");
        } else if (facetObj instanceof XmlSchemaMinLengthFacet) {
            serializedFacet = this.constructFacet(facetObj, doc, schema, "minLength");
        } else if (facetObj instanceof XmlSchemaMaxLengthFacet) {
            serializedFacet = this.constructFacet(facetObj, doc, schema, "maxLength");
        } else if (facetObj instanceof XmlSchemaEnumerationFacet) {
            serializedFacet = this.constructFacet(facetObj, doc, schema, "enumeration");
        } else if (facetObj instanceof XmlSchemaWhiteSpaceFacet) {
            serializedFacet = this.constructFacet(facetObj, doc, schema, "whiteSpace");
        } else if (facetObj instanceof XmlSchemaPatternFacet) {
            serializedFacet = this.constructFacet(facetObj, doc, schema, "pattern");
        } else {
            throw new XmlSchemaSerializerException("facet not exist " + facetObj.getClass().getName());
        }
        if (facetObj.getId() != null) {
            serializedFacet.setAttributeNS(null, "id", facetObj.getId());
        }
        this.processExtensibilityComponents(facetObj, serializedFacet);
        return serializedFacet;
    }

    Element serializeField(Document doc, XmlSchemaXPath fieldObj, XmlSchema schema) throws XmlSchemaSerializerException {
        Element field2 = this.createNewElement(doc, "field", schema.getSchemaNamespacePrefix(), XSD_NAMESPACE);
        if (fieldObj.xpath == null) {
            throw new XmlSchemaSerializerException("xpath can't be null");
        }
        field2.setAttributeNS(null, "xpath", fieldObj.xpath);
        if (fieldObj.getAnnotation() != null) {
            Element annotation = this.serializeAnnotation(doc, fieldObj.getAnnotation(), schema);
            field2.appendChild(annotation);
        }
        this.processExtensibilityComponents(fieldObj, field2);
        return field2;
    }

    Element serializeGroup(Document doc, XmlSchemaGroup groupObj, XmlSchema schema) throws XmlSchemaSerializerException {
        Element group = this.createNewElement(doc, "group", schema.getSchemaNamespacePrefix(), XSD_NAMESPACE);
        if (!groupObj.isAnonymous()) {
            String grpName = groupObj.getName();
            if (grpName.length() > 0) {
                group.setAttributeNS(null, "name", grpName);
            }
        } else {
            throw new XmlSchemaSerializerException("Group must have name or ref");
        }
        if (groupObj.getAnnotation() != null) {
            Element annotation = this.serializeAnnotation(doc, groupObj.getAnnotation(), schema);
            group.appendChild(annotation);
        }
        if (groupObj.getParticle() instanceof XmlSchemaSequence) {
            Element sequence2 = this.serializeSequence(doc, (XmlSchemaSequence)groupObj.getParticle(), schema);
            group.appendChild(sequence2);
        } else if (groupObj.getParticle() instanceof XmlSchemaChoice) {
            Element choice = this.serializeChoice(doc, (XmlSchemaChoice)groupObj.getParticle(), schema);
            group.appendChild(choice);
        } else if (groupObj.getParticle() instanceof XmlSchemaAll) {
            Element all = this.serializeAll(doc, (XmlSchemaAll)groupObj.getParticle(), schema);
            group.appendChild(all);
        }
        this.processExtensibilityComponents(groupObj, group);
        return group;
    }

    Element serializeGroupRef(Document doc, XmlSchemaGroupRef groupRefObj, XmlSchema schema) throws XmlSchemaSerializerException {
        Element groupRef = this.createNewElement(doc, "group", schema.getSchemaNamespacePrefix(), XSD_NAMESPACE);
        if (groupRefObj.getRefName() == null) {
            throw new XmlSchemaSerializerException("Group must have name or ref");
        }
        String groupRefName = this.resolveQName(groupRefObj.getRefName(), schema);
        groupRef.setAttributeNS(null, "ref", groupRefName);
        this.serializeMaxMinOccurs(groupRefObj, groupRef);
        if (groupRefObj.getParticle() != null) {
            if (groupRefObj.getParticle() instanceof XmlSchemaChoice) {
                this.serializeChoice(doc, (XmlSchemaChoice)groupRefObj.getParticle(), schema);
            } else if (groupRefObj.getParticle() instanceof XmlSchemaSequence) {
                this.serializeSequence(doc, (XmlSchemaSequence)groupRefObj.getParticle(), schema);
            } else if (groupRefObj.getParticle() instanceof XmlSchemaAll) {
                this.serializeAll(doc, (XmlSchemaAll)groupRefObj.getParticle(), schema);
            } else {
                throw new XmlSchemaSerializerException("The content of group ref particle should be sequence, choice or all reference:  www.w3.org/TR/xmlschema-1#element-group-3.7.2");
            }
        }
        if (groupRefObj.getAnnotation() != null) {
            Element annotation = this.serializeAnnotation(doc, groupRefObj.getAnnotation(), schema);
            groupRef.appendChild(annotation);
        }
        this.processExtensibilityComponents(groupRefObj, groupRef);
        return groupRef;
    }

    Element serializeIdentityConstraint(Document doc, XmlSchemaIdentityConstraint constraintObj, XmlSchema schema) throws XmlSchemaSerializerException {
        List<XmlSchemaXPath> fieldColl;
        Element constraint;
        if (constraintObj instanceof XmlSchemaUnique) {
            constraint = this.createNewElement(doc, "unique", schema.getSchemaNamespacePrefix(), XSD_NAMESPACE);
        } else if (constraintObj instanceof XmlSchemaKey) {
            constraint = this.createNewElement(doc, "key", schema.getSchemaNamespacePrefix(), XSD_NAMESPACE);
        } else if (constraintObj instanceof XmlSchemaKeyref) {
            constraint = this.createNewElement(doc, "keyref", schema.getSchemaNamespacePrefix(), XSD_NAMESPACE);
            XmlSchemaKeyref keyref = (XmlSchemaKeyref)constraintObj;
            if (keyref.refer != null) {
                String keyrefStr = this.resolveQName(keyref.refer, schema);
                constraint.setAttributeNS(null, "refer", keyrefStr);
            }
        } else {
            throw new XmlSchemaSerializerException("not valid identity constraint");
        }
        if (constraintObj.getName() != null) {
            constraint.setAttributeNS(null, "name", constraintObj.getName());
        }
        if (constraintObj.getAnnotation() != null) {
            Element annotation = this.serializeAnnotation(doc, constraintObj.getAnnotation(), schema);
            constraint.appendChild(annotation);
        }
        if (constraintObj.getSelector() != null) {
            Element selector = this.serializeSelector(doc, constraintObj.getSelector(), schema);
            constraint.appendChild(selector);
        }
        if ((fieldColl = constraintObj.getFields()) != null) {
            int fieldLength = fieldColl.size();
            for (int i = 0; i < fieldLength; ++i) {
                Element field2 = this.serializeField(doc, fieldColl.get(i), schema);
                constraint.appendChild(field2);
            }
        }
        this.processExtensibilityComponents(constraintObj, constraint);
        return constraint;
    }

    Element serializeImport(Document doc, XmlSchemaImport importObj, XmlSchema schema, boolean serializeIncluded) throws XmlSchemaSerializerException {
        Element importEl = this.createNewElement(doc, "import", schema.getSchemaNamespacePrefix(), XSD_NAMESPACE);
        if (importObj.namespace != null && !"".equals(importObj.namespace)) {
            importEl.setAttributeNS(null, "namespace", importObj.namespace);
        }
        if (importObj.schemaLocation != null && !importObj.schemaLocation.trim().equals("")) {
            importEl.setAttributeNS(null, "schemaLocation", importObj.schemaLocation);
        }
        if (importObj.getId() != null) {
            importEl.setAttributeNS(null, "id", importObj.getId());
        }
        if (importObj.getAnnotation() != null) {
            Element annotation = this.serializeAnnotation(doc, importObj.getAnnotation(), schema);
            importEl.appendChild(annotation);
        }
        if (importObj.schema != null && serializeIncluded) {
            XmlSchemaSerializer importSeri = new XmlSchemaSerializer();
            importSeri.serializeSchemaElement(importObj.schema, serializeIncluded);
            this.docs.addAll(importSeri.docs);
        }
        this.processExtensibilityComponents(importObj, importEl);
        return importEl;
    }

    Element serializeInclude(Document doc, XmlSchemaInclude includeObj, XmlSchema schema, boolean serializeIncluded) throws XmlSchemaSerializerException {
        XmlSchema includedSchemaObj;
        Element includeEl = this.createNewElement(doc, "include", schema.getSchemaNamespacePrefix(), XSD_NAMESPACE);
        if (includeObj.schemaLocation != null) {
            includeEl.setAttributeNS(null, "schemaLocation", includeObj.schemaLocation);
        }
        if (includeObj.getId() != null) {
            includeEl.setAttributeNS(null, "id", includeObj.getId());
        }
        if (includeObj.getAnnotation() != null) {
            Element annotation = this.serializeAnnotation(doc, includeObj.getAnnotation(), schema);
            includeEl.appendChild(annotation);
        }
        if ((includedSchemaObj = includeObj.getSchema()) != null && serializeIncluded) {
            XmlSchemaSerializer includeSeri = new XmlSchemaSerializer();
            includeSeri.serializeSchemaElement(includedSchemaObj, true);
            this.docs.addAll(includeSeri.docs);
        }
        this.processExtensibilityComponents(includeObj, includeEl);
        return includeEl;
    }

    Element serializeRedefine(Document doc, XmlSchemaRedefine redefineObj, XmlSchema schema) throws XmlSchemaSerializerException {
        Element redefine = this.createNewElement(doc, "redefine", schema.getSchemaNamespacePrefix(), XSD_NAMESPACE);
        if (redefineObj.schemaLocation == null) {
            throw new XmlSchemaSerializerException("redefine must have schemaLocation fields fill");
        }
        redefine.setAttributeNS(null, "schemaLocation", redefineObj.schemaLocation);
        if (redefineObj.getId() != null) {
            redefine.setAttributeNS(null, "id", redefineObj.getId());
        }
        if (redefineObj.getAnnotation() != null) {
            Element annotation = this.serializeAnnotation(doc, redefineObj.getAnnotation(), schema);
            redefine.appendChild(annotation);
        }
        int itemsLength = redefineObj.getItems().size();
        for (int i = 0; i < itemsLength; ++i) {
            XmlSchemaObject obj = redefineObj.getItems().get(i);
            if (obj instanceof XmlSchemaSimpleType) {
                Element simpleType = this.serializeSimpleType(doc, (XmlSchemaSimpleType)obj, schema);
                redefine.appendChild(simpleType);
                continue;
            }
            if (obj instanceof XmlSchemaComplexType) {
                Element complexType = this.serializeComplexType(doc, (XmlSchemaComplexType)obj, schema);
                redefine.appendChild(complexType);
                continue;
            }
            if (obj instanceof XmlSchemaGroupRef) {
                Element groupRef = this.serializeGroupRef(doc, (XmlSchemaGroupRef)obj, schema);
                redefine.appendChild(groupRef);
                continue;
            }
            if (obj instanceof XmlSchemaGroup) {
                Element group = this.serializeGroup(doc, (XmlSchemaGroup)obj, schema);
                redefine.appendChild(group);
                continue;
            }
            if (obj instanceof XmlSchemaAttributeGroup) {
                Element attributeGroup = this.serializeAttributeGroup(doc, (XmlSchemaAttributeGroup)obj, schema);
                redefine.appendChild(attributeGroup);
                continue;
            }
            if (!(obj instanceof XmlSchemaAttributeGroupRef)) continue;
            Element attributeGroupRef = this.serializeAttributeGroupRef(doc, (XmlSchemaAttributeGroupRef)obj, schema);
            redefine.appendChild(attributeGroupRef);
        }
        this.processExtensibilityComponents(redefineObj, redefine);
        return redefine;
    }

    Document[] serializeSchemaElement(XmlSchema schemaObj, boolean serializeIncluded) throws XmlSchemaSerializerException {
        String formQualified;
        Element serializedSchema;
        Document serializedSchemaDocs;
        List<XmlSchemaObject> items = schemaObj.getItems();
        try {
            DocumentBuilderFactory docFac = DocumentBuilderFactory.newInstance();
            docFac.setFeature("http://javax.xml.XMLConstants/feature/secure-processing", Boolean.TRUE);
            docFac.setNamespaceAware(true);
            DocumentBuilder builder = docFac.newDocumentBuilder();
            serializedSchemaDocs = builder.newDocument();
        }
        catch (ParserConfigurationException e) {
            throw new XmlSchemaException(e.getMessage());
        }
        this.schemaElement = serializedSchema = this.setupNamespaces(serializedSchemaDocs, schemaObj);
        if (schemaObj.getSyntacticalTargetNamespace() != null) {
            String targetNS;
            String targetNamespace = schemaObj.getSyntacticalTargetNamespace();
            if (targetNamespace != null && !"".equals(targetNamespace)) {
                serializedSchema.setAttributeNS(null, "targetNamespace", targetNamespace);
            }
            if ((targetNS = this.schemaNamespace.get(targetNamespace)) == null) {
                String prefix = null;
                if (schemaObj.getNamespaceContext() != null) {
                    prefix = schemaObj.getNamespaceContext().getPrefix(schemaObj.getSyntacticalTargetNamespace());
                }
                if (prefix == null && schemaObj.getParent() != null && schemaObj.getParent().getNamespaceContext() != null) {
                    prefix = schemaObj.getParent().getNamespaceContext().getPrefix(schemaObj.getSyntacticalTargetNamespace());
                }
                if (prefix == null) {
                    if (serializedSchema.getAttributeNode("xmlns") == null) {
                        prefix = "";
                    }
                } else {
                    String ns = serializedSchema.getAttribute("xmlns:" + prefix);
                    if (ns != null && !"".equals(ns)) {
                        prefix = null;
                    }
                }
                if (prefix == null) {
                    int count = 0;
                    prefix = "tns";
                    String ns = serializedSchema.getAttribute("xmlns:" + prefix);
                    while (ns != null && !"".equals(ns)) {
                        prefix = "tns" + ++count;
                        ns = serializedSchema.getAttribute("xmlns:" + prefix);
                    }
                }
                if ("".equals(prefix)) {
                    serializedSchema.setAttributeNS(XMLNS_NAMESPACE_URI, "xmlns", schemaObj.getSyntacticalTargetNamespace());
                } else {
                    serializedSchema.setAttributeNS(XMLNS_NAMESPACE_URI, "xmlns:" + prefix, schemaObj.getSyntacticalTargetNamespace());
                }
                this.schemaNamespace.put(schemaObj.getSyntacticalTargetNamespace(), prefix);
            }
        }
        if (schemaObj.getAttributeFormDefault() != null && !(formQualified = schemaObj.getAttributeFormDefault().toString()).equals(XmlSchemaForm.NONE.toString())) {
            serializedSchema.setAttributeNS(null, "attributeFormDefault", formQualified);
        }
        if (schemaObj.getElementFormDefault() != null && !(formQualified = schemaObj.getElementFormDefault().toString()).equals(XmlSchemaForm.NONE.toString())) {
            serializedSchema.setAttributeNS(null, "elementFormDefault", formQualified);
        }
        if (schemaObj.getAnnotation() != null) {
            Element annotation = this.serializeAnnotation(serializedSchemaDocs, schemaObj.getAnnotation(), schemaObj);
            serializedSchema.appendChild(annotation);
        }
        if (schemaObj.getId() != null) {
            serializedSchema.setAttributeNS(null, "id", schemaObj.getId());
        }
        if (schemaObj.getBlockDefault() != XmlSchemaDerivationMethod.NONE) {
            String blockDefault = schemaObj.getBlockDefault().toString();
            serializedSchema.setAttributeNS(null, "blockDefault", blockDefault);
        }
        if (schemaObj.getFinalDefault() != XmlSchemaDerivationMethod.NONE) {
            String finalDefault = schemaObj.getFinalDefault().toString();
            serializedSchema.setAttributeNS(null, "finalDefault", finalDefault);
        }
        if (schemaObj.getVersion() != null) {
            serializedSchema.setAttributeNS(null, "version", schemaObj.getVersion());
        }
        this.serializeSchemaChild(items, serializedSchema, serializedSchemaDocs, schemaObj, serializeIncluded);
        this.processExtensibilityComponents(schemaObj, serializedSchema);
        serializedSchemaDocs.appendChild(serializedSchema);
        this.docs.add(serializedSchemaDocs);
        Document[] serializedDocs = new Document[this.docs.size()];
        this.docs.toArray(serializedDocs);
        return serializedDocs;
    }

    Element serializeSelector(Document doc, XmlSchemaXPath selectorObj, XmlSchema schema) throws XmlSchemaSerializerException {
        Element selector = this.createNewElement(doc, "selector", schema.getSchemaNamespacePrefix(), XSD_NAMESPACE);
        if (selectorObj.xpath == null) {
            throw new XmlSchemaSerializerException("xpath can't be null");
        }
        selector.setAttributeNS(null, "xpath", selectorObj.xpath);
        if (selectorObj.getAnnotation() != null) {
            Element annotation = this.serializeAnnotation(doc, selectorObj.getAnnotation(), schema);
            selector.appendChild(annotation);
        }
        this.processExtensibilityComponents(selectorObj, selector);
        return selector;
    }

    Element serializeSequence(Document doc, XmlSchemaSequence sequenceObj, XmlSchema schema) throws XmlSchemaSerializerException {
        Element sequence2 = this.createNewElement(doc, "sequence", schema.getSchemaNamespacePrefix(), XSD_NAMESPACE);
        if (sequenceObj.getId() != null) {
            sequence2.setAttributeNS(null, "id", sequenceObj.getId());
        }
        this.serializeMaxMinOccurs(sequenceObj, sequence2);
        List<XmlSchemaSequenceMember> seqColl = sequenceObj.getItems();
        int containLength = seqColl.size();
        for (int i = 0; i < containLength; ++i) {
            XmlSchemaSequenceMember obj = seqColl.get(i);
            if (obj instanceof XmlSchemaElement) {
                Element el = this.serializeElement(doc, (XmlSchemaElement)obj, schema);
                sequence2.appendChild(el);
                continue;
            }
            if (obj instanceof XmlSchemaGroupRef) {
                Element group = this.serializeGroupRef(doc, (XmlSchemaGroupRef)obj, schema);
                sequence2.appendChild(group);
                continue;
            }
            if (obj instanceof XmlSchemaChoice) {
                Element choice = this.serializeChoice(doc, (XmlSchemaChoice)obj, schema);
                sequence2.appendChild(choice);
                continue;
            }
            if (obj instanceof XmlSchemaSequence) {
                Element sequenceChild = this.serializeSequence(doc, (XmlSchemaSequence)obj, schema);
                sequence2.appendChild(sequenceChild);
                continue;
            }
            if (!(obj instanceof XmlSchemaAny)) continue;
            Element any = this.serializeAny(doc, (XmlSchemaAny)obj, schema);
            sequence2.appendChild(any);
        }
        this.processExtensibilityComponents(sequenceObj, sequence2);
        return sequence2;
    }

    Element serializeSimpleContent(Document doc, XmlSchemaSimpleContent simpleContentObj, XmlSchema schema) throws XmlSchemaSerializerException {
        Element content;
        Element simpleContent = this.createNewElement(doc, "simpleContent", schema.getSchemaNamespacePrefix(), XSD_NAMESPACE);
        if (simpleContentObj.getAnnotation() != null) {
            Element annotation = this.serializeAnnotation(doc, simpleContentObj.getAnnotation(), schema);
            simpleContent.appendChild(annotation);
        }
        if (simpleContentObj.content instanceof XmlSchemaSimpleContentRestriction) {
            content = this.serializeSimpleContentRestriction(doc, (XmlSchemaSimpleContentRestriction)simpleContentObj.content, schema);
        } else if (simpleContentObj.content instanceof XmlSchemaSimpleContentExtension) {
            content = this.serializeSimpleContentExtension(doc, (XmlSchemaSimpleContentExtension)simpleContentObj.content, schema);
        } else {
            throw new XmlSchemaSerializerException("content of simple content must be restriction or extension");
        }
        simpleContent.appendChild(content);
        this.processExtensibilityComponents(simpleContentObj, simpleContent);
        return simpleContent;
    }

    Element serializeSimpleContentExtension(Document doc, XmlSchemaSimpleContentExtension extensionObj, XmlSchema schema) throws XmlSchemaSerializerException {
        Element extension = this.createNewElement(doc, "extension", schema.getSchemaNamespacePrefix(), XSD_NAMESPACE);
        if (extensionObj.getBaseTypeName() != null) {
            String baseTypeName = this.resolveQName(extensionObj.getBaseTypeName(), schema);
            extension.setAttributeNS(null, "base", baseTypeName);
        }
        if (extensionObj.getId() != null) {
            extension.setAttributeNS(null, "id", extensionObj.getId());
        }
        if (extensionObj.getAnnotation() != null) {
            Element annotation = this.serializeAnnotation(doc, extensionObj.getAnnotation(), schema);
            extension.appendChild(annotation);
        }
        List<XmlSchemaAttributeOrGroupRef> attributes = extensionObj.getAttributes();
        int attributeLength = attributes.size();
        for (int i = 0; i < attributeLength; ++i) {
            XmlSchemaObject obj = attributes.get(i);
            if (obj instanceof XmlSchemaAttribute) {
                Element attribute = this.serializeAttribute(doc, (XmlSchemaAttribute)obj, schema);
                extension.appendChild(attribute);
                continue;
            }
            if (!(obj instanceof XmlSchemaAttributeGroupRef)) continue;
            Element attributeGroupRef = this.serializeAttributeGroupRef(doc, (XmlSchemaAttributeGroupRef)obj, schema);
            extension.appendChild(attributeGroupRef);
        }
        if (extensionObj.getAnyAttribute() != null) {
            Element anyAttribute = this.serializeAnyAttribute(doc, extensionObj.getAnyAttribute(), schema);
            extension.appendChild(anyAttribute);
        }
        this.processExtensibilityComponents(extensionObj, extension);
        return extension;
    }

    Element serializeSimpleContentRestriction(Document doc, XmlSchemaSimpleContentRestriction restrictionObj, XmlSchema schema) throws XmlSchemaSerializerException {
        Element restriction = this.createNewElement(doc, "restriction", schema.getSchemaNamespacePrefix(), XSD_NAMESPACE);
        if (restrictionObj.getBaseTypeName() != null) {
            String baseTypeName = this.resolveQName(restrictionObj.getBaseTypeName(), schema);
            restriction.setAttributeNS(null, "base", baseTypeName);
        }
        if (restrictionObj.getId() != null) {
            restriction.setAttributeNS(null, "id", restrictionObj.getId());
        }
        if (restrictionObj.getAnnotation() != null) {
            Element annotation = this.serializeAnnotation(doc, restrictionObj.getAnnotation(), schema);
            restriction.appendChild(annotation);
        }
        if (restrictionObj.getBaseType() != null) {
            Element inlineSimpleType = this.serializeSimpleType(doc, restrictionObj.getBaseType(), schema);
            restriction.appendChild(inlineSimpleType);
        }
        List<XmlSchemaFacet> facets = restrictionObj.getFacets();
        int facetLength = facets.size();
        for (int i = 0; i < facetLength; ++i) {
            Element facet = this.serializeFacet(doc, facets.get(i), schema);
            restriction.appendChild(facet);
        }
        int attrCollLength = restrictionObj.getAttributes().size();
        for (int i = 0; i < attrCollLength; ++i) {
            XmlSchemaAnnotated obj = restrictionObj.getAttributes().get(i);
            if (obj instanceof XmlSchemaAttribute) {
                Element attribute = this.serializeAttribute(doc, (XmlSchemaAttribute)obj, schema);
                restriction.appendChild(attribute);
                continue;
            }
            if (!(obj instanceof XmlSchemaAttributeGroupRef)) continue;
            Element attributeGroup = this.serializeAttributeGroupRef(doc, (XmlSchemaAttributeGroupRef)obj, schema);
            restriction.appendChild(attributeGroup);
        }
        if (restrictionObj.anyAttribute != null) {
            Element anyAttribute = this.serializeAnyAttribute(doc, restrictionObj.anyAttribute, schema);
            restriction.appendChild(anyAttribute);
        }
        this.processExtensibilityComponents(restrictionObj, restriction);
        return restriction;
    }

    Element serializeSimpleType(Document doc, XmlSchemaSimpleType simpleTypeObj, XmlSchema schema) throws XmlSchemaSerializerException {
        Element serializedSimpleType = this.createNewElement(doc, "simpleType", schema.getSchemaNamespacePrefix(), XSD_NAMESPACE);
        if (simpleTypeObj.getFinalDerivation() != null && simpleTypeObj.getFinalDerivation() != XmlSchemaDerivationMethod.NONE) {
            serializedSimpleType.setAttributeNS(null, "final", simpleTypeObj.getFinalDerivation().toString());
        }
        if (simpleTypeObj.getId() != null) {
            serializedSimpleType.setAttributeNS(null, "id", simpleTypeObj.getId());
        }
        if (!simpleTypeObj.isAnonymous()) {
            serializedSimpleType.setAttributeNS(null, "name", simpleTypeObj.getName());
        }
        if (simpleTypeObj.getAnnotation() != null) {
            Element annotationEl = this.serializeAnnotation(doc, simpleTypeObj.getAnnotation(), schema);
            serializedSimpleType.appendChild(annotationEl);
        }
        if (simpleTypeObj.content != null) {
            if (simpleTypeObj.content instanceof XmlSchemaSimpleTypeRestriction) {
                Element restEl = this.serializeSimpleTypeRestriction(doc, (XmlSchemaSimpleTypeRestriction)simpleTypeObj.content, schema);
                serializedSimpleType.appendChild(restEl);
            } else if (simpleTypeObj.content instanceof XmlSchemaSimpleTypeList) {
                Element listEl = this.serializeSimpleTypeList(doc, (XmlSchemaSimpleTypeList)simpleTypeObj.content, schema);
                serializedSimpleType.appendChild(listEl);
            } else if (simpleTypeObj.content instanceof XmlSchemaSimpleTypeUnion) {
                Element unionEl = this.serializeSimpleTypeUnion(doc, (XmlSchemaSimpleTypeUnion)simpleTypeObj.content, schema);
                serializedSimpleType.appendChild(unionEl);
            }
        }
        this.processExtensibilityComponents(simpleTypeObj, serializedSimpleType);
        return serializedSimpleType;
    }

    Element serializeSimpleTypeList(Document doc, XmlSchemaSimpleTypeList listObj, XmlSchema schema) throws XmlSchemaSerializerException {
        Element list = this.createNewElement(doc, "list", schema.getSchemaNamespacePrefix(), XSD_NAMESPACE);
        if (listObj.itemTypeName != null) {
            String listItemType = this.resolveQName(listObj.itemTypeName, schema);
            list.setAttributeNS(null, "itemType", listItemType);
        }
        if (listObj.getId() != null) {
            list.setAttributeNS(null, "id", listObj.getId());
        } else if (listObj.itemType != null) {
            Element inlineSimpleEl = this.serializeSimpleType(doc, listObj.itemType, schema);
            list.appendChild(inlineSimpleEl);
        }
        if (listObj.getAnnotation() != null) {
            Element annotation = this.serializeAnnotation(doc, listObj.getAnnotation(), schema);
            list.appendChild(annotation);
        }
        this.processExtensibilityComponents(listObj, list);
        return list;
    }

    Element serializeSimpleTypeRestriction(Document doc, XmlSchemaSimpleTypeRestriction restrictionObj, XmlSchema schema) throws XmlSchemaSerializerException {
        Element serializedRestriction = this.createNewElement(doc, "restriction", schema.getSchemaNamespacePrefix(), XSD_NAMESPACE);
        if (schema.getSchemaNamespacePrefix().length() > 0) {
            serializedRestriction.setPrefix(schema.getSchemaNamespacePrefix());
        }
        if (restrictionObj.getBaseTypeName() != null) {
            String baseType = this.resolveQName(restrictionObj.getBaseTypeName(), schema);
            serializedRestriction.setAttributeNS(null, "base", baseType);
        } else if (restrictionObj.getBaseType() instanceof XmlSchemaSimpleType) {
            Element inlineSimpleType = this.serializeSimpleType(doc, restrictionObj.getBaseType(), schema);
            serializedRestriction.appendChild(inlineSimpleType);
        } else {
            throw new XmlSchemaSerializerException("restriction must be define with specifying base or inline simpleType");
        }
        if (restrictionObj.getId() != null) {
            serializedRestriction.setAttributeNS(null, "id", restrictionObj.getId());
        }
        if (restrictionObj.getAnnotation() != null) {
            Element annotation = this.serializeAnnotation(doc, restrictionObj.getAnnotation(), schema);
            serializedRestriction.appendChild(annotation);
        }
        if (restrictionObj.getFacets().size() > 0) {
            int facetsNum = restrictionObj.getFacets().size();
            for (int i = 0; i < facetsNum; ++i) {
                Element facetEl = this.serializeFacet(doc, restrictionObj.getFacets().get(i), schema);
                serializedRestriction.appendChild(facetEl);
            }
        }
        this.processExtensibilityComponents(restrictionObj, serializedRestriction);
        return serializedRestriction;
    }

    Element serializeSimpleTypeUnion(Document doc, XmlSchemaSimpleTypeUnion unionObj, XmlSchema schema) throws XmlSchemaSerializerException {
        int i;
        Element union = this.createNewElement(doc, "union", schema.getSchemaNamespacePrefix(), XSD_NAMESPACE);
        if (unionObj.getId() != null) {
            union.setAttributeNS(null, "id", unionObj.getId());
        }
        if (unionObj.getMemberTypesSource() != null) {
            union.setAttributeNS(null, "memberTypes", unionObj.getMemberTypesSource());
        } else {
            QName[] memberTypesQNames = unionObj.getMemberTypesQNames();
            if (memberTypesQNames != null && memberTypesQNames.length > 0) {
                StringBuilder memberTypes = new StringBuilder();
                int n = memberTypesQNames.length - 1;
                for (i = 0; i <= n; ++i) {
                    String prefix;
                    QName memberTypesQName = memberTypesQNames[i];
                    String namespace = memberTypesQName.getNamespaceURI();
                    if (namespace.length() != 0 && (prefix = this.schemaNamespace.get(namespace)).length() != 0) {
                        memberTypes.append(prefix).append(':');
                    }
                    memberTypes.append(memberTypesQName.getLocalPart());
                    if (i == n) continue;
                    memberTypes.append(' ');
                }
                union.setAttributeNS(null, "memberTypes", memberTypes.toString());
            }
        }
        if (unionObj.getBaseTypes().size() > 0) {
            int baseTypesLength = unionObj.getBaseTypes().size();
            for (i = 0; i < baseTypesLength; ++i) {
                try {
                    Element baseType = this.serializeSimpleType(doc, unionObj.getBaseTypes().get(i), schema);
                    union.appendChild(baseType);
                    continue;
                }
                catch (ClassCastException e) {
                    throw new XmlSchemaSerializerException("only inline simple type allow as attribute's inline type");
                }
            }
        }
        if (unionObj.getAnnotation() != null) {
            Element annotation = this.serializeAnnotation(doc, unionObj.getAnnotation(), schema);
            union.appendChild(annotation);
        }
        this.processExtensibilityComponents(unionObj, union);
        return union;
    }

    void setupAttr(Document doc, List<XmlSchemaAttributeOrGroupRef> attrColl, XmlSchema schema, Element container) throws XmlSchemaSerializerException {
        int collectionLength = attrColl.size();
        for (int i = 0; i < collectionLength; ++i) {
            Element attrEl;
            XmlSchemaAttributeOrGroupRef attr;
            XmlSchemaAttributeOrGroupRef obj = attrColl.get(i);
            if (obj instanceof XmlSchemaAttribute) {
                attr = (XmlSchemaAttribute)obj;
                attrEl = this.serializeAttribute(doc, (XmlSchemaAttribute)attr, schema);
                container.appendChild(attrEl);
                continue;
            }
            if (!(obj instanceof XmlSchemaAttributeGroupRef)) continue;
            attr = (XmlSchemaAttributeGroupRef)obj;
            attrEl = this.serializeAttributeGroupRef(doc, (XmlSchemaAttributeGroupRef)attr, schema);
            container.appendChild(attrEl);
        }
    }

    private void appendElement(Document doc, Element parent, Node children, XmlSchema schema) {
        Element elTmp = (Element)children;
        Element el = this.createNewElement(doc, elTmp.getLocalName(), schema.getSchemaNamespacePrefix(), XSD_NAMESPACE);
        NamedNodeMap attributes = el.getAttributes();
        int attributeLength = attributes.getLength();
        for (int i = 0; i < attributeLength; ++i) {
            Node n = attributes.item(i);
            el.setAttributeNS(null, n.getNodeName(), n.getNodeValue());
        }
        NodeList decendants = el.getChildNodes();
        int decendantLength = decendants.getLength();
        for (int i = 0; i < decendantLength; ++i) {
            String nValue;
            Node n = decendants.item(i);
            short nodeType = n.getNodeType();
            if (nodeType == 3) {
                nValue = n.getNodeValue();
                Text t2 = doc.createTextNode(nValue);
                el.appendChild(t2);
                continue;
            }
            if (nodeType == 4) {
                nValue = n.getNodeValue();
                CDATASection s2 = doc.createCDATASection(nValue);
                el.appendChild(s2);
                continue;
            }
            if (nodeType != 1) continue;
            this.appendElement(doc, el, n, schema);
        }
    }

    private Element constructFacet(XmlSchemaFacet facetObj, Document doc, XmlSchema schema, String tagName) {
        Element facetEl = this.createNewElement(doc, tagName, schema.getSchemaNamespacePrefix(), XSD_NAMESPACE);
        facetEl.setAttributeNS(null, "value", facetObj.value.toString());
        if (facetObj.fixed) {
            facetEl.setAttributeNS(null, "fixed", "true");
        }
        if (facetObj.getAnnotation() != null) {
            Element annotation = this.serializeAnnotation(doc, facetObj.getAnnotation(), schema);
            facetEl.appendChild(annotation);
        }
        return facetEl;
    }

    private Element createNewElement(Document document, String localName, String prefix, String namespace) {
        if (prefix.length() > 0) {
            prefix = prefix + ":";
        }
        String elementName = prefix + localName;
        return document.createElementNS(namespace, elementName);
    }

    private void processExtensibilityComponents(XmlSchemaObject schemaObject, Element parentElement) {
        Map<Object, Object> metaInfoMap;
        if (this.extReg != null && (metaInfoMap = schemaObject.getMetaInfoMap()) != null && !metaInfoMap.isEmpty()) {
            for (Object key : metaInfoMap.keySet()) {
                this.extReg.serializeExtension(schemaObject, metaInfoMap.get(key).getClass(), parentElement);
            }
        }
    }

    private String resolveQName(QName names, XmlSchema schemaObj) {
        String prefixStr;
        String prefix;
        String namespace = names.getNamespaceURI();
        String[] type = XmlSchemaSerializer.getParts(names.getLocalPart());
        String typeName = type.length > 1 ? type[1] : type[0];
        String string = prefix = "".equals(namespace) ? "" : this.schemaNamespace.get(namespace);
        if (prefix == null) {
            if ("http://www.w3.org/XML/1998/namespace".equals(namespace)) {
                prefix = "xml";
            } else {
                int magicNumber = 0;
                Collection<String> prefixes = this.schemaNamespace.values();
                while (prefixes.contains("ns" + magicNumber)) {
                    ++magicNumber;
                }
                prefix = "ns" + magicNumber;
                this.schemaNamespace.put(namespace, prefix);
                this.schemaElement.setAttributeNS(XMLNS_NAMESPACE_URI, "xmlns:" + prefix.toString(), namespace);
            }
        }
        prefixStr = (prefixStr = prefix.toString()).trim().length() > 0 ? prefixStr + ":" : "";
        return prefixStr + typeName;
    }

    private void serializeMaxMinOccurs(XmlSchemaParticle particle, Element element) {
        if (particle.getMaxOccurs() < Long.MAX_VALUE && (particle.getMaxOccurs() > 1L || particle.getMaxOccurs() == 0L)) {
            element.setAttributeNS(null, "maxOccurs", particle.getMaxOccurs() + "");
        } else if (particle.getMaxOccurs() == Long.MAX_VALUE) {
            element.setAttributeNS(null, "maxOccurs", "unbounded");
        }
        if (particle.getMinOccurs() > 1L || particle.getMinOccurs() == 0L) {
            element.setAttributeNS(null, "minOccurs", particle.getMinOccurs() + "");
        }
    }

    private void serializeSchemaChild(List<XmlSchemaObject> items, Element serializedSchema, Document serializedSchemaDocs, XmlSchema schemaObj, boolean serializeIncluded) throws XmlSchemaSerializerException {
        Element e;
        XmlSchemaObject obj;
        int i;
        int itemsLength = items.size();
        for (i = 0; i < itemsLength; ++i) {
            obj = items.get(i);
            if (obj instanceof XmlSchemaInclude) {
                e = this.serializeInclude(serializedSchemaDocs, (XmlSchemaInclude)obj, schemaObj, serializeIncluded);
                serializedSchema.appendChild(e);
                continue;
            }
            if (!(obj instanceof XmlSchemaImport)) continue;
            e = this.serializeImport(serializedSchemaDocs, (XmlSchemaImport)obj, schemaObj, serializeIncluded);
            serializedSchema.appendChild(e);
        }
        for (i = 0; i < itemsLength; ++i) {
            obj = items.get(i);
            if (obj instanceof XmlSchemaElement) {
                e = this.serializeElement(serializedSchemaDocs, (XmlSchemaElement)obj, schemaObj);
                serializedSchema.appendChild(e);
                continue;
            }
            if (obj instanceof XmlSchemaSimpleType) {
                e = this.serializeSimpleType(serializedSchemaDocs, (XmlSchemaSimpleType)obj, schemaObj);
                serializedSchema.appendChild(e);
                continue;
            }
            if (obj instanceof XmlSchemaComplexType) {
                e = this.serializeComplexType(serializedSchemaDocs, (XmlSchemaComplexType)obj, schemaObj);
                serializedSchema.appendChild(e);
                continue;
            }
            if (obj instanceof XmlSchemaGroup) {
                e = this.serializeGroup(serializedSchemaDocs, (XmlSchemaGroup)obj, schemaObj);
                serializedSchema.appendChild(e);
                continue;
            }
            if (obj instanceof XmlSchemaAttributeGroup) {
                e = this.serializeAttributeGroup(serializedSchemaDocs, (XmlSchemaAttributeGroup)obj, schemaObj);
                serializedSchema.appendChild(e);
                continue;
            }
            if (obj instanceof XmlSchemaAttribute) {
                e = this.serializeAttribute(serializedSchemaDocs, (XmlSchemaAttribute)obj, schemaObj);
                serializedSchema.appendChild(e);
                continue;
            }
            if (!(obj instanceof XmlSchemaRedefine)) continue;
            e = this.serializeRedefine(serializedSchemaDocs, (XmlSchemaRedefine)obj, schemaObj);
            serializedSchema.appendChild(e);
        }
    }

    private Element setupNamespaces(Document schemaDocs, XmlSchema schemaObj) {
        NamespacePrefixList ctx = schemaObj.getNamespaceContext();
        this.xsdPrefix = ctx != null ? ctx.getPrefix(XSD_NAMESPACE) : null;
        if (this.xsdPrefix == null) {
            this.xsdPrefix = "";
            if (ctx != null && ctx.getNamespaceURI(this.xsdPrefix).length() > 0) {
                this.xsdPrefix = "xsd";
            }
            int count = 0;
            while (ctx != null && ctx.getNamespaceURI(this.xsdPrefix).length() > 0) {
                this.xsdPrefix = "xsd" + ++count;
            }
        }
        schemaObj.setSchemaNamespacePrefix(this.xsdPrefix);
        Element schemaEl = this.createNewElement(schemaDocs, "schema", schemaObj.getSchemaNamespacePrefix(), XSD_NAMESPACE);
        if (ctx != null) {
            String[] prefixes = ctx.getDeclaredPrefixes();
            for (int i = 0; i < prefixes.length; ++i) {
                String uri;
                String prefix = prefixes[i];
                if (prefix == null || (uri = ctx.getNamespaceURI(prefix)).length() <= 0) continue;
                if ("".equals(prefix) || !this.schemaNamespace.containsKey(uri)) {
                    this.schemaNamespace.put(uri, prefix);
                }
                prefix = prefix.length() > 0 ? "xmlns:" + prefix : "xmlns";
                schemaEl.setAttributeNS(XMLNS_NAMESPACE_URI, prefix, uri);
            }
        }
        if (this.schemaNamespace.get(XSD_NAMESPACE) == null) {
            this.schemaNamespace.put(XSD_NAMESPACE, this.xsdPrefix);
            if ("".equals(this.xsdPrefix)) {
                schemaEl.setAttributeNS(XMLNS_NAMESPACE_URI, "xmlns", XSD_NAMESPACE);
            } else {
                schemaEl.setAttributeNS(XMLNS_NAMESPACE_URI, "xmlns:" + this.xsdPrefix, XSD_NAMESPACE);
            }
            schemaObj.setSchemaNamespacePrefix(this.xsdPrefix);
        }
        return schemaEl;
    }

    public static class XmlSchemaSerializerException
    extends Exception {
        private static final long serialVersionUID = 1L;

        public XmlSchemaSerializerException(String msg) {
            super(msg);
        }
    }
}

