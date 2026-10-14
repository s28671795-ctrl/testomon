/*
 * Decompiled with CFR 0.152.
 */
package com.google.inject.internal;

import com.google.common.base.CaseFormat;
import com.google.common.collect.ImmutableSet;
import com.google.inject.internal.ErrorId;
import com.google.inject.spi.ErrorDetail;
import java.util.List;
import java.util.Optional;

abstract class InternalErrorDetail<T extends ErrorDetail<T>>
extends ErrorDetail<T> {
    private static final ImmutableSet<ErrorId> DOCUMENTED_ERRORS = ((ImmutableSet.Builder)((ImmutableSet.Builder)((ImmutableSet.Builder)((ImmutableSet.Builder)((ImmutableSet.Builder)((ImmutableSet.Builder)((ImmutableSet.Builder)((ImmutableSet.Builder)((ImmutableSet.Builder)((ImmutableSet.Builder)((ImmutableSet.Builder)((ImmutableSet.Builder)((ImmutableSet.Builder)((ImmutableSet.Builder)((ImmutableSet.Builder)((ImmutableSet.Builder)ImmutableSet.builder().add((Object)ErrorId.BINDING_ALREADY_SET)).add((Object)ErrorId.CAN_NOT_PROXY_CLASS)).add((Object)ErrorId.CIRCULAR_PROXY_DISABLED)).add((Object)ErrorId.DUPLICATE_BINDING_ANNOTATIONS)).add((Object)ErrorId.DUPLICATE_ELEMENT)).add((Object)ErrorId.DUPLICATE_SCOPES)).add((Object)ErrorId.ERROR_INJECTING_CONSTRUCTOR)).add((Object)ErrorId.ERROR_INJECTING_METHOD)).add((Object)ErrorId.ERROR_IN_CUSTOM_PROVIDER)).add((Object)ErrorId.INJECT_INNER_CLASS)).add((Object)ErrorId.MISSING_CONSTRUCTOR)).add((Object)ErrorId.MISSING_IMPLEMENTATION)).add((Object)ErrorId.NULL_INJECTED_INTO_NON_NULLABLE)).add((Object)ErrorId.NULL_VALUE_IN_MAP)).add((Object)ErrorId.SCOPE_NOT_FOUND)).add((Object)ErrorId.TOO_MANY_CONSTRUCTORS)).build();
    private static final String DOC_BASE_URL = "https://github.com/google/guice/wiki/";
    protected final ErrorId errorId;

    protected InternalErrorDetail(ErrorId errorId, String message, List<Object> sources, Throwable cause) {
        super(message, sources, cause);
        this.errorId = errorId;
    }

    @Override
    protected final Optional<String> getLearnMoreLink() {
        if (DOCUMENTED_ERRORS.contains((Object)this.errorId)) {
            return Optional.of(DOC_BASE_URL + this.errorId.name());
        }
        return Optional.empty();
    }

    @Override
    protected final Optional<String> getErrorIdentifier() {
        if (this.errorId == ErrorId.OTHER) {
            return Optional.empty();
        }
        String id = "Guice/" + CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, this.errorId.name());
        return Optional.of(id);
    }
}

