/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.feature;

import java.util.List;
import javax.xml.ws.WebServiceFeature;
import org.apache.cxf.Bus;
import org.apache.cxf.feature.AbstractPortableFeature;
import org.apache.cxf.feature.Feature;
import org.apache.cxf.interceptor.InterceptorProvider;

public abstract class AbstractFeature
extends WebServiceFeature
implements AbstractPortableFeature {
    @Override
    public String getID() {
        return this.getClass().getName();
    }

    @Override
    public boolean isEnabled() {
        return this.enabled;
    }

    @Override
    public void doInitializeProvider(InterceptorProvider provider, Bus bus) {
        this.initializeProvider(provider, bus);
    }

    protected void initializeProvider(InterceptorProvider provider, Bus bus) {
    }

    public static <T> T getActive(List<? extends Feature> features, Class<T> type) {
        return AbstractPortableFeature.getActive(features, type);
    }
}

