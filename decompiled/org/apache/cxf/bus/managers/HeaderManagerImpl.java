/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.bus.managers;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import javax.annotation.Resource;
import org.apache.cxf.Bus;
import org.apache.cxf.common.injection.NoJSR250Annotations;
import org.apache.cxf.headers.HeaderManager;
import org.apache.cxf.headers.HeaderProcessor;

@NoJSR250Annotations(unlessNull={"bus"})
public class HeaderManagerImpl
implements HeaderManager {
    Map<String, HeaderProcessor> processors = new ConcurrentHashMap<String, HeaderProcessor>(4, 0.75f, 2);
    Bus bus;

    public HeaderManagerImpl() {
    }

    public HeaderManagerImpl(Bus b) {
        this.setBus(b);
    }

    public Bus getBus() {
        return this.bus;
    }

    @Resource
    public final void setBus(Bus bus) {
        this.bus = bus;
        if (null != bus) {
            bus.setExtension(this, HeaderManager.class);
        }
    }

    @Override
    public HeaderProcessor getHeaderProcessor(String namespace) {
        return this.processors.get(namespace != null ? namespace : "");
    }

    @Override
    public void registerHeaderProcessor(HeaderProcessor processor) {
        this.processors.put(processor.getNamespace(), processor);
    }
}

