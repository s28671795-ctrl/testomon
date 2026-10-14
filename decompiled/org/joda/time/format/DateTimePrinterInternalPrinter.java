/*
 * Decompiled with CFR 0.152.
 */
package org.joda.time.format;

import java.io.IOException;
import java.io.Writer;
import java.util.Locale;
import org.joda.time.Chronology;
import org.joda.time.DateTimeZone;
import org.joda.time.ReadablePartial;
import org.joda.time.format.DateTimePrinter;
import org.joda.time.format.InternalPrinter;
import org.joda.time.format.InternalPrinterDateTimePrinter;

class DateTimePrinterInternalPrinter
implements InternalPrinter {
    private final DateTimePrinter underlying;

    static InternalPrinter of(DateTimePrinter dateTimePrinter) {
        if (dateTimePrinter instanceof InternalPrinterDateTimePrinter) {
            return (InternalPrinter)((Object)dateTimePrinter);
        }
        if (dateTimePrinter == null) {
            return null;
        }
        return new DateTimePrinterInternalPrinter(dateTimePrinter);
    }

    private DateTimePrinterInternalPrinter(DateTimePrinter dateTimePrinter) {
        this.underlying = dateTimePrinter;
    }

    DateTimePrinter getUnderlying() {
        return this.underlying;
    }

    public int estimatePrintedLength() {
        return this.underlying.estimatePrintedLength();
    }

    public void printTo(Appendable appendable, long l, Chronology chronology, int n, DateTimeZone dateTimeZone, Locale locale) throws IOException {
        Appendable appendable2;
        if (appendable instanceof StringBuffer) {
            appendable2 = (StringBuffer)appendable;
            this.underlying.printTo((StringBuffer)appendable2, l, chronology, n, dateTimeZone, locale);
        }
        if (appendable instanceof Writer) {
            appendable2 = (Writer)appendable;
            this.underlying.printTo((Writer)appendable2, l, chronology, n, dateTimeZone, locale);
        }
        appendable2 = new StringBuffer(this.estimatePrintedLength());
        this.underlying.printTo((StringBuffer)appendable2, l, chronology, n, dateTimeZone, locale);
        appendable.append((CharSequence)((Object)appendable2));
    }

    public void printTo(Appendable appendable, ReadablePartial readablePartial, Locale locale) throws IOException {
        Appendable appendable2;
        if (appendable instanceof StringBuffer) {
            appendable2 = (StringBuffer)appendable;
            this.underlying.printTo((StringBuffer)appendable2, readablePartial, locale);
        }
        if (appendable instanceof Writer) {
            appendable2 = (Writer)appendable;
            this.underlying.printTo((Writer)appendable2, readablePartial, locale);
        }
        appendable2 = new StringBuffer(this.estimatePrintedLength());
        this.underlying.printTo((StringBuffer)appendable2, readablePartial, locale);
        appendable.append((CharSequence)((Object)appendable2));
    }
}

