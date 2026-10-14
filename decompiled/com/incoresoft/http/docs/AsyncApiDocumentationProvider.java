/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.http.docs;

import io.javalin.http.Context;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AsyncApiDocumentationProvider {
    private static final Logger log = LoggerFactory.getLogger(AsyncApiDocumentationProvider.class);
    private static final String WS_FILE_PATH = "/asyncapi/ws/index.html";
    private static final String SSE_FILE_PATH = "/asyncapi/sse/index.html";
    private final AsyncApiFileDocumentation wsDocumentation;
    private final AsyncApiFileDocumentation sseDocumentation;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public AsyncApiDocumentationProvider() {
        AsyncApiFileDocumentation tempWsDocumentation = new AsyncApiFileDocumentation();
        AsyncApiFileDocumentation tempSseDocumentation = new AsyncApiFileDocumentation();
        try (InputStream wsInputStream = this.getClass().getResourceAsStream(WS_FILE_PATH);
             InputStream sseInputStream = this.getClass().getResourceAsStream(SSE_FILE_PATH);){
            tempWsDocumentation = new AsyncApiFileDocumentation(wsInputStream);
            tempSseDocumentation = new AsyncApiFileDocumentation(sseInputStream);
        }
        catch (Exception e) {
            log.error("Could not initialize AsyncApiHandler: ", e);
        }
        finally {
            this.wsDocumentation = tempWsDocumentation;
            this.sseDocumentation = tempSseDocumentation;
        }
    }

    public void provideAsyncApiWS(Context context) {
        this.provide(context, this.wsDocumentation);
    }

    public void provideAsyncApiSSE(Context context) {
        this.provide(context, this.sseDocumentation);
    }

    private void provide(Context context, AsyncApiFileDocumentation documentation) {
        if (documentation.exists) {
            context.html(documentation.getHtmlContext()).result();
        } else {
            log.error("Problem with configuration of Async API documentation");
            context.status(404);
        }
    }

    private static class AsyncApiFileDocumentation {
        private String htmlContext;
        private final boolean exists;

        public AsyncApiFileDocumentation(@Nullable InputStream inputStream2) throws IOException {
            if (inputStream2 != null) {
                this.exists = true;
                this.htmlContext = new String(inputStream2.readAllBytes(), StandardCharsets.UTF_8);
            } else {
                this.exists = false;
            }
        }

        public AsyncApiFileDocumentation() {
            this.htmlContext = null;
            this.exists = false;
        }

        public String getHtmlContext() {
            return this.htmlContext;
        }

        public boolean isExists() {
            return this.exists;
        }
    }
}

