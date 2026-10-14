/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.interceptor.security;

import org.apache.cxf.Bus;
import org.apache.cxf.feature.AbstractPortableFeature;
import org.apache.cxf.feature.DelegatingFeature;
import org.apache.cxf.interceptor.InterceptorProvider;
import org.apache.cxf.interceptor.security.JAASLoginInterceptor;

public class JAASAuthenticationFeature
extends DelegatingFeature<Portable> {
    public static final String ID = "jaas";

    public JAASAuthenticationFeature() {
        super(new Portable());
    }

    public void setContextName(String contextName) {
        ((Portable)this.delegate).setContextName(contextName);
    }

    public void setReportFault(boolean reportFault) {
        ((Portable)this.delegate).setReportFault(reportFault);
    }

    @Override
    public String getID() {
        return ID;
    }

    public static class Portable
    implements AbstractPortableFeature {
        private String contextName = "karaf";
        private boolean reportFault;

        @Override
        public void doInitializeProvider(InterceptorProvider provider, Bus bus) {
            JAASLoginInterceptor jaasLoginInterceptor = new JAASLoginInterceptor();
            jaasLoginInterceptor.setRoleClassifierType("classname");
            jaasLoginInterceptor.setRoleClassifier("org.apache.karaf.jaas.boot.principal.RolePrincipal");
            jaasLoginInterceptor.setContextName(this.contextName);
            jaasLoginInterceptor.setReportFault(this.reportFault);
            provider.getInInterceptors().add(jaasLoginInterceptor);
        }

        public void setContextName(String contextName) {
            this.contextName = contextName;
        }

        public void setReportFault(boolean reportFault) {
            this.reportFault = reportFault;
        }
    }
}

