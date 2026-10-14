/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.validation;

import org.apache.cxf.Bus;
import org.apache.cxf.annotations.Provider;
import org.apache.cxf.feature.AbstractPortableFeature;
import org.apache.cxf.feature.DelegatingFeature;
import org.apache.cxf.interceptor.InterceptorProvider;
import org.apache.cxf.validation.BeanValidationInInterceptor;
import org.apache.cxf.validation.BeanValidationOutInterceptor;
import org.apache.cxf.validation.BeanValidationProvider;

@Provider(value=Provider.Type.Feature, scope=Provider.Scope.Server)
public class BeanValidationFeature
extends DelegatingFeature<Portable> {
    public BeanValidationFeature() {
        super(new Portable());
    }

    public void setProvider(BeanValidationProvider provider) {
        ((Portable)this.delegate).setProvider(provider);
    }

    public static class Portable
    implements AbstractPortableFeature {
        private BeanValidationProvider validationProvider;

        @Override
        public void doInitializeProvider(InterceptorProvider interceptorProvider, Bus bus) {
            BeanValidationInInterceptor in = new BeanValidationInInterceptor();
            BeanValidationOutInterceptor out = new BeanValidationOutInterceptor();
            if (this.validationProvider != null) {
                in.setProvider(this.validationProvider);
                out.setProvider(this.validationProvider);
            }
            interceptorProvider.getInInterceptors().add(in);
            interceptorProvider.getOutInterceptors().add(out);
        }

        public void setProvider(BeanValidationProvider provider) {
            this.validationProvider = provider;
        }
    }
}

