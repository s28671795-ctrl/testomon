/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.feature;

import org.apache.cxf.Bus;
import org.apache.cxf.common.injection.NoJSR250Annotations;
import org.apache.cxf.feature.AbstractPortableFeature;
import org.apache.cxf.feature.DelegatingFeature;
import org.apache.cxf.interceptor.FIStaxInInterceptor;
import org.apache.cxf.interceptor.FIStaxOutInterceptor;
import org.apache.cxf.interceptor.InterceptorProvider;

@NoJSR250Annotations
public class FastInfosetFeature
extends DelegatingFeature<Portable> {
    public FastInfosetFeature() {
        super(new Portable());
    }

    public void setForce(boolean b) {
        ((Portable)this.delegate).setForce(b);
    }

    public boolean getForce() {
        return ((Portable)this.delegate).getForce();
    }

    public static class Portable
    implements AbstractPortableFeature {
        boolean force;
        private Integer serializerAttributeValueMapMemoryLimit;
        private Integer serializerMinAttributeValueSize;
        private Integer serializerMaxAttributeValueSize;
        private Integer serializerCharacterContentChunkMapMemoryLimit;
        private Integer serializerMinCharacterContentChunkSize;
        private Integer serializerMaxCharacterContentChunkSize;

        @Override
        public void doInitializeProvider(InterceptorProvider provider, Bus bus) {
            FIStaxInInterceptor in = new FIStaxInInterceptor();
            FIStaxOutInterceptor out = new FIStaxOutInterceptor(this.force);
            if (this.serializerAttributeValueMapMemoryLimit != null && this.serializerAttributeValueMapMemoryLimit > 0) {
                out.setSerializerAttributeValueMapMemoryLimit(this.serializerAttributeValueMapMemoryLimit);
            }
            if (this.serializerMinAttributeValueSize != null && this.serializerMinAttributeValueSize > 0) {
                out.setSerializerMinAttributeValueSize(this.serializerMinAttributeValueSize);
            }
            if (this.serializerMaxAttributeValueSize != null && this.serializerMaxAttributeValueSize > 0) {
                out.setSerializerMaxAttributeValueSize(this.serializerMaxAttributeValueSize);
            }
            if (this.serializerCharacterContentChunkMapMemoryLimit != null && this.serializerCharacterContentChunkMapMemoryLimit > 0) {
                out.setSerializerCharacterContentChunkMapMemoryLimit(this.serializerCharacterContentChunkMapMemoryLimit);
            }
            if (this.serializerMinCharacterContentChunkSize != null && this.serializerMinCharacterContentChunkSize > 0) {
                out.setSerializerMinCharacterContentChunkSize(this.serializerMinCharacterContentChunkSize);
            }
            if (this.serializerMaxCharacterContentChunkSize != null && this.serializerMaxCharacterContentChunkSize > 0) {
                out.setSerializerMaxCharacterContentChunkSize(this.serializerMaxCharacterContentChunkSize);
            }
            provider.getInInterceptors().add(in);
            provider.getInFaultInterceptors().add(in);
            provider.getOutInterceptors().add(out);
            provider.getOutFaultInterceptors().add(out);
        }

        public void setForce(boolean b) {
            this.force = b;
        }

        public boolean getForce() {
            return this.force;
        }
    }
}

