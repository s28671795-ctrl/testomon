/*
 * Decompiled with CFR 0.152.
 */
package org.apache.ws.commons.schema;

import java.io.IOException;
import java.io.Reader;
import java.math.BigInteger;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Source;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.sax.SAXSource;
import javax.xml.transform.stream.StreamSource;
import org.apache.ws.commons.schema.SchemaBuilder;
import org.apache.ws.commons.schema.TypeReceiver;
import org.apache.ws.commons.schema.XmlSchema;
import org.apache.ws.commons.schema.XmlSchemaAttribute;
import org.apache.ws.commons.schema.XmlSchemaAttributeGroup;
import org.apache.ws.commons.schema.XmlSchemaElement;
import org.apache.ws.commons.schema.XmlSchemaException;
import org.apache.ws.commons.schema.XmlSchemaFacet;
import org.apache.ws.commons.schema.XmlSchemaFractionDigitsFacet;
import org.apache.ws.commons.schema.XmlSchemaGroup;
import org.apache.ws.commons.schema.XmlSchemaMaxInclusiveFacet;
import org.apache.ws.commons.schema.XmlSchemaMinInclusiveFacet;
import org.apache.ws.commons.schema.XmlSchemaNotation;
import org.apache.ws.commons.schema.XmlSchemaPatternFacet;
import org.apache.ws.commons.schema.XmlSchemaSimpleType;
import org.apache.ws.commons.schema.XmlSchemaSimpleTypeList;
import org.apache.ws.commons.schema.XmlSchemaSimpleTypeRestriction;
import org.apache.ws.commons.schema.XmlSchemaType;
import org.apache.ws.commons.schema.XmlSchemaWhiteSpaceFacet;
import org.apache.ws.commons.schema.constants.Constants;
import org.apache.ws.commons.schema.extensions.ExtensionRegistry;
import org.apache.ws.commons.schema.resolver.CollectionURIResolver;
import org.apache.ws.commons.schema.resolver.DefaultURIResolver;
import org.apache.ws.commons.schema.resolver.URIResolver;
import org.apache.ws.commons.schema.utils.NamespacePrefixList;
import org.apache.ws.commons.schema.utils.TargetNamespaceValidator;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public final class XmlSchemaCollection {
    String baseUri;
    Deque<SchemaKey> stack;
    Map<QName, List<TypeReceiver>> unresolvedTypes;
    XmlSchema xsd;
    private ExtensionRegistry extReg;
    private Map<String, XmlSchema> knownNamespaceMap;
    private NamespacePrefixList namespaceContext;
    private URIResolver schemaResolver;
    private Map<SchemaKey, XmlSchema> schemas;

    public XmlSchemaCollection() {
        this.init();
    }

    public boolean check(SchemaKey pKey) {
        return !this.stack.contains(pKey);
    }

    public ExtensionRegistry getExtReg() {
        return this.extReg;
    }

    public Map<String, XmlSchema> getKnownNamespaceMap() {
        return this.knownNamespaceMap;
    }

    public NamespacePrefixList getNamespaceContext() {
        return this.namespaceContext;
    }

    public URIResolver getSchemaResolver() {
        return this.schemaResolver;
    }

    public XmlSchemaType getTypeByQName(QName schemaTypeName) {
        String uri = schemaTypeName.getNamespaceURI();
        for (Map.Entry<SchemaKey, XmlSchema> entry : this.schemas.entrySet()) {
            XmlSchemaType type;
            if (!entry.getKey().getNamespace().equals(uri) || (type = entry.getValue().getTypeByName(schemaTypeName)) == null) continue;
            return type;
        }
        return null;
    }

    public XmlSchema[] getXmlSchema(String systemId) {
        if (systemId == null) {
            systemId = "";
        }
        ArrayList<XmlSchema> result = new ArrayList<XmlSchema>();
        for (Map.Entry<SchemaKey, XmlSchema> entry : this.schemas.entrySet()) {
            if (!entry.getKey().getSystemId().equals(systemId)) continue;
            result.add(entry.getValue());
        }
        return result.toArray(new XmlSchema[result.size()]);
    }

    public XmlSchema[] getXmlSchemas() {
        Collection<XmlSchema> c = this.schemas.values();
        return c.toArray(new XmlSchema[c.size()]);
    }

    public void init() {
        this.stack = new ArrayDeque<SchemaKey>();
        this.unresolvedTypes = new HashMap<QName, List<TypeReceiver>>();
        this.extReg = new ExtensionRegistry();
        this.knownNamespaceMap = new HashMap<String, XmlSchema>();
        this.schemaResolver = new DefaultURIResolver();
        this.schemas = new HashMap<SchemaKey, XmlSchema>();
        this.xsd = new XmlSchema("http://www.w3.org/2001/XMLSchema", this);
        this.addSimpleType(this.xsd, Constants.XSD_ANYSIMPLETYPE.getLocalPart());
        this.addSimpleType(this.xsd, Constants.XSD_ANYTYPE.getLocalPart());
        this.addSimpleType(this.xsd, Constants.XSD_STRING.getLocalPart());
        this.addSimpleType(this.xsd, Constants.XSD_BOOLEAN.getLocalPart());
        this.addSimpleType(this.xsd, Constants.XSD_FLOAT.getLocalPart());
        this.addSimpleType(this.xsd, Constants.XSD_DOUBLE.getLocalPart());
        this.addSimpleType(this.xsd, Constants.XSD_QNAME.getLocalPart());
        this.addSimpleType(this.xsd, Constants.XSD_DECIMAL.getLocalPart());
        this.addSimpleType(this.xsd, Constants.XSD_DURATION.getLocalPart());
        this.addSimpleType(this.xsd, Constants.XSD_DATE.getLocalPart());
        this.addSimpleType(this.xsd, Constants.XSD_TIME.getLocalPart());
        this.addSimpleType(this.xsd, Constants.XSD_DATETIME.getLocalPart());
        this.addSimpleType(this.xsd, Constants.XSD_DAY.getLocalPart());
        this.addSimpleType(this.xsd, Constants.XSD_MONTH.getLocalPart());
        this.addSimpleType(this.xsd, Constants.XSD_MONTHDAY.getLocalPart());
        this.addSimpleType(this.xsd, Constants.XSD_YEAR.getLocalPart());
        this.addSimpleType(this.xsd, Constants.XSD_YEARMONTH.getLocalPart());
        this.addSimpleType(this.xsd, Constants.XSD_NOTATION.getLocalPart());
        this.addSimpleType(this.xsd, Constants.XSD_HEXBIN.getLocalPart());
        this.addSimpleType(this.xsd, Constants.XSD_BASE64.getLocalPart());
        this.addSimpleType(this.xsd, Constants.XSD_ANYURI.getLocalPart());
        this.addSimpleType(this.xsd, Constants.XSD_LONG.getLocalPart());
        this.addSimpleType(this.xsd, Constants.XSD_SHORT.getLocalPart());
        this.addSimpleType(this.xsd, Constants.XSD_BYTE.getLocalPart());
        this.addSimpleType(this.xsd, Constants.XSD_INTEGER.getLocalPart());
        this.addSimpleType(this.xsd, Constants.XSD_INT.getLocalPart());
        this.addSimpleType(this.xsd, Constants.XSD_POSITIVEINTEGER.getLocalPart());
        this.addSimpleType(this.xsd, Constants.XSD_NEGATIVEINTEGER.getLocalPart());
        this.addSimpleType(this.xsd, Constants.XSD_NONPOSITIVEINTEGER.getLocalPart());
        this.addSimpleType(this.xsd, Constants.XSD_NONNEGATIVEINTEGER.getLocalPart());
        this.addSimpleType(this.xsd, Constants.XSD_UNSIGNEDBYTE.getLocalPart());
        this.addSimpleType(this.xsd, Constants.XSD_UNSIGNEDINT.getLocalPart());
        this.addSimpleType(this.xsd, Constants.XSD_UNSIGNEDLONG.getLocalPart());
        this.addSimpleType(this.xsd, Constants.XSD_UNSIGNEDSHORT.getLocalPart());
        this.addSimpleType(this.xsd, Constants.XSD_NAME.getLocalPart());
        this.addSimpleType(this.xsd, Constants.XSD_NORMALIZEDSTRING.getLocalPart());
        this.addSimpleType(this.xsd, Constants.XSD_NCNAME.getLocalPart());
        this.addSimpleType(this.xsd, Constants.XSD_NMTOKEN.getLocalPart());
        this.addSimpleType(this.xsd, Constants.XSD_NMTOKENS.getLocalPart());
        this.addSimpleType(this.xsd, Constants.XSD_ENTITY.getLocalPart());
        this.addSimpleType(this.xsd, Constants.XSD_ENTITIES.getLocalPart());
        this.addSimpleType(this.xsd, Constants.XSD_ID.getLocalPart());
        this.addSimpleType(this.xsd, Constants.XSD_IDREF.getLocalPart());
        this.addSimpleType(this.xsd, Constants.XSD_IDREFS.getLocalPart());
        this.addSimpleType(this.xsd, Constants.XSD_LANGUAGE.getLocalPart());
        this.addSimpleType(this.xsd, Constants.XSD_TOKEN.getLocalPart());
        this.setupBuiltinDatatypeHierarchy(this.xsd);
        String extRegProp = this.getSystemProperty("org.apache.ws.commons.extensions.ExtensionRegistry");
        if (extRegProp != null) {
            try {
                Class<?> clazz = Class.forName(extRegProp);
                this.extReg = (ExtensionRegistry)clazz.newInstance();
            }
            catch (ClassNotFoundException e) {
                System.err.println("The specified extension registry class cannot be found!");
            }
            catch (InstantiationException e) {
                System.err.println("The specified extension registry class cannot be instantiated!");
            }
            catch (IllegalAccessException e) {
                System.err.println("The specified extension registry class cannot be accessed!");
            }
        }
    }

    private String getSystemProperty(final String s2) {
        try {
            return AccessController.doPrivileged(new PrivilegedAction<String>(){

                @Override
                public String run() {
                    return System.getProperty(s2);
                }
            });
        }
        catch (SecurityException ex) {
            return null;
        }
    }

    private void setupBuiltinDatatypeHierarchy(XmlSchema xsd) {
        this.setDerivationByRestriction(xsd, Constants.XSD_ANYSIMPLETYPE, Constants.XSD_ANYTYPE);
        this.setDerivationByRestriction(xsd, Constants.XSD_DURATION, Constants.XSD_ANYSIMPLETYPE, new XmlSchemaFacet[]{new XmlSchemaWhiteSpaceFacet("collapse", true)});
        this.setDerivationByRestriction(xsd, Constants.XSD_DATETIME, Constants.XSD_ANYSIMPLETYPE, new XmlSchemaFacet[]{new XmlSchemaWhiteSpaceFacet("collapse", true)});
        this.setDerivationByRestriction(xsd, Constants.XSD_TIME, Constants.XSD_ANYSIMPLETYPE, new XmlSchemaFacet[]{new XmlSchemaWhiteSpaceFacet("collapse", true)});
        this.setDerivationByRestriction(xsd, Constants.XSD_DATE, Constants.XSD_ANYSIMPLETYPE, new XmlSchemaFacet[]{new XmlSchemaWhiteSpaceFacet("collapse", true)});
        this.setDerivationByRestriction(xsd, Constants.XSD_YEARMONTH, Constants.XSD_ANYSIMPLETYPE, new XmlSchemaFacet[]{new XmlSchemaWhiteSpaceFacet("collapse", true)});
        this.setDerivationByRestriction(xsd, Constants.XSD_YEAR, Constants.XSD_ANYSIMPLETYPE, new XmlSchemaFacet[]{new XmlSchemaWhiteSpaceFacet("collapse", true)});
        this.setDerivationByRestriction(xsd, Constants.XSD_MONTHDAY, Constants.XSD_ANYSIMPLETYPE, new XmlSchemaFacet[]{new XmlSchemaWhiteSpaceFacet("collapse", true)});
        this.setDerivationByRestriction(xsd, Constants.XSD_DAY, Constants.XSD_ANYSIMPLETYPE, new XmlSchemaFacet[]{new XmlSchemaWhiteSpaceFacet("collapse", true)});
        this.setDerivationByRestriction(xsd, Constants.XSD_MONTH, Constants.XSD_ANYSIMPLETYPE, new XmlSchemaFacet[]{new XmlSchemaWhiteSpaceFacet("collapse", true)});
        this.setDerivationByRestriction(xsd, Constants.XSD_BOOLEAN, Constants.XSD_ANYSIMPLETYPE, new XmlSchemaFacet[]{new XmlSchemaWhiteSpaceFacet("collapse", true)});
        this.setDerivationByRestriction(xsd, Constants.XSD_BASE64, Constants.XSD_ANYSIMPLETYPE, new XmlSchemaFacet[]{new XmlSchemaWhiteSpaceFacet("collapse", true)});
        this.setDerivationByRestriction(xsd, Constants.XSD_HEXBIN, Constants.XSD_ANYSIMPLETYPE, new XmlSchemaFacet[]{new XmlSchemaWhiteSpaceFacet("collapse", true)});
        this.setDerivationByRestriction(xsd, Constants.XSD_FLOAT, Constants.XSD_ANYSIMPLETYPE, new XmlSchemaFacet[]{new XmlSchemaWhiteSpaceFacet("collapse", true)});
        this.setDerivationByRestriction(xsd, Constants.XSD_DOUBLE, Constants.XSD_ANYSIMPLETYPE, new XmlSchemaFacet[]{new XmlSchemaWhiteSpaceFacet("collapse", true)});
        this.setDerivationByRestriction(xsd, Constants.XSD_ANYURI, Constants.XSD_ANYSIMPLETYPE, new XmlSchemaFacet[]{new XmlSchemaWhiteSpaceFacet("collapse", true)});
        this.setDerivationByRestriction(xsd, Constants.XSD_QNAME, Constants.XSD_ANYSIMPLETYPE, new XmlSchemaFacet[]{new XmlSchemaWhiteSpaceFacet("collapse", true)});
        this.setDerivationByRestriction(xsd, Constants.XSD_NOTATION, Constants.XSD_ANYSIMPLETYPE, new XmlSchemaFacet[]{new XmlSchemaWhiteSpaceFacet("collapse", true)});
        this.setDerivationByRestriction(xsd, Constants.XSD_DECIMAL, Constants.XSD_ANYSIMPLETYPE, new XmlSchemaFacet[]{new XmlSchemaWhiteSpaceFacet("collapse", true)});
        this.setDerivationByRestriction(xsd, Constants.XSD_INTEGER, Constants.XSD_DECIMAL, new XmlSchemaFacet[]{new XmlSchemaFractionDigitsFacet(0, true), new XmlSchemaPatternFacet("[\\-+]?[0-9]+", false)});
        this.setDerivationByRestriction(xsd, Constants.XSD_NONPOSITIVEINTEGER, Constants.XSD_INTEGER, new XmlSchemaFacet[]{new XmlSchemaMaxInclusiveFacet(0, false)});
        this.setDerivationByRestriction(xsd, Constants.XSD_NEGATIVEINTEGER, Constants.XSD_NONPOSITIVEINTEGER, new XmlSchemaFacet[]{new XmlSchemaMaxInclusiveFacet(-1, false)});
        this.setDerivationByRestriction(xsd, Constants.XSD_LONG, Constants.XSD_INTEGER, new XmlSchemaFacet[]{new XmlSchemaMinInclusiveFacet(Long.MIN_VALUE, false), new XmlSchemaMaxInclusiveFacet(Long.MAX_VALUE, false)});
        this.setDerivationByRestriction(xsd, Constants.XSD_INT, Constants.XSD_LONG, new XmlSchemaFacet[]{new XmlSchemaMinInclusiveFacet(Integer.MIN_VALUE, false), new XmlSchemaMaxInclusiveFacet(Integer.MAX_VALUE, false)});
        this.setDerivationByRestriction(xsd, Constants.XSD_SHORT, Constants.XSD_INT, new XmlSchemaFacet[]{new XmlSchemaMinInclusiveFacet((short)Short.MIN_VALUE, false), new XmlSchemaMaxInclusiveFacet((short)Short.MAX_VALUE, false)});
        this.setDerivationByRestriction(xsd, Constants.XSD_BYTE, Constants.XSD_SHORT, new XmlSchemaFacet[]{new XmlSchemaMinInclusiveFacet((byte)-128, false), new XmlSchemaMaxInclusiveFacet((byte)127, false)});
        this.setDerivationByRestriction(xsd, Constants.XSD_NONNEGATIVEINTEGER, Constants.XSD_INTEGER, new XmlSchemaFacet[]{new XmlSchemaMinInclusiveFacet(0, false)});
        this.setDerivationByRestriction(xsd, Constants.XSD_POSITIVEINTEGER, Constants.XSD_NONNEGATIVEINTEGER, new XmlSchemaFacet[]{new XmlSchemaMinInclusiveFacet(1, false)});
        this.setDerivationByRestriction(xsd, Constants.XSD_UNSIGNEDLONG, Constants.XSD_NONNEGATIVEINTEGER, new XmlSchemaFacet[]{new XmlSchemaMaxInclusiveFacet(new BigInteger("18446744073709551615"), false)});
        this.setDerivationByRestriction(xsd, Constants.XSD_UNSIGNEDINT, Constants.XSD_UNSIGNEDLONG, new XmlSchemaFacet[]{new XmlSchemaMaxInclusiveFacet(0xFFFFFFFFL, false)});
        this.setDerivationByRestriction(xsd, Constants.XSD_UNSIGNEDSHORT, Constants.XSD_UNSIGNEDINT, new XmlSchemaFacet[]{new XmlSchemaMaxInclusiveFacet(65535, false)});
        this.setDerivationByRestriction(xsd, Constants.XSD_UNSIGNEDBYTE, Constants.XSD_UNSIGNEDSHORT, new XmlSchemaFacet[]{new XmlSchemaMaxInclusiveFacet((short)255, false)});
        this.setDerivationByRestriction(xsd, Constants.XSD_STRING, Constants.XSD_ANYSIMPLETYPE, new XmlSchemaFacet[]{new XmlSchemaWhiteSpaceFacet("preserve", false)});
        this.setDerivationByRestriction(xsd, Constants.XSD_NORMALIZEDSTRING, Constants.XSD_STRING, new XmlSchemaFacet[]{new XmlSchemaWhiteSpaceFacet("replace", false)});
        this.setDerivationByRestriction(xsd, Constants.XSD_TOKEN, Constants.XSD_NORMALIZEDSTRING, new XmlSchemaFacet[]{new XmlSchemaWhiteSpaceFacet("collapse", false)});
        this.setDerivationByRestriction(xsd, Constants.XSD_LANGUAGE, Constants.XSD_TOKEN, new XmlSchemaFacet[]{new XmlSchemaPatternFacet("[a-zA-Z]{1,8}(-[a-zA-Z0-9]{1,8})*", false)});
        this.setDerivationByRestriction(xsd, Constants.XSD_NMTOKEN, Constants.XSD_TOKEN, new XmlSchemaFacet[]{new XmlSchemaPatternFacet("\\c+", false)});
        this.setDerivationByRestriction(xsd, Constants.XSD_NAME, Constants.XSD_NMTOKEN, new XmlSchemaFacet[]{new XmlSchemaPatternFacet("\\i\\c*", false)});
        this.setDerivationByRestriction(xsd, Constants.XSD_NCNAME, Constants.XSD_TOKEN, new XmlSchemaFacet[]{new XmlSchemaPatternFacet("[\\i-[:]][\\c-[:]]*", false)});
        this.setDerivationByRestriction(xsd, Constants.XSD_ID, Constants.XSD_NCNAME);
        this.setDerivationByRestriction(xsd, Constants.XSD_IDREF, Constants.XSD_NCNAME);
        this.setDerivationByRestriction(xsd, Constants.XSD_ENTITY, Constants.XSD_NCNAME);
        this.setDerivationByList(xsd, Constants.XSD_NMTOKENS, Constants.XSD_NMTOKEN);
        this.setDerivationByList(xsd, Constants.XSD_IDREFS, Constants.XSD_IDREF);
        this.setDerivationByList(xsd, Constants.XSD_ENTITIES, Constants.XSD_ENTITY);
    }

    private void setDerivationByRestriction(XmlSchema xsd, QName child, QName parent) {
        this.setDerivationByRestriction(xsd, child, parent, null);
    }

    private void setDerivationByRestriction(XmlSchema xsd, QName child, QName parent, XmlSchemaFacet[] facets) {
        XmlSchemaSimpleType simple = (XmlSchemaSimpleType)xsd.getTypeByName(child);
        XmlSchemaSimpleTypeRestriction restriction = new XmlSchemaSimpleTypeRestriction();
        restriction.setBaseTypeName(parent);
        restriction.setBaseType((XmlSchemaSimpleType)xsd.getTypeByName(parent));
        if (facets != null) {
            for (XmlSchemaFacet facet : facets) {
                restriction.getFacets().add(facet);
            }
        }
        simple.setContent(restriction);
    }

    private void setDerivationByList(XmlSchema xsd, QName child, QName parent) {
        XmlSchemaSimpleType simple = (XmlSchemaSimpleType)xsd.getTypeByName(child);
        XmlSchemaSimpleTypeList restriction = new XmlSchemaSimpleTypeList();
        restriction.setItemTypeName(parent);
        restriction.setItemType((XmlSchemaSimpleType)xsd.getTypeByName(parent));
        simple.setContent(restriction);
    }

    public void pop() {
        this.stack.pop();
    }

    public void push(SchemaKey pKey) {
        this.stack.push(pKey);
    }

    public XmlSchema read(Document doc, String systemId) {
        return this.read(doc, systemId, null);
    }

    public XmlSchema read(Document doc, String systemId, TargetNamespaceValidator validator) {
        SchemaBuilder builder = new SchemaBuilder(this, validator);
        XmlSchema schema = builder.build(doc, systemId);
        schema.setInputEncoding(doc.getInputEncoding());
        return schema;
    }

    public XmlSchema read(Document doc) {
        SchemaBuilder builder = new SchemaBuilder(this, null);
        return builder.build(doc, null);
    }

    public XmlSchema read(Element elem) {
        SchemaBuilder builder = new SchemaBuilder(this, null);
        XmlSchema xmlSchema = builder.handleXmlSchemaElement(elem, null);
        xmlSchema.setInputEncoding(elem.getOwnerDocument().getXmlEncoding());
        return xmlSchema;
    }

    public XmlSchema read(Element elem, String systemId) {
        SchemaBuilder builder = new SchemaBuilder(this, null);
        XmlSchema xmlSchema = builder.handleXmlSchemaElement(elem, systemId);
        xmlSchema.setInputEncoding(elem.getOwnerDocument().getInputEncoding());
        return xmlSchema;
    }

    public XmlSchema read(InputSource inputSource) {
        return this.read(inputSource, null);
    }

    public XmlSchema read(Reader r) {
        return this.read(new InputSource(r));
    }

    public XmlSchema read(Source source2) {
        if (source2 instanceof SAXSource) {
            return this.read(((SAXSource)source2).getInputSource());
        }
        if (source2 instanceof DOMSource) {
            Node node = ((DOMSource)source2).getNode();
            if (node instanceof Document) {
                node = ((Document)node).getDocumentElement();
            }
            return this.read((Document)node);
        }
        if (source2 instanceof StreamSource) {
            StreamSource ss = (StreamSource)source2;
            InputSource isource = new InputSource(ss.getSystemId());
            isource.setByteStream(ss.getInputStream());
            isource.setCharacterStream(ss.getReader());
            isource.setPublicId(ss.getPublicId());
            return this.read(isource);
        }
        InputSource isource = new InputSource(source2.getSystemId());
        return this.read(isource);
    }

    public XmlSchema schemaForNamespace(String uri) {
        for (Map.Entry<SchemaKey, XmlSchema> entry : this.schemas.entrySet()) {
            if (!entry.getKey().getNamespace().equals(uri)) continue;
            return entry.getValue();
        }
        return null;
    }

    public void setBaseUri(String baseUri) {
        this.baseUri = baseUri;
        if (this.schemaResolver instanceof CollectionURIResolver) {
            CollectionURIResolver resolverWithBase = (CollectionURIResolver)this.schemaResolver;
            resolverWithBase.setCollectionBaseURI(baseUri);
        }
    }

    public void setExtReg(ExtensionRegistry extReg) {
        this.extReg = extReg;
    }

    public void setKnownNamespaceMap(Map<String, XmlSchema> knownNamespaceMap) {
        this.knownNamespaceMap = knownNamespaceMap;
    }

    public void setNamespaceContext(NamespacePrefixList namespaceContext) {
        this.namespaceContext = namespaceContext;
    }

    public void setSchemaResolver(URIResolver schemaResolver) {
        this.schemaResolver = schemaResolver;
    }

    public String toString() {
        return super.toString() + "[" + this.schemas.toString() + "]";
    }

    void addSchema(SchemaKey pKey, XmlSchema pSchema) {
        if (this.schemas.containsKey(pKey)) {
            throw new IllegalStateException("A schema with target namespace " + pKey.getNamespace() + " and system ID " + pKey.getSystemId() + " is already present.");
        }
        this.schemas.put(pKey, pSchema);
    }

    void addUnresolvedType(QName type, TypeReceiver receiver) {
        List<TypeReceiver> receivers = this.unresolvedTypes.get(type);
        if (receivers == null) {
            receivers = new ArrayList<TypeReceiver>();
            this.unresolvedTypes.put(type, receivers);
        }
        receivers.add(receiver);
    }

    boolean containsSchema(SchemaKey pKey) {
        return this.schemas.containsKey(pKey);
    }

    XmlSchema getKnownSchema(String namespace) {
        return this.knownNamespaceMap.get(namespace);
    }

    XmlSchema getSchema(SchemaKey pKey) {
        return this.schemas.get(pKey);
    }

    XmlSchema read(InputSource inputSource, TargetNamespaceValidator namespaceValidator) {
        try {
            DocumentBuilderFactory docFac = DocumentBuilderFactory.newInstance();
            docFac.setFeature("http://javax.xml.XMLConstants/feature/secure-processing", Boolean.TRUE);
            docFac.setNamespaceAware(true);
            DocumentBuilder builder = docFac.newDocumentBuilder();
            Document doc = null;
            doc = this.parseDoPriv(inputSource, builder, doc);
            return this.read(doc, inputSource.getSystemId(), namespaceValidator);
        }
        catch (ParserConfigurationException e) {
            throw new XmlSchemaException(e.getMessage(), e);
        }
        catch (IOException e) {
            throw new XmlSchemaException(e.getMessage(), e);
        }
        catch (SAXException e) {
            throw new XmlSchemaException(e.getMessage(), e);
        }
    }

    void resolveType(QName typeName, XmlSchemaType type) {
        List<TypeReceiver> receivers = this.unresolvedTypes.get(typeName);
        if (receivers == null) {
            return;
        }
        for (TypeReceiver receiver : receivers) {
            receiver.setType(type);
        }
        this.unresolvedTypes.remove(typeName);
    }

    private void addSimpleType(XmlSchema schema, String typeName) {
        XmlSchemaSimpleType type = new XmlSchemaSimpleType(schema, true);
        type.setName(typeName);
    }

    private Document parseDoPriv(final InputSource inputSource, final DocumentBuilder builder, Document doc) throws IOException, SAXException {
        block3: {
            try {
                doc = AccessController.doPrivileged(new PrivilegedExceptionAction<Document>(){

                    @Override
                    public Document run() throws IOException, SAXException {
                        return builder.parse(inputSource);
                    }
                });
            }
            catch (PrivilegedActionException e) {
                Exception exception = e.getException();
                if (exception instanceof IOException) {
                    throw (IOException)exception;
                }
                if (!(exception instanceof SAXException)) break block3;
                throw (SAXException)exception;
            }
        }
        return doc;
    }

    public XmlSchemaAttribute getAttributeByQName(QName schemaAttributeName) {
        if (schemaAttributeName == null) {
            return null;
        }
        String uri = schemaAttributeName.getNamespaceURI();
        for (Map.Entry<SchemaKey, XmlSchema> entry : this.schemas.entrySet()) {
            XmlSchemaAttribute attribute;
            if (!entry.getKey().getNamespace().equals(uri) || (attribute = entry.getValue().getAttributeByName(schemaAttributeName)) == null) continue;
            return attribute;
        }
        return null;
    }

    public XmlSchemaElement getElementByQName(QName qname) {
        if (qname == null) {
            return null;
        }
        String uri = qname.getNamespaceURI();
        for (Map.Entry<SchemaKey, XmlSchema> entry : this.schemas.entrySet()) {
            XmlSchemaElement element;
            if (!entry.getKey().getNamespace().equals(uri) || (element = entry.getValue().getElementByName(qname)) == null) continue;
            return element;
        }
        return null;
    }

    public XmlSchemaAttributeGroup getAttributeGroupByQName(QName name) {
        if (name == null) {
            return null;
        }
        String uri = name.getNamespaceURI();
        for (Map.Entry<SchemaKey, XmlSchema> entry : this.schemas.entrySet()) {
            XmlSchemaAttributeGroup group;
            if (!entry.getKey().getNamespace().equals(uri) || (group = entry.getValue().getAttributeGroupByName(name)) == null) continue;
            return group;
        }
        return null;
    }

    public XmlSchemaGroup getGroupByQName(QName name) {
        if (name == null) {
            return null;
        }
        String uri = name.getNamespaceURI();
        for (Map.Entry<SchemaKey, XmlSchema> entry : this.schemas.entrySet()) {
            XmlSchemaGroup group;
            if (!entry.getKey().getNamespace().equals(uri) || (group = entry.getValue().getGroupByName(name)) == null) continue;
            return group;
        }
        return null;
    }

    public XmlSchemaNotation getNotationByQName(QName name) {
        if (name == null) {
            return null;
        }
        String uri = name.getNamespaceURI();
        for (Map.Entry<SchemaKey, XmlSchema> entry : this.schemas.entrySet()) {
            XmlSchemaNotation notation;
            if (!entry.getKey().getNamespace().equals(uri) || (notation = entry.getValue().getNotationByName(name)) == null) continue;
            return notation;
        }
        return null;
    }

    public static class SchemaKey {
        private final String namespace;
        private final String systemId;

        SchemaKey(String pNamespace, String pSystemId) {
            this.namespace = pNamespace == null ? "" : pNamespace;
            this.systemId = pSystemId == null ? "" : pSystemId;
        }

        public int hashCode() {
            int prime = 31;
            int result = 1;
            result = 31 * result + (this.namespace == null ? 0 : this.namespace.hashCode());
            result = 31 * result + (this.systemId == null ? 0 : this.systemId.hashCode());
            return result;
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null) {
                return false;
            }
            if (this.getClass() != obj.getClass()) {
                return false;
            }
            SchemaKey other = (SchemaKey)obj;
            if (this.namespace == null ? other.namespace != null : !this.namespace.equals(other.namespace)) {
                return false;
            }
            return !(this.systemId == null ? other.systemId != null : !this.systemId.equals(other.systemId));
        }

        public String toString() {
            return "".equals(this.namespace) ? this.systemId : "{" + this.namespace + "}" + this.systemId;
        }

        String getNamespace() {
            return this.namespace;
        }

        String getSystemId() {
            return this.systemId;
        }
    }
}

