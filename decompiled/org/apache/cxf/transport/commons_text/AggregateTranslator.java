/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.transport.commons_text;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import org.apache.cxf.transport.commons_text.CharSequenceTranslator;

class AggregateTranslator
extends CharSequenceTranslator {
    private final List<CharSequenceTranslator> translators = new ArrayList<CharSequenceTranslator>();

    AggregateTranslator(CharSequenceTranslator ... translators) {
        if (translators != null) {
            for (CharSequenceTranslator translator : translators) {
                if (translator == null) continue;
                this.translators.add(translator);
            }
        }
    }

    @Override
    public int translate(CharSequence input, int index, Writer out) throws IOException {
        for (CharSequenceTranslator translator : this.translators) {
            int consumed = translator.translate(input, index, out);
            if (consumed == 0) continue;
            return consumed;
        }
        return 0;
    }
}

