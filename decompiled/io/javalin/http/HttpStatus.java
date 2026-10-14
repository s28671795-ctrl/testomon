/*
 * Decompiled with CFR 0.152.
 */
package io.javalin.http;

import kotlin.Deprecated;
import kotlin.Metadata;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.SourceDebugExtension;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0002\bG\b\u0086\u0001\u0018\u0000 K2\b\u0012\u0004\u0012\u00020\u00000\u0001:\u0001KB\u0017\b\u0002\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J\b\u0010\u000b\u001a\u00020\u0005H\u0016R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nj\u0002\b\fj\u0002\b\rj\u0002\b\u000ej\u0002\b\u000fj\u0002\b\u0010j\u0002\b\u0011j\u0002\b\u0012j\u0002\b\u0013j\u0002\b\u0014j\u0002\b\u0015j\u0002\b\u0016j\u0002\b\u0017j\u0002\b\u0018j\u0002\b\u0019j\u0002\b\u001aj\u0002\b\u001bj\u0002\b\u001cj\u0002\b\u001dj\u0002\b\u001ej\u0002\b\u001fj\u0002\b j\u0002\b!j\u0002\b\"j\u0002\b#j\u0002\b$j\u0002\b%j\u0002\b&j\u0002\b'j\u0002\b(j\u0002\b)j\u0002\b*j\u0002\b+j\u0002\b,j\u0002\b-j\u0002\b.j\u0002\b/j\u0002\b0j\u0002\b1j\u0002\b2j\u0002\b3j\u0002\b4j\u0002\b5j\u0002\b6j\u0002\b7j\u0002\b8j\u0002\b9j\u0002\b:j\u0002\b;j\u0002\b<j\u0002\b=j\u0002\b>j\u0002\b?j\u0002\b@j\u0002\bAj\u0002\bBj\u0002\bCj\u0002\bDj\u0002\bEj\u0002\bFj\u0002\bGj\u0002\bHj\u0002\bIj\u0002\bJ\u00a8\u0006L"}, d2={"Lio/javalin/http/HttpStatus;", "", "code", "", "message", "", "(Ljava/lang/String;IILjava/lang/String;)V", "getCode", "()I", "getMessage", "()Ljava/lang/String;", "toString", "CONTINUE", "SWITCHING_PROTOCOLS", "PROCESSING", "EARLY_HINTS", "OK", "CREATED", "ACCEPTED", "NON_AUTHORITATIVE_INFORMATION", "NO_CONTENT", "RESET_CONTENT", "PARTIAL_CONTENT", "MULTI_STATUS", "ALREADY_REPORTED", "IM_USED", "MULTIPLE_CHOICES", "MOVED_PERMANENTLY", "FOUND", "SEE_OTHER", "NOT_MODIFIED", "USE_PROXY", "TEMPORARY_REDIRECT", "PERMANENT_REDIRECT", "BAD_REQUEST", "UNAUTHORIZED", "PAYMENT_REQUIRED", "FORBIDDEN", "NOT_FOUND", "METHOD_NOT_ALLOWED", "NOT_ACCEPTABLE", "PROXY_AUTHENTICATION_REQUIRED", "REQUEST_TIMEOUT", "CONFLICT", "GONE", "LENGTH_REQUIRED", "PRECONDITION_FAILED", "CONTENT_TOO_LARGE", "URI_TOO_LONG", "UNSUPPORTED_MEDIA_TYPE", "RANGE_NOT_SATISFIABLE", "EXPECTATION_FAILED", "IM_A_TEAPOT", "ENHANCE_YOUR_CALM", "MISDIRECTED_REQUEST", "UNPROCESSABLE_CONTENT", "LOCKED", "FAILED_DEPENDENCY", "TOO_EARLY", "UPGRADE_REQUIRED", "PRECONDITION_REQUIRED", "TOO_MANY_REQUESTS", "REQUEST_HEADER_FIELDS_TOO_LARGE", "UNAVAILABLE_FOR_LEGAL_REASONS", "INTERNAL_SERVER_ERROR", "NOT_IMPLEMENTED", "BAD_GATEWAY", "SERVICE_UNAVAILABLE", "GATEWAY_TIMEOUT", "HTTP_VERSION_NOT_SUPPORTED", "INSUFFICIENT_STORAGE", "LOOP_DETECTED", "NOT_EXTENDED", "NETWORK_AUTHENTICATION_REQUIRED", "UNKNOWN", "Companion", "javalin"})
public final class HttpStatus
extends Enum<HttpStatus> {
    @NotNull
    public static final Companion Companion;
    private final int code;
    @NotNull
    private final String message;
    @NotNull
    private static final HttpStatus[] cachedValues;
    public static final /* enum */ HttpStatus CONTINUE;
    public static final /* enum */ HttpStatus SWITCHING_PROTOCOLS;
    public static final /* enum */ HttpStatus PROCESSING;
    public static final /* enum */ HttpStatus EARLY_HINTS;
    public static final /* enum */ HttpStatus OK;
    public static final /* enum */ HttpStatus CREATED;
    public static final /* enum */ HttpStatus ACCEPTED;
    public static final /* enum */ HttpStatus NON_AUTHORITATIVE_INFORMATION;
    public static final /* enum */ HttpStatus NO_CONTENT;
    public static final /* enum */ HttpStatus RESET_CONTENT;
    public static final /* enum */ HttpStatus PARTIAL_CONTENT;
    public static final /* enum */ HttpStatus MULTI_STATUS;
    public static final /* enum */ HttpStatus ALREADY_REPORTED;
    public static final /* enum */ HttpStatus IM_USED;
    public static final /* enum */ HttpStatus MULTIPLE_CHOICES;
    public static final /* enum */ HttpStatus MOVED_PERMANENTLY;
    public static final /* enum */ HttpStatus FOUND;
    public static final /* enum */ HttpStatus SEE_OTHER;
    public static final /* enum */ HttpStatus NOT_MODIFIED;
    public static final /* enum */ HttpStatus USE_PROXY;
    public static final /* enum */ HttpStatus TEMPORARY_REDIRECT;
    public static final /* enum */ HttpStatus PERMANENT_REDIRECT;
    public static final /* enum */ HttpStatus BAD_REQUEST;
    public static final /* enum */ HttpStatus UNAUTHORIZED;
    public static final /* enum */ HttpStatus PAYMENT_REQUIRED;
    public static final /* enum */ HttpStatus FORBIDDEN;
    public static final /* enum */ HttpStatus NOT_FOUND;
    public static final /* enum */ HttpStatus METHOD_NOT_ALLOWED;
    public static final /* enum */ HttpStatus NOT_ACCEPTABLE;
    public static final /* enum */ HttpStatus PROXY_AUTHENTICATION_REQUIRED;
    public static final /* enum */ HttpStatus REQUEST_TIMEOUT;
    public static final /* enum */ HttpStatus CONFLICT;
    public static final /* enum */ HttpStatus GONE;
    public static final /* enum */ HttpStatus LENGTH_REQUIRED;
    public static final /* enum */ HttpStatus PRECONDITION_FAILED;
    public static final /* enum */ HttpStatus CONTENT_TOO_LARGE;
    public static final /* enum */ HttpStatus URI_TOO_LONG;
    public static final /* enum */ HttpStatus UNSUPPORTED_MEDIA_TYPE;
    public static final /* enum */ HttpStatus RANGE_NOT_SATISFIABLE;
    public static final /* enum */ HttpStatus EXPECTATION_FAILED;
    public static final /* enum */ HttpStatus IM_A_TEAPOT;
    public static final /* enum */ HttpStatus ENHANCE_YOUR_CALM;
    public static final /* enum */ HttpStatus MISDIRECTED_REQUEST;
    public static final /* enum */ HttpStatus UNPROCESSABLE_CONTENT;
    public static final /* enum */ HttpStatus LOCKED;
    public static final /* enum */ HttpStatus FAILED_DEPENDENCY;
    public static final /* enum */ HttpStatus TOO_EARLY;
    public static final /* enum */ HttpStatus UPGRADE_REQUIRED;
    public static final /* enum */ HttpStatus PRECONDITION_REQUIRED;
    public static final /* enum */ HttpStatus TOO_MANY_REQUESTS;
    public static final /* enum */ HttpStatus REQUEST_HEADER_FIELDS_TOO_LARGE;
    public static final /* enum */ HttpStatus UNAVAILABLE_FOR_LEGAL_REASONS;
    public static final /* enum */ HttpStatus INTERNAL_SERVER_ERROR;
    public static final /* enum */ HttpStatus NOT_IMPLEMENTED;
    public static final /* enum */ HttpStatus BAD_GATEWAY;
    public static final /* enum */ HttpStatus SERVICE_UNAVAILABLE;
    public static final /* enum */ HttpStatus GATEWAY_TIMEOUT;
    public static final /* enum */ HttpStatus HTTP_VERSION_NOT_SUPPORTED;
    public static final /* enum */ HttpStatus INSUFFICIENT_STORAGE;
    public static final /* enum */ HttpStatus LOOP_DETECTED;
    @Deprecated(message="Obsoleted by https://datatracker.ietf.org/doc/status-change-http-experiments-to-historic/")
    public static final /* enum */ HttpStatus NOT_EXTENDED;
    public static final /* enum */ HttpStatus NETWORK_AUTHENTICATION_REQUIRED;
    public static final /* enum */ HttpStatus UNKNOWN;
    private static final /* synthetic */ HttpStatus[] $VALUES;

    private HttpStatus(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public final int getCode() {
        return this.code;
    }

    @NotNull
    public final String getMessage() {
        return this.message;
    }

    @NotNull
    public String toString() {
        return this.code + " " + this.message;
    }

    public static HttpStatus[] values() {
        return (HttpStatus[])$VALUES.clone();
    }

    public static HttpStatus valueOf(String value) {
        return Enum.valueOf(HttpStatus.class, value);
    }

    @JvmStatic
    @NotNull
    public static final HttpStatus forStatus(int status) {
        return Companion.forStatus(status);
    }

    static {
        CONTINUE = new HttpStatus(100, "Continue");
        SWITCHING_PROTOCOLS = new HttpStatus(101, "Switching Protocols");
        PROCESSING = new HttpStatus(102, "Processing");
        EARLY_HINTS = new HttpStatus(103, "Early Hints");
        OK = new HttpStatus(200, "OK");
        CREATED = new HttpStatus(201, "Created");
        ACCEPTED = new HttpStatus(202, "Accepted");
        NON_AUTHORITATIVE_INFORMATION = new HttpStatus(203, "Non Authoritative Information");
        NO_CONTENT = new HttpStatus(204, "No Content");
        RESET_CONTENT = new HttpStatus(205, "Reset Content");
        PARTIAL_CONTENT = new HttpStatus(206, "Partial Content");
        MULTI_STATUS = new HttpStatus(207, "Multi-Status");
        ALREADY_REPORTED = new HttpStatus(208, "Already Reported");
        IM_USED = new HttpStatus(226, "IM Used");
        MULTIPLE_CHOICES = new HttpStatus(300, "Multiple Choices");
        MOVED_PERMANENTLY = new HttpStatus(301, "Moved Permanently");
        FOUND = new HttpStatus(302, "Found");
        SEE_OTHER = new HttpStatus(303, "See Other");
        NOT_MODIFIED = new HttpStatus(304, "Not Modified");
        USE_PROXY = new HttpStatus(305, "Use Proxy");
        TEMPORARY_REDIRECT = new HttpStatus(307, "Temporary Redirect");
        PERMANENT_REDIRECT = new HttpStatus(308, "Permanent Redirect");
        BAD_REQUEST = new HttpStatus(400, "Bad Request");
        UNAUTHORIZED = new HttpStatus(401, "Unauthorized");
        PAYMENT_REQUIRED = new HttpStatus(402, "Payment Required");
        FORBIDDEN = new HttpStatus(403, "Forbidden");
        NOT_FOUND = new HttpStatus(404, "Not Found");
        METHOD_NOT_ALLOWED = new HttpStatus(405, "Method Not Allowed");
        NOT_ACCEPTABLE = new HttpStatus(406, "Not Acceptable");
        PROXY_AUTHENTICATION_REQUIRED = new HttpStatus(407, "Proxy Authentication Required");
        REQUEST_TIMEOUT = new HttpStatus(408, "Request Timeout");
        CONFLICT = new HttpStatus(409, "Conflict");
        GONE = new HttpStatus(410, "Gone");
        LENGTH_REQUIRED = new HttpStatus(411, "Length Required");
        PRECONDITION_FAILED = new HttpStatus(412, "Precondition Failed");
        CONTENT_TOO_LARGE = new HttpStatus(413, "Content Too Large");
        URI_TOO_LONG = new HttpStatus(414, "URI Too Long");
        UNSUPPORTED_MEDIA_TYPE = new HttpStatus(415, "Unsupported Media Type");
        RANGE_NOT_SATISFIABLE = new HttpStatus(416, "Range Not Satisfiable");
        EXPECTATION_FAILED = new HttpStatus(417, "Expectation Failed");
        IM_A_TEAPOT = new HttpStatus(418, "I'm a Teapot");
        ENHANCE_YOUR_CALM = new HttpStatus(420, "Enhance your Calm");
        MISDIRECTED_REQUEST = new HttpStatus(421, "Misdirected Request");
        UNPROCESSABLE_CONTENT = new HttpStatus(422, "Unprocessable Content");
        LOCKED = new HttpStatus(423, "Locked");
        FAILED_DEPENDENCY = new HttpStatus(424, "Failed Dependency");
        TOO_EARLY = new HttpStatus(425, "Too Early");
        UPGRADE_REQUIRED = new HttpStatus(426, "Upgrade Required");
        PRECONDITION_REQUIRED = new HttpStatus(428, "Precondition Required");
        TOO_MANY_REQUESTS = new HttpStatus(429, "Too Many Requests");
        REQUEST_HEADER_FIELDS_TOO_LARGE = new HttpStatus(431, "Request Header Fields Too Large");
        UNAVAILABLE_FOR_LEGAL_REASONS = new HttpStatus(451, "Unavailable for Legal Reason");
        INTERNAL_SERVER_ERROR = new HttpStatus(500, "Server Error");
        NOT_IMPLEMENTED = new HttpStatus(501, "Not Implemented");
        BAD_GATEWAY = new HttpStatus(502, "Bad Gateway");
        SERVICE_UNAVAILABLE = new HttpStatus(503, "Service Unavailable");
        GATEWAY_TIMEOUT = new HttpStatus(504, "Gateway Timeout");
        HTTP_VERSION_NOT_SUPPORTED = new HttpStatus(505, "HTTP Version Not Supported");
        INSUFFICIENT_STORAGE = new HttpStatus(507, "Insufficient Storage");
        LOOP_DETECTED = new HttpStatus(508, "Loop Detected");
        NOT_EXTENDED = new HttpStatus(510, "Not Extended");
        NETWORK_AUTHENTICATION_REQUIRED = new HttpStatus(511, "Network Authentication Required");
        UNKNOWN = new HttpStatus(-1, "Unknown HTTP code");
        $VALUES = httpStatusArray = new HttpStatus[]{HttpStatus.CONTINUE, HttpStatus.SWITCHING_PROTOCOLS, HttpStatus.PROCESSING, HttpStatus.EARLY_HINTS, HttpStatus.OK, HttpStatus.CREATED, HttpStatus.ACCEPTED, HttpStatus.NON_AUTHORITATIVE_INFORMATION, HttpStatus.NO_CONTENT, HttpStatus.RESET_CONTENT, HttpStatus.PARTIAL_CONTENT, HttpStatus.MULTI_STATUS, HttpStatus.ALREADY_REPORTED, HttpStatus.IM_USED, HttpStatus.MULTIPLE_CHOICES, HttpStatus.MOVED_PERMANENTLY, HttpStatus.FOUND, HttpStatus.SEE_OTHER, HttpStatus.NOT_MODIFIED, HttpStatus.USE_PROXY, HttpStatus.TEMPORARY_REDIRECT, HttpStatus.PERMANENT_REDIRECT, HttpStatus.BAD_REQUEST, HttpStatus.UNAUTHORIZED, HttpStatus.PAYMENT_REQUIRED, HttpStatus.FORBIDDEN, HttpStatus.NOT_FOUND, HttpStatus.METHOD_NOT_ALLOWED, HttpStatus.NOT_ACCEPTABLE, HttpStatus.PROXY_AUTHENTICATION_REQUIRED, HttpStatus.REQUEST_TIMEOUT, HttpStatus.CONFLICT, HttpStatus.GONE, HttpStatus.LENGTH_REQUIRED, HttpStatus.PRECONDITION_FAILED, HttpStatus.CONTENT_TOO_LARGE, HttpStatus.URI_TOO_LONG, HttpStatus.UNSUPPORTED_MEDIA_TYPE, HttpStatus.RANGE_NOT_SATISFIABLE, HttpStatus.EXPECTATION_FAILED, HttpStatus.IM_A_TEAPOT, HttpStatus.ENHANCE_YOUR_CALM, HttpStatus.MISDIRECTED_REQUEST, HttpStatus.UNPROCESSABLE_CONTENT, HttpStatus.LOCKED, HttpStatus.FAILED_DEPENDENCY, HttpStatus.TOO_EARLY, HttpStatus.UPGRADE_REQUIRED, HttpStatus.PRECONDITION_REQUIRED, HttpStatus.TOO_MANY_REQUESTS, HttpStatus.REQUEST_HEADER_FIELDS_TOO_LARGE, HttpStatus.UNAVAILABLE_FOR_LEGAL_REASONS, HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.NOT_IMPLEMENTED, HttpStatus.BAD_GATEWAY, HttpStatus.SERVICE_UNAVAILABLE, HttpStatus.GATEWAY_TIMEOUT, HttpStatus.HTTP_VERSION_NOT_SUPPORTED, HttpStatus.INSUFFICIENT_STORAGE, HttpStatus.LOOP_DETECTED, HttpStatus.NOT_EXTENDED, HttpStatus.NETWORK_AUTHENTICATION_REQUIRED, HttpStatus.UNKNOWN};
        Companion = new Companion(null);
        cachedValues = HttpStatus.values();
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\u0007\u001a\u00020\u00052\u0006\u0010\b\u001a\u00020\tH\u0007R\u0016\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u0004\u00a2\u0006\u0004\n\u0002\u0010\u0006\u00a8\u0006\n"}, d2={"Lio/javalin/http/HttpStatus$Companion;", "", "()V", "cachedValues", "", "Lio/javalin/http/HttpStatus;", "[Lio/javalin/http/HttpStatus;", "forStatus", "status", "", "javalin"})
    @SourceDebugExtension(value={"SMAP\nHttpStatus.kt\nKotlin\n*S Kotlin\n*F\n+ 1 HttpStatus.kt\nio/javalin/http/HttpStatus$Companion\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,83:1\n1#2:84\n*E\n"})
    public static final class Companion {
        private Companion() {
        }

        @JvmStatic
        @NotNull
        public final HttpStatus forStatus(int status) {
            HttpStatus httpStatus;
            HttpStatus httpStatus2;
            block2: {
                HttpStatus[] httpStatusArray = cachedValues;
                int n = httpStatusArray.length;
                for (int i = 0; i < n; ++i) {
                    HttpStatus httpStatus3;
                    HttpStatus it = httpStatus3 = httpStatusArray[i];
                    boolean bl = false;
                    if (!(it.getCode() == status)) continue;
                    httpStatus2 = httpStatus3;
                    break block2;
                }
                httpStatus2 = httpStatus = null;
            }
            if (httpStatus2 == null) {
                httpStatus = UNKNOWN;
            }
            return httpStatus;
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}

