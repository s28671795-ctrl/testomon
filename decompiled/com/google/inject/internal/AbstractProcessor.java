/*
 * Decompiled with CFR 0.152.
 */
package com.google.inject.internal;

import com.google.inject.internal.Errors;
import com.google.inject.internal.InjectorImpl;
import com.google.inject.internal.InjectorShell;
import com.google.inject.spi.DefaultElementVisitor;
import com.google.inject.spi.Element;
import java.util.List;

abstract class AbstractProcessor
extends DefaultElementVisitor<Boolean> {
    protected Errors errors;
    protected InjectorImpl injector;

    protected AbstractProcessor(Errors errors2) {
        this.errors = errors2;
    }

    public void process(Iterable<InjectorShell> isolatedInjectorBuilders) {
        for (InjectorShell injectorShell : isolatedInjectorBuilders) {
            this.process(injectorShell.getInjector(), injectorShell.getElements());
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void process(InjectorImpl injector, List<Element> elements) {
        Errors errorsAnyElement = this.errors;
        this.injector = injector;
        try {
            elements.removeIf(e -> {
                this.errors = errorsAnyElement.withSource(e.getSource());
                return e.acceptVisitor(this);
            });
        }
        finally {
            this.errors = errorsAnyElement;
            this.injector = null;
        }
    }

    @Override
    protected Boolean visitOther(Element element) {
        return false;
    }
}

