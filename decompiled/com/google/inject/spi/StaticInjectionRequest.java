/*
 * Decompiled with CFR 0.152.
 */
package com.google.inject.spi;

import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.inject.Binder;
import com.google.inject.ConfigurationException;
import com.google.inject.spi.Element;
import com.google.inject.spi.ElementVisitor;
import com.google.inject.spi.InjectionPoint;
import java.util.Set;

public final class StaticInjectionRequest
implements Element {
    private final Object source;
    private final Class<?> type;

    StaticInjectionRequest(Object source2, Class<?> type) {
        this.source = Preconditions.checkNotNull(source2, "source");
        this.type = Preconditions.checkNotNull(type, "type");
    }

    @Override
    public Object getSource() {
        return this.source;
    }

    public Class<?> getType() {
        return this.type;
    }

    public Set<InjectionPoint> getInjectionPoints() throws ConfigurationException {
        return InjectionPoint.forStaticMethodsAndFields(this.type);
    }

    @Override
    public void applyTo(Binder binder) {
        binder.withSource(this.getSource()).requestStaticInjection(this.type);
    }

    @Override
    public <T> T acceptVisitor(ElementVisitor<T> visitor) {
        return visitor.visit(this);
    }

    public boolean equals(Object obj) {
        return obj instanceof StaticInjectionRequest && ((StaticInjectionRequest)obj).source.equals(this.source) && ((StaticInjectionRequest)obj).type.equals(this.type);
    }

    public int hashCode() {
        return Objects.hashCode(this.source, this.type);
    }
}

