/*
 * Decompiled with CFR 0.152.
 */
package io.javalin.openapi;

import io.javalin.openapi.OpenApiContact;
import io.javalin.openapi.OpenApiLicense;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 7, 1}, k=1, xi=48, d1={"\u0000$\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0011\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002R\u001c\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u001c\u0010\t\u001a\u0004\u0018\u00010\nX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000eR\u001c\u0010\u000f\u001a\u0004\u0018\u00010\u0010X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0011\u0010\u0012\"\u0004\b\u0013\u0010\u0014R\u001c\u0010\u0015\u001a\u0004\u0018\u00010\nX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0016\u0010\f\"\u0004\b\u0017\u0010\u000eR\u001c\u0010\u0018\u001a\u0004\u0018\u00010\nX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0019\u0010\f\"\u0004\b\u001a\u0010\u000eR\u001a\u0010\u001b\u001a\u00020\nX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u001c\u0010\f\"\u0004\b\u001d\u0010\u000eR\u001a\u0010\u001e\u001a\u00020\nX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u001f\u0010\f\"\u0004\b \u0010\u000e\u00a8\u0006!"}, d2={"Lio/javalin/openapi/OpenApiInfo;", "", "()V", "contact", "Lio/javalin/openapi/OpenApiContact;", "getContact", "()Lio/javalin/openapi/OpenApiContact;", "setContact", "(Lio/javalin/openapi/OpenApiContact;)V", "description", "", "getDescription", "()Ljava/lang/String;", "setDescription", "(Ljava/lang/String;)V", "license", "Lio/javalin/openapi/OpenApiLicense;", "getLicense", "()Lio/javalin/openapi/OpenApiLicense;", "setLicense", "(Lio/javalin/openapi/OpenApiLicense;)V", "summary", "getSummary", "setSummary", "termsOfService", "getTermsOfService", "setTermsOfService", "title", "getTitle", "setTitle", "version", "getVersion", "setVersion", "openapi-specification"})
public final class OpenApiInfo {
    @NotNull
    private String title = "OpenApi Title";
    @Nullable
    private String summary;
    @Nullable
    private String description;
    @Nullable
    private String termsOfService;
    @Nullable
    private OpenApiContact contact;
    @Nullable
    private OpenApiLicense license;
    @NotNull
    private String version = "";

    @NotNull
    public final String getTitle() {
        return this.title;
    }

    public final void setTitle(@NotNull String string) {
        Intrinsics.checkNotNullParameter(string, "<set-?>");
        this.title = string;
    }

    @Nullable
    public final String getSummary() {
        return this.summary;
    }

    public final void setSummary(@Nullable String string) {
        this.summary = string;
    }

    @Nullable
    public final String getDescription() {
        return this.description;
    }

    public final void setDescription(@Nullable String string) {
        this.description = string;
    }

    @Nullable
    public final String getTermsOfService() {
        return this.termsOfService;
    }

    public final void setTermsOfService(@Nullable String string) {
        this.termsOfService = string;
    }

    @Nullable
    public final OpenApiContact getContact() {
        return this.contact;
    }

    public final void setContact(@Nullable OpenApiContact openApiContact) {
        this.contact = openApiContact;
    }

    @Nullable
    public final OpenApiLicense getLicense() {
        return this.license;
    }

    public final void setLicense(@Nullable OpenApiLicense openApiLicense) {
        this.license = openApiLicense;
    }

    @NotNull
    public final String getVersion() {
        return this.version;
    }

    public final void setVersion(@NotNull String string) {
        Intrinsics.checkNotNullParameter(string, "<set-?>");
        this.version = string;
    }
}

