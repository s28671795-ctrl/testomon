/*
 * Decompiled with CFR 0.152.
 */
package com.google.inject.internal;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.inject.Guice;
import com.google.inject.Key;
import com.google.inject.MembersInjector;
import com.google.inject.Provides;
import com.google.inject.ProvisionException;
import com.google.inject.TypeLiteral;
import com.google.inject.internal.ErrorId;
import com.google.inject.internal.Errors;
import com.google.inject.internal.InternalFlags;
import com.google.inject.internal.Messages;
import com.google.inject.internal.SourceFormatter;
import com.google.inject.internal.util.SourceProvider;
import com.google.inject.internal.util.StackTraceElements;
import com.google.inject.spi.Dependency;
import com.google.inject.spi.InjectionListener;
import com.google.inject.spi.Message;
import jakarta.inject.Provider;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class InternalProvisionException
extends Exception {
    private static final Logger logger = Logger.getLogger(Guice.class.getName());
    private static final Set<Dependency<?>> warnedDependencies = Collections.newSetFromMap(new ConcurrentHashMap());
    private final List<Object> sourcesToPrepend = new ArrayList<Object>();
    private final ImmutableList<Message> errors;

    public static InternalProvisionException circularDependenciesDisabled(Class<?> expectedType) {
        return InternalProvisionException.create(ErrorId.CIRCULAR_PROXY_DISABLED, "Found a circular dependency involving %s, and circular dependencies are disabled.", expectedType);
    }

    public static InternalProvisionException cannotProxyClass(Class<?> expectedType) {
        return InternalProvisionException.create(ErrorId.CAN_NOT_PROXY_CLASS, "Tried proxying %s to support a circular dependency, but it is not an interface.", expectedType);
    }

    public static InternalProvisionException create(ErrorId errorId, String format, Object ... arguments) {
        return new InternalProvisionException(Messages.create(errorId, format, arguments));
    }

    public static InternalProvisionException errorInUserCode(ErrorId errorId, Throwable cause, String messageFormat, Object ... arguments) {
        Collection<Message> messages = Errors.getMessagesFromThrowable(cause);
        if (!messages.isEmpty()) {
            return new InternalProvisionException(messages);
        }
        return new InternalProvisionException(Messages.create(errorId, cause, messageFormat, arguments));
    }

    public static InternalProvisionException subtypeNotProvided(Class<? extends Provider<?>> providerType, Class<?> type) {
        return InternalProvisionException.create(ErrorId.SUBTYPE_NOT_PROVIDED, "%s doesn't provide instances of %s.", providerType, type);
    }

    public static InternalProvisionException errorInProvider(Throwable cause) {
        return InternalProvisionException.errorInUserCode(ErrorId.ERROR_IN_CUSTOM_PROVIDER, cause, "%s", cause);
    }

    public static InternalProvisionException errorInjectingMethod(Throwable cause) {
        return InternalProvisionException.errorInUserCode(ErrorId.ERROR_INJECTING_METHOD, cause, "%s", cause);
    }

    public static InternalProvisionException errorInjectingConstructor(Throwable cause) {
        return InternalProvisionException.errorInUserCode(ErrorId.ERROR_INJECTING_CONSTRUCTOR, cause, "%s", cause);
    }

    public static InternalProvisionException errorInUserInjector(MembersInjector<?> listener, TypeLiteral<?> type, RuntimeException cause) {
        return InternalProvisionException.errorInUserCode(ErrorId.ERROR_IN_USER_INJECTOR, cause, "Error injecting %s using %s.\n Reason: %s", type, listener, cause);
    }

    public static InternalProvisionException jitDisabled(Key<?> key) {
        return InternalProvisionException.create(ErrorId.JIT_DISABLED, "Explicit bindings are required and %s is not explicitly bound.", key);
    }

    public static InternalProvisionException errorNotifyingInjectionListener(InjectionListener<?> listener, TypeLiteral<?> type, RuntimeException cause) {
        return InternalProvisionException.errorInUserCode(ErrorId.OTHER, cause, "Error notifying InjectionListener %s of %s.\n Reason: %s", listener, type, cause);
    }

    static void onNullInjectedIntoNonNullableDependency(Object source2, Dependency<?> dependency) throws InternalProvisionException {
        Method annotated;
        if (dependency.getInjectionPoint().getMember() instanceof Method && (annotated = (Method)dependency.getInjectionPoint().getMember()).isAnnotationPresent(Provides.class)) {
            switch (InternalFlags.getNullableProvidesOption()) {
                case ERROR: {
                    break;
                }
                case IGNORE: {
                    return;
                }
                case WARN: {
                    if (warnedDependencies.add(dependency)) {
                        logger.log(Level.WARNING, "Guice injected null into {0} (a {1}), please mark it @Nullable. Use -Dguice_check_nullable_provides_params=ERROR to turn this into an error.", new Object[]{SourceFormatter.getParameterName(dependency), Messages.convert(dependency.getKey())});
                    }
                    return;
                }
            }
        }
        String parameterName = dependency.getParameterIndex() != -1 ? SourceFormatter.getParameterName(dependency) : "";
        Object memberStackTraceElement = StackTraceElements.forMember(dependency.getInjectionPoint().getMember());
        Object formattedDependency = parameterName.isEmpty() ? memberStackTraceElement : "the " + parameterName + " of " + memberStackTraceElement;
        throw InternalProvisionException.create(ErrorId.NULL_INJECTED_INTO_NON_NULLABLE, "null returned by binding at %s\n but %s is not @Nullable", source2, formattedDependency).addSource(source2);
    }

    InternalProvisionException(Message error) {
        this(ImmutableList.of(error));
    }

    private InternalProvisionException(Iterable<Message> errors2) {
        this.errors = ImmutableList.copyOf(errors2);
        Preconditions.checkArgument(!this.errors.isEmpty(), "Can't create a provision exception with no errors");
    }

    InternalProvisionException addSource(Object source2) {
        if (source2 == SourceProvider.UNKNOWN_SOURCE) {
            return this;
        }
        int sz = this.sourcesToPrepend.size();
        if (sz > 0 && this.sourcesToPrepend.get(sz - 1) == source2) {
            return this;
        }
        this.sourcesToPrepend.add(source2);
        return this;
    }

    ImmutableList<Message> getErrors() {
        ImmutableList.Builder builder = ImmutableList.builder();
        List<Object> newSources = Lists.reverse(this.sourcesToPrepend);
        for (Message error : this.errors) {
            builder.add(Messages.mergeSources(newSources, error));
        }
        return builder.build();
    }

    public ProvisionException toProvisionException() {
        ProvisionException exception = new ProvisionException(this.getErrors());
        return exception;
    }
}

