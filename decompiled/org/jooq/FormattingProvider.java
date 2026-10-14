/*
 * Decompiled with CFR 0.152.
 */
package org.jooq;

import java.util.function.Supplier;
import java.util.function.ToIntFunction;
import org.jetbrains.annotations.NotNull;
import org.jooq.CSVFormat;
import org.jooq.ChartFormat;
import org.jooq.JSONFormat;
import org.jooq.TXTFormat;
import org.jooq.XMLFormat;
import org.jooq.impl.CallbackFormattingProvider;

public interface FormattingProvider {
    @NotNull
    public TXTFormat txtFormat();

    @NotNull
    public CSVFormat csvFormat();

    @NotNull
    public JSONFormat jsonFormatForResults();

    @NotNull
    public JSONFormat jsonFormatForRecords();

    @NotNull
    public XMLFormat xmlFormatForResults();

    @NotNull
    public XMLFormat xmlFormatForRecords();

    @NotNull
    public ChartFormat chartFormat();

    public int width(String var1);

    @NotNull
    public static CallbackFormattingProvider onTxtFormat(Supplier<? extends TXTFormat> newOnTxtFormat) {
        return new CallbackFormattingProvider().onTxtFormat(newOnTxtFormat);
    }

    @NotNull
    public static CallbackFormattingProvider onCsvFormat(Supplier<? extends CSVFormat> newOnCsvFormat) {
        return new CallbackFormattingProvider().onCsvFormat(newOnCsvFormat);
    }

    @NotNull
    public static CallbackFormattingProvider onJsonFormatForResults(Supplier<? extends JSONFormat> newOnJsonFormatForResults) {
        return new CallbackFormattingProvider().onJsonFormatForResults(newOnJsonFormatForResults);
    }

    @NotNull
    public static CallbackFormattingProvider onJsonFormatForRecords(Supplier<? extends JSONFormat> newOnJsonFormatForRecords) {
        return new CallbackFormattingProvider().onJsonFormatForRecords(newOnJsonFormatForRecords);
    }

    @NotNull
    public static CallbackFormattingProvider onXmlFormatForResults(Supplier<? extends XMLFormat> newOnXmlFormatForResults) {
        return new CallbackFormattingProvider().onXmlFormatForResults(newOnXmlFormatForResults);
    }

    @NotNull
    public static CallbackFormattingProvider onXmlFormatForRecords(Supplier<? extends XMLFormat> newOnXmlFormatForRecords) {
        return new CallbackFormattingProvider().onXmlFormatForRecords(newOnXmlFormatForRecords);
    }

    @NotNull
    public static CallbackFormattingProvider onChartFormat(Supplier<? extends ChartFormat> newOnChartFormat) {
        return new CallbackFormattingProvider().onChartFormat(newOnChartFormat);
    }

    @NotNull
    public static CallbackFormattingProvider onWidth(ToIntFunction<? super String> newOnWidth) {
        return new CallbackFormattingProvider().onWidth(newOnWidth);
    }
}

