/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.ws.policy;

import java.lang.reflect.Method;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ListIterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.wsdl.extensions.UnknownExtensibilityElement;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import org.apache.cxf.Bus;
import org.apache.cxf.annotations.Policies;
import org.apache.cxf.annotations.Policy;
import org.apache.cxf.common.logging.LogUtils;
import org.apache.cxf.common.util.StringUtils;
import org.apache.cxf.configuration.ConfiguredBeanLocator;
import org.apache.cxf.endpoint.Endpoint;
import org.apache.cxf.helpers.CastUtils;
import org.apache.cxf.helpers.DOMUtils;
import org.apache.cxf.resource.ExtendedURIResolver;
import org.apache.cxf.service.factory.AbstractServiceFactoryBean;
import org.apache.cxf.service.factory.FactoryBeanListener;
import org.apache.cxf.service.model.AbstractPropertiesHolder;
import org.apache.cxf.service.model.BindingFaultInfo;
import org.apache.cxf.service.model.BindingInfo;
import org.apache.cxf.service.model.BindingOperationInfo;
import org.apache.cxf.service.model.DescriptionInfo;
import org.apache.cxf.service.model.FaultInfo;
import org.apache.cxf.service.model.InterfaceInfo;
import org.apache.cxf.service.model.OperationInfo;
import org.apache.cxf.service.model.ServiceInfo;
import org.apache.cxf.staxutils.StaxUtils;
import org.apache.cxf.ws.policy.PolicyBean;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;

public class PolicyAnnotationListener
implements FactoryBeanListener {
    private static final Logger LOG = LogUtils.getL7dLogger(PolicyAnnotationListener.class);
    private static final String EXTRA_POLICIES = PolicyAnnotationListener.class.getName() + ".EXTRA_POLICIES";
    private Bus bus;

    public PolicyAnnotationListener(Bus bus) {
        this.bus = bus;
    }

    @Override
    public void handleEvent(FactoryBeanListener.Event ev, AbstractServiceFactoryBean factory2, Object ... args2) {
        switch (ev) {
            case INTERFACE_CREATED: {
                InterfaceInfo ii = (InterfaceInfo)args2[0];
                Class cls = (Class)args2[1];
                this.addPolicies(factory2, ii, cls);
                break;
            }
            case ENDPOINT_SELECTED: {
                Class cls = (Class)args2[2];
                Class implCls = (Class)args2[3];
                Endpoint ep = (Endpoint)args2[1];
                if (ep.getEndpointInfo().getInterface() == null) break;
                this.addPolicies(factory2, ep, cls);
                this.addEndpointImplPolicies(factory2, ep, implCls);
                break;
            }
            case INTERFACE_OPERATION_BOUND: {
                OperationInfo inf = (OperationInfo)args2[0];
                Method m4 = (Method)args2[1];
                this.addPolicies(factory2, inf, m4);
                break;
            }
            case BINDING_OPERATION_CREATED: {
                BindingOperationInfo boi = (BindingOperationInfo)args2[1];
                Method m5 = (Method)args2[2];
                this.addPolicies(factory2, boi.getOperationInfo(), m5);
                break;
            }
        }
    }

    private void addPolicies(AbstractServiceFactoryBean factory2, OperationInfo inf, Method m4) {
        if (m4 == null) {
            return;
        }
        Policy p = m4.getAnnotation(Policy.class);
        Policies ps = m4.getAnnotation(Policies.class);
        if (p != null || ps != null) {
            ArrayList<Policy> list = new ArrayList<Policy>();
            if (p != null) {
                list.add(p);
            }
            if (ps != null) {
                Collections.addAll(list, ps.value());
            }
            ListIterator it = list.listIterator();
            block6: while (it.hasNext()) {
                p = (Policy)it.next();
                Policy.Placement place = p.placement();
                if (place == Policy.Placement.DEFAULT) {
                    place = Policy.Placement.BINDING_OPERATION;
                }
                ServiceInfo service2 = inf.getInterface().getService();
                Class<?> cls = m4.getDeclaringClass();
                switch (place) {
                    case PORT_TYPE_OPERATION: {
                        this.addPolicy(inf, service2, p, cls, inf.getName().getLocalPart() + "PortTypeOpPolicy");
                        it.remove();
                        break;
                    }
                    case PORT_TYPE_OPERATION_INPUT: {
                        this.addPolicy(inf.getInput(), service2, p, cls, inf.getName().getLocalPart() + "PortTypeOpInputPolicy");
                        it.remove();
                        break;
                    }
                    case PORT_TYPE_OPERATION_OUTPUT: {
                        this.addPolicy(inf.getOutput(), service2, p, cls, inf.getName().getLocalPart() + "PortTypeOpOutputPolicy");
                        it.remove();
                        break;
                    }
                    case PORT_TYPE_OPERATION_FAULT: {
                        for (FaultInfo f : inf.getFaults()) {
                            if (!p.faultClass().equals(f.getProperty(Class.class.getName()))) continue;
                            this.addPolicy(f, service2, p, cls, f.getName().getLocalPart() + "PortTypeOpFaultPolicy");
                            it.remove();
                        }
                        continue block6;
                    }
                }
            }
            if (!list.isEmpty()) {
                List stuff = CastUtils.cast((List)inf.getProperty(EXTRA_POLICIES));
                if (stuff != null) {
                    for (Policy p2 : list) {
                        if (stuff.contains(p2)) continue;
                        stuff.add(p2);
                    }
                } else {
                    inf.setProperty(EXTRA_POLICIES, list);
                }
            }
        }
    }

    private void addPolicies(AbstractServiceFactoryBean factory2, Endpoint ep, Class<?> cls) {
        List<Policy> list = CastUtils.cast((List)ep.getEndpointInfo().getInterface().removeProperty(EXTRA_POLICIES));
        if (list != null) {
            this.addPolicies(factory2, ep, cls, list, Policy.Placement.BINDING);
        }
        ServiceInfo service2 = ep.getService().getServiceInfos().get(0);
        for (BindingOperationInfo binfo : ep.getBinding().getBindingInfo().getOperations()) {
            List<Policy> later = CastUtils.cast((List)binfo.getOperationInfo().removeProperty(EXTRA_POLICIES));
            if (later == null) continue;
            block7: for (Policy p : later) {
                switch (p.placement()) {
                    case DEFAULT: 
                    case BINDING_OPERATION: {
                        this.addPolicy(binfo, service2, p, cls, binfo.getName().getLocalPart() + "BindingOpPolicy");
                        break;
                    }
                    case BINDING_OPERATION_INPUT: {
                        this.addPolicy(binfo.getInput(), service2, p, cls, binfo.getName().getLocalPart() + "BindingOpInputPolicy");
                        break;
                    }
                    case BINDING_OPERATION_OUTPUT: {
                        this.addPolicy(binfo.getOutput(), service2, p, cls, binfo.getName().getLocalPart() + "BindingOpOutputPolicy");
                        break;
                    }
                    case BINDING_OPERATION_FAULT: {
                        for (BindingFaultInfo f : binfo.getFaults()) {
                            if (!p.faultClass().equals(f.getFaultInfo().getProperty(Class.class.getName()))) continue;
                            this.addPolicy(f, service2, p, cls, f.getFaultInfo().getName().getLocalPart() + "BindingOpFaultPolicy");
                        }
                        continue block7;
                    }
                }
            }
        }
    }

    private void addEndpointImplPolicies(AbstractServiceFactoryBean factory2, Endpoint endpoint, Class<?> cls) {
        List<Policy> list = CastUtils.cast((List)endpoint.getEndpointInfo().getInterface().removeProperty(EXTRA_POLICIES));
        if (list != null) {
            this.addPolicies(factory2, endpoint, cls, list, Policy.Placement.BINDING);
        }
        if (cls == null) {
            return;
        }
        Policy p = cls.getAnnotation(Policy.class);
        Policies ps = cls.getAnnotation(Policies.class);
        if (p != null || ps != null) {
            list = new ArrayList<Policy>();
            if (p != null) {
                list.add(p);
            }
            if (ps != null) {
                Collections.addAll(list, ps.value());
            }
            this.addPolicies(factory2, endpoint, cls, list, Policy.Placement.SERVICE);
        }
    }

    private void addPolicies(AbstractServiceFactoryBean factory2, Endpoint endpoint, Class<?> cls, List<Policy> list, Policy.Placement defaultPlace) {
        ListIterator<Policy> it = list.listIterator();
        InterfaceInfo inf = endpoint.getEndpointInfo().getInterface();
        BindingInfo binf = endpoint.getBinding().getBindingInfo();
        ServiceInfo si = endpoint.getService().getServiceInfos().get(0);
        while (it.hasNext()) {
            Policy p = it.next();
            Policy.Placement place = p.placement();
            if (place == Policy.Placement.DEFAULT) {
                place = defaultPlace;
            }
            switch (place) {
                case PORT_TYPE: {
                    this.addPolicy(inf, si, p, cls, inf.getName().getLocalPart() + "PortTypePolicy");
                    it.remove();
                    break;
                }
                case BINDING: {
                    this.addPolicy(binf, si, p, cls, binf.getName().getLocalPart() + "BindingPolicy");
                    it.remove();
                    break;
                }
                case SERVICE: {
                    this.addPolicy(si, si, p, cls, si.getName().getLocalPart() + "ServicePolicy");
                    it.remove();
                    break;
                }
                case SERVICE_PORT: {
                    this.addPolicy(endpoint.getEndpointInfo(), si, p, cls, endpoint.getEndpointInfo().getName().getLocalPart() + "PortPolicy");
                    it.remove();
                    break;
                }
            }
        }
    }

    private void addPolicies(AbstractServiceFactoryBean factory2, InterfaceInfo ii, Class<?> cls) {
        if (cls == null) {
            return;
        }
        Policy p = cls.getAnnotation(Policy.class);
        Policies ps = cls.getAnnotation(Policies.class);
        if (p != null || ps != null) {
            ArrayList<Policy> list = new ArrayList<Policy>();
            if (p != null) {
                list.add(p);
            }
            if (ps != null) {
                Collections.addAll(list, ps.value());
            }
            ListIterator it = list.listIterator();
            while (it.hasNext()) {
                p = (Policy)it.next();
                Policy.Placement place = p.placement();
                if (place == Policy.Placement.DEFAULT) {
                    place = Policy.Placement.BINDING;
                }
                switch (place) {
                    case PORT_TYPE: {
                        this.addPolicy(ii, ii.getService(), p, cls, ii.getName().getLocalPart() + "PortTypePolicy");
                        it.remove();
                        break;
                    }
                    case SERVICE: {
                        this.addPolicy(ii.getService(), ii.getService(), p, cls, ii.getService().getName().getLocalPart() + "ServicePolicy");
                        it.remove();
                        break;
                    }
                }
            }
            if (!list.isEmpty()) {
                List stuff = CastUtils.cast((List)ii.getProperty(EXTRA_POLICIES));
                if (stuff != null) {
                    for (Policy p2 : list) {
                        if (stuff.contains(p2)) continue;
                        stuff.add(p2);
                    }
                } else {
                    ii.setProperty(EXTRA_POLICIES, list);
                }
            }
        }
    }

    private void addPolicy(AbstractPropertiesHolder place, ServiceInfo service2, Policy p, Class<?> cls, String defName) {
        Element el = this.addPolicy(service2, p, cls, defName);
        if (el != null && !this.isExistsPolicyReference(place.getExtensors().get(), this.getPolicyRefURI(el))) {
            UnknownExtensibilityElement uee = new UnknownExtensibilityElement();
            uee.setElement(el);
            uee.setRequired(true);
            uee.setElementType(DOMUtils.getElementQName(el));
            place.addExtensor(uee);
        }
    }

    private Element addPolicy(ServiceInfo service2, Policy p, Class<?> cls, String defName) {
        String uri = p.uri();
        String ns = "http://www.w3.org/ns/ws-policy";
        if (p.includeInWSDL()) {
            Element element = this.loadPolicy(uri, defName);
            if (element == null) {
                return null;
            }
            uri = this.getPolicyId(element);
            ns = element.getNamespaceURI();
            if (service2.getDescription() == null && cls != null) {
                service2.setDescription(new DescriptionInfo());
                URL u = cls.getResource("/");
                if (u != null) {
                    service2.getDescription().setBaseURI(u.toString());
                }
            }
            if (!this.isExistsPolicy(service2.getDescription().getExtensors().get(), uri)) {
                UnknownExtensibilityElement uee = new UnknownExtensibilityElement();
                uee.setElement(element);
                uee.setRequired(true);
                uee.setElementType(DOMUtils.getElementQName(element));
                service2.getDescription().addExtensor(uee);
            }
            uri = "#" + uri;
        }
        Document doc = DOMUtils.getEmptyDocument();
        Element el = doc.createElementNS(ns, "wsp:PolicyReference");
        Attr att = doc.createAttributeNS(null, "URI");
        att.setValue(uri);
        el.setAttributeNodeNS(att);
        return el;
    }

    private String getPolicyId(Element element) {
        return element.getAttributeNS("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd", "Id");
    }

    private String getPolicyRefURI(Element element) {
        return element.getAttributeNS(null, "URI");
    }

    private boolean isExistsPolicy(Object[] exts, String uri) {
        for (Object o : exts = exts == null ? new Object[]{} : exts) {
            UnknownExtensibilityElement uee;
            String uri2;
            if (!(o instanceof UnknownExtensibilityElement) || !uri.equals(uri2 = this.getPolicyId((uee = (UnknownExtensibilityElement)o).getElement()))) continue;
            return true;
        }
        return false;
    }

    private boolean isExistsPolicyReference(Object[] exts, String uri) {
        for (Object o : exts = exts == null ? new Object[]{} : exts) {
            UnknownExtensibilityElement uee;
            String uri2;
            if (!(o instanceof UnknownExtensibilityElement) || !uri.equals(uri2 = this.getPolicyRefURI((uee = (UnknownExtensibilityElement)o).getElement()))) continue;
            return true;
        }
        return false;
    }

    private Element loadPolicy(String uri, String defName) {
        if (!uri.startsWith("#")) {
            return this.loadRemotePolicy(uri, defName);
        }
        return this.loadLocalPolicy(uri);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private Element loadRemotePolicy(String uri, String defName) {
        ExtendedURIResolver resolver2 = new ExtendedURIResolver();
        InputSource src = resolver2.resolve(uri, "classpath:");
        if (null == src) {
            return null;
        }
        XMLStreamReader reader = null;
        try {
            reader = StaxUtils.createXMLStreamReader(src);
            Document doc = StaxUtils.read(reader);
            uri = this.getPolicyId(doc.getDocumentElement());
            if (StringUtils.isEmpty(uri)) {
                Attr att = doc.createAttributeNS("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd", "wsu:Id");
                att.setNodeValue(defName);
                doc.getDocumentElement().setAttributeNodeNS(att);
            }
            Element element = doc.getDocumentElement();
            return element;
        }
        catch (XMLStreamException e) {
            LOG.log(Level.WARNING, e.getMessage());
            Element element = null;
            return element;
        }
        finally {
            try {
                StaxUtils.close(reader);
            }
            catch (XMLStreamException e) {
                LOG.log(Level.WARNING, e.getMessage());
            }
        }
    }

    private Element loadLocalPolicy(String uri) {
        PolicyBean pb = this.bus.getExtension(ConfiguredBeanLocator.class).getBeanOfType(uri.substring(1), PolicyBean.class);
        if (null != pb) {
            return pb.getElement();
        }
        return null;
    }
}

