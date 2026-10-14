/*
 * Decompiled with CFR 0.152.
 */
package com.google.inject.internal;

import com.google.inject.internal.SourceFormatter;
import java.util.Formatter;
import java.util.List;

final class ErrorFormatter {
    private ErrorFormatter() {
    }

    static void formatSources(int index, List<Object> sources, Formatter formatter) {
        for (int i = 0; i < sources.size(); ++i) {
            Object source2 = sources.get(i);
            if (i == 0) {
                formatter.format("%-3s: ", index);
            } else {
                formatter.format(SourceFormatter.INDENT, new Object[0]);
            }
            new SourceFormatter(source2, formatter, i == 0).format();
        }
    }

    static void formatSources(List<Object> sources, Formatter formatter) {
        for (int i = 0; i < sources.size(); ++i) {
            Object source2 = sources.get(i);
            formatter.format("  ", new Object[0]);
            new SourceFormatter(source2, formatter, i == 0).format();
        }
    }
}

