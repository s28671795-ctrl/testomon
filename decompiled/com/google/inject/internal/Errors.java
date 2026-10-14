/*
 * Decompiled with CFR 0.152.
 */
package com.google.inject.internal;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;
import com.google.common.collect.Ordering;
import com.google.inject.Binding;
import com.google.inject.ConfigurationException;
import com.google.inject.CreationException;
import com.google.inject.Injector;
import com.google.inject.Key;
import com.google.inject.ProvisionException;
import com.google.inject.Scope;
import com.google.inject.TypeLiteral;
import com.google.inject.internal.BindingAlreadySetError;
import com.google.inject.internal.ChildBindingAlreadySetError;
import com.google.inject.internal.DuplicateMapKeyError;
import com.google.inject.internal.ErrorId;
import com.google.inject.internal.ErrorsException;
import com.google.inject.internal.GuiceInternal;
import com.google.inject.internal.InternalProvisionException;
import com.google.inject.internal.Messages;
import com.google.inject.internal.MissingConstructorError;
import com.google.inject.internal.MissingImplementationError;
import com.google.inject.internal.ScopeNotFoundError;
import com.google.inject.internal.SourceFormatter;
import com.google.inject.internal.util.SourceProvider;
import com.google.inject.spi.ElementSource;
import com.google.inject.spi.InterceptorBinding;
import com.google.inject.spi.Message;
import com.google.inject.spi.ScopeBinding;
import com.google.inject.spi.TypeConverterBinding;
import com.google.inject.spi.TypeListenerBinding;
import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Formatter;
import java.util.List;
import java.util.Map;
import java.util.Set;

public final class Errors
implements Serializable {
    private final Errors root;
    private final Errors parent;
    private final Object source;
    private List<Message> errors;
    private static final String CONSTRUCTOR_RULES = "Injectable classes must have either one (and only one) constructor annotated with @Inject or a zero-argument constructor that is not private.";

    static <T> T checkNotNull(T reference, String name) {
        if (reference != null) {
            return reference;
        }
        NullPointerException npe = new NullPointerException(name);
        throw new ConfigurationException(ImmutableSet.of(new Message(npe.toString(), npe)));
    }

    static void checkConfiguration(boolean condition, String format, Object ... args2) {
        if (condition) {
            return;
        }
        throw new ConfigurationException(ImmutableSet.of(new Message(Errors.format(format, args2))));
    }

    public Errors() {
        this.root = this;
        this.parent = null;
        this.source = SourceProvider.UNKNOWN_SOURCE;
    }

    public Errors(Object source2) {
        this.root = this;
        this.parent = null;
        this.source = source2;
    }

    private Errors(Errors parent, Object source2) {
        this.root = parent.root;
        this.parent = parent;
        this.source = source2;
    }

    public Errors withSource(Object source2) {
        return source2 == this.source || source2 == SourceProvider.UNKNOWN_SOURCE ? this : new Errors(this, source2);
    }

    public Errors aopDisabled(InterceptorBinding binding) {
        return this.addMessage(ErrorId.AOP_DISABLED, "Binding interceptor is not supported when bytecode generation is disabled. \nInterceptor bound at: %s", binding.getSource());
    }

    public Errors missingImplementation(Key<?> key) {
        return this.addMessage(ErrorId.MISSING_IMPLEMENTATION, "No implementation for %s was bound.", key);
    }

    <T> Errors missingImplementationWithHint(Key<T> key, Injector injector) {
        MissingImplementationError<T> error = new MissingImplementationError<T>(key, injector, this.getSources());
        return this.addMessage(new Message(GuiceInternal.GUICE_INTERNAL, ErrorId.MISSING_IMPLEMENTATION, error));
    }

    public Errors jitDisabled(Key<?> key) {
        return this.addMessage(ErrorId.JIT_DISABLED, "Explicit bindings are required and %s is not explicitly bound.", key);
    }

    public Errors jitDisabledInParent(Key<?> key) {
        return this.addMessage(ErrorId.JIT_DISABLED_IN_PARENT, "Explicit bindings are required and %s would be bound in a parent injector.\nPlease add an explicit binding for it, either in the child or the parent.", key);
    }

    public Errors atInjectRequired(TypeLiteral<?> type) {
        return this.addMessage(new Message(GuiceInternal.GUICE_INTERNAL, ErrorId.MISSING_CONSTRUCTOR, new MissingConstructorError(type, true, this.getSources())));
    }

    public Errors converterReturnedNull(String stringValue, Object source2, TypeLiteral<?> type, TypeConverterBinding typeConverterBinding) {
        return this.addMessage(ErrorId.CONVERTER_RETURNED_NULL, "Received null converting '%s' (bound at %s) to %s\n using %s.", stringValue, Errors.convert(source2), type, typeConverterBinding);
    }

    public Errors conversionTypeError(String stringValue, Object source2, TypeLiteral<?> type, TypeConverterBinding typeConverterBinding, Object converted) {
        return this.addMessage(ErrorId.CONVERSION_TYPE_ERROR, "Type mismatch converting '%s' (bound at %s) to %s\n using %s.\n Converter returned %s.", stringValue, Errors.convert(source2), type, typeConverterBinding, converted);
    }

    public Errors conversionError(String stringValue, Object source2, TypeLiteral<?> type, TypeConverterBinding typeConverterBinding, RuntimeException cause) {
        return this.errorInUserCode(cause, "Error converting '%s' (bound at %s) to %s\n using %s.\n Reason: %s", stringValue, Errors.convert(source2), type, typeConverterBinding, cause);
    }

    public Errors ambiguousTypeConversion(String stringValue, Object source2, TypeLiteral<?> type, TypeConverterBinding a, TypeConverterBinding b) {
        return this.addMessage(ErrorId.AMBIGUOUS_TYPE_CONVERSION, "Multiple converters can convert '%s' (bound at %s) to %s:\n %s and\n %s.\n Please adjust your type converter configuration to avoid overlapping matches.", stringValue, Errors.convert(source2), type, a, b);
    }

    public Errors bindingToProvider() {
        return this.addMessage(ErrorId.BINDING_TO_PROVIDER, "Binding to Provider is not allowed.", new Object[0]);
    }

    public Errors notASubtype(Class<?> implementationType, Class<?> type) {
        return this.addMessage(ErrorId.NOT_A_SUBTYPE, "%s doesn't extend %s.", implementationType, type);
    }

    public Errors recursiveImplementationType() {
        return this.addMessage(ErrorId.RECURSIVE_IMPLEMENTATION_TYPE, "@ImplementedBy points to the same class it annotates.", new Object[0]);
    }

    public Errors recursiveProviderType() {
        return this.addMessage(ErrorId.RECURSIVE_PROVIDER_TYPE, "@ProvidedBy points to the same class it annotates.", new Object[0]);
    }

    public Errors missingRuntimeRetention(Class<? extends Annotation> annotation) {
        return this.addMessage(ErrorId.MISSING_RUNTIME_RETENTION, Errors.format("Please annotate %s with @Retention(RUNTIME).", annotation), new Object[0]);
    }

    public Errors missingScopeAnnotation(Class<? extends Annotation> annotation) {
        return this.addMessage(ErrorId.MISSING_SCOPE_ANNOTATION, Errors.format("Please annotate %s with @ScopeAnnotation.", annotation), new Object[0]);
    }

    public Errors optionalConstructor(Constructor<?> constructor) {
        return this.addMessage(ErrorId.OPTIONAL_CONSTRUCTOR, "%s is annotated @Inject(optional=true), but constructors cannot be optional.", constructor);
    }

    public Errors cannotBindToGuiceType(String simpleName) {
        return this.addMessage(ErrorId.BINDING_TO_GUICE_TYPE, "Binding to core guice framework type is not allowed: %s.", simpleName);
    }

    public Errors scopeNotFound(Class<? extends Annotation> scopeAnnotation) {
        return this.addMessage(new Message(GuiceInternal.GUICE_INTERNAL, ErrorId.SCOPE_NOT_FOUND, new ScopeNotFoundError(scopeAnnotation, this.getSources())));
    }

    public Errors scopeAnnotationOnAbstractType(Class<? extends Annotation> scopeAnnotation, Class<?> type, Object source2) {
        return this.addMessage(ErrorId.SCOPE_ANNOTATION_ON_ABSTRACT_TYPE, "%s is annotated with %s, but scope annotations are not supported for abstract types.\n Bound at %s.", type, scopeAnnotation, Errors.convert(source2));
    }

    public Errors misplacedBindingAnnotation(Member member, Annotation bindingAnnotation) {
        return this.addMessage(ErrorId.MISPLACED_BINDING_ANNOTATION, "%s is annotated with %s, but binding annotations should be applied to its parameters instead.", member, bindingAnnotation);
    }

    public Errors missingConstructor(TypeLiteral<?> type) {
        return this.addMessage(new Message(GuiceInternal.GUICE_INTERNAL, ErrorId.MISSING_CONSTRUCTOR, new MissingConstructorError(type, false, this.getSources())));
    }

    public Errors tooManyConstructors(Class<?> implementation) {
        return this.addMessage(ErrorId.TOO_MANY_CONSTRUCTORS, "%s has more than one constructor annotated with @Inject. %s", implementation, CONSTRUCTOR_RULES);
    }

    public Errors constructorNotDefinedByType(Constructor<?> constructor, TypeLiteral<?> type) {
        return this.addMessage(ErrorId.CONSTRUCTOR_NOT_DEFINED_BY_TYPE, "%s does not define %s", type, constructor);
    }

    public <K, V> Errors duplicateMapKey(Key<Map<K, V>> mapKey, Multimap<K, Binding<V>> duplicates) {
        return this.addMessage(new Message(GuiceInternal.GUICE_INTERNAL, ErrorId.DUPLICATE_MAP_KEY, new DuplicateMapKeyError<K, V>(mapKey, duplicates, this.getSources())));
    }

    public Errors duplicateScopes(ScopeBinding existing, Class<? extends Annotation> annotationType, Scope scope) {
        return this.addMessage(ErrorId.DUPLICATE_SCOPES, "Scope %s is already bound to %s at %s.\n Cannot bind %s.", existing.getScope(), annotationType, existing.getSource(), scope);
    }

    public Errors voidProviderMethod() {
        return this.addMessage(ErrorId.VOID_PROVIDER_METHOD, "Provider methods must return a value. Do not return void.", new Object[0]);
    }

    public Errors missingConstantValues() {
        return this.addMessage(ErrorId.MISSING_CONSTANT_VALUES, "Missing constant value. Please call to(...).", new Object[0]);
    }

    public Errors cannotInjectInnerClass(Class<?> type) {
        return this.addMessage(ErrorId.INJECT_INNER_CLASS, "Injecting into inner classes is not supported.  Please use a 'static' class (top-level or nested) instead of %s.", type);
    }

    public Errors cannotInjectLocalClass(Class<?> type) {
        return this.addMessage(ErrorId.INJECT_LOCAL_CLASS, "Injecting into local classes is not supported.  Please use a non-local class instead of %s.", type);
    }

    public Errors duplicateBindingAnnotations(Member member, Class<? extends Annotation> a, Class<? extends Annotation> b) {
        return this.addMessage(ErrorId.DUPLICATE_BINDING_ANNOTATIONS, "%s has more than one annotation annotated with @BindingAnnotation: %s and %s", member, a, b);
    }

    public Errors staticInjectionOnInterface(Class<?> clazz) {
        return this.addMessage(ErrorId.STATIC_INJECTION_ON_INTERFACE, "%s is an interface, but interfaces have no static injection points.", clazz);
    }

    public Errors cannotInjectFinalField(Field field2) {
        return this.addMessage(ErrorId.INJECT_FINAL_FIELD, "Injected field %s cannot be final.", field2);
    }

    public Errors atTargetIsMissingParameter(Annotation bindingAnnotation, String parameterName, Class<?> clazz) {
        return this.addMessage(ErrorId.AT_TARGET_IS_MISSING_PARAMETER, "Binding annotation %s must have PARAMETER listed in its @Targets. It was used on constructor parameter %s in %s.", bindingAnnotation, parameterName, clazz);
    }

    public Errors cannotInjectAbstractMethod(Method method2) {
        return this.addMessage(ErrorId.INJECT_ABSTRACT_METHOD, "Injected method %s cannot be abstract.", method2);
    }

    public Errors cannotInjectMethodWithTypeParameters(Method method2) {
        return this.addMessage(ErrorId.INJECT_METHOD_WITH_TYPE_PARAMETER, "Injected method %s cannot declare type parameters of its own.", method2);
    }

    public Errors duplicateScopeAnnotations(Class<? extends Annotation> a, Class<? extends Annotation> b) {
        return this.addMessage(ErrorId.DUPLICATE_SCOPE_ANNOTATIONS, "More than one scope annotation was found: %s and %s.", a, b);
    }

    public Errors recursiveBinding(Key<?> key, Key<?> linkedKey) {
        return this.addMessage(ErrorId.RECURSIVE_BINDING, "Binding points to itself. Key: %s", Messages.convert(key));
    }

    Errors bindingAlreadySet(Binding<?> binding, Binding<?> original) {
        BindingAlreadySetError error = new BindingAlreadySetError(binding, original, this.getSources());
        return this.addMessage(new Message(GuiceInternal.GUICE_INTERNAL, ErrorId.BINDING_ALREADY_SET, error));
    }

    public Errors bindingAlreadySet(Key<?> key, Object source2) {
        return this.addMessage(ErrorId.BINDING_ALREADY_SET, "A binding to %s was already configured at %s.", key, Errors.convert(source2));
    }

    public Errors jitBindingAlreadySet(Key<?> key) {
        return this.addMessage(ErrorId.JIT_BINDING_ALREADY_SET, "A just-in-time binding to %s was already configured on a parent injector.", key);
    }

    public Errors childBindingAlreadySet(Key<?> key, Set<Object> sources) {
        Message message = new Message(GuiceInternal.GUICE_INTERNAL, ErrorId.CHILD_BINDING_ALREADY_SET, new ChildBindingAlreadySetError(key, sources, this.getSources()));
        return this.addMessage(message);
    }

    public Errors errorCheckingDuplicateBinding(Key<?> key, Object source2, Throwable t2) {
        return this.addMessage(ErrorId.OTHER, "A binding to %s was already configured at %s and an error was thrown while checking duplicate bindings.  Error: %s", key, Errors.convert(source2), t2);
    }

    public Errors requestInjectionWithDifferentTypes(Object instance, TypeLiteral<?> type1, Object type1Source, TypeLiteral<?> type2) {
        return this.addMessage(ErrorId.REQUEST_INJECTION_WITH_DIFFERENT_TYPES, "Cannot request injection on one instance with two different types. requestInjection was already called for instance %s at %s (with type %s), which is different than type %s.", instance.getClass().getName() + "@" + System.identityHashCode(instance), type1Source, type1, type2);
    }

    public Errors errorNotifyingTypeListener(TypeListenerBinding listener, TypeLiteral<?> type, Throwable cause) {
        return this.errorInUserCode(cause, "Error notifying TypeListener %s (bound at %s) of %s.\n Reason: %s", listener.getListener(), Errors.convert(listener.getSource()), type, cause);
    }

    public Errors exposedButNotBound(Key<?> key) {
        return this.addMessage(ErrorId.EXPOSED_BUT_NOT_BOUND, "Could not expose() %s, it must be explicitly bound.", key);
    }

    public Errors keyNotFullySpecified(TypeLiteral<?> typeLiteral) {
        return this.addMessage(ErrorId.KEY_NOT_FULLY_SPECIFIED, "%s cannot be used as a key; It is not fully specified.", typeLiteral);
    }

    public Errors errorEnhancingClass(Class<?> clazz, Throwable cause) {
        return this.errorInUserCode(cause, "Unable to method intercept: %s", clazz);
    }

    public static Collection<Message> getMessagesFromThrowable(Throwable throwable) {
        if (throwable instanceof ProvisionException) {
            return ((ProvisionException)throwable).getErrorMessages();
        }
        if (throwable instanceof ConfigurationException) {
            return ((ConfigurationException)throwable).getErrorMessages();
        }
        if (throwable instanceof CreationException) {
            return ((CreationException)throwable).getErrorMessages();
        }
        return ImmutableSet.of();
    }

    public Errors errorInUserCode(Throwable cause, String messageFormat, Object ... arguments) {
        Collection<Message> messages = Errors.getMessagesFromThrowable(cause);
        if (!messages.isEmpty()) {
            return this.merge(messages);
        }
        return this.addMessage(ErrorId.ERROR_IN_USER_CODE, cause, messageFormat, arguments);
    }

    public Errors cannotInjectRawProvider() {
        return this.addMessage(ErrorId.INJECT_RAW_PROVIDER, "Cannot inject a Provider that has no type parameter", new Object[0]);
    }

    public Errors cannotInjectRawMembersInjector() {
        return this.addMessage(ErrorId.INJECT_RAW_MEMBERS_INJECTOR, "Cannot inject a MembersInjector that has no type parameter", new Object[0]);
    }

    public Errors cannotInjectTypeLiteralOf(Type unsupportedType) {
        return this.addMessage(ErrorId.OTHER, "Cannot inject a TypeLiteral of %s", unsupportedType);
    }

    public Errors cannotInjectRawTypeLiteral() {
        return this.addMessage(ErrorId.INJECT_RAW_TYPE_LITERAL, "Cannot inject a TypeLiteral that has no type parameter", new Object[0]);
    }

    public void throwCreationExceptionIfErrorsExist() {
        if (!this.hasErrors()) {
            return;
        }
        CreationException exception = new CreationException(this.getMessages());
        throw exception;
    }

    public void throwConfigurationExceptionIfErrorsExist() {
        if (!this.hasErrors()) {
            return;
        }
        ConfigurationException exception = new ConfigurationException(this.getMessages());
        throw exception;
    }

    public void throwProvisionExceptionIfErrorsExist() {
        if (!this.hasErrors()) {
            return;
        }
        ProvisionException exception = new ProvisionException(this.getMessages());
        throw exception;
    }

    public Errors merge(Collection<Message> messages) {
        List<Object> sources = this.getSources();
        for (Message message : messages) {
            this.addMessage(Messages.mergeSources(sources, message));
        }
        return this;
    }

    public Errors merge(Errors moreErrors) {
        if (moreErrors.root == this.root || moreErrors.root.errors == null) {
            return this;
        }
        this.merge(moreErrors.root.errors);
        return this;
    }

    public Errors merge(InternalProvisionException ipe) {
        this.merge(ipe.getErrors());
        return this;
    }

    private List<Object> getSources() {
        ArrayList<Object> sources = Lists.newArrayList();
        Errors e = this;
        while (e != null) {
            if (e.source != SourceProvider.UNKNOWN_SOURCE) {
                sources.add(0, e.source);
            }
            e = e.parent;
        }
        return sources;
    }

    public void throwIfNewErrors(int expectedSize) throws ErrorsException {
        if (this.size() == expectedSize) {
            return;
        }
        throw this.toException();
    }

    public ErrorsException toException() {
        return new ErrorsException(this);
    }

    public boolean hasErrors() {
        return this.root.errors != null;
    }

    public Errors addMessage(String messageFormat, Object ... arguments) {
        return this.addMessage(ErrorId.OTHER, null, messageFormat, arguments);
    }

    public Errors addMessage(ErrorId errorId, String messageFormat, Object ... arguments) {
        return this.addMessage(errorId, null, messageFormat, arguments);
    }

    private Errors addMessage(ErrorId errorId, Throwable cause, String messageFormat, Object ... arguments) {
        this.addMessage(Messages.create(errorId, cause, this.getSources(), messageFormat, arguments));
        return this;
    }

    public Errors addMessage(Message message) {
        if (this.root.errors == null) {
            this.root.errors = Lists.newArrayList();
        }
        this.root.errors.add(message);
        return this;
    }

    public static String format(String messageFormat, Object ... arguments) {
        return Messages.format(messageFormat, arguments);
    }

    public List<Message> getMessages() {
        if (this.root.errors == null) {
            return ImmutableList.of();
        }
        return new Ordering<Message>(this){

            @Override
            public int compare(Message a, Message b) {
                return a.getSource().compareTo(b.getSource());
            }
        }.sortedCopy(this.root.errors);
    }

    public int size() {
        return this.root.errors == null ? 0 : this.root.errors.size();
    }

    public static Object convert(Object o) {
        return Messages.convert(o);
    }

    public static Object convert(Object o, ElementSource source2) {
        return Messages.convert(o, source2);
    }

    public static void formatSource(Formatter formatter, Object source2) {
        formatter.format("  ", new Object[0]);
        new SourceFormatter(source2, formatter, false).format();
    }
}

