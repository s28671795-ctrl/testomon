/*
 * Decompiled with CFR 0.152.
 */
package com.google.inject.spi;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;
import com.google.inject.Binder;
import com.google.inject.internal.ErrorId;
import com.google.inject.internal.Errors;
import com.google.inject.internal.GenericErrorDetail;
import com.google.inject.internal.GuiceInternal;
import com.google.inject.internal.util.SourceProvider;
import com.google.inject.spi.Element;
import com.google.inject.spi.ElementVisitor;
import com.google.inject.spi.ErrorDetail;
import java.io.ObjectStreamException;
import java.io.Serializable;
import java.util.List;

public final class Message
implements Serializable,
Element {
    private final ErrorId errorId;
    private final ErrorDetail<?> errorDetail;
    private static final long serialVersionUID = 0L;

    public Message(GuiceInternal internalOnly, ErrorId errorId, ErrorDetail<?> errorDetail) {
        Preconditions.checkNotNull(internalOnly);
        this.errorId = errorId;
        this.errorDetail = errorDetail;
    }

    private Message(ErrorId errorId, ErrorDetail<?> errorDetail) {
        this.errorId = errorId;
        this.errorDetail = errorDetail;
    }

    public Message(ErrorId errorId, List<Object> sources, String message, Throwable cause) {
        this.errorId = errorId;
        this.errorDetail = new GenericErrorDetail(errorId, message, sources, cause);
    }

    public Message(List<Object> sources, String message, Throwable cause) {
        this(ErrorId.OTHER, sources, message, cause);
    }

    public Message(String message, Throwable cause) {
        this(ImmutableList.of(), message, cause);
    }

    public Message(Object source2, String message) {
        this(ImmutableList.of(source2), message, null);
    }

    public Message(String message) {
        this(ImmutableList.of(), message, null);
    }

    public ErrorDetail<?> getErrorDetail() {
        return this.errorDetail;
    }

    @Override
    public String getSource() {
        List<Object> sources = this.errorDetail.getSources();
        return sources.isEmpty() ? SourceProvider.UNKNOWN_SOURCE.toString() : Errors.convert(Iterables.getLast(sources)).toString();
    }

    public List<Object> getSources() {
        return this.errorDetail.getSources();
    }

    public String getMessage() {
        return this.errorDetail.getMessage();
    }

    @Override
    public <T> T acceptVisitor(ElementVisitor<T> visitor) {
        return visitor.visit(this);
    }

    public Throwable getCause() {
        return this.errorDetail.getCause();
    }

    public String toString() {
        return this.errorDetail.getMessage();
    }

    public int hashCode() {
        return this.errorDetail.hashCode();
    }

    public boolean equals(Object o) {
        if (!(o instanceof Message)) {
            return false;
        }
        Message e = (Message)o;
        return this.errorDetail.equals(e.errorDetail);
    }

    @Override
    public void applyTo(Binder binder) {
        binder.withSource(this.getSource()).addError(this);
    }

    public Message withSource(List<Object> newSources) {
        return new Message(this.errorId, (ErrorDetail<?>)this.errorDetail.withSources(newSources));
    }

    private Object writeReplace() throws ObjectStreamException {
        Object[] sourcesAsStrings = this.getSources().toArray();
        for (int i = 0; i < sourcesAsStrings.length; ++i) {
            sourcesAsStrings[i] = Errors.convert(sourcesAsStrings[i]).toString();
        }
        return new Message(this.errorId, new GenericErrorDetail(this.errorId, this.getMessage(), ImmutableList.copyOf(sourcesAsStrings), this.getCause()));
    }
}

