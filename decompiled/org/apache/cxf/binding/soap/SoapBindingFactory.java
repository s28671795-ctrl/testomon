/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.binding.soap;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import javax.wsdl.BindingInput;
import javax.wsdl.BindingOutput;
import javax.wsdl.Definition;
import javax.wsdl.Import;
import javax.wsdl.Part;
import javax.wsdl.WSDLException;
import javax.wsdl.extensions.ExtensibilityElement;
import javax.wsdl.extensions.ExtensionRegistry;
import javax.wsdl.extensions.mime.MIMEContent;
import javax.wsdl.extensions.mime.MIMEMultipartRelated;
import javax.wsdl.extensions.mime.MIMEPart;
import javax.xml.namespace.QName;
import org.apache.cxf.Bus;
import org.apache.cxf.binding.Binding;
import org.apache.cxf.binding.soap.SOAPBindingUtil;
import org.apache.cxf.binding.soap.Soap12;
import org.apache.cxf.binding.soap.SoapBindingConfiguration;
import org.apache.cxf.binding.soap.SoapMessage;
import org.apache.cxf.binding.soap.SoapVersion;
import org.apache.cxf.binding.soap.interceptor.AbstractSoapInterceptor;
import org.apache.cxf.binding.soap.interceptor.CheckFaultInterceptor;
import org.apache.cxf.binding.soap.interceptor.EndpointSelectionInterceptor;
import org.apache.cxf.binding.soap.interceptor.MustUnderstandInterceptor;
import org.apache.cxf.binding.soap.interceptor.RPCInInterceptor;
import org.apache.cxf.binding.soap.interceptor.RPCOutInterceptor;
import org.apache.cxf.binding.soap.interceptor.ReadHeadersInterceptor;
import org.apache.cxf.binding.soap.interceptor.Soap11FaultInInterceptor;
import org.apache.cxf.binding.soap.interceptor.Soap11FaultOutInterceptor;
import org.apache.cxf.binding.soap.interceptor.Soap12FaultInInterceptor;
import org.apache.cxf.binding.soap.interceptor.Soap12FaultOutInterceptor;
import org.apache.cxf.binding.soap.interceptor.SoapActionInInterceptor;
import org.apache.cxf.binding.soap.interceptor.SoapHeaderInterceptor;
import org.apache.cxf.binding.soap.interceptor.SoapHeaderOutFilterInterceptor;
import org.apache.cxf.binding.soap.interceptor.SoapOutInterceptor;
import org.apache.cxf.binding.soap.interceptor.SoapPreProtocolOutInterceptor;
import org.apache.cxf.binding.soap.interceptor.StartBodyInterceptor;
import org.apache.cxf.binding.soap.jms.interceptor.SoapJMSInInterceptor;
import org.apache.cxf.binding.soap.model.SoapBindingInfo;
import org.apache.cxf.binding.soap.model.SoapBodyInfo;
import org.apache.cxf.binding.soap.model.SoapHeaderInfo;
import org.apache.cxf.binding.soap.model.SoapOperationInfo;
import org.apache.cxf.binding.soap.wsdl.extensions.SoapBinding;
import org.apache.cxf.binding.soap.wsdl.extensions.SoapBody;
import org.apache.cxf.binding.soap.wsdl.extensions.SoapFault;
import org.apache.cxf.binding.soap.wsdl.extensions.SoapHeader;
import org.apache.cxf.binding.soap.wsdl.extensions.SoapOperation;
import org.apache.cxf.common.injection.NoJSR250Annotations;
import org.apache.cxf.common.util.StringUtils;
import org.apache.cxf.common.xmlschema.SchemaCollection;
import org.apache.cxf.endpoint.Endpoint;
import org.apache.cxf.helpers.CastUtils;
import org.apache.cxf.interceptor.AttachmentInInterceptor;
import org.apache.cxf.interceptor.AttachmentOutInterceptor;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.interceptor.InterceptorProvider;
import org.apache.cxf.interceptor.StaxInInterceptor;
import org.apache.cxf.interceptor.StaxOutInterceptor;
import org.apache.cxf.message.Message;
import org.apache.cxf.service.model.BindingFaultInfo;
import org.apache.cxf.service.model.BindingInfo;
import org.apache.cxf.service.model.BindingMessageInfo;
import org.apache.cxf.service.model.BindingOperationInfo;
import org.apache.cxf.service.model.EndpointInfo;
import org.apache.cxf.service.model.MessageInfo;
import org.apache.cxf.service.model.MessagePartInfo;
import org.apache.cxf.service.model.OperationInfo;
import org.apache.cxf.service.model.ServiceInfo;
import org.apache.cxf.transport.ChainInitiationObserver;
import org.apache.cxf.transport.Destination;
import org.apache.cxf.transport.MessageObserver;
import org.apache.cxf.transport.MultipleEndpointObserver;
import org.apache.cxf.ws.addressing.AddressingProperties;
import org.apache.cxf.ws.addressing.ContextUtils;
import org.apache.cxf.ws.addressing.WSAddressingFeature;
import org.apache.cxf.wsdl.WSDLManager;
import org.apache.cxf.wsdl.binding.AbstractWSDLBindingFactory;
import org.apache.cxf.wsdl.interceptors.BareOutInterceptor;
import org.apache.cxf.wsdl.interceptors.DocLiteralInInterceptor;
import org.apache.cxf.wsdl.interceptors.WrappedOutInterceptor;
import org.apache.cxf.wsdl11.WSDLServiceBuilder;

@NoJSR250Annotations(unlessNull={"bus"})
public class SoapBindingFactory
extends AbstractWSDLBindingFactory {
    public static final Collection<String> DEFAULT_NAMESPACES = Collections.unmodifiableList(Arrays.asList("http://schemas.xmlsoap.org/soap/", "http://schemas.xmlsoap.org/wsdl/soap/", "http://schemas.xmlsoap.org/wsdl/soap12/", "http://schemas.xmlsoap.org/wsdl/soap/http", "http://www.w3.org/2003/05/soap/bindings/HTTP/", "http://www.w3.org/2010/soapjms/"));
    public static final String SOAP_11_BINDING = "http://schemas.xmlsoap.org/wsdl/soap/";
    public static final String SOAP_12_BINDING = "http://schemas.xmlsoap.org/wsdl/soap12/";
    public static final String HEADER = "messagepart.isheader";
    public static final String OUT_OF_BAND_HEADER = "messagepart.is_out_of_band_header";

    public SoapBindingFactory() {
    }

    public SoapBindingFactory(Bus b) {
        super(b, DEFAULT_NAMESPACES);
    }

    @Override
    public BindingInfo createBindingInfo(ServiceInfo si, String bindingid, Object conf) {
        SoapBindingConfiguration config = conf instanceof SoapBindingConfiguration ? (SoapBindingConfiguration)conf : new SoapBindingConfiguration();
        if (SOAP_12_BINDING.equals(bindingid) || "http://www.w3.org/2003/05/soap/bindings/HTTP/".equals(bindingid)) {
            config.setVersion(Soap12.getInstance());
            config.setTransportURI("http://schemas.xmlsoap.org/soap/http");
        }
        SoapBindingInfo info = new SoapBindingInfo(si, bindingid, config.getVersion());
        info.setName(config.getBindingName(si));
        info.setStyle(config.getStyle());
        info.setTransportURI(config.getTransportURI());
        if (config.isMtomEnabled()) {
            info.setProperty("mtom-enabled", Boolean.TRUE);
        }
        for (OperationInfo op : si.getInterface().getOperations()) {
            BindingMessageInfo bOutput;
            SoapOperationInfo sop = new SoapOperationInfo();
            sop.setAction(config.getSoapAction(op));
            sop.setStyle(config.getStyle(op));
            BindingOperationInfo bop = info.buildOperation(op.getName(), op.getInputName(), op.getOutputName());
            bop.addExtensor(sop);
            info.addOperation(bop);
            BindingMessageInfo bInput = bop.getInput();
            if (bInput != null) {
                MessageInfo input = null;
                BindingMessageInfo unwrappedMsg = bInput;
                if (bop.isUnwrappedCapable()) {
                    input = bop.getOperationInfo().getUnwrappedOperation().getInput();
                    unwrappedMsg = bop.getUnwrappedOperation().getInput();
                } else {
                    input = bop.getOperationInfo().getInput();
                }
                this.setupHeaders(bop, bInput, unwrappedMsg, input, config);
            }
            if ((bOutput = bop.getOutput()) == null) continue;
            MessageInfo output = null;
            BindingMessageInfo unwrappedMsg = bOutput;
            if (bop.isUnwrappedCapable()) {
                output = bop.getOperationInfo().getUnwrappedOperation().getOutput();
                unwrappedMsg = bop.getUnwrappedOperation().getOutput();
            } else {
                output = bop.getOperationInfo().getOutput();
            }
            this.setupHeaders(bop, bOutput, unwrappedMsg, output, config);
        }
        try {
            this.createSoapBinding(info);
        }
        catch (WSDLException e) {
            e.printStackTrace();
        }
        return info;
    }

    private void createSoapBinding(SoapBindingInfo bi) throws WSDLException {
        boolean isSoap12 = bi.getSoapVersion() instanceof Soap12;
        Bus bs = this.getBus();
        WSDLManager m4 = bs.getExtension(WSDLManager.class);
        ExtensionRegistry extensionRegistry = m4.getExtensionRegistry();
        SoapBinding soapBinding = SOAPBindingUtil.createSoapBinding(extensionRegistry, isSoap12);
        soapBinding.setStyle(bi.getStyle());
        soapBinding.setTransportURI(bi.getTransportURI());
        bi.addExtensor(soapBinding);
        for (BindingOperationInfo b : bi.getOperations()) {
            Iterator<SoapHeaderInfo> body2;
            SoapHeader soapHeader;
            List<SoapHeaderInfo> headerInfos;
            ArrayList<String> bodyParts;
            for (BindingFaultInfo faultInfo : b.getFaults()) {
                SoapFault soapFault = SOAPBindingUtil.createSoapFault(extensionRegistry, isSoap12);
                soapFault.setUse("literal");
                soapFault.setName(faultInfo.getFaultInfo().getFaultName().getLocalPart());
                faultInfo.addExtensor(soapFault);
            }
            SoapOperationInfo soi = b.getExtensor(SoapOperationInfo.class);
            SoapOperation soapOperation = SOAPBindingUtil.createSoapOperation(extensionRegistry, isSoap12);
            soapOperation.setSoapActionURI(soi.getAction());
            soapOperation.setStyle(soi.getStyle());
            boolean isRpc = "rpc".equals(soapOperation.getStyle());
            b.addExtensor(soapOperation);
            if (b.getInput() != null) {
                bodyParts = null;
                headerInfos = b.getInput().getExtensors(SoapHeaderInfo.class);
                if (headerInfos != null && !headerInfos.isEmpty()) {
                    bodyParts = new ArrayList<String>();
                    for (MessagePartInfo part : b.getInput().getMessageParts()) {
                        bodyParts.add(part.getName().getLocalPart());
                    }
                    for (SoapHeaderInfo headerInfo : headerInfos) {
                        soapHeader = SOAPBindingUtil.createSoapHeader(extensionRegistry, BindingInput.class, isSoap12);
                        soapHeader.setMessage(b.getInput().getMessageInfo().getName());
                        soapHeader.setPart(headerInfo.getPart().getName().getLocalPart());
                        soapHeader.setUse("literal");
                        bodyParts.remove(headerInfo.getPart().getName().getLocalPart());
                        headerInfo.getPart().setProperty(HEADER, true);
                        b.getInput().addExtensor(soapHeader);
                    }
                }
                body2 = SOAPBindingUtil.createSoapBody(extensionRegistry, BindingInput.class, isSoap12);
                body2.setUse("literal");
                if (isRpc) {
                    body2.setNamespaceURI(b.getName().getNamespaceURI());
                }
                if (bodyParts != null) {
                    body2.setParts(bodyParts);
                }
                b.getInput().addExtensor(body2);
            }
            if (b.getOutput() == null) continue;
            bodyParts = null;
            headerInfos = b.getOutput().getExtensors(SoapHeaderInfo.class);
            if (headerInfos != null && !headerInfos.isEmpty()) {
                bodyParts = new ArrayList();
                for (MessagePartInfo part : b.getOutput().getMessageParts()) {
                    bodyParts.add(part.getName().getLocalPart());
                }
                for (SoapHeaderInfo headerInfo : headerInfos) {
                    soapHeader = SOAPBindingUtil.createSoapHeader(extensionRegistry, BindingOutput.class, isSoap12);
                    soapHeader.setMessage(b.getOutput().getMessageInfo().getName());
                    soapHeader.setPart(headerInfo.getPart().getName().getLocalPart());
                    soapHeader.setUse("literal");
                    bodyParts.remove(headerInfo.getPart().getName().getLocalPart());
                    b.getOutput().addExtensor(soapHeader);
                }
            }
            body2 = SOAPBindingUtil.createSoapBody(extensionRegistry, BindingOutput.class, isSoap12);
            body2.setUse("literal");
            if (isRpc) {
                body2.setNamespaceURI(b.getName().getNamespaceURI());
            }
            if (bodyParts != null) {
                body2.setParts(bodyParts);
            }
            b.getOutput().addExtensor(body2);
        }
    }

    private void setupHeaders(BindingOperationInfo op, BindingMessageInfo bMsg, BindingMessageInfo unwrappedBMsg, MessageInfo msg, SoapBindingConfiguration config) {
        ArrayList<MessagePartInfo> parts = new ArrayList<MessagePartInfo>();
        for (MessagePartInfo part : msg.getMessageParts()) {
            if (config.isHeader(op, part)) {
                SoapHeaderInfo headerInfo = new SoapHeaderInfo();
                headerInfo.setPart(part);
                headerInfo.setUse(config.getUse());
                bMsg.addExtensor(headerInfo);
                continue;
            }
            parts.add(part);
        }
        unwrappedBMsg.setMessageParts(parts);
    }

    @Override
    public Binding createBinding(BindingInfo binding) {
        String parameterStyle = "wrapped";
        String bindingStyle = "document";
        boolean hasWrapped = false;
        org.apache.cxf.binding.soap.SoapBinding sb = null;
        SoapVersion version = null;
        if (binding instanceof SoapBindingInfo) {
            SoapBindingInfo sbi = (SoapBindingInfo)binding;
            version = sbi.getSoapVersion();
            sb = new org.apache.cxf.binding.soap.SoapBinding(binding, version);
            if (!StringUtils.isEmpty(sbi.getStyle())) {
                bindingStyle = sbi.getStyle();
            }
            boolean hasRPC = false;
            boolean hasDoc = false;
            for (BindingOperationInfo boi : sbi.getOperations()) {
                String st = sbi.getStyle(boi.getOperationInfo());
                if (st != null) {
                    bindingStyle = st;
                    if ("rpc".equalsIgnoreCase(st)) {
                        hasRPC = true;
                    } else {
                        hasDoc = true;
                    }
                }
                if (boi.getUnwrappedOperation() == null) {
                    parameterStyle = "bare";
                    continue;
                }
                hasWrapped = true;
            }
            if (Boolean.TRUE.equals(binding.getService().getProperty("soap.force.doclit.bare"))) {
                hasDoc = true;
                hasRPC = false;
                parameterStyle = "bare";
                bindingStyle = "document";
            }
            if (hasRPC && hasDoc) {
                throw new RuntimeException("WSI-BP prohibits RPC and Document style operations in same service.");
            }
            if (sbi.getTransportURI().equals("http://www.w3.org/2010/soapjms/")) {
                sb.getInInterceptors().add(new SoapJMSInInterceptor());
            }
        } else {
            throw new RuntimeException("Can not initialize SoapBinding, BindingInfo is not SoapBindingInfo");
        }
        sb.getOutFaultInterceptors().add(new StaxOutInterceptor());
        sb.getOutFaultInterceptors().add(new SoapOutInterceptor(this.getBus()));
        sb.getOutFaultInterceptors().add(new AttachmentOutInterceptor());
        sb.getInInterceptors().add(new AttachmentInInterceptor());
        sb.getInInterceptors().add(new StaxInInterceptor());
        sb.getInInterceptors().add(new SoapActionInInterceptor());
        sb.getOutInterceptors().add(new AttachmentOutInterceptor());
        sb.getOutInterceptors().add(new StaxOutInterceptor());
        sb.getOutInterceptors().add(SoapHeaderOutFilterInterceptor.INSTANCE);
        if ("rpc".equalsIgnoreCase(bindingStyle)) {
            sb.getInInterceptors().add(new RPCInInterceptor());
            sb.getOutInterceptors().add(new RPCOutInterceptor());
        } else if ("document".equalsIgnoreCase(bindingStyle) && "bare".equalsIgnoreCase(parameterStyle)) {
            sb.getInInterceptors().add(new DocLiteralInInterceptor());
            if (hasWrapped) {
                sb.getOutInterceptors().add(new WrappedOutInterceptor());
            }
            sb.getOutInterceptors().add(new BareOutInterceptor());
        } else {
            sb.getInInterceptors().add(new DocLiteralInInterceptor());
            sb.getOutInterceptors().add(new WrappedOutInterceptor());
            sb.getOutInterceptors().add(new BareOutInterceptor());
        }
        sb.getInInterceptors().add(new SoapHeaderInterceptor());
        sb.getInInterceptors().add(new ReadHeadersInterceptor(this.getBus(), version));
        sb.getInInterceptors().add(new StartBodyInterceptor());
        sb.getInInterceptors().add(new CheckFaultInterceptor());
        sb.getInInterceptors().add(new MustUnderstandInterceptor());
        sb.getOutInterceptors().add(new SoapPreProtocolOutInterceptor());
        sb.getOutInterceptors().add(new SoapOutInterceptor(this.getBus()));
        sb.getOutFaultInterceptors().add(new SoapOutInterceptor(this.getBus()));
        sb.getOutFaultInterceptors().add(SoapHeaderOutFilterInterceptor.INSTANCE);
        if (version.getVersion() == 1.1) {
            sb.getInFaultInterceptors().add(new Soap11FaultInInterceptor());
            sb.getOutFaultInterceptors().add(new Soap11FaultOutInterceptor());
        } else if (version.getVersion() == 1.2) {
            sb.getInFaultInterceptors().add(new Soap12FaultInInterceptor());
            sb.getOutFaultInterceptors().add(new Soap12FaultOutInterceptor());
        }
        if (binding.getService() != null) {
            for (EndpointInfo ei : binding.getService().getEndpoints()) {
                if (ei.getAddress() == null || !ei.getAddress().startsWith("soap.udp")) continue;
                this.setupUDP(sb, ei);
            }
        }
        return sb;
    }

    protected void setupUDP(InterceptorProvider p, EndpointInfo ei) {
        WSAddressingFeature add = new WSAddressingFeature();
        add.setAddressingRequired(true);
        add.initialize(p, this.bus);
        p.getOutInterceptors().add(new AbstractSoapInterceptor("post-logical"){

            @Override
            public void handleMessage(SoapMessage message) throws Fault {
                AddressingProperties p = ContextUtils.retrieveMAPs(message, false, true);
                if (p == null) {
                    return;
                }
                String ns = p.getNamespaceURI();
                Map<String, String> nsMap = message.getEnvelopeNs();
                nsMap = nsMap == null ? new HashMap<String, String>() : new HashMap<String, String>(nsMap);
                message.put("soap.env.ns.map", (Object)nsMap);
                if (!nsMap.containsValue(ns) && !nsMap.containsKey("wsa")) {
                    nsMap.put("wsa", ns);
                }
                message.put("disable.outputstream.optimization", (Object)Boolean.TRUE);
            }
        });
        ei.setProperty("ws-addressing.write.optional.replyto", Boolean.FALSE);
    }

    @Override
    protected void addMessageFromBinding(ExtensibilityElement ext, BindingOperationInfo bop, boolean isInput) {
        SoapHeader header = SOAPBindingUtil.getSoapHeader(ext);
        ServiceInfo serviceInfo = bop.getBinding().getService();
        if (header != null && header.getMessage() == null) {
            throw new RuntimeException("Problem with WSDL: soap:header element for operation " + bop.getName() + " under binding " + bop.getBinding().getName() + " does not contain a valid message attribute.");
        }
        if (header != null && serviceInfo.getMessage(header.getMessage()) == null) {
            Definition def = (Definition)serviceInfo.getProperty(WSDLServiceBuilder.WSDL_DEFINITION);
            SchemaCollection schemas = serviceInfo.getXmlSchemaCollection();
            if (def != null && schemas != null) {
                QName qn = header.getMessage();
                javax.wsdl.Message msg = this.findMessage(qn, def);
                if (msg != null) {
                    this.addOutOfBandParts(bop, msg, schemas, isInput, header.getPart());
                    serviceInfo.refresh();
                } else {
                    throw new RuntimeException("Problem with WSDL: soap:header element for operation " + bop.getName() + " is referring to an undefined wsdl:message element: " + qn);
                }
            }
        }
    }

    private javax.wsdl.Message findMessage(QName qn, Definition def) {
        javax.wsdl.Message msg = def.getMessage(qn);
        if (msg == null) {
            msg = this.findMessage(qn, def, new ArrayList<Definition>());
        }
        return msg;
    }

    private javax.wsdl.Message findMessage(QName qn, Definition def, List<Definition> done) {
        javax.wsdl.Message msg = def.getMessage(qn);
        if (msg == null) {
            if (done.contains(def)) {
                return null;
            }
            done.add(def);
            Collection<List> ilist = CastUtils.cast(def.getImports().values());
            for (List list : ilist) {
                for (Import i : list) {
                    if (!qn.getNamespaceURI().equals(i.getDefinition().getTargetNamespace())) continue;
                    return i.getDefinition().getMessage(qn);
                }
            }
            for (List list : ilist) {
                for (Import i : list) {
                    msg = this.findMessage(qn, i.getDefinition(), done);
                    if (msg == null) continue;
                    return msg;
                }
            }
        }
        return msg;
    }

    private void addOutOfBandParts(BindingOperationInfo bop, javax.wsdl.Message msg, SchemaCollection schemas, boolean isInput, String partName) {
        MessageInfo.Type type;
        MessageInfo minfo = null;
        int nextId = 0;
        minfo = bop.getOperationInfo().getInput();
        if (minfo != null) {
            for (MessagePartInfo part : minfo.getMessageParts()) {
                if (part.getIndex() < nextId) continue;
                nextId = part.getIndex() + 1;
            }
        }
        if ((minfo = bop.getOperationInfo().getOutput()) != null) {
            for (MessagePartInfo part : minfo.getMessageParts()) {
                if (part.getIndex() < nextId) continue;
                nextId = part.getIndex() + 1;
            }
        }
        if (isInput) {
            type = MessageInfo.Type.INPUT;
            minfo = bop.getOperationInfo().getInput();
        } else {
            type = MessageInfo.Type.OUTPUT;
            minfo = bop.getOperationInfo().getOutput();
        }
        if (minfo == null) {
            minfo = new MessageInfo(bop.getOperationInfo(), type, msg.getQName());
        }
        this.buildMessage(minfo, msg, schemas, nextId, partName);
        OperationInfo unwrapped = bop.getOperationInfo().getUnwrappedOperation();
        if (unwrapped == null) {
            return;
        }
        nextId = 0;
        if (isInput) {
            minfo = unwrapped.getInput();
            type = MessageInfo.Type.INPUT;
            if (minfo != null) {
                for (MessagePartInfo part : minfo.getMessageParts()) {
                    if (part.getIndex() < nextId) continue;
                    nextId = part.getIndex() + 1;
                }
            }
        } else {
            minfo = unwrapped.getOutput();
            type = MessageInfo.Type.OUTPUT;
            if (minfo != null) {
                for (MessagePartInfo part : minfo.getMessageParts()) {
                    if (part.getIndex() < nextId) continue;
                    nextId = part.getIndex() + 1;
                }
            }
        }
        if (minfo == null) {
            minfo = new MessageInfo(unwrapped, type, msg.getQName());
        }
        this.buildMessage(minfo, msg, schemas, nextId, partName);
    }

    private void buildMessage(MessageInfo minfo, javax.wsdl.Message msg, SchemaCollection schemas, int nextId, String partNameFilter) {
        for (Part part : CastUtils.cast(msg.getParts().values(), Part.class)) {
            if (!StringUtils.isEmpty(partNameFilter) && !part.getName().equals(partNameFilter)) continue;
            if (StringUtils.isEmpty(part.getName())) {
                throw new RuntimeException("Problem with WSDL: part element in message " + msg.getQName().getLocalPart() + " does not specify a name.");
            }
            QName pqname = new QName(minfo.getName().getNamespaceURI(), part.getName());
            MessagePartInfo pi = minfo.getMessagePart(pqname);
            if (pi != null && pi.getMessageInfo().getName().equals(msg.getQName())) continue;
            pi = minfo.addOutOfBandMessagePart(pqname);
            if (!minfo.getName().equals(msg.getQName())) {
                pi.setMessageContainer(new MessageInfo(minfo.getOperation(), null, msg.getQName()));
            }
            if (part.getTypeName() != null) {
                pi.setTypeQName(part.getTypeName());
                pi.setElement(false);
                pi.setXmlSchema(schemas.getTypeByQName(part.getTypeName()));
            } else {
                pi.setElementQName(part.getElementName());
                pi.setElement(true);
                pi.setXmlSchema(schemas.getElementByQName(part.getElementName()));
            }
            pi.setProperty(OUT_OF_BAND_HEADER, Boolean.TRUE);
            pi.setProperty(HEADER, Boolean.TRUE);
            pi.setIndex(nextId);
            ++nextId;
        }
    }

    @Override
    public BindingInfo createBindingInfo(ServiceInfo service2, javax.wsdl.Binding binding, String ns) {
        SoapBindingInfo bi = new SoapBindingInfo(service2, ns);
        this.initializeBindingInfo(service2, binding, bi);
        SoapBinding wSoapBinding = SOAPBindingUtil.getSoapBinding(bi.getExtensors(ExtensibilityElement.class));
        bi.setTransportURI(wSoapBinding.getTransportURI());
        bi.setStyle(wSoapBinding.getStyle());
        for (BindingOperationInfo boi : bi.getOperations()) {
            this.initializeBindingOperation(bi, boi);
        }
        return bi;
    }

    private void initializeBindingOperation(SoapBindingInfo bi, BindingOperationInfo boi) {
        SoapOperationInfo soi = new SoapOperationInfo();
        SoapOperation soapOp = SOAPBindingUtil.getSoapOperation(boi.getExtensors(ExtensibilityElement.class));
        if (soapOp != null) {
            String action = soapOp.getSoapActionURI();
            if (action == null) {
                action = "";
            }
            soi.setAction(action);
            soi.setStyle(soapOp.getStyle());
        }
        boi.addExtensor(soi);
        if (boi.getInput() != null) {
            this.initializeMessage(bi, boi, boi.getInput());
        }
        if (boi.getOutput() != null) {
            this.initializeMessage(bi, boi, boi.getOutput());
        }
    }

    private void initializeMessage(SoapBindingInfo bi, BindingOperationInfo boi, BindingMessageInfo bmsg) {
        MessageInfo msg = bmsg.getMessageInfo();
        ArrayList<MessagePartInfo> messageParts = new ArrayList<MessagePartInfo>();
        messageParts.addAll(msg.getMessageParts());
        List<SoapHeader> headers = SOAPBindingUtil.getSoapHeaders(bmsg.getExtensors(ExtensibilityElement.class));
        if (headers != null) {
            for (SoapHeader header : headers) {
                SoapHeaderInfo headerInfo = new SoapHeaderInfo();
                headerInfo.setUse(header.getUse());
                if (StringUtils.isEmpty(header.getPart())) {
                    throw new RuntimeException("Problem with WSDL: soap:header element in operation " + boi.getName().getLocalPart() + " does not specify a part.");
                }
                MessagePartInfo part = msg.getMessagePart(new QName(msg.getName().getNamespaceURI(), header.getPart()));
                if (part != null && header.getMessage() != null && !part.getMessageInfo().getName().equals(header.getMessage())) {
                    part = null;
                    for (MessagePartInfo mpi : msg.getOutOfBandParts()) {
                        if (!mpi.getName().getLocalPart().equals(header.getPart()) || !mpi.getMessageInfo().getName().equals(header.getMessage())) continue;
                        part = mpi;
                    }
                }
                if (part == null) continue;
                headerInfo.setPart(part);
                messageParts.remove(part);
                bmsg.addExtensor(headerInfo);
            }
            bmsg.setMessageParts(messageParts);
        }
        SoapBodyInfo bodyInfo = new SoapBodyInfo();
        SoapBody soapBody = SOAPBindingUtil.getSoapBody(bmsg.getExtensors(ExtensibilityElement.class));
        List parts = null;
        if (soapBody == null) {
            MIMEMultipartRelated mmr = bmsg.getExtensor(MIMEMultipartRelated.class);
            if (mmr != null) {
                parts = mmr.getMIMEParts();
            }
        } else {
            bmsg.addExtensor(soapBody);
            bodyInfo.setUse(soapBody.getUse());
            parts = soapBody.getParts();
        }
        List<MessagePartInfo> attParts = null;
        if (parts != null) {
            ArrayList<MessagePartInfo> bodyParts = new ArrayList<MessagePartInfo>();
            for (Object part : parts) {
                if (part instanceof MIMEPart) {
                    MIMEPart mpart = (MIMEPart)part;
                    attParts = this.handleMimePart(mpart, attParts, msg, bmsg, bodyParts, messageParts);
                    continue;
                }
                this.addSoapBodyPart(msg, bodyParts, (String)part);
            }
            bodyInfo.setParts(bodyParts);
            bodyInfo.setAttachments(attParts);
        } else {
            bodyInfo.setParts(messageParts);
        }
        bmsg.addExtensor(bodyInfo);
    }

    private List<MessagePartInfo> handleMimePart(MIMEPart mpart, List<MessagePartInfo> attParts, MessageInfo msg, BindingMessageInfo bmsg, List<MessagePartInfo> bodyParts, List<MessagePartInfo> messageParts) {
        if (mpart.getExtensibilityElements().size() < 1) {
            throw new RuntimeException("MIMEPart should at least contain one element!");
        }
        String partName = null;
        for (Object content : mpart.getExtensibilityElements()) {
            if (content instanceof MIMEContent) {
                MIMEContent mc = (MIMEContent)content;
                partName = mc.getPart();
                if (attParts == null) {
                    attParts = new LinkedList<MessagePartInfo>();
                }
                if (StringUtils.isEmpty(partName)) {
                    throw new RuntimeException("Problem with WSDL: mime content element in operation " + bmsg.getBindingOperation().getName().getLocalPart() + " does not specify a part.");
                }
                MessagePartInfo mpi = msg.getMessagePart(new QName(msg.getName().getNamespaceURI(), partName));
                mpi.setProperty("Content-Type", mc.getType());
                attParts.add(mpi);
                bmsg.getMessageParts().remove(mpi);
                continue;
            }
            if (SOAPBindingUtil.isSOAPBody(content)) {
                SoapBody sb = SOAPBindingUtil.getSoapBody(content);
                if (sb.getParts() != null && sb.getParts().size() == 1) {
                    partName = (String)sb.getParts().get(0);
                }
                if (partName == null) continue;
                this.addSoapBodyPart(msg, bodyParts, partName);
                continue;
            }
            if (!SOAPBindingUtil.isSOAPHeader(content)) continue;
            SoapHeader header = SOAPBindingUtil.getSoapHeader(content);
            SoapHeaderInfo headerInfo = new SoapHeaderInfo();
            headerInfo.setUse(header.getUse());
            if (StringUtils.isEmpty(header.getPart())) {
                throw new RuntimeException("Problem with WSDL: soap:header element in operation " + bmsg.getBindingOperation().getName().getLocalPart() + " does not specify a part.");
            }
            MessagePartInfo mpi = msg.getMessagePart(new QName(msg.getName().getNamespaceURI(), header.getPart()));
            if (mpi != null && header.getMessage() != null && !mpi.getMessageInfo().getName().equals(header.getMessage())) {
                mpi = null;
                for (MessagePartInfo mpi2 : msg.getOutOfBandParts()) {
                    if (!mpi2.getName().getLocalPart().equals(header.getPart()) || !mpi2.getMessageInfo().getName().equals(header.getMessage())) continue;
                    mpi = mpi2;
                }
            }
            if (mpi == null) continue;
            headerInfo.setPart(mpi);
            messageParts.remove(mpi);
            bmsg.getMessageParts().remove(mpi);
            bmsg.addExtensor(headerInfo);
        }
        return attParts;
    }

    private void addSoapBodyPart(MessageInfo msg, List<MessagePartInfo> bodyParts, String partName) {
        MessagePartInfo mpi = msg.getMessagePart(new QName(msg.getName().getNamespaceURI(), partName));
        bodyParts.add(mpi);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public synchronized void addListener(Destination d, Endpoint e) {
        Destination destination = d;
        synchronized (destination) {
            MessageObserver mo = d.getMessageObserver();
            if (d.getAddress() != null && d.getAddress().getAddress() != null && d.getAddress().getAddress().getValue() != null && d.getAddress().getAddress().getValue().startsWith("soap.udp")) {
                this.setupUDP(e, e.getEndpointInfo());
            }
            if (mo == null) {
                super.addListener(d, e);
                return;
            }
            if (mo instanceof ChainInitiationObserver) {
                Binding b2;
                ChainInitiationObserver cio = (ChainInitiationObserver)mo;
                Binding b = e.getBinding();
                if (b == (b2 = cio.getEndpoint().getBinding())) {
                    return;
                }
                Object o = cio.getEndpoint().get("allow-multiplex-endpoint");
                if (o instanceof String) {
                    o = Boolean.parseBoolean((String)o);
                } else if (o == null) {
                    o = Boolean.FALSE;
                }
                if (b instanceof org.apache.cxf.binding.soap.SoapBinding && b2 instanceof org.apache.cxf.binding.soap.SoapBinding && ((org.apache.cxf.binding.soap.SoapBinding)b).getSoapVersion().equals(((org.apache.cxf.binding.soap.SoapBinding)b2).getSoapVersion()) && Boolean.FALSE.equals(o)) {
                    throw new RuntimeException("Soap " + ((org.apache.cxf.binding.soap.SoapBinding)b).getSoapVersion().getVersion() + " endpoint already registered on address " + e.getEndpointInfo().getAddress());
                }
                MultipleEndpointObserver newMO = new MultipleEndpointObserver(this.getBus()){

                    @Override
                    protected Message createMessage(Message message) {
                        return new SoapMessage(message);
                    }
                };
                newMO.getBindingInterceptors().add(new AttachmentInInterceptor());
                newMO.getBindingInterceptors().add(new StaxInInterceptor());
                newMO.getBindingInterceptors().add(new ReadHeadersInterceptor(this.getBus(), (SoapVersion)null));
                newMO.getBindingInterceptors().add(new StartBodyInterceptor());
                newMO.getBindingInterceptors().add(new CheckFaultInterceptor());
                newMO.getRoutingInterceptors().add(new EndpointSelectionInterceptor());
                newMO.getEndpoints().add(cio.getEndpoint());
                mo = newMO;
            }
            if (mo instanceof MultipleEndpointObserver) {
                MultipleEndpointObserver meo = (MultipleEndpointObserver)mo;
                meo.getEndpoints().add(e);
            }
            d.setMessageObserver(mo);
        }
    }
}

