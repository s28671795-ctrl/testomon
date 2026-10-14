/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.service.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import javax.xml.namespace.QName;
import org.apache.cxf.common.xmlschema.SchemaCollection;
import org.apache.cxf.service.model.AbstractDescriptionElement;
import org.apache.cxf.service.model.BindingInfo;
import org.apache.cxf.service.model.DescriptionInfo;
import org.apache.cxf.service.model.EndpointInfo;
import org.apache.cxf.service.model.InterfaceInfo;
import org.apache.cxf.service.model.MessageInfo;
import org.apache.cxf.service.model.NamedItem;
import org.apache.cxf.service.model.OperationInfo;
import org.apache.cxf.service.model.SchemaInfo;
import org.apache.cxf.service.model.ServiceSchemaInfo;

public class ServiceInfo
extends AbstractDescriptionElement
implements NamedItem {
    QName name;
    String targetNamespace;
    InterfaceInfo intf;
    List<BindingInfo> bindings = new CopyOnWriteArrayList<BindingInfo>();
    List<EndpointInfo> endpoints = new CopyOnWriteArrayList<EndpointInfo>();
    Map<QName, MessageInfo> messages;
    List<SchemaInfo> schemas = new ArrayList<SchemaInfo>(4);
    private SchemaCollection xmlSchemaCollection = new SchemaCollection();
    private String topLevelDoc;
    private DescriptionInfo description;

    @Override
    public DescriptionInfo getDescription() {
        return this.description;
    }

    public void setDescription(DescriptionInfo d) {
        this.description = d;
    }

    public String getTopLevelDoc() {
        return this.topLevelDoc;
    }

    public void setTopLevelDoc(String s2) {
        this.topLevelDoc = s2;
    }

    public String getTargetNamespace() {
        return this.targetNamespace;
    }

    public void setTargetNamespace(String ns) {
        this.targetNamespace = ns;
    }

    public void setName(QName n) {
        this.name = n;
    }

    @Override
    public QName getName() {
        return this.name;
    }

    public InterfaceInfo createInterface(QName qn) {
        this.intf = new InterfaceInfo(this, qn);
        return this.intf;
    }

    public void setInterface(InterfaceInfo inf) {
        this.intf = inf;
    }

    public InterfaceInfo getInterface() {
        return this.intf;
    }

    public BindingInfo getBinding(QName qn) {
        for (BindingInfo bi : this.bindings) {
            if (!qn.equals(bi.getName())) continue;
            return bi;
        }
        return null;
    }

    public void addBinding(BindingInfo binding) {
        BindingInfo bi = this.getBinding(binding.getName());
        if (bi != null) {
            this.bindings.remove(bi);
        }
        this.bindings.add(binding);
    }

    public EndpointInfo getEndpoint(QName qn) {
        for (EndpointInfo ei : this.endpoints) {
            if (!qn.equals(ei.getName())) continue;
            return ei;
        }
        return null;
    }

    public void addEndpoint(EndpointInfo ep) {
        EndpointInfo ei = this.getEndpoint(ep.getName());
        if (ei != null) {
            this.endpoints.remove(ei);
        }
        this.endpoints.add(ep);
    }

    public Collection<EndpointInfo> getEndpoints() {
        return Collections.unmodifiableCollection(this.endpoints);
    }

    public Collection<BindingInfo> getBindings() {
        return Collections.unmodifiableCollection(this.bindings);
    }

    public Map<QName, MessageInfo> getMessages() {
        if (this.messages == null) {
            this.initMessagesMap();
        }
        return this.messages;
    }

    public MessageInfo getMessage(QName qname) {
        return this.getMessages().get(qname);
    }

    private void initMessagesMap() {
        this.messages = new ConcurrentHashMap<QName, MessageInfo>(16, 0.75f, 2);
        for (OperationInfo operation : this.getInterface().getOperations()) {
            if (operation.getInput() != null) {
                this.messages.put(operation.getInput().getName(), operation.getInput());
            }
            if (operation.getOutput() == null) continue;
            this.messages.put(operation.getOutput().getName(), operation.getOutput());
        }
    }

    public void setMessages(Map<QName, MessageInfo> msgs) {
        this.messages = msgs;
    }

    public void refresh() {
        this.initMessagesMap();
    }

    public void addSchema(SchemaInfo schemaInfo) {
        this.schemas.add(schemaInfo);
    }

    public SchemaInfo addNewSchema(String namespaceURI) {
        SchemaInfo schemaInfo = new SchemaInfo(namespaceURI);
        schemaInfo.setSchema(this.getXmlSchemaCollection().newXmlSchemaInCollection(namespaceURI));
        this.schemas.add(schemaInfo);
        return schemaInfo;
    }

    public SchemaInfo getSchema(String namespaceURI) {
        for (SchemaInfo s2 : this.schemas) {
            if (!(namespaceURI != null ? namespaceURI.equals(s2.getNamespaceURI()) : s2.getNamespaceURI() == null)) continue;
            return s2;
        }
        return null;
    }

    public List<SchemaInfo> getSchemas() {
        return this.schemas;
    }

    public SchemaCollection getXmlSchemaCollection() {
        return this.xmlSchemaCollection;
    }

    public void setServiceSchemaInfo(ServiceSchemaInfo serviceSchemaInfo) {
        this.xmlSchemaCollection = serviceSchemaInfo.getSchemaCollection();
        this.schemas = serviceSchemaInfo.getSchemaInfoList();
    }

    public void setSchemas(SchemaCollection cachedXmlSchemaCollection, List<SchemaInfo> cachedSchemas) {
        this.xmlSchemaCollection = cachedXmlSchemaCollection;
        this.schemas = cachedSchemas;
    }
}

