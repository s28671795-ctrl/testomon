/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.List;
import org.jooq.Attachable;
import org.jooq.CSVFormat;
import org.jooq.ChartFormat;
import org.jooq.Configuration;
import org.jooq.Field;
import org.jooq.Formattable;
import org.jooq.JSONFormat;
import org.jooq.TXTFormat;
import org.jooq.Table;
import org.jooq.XMLFormat;
import org.jooq.impl.Tools;
import org.w3c.dom.Document;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;

abstract class AbstractFormattable
implements Formattable,
Attachable {
    Configuration configuration;

    AbstractFormattable(Configuration configuration) {
        this.configuration = configuration;
    }

    abstract List<? extends Attachable> getAttachables();

    @Override
    public final void attach(Configuration c) {
        this.configuration = c;
        for (Attachable attachable : this.getAttachables()) {
            if (attachable == null) continue;
            attachable.attach(c);
        }
    }

    @Override
    public final void detach() {
        this.attach(null);
    }

    @Override
    public final Configuration configuration() {
        return this.configuration;
    }

    @Override
    public final String format() {
        return this.format(Tools.configuration(this).formattingProvider().txtFormat());
    }

    @Override
    public final String format(int maxRecords) {
        return this.format(Tools.configuration(this).formattingProvider().txtFormat().maxRows(maxRecords));
    }

    @Override
    public final String format(TXTFormat format) {
        StringWriter writer = new StringWriter();
        this.format((Writer)writer, format);
        return writer.toString();
    }

    @Override
    public final void format(OutputStream stream) {
        this.format(new OutputStreamWriter(stream));
    }

    @Override
    public final void format(OutputStream stream, int maxRecords) {
        this.format((Writer)new OutputStreamWriter(stream), maxRecords);
    }

    @Override
    public final void format(OutputStream stream, TXTFormat format) {
        this.format((Writer)new OutputStreamWriter(stream), format);
    }

    @Override
    public final void format(Writer writer) {
        this.format(writer, Tools.configuration(this).formattingProvider().txtFormat());
    }

    @Override
    public final void format(Writer writer, int maxRecords) {
        this.format(writer, Tools.configuration(this).formattingProvider().txtFormat().maxRows(maxRecords));
    }

    @Override
    public final String formatCSV() {
        return this.formatCSV(Tools.configuration(this).formattingProvider().csvFormat());
    }

    @Override
    public final String formatCSV(boolean header) {
        return this.formatCSV(Tools.configuration(this).formattingProvider().csvFormat().header(header));
    }

    @Override
    public final String formatCSV(char delimiter) {
        return this.formatCSV(Tools.configuration(this).formattingProvider().csvFormat().delimiter(delimiter));
    }

    @Override
    public final String formatCSV(boolean header, char delimiter) {
        return this.formatCSV(Tools.configuration(this).formattingProvider().csvFormat().header(header).delimiter(delimiter));
    }

    @Override
    public final String formatCSV(char delimiter, String nullString) {
        return this.formatCSV(Tools.configuration(this).formattingProvider().csvFormat().delimiter(delimiter).nullString(nullString));
    }

    @Override
    public final String formatCSV(boolean header, char delimiter, String nullString) {
        return this.formatCSV(Tools.configuration(this).formattingProvider().csvFormat().header(header).delimiter(delimiter).nullString(nullString));
    }

    @Override
    public final String formatCSV(CSVFormat format) {
        StringWriter writer = new StringWriter();
        this.formatCSV((Writer)writer, format);
        return writer.toString();
    }

    @Override
    public final void formatCSV(OutputStream stream) {
        this.formatCSV(new OutputStreamWriter(stream));
    }

    @Override
    public final void formatCSV(OutputStream stream, boolean header) {
        this.formatCSV((Writer)new OutputStreamWriter(stream), header);
    }

    @Override
    public final void formatCSV(OutputStream stream, char delimiter) {
        this.formatCSV(stream, Tools.configuration(this).formattingProvider().csvFormat().delimiter(delimiter));
    }

    @Override
    public final void formatCSV(OutputStream stream, boolean header, char delimiter) {
        this.formatCSV((Writer)new OutputStreamWriter(stream), header, delimiter);
    }

    @Override
    public final void formatCSV(OutputStream stream, char delimiter, String nullString) {
        this.formatCSV((Writer)new OutputStreamWriter(stream), delimiter, nullString);
    }

    @Override
    public final void formatCSV(OutputStream stream, boolean header, char delimiter, String nullString) {
        this.formatCSV(new OutputStreamWriter(stream), header, delimiter, nullString);
    }

    @Override
    public final void formatCSV(OutputStream stream, CSVFormat format) {
        this.formatCSV((Writer)new OutputStreamWriter(stream), format);
    }

    @Override
    public final void formatCSV(Writer writer) {
        this.formatCSV(writer, Tools.configuration(this).formattingProvider().csvFormat());
    }

    @Override
    public final void formatCSV(Writer writer, boolean header) {
        this.formatCSV(writer, Tools.configuration(this).formattingProvider().csvFormat().header(header));
    }

    @Override
    public final void formatCSV(Writer writer, char delimiter) {
        this.formatCSV(writer, Tools.configuration(this).formattingProvider().csvFormat().delimiter(delimiter));
    }

    @Override
    public final void formatCSV(Writer writer, boolean header, char delimiter) {
        this.formatCSV(writer, Tools.configuration(this).formattingProvider().csvFormat().header(header).delimiter(delimiter));
    }

    @Override
    public final void formatCSV(Writer writer, char delimiter, String nullString) {
        this.formatCSV(writer, Tools.configuration(this).formattingProvider().csvFormat().delimiter(delimiter).nullString(nullString));
    }

    @Override
    public final void formatCSV(Writer writer, boolean header, char delimiter, String nullString) {
        this.formatCSV(writer, Tools.configuration(this).formattingProvider().csvFormat().header(header).delimiter(delimiter).nullString(nullString));
    }

    abstract JSONFormat defaultJSONFormat();

    @Override
    public final String formatJSON() {
        StringWriter writer = new StringWriter();
        this.formatJSON(writer);
        return writer.toString();
    }

    @Override
    public final String formatJSON(JSONFormat format) {
        StringWriter writer = new StringWriter();
        this.formatJSON(writer, format);
        return writer.toString();
    }

    @Override
    public final void formatJSON(OutputStream stream) {
        this.formatJSON(new OutputStreamWriter(stream));
    }

    @Override
    public final void formatJSON(OutputStream stream, JSONFormat format) {
        this.formatJSON(new OutputStreamWriter(stream), format);
    }

    @Override
    public final void formatJSON(Writer writer) {
        this.formatJSON(writer, this.defaultJSONFormat());
    }

    abstract XMLFormat defaultXMLFormat();

    @Override
    public final String formatXML() {
        return this.formatXML(this.defaultXMLFormat());
    }

    @Override
    public final String formatXML(XMLFormat format) {
        StringWriter writer = new StringWriter();
        this.formatXML(writer, format);
        return writer.toString();
    }

    @Override
    public final void formatXML(OutputStream stream) {
        this.formatXML(stream, this.defaultXMLFormat());
    }

    @Override
    public final void formatXML(OutputStream stream, XMLFormat format) {
        this.formatXML(new OutputStreamWriter(stream), format);
    }

    @Override
    public final void formatXML(Writer writer) {
        this.formatXML(writer, this.defaultXMLFormat());
    }

    @Override
    public final String formatChart() {
        StringWriter writer = new StringWriter();
        this.formatChart(writer);
        return writer.toString();
    }

    @Override
    public final String formatChart(ChartFormat format) {
        StringWriter writer = new StringWriter();
        this.formatChart(writer, format);
        return writer.toString();
    }

    @Override
    public final void formatChart(OutputStream stream) {
        this.formatChart(new OutputStreamWriter(stream));
    }

    @Override
    public final void formatChart(OutputStream stream, ChartFormat format) {
        this.formatChart(new OutputStreamWriter(stream), format);
    }

    @Override
    public final void formatChart(Writer writer) {
        this.formatChart(writer, Tools.configuration(this).formattingProvider().chartFormat());
    }

    @Override
    public final String formatInsert() {
        StringWriter writer = new StringWriter();
        this.formatInsert(writer);
        return writer.toString();
    }

    @Override
    public final void formatInsert(OutputStream stream) {
        this.formatInsert(new OutputStreamWriter(stream));
    }

    @Override
    public final String formatInsert(Table<?> table, Field<?> ... f) {
        StringWriter writer = new StringWriter();
        this.formatInsert(writer, table, f);
        return writer.toString();
    }

    @Override
    public final void formatInsert(OutputStream stream, Table<?> table, Field<?> ... f) {
        this.formatInsert(new OutputStreamWriter(stream), table, f);
    }

    @Override
    public final String formatHTML() {
        StringWriter writer = new StringWriter();
        this.formatHTML(writer);
        return writer.toString();
    }

    @Override
    public final void formatHTML(OutputStream stream) {
        this.formatHTML(new OutputStreamWriter(stream));
    }

    @Override
    public final Document intoXML() {
        return this.intoXML((ContentHandler)((Object)this.defaultXMLFormat()));
    }

    @Override
    public final <H extends ContentHandler> H intoXML(H handler) throws SAXException {
        return this.intoXML(handler, this.defaultXMLFormat());
    }
}

