/*
 * Decompiled with CFR 0.152.
 */
package net.shibboleth.utilities.java.support.logic;

import com.google.common.base.Function;
import com.google.common.base.MoreObjects;
import com.google.common.base.Optional;
import com.google.common.base.Predicate;
import java.util.Objects;
import javax.annotation.Nonnull;
import javax.annotation.concurrent.ThreadSafe;
import net.shibboleth.utilities.java.support.logic.Constraint;

@ThreadSafe
public class TransformAndCheckFunction<T>
implements Function<T, Optional<? extends T>> {
    private final Function<T, ? extends T> preprocessor;
    private final Predicate<T> constraint;
    private final boolean failOnConstraintViolation;

    public TransformAndCheckFunction(@Nonnull Function<T, ? extends T> inputPreprocessor, @Nonnull Predicate<T> inputConstraint, boolean failOnInputConstraintViolation) {
        this.preprocessor = Constraint.isNotNull(inputPreprocessor, "Input preprocessor can not be null");
        this.constraint = Constraint.isNotNull(inputConstraint, "Input constraint can not be null");
        this.failOnConstraintViolation = failOnInputConstraintViolation;
    }

    @Override
    public Optional<? extends T> apply(T input) {
        T processedValue = this.preprocessor.apply(input);
        boolean meetsCriteria = this.constraint.apply(processedValue);
        if (meetsCriteria) {
            return Optional.of(processedValue);
        }
        if (this.failOnConstraintViolation) {
            throw new IllegalArgumentException(input + " does not meet constraint");
        }
        return Optional.absent();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        if (obj instanceof TransformAndCheckFunction) {
            TransformAndCheckFunction other = (TransformAndCheckFunction)obj;
            return Objects.equals(this.preprocessor, other.preprocessor) && Objects.equals(this.constraint, other.constraint) && Objects.equals(this.failOnConstraintViolation, other.failOnConstraintViolation);
        }
        return false;
    }

    public int hashCode() {
        return com.google.common.base.Objects.hashCode(this.preprocessor, this.constraint, this.failOnConstraintViolation);
    }

    public String toString() {
        return MoreObjects.toStringHelper(this).add("preprocessor", this.preprocessor).add("constraint", this.constraint).add("failOnConstraintViolation", this.failOnConstraintViolation).toString();
    }
}

