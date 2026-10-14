/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.feature.transform;

import org.apache.cxf.Bus;
import org.apache.cxf.common.injection.NoJSR250Annotations;
import org.apache.cxf.feature.AbstractPortableFeature;
import org.apache.cxf.feature.DelegatingFeature;
import org.apache.cxf.feature.transform.XSLTInInterceptor;
import org.apache.cxf.feature.transform.XSLTOutInterceptor;
import org.apache.cxf.interceptor.InterceptorProvider;

@NoJSR250Annotations
public class XSLTFeature
extends DelegatingFeature<Portable> {
    public XSLTFeature() {
        super(new Portable());
    }

    public void setInXSLTPath(String inXSLTPath) {
        ((Portable)this.delegate).setInXSLTPath(inXSLTPath);
    }

    public void setOutXSLTPath(String outXSLTPath) {
        ((Portable)this.delegate).setOutXSLTPath(outXSLTPath);
    }

    public static class Portable
    implements AbstractPortableFeature {
        private String inXSLTPath;
        private String outXSLTPath;

        @Override
        public void doInitializeProvider(InterceptorProvider provider, Bus bus) {
            if (this.inXSLTPath != null) {
                XSLTInInterceptor in = new XSLTInInterceptor(this.inXSLTPath);
                provider.getInInterceptors().add(in);
            }
            if (this.outXSLTPath != null) {
                XSLTOutInterceptor out = new XSLTOutInterceptor(this.outXSLTPath);
                provider.getOutInterceptors().add(out);
                provider.getOutFaultInterceptors().add(out);
            }
        }

        public void setInXSLTPath(String inXSLTPath) {
            this.inXSLTPath = inXSLTPath;
        }

        public void setOutXSLTPath(String outXSLTPath) {
            this.outXSLTPath = outXSLTPath;
        }
    }
}

