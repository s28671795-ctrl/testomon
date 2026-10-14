/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.client.internal;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import org.glassfish.jersey.client.ClientProperties;
import org.glassfish.jersey.client.ClientRequest;
import org.glassfish.jersey.client.RequestEntityProcessing;
import org.glassfish.jersey.client.internal.ConnectorExtension;

class HttpUrlExpect100ContinueConnectorExtension
implements ConnectorExtension<HttpURLConnection, IOException> {
    private static final String EXCEPTION_MESSAGE = "Server rejected operation";

    HttpUrlExpect100ContinueConnectorExtension() {
    }

    @Override
    public void invoke(ClientRequest request, HttpURLConnection uc) {
        boolean allowStreaming;
        long length = request.getLengthLong();
        RequestEntityProcessing entityProcessing = (RequestEntityProcessing)((Object)request.resolveProperty("jersey.config.client.request.entity.processing", RequestEntityProcessing.class));
        Boolean expectContinueActivated = (Boolean)((Object)request.resolveProperty("jersey.config.client.request.expect.100.continue.processing", Boolean.class));
        Long expectContinueSizeThreshold = request.resolveProperty("jersey.config.client.request.expect.100.continue.threshold.size", ClientProperties.DEFAULT_EXPECT_100_CONTINUE_THRESHOLD_SIZE);
        boolean bl = allowStreaming = length > expectContinueSizeThreshold || entityProcessing == RequestEntityProcessing.CHUNKED;
        if (!Boolean.TRUE.equals(expectContinueActivated) || !"POST".equals(uc.getRequestMethod()) && !"PUT".equals(uc.getRequestMethod()) || !allowStreaming) {
            return;
        }
        uc.setRequestProperty("Expect", "100-Continue");
    }

    @Override
    public void postConnectionProcessing(HttpURLConnection extensionParam) {
    }

    @Override
    public boolean handleException(ClientRequest request, HttpURLConnection extensionParam, IOException ex) {
        Boolean expectContinueActivated = request.resolveProperty("jersey.config.client.request.expect.100.continue.processing", Boolean.FALSE);
        return expectContinueActivated != false && ex instanceof ProtocolException && ex.getMessage().equals(EXCEPTION_MESSAGE);
    }
}

