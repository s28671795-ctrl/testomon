/*
 * Decompiled with CFR 0.152.
 */
package com.google.inject.internal;

import com.google.common.collect.Multimap;
import com.google.inject.Binding;
import com.google.inject.Key;
import com.google.inject.internal.ErrorFormatter;
import com.google.inject.internal.ErrorId;
import com.google.inject.internal.InternalErrorDetail;
import com.google.inject.internal.Messages;
import com.google.inject.internal.SourceFormatter;
import com.google.inject.spi.ErrorDetail;
import java.util.Collection;
import java.util.Formatter;
import java.util.List;
import java.util.Map;
import java.util.Set;

final class DuplicateMapKeyError<K, V>
extends InternalErrorDetail<DuplicateMapKeyError<K, V>> {
    private final Key<Map<K, V>> mapKey;
    private final Multimap<K, Binding<V>> duplicates;

    DuplicateMapKeyError(Key<Map<K, V>> mapKey, Multimap<K, Binding<V>> duplicates, List<Object> sources) {
        super(ErrorId.DUPLICATE_MAP_KEY, DuplicateMapKeyError.getDuplicateKeysMessage(mapKey, duplicates), sources, null);
        this.mapKey = mapKey;
        this.duplicates = duplicates;
    }

    @Override
    protected final void formatDetail(List<ErrorDetail<?>> others, Formatter formatter) {
        formatter.format("\n%s\n", Messages.bold("Duplicates:"));
        for (Map.Entry<K, Collection<Binding<V>>> entry : this.duplicates.asMap().entrySet()) {
            formatter.format("  Key: \"%s\"\n", Messages.redBold(entry.getKey().toString()));
            formatter.format("  Bound at:\n", new Object[0]);
            int index = 1;
            for (Binding<V> binding : entry.getValue()) {
                formatter.format("    %-2s: ", index++);
                new SourceFormatter(binding.getSource(), formatter, true).format();
            }
            formatter.format("\n", new Object[0]);
        }
        formatter.format("%s\n", Messages.bold("MapBinder declared at:"));
        ErrorFormatter.formatSources(this.getSources(), formatter);
    }

    @Override
    public DuplicateMapKeyError<K, V> withSources(List<Object> newSources) {
        return new DuplicateMapKeyError<K, V>(this.mapKey, this.duplicates, newSources);
    }

    private static <K, V> String getDuplicateKeysMessage(Key<Map<K, V>> mapKey, Multimap<K, Binding<V>> duplicates) {
        Set<K> duplicateKeys = duplicates.keySet();
        String mapBinderKey = Messages.convert(mapKey).toString();
        String firstDuplicateKey = duplicateKeys.iterator().next().toString();
        if (duplicateKeys.size() == 1) {
            return String.format("Duplicate key \"%s\" found in %s.", firstDuplicateKey, mapBinderKey);
        }
        return String.format("\"%s\" and %s other duplicate keys found in %s.", firstDuplicateKey, duplicateKeys.size() - 1, mapBinderKey);
    }
}

