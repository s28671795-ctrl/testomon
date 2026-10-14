/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.common.xmlschema;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import javax.xml.namespace.QName;
import org.apache.cxf.common.logging.LogUtils;
import org.apache.cxf.common.xmlschema.SchemaCollection;
import org.apache.cxf.common.xmlschema.XmlSchemaInvalidOperation;
import org.apache.ws.commons.schema.XmlSchema;
import org.apache.ws.commons.schema.XmlSchemaAnnotated;
import org.apache.ws.commons.schema.XmlSchemaAttribute;
import org.apache.ws.commons.schema.XmlSchemaAttributeOrGroupRef;
import org.apache.ws.commons.schema.XmlSchemaComplexContentExtension;
import org.apache.ws.commons.schema.XmlSchemaComplexType;
import org.apache.ws.commons.schema.XmlSchemaContent;
import org.apache.ws.commons.schema.XmlSchemaContentModel;
import org.apache.ws.commons.schema.XmlSchemaElement;
import org.apache.ws.commons.schema.XmlSchemaEnumerationFacet;
import org.apache.ws.commons.schema.XmlSchemaExternal;
import org.apache.ws.commons.schema.XmlSchemaFacet;
import org.apache.ws.commons.schema.XmlSchemaForm;
import org.apache.ws.commons.schema.XmlSchemaImport;
import org.apache.ws.commons.schema.XmlSchemaParticle;
import org.apache.ws.commons.schema.XmlSchemaSimpleType;
import org.apache.ws.commons.schema.XmlSchemaSimpleTypeContent;
import org.apache.ws.commons.schema.XmlSchemaSimpleTypeRestriction;

public final class XmlSchemaUtils {
    public static final String XSI_NIL = "xsi:nil='true'";
    private static final Logger LOG = LogUtils.getL7dLogger(XmlSchemaUtils.class);

    private XmlSchemaUtils() {
    }

    public static void setElementRefName(XmlSchemaElement element, QName name) {
        if (name != null && (element.getQName() != null && !element.getQName().equals(name) || element.getName() != null && !element.getName().equals(name.getLocalPart()))) {
            LOG.severe("Attempt to set the refName of an element with a name or QName");
            throw new XmlSchemaInvalidOperation("Attempt to set the refName of an element with a name or QName.");
        }
        element.getRef().setTargetQName(name);
    }

    public static boolean isEumeration(XmlSchemaSimpleType type) {
        XmlSchemaSimpleTypeContent content = type.getContent();
        if (!(content instanceof XmlSchemaSimpleTypeRestriction)) {
            return false;
        }
        XmlSchemaSimpleTypeRestriction restriction = (XmlSchemaSimpleTypeRestriction)content;
        List<XmlSchemaFacet> facets = restriction.getFacets();
        for (XmlSchemaFacet facet : facets) {
            if (facet instanceof XmlSchemaEnumerationFacet) continue;
            return false;
        }
        return true;
    }

    public static List<String> enumeratorValues(XmlSchemaSimpleType type) {
        XmlSchemaSimpleTypeContent content = type.getContent();
        XmlSchemaSimpleTypeRestriction restriction = (XmlSchemaSimpleTypeRestriction)content;
        List<XmlSchemaFacet> facets = restriction.getFacets();
        ArrayList<String> values2 = new ArrayList<String>();
        for (XmlSchemaFacet facet : facets) {
            XmlSchemaEnumerationFacet enumFacet = (XmlSchemaEnumerationFacet)facet;
            values2.add(enumFacet.getValue().toString());
        }
        return values2;
    }

    public static boolean schemaImportsNamespace(XmlSchema schema, String namespaceUri) {
        List<XmlSchemaExternal> externals = schema.getExternals();
        for (XmlSchemaExternal what : externals) {
            XmlSchemaImport imp;
            if (!(what instanceof XmlSchemaImport) || !namespaceUri.equals((imp = (XmlSchemaImport)what).getNamespace())) continue;
            return true;
        }
        return false;
    }

    public static void addImportIfNeeded(XmlSchema schema, String namespaceUri) {
        if ("".equals(namespaceUri) || "http://www.w3.org/2001/XMLSchema".equals(namespaceUri) || schema.getTargetNamespace().equals(namespaceUri)) {
            return;
        }
        List<XmlSchemaExternal> externals = schema.getExternals();
        for (XmlSchemaExternal what : externals) {
            XmlSchemaImport imp;
            if (!(what instanceof XmlSchemaImport) || !namespaceUri.equals((imp = (XmlSchemaImport)what).getNamespace())) continue;
            return;
        }
        XmlSchemaImport imp = new XmlSchemaImport(schema);
        imp.setNamespace(namespaceUri);
    }

    public static void addImportIfNeeded(XmlSchema schema, QName qname) {
        if (qname == null) {
            return;
        }
        if (qname.getNamespaceURI() == null) {
            return;
        }
        XmlSchemaUtils.addImportIfNeeded(schema, qname.getNamespaceURI());
    }

    public static XmlSchemaElement findElementByRefName(SchemaCollection xmlSchemaCollection, QName name, String referencingURI) {
        String uri = name.getNamespaceURI();
        if ("".equals(uri)) {
            uri = referencingURI;
        }
        QName copyName = new QName(uri, name.getLocalPart());
        XmlSchemaElement target = xmlSchemaCollection.getElementByQName(copyName);
        assert (target != null);
        return target;
    }

    public static QName getBaseType(XmlSchemaComplexType type) {
        XmlSchemaContentModel model = type.getContentModel();
        if (model == null) {
            return null;
        }
        XmlSchemaContent content = model.getContent();
        if (content == null) {
            return null;
        }
        if (!(content instanceof XmlSchemaComplexContentExtension)) {
            return null;
        }
        XmlSchemaComplexContentExtension ext = (XmlSchemaComplexContentExtension)content;
        return ext.getBaseTypeName();
    }

    public static List<XmlSchemaAttributeOrGroupRef> getContentAttributes(XmlSchemaComplexType type) {
        XmlSchemaContentModel model = type.getContentModel();
        if (model == null) {
            return null;
        }
        XmlSchemaContent content = model.getContent();
        if (content == null) {
            return null;
        }
        if (!(content instanceof XmlSchemaComplexContentExtension)) {
            return null;
        }
        XmlSchemaComplexContentExtension ext = (XmlSchemaComplexContentExtension)content;
        return ext.getAttributes();
    }

    public static List<XmlSchemaAnnotated> getContentAttributes(XmlSchemaComplexType type, SchemaCollection collection) {
        ArrayList<XmlSchemaAnnotated> results = new ArrayList<XmlSchemaAnnotated>();
        QName baseTypeName = XmlSchemaUtils.getBaseType(type);
        if (baseTypeName != null) {
            XmlSchemaComplexType baseType = (XmlSchemaComplexType)collection.getTypeByQName(baseTypeName);
            results.addAll(XmlSchemaUtils.getContentAttributes(baseType, collection));
            List<XmlSchemaAttributeOrGroupRef> extAttrs = XmlSchemaUtils.getContentAttributes(type);
            results.addAll(extAttrs);
            return results;
        }
        List<XmlSchemaAttributeOrGroupRef> attrs = type.getAttributes();
        results.addAll(attrs);
        return results;
    }

    public static QName getElementQualifiedName(XmlSchemaElement element, XmlSchema schema) {
        if (element.getQName() != null) {
            return element.getQName();
        }
        if (element.getName() != null) {
            return new QName(schema.getTargetNamespace(), element.getName());
        }
        return null;
    }

    public static boolean isAttributeNameQualified(XmlSchemaAttribute attribute, XmlSchema schema) {
        if (attribute.isRef()) {
            throw new RuntimeException("isElementNameQualified on element with ref=");
        }
        if (attribute.getForm().equals((Object)XmlSchemaForm.QUALIFIED)) {
            return true;
        }
        if (attribute.getForm().equals((Object)XmlSchemaForm.UNQUALIFIED)) {
            return false;
        }
        return schema.getAttributeFormDefault().equals((Object)XmlSchemaForm.QUALIFIED);
    }

    public static boolean isAttributeQualified(XmlSchemaAttribute attribute, boolean global, XmlSchema localSchema, XmlSchema attributeSchema) {
        if (attribute.getQName() == null) {
            throw new RuntimeException("getSchemaQualifier on anonymous element.");
        }
        if (attribute.isRef()) {
            throw new RuntimeException("getSchemaQualified on the 'from' side of ref=.");
        }
        if (global) {
            return XmlSchemaUtils.isAttributeNameQualified(attribute, attributeSchema) || localSchema != null && !attribute.getQName().getNamespaceURI().equals(localSchema.getTargetNamespace());
        }
        return XmlSchemaUtils.isAttributeNameQualified(attribute, attributeSchema);
    }

    public static boolean isElementNameQualified(XmlSchemaElement element, XmlSchema schema) {
        if (element.isRef()) {
            throw new RuntimeException("isElementNameQualified on element with ref=");
        }
        if (element.getForm().equals((Object)XmlSchemaForm.QUALIFIED)) {
            return true;
        }
        if (element.getForm().equals((Object)XmlSchemaForm.UNQUALIFIED)) {
            return false;
        }
        return schema.getElementFormDefault().equals((Object)XmlSchemaForm.QUALIFIED);
    }

    public static boolean isElementQualified(XmlSchemaElement element, boolean global, XmlSchema localSchema, XmlSchema elementSchema) {
        QName qn = XmlSchemaUtils.getElementQualifiedName(element, localSchema);
        if (qn == null) {
            throw new RuntimeException("isElementQualified on anonymous element.");
        }
        if (element.isRef()) {
            throw new RuntimeException("isElementQualified on the 'from' side of ref=.");
        }
        if (global) {
            return XmlSchemaUtils.isElementNameQualified(element, elementSchema) || localSchema != null && !qn.getNamespaceURI().equals(localSchema.getTargetNamespace());
        }
        return XmlSchemaUtils.isElementNameQualified(element, elementSchema);
    }

    public static boolean isParticleArray(XmlSchemaParticle particle) {
        return particle.getMaxOccurs() > 1L;
    }

    public static boolean isParticleOptional(XmlSchemaParticle particle) {
        return particle.getMinOccurs() == 0L && particle.getMaxOccurs() == 1L;
    }
}

