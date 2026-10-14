/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.transport.https;

import org.apache.cxf.Bus;
import org.apache.cxf.common.injection.NoJSR250Annotations;
import org.apache.cxf.configuration.security.CertificateConstraintsType;
import org.apache.cxf.endpoint.Client;
import org.apache.cxf.endpoint.Server;
import org.apache.cxf.feature.AbstractPortableFeature;
import org.apache.cxf.feature.DelegatingFeature;
import org.apache.cxf.interceptor.InterceptorProvider;
import org.apache.cxf.transport.https.CertConstraints;
import org.apache.cxf.transport.https.CertConstraintsInterceptor;
import org.apache.cxf.transport.https.CertConstraintsJaxBUtils;

@NoJSR250Annotations
public class CertConstraintsFeature
extends DelegatingFeature<Portable> {
    public CertConstraintsFeature() {
        super(new Portable());
    }

    public void setCertificateConstraints(CertificateConstraintsType c) {
        ((Portable)this.delegate).setCertificateConstraints(c);
    }

    public CertificateConstraintsType getCertificateConstraints() {
        return ((Portable)this.delegate).getCertificateConstraints();
    }

    public static class Portable
    implements AbstractPortableFeature {
        CertificateConstraintsType contraints;

        @Override
        public void initialize(Server server, Bus bus) {
            if (this.contraints == null) {
                return;
            }
            this.doInitializeProvider(server.getEndpoint(), bus);
            CertConstraints c = CertConstraintsJaxBUtils.createCertConstraints(this.contraints);
            server.getEndpoint().put(CertConstraints.class.getName(), c);
        }

        @Override
        public void initialize(Client client, Bus bus) {
            if (this.contraints == null) {
                return;
            }
            this.doInitializeProvider(client, bus);
            CertConstraints c = CertConstraintsJaxBUtils.createCertConstraints(this.contraints);
            client.getEndpoint().put(CertConstraints.class.getName(), c);
        }

        @Override
        public void initialize(Bus bus) {
            if (this.contraints == null) {
                return;
            }
            this.doInitializeProvider(bus, bus);
            CertConstraints c = CertConstraintsJaxBUtils.createCertConstraints(this.contraints);
            bus.setProperty(CertConstraints.class.getName(), c);
        }

        @Override
        public void doInitializeProvider(InterceptorProvider provider, Bus bus) {
            if (this.contraints == null) {
                return;
            }
            provider.getInInterceptors().add(CertConstraintsInterceptor.INSTANCE);
            provider.getInFaultInterceptors().add(CertConstraintsInterceptor.INSTANCE);
        }

        public void setCertificateConstraints(CertificateConstraintsType c) {
            this.contraints = c;
        }

        public CertificateConstraintsType getCertificateConstraints() {
            return this.contraints;
        }
    }
}

