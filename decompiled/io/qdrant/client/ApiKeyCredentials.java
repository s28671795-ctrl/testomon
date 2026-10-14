/*
 * Decompiled with CFR 0.152.
 */
package io.qdrant.client;

import io.grpc.CallCredentials;
import io.grpc.Metadata;
import io.grpc.Status;
import java.util.concurrent.Executor;

public class ApiKeyCredentials
extends CallCredentials {
    private final String apiKey;

    public ApiKeyCredentials(String apiKey) {
        this.apiKey = apiKey;
    }

    @Override
    public void applyRequestMetadata(CallCredentials.RequestInfo requestInfo, Executor appExecutor, CallCredentials.MetadataApplier applier) {
        appExecutor.execute(() -> {
            try {
                Metadata headers = new Metadata();
                headers.put(Metadata.Key.of("api-key", Metadata.ASCII_STRING_MARSHALLER), this.apiKey);
                applier.apply(headers);
            }
            catch (Throwable e) {
                applier.fail(Status.UNAUTHENTICATED.withCause(e));
            }
        });
    }
}

