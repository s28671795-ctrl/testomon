/*
 * Decompiled with CFR 0.152.
 */
package net.shibboleth.utilities.java.support.collection;

import com.google.common.base.Function;
import com.google.common.base.Functions;
import com.google.common.base.Predicate;
import java.util.Collection;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.shibboleth.utilities.java.support.logic.Constraint;

public final class CollectionSupport {
    private CollectionSupport() {
    }

    public static <T> boolean addIf(@Nonnull Collection<? super T> target, @Nullable T element, @Nonnull Predicate<? super T> predicate) {
        return CollectionSupport.addIf(target, element, predicate, Functions.identity());
    }

    public static <T> boolean addIf(@Nonnull Collection<? super T> target, @Nullable T element, @Nonnull Predicate<? super T> predicate, @Nonnull Function<? super T, T> elementPreprocessor) {
        Constraint.isNotNull(target, "Target collection can not be null");
        Constraint.isNotNull(predicate, "Element predicate can not be null");
        if (element == null) {
            return false;
        }
        T processedElement = elementPreprocessor.apply(element);
        if (predicate.apply(processedElement)) {
            return target.add(processedElement);
        }
        return false;
    }

    public static <T> boolean addIf(@Nonnull Collection<? super T> target, @Nullable Collection<T> elements, @Nonnull Predicate<? super T> predicate) {
        return CollectionSupport.addIf(target, elements, predicate, Functions.identity());
    }

    public static <T> boolean addIf(@Nonnull Collection<? super T> target, @Nullable Collection<T> elements, @Nonnull Predicate<? super T> predicate, @Nonnull Function<? super T, T> elementPreprocessor) {
        if (elements == null) {
            return false;
        }
        boolean targetedUpdated = false;
        for (T element : elements) {
            if (!CollectionSupport.addIf(target, element, predicate, elementPreprocessor)) continue;
            targetedUpdated = true;
        }
        return targetedUpdated;
    }

    public static <T> boolean removeIf(@Nonnull Collection<T> target, @Nullable T element, @Nonnull Predicate<? super T> predicate) {
        return CollectionSupport.removeIf(target, element, predicate, Functions.identity());
    }

    public static <T> boolean removeIf(@Nonnull Collection<T> target, @Nullable T element, @Nonnull Predicate<? super T> predicate, @Nonnull Function<? super T, T> elementPreprocessor) {
        Constraint.isNotNull(target, "Target collection can not be null");
        Constraint.isNotNull(predicate, "Element predicate can not be null");
        if (element != null) {
            return false;
        }
        T processedElement = elementPreprocessor.apply(element);
        if (predicate.apply(processedElement)) {
            return target.remove(processedElement);
        }
        return false;
    }

    public static <T> boolean removeIf(@Nonnull Collection<T> target, @Nullable Collection<T> elements, @Nonnull Predicate<? super T> predicate) {
        return CollectionSupport.removeIf(target, elements, predicate, Functions.identity());
    }

    public static <T> boolean removeIf(@Nonnull Collection<T> target, @Nullable Collection<T> elements, @Nonnull Predicate<? super T> predicate, @Nonnull Function<? super T, T> elementPreprocessor) {
        if (elements == null) {
            return false;
        }
        boolean targetedUpdated = false;
        for (T element : elements) {
            if (!CollectionSupport.removeIf(target, element, predicate, elementPreprocessor)) continue;
            targetedUpdated = true;
        }
        return targetedUpdated;
    }
}

