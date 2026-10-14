/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.wsdl11;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;
import javax.wsdl.Definition;
import javax.wsdl.Import;
import javax.wsdl.Types;
import javax.wsdl.extensions.UnknownExtensibilityElement;
import javax.wsdl.extensions.schema.Schema;
import javax.wsdl.extensions.schema.SchemaImport;
import org.apache.cxf.Bus;
import org.apache.cxf.catalog.CatalogXmlSchemaURIResolver;
import org.apache.cxf.common.xmlschema.SchemaCollection;
import org.apache.cxf.helpers.CastUtils;
import org.apache.cxf.service.model.SchemaInfo;
import org.apache.cxf.service.model.ServiceInfo;
import org.apache.ws.commons.schema.XmlSchema;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public final class SchemaUtil {
    private final Map<String, Element> schemaList;
    private final Map<String, String> catalogResolved = new HashMap<String, String>();
    private final Bus bus;

    public SchemaUtil(Bus b, Map<String, Element> s2) {
        this.bus = b;
        this.schemaList = s2;
    }

    public void getSchemas(Definition def, ServiceInfo serviceInfo) {
        SchemaCollection schemaCol = serviceInfo.getXmlSchemaCollection();
        this.getSchemas(def, schemaCol, serviceInfo);
    }

    public void getSchemas(Definition def, SchemaCollection schemaCol, ServiceInfo serviceInfo) {
        this.getSchemas(def, schemaCol, serviceInfo.getSchemas());
    }

    public void getSchemas(Definition def, SchemaCollection schemaCol, List<SchemaInfo> schemas) {
        ArrayList<Definition> defList = new ArrayList<Definition>();
        this.parseImports(def, defList);
        this.extractSchema(def, schemaCol, schemas);
        this.getSchemaList(def);
        IdentityHashMap<Definition, Definition> done = new IdentityHashMap<Definition, Definition>();
        done.put(def, def);
        for (Definition def2 : defList) {
            if (done.containsKey(def2)) continue;
            this.extractSchema(def2, schemaCol, schemas);
            this.getSchemaList(def2);
            done.put(def2, def2);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void extractSchema(Definition def, SchemaCollection schemaCol, List<SchemaInfo> schemaInfos) {
        Types typesElement = def.getTypes();
        if (typesElement != null) {
            int schemaCount = 1;
            for (Object obj : typesElement.getExtensibilityElements()) {
                Element elem;
                Element schemaElem = null;
                if (obj instanceof Schema) {
                    Schema schema = (Schema)obj;
                    schemaElem = schema.getElement();
                } else if (obj instanceof UnknownExtensibilityElement && "schema".equals((elem = ((UnknownExtensibilityElement)obj).getElement()).getLocalName())) {
                    schemaElem = elem;
                }
                if (schemaElem == null) continue;
                Document document = schemaElem.getOwnerDocument();
                synchronized (document) {
                    for (Object prefix : def.getNamespaces().keySet()) {
                        String ns = (String)def.getNamespaces().get(prefix);
                        if ("".equals(prefix)) {
                            if (schemaElem.hasAttribute("xmlns")) continue;
                            Attr attr = schemaElem.getOwnerDocument().createAttributeNS("http://www.w3.org/2000/xmlns/", "xmlns");
                            attr.setValue(ns);
                            schemaElem.setAttributeNodeNS(attr);
                            continue;
                        }
                        if (schemaElem.hasAttribute("xmlns:" + prefix)) continue;
                        String namespace = "http://www.w3.org/2000/xmlns/";
                        Attr attr = schemaElem.getOwnerDocument().createAttributeNS(namespace, "xmlns:" + prefix);
                        attr.setValue(ns);
                        schemaElem.setAttributeNodeNS(attr);
                    }
                    String systemId = def.getDocumentBaseURI() + "#types" + schemaCount;
                    if (def.getDocumentBaseURI() != null && def.getDocumentBaseURI().toUpperCase().endsWith(".XSD") && def.getTargetNamespace() == null && obj instanceof Schema && ((Schema)obj).getDocumentBaseURI().equals(def.getDocumentBaseURI())) {
                        systemId = def.getDocumentBaseURI();
                    }
                    schemaCol.setBaseUri(def.getDocumentBaseURI());
                    CatalogXmlSchemaURIResolver schemaResolver = new CatalogXmlSchemaURIResolver(this.bus);
                    schemaCol.setSchemaResolver(schemaResolver);
                    XmlSchema xmlSchema = schemaCol.read(schemaElem, systemId);
                    this.catalogResolved.putAll(schemaResolver.getResolvedMap());
                    SchemaInfo schemaInfo = new SchemaInfo(xmlSchema.getTargetNamespace());
                    schemaInfo.setSchema(xmlSchema);
                    schemaInfo.setSystemId(systemId);
                    schemaInfo.setElement(schemaElem);
                    schemaInfos.add(schemaInfo);
                    ++schemaCount;
                }
            }
        }
    }

    private void parseImports(Definition def, List<Definition> defList) {
        ArrayList importList = new ArrayList();
        Collection<List> ilist = CastUtils.cast(def.getImports().values());
        for (List list : ilist) {
            importList.addAll(list);
        }
        for (Import impt : importList) {
            if (defList.contains(impt.getDefinition())) continue;
            defList.add(impt.getDefinition());
            this.parseImports(impt.getDefinition(), defList);
        }
    }

    private void getSchemaList(Definition def) {
        Types typesElement = def.getTypes();
        if (typesElement != null) {
            for (Object obj : typesElement.getExtensibilityElements()) {
                if (!(obj instanceof Schema)) continue;
                Schema schema = (Schema)obj;
                this.addSchema(schema.getDocumentBaseURI(), schema);
            }
        }
    }

    private void addSchema(String docBaseURI, Schema schema) {
        String tns;
        Element schemaEle = schema.getElement();
        if (this.schemaList.get(docBaseURI) == null) {
            this.schemaList.put(docBaseURI, schemaEle);
        } else if (!(this.schemaList.get(docBaseURI) != null && this.schemaList.containsValue(schemaEle) || this.schemaList.get(tns = schema.getDocumentBaseURI() + "#" + schema.getElement().getAttribute("targetNamespace")) != null)) {
            this.schemaList.put(tns, schema.getElement());
        }
        Map imports = CastUtils.cast(schema.getImports());
        if (imports != null && !imports.isEmpty()) {
            for (Map.Entry entry : imports.entrySet()) {
                String importNamespace = (String)entry.getKey();
                List<SchemaImport> schemaImports = CastUtils.cast((List)entry.getValue());
                for (SchemaImport schemaImport : schemaImports) {
                    Schema tempImport = schemaImport.getReferencedSchema();
                    String key = schemaImport.getSchemaLocationURI();
                    if (importNamespace == null && tempImport != null) {
                        importNamespace = tempImport.getDocumentBaseURI();
                    }
                    if (tempImport != null && !this.catalogResolved.containsKey(key)) {
                        key = tempImport.getDocumentBaseURI();
                    }
                    if (tempImport == null || this.isSchemaParsed(key, importNamespace) || this.schemaList.containsValue(tempImport.getElement())) continue;
                    this.addSchema(key, tempImport);
                }
            }
        }
    }

    private boolean isSchemaParsed(String baseUri, String ns) {
        Element ele;
        String tns;
        return this.schemaList.get(baseUri) != null && ns.equals(tns = (ele = this.schemaList.get(baseUri)).getAttribute("targetNamespace"));
    }
}

