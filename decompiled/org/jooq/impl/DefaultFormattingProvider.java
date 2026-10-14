/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import org.jooq.CSVFormat;
import org.jooq.ChartFormat;
import org.jooq.FormattingProvider;
import org.jooq.JSONFormat;
import org.jooq.TXTFormat;
import org.jooq.XMLFormat;

public class DefaultFormattingProvider
implements FormattingProvider {
    @Override
    public TXTFormat txtFormat() {
        return TXTFormat.DEFAULT;
    }

    @Override
    public CSVFormat csvFormat() {
        return CSVFormat.DEFAULT;
    }

    @Override
    public JSONFormat jsonFormatForResults() {
        return JSONFormat.DEFAULT_FOR_RESULTS;
    }

    @Override
    public JSONFormat jsonFormatForRecords() {
        return JSONFormat.DEFAULT_FOR_RECORDS;
    }

    @Override
    public XMLFormat xmlFormatForResults() {
        return XMLFormat.DEFAULT_FOR_RESULTS;
    }

    @Override
    public XMLFormat xmlFormatForRecords() {
        return XMLFormat.DEFAULT_FOR_RECORDS;
    }

    @Override
    public ChartFormat chartFormat() {
        return ChartFormat.DEFAULT;
    }

    @Override
    public int width(String string) {
        return string.length();
    }
}

