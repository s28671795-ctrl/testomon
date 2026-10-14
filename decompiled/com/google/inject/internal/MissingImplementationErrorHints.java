/*
 * Decompiled with CFR 0.152.
 */
package com.google.inject.internal;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.primitives.Primitives;
import com.google.inject.Binding;
import com.google.inject.Injector;
import com.google.inject.Key;
import com.google.inject.TypeLiteral;
import com.google.inject.internal.GuiceInternal;
import com.google.inject.internal.Messages;
import com.google.inject.internal.SourceFormatter;
import com.google.inject.spi.BindingSourceRestriction;
import com.google.inject.spi.UntargettedBinding;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.Map;

final class MissingImplementationErrorHints {
    private static final int MAX_MATCHING_TYPES_REPORTED = 3;
    private static final int MAX_RELATED_TYPES_REPORTED = 3;
    private static final ImmutableSet<Class<?>> COMMON_AMBIGUOUS_TYPES = ((ImmutableSet.Builder)((ImmutableSet.Builder)((ImmutableSet.Builder)ImmutableSet.builder().add(Object.class)).add(String.class)).addAll(Primitives.allWrapperTypes())).build();

    private MissingImplementationErrorHints() {
    }

    static <T> ImmutableList<String> getSuggestions(Key<T> key, Injector injector) {
        ImmutableList.Builder suggestions = ImmutableList.builder();
        TypeLiteral<T> type = key.getTypeLiteral();
        BindingSourceRestriction.getMissingImplementationSuggestion(GuiceInternal.GUICE_INTERNAL, key).ifPresent(suggestions::add);
        ArrayList<String> possibleMatches = new ArrayList<String>();
        ImmutableList sameTypes = injector.findBindingsByType(type).stream().filter(b -> !(b instanceof UntargettedBinding)).collect(ImmutableList.toImmutableList());
        if (!sameTypes.isEmpty()) {
            suggestions.add("\nDid you mean?");
            int howMany = Math.min(sameTypes.size(), 3);
            for (int i = 0; i < howMany; ++i) {
                Key bindingKey = ((Binding)sameTypes.get(i)).getKey();
                suggestions.add(Messages.format("\n    * %s", MissingImplementationErrorHints.formatSuggestion(bindingKey, injector.getExistingBinding(bindingKey))));
            }
            int remaining = sameTypes.size() - 3;
            if (remaining > 0) {
                String plural = remaining == 1 ? "" : "s";
                suggestions.add(Messages.format("\n    * %d more binding%s with other annotations.", remaining, plural));
            }
        } else {
            String want = type.toString();
            Map<Key<?>, Binding<?>> bindingMap = injector.getAllBindings();
            for (Key<?> bindingKey : bindingMap.keySet()) {
                String have;
                Binding<?> binding = bindingMap.get(bindingKey);
                if (binding instanceof UntargettedBinding || !(have = bindingKey.getTypeLiteral().toString()).contains(want) && !want.contains(have)) continue;
                possibleMatches.add(MissingImplementationErrorHints.formatSuggestion(bindingKey, bindingMap.get(bindingKey)));
                if (possibleMatches.size() <= 3) continue;
                break;
            }
            if (!possibleMatches.isEmpty() && possibleMatches.size() <= 3) {
                suggestions.add("\nDid you mean?");
                for (String possibleMatch : possibleMatches) {
                    suggestions.add(Messages.format("\n    * %s", possibleMatch));
                }
            }
        }
        if (sameTypes.isEmpty() && possibleMatches.isEmpty() && key.getAnnotationType() == null && COMMON_AMBIGUOUS_TYPES.contains(key.getTypeLiteral().getRawType())) {
            suggestions.add("\nThe key seems very generic, did you forget an annotation?");
        }
        return suggestions.build();
    }

    private static String formatSuggestion(Key<?> key, Binding<?> binding) {
        Formatter fmt = new Formatter();
        fmt.format("%s bound ", Messages.convert(key));
        new SourceFormatter(binding.getSource(), fmt, false).format();
        return fmt.toString();
    }
}

