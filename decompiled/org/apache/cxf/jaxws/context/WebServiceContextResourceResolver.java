/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.jaxws.context;

import java.io.InputStream;
import javax.annotation.Resource;
import javax.xml.ws.WebServiceContext;
import org.apache.cxf.Bus;
import org.apache.cxf.common.injection.NoJSR250Annotations;
import org.apache.cxf.jaxws.context.WebServiceContextImpl;
import org.apache.cxf.resource.ResourceManager;
import org.apache.cxf.resource.ResourceResolver;

@NoJSR250Annotations(unlessNull={"bus"})
public class WebServiceContextResourceResolver
implements ResourceResolver {
    Bus bus;

    public WebServiceContextResourceResolver() {
    }

    public WebServiceContextResourceResolver(Bus b) {
        this.setBus(b);
    }

    @Resource
    public final void setBus(Bus b) {
        this.bus = b;
        if (this.bus != null && this.bus.getExtension(ResourceManager.class) != null) {
            this.bus.getExtension(ResourceManager.class).addResourceResolver(this);
        }
    }

    @Override
    public final InputStream getAsStream(String string) {
        return null;
    }

    @Override
    public final <T> T resolve(String string, Class<T> clz) {
        if (WebServiceContext.class.isAssignableFrom(clz)) {
            return clz.cast(new WebServiceContextImpl());
        }
        return null;
    }
}

