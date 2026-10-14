/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.jaxws.support;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;
import javax.wsdl.Binding;
import javax.wsdl.extensions.ExtensibilityElement;
import javax.wsdl.extensions.ExtensionRegistry;
import javax.wsdl.extensions.UnknownExtensibilityElement;
import javax.xml.namespace.QName;
import javax.xml.transform.dom.DOMSource;
import javax.xml.ws.RespectBindingFeature;
import javax.xml.ws.Service;
import javax.xml.ws.WebServiceException;
import javax.xml.ws.WebServiceFeature;
import javax.xml.ws.soap.Addressing;
import javax.xml.ws.soap.AddressingFeature;
import javax.xml.ws.soap.MTOMFeature;
import javax.xml.ws.soap.SOAPBinding;
import javax.xml.ws.wsaddressing.W3CEndpointReference;
import org.apache.cxf.Bus;
import org.apache.cxf.binding.soap.SoapBinding;
import org.apache.cxf.binding.soap.saaj.SAAJInInterceptor;
import org.apache.cxf.binding.soap.saaj.SAAJOutInterceptor;
import org.apache.cxf.binding.xml.XMLBinding;
import org.apache.cxf.common.logging.LogUtils;
import org.apache.cxf.endpoint.EndpointException;
import org.apache.cxf.endpoint.EndpointImpl;
import org.apache.cxf.feature.Feature;
import org.apache.cxf.helpers.CastUtils;
import org.apache.cxf.helpers.DOMUtils;
import org.apache.cxf.interceptor.AbstractInDatabindingInterceptor;
import org.apache.cxf.interceptor.Interceptor;
import org.apache.cxf.jaxws.binding.DefaultBindingImpl;
import org.apache.cxf.jaxws.binding.http.HTTPBindingImpl;
import org.apache.cxf.jaxws.binding.soap.SOAPBindingImpl;
import org.apache.cxf.jaxws.handler.logical.LogicalHandlerFaultInInterceptor;
import org.apache.cxf.jaxws.handler.logical.LogicalHandlerFaultOutInterceptor;
import org.apache.cxf.jaxws.handler.logical.LogicalHandlerInInterceptor;
import org.apache.cxf.jaxws.handler.logical.LogicalHandlerOutInterceptor;
import org.apache.cxf.jaxws.handler.soap.SOAPHandlerFaultInInterceptor;
import org.apache.cxf.jaxws.handler.soap.SOAPHandlerFaultOutInterceptor;
import org.apache.cxf.jaxws.handler.soap.SOAPHandlerInterceptor;
import org.apache.cxf.jaxws.interceptors.HolderInInterceptor;
import org.apache.cxf.jaxws.interceptors.HolderOutInterceptor;
import org.apache.cxf.jaxws.interceptors.MessageModeInInterceptor;
import org.apache.cxf.jaxws.interceptors.MessageModeOutInterceptor;
import org.apache.cxf.jaxws.interceptors.SwAInInterceptor;
import org.apache.cxf.jaxws.interceptors.SwAOutInterceptor;
import org.apache.cxf.jaxws.interceptors.WrapperClassInInterceptor;
import org.apache.cxf.jaxws.interceptors.WrapperClassOutInterceptor;
import org.apache.cxf.jaxws.spi.ProviderImpl;
import org.apache.cxf.jaxws.support.JaxWsImplementorInfo;
import org.apache.cxf.message.Message;
import org.apache.cxf.service.Service;
import org.apache.cxf.service.model.BindingFaultInfo;
import org.apache.cxf.service.model.BindingInfo;
import org.apache.cxf.service.model.BindingMessageInfo;
import org.apache.cxf.service.model.BindingOperationInfo;
import org.apache.cxf.service.model.DescriptionInfo;
import org.apache.cxf.service.model.EndpointInfo;
import org.apache.cxf.service.model.Extensible;
import org.apache.cxf.staxutils.W3CDOMStreamWriter;
import org.apache.cxf.ws.addressing.EndpointReferenceType;
import org.apache.cxf.ws.addressing.JAXWSAConstants;
import org.apache.cxf.ws.addressing.WSAddressingFeature;
import org.apache.cxf.wsdl.WSDLManager;
import org.w3c.dom.Element;

public class JaxWsEndpointImpl
extends EndpointImpl {
    private static final long serialVersionUID = 4718088821386100282L;
    private static final String URI_POLICY_NS = "http://www.w3.org/ns/ws-policy";
    private static final String URI_WSU_NS = "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd";
    private static final Logger LOG = LogUtils.getL7dLogger(JaxWsEndpointImpl.class);
    private javax.xml.ws.Binding jaxwsBinding;
    private JaxWsImplementorInfo implInfo;
    private List<WebServiceFeature> wsFeatures;
    private List<Feature> features;
    private SOAPHandlerInterceptor soapHandlerInterceptor;
    private LogicalHandlerInInterceptor logicalInInterceptor;
    private LogicalHandlerOutInterceptor logicalOutInterceptor;
    private LogicalHandlerFaultOutInterceptor logicalFaultOutInterceptor;
    private SOAPHandlerFaultOutInterceptor soapFaultOutInterceptor;
    private LogicalHandlerFaultInInterceptor logicalFaultInInterceptor;
    private SOAPHandlerFaultInInterceptor soapFaultInInterceptor;
    private boolean handlerInterceptorsAdded;

    public JaxWsEndpointImpl(Bus bus, Service s2, EndpointInfo ei) throws EndpointException {
        this(bus, s2, ei, null, null, null, true);
    }

    public JaxWsEndpointImpl(Bus bus, Service s2, EndpointInfo ei, List<WebServiceFeature> wf) throws EndpointException {
        this(bus, s2, ei, null, wf, new ArrayList(), true);
    }

    public JaxWsEndpointImpl(Bus bus, Service s2, EndpointInfo ei, JaxWsImplementorInfo implementorInfo, List<WebServiceFeature> wf, List<? extends Feature> af, boolean isFromWsdl) throws EndpointException {
        super(bus, s2, ei);
        Service.Mode mode;
        this.implInfo = implementorInfo;
        this.wsFeatures = new ArrayList<WebServiceFeature>();
        this.features = af != null ? CastUtils.cast(af) : new ArrayList<Feature>();
        if (wf != null) {
            for (WebServiceFeature f : wf) {
                if (f instanceof Feature) {
                    this.features.add((Feature)((Object)f));
                    continue;
                }
                this.wsFeatures.add(f);
            }
        }
        this.createJaxwsBinding();
        List<Interceptor<? extends Message>> in = super.getInInterceptors();
        List<Interceptor<? extends Message>> out = super.getOutInterceptors();
        boolean isProvider = this.implInfo != null && this.implInfo.isWebServiceProvider();
        Class<?> clazz = this.implInfo != null && isProvider ? this.implInfo.getProviderParameterType() : null;
        Service.Mode mode2 = mode = this.implInfo != null && isProvider ? this.implInfo.getServiceMode() : null;
        if (isProvider) {
            s2.put(AbstractInDatabindingInterceptor.NO_VALIDATE_PARTS, Boolean.TRUE);
        }
        this.logicalInInterceptor = new LogicalHandlerInInterceptor(this.jaxwsBinding);
        if (!isProvider) {
            in.add(new WrapperClassInInterceptor());
            in.add(new HolderInInterceptor());
        }
        if (this.getBinding() instanceof SoapBinding) {
            this.soapHandlerInterceptor = new SOAPHandlerInterceptor(this.jaxwsBinding);
            in.add(new SwAInInterceptor());
            this.getOutInterceptors().add(new SwAOutInterceptor());
            if (isProvider && mode == Service.Mode.MESSAGE) {
                in.add(new SAAJInInterceptor());
            }
        }
        if (isProvider && mode == Service.Mode.MESSAGE) {
            in.add(new MessageModeInInterceptor(clazz, this.getBinding().getBindingInfo().getName()));
        }
        this.logicalOutInterceptor = new LogicalHandlerOutInterceptor(this.jaxwsBinding);
        if (!isProvider) {
            out.add(new WrapperClassOutInterceptor());
            out.add(new HolderOutInterceptor());
        }
        if (this.getBinding() instanceof SoapBinding && mode == Service.Mode.MESSAGE) {
            SAAJOutInterceptor saajOut = new SAAJOutInterceptor();
            out.add(saajOut);
            out.add(new MessageModeOutInterceptor(saajOut, this.getBinding().getBindingInfo().getName()));
        } else if (isProvider) {
            out.add(new MessageModeOutInterceptor(clazz, this.getBinding().getBindingInfo().getName()));
        }
        this.logicalFaultOutInterceptor = new LogicalHandlerFaultOutInterceptor(this.jaxwsBinding);
        this.logicalFaultInInterceptor = new LogicalHandlerFaultInInterceptor(this.jaxwsBinding);
        if (this.getBinding() instanceof SoapBinding) {
            this.soapFaultOutInterceptor = new SOAPHandlerFaultOutInterceptor(this.jaxwsBinding);
            this.soapFaultInInterceptor = new SOAPHandlerFaultInInterceptor(this.jaxwsBinding);
        }
        if (ei != null) {
            if (!isFromWsdl) {
                this.buildWsdlExtensibilities(ei.getBinding());
            }
            this.extractWsdlExtensibilities(ei);
        }
        this.resolveFeatures();
    }

    private void extractWsdlExtensibilities(EndpointInfo endpoint) {
        List<ExtensibilityElement> portExtensors = this.getExtensors(endpoint);
        List<ExtensibilityElement> bindingExtensors = this.getExtensors(endpoint.getBinding());
        this.checkRespectBindingFeature(bindingExtensors);
        Collection<BindingOperationInfo> bindingOperations = endpoint.getBinding().getOperations();
        if (null != bindingOperations) {
            for (BindingOperationInfo operationInfo : bindingOperations) {
                BindingMessageInfo inputInfo = operationInfo.getInput();
                BindingMessageInfo outputnfo = operationInfo.getOutput();
                Collection<BindingFaultInfo> faults = operationInfo.getFaults();
                this.checkRespectBindingFeature(this.getExtensors(operationInfo));
                this.checkRespectBindingFeature(this.getExtensors(inputInfo));
                this.checkRespectBindingFeature(this.getExtensors(outputnfo));
                if (null == faults) continue;
                for (BindingFaultInfo faultInfo : faults) {
                    this.checkRespectBindingFeature(this.getExtensors(faultInfo));
                }
            }
        }
        if (this.hasUsingAddressing(bindingExtensors) || this.hasUsingAddressing(portExtensors)) {
            WSAddressingFeature feature = new WSAddressingFeature();
            if (this.addressingRequired(bindingExtensors) || this.addressingRequired(portExtensors)) {
                feature.setAddressingRequired(true);
            }
            this.addAddressingFeature(feature);
        }
        this.extractWsdlEprs(endpoint);
    }

    private List<ExtensibilityElement> getExtensors(Extensible extensibleInfo) {
        return null != extensibleInfo ? extensibleInfo.getExtensors(ExtensibilityElement.class) : null;
    }

    private void checkRespectBindingFeature(List<ExtensibilityElement> bindingExtensors) {
        if (bindingExtensors != null) {
            for (ExtensibilityElement ext : bindingExtensors) {
                if (!(ext instanceof UnknownExtensibilityElement) || !Boolean.TRUE.equals(ext.getRequired()) || this.wsFeatures == null) continue;
                for (WebServiceFeature feature : this.wsFeatures) {
                    if (!(feature instanceof RespectBindingFeature) || !feature.isEnabled()) continue;
                    org.apache.cxf.common.i18n.Message message = new org.apache.cxf.common.i18n.Message("UNKONWN_REQUIRED_WSDL_BINDING", LOG, new Object[0]);
                    LOG.severe(message.toString());
                    throw new WebServiceException(message.toString());
                }
            }
        }
    }

    private void extractWsdlEprs(EndpointInfo endpoint) {
        List<ExtensibilityElement> portExtensors = endpoint.getExtensors(ExtensibilityElement.class);
        if (portExtensors != null) {
            Iterator<ExtensibilityElement> extensionElements = portExtensors.iterator();
            QName wsaEpr = new QName("http://www.w3.org/2005/08/addressing", "EndpointReference");
            while (extensionElements.hasNext()) {
                ExtensibilityElement ext = extensionElements.next();
                if (!(ext instanceof UnknownExtensibilityElement) || !wsaEpr.equals(ext.getElementType())) continue;
                DOMSource domSource = new DOMSource(((UnknownExtensibilityElement)ext).getElement());
                W3CEndpointReference w3cEPR = new W3CEndpointReference(domSource);
                EndpointReferenceType ref = ProviderImpl.convertToInternal(w3cEPR);
                endpoint.getTarget().setMetadata(ref.getMetadata());
                endpoint.getTarget().setReferenceParameters(ref.getReferenceParameters());
                endpoint.getTarget().getOtherAttributes().putAll(ref.getOtherAttributes());
            }
        }
    }

    private boolean hasUsingAddressing(List<ExtensibilityElement> exts) {
        boolean found = false;
        if (exts != null) {
            Iterator<ExtensibilityElement> extensionElements = exts.iterator();
            while (extensionElements.hasNext() && !found) {
                ExtensibilityElement ext = extensionElements.next();
                found = JAXWSAConstants.WSAW_USINGADDRESSING_QNAME.equals(ext.getElementType());
            }
        }
        return found;
    }

    private boolean addressingRequired(List<ExtensibilityElement> exts) {
        boolean found = false;
        if (exts != null) {
            Iterator<ExtensibilityElement> extensionElements = exts.iterator();
            while (extensionElements.hasNext() && !found) {
                ExtensibilityElement ext = extensionElements.next();
                if (!JAXWSAConstants.WSAW_USINGADDRESSING_QNAME.equals(ext.getElementType()) || ext.getRequired() == null) continue;
                return ext.getRequired();
            }
        }
        return false;
    }

    private void buildWsdlExtensibilities(BindingInfo bindingInfo) {
        Addressing addressing = this.getAddressing();
        if (addressing != null) {
            ExtensionRegistry extensionRegistry = this.getBus().getExtension(WSDLManager.class).getExtensionRegistry();
            try {
                ExtensibilityElement el = extensionRegistry.createExtension(Binding.class, JAXWSAConstants.WSAW_USINGADDRESSING_QNAME);
                el.setRequired(addressing.required());
                bindingInfo.addExtensor(el);
                StringBuilder polRefId = new StringBuilder(bindingInfo.getName().getLocalPart());
                polRefId.append("_WSAM_Addressing_Policy");
                UnknownExtensibilityElement uel = new UnknownExtensibilityElement();
                W3CDOMStreamWriter writer = new W3CDOMStreamWriter();
                writer.writeStartElement("wsp", "PolicyReference", URI_POLICY_NS);
                writer.writeAttribute("URI", "#" + polRefId.toString());
                writer.writeEndElement();
                Element pr = writer.getDocument().getDocumentElement();
                uel.setElement(pr);
                uel.setElementType(DOMUtils.getElementQName(pr));
                bindingInfo.addExtensor(uel);
                writer = new W3CDOMStreamWriter();
                writer.writeStartElement("wsp", "Policy", URI_POLICY_NS);
                writer.writeAttribute("wsu", URI_WSU_NS, "Id", polRefId.toString());
                writer.writeStartElement("wsam", "Addressing", "http://www.w3.org/2007/05/addressing/metadata");
                if (!addressing.required()) {
                    writer.writeAttribute("wsp", URI_POLICY_NS, "Optional", "true");
                }
                writer.writeStartElement("wsp", "Policy", URI_POLICY_NS);
                String s2 = this.getAddressingRequirement(addressing);
                if (s2 != null) {
                    writer.writeEmptyElement("wsam", s2, "http://www.w3.org/2007/05/addressing/metadata");
                }
                writer.writeEndElement();
                writer.writeEndElement();
                writer.writeEndElement();
                pr = writer.getDocument().getDocumentElement();
                uel = new UnknownExtensibilityElement();
                uel.setElement(pr);
                uel.setElementType(DOMUtils.getElementQName(pr));
                if (bindingInfo.getService().getDescription() == null) {
                    DescriptionInfo description = new DescriptionInfo();
                    description.setName(bindingInfo.getService().getName());
                    bindingInfo.getService().setDescription(description);
                }
                bindingInfo.getService().getDescription().addExtensor(uel);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private String getAddressingRequirement(Addressing addressing) {
        try {
            Object o = Addressing.class.getMethod("responses", new Class[0]).invoke((Object)addressing, new Object[0]);
            if (o != null) {
                String s2 = o.toString();
                if ("ANONYMOUS".equals(s2)) {
                    return "AnonymousResponses";
                }
                if ("NON_ANONYMOUS".equals(s2)) {
                    return "NonAnonymousResponses";
                }
            }
        }
        catch (Throwable throwable) {
            // empty catch block
        }
        return null;
    }

    private Addressing getAddressing() {
        Addressing ad;
        Class<?> serviceClass = this.implInfo.getImplementorClass();
        if (serviceClass != null && (ad = serviceClass.getAnnotation(Addressing.class)) != null) {
            return ad;
        }
        serviceClass = this.implInfo.getSEIClass();
        if (serviceClass != null && (ad = serviceClass.getAnnotation(Addressing.class)) != null) {
            return ad;
        }
        return null;
    }

    public javax.xml.ws.Binding getJaxwsBinding() {
        return this.jaxwsBinding;
    }

    private AddressingFeature getAddressingFeature() {
        if (this.wsFeatures == null) {
            return null;
        }
        for (WebServiceFeature feature : this.wsFeatures) {
            if (!(feature instanceof AddressingFeature)) continue;
            return (AddressingFeature)feature;
        }
        return null;
    }

    public final void resolveFeatures() {
        AddressingFeature addressing = this.getAddressingFeature();
        if (addressing == null) {
            return;
        }
        if (addressing.isEnabled()) {
            WSAddressingFeature feature = this.getWSAddressingFeature();
            if (feature == null) {
                feature = new WSAddressingFeature();
                this.addAddressingFeature(feature);
            }
            feature.setAddressingRequired(addressing.isRequired());
            feature.setResponses(addressing.getResponses().toString());
        } else {
            this.removeAddressingFeature();
            this.getEndpointInfo().setProperty("org.apache.cxf.ws.addressing.MAPAggregator.addressingDisabled", Boolean.TRUE);
        }
    }

    public List<Feature> getFeatures() {
        return this.features;
    }

    private WSAddressingFeature getWSAddressingFeature() {
        if (this.features == null) {
            return null;
        }
        for (Feature f : this.features) {
            if (!(f instanceof WSAddressingFeature)) continue;
            return (WSAddressingFeature)f;
        }
        return null;
    }

    private void addAddressingFeature(WSAddressingFeature a) {
        WSAddressingFeature f = this.getWSAddressingFeature();
        if (f == null) {
            this.features.add(a);
        }
    }

    private void removeAddressingFeature() {
        WSAddressingFeature f = this.getWSAddressingFeature();
        if (f != null) {
            this.features.remove(f);
        }
    }

    private MTOMFeature getMTOMFeature() {
        if (this.wsFeatures == null) {
            return null;
        }
        for (WebServiceFeature feature : this.wsFeatures) {
            if (!(feature instanceof MTOMFeature)) continue;
            return (MTOMFeature)feature;
        }
        return null;
    }

    final void createJaxwsBinding() {
        if (this.getBinding() instanceof SoapBinding) {
            this.jaxwsBinding = new SOAPBindingImpl(this.getEndpointInfo().getBinding(), this);
            MTOMFeature mtomFeature = this.getMTOMFeature();
            if (mtomFeature != null && mtomFeature.isEnabled()) {
                ((SOAPBinding)this.jaxwsBinding).setMTOMEnabled(true);
            }
        } else {
            this.jaxwsBinding = this.getBinding() instanceof XMLBinding ? new HTTPBindingImpl(this.getEndpointInfo().getBinding(), this) : new DefaultBindingImpl(this);
        }
    }

    public void addHandlerInterceptors() {
        if (this.handlerInterceptorsAdded) {
            return;
        }
        this.handlerInterceptorsAdded = true;
        List<Interceptor<? extends Message>> in = super.getInInterceptors();
        List<Interceptor<? extends Message>> out = super.getOutInterceptors();
        List<Interceptor<? extends Message>> outFault = super.getOutFaultInterceptors();
        List<Interceptor<? extends Message>> inFault = super.getInFaultInterceptors();
        in.add(this.logicalInInterceptor);
        out.add(this.logicalOutInterceptor);
        inFault.add(this.logicalFaultInInterceptor);
        outFault.add(this.logicalFaultOutInterceptor);
        if (this.soapHandlerInterceptor != null) {
            in.add(this.soapHandlerInterceptor);
            out.add(this.soapHandlerInterceptor);
        }
        if (this.soapFaultInInterceptor != null) {
            inFault.add(this.soapFaultInInterceptor);
        }
        if (this.soapFaultOutInterceptor != null) {
            outFault.add(this.soapFaultOutInterceptor);
        }
    }

    public void removeHandlerInterceptors() {
        if (!this.handlerInterceptorsAdded) {
            return;
        }
        this.handlerInterceptorsAdded = false;
        List<Interceptor<? extends Message>> in = super.getInInterceptors();
        List<Interceptor<? extends Message>> out = super.getOutInterceptors();
        List<Interceptor<? extends Message>> outFault = super.getOutFaultInterceptors();
        List<Interceptor<? extends Message>> inFault = super.getInFaultInterceptors();
        in.remove(this.logicalInInterceptor);
        out.remove(this.logicalOutInterceptor);
        inFault.remove(this.logicalFaultInInterceptor);
        outFault.remove(this.logicalFaultOutInterceptor);
        if (this.soapHandlerInterceptor != null) {
            in.remove(this.soapHandlerInterceptor);
            out.remove(this.soapHandlerInterceptor);
        }
        if (this.soapFaultInInterceptor != null) {
            inFault.remove(this.soapFaultInInterceptor);
        }
        if (this.soapFaultOutInterceptor != null) {
            outFault.remove(this.soapFaultOutInterceptor);
        }
    }
}

