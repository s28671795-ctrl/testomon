/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.feature.validation;

import org.apache.cxf.Bus;
import org.apache.cxf.annotations.SchemaValidation;
import org.apache.cxf.endpoint.Client;
import org.apache.cxf.endpoint.Endpoint;
import org.apache.cxf.endpoint.Server;
import org.apache.cxf.feature.AbstractPortableFeature;
import org.apache.cxf.feature.DelegatingFeature;
import org.apache.cxf.feature.validation.SchemaValidationTypeProvider;
import org.apache.cxf.interceptor.InterceptorProvider;
import org.apache.cxf.service.model.BindingOperationInfo;

public class SchemaValidationFeature
extends DelegatingFeature<Portable> {
    public SchemaValidationFeature(SchemaValidationTypeProvider provider) {
        super(new Portable(provider));
    }

    public static class Portable
    implements AbstractPortableFeature {
        private final SchemaValidationTypeProvider provider;

        public Portable(SchemaValidationTypeProvider provider) {
            this.provider = provider;
        }

        @Override
        public void initialize(Server server, Bus bus) {
            this.initialise(server.getEndpoint());
        }

        @Override
        public void initialize(Client client, Bus bus) {
            this.initialise(client.getEndpoint());
        }

        @Override
        public void doInitializeProvider(InterceptorProvider interceptorProvider, Bus bus) {
        }

        private void initialise(Endpoint endpoint) {
            for (BindingOperationInfo bop : endpoint.getEndpointInfo().getBinding().getOperations()) {
                SchemaValidation.SchemaValidationType type = this.provider.getSchemaValidationType(bop.getOperationInfo());
                if (type == null) continue;
                bop.getOperationInfo().setProperty("schema-validation-type", (Object)type);
            }
        }
    }
}

