/*
 * Decompiled with CFR 0.152.
 */
package io.javalin.openapi;

import kotlin.Metadata;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 7, 1}, k=1, xi=48, d1={"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0005\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\t"}, d2={"Lio/javalin/openapi/ContentType;", "", "()V", "AUTODETECT", "", "FORM_DATA_MULTIPART", "FORM_DATA_URL_ENCODED", "HTML", "JSON", "openapi-specification"})
public final class ContentType {
    @NotNull
    public static final ContentType INSTANCE = new ContentType();
    @NotNull
    public static final String JSON = "application/json";
    @NotNull
    public static final String HTML = "text/html";
    @NotNull
    public static final String FORM_DATA_URL_ENCODED = "application/x-www-form-urlencoded";
    @NotNull
    public static final String FORM_DATA_MULTIPART = "multipart/form-data";
    @NotNull
    public static final String AUTODETECT = "AUTODETECT - Will be replaced later";

    private ContentType() {
    }
}

