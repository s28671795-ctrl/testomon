/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.ws.policy;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;
import javax.xml.namespace.QName;
import org.apache.cxf.common.i18n.BundleUtils;
import org.apache.cxf.interceptor.Interceptor;
import org.apache.cxf.message.Message;
import org.apache.cxf.service.model.BindingFaultInfo;
import org.apache.cxf.service.model.BindingOperationInfo;
import org.apache.cxf.service.model.EndpointInfo;
import org.apache.cxf.ws.policy.Assertor;
import org.apache.cxf.ws.policy.EffectivePolicy;
import org.apache.cxf.ws.policy.EndpointPolicy;
import org.apache.cxf.ws.policy.PolicyEngineImpl;
import org.apache.cxf.ws.policy.PolicyException;
import org.apache.cxf.ws.policy.PolicyInterceptorProviderRegistry;
import org.apache.cxf.ws.policy.PolicyUtils;
import org.apache.neethi.Assertion;
import org.apache.neethi.ExactlyOne;
import org.apache.neethi.Policy;
import org.apache.neethi.PolicyComponent;
import org.apache.neethi.PolicyContainingAssertion;

public class EndpointPolicyImpl
implements EndpointPolicy {
    private static final ResourceBundle BUNDLE = BundleUtils.getBundle(EndpointPolicyImpl.class);
    private Policy policy;
    private Collection<Assertion> chosenAlternative;
    private volatile Collection<Assertion> vocabulary;
    private Collection<Assertion> faultVocabulary;
    private volatile List<Interceptor<? extends Message>> interceptors;
    private List<Interceptor<? extends Message>> faultInterceptors;
    private EndpointInfo ei;
    private PolicyEngineImpl engine;
    private boolean requestor;
    private Assertor assertor;

    public EndpointPolicyImpl() {
    }

    public EndpointPolicyImpl(Policy p) {
        this.policy = p;
    }

    public EndpointPolicyImpl(EndpointInfo ei, PolicyEngineImpl engine, boolean requestor, Assertor assertor) {
        this.ei = ei;
        this.engine = engine;
        this.requestor = requestor;
        this.assertor = assertor;
    }

    @Override
    public Policy getPolicy() {
        return this.policy;
    }

    public Assertor getAssertor() {
        return this.assertor;
    }

    @Override
    public EndpointPolicy updatePolicy(Policy p, Message msg) {
        EndpointPolicyImpl epi = this.createEndpointPolicy();
        if (!PolicyUtils.isEmptyPolicy(p)) {
            Policy normalizedPolicy = p.normalize(this.engine == null ? null : this.engine.getRegistry(), true);
            epi.setPolicy(this.getPolicy().merge(normalizedPolicy));
        } else {
            Policy clonedPolicy = new Policy();
            clonedPolicy.addPolicyComponents(this.getPolicy().getPolicyComponents());
            epi.setPolicy(clonedPolicy);
        }
        epi.checkExactlyOnes();
        epi.finalizeConfig(msg);
        return epi;
    }

    @Override
    public Collection<Assertion> getChosenAlternative() {
        return this.chosenAlternative;
    }

    @Override
    public Collection<Assertion> getVocabulary(Message m4) {
        if (this.vocabulary == null) {
            this.initializeVocabulary(m4);
        }
        return this.vocabulary;
    }

    @Override
    public Collection<Assertion> getFaultVocabulary(Message m4) {
        if (this.vocabulary == null) {
            this.initializeVocabulary(m4);
        }
        return this.faultVocabulary;
    }

    @Override
    public List<Interceptor<? extends Message>> getInterceptors(Message m4) {
        if (this.interceptors == null) {
            this.initializeInterceptors(m4);
        }
        return this.interceptors;
    }

    @Override
    public List<Interceptor<? extends Message>> getFaultInterceptors(Message m4) {
        if (this.interceptors == null) {
            this.initializeInterceptors(m4);
        }
        return this.faultInterceptors;
    }

    public void initialize(Message m4) {
        this.initializePolicy(m4);
        this.checkExactlyOnes();
        this.finalizeConfig(m4);
    }

    void finalizeConfig(Message m4) {
        this.chooseAlternative(m4);
    }

    void initializePolicy(Message m4) {
        if (this.engine != null) {
            this.policy = this.engine.getAggregatedServicePolicy(this.ei.getService(), m4);
            this.policy = this.policy.merge(this.engine.getAggregatedEndpointPolicy(this.ei, m4));
            if (!this.policy.isEmpty()) {
                this.policy = this.policy.normalize(this.engine.getRegistry(), true);
            }
        }
    }

    void chooseAlternative(Message m4) {
        Collection<Assertion> alternative = null;
        alternative = this.requestor ? (this.engine.isEnabled() ? this.engine.getAlternativeSelector().selectAlternative(this.policy, this.engine, this.assertor, null, m4) : Collections.emptyList()) : this.getSupportedAlternatives(m4);
        if (null == alternative) {
            throw new PolicyException(new org.apache.cxf.common.i18n.Message("NO_ALTERNATIVE_EXC", BUNDLE, new Object[0]));
        }
        this.setChosenAlternative(alternative);
    }

    protected Collection<Assertion> getSupportedAlternatives(Message msg) {
        ArrayList<Assertion> alternatives = new ArrayList<Assertion>();
        Iterator<List<Assertion>> it = this.policy.getAlternatives();
        while (it.hasNext()) {
            List<Assertion> alternative = it.next();
            if (!this.engine.supportsAlternative(alternative, this.assertor, msg)) continue;
            alternatives.addAll(alternative);
        }
        return alternatives;
    }

    private void addAll(Collection<Assertion> target, Collection<Assertion> l1) {
        for (Assertion l : l1) {
            if (target.contains(l)) continue;
            target.add(l);
        }
    }

    synchronized void initializeVocabulary(Message m4) {
        if (this.vocabulary != null) {
            return;
        }
        ArrayList<Assertion> v = new ArrayList<Assertion>();
        ArrayList<Assertion> fv = null;
        if (this.requestor) {
            fv = new ArrayList<Assertion>();
        }
        if (this.getChosenAlternative() != null) {
            for (Assertion a : this.getChosenAlternative()) {
                if (a.isOptional()) continue;
                v.add(a);
                if (null == fv) continue;
                fv.add(a);
            }
        }
        for (BindingOperationInfo boi : this.ei.getBinding().getOperations()) {
            Collection<Assertion> c;
            EffectivePolicy p = null;
            if (!this.requestor) {
                p = this.engine.getEffectiveServerRequestPolicy(this.ei, boi, m4);
                c = this.engine.getAssertions(p, false);
                if (c == null) continue;
                this.addAll(v, c);
                continue;
            }
            p = this.engine.getEffectiveClientResponsePolicy(this.ei, boi, m4);
            c = this.engine.getAssertions(p, false);
            if (c != null) {
                this.addAll(v, c);
                if (null != fv) {
                    this.addAll(fv, c);
                }
            }
            if (boi.getFaults() == null || null == fv) continue;
            for (BindingFaultInfo bfi : boi.getFaults()) {
                p = this.engine.getEffectiveClientFaultPolicy(this.ei, boi, bfi, m4);
                c = this.engine.getAssertions(p, false);
                if (c == null) continue;
                this.addAll(fv, c);
            }
        }
        if (this.requestor) {
            this.faultVocabulary = fv;
        }
        this.vocabulary = v;
    }

    Collection<Assertion> getSupportedAlternatives(Policy p, Message msg) {
        ArrayList<Assertion> alternatives = new ArrayList<Assertion>();
        Iterator<List<Assertion>> it = p.getAlternatives();
        while (it.hasNext()) {
            List<Assertion> alternative = it.next();
            if (!this.engine.supportsAlternative(alternative, null, msg)) continue;
            alternatives.addAll(alternative);
        }
        return alternatives;
    }

    void initializeInterceptors(PolicyInterceptorProviderRegistry reg, Set<Interceptor<? extends Message>> out, Assertion a, boolean fault, Message msg) {
        Policy p;
        QName qn = a.getName();
        List<Interceptor<? extends Message>> i = fault ? reg.getInFaultInterceptorsForAssertion(qn) : reg.getInInterceptorsForAssertion(qn);
        out.addAll(i);
        if (a instanceof PolicyContainingAssertion && (p = ((PolicyContainingAssertion)a).getPolicy()) != null) {
            for (Assertion a2 : this.getSupportedAlternatives(p, msg)) {
                this.initializeInterceptors(reg, out, a2, fault, msg);
            }
        }
    }

    synchronized void initializeInterceptors(Message m4) {
        if (this.interceptors != null) {
            return;
        }
        if (this.engine == null || this.engine.getBus() == null || this.engine.getBus().getExtension(PolicyInterceptorProviderRegistry.class) == null) {
            return;
        }
        PolicyInterceptorProviderRegistry reg = this.engine.getBus().getExtension(PolicyInterceptorProviderRegistry.class);
        LinkedHashSet<Interceptor<? extends Message>> out = new LinkedHashSet<Interceptor<? extends Message>>();
        if (this.getChosenAlternative() != null) {
            for (Assertion a : this.getChosenAlternative()) {
                this.initializeInterceptors(reg, out, a, false, m4);
            }
        }
        ArrayList<Interceptor<? extends Message>> tmp = null;
        if (this.requestor) {
            tmp = new ArrayList<Interceptor<? extends Message>>(out);
            out.clear();
            for (Assertion a : this.getChosenAlternative()) {
                this.initializeInterceptors(reg, out, a, true, m4);
            }
            this.faultInterceptors = new ArrayList<Interceptor<? extends Message>>(out);
        } else if (this.ei != null && this.ei.getBinding() != null) {
            for (BindingOperationInfo boi : this.ei.getBinding().getOperations()) {
                Collection<Assertion> c;
                EffectivePolicy p = this.engine.getEffectiveServerRequestPolicy(this.ei, boi, m4);
                if (p == null || p.getPolicy() == null || p.getPolicy().isEmpty() || (c = this.engine.getAssertions(p, true)) == null) continue;
                for (Assertion a : c) {
                    this.initializeInterceptors(reg, out, a, false, m4);
                    this.initializeInterceptors(reg, out, a, true, m4);
                }
            }
            tmp = new ArrayList<Interceptor<? extends Message>>(out);
        } else {
            tmp = new ArrayList(out);
        }
        this.interceptors = tmp;
    }

    void setPolicy(Policy ep) {
        this.policy = ep;
    }

    void setChosenAlternative(Collection<Assertion> c) {
        this.chosenAlternative = c;
    }

    void setVocabulary(Collection<Assertion> v) {
        this.vocabulary = v;
    }

    void setFaultVocabulary(Collection<Assertion> v) {
        this.faultVocabulary = v;
    }

    void setInterceptors(List<Interceptor<? extends Message>> in) {
        this.interceptors = in;
    }

    void setFaultInterceptors(List<Interceptor<? extends Message>> inFault) {
        this.faultInterceptors = inFault;
    }

    protected EndpointPolicyImpl createEndpointPolicy() {
        return new EndpointPolicyImpl(this.ei, this.engine, this.requestor, this.assertor);
    }

    void checkExactlyOnes() {
        List<PolicyComponent> assertions = this.policy.getPolicyComponents();
        if (assertions.size() <= 1) {
            return;
        }
        Policy p = new Policy();
        ExactlyOne alternatives = new ExactlyOne();
        p.addPolicyComponent(alternatives);
        for (PolicyComponent a : assertions) {
            alternatives.addPolicyComponents(((ExactlyOne)a).getPolicyComponents());
        }
        this.setPolicy(p);
    }
}

