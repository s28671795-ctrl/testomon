/*
 * Decompiled with CFR 0.152.
 */
package org.apache.ws.commons.schema;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import javax.xml.namespace.QName;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.apache.ws.commons.schema.XmlSchemaAnnotated;
import org.apache.ws.commons.schema.XmlSchemaAttribute;
import org.apache.ws.commons.schema.XmlSchemaAttributeGroup;
import org.apache.ws.commons.schema.XmlSchemaCollection;
import org.apache.ws.commons.schema.XmlSchemaDerivationMethod;
import org.apache.ws.commons.schema.XmlSchemaElement;
import org.apache.ws.commons.schema.XmlSchemaException;
import org.apache.ws.commons.schema.XmlSchemaExternal;
import org.apache.ws.commons.schema.XmlSchemaForm;
import org.apache.ws.commons.schema.XmlSchemaGroup;
import org.apache.ws.commons.schema.XmlSchemaImport;
import org.apache.ws.commons.schema.XmlSchemaInclude;
import org.apache.ws.commons.schema.XmlSchemaNotation;
import org.apache.ws.commons.schema.XmlSchemaObject;
import org.apache.ws.commons.schema.XmlSchemaSerializer;
import org.apache.ws.commons.schema.XmlSchemaType;
import org.apache.ws.commons.schema.utils.CollectionFactory;
import org.apache.ws.commons.schema.utils.NamespaceContextOwner;
import org.apache.ws.commons.schema.utils.NamespacePrefixList;
import org.w3c.dom.Document;

public class XmlSchema
extends XmlSchemaAnnotated
implements NamespaceContextOwner {
    static final String SCHEMA_NS = "http://www.w3.org/2001/XMLSchema";
    private static final String UTF_8_ENCODING = "UTF-8";
    private List<XmlSchemaObject> items;
    private XmlSchemaCollection parent;
    private XmlSchemaDerivationMethod blockDefault;
    private XmlSchemaDerivationMethod finalDefault;
    private XmlSchemaForm elementFormDefault;
    private XmlSchemaForm attributeFormDefault;
    private List<XmlSchemaExternal> externals;
    private Map<QName, XmlSchemaAttributeGroup> attributeGroups;
    private Map<QName, XmlSchemaAttribute> attributes;
    private Map<QName, XmlSchemaElement> elements;
    private Map<QName, XmlSchemaGroup> groups;
    private Map<QName, XmlSchemaNotation> notations;
    private Map<QName, XmlSchemaType> schemaTypes;
    private String syntacticalTargetNamespace;
    private String schemaNamespacePrefix;
    private String logicalTargetNamespace;
    private String version;
    private NamespacePrefixList namespaceContext;
    private String inputEncoding;

    public XmlSchema() {
        this(null, null, null);
    }

    public XmlSchema(String namespace, String systemId, XmlSchemaCollection parent) {
        this.parent = parent;
        this.attributeFormDefault = XmlSchemaForm.UNQUALIFIED;
        this.elementFormDefault = XmlSchemaForm.UNQUALIFIED;
        this.blockDefault = XmlSchemaDerivationMethod.NONE;
        this.finalDefault = XmlSchemaDerivationMethod.NONE;
        this.items = new ArrayList<XmlSchemaObject>();
        this.externals = new ArrayList<XmlSchemaExternal>();
        this.elements = new HashMap<QName, XmlSchemaElement>();
        this.attributeGroups = new HashMap<QName, XmlSchemaAttributeGroup>();
        this.attributes = new HashMap<QName, XmlSchemaAttribute>();
        this.groups = new HashMap<QName, XmlSchemaGroup>();
        this.notations = new HashMap<QName, XmlSchemaNotation>();
        this.schemaTypes = new HashMap<QName, XmlSchemaType>();
        this.logicalTargetNamespace = namespace;
        this.syntacticalTargetNamespace = namespace;
        if (this.logicalTargetNamespace == null) {
            this.logicalTargetNamespace = "";
        }
        if (parent != null) {
            XmlSchemaCollection.SchemaKey schemaKey = new XmlSchemaCollection.SchemaKey(this.logicalTargetNamespace, systemId);
            if (parent.containsSchema(schemaKey)) {
                throw new XmlSchemaException("Schema name conflict in collection");
            }
            parent.addSchema(schemaKey, this);
        }
    }

    public XmlSchema(String namespace, XmlSchemaCollection parent) {
        this(namespace, namespace, parent);
    }

    public Document[] getAllSchemas() {
        try {
            XmlSchemaSerializer xser = new XmlSchemaSerializer();
            xser.setExtReg(this.parent.getExtReg());
            return xser.serializeSchema(this, true);
        }
        catch (XmlSchemaSerializer.XmlSchemaSerializerException e) {
            throw new XmlSchemaException("Error serializing schema", e);
        }
    }

    public XmlSchemaAttribute getAttributeByName(QName name) {
        return this.getAttributeByName(name, true, null);
    }

    public XmlSchemaAttribute getAttributeByName(String name) {
        QName nameToSearchFor = new QName(this.getTargetNamespace(), name);
        return this.getAttributeByName(nameToSearchFor, false, null);
    }

    public XmlSchemaForm getAttributeFormDefault() {
        return this.attributeFormDefault;
    }

    public XmlSchemaAttributeGroup getAttributeGroupByName(QName name) {
        return this.getAttributeGroupByName(name, true, null);
    }

    public Map<QName, XmlSchemaAttributeGroup> getAttributeGroups() {
        return CollectionFactory.getProtectedMap(this.attributeGroups);
    }

    public Map<QName, XmlSchemaAttribute> getAttributes() {
        return CollectionFactory.getProtectedMap(this.attributes);
    }

    public XmlSchemaDerivationMethod getBlockDefault() {
        return this.blockDefault;
    }

    public XmlSchemaElement getElementByName(QName name) {
        return this.getElementByName(name, true, null);
    }

    public XmlSchemaElement getElementByName(String name) {
        QName nameToSearchFor = new QName(this.getTargetNamespace(), name);
        return this.getElementByName(nameToSearchFor, false, null);
    }

    public XmlSchemaForm getElementFormDefault() {
        return this.elementFormDefault;
    }

    public Map<QName, XmlSchemaElement> getElements() {
        return CollectionFactory.getProtectedMap(this.elements);
    }

    public List<XmlSchemaExternal> getExternals() {
        return CollectionFactory.getProtectedList(this.externals);
    }

    public XmlSchemaDerivationMethod getFinalDefault() {
        return this.finalDefault;
    }

    public XmlSchemaGroup getGroupByName(QName name) {
        return this.getGroupByName(name, true, null);
    }

    public Map<QName, XmlSchemaGroup> getGroups() {
        return CollectionFactory.getProtectedMap(this.groups);
    }

    public String getInputEncoding() {
        return this.inputEncoding;
    }

    public List<XmlSchemaObject> getItems() {
        return CollectionFactory.getProtectedList(this.items);
    }

    public String getLogicalTargetNamespace() {
        return this.logicalTargetNamespace;
    }

    @Override
    public NamespacePrefixList getNamespaceContext() {
        return this.namespaceContext;
    }

    public XmlSchemaNotation getNotationByName(QName name) {
        return this.getNotationByName(name, true, null);
    }

    public Map<QName, XmlSchemaNotation> getNotations() {
        return CollectionFactory.getProtectedMap(this.notations);
    }

    public XmlSchemaCollection getParent() {
        return this.parent;
    }

    public Document getSchemaDocument() throws XmlSchemaSerializer.XmlSchemaSerializerException {
        XmlSchemaSerializer xser = new XmlSchemaSerializer();
        xser.setExtReg(this.parent.getExtReg());
        return xser.serializeSchema(this, false)[0];
    }

    public String getSchemaNamespacePrefix() {
        return this.schemaNamespacePrefix;
    }

    public Map<QName, XmlSchemaType> getSchemaTypes() {
        return this.schemaTypes;
    }

    public String getTargetNamespace() {
        return this.syntacticalTargetNamespace;
    }

    public XmlSchemaType getTypeByName(QName name) {
        return this.getTypeByName(name, true, null);
    }

    public XmlSchemaType getTypeByName(String name) {
        QName nameToSearchFor = new QName(this.getTargetNamespace(), name);
        return this.getTypeByName(nameToSearchFor, false, null);
    }

    public String getVersion() {
        return this.version;
    }

    public void setAttributeFormDefault(XmlSchemaForm value) {
        this.attributeFormDefault = value;
    }

    public void setBlockDefault(XmlSchemaDerivationMethod blockDefault) {
        this.blockDefault = blockDefault;
    }

    public void setElementFormDefault(XmlSchemaForm elementFormDefault) {
        this.elementFormDefault = elementFormDefault;
    }

    public void setFinalDefault(XmlSchemaDerivationMethod finalDefault) {
        this.finalDefault = finalDefault;
    }

    public void setInputEncoding(String encoding) {
        this.inputEncoding = encoding;
    }

    public void setNamespaceContext(NamespacePrefixList namespaceContext) {
        this.namespaceContext = namespaceContext;
    }

    public void setSchemaNamespacePrefix(String schemaNamespacePrefix) {
        this.schemaNamespacePrefix = schemaNamespacePrefix;
    }

    public void setTargetNamespace(String targetNamespace) {
        if (!"".equals(targetNamespace)) {
            this.logicalTargetNamespace = targetNamespace;
            this.syntacticalTargetNamespace = targetNamespace;
        }
    }

    @Override
    public String toString() {
        return super.toString() + "[" + this.logicalTargetNamespace + "]";
    }

    public void write(OutputStream out) throws UnsupportedEncodingException {
        if (this.inputEncoding != null && !"".equals(this.inputEncoding)) {
            this.write(new OutputStreamWriter(out, this.inputEncoding));
        } else {
            this.write(new OutputStreamWriter(out, UTF_8_ENCODING));
        }
    }

    public void write(OutputStream out, Map<String, String> options) throws UnsupportedEncodingException {
        if (this.inputEncoding != null && !"".equals(this.inputEncoding)) {
            this.write(new OutputStreamWriter(out, this.inputEncoding), options);
        } else {
            this.write(new OutputStreamWriter(out, UTF_8_ENCODING), options);
        }
    }

    public void write(Writer writer) {
        this.serializeInternal(writer, null);
    }

    public void write(Writer writer, Map<String, String> options) {
        this.serializeInternal(writer, options);
    }

    protected XmlSchemaAttribute getAttributeByName(QName name, boolean deep, Stack<XmlSchema> schemaStack) {
        if (schemaStack != null && schemaStack.contains(this)) {
            return null;
        }
        XmlSchemaAttribute attribute = this.attributes.get(name);
        if (deep) {
            if (attribute == null) {
                for (XmlSchemaExternal item : this.externals) {
                    XmlSchema schema = this.getSchema(item);
                    if (schema == null) continue;
                    if (schemaStack == null) {
                        schemaStack = new Stack();
                    }
                    schemaStack.push(this);
                    attribute = schema.getAttributeByName(name, deep, schemaStack);
                    if (attribute == null) continue;
                    return attribute;
                }
            } else {
                return attribute;
            }
        }
        return attribute;
    }

    protected XmlSchemaAttributeGroup getAttributeGroupByName(QName name, boolean deep, Stack<XmlSchema> schemaStack) {
        if (schemaStack != null && schemaStack.contains(this)) {
            return null;
        }
        XmlSchemaAttributeGroup group = this.attributeGroups.get(name);
        if (deep) {
            if (group == null) {
                for (XmlSchemaExternal item : this.externals) {
                    XmlSchema schema = this.getSchema(item);
                    if (schema == null) continue;
                    if (schemaStack == null) {
                        schemaStack = new Stack();
                    }
                    schemaStack.push(this);
                    group = schema.getAttributeGroupByName(name, deep, schemaStack);
                    if (group == null) continue;
                    return group;
                }
            } else {
                return group;
            }
        }
        return group;
    }

    protected XmlSchemaElement getElementByName(QName name, boolean deep, Stack<XmlSchema> schemaStack) {
        if (schemaStack != null && schemaStack.contains(this)) {
            return null;
        }
        XmlSchemaElement element = this.elements.get(name);
        if (deep) {
            if (element == null) {
                for (XmlSchemaExternal item : this.externals) {
                    XmlSchema schema = this.getSchema(item);
                    if (schema == null) continue;
                    if (schemaStack == null) {
                        schemaStack = new Stack();
                    }
                    schemaStack.push(this);
                    element = schema.getElementByName(name, deep, schemaStack);
                    if (element == null) continue;
                    return element;
                }
            } else {
                return element;
            }
        }
        return element;
    }

    protected XmlSchemaGroup getGroupByName(QName name, boolean deep, Stack<XmlSchema> schemaStack) {
        if (schemaStack != null && schemaStack.contains(this)) {
            return null;
        }
        XmlSchemaGroup group = this.groups.get(name);
        if (deep) {
            if (group == null) {
                for (XmlSchemaExternal item : this.externals) {
                    XmlSchema schema = this.getSchema(item);
                    if (schema == null) continue;
                    if (schemaStack == null) {
                        schemaStack = new Stack();
                    }
                    schemaStack.push(this);
                    group = schema.getGroupByName(name, deep, schemaStack);
                    if (group == null) continue;
                    return group;
                }
            } else {
                return group;
            }
        }
        return group;
    }

    protected XmlSchemaNotation getNotationByName(QName name, boolean deep, Stack<XmlSchema> schemaStack) {
        if (schemaStack != null && schemaStack.contains(this)) {
            return null;
        }
        XmlSchemaNotation notation = this.notations.get(name);
        if (deep) {
            if (notation == null) {
                for (XmlSchemaExternal item : this.externals) {
                    XmlSchema schema = this.getSchema(item);
                    if (schema == null) continue;
                    if (schemaStack == null) {
                        schemaStack = new Stack();
                    }
                    schemaStack.push(this);
                    notation = schema.getNotationByName(name, deep, schemaStack);
                    if (notation == null) continue;
                    return notation;
                }
            } else {
                return notation;
            }
        }
        return notation;
    }

    protected XmlSchemaType getTypeByName(QName name, boolean deep, Stack<XmlSchema> schemaStack) {
        if (schemaStack != null && schemaStack.contains(this)) {
            return null;
        }
        XmlSchemaType type = this.schemaTypes.get(name);
        if (deep) {
            if (type == null) {
                for (XmlSchemaExternal item : this.externals) {
                    XmlSchema schema = this.getSchema(item);
                    if (schema == null) continue;
                    if (schemaStack == null) {
                        schemaStack = new Stack();
                    }
                    schemaStack.push(this);
                    type = schema.getTypeByName(name, deep, schemaStack);
                    if (type == null) continue;
                    return type;
                }
            } else {
                return type;
            }
        }
        return type;
    }

    String getSyntacticalTargetNamespace() {
        return this.syntacticalTargetNamespace;
    }

    void setLogicalTargetNamespace(String logicalTargetNamespace) {
        this.logicalTargetNamespace = logicalTargetNamespace;
    }

    void setParent(XmlSchemaCollection parent) {
        this.parent = parent;
    }

    void setSyntacticalTargetNamespace(String syntacticalTargetNamespace) {
        this.syntacticalTargetNamespace = syntacticalTargetNamespace;
    }

    void setVersion(String version) {
        this.version = version;
    }

    private XmlSchema getSchema(Object includeOrImport) {
        XmlSchema schema = includeOrImport instanceof XmlSchemaImport ? ((XmlSchemaImport)includeOrImport).getSchema() : (includeOrImport instanceof XmlSchemaInclude ? ((XmlSchemaInclude)includeOrImport).getSchema() : null);
        return schema;
    }

    private void loadDefaultOptions(Map<String, String> options) {
        options.put("omit-xml-declaration", "yes");
        options.put("indent", "yes");
    }

    private void serializeInternal(Writer out, Map<String, String> options) {
        try {
            XmlSchemaSerializer xser = new XmlSchemaSerializer();
            xser.setExtReg(this.parent.getExtReg());
            Document[] serializedSchemas = xser.serializeSchema(this, false);
            TransformerFactory trFac = TransformerFactory.newInstance();
            trFac.setFeature("http://javax.xml.XMLConstants/feature/secure-processing", Boolean.TRUE);
            try {
                trFac.setAttribute("indent-number", "4");
            }
            catch (IllegalArgumentException illegalArgumentException) {
                // empty catch block
            }
            DOMSource source2 = new DOMSource(serializedSchemas[0]);
            StreamResult result = new StreamResult(out);
            Transformer tr = trFac.newTransformer();
            if (this.inputEncoding != null && !"".equals(this.inputEncoding)) {
                tr.setOutputProperty("encoding", this.inputEncoding);
            }
            if (options == null) {
                options = new HashMap<String, String>();
                this.loadDefaultOptions(options);
            }
            for (String key : options.keySet()) {
                tr.setOutputProperty(key, options.get(key));
            }
            tr.transform(source2, result);
            out.flush();
        }
        catch (TransformerConfigurationException e) {
            throw new XmlSchemaException(e.getMessage());
        }
        catch (TransformerException e) {
            throw new XmlSchemaException(e.getMessage());
        }
        catch (XmlSchemaSerializer.XmlSchemaSerializerException e) {
            throw new XmlSchemaException(e.getMessage());
        }
        catch (IOException e) {
            throw new XmlSchemaException(e.getMessage());
        }
    }
}

