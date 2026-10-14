/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.ws.policy;

import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.xml.namespace.QName;
import org.apache.cxf.Bus;
import org.apache.cxf.common.i18n.BundleUtils;
import org.apache.cxf.common.i18n.Message;
import org.apache.cxf.common.injection.NoJSR250Annotations;
import org.apache.cxf.common.logging.LogUtils;
import org.apache.cxf.configuration.ConfiguredBeanLocator;
import org.apache.cxf.extension.BusExtension;
import org.apache.cxf.ws.policy.AssertionBuilderLoader;
import org.apache.cxf.ws.policy.AssertionBuilderRegistry;
import org.apache.cxf.ws.policy.PolicyBuilder;
import org.apache.cxf.ws.policy.PolicyException;
import org.apache.neethi.AssertionBuilderFactoryImpl;
import org.apache.neethi.builders.AssertionBuilder;
import org.apache.neethi.builders.xml.XMLPrimitiveAssertionBuilder;

@NoJSR250Annotations(unlessNull={"bus"})
public class AssertionBuilderRegistryImpl
extends AssertionBuilderFactoryImpl
implements AssertionBuilderRegistry,
BusExtension {
    private static final ResourceBundle BUNDLE = BundleUtils.getBundle(AssertionBuilderRegistryImpl.class);
    private static final Logger LOG = LogUtils.getL7dLogger(AssertionBuilderRegistryImpl.class);
    private boolean ignoreUnknownAssertions = true;
    private Set<QName> ignored = new HashSet<QName>();
    private Bus bus;
    private boolean dynamicLoaded;

    public AssertionBuilderRegistryImpl() {
        super(null);
    }

    public AssertionBuilderRegistryImpl(Bus b) {
        super(null);
        this.setBus(b);
    }

    @Resource
    public final void setBus(Bus b) {
        this.bus = b;
        if (b != null) {
            b.setExtension(this, AssertionBuilderRegistry.class);
            PolicyBuilder builder = b.getExtension(PolicyBuilder.class);
            if (builder instanceof org.apache.neethi.PolicyBuilder) {
                this.engine = (org.apache.neethi.PolicyBuilder)((Object)builder);
            }
        }
    }

    @Override
    public Class<?> getRegistrationType() {
        return AssertionBuilderRegistry.class;
    }

    @Override
    public boolean isIgnoreUnknownAssertions() {
        return this.ignoreUnknownAssertions;
    }

    @Override
    public void setIgnoreUnknownAssertions(boolean ignore) {
        this.ignoreUnknownAssertions = ignore;
    }

    @Override
    protected synchronized void loadDynamic() {
        if (!this.dynamicLoaded && this.bus != null) {
            this.dynamicLoaded = true;
            ConfiguredBeanLocator c = this.bus.getExtension(ConfiguredBeanLocator.class);
            if (c != null) {
                c.getBeansOfType(AssertionBuilderLoader.class);
                for (AssertionBuilder b : c.getBeansOfType(AssertionBuilder.class)) {
                    this.registerBuilder(b);
                }
            }
        }
    }

    @Override
    protected AssertionBuilder<?> handleNoRegisteredBuilder(QName qname) {
        if (this.ignoreUnknownAssertions) {
            boolean alreadyWarned = this.ignored.contains(qname);
            if (!alreadyWarned) {
                this.ignored.add(qname);
                Message m4 = new Message("NO_ASSERTIONBUILDER_EXC", BUNDLE, qname.toString());
                LOG.warning(m4.toString());
            }
            return new XMLPrimitiveAssertionBuilder();
        }
        Message m5 = new Message("NO_ASSERTIONBUILDER_EXC", BUNDLE, qname.toString());
        throw new PolicyException(m5);
    }
}

