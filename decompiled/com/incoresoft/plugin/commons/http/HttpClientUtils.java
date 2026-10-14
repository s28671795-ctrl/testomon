/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.plugin.commons.http;

import com.incoresoft.middleware.http.serialization.Serialization;
import com.incoresoft.plugin.commons.exceptions.http.HttpRequestFailedException;
import com.incoresoft.plugin.commons.exceptions.http.HttpServerUnavailableException;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.function.Function;
import org.apache.hc.client5.http.classic.HttpClient;
import org.apache.hc.core5.http.ClassicHttpRequest;
import org.apache.hc.core5.http.ContentType;
import org.apache.hc.core5.http.io.HttpClientResponseHandler;
import org.apache.hc.core5.http.io.entity.BasicHttpEntity;
import org.apache.hc.core5.net.URIBuilder;

public class HttpClientUtils {
    public static void addAuthHeader(ClassicHttpRequest request, String token) {
        request.addHeader("Authorization", "Bearer " + token);
    }

    public static void setJsonBody(ClassicHttpRequest request, Object body2) {
        request.setEntity(new BasicHttpEntity(new ByteArrayInputStream(Serialization.writeValueAsString(body2).getBytes(StandardCharsets.UTF_8)), ContentType.APPLICATION_JSON));
    }

    public static String withQueryParameters(String url2, Map<String, String> parameters) {
        try {
            URIBuilder uriBuilder = new URIBuilder(url2);
            parameters.forEach(uriBuilder::addParameter);
            return uriBuilder.build().toString();
        }
        catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> T send(HttpClient httpClient, ClassicHttpRequest request, Function<String, T> successfulResultMapper, HttpErrorResponseHandler ... errorHandlers) {
        HttpClientResponseHandler<Object> responseHandler = response -> {
            String responseContent;
            boolean isBodyEmpty = response.getEntity() == null || response.getEntity().getContent() == null;
            String string = responseContent = isBodyEmpty ? null : new String(response.getEntity().getContent().readAllBytes(), StandardCharsets.UTF_8);
            if (response.getCode() >= 200 && response.getCode() < 300) {
                return successfulResultMapper.apply(responseContent);
            }
            for (HttpErrorResponseHandler errorHandler : errorHandlers) {
                if (response.getCode() != errorHandler.errorStatusCode) continue;
                throw errorHandler.exceptionProducer.apply(responseContent);
            }
            throw new HttpRequestFailedException(responseContent);
        };
        try {
            return (T)httpClient.execute(request, responseHandler);
        }
        catch (IOException e) {
            throw new HttpServerUnavailableException(e);
        }
    }

    public static final class HttpErrorResponseHandler {
        private final int errorStatusCode;
        private final Function<String, RuntimeException> exceptionProducer;

        public HttpErrorResponseHandler(int errorStatusCode, Function<String, RuntimeException> exceptionProducer) {
            this.errorStatusCode = errorStatusCode;
            this.exceptionProducer = exceptionProducer;
        }

        public int getErrorStatusCode() {
            return this.errorStatusCode;
        }

        public Function<String, RuntimeException> getExceptionProducer() {
            return this.exceptionProducer;
        }

        public boolean equals(Object o) {
            if (o == this) {
                return true;
            }
            if (!(o instanceof HttpErrorResponseHandler)) {
                return false;
            }
            HttpErrorResponseHandler other = (HttpErrorResponseHandler)o;
            if (this.getErrorStatusCode() != other.getErrorStatusCode()) {
                return false;
            }
            Function<String, RuntimeException> this$exceptionProducer = this.getExceptionProducer();
            Function<String, RuntimeException> other$exceptionProducer = other.getExceptionProducer();
            return !(this$exceptionProducer == null ? other$exceptionProducer != null : !this$exceptionProducer.equals(other$exceptionProducer));
        }

        public int hashCode() {
            int PRIME = 59;
            int result = 1;
            result = result * 59 + this.getErrorStatusCode();
            Function<String, RuntimeException> $exceptionProducer = this.getExceptionProducer();
            result = result * 59 + ($exceptionProducer == null ? 43 : $exceptionProducer.hashCode());
            return result;
        }

        public String toString() {
            return "HttpClientUtils.HttpErrorResponseHandler(errorStatusCode=" + this.getErrorStatusCode() + ", exceptionProducer=" + String.valueOf(this.getExceptionProducer()) + ")";
        }
    }
}

