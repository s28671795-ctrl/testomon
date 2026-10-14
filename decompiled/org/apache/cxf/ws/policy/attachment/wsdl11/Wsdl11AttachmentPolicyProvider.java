/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.ws.policy.attachment.wsdl11;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.logging.Logger;
import javax.wsdl.Definition;
import javax.wsdl.extensions.ExtensibilityElement;
import javax.wsdl.extensions.UnknownExtensibilityElement;
import javax.xml.namespace.QName;
import org.apache.cxf.Bus;
import org.apache.cxf.common.injection.NoJSR250Annotations;
import org.apache.cxf.common.logging.LogUtils;
import org.apache.cxf.common.util.StringUtils;
import org.apache.cxf.helpers.CastUtils;
import org.apache.cxf.message.Message;
import org.apache.cxf.service.model.AbstractDescriptionElement;
import org.apache.cxf.service.model.BindingFaultInfo;
import org.apache.cxf.service.model.BindingMessageInfo;
import org.apache.cxf.service.model.BindingOperationInfo;
import org.apache.cxf.service.model.DescriptionInfo;
import org.apache.cxf.service.model.EndpointInfo;
import org.apache.cxf.service.model.Extensible;
import org.apache.cxf.service.model.FaultInfo;
import org.apache.cxf.service.model.MessageInfo;
import org.apache.cxf.service.model.ServiceInfo;
import org.apache.cxf.ws.policy.attachment.AbstractPolicyProvider;
import org.apache.cxf.ws.policy.attachment.reference.LocalServiceModelReferenceResolver;
import org.apache.neethi.Constants;
import org.apache.neethi.Policy;
import org.apache.neethi.PolicyReference;

@NoJSR250Annotations
public class Wsdl11AttachmentPolicyProvider
extends AbstractPolicyProvider {
    private static final Logger LOG = LogUtils.getL7dLogger(Wsdl11AttachmentPolicyProvider.class);

    public Wsdl11AttachmentPolicyProvider() {
        this(null);
    }

    public Wsdl11AttachmentPolicyProvider(Bus bus) {
        super(bus);
    }

    @Override
    public Policy getEffectivePolicy(ServiceInfo si, Message m4) {
        return this.getElementPolicy(si);
    }

    private Policy mergePolicies(Policy p1, Policy p2) {
        if (p1 == null) {
            return p2;
        }
        if (p2 == null) {
            return p1;
        }
        return p1.merge(p2);
    }

    @Override
    public Policy getEffectivePolicy(EndpointInfo ei, Message m4) {
        Policy p = this.getElementPolicy(ei);
        p = this.mergePolicies(p, this.getElementPolicy(ei.getBinding()));
        p = this.mergePolicies(p, this.getElementPolicy(ei.getInterface(), true));
        return p;
    }

    @Override
    public Policy getEffectivePolicy(BindingOperationInfo bi, Message m4) {
        DescriptionInfo di = bi.getBinding().getDescription();
        Policy p = this.getElementPolicy(bi, false, di);
        p = this.mergePolicies(p, this.getElementPolicy(bi.getOperationInfo(), false, di));
        return p;
    }

    @Override
    public Policy getEffectivePolicy(BindingMessageInfo bmi, Message m4) {
        ServiceInfo si = bmi.getBindingOperation().getBinding().getService();
        DescriptionInfo di = si.getDescription();
        Policy p = this.getElementPolicy(bmi, false, di);
        MessageInfo mi = bmi.getMessageInfo();
        p = this.mergePolicies(p, this.getElementPolicy(mi, true, di));
        Extensible ex = this.getMessageTypeInfo(mi.getName(), di);
        p = this.mergePolicies(p, this.getElementPolicy(ex, false, di));
        return p;
    }

    @Override
    public Policy getEffectivePolicy(BindingFaultInfo bfi, Message m4) {
        ServiceInfo si = bfi.getBindingOperation().getBinding().getService();
        DescriptionInfo di = si.getDescription();
        Policy p = this.getElementPolicy(bfi, false, di);
        FaultInfo fi = bfi.getFaultInfo();
        p = this.mergePolicies(p, this.getElementPolicy(fi, true, di));
        Extensible ex = this.getMessageTypeInfo(fi.getName(), di);
        p = this.mergePolicies(p, this.getElementPolicy(ex, false, di));
        return p;
    }

    Policy getElementPolicy(AbstractDescriptionElement adh) {
        return this.getElementPolicy(adh, false);
    }

    Policy getElementPolicy(AbstractDescriptionElement adh, boolean includeAttributes) {
        if (adh == null) {
            return null;
        }
        return this.getElementPolicy(adh, includeAttributes, adh.getDescription());
    }

    Policy getElementPolicy(Extensible ex, boolean includeAttributes, DescriptionInfo di) {
        if (null == ex || null == di) {
            return null;
        }
        if (di.getProperty("registeredPolicy") == null) {
            List<UnknownExtensibilityElement> diext = di.getExtensors(UnknownExtensibilityElement.class);
            if (diext != null) {
                for (UnknownExtensibilityElement e : diext) {
                    String id;
                    Policy policy;
                    String string = e.getElement().getAttributeNS("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd", "Id");
                    if (!Constants.isPolicyElement(e.getElementType()) || StringUtils.isEmpty(string) || (policy = this.registry.lookup(id = (di.getBaseURI() == null ? Integer.toString(di.hashCode()) : di.getBaseURI()) + "#" + string)) != null) continue;
                    try {
                        policy = this.builder.getPolicy(e.getElement());
                        String fragement = "#" + string;
                        this.registry.register(fragement, policy);
                        this.registry.register(id, policy);
                    }
                    catch (Exception policyEx) {
                        LOG.warning("Failed to build the policy '" + string + "':" + policyEx.getMessage());
                    }
                }
            }
            di.setProperty("registeredPolicy", true);
        }
        Policy elementPolicy = null;
        List<UnknownExtensibilityElement> extensions = ex.getExtensors(UnknownExtensibilityElement.class);
        if (null != extensions) {
            for (UnknownExtensibilityElement unknownExtensibilityElement : extensions) {
                PolicyReference ref;
                Policy p = null;
                if (Constants.isPolicyElement(unknownExtensibilityElement.getElementType())) {
                    p = this.builder.getPolicy(unknownExtensibilityElement.getElement());
                } else if (Constants.isPolicyRef(unknownExtensibilityElement.getElementType()) && null != (ref = this.builder.getPolicyReference(unknownExtensibilityElement.getElement()))) {
                    p = this.resolveReference(ref, di);
                }
                if (null == p) continue;
                if (elementPolicy == null) {
                    elementPolicy = new Policy();
                }
                elementPolicy = elementPolicy.merge(p);
            }
        }
        if (includeAttributes && ex.getExtensionAttributes() != null) {
            for (Map.Entry entry : ex.getExtensionAttributes().entrySet()) {
                if (!Constants.isPolicyURIsAttr((QName)entry.getKey())) continue;
                Object attr = entry.getValue();
                String uris = null;
                if (attr instanceof QName) {
                    uris = ((QName)attr).getLocalPart();
                } else if (attr instanceof String) {
                    uris = (String)attr;
                }
                if (null == uris) continue;
                StringTokenizer st = new StringTokenizer(uris);
                while (st.hasMoreTokens()) {
                    String uri = st.nextToken();
                    PolicyReference ref = new PolicyReference();
                    ref.setURI(uri);
                    Policy p = this.resolveReference(ref, di);
                    if (null == p) continue;
                    elementPolicy = elementPolicy == null ? new Policy().merge(p) : elementPolicy.merge(p);
                }
            }
        }
        return elementPolicy;
    }

    Policy resolveReference(PolicyReference ref, DescriptionInfo di) {
        Policy p = null;
        if (this.isExternal(ref)) {
            String uri = di.getBaseURI();
            if (uri == null) {
                uri = Integer.toString(di.hashCode());
            }
            p = this.resolveExternal(ref, uri);
        } else {
            p = this.resolveLocal(ref, di);
        }
        this.checkResolved(ref, p);
        return p;
    }

    Policy resolveLocal(PolicyReference ref, DescriptionInfo di) {
        String uri = ref.getURI().substring(1);
        String absoluteURI = di.getBaseURI();
        absoluteURI = absoluteURI == null ? Integer.toString(di.hashCode()) + ref.getURI() : absoluteURI + ref.getURI();
        Policy resolved = this.registry.lookup(absoluteURI);
        if (null != resolved) {
            return resolved;
        }
        LocalServiceModelReferenceResolver resolver2 = new LocalServiceModelReferenceResolver(di, this.builder);
        resolved = resolver2.resolveReference(uri);
        if (null != resolved) {
            ref.setURI(absoluteURI);
            this.registry.register(absoluteURI, resolved);
        }
        return resolved;
    }

    private Extensible getMessageTypeInfo(QName name, DescriptionInfo di) {
        List<ExtensibilityElement> extensors;
        if (null == di) {
            return null;
        }
        Definition def = (Definition)di.getProperty("org.apache.cxf.wsdl11.WSDLServiceBuilder.DEFINITION");
        if (null == def) {
            return null;
        }
        javax.wsdl.Message m4 = def.getMessage(name);
        if (null != m4 && null != (extensors = CastUtils.cast(m4.getExtensibilityElements(), ExtensibilityElement.class))) {
            return new ExtensibleInfo(extensors);
        }
        return null;
    }

    private static class ExtensibleInfo
    implements Extensible {
        private List<ExtensibilityElement> extensors;

        ExtensibleInfo(List<ExtensibilityElement> e) {
            this.extensors = e;
        }

        @Override
        public <T> T getExtensor(Class<T> cls) {
            for (ExtensibilityElement e : this.extensors) {
                if (!cls.isInstance(e)) continue;
                return cls.cast(e);
            }
            return null;
        }

        @Override
        public <T> List<T> getExtensors(Class<T> cls) {
            if (null == this.extensors) {
                return null;
            }
            ArrayList<T> list = new ArrayList<T>(this.extensors.size());
            for (ExtensibilityElement e : this.extensors) {
                if (!cls.isInstance(e)) continue;
                list.add(cls.cast(e));
            }
            return list;
        }

        @Override
        public void addExtensionAttribute(QName arg0, Object arg1) {
        }

        @Override
        public void addExtensor(Object arg0) {
        }

        @Override
        public Object getExtensionAttribute(QName arg0) {
            return null;
        }

        @Override
        public Map<QName, Object> getExtensionAttributes() {
            return null;
        }

        @Override
        public void setExtensionAttributes(Map<QName, Object> arg0) {
        }
    }
}

