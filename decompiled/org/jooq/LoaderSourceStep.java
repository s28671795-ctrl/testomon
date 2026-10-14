/*
 * Decompiled with CFR 0.152.
 */
package org.jooq;

import java.io.File;
import java.io.InputStream;
import java.io.Reader;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.util.Iterator;
import java.util.stream.Stream;
import org.jetbrains.annotations.NotNull;
import org.jooq.CheckReturnValue;
import org.jooq.LoaderCSVStep;
import org.jooq.LoaderJSONStep;
import org.jooq.LoaderRowsStep;
import org.jooq.LoaderXMLStep;
import org.jooq.Record;
import org.jooq.Source;
import org.jooq.Support;
import org.xml.sax.InputSource;

public interface LoaderSourceStep<R extends Record> {
    @Support
    @NotNull
    @CheckReturnValue
    public LoaderRowsStep<R> loadArrays(Object[] ... var1);

    @Support
    @NotNull
    @CheckReturnValue
    public LoaderRowsStep<R> loadArrays(Iterable<? extends Object[]> var1);

    @Support
    @NotNull
    @CheckReturnValue
    public LoaderRowsStep<R> loadArrays(Iterator<? extends Object[]> var1);

    @Support
    @NotNull
    @CheckReturnValue
    public LoaderRowsStep<R> loadArrays(Stream<? extends Object[]> var1);

    @Support
    @NotNull
    @CheckReturnValue
    public LoaderRowsStep<R> loadRecords(Record ... var1);

    @Support
    @NotNull
    @CheckReturnValue
    public LoaderRowsStep<R> loadRecords(Iterable<? extends Record> var1);

    @Support
    @NotNull
    @CheckReturnValue
    public LoaderRowsStep<R> loadRecords(Iterator<? extends Record> var1);

    @Support
    @NotNull
    @CheckReturnValue
    public LoaderRowsStep<R> loadRecords(Stream<? extends Record> var1);

    @Support
    @NotNull
    @CheckReturnValue
    public LoaderCSVStep<R> loadCSV(File var1);

    @Support
    @NotNull
    @CheckReturnValue
    public LoaderCSVStep<R> loadCSV(File var1, String var2);

    @Support
    @NotNull
    @CheckReturnValue
    public LoaderCSVStep<R> loadCSV(File var1, Charset var2);

    @Support
    @NotNull
    @CheckReturnValue
    public LoaderCSVStep<R> loadCSV(File var1, CharsetDecoder var2);

    @Support
    @NotNull
    @CheckReturnValue
    public LoaderCSVStep<R> loadCSV(String var1);

    @Support
    @NotNull
    @CheckReturnValue
    public LoaderCSVStep<R> loadCSV(InputStream var1);

    @Support
    @NotNull
    @CheckReturnValue
    public LoaderCSVStep<R> loadCSV(InputStream var1, String var2);

    @Support
    @NotNull
    @CheckReturnValue
    public LoaderCSVStep<R> loadCSV(InputStream var1, Charset var2);

    @Support
    @NotNull
    @CheckReturnValue
    public LoaderCSVStep<R> loadCSV(InputStream var1, CharsetDecoder var2);

    @Support
    @NotNull
    @CheckReturnValue
    public LoaderCSVStep<R> loadCSV(Reader var1);

    @Support
    @NotNull
    @CheckReturnValue
    public LoaderCSVStep<R> loadCSV(Source var1);

    @Support
    @NotNull
    @CheckReturnValue
    public LoaderXMLStep<R> loadXML(File var1);

    @Support
    @NotNull
    @CheckReturnValue
    public LoaderXMLStep<R> loadXML(File var1, String var2);

    @Support
    @NotNull
    @CheckReturnValue
    public LoaderXMLStep<R> loadXML(File var1, Charset var2);

    @Support
    @NotNull
    @CheckReturnValue
    public LoaderXMLStep<R> loadXML(File var1, CharsetDecoder var2);

    @Support
    @NotNull
    @CheckReturnValue
    public LoaderXMLStep<R> loadXML(String var1);

    @Support
    @NotNull
    @CheckReturnValue
    public LoaderXMLStep<R> loadXML(InputStream var1);

    @Support
    @NotNull
    @CheckReturnValue
    public LoaderXMLStep<R> loadXML(InputStream var1, String var2);

    @Support
    @NotNull
    @CheckReturnValue
    public LoaderXMLStep<R> loadXML(InputStream var1, Charset var2);

    @Support
    @NotNull
    @CheckReturnValue
    public LoaderXMLStep<R> loadXML(InputStream var1, CharsetDecoder var2);

    @Support
    @NotNull
    @CheckReturnValue
    public LoaderXMLStep<R> loadXML(Reader var1);

    @Support
    @NotNull
    @CheckReturnValue
    public LoaderXMLStep<R> loadXML(InputSource var1);

    @Support
    @NotNull
    @CheckReturnValue
    public LoaderXMLStep<R> loadXML(Source var1);

    @Support
    @NotNull
    @CheckReturnValue
    public LoaderJSONStep<R> loadJSON(File var1);

    @Support
    @NotNull
    @CheckReturnValue
    public LoaderJSONStep<R> loadJSON(File var1, String var2);

    @Support
    @NotNull
    @CheckReturnValue
    public LoaderJSONStep<R> loadJSON(File var1, Charset var2);

    @Support
    @NotNull
    @CheckReturnValue
    public LoaderJSONStep<R> loadJSON(File var1, CharsetDecoder var2);

    @Support
    @NotNull
    @CheckReturnValue
    public LoaderJSONStep<R> loadJSON(String var1);

    @Support
    @NotNull
    @CheckReturnValue
    public LoaderJSONStep<R> loadJSON(InputStream var1);

    @Support
    @NotNull
    @CheckReturnValue
    public LoaderJSONStep<R> loadJSON(InputStream var1, String var2);

    @Support
    @NotNull
    @CheckReturnValue
    public LoaderJSONStep<R> loadJSON(InputStream var1, Charset var2);

    @Support
    @NotNull
    @CheckReturnValue
    public LoaderJSONStep<R> loadJSON(InputStream var1, CharsetDecoder var2);

    @Support
    @NotNull
    @CheckReturnValue
    public LoaderJSONStep<R> loadJSON(Reader var1);

    @Support
    @NotNull
    @CheckReturnValue
    public LoaderJSONStep<R> loadJSON(Source var1);
}

