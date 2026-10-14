/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.ws.policy;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.ResourceBundle;
import org.apache.cxf.Bus;
import org.apache.cxf.common.i18n.BundleUtils;
import org.apache.cxf.common.i18n.Message;
import org.apache.cxf.common.injection.NoJSR250Annotations;
import org.apache.cxf.configuration.ConfiguredBeanLocator;
import org.apache.cxf.endpoint.Client;
import org.apache.cxf.endpoint.Endpoint;
import org.apache.cxf.endpoint.Server;
import org.apache.cxf.feature.AbstractFeature;
import org.apache.cxf.message.ExchangeImpl;
import org.apache.cxf.message.MessageImpl;
import org.apache.cxf.service.Service;
import org.apache.cxf.service.model.DescriptionInfo;
import org.apache.cxf.service.model.EndpointInfo;
import org.apache.cxf.ws.policy.AlternativeSelector;
import org.apache.cxf.ws.policy.EndpointPolicy;
import org.apache.cxf.ws.policy.PolicyBean;
import org.apache.cxf.ws.policy.PolicyBuilder;
import org.apache.cxf.ws.policy.PolicyEngine;
import org.apache.cxf.ws.policy.PolicyEngineImpl;
import org.apache.cxf.ws.policy.PolicyException;
import org.apache.cxf.ws.policy.PolicyProvider;
import org.apache.cxf.ws.policy.ServiceModelPolicyUpdater;
import org.apache.cxf.ws.policy.attachment.external.ExternalAttachmentProvider;
import org.apache.cxf.ws.policy.attachment.reference.ReferenceResolver;
import org.apache.cxf.ws.policy.attachment.reference.RemoteReferenceResolver;
import org.apache.neethi.Policy;
import org.apache.neethi.PolicyReference;
import org.apache.neethi.PolicyRegistry;
import org.w3c.dom.Element;

@NoJSR250Annotations
public class WSPolicyFeature
extends AbstractFeature {
    private static final ResourceBundle BUNDLE = BundleUtils.getBundle(WSPolicyFeature.class);
    private Collection<Policy> policies;
    private Collection<Element> policyElements;
    private Collection<Element> policyReferenceElements;
    private boolean ignoreUnknownAssertions;
    private AlternativeSelector alternativeSelector;
    private boolean enabled = true;

    public WSPolicyFeature() {
    }

    public WSPolicyFeature(Policy ... ps) {
        this.policies = new ArrayList<Policy>();
        Collections.addAll(this.policies, ps);
    }

    @Override
    public boolean isEnabled() {
        return this.enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void initialize(Bus bus) {
        this.initializePolicyEngine(bus);
        ArrayList<Policy> loadedPolicies = null;
        if (this.policyElements != null || this.policyReferenceElements != null) {
            loadedPolicies = new ArrayList<Policy>();
            PolicyBuilder builder = bus.getExtension(PolicyBuilder.class);
            if (null != this.policyElements) {
                for (Element e : this.policyElements) {
                    loadedPolicies.add(builder.getPolicy(e));
                }
            }
            if (null != this.policyReferenceElements) {
                for (Element e : this.policyReferenceElements) {
                    PolicyReference pr = builder.getPolicyReference(e);
                    Policy resolved = this.resolveReference(pr, builder, bus, null);
                    if (null == resolved) continue;
                    loadedPolicies.add(resolved);
                }
            }
        }
        Policy thePolicy = new Policy();
        if (this.policies != null) {
            for (Policy p : this.policies) {
                thePolicy = thePolicy.merge(p);
            }
        }
        if (loadedPolicies != null) {
            for (Policy p : loadedPolicies) {
                thePolicy = thePolicy.merge(p);
            }
        }
        if (!thePolicy.isEmpty()) {
            PolicyEngine pe;
            PolicyEngine policyEngine = pe = bus.getExtension(PolicyEngine.class);
            synchronized (policyEngine) {
                pe.addPolicy(thePolicy);
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void initializePolicyEngine(Bus bus) {
        PolicyEngine pe;
        PolicyEngine policyEngine = pe = bus.getExtension(PolicyEngine.class);
        synchronized (policyEngine) {
            pe.setEnabled(this.enabled);
            pe.setIgnoreUnknownAssertions(this.ignoreUnknownAssertions);
            if (null != this.alternativeSelector) {
                pe.setAlternativeSelector(this.alternativeSelector);
            }
        }
    }

    private MessageImpl createMessage(Endpoint e, Bus b) {
        MessageImpl m4 = new MessageImpl();
        ExchangeImpl ex = new ExchangeImpl();
        m4.setExchange(ex);
        ex.put(Endpoint.class, e);
        ex.put(Bus.class, b);
        ex.put(Service.class, e.getService());
        return m4;
    }

    @Override
    public void initialize(Client client, Bus bus) {
        Endpoint endpoint = client.getEndpoint();
        Policy p = this.initializeEndpointPolicy(endpoint, bus);
        PolicyEngine pe = bus.getExtension(PolicyEngine.class);
        EndpointInfo ei = endpoint.getEndpointInfo();
        EndpointPolicy ep = pe.getClientEndpointPolicy(ei, null, null);
        pe.setClientEndpointPolicy(ei, ep.updatePolicy(p, this.createMessage(endpoint, bus)));
    }

    @Override
    public void initialize(Server server, Bus bus) {
        Endpoint endpoint = server.getEndpoint();
        Policy p = this.initializeEndpointPolicy(endpoint, bus);
        PolicyEngine pe = bus.getExtension(PolicyEngine.class);
        EndpointInfo ei = endpoint.getEndpointInfo();
        EndpointPolicy ep = pe.getServerEndpointPolicy(ei, null, null);
        pe.setServerEndpointPolicy(ei, ep.updatePolicy(p, this.createMessage(endpoint, bus)));
        ServiceModelPolicyUpdater pu = new ServiceModelPolicyUpdater(ei);
        for (PolicyProvider pp : ((PolicyEngineImpl)pe).getPolicyProviders()) {
            if (!(pp instanceof ExternalAttachmentProvider)) continue;
            pu.addPolicyAttachments(((ExternalAttachmentProvider)pp).getAttachments());
        }
    }

    private Policy initializeEndpointPolicy(Endpoint endpoint, Bus bus) {
        this.initializePolicyEngine(bus);
        DescriptionInfo i = endpoint.getEndpointInfo().getDescription();
        ArrayList<Policy> loadedPolicies = null;
        if (this.policyElements != null || this.policyReferenceElements != null) {
            loadedPolicies = new ArrayList<Policy>();
            PolicyBuilder builder = bus.getExtension(PolicyBuilder.class);
            if (null != this.policyElements) {
                for (Element e : this.policyElements) {
                    loadedPolicies.add(builder.getPolicy(e));
                }
            }
            if (null != this.policyReferenceElements) {
                for (Element e : this.policyReferenceElements) {
                    PolicyReference pr = builder.getPolicyReference(e);
                    Policy resolved = this.resolveReference(pr, builder, bus, i);
                    if (null == resolved) continue;
                    loadedPolicies.add(resolved);
                }
            }
        }
        Policy thePolicy = new Policy();
        if (this.policies != null) {
            for (Policy p : this.policies) {
                thePolicy = thePolicy.merge(p);
            }
        }
        if (loadedPolicies != null) {
            for (Policy p : loadedPolicies) {
                thePolicy = thePolicy.merge(p);
            }
        }
        return thePolicy;
    }

    public Collection<Policy> getPolicies() {
        if (this.policies == null) {
            this.policies = new ArrayList<Policy>();
        }
        return this.policies;
    }

    public void setPolicies(Collection<Policy> policies) {
        this.policies = policies;
    }

    public Collection<Element> getPolicyElements() {
        if (this.policyElements == null) {
            this.policyElements = new ArrayList<Element>();
        }
        return this.policyElements;
    }

    public void setPolicyElements(Collection<Element> elements) {
        this.policyElements = elements;
    }

    public Collection<Element> getPolicyReferenceElements() {
        if (this.policyReferenceElements == null) {
            this.policyReferenceElements = new ArrayList<Element>();
        }
        return this.policyReferenceElements;
    }

    public void setPolicyReferenceElements(Collection<Element> elements) {
        this.policyReferenceElements = elements;
    }

    public void setIgnoreUnknownAssertions(boolean ignore) {
        this.ignoreUnknownAssertions = ignore;
    }

    public void setAlternativeSelector(AlternativeSelector as) {
        this.alternativeSelector = as;
    }

    Policy resolveReference(PolicyReference ref, PolicyBuilder builder, Bus bus, DescriptionInfo i) {
        Policy p = null;
        if (!ref.getURI().startsWith("#")) {
            String base = i == null ? null : i.getBaseURI();
            p = this.resolveExternal(ref, base, bus);
        } else {
            p = this.resolveLocal(ref, bus, i);
        }
        if (null == p) {
            throw new PolicyException(new Message("UNRESOLVED_POLICY_REFERENCE_EXC", BUNDLE, ref.getURI()));
        }
        return p;
    }

    Policy resolveLocal(PolicyReference ref, final Bus bus, DescriptionInfo i) {
        String uri = ref.getURI().substring(1);
        String absoluteURI = i == null ? uri : i.getBaseURI() + uri;
        PolicyRegistry registry = bus.getExtension(PolicyEngine.class).getRegistry();
        Policy resolved = registry.lookup(absoluteURI);
        if (null != resolved) {
            return resolved;
        }
        ReferenceResolver resolver2 = new ReferenceResolver(){

            @Override
            public Policy resolveReference(String uri) {
                PolicyBean pb = bus.getExtension(ConfiguredBeanLocator.class).getBeanOfType(uri, PolicyBean.class);
                if (null != pb) {
                    PolicyBuilder builder = bus.getExtension(PolicyBuilder.class);
                    return builder.getPolicy(pb.getElement());
                }
                return null;
            }
        };
        resolved = resolver2.resolveReference(uri);
        if (null != resolved) {
            ref.setURI(absoluteURI);
            registry.register(absoluteURI, resolved);
        }
        return resolved;
    }

    protected Policy resolveExternal(PolicyReference ref, String baseURI, Bus bus) {
        PolicyBuilder builder = bus.getExtension(PolicyBuilder.class);
        RemoteReferenceResolver resolver2 = new RemoteReferenceResolver(baseURI, builder);
        PolicyRegistry registry = bus.getExtension(PolicyEngine.class).getRegistry();
        Policy resolved = registry.lookup(ref.getURI());
        if (null != resolved) {
            return resolved;
        }
        return resolver2.resolveReference(ref.getURI());
    }
}

