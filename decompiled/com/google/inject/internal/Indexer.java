/*
 * Decompiled with CFR 0.152.
 */
package com.google.inject.internal;

import com.google.common.base.Objects;
import com.google.inject.Binding;
import com.google.inject.Injector;
import com.google.inject.Scope;
import com.google.inject.Scopes;
import com.google.inject.TypeLiteral;
import com.google.inject.internal.Element;
import com.google.inject.spi.BindingScopingVisitor;
import com.google.inject.spi.ConstructorBinding;
import com.google.inject.spi.ConvertedConstantBinding;
import com.google.inject.spi.DefaultBindingTargetVisitor;
import com.google.inject.spi.ExposedBinding;
import com.google.inject.spi.InstanceBinding;
import com.google.inject.spi.LinkedKeyBinding;
import com.google.inject.spi.ProviderBinding;
import com.google.inject.spi.ProviderInstanceBinding;
import com.google.inject.spi.ProviderKeyBinding;
import com.google.inject.spi.UntargettedBinding;
import java.lang.annotation.Annotation;

class Indexer
extends DefaultBindingTargetVisitor<Object, IndexedBinding>
implements BindingScopingVisitor<Object> {
    final Injector injector;
    private static final Object EAGER_SINGLETON = new Object();

    Indexer(Injector injector) {
        this.injector = injector;
    }

    boolean isIndexable(Binding<?> binding) {
        return binding.getKey().getAnnotation() instanceof Element;
    }

    private Object scope(Binding<?> binding) {
        return binding.acceptScopingVisitor(this);
    }

    @Override
    public IndexedBinding visit(ConstructorBinding<? extends Object> binding) {
        return new IndexedBinding(binding, BindingType.CONSTRUCTOR, this.scope(binding), binding.getConstructor());
    }

    @Override
    public IndexedBinding visit(ConvertedConstantBinding<? extends Object> binding) {
        return new IndexedBinding(binding, BindingType.CONSTANT, this.scope(binding), binding.getValue());
    }

    @Override
    public IndexedBinding visit(ExposedBinding<? extends Object> binding) {
        return new IndexedBinding(binding, BindingType.EXPOSED, this.scope(binding), binding);
    }

    @Override
    public IndexedBinding visit(InstanceBinding<? extends Object> binding) {
        return new IndexedBinding(binding, BindingType.INSTANCE, this.scope(binding), binding.getInstance());
    }

    @Override
    public IndexedBinding visit(LinkedKeyBinding<? extends Object> binding) {
        return new IndexedBinding(binding, BindingType.LINKED_KEY, this.scope(binding), binding.getLinkedKey());
    }

    @Override
    public IndexedBinding visit(ProviderBinding<? extends Object> binding) {
        return new IndexedBinding(binding, BindingType.PROVIDED_BY, this.scope(binding), this.injector.getBinding(binding.getProvidedKey()));
    }

    @Override
    public IndexedBinding visit(ProviderInstanceBinding<? extends Object> binding) {
        return new IndexedBinding(binding, BindingType.PROVIDER_INSTANCE, this.scope(binding), binding.getUserSuppliedProvider());
    }

    @Override
    public IndexedBinding visit(ProviderKeyBinding<? extends Object> binding) {
        return new IndexedBinding(binding, BindingType.PROVIDER_KEY, this.scope(binding), binding.getProviderKey());
    }

    @Override
    public IndexedBinding visit(UntargettedBinding<? extends Object> binding) {
        return new IndexedBinding(binding, BindingType.UNTARGETTED, this.scope(binding), null);
    }

    @Override
    public Object visitEagerSingleton() {
        return EAGER_SINGLETON;
    }

    @Override
    public Object visitNoScoping() {
        return Scopes.NO_SCOPE;
    }

    @Override
    public Object visitScope(Scope scope) {
        return scope;
    }

    @Override
    public Object visitScopeAnnotation(Class<? extends Annotation> scopeAnnotation) {
        return scopeAnnotation;
    }

    static class IndexedBinding {
        final String annotationName;
        final Element.Type annotationType;
        final TypeLiteral<?> typeLiteral;
        final Object scope;
        final BindingType type;
        final Object extraEquality;

        IndexedBinding(Binding<?> binding, BindingType type, Object scope, Object extraEquality) {
            this.scope = scope;
            this.type = type;
            this.extraEquality = extraEquality;
            this.typeLiteral = binding.getKey().getTypeLiteral();
            Element annotation = (Element)binding.getKey().getAnnotation();
            this.annotationName = annotation.setName();
            this.annotationType = annotation.type();
        }

        public boolean equals(Object obj) {
            if (!(obj instanceof IndexedBinding)) {
                return false;
            }
            IndexedBinding o = (IndexedBinding)obj;
            return this.type == o.type && Objects.equal(this.scope, o.scope) && this.typeLiteral.equals(o.typeLiteral) && this.annotationType == o.annotationType && this.annotationName.equals(o.annotationName) && Objects.equal(this.extraEquality, o.extraEquality);
        }

        public int hashCode() {
            return Objects.hashCode(new Object[]{this.type, this.scope, this.typeLiteral, this.annotationType, this.annotationName, this.extraEquality});
        }
    }

    static enum BindingType {
        INSTANCE,
        PROVIDER_INSTANCE,
        PROVIDER_KEY,
        LINKED_KEY,
        UNTARGETTED,
        CONSTRUCTOR,
        CONSTANT,
        EXPOSED,
        PROVIDED_BY;

    }
}

