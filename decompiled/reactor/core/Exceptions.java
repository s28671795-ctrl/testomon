/*
 * Decompiled with CFR 0.152.
 */
package reactor.core;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import reactor.util.Logger;
import reactor.util.Loggers;
import reactor.util.annotation.Nullable;

public abstract class Exceptions {
    private static final Logger LOGGER = Loggers.getLogger(Exceptions.class);
    public static final String BACKPRESSURE_ERROR_QUEUE_FULL = "Queue is full: Reactive Streams source doesn't respect backpressure";
    public static final Throwable TERMINATED = new StaticThrowable("Operator has been terminated");
    static final RejectedExecutionException REJECTED_EXECUTION = new StaticRejectedExecutionException("Scheduler unavailable");
    static final RejectedExecutionException NOT_TIME_CAPABLE_REJECTED_EXECUTION = new StaticRejectedExecutionException("Scheduler is not capable of time-based scheduling");

    public static <T> boolean addThrowable(AtomicReferenceFieldUpdater<T, Throwable> field2, T instance, Throwable exception) {
        Throwable update;
        Throwable current;
        do {
            if ((current = field2.get(instance)) == TERMINATED) {
                return false;
            }
            if (!(current instanceof CompositeException)) continue;
            current.addSuppressed(exception);
            return true;
        } while (!field2.compareAndSet(instance, current, update = current == null ? exception : Exceptions.multiple(current, exception)));
        return true;
    }

    public static Throwable wrapSource(Throwable throwable) {
        return new SourceException(throwable);
    }

    public static RuntimeException multiple(Throwable ... throwables) {
        CompositeException multiple = new CompositeException();
        if (throwables != null) {
            for (Throwable t2 : throwables) {
                multiple.addSuppressed(t2);
            }
        }
        return multiple;
    }

    public static RuntimeException multiple(Iterable<Throwable> throwables) {
        CompositeException multiple = new CompositeException();
        if (throwables != null) {
            for (Throwable t2 : throwables) {
                multiple.addSuppressed(t2);
            }
        }
        return multiple;
    }

    public static RuntimeException bubble(Throwable t2) {
        Exceptions.throwIfFatal(t2);
        return new BubblingException(t2);
    }

    public static IllegalStateException duplicateOnSubscribeException() {
        return new IllegalStateException("Spec. Rule 2.12 - Subscriber.onSubscribe MUST NOT be called more than once (based on object equality)");
    }

    public static UnsupportedOperationException errorCallbackNotImplemented(Throwable cause) {
        Objects.requireNonNull(cause, "cause");
        return new ErrorCallbackNotImplemented(cause);
    }

    public static RuntimeException failWithCancel() {
        return new CancelException();
    }

    public static IllegalStateException failWithOverflow() {
        return new OverflowException("The receiver is overrun by more signals than expected (bounded queue...)");
    }

    public static IllegalStateException failWithOverflow(String message) {
        return new OverflowException(message);
    }

    public static RejectedExecutionException failWithRejected() {
        return REJECTED_EXECUTION;
    }

    public static RejectedExecutionException failWithRejectedNotTimeCapable() {
        return NOT_TIME_CAPABLE_REJECTED_EXECUTION;
    }

    public static RejectedExecutionException failWithRejected(Throwable cause) {
        if (cause instanceof ReactorRejectedExecutionException) {
            return (RejectedExecutionException)cause;
        }
        return new ReactorRejectedExecutionException("Scheduler unavailable", cause);
    }

    public static RejectedExecutionException failWithRejected(String message) {
        return new ReactorRejectedExecutionException(message);
    }

    public static RuntimeException retryExhausted(String message, @Nullable Throwable cause) {
        return cause == null ? new RetryExhaustedException(message) : new RetryExhaustedException(message, cause);
    }

    public static boolean isOverflow(@Nullable Throwable t2) {
        return t2 instanceof OverflowException;
    }

    public static boolean isBubbling(@Nullable Throwable t2) {
        return t2 instanceof BubblingException;
    }

    public static boolean isCancel(@Nullable Throwable t2) {
        return t2 instanceof CancelException;
    }

    public static boolean isErrorCallbackNotImplemented(@Nullable Throwable t2) {
        return t2 instanceof ErrorCallbackNotImplemented;
    }

    public static boolean isMultiple(@Nullable Throwable t2) {
        return t2 instanceof CompositeException;
    }

    public static boolean isRetryExhausted(@Nullable Throwable t2) {
        return t2 instanceof RetryExhaustedException;
    }

    public static boolean isTraceback(@Nullable Throwable t2) {
        if (t2 == null) {
            return false;
        }
        return "reactor.core.publisher.FluxOnAssembly.OnAssemblyException".equals(t2.getClass().getCanonicalName());
    }

    public static IllegalArgumentException nullOrNegativeRequestException(long elements) {
        return new IllegalArgumentException("Spec. Rule 3.9 - Cannot request a non strictly positive number: " + elements);
    }

    public static RuntimeException propagate(Throwable t2) {
        Exceptions.throwIfFatal(t2);
        if (t2 instanceof RuntimeException) {
            return (RuntimeException)t2;
        }
        return new ReactiveException(t2);
    }

    @Nullable
    public static <T> Throwable terminate(AtomicReferenceFieldUpdater<T, Throwable> field2, T instance) {
        Throwable current = field2.get(instance);
        if (current != TERMINATED) {
            current = field2.getAndSet(instance, TERMINATED);
        }
        return current;
    }

    public static boolean isJvmFatal(@Nullable Throwable t2) {
        return t2 instanceof VirtualMachineError || t2 instanceof ThreadDeath || t2 instanceof LinkageError;
    }

    public static boolean isFatal(@Nullable Throwable t2) {
        return Exceptions.isFatalButNotJvmFatal(t2) || Exceptions.isJvmFatal(t2);
    }

    static boolean isFatalButNotJvmFatal(@Nullable Throwable t2) {
        return t2 instanceof BubblingException || t2 instanceof ErrorCallbackNotImplemented;
    }

    public static void throwIfFatal(@Nullable Throwable t2) {
        if (t2 == null) {
            return;
        }
        if (Exceptions.isFatalButNotJvmFatal(t2)) {
            LOGGER.warn("throwIfFatal detected a fatal exception, which is thrown and logged below:", t2);
            throw (RuntimeException)t2;
        }
        if (Exceptions.isJvmFatal(t2)) {
            LOGGER.warn("throwIfFatal detected a jvm fatal exception, which is thrown and logged below:", t2);
            throw (Error)t2;
        }
    }

    public static void throwIfJvmFatal(@Nullable Throwable t2) {
        if (t2 == null) {
            return;
        }
        if (Exceptions.isJvmFatal(t2)) {
            LOGGER.warn("throwIfJvmFatal detected a jvm fatal exception, which is thrown and logged below:", t2);
            assert (t2 instanceof Error);
            throw (Error)t2;
        }
    }

    public static Throwable unwrap(Throwable t2) {
        Throwable _t = t2;
        while (_t instanceof ReactiveException) {
            _t = _t.getCause();
        }
        return _t == null ? t2 : _t;
    }

    public static List<Throwable> unwrapMultiple(@Nullable Throwable potentialMultiple) {
        if (potentialMultiple == null) {
            return Collections.emptyList();
        }
        if (Exceptions.isMultiple(potentialMultiple)) {
            return Arrays.asList(potentialMultiple.getSuppressed());
        }
        return Collections.singletonList(potentialMultiple);
    }

    public static List<Throwable> unwrapMultipleExcludingTracebacks(@Nullable Throwable potentialMultiple) {
        if (potentialMultiple == null) {
            return Collections.emptyList();
        }
        if (Exceptions.isMultiple(potentialMultiple)) {
            Throwable[] suppressed = potentialMultiple.getSuppressed();
            ArrayList<Throwable> filtered2 = new ArrayList<Throwable>(suppressed.length);
            for (Throwable t2 : suppressed) {
                if (Exceptions.isTraceback(t2)) continue;
                filtered2.add(t2);
            }
            return filtered2;
        }
        return Collections.singletonList(potentialMultiple);
    }

    public static final RuntimeException addSuppressed(RuntimeException original, Throwable suppressed) {
        if (original == suppressed) {
            return original;
        }
        if (original == REJECTED_EXECUTION || original == NOT_TIME_CAPABLE_REJECTED_EXECUTION) {
            RejectedExecutionException ree = new RejectedExecutionException(original.getMessage());
            ree.addSuppressed(suppressed);
            return ree;
        }
        original.addSuppressed(suppressed);
        return original;
    }

    public static final Throwable addSuppressed(Throwable original, Throwable suppressed) {
        if (original == suppressed) {
            return original;
        }
        if (original == TERMINATED) {
            return original;
        }
        if (original == REJECTED_EXECUTION || original == NOT_TIME_CAPABLE_REJECTED_EXECUTION) {
            RejectedExecutionException ree = new RejectedExecutionException(original.getMessage());
            ree.addSuppressed(suppressed);
            return ree;
        }
        original.addSuppressed(suppressed);
        return original;
    }

    Exceptions() {
    }

    static final class StaticThrowable
    extends Error {
        StaticThrowable(String message) {
            super(message, null, false, false);
        }

        StaticThrowable(String message, Throwable cause) {
            super(message, cause, false, false);
        }

        StaticThrowable(Throwable cause) {
            super(cause.toString(), cause, false, false);
        }
    }

    static final class StaticRejectedExecutionException
    extends RejectedExecutionException {
        StaticRejectedExecutionException(String message, Throwable cause) {
            super(message, cause);
        }

        StaticRejectedExecutionException(String message) {
            super(message);
        }

        @Override
        public synchronized Throwable fillInStackTrace() {
            return this;
        }
    }

    static class ReactorRejectedExecutionException
    extends RejectedExecutionException {
        ReactorRejectedExecutionException(String message, Throwable cause) {
            super(message, cause);
        }

        ReactorRejectedExecutionException(String message) {
            super(message);
        }
    }

    static final class RetryExhaustedException
    extends IllegalStateException {
        RetryExhaustedException(String message) {
            super(message);
        }

        RetryExhaustedException(String message, Throwable cause) {
            super(message, cause);
        }
    }

    static final class OverflowException
    extends IllegalStateException {
        OverflowException(String s2) {
            super(s2);
        }
    }

    static final class CancelException
    extends BubblingException {
        private static final long serialVersionUID = 2491425227432776144L;

        CancelException() {
            super("The subscriber has denied dispatching");
        }
    }

    static final class ErrorCallbackNotImplemented
    extends UnsupportedOperationException {
        private static final long serialVersionUID = 2491425227432776143L;

        ErrorCallbackNotImplemented(Throwable cause) {
            super(cause);
        }

        @Override
        public synchronized Throwable fillInStackTrace() {
            return this;
        }
    }

    public static class SourceException
    extends ReactiveException {
        private static final long serialVersionUID = 5747581575202629465L;

        SourceException(Throwable cause) {
            super(cause);
        }
    }

    static class ReactiveException
    extends RuntimeException {
        private static final long serialVersionUID = 2491425227432776143L;

        ReactiveException(Throwable cause) {
            super(cause);
        }

        ReactiveException(String message) {
            super(message);
        }

        @Override
        public synchronized Throwable fillInStackTrace() {
            return this.getCause() != null ? this.getCause().fillInStackTrace() : super.fillInStackTrace();
        }
    }

    static class BubblingException
    extends ReactiveException {
        private static final long serialVersionUID = 2491425277432776142L;

        BubblingException(String message) {
            super(message);
        }

        BubblingException(Throwable cause) {
            super(cause);
        }
    }

    static class CompositeException
    extends ReactiveException {
        private static final long serialVersionUID = 8070744939537687606L;

        CompositeException() {
            super("Multiple exceptions");
        }
    }
}

