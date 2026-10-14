/*
 * Decompiled with CFR 0.152.
 */
package org.apache.ws.commons.schema;

import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;
import javax.xml.namespace.NamespaceContext;
import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilderFactory;
import org.apache.ws.commons.schema.DocumentFragmentNodeList;
import org.apache.ws.commons.schema.XmlSchema;
import org.apache.ws.commons.schema.XmlSchemaAll;
import org.apache.ws.commons.schema.XmlSchemaAnnotated;
import org.apache.ws.commons.schema.XmlSchemaAnnotation;
import org.apache.ws.commons.schema.XmlSchemaAnnotationItem;
import org.apache.ws.commons.schema.XmlSchemaAny;
import org.apache.ws.commons.schema.XmlSchemaAnyAttribute;
import org.apache.ws.commons.schema.XmlSchemaAppInfo;
import org.apache.ws.commons.schema.XmlSchemaAttribute;
import org.apache.ws.commons.schema.XmlSchemaAttributeGroup;
import org.apache.ws.commons.schema.XmlSchemaAttributeGroupRef;
import org.apache.ws.commons.schema.XmlSchemaChoice;
import org.apache.ws.commons.schema.XmlSchemaCollection;
import org.apache.ws.commons.schema.XmlSchemaComplexContent;
import org.apache.ws.commons.schema.XmlSchemaComplexContentExtension;
import org.apache.ws.commons.schema.XmlSchemaComplexContentRestriction;
import org.apache.ws.commons.schema.XmlSchemaComplexType;
import org.apache.ws.commons.schema.XmlSchemaContentProcessing;
import org.apache.ws.commons.schema.XmlSchemaDerivationMethod;
import org.apache.ws.commons.schema.XmlSchemaDocumentation;
import org.apache.ws.commons.schema.XmlSchemaElement;
import org.apache.ws.commons.schema.XmlSchemaException;
import org.apache.ws.commons.schema.XmlSchemaFacet;
import org.apache.ws.commons.schema.XmlSchemaForm;
import org.apache.ws.commons.schema.XmlSchemaGroup;
import org.apache.ws.commons.schema.XmlSchemaGroupRef;
import org.apache.ws.commons.schema.XmlSchemaIdentityConstraint;
import org.apache.ws.commons.schema.XmlSchemaImport;
import org.apache.ws.commons.schema.XmlSchemaInclude;
import org.apache.ws.commons.schema.XmlSchemaKey;
import org.apache.ws.commons.schema.XmlSchemaKeyref;
import org.apache.ws.commons.schema.XmlSchemaNotation;
import org.apache.ws.commons.schema.XmlSchemaObject;
import org.apache.ws.commons.schema.XmlSchemaRedefine;
import org.apache.ws.commons.schema.XmlSchemaSequence;
import org.apache.ws.commons.schema.XmlSchemaSimpleContent;
import org.apache.ws.commons.schema.XmlSchemaSimpleContentExtension;
import org.apache.ws.commons.schema.XmlSchemaSimpleContentRestriction;
import org.apache.ws.commons.schema.XmlSchemaSimpleType;
import org.apache.ws.commons.schema.XmlSchemaSimpleTypeList;
import org.apache.ws.commons.schema.XmlSchemaSimpleTypeRestriction;
import org.apache.ws.commons.schema.XmlSchemaSimpleTypeUnion;
import org.apache.ws.commons.schema.XmlSchemaType;
import org.apache.ws.commons.schema.XmlSchemaUnique;
import org.apache.ws.commons.schema.XmlSchemaUse;
import org.apache.ws.commons.schema.XmlSchemaXPath;
import org.apache.ws.commons.schema.extensions.ExtensionRegistry;
import org.apache.ws.commons.schema.utils.NodeNamespaceContext;
import org.apache.ws.commons.schema.utils.TargetNamespaceValidator;
import org.apache.ws.commons.schema.utils.XDOMUtil;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;

public class SchemaBuilder {
    static ThreadLocal<Map<String, SoftReference<XmlSchema>>> resolvedSchemas = new ThreadLocal();
    private static final Set<String> RESERVED_ATTRIBUTES = new HashSet<String>();
    private static final String[] RESERVED_ATTRIBUTES_LIST;
    XmlSchemaCollection collection;
    Document currentDocument;
    XmlSchema currentSchema;
    DocumentBuilderFactory docFac;
    private final TargetNamespaceValidator currentValidator;
    private ExtensionRegistry extReg;

    SchemaBuilder(XmlSchemaCollection collection, TargetNamespaceValidator validator) {
        this.collection = collection;
        this.currentValidator = validator;
        if (collection.getExtReg() != null) {
            this.extReg = collection.getExtReg();
        }
        this.currentSchema = new XmlSchema();
    }

    public static void clearCache() {
        Map<String, SoftReference<XmlSchema>> threadResolvedSchemas = resolvedSchemas.get();
        if (threadResolvedSchemas != null) {
            threadResolvedSchemas.clear();
            resolvedSchemas.remove();
        }
    }

    public static void initCache() {
        Map<String, SoftReference<XmlSchema>> threadResolvedSchemas = resolvedSchemas.get();
        if (threadResolvedSchemas == null) {
            threadResolvedSchemas = Collections.synchronizedMap(new HashMap());
            resolvedSchemas.set(threadResolvedSchemas);
        }
    }

    public ExtensionRegistry getExtReg() {
        return this.extReg;
    }

    public void setExtReg(ExtensionRegistry extReg) {
        this.extReg = extReg;
    }

    XmlSchema build(Document doc, String uri) {
        Element schemaEl = doc.getDocumentElement();
        XmlSchema xmlSchema = this.handleXmlSchemaElement(schemaEl, uri);
        xmlSchema.setInputEncoding(doc.getInputEncoding());
        return xmlSchema;
    }

    XmlSchemaDerivationMethod getDerivation(Element el, String attrName) {
        if (el.hasAttribute(attrName) && !el.getAttribute(attrName).equals("")) {
            String derivationMethod = el.getAttribute(attrName).trim();
            return XmlSchemaDerivationMethod.schemaValueOf(derivationMethod);
        }
        return XmlSchemaDerivationMethod.NONE;
    }

    String getEnumString(Element el, String attrName) {
        if (el.hasAttribute(attrName)) {
            return el.getAttribute(attrName).trim();
        }
        return "none";
    }

    XmlSchemaForm getFormDefault(Element el, String attrName) {
        if (el.getAttributeNode(attrName) != null) {
            String value = el.getAttribute(attrName);
            return XmlSchemaForm.schemaValueOf(value);
        }
        return XmlSchemaForm.UNQUALIFIED;
    }

    long getMaxOccurs(Element el) {
        try {
            if (el.getAttributeNode("maxOccurs") != null) {
                String value = el.getAttribute("maxOccurs");
                if ("unbounded".equals(value)) {
                    return Long.MAX_VALUE;
                }
                return Long.parseLong(value);
            }
            return 1L;
        }
        catch (NumberFormatException e) {
            return 1L;
        }
    }

    long getMinOccurs(Element el) {
        try {
            if (el.getAttributeNode("minOccurs") != null) {
                String value = el.getAttribute("minOccurs");
                if ("unbounded".equals(value)) {
                    return Long.MAX_VALUE;
                }
                return Long.parseLong(value);
            }
            return 1L;
        }
        catch (NumberFormatException e) {
            return 1L;
        }
    }

    XmlSchemaAnnotation handleAnnotation(Element annotEl) {
        XmlSchemaAnnotation annotation = new XmlSchemaAnnotation();
        List<XmlSchemaAnnotationItem> content = annotation.getItems();
        Element appinfo = XDOMUtil.getFirstChildElementNS(annotEl, "http://www.w3.org/2001/XMLSchema", "appinfo");
        while (appinfo != null) {
            XmlSchemaAppInfo appInfoObj = this.handleAppInfo(appinfo);
            if (appInfoObj != null) {
                content.add(appInfoObj);
            }
            appinfo = XDOMUtil.getNextSiblingElementNS(appinfo, "http://www.w3.org/2001/XMLSchema", "appinfo");
        }
        Element documentation = XDOMUtil.getFirstChildElementNS(annotEl, "http://www.w3.org/2001/XMLSchema", "documentation");
        while (documentation != null) {
            XmlSchemaDocumentation docsObj = this.handleDocumentation(documentation);
            if (docsObj != null) {
                content.add(docsObj);
            }
            documentation = XDOMUtil.getNextSiblingElementNS(documentation, "http://www.w3.org/2001/XMLSchema", "documentation");
        }
        this.processExtensibilityComponents(annotation, annotEl, true);
        return annotation;
    }

    XmlSchemaAppInfo handleAppInfo(Element content) {
        XmlSchemaAppInfo appInfo = new XmlSchemaAppInfo();
        DocumentFragmentNodeList markup = new DocumentFragmentNodeList(content);
        if (!content.hasAttribute("source") && markup.getLength() == 0) {
            return null;
        }
        appInfo.setSource(this.getAttribute(content, "source"));
        appInfo.setMarkup(markup);
        return appInfo;
    }

    XmlSchemaComplexType handleComplexType(XmlSchema schema, Element complexEl, Element schemaEl, boolean topLevel) {
        XmlSchemaComplexType ct = new XmlSchemaComplexType(schema, topLevel);
        if (complexEl.hasAttribute("name")) {
            ct.setName(complexEl.getAttribute("name"));
        }
        Element el = XDOMUtil.getFirstChildElementNS((Node)complexEl, "http://www.w3.org/2001/XMLSchema");
        while (el != null) {
            if (el.getLocalName().equals("sequence")) {
                ct.setParticle(this.handleSequence(schema, el, schemaEl));
            } else if (el.getLocalName().equals("choice")) {
                ct.setParticle(this.handleChoice(schema, el, schemaEl));
            } else if (el.getLocalName().equals("all")) {
                ct.setParticle(this.handleAll(schema, el, schemaEl));
            } else if (el.getLocalName().equals("attribute")) {
                ct.getAttributes().add(this.handleAttribute(schema, el, schemaEl));
            } else if (el.getLocalName().equals("attributeGroup")) {
                ct.getAttributes().add(this.handleAttributeGroupRef(schema, el));
            } else if (el.getLocalName().equals("group")) {
                XmlSchemaGroupRef group = this.handleGroupRef(schema, el, schemaEl);
                if (group.getParticle() == null) {
                    ct.setParticle(group);
                } else {
                    ct.setParticle(group.getParticle());
                }
            } else if (el.getLocalName().equals("simpleContent")) {
                ct.setContentModel(this.handleSimpleContent(schema, el, schemaEl));
            } else if (el.getLocalName().equals("complexContent")) {
                ct.setContentModel(this.handleComplexContent(schema, el, schemaEl));
            } else if (el.getLocalName().equals("annotation")) {
                ct.setAnnotation(this.handleAnnotation(el));
            } else if (el.getLocalName().equals("anyAttribute")) {
                ct.setAnyAttribute(this.handleAnyAttribute(schema, el, schemaEl));
            }
            el = XDOMUtil.getNextSiblingElementNS((Node)el, "http://www.w3.org/2001/XMLSchema");
        }
        if (complexEl.hasAttribute("block")) {
            String blockStr = complexEl.getAttribute("block");
            ct.setBlock(XmlSchemaDerivationMethod.schemaValueOf(blockStr));
        }
        if (complexEl.hasAttribute("final")) {
            String finalstr = complexEl.getAttribute("final");
            ct.setFinal(XmlSchemaDerivationMethod.schemaValueOf(finalstr));
        }
        if (complexEl.hasAttribute("abstract")) {
            String abs = complexEl.getAttribute("abstract");
            if (abs.equalsIgnoreCase("true")) {
                ct.setAbstract(true);
            } else {
                ct.setAbstract(false);
            }
        }
        if (complexEl.hasAttribute("mixed")) {
            String mixed = complexEl.getAttribute("mixed");
            if (mixed.equalsIgnoreCase("true")) {
                ct.setMixed(true);
            } else {
                ct.setMixed(false);
            }
        }
        this.processExtensibilityComponents(ct, complexEl, true);
        return ct;
    }

    XmlSchemaDocumentation handleDocumentation(Element content) {
        XmlSchemaDocumentation documentation = new XmlSchemaDocumentation();
        List<Node> markup = this.getChildren(content);
        if (!content.hasAttribute("source") && !content.hasAttribute("xml:lang") && markup == null) {
            return null;
        }
        documentation.setSource(this.getAttribute(content, "source"));
        documentation.setLanguage(this.getAttribute(content, "xml:lang"));
        documentation.setMarkup(new DocumentFragmentNodeList(content));
        return documentation;
    }

    XmlSchemaElement handleElement(XmlSchema schema, Element el, Element schemaEl, boolean isGlobal) {
        Element uniqueEl;
        Element keyrefEl;
        XmlSchemaElement element = new XmlSchemaElement(schema, isGlobal);
        if (el.getAttributeNode("name") != null) {
            element.setName(el.getAttribute("name"));
        }
        boolean isQualified = schema.getElementFormDefault() == XmlSchemaForm.QUALIFIED;
        isQualified = this.handleElementForm(el, element, isQualified);
        this.handleElementName(isGlobal, element, isQualified);
        this.handleElementAnnotation(el, element);
        this.handleElementGlobalType(el, element);
        Element simpleTypeEl = XDOMUtil.getFirstChildElementNS(el, "http://www.w3.org/2001/XMLSchema", "simpleType");
        if (simpleTypeEl != null) {
            XmlSchemaSimpleType simpleType = this.handleSimpleType(schema, simpleTypeEl, schemaEl, false);
            element.setSchemaType(simpleType);
            element.setSchemaTypeName(simpleType.getQName());
        } else {
            Element complexTypeEl = XDOMUtil.getFirstChildElementNS(el, "http://www.w3.org/2001/XMLSchema", "complexType");
            if (complexTypeEl != null) {
                element.setSchemaType(this.handleComplexType(schema, complexTypeEl, schemaEl, false));
            }
        }
        Element keyEl = XDOMUtil.getFirstChildElementNS(el, "http://www.w3.org/2001/XMLSchema", "key");
        if (keyEl != null) {
            while (keyEl != null) {
                element.getConstraints().add(this.handleConstraint(keyEl, XmlSchemaKey.class));
                keyEl = XDOMUtil.getNextSiblingElementNS(keyEl, "http://www.w3.org/2001/XMLSchema", "key");
            }
        }
        if ((keyrefEl = XDOMUtil.getFirstChildElementNS(el, "http://www.w3.org/2001/XMLSchema", "keyref")) != null) {
            while (keyrefEl != null) {
                XmlSchemaKeyref keyRef = (XmlSchemaKeyref)this.handleConstraint(keyrefEl, XmlSchemaKeyref.class);
                if (keyrefEl.hasAttribute("refer")) {
                    String name = keyrefEl.getAttribute("refer");
                    keyRef.refer = this.getRefQName(name, el);
                }
                element.getConstraints().add(keyRef);
                keyrefEl = XDOMUtil.getNextSiblingElementNS(keyrefEl, "http://www.w3.org/2001/XMLSchema", "keyref");
            }
        }
        if ((uniqueEl = XDOMUtil.getFirstChildElementNS(el, "http://www.w3.org/2001/XMLSchema", "unique")) != null) {
            while (uniqueEl != null) {
                element.getConstraints().add(this.handleConstraint(uniqueEl, XmlSchemaUnique.class));
                uniqueEl = XDOMUtil.getNextSiblingElementNS(uniqueEl, "http://www.w3.org/2001/XMLSchema", "unique");
            }
        }
        if (el.hasAttribute("abstract")) {
            element.setAbstractElement(Boolean.valueOf(el.getAttribute("abstract")));
        }
        if (el.hasAttribute("block")) {
            element.setBlock(this.getDerivation(el, "block"));
        }
        if (el.hasAttribute("default")) {
            element.setDefaultValue(el.getAttribute("default"));
        }
        if (el.hasAttribute("final")) {
            element.setFinalDerivation(this.getDerivation(el, "final"));
        }
        if (el.hasAttribute("fixed")) {
            element.setFixedValue(el.getAttribute("fixed"));
        }
        if (el.hasAttribute("id")) {
            element.setId(el.getAttribute("id"));
        }
        if (el.hasAttribute("nillable")) {
            element.setNillable(Boolean.valueOf(el.getAttribute("nillable")));
        }
        if (el.hasAttribute("substitutionGroup")) {
            String substitutionGroup = el.getAttribute("substitutionGroup");
            element.setSubstitutionGroup(this.getRefQName(substitutionGroup, el));
        }
        element.setMinOccurs(this.getMinOccurs(el));
        element.setMaxOccurs(this.getMaxOccurs(el));
        this.processExtensibilityComponents(element, el, true);
        return element;
    }

    XmlSchemaImport handleImport(XmlSchema schema, Element importEl, Element schemaEl) {
        XmlSchemaImport schemaImport = new XmlSchemaImport(schema);
        Element annotationEl = XDOMUtil.getFirstChildElementNS(importEl, "http://www.w3.org/2001/XMLSchema", "annotation");
        if (annotationEl != null) {
            XmlSchemaAnnotation importAnnotation = this.handleAnnotation(annotationEl);
            schemaImport.setAnnotation(importAnnotation);
        }
        final String uri = schemaImport.namespace = importEl.getAttribute("namespace");
        schemaImport.schemaLocation = importEl.getAttribute("schemaLocation");
        TargetNamespaceValidator validator = new TargetNamespaceValidator(){

            @Override
            public void validate(XmlSchema pSchema) {
                boolean valid = this.isEmpty(uri) ? this.isEmpty(pSchema.getSyntacticalTargetNamespace()) : pSchema.getSyntacticalTargetNamespace().equals(uri);
                if (!valid) {
                    throw new XmlSchemaException("An imported schema was announced to have the namespace " + uri + ", but has the namespace " + pSchema.getSyntacticalTargetNamespace());
                }
            }

            private boolean isEmpty(String pValue) {
                return pValue == null || "".equals(pValue);
            }
        };
        schemaImport.schema = schema.getSourceURI() != null ? this.resolveXmlSchema(uri, schemaImport.schemaLocation, schema.getSourceURI(), validator) : this.resolveXmlSchema(schemaImport.namespace, schemaImport.schemaLocation, validator);
        return schemaImport;
    }

    XmlSchemaInclude handleInclude(XmlSchema schema, Element includeEl, Element schemaEl) {
        XmlSchemaInclude include = new XmlSchemaInclude(schema);
        Element annotationEl = XDOMUtil.getFirstChildElementNS(includeEl, "http://www.w3.org/2001/XMLSchema", "annotation");
        if (annotationEl != null) {
            XmlSchemaAnnotation includeAnnotation = this.handleAnnotation(annotationEl);
            include.setAnnotation(includeAnnotation);
        }
        include.schemaLocation = includeEl.getAttribute("schemaLocation");
        TargetNamespaceValidator validator = this.newIncludeValidator(schema);
        include.schema = schema.getSourceURI() != null ? this.resolveXmlSchema(schema.getLogicalTargetNamespace(), include.schemaLocation, schema.getSourceURI(), validator) : this.resolveXmlSchema(schema.getLogicalTargetNamespace(), include.schemaLocation, validator);
        this.processExtensibilityComponents(include, includeEl, true);
        return include;
    }

    XmlSchemaSimpleType handleSimpleType(XmlSchema schema, Element simpleEl, Element schemaEl, boolean topLevel) {
        XmlSchemaSimpleType simpleType = new XmlSchemaSimpleType(schema, topLevel);
        if (simpleEl.hasAttribute("name")) {
            simpleType.setName(simpleEl.getAttribute("name"));
        }
        this.handleSimpleTypeFinal(simpleEl, simpleType);
        Element simpleTypeAnnotationEl = XDOMUtil.getFirstChildElementNS(simpleEl, "http://www.w3.org/2001/XMLSchema", "annotation");
        if (simpleTypeAnnotationEl != null) {
            XmlSchemaAnnotation simpleTypeAnnotation = this.handleAnnotation(simpleTypeAnnotationEl);
            simpleType.setAnnotation(simpleTypeAnnotation);
        }
        Element restrictionEl = XDOMUtil.getFirstChildElementNS(simpleEl, "http://www.w3.org/2001/XMLSchema", "restriction");
        Element listEl = XDOMUtil.getFirstChildElementNS(simpleEl, "http://www.w3.org/2001/XMLSchema", "list");
        Element unionEl = XDOMUtil.getFirstChildElementNS(simpleEl, "http://www.w3.org/2001/XMLSchema", "union");
        if (restrictionEl != null) {
            this.handleSimpleTypeRestriction(schema, schemaEl, simpleType, restrictionEl);
        } else if (listEl != null) {
            this.handleSimpleTypeList(schema, schemaEl, simpleType, listEl);
        } else if (unionEl != null) {
            this.handleSimpleTypeUnion(schema, schemaEl, simpleType, unionEl);
        }
        this.processExtensibilityComponents(simpleType, simpleEl, true);
        return simpleType;
    }

    XmlSchema handleXmlSchemaElement(Element schemaEl, String systemId) {
        this.currentSchema.setNamespaceContext(NodeNamespaceContext.getNamespaceContext(schemaEl));
        this.setNamespaceAttributes(this.currentSchema, schemaEl);
        XmlSchemaCollection.SchemaKey schemaKey = new XmlSchemaCollection.SchemaKey(this.currentSchema.getLogicalTargetNamespace(), systemId);
        this.handleSchemaElementBasics(schemaEl, systemId, schemaKey);
        Element el = XDOMUtil.getFirstChildElementNS((Node)schemaEl, "http://www.w3.org/2001/XMLSchema");
        if (el == null && XDOMUtil.getFirstChildElementNS((Node)schemaEl, "http://www.w3.org/1999/XMLSchema") != null) {
            throw new XmlSchemaException("Schema defined using \"http://www.w3.org/1999/XMLSchema\" is not supported. Please update the schema to the \"http://www.w3.org/2001/XMLSchema\" namespace");
        }
        while (el != null) {
            this.handleSchemaElementChild(schemaEl, el);
            el = XDOMUtil.getNextSiblingElementNS((Node)el, "http://www.w3.org/2001/XMLSchema");
        }
        this.processExtensibilityComponents(this.currentSchema, schemaEl, false);
        return this.currentSchema;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    XmlSchema resolveXmlSchema(String targetNamespace, String schemaLocation, String baseUri, TargetNamespaceValidator validator) {
        if (this.getCachedSchema(targetNamespace, schemaLocation, baseUri) != null) {
            return this.getCachedSchema(targetNamespace, schemaLocation, baseUri);
        }
        if (schemaLocation != null && !"".equals(schemaLocation)) {
            InputSource source2 = this.collection.getSchemaResolver().resolveEntity(targetNamespace, schemaLocation, baseUri);
            if (source2 == null) {
                return this.collection.getKnownSchema(targetNamespace);
            }
            String systemId = source2.getSystemId() == null ? schemaLocation : source2.getSystemId();
            source2.setSystemId(systemId);
            XmlSchemaCollection.SchemaKey key = new XmlSchemaCollection.SchemaKey(targetNamespace, systemId);
            XmlSchema schema = this.collection.getSchema(key);
            if (schema != null) {
                return schema;
            }
            if (this.collection.check(key)) {
                this.collection.push(key);
                try {
                    XmlSchema readSchema = this.collection.read(source2, validator);
                    this.putCachedSchema(targetNamespace, schemaLocation, baseUri, readSchema);
                    XmlSchema xmlSchema = readSchema;
                    return xmlSchema;
                }
                finally {
                    this.collection.pop();
                }
            }
        } else {
            XmlSchema schema = this.collection.getKnownSchema(targetNamespace);
            if (schema != null) {
                return schema;
            }
        }
        return null;
    }

    XmlSchema resolveXmlSchema(String targetNamespace, String schemaLocation, TargetNamespaceValidator validator) {
        return this.resolveXmlSchema(targetNamespace, schemaLocation, this.collection.baseUri, validator);
    }

    void setNamespaceAttributes(XmlSchema schema, Element schemaEl) {
        if (schemaEl.getAttributeNode("targetNamespace") != null) {
            String contain = schemaEl.getAttribute("targetNamespace");
            schema.setTargetNamespace(contain);
        }
        if (this.currentValidator != null) {
            this.currentValidator.validate(schema);
        }
    }

    private String getAttribute(Element content, String attrName) {
        if (content.hasAttribute(attrName)) {
            return content.getAttribute(attrName);
        }
        return null;
    }

    private XmlSchema getCachedSchema(String targetNamespace, String schemaLocation, String baseUri) {
        String schemaKey;
        SoftReference<XmlSchema> softref;
        Map<String, SoftReference<XmlSchema>> threadResolvedSchemas;
        XmlSchema resolvedSchema = null;
        if (resolvedSchemas != null && (threadResolvedSchemas = resolvedSchemas.get()) != null && (softref = threadResolvedSchemas.get(schemaKey = targetNamespace + schemaLocation + baseUri)) != null) {
            resolvedSchema = softref.get();
        }
        return resolvedSchema;
    }

    private List<Node> getChildren(Element content) {
        ArrayList<Node> result = new ArrayList<Node>();
        for (Node n = content.getFirstChild(); n != null; n = n.getNextSibling()) {
            result.add(n);
        }
        if (result.size() == 0) {
            return null;
        }
        return result;
    }

    private QName getRefQName(String pName, NamespaceContext pContext) {
        String prefix;
        String localName;
        String uri;
        int offset = pName.indexOf(58);
        if (offset == -1) {
            uri = pContext.getNamespaceURI("");
            if ("".equals(uri)) {
                if (this.currentSchema.getTargetNamespace() == null && !this.currentSchema.getLogicalTargetNamespace().isEmpty()) {
                    return new QName(this.currentSchema.getLogicalTargetNamespace(), pName);
                }
                return new QName("", pName);
            }
            localName = pName;
            prefix = "";
        } else {
            prefix = pName.substring(0, offset);
            uri = pContext.getNamespaceURI(prefix);
            if (uri == null || "".equals(uri) && this.currentSchema.getParent() != null && this.currentSchema.getParent().getNamespaceContext() != null) {
                uri = this.currentSchema.getParent().getNamespaceContext().getNamespaceURI(prefix);
            }
            if (uri == null || "".equals(uri)) {
                throw new IllegalStateException("The prefix " + prefix + " is not bound.");
            }
            localName = pName.substring(offset + 1);
        }
        return new QName(uri, localName, prefix);
    }

    private QName getRefQName(String pName, Node pNode) {
        return this.getRefQName(pName, NodeNamespaceContext.getNamespaceContext(pNode));
    }

    private XmlSchemaAll handleAll(XmlSchema schema, Element allEl, Element schemaEl) {
        XmlSchemaAll all = new XmlSchemaAll();
        all.setMinOccurs(this.getMinOccurs(allEl));
        all.setMaxOccurs(this.getMaxOccurs(allEl));
        Element el = XDOMUtil.getFirstChildElementNS((Node)allEl, "http://www.w3.org/2001/XMLSchema");
        while (el != null) {
            if (el.getLocalName().equals("element")) {
                XmlSchemaElement element = this.handleElement(schema, el, schemaEl, false);
                all.getItems().add(element);
            } else if (el.getLocalName().equals("annotation")) {
                XmlSchemaAnnotation annotation = this.handleAnnotation(el);
                all.setAnnotation(annotation);
            }
            el = XDOMUtil.getNextSiblingElementNS((Node)el, "http://www.w3.org/2001/XMLSchema");
        }
        return all;
    }

    private XmlSchemaAny handleAny(XmlSchema schema, Element anyEl, Element schemaEl) {
        Element annotationEl;
        XmlSchemaAny any = new XmlSchemaAny();
        any.setTargetNamespace(schema.getLogicalTargetNamespace());
        if (anyEl.hasAttribute("namespace")) {
            any.setNamespace(anyEl.getAttribute("namespace"));
        }
        if (anyEl.hasAttribute("processContents")) {
            String processContent = this.getEnumString(anyEl, "processContents");
            any.setProcessContent(XmlSchemaContentProcessing.schemaValueOf(processContent));
        }
        if ((annotationEl = XDOMUtil.getFirstChildElementNS(anyEl, "http://www.w3.org/2001/XMLSchema", "annotation")) != null) {
            XmlSchemaAnnotation annotation = this.handleAnnotation(annotationEl);
            any.setAnnotation(annotation);
        }
        any.setMinOccurs(this.getMinOccurs(anyEl));
        any.setMaxOccurs(this.getMaxOccurs(anyEl));
        return any;
    }

    private XmlSchemaAnyAttribute handleAnyAttribute(XmlSchema schema, Element anyAttrEl, Element schemaEl) {
        Element annotationEl;
        XmlSchemaAnyAttribute anyAttr = new XmlSchemaAnyAttribute();
        if (anyAttrEl.hasAttribute("namespace")) {
            anyAttr.namespace = anyAttrEl.getAttribute("namespace");
        }
        if (anyAttrEl.hasAttribute("processContents")) {
            String contentProcessing = this.getEnumString(anyAttrEl, "processContents");
            anyAttr.processContent = XmlSchemaContentProcessing.schemaValueOf(contentProcessing);
        }
        if (anyAttrEl.hasAttribute("id")) {
            anyAttr.setId(anyAttrEl.getAttribute("id"));
        }
        if ((annotationEl = XDOMUtil.getFirstChildElementNS(anyAttrEl, "http://www.w3.org/2001/XMLSchema", "annotation")) != null) {
            XmlSchemaAnnotation annotation = this.handleAnnotation(annotationEl);
            anyAttr.setAnnotation(annotation);
        }
        return anyAttr;
    }

    private XmlSchemaAttribute handleAttribute(XmlSchema schema, Element attrEl, Element schemaEl) {
        return this.handleAttribute(schema, attrEl, schemaEl, false);
    }

    private XmlSchemaAttribute handleAttribute(XmlSchema schema, Element attrEl, Element schemaEl, boolean topLevel) {
        Element annotationEl;
        Element simpleTypeEl;
        String name;
        XmlSchemaAttribute attr = new XmlSchemaAttribute(schema, topLevel);
        if (attrEl.hasAttribute("name")) {
            name = attrEl.getAttribute("name");
            attr.setName(name);
        }
        if (attrEl.hasAttribute("type")) {
            name = attrEl.getAttribute("type");
            attr.setSchemaTypeName(this.getRefQName(name, attrEl));
        }
        if (attrEl.hasAttribute("default")) {
            attr.setDefaultValue(attrEl.getAttribute("default"));
        }
        if (attrEl.hasAttribute("fixed")) {
            attr.setFixedValue(attrEl.getAttribute("fixed"));
        }
        if (attrEl.hasAttribute("form")) {
            String formValue = this.getEnumString(attrEl, "form");
            attr.setForm(XmlSchemaForm.schemaValueOf(formValue));
        }
        if (attrEl.hasAttribute("id")) {
            attr.setId(attrEl.getAttribute("id"));
        }
        if (attrEl.hasAttribute("use")) {
            String useType = this.getEnumString(attrEl, "use");
            attr.setUse(XmlSchemaUse.schemaValueOf(useType));
        }
        if (attrEl.hasAttribute("ref")) {
            name = attrEl.getAttribute("ref");
            attr.getRef().setTargetQName(this.getRefQName(name, attrEl));
        }
        if ((simpleTypeEl = XDOMUtil.getFirstChildElementNS(attrEl, "http://www.w3.org/2001/XMLSchema", "simpleType")) != null) {
            attr.setSchemaType(this.handleSimpleType(schema, simpleTypeEl, schemaEl, false));
        }
        if ((annotationEl = XDOMUtil.getFirstChildElementNS(attrEl, "http://www.w3.org/2001/XMLSchema", "annotation")) != null) {
            XmlSchemaAnnotation annotation = this.handleAnnotation(annotationEl);
            attr.setAnnotation(annotation);
        }
        NamedNodeMap attrNodes = attrEl.getAttributes();
        ArrayList<Attr> attrs = new ArrayList<Attr>();
        NodeNamespaceContext ctx = null;
        for (int i = 0; i < attrNodes.getLength(); ++i) {
            String namespace;
            Attr att = (Attr)attrNodes.item(i);
            String attName = att.getName();
            if (RESERVED_ATTRIBUTES.contains(attName)) continue;
            attrs.add(att);
            String value = att.getValue();
            if (value.indexOf(":") <= -1) continue;
            String prefix = value.substring(0, value.indexOf(":"));
            if (ctx == null) {
                ctx = NodeNamespaceContext.getNamespaceContext(attrEl);
            }
            if ("".equals(namespace = ctx.getNamespaceURI(prefix))) continue;
            Attr nsAttr = attrEl.getOwnerDocument().createAttributeNS("http://www.w3.org/2000/xmlns/", "xmlns:" + prefix);
            nsAttr.setValue(namespace);
            attrs.add(nsAttr);
        }
        if (attrs.size() > 0) {
            attr.setUnhandledAttributes(attrs.toArray(new Attr[attrs.size()]));
        }
        this.processExtensibilityComponents(attr, attrEl, true);
        return attr;
    }

    private XmlSchemaAttributeGroup handleAttributeGroup(XmlSchema schema, Element groupEl, Element schemaEl) {
        XmlSchemaAttributeGroup attrGroup = new XmlSchemaAttributeGroup(schema);
        if (groupEl.hasAttribute("name")) {
            attrGroup.setName(groupEl.getAttribute("name"));
        }
        if (groupEl.hasAttribute("id")) {
            attrGroup.setId(groupEl.getAttribute("id"));
        }
        Element el = XDOMUtil.getFirstChildElementNS((Node)groupEl, "http://www.w3.org/2001/XMLSchema");
        while (el != null) {
            if (el.getLocalName().equals("attribute")) {
                XmlSchemaAttribute attr = this.handleAttribute(schema, el, schemaEl);
                attrGroup.getAttributes().add(attr);
            } else if (el.getLocalName().equals("attributeGroup")) {
                XmlSchemaAttributeGroupRef attrGroupRef = this.handleAttributeGroupRef(schema, el);
                attrGroup.getAttributes().add(attrGroupRef);
            } else if (el.getLocalName().equals("anyAttribute")) {
                attrGroup.setAnyAttribute(this.handleAnyAttribute(schema, el, schemaEl));
            } else if (el.getLocalName().equals("annotation")) {
                XmlSchemaAnnotation ann = this.handleAnnotation(el);
                attrGroup.setAnnotation(ann);
            }
            el = XDOMUtil.getNextSiblingElementNS((Node)el, "http://www.w3.org/2001/XMLSchema");
        }
        return attrGroup;
    }

    private XmlSchemaAttributeGroupRef handleAttributeGroupRef(XmlSchema schema, Element attrGroupEl) {
        Element annotationEl;
        XmlSchemaAttributeGroupRef attrGroup = new XmlSchemaAttributeGroupRef(schema);
        if (attrGroupEl.hasAttribute("ref")) {
            String ref = attrGroupEl.getAttribute("ref");
            attrGroup.getRef().setTargetQName(this.getRefQName(ref, attrGroupEl));
        }
        if (attrGroupEl.hasAttribute("id")) {
            attrGroup.setId(attrGroupEl.getAttribute("id"));
        }
        if ((annotationEl = XDOMUtil.getFirstChildElementNS(attrGroupEl, "http://www.w3.org/2001/XMLSchema", "annotation")) != null) {
            XmlSchemaAnnotation annotation = this.handleAnnotation(annotationEl);
            attrGroup.setAnnotation(annotation);
        }
        return attrGroup;
    }

    private XmlSchemaChoice handleChoice(XmlSchema schema, Element choiceEl, Element schemaEl) {
        XmlSchemaChoice choice = new XmlSchemaChoice();
        if (choiceEl.hasAttribute("id")) {
            choice.setId(choiceEl.getAttribute("id"));
        }
        choice.setMinOccurs(this.getMinOccurs(choiceEl));
        choice.setMaxOccurs(this.getMaxOccurs(choiceEl));
        Element el = XDOMUtil.getFirstChildElementNS((Node)choiceEl, "http://www.w3.org/2001/XMLSchema");
        while (el != null) {
            if (el.getLocalName().equals("sequence")) {
                XmlSchemaSequence seq = this.handleSequence(schema, el, schemaEl);
                choice.getItems().add(seq);
            } else if (el.getLocalName().equals("element")) {
                XmlSchemaElement element = this.handleElement(schema, el, schemaEl, false);
                choice.getItems().add(element);
            } else if (el.getLocalName().equals("group")) {
                XmlSchemaGroupRef group = this.handleGroupRef(schema, el, schemaEl);
                choice.getItems().add(group);
            } else if (el.getLocalName().equals("choice")) {
                XmlSchemaChoice choiceItem = this.handleChoice(schema, el, schemaEl);
                choice.getItems().add(choiceItem);
            } else if (el.getLocalName().equals("any")) {
                XmlSchemaAny any = this.handleAny(schema, el, schemaEl);
                choice.getItems().add(any);
            } else if (el.getLocalName().equals("annotation")) {
                XmlSchemaAnnotation annotation = this.handleAnnotation(el);
                choice.setAnnotation(annotation);
            }
            el = XDOMUtil.getNextSiblingElementNS((Node)el, "http://www.w3.org/2001/XMLSchema");
        }
        return choice;
    }

    private XmlSchemaComplexContent handleComplexContent(XmlSchema schema, Element complexEl, Element schemaEl) {
        XmlSchemaComplexContent complexContent = new XmlSchemaComplexContent();
        Element el = XDOMUtil.getFirstChildElementNS((Node)complexEl, "http://www.w3.org/2001/XMLSchema");
        while (el != null) {
            if (el.getLocalName().equals("restriction")) {
                complexContent.content = this.handleComplexContentRestriction(schema, el, schemaEl);
            } else if (el.getLocalName().equals("extension")) {
                complexContent.content = this.handleComplexContentExtension(schema, el, schemaEl);
            } else if (el.getLocalName().equals("annotation")) {
                complexContent.setAnnotation(this.handleAnnotation(el));
            }
            el = XDOMUtil.getNextSiblingElementNS((Node)el, "http://www.w3.org/2001/XMLSchema");
        }
        if (complexEl.hasAttribute("mixed")) {
            String mixed = complexEl.getAttribute("mixed");
            if (mixed.equalsIgnoreCase("true")) {
                complexContent.setMixed(true);
            } else {
                complexContent.setMixed(false);
            }
        }
        return complexContent;
    }

    private XmlSchemaComplexContentExtension handleComplexContentExtension(XmlSchema schema, Element extEl, Element schemaEl) {
        XmlSchemaComplexContentExtension ext = new XmlSchemaComplexContentExtension();
        if (extEl.hasAttribute("base")) {
            String name = extEl.getAttribute("base");
            ext.setBaseTypeName(this.getRefQName(name, extEl));
        }
        Element el = XDOMUtil.getFirstChildElementNS((Node)extEl, "http://www.w3.org/2001/XMLSchema");
        while (el != null) {
            if (el.getLocalName().equals("sequence")) {
                ext.setParticle(this.handleSequence(schema, el, schemaEl));
            } else if (el.getLocalName().equals("choice")) {
                ext.setParticle(this.handleChoice(schema, el, schemaEl));
            } else if (el.getLocalName().equals("all")) {
                ext.setParticle(this.handleAll(schema, el, schemaEl));
            } else if (el.getLocalName().equals("attribute")) {
                ext.getAttributes().add(this.handleAttribute(schema, el, schemaEl));
            } else if (el.getLocalName().equals("attributeGroup")) {
                ext.getAttributes().add(this.handleAttributeGroupRef(schema, el));
            } else if (el.getLocalName().equals("group")) {
                ext.setParticle(this.handleGroupRef(schema, el, schemaEl));
            } else if (el.getLocalName().equals("anyAttribute")) {
                ext.setAnyAttribute(this.handleAnyAttribute(schema, el, schemaEl));
            } else if (el.getLocalName().equals("annotation")) {
                ext.setAnnotation(this.handleAnnotation(el));
            }
            el = XDOMUtil.getNextSiblingElementNS((Node)el, "http://www.w3.org/2001/XMLSchema");
        }
        return ext;
    }

    private XmlSchemaComplexContentRestriction handleComplexContentRestriction(XmlSchema schema, Element restrictionEl, Element schemaEl) {
        XmlSchemaComplexContentRestriction restriction = new XmlSchemaComplexContentRestriction();
        if (restrictionEl.hasAttribute("base")) {
            String name = restrictionEl.getAttribute("base");
            restriction.setBaseTypeName(this.getRefQName(name, restrictionEl));
        }
        Element el = XDOMUtil.getFirstChildElementNS((Node)restrictionEl, "http://www.w3.org/2001/XMLSchema");
        while (el != null) {
            if (el.getLocalName().equals("sequence")) {
                restriction.setParticle(this.handleSequence(schema, el, schemaEl));
            } else if (el.getLocalName().equals("choice")) {
                restriction.setParticle(this.handleChoice(schema, el, schemaEl));
            } else if (el.getLocalName().equals("all")) {
                restriction.setParticle(this.handleAll(schema, el, schemaEl));
            } else if (el.getLocalName().equals("attribute")) {
                restriction.getAttributes().add(this.handleAttribute(schema, el, schemaEl));
            } else if (el.getLocalName().equals("attributeGroup")) {
                restriction.getAttributes().add(this.handleAttributeGroupRef(schema, el));
            } else if (el.getLocalName().equals("group")) {
                restriction.setParticle(this.handleGroupRef(schema, el, schemaEl));
            } else if (el.getLocalName().equals("anyAttribute")) {
                restriction.setAnyAttribute(this.handleAnyAttribute(schema, el, schemaEl));
            } else if (el.getLocalName().equals("annotation")) {
                restriction.setAnnotation(this.handleAnnotation(el));
            }
            el = XDOMUtil.getNextSiblingElementNS((Node)el, "http://www.w3.org/2001/XMLSchema");
        }
        return restriction;
    }

    private XmlSchemaIdentityConstraint handleConstraint(Element constraintEl, Class<? extends XmlSchemaIdentityConstraint> typeClass) {
        try {
            XmlSchemaIdentityConstraint constraint = typeClass.newInstance();
            if (constraintEl.hasAttribute("name")) {
                constraint.setName(constraintEl.getAttribute("name"));
            }
            if (constraintEl.hasAttribute("refer")) {
                String name = constraintEl.getAttribute("refer");
                ((XmlSchemaKeyref)constraint).refer = this.getRefQName(name, constraintEl);
            }
            Element el = XDOMUtil.getFirstChildElementNS((Node)constraintEl, "http://www.w3.org/2001/XMLSchema");
            while (el != null) {
                XmlSchemaAnnotation annotation;
                Element annotationEl;
                if (el.getLocalName().equals("selector")) {
                    XmlSchemaXPath selectorXPath = new XmlSchemaXPath();
                    selectorXPath.xpath = el.getAttribute("xpath");
                    annotationEl = XDOMUtil.getFirstChildElementNS(el, "http://www.w3.org/2001/XMLSchema", "annotation");
                    if (annotationEl != null) {
                        annotation = this.handleAnnotation(annotationEl);
                        selectorXPath.setAnnotation(annotation);
                    }
                    constraint.setSelector(selectorXPath);
                } else if (el.getLocalName().equals("field")) {
                    XmlSchemaXPath fieldXPath = new XmlSchemaXPath();
                    fieldXPath.xpath = el.getAttribute("xpath");
                    constraint.getFields().add(fieldXPath);
                    annotationEl = XDOMUtil.getFirstChildElementNS(el, "http://www.w3.org/2001/XMLSchema", "annotation");
                    if (annotationEl != null) {
                        annotation = this.handleAnnotation(annotationEl);
                        fieldXPath.setAnnotation(annotation);
                    }
                } else if (el.getLocalName().equals("annotation")) {
                    XmlSchemaAnnotation constraintAnnotation = this.handleAnnotation(el);
                    constraint.setAnnotation(constraintAnnotation);
                }
                el = XDOMUtil.getNextSiblingElementNS((Node)el, "http://www.w3.org/2001/XMLSchema");
            }
            return constraint;
        }
        catch (InstantiationException e) {
            throw new XmlSchemaException(e.getMessage());
        }
        catch (IllegalAccessException e) {
            throw new XmlSchemaException(e.getMessage());
        }
    }

    private void handleElementAnnotation(Element el, XmlSchemaElement element) {
        Element annotationEl = XDOMUtil.getFirstChildElementNS(el, "http://www.w3.org/2001/XMLSchema", "annotation");
        if (annotationEl != null) {
            XmlSchemaAnnotation annotation = this.handleAnnotation(annotationEl);
            element.setAnnotation(annotation);
        }
    }

    private boolean handleElementForm(Element el, XmlSchemaElement element, boolean isQualified) {
        if (el.hasAttribute("form")) {
            String formDef = el.getAttribute("form");
            element.setForm(XmlSchemaForm.schemaValueOf(formDef));
        }
        isQualified = element.getForm() == XmlSchemaForm.QUALIFIED;
        return isQualified;
    }

    private void handleElementGlobalType(Element el, XmlSchemaElement element) {
        if (el.getAttributeNode("type") != null) {
            String typeName = el.getAttribute("type");
            element.setSchemaTypeName(this.getRefQName(typeName, el));
            QName typeQName = element.getSchemaTypeName();
            XmlSchemaType type = this.collection.getTypeByQName(typeQName);
            if (type == null) {
                this.collection.addUnresolvedType(typeQName, element);
            }
            element.setSchemaType(type);
        } else if (el.getAttributeNode("ref") != null) {
            String refName = el.getAttribute("ref");
            QName refQName = this.getRefQName(refName, el);
            element.getRef().setTargetQName(refQName);
        }
    }

    private void handleElementName(boolean isGlobal, XmlSchemaElement element, boolean isQualified) {
    }

    private XmlSchemaGroup handleGroup(XmlSchema schema, Element groupEl, Element schemaEl) {
        XmlSchemaGroup group = new XmlSchemaGroup(schema);
        group.setName(groupEl.getAttribute("name"));
        Element el = XDOMUtil.getFirstChildElementNS((Node)groupEl, "http://www.w3.org/2001/XMLSchema");
        while (el != null) {
            if (el.getLocalName().equals("all")) {
                group.setParticle(this.handleAll(schema, el, schemaEl));
            } else if (el.getLocalName().equals("sequence")) {
                group.setParticle(this.handleSequence(schema, el, schemaEl));
            } else if (el.getLocalName().equals("choice")) {
                group.setParticle(this.handleChoice(schema, el, schemaEl));
            } else if (el.getLocalName().equals("annotation")) {
                XmlSchemaAnnotation groupAnnotation = this.handleAnnotation(el);
                group.setAnnotation(groupAnnotation);
            }
            el = XDOMUtil.getNextSiblingElementNS((Node)el, "http://www.w3.org/2001/XMLSchema");
        }
        return group;
    }

    private XmlSchemaGroupRef handleGroupRef(XmlSchema schema, Element groupEl, Element schemaEl) {
        XmlSchemaGroupRef group = new XmlSchemaGroupRef();
        group.setMaxOccurs(this.getMaxOccurs(groupEl));
        group.setMinOccurs(this.getMinOccurs(groupEl));
        Element annotationEl = XDOMUtil.getFirstChildElementNS(groupEl, "http://www.w3.org/2001/XMLSchema", "annotation");
        if (annotationEl != null) {
            XmlSchemaAnnotation annotation = this.handleAnnotation(annotationEl);
            group.setAnnotation(annotation);
        }
        if (groupEl.hasAttribute("ref")) {
            String ref = groupEl.getAttribute("ref");
            group.setRefName(this.getRefQName(ref, groupEl));
            return group;
        }
        Element el = XDOMUtil.getFirstChildElementNS((Node)groupEl, "http://www.w3.org/2001/XMLSchema");
        while (el != null) {
            if (el.getLocalName().equals("sequence")) {
                group.setParticle(this.handleSequence(schema, el, schemaEl));
            } else if (el.getLocalName().equals("all")) {
                group.setParticle(this.handleAll(schema, el, schemaEl));
            } else if (el.getLocalName().equals("choice")) {
                group.setParticle(this.handleChoice(schema, el, schemaEl));
            }
            el = XDOMUtil.getNextSiblingElement(el);
        }
        return group;
    }

    private XmlSchemaNotation handleNotation(XmlSchema schema, Element notationEl) {
        Element annotationEl;
        XmlSchemaNotation notation = new XmlSchemaNotation(schema);
        if (notationEl.hasAttribute("id")) {
            notation.setId(notationEl.getAttribute("id"));
        }
        if (notationEl.hasAttribute("name")) {
            notation.setName(notationEl.getAttribute("name"));
        }
        if (notationEl.hasAttribute("public")) {
            notation.setPublicNotation(notationEl.getAttribute("public"));
        }
        if (notationEl.hasAttribute("system")) {
            notation.setSystem(notationEl.getAttribute("system"));
        }
        if ((annotationEl = XDOMUtil.getFirstChildElementNS(notationEl, "http://www.w3.org/2001/XMLSchema", "annotation")) != null) {
            XmlSchemaAnnotation annotation = this.handleAnnotation(annotationEl);
            notation.setAnnotation(annotation);
        }
        return notation;
    }

    private XmlSchemaRedefine handleRedefine(XmlSchema schema, Element redefineEl, Element schemaEl) {
        XmlSchemaRedefine redefine = new XmlSchemaRedefine(schema);
        redefine.schemaLocation = redefineEl.getAttribute("schemaLocation");
        TargetNamespaceValidator validator = this.newIncludeValidator(schema);
        redefine.schema = schema.getSourceURI() != null ? this.resolveXmlSchema(schema.getLogicalTargetNamespace(), redefine.schemaLocation, schema.getSourceURI(), validator) : this.resolveXmlSchema(schema.getLogicalTargetNamespace(), redefine.schemaLocation, validator);
        Element el = XDOMUtil.getFirstChildElementNS((Node)redefineEl, "http://www.w3.org/2001/XMLSchema");
        while (el != null) {
            XmlSchemaAnnotated group;
            XmlSchemaType type;
            if (el.getLocalName().equals("simpleType")) {
                type = this.handleSimpleType(schema, el, schemaEl, false);
                redefine.getSchemaTypes().put(type.getQName(), type);
                redefine.getItems().add(type);
            } else if (el.getLocalName().equals("complexType")) {
                type = this.handleComplexType(schema, el, schemaEl, true);
                redefine.getSchemaTypes().put(type.getQName(), type);
                redefine.getItems().add(type);
            } else if (el.getLocalName().equals("group")) {
                group = this.handleGroup(schema, el, schemaEl);
                redefine.getGroups().put(((XmlSchemaGroup)group).getQName(), (XmlSchemaGroup)group);
                redefine.getItems().add(group);
            } else if (el.getLocalName().equals("attributeGroup")) {
                group = this.handleAttributeGroup(schema, el, schemaEl);
                redefine.getAttributeGroups().put(((XmlSchemaAttributeGroup)group).getQName(), (XmlSchemaAttributeGroup)group);
                redefine.getItems().add(group);
            } else if (el.getLocalName().equals("annotation")) {
                XmlSchemaAnnotation annotation = this.handleAnnotation(el);
                redefine.setAnnotation(annotation);
            }
            el = XDOMUtil.getNextSiblingElementNS((Node)el, "http://www.w3.org/2001/XMLSchema");
        }
        return redefine;
    }

    private void handleSchemaElementBasics(Element schemaEl, String systemId, XmlSchemaCollection.SchemaKey schemaKey) {
        if (this.collection.containsSchema(schemaKey)) {
            throw new XmlSchemaException("Schema name conflict in collection. Namespace: " + this.currentSchema.getLogicalTargetNamespace());
        }
        this.collection.addSchema(schemaKey, this.currentSchema);
        this.currentSchema.setParent(this.collection);
        this.currentSchema.setElementFormDefault(this.getFormDefault(schemaEl, "elementFormDefault"));
        this.currentSchema.setAttributeFormDefault(this.getFormDefault(schemaEl, "attributeFormDefault"));
        this.currentSchema.setBlockDefault(this.getDerivation(schemaEl, "blockDefault"));
        this.currentSchema.setFinalDefault(this.getDerivation(schemaEl, "finalDefault"));
        if (schemaEl.hasAttribute("id")) {
            this.currentSchema.setId(schemaEl.getAttribute("id"));
        }
        if (schemaEl.hasAttribute("version")) {
            this.currentSchema.setVersion(schemaEl.getAttribute("version"));
        }
        this.currentSchema.setSourceURI(systemId);
    }

    private void handleSchemaElementChild(Element schemaEl, Element el) {
        if (el.getLocalName().equals("simpleType")) {
            XmlSchemaSimpleType type = this.handleSimpleType(this.currentSchema, el, schemaEl, true);
            this.collection.resolveType(type.getQName(), type);
        } else if (el.getLocalName().equals("complexType")) {
            XmlSchemaComplexType type = this.handleComplexType(this.currentSchema, el, schemaEl, true);
            this.collection.resolveType(type.getQName(), type);
        } else if (el.getLocalName().equals("element")) {
            this.handleElement(this.currentSchema, el, schemaEl, true);
        } else if (el.getLocalName().equals("include")) {
            this.handleInclude(this.currentSchema, el, schemaEl);
        } else if (el.getLocalName().equals("import")) {
            this.handleImport(this.currentSchema, el, schemaEl);
        } else if (el.getLocalName().equals("group")) {
            this.handleGroup(this.currentSchema, el, schemaEl);
        } else if (el.getLocalName().equals("attributeGroup")) {
            this.handleAttributeGroup(this.currentSchema, el, schemaEl);
        } else if (el.getLocalName().equals("attribute")) {
            this.handleAttribute(this.currentSchema, el, schemaEl, true);
        } else if (el.getLocalName().equals("redefine")) {
            this.handleRedefine(this.currentSchema, el, schemaEl);
        } else if (el.getLocalName().equals("notation")) {
            this.handleNotation(this.currentSchema, el);
        } else if (el.getLocalName().equals("annotation")) {
            XmlSchemaAnnotation annotation = this.handleAnnotation(el);
            this.currentSchema.setAnnotation(annotation);
        }
    }

    private XmlSchemaSequence handleSequence(XmlSchema schema, Element sequenceEl, Element schemaEl) {
        XmlSchemaSequence sequence2 = new XmlSchemaSequence();
        sequence2.setMinOccurs(this.getMinOccurs(sequenceEl));
        sequence2.setMaxOccurs(this.getMaxOccurs(sequenceEl));
        Element el = XDOMUtil.getFirstChildElementNS((Node)sequenceEl, "http://www.w3.org/2001/XMLSchema");
        while (el != null) {
            if (el.getLocalName().equals("sequence")) {
                XmlSchemaSequence seq = this.handleSequence(schema, el, schemaEl);
                sequence2.getItems().add(seq);
            } else if (el.getLocalName().equals("element")) {
                XmlSchemaElement element = this.handleElement(schema, el, schemaEl, false);
                sequence2.getItems().add(element);
            } else if (el.getLocalName().equals("group")) {
                XmlSchemaGroupRef group = this.handleGroupRef(schema, el, schemaEl);
                sequence2.getItems().add(group);
            } else if (el.getLocalName().equals("choice")) {
                XmlSchemaChoice choice = this.handleChoice(schema, el, schemaEl);
                sequence2.getItems().add(choice);
            } else if (el.getLocalName().equals("any")) {
                XmlSchemaAny any = this.handleAny(schema, el, schemaEl);
                sequence2.getItems().add(any);
            } else if (el.getLocalName().equals("annotation")) {
                XmlSchemaAnnotation annotation = this.handleAnnotation(el);
                sequence2.setAnnotation(annotation);
            }
            el = XDOMUtil.getNextSiblingElementNS((Node)el, "http://www.w3.org/2001/XMLSchema");
        }
        return sequence2;
    }

    private XmlSchemaSimpleContent handleSimpleContent(XmlSchema schema, Element simpleEl, Element schemaEl) {
        XmlSchemaSimpleContent simpleContent = new XmlSchemaSimpleContent();
        Element el = XDOMUtil.getFirstChildElementNS((Node)simpleEl, "http://www.w3.org/2001/XMLSchema");
        while (el != null) {
            if (el.getLocalName().equals("restriction")) {
                simpleContent.content = this.handleSimpleContentRestriction(schema, el, schemaEl);
            } else if (el.getLocalName().equals("extension")) {
                simpleContent.content = this.handleSimpleContentExtension(schema, el, schemaEl);
            } else if (el.getLocalName().equals("annotation")) {
                simpleContent.setAnnotation(this.handleAnnotation(el));
            }
            el = XDOMUtil.getNextSiblingElementNS((Node)el, "http://www.w3.org/2001/XMLSchema");
        }
        return simpleContent;
    }

    private XmlSchemaSimpleContentExtension handleSimpleContentExtension(XmlSchema schema, Element extEl, Element schemaEl) {
        XmlSchemaSimpleContentExtension ext = new XmlSchemaSimpleContentExtension();
        if (extEl.hasAttribute("base")) {
            String name = extEl.getAttribute("base");
            ext.setBaseTypeName(this.getRefQName(name, extEl));
        }
        Element el = XDOMUtil.getFirstChildElementNS((Node)extEl, "http://www.w3.org/2001/XMLSchema");
        while (el != null) {
            if (el.getLocalName().equals("attribute")) {
                XmlSchemaAttribute attr = this.handleAttribute(schema, el, schemaEl);
                ext.getAttributes().add(attr);
            } else if (el.getLocalName().equals("attributeGroup")) {
                XmlSchemaAttributeGroupRef attrGroup = this.handleAttributeGroupRef(schema, el);
                ext.getAttributes().add(attrGroup);
            } else if (el.getLocalName().equals("anyAttribute")) {
                ext.setAnyAttribute(this.handleAnyAttribute(schema, el, schemaEl));
            } else if (el.getLocalName().equals("annotation")) {
                XmlSchemaAnnotation ann = this.handleAnnotation(el);
                ext.setAnnotation(ann);
            }
            el = XDOMUtil.getNextSiblingElementNS((Node)el, "http://www.w3.org/2001/XMLSchema");
        }
        return ext;
    }

    private XmlSchemaSimpleContentRestriction handleSimpleContentRestriction(XmlSchema schema, Element restrictionEl, Element schemaEl) {
        XmlSchemaSimpleContentRestriction restriction = new XmlSchemaSimpleContentRestriction();
        if (restrictionEl.hasAttribute("base")) {
            String name = restrictionEl.getAttribute("base");
            restriction.setBaseTypeName(this.getRefQName(name, restrictionEl));
        }
        if (restrictionEl.hasAttribute("id")) {
            restriction.setId(restrictionEl.getAttribute("id"));
        }
        Element el = XDOMUtil.getFirstChildElementNS((Node)restrictionEl, "http://www.w3.org/2001/XMLSchema");
        while (el != null) {
            if (el.getLocalName().equals("attribute")) {
                XmlSchemaAttribute attr = this.handleAttribute(schema, el, schemaEl);
                restriction.getAttributes().add(attr);
            } else if (el.getLocalName().equals("attributeGroup")) {
                XmlSchemaAttributeGroupRef attrGroup = this.handleAttributeGroupRef(schema, el);
                restriction.getAttributes().add(attrGroup);
            } else if (el.getLocalName().equals("simpleType")) {
                restriction.setBaseType(this.handleSimpleType(schema, el, schemaEl, false));
            } else if (el.getLocalName().equals("anyAttribute")) {
                restriction.anyAttribute = this.handleAnyAttribute(schema, el, schemaEl);
            } else if (el.getLocalName().equals("annotation")) {
                restriction.setAnnotation(this.handleAnnotation(el));
            } else {
                XmlSchemaFacet facet = XmlSchemaFacet.construct(el);
                Element annotation = XDOMUtil.getFirstChildElementNS(el, "http://www.w3.org/2001/XMLSchema", "annotation");
                if (annotation != null) {
                    XmlSchemaAnnotation facetAnnotation = this.handleAnnotation(annotation);
                    facet.setAnnotation(facetAnnotation);
                }
                restriction.getFacets().add(facet);
                this.processExtensibilityComponents(facet, el, true);
            }
            el = XDOMUtil.getNextSiblingElementNS((Node)el, "http://www.w3.org/2001/XMLSchema");
        }
        return restriction;
    }

    private void handleSimpleTypeFinal(Element simpleEl, XmlSchemaSimpleType simpleType) {
        if (simpleEl.hasAttribute("final")) {
            String finalstr = simpleEl.getAttribute("final");
            simpleType.setFinal(XmlSchemaDerivationMethod.schemaValueOf(finalstr));
        }
    }

    private void handleSimpleTypeList(XmlSchema schema, Element schemaEl, XmlSchemaSimpleType simpleType, Element listEl) {
        XmlSchemaSimpleTypeList list = new XmlSchemaSimpleTypeList();
        Element inlineListType = XDOMUtil.getFirstChildElementNS(listEl, "http://www.w3.org/2001/XMLSchema", "simpleType");
        if (listEl.hasAttribute("itemType")) {
            String name = listEl.getAttribute("itemType");
            list.itemTypeName = this.getRefQName(name, listEl);
        } else if (inlineListType != null) {
            list.itemType = this.handleSimpleType(schema, inlineListType, schemaEl, false);
        }
        Element listAnnotationEl = XDOMUtil.getFirstChildElementNS(listEl, "http://www.w3.org/2001/XMLSchema", "annotation");
        if (listAnnotationEl != null) {
            XmlSchemaAnnotation listAnnotation = this.handleAnnotation(listAnnotationEl);
            list.setAnnotation(listAnnotation);
        }
        simpleType.content = list;
    }

    private void handleSimpleTypeRestriction(XmlSchema schema, Element schemaEl, XmlSchemaSimpleType simpleType, Element restrictionEl) {
        XmlSchemaSimpleTypeRestriction restriction = new XmlSchemaSimpleTypeRestriction();
        Element restAnnotationEl = XDOMUtil.getFirstChildElementNS(restrictionEl, "http://www.w3.org/2001/XMLSchema", "annotation");
        if (restAnnotationEl != null) {
            XmlSchemaAnnotation restAnnotation = this.handleAnnotation(restAnnotationEl);
            restriction.setAnnotation(restAnnotation);
        }
        Element inlineSimpleType = XDOMUtil.getFirstChildElementNS(restrictionEl, "http://www.w3.org/2001/XMLSchema", "simpleType");
        if (restrictionEl.hasAttribute("base")) {
            NodeNamespaceContext ctx = NodeNamespaceContext.getNamespaceContext(restrictionEl);
            restriction.setBaseTypeName(this.getRefQName(restrictionEl.getAttribute("base"), ctx));
        } else if (inlineSimpleType != null) {
            restriction.setBaseType(this.handleSimpleType(schema, inlineSimpleType, schemaEl, false));
        }
        Element el = XDOMUtil.getFirstChildElementNS((Node)restrictionEl, "http://www.w3.org/2001/XMLSchema");
        while (el != null) {
            if (!el.getLocalName().equals("annotation") && !el.getLocalName().equals("simpleType")) {
                XmlSchemaFacet facet = XmlSchemaFacet.construct(el);
                Element annotation = XDOMUtil.getFirstChildElementNS(el, "http://www.w3.org/2001/XMLSchema", "annotation");
                if (annotation != null) {
                    XmlSchemaAnnotation facetAnnotation = this.handleAnnotation(annotation);
                    facet.setAnnotation(facetAnnotation);
                }
                this.processExtensibilityComponents(facet, el, true);
                restriction.getFacets().add(facet);
            }
            el = XDOMUtil.getNextSiblingElementNS((Node)el, "http://www.w3.org/2001/XMLSchema");
        }
        simpleType.content = restriction;
    }

    private void handleSimpleTypeUnion(XmlSchema schema, Element schemaEl, XmlSchemaSimpleType simpleType, Element unionEl) {
        XmlSchemaSimpleTypeUnion union = new XmlSchemaSimpleTypeUnion();
        if (unionEl.hasAttribute("memberTypes")) {
            String memberTypes = unionEl.getAttribute("memberTypes");
            union.setMemberTypesSource(memberTypes);
            ArrayList<QName> v = new ArrayList<QName>();
            StringTokenizer tokenizer = new StringTokenizer(memberTypes, " ");
            while (tokenizer.hasMoreTokens()) {
                String member = tokenizer.nextToken();
                v.add(this.getRefQName(member, unionEl));
            }
            union.setMemberTypesQNames(v.toArray(new QName[v.size()]));
        }
        Element inlineUnionType = XDOMUtil.getFirstChildElementNS(unionEl, "http://www.w3.org/2001/XMLSchema", "simpleType");
        while (inlineUnionType != null) {
            XmlSchemaSimpleType unionSimpleType = this.handleSimpleType(schema, inlineUnionType, schemaEl, false);
            union.getBaseTypes().add(unionSimpleType);
            if (!unionSimpleType.isAnonymous()) {
                union.setMemberTypesSource(union.getMemberTypesSource() + " " + unionSimpleType.getName());
            }
            inlineUnionType = XDOMUtil.getNextSiblingElementNS(inlineUnionType, "http://www.w3.org/2001/XMLSchema", "simpleType");
        }
        Element unionAnnotationEl = XDOMUtil.getFirstChildElementNS(unionEl, "http://www.w3.org/2001/XMLSchema", "annotation");
        if (unionAnnotationEl != null) {
            XmlSchemaAnnotation unionAnnotation = this.handleAnnotation(unionAnnotationEl);
            union.setAnnotation(unionAnnotation);
        }
        simpleType.content = union;
    }

    private TargetNamespaceValidator newIncludeValidator(final XmlSchema schema) {
        return new TargetNamespaceValidator(){

            @Override
            public void validate(XmlSchema pSchema) {
                if (this.isEmpty(pSchema.getSyntacticalTargetNamespace())) {
                    pSchema.setLogicalTargetNamespace(schema.getLogicalTargetNamespace());
                } else if (!pSchema.getSyntacticalTargetNamespace().equals(schema.getLogicalTargetNamespace())) {
                    String msg = "An included schema was announced to have the default target namespace";
                    if (!this.isEmpty(schema.getLogicalTargetNamespace())) {
                        msg = msg + " or the target namespace " + schema.getLogicalTargetNamespace();
                    }
                    throw new XmlSchemaException(msg + ", but has the target namespace " + pSchema.getLogicalTargetNamespace());
                }
            }

            private boolean isEmpty(String pValue) {
                return pValue == null || "".equals(pValue);
            }
        };
    }

    private void processExtensibilityComponents(XmlSchemaObject schemaObject, Element parentElement, boolean namespaces) {
        if (this.extReg != null) {
            QName qName;
            String name;
            String namespaceURI;
            NamedNodeMap attributes = parentElement.getAttributes();
            for (int i = 0; i < attributes.getLength(); ++i) {
                Attr attribute = (Attr)attributes.item(i);
                namespaceURI = attribute.getNamespaceURI();
                name = attribute.getLocalName();
                if (namespaceURI == null || "".equals(namespaceURI) || !namespaces && namespaceURI.startsWith("http://www.w3.org/2000/xmlns/") || "http://www.w3.org/2001/XMLSchema".equals(namespaceURI)) continue;
                qName = new QName(namespaceURI, name);
                this.extReg.deserializeExtension(schemaObject, qName, attribute);
            }
            for (Node child = parentElement.getFirstChild(); child != null; child = child.getNextSibling()) {
                if (child.getNodeType() != 1) continue;
                Element extElement = (Element)child;
                namespaceURI = extElement.getNamespaceURI();
                name = extElement.getLocalName();
                if (namespaceURI == null || "http://www.w3.org/2001/XMLSchema".equals(namespaceURI)) continue;
                qName = new QName(namespaceURI, name);
                this.extReg.deserializeExtension(schemaObject, qName, extElement);
            }
        }
    }

    private void putCachedSchema(String targetNamespace, String schemaLocation, String baseUri, XmlSchema readSchema) {
        Map<String, SoftReference<XmlSchema>> threadResolvedSchemas;
        if (resolvedSchemas != null && (threadResolvedSchemas = resolvedSchemas.get()) != null) {
            String schemaKey = targetNamespace + schemaLocation + baseUri;
            threadResolvedSchemas.put(schemaKey, new SoftReference<XmlSchema>(readSchema));
        }
    }

    static {
        for (String s2 : RESERVED_ATTRIBUTES_LIST = new String[]{"name", "type", "default", "fixed", "form", "id", "use", "ref"}) {
            RESERVED_ATTRIBUTES.add(s2);
        }
    }
}

