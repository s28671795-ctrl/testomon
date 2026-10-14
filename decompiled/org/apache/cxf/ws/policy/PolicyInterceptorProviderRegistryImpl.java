/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.ws.policy;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import javax.annotation.Resource;
import javax.xml.namespace.QName;
import org.apache.cxf.Bus;
import org.apache.cxf.common.injection.NoJSR250Annotations;
import org.apache.cxf.configuration.ConfiguredBeanLocator;
import org.apache.cxf.extension.BusExtension;
import org.apache.cxf.extension.RegistryImpl;
import org.apache.cxf.interceptor.Interceptor;
import org.apache.cxf.message.Message;
import org.apache.cxf.ws.policy.PolicyInterceptorProvider;
import org.apache.cxf.ws.policy.PolicyInterceptorProviderLoader;
import org.apache.cxf.ws.policy.PolicyInterceptorProviderRegistry;
import org.apache.neethi.Assertion;

@NoJSR250Annotations(unlessNull={"bus"})
public class PolicyInterceptorProviderRegistryImpl
extends RegistryImpl<QName, Set<PolicyInterceptorProvider>>
implements PolicyInterceptorProviderRegistry,
BusExtension {
    private Bus bus;
    private boolean dynamicLoaded;

    public PolicyInterceptorProviderRegistryImpl() {
        super(null);
    }

    public PolicyInterceptorProviderRegistryImpl(Bus b) {
        super(null);
        this.setBus(b);
    }

    public PolicyInterceptorProviderRegistryImpl(Map<QName, Set<PolicyInterceptorProvider>> interceptors) {
        super(interceptors);
    }

    @Resource
    public final void setBus(Bus b) {
        this.bus = b;
        if (b != null) {
            b.setExtension(this, PolicyInterceptorProviderRegistry.class);
        }
    }

    @Override
    public void register(PolicyInterceptorProvider provider) {
        for (QName qn : provider.getAssertionTypes()) {
            CopyOnWriteArraySet<PolicyInterceptorProvider> providers = (CopyOnWriteArraySet<PolicyInterceptorProvider>)super.get(qn);
            if (providers == null) {
                providers = new CopyOnWriteArraySet<PolicyInterceptorProvider>();
            }
            providers.add(provider);
            super.register(qn, providers);
        }
    }

    @Override
    public Class<?> getRegistrationType() {
        return PolicyInterceptorProviderRegistry.class;
    }

    @Override
    protected synchronized void loadDynamic() {
        if (!this.dynamicLoaded && this.bus != null) {
            this.dynamicLoaded = true;
            ConfiguredBeanLocator c = this.bus.getExtension(ConfiguredBeanLocator.class);
            if (c != null) {
                c.getBeansOfType(PolicyInterceptorProviderLoader.class);
                for (PolicyInterceptorProvider b : c.getBeansOfType(PolicyInterceptorProvider.class)) {
                    this.register(b);
                }
            }
        }
    }

    @Override
    public Set<PolicyInterceptorProvider> get(QName qn) {
        Set<PolicyInterceptorProvider> pps = (Set<PolicyInterceptorProvider>)super.get(qn);
        if (pps == null) {
            pps = Collections.emptySet();
        }
        return pps;
    }

    @Override
    public List<Interceptor<? extends Message>> getInterceptorsForAlternative(Collection<? extends Assertion> alternative, boolean out, boolean fault) {
        ArrayList<Interceptor<? extends Message>> interceptors = new ArrayList<Interceptor<? extends Message>>();
        for (Assertion assertion : alternative) {
            if (assertion.isOptional()) continue;
            QName qn = assertion.getName();
            interceptors.addAll(this.getInterceptorsForAssertion(qn, out, fault));
        }
        return interceptors;
    }

    @Override
    public List<Interceptor<? extends Message>> getInInterceptorsForAssertion(QName qn) {
        return this.getInterceptorsForAssertion(qn, false, false);
    }

    @Override
    public List<Interceptor<? extends Message>> getInFaultInterceptorsForAssertion(QName qn) {
        return this.getInterceptorsForAssertion(qn, false, true);
    }

    @Override
    public List<Interceptor<? extends Message>> getOutInterceptorsForAssertion(QName qn) {
        return this.getInterceptorsForAssertion(qn, true, false);
    }

    @Override
    public List<Interceptor<? extends Message>> getOutFaultInterceptorsForAssertion(QName qn) {
        return this.getInterceptorsForAssertion(qn, true, true);
    }

    protected List<Interceptor<? extends Message>> getInterceptorsForAssertion(QName qn, boolean out, boolean fault) {
        this.loadDynamic();
        ArrayList<Interceptor<? extends Message>> interceptors = new ArrayList<Interceptor<? extends Message>>();
        Set<PolicyInterceptorProvider> pps = this.get(qn);
        for (PolicyInterceptorProvider pp : pps) {
            interceptors.addAll(out ? (fault ? pp.getOutFaultInterceptors() : pp.getOutInterceptors()) : (fault ? pp.getInFaultInterceptors() : pp.getInInterceptors()));
        }
        return interceptors;
    }
}

