/*
 * Decompiled with CFR 0.152.
 */
package com.google.inject.spi;

import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.inject.Binder;
import com.google.inject.ConfigurationException;
import com.google.inject.MembersInjector;
import com.google.inject.TypeLiteral;
import com.google.inject.spi.Element;
import com.google.inject.spi.ElementVisitor;
import com.google.inject.spi.InjectionPoint;
import java.util.Set;

public final class MembersInjectorLookup<T>
implements Element {
    private final Object source;
    private final TypeLiteral<T> type;
    private MembersInjector<T> delegate;

    public MembersInjectorLookup(Object source2, TypeLiteral<T> type) {
        this.source = Preconditions.checkNotNull(source2, "source");
        this.type = Preconditions.checkNotNull(type, "type");
    }

    @Override
    public Object getSource() {
        return this.source;
    }

    public TypeLiteral<T> getType() {
        return this.type;
    }

    @Override
    public <T> T acceptVisitor(ElementVisitor<T> visitor) {
        return visitor.visit(this);
    }

    public void initializeDelegate(MembersInjector<T> delegate) {
        Preconditions.checkState(this.delegate == null, "delegate already initialized");
        this.delegate = Preconditions.checkNotNull(delegate, "delegate");
    }

    @Override
    public void applyTo(Binder binder) {
        this.initializeDelegate(binder.withSource(this.getSource()).getMembersInjector(this.type));
    }

    public MembersInjector<T> getDelegate() {
        return this.delegate;
    }

    public Set<InjectionPoint> getInjectionPoints() throws ConfigurationException {
        return InjectionPoint.forInstanceMethodsAndFields(this.type);
    }

    public MembersInjector<T> getMembersInjector() {
        return new MembersInjector<T>(){

            @Override
            public void injectMembers(T instance) {
                MembersInjector local = MembersInjectorLookup.this.delegate;
                if (local == null) {
                    throw new IllegalStateException("This MembersInjector cannot be used until the Injector has been created.");
                }
                local.injectMembers(instance);
            }

            public String toString() {
                return "MembersInjector<" + MembersInjectorLookup.this.type + ">";
            }
        };
    }

    public boolean equals(Object obj) {
        return obj instanceof MembersInjectorLookup && ((MembersInjectorLookup)obj).type.equals(this.type) && ((MembersInjectorLookup)obj).source.equals(this.source);
    }

    public int hashCode() {
        return Objects.hashCode(this.type, this.source);
    }
}

