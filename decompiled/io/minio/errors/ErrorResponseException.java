/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  edu.umd.cs.findbugs.annotations.SuppressFBWarnings
 */
package io.minio.errors;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import io.minio.errors.MinioException;
import io.minio.messages.ErrorResponse;
import okhttp3.Request;
import okhttp3.Response;

public class ErrorResponseException
extends MinioException {
    private static final long serialVersionUID = -2933211538346902928L;
    private final ErrorResponse errorResponse;
    @SuppressFBWarnings(value={"Se"}, justification="There's really no excuse except that nobody has complained")
    private final Response response;

    public ErrorResponseException(ErrorResponse errorResponse, Response response, String httpTrace) {
        super(errorResponse.message(), httpTrace);
        this.errorResponse = errorResponse;
        this.response = response;
    }

    public ErrorResponse errorResponse() {
        return this.errorResponse;
    }

    public Response response() {
        return this.response;
    }

    @Override
    public String toString() {
        Request request = this.response.request();
        return "error occurred\n" + this.errorResponse.toString() + "\nrequest={method=" + request.method() + ", url=" + request.url() + ", headers=" + request.headers().toString().replaceAll("Signature=([0-9a-f]+)", "Signature=*REDACTED*").replaceAll("Credential=([^/]+)", "Credential=*REDACTED*") + "}\nresponse={code=" + this.response.code() + ", headers=" + this.response.headers() + "}\n";
    }
}

