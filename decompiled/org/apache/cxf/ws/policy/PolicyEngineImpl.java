/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.ws.policy;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.xml.namespace.QName;
import org.apache.cxf.Bus;
import org.apache.cxf.common.injection.NoJSR250Annotations;
import org.apache.cxf.common.logging.LogUtils;
import org.apache.cxf.configuration.ConfiguredBeanLocator;
import org.apache.cxf.extension.BusExtension;
import org.apache.cxf.helpers.CastUtils;
import org.apache.cxf.message.Message;
import org.apache.cxf.service.factory.FactoryBeanListener;
import org.apache.cxf.service.factory.FactoryBeanListenerManager;
import org.apache.cxf.service.model.BindingFaultInfo;
import org.apache.cxf.service.model.BindingMessageInfo;
import org.apache.cxf.service.model.BindingOperationInfo;
import org.apache.cxf.service.model.EndpointInfo;
import org.apache.cxf.service.model.ServiceInfo;
import org.apache.cxf.transport.Conduit;
import org.apache.cxf.transport.Destination;
import org.apache.cxf.ws.policy.AlternativeSelector;
import org.apache.cxf.ws.policy.AssertionBuilderRegistry;
import org.apache.cxf.ws.policy.Assertor;
import org.apache.cxf.ws.policy.ClientPolicyInFaultInterceptor;
import org.apache.cxf.ws.policy.EffectivePolicy;
import org.apache.cxf.ws.policy.EffectivePolicyImpl;
import org.apache.cxf.ws.policy.EndpointPolicy;
import org.apache.cxf.ws.policy.EndpointPolicyImpl;
import org.apache.cxf.ws.policy.PolicyAnnotationListener;
import org.apache.cxf.ws.policy.PolicyEngine;
import org.apache.cxf.ws.policy.PolicyInInterceptor;
import org.apache.cxf.ws.policy.PolicyInterceptorProvider;
import org.apache.cxf.ws.policy.PolicyInterceptorProviderRegistry;
import org.apache.cxf.ws.policy.PolicyOutInterceptor;
import org.apache.cxf.ws.policy.PolicyProvider;
import org.apache.cxf.ws.policy.PolicyRegistryImpl;
import org.apache.cxf.ws.policy.PolicyUtils;
import org.apache.cxf.ws.policy.PolicyVerificationInFaultInterceptor;
import org.apache.cxf.ws.policy.ServerPolicyOutFaultInterceptor;
import org.apache.cxf.ws.policy.selector.MinimalAlternativeSelector;
import org.apache.neethi.Assertion;
import org.apache.neethi.Policy;
import org.apache.neethi.PolicyComponent;
import org.apache.neethi.PolicyOperator;
import org.apache.neethi.PolicyReference;
import org.apache.neethi.PolicyRegistry;

@NoJSR250Annotations(unlessNull={"bus"})
public class PolicyEngineImpl
implements PolicyEngine,
BusExtension {
    private static final Logger LOG = LogUtils.getL7dLogger(PolicyEngineImpl.class);
    private static final String POLICY_INFO_REQUEST_SERVER = "policy-engine-info-serve-request";
    private static final String POLICY_INFO_FAULT_SERVER = "policy-engine-info-serve-fault";
    private static final String POLICY_INFO_RESPONSE_SERVER = "policy-engine-info-serve-response";
    private static final String POLICY_INFO_ENDPOINT_SERVER = "policy-engine-info-serve-rendpoint";
    private static final String POLICY_INFO_REQUEST_CLIENT = "policy-engine-info-client-request";
    private static final String POLICY_INFO_FAULT_CLIENT = "policy-engine-info-client-fault";
    private static final String POLICY_INFO_RESPONSE_CLIENT = "policy-engine-info-client-response";
    private static final String POLICY_INFO_ENDPOINT_CLIENT = "policy-engine-info-client-endpoint";
    private Bus bus;
    private PolicyRegistry registry;
    private Collection<PolicyProvider> policyProviders;
    private Collection<PolicyProvider> preSetPolicyProviders = new LinkedList<PolicyProvider>();
    private Policy busPolicy;
    private boolean enabled = true;
    private Boolean ignoreUnknownAssertions;
    private boolean addedBusInterceptors;
    private AlternativeSelector alternativeSelector;

    public PolicyEngineImpl() {
        this.init();
    }

    public PolicyEngineImpl(boolean en) {
        this.enabled = en;
        this.init();
    }

    public PolicyEngineImpl(Bus b) {
        this.init();
        this.setBus(b);
    }

    @Override
    public boolean isEnabled() {
        return this.enabled;
    }

    @Resource
    public final void setBus(Bus b) {
        if (this.bus == b) {
            return;
        }
        this.bus = b;
        this.addBusInterceptors();
        FactoryBeanListenerManager fblm = this.bus.getExtension(FactoryBeanListenerManager.class);
        if (fblm != null) {
            for (FactoryBeanListener l : fblm.getListeners()) {
                if (!(l instanceof PolicyAnnotationListener)) continue;
                return;
            }
            fblm.addListener(new PolicyAnnotationListener(this.bus));
        }
    }

    public Bus getBus() {
        return this.bus;
    }

    @Override
    public void addPolicy(Policy p) {
        this.busPolicy = this.busPolicy == null ? p : this.busPolicy.merge(p);
    }

    public void setPolicyProviders(Collection<PolicyProvider> p) {
        this.policyProviders = new CopyOnWriteArrayList<PolicyProvider>(p);
    }

    public synchronized void addPolicyProvider(PolicyProvider p) {
        if (this.policyProviders != null) {
            this.policyProviders.add(p);
        } else {
            this.preSetPolicyProviders.add(p);
        }
    }

    public synchronized Collection<PolicyProvider> getPolicyProviders() {
        if (this.policyProviders == null) {
            ConfiguredBeanLocator loc;
            this.policyProviders = new CopyOnWriteArrayList<PolicyProvider>();
            if (this.bus != null && (loc = this.bus.getExtension(ConfiguredBeanLocator.class)) != null) {
                loc.getBeansOfType(PolicyProvider.class);
            }
            this.policyProviders.addAll(this.preSetPolicyProviders);
            this.preSetPolicyProviders = null;
        }
        return this.policyProviders;
    }

    public void setRegistry(PolicyRegistry r) {
        this.registry = r;
    }

    @Override
    public PolicyRegistry getRegistry() {
        return this.registry;
    }

    @Override
    public synchronized void setEnabled(boolean e) {
        this.enabled = e;
        if (this.enabled && !this.addedBusInterceptors) {
            this.addBusInterceptors();
        } else if (!this.enabled && this.addedBusInterceptors) {
            this.removeBusInterceptors();
        }
    }

    @Override
    public synchronized AlternativeSelector getAlternativeSelector() {
        if (this.alternativeSelector == null && this.enabled) {
            this.alternativeSelector = new MinimalAlternativeSelector();
        }
        return this.alternativeSelector;
    }

    @Override
    public void setAlternativeSelector(AlternativeSelector as) {
        this.alternativeSelector = as;
    }

    @Override
    public boolean isIgnoreUnknownAssertions() {
        return this.ignoreUnknownAssertions == null || this.ignoreUnknownAssertions != false;
    }

    @Override
    public void setIgnoreUnknownAssertions(boolean ignore) {
        this.ignoreUnknownAssertions = ignore;
    }

    @Override
    public Class<?> getRegistrationType() {
        return PolicyEngine.class;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public EffectivePolicy getEffectiveClientRequestPolicy(EndpointInfo ei, BindingOperationInfo boi, Conduit c, Message m4) {
        EffectivePolicy effectivePolicy = (EffectivePolicy)boi.getProperty(POLICY_INFO_REQUEST_CLIENT);
        if (effectivePolicy == null) {
            EndpointInfo endpointInfo = ei;
            synchronized (endpointInfo) {
                effectivePolicy = (EffectivePolicy)boi.getProperty(POLICY_INFO_REQUEST_CLIENT);
                if (null == effectivePolicy) {
                    EffectivePolicyImpl epi = this.createOutPolicyInfo();
                    Assertor assertor = PolicyUtils.createAsserter(c);
                    epi.initialise(ei, boi, this, assertor, true, true, m4);
                    if (m4 != null) {
                        boi.setProperty(POLICY_INFO_REQUEST_CLIENT, epi);
                    }
                    effectivePolicy = epi;
                }
            }
        }
        return effectivePolicy;
    }

    @Override
    public void setEffectiveClientRequestPolicy(EndpointInfo ei, BindingOperationInfo boi, EffectivePolicy ep) {
        boi.setProperty(POLICY_INFO_REQUEST_CLIENT, ep);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public EffectivePolicy getEffectiveServerResponsePolicy(EndpointInfo ei, BindingOperationInfo boi, Destination d, List<List<Assertion>> incoming, Message m4) {
        if (incoming == null) {
            EffectivePolicy effectivePolicy = (EffectivePolicy)boi.getProperty(POLICY_INFO_RESPONSE_SERVER);
            if (effectivePolicy == null) {
                EndpointInfo endpointInfo = ei;
                synchronized (endpointInfo) {
                    effectivePolicy = (EffectivePolicy)boi.getProperty(POLICY_INFO_RESPONSE_SERVER);
                    if (null == effectivePolicy) {
                        EffectivePolicyImpl epi = this.createOutPolicyInfo();
                        Assertor assertor = PolicyUtils.createAsserter(d);
                        epi.initialise(ei, boi, this, assertor, false, false, null);
                        if (m4 != null) {
                            boi.setProperty(POLICY_INFO_RESPONSE_SERVER, epi);
                        }
                        effectivePolicy = epi;
                    }
                }
            }
            return effectivePolicy;
        }
        EffectivePolicyImpl epi = this.createOutPolicyInfo();
        Assertor assertor = PolicyUtils.createAsserter(d);
        epi.initialise(ei, boi, (PolicyEngine)this, assertor, incoming, m4);
        return epi;
    }

    @Override
    public void setEffectiveServerResponsePolicy(EndpointInfo ei, BindingOperationInfo boi, EffectivePolicy ep) {
        boi.setProperty(POLICY_INFO_RESPONSE_SERVER, ep);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public EffectivePolicy getEffectiveServerFaultPolicy(EndpointInfo ei, BindingOperationInfo boi, BindingFaultInfo bfi, Destination d, Message m4) {
        if (bfi == null) {
            EffectivePolicyImpl epi = this.createOutPolicyInfo();
            Assertor assertor = PolicyUtils.createAsserter(d);
            epi.initialise(ei, boi, null, this, assertor, m4);
            return epi;
        }
        EffectivePolicy effectivePolicy = (EffectivePolicy)(bfi = this.mapToWrappedBindingFaultInfo(bfi)).getProperty(POLICY_INFO_FAULT_SERVER);
        if (effectivePolicy == null) {
            EndpointInfo endpointInfo = ei;
            synchronized (endpointInfo) {
                effectivePolicy = (EffectivePolicy)bfi.getProperty(POLICY_INFO_FAULT_SERVER);
                if (null == effectivePolicy) {
                    EffectivePolicyImpl epi = this.createOutPolicyInfo();
                    Assertor assertor = PolicyUtils.createAsserter(d);
                    epi.initialise(ei, boi, bfi, this, assertor, m4);
                    if (m4 != null) {
                        bfi.setProperty(POLICY_INFO_FAULT_SERVER, epi);
                    }
                    effectivePolicy = epi;
                }
            }
        }
        return effectivePolicy;
    }

    private BindingFaultInfo mapToWrappedBindingFaultInfo(BindingFaultInfo bfi) {
        BindingOperationInfo boi = bfi.getBindingOperation();
        if (boi != null && boi.isUnwrapped()) {
            boi = boi.getWrappedOperation();
            for (BindingFaultInfo bf2 : boi.getFaults()) {
                if (!bf2.getFaultInfo().getName().equals(bfi.getFaultInfo().getName())) continue;
                return bf2;
            }
        }
        return bfi;
    }

    @Override
    public void setEffectiveServerFaultPolicy(EndpointInfo ei, BindingFaultInfo bfi, EffectivePolicy ep) {
        bfi.setProperty(POLICY_INFO_FAULT_SERVER, ep);
    }

    @Override
    public EndpointPolicy getClientEndpointPolicy(EndpointInfo ei, Conduit conduit, Message m4) {
        Assertor assertor = PolicyUtils.createAsserter(conduit);
        return this.getEndpointPolicy(ei, true, assertor, m4);
    }

    @Override
    public EndpointPolicy getServerEndpointPolicy(EndpointInfo ei, Destination destination, Message m4) {
        Assertor assertor = PolicyUtils.createAsserter(destination);
        return this.getEndpointPolicy(ei, false, assertor, m4);
    }

    private EndpointPolicy getEndpointPolicy(EndpointInfo ei, boolean isRequestor, Assertor assertor, Message m4) {
        return this.createEndpointPolicyInfo(ei, isRequestor, assertor, m4);
    }

    @Override
    public void setClientEndpointPolicy(EndpointInfo ei, EndpointPolicy ep) {
        ei.setProperty(POLICY_INFO_ENDPOINT_CLIENT, ep);
    }

    @Override
    public void setServerEndpointPolicy(EndpointInfo ei, EndpointPolicy ep) {
        ei.setProperty(POLICY_INFO_ENDPOINT_SERVER, ep);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public EffectivePolicy getEffectiveServerRequestPolicy(EndpointInfo ei, BindingOperationInfo boi, Message m4) {
        EffectivePolicy effectivePolicy = (EffectivePolicy)boi.getProperty(POLICY_INFO_REQUEST_SERVER);
        if (effectivePolicy == null) {
            EndpointInfo endpointInfo = ei;
            synchronized (endpointInfo) {
                effectivePolicy = (EffectivePolicy)boi.getProperty(POLICY_INFO_REQUEST_SERVER);
                if (null == effectivePolicy) {
                    EffectivePolicyImpl epi = this.createOutPolicyInfo();
                    epi.initialise(ei, boi, (PolicyEngine)this, false, true, m4);
                    if (m4 != null) {
                        boi.setProperty(POLICY_INFO_REQUEST_SERVER, epi);
                    }
                    effectivePolicy = epi;
                }
            }
        }
        return effectivePolicy;
    }

    @Override
    public void setEffectiveServerRequestPolicy(EndpointInfo ei, BindingOperationInfo boi, EffectivePolicy ep) {
        boi.setProperty(POLICY_INFO_REQUEST_SERVER, ep);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public EffectivePolicy getEffectiveClientResponsePolicy(EndpointInfo ei, BindingOperationInfo boi, Message m4) {
        EffectivePolicy effectivePolicy = (EffectivePolicy)boi.getProperty(POLICY_INFO_RESPONSE_CLIENT);
        if (effectivePolicy == null) {
            EndpointInfo endpointInfo = ei;
            synchronized (endpointInfo) {
                effectivePolicy = (EffectivePolicy)boi.getProperty(POLICY_INFO_RESPONSE_CLIENT);
                if (null == effectivePolicy) {
                    EffectivePolicyImpl epi = this.createOutPolicyInfo();
                    epi.initialise(ei, boi, (PolicyEngine)this, true, false, m4);
                    if (m4 != null) {
                        boi.setProperty(POLICY_INFO_RESPONSE_CLIENT, epi);
                    }
                    effectivePolicy = epi;
                }
            }
        }
        return effectivePolicy;
    }

    @Override
    public void setEffectiveClientResponsePolicy(EndpointInfo ei, BindingOperationInfo boi, EffectivePolicy ep) {
        boi.setProperty(POLICY_INFO_RESPONSE_CLIENT, ep);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public EffectivePolicy getEffectiveClientFaultPolicy(EndpointInfo ei, BindingOperationInfo boi, BindingFaultInfo bfi, Message m4) {
        EffectivePolicy effectivePolicy = null;
        if (bfi != null) {
            effectivePolicy = (EffectivePolicy)bfi.getProperty(POLICY_INFO_FAULT_CLIENT);
        }
        if (effectivePolicy == null) {
            EndpointInfo endpointInfo = ei;
            synchronized (endpointInfo) {
                if (bfi != null) {
                    effectivePolicy = (EffectivePolicy)bfi.getProperty(POLICY_INFO_FAULT_CLIENT);
                }
                if (null == effectivePolicy) {
                    EffectivePolicyImpl epi = this.createOutPolicyInfo();
                    epi.initialisePolicy(ei, boi, bfi, this, m4);
                    if (bfi != null) {
                        bfi.setProperty(POLICY_INFO_FAULT_CLIENT, epi);
                    }
                    effectivePolicy = epi;
                }
            }
        }
        return effectivePolicy;
    }

    @Override
    public void setEffectiveClientFaultPolicy(EndpointInfo ei, BindingFaultInfo bfi, EffectivePolicy ep) {
        bfi.setProperty(POLICY_INFO_FAULT_CLIENT, ep);
    }

    protected final void init() {
        this.registry = new PolicyRegistryImpl();
    }

    public synchronized void removeBusInterceptors() {
        this.bus.getInInterceptors().remove(PolicyInInterceptor.INSTANCE);
        this.bus.getOutInterceptors().remove(PolicyOutInterceptor.INSTANCE);
        this.bus.getInFaultInterceptors().remove(ClientPolicyInFaultInterceptor.INSTANCE);
        this.bus.getOutFaultInterceptors().remove(ServerPolicyOutFaultInterceptor.INSTANCE);
        this.bus.getInFaultInterceptors().remove(PolicyVerificationInFaultInterceptor.INSTANCE);
        this.addedBusInterceptors = false;
    }

    public final synchronized void addBusInterceptors() {
        AssertionBuilderRegistry abr;
        if (null == this.bus || !this.enabled) {
            return;
        }
        if (this.ignoreUnknownAssertions != null && null != (abr = this.bus.getExtension(AssertionBuilderRegistry.class))) {
            abr.setIgnoreUnknownAssertions(this.ignoreUnknownAssertions);
        }
        this.bus.getInInterceptors().add(PolicyInInterceptor.INSTANCE);
        this.bus.getOutInterceptors().add(PolicyOutInterceptor.INSTANCE);
        this.bus.getInFaultInterceptors().add(ClientPolicyInFaultInterceptor.INSTANCE);
        this.bus.getOutFaultInterceptors().add(ServerPolicyOutFaultInterceptor.INSTANCE);
        this.bus.getInFaultInterceptors().add(PolicyVerificationInFaultInterceptor.INSTANCE);
        this.addedBusInterceptors = true;
    }

    Policy getAggregatedServicePolicy(ServiceInfo si, Message m4) {
        if (si == null) {
            return new Policy();
        }
        Policy aggregated = this.busPolicy;
        for (PolicyProvider pp : this.getPolicyProviders()) {
            Policy p = pp.getEffectivePolicy(si, m4);
            if (null == aggregated) {
                aggregated = p;
                continue;
            }
            if (p == null) continue;
            aggregated = aggregated.merge(p);
        }
        return aggregated == null ? new Policy() : aggregated;
    }

    Policy getAggregatedEndpointPolicy(EndpointInfo ei, Message m4) {
        Policy aggregated = null;
        for (PolicyProvider pp : this.getPolicyProviders()) {
            Policy p = pp.getEffectivePolicy(ei, m4);
            if (null == aggregated) {
                aggregated = p;
                continue;
            }
            if (p == null) continue;
            aggregated = aggregated.merge(p);
        }
        return aggregated == null ? new Policy() : aggregated;
    }

    Policy getAggregatedOperationPolicy(BindingOperationInfo boi, Message m4) {
        Policy aggregated = null;
        for (PolicyProvider pp : this.getPolicyProviders()) {
            Policy p = pp.getEffectivePolicy(boi, m4);
            if (null == aggregated) {
                aggregated = p;
                continue;
            }
            if (p == null) continue;
            aggregated = aggregated.merge(p);
        }
        return aggregated == null ? new Policy() : aggregated;
    }

    Policy getAggregatedMessagePolicy(BindingMessageInfo bmi, Message m4) {
        Policy aggregated = null;
        for (PolicyProvider pp : this.getPolicyProviders()) {
            Policy p = pp.getEffectivePolicy(bmi, m4);
            if (null == aggregated) {
                aggregated = p;
                continue;
            }
            if (p == null) continue;
            aggregated = aggregated.merge(p);
        }
        return aggregated == null ? new Policy() : aggregated;
    }

    Policy getAggregatedFaultPolicy(BindingFaultInfo bfi, Message m4) {
        Policy aggregated = null;
        for (PolicyProvider pp : this.getPolicyProviders()) {
            Policy p = pp.getEffectivePolicy(bfi, m4);
            if (null == aggregated) {
                aggregated = p;
                continue;
            }
            if (p == null) continue;
            aggregated = aggregated.merge(p);
        }
        return aggregated == null ? new Policy() : aggregated;
    }

    Collection<Assertion> getAssertions(PolicyComponent pc, boolean includeOptional) {
        ArrayList<Assertion> assertions = new ArrayList<Assertion>();
        if (5 == pc.getType()) {
            Assertion a = (Assertion)pc;
            if (includeOptional || !a.isOptional()) {
                assertions.add(a);
            }
        } else {
            this.addAssertions(pc, includeOptional, assertions);
        }
        return assertions;
    }

    Collection<Assertion> getAssertions(EffectivePolicy pc, boolean includeOptional) {
        if (pc == null || pc.getChosenAlternative() == null) {
            return null;
        }
        ArrayList<Assertion> assertions = new ArrayList<Assertion>();
        for (Assertion assertion : pc.getChosenAlternative()) {
            if (5 == assertion.getType()) {
                if (!includeOptional && assertion.isOptional()) continue;
                assertions.add(assertion);
                continue;
            }
            this.addAssertions(assertion, includeOptional, assertions);
        }
        return assertions;
    }

    void addAssertions(PolicyComponent pc, boolean includeOptional, Collection<Assertion> assertions) {
        if (5 == pc.getType()) {
            Assertion a = (Assertion)pc;
            if (includeOptional || !a.isOptional()) {
                assertions.add((Assertion)pc);
            }
            return;
        }
        if (4 == pc.getType()) {
            PolicyReference pr = (PolicyReference)pc;
            pc = pr.normalize(this.registry, false);
        }
        PolicyOperator po = (PolicyOperator)pc;
        List<PolicyComponent> pcs = CastUtils.cast(po.getPolicyComponents(), PolicyComponent.class);
        for (PolicyComponent child : pcs) {
            this.addAssertions(child, includeOptional, assertions);
        }
    }

    Set<QName> getVocabulary(PolicyComponent pc, boolean includeOptional) {
        Collection<Assertion> assertions = this.getAssertions(pc, includeOptional);
        HashSet<QName> vocabulary = new HashSet<QName>();
        for (Assertion a : assertions) {
            vocabulary.add(a.getName());
        }
        return vocabulary;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    EndpointPolicy createEndpointPolicyInfo(EndpointInfo ei, boolean isRequestor, Assertor assertor, Message m4) {
        EndpointPolicy ep = (EndpointPolicy)ei.getProperty(isRequestor ? POLICY_INFO_ENDPOINT_CLIENT : POLICY_INFO_ENDPOINT_SERVER);
        if (ep == null) {
            EndpointInfo endpointInfo = ei;
            synchronized (endpointInfo) {
                ep = (EndpointPolicy)ei.getProperty(isRequestor ? POLICY_INFO_ENDPOINT_CLIENT : POLICY_INFO_ENDPOINT_SERVER);
                if (ep == null) {
                    EndpointPolicyImpl epi = new EndpointPolicyImpl(ei, this, isRequestor, assertor);
                    epi.initialize(m4);
                    if (m4 != null) {
                        ei.setProperty(isRequestor ? POLICY_INFO_ENDPOINT_CLIENT : POLICY_INFO_ENDPOINT_SERVER, epi);
                    }
                    ep = epi;
                }
            }
        }
        return ep;
    }

    @Override
    public boolean supportsAlternative(Collection<? extends PolicyComponent> alternative, Assertor assertor, Message m4) {
        PolicyInterceptorProviderRegistry pipr = this.bus.getExtension(PolicyInterceptorProviderRegistry.class);
        boolean doLog = LOG.isLoggable(Level.FINE);
        for (PolicyComponent policyComponent : alternative) {
            if (policyComponent instanceof Assertion) {
                Assertion a = (Assertion)policyComponent;
                if (a.isOptional() || null != assertor && assertor.canAssert(a.getName())) continue;
                Set s2 = (Set)pipr.get(a.getName());
                if (s2.isEmpty()) {
                    if (doLog) {
                        LOG.fine("Alternative " + a.getName() + " is not supported");
                    }
                    return false;
                }
                for (PolicyInterceptorProvider p : s2) {
                    if (p.configurationPresent(m4, a)) continue;
                    if (doLog) {
                        LOG.fine("Alternative " + a.getName() + " is not supported");
                    }
                    return false;
                }
                continue;
            }
            return false;
        }
        return true;
    }

    EffectivePolicyImpl createOutPolicyInfo() {
        return new EffectivePolicyImpl();
    }
}

