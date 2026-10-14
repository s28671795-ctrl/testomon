/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.ws.policy;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.namespace.QName;
import org.apache.cxf.common.i18n.BundleUtils;
import org.apache.cxf.common.logging.LogUtils;
import org.apache.cxf.interceptor.Interceptor;
import org.apache.cxf.message.Message;
import org.apache.cxf.service.model.BindingFaultInfo;
import org.apache.cxf.service.model.BindingMessageInfo;
import org.apache.cxf.service.model.BindingOperationInfo;
import org.apache.cxf.service.model.EndpointInfo;
import org.apache.cxf.transport.Conduit;
import org.apache.cxf.transport.Destination;
import org.apache.cxf.ws.policy.Assertor;
import org.apache.cxf.ws.policy.EffectivePolicy;
import org.apache.cxf.ws.policy.EndpointPolicy;
import org.apache.cxf.ws.policy.EndpointPolicyImpl;
import org.apache.cxf.ws.policy.PolicyEngine;
import org.apache.cxf.ws.policy.PolicyEngineImpl;
import org.apache.cxf.ws.policy.PolicyException;
import org.apache.cxf.ws.policy.PolicyInterceptorProviderRegistry;
import org.apache.cxf.ws.policy.PolicyUtils;
import org.apache.neethi.Assertion;
import org.apache.neethi.Policy;
import org.apache.neethi.PolicyContainingAssertion;

public class EffectivePolicyImpl
implements EffectivePolicy {
    private static final ResourceBundle BUNDLE = BundleUtils.getBundle(EffectivePolicyImpl.class);
    private static final Logger LOG = LogUtils.getL7dLogger(EffectivePolicyImpl.class);
    protected Policy policy;
    protected Collection<Assertion> chosenAlternative;
    protected List<Interceptor<? extends Message>> interceptors;

    @Override
    public Policy getPolicy() {
        return this.policy;
    }

    @Override
    public List<Interceptor<? extends Message>> getInterceptors() {
        return this.interceptors;
    }

    @Override
    public Collection<Assertion> getChosenAlternative() {
        return this.chosenAlternative;
    }

    public void initialise(EndpointPolicy epi, PolicyEngine engine, boolean inbound, Message m4) {
        this.initialise(epi, engine, inbound, false, m4);
    }

    public void initialise(EndpointPolicy epi, PolicyEngine engine, boolean inbound, boolean fault, Message m4) {
        this.policy = epi.getPolicy();
        this.chosenAlternative = epi.getChosenAlternative();
        if (this.chosenAlternative == null) {
            this.chooseAlternative(engine, null, m4);
        }
        this.initialiseInterceptors(engine, inbound, fault, m4);
    }

    public void initialise(EndpointInfo ei, BindingOperationInfo boi, PolicyEngine engine, Assertor assertor, boolean requestor, boolean request, Message m4) {
        this.initialisePolicy(ei, boi, engine, requestor, request, assertor, m4);
        this.chooseAlternative(engine, assertor, m4);
        this.initialiseInterceptors(engine, false, m4);
    }

    public void initialise(EndpointInfo ei, BindingOperationInfo boi, PolicyEngine engine, Assertor assertor, List<List<Assertion>> incoming, Message m4) {
        this.initialisePolicy(ei, boi, engine, false, false, assertor, m4);
        this.chooseAlternative(engine, assertor, incoming, m4);
        this.initialiseInterceptors(engine, false, m4);
    }

    public void initialise(EndpointInfo ei, BindingOperationInfo boi, PolicyEngine engine, boolean requestor, boolean request, Message m4) {
        Assertor assertor = this.initialisePolicy(ei, boi, engine, requestor, request, null, m4);
        if (requestor || !request) {
            this.chooseAlternative(engine, assertor, m4);
            this.initialiseInterceptors(engine, requestor, m4);
        } else {
            Collection<Assertion> alternative = ((PolicyEngineImpl)engine).getAssertions(this.policy, true);
            this.setChosenAlternative(alternative);
        }
    }

    public void initialise(EndpointInfo ei, BindingOperationInfo boi, BindingFaultInfo bfi, PolicyEngine engine, Assertor assertor, Message m4) {
        this.initialisePolicy(ei, boi, bfi, engine, m4);
        this.chooseAlternative(engine, assertor, m4);
        this.initialiseInterceptors(engine, false, m4);
    }

    private <T> T getAssertorAs(Assertor as, Class<T> t2) {
        org.apache.cxf.transport.Assertor o;
        if (t2.isInstance(as)) {
            return t2.cast(as);
        }
        if (as instanceof PolicyUtils.WrappedAssertor && t2.isInstance(o = ((PolicyUtils.WrappedAssertor)as).getWrappedAssertor())) {
            return t2.cast(o);
        }
        return null;
    }

    Assertor initialisePolicy(EndpointInfo ei, BindingOperationInfo boi, PolicyEngine engine, boolean requestor, boolean request, Assertor assertor, Message m4) {
        if (boi.isUnwrapped()) {
            boi = boi.getUnwrappedOperation();
        }
        BindingMessageInfo bmi = request ? boi.getInput() : boi.getOutput();
        EndpointPolicy ep = requestor ? engine.getClientEndpointPolicy(ei, this.getAssertorAs(assertor, Conduit.class), m4) : engine.getServerEndpointPolicy(ei, this.getAssertorAs(assertor, Destination.class), m4);
        this.policy = ep.getPolicy();
        if (ep instanceof EndpointPolicyImpl) {
            assertor = ((EndpointPolicyImpl)ep).getAssertor();
        }
        this.policy = this.policy.merge(((PolicyEngineImpl)engine).getAggregatedOperationPolicy(boi, m4));
        if (null != bmi) {
            this.policy = this.policy.merge(((PolicyEngineImpl)engine).getAggregatedMessagePolicy(bmi, m4));
        }
        this.policy = this.policy.normalize(engine.getRegistry(), true);
        return assertor;
    }

    void initialisePolicy(EndpointInfo ei, BindingOperationInfo boi, BindingFaultInfo bfi, PolicyEngine engine, Message m4) {
        this.policy = engine.getServerEndpointPolicy(ei, null, m4).getPolicy();
        this.policy = this.policy.merge(((PolicyEngineImpl)engine).getAggregatedOperationPolicy(boi, m4));
        if (bfi != null) {
            this.policy = this.policy.merge(((PolicyEngineImpl)engine).getAggregatedFaultPolicy(bfi, m4));
        }
        this.policy = this.policy.normalize(engine.getRegistry(), true);
    }

    void chooseAlternative(PolicyEngine engine, Assertor assertor, Message m4) {
        this.chooseAlternative(engine, assertor, null, m4);
    }

    void chooseAlternative(PolicyEngine engine, Assertor assertor, List<List<Assertion>> incoming, Message m4) {
        Collection<Assertion> alternative = engine.getAlternativeSelector().selectAlternative(this.policy, engine, assertor, incoming, m4);
        if (null == alternative) {
            PolicyUtils.logPolicy(LOG, Level.FINE, "No alternative supported.", this.getPolicy());
            throw new PolicyException(new org.apache.cxf.common.i18n.Message("NO_ALTERNATIVE_EXC", BUNDLE, new Object[0]));
        }
        this.setChosenAlternative(alternative);
    }

    void initialiseInterceptors(PolicyEngine engine, Message m4) {
        this.initialiseInterceptors(engine, false, m4);
    }

    void initialiseInterceptors(PolicyEngine engine, boolean useIn, Message m4) {
        this.initialiseInterceptors(engine, useIn, false, m4);
    }

    void initialiseInterceptors(PolicyEngine engine, boolean useIn, boolean fault, Message m4) {
        if (((PolicyEngineImpl)engine).getBus() != null) {
            PolicyInterceptorProviderRegistry reg = ((PolicyEngineImpl)engine).getBus().getExtension(PolicyInterceptorProviderRegistry.class);
            LinkedHashSet<Interceptor<? extends Message>> out = new LinkedHashSet<Interceptor<? extends Message>>();
            for (Assertion a : this.getChosenAlternative()) {
                this.initialiseInterceptors(reg, engine, out, a, useIn, fault, m4);
            }
            this.setInterceptors(new ArrayList<Interceptor<? extends Message>>(out));
        }
    }

    protected Collection<Assertion> getSupportedAlternatives(PolicyEngine engine, Policy p, Message m4) {
        ArrayList<Assertion> alternatives = new ArrayList<Assertion>();
        Iterator<List<Assertion>> it = p.getAlternatives();
        while (it.hasNext()) {
            List<Assertion> alternative = it.next();
            if (!engine.supportsAlternative(alternative, null, m4)) continue;
            alternatives.addAll(alternative);
        }
        return alternatives;
    }

    void initialiseInterceptors(PolicyInterceptorProviderRegistry reg, PolicyEngine engine, Set<Interceptor<? extends Message>> out, Assertion a, boolean useIn, boolean fault, Message m4) {
        Policy p;
        QName qn = a.getName();
        List<Interceptor<? extends Message>> i = null;
        if (useIn && !fault) {
            i = reg.getInInterceptorsForAssertion(qn);
        } else if (!useIn && !fault) {
            i = reg.getOutInterceptorsForAssertion(qn);
        } else if (useIn && fault) {
            i = reg.getInFaultInterceptorsForAssertion(qn);
        } else if (!useIn && fault) {
            i = reg.getOutFaultInterceptorsForAssertion(qn);
        }
        out.addAll(i);
        if (a instanceof PolicyContainingAssertion && (p = ((PolicyContainingAssertion)a).getPolicy()) != null) {
            for (Assertion a2 : this.getSupportedAlternatives(engine, p, m4)) {
                this.initialiseInterceptors(reg, engine, out, a2, useIn, fault, m4);
            }
        }
    }

    void setPolicy(Policy ep) {
        this.policy = ep;
    }

    void setChosenAlternative(Collection<Assertion> c) {
        this.chosenAlternative = c;
    }

    void setInterceptors(List<Interceptor<? extends Message>> out) {
        this.interceptors = out;
    }
}

