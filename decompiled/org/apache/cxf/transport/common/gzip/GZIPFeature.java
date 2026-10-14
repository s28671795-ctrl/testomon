/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.transport.common.gzip;

import java.util.List;
import org.apache.cxf.Bus;
import org.apache.cxf.annotations.Provider;
import org.apache.cxf.common.injection.NoJSR250Annotations;
import org.apache.cxf.feature.AbstractPortableFeature;
import org.apache.cxf.feature.DelegatingFeature;
import org.apache.cxf.interceptor.Interceptor;
import org.apache.cxf.interceptor.InterceptorProvider;
import org.apache.cxf.message.Message;
import org.apache.cxf.transport.common.gzip.GZIPInInterceptor;
import org.apache.cxf.transport.common.gzip.GZIPOutInterceptor;

@NoJSR250Annotations
@Provider(value=Provider.Type.Feature)
public class GZIPFeature
extends DelegatingFeature<Portable> {
    public GZIPFeature() {
        super(new Portable());
    }

    public void remove(List<Interceptor<? extends Message>> outInterceptors) {
        ((Portable)this.delegate).remove(outInterceptors);
    }

    public void setThreshold(int threshold) {
        ((Portable)this.delegate).setThreshold(threshold);
    }

    public int getThreshold() {
        return ((Portable)this.delegate).getThreshold();
    }

    public void setForce(boolean b) {
        ((Portable)this.delegate).setForce(b);
    }

    public boolean getForce() {
        return ((Portable)this.delegate).getForce();
    }

    public static class Portable
    implements AbstractPortableFeature {
        private static final GZIPInInterceptor IN = new GZIPInInterceptor();
        private static final GZIPOutInterceptor OUT = new GZIPOutInterceptor();
        int threshold = -1;
        boolean force;

        @Override
        public void doInitializeProvider(InterceptorProvider provider, Bus bus) {
            provider.getInInterceptors().add(IN);
            if (this.threshold == -1 && !this.force) {
                provider.getOutInterceptors().add(OUT);
                provider.getOutFaultInterceptors().add(OUT);
            } else {
                GZIPOutInterceptor out = new GZIPOutInterceptor();
                out.setThreshold(this.threshold);
                out.setForce(this.force);
                this.remove(provider.getOutInterceptors());
                this.remove(provider.getOutFaultInterceptors());
                provider.getOutInterceptors().add(out);
                provider.getOutFaultInterceptors().add(out);
            }
        }

        private void remove(List<Interceptor<? extends Message>> outInterceptors) {
            int x = outInterceptors.size();
            while (x > 0) {
                if (!(outInterceptors.get(--x) instanceof GZIPOutInterceptor)) continue;
                outInterceptors.remove(x);
            }
        }

        public void setThreshold(int threshold) {
            this.threshold = threshold;
        }

        public int getThreshold() {
            return this.threshold;
        }

        public void setForce(boolean b) {
            this.force = b;
        }

        public boolean getForce() {
            return this.force;
        }
    }
}

