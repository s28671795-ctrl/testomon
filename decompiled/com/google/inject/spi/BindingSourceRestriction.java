/*
 * Decompiled with CFR 0.152.
 */
package com.google.inject.spi;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Iterables;
import com.google.inject.Binding;
import com.google.inject.Key;
import com.google.inject.RestrictedBindingSource;
import com.google.inject.internal.Errors;
import com.google.inject.internal.GuiceInternal;
import com.google.inject.spi.DefaultElementVisitor;
import com.google.inject.spi.Element;
import com.google.inject.spi.ElementSource;
import com.google.inject.spi.Message;
import com.google.inject.spi.ModuleSource;
import com.google.inject.spi.PrivateElements;
import java.lang.annotation.Annotation;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;
import java.util.Formatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public final class BindingSourceRestriction {
    private static final Logger logger = Logger.getLogger(RestrictedBindingSource.class.getName());

    private BindingSourceRestriction() {
    }

    public static Optional<String> getMissingImplementationSuggestion(GuiceInternal guiceInternal, Key<?> key) {
        Preconditions.checkNotNull(guiceInternal);
        RestrictedBindingSource restriction = BindingSourceRestriction.getRestriction(key);
        if (restriction == null) {
            return Optional.empty();
        }
        return Optional.of(String.format("\nHint: This key is restricted and cannot be bound directly. Restriction explanation: %s", restriction.explanation()));
    }

    public static ImmutableList<Message> check(GuiceInternal guiceInternal, List<Element> elements) {
        Preconditions.checkNotNull(guiceInternal);
        ImmutableList<Message> errorMessages = BindingSourceRestriction.check(elements);
        elements.forEach(BindingSourceRestriction::clear);
        return errorMessages;
    }

    private static ImmutableList<Message> check(List<Element> elements) {
        ImmutableList.Builder errorMessagesBuilder = ImmutableList.builder();
        for (Element element : elements) {
            errorMessagesBuilder.addAll(BindingSourceRestriction.check(element));
        }
        return errorMessagesBuilder.build();
    }

    private static ImmutableList<Message> check(Element element) {
        return element.acceptVisitor(new DefaultElementVisitor<ImmutableList<Message>>(){

            @Override
            protected ImmutableList<Message> visitOther(Element element) {
                return ImmutableList.of();
            }

            @Override
            public <T> ImmutableList<Message> visit(Binding<T> binding) {
                Optional errorMessage = BindingSourceRestriction.check(binding);
                if (errorMessage.isPresent()) {
                    return ImmutableList.of((Message)errorMessage.get());
                }
                return ImmutableList.of();
            }

            @Override
            public ImmutableList<Message> visit(PrivateElements privateElements) {
                return BindingSourceRestriction.check(privateElements.getElements());
            }
        });
    }

    private static Optional<Message> check(Binding<?> binding) {
        Key<?> key = binding.getKey();
        ElementSource elementSource = (ElementSource)binding.getSource();
        RestrictedBindingSource restriction = BindingSourceRestriction.getRestriction(key);
        if (restriction == null) {
            return Optional.empty();
        }
        ImmutableSet<Class<? extends Annotation>> permits = BindingSourceRestriction.getAllPermits(elementSource);
        ImmutableSet<Class<? extends Annotation>> acceptablePermits = ImmutableSet.copyOf(restriction.permits());
        boolean bindingPermitted = permits.stream().anyMatch(acceptablePermits::contains);
        if (bindingPermitted || BindingSourceRestriction.isExempt(elementSource, restriction.exemptModules())) {
            return Optional.empty();
        }
        String violationMessage = BindingSourceRestriction.getViolationMessage(key, restriction.explanation(), acceptablePermits, key.getAnnotationType() != null);
        if (restriction.restrictionLevel() == RestrictedBindingSource.RestrictionLevel.WARNING) {
            Formatter sourceFormatter = new Formatter();
            Errors.formatSource(sourceFormatter, elementSource);
            logger.log(Level.WARNING, violationMessage + "\n" + sourceFormatter);
            return Optional.empty();
        }
        return Optional.of(new Message(elementSource, violationMessage));
    }

    private static String getViolationMessage(Key<?> key, String explanation, ImmutableSet<Class<? extends Annotation>> acceptablePermits, boolean annotationRestricted) {
        return String.format("Unable to bind key: %s. One of the modules that created this binding has to be annotated with one of %s, because the key's %s is annotated with @RestrictedBindingSource. %s", key, acceptablePermits.stream().map(a -> "@" + a.getName()).collect(Collectors.toList()), annotationRestricted ? "annotation" : "type", explanation);
    }

    private static ImmutableSet<Class<? extends Annotation>> getAllPermits(ElementSource elementSource) {
        ImmutableSet.Builder permitsBuilder = ImmutableSet.builder();
        permitsBuilder.addAll(elementSource.moduleSource.getPermitMap().getPermits(elementSource));
        if (elementSource.scanner != null) {
            BindingSourceRestriction.getPermits(elementSource.scanner.getClass()).forEach(permitsBuilder::add);
        }
        if (elementSource.getOriginalElementSource() != null && elementSource.trustedOriginalElementSource) {
            permitsBuilder.addAll(BindingSourceRestriction.getAllPermits(elementSource.getOriginalElementSource()));
        }
        return permitsBuilder.build();
    }

    private static boolean isExempt(ElementSource elementSource, String exemptModulesRegex) {
        if (exemptModulesRegex.isEmpty()) {
            return false;
        }
        Pattern exemptModulePattern = Pattern.compile(exemptModulesRegex);
        return StreamSupport.stream(BindingSourceRestriction.getAllModules(elementSource).spliterator(), false).anyMatch(moduleName -> exemptModulePattern.matcher((CharSequence)moduleName).matches());
    }

    private static Iterable<String> getAllModules(ElementSource elementSource) {
        List<String> modules = elementSource.getModuleClassNames();
        if (elementSource.getOriginalElementSource() == null || !elementSource.trustedOriginalElementSource) {
            return modules;
        }
        return Iterables.concat(modules, BindingSourceRestriction.getAllModules(elementSource.getOriginalElementSource()));
    }

    private static void clear(Element element) {
        element.acceptVisitor(new DefaultElementVisitor<Void>(){

            @Override
            protected Void visitOther(Element element) {
                Object source2 = element.getSource();
                if (source2 instanceof ElementSource) {
                    BindingSourceRestriction.clear((ElementSource)source2);
                }
                return null;
            }

            @Override
            public Void visit(PrivateElements privateElements) {
                privateElements.getElements().forEach(x$0 -> BindingSourceRestriction.clear(x$0));
                return null;
            }
        });
    }

    private static void clear(ElementSource elementSource) {
        while (elementSource != null) {
            elementSource.moduleSource.getPermitMap().clear();
            elementSource = elementSource.getOriginalElementSource();
        }
    }

    private static RestrictedBindingSource getRestriction(Key<?> key) {
        return key.getAnnotationType() == null ? key.getTypeLiteral().getRawType().getAnnotation(RestrictedBindingSource.class) : key.getAnnotationType().getAnnotation(RestrictedBindingSource.class);
    }

    private static Stream<Class<? extends Annotation>> getPermits(Class<?> clazz) {
        Stream<Annotation> annotations = Arrays.stream(clazz.getAnnotations());
        if (clazz.getAnnotatedSuperclass() != null) {
            annotations = Stream.concat(annotations, Arrays.stream(clazz.getAnnotatedSuperclass().getAnnotations()));
        }
        return annotations.map(Annotation::annotationType).filter(a -> a.isAnnotationPresent(RestrictedBindingSource.Permit.class));
    }

    static final class PermitMapConstruction {
        final Map<ModuleSource, ImmutableSet<Class<? extends Annotation>>> modulePermits = new HashMap<ModuleSource, ImmutableSet<Class<? extends Annotation>>>();
        ImmutableSet<Class<? extends Annotation>> currentModulePermits = ImmutableSet.of();
        final Deque<ImmutableSet<Class<? extends Annotation>>> modulePermitsStack = new ArrayDeque<ImmutableSet<Class<? extends Annotation>>>();
        final PermitMapImpl permitMap = new PermitMapImpl();

        PermitMapConstruction() {
        }

        PermitMap getPermitMap() {
            return this.permitMap;
        }

        void restoreCurrentModulePermits(ModuleSource moduleSource) {
            this.currentModulePermits = this.modulePermits.get(moduleSource);
        }

        void pushModule(Class<?> module, ModuleSource moduleSource) {
            List newModulePermits = BindingSourceRestriction.getPermits(module).filter(permit -> !this.currentModulePermits.contains(permit)).collect(Collectors.toList());
            this.modulePermitsStack.push(this.currentModulePermits);
            if (!newModulePermits.isEmpty()) {
                this.currentModulePermits = ((ImmutableSet.Builder)((ImmutableSet.Builder)ImmutableSet.builder().addAll(this.currentModulePermits)).addAll((Iterable)newModulePermits)).build();
            }
            this.modulePermits.put(moduleSource, this.currentModulePermits);
        }

        void popModule() {
            this.currentModulePermits = this.modulePermitsStack.pop();
        }

        void finish() {
            this.permitMap.modulePermits = this.modulePermits;
        }

        @VisibleForTesting
        static boolean isElementSourceCleared(ElementSource elementSource) {
            PermitMapImpl permitMap = (PermitMapImpl)elementSource.moduleSource.getPermitMap();
            return permitMap.modulePermits == null;
        }

        private static final class PermitMapImpl
        implements PermitMap {
            Map<ModuleSource, ImmutableSet<Class<? extends Annotation>>> modulePermits;

            private PermitMapImpl() {
            }

            @Override
            public ImmutableSet<Class<? extends Annotation>> getPermits(ElementSource elementSource) {
                return this.modulePermits.get(elementSource.moduleSource);
            }

            @Override
            public void clear() {
                this.modulePermits = null;
            }
        }
    }

    static interface PermitMap {
        public ImmutableSet<Class<? extends Annotation>> getPermits(ElementSource var1);

        public void clear();
    }
}

