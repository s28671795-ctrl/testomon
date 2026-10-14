/*
 * Decompiled with CFR 0.152.
 */
package com.google.inject.internal;

import com.google.common.base.Objects;
import com.google.common.collect.Lists;
import com.google.inject.TypeLiteral;
import com.google.inject.internal.ErrorFormatter;
import com.google.inject.internal.ErrorId;
import com.google.inject.internal.InternalErrorDetail;
import com.google.inject.internal.Messages;
import com.google.inject.spi.ErrorDetail;
import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.List;

final class MissingConstructorError
extends InternalErrorDetail<MissingConstructorError> {
    private final TypeLiteral<?> type;
    private final boolean atInjectRequired;

    MissingConstructorError(TypeLiteral<?> type, boolean atInjectRequired, List<Object> sources) {
        super(ErrorId.MISSING_CONSTRUCTOR, "No injectable constructor for type " + type + ".", sources, null);
        this.type = type;
        this.atInjectRequired = atInjectRequired;
    }

    @Override
    public boolean isMergeable(ErrorDetail<?> other) {
        if (other instanceof MissingConstructorError) {
            MissingConstructorError otherMissing = (MissingConstructorError)other;
            return Objects.equal(this.type, otherMissing.type) && Objects.equal(this.atInjectRequired, otherMissing.atInjectRequired);
        }
        return false;
    }

    @Override
    protected void formatDetail(List<ErrorDetail<?>> mergeableErrors, Formatter formatter) {
        formatter.format("\n", new Object[0]);
        Class<?> rawType = this.type.getRawType();
        if (this.atInjectRequired) {
            formatter.format("Injector is configured to require @Inject constructors but %s does not have a @Inject annotated constructor.\n", rawType);
        } else {
            Constructor<?> noArgConstructor = null;
            try {
                noArgConstructor = this.type.getRawType().getDeclaredConstructor(new Class[0]);
            }
            catch (NoSuchMethodException noSuchMethodException) {
                // empty catch block
            }
            if (noArgConstructor == null) {
                formatter.format("%s does not have a @Inject annotated constructor or a no-arg constructor.\n", rawType);
            } else if (Modifier.isPrivate(noArgConstructor.getModifiers()) && !Modifier.isPrivate(rawType.getModifiers())) {
                formatter.format("%s has a private no-arg constructor but the class is not private. Guice can only use a private no-arg constructor if it is defined in a private class.\n", rawType);
            }
        }
        formatter.format("\n", new Object[0]);
        ArrayList<List<Object>> sourcesList = new ArrayList<List<Object>>();
        sourcesList.add(this.getSources());
        mergeableErrors.forEach(error -> sourcesList.add(error.getSources()));
        formatter.format("%s\n", Messages.bold("Requested by:"));
        int sourceListIndex = 1;
        for (List list : sourcesList) {
            ErrorFormatter.formatSources(sourceListIndex++, Lists.reverse(list), formatter);
        }
    }

    @Override
    public MissingConstructorError withSources(List<Object> newSources) {
        return new MissingConstructorError(this.type, this.atInjectRequired, newSources);
    }
}

