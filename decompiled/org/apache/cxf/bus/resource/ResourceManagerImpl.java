/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.bus.resource;

import java.util.List;
import javax.annotation.Resource;
import org.apache.cxf.Bus;
import org.apache.cxf.common.injection.NoJSR250Annotations;
import org.apache.cxf.configuration.ConfiguredBeanLocator;
import org.apache.cxf.extension.BusExtension;
import org.apache.cxf.resource.DefaultResourceManager;
import org.apache.cxf.resource.ObjectTypeResolver;
import org.apache.cxf.resource.ResourceManager;
import org.apache.cxf.resource.ResourceResolver;

@NoJSR250Annotations(unlessNull={"bus"})
public class ResourceManagerImpl
extends DefaultResourceManager
implements BusExtension {
    private Bus bus;

    public ResourceManagerImpl() {
    }

    public ResourceManagerImpl(Bus b) {
        this.setBus(b);
    }

    @Override
    protected void onFirstResolve() {
        ConfiguredBeanLocator locator;
        super.onFirstResolve();
        if (this.bus != null && (locator = this.bus.getExtension(ConfiguredBeanLocator.class)) != null) {
            this.addResourceResolvers(locator.getBeansOfType(ResourceResolver.class));
        }
    }

    public final void setResolvers(List<? extends ResourceResolver> resolvers) {
        this.registeredResolvers.clear();
        this.registeredResolvers.addAll(resolvers);
    }

    @Resource
    public final void setBus(Bus b) {
        if (this.bus != b) {
            this.bus = b;
            this.firstCalled = false;
            super.addResourceResolver(new ObjectTypeResolver(this.bus));
            if (null != this.bus) {
                this.bus.setExtension(this, ResourceManager.class);
            }
        }
    }

    @Override
    public Class<?> getRegistrationType() {
        return ResourceManager.class;
    }
}

