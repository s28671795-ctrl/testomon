/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.feature;

import java.util.List;
import java.util.Map;
import org.apache.cxf.Bus;
import org.apache.cxf.common.injection.NoJSR250Annotations;
import org.apache.cxf.feature.AbstractPortableFeature;
import org.apache.cxf.feature.DelegatingFeature;
import org.apache.cxf.interceptor.InterceptorProvider;
import org.apache.cxf.interceptor.transform.TransformInInterceptor;
import org.apache.cxf.interceptor.transform.TransformOutInterceptor;

@NoJSR250Annotations
public class StaxTransformFeature
extends DelegatingFeature<Portable> {
    public StaxTransformFeature() {
        super(new Portable());
    }

    public void setOutTransformElements(Map<String, String> outElements) {
        ((Portable)this.delegate).setOutTransformElements(outElements);
    }

    public void setOutTransformAttributes(Map<String, String> outAttributes) {
        ((Portable)this.delegate).setOutTransformAttributes(outAttributes);
    }

    public void setAttributesToElements(boolean value) {
        ((Portable)this.delegate).setAttributesToElements(value);
    }

    public void setSkipOnFault(boolean value) {
        ((Portable)this.delegate).setSkipOnFault(value);
    }

    public void setOutAppendElements(Map<String, String> map) {
        ((Portable)this.delegate).setOutAppendElements(map);
    }

    public void setOutDropElements(List<String> dropElementsSet) {
        ((Portable)this.delegate).setOutDropElements(dropElementsSet);
    }

    public void setInAppendElements(Map<String, String> inElements) {
        ((Portable)this.delegate).setInAppendElements(inElements);
    }

    public void setInDropElements(List<String> dropElementsSet) {
        ((Portable)this.delegate).setInDropElements(dropElementsSet);
    }

    public void setInTransformElements(Map<String, String> inElements) {
        ((Portable)this.delegate).setInTransformElements(inElements);
    }

    public void setInTransformAttributes(Map<String, String> inAttributes) {
        ((Portable)this.delegate).setInTransformAttributes(inAttributes);
    }

    public void setOutDefaultNamespace(String ns) {
        ((Portable)this.delegate).setOutDefaultNamespace(ns);
    }

    public void setContextPropertyName(String propertyName) {
        ((Portable)this.delegate).setContextPropertyName(propertyName);
    }

    public static class Portable
    implements AbstractPortableFeature {
        private TransformInInterceptor in = new TransformInInterceptor();
        private TransformOutInterceptor out = new TransformOutInterceptor();

        @Override
        public void doInitializeProvider(InterceptorProvider provider, Bus bus) {
            provider.getInInterceptors().add(this.in);
            provider.getOutInterceptors().add(this.out);
            provider.getOutFaultInterceptors().add(this.out);
        }

        public void setOutTransformElements(Map<String, String> outElements) {
            this.out.setOutTransformElements(outElements);
        }

        public void setOutTransformAttributes(Map<String, String> outAttributes) {
            this.out.setOutTransformAttributes(outAttributes);
        }

        public void setAttributesToElements(boolean value) {
            this.out.setAttributesToElements(value);
        }

        public void setSkipOnFault(boolean value) {
            this.out.setSkipOnFault(value);
        }

        public void setOutAppendElements(Map<String, String> map) {
            this.out.setOutAppendElements(map);
        }

        public void setOutDropElements(List<String> dropElementsSet) {
            this.out.setOutDropElements(dropElementsSet);
        }

        public void setInAppendElements(Map<String, String> inElements) {
            this.in.setInAppendElements(inElements);
        }

        public void setInDropElements(List<String> dropElementsSet) {
            this.in.setInDropElements(dropElementsSet);
        }

        public void setInTransformElements(Map<String, String> inElements) {
            this.in.setInTransformElements(inElements);
        }

        public void setInTransformAttributes(Map<String, String> inAttributes) {
            this.in.setInTransformAttributes(inAttributes);
        }

        public void setOutDefaultNamespace(String ns) {
            this.out.setDefaultNamespace(ns);
        }

        public void setContextPropertyName(String propertyName) {
            this.in.setContextPropertyName(propertyName);
            this.out.setContextPropertyName(propertyName);
        }
    }
}

