/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.wsdl11;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import javax.wsdl.Binding;
import javax.wsdl.BindingFault;
import javax.wsdl.BindingInput;
import javax.wsdl.BindingOperation;
import javax.wsdl.BindingOutput;
import javax.wsdl.Definition;
import javax.wsdl.Fault;
import javax.wsdl.Import;
import javax.wsdl.Input;
import javax.wsdl.Message;
import javax.wsdl.Operation;
import javax.wsdl.OperationType;
import javax.wsdl.Output;
import javax.wsdl.Part;
import javax.wsdl.Port;
import javax.wsdl.PortType;
import javax.wsdl.Service;
import javax.wsdl.Types;
import javax.wsdl.WSDLElement;
import javax.wsdl.WSDLException;
import javax.wsdl.extensions.AttributeExtensible;
import javax.wsdl.extensions.ElementExtensible;
import javax.wsdl.extensions.ExtensibilityElement;
import javax.wsdl.extensions.ExtensionRegistry;
import javax.wsdl.extensions.UnknownExtensibilityElement;
import javax.wsdl.extensions.schema.Schema;
import javax.wsdl.extensions.schema.SchemaImport;
import javax.wsdl.extensions.schema.SchemaReference;
import javax.xml.namespace.QName;
import org.apache.cxf.Bus;
import org.apache.cxf.helpers.CastUtils;
import org.apache.cxf.helpers.DOMUtils;
import org.apache.cxf.service.model.AbstractMessageContainer;
import org.apache.cxf.service.model.AbstractPropertiesHolder;
import org.apache.cxf.service.model.BindingFaultInfo;
import org.apache.cxf.service.model.BindingInfo;
import org.apache.cxf.service.model.BindingMessageInfo;
import org.apache.cxf.service.model.BindingOperationInfo;
import org.apache.cxf.service.model.EndpointInfo;
import org.apache.cxf.service.model.FaultInfo;
import org.apache.cxf.service.model.InterfaceInfo;
import org.apache.cxf.service.model.MessageInfo;
import org.apache.cxf.service.model.MessagePartInfo;
import org.apache.cxf.service.model.OperationInfo;
import org.apache.cxf.service.model.SchemaInfo;
import org.apache.cxf.service.model.ServiceInfo;
import org.apache.cxf.wsdl.WSDLConstants;
import org.apache.cxf.wsdl.WSDLManager;
import org.apache.cxf.wsdl11.NSManager;
import org.apache.cxf.wsdl11.WSDLServiceBuilder;
import org.apache.ws.commons.schema.XmlSchemaExternal;
import org.apache.ws.commons.schema.XmlSchemaImport;
import org.apache.ws.commons.schema.XmlSchemaInclude;
import org.apache.ws.commons.schema.XmlSchemaRedefine;
import org.apache.ws.commons.schema.XmlSchemaSerializer;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class ServiceWSDLBuilder {
    private final Map<String, String> ns2prefix;
    private final List<ServiceInfo> services;
    private boolean useSchemaImports;
    private String baseFileName;
    private int xsdCount;
    private final Bus bus;
    private final NSManager nsMan;

    public ServiceWSDLBuilder(Bus b, List<ServiceInfo> services) {
        this.services = services;
        this.bus = b;
        this.ns2prefix = new HashMap<String, String>();
        this.nsMan = new NSManager();
    }

    public ServiceWSDLBuilder(Bus b, ServiceInfo ... services) {
        this(b, Arrays.asList(services));
    }

    public void setUseSchemaImports(boolean b) {
        this.useSchemaImports = b;
    }

    public void setBaseFileName(String s2) {
        this.baseFileName = s2;
    }

    public Definition build() throws WSDLException {
        this.useSchemaImports = false;
        return this.build(null);
    }

    public Definition build(Map<String, SchemaInfo> imports) throws WSDLException {
        Definition definition = null;
        try {
            definition = this.services.get(0).getProperty(WSDLServiceBuilder.WSDL_DEFINITION, Definition.class);
        }
        catch (ClassCastException classCastException) {
            // empty catch block
        }
        if (definition == null) {
            ServiceInfo si = this.services.get(0);
            definition = this.newDefinition(si.getName(), si.getTargetNamespace());
            this.addNamespace("tns", si.getTargetNamespace(), definition);
            this.addExtensibilityElements(definition, definition, this.getWSDL11Extensors(si.getDescription()));
            HashSet<PortType> portTypes = new HashSet<PortType>();
            for (ServiceInfo service2 : this.services) {
                Definition portTypeDef = definition;
                Definition orig = definition;
                if (!this.isSameTNS(service2)) {
                    portTypeDef = this.newDefinition(service2.getInterface().getName(), service2.getInterface().getName().getNamespaceURI());
                    Import wsdlImport = definition.createImport();
                    String tns = service2.getInterface().getName().getNamespaceURI();
                    wsdlImport.setDefinition(portTypeDef);
                    wsdlImport.setNamespaceURI(tns);
                    wsdlImport.setLocationURI(service2.getInterface().getName().getLocalPart() + ".wsdl");
                    definition.addImport(wsdlImport);
                    this.addNamespace(this.getPrefix(tns), tns, definition);
                    definition = portTypeDef;
                }
                portTypes.add(this.buildPortType(service2.getInterface(), portTypeDef));
                if (service2.getSchemas() != null && service2.getSchemas().size() > 0) {
                    if (!this.useSchemaImports) {
                        this.buildTypes(service2.getSchemas(), imports, portTypeDef);
                    } else {
                        this.buildTypesWithSchemaImports(service2.getSchemas(), imports, portTypeDef);
                    }
                }
                definition = orig;
            }
            for (ServiceInfo service2 : this.services) {
                this.buildBinding(definition, service2.getBindings(), portTypes);
                this.buildService(service2, definition);
            }
        }
        return definition;
    }

    private boolean isSameTNS(ServiceInfo service2) {
        return service2.getName().getNamespaceURI().equals(service2.getInterface().getName().getNamespaceURI());
    }

    private Definition newDefinition(QName name, String targetNamespace) {
        Definition d = this.bus.getExtension(WSDLManager.class).getWSDLFactory().newDefinition();
        d.setExtensionRegistry(this.bus.getExtension(WSDLManager.class).getExtensionRegistry());
        d.setQName(name);
        d.setTargetNamespace(targetNamespace);
        this.addNamespace("xsd", "http://www.w3.org/2001/XMLSchema", d);
        return d;
    }

    public List<ExtensibilityElement> getWSDL11Extensors(AbstractPropertiesHolder holder) {
        if (holder == null) {
            return null;
        }
        return holder.getExtensors(ExtensibilityElement.class);
    }

    protected void addDocumentation(WSDLElement wsdlel, String text) {
        if (text == null) {
            return;
        }
        Element el = DOMUtils.getEmptyDocument().createElementNS("http://schemas.xmlsoap.org/wsdl/", "wsdl:documentation");
        el.setTextContent(text);
        wsdlel.setDocumentationElement(el);
    }

    protected void addExtensibilityElements(Definition def, ElementExtensible elementExtensible, List<ExtensibilityElement> extensibilityElements) {
        if (extensibilityElements != null) {
            for (ExtensibilityElement element : extensibilityElements) {
                if (element instanceof UnknownExtensibilityElement) {
                    UnknownExtensibilityElement uee = (UnknownExtensibilityElement)element;
                    String pfx = uee.getElement().getPrefix();
                    this.addNamespace(pfx, element.getElementType().getNamespaceURI(), def);
                } else {
                    QName qn = element.getElementType();
                    this.addNamespace(qn.getNamespaceURI(), def);
                }
                elementExtensible.addExtensibilityElement(element);
            }
        }
    }

    private void addExtensibilityAttributes(Definition def, AttributeExtensible attributeExtensible, Map<QName, Object> attributes) {
        if (attributes == null) {
            return;
        }
        for (Map.Entry<QName, Object> entry : attributes.entrySet()) {
            this.addNamespace(entry.getKey().getNamespaceURI(), def);
            attributeExtensible.setExtensionAttribute(entry.getKey(), entry.getValue());
        }
    }

    private Schema getSchemaImplementation(Definition def) {
        ExtensibilityElement extension;
        ExtensionRegistry reg = def.getExtensionRegistry();
        try {
            extension = reg.createExtension(Types.class, WSDLConstants.QNAME_SCHEMA);
        }
        catch (WSDLException e) {
            throw new RuntimeException("Problem creating schema implementation", e);
        }
        try {
            return (Schema)Schema.class.cast(extension);
        }
        catch (ClassCastException e) {
            throw new RuntimeException("Schema implementation problem", e);
        }
    }

    protected void buildTypes(Collection<SchemaInfo> schemas, Map<String, SchemaInfo> imports, Definition def) {
        Types types = def.createTypes();
        for (SchemaInfo schemaInfo : schemas) {
            Schema schemaImpl = this.getSchemaImplementation(def);
            schemaImpl.setRequired(true);
            schemaImpl.setElementType(WSDLConstants.QNAME_SCHEMA);
            schemaImpl.setElement(schemaInfo.getElement());
            for (XmlSchemaExternal ext : schemaInfo.getSchema().getExternals()) {
                Schema schemaImpl2;
                SchemaReference imp;
                if (ext.getSchema() == null) continue;
                if (ext instanceof XmlSchemaImport) {
                    imp = schemaImpl.createImport();
                    imp.setNamespaceURI(((XmlSchemaImport)ext).getNamespace());
                    imp.setSchemaLocationURI(((XmlSchemaImport)ext).getSchemaLocation());
                    schemaImpl2 = this.getSchemaImplementation(def);
                    schemaImpl2.setRequired(true);
                    schemaImpl2.setElementType(WSDLConstants.QNAME_SCHEMA);
                    schemaImpl2.setDocumentBaseURI(ext.getSchema().getSourceURI());
                    try {
                        schemaImpl2.setElement(ext.getSchema().getSchemaDocument().getDocumentElement());
                    }
                    catch (XmlSchemaSerializer.XmlSchemaSerializerException xmlSchemaSerializerException) {
                        // empty catch block
                    }
                    imp.setReferencedSchema(schemaImpl2);
                    schemaImpl.addImport((SchemaImport)imp);
                    continue;
                }
                if (ext instanceof XmlSchemaInclude) {
                    imp = schemaImpl.createInclude();
                    imp.setSchemaLocationURI(((XmlSchemaInclude)ext).getSchemaLocation());
                    schemaImpl2 = this.getSchemaImplementation(def);
                    schemaImpl2.setRequired(true);
                    schemaImpl2.setElementType(WSDLConstants.QNAME_SCHEMA);
                    schemaImpl2.setDocumentBaseURI(ext.getSchema().getSourceURI());
                    try {
                        schemaImpl2.setElement(ext.getSchema().getSchemaDocument().getDocumentElement());
                    }
                    catch (XmlSchemaSerializer.XmlSchemaSerializerException xmlSchemaSerializerException) {
                        // empty catch block
                    }
                    imp.setReferencedSchema(schemaImpl2);
                    schemaImpl.addInclude(imp);
                    continue;
                }
                if (!(ext instanceof XmlSchemaRedefine)) continue;
                imp = schemaImpl.createRedefine();
                imp.setSchemaLocationURI(((XmlSchemaRedefine)ext).getSchemaLocation());
                schemaImpl2 = this.getSchemaImplementation(def);
                schemaImpl2.setRequired(true);
                schemaImpl2.setElementType(WSDLConstants.QNAME_SCHEMA);
                schemaImpl2.setDocumentBaseURI(ext.getSchema().getSourceURI());
                try {
                    schemaImpl2.setElement(ext.getSchema().getSchemaDocument().getDocumentElement());
                }
                catch (XmlSchemaSerializer.XmlSchemaSerializerException xmlSchemaSerializerException) {
                    // empty catch block
                }
                imp.setReferencedSchema(schemaImpl2);
                schemaImpl.addRedefine(imp);
            }
            types.addExtensibilityElement(schemaImpl);
        }
        def.setTypes(types);
    }

    protected void buildTypesWithSchemaImports(Collection<SchemaInfo> schemas, Map<String, SchemaInfo> imports, Definition def) {
        Schema schema;
        Types types = def.createTypes();
        HashMap<String, Schema> namespaceToSchemaMap = new HashMap<String, Schema>();
        HashMap<String, SchemaInfo> namespaceToSchemaInfo = new HashMap<String, SchemaInfo>();
        for (SchemaInfo schemaInfo : schemas) {
            schema = this.getSchemaImplementation(def);
            schema.setRequired(true);
            schema.setElementType(WSDLConstants.QNAME_SCHEMA);
            String name = this.baseFileName + "_schema" + ++this.xsdCount + ".xsd";
            schema.setDocumentBaseURI(name);
            schema.setElement(schemaInfo.getElement());
            namespaceToSchemaMap.put(schemaInfo.getNamespaceURI(), schema);
            namespaceToSchemaInfo.put(schemaInfo.getNamespaceURI(), schemaInfo);
            imports.put(name, schemaInfo);
        }
        for (Schema schema2 : namespaceToSchemaMap.values()) {
            Element docElement = schema2.getElement();
            List<Element> elementList = DOMUtils.findAllElementsByTagNameNS(docElement, "http://www.w3.org/2001/XMLSchema", "import");
            for (Element el : elementList) {
                String sn = el.getAttribute("namespace");
                Schema referencedSchema = (Schema)namespaceToSchemaMap.get(sn);
                if (referencedSchema == null) continue;
                SchemaInfo schemaInfo = (SchemaInfo)namespaceToSchemaInfo.get(sn);
                el.setAttribute("schemaLocation", referencedSchema.getDocumentBaseURI());
                this.addSchemaImport(schema2, schemaInfo, referencedSchema);
            }
        }
        Document doc = DOMUtils.createDocument();
        Element nd = doc.createElementNS("http://www.w3.org/2001/XMLSchema", "schema");
        nd.setAttribute("xmlns", "http://www.w3.org/2001/XMLSchema");
        doc.appendChild(nd);
        schema = this.getSchemaImplementation(def);
        schema.setRequired(true);
        schema.setElementType(WSDLConstants.QNAME_SCHEMA);
        Collection defNamespaces = CastUtils.cast(def.getNamespaces().values());
        for (SchemaInfo schemaInfo : schemas) {
            Schema referencedSchema = (Schema)namespaceToSchemaMap.get(schemaInfo.getNamespaceURI());
            if (!defNamespaces.contains(schemaInfo.getNamespaceURI())) continue;
            Element impElement = doc.createElementNS("http://www.w3.org/2001/XMLSchema", "import");
            impElement.setAttribute("schemaLocation", referencedSchema.getDocumentBaseURI());
            impElement.setAttribute("namespace", schemaInfo.getNamespaceURI());
            nd.appendChild(impElement);
            this.addSchemaImport(schema, schemaInfo, referencedSchema);
        }
        schema.setElement(nd);
        types.addExtensibilityElement(schema);
        def.setTypes(types);
    }

    private void addSchemaImport(Schema schema, SchemaInfo schemaInfo, Schema referencedSchema) {
        SchemaImport imp = schema.createImport();
        imp.setId(schemaInfo.getSystemId());
        imp.setNamespaceURI(schemaInfo.getNamespaceURI());
        imp.setSchemaLocationURI(referencedSchema.getDocumentBaseURI());
        imp.setReferencedSchema(referencedSchema);
        schema.addImport(imp);
    }

    protected void buildBinding(Definition definition, Collection<BindingInfo> bindingInfos, Collection<PortType> portTypes) {
        Binding binding = null;
        for (BindingInfo bindingInfo : bindingInfos) {
            binding = definition.createBinding();
            this.addDocumentation(binding, bindingInfo.getDocumentation());
            binding.setUndefined(false);
            for (PortType portType : portTypes) {
                if (!portType.getQName().equals(bindingInfo.getInterface().getName())) continue;
                binding.setPortType(portType);
                break;
            }
            binding.setQName(bindingInfo.getName());
            if (!bindingInfo.getName().getNamespaceURI().equals(definition.getTargetNamespace())) {
                this.addNamespace(bindingInfo.getName().getNamespaceURI(), definition);
            }
            this.buildBindingOperation(definition, binding, bindingInfo.getOperations());
            this.addExtensibilityElements(definition, binding, this.getWSDL11Extensors(bindingInfo));
            definition.addBinding(binding);
        }
    }

    protected void buildBindingOperation(Definition def, Binding binding, Collection<BindingOperationInfo> bindingOperationInfos) {
        BindingOperation bindingOperation = null;
        for (BindingOperationInfo bindingOperationInfo : bindingOperationInfos) {
            bindingOperation = def.createBindingOperation();
            this.addDocumentation(bindingOperation, bindingOperationInfo.getDocumentation());
            bindingOperation.setName(bindingOperationInfo.getName().getLocalPart());
            for (Operation operation : CastUtils.cast(binding.getPortType().getOperations(), Operation.class)) {
                if (!operation.getName().equals(bindingOperation.getName())) continue;
                bindingOperation.setOperation(operation);
                break;
            }
            this.buildBindingInput(def, bindingOperation, bindingOperationInfo.getInput());
            this.buildBindingOutput(def, bindingOperation, bindingOperationInfo.getOutput());
            this.buildBindingFault(def, bindingOperation, bindingOperationInfo.getFaults());
            this.addExtensibilityAttributes(def, bindingOperation, bindingOperationInfo.getExtensionAttributes());
            this.addExtensibilityElements(def, bindingOperation, this.getWSDL11Extensors(bindingOperationInfo));
            binding.addBindingOperation(bindingOperation);
        }
    }

    protected void buildBindingFault(Definition def, BindingOperation bindingOperation, Collection<BindingFaultInfo> bindingFaultInfos) {
        BindingFault bindingFault = null;
        for (BindingFaultInfo bindingFaultInfo : bindingFaultInfos) {
            bindingFault = def.createBindingFault();
            this.addDocumentation(bindingFault, bindingFaultInfo.getDocumentation());
            bindingFault.setName(bindingFaultInfo.getFaultInfo().getFaultName().getLocalPart());
            bindingOperation.addBindingFault(bindingFault);
            this.addExtensibilityAttributes(def, bindingFault, bindingFaultInfo.getExtensionAttributes());
            this.addExtensibilityElements(def, bindingFault, this.getWSDL11Extensors(bindingFaultInfo));
        }
    }

    protected void buildBindingInput(Definition def, BindingOperation bindingOperation, BindingMessageInfo bindingMessageInfo) {
        BindingInput bindingInput = null;
        if (bindingMessageInfo != null) {
            bindingInput = def.createBindingInput();
            this.addDocumentation(bindingInput, bindingMessageInfo.getDocumentation());
            bindingInput.setName(bindingMessageInfo.getMessageInfo().getName().getLocalPart());
            bindingOperation.setBindingInput(bindingInput);
            this.addExtensibilityAttributes(def, bindingInput, bindingMessageInfo.getExtensionAttributes());
            this.addExtensibilityElements(def, bindingInput, this.getWSDL11Extensors(bindingMessageInfo));
        }
    }

    protected void buildBindingOutput(Definition def, BindingOperation bindingOperation, BindingMessageInfo bindingMessageInfo) {
        BindingOutput bindingOutput = null;
        if (bindingMessageInfo != null) {
            bindingOutput = def.createBindingOutput();
            this.addDocumentation(bindingOutput, bindingMessageInfo.getDocumentation());
            bindingOutput.setName(bindingMessageInfo.getMessageInfo().getName().getLocalPart());
            bindingOperation.setBindingOutput(bindingOutput);
            this.addExtensibilityAttributes(def, bindingOutput, bindingMessageInfo.getExtensionAttributes());
            this.addExtensibilityElements(def, bindingOutput, this.getWSDL11Extensors(bindingMessageInfo));
        }
    }

    protected void buildService(ServiceInfo serviceInfo, Definition definition) {
        Map<QName, MessageInfo> messages = serviceInfo.getMessages();
        for (Map.Entry<QName, MessageInfo> mie : messages.entrySet()) {
            if (!mie.getKey().getNamespaceURI().equals(definition.getTargetNamespace()) || definition.getMessage(mie.getKey()) != null) continue;
            Message message = definition.createMessage();
            this.addDocumentation(message, mie.getValue().getMessageDocumentation());
            message.setUndefined(false);
            message.setQName(mie.getKey());
            for (MessagePartInfo mpi : mie.getValue().getMessageParts()) {
                Part part = definition.createPart();
                boolean elemental = mpi.isElement();
                if (elemental && null == serviceInfo.getXmlSchemaCollection().getElementByQName(mpi.getElementQName())) {
                    elemental = false;
                }
                if (elemental) {
                    part.setElementName(mpi.getElementQName());
                } else {
                    part.setTypeName(mpi.getTypeQName());
                }
                part.setName(mpi.getName().getLocalPart());
                message.addPart(part);
            }
            definition.addMessage(message);
        }
        this.addDocumentation(definition, serviceInfo.getTopLevelDoc());
        Service serv = definition.createService();
        this.addDocumentation(serv, serviceInfo.getDocumentation());
        serv.setQName(serviceInfo.getName());
        this.addNamespace(serviceInfo.getName().getNamespaceURI(), definition);
        this.addExtensibilityElements(definition, serv, this.getWSDL11Extensors(serviceInfo));
        definition.addService(serv);
        for (EndpointInfo ei : serviceInfo.getEndpoints()) {
            this.addNamespace(ei.getTransportId(), definition);
            Port port = definition.createPort();
            this.addDocumentation(port, ei.getDocumentation());
            port.setName(ei.getName().getLocalPart());
            port.setBinding(definition.getBinding(ei.getBinding().getName()));
            this.addExtensibilityElements(definition, port, this.getWSDL11Extensors(ei));
            serv.addPort(port);
        }
    }

    protected PortType buildPortType(InterfaceInfo intf, Definition def) {
        PortType portType = null;
        try {
            portType = intf.getProperty(WSDLServiceBuilder.WSDL_PORTTYPE, PortType.class);
        }
        catch (ClassCastException classCastException) {
            // empty catch block
        }
        if (portType == null) {
            portType = def.createPortType();
            portType.setQName(intf.getName());
            this.addDocumentation(portType, intf.getDocumentation());
            this.addNamespace(intf.getName().getNamespaceURI(), def);
            this.addExtensibilityElements(def, portType, this.getWSDL11Extensors(intf));
            this.addExtensibilityAttributes(def, portType, intf.getExtensionAttributes());
            portType.setUndefined(false);
            this.buildPortTypeOperation(portType, intf.getOperations(), def);
        }
        def.addPortType(portType);
        return portType;
    }

    protected void addNamespace(String namespaceURI, Definition def) {
        this.addNamespace(this.getPrefix(namespaceURI), namespaceURI, def);
    }

    protected void addNamespace(String prefix, String namespaceURI, Definition def) {
        this.ns2prefix.put(namespaceURI, prefix);
        def.addNamespace(prefix, namespaceURI);
    }

    private OperationInfo getOperationInfo(OperationInfo operation) {
        if (operation.getUnwrappedOperation() != null) {
            return operation.getUnwrappedOperation();
        }
        return operation;
    }

    private Map<QName, Object> getInputExtensionAttributes(OperationInfo operation) {
        return this.getOperationInfo(operation).getInput().getExtensionAttributes();
    }

    private Map<QName, Object> getOutputExtensionAttributes(OperationInfo operation) {
        return this.getOperationInfo(operation).getOutput().getExtensionAttributes();
    }

    protected void buildPortTypeOperation(PortType portType, Collection<OperationInfo> operationInfos, Definition def) {
        for (OperationInfo operationInfo : operationInfos) {
            Operation operation = null;
            try {
                operation = operationInfo.getProperty(WSDLServiceBuilder.WSDL_OPERATION, Operation.class);
            }
            catch (ClassCastException classCastException) {
                // empty catch block
            }
            if (operation == null) {
                operation = def.createOperation();
                this.addDocumentation(operation, operationInfo.getDocumentation());
                operation.setUndefined(false);
                operation.setName(operationInfo.getName().getLocalPart());
                this.addNamespace(operationInfo.getName().getNamespaceURI(), def);
                if (operationInfo.isOneWay()) {
                    operation.setStyle(OperationType.ONE_WAY);
                }
                this.addExtensibilityElements(def, operation, this.getWSDL11Extensors(operationInfo));
                Input input = def.createInput();
                this.addDocumentation(input, operationInfo.getInput().getDocumentation());
                input.setName(operationInfo.getInputName());
                Message message = def.createMessage();
                this.buildMessage(message, operationInfo.getInput(), def);
                this.addExtensibilityAttributes(def, input, this.getInputExtensionAttributes(operationInfo));
                this.addExtensibilityElements(def, input, this.getWSDL11Extensors(operationInfo.getInput()));
                input.setMessage(message);
                operation.setInput(input);
                operation.setParameterOrdering(operationInfo.getParameterOrdering());
                if (operationInfo.getOutput() != null) {
                    Output output = def.createOutput();
                    this.addDocumentation(output, operationInfo.getOutput().getDocumentation());
                    output.setName(operationInfo.getOutputName());
                    message = def.createMessage();
                    this.buildMessage(message, operationInfo.getOutput(), def);
                    this.addExtensibilityAttributes(def, output, this.getOutputExtensionAttributes(operationInfo));
                    this.addExtensibilityElements(def, output, this.getWSDL11Extensors(operationInfo.getOutput()));
                    output.setMessage(message);
                    operation.setOutput(output);
                }
                Collection<FaultInfo> faults = operationInfo.getFaults();
                Fault fault = null;
                for (FaultInfo faultInfo : faults) {
                    fault = def.createFault();
                    this.addDocumentation(fault, faultInfo.getDocumentation());
                    fault.setName(faultInfo.getFaultName().getLocalPart());
                    message = def.createMessage();
                    this.buildMessage(message, faultInfo, def);
                    this.addExtensibilityAttributes(def, fault, faultInfo.getExtensionAttributes());
                    this.addExtensibilityElements(def, fault, this.getWSDL11Extensors(faultInfo));
                    fault.setMessage(message);
                    operation.addFault(fault);
                }
            }
            portType.addOperation(operation);
        }
    }

    private String getPrefix(String ns) {
        for (String namespace : this.nsMan.getNamespaces()) {
            if (!namespace.equals(ns)) continue;
            return this.nsMan.getPrefixFromNS(namespace);
        }
        String prefix = this.ns2prefix.get(ns);
        if (prefix == null) {
            prefix = this.getNewPrefix();
            this.ns2prefix.put(ns, prefix);
        }
        return prefix;
    }

    private String getNewPrefix() {
        String prefix = "ns1";
        int i = 0;
        while (this.ns2prefix.containsValue(prefix)) {
            prefix = "ns" + ++i;
        }
        return prefix;
    }

    protected void buildMessage(Message message, AbstractMessageContainer messageContainer, Definition def) {
        this.addDocumentation(message, messageContainer.getMessageDocumentation());
        message.setQName(messageContainer.getName());
        message.setUndefined(false);
        def.addMessage(message);
        List<MessagePartInfo> messageParts = messageContainer.getMessageParts();
        Part messagePart = null;
        for (MessagePartInfo messagePartInfo : messageParts) {
            messagePart = def.createPart();
            messagePart.setName(messagePartInfo.getName().getLocalPart());
            if (messagePartInfo.isElement()) {
                messagePart.setElementName(messagePartInfo.getElementQName());
                this.addNamespace(messagePartInfo.getElementQName().getNamespaceURI(), def);
            } else if (messagePartInfo.getTypeQName() != null) {
                messagePart.setTypeName(messagePartInfo.getTypeQName());
                this.addNamespace(messagePartInfo.getTypeQName().getNamespaceURI(), def);
            }
            message.addPart(messagePart);
        }
    }
}

