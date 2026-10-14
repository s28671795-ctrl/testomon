/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.wsdl11;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.wsdl.Binding;
import javax.wsdl.BindingFault;
import javax.wsdl.BindingOperation;
import javax.wsdl.Definition;
import javax.wsdl.Fault;
import javax.wsdl.Import;
import javax.wsdl.Input;
import javax.wsdl.Message;
import javax.wsdl.Operation;
import javax.wsdl.Output;
import javax.wsdl.Part;
import javax.wsdl.Port;
import javax.wsdl.PortType;
import javax.wsdl.Service;
import javax.wsdl.WSDLElement;
import javax.wsdl.extensions.AttributeExtensible;
import javax.wsdl.extensions.ExtensibilityElement;
import javax.wsdl.extensions.soap.SOAPBinding;
import javax.wsdl.extensions.soap12.SOAP12Binding;
import javax.xml.namespace.QName;
import org.apache.cxf.Bus;
import org.apache.cxf.BusException;
import org.apache.cxf.binding.AbstractBindingFactory;
import org.apache.cxf.binding.BindingFactory;
import org.apache.cxf.common.logging.LogUtils;
import org.apache.cxf.common.xmlschema.SchemaCollection;
import org.apache.cxf.helpers.CastUtils;
import org.apache.cxf.helpers.DOMUtils;
import org.apache.cxf.service.model.AbstractMessageContainer;
import org.apache.cxf.service.model.AbstractPropertiesHolder;
import org.apache.cxf.service.model.BindingFaultInfo;
import org.apache.cxf.service.model.BindingInfo;
import org.apache.cxf.service.model.BindingMessageInfo;
import org.apache.cxf.service.model.BindingOperationInfo;
import org.apache.cxf.service.model.DescriptionInfo;
import org.apache.cxf.service.model.EndpointInfo;
import org.apache.cxf.service.model.FaultInfo;
import org.apache.cxf.service.model.InterfaceInfo;
import org.apache.cxf.service.model.MessageInfo;
import org.apache.cxf.service.model.MessagePartInfo;
import org.apache.cxf.service.model.OperationInfo;
import org.apache.cxf.service.model.ServiceInfo;
import org.apache.cxf.service.model.ServiceSchemaInfo;
import org.apache.cxf.service.model.UnwrappedOperationInfo;
import org.apache.cxf.transport.DestinationFactory;
import org.apache.cxf.transport.DestinationFactoryManager;
import org.apache.cxf.wsdl.JAXBExtensibilityElement;
import org.apache.cxf.wsdl.WSDLManager;
import org.apache.cxf.wsdl.binding.WSDLBindingFactory;
import org.apache.cxf.wsdl11.SchemaUtil;
import org.apache.cxf.wsdl11.WSDLEndpointFactory;
import org.apache.cxf.wsdl11.WSDLRuntimeException;
import org.apache.cxf.wsdl11.WSDLServiceUtils;
import org.apache.ws.commons.schema.XmlSchemaComplexContentExtension;
import org.apache.ws.commons.schema.XmlSchemaComplexType;
import org.apache.ws.commons.schema.XmlSchemaElement;
import org.apache.ws.commons.schema.XmlSchemaSequence;
import org.apache.ws.commons.schema.XmlSchemaSequenceMember;
import org.apache.ws.commons.schema.XmlSchemaType;
import org.w3c.dom.Element;

public class WSDLServiceBuilder {
    public static final String WSDL_SCHEMA_LIST = WSDLServiceBuilder.class.getName() + ".SCHEMA";
    public static final String WSDL_DEFINITION = WSDLServiceBuilder.class.getName() + ".DEFINITION";
    public static final String WSDL_SERVICE = WSDLServiceBuilder.class.getName() + ".SERVICE";
    public static final String WSDL_PORTTYPE = WSDLServiceBuilder.class.getName() + ".WSDL_PORTTYPE";
    public static final String WSDL_PORT = WSDLServiceBuilder.class.getName() + ".PORT";
    public static final String WSDL_BINDING = WSDLServiceBuilder.class.getName() + ".BINDING";
    public static final String WSDL_SCHEMA_ELEMENT_LIST = WSDLServiceBuilder.class.getName() + ".SCHEMA_ELEMENTS";
    public static final String WSDL_OPERATION = WSDLServiceBuilder.class.getName() + ".OPERATION";
    public static final String WSDL_BINDING_OPERATION = WSDLServiceBuilder.class.getName() + ".BINDING_OPERATION";
    private static final Logger LOG = LogUtils.getL7dLogger(WSDLServiceBuilder.class);
    private Bus bus;
    private Map<String, Element> schemaList = new HashMap<String, Element>();
    private boolean recordOriginal = true;
    private boolean allowRefs;
    private boolean ignoreUnknownBindings;
    private Level unwrapLogLevel = Level.FINE;

    public WSDLServiceBuilder(Bus bus) {
        this.bus = bus;
    }

    public WSDLServiceBuilder(Bus bus, boolean record) {
        this.bus = bus;
        this.recordOriginal = record;
    }

    public void setUnwrapLogLevel(Level l) {
        this.unwrapLogLevel = l;
    }

    public void setIgnoreUnknownBindings(boolean b) {
        this.ignoreUnknownBindings = b;
    }

    public void setAllowElementRefs(boolean b) {
        this.allowRefs = b;
    }

    private void copyExtensors(AbstractPropertiesHolder info, List<?> extList) {
        if (info != null) {
            for (ExtensibilityElement ext : CastUtils.cast(extList, ExtensibilityElement.class)) {
                Object o = ext;
                if (ext instanceof JAXBExtensibilityElement) {
                    o = ((JAXBExtensibilityElement)ext).getValue();
                }
                if (info.containsExtensor(o)) continue;
                info.addExtensor(o);
            }
        }
    }

    private void copyExtensionAttributes(AbstractPropertiesHolder info, AttributeExtensible ae) {
        Map<QName, Object> attrs = CastUtils.cast(ae.getExtensionAttributes());
        if (!attrs.isEmpty()) {
            info.setExtensionAttributes(attrs);
        }
    }

    private void copyDocumentation(AbstractPropertiesHolder info, WSDLElement el) {
        if (el.getDocumentationElement() != null) {
            String doc = DOMUtils.getRawContent(el.getDocumentationElement());
            info.setDocumentation(doc);
        }
    }

    public List<ServiceInfo> buildServices(Definition d) {
        DescriptionInfo description = new DescriptionInfo();
        if (this.recordOriginal) {
            description.setProperty(WSDL_DEFINITION, d);
        }
        description.setName(d.getQName());
        description.setBaseURI(d.getDocumentBaseURI());
        this.copyExtensors(description, d.getExtensibilityElements());
        this.copyExtensionAttributes(description, d);
        ArrayList<ServiceInfo> serviceList = new ArrayList<ServiceInfo>();
        Iterator ite = CastUtils.cast(d.getServices().keySet().iterator());
        while (ite.hasNext()) {
            QName qn = (QName)ite.next();
            serviceList.addAll(this.buildServices(d, qn, null, description));
        }
        return serviceList;
    }

    public List<ServiceInfo> buildServices(Definition d, QName name) {
        return this.buildServices(d, name, null, null);
    }

    public List<ServiceInfo> buildServices(Definition d, QName name, QName endpointName) {
        return this.buildServices(d, name, endpointName, null);
    }

    private List<ServiceInfo> buildServices(Definition d, QName name, QName endpointName, DescriptionInfo description) {
        Service service2 = d.getService(name);
        if (service2 == null) {
            org.apache.cxf.common.i18n.Message msg = new org.apache.cxf.common.i18n.Message("MISSING_SERVICE", LOG, name);
            throw new WSDLRuntimeException(msg);
        }
        return this.buildServices(d, service2, endpointName, description);
    }

    public List<ServiceInfo> buildServices(Definition def, Service serv) {
        return this.buildServices(def, serv, null, null);
    }

    public List<ServiceInfo> buildServices(Definition def, Service serv, QName endpointName) {
        return this.buildServices(def, serv, endpointName, null);
    }

    public List<ServiceInfo> buildMockServices(Definition d) {
        ArrayList<ServiceInfo> serviceList = new ArrayList<ServiceInfo>();
        ArrayList<Definition> defList = new ArrayList<Definition>();
        defList.add(d);
        this.parseImports(d, defList);
        for (Definition def : defList) {
            block1: for (Map.Entry entry : def.getPortTypes().entrySet()) {
                PortType portType = def.getPortType((QName)entry.getKey());
                ServiceInfo serviceInfo = this.buildMockService(def, portType);
                serviceList.add(serviceInfo);
                for (Binding b : d.getAllBindings().values()) {
                    if (b.getPortType() != portType) continue;
                    this.buildBinding(serviceInfo, b);
                    continue block1;
                }
            }
            if (!def.getPortTypes().isEmpty()) continue;
            DescriptionInfo description = new DescriptionInfo();
            if (this.recordOriginal) {
                description.setProperty(WSDL_DEFINITION, def);
            }
            description.setName(def.getQName());
            description.setBaseURI(def.getDocumentBaseURI());
            this.copyExtensors(description, def.getExtensibilityElements());
            this.copyExtensionAttributes(description, def);
            ServiceInfo service2 = new ServiceInfo();
            service2.setDescription(description);
            if (this.recordOriginal) {
                service2.setProperty(WSDL_DEFINITION, def);
            }
            this.getSchemas(def, service2);
            service2.setProperty(WSDL_SCHEMA_ELEMENT_LIST, this.schemaList);
            serviceList.add(service2);
        }
        return serviceList;
    }

    public ServiceInfo buildMockService(Definition def, PortType p) {
        DescriptionInfo description = new DescriptionInfo();
        if (this.recordOriginal) {
            description.setProperty(WSDL_DEFINITION, def);
        }
        description.setName(def.getQName());
        description.setBaseURI(def.getDocumentBaseURI());
        this.copyExtensors(description, def.getExtensibilityElements());
        this.copyExtensionAttributes(description, def);
        ServiceInfo service2 = new ServiceInfo();
        service2.setDescription(description);
        if (this.recordOriginal) {
            service2.setProperty(WSDL_DEFINITION, def);
        }
        this.getSchemas(def, service2);
        service2.setProperty(WSDL_SCHEMA_ELEMENT_LIST, this.schemaList);
        this.buildInterface(service2, p);
        return service2;
    }

    private List<ServiceInfo> buildServices(Definition def, Service serv, QName endpointName, DescriptionInfo d) {
        LinkedHashMap<QName, ServiceInfo> services = new LinkedHashMap<QName, ServiceInfo>();
        DescriptionInfo description = d;
        if (null == description) {
            description = new DescriptionInfo();
            if (this.recordOriginal) {
                description.setProperty(WSDL_DEFINITION, def);
            }
            description.setName(def.getQName());
            description.setBaseURI(def.getDocumentBaseURI());
            this.copyExtensors(description, def.getExtensibilityElements());
            this.copyExtensionAttributes(description, def);
            HashSet<Definition> done = new HashSet<Definition>();
            done.add(def);
            Collection<List> values2 = CastUtils.cast(def.getImports().values());
            for (List imports : values2) {
                for (Import imp : imports) {
                    if (done.contains(imp.getDefinition())) continue;
                    done.add(imp.getDefinition());
                    this.copyExtensors(description, imp.getExtensibilityElements());
                    this.copyExtensionAttributes(description, imp);
                    this.copyExtensors(description, imp.getDefinition().getExtensibilityElements());
                    this.copyExtensionAttributes(description, imp.getDefinition());
                }
            }
        }
        for (Port port : CastUtils.cast(serv.getPorts().values(), Port.class)) {
            BindingInfo bi;
            ServiceInfo service2;
            if (endpointName != null && !endpointName.getLocalPart().equals(port.getName())) continue;
            Binding binding = port.getBinding();
            PortType bindingPt = binding.getPortType();
            if (bindingPt == null) {
                org.apache.cxf.common.i18n.Message msg = new org.apache.cxf.common.i18n.Message("BINDING_MISSING_TYPE", LOG, binding.getQName());
                throw new WSDLRuntimeException(msg);
            }
            PortType pt = def.getPortType(bindingPt.getQName());
            if (pt == null) {
                pt = bindingPt;
            }
            if ((service2 = (ServiceInfo)services.get(pt.getQName())) == null) {
                service2 = new ServiceInfo();
                service2.setDescription(description);
                description.getDescribed().add(service2);
                if (this.recordOriginal) {
                    service2.setProperty(WSDL_DEFINITION, def);
                    service2.setProperty(WSDL_SERVICE, serv);
                }
                this.getSchemas(def, service2);
                this.copyDocumentation(service2, serv);
                service2.setProperty(WSDL_SCHEMA_ELEMENT_LIST, this.schemaList);
                service2.setTargetNamespace(def.getTargetNamespace());
                service2.setName(serv.getQName());
                this.copyExtensors(service2, serv.getExtensibilityElements());
                this.copyExtensionAttributes(service2, serv);
                this.buildInterface(service2, pt);
                services.put(pt.getQName(), service2);
            }
            if ((bi = service2.getBinding(binding.getQName())) == null) {
                bi = this.buildBinding(service2, binding);
            }
            this.buildEndpoint(service2, bi, port);
        }
        return new ArrayList<ServiceInfo>(services.values());
    }

    private void getSchemas(Definition def, ServiceInfo serviceInfo) {
        ServiceSchemaInfo serviceSchemaInfo = null;
        WSDLManager wsdlManager = this.bus.getExtension(WSDLManager.class);
        if (wsdlManager != null) {
            serviceSchemaInfo = wsdlManager.getSchemasForDefinition(def);
        }
        if (serviceSchemaInfo == null) {
            SchemaUtil schemaUtil = new SchemaUtil(this.bus, this.schemaList);
            schemaUtil.getSchemas(def, serviceInfo);
            serviceSchemaInfo = new ServiceSchemaInfo();
            serviceSchemaInfo.setSchemaElementList(this.schemaList);
            serviceSchemaInfo.setSchemaCollection(serviceInfo.getXmlSchemaCollection());
            serviceSchemaInfo.setSchemaInfoList(serviceInfo.getSchemas());
            if (wsdlManager != null) {
                wsdlManager.putSchemasForDefinition(def, serviceSchemaInfo);
            }
        } else {
            serviceInfo.setServiceSchemaInfo(serviceSchemaInfo);
            this.schemaList.putAll(serviceSchemaInfo.getSchemaElementList());
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

    public EndpointInfo buildEndpoint(ServiceInfo service2, BindingInfo bi, Port port) {
        List elements = port.getExtensibilityElements();
        String ns = null;
        DestinationFactory factory2 = null;
        EndpointInfo ei = null;
        if (null != elements && !elements.isEmpty()) {
            for (ExtensibilityElement el : CastUtils.cast(elements, ExtensibilityElement.class)) {
                ns = el.getElementType().getNamespaceURI();
                try {
                    factory2 = this.bus.getExtension(DestinationFactoryManager.class).getDestinationFactory(ns);
                }
                catch (BusException busException) {
                    // empty catch block
                }
                if (factory2 == null) continue;
                break;
            }
            if (factory2 == null) {
                ns = ((ExtensibilityElement)elements.get(0)).getElementType().getNamespaceURI();
            }
        }
        if (factory2 == null) {
            elements = port.getBinding().getExtensibilityElements();
            if (null != elements && !elements.isEmpty()) {
                for (ExtensibilityElement el : CastUtils.cast(elements, ExtensibilityElement.class)) {
                    if (el instanceof SOAPBinding) {
                        ns = ((SOAPBinding)el).getTransportURI();
                        break;
                    }
                    if (!(el instanceof SOAP12Binding)) continue;
                    ns = ((SOAP12Binding)el).getTransportURI();
                    break;
                }
            }
            if (ns == null) {
                if (this.ignoreUnknownBindings) {
                    return null;
                }
                org.apache.cxf.common.i18n.Message msg = new org.apache.cxf.common.i18n.Message("MISSING_DESTINATION_FACTORY", LOG, port.getName());
                throw new WSDLRuntimeException(msg);
            }
            try {
                factory2 = this.bus.getExtension(DestinationFactoryManager.class).getDestinationFactory(ns);
            }
            catch (BusException msg) {
                // empty catch block
            }
        }
        if (factory2 instanceof WSDLEndpointFactory) {
            WSDLEndpointFactory wFactory = (WSDLEndpointFactory)((Object)factory2);
            ei = wFactory.createEndpointInfo(this.bus, service2, bi, port.getExtensibilityElements());
        }
        if (ei == null) {
            ei = new EndpointInfo(service2, ns);
        }
        this.copyDocumentation(ei, port);
        ei.setName(new QName(service2.getName().getNamespaceURI(), port.getName()));
        ei.setBinding(bi);
        this.copyExtensors(ei, port.getExtensibilityElements());
        this.copyExtensionAttributes(ei, port);
        service2.addEndpoint(ei);
        DescriptionInfo d = service2.getDescription();
        if (null != d) {
            d.getDescribed().add(ei);
        }
        return ei;
    }

    public BindingInfo buildBinding(ServiceInfo service2, Binding binding) {
        BindingInfo bi = null;
        StringBuilder ns = new StringBuilder(100);
        BindingFactory factory2 = WSDLServiceUtils.getBindingFactory(binding, this.bus, ns);
        if (factory2 instanceof WSDLBindingFactory) {
            WSDLBindingFactory wFactory = (WSDLBindingFactory)factory2;
            bi = wFactory.createBindingInfo(service2, binding, ns.toString());
            this.copyExtensors(bi, binding.getExtensibilityElements());
            this.copyExtensionAttributes(bi, binding);
        }
        if (bi == null) {
            boolean onlyExtensors = false;
            if (factory2 instanceof AbstractBindingFactory) {
                bi = ((AbstractBindingFactory)factory2).createBindingInfo(service2, ns.toString(), null);
                onlyExtensors = true;
            } else {
                bi = new BindingInfo(service2, ns.toString());
            }
            bi.setName(binding.getQName());
            this.copyExtensors(bi, binding.getExtensibilityElements());
            this.copyExtensionAttributes(bi, binding);
            for (BindingOperation bop : CastUtils.cast(binding.getBindingOperations(), BindingOperation.class)) {
                if (LOG.isLoggable(Level.FINER)) {
                    LOG.finer("binding operation name is " + bop.getName());
                }
                String inName = null;
                String outName = null;
                if (bop.getBindingInput() != null) {
                    inName = bop.getBindingInput().getName();
                }
                if (bop.getBindingOutput() != null) {
                    outName = bop.getBindingOutput().getName();
                }
                BindingOperationInfo bop2 = null;
                if (onlyExtensors) {
                    bop2 = bi.getOperation(new QName(binding.getQName().getNamespaceURI(), bop.getName()));
                } else {
                    bop2 = bi.buildOperation(new QName(binding.getQName().getNamespaceURI(), bop.getName()), inName, outName);
                    if (bop2 != null) {
                        bi.addOperation(bop2);
                    }
                }
                if (bop2 == null) continue;
                this.copyExtensors(bop2, bop.getExtensibilityElements());
                this.copyExtensionAttributes(bop2, bop);
                if (bop.getBindingInput() != null) {
                    this.copyExtensors(bop2.getInput(), bop.getBindingInput().getExtensibilityElements());
                    this.copyExtensionAttributes(bop2.getInput(), bop.getBindingInput());
                    this.handleHeader(bop2.getInput());
                }
                if (bop.getBindingOutput() != null) {
                    this.copyExtensors(bop2.getOutput(), bop.getBindingOutput().getExtensibilityElements());
                    this.copyExtensionAttributes(bop2.getOutput(), bop.getBindingOutput());
                    this.handleHeader(bop2.getOutput());
                }
                for (BindingFault f : CastUtils.cast(bop.getBindingFaults().values(), BindingFault.class)) {
                    BindingFaultInfo bif = bop2.getFault(new QName(service2.getTargetNamespace(), f.getName()));
                    this.copyExtensors(bif, bop.getBindingFault(f.getName()).getExtensibilityElements());
                    this.copyExtensionAttributes(bif, bop.getBindingFault(f.getName()));
                }
            }
        }
        service2.addBinding(bi);
        DescriptionInfo d = service2.getDescription();
        if (null != d) {
            d.getDescribed().add(bi);
        }
        return bi;
    }

    private void handleHeader(BindingMessageInfo bindingMessageInfo) {
        List<ExtensibilityElement> extensiblilityElement = bindingMessageInfo.getExtensors(ExtensibilityElement.class);
        if (extensiblilityElement == null) {
            return;
        }
    }

    public void buildInterface(ServiceInfo si, PortType p) {
        InterfaceInfo inf = si.createInterface(p.getQName());
        DescriptionInfo d = si.getDescription();
        if (null != d) {
            d.getDescribed().add(inf);
        }
        this.copyDocumentation(inf, p);
        this.copyExtensors(inf, p.getExtensibilityElements());
        this.copyExtensionAttributes(inf, p);
        if (this.recordOriginal) {
            inf.setProperty(WSDL_PORTTYPE, p);
        }
        for (Operation op : CastUtils.cast(p.getOperations(), Operation.class)) {
            this.buildInterfaceOperation(inf, op);
        }
    }

    private void buildInterfaceOperation(InterfaceInfo inf, Operation op) {
        Output output;
        OperationInfo opInfo = inf.addOperation(new QName(inf.getName().getNamespaceURI(), op.getName()));
        if (this.recordOriginal) {
            opInfo.setProperty(WSDL_OPERATION, op);
        }
        this.copyDocumentation(opInfo, op);
        List<String> porderList = CastUtils.cast(op.getParameterOrdering());
        opInfo.setParameterOrdering(porderList);
        this.copyExtensors(opInfo, op.getExtensibilityElements());
        this.copyExtensionAttributes(opInfo, op);
        Input input = op.getInput();
        if (input != null) {
            if (input.getMessage() == null) {
                throw new WSDLRuntimeException(LOG, "NO_MESSAGE", "input", op.getName(), input.getName());
            }
            MessageInfo minfo = opInfo.createMessage(input.getMessage().getQName(), MessageInfo.Type.INPUT);
            opInfo.setInput(input.getName(), minfo);
            this.buildMessage(minfo, input.getMessage());
            this.copyExtensors(minfo, input.getExtensibilityElements());
            this.copyExtensionAttributes(minfo, input);
        }
        if ((output = op.getOutput()) != null) {
            if (output.getMessage() == null) {
                throw new WSDLRuntimeException(LOG, "NO_MESSAGE", "output", op.getName(), output.getName());
            }
            MessageInfo minfo = opInfo.createMessage(output.getMessage().getQName(), MessageInfo.Type.OUTPUT);
            opInfo.setOutput(output.getName(), minfo);
            this.buildMessage(minfo, output.getMessage());
            this.copyExtensors(minfo, output.getExtensibilityElements());
            this.copyExtensionAttributes(minfo, output);
        }
        Map m4 = op.getFaults();
        for (Map.Entry rawentry : m4.entrySet()) {
            Map.Entry<String, Fault> entry = CastUtils.cast(rawentry, String.class, Fault.class);
            FaultInfo finfo = opInfo.addFault(new QName(inf.getName().getNamespaceURI(), entry.getKey()), entry.getValue().getMessage().getQName());
            this.copyDocumentation(finfo, entry.getValue());
            this.buildMessage(finfo, entry.getValue().getMessage());
            this.copyExtensors(finfo, entry.getValue().getExtensibilityElements());
            this.copyExtensionAttributes(finfo, entry.getValue());
        }
        WSDLServiceBuilder.checkForWrapped(opInfo, this.allowRefs, false, this.unwrapLogLevel);
    }

    public static void checkForWrapped(OperationInfo opInfo, boolean relaxed) {
        WSDLServiceBuilder.checkForWrapped(opInfo, relaxed, relaxed);
    }

    public static void checkForWrapped(OperationInfo opInfo, boolean allowRefs, boolean relaxed) {
        WSDLServiceBuilder.checkForWrapped(opInfo, allowRefs, relaxed, Level.FINE);
    }

    public static void checkForWrapped(OperationInfo opInfo, boolean allowRefs, boolean relaxed, Level logLevel) {
        MessageInfo inputMessage = opInfo.getInput();
        MessageInfo outputMessage = opInfo.getOutput();
        boolean passedRule = true;
        if (inputMessage == null || inputMessage.size() == 0 || inputMessage.size() > 1 && !relaxed) {
            passedRule = false;
        }
        if (outputMessage != null && outputMessage.size() > 1) {
            passedRule = false;
        }
        if (!passedRule) {
            org.apache.cxf.common.i18n.Message message = new org.apache.cxf.common.i18n.Message("WRAPPED_RULE_1", LOG, opInfo.getName());
            LOG.log(logLevel, message.toString());
            return;
        }
        SchemaCollection schemas = opInfo.getInterface().getService().getXmlSchemaCollection();
        XmlSchemaElement inputEl = null;
        XmlSchemaElement outputEl = null;
        MessagePartInfo inputPart = inputMessage.getMessagePartByIndex(0);
        if (!inputPart.isElement()) {
            passedRule = false;
        } else {
            QName inputElementName = inputPart.getElementQName();
            inputEl = schemas.getElementByQName(inputElementName);
            if (inputEl == null) {
                passedRule = false;
            } else if (!opInfo.getName().getLocalPart().equals(inputElementName.getLocalPart())) {
                passedRule = relaxed;
            }
        }
        if (!passedRule) {
            org.apache.cxf.common.i18n.Message message = new org.apache.cxf.common.i18n.Message("WRAPPED_RULE_2", LOG, opInfo.getName());
            LOG.log(logLevel, message.toString());
            return;
        }
        MessagePartInfo outputPart = null;
        if (outputMessage != null && outputMessage.size() == 1 && (outputPart = outputMessage.getMessagePartByIndex(0)) != null) {
            if (!outputPart.isElement() || schemas.getElementByQName(outputPart.getElementQName()) == null) {
                passedRule = false;
            } else {
                outputEl = schemas.getElementByQName(outputPart.getElementQName());
            }
        }
        if (!passedRule) {
            org.apache.cxf.common.i18n.Message message = new org.apache.cxf.common.i18n.Message("WRAPPED_RULE_3", LOG, opInfo.getName());
            LOG.log(logLevel, message.toString());
            return;
        }
        UnwrappedOperationInfo unwrapped = new UnwrappedOperationInfo(opInfo);
        MessageInfo unwrappedInput = new MessageInfo(unwrapped, MessageInfo.Type.INPUT, inputMessage.getName());
        MessageInfo unwrappedOutput = null;
        XmlSchemaComplexType xsct = null;
        if (inputEl.getSchemaType() instanceof XmlSchemaComplexType) {
            xsct = (XmlSchemaComplexType)inputEl.getSchemaType();
            if (WSDLServiceBuilder.hasAttributes(xsct) || inputEl.isNillable() && !relaxed || !WSDLServiceBuilder.isWrappableSequence(xsct, inputEl.getQName().getNamespaceURI(), unwrappedInput, allowRefs)) {
                passedRule = false;
            }
        } else {
            passedRule = false;
        }
        if (!passedRule) {
            org.apache.cxf.common.i18n.Message message = new org.apache.cxf.common.i18n.Message("WRAPPED_RULE_4", LOG, opInfo.getName());
            LOG.log(logLevel, message.toString());
            return;
        }
        if (outputMessage != null) {
            unwrappedOutput = new MessageInfo(unwrapped, MessageInfo.Type.OUTPUT, outputMessage.getName());
            if (outputEl != null && outputEl.getSchemaType() instanceof XmlSchemaComplexType) {
                xsct = (XmlSchemaComplexType)outputEl.getSchemaType();
                if (xsct.isAbstract()) {
                    passedRule = false;
                }
                if (WSDLServiceBuilder.hasAttributes(xsct) || outputEl.isNillable() && !relaxed || !WSDLServiceBuilder.isWrappableSequence(xsct, outputEl.getQName().getNamespaceURI(), unwrappedOutput, allowRefs)) {
                    passedRule = false;
                }
            } else {
                passedRule = false;
            }
        }
        if (!passedRule) {
            org.apache.cxf.common.i18n.Message message = new org.apache.cxf.common.i18n.Message("WRAPPED_RULE_5", LOG, opInfo.getName());
            LOG.log(logLevel, message.toString());
            return;
        }
        opInfo.setUnwrappedOperation(unwrapped);
        ((OperationInfo)unwrapped).setInput(opInfo.getInputName(), unwrappedInput);
        if (outputMessage != null) {
            ((OperationInfo)unwrapped).setOutput(opInfo.getOutputName(), unwrappedOutput);
        }
    }

    private static boolean hasAttributes(XmlSchemaComplexType complexType) {
        return complexType.getAnyAttribute() != null || !complexType.getAttributes().isEmpty();
    }

    private static boolean isWrappableSequence(XmlSchemaComplexType type, String namespaceURI, MessageInfo wrapper, boolean allowRefs) {
        if (!WSDLServiceBuilder.hasAttributes(type)) {
            if (type.getParticle() instanceof XmlSchemaSequence) {
                XmlSchemaSequence seq = (XmlSchemaSequence)type.getParticle();
                return WSDLServiceBuilder.buildMessageParts(seq, namespaceURI, wrapper, allowRefs);
            }
            if (type.getParticle() == null) {
                if (type.getContentModel() == null) {
                    return true;
                }
                if (type.getContentModel().getContent() instanceof XmlSchemaComplexContentExtension) {
                    XmlSchemaComplexContentExtension extension = (XmlSchemaComplexContentExtension)type.getContentModel().getContent();
                    QName baseTypeName = extension.getBaseTypeName();
                    ServiceInfo serviceInfo = wrapper.getOperation().getInterface().getService();
                    XmlSchemaType schemaType = serviceInfo.getXmlSchemaCollection().getTypeByQName(baseTypeName);
                    if (!(schemaType instanceof XmlSchemaComplexType) || !WSDLServiceBuilder.isWrappableSequence((XmlSchemaComplexType)schemaType, namespaceURI, wrapper, allowRefs)) {
                        return false;
                    }
                    if (extension.getParticle() instanceof XmlSchemaSequence) {
                        XmlSchemaSequence seq = (XmlSchemaSequence)extension.getParticle();
                        return WSDLServiceBuilder.buildMessageParts(seq, namespaceURI, wrapper, allowRefs);
                    }
                }
                return true;
            }
        }
        return false;
    }

    private static boolean buildMessageParts(XmlSchemaSequence seq, String namespaceURI, MessageInfo wrapper, boolean allowRefs) {
        List<XmlSchemaSequenceMember> items = seq.getItems();
        boolean ret = true;
        for (XmlSchemaSequenceMember seqItem : items) {
            MessagePartInfo mpi;
            if (!(seqItem instanceof XmlSchemaElement)) {
                return false;
            }
            XmlSchemaElement el = (XmlSchemaElement)seqItem;
            if (el.getSchemaTypeName() != null) {
                mpi = wrapper.addMessagePart(new QName(namespaceURI, el.getName()));
                mpi.setTypeQName(el.getSchemaTypeName());
                mpi.setElement(true);
                mpi.setElementQName(el.getWireName());
                mpi.setConcreteName(el.getWireName());
                mpi.setXmlSchema(el);
                continue;
            }
            if (el.getRef().getTargetQName() != null) {
                mpi = wrapper.addMessagePart(el.getRef().getTargetQName());
                mpi.setTypeQName(el.getRef().getTargetQName());
                mpi.setElementQName(el.getRef().getTargetQName());
                mpi.setElement(true);
                mpi.setXmlSchema(el);
                mpi.setProperty("isRefElement", true);
                if (allowRefs) continue;
                ret = false;
                continue;
            }
            mpi = wrapper.addMessagePart(new QName(namespaceURI, el.getName()));
            mpi.setElementQName(mpi.getName());
            mpi.setConcreteName(el.getWireName());
            mpi.setElement(true);
            mpi.setXmlSchema(el);
        }
        return ret;
    }

    private void buildMessage(AbstractMessageContainer minfo, Message msg) {
        SchemaCollection schemas = minfo.getOperation().getInterface().getService().getXmlSchemaCollection();
        List orderedParam = msg.getOrderedParts(null);
        for (Part part : CastUtils.cast(orderedParam, Part.class)) {
            MessagePartInfo pi = minfo.addMessagePart(new QName(minfo.getName().getNamespaceURI(), part.getName()));
            if (part.getTypeName() != null) {
                pi.setTypeQName(part.getTypeName());
                pi.setElement(false);
                pi.setXmlSchema(schemas.getTypeByQName(part.getTypeName()));
                continue;
            }
            if (part.getElementName() != null) {
                pi.setElementQName(part.getElementName());
                XmlSchemaElement schemaElement = schemas.getElementByQName(part.getElementName());
                if (null == schemaElement) {
                    org.apache.cxf.common.i18n.Message errorMessage = new org.apache.cxf.common.i18n.Message("WSDL4J_BAD_ELEMENT_PART", LOG, part.getName(), part.getElementName());
                    throw new WSDLRuntimeException(errorMessage);
                }
                pi.setElement(true);
                pi.setXmlSchema(schemaElement);
                continue;
            }
            org.apache.cxf.common.i18n.Message errorMessage = new org.apache.cxf.common.i18n.Message("PART_NO_NAME_NO_TYPE", LOG, part.getName());
            throw new WSDLRuntimeException(errorMessage);
        }
    }
}

