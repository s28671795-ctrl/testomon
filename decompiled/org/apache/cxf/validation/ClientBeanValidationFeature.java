/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.validation;

import org.apache.cxf.Bus;
import org.apache.cxf.annotations.Provider;
import org.apache.cxf.feature.AbstractPortableFeature;
import org.apache.cxf.feature.DelegatingFeature;
import org.apache.cxf.interceptor.InterceptorProvider;
import org.apache.cxf.validation.BeanValidationProvider;
import org.apache.cxf.validation.ClientBeanValidationOutInterceptor;

@Provider(value=Provider.Type.Feature, scope=Provider.Scope.Client)
public class ClientBeanValidationFeature
extends DelegatingFeature<Portable> {
    public ClientBeanValidationFeature() {
        super(new Portable());
    }

    protected ClientBeanValidationFeature(Portable d) {
        super(d);
    }

    public void addInterceptor(InterceptorProvider interceptorProvider, ClientBeanValidationOutInterceptor out) {
        ((Portable)this.delegate).addInterceptor(interceptorProvider, out);
    }

    public void setProvider(BeanValidationProvider provider) {
        ((Portable)this.delegate).setProvider(provider);
    }

    public static class Portable
    implements AbstractPortableFeature {
        private BeanValidationProvider validationProvider;

        @Override
        public void doInitializeProvider(InterceptorProvider interceptorProvider, Bus bus) {
            ClientBeanValidationOutInterceptor out = new ClientBeanValidationOutInterceptor();
            this.addInterceptor(interceptorProvider, out);
        }

        protected void addInterceptor(InterceptorProvider interceptorProvider, ClientBeanValidationOutInterceptor out) {
            if (this.validationProvider != null) {
                out.setProvider(this.validationProvider);
            }
            interceptorProvider.getOutInterceptors().add(out);
        }

        public void setProvider(BeanValidationProvider provider) {
            this.validationProvider = provider;
        }
    }
}

