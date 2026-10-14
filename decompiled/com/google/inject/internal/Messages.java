/*
 * Decompiled with CFR 0.152.
 */
package com.google.inject.internal;

import com.google.common.base.Equivalence;
import com.google.common.base.Objects;
import com.google.common.base.Throwables;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Maps;
import com.google.inject.Key;
import com.google.inject.internal.ErrorId;
import com.google.inject.internal.InternalFlags;
import com.google.inject.internal.PackageNameCompressor;
import com.google.inject.internal.SourceFormatter;
import com.google.inject.internal.util.Classes;
import com.google.inject.spi.ElementSource;
import com.google.inject.spi.ErrorDetail;
import com.google.inject.spi.Message;
import java.lang.reflect.Member;
import java.util.Arrays;
import java.util.Collection;
import java.util.Formatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public final class Messages {
    private static final Collection<Converter<?>> converters = ImmutableList.of(new Converter<Class>(Class.class){

        @Override
        public String toString(Class c) {
            return c.getName();
        }
    }, new Converter<Member>(Member.class){

        @Override
        public String toString(Member member) {
            return Classes.toString(member);
        }
    }, new Converter<Key>(Key.class){

        @Override
        public String toString(Key key) {
            if (key.getAnnotationType() != null) {
                return key.getTypeLiteral() + " annotated with " + (key.getAnnotation() != null ? key.getAnnotation() : key.getAnnotationType());
            }
            return key.getTypeLiteral().toString();
        }
    });

    private Messages() {
    }

    static Message mergeSources(List<Object> sources, Message message) {
        List<Object> messageSources = message.getSources();
        if (!sources.isEmpty() && !messageSources.isEmpty() && Objects.equal(messageSources.get(0), sources.get(sources.size() - 1))) {
            messageSources = messageSources.subList(1, messageSources.size());
        }
        return message.withSource((List<Object>)((Object)((ImmutableList.Builder)((ImmutableList.Builder)ImmutableList.builder().addAll(sources)).addAll(messageSources)).build()));
    }

    public static String format(String messageFormat, Object ... arguments) {
        for (int i = 0; i < arguments.length; ++i) {
            arguments[i] = Messages.convert(arguments[i]);
        }
        return String.format(messageFormat, arguments);
    }

    public static String formatMessages(String heading, Collection<Message> errorMessages) {
        Formatter fmt = new Formatter().format(heading, new Object[0]).format(":\n\n", new Object[0]);
        int index = 1;
        boolean displayCauses = Messages.getOnlyCause(errorMessages) == null;
        List<Object> remainingErrors = errorMessages.stream().map(Message::getErrorDetail).collect(Collectors.toList());
        HashMap<Equivalence.Wrapper<Throwable>, Integer> causes = Maps.newHashMap();
        while (!remainingErrors.isEmpty()) {
            ErrorDetail currentError = (ErrorDetail)remainingErrors.get(0);
            Map<Boolean, List<ErrorDetail>> partitionedByMergeable = remainingErrors.subList(1, remainingErrors.size()).stream().collect(Collectors.partitioningBy(currentError::isMergeable));
            remainingErrors = partitionedByMergeable.get(false);
            currentError.format(index, partitionedByMergeable.get(true), fmt);
            Throwable cause = currentError.getCause();
            if (displayCauses && cause != null) {
                Equivalence.Wrapper<Throwable> causeEquivalence = ThrowableEquivalence.INSTANCE.wrap(cause);
                if (!causes.containsKey(causeEquivalence)) {
                    causes.put(causeEquivalence, index);
                    fmt.format("Caused by: %s", Throwables.getStackTraceAsString(cause));
                } else {
                    int causeIdx = (Integer)causes.get(causeEquivalence);
                    fmt.format("Caused by: %s (same stack trace as error #%s)", cause.getClass().getName(), causeIdx);
                }
            }
            fmt.format("\n", new Object[0]);
            ++index;
        }
        if (index == 2) {
            fmt.format("1 error", new Object[0]);
        } else {
            fmt.format("%s errors", index - 1);
        }
        return PackageNameCompressor.compressPackagesInMessage(fmt.toString());
    }

    public static Message create(ErrorId errorId, String messageFormat, Object ... arguments) {
        return Messages.create(errorId, null, messageFormat, arguments);
    }

    public static Message create(ErrorId errorId, Throwable cause, String messageFormat, Object ... arguments) {
        return Messages.create(errorId, cause, ImmutableList.of(), messageFormat, arguments);
    }

    public static Message create(ErrorId errorId, Throwable cause, List<Object> sources, String messageFormat, Object ... arguments) {
        String message = Messages.format(messageFormat, arguments);
        return new Message(errorId, sources, message, cause);
    }

    static Object convert(Object o) {
        ElementSource source2 = null;
        if (o instanceof ElementSource) {
            source2 = (ElementSource)o;
            o = source2.getDeclaringSource();
        }
        return Messages.convert(o, source2);
    }

    static Object convert(Object o, ElementSource source2) {
        for (Converter<?> converter : converters) {
            if (!converter.appliesTo(o)) continue;
            return Messages.appendModules(converter.convert(o), source2);
        }
        return Messages.appendModules(o, source2);
    }

    private static Object appendModules(Object source2, ElementSource elementSource) {
        String modules = SourceFormatter.getModuleStack(elementSource);
        if (modules.length() == 0) {
            return source2;
        }
        return source2 + " (installed by: " + modules + ")";
    }

    public static Throwable getOnlyCause(Collection<Message> messages) {
        Throwable onlyCause = null;
        for (Message message : messages) {
            Throwable messageCause = message.getCause();
            if (messageCause == null) continue;
            if (onlyCause != null && !ThrowableEquivalence.INSTANCE.equivalent(onlyCause, messageCause)) {
                return null;
            }
            onlyCause = messageCause;
        }
        return onlyCause;
    }

    private static final String formatText(String text, FormatOptions ... options) {
        if (!InternalFlags.enableColorizeErrorMessages()) {
            return text;
        }
        return String.format("%s%s%s", Arrays.stream(options).map(option -> ((FormatOptions)option).ansiCode).collect(Collectors.joining()), text, FormatOptions.RESET.ansiCode);
    }

    public static final String bold(String text) {
        return Messages.formatText(text, FormatOptions.BOLD);
    }

    public static final String redBold(String text) {
        return Messages.formatText(text, FormatOptions.RED, FormatOptions.BOLD);
    }

    public static final String underline(String text) {
        return Messages.formatText(text, FormatOptions.UNDERLINE);
    }

    public static final String faint(String text) {
        return Messages.formatText(text, FormatOptions.FAINT);
    }

    private static enum FormatOptions {
        RED("\u001b[31m"),
        BOLD("\u001b[1m"),
        FAINT("\u001b[2m"),
        ITALIC("\u001b[3m"),
        UNDERLINE("\u001b[4m"),
        RESET("\u001b[0m");

        private final String ansiCode;

        private FormatOptions(String ansiCode) {
            this.ansiCode = ansiCode;
        }
    }

    private static final class ThrowableEquivalence
    extends Equivalence<Throwable> {
        static final ThrowableEquivalence INSTANCE = new ThrowableEquivalence();

        private ThrowableEquivalence() {
        }

        @Override
        protected boolean doEquivalent(Throwable a, Throwable b) {
            return a.getClass().equals(b.getClass()) && Objects.equal(a.getMessage(), b.getMessage()) && Arrays.equals(a.getStackTrace(), b.getStackTrace()) && this.equivalent(a.getCause(), b.getCause());
        }

        @Override
        protected int doHash(Throwable t2) {
            return Objects.hashCode(t2.getClass().hashCode(), t2.getMessage(), this.hash(t2.getCause()));
        }
    }

    private static abstract class Converter<T> {
        final Class<T> type;

        Converter(Class<T> type) {
            this.type = type;
        }

        boolean appliesTo(Object o) {
            return o != null && this.type.isAssignableFrom(o.getClass());
        }

        String convert(Object o) {
            return this.toString(this.type.cast(o));
        }

        abstract String toString(T var1);
    }
}

