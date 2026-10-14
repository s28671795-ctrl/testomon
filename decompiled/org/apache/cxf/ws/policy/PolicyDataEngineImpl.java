/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.ws.policy;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.logging.Logger;
import javax.xml.namespace.QName;
import org.apache.cxf.Bus;
import org.apache.cxf.common.logging.LogUtils;
import org.apache.cxf.message.Message;
import org.apache.cxf.policy.PolicyCalculator;
import org.apache.cxf.policy.PolicyDataEngine;
import org.apache.cxf.service.model.EndpointInfo;
import org.apache.cxf.transport.Conduit;
import org.apache.cxf.transport.Destination;
import org.apache.cxf.ws.policy.AssertionInfo;
import org.apache.cxf.ws.policy.AssertionInfoMap;
import org.apache.cxf.ws.policy.PolicyEngine;
import org.apache.cxf.ws.policy.PolicyException;
import org.apache.cxf.ws.policy.builder.jaxb.JaxbAssertion;
import org.apache.neethi.Assertion;

public class PolicyDataEngineImpl
implements PolicyDataEngine {
    private static final Logger LOG = LogUtils.getL7dLogger(PolicyDataEngineImpl.class);
    private Bus bus;
    private PolicyEngine policyEngine;

    public PolicyDataEngineImpl(Bus bus) {
        this.bus = bus;
    }

    void setPolicyEngine(PolicyEngine policyEngine) {
        this.policyEngine = policyEngine;
    }

    private PolicyEngine getPolicyEngine() {
        if (this.policyEngine == null) {
            this.policyEngine = this.bus.getExtension(PolicyEngine.class);
        }
        return this.policyEngine;
    }

    @Override
    public <T> T getClientEndpointPolicy(Message m4, EndpointInfo ei, Conduit c, PolicyCalculator<T> policyCalculator) {
        Collection<Assertion> alternative = this.getPolicyEngine().getClientEndpointPolicy(ei, c, m4).getChosenAlternative();
        ArrayList<T> filteredPolicies = new ArrayList<T>();
        for (Assertion a : alternative) {
            if (!policyCalculator.getDataClassName().equals(a.getName())) continue;
            T p = JaxbAssertion.cast(a, policyCalculator.getDataClass()).getData();
            filteredPolicies.add(p);
        }
        return (T)this.getPolicy(filteredPolicies, policyCalculator);
    }

    @Override
    public <T> T getPolicy(Message message, T confPolicy, PolicyCalculator<T> intersector) {
        List<T> policies = this.getPoliciesFromMessage(intersector.getDataClassName(), message, intersector.getDataClass());
        if (!policies.contains(confPolicy)) {
            policies.add(confPolicy);
        }
        return this.getPolicy(policies, intersector);
    }

    @Override
    public <T> T getServerEndpointPolicy(Message m4, EndpointInfo ei, Destination d, PolicyCalculator<T> policyCalculator) {
        Collection<Assertion> alternative = this.getPolicyEngine().getServerEndpointPolicy(ei, d, m4).getChosenAlternative();
        ArrayList<T> filteredPolicies = new ArrayList<T>();
        for (Assertion a : alternative) {
            if (!policyCalculator.getDataClassName().equals(a.getName())) continue;
            T p = JaxbAssertion.cast(a, policyCalculator.getDataClass()).getData();
            filteredPolicies.add(p);
        }
        return (T)this.getPolicy(filteredPolicies, policyCalculator);
    }

    private <T> List<T> getPoliciesFromMessage(QName name, Message message, Class<T> dataClass) {
        ArrayList<T> policies = new ArrayList<T>();
        AssertionInfoMap amap = message.get(AssertionInfoMap.class);
        if (null == amap || amap.isEmpty()) {
            return policies;
        }
        Collection ais = (Collection)amap.get(name);
        if (null == ais) {
            return policies;
        }
        for (AssertionInfo ai : ais) {
            T policy = JaxbAssertion.cast(ai.getAssertion(), dataClass).getData();
            policies.add(policy);
        }
        return policies;
    }

    @Override
    public <T> void assertMessage(Message message, T confPol, PolicyCalculator<T> policyCalculator) {
        T messagePol = message.get(policyCalculator.getDataClass());
        T refPolicy = messagePol != null ? policyCalculator.intersect(messagePol, confPol) : confPol;
        AssertionInfoMap amap = message.get(AssertionInfoMap.class);
        if (null == amap || amap.isEmpty()) {
            return;
        }
        Collection ais = (Collection)amap.get(policyCalculator.getDataClassName());
        if (ais == null) {
            return;
        }
        for (AssertionInfo ai : ais) {
            T policy = JaxbAssertion.cast(ai.getAssertion(), policyCalculator.getDataClass()).getData();
            ai.setAsserted(policyCalculator.isAsserted(message, policy, refPolicy));
        }
    }

    private <T> T getPolicy(List<T> policies, PolicyCalculator<T> intersector) {
        Object compatible = null;
        for (T p : policies) {
            if (null == compatible) {
                compatible = p;
                continue;
            }
            if (compatible == p || null != (compatible = intersector.intersect(p, compatible))) continue;
            this.logAndThrowPolicyException(p);
        }
        return compatible;
    }

    private <T> void logAndThrowPolicyException(T dataClass) {
        org.apache.cxf.common.i18n.Message msg = new org.apache.cxf.common.i18n.Message("INCOMPATIBLE_HTTPCLIENTPOLICY_ASSERTIONS", LOG, dataClass.getClass());
        LOG.severe(msg.toString());
        throw new PolicyException(msg);
    }
}

