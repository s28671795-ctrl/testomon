/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.databinding;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import javax.annotation.Resource;
import javax.xml.namespace.QName;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLStreamException;
import javax.xml.transform.dom.DOMSource;
import org.apache.cxf.Bus;
import org.apache.cxf.BusFactory;
import org.apache.cxf.common.util.StringUtils;
import org.apache.cxf.common.xmlschema.SchemaCollection;
import org.apache.cxf.databinding.DataBinding;
import org.apache.cxf.helpers.DOMUtils;
import org.apache.cxf.service.model.SchemaInfo;
import org.apache.cxf.service.model.ServiceInfo;
import org.apache.cxf.staxutils.StaxUtils;
import org.apache.ws.commons.schema.XmlSchema;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public abstract class AbstractDataBinding
implements DataBinding {
    private static final Map<String, String> BUILTIN_SCHEMA_LOCS = new HashMap<String, String>(2);
    protected boolean mtomEnabled;
    protected int mtomThreshold;
    private Bus bus;
    private Collection<DOMSource> schemas;
    private Map<String, String> namespaceMap;
    private Map<String, String> contextualNamespaceMap;
    private boolean hackAroundEmptyNamespaceIssue;

    protected Bus getBus() {
        if (this.bus == null) {
            return BusFactory.getDefaultBus();
        }
        return this.bus;
    }

    @Resource(name="cxf")
    public void setBus(Bus bus) {
        assert (this.bus == null || this.bus == bus);
        this.bus = bus;
    }

    public Collection<DOMSource> getSchemas() {
        return this.schemas;
    }

    public void setSchemas(Collection<DOMSource> schemas) {
        this.schemas = schemas;
    }

    public XmlSchema addSchemaDocument(ServiceInfo serviceInfo, SchemaCollection col, Document d, String systemId) {
        return this.addSchemaDocument(serviceInfo, col, d, systemId, null);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public XmlSchema addSchemaDocument(ServiceInfo serviceInfo, SchemaCollection col, Document d, String systemId, Collection<String> ids) {
        XmlSchema xmlSchema;
        Element e;
        Node n;
        SchemaInfo schemaInfo;
        if (!"http://www.w3.org/2001/XMLSchema".equals(d.getDocumentElement().getNamespaceURI())) {
            QName qn = DOMUtils.getElementQName(d.getDocumentElement());
            throw new RuntimeException("Invalid schema document passed to AbstractDataBinding.addSchemaDocument, not in W3C schema namespace: " + qn);
        }
        if (!"schema".equals(d.getDocumentElement().getLocalName())) {
            QName qn = DOMUtils.getElementQName(d.getDocumentElement());
            throw new RuntimeException("Invalid schema document passed to AbstractDataBinding.addSchemaDocument, document element isn't 'schema': " + qn);
        }
        String ns = d.getDocumentElement().getAttribute("targetNamespace");
        boolean copied = false;
        if (StringUtils.isEmpty(ns)) {
            if (DOMUtils.getFirstElement(d.getDocumentElement()) == null) {
                this.hackAroundEmptyNamespaceIssue = true;
                return null;
            }
            d = this.copy(d);
            copied = true;
            ns = serviceInfo.getInterface().getName().getNamespaceURI();
            d.getDocumentElement().setAttribute("targetNamespace", ns);
        }
        if ((schemaInfo = serviceInfo.getSchema(ns)) != null && (systemId == null && schemaInfo.getSystemId() == null || systemId != null && systemId.equalsIgnoreCase(schemaInfo.getSystemId()))) {
            return schemaInfo.getSchema();
        }
        if (this.hackAroundEmptyNamespaceIssue) {
            d = this.doEmptyNamespaceHack(d, copied);
        }
        boolean patchRequired = false;
        for (n = d.getDocumentElement().getFirstChild(); n != null; n = n.getNextSibling()) {
            if (!(n instanceof Element) || !"import".equals((e = (Element)n).getLocalName())) continue;
            patchRequired = true;
            break;
        }
        if (patchRequired) {
            if (!copied) {
                d = this.copy(d);
            }
            for (n = d.getDocumentElement().getFirstChild(); n != null; n = n.getNextSibling()) {
                if (!(n instanceof Element) || !"import".equals((e = (Element)n).getLocalName())) continue;
                String loc = e.getAttribute("schemaLocation");
                if (ids == null || ids.contains(loc)) {
                    e.removeAttribute("schemaLocation");
                }
                this.updateSchemaLocation(e);
                if (!StringUtils.isEmpty(e.getAttribute("namespace"))) continue;
                e.setAttribute("namespace", serviceInfo.getInterface().getName().getNamespaceURI());
            }
        }
        SchemaInfo schema = new SchemaInfo(ns);
        schema.setSystemId(systemId);
        Document document = d;
        synchronized (document) {
            xmlSchema = col.read(d, systemId);
            schema.setSchema(xmlSchema);
            schema.setElement(d.getDocumentElement());
        }
        serviceInfo.addSchema(schema);
        return xmlSchema;
    }

    private Document doEmptyNamespaceHack(Document d, boolean alreadyWritable) {
        boolean hasStuffToRemove = false;
        Element el = DOMUtils.getFirstElement(d.getDocumentElement());
        while (el != null) {
            if ("import".equals(el.getLocalName()) && StringUtils.isEmpty(el.getAttribute("targetNamespace"))) {
                hasStuffToRemove = true;
                break;
            }
            el = DOMUtils.getNextElement(el);
        }
        if (hasStuffToRemove) {
            if (!alreadyWritable) {
                d = this.copy(d);
            }
            el = DOMUtils.getFirstElement(d.getDocumentElement());
            while (el != null) {
                if ("import".equals(el.getLocalName()) && StringUtils.isEmpty(el.getAttribute("targetNamespace"))) {
                    d.getDocumentElement().removeChild(el);
                    el = DOMUtils.getFirstElement(d.getDocumentElement());
                    continue;
                }
                el = DOMUtils.getNextElement(el);
            }
        }
        return d;
    }

    private Document copy(Document doc) {
        try {
            return StaxUtils.copy(doc);
        }
        catch (ParserConfigurationException | XMLStreamException exception) {
            return doc;
        }
    }

    protected void updateSchemaLocation(Element e) {
        String ns = e.getAttribute("namespace");
        String newLoc = BUILTIN_SCHEMA_LOCS.get(ns);
        if (newLoc != null) {
            e.setAttribute("schemaLocation", newLoc);
        }
    }

    public Map<String, String> getNamespaceMap() {
        return this.namespaceMap;
    }

    public void setNamespaceMap(Map<String, String> namespaceMap) {
        AbstractDataBinding.checkNamespaceMap(namespaceMap);
        this.namespaceMap = namespaceMap;
    }

    public Map<String, String> getContextualNamespaceMap() {
        return this.contextualNamespaceMap;
    }

    public void setContextualNamespaceMap(Map<String, String> contextualNamespaceMap) {
        this.contextualNamespaceMap = contextualNamespaceMap;
    }

    @Override
    public Map<String, String> getDeclaredNamespaceMappings() {
        return this.namespaceMap;
    }

    protected static void checkNamespaceMap(Map<String, String> namespaceMap) {
        if (namespaceMap != null) {
            HashSet<String> prefixesSoFar = new HashSet<String>();
            for (Map.Entry<String, String> mapping : namespaceMap.entrySet()) {
                if (prefixesSoFar.contains(mapping.getValue())) {
                    throw new IllegalArgumentException("Duplicate prefix " + mapping.getValue());
                }
                prefixesSoFar.add(mapping.getValue());
            }
        }
    }

    @Override
    public void setMtomEnabled(boolean enabled) {
        this.mtomEnabled = enabled;
    }

    @Override
    public boolean isMtomEnabled() {
        return this.mtomEnabled;
    }

    @Override
    public int getMtomThreshold() {
        return this.mtomThreshold;
    }

    @Override
    public void setMtomThreshold(int threshold) {
        this.mtomThreshold = threshold;
    }

    static {
        BUILTIN_SCHEMA_LOCS.put("http://www.w3.org/2005/08/addressing", "http://www.w3.org/2006/03/addressing/ws-addr.xsd");
        BUILTIN_SCHEMA_LOCS.put("http://ws-i.org/profiles/basic/1.1/xsd", "http://ws-i.org/profiles/basic/1.1/swaref.xsd");
    }
}

