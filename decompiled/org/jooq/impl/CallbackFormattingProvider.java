/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import java.util.function.Supplier;
import java.util.function.ToIntFunction;
import org.jetbrains.annotations.NotNull;
import org.jooq.CSVFormat;
import org.jooq.ChartFormat;
import org.jooq.FormattingProvider;
import org.jooq.JSONFormat;
import org.jooq.TXTFormat;
import org.jooq.XMLFormat;
import org.jooq.impl.DefaultFormattingProvider;

public final class CallbackFormattingProvider
implements FormattingProvider {
    private static final DefaultFormattingProvider DEFAULT = new DefaultFormattingProvider();
    private final Supplier<? extends TXTFormat> onTxtFormat;
    private final Supplier<? extends CSVFormat> onCsvFormat;
    private final Supplier<? extends JSONFormat> onJsonFormatForResults;
    private final Supplier<? extends JSONFormat> onJsonFormatForRecords;
    private final Supplier<? extends XMLFormat> onXmlFormatForResults;
    private final Supplier<? extends XMLFormat> onXmlFormatForRecords;
    private final Supplier<? extends ChartFormat> onChartFormat;
    private final ToIntFunction<? super String> onWidth;

    public CallbackFormattingProvider() {
        this(DEFAULT::txtFormat, DEFAULT::csvFormat, DEFAULT::jsonFormatForResults, DEFAULT::jsonFormatForRecords, DEFAULT::xmlFormatForResults, DEFAULT::xmlFormatForRecords, DEFAULT::chartFormat, DEFAULT::width);
    }

    private CallbackFormattingProvider(Supplier<? extends TXTFormat> onTxtFormat, Supplier<? extends CSVFormat> onCsvFormat, Supplier<? extends JSONFormat> onJsonFormatForResults, Supplier<? extends JSONFormat> onJsonFormatForRecords, Supplier<? extends XMLFormat> onXmlFormatForResults, Supplier<? extends XMLFormat> onXmlFormatForRecords, Supplier<? extends ChartFormat> onChartFormat, ToIntFunction<? super String> onWidth) {
        this.onTxtFormat = onTxtFormat;
        this.onCsvFormat = onCsvFormat;
        this.onJsonFormatForResults = onJsonFormatForResults;
        this.onJsonFormatForRecords = onJsonFormatForRecords;
        this.onXmlFormatForResults = onXmlFormatForResults;
        this.onXmlFormatForRecords = onXmlFormatForRecords;
        this.onChartFormat = onChartFormat;
        this.onWidth = onWidth;
    }

    @Override
    @NotNull
    public final TXTFormat txtFormat() {
        return this.onTxtFormat.get();
    }

    @Override
    @NotNull
    public final CSVFormat csvFormat() {
        return this.onCsvFormat.get();
    }

    @Override
    @NotNull
    public final JSONFormat jsonFormatForResults() {
        return this.onJsonFormatForResults.get();
    }

    @Override
    @NotNull
    public final JSONFormat jsonFormatForRecords() {
        return this.onJsonFormatForRecords.get();
    }

    @Override
    @NotNull
    public final XMLFormat xmlFormatForResults() {
        return this.onXmlFormatForResults.get();
    }

    @Override
    @NotNull
    public final XMLFormat xmlFormatForRecords() {
        return this.onXmlFormatForRecords.get();
    }

    @Override
    @NotNull
    public final ChartFormat chartFormat() {
        return this.onChartFormat.get();
    }

    @Override
    public final int width(String string) {
        return this.onWidth.applyAsInt(string);
    }

    @Override
    @NotNull
    public final CallbackFormattingProvider onTxtFormat(Supplier<? extends TXTFormat> newOnTxtFormat) {
        return new CallbackFormattingProvider(newOnTxtFormat, this.onCsvFormat, this.onJsonFormatForResults, this.onJsonFormatForRecords, this.onXmlFormatForResults, this.onXmlFormatForRecords, this.onChartFormat, this.onWidth);
    }

    @Override
    @NotNull
    public final CallbackFormattingProvider onCsvFormat(Supplier<? extends CSVFormat> newOnCsvFormat) {
        return new CallbackFormattingProvider(this.onTxtFormat, newOnCsvFormat, this.onJsonFormatForResults, this.onJsonFormatForRecords, this.onXmlFormatForResults, this.onXmlFormatForRecords, this.onChartFormat, this.onWidth);
    }

    @Override
    @NotNull
    public final CallbackFormattingProvider onJsonFormatForResults(Supplier<? extends JSONFormat> newOnJsonFormatForResults) {
        return new CallbackFormattingProvider(this.onTxtFormat, this.onCsvFormat, newOnJsonFormatForResults, this.onJsonFormatForRecords, this.onXmlFormatForResults, this.onXmlFormatForRecords, this.onChartFormat, this.onWidth);
    }

    @Override
    @NotNull
    public final CallbackFormattingProvider onJsonFormatForRecords(Supplier<? extends JSONFormat> newOnJsonFormatForRecords) {
        return new CallbackFormattingProvider(this.onTxtFormat, this.onCsvFormat, this.onJsonFormatForResults, newOnJsonFormatForRecords, this.onXmlFormatForResults, this.onXmlFormatForRecords, this.onChartFormat, this.onWidth);
    }

    @Override
    @NotNull
    public final CallbackFormattingProvider onXmlFormatForResults(Supplier<? extends XMLFormat> newOnXmlFormatForResults) {
        return new CallbackFormattingProvider(this.onTxtFormat, this.onCsvFormat, this.onJsonFormatForResults, this.onJsonFormatForRecords, newOnXmlFormatForResults, this.onXmlFormatForRecords, this.onChartFormat, this.onWidth);
    }

    @Override
    @NotNull
    public final CallbackFormattingProvider onXmlFormatForRecords(Supplier<? extends XMLFormat> newOnXmlFormatForRecords) {
        return new CallbackFormattingProvider(this.onTxtFormat, this.onCsvFormat, this.onJsonFormatForResults, this.onJsonFormatForRecords, this.onXmlFormatForResults, newOnXmlFormatForRecords, this.onChartFormat, this.onWidth);
    }

    @Override
    @NotNull
    public final CallbackFormattingProvider onChartFormat(Supplier<? extends ChartFormat> newOnChartFormat) {
        return new CallbackFormattingProvider(this.onTxtFormat, this.onCsvFormat, this.onJsonFormatForResults, this.onJsonFormatForRecords, this.onXmlFormatForResults, this.onXmlFormatForRecords, newOnChartFormat, this.onWidth);
    }

    @Override
    @NotNull
    public final CallbackFormattingProvider onWidth(ToIntFunction<? super String> newOnWidth) {
        return new CallbackFormattingProvider(this.onTxtFormat, this.onCsvFormat, this.onJsonFormatForResults, this.onJsonFormatForRecords, this.onXmlFormatForResults, this.onXmlFormatForRecords, this.onChartFormat, newOnWidth);
    }
}

