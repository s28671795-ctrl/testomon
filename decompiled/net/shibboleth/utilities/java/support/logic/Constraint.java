/*
 * Decompiled with CFR 0.152.
 */
package net.shibboleth.utilities.java.support.logic;

import java.util.Collection;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.shibboleth.utilities.java.support.logic.ConstraintViolationException;

public final class Constraint {
    private Constraint() {
    }

    @Nonnull
    public static <T> Collection<T> isEmpty(@Nonnull Collection<T> collection, @Nonnull String message) {
        if (collection != null && !collection.isEmpty()) {
            throw new ConstraintViolationException(message);
        }
        return collection;
    }

    public static boolean isFalse(boolean b, @Nonnull String message) {
        if (b) {
            throw new ConstraintViolationException(message);
        }
        return b;
    }

    public static long isGreaterThan(long threshold, long number, @Nonnull String message) {
        if (number <= threshold) {
            throw new ConstraintViolationException(message);
        }
        return number;
    }

    public static long isGreaterThanOrEqual(long threshold, long number, @Nonnull String message) {
        if (number < threshold) {
            throw new ConstraintViolationException(message);
        }
        return number;
    }

    public static long isLessThan(long threshold, long number, @Nonnull String message) {
        if (number >= threshold) {
            throw new ConstraintViolationException(message);
        }
        return number;
    }

    public static long isLessThanOrEqual(long threshold, long number, @Nonnull String message) {
        if (number > threshold) {
            throw new ConstraintViolationException(message);
        }
        return number;
    }

    @Nonnull
    public static <T> Collection<T> isNotEmpty(@Nonnull Collection<T> collection, @Nonnull String message) {
        if (collection == null || collection.isEmpty()) {
            throw new ConstraintViolationException(message);
        }
        return collection;
    }

    @Nonnull
    public static <T> T[] isNotEmpty(@Nonnull T[] array, @Nonnull String message) {
        if (array == null || array.length == 0) {
            throw new ConstraintViolationException(message);
        }
        return array;
    }

    @Nonnull
    public static byte[] isNotEmpty(@Nonnull byte[] array, @Nonnull String message) {
        if (array == null || array.length == 0) {
            throw new ConstraintViolationException(message);
        }
        return array;
    }

    @Nonnull
    public static String isNotEmpty(@Nonnull String string, @Nonnull String message) {
        if (string == null || string.length() == 0) {
            throw new ConstraintViolationException(message);
        }
        return string;
    }

    @Nonnull
    public static <T> T isNotNull(@Nullable T obj, @Nonnull String message) {
        if (obj == null) {
            throw new ConstraintViolationException(message);
        }
        return obj;
    }

    @Nullable
    public static <T> T isNull(@Nullable T obj, @Nonnull String message) {
        if (obj != null) {
            throw new ConstraintViolationException(message);
        }
        return obj;
    }

    public static boolean isTrue(boolean b, @Nonnull String message) {
        if (!b) {
            throw new ConstraintViolationException(message);
        }
        return b;
    }

    @Nonnull
    public static <T> T[] noNullItems(@Nonnull T[] array, @Nonnull String message) {
        if (array == null) {
            throw new ConstraintViolationException(message);
        }
        for (T element : array) {
            if (element != null) continue;
            throw new ConstraintViolationException(message);
        }
        return array;
    }

    @Nonnull
    public static <T extends Collection<?>> T noNullItems(@Nonnull T collection, @Nonnull String message) {
        if (collection == null) {
            throw new ConstraintViolationException(message);
        }
        for (Object element : collection) {
            if (element != null) continue;
            throw new ConstraintViolationException(message);
        }
        return collection;
    }

    public static long numberInRangeExclusive(long lowerTheshold, long upperThreshold, long number, @Nonnull String message) {
        if (number <= lowerTheshold || number >= upperThreshold) {
            throw new ConstraintViolationException(message);
        }
        return number;
    }

    public static long numberInRangeInclusive(long lowerTheshold, long upperThreshold, long number, @Nonnull String message) {
        if (number < lowerTheshold || number > upperThreshold) {
            throw new ConstraintViolationException(message);
        }
        return number;
    }
}

