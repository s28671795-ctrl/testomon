/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.common.xmlschema;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.xml.namespace.QName;
import org.apache.cxf.common.xmlschema.InvalidXmlSchemaReferenceException;
import org.apache.cxf.common.xmlschema.XmlSchemaUtils;
import org.apache.ws.commons.schema.XmlSchema;
import org.apache.ws.commons.schema.XmlSchemaAll;
import org.apache.ws.commons.schema.XmlSchemaAttribute;
import org.apache.ws.commons.schema.XmlSchemaAttributeGroupRef;
import org.apache.ws.commons.schema.XmlSchemaAttributeOrGroupRef;
import org.apache.ws.commons.schema.XmlSchemaChoice;
import org.apache.ws.commons.schema.XmlSchemaCollection;
import org.apache.ws.commons.schema.XmlSchemaComplexContentExtension;
import org.apache.ws.commons.schema.XmlSchemaComplexContentRestriction;
import org.apache.ws.commons.schema.XmlSchemaComplexType;
import org.apache.ws.commons.schema.XmlSchemaContent;
import org.apache.ws.commons.schema.XmlSchemaContentModel;
import org.apache.ws.commons.schema.XmlSchemaElement;
import org.apache.ws.commons.schema.XmlSchemaParticle;
import org.apache.ws.commons.schema.XmlSchemaSequence;
import org.apache.ws.commons.schema.XmlSchemaSequenceMember;
import org.apache.ws.commons.schema.XmlSchemaSimpleContentExtension;
import org.apache.ws.commons.schema.XmlSchemaSimpleContentRestriction;
import org.apache.ws.commons.schema.XmlSchemaType;
import org.apache.ws.commons.schema.extensions.ExtensionRegistry;
import org.apache.ws.commons.schema.resolver.URIResolver;
import org.apache.ws.commons.schema.utils.NamespaceMap;
import org.apache.ws.commons.schema.utils.NamespacePrefixList;
import org.apache.ws.commons.schema.utils.XmlSchemaObjectBase;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class SchemaCollection {
    private XmlSchemaCollection schemaCollection;
    private Map<XmlSchema, Set<XmlSchemaType>> xmlTypesCheckedForCrossImportsPerSchema = new HashMap<XmlSchema, Set<XmlSchemaType>>();

    public SchemaCollection() {
        this(new XmlSchemaCollection());
    }

    public SchemaCollection(XmlSchemaCollection col) {
        this.schemaCollection = col;
        if (this.schemaCollection.getNamespaceContext() == null) {
            this.schemaCollection.setNamespaceContext(new NamespaceMap());
        }
    }

    public XmlSchemaCollection getXmlSchemaCollection() {
        return this.schemaCollection;
    }

    public boolean equals(Object obj) {
        if (obj instanceof SchemaCollection) {
            return this.schemaCollection.equals(((SchemaCollection)obj).schemaCollection);
        }
        if (obj instanceof XmlSchemaCollection) {
            return this.schemaCollection.equals(obj);
        }
        return false;
    }

    public XmlSchemaElement getElementByQName(QName qname) {
        return this.schemaCollection.getElementByQName(qname);
    }

    public XmlSchemaAttribute getAttributeByQName(QName qname) {
        return this.schemaCollection.getAttributeByQName(qname);
    }

    public ExtensionRegistry getExtReg() {
        return this.schemaCollection.getExtReg();
    }

    public NamespacePrefixList getNamespaceContext() {
        return this.schemaCollection.getNamespaceContext();
    }

    public XmlSchemaType getTypeByQName(QName schemaTypeName) {
        return this.schemaCollection.getTypeByQName(schemaTypeName);
    }

    public XmlSchema[] getXmlSchema(String systemId) {
        return this.schemaCollection.getXmlSchema(systemId);
    }

    public XmlSchema[] getXmlSchemas() {
        return this.schemaCollection.getXmlSchemas();
    }

    public int hashCode() {
        return this.schemaCollection.hashCode();
    }

    public void init() {
        this.schemaCollection.init();
    }

    public XmlSchema read(Element elem, String uri) {
        return this.schemaCollection.read(elem, uri);
    }

    public XmlSchema read(Document d, String uri) {
        return this.schemaCollection.read(d, uri);
    }

    public XmlSchema read(Element elem) {
        return this.schemaCollection.read(elem);
    }

    public void setBaseUri(String baseUri) {
        this.schemaCollection.setBaseUri(baseUri);
    }

    public void setExtReg(ExtensionRegistry extReg) {
        this.schemaCollection.setExtReg(extReg);
    }

    public void setNamespaceContext(NamespacePrefixList namespaceContext) {
        this.schemaCollection.setNamespaceContext(namespaceContext);
    }

    public void setSchemaResolver(URIResolver schemaResolver) {
        this.schemaCollection.setSchemaResolver(schemaResolver);
    }

    public XmlSchema getSchemaByTargetNamespace(String namespaceURI) {
        for (XmlSchema schema : this.schemaCollection.getXmlSchemas()) {
            if ((namespaceURI == null || !namespaceURI.equals(schema.getTargetNamespace())) && (namespaceURI != null || schema.getTargetNamespace() != null)) continue;
            return schema;
        }
        return null;
    }

    public XmlSchema getSchemaForElement(QName name) {
        for (XmlSchema schema : this.schemaCollection.getXmlSchemas()) {
            if (!name.getNamespaceURI().equals(schema.getTargetNamespace())) continue;
            if (schema.getElementByName(name.getLocalPart()) != null) {
                return schema;
            }
            if (schema.getElementByName(name) == null) continue;
            return schema;
        }
        return null;
    }

    public XmlSchema newXmlSchemaInCollection(String namespaceURI) {
        return new XmlSchema(namespaceURI, this.schemaCollection);
    }

    public void validateQNameNamespace(QName qname) {
        if ("".equals(qname.getNamespaceURI())) {
            return;
        }
        for (XmlSchema schema : this.schemaCollection.getXmlSchemas()) {
            if (!schema.getTargetNamespace().equals(qname.getNamespaceURI())) continue;
            return;
        }
        throw new InvalidXmlSchemaReferenceException(qname + " refers to unknown namespace.");
    }

    public void validateElementName(QName referrer, QName elementQName) {
        XmlSchemaElement element = this.schemaCollection.getElementByQName(elementQName);
        if (element == null) {
            throw new InvalidXmlSchemaReferenceException(referrer + " references non-existent element " + elementQName);
        }
    }

    public void validateTypeName(QName referrer, QName typeQName) {
        XmlSchemaType type = this.schemaCollection.getTypeByQName(typeQName);
        if (type == null) {
            throw new InvalidXmlSchemaReferenceException(referrer + " references non-existent type " + typeQName);
        }
    }

    public void addCrossImports() {
        for (XmlSchema schema : this.schemaCollection.getXmlSchemas()) {
            this.addOneSchemaCrossImports(schema);
        }
    }

    private void addOneSchemaCrossImports(XmlSchema schema) {
        for (XmlSchemaElement element : schema.getElements().values()) {
            this.addElementCrossImportsElement(schema, element);
        }
        for (XmlSchemaAttribute attribute : schema.getAttributes().values()) {
            XmlSchemaUtils.addImportIfNeeded(schema, attribute.getRef().getTargetQName());
            XmlSchemaUtils.addImportIfNeeded(schema, attribute.getSchemaTypeName());
        }
        for (XmlSchemaType type : schema.getSchemaTypes().values()) {
            this.addCrossImportsType(schema, type);
        }
    }

    private void addElementCrossImportsElement(XmlSchema schema, XmlSchemaElement item) {
        XmlSchemaElement element = item;
        XmlSchemaUtils.addImportIfNeeded(schema, element.getRef().getTargetQName());
        XmlSchemaUtils.addImportIfNeeded(schema, element.getSchemaTypeName());
        XmlSchemaType schemaType = element.getSchemaType();
        if (!this.crossImportsAdded(schema, schemaType)) {
            this.addCrossImportsType(schema, schemaType);
        }
    }

    private boolean crossImportsAdded(XmlSchema schema, XmlSchemaType schemaType) {
        boolean result = true;
        if (schemaType != null) {
            Set<Object> xmlTypesCheckedForCrossImports;
            if (!this.xmlTypesCheckedForCrossImportsPerSchema.containsKey(schema)) {
                xmlTypesCheckedForCrossImports = new HashSet();
                this.xmlTypesCheckedForCrossImportsPerSchema.put(schema, xmlTypesCheckedForCrossImports);
            } else {
                xmlTypesCheckedForCrossImports = this.xmlTypesCheckedForCrossImportsPerSchema.get(schema);
            }
            if (!xmlTypesCheckedForCrossImports.contains(schemaType)) {
                xmlTypesCheckedForCrossImports.add(schemaType);
                result = false;
            }
        }
        return result;
    }

    private void addCrossImportsType(XmlSchema schema, XmlSchemaType schemaType) {
        if (schemaType instanceof XmlSchemaComplexType) {
            XmlSchemaComplexType complexType = (XmlSchemaComplexType)schemaType;
            XmlSchemaUtils.addImportIfNeeded(schema, complexType.getBaseSchemaTypeName());
            this.addCrossImports(schema, complexType.getContentModel());
            this.addCrossImportsAttributeList(schema, complexType.getAttributes());
            if (complexType.getParticle() instanceof XmlSchemaChoice) {
                XmlSchemaChoice choice = (XmlSchemaChoice)complexType.getParticle();
                this.addCrossImports(schema, choice);
            } else if (complexType.getParticle() instanceof XmlSchemaAll) {
                XmlSchemaAll all = (XmlSchemaAll)complexType.getParticle();
                this.addCrossImports(schema, all);
            } else if (complexType.getParticle() instanceof XmlSchemaSequence) {
                XmlSchemaSequence sequence2 = (XmlSchemaSequence)complexType.getParticle();
                this.addCrossImports(schema, sequence2);
            }
        }
    }

    private void addCrossImports(XmlSchema schema, XmlSchemaAll all) {
        for (XmlSchemaObjectBase xmlSchemaObjectBase : all.getItems()) {
            if (!(xmlSchemaObjectBase instanceof XmlSchemaElement)) continue;
            this.addElementCrossImportsElement(schema, (XmlSchemaElement)xmlSchemaObjectBase);
        }
    }

    private void addCrossImports(XmlSchema schema, XmlSchemaChoice choice) {
        for (XmlSchemaObjectBase xmlSchemaObjectBase : choice.getItems()) {
            if (!(xmlSchemaObjectBase instanceof XmlSchemaElement)) continue;
            this.addElementCrossImportsElement(schema, (XmlSchemaElement)xmlSchemaObjectBase);
        }
    }

    private void addCrossImports(XmlSchema schema, XmlSchemaSequence sequence2) {
        for (XmlSchemaSequenceMember seqMember : sequence2.getItems()) {
            if (!(seqMember instanceof XmlSchemaElement)) continue;
            this.addElementCrossImportsElement(schema, (XmlSchemaElement)seqMember);
        }
    }

    private void addCrossImportsAttributeList(XmlSchema schema, List<XmlSchemaAttributeOrGroupRef> list) {
        for (XmlSchemaAttributeOrGroupRef attr : list) {
            QName ref;
            if (attr instanceof XmlSchemaAttribute) {
                ref = ((XmlSchemaAttribute)attr).getRef().getTargetQName();
            } else {
                XmlSchemaAttributeGroupRef groupRef = (XmlSchemaAttributeGroupRef)attr;
                ref = groupRef.getRef().getTargetQName();
            }
            if (ref == null) continue;
            XmlSchemaUtils.addImportIfNeeded(schema, ref);
        }
    }

    private void addCrossImports(XmlSchema schema, XmlSchemaContentModel contentModel) {
        if (contentModel == null) {
            return;
        }
        XmlSchemaContent content = contentModel.getContent();
        if (content == null) {
            return;
        }
        if (content instanceof XmlSchemaComplexContentExtension) {
            XmlSchemaComplexContentExtension extension = (XmlSchemaComplexContentExtension)content;
            XmlSchemaUtils.addImportIfNeeded(schema, extension.getBaseTypeName());
            this.addCrossImportsAttributeList(schema, extension.getAttributes());
            XmlSchemaParticle particle = extension.getParticle();
            if (particle instanceof XmlSchemaSequence) {
                this.addCrossImports(schema, (XmlSchemaSequence)particle);
            } else if (particle instanceof XmlSchemaChoice) {
                this.addCrossImports(schema, (XmlSchemaChoice)particle);
            } else if (particle instanceof XmlSchemaAll) {
                this.addCrossImports(schema, (XmlSchemaAll)particle);
            }
        } else if (content instanceof XmlSchemaComplexContentRestriction) {
            XmlSchemaComplexContentRestriction restriction = (XmlSchemaComplexContentRestriction)content;
            XmlSchemaUtils.addImportIfNeeded(schema, restriction.getBaseTypeName());
            this.addCrossImportsAttributeList(schema, restriction.getAttributes());
        } else if (content instanceof XmlSchemaSimpleContentExtension) {
            XmlSchemaSimpleContentExtension extension = (XmlSchemaSimpleContentExtension)content;
            XmlSchemaUtils.addImportIfNeeded(schema, extension.getBaseTypeName());
            this.addCrossImportsAttributeList(schema, extension.getAttributes());
        } else if (content instanceof XmlSchemaSimpleContentRestriction) {
            XmlSchemaSimpleContentRestriction restriction = (XmlSchemaSimpleContentRestriction)content;
            XmlSchemaUtils.addImportIfNeeded(schema, restriction.getBaseTypeName());
            this.addCrossImportsAttributeList(schema, restriction.getAttributes());
        }
    }
}

