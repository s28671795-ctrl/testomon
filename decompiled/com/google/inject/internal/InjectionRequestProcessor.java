/*
 * Decompiled with CFR 0.152.
 */
package com.google.inject.internal;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.inject.ConfigurationException;
import com.google.inject.Stage;
import com.google.inject.internal.AbstractProcessor;
import com.google.inject.internal.Errors;
import com.google.inject.internal.Initializable;
import com.google.inject.internal.Initializer;
import com.google.inject.internal.InjectorImpl;
import com.google.inject.internal.InternalContext;
import com.google.inject.internal.InternalProvisionException;
import com.google.inject.internal.SingleMemberInjector;
import com.google.inject.spi.InjectionPoint;
import com.google.inject.spi.InjectionRequest;
import com.google.inject.spi.StaticInjectionRequest;
import java.util.List;
import java.util.Set;

final class InjectionRequestProcessor
extends AbstractProcessor {
    private final List<StaticInjection> staticInjections = Lists.newArrayList();
    private final Initializer initializer;

    InjectionRequestProcessor(Errors errors2, Initializer initializer) {
        super(errors2);
        this.initializer = initializer;
    }

    @Override
    public Boolean visit(StaticInjectionRequest request) {
        this.staticInjections.add(new StaticInjection(this.injector, request));
        this.injector.getBindingData().putStaticInjectionRequest(request);
        return true;
    }

    @Override
    public Boolean visit(InjectionRequest<?> request) {
        Set injectionPoints;
        try {
            injectionPoints = request.getInjectionPoints();
        }
        catch (ConfigurationException e) {
            this.errors.merge(e.getErrorMessages());
            injectionPoints = (Set)e.getPartialValue();
        }
        this.requestInjection(request, injectionPoints, this.errors);
        this.injector.getBindingData().putInjectionRequest(new InjectionRequest<Object>(request.getSource(), request.getType(), null));
        return true;
    }

    private <T> void requestInjection(InjectionRequest<T> request, Set<InjectionPoint> injectionPoints, Errors errors2) {
        Initializable<T> unused2 = this.initializer.requestInjection(this.injector, request.getType(), request.getInstance(), null, request.getSource(), injectionPoints, errors2);
    }

    void validate() {
        for (StaticInjection staticInjection : this.staticInjections) {
            staticInjection.validate();
        }
    }

    void injectMembers() {
        for (StaticInjection staticInjection : this.staticInjections) {
            staticInjection.injectMembers();
        }
    }

    private class StaticInjection {
        final InjectorImpl injector;
        final Object source;
        final StaticInjectionRequest request;
        ImmutableList<SingleMemberInjector> memberInjectors;

        public StaticInjection(InjectorImpl injector, StaticInjectionRequest request) {
            this.injector = injector;
            this.source = request.getSource();
            this.request = request;
        }

        void validate() {
            Set injectionPoints;
            Errors errorsForMember = InjectionRequestProcessor.this.errors.withSource(this.source);
            try {
                injectionPoints = this.request.getInjectionPoints();
            }
            catch (ConfigurationException e) {
                errorsForMember.merge(e.getErrorMessages());
                injectionPoints = (Set)e.getPartialValue();
            }
            this.memberInjectors = injectionPoints != null ? this.injector.membersInjectorStore.getInjectors(injectionPoints, errorsForMember) : ImmutableList.of();
            InjectionRequestProcessor.this.errors.merge(errorsForMember);
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        void injectMembers() {
            try (InternalContext context = this.injector.enterContext();){
                boolean isStageTool = this.injector.options.stage == Stage.TOOL;
                for (SingleMemberInjector memberInjector : this.memberInjectors) {
                    if (isStageTool && !memberInjector.getInjectionPoint().isToolable()) continue;
                    try {
                        memberInjector.inject(context, null);
                    }
                    catch (InternalProvisionException e) {
                        InjectionRequestProcessor.this.errors.merge(e);
                    }
                }
            }
        }
    }
}

