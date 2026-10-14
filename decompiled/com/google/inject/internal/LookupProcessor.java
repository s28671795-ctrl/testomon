/*
 * Decompiled with CFR 0.152.
 */
package com.google.inject.internal;

import com.google.inject.Provider;
import com.google.inject.internal.AbstractProcessor;
import com.google.inject.internal.Errors;
import com.google.inject.internal.ErrorsException;
import com.google.inject.internal.MembersInjectorImpl;
import com.google.inject.spi.MembersInjectorLookup;
import com.google.inject.spi.ProviderLookup;

final class LookupProcessor
extends AbstractProcessor {
    LookupProcessor(Errors errors2) {
        super(errors2);
    }

    @Override
    public <T> Boolean visit(MembersInjectorLookup<T> lookup) {
        try {
            MembersInjectorImpl<T> membersInjector = this.injector.membersInjectorStore.get(lookup.getType(), this.errors);
            lookup.initializeDelegate(membersInjector);
            this.injector.getBindingData().putMembersInjectorLookup(lookup);
        }
        catch (ErrorsException e) {
            this.errors.merge(e.getErrors());
        }
        return true;
    }

    @Override
    public <T> Boolean visit(ProviderLookup<T> lookup) {
        try {
            Provider<T> provider = this.injector.getProviderOrThrow(lookup.getDependency(), this.errors);
            lookup.initializeDelegate(provider);
            this.injector.getBindingData().putProviderLookup(lookup);
        }
        catch (ErrorsException e) {
            this.errors.merge(e.getErrors());
        }
        return true;
    }
}

