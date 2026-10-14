/*
 * Decompiled with CFR 0.152.
 */
package org.jooq;

import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.jooq.EnumType;

final class EnumTypes {
    private static final Map<Class<?>, Map<String, ? extends EnumType>> LOOKUP = new ConcurrentHashMap();

    static <E extends EnumType> E lookupLiteral(Class<E> enumType, String literal) {
        return (E)((EnumType)LOOKUP.computeIfAbsent(enumType, t2 -> Arrays.stream((EnumType[])enumType.getEnumConstants()).collect(Collectors.toMap(EnumType::getLiteral, Function.identity()))).get(literal));
    }

    private EnumTypes() {
    }
}

