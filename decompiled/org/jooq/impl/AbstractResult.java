/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import java.io.IOException;
import java.io.StringReader;
import java.io.Writer;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.TreeMap;
import java.util.stream.Collectors;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.jooq.CSVFormat;
import org.jooq.ChartFormat;
import org.jooq.Configuration;
import org.jooq.Cursor;
import org.jooq.DSLContext;
import org.jooq.EnumType;
import org.jooq.Field;
import org.jooq.Formattable;
import org.jooq.FormattingProvider;
import org.jooq.JSON;
import org.jooq.JSONB;
import org.jooq.JSONFormat;
import org.jooq.Param;
import org.jooq.Record;
import org.jooq.Result;
import org.jooq.Row;
import org.jooq.Schema;
import org.jooq.TXTFormat;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.TableRecord;
import org.jooq.XML;
import org.jooq.XMLFormat;
import org.jooq.conf.SettingsTools;
import org.jooq.impl.AbstractDataType;
import org.jooq.impl.AbstractFormattable;
import org.jooq.impl.AbstractRow;
import org.jooq.impl.DSL;
import org.jooq.impl.FieldsImpl;
import org.jooq.impl.FieldsTrait;
import org.jooq.impl.Tools;
import org.jooq.tools.StringUtils;
import org.jooq.tools.json.JSONValue;
import org.w3c.dom.Document;
import org.w3c.dom.DocumentFragment;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.ContentHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;
import org.xml.sax.helpers.DefaultHandler;

abstract class AbstractResult<R extends Record>
extends AbstractFormattable
implements FieldsTrait,
Iterable<R> {
    final AbstractRow<R> fields;

    AbstractResult(Configuration configuration, AbstractRow<R> row) {
        super(configuration);
        this.fields = row;
    }

    public final FieldsImpl<R> recordType() {
        return this.fields.fields;
    }

    @Override
    public final Row fieldsRow() {
        return this.fields;
    }

    @Override
    public final void format(Writer writer, TXTFormat format) {
        try {
            int i;
            int index;
            FormattingProvider fp = Tools.configuration(this).formattingProvider();
            int NUM_COL_MAX_WIDTH = format.maxColWidth() == Integer.MAX_VALUE ? Integer.MAX_VALUE : 2 * format.maxColWidth();
            int MAX_RECORDS = Math.min(50, format.maxRows());
            ArrayDeque<Record> buffer = new ArrayDeque<Record>();
            Iterator it = this.iterator();
            for (int i2 = 0; i2 < MAX_RECORDS && it.hasNext(); ++i2) {
                buffer.offer(this.nullSafe((Record)it.next()));
            }
            int size = this.fields.size();
            int[] decimalPlaces = new int[size];
            int[] widths = new int[size];
            for (int index2 = 0; index2 < size; ++index2) {
                if (!Number.class.isAssignableFrom(this.fields.field(index2).getType())) continue;
                ArrayList<Integer> decimalPlacesList = new ArrayList<Integer>(1 + buffer.size());
                decimalPlacesList.add(0);
                for (Record record : buffer) {
                    decimalPlacesList.add(AbstractResult.decimalPlaces(AbstractResult.format0(record.get(index2), record.changed(index2), true)));
                }
                decimalPlaces[index2] = (Integer)Collections.max(decimalPlacesList);
            }
            for (index = 0; index < size; ++index) {
                boolean isNumCol = Number.class.isAssignableFrom(this.fields.field(index).getType());
                int colMaxWidth = isNumCol ? NUM_COL_MAX_WIDTH : format.maxColWidth();
                ArrayList<Integer> widthList = new ArrayList<Integer>(1 + buffer.size());
                widthList.add(Math.min(colMaxWidth, Math.max(format.minColWidth(), fp.width(this.fields.field(index).getName()))));
                for (Record record : buffer) {
                    String value = AbstractResult.format0(record.get(index), record.changed(index), true);
                    if (isNumCol) {
                        value = AbstractResult.alignNumberValue(decimalPlaces[index], value);
                    }
                    widthList.add(Math.min(colMaxWidth, fp.width(value)));
                }
                widths[index] = (Integer)Collections.max(widthList);
            }
            if (format.horizontalTableBorder()) {
                this.formatHorizontalLine(writer, format, widths);
            }
            if (format.verticalTableBorder()) {
                writer.append('|');
            }
            for (index = 0; index < size; ++index) {
                if (index > 0) {
                    if (format.verticalCellBorder()) {
                        writer.append('|');
                    } else {
                        writer.append(' ');
                    }
                }
                String name = this.fields.field(index).getName();
                int width = fp.width(name);
                String padded = Number.class.isAssignableFrom(this.fields.field(index).getType()) ? StringUtils.leftPad(name, width, widths[index]) : StringUtils.rightPad(name, width, widths[index]);
                if (widths[index] < 4) {
                    writer.append(padded);
                    continue;
                }
                writer.append(StringUtils.abbreviate(padded, widths[index]));
            }
            if (format.verticalTableBorder()) {
                writer.append('|');
            }
            writer.append('\n');
            if (format.horizontalHeaderBorder()) {
                this.formatHorizontalLine(writer, format, widths);
            }
            for (i = 0; i < format.maxRows(); ++i) {
                Record record = (Record)buffer.pollFirst();
                if (record == null) {
                    if (!it.hasNext()) break;
                    record = this.nullSafe((Record)it.next());
                }
                if (i > 0 && format.horizontalCellBorder()) {
                    this.formatHorizontalLine(writer, format, widths);
                }
                if (format.verticalTableBorder()) {
                    writer.append('|');
                }
                for (int index3 = 0; index3 < size; ++index3) {
                    if (index3 > 0) {
                        if (format.verticalCellBorder()) {
                            writer.append('|');
                        } else {
                            writer.append(' ');
                        }
                    }
                    String value = StringUtils.replace(StringUtils.replace(StringUtils.replace(AbstractResult.format0(record.get(index3), record.changed(index3), true), "\n", "{lf}"), "\r", "{cr}"), "\t", "{tab}");
                    String padded = Number.class.isAssignableFrom(this.fields.field(index3).getType()) ? StringUtils.leftPad(AbstractResult.alignNumberValue(decimalPlaces[index3], value), widths[index3]) : StringUtils.rightPad(value, fp.width(value), widths[index3]);
                    if (widths[index3] < 4) {
                        writer.append(padded);
                        continue;
                    }
                    writer.append(StringUtils.abbreviate(padded, widths[index3]));
                }
                if (format.verticalTableBorder()) {
                    writer.append('|');
                }
                writer.append('\n');
            }
            if (format.horizontalTableBorder() && i > 0) {
                this.formatHorizontalLine(writer, format, widths);
            }
            if (it.hasNext()) {
                if (format.verticalTableBorder()) {
                    writer.append('|');
                }
                writer.append("...record(s) truncated...\n");
            }
            writer.flush();
        }
        catch (IOException e) {
            throw new org.jooq.exception.IOException("Exception while writing TEXT", e);
        }
    }

    private final R nullSafe(R r) {
        return (R)(r != null ? r : Tools.configuration(this).dsl().newRecord(this.fields()));
    }

    private final void formatHorizontalLine(Writer writer, TXTFormat format, int[] widths) throws IOException {
        if (format.verticalTableBorder()) {
            if (format.intersectLines()) {
                writer.append('+');
            } else {
                writer.append('-');
            }
        }
        int size = this.fields.size();
        for (int index = 0; index < size; ++index) {
            if (index > 0) {
                if (format.verticalCellBorder()) {
                    if (format.intersectLines()) {
                        writer.append('+');
                    } else {
                        writer.append('-');
                    }
                } else {
                    writer.append(' ');
                }
            }
            writer.append(StringUtils.rightPad("", widths[index], "-"));
        }
        if (format.verticalTableBorder()) {
            if (format.intersectLines()) {
                writer.append('+');
            } else {
                writer.append('-');
            }
        }
        writer.append('\n');
    }

    private static final String alignNumberValue(Integer columnDecimalPlaces, String value) {
        if (!"{null}".equals(value) && columnDecimalPlaces != 0) {
            int decimalPlaces = AbstractResult.decimalPlaces(value);
            int rightPadSize = value.length() + columnDecimalPlaces - decimalPlaces;
            value = decimalPlaces == 0 ? StringUtils.rightPad(value, rightPadSize + 1) : StringUtils.rightPad(value, rightPadSize);
        }
        return value;
    }

    private static final int decimalPlaces(String value) {
        int decimalPlaces = 0;
        int dotIndex = value.indexOf(".");
        if (dotIndex != -1) {
            decimalPlaces = value.length() - dotIndex - 1;
        }
        return decimalPlaces;
    }

    @Override
    public final void formatCSV(Writer writer, CSVFormat format) {
        try {
            if (format.header()) {
                Object sep1 = "";
                for (Field<?> field2 : this.fields.fields.fields) {
                    writer.append((CharSequence)sep1);
                    writer.append(AbstractResult.formatCSV0(field2.getName(), format));
                    sep1 = format.delimiter();
                }
                writer.append(format.newline());
            }
            for (Record record : this) {
                record = this.nullSafe(record);
                String sep2 = "";
                int size = this.fields.size();
                for (int index = 0; index < size; ++index) {
                    writer.append(sep2);
                    writer.append(AbstractResult.formatCSV0(record.getValue(index), format));
                    sep2 = format.delimiter();
                }
                writer.append(format.newline());
            }
            writer.flush();
        }
        catch (IOException e) {
            throw new org.jooq.exception.IOException("Exception while writing CSV", e);
        }
    }

    private static final String formatCSV0(Object value, CSVFormat format) {
        String string;
        if (value == null) {
            return format.nullString();
        }
        if ("".equals(value.toString())) {
            return format.emptyString();
        }
        if (value instanceof Formattable) {
            Formattable f = (Formattable)value;
            string = f.formatCSV(format);
        } else {
            string = AbstractResult.format0(value, false, false);
        }
        String result = string;
        switch (format.quote()) {
            case NEVER: {
                return result;
            }
            case SPECIAL_CHARACTERS: {
                if (StringUtils.containsAny(result, ',', ';', '\t', '\"', '\n', '\r', '\'', '\\')) break;
                return result;
            }
        }
        return format.quoteString() + StringUtils.replace(StringUtils.replace(result, "\\", "\\\\"), format.quoteString(), format.quoteString() + format.quoteString()) + format.quoteString();
    }

    @Override
    final JSONFormat defaultJSONFormat() {
        return Tools.configuration(this).formattingProvider().jsonFormatForResults();
    }

    @Override
    public final void formatJSON(Writer writer, JSONFormat format) {
        format = format.mutable(true);
        try {
            String separator;
            int recordLevel = format.header() ? 2 : 1;
            boolean hasRecords = false;
            if (format.header()) {
                if (format.format()) {
                    writer.append('{').append(format.newline()).append(format.indentString(1)).append("\"fields\": [");
                } else {
                    writer.append("{\"fields\":[");
                }
                separator = "";
                for (Field<?> field2 : this.fields.fields.fields) {
                    TableField f;
                    Table table;
                    writer.append(separator);
                    if (format.format()) {
                        writer.append(format.newline()).append(format.indentString(2));
                    }
                    writer.append('{');
                    if (format.format()) {
                        writer.append(format.newline()).append(format.indentString(3));
                    }
                    if (field2 instanceof TableField && (table = (f = (TableField)field2).getTable()) != null) {
                        Schema schema = table.getSchema();
                        if (schema != null) {
                            writer.append("\"schema\":");
                            if (format.format()) {
                                writer.append(' ');
                            }
                            JSONValue.writeJSONString(schema.getName(), writer);
                            writer.append(',');
                            if (format.format()) {
                                writer.append(format.newline()).append(format.indentString(3));
                            }
                        }
                        writer.append("\"table\":");
                        if (format.format()) {
                            writer.append(' ');
                        }
                        JSONValue.writeJSONString(table.getName(), writer);
                        writer.append(',');
                        if (format.format()) {
                            writer.append(format.newline()).append(format.indentString(3));
                        }
                    }
                    writer.append("\"name\":");
                    if (format.format()) {
                        writer.append(' ');
                    }
                    JSONValue.writeJSONString(field2.getName(), writer);
                    writer.append(',');
                    if (format.format()) {
                        writer.append(format.newline()).append(format.indentString(3));
                    }
                    writer.append("\"type\":");
                    if (format.format()) {
                        writer.append(' ');
                    }
                    JSONValue.writeJSONString(this.formatTypeName(field2), writer);
                    if (format.format()) {
                        writer.append(format.newline()).append(format.indentString(2));
                    }
                    writer.append('}');
                    separator = ",";
                }
                if (format.format()) {
                    writer.append(format.newline()).append(format.indentString(1)).append("],").append(format.newline()).append(format.indentString(1)).append("\"records\": ");
                } else {
                    writer.append("],\"records\":");
                }
            }
            writer.append('[');
            separator = "";
            switch (format.recordFormat()) {
                case ARRAY: {
                    for (Record record : this) {
                        hasRecords = true;
                        writer.append(separator);
                        if (format.format()) {
                            writer.append(format.newline()).append(format.indentString(recordLevel));
                        }
                        AbstractResult.formatJSONArray0(record, this.fields, format, recordLevel, writer);
                        separator = ",";
                    }
                    break;
                }
                case OBJECT: {
                    for (Record record : this) {
                        hasRecords = true;
                        writer.append(separator);
                        if (format.format()) {
                            writer.append(format.newline()).append(format.indentString(recordLevel));
                        }
                        AbstractResult.formatJSONMap0(record, this.fields, format, recordLevel, writer);
                        separator = ",";
                    }
                    break;
                }
                default: {
                    throw new IllegalArgumentException("Format not supported: " + String.valueOf(format));
                }
            }
            if (format.format() && hasRecords) {
                writer.append(format.newline());
                if (format.header()) {
                    writer.append(format.indentString(1));
                } else {
                    writer.append(format.indentString(0));
                }
            }
            writer.append(']');
            if (format.header()) {
                if (format.format()) {
                    writer.append(format.newline()).append(format.indentString(0));
                }
                writer.append('}');
            }
            writer.flush();
        }
        catch (IOException e) {
            throw new org.jooq.exception.IOException("Exception while writing JSON", e);
        }
    }

    private final String formatTypeName(Field<?> field2) {
        return ((AbstractDataType)field2.getDataType()).typeName0().toUpperCase(SettingsTools.renderLocale(this.configuration.settings()));
    }

    static final void formatJSON0(Object value, Writer writer, JSONFormat format) throws IOException {
        if (value instanceof byte[]) {
            byte[] a = (byte[])value;
            JSONValue.writeJSONString(Base64.getEncoder().encodeToString(a), writer);
        } else if (value instanceof Object[]) {
            Object[] array = (Object[])value;
            writer.append('[');
            for (int i = 0; i < array.length; ++i) {
                if (i > 0) {
                    writer.append(',');
                }
                AbstractResult.formatJSON0(array[i], writer, format);
            }
            writer.append(']');
        } else if (value instanceof Formattable) {
            Formattable f = (Formattable)value;
            f.formatJSON(writer, format);
        } else if (value instanceof JSON && !format.quoteNested()) {
            writer.write(((JSON)value).data());
        } else if (value instanceof JSONB && !format.quoteNested()) {
            writer.write(((JSONB)value).data());
        } else {
            JSONValue.writeJSONString(value, writer);
        }
    }

    static final void formatJSONMap0(Record record, AbstractRow<?> fields, JSONFormat format, int recordLevel, Writer writer) throws IOException {
        boolean wrapRecords;
        if (record == null) {
            writer.append("null");
            return;
        }
        String separator = "";
        int size = fields.size();
        boolean bl = wrapRecords = format.wrapSingleColumnRecords() || size > 1;
        if (wrapRecords) {
            writer.append('{');
        }
        for (int index = 0; index < size; ++index) {
            writer.append(separator);
            if (format.format()) {
                if (size > 1) {
                    writer.append(format.newline()).append(format.indentString(recordLevel + 1));
                } else if (format.wrapSingleColumnRecords()) {
                    writer.append(' ');
                }
            }
            if (wrapRecords) {
                JSONValue.writeJSONString(record.field(index).getName(), writer);
                writer.append(':');
                if (format.format()) {
                    writer.append(' ');
                }
            }
            int previous = format.globalIndent();
            AbstractResult.formatJSON0(record.get(index), writer, format.globalIndent(format.globalIndent() + format.indent() * (recordLevel + 1)));
            format.globalIndent(previous);
            if (format.format() && format.wrapSingleColumnRecords() && size == 1) {
                writer.append(' ');
            }
            separator = ",";
        }
        if (wrapRecords) {
            if (format.format() && size > 1) {
                writer.append(format.newline()).append(format.indentString(recordLevel)).append('}');
            } else {
                writer.append('}');
            }
        }
    }

    static final void formatJSONArray0(Record record, AbstractRow<?> fields, JSONFormat format, int recordLevel, Writer writer) throws IOException {
        boolean wrapRecords;
        if (record == null) {
            writer.append("null");
            return;
        }
        String separator = "";
        int size = fields.size();
        boolean bl = wrapRecords = format.wrapSingleColumnRecords() || size > 1;
        if (wrapRecords) {
            writer.append('[');
        }
        for (int index = 0; index < size; ++index) {
            writer.append(separator);
            if (format.format()) {
                if (size > 1) {
                    writer.append(format.newline()).append(format.indentString(recordLevel + 1));
                } else if (format.wrapSingleColumnRecords()) {
                    writer.append(' ');
                }
            }
            int previous = format.globalIndent();
            AbstractResult.formatJSON0(record.get(index), writer, format.globalIndent(format.globalIndent() + format.indent() * (recordLevel + 1)));
            format.globalIndent(previous);
            if (format.format() && format.wrapSingleColumnRecords() && size == 1) {
                writer.append(' ');
            }
            separator = ",";
        }
        if (wrapRecords) {
            if (format.format() && size > 1) {
                writer.append(format.newline()).append(format.indentString(recordLevel)).append(']');
            } else {
                writer.append(']');
            }
        }
    }

    @Override
    final XMLFormat defaultXMLFormat() {
        return Tools.configuration(this).formattingProvider().xmlFormatForResults();
    }

    @Override
    public final void formatXML(Writer writer, XMLFormat format) {
        format = format.mutable(true);
        String newline = format.newline();
        int recordLevel = format.header() ? 2 : 1;
        try {
            writer.append("<result");
            if (format.xmlns()) {
                format = format.xmlns(false);
                writer.append(" xmlns=\"http://www.jooq.org/xsd/jooq-export-3.10.0.xsd\"");
            }
            writer.append(">");
            if (format.header()) {
                writer.append(newline).append(format.indentString(1)).append("<fields>");
                for (Field<?> field2 : this.fields.fields.fields) {
                    TableField f;
                    Table table;
                    writer.append(newline).append(format.indentString(2)).append("<field");
                    if (field2 instanceof TableField && (table = (f = (TableField)field2).getTable()) != null) {
                        Schema schema = table.getSchema();
                        if (schema != null) {
                            writer.append(" schema=\"");
                            writer.append(AbstractResult.escapeXML(schema.getName()));
                            writer.append("\"");
                        }
                        writer.append(" table=\"");
                        writer.append(AbstractResult.escapeXML(table.getName()));
                        writer.append("\"");
                    }
                    writer.append(" name=\"");
                    writer.append(AbstractResult.escapeXML(field2.getName()));
                    writer.append("\"");
                    writer.append(" type=\"");
                    writer.append(AbstractResult.escapeXML(this.formatTypeName(field2)));
                    writer.append("\"/>");
                }
                writer.append(newline).append(format.indentString(1)).append("</fields>");
                writer.append(newline).append(format.indentString(1)).append("<records>");
            }
            for (Record record : this) {
                writer.append(newline).append(format.indentString(recordLevel));
                AbstractResult.formatXMLRecord(writer, format, recordLevel, record, this.fields);
            }
            if (format.header()) {
                writer.append(newline).append(format.indentString(1)).append("</records>");
            }
            writer.append(newline).append(format.indentString(0)).append("</result>");
            writer.flush();
        }
        catch (IOException e) {
            throw new org.jooq.exception.IOException("Exception while writing XML", e);
        }
    }

    static final void formatXMLRecord(Writer writer, XMLFormat format, int recordLevel, Record record, AbstractRow<?> fields) throws IOException {
        String newline = format.newline();
        writer.append("<record");
        if (format.xmlns()) {
            format = format.xmlns(false);
            writer.append(" xmlns=\"http://www.jooq.org/xsd/jooq-export-3.10.0.xsd\"");
        }
        if (record == null) {
            writer.append("/>");
            return;
        }
        writer.append(">");
        int size = fields.size();
        for (int index = 0; index < size; ++index) {
            Object value = record.get(index);
            writer.append(newline).append(format.indentString(recordLevel + 1));
            String tag = format.recordFormat() == XMLFormat.RecordFormat.COLUMN_NAME_ELEMENTS ? AbstractResult.escapeXML(fields.field(index).getName()) : "value";
            writer.append("<" + tag);
            if (format.recordFormat() == XMLFormat.RecordFormat.VALUE_ELEMENTS_WITH_FIELD_ATTRIBUTE) {
                writer.append(" field=\"");
                writer.append(AbstractResult.escapeXML(fields.field(index).getName()));
                writer.append("\"");
            }
            if (value == null) {
                writer.append("/>");
                continue;
            }
            writer.append(">");
            if (value instanceof Formattable) {
                Formattable f = (Formattable)value;
                writer.append(newline).append(format.indentString(recordLevel + 2));
                int previous = format.globalIndent();
                f.formatXML(writer, format.globalIndent(format.globalIndent() + format.indent() * (recordLevel + 2)));
                format.globalIndent(previous);
                writer.append(newline).append(format.indentString(recordLevel + 1));
            } else if (value instanceof XML && !format.quoteNested()) {
                writer.append(((XML)value).data());
            } else {
                writer.append(AbstractResult.escapeXML(AbstractResult.format0(value, false, false)));
            }
            writer.append("</" + tag + ">");
        }
        writer.append(newline).append(format.indentString(recordLevel)).append("</record>");
    }

    @Override
    public final void formatChart(Writer writer, ChartFormat format) {
        Result result;
        if (this instanceof Result) {
            result = (Result)((Object)this);
        } else if (this instanceof Cursor) {
            result = ((Cursor)((Object)this)).fetch();
        } else {
            throw new IllegalStateException();
        }
        try {
            int y;
            if (result.isEmpty()) {
                writer.append("No data available");
                return;
            }
            DSLContext ctx = this.configuration.dsl();
            FormattingProvider fp = this.configuration.formattingProvider();
            Field<?> category = this.fields.field(format.category());
            TreeMap groups2 = new TreeMap(result.intoGroups(format.category()));
            if (!format.categoryAsText() && Date.class.isAssignableFrom(category.getType())) {
                Date categoryMin = (Date)groups2.firstKey();
                Date categoryMax = (Date)groups2.lastKey();
                Date i = categoryMin;
                while (i.before(categoryMax)) {
                    if (!groups2.containsKey(i)) {
                        groups2.put(i, ctx.newResult(this.fields.fields.fields));
                    }
                    i = new Date(i.getYear(), i.getMonth(), i.getDate() + 1);
                }
            }
            ArrayList categories = new ArrayList(groups2.keySet());
            boolean categoryPadding = true;
            int categoryWidth = 0;
            for (Object o : categories) {
                categoryWidth = Math.max(categoryWidth, fp.width(String.valueOf(o)));
            }
            double axisMin = Double.POSITIVE_INFINITY;
            double axisMax = Double.NEGATIVE_INFINITY;
            for (Result values2 : groups2.values()) {
                double sum = 0.0;
                for (int i = 0; i < format.values().length; ++i) {
                    if (format.display() == ChartFormat.Display.DEFAULT) {
                        sum = 0.0;
                    }
                    for (Record r : values2) {
                        sum += r.get(format.values()[i], Double.TYPE).doubleValue();
                    }
                    if (sum < axisMin) {
                        axisMin = sum;
                    }
                    if (!(sum > axisMax)) continue;
                    axisMax = sum;
                }
            }
            int verticalLegendWidth = format.showVerticalLegend() ? (format.display() == ChartFormat.Display.HUNDRED_PERCENT_STACKED ? fp.width(format.percentFormat().format(100.0)) : Math.max(format.numericFormat().format(axisMin).length(), format.numericFormat().format(axisMax).length())) : 0;
            int horizontalLegendHeight = format.showHorizontalLegend() ? 1 : 0;
            int verticalBorderWidth = format.showVerticalLegend() ? 1 : 0;
            int horizontalBorderHeight = format.showHorizontalLegend() ? 1 : 0;
            int chartHeight = format.height() - horizontalLegendHeight - horizontalBorderHeight;
            int chartWidth = format.width() - verticalLegendWidth - verticalBorderWidth;
            double barWidth = (double)chartWidth / (double)groups2.size();
            double axisStep = (axisMax - axisMin) / (double)(chartHeight - 1);
            for (y = chartHeight - 1; y >= 0; --y) {
                double axisLegend = axisMax - axisStep * (double)(chartHeight - 1 - y);
                double axisLegendPercent = (axisLegend - axisMin) / (axisMax - axisMin);
                if (format.showVerticalLegend()) {
                    int x;
                    String axisLegendString = format.display() == ChartFormat.Display.HUNDRED_PERCENT_STACKED ? format.percentFormat().format(axisLegendPercent * 100.0) : format.numericFormat().format(axisLegend);
                    for (x = fp.width(axisLegendString); x < verticalLegendWidth; ++x) {
                        writer.write(32);
                    }
                    writer.write(axisLegendString);
                    for (x = 0; x < verticalBorderWidth; ++x) {
                        writer.write(124);
                    }
                }
                for (int x = 0; x < chartWidth; ++x) {
                    int index = (int)((double)x / barWidth);
                    Result group = groups2.get(categories.get(index));
                    double[] values3 = new double[format.values().length];
                    for (Record record : group) {
                        for (int i = 0; i < values3.length; ++i) {
                            values3[i] = values3[i] + record.get(format.values()[i], Double.TYPE);
                        }
                    }
                    if (format.display() == ChartFormat.Display.STACKED || format.display() == ChartFormat.Display.HUNDRED_PERCENT_STACKED) {
                        for (int i = 1; i < values3.length; ++i) {
                            values3[i] = values3[i] + values3[i - 1];
                        }
                    }
                    if (format.display() == ChartFormat.Display.HUNDRED_PERCENT_STACKED) {
                        for (int i = 0; i < values3.length; ++i) {
                            values3[i] = values3[i] / values3[values3.length - 1];
                        }
                    }
                    int shadeIndex = -1;
                    int i = values3.length - 1;
                    while (i >= 0) {
                        if ((format.display() == ChartFormat.Display.HUNDRED_PERCENT_STACKED ? axisLegendPercent : axisLegend) > values3[i]) break;
                        shadeIndex = i--;
                    }
                    if (shadeIndex == -1) {
                        writer.write(32);
                        continue;
                    }
                    writer.write(format.shades()[shadeIndex % format.shades().length]);
                }
                writer.write(format.newline());
            }
            if (format.showHorizontalLegend()) {
                for (y = 0; y < horizontalBorderHeight; ++y) {
                    int x;
                    if (format.showVerticalLegend()) {
                        for (x = 0; x < verticalLegendWidth; ++x) {
                            writer.write(45);
                        }
                        for (x = 0; x < verticalBorderWidth; ++x) {
                            writer.write(43);
                        }
                    }
                    for (x = 0; x < chartWidth; ++x) {
                        writer.write(45);
                    }
                    writer.write(format.newline());
                }
                for (y = 0; y < horizontalLegendHeight; ++y) {
                    if (format.showVerticalLegend()) {
                        int x;
                        for (x = 0; x < verticalLegendWidth; ++x) {
                            writer.write(32);
                        }
                        for (x = 0; x < verticalBorderWidth; ++x) {
                            writer.write(124);
                        }
                    }
                    double rounding = 0.0;
                    double x = 0.0;
                    while (x < (double)chartWidth) {
                        int i;
                        String label = String.valueOf(categories.get((int)(x / barWidth)));
                        int width = fp.width(label);
                        double padding = Math.max((double)categoryPadding, (barWidth - (double)width) / 2.0);
                        rounding = (rounding + padding - Math.floor(padding)) % 1.0;
                        x += padding + rounding;
                        for (i = 0; i < (int)(padding + rounding); ++i) {
                            writer.write(32);
                        }
                        if ((x += (double)width) >= (double)chartWidth) break;
                        writer.write(label);
                        rounding = (rounding + padding - Math.floor(padding)) % 1.0;
                        x += padding + rounding;
                        for (i = 0; i < (int)(padding + rounding); ++i) {
                            writer.write(32);
                        }
                    }
                    writer.write(format.newline());
                }
            }
        }
        catch (IOException e) {
            throw new org.jooq.exception.IOException("Exception while writing Chart", e);
        }
    }

    @Override
    public final void formatInsert(Writer writer) {
        this.formatInsert(writer, null, this.fields.fields.fields);
    }

    @Override
    public final void formatInsert(Writer writer, Table<?> table, Field<?> ... f) {
        DSLContext ctx = this.configuration.dsl();
        try {
            for (Record record : this) {
                if (table == null) {
                    if (record instanceof TableRecord) {
                        TableRecord r = (TableRecord)record;
                        table = r.getTable();
                    } else {
                        table = DSL.table(DSL.name("UNKNOWN_TABLE"));
                    }
                }
                writer.append(ctx.renderInlined(DSL.insertInto(table, f).values(record.intoArray()))).append(";\n");
            }
            writer.flush();
        }
        catch (IOException e) {
            throw new org.jooq.exception.IOException("Exception while writing INSERTs", e);
        }
    }

    @Override
    public final void formatHTML(Writer writer) {
        try {
            writer.append("<table>");
            writer.append("<thead>");
            writer.append("<tr>");
            for (Field<?> field2 : this.fields.fields.fields) {
                writer.append("<th>");
                writer.append(AbstractResult.escapeXML(field2.getName()));
                writer.append("</th>");
            }
            writer.append("</tr>");
            writer.append("</thead>");
            writer.append("<tbody>");
            for (Record record : this) {
                record = this.nullSafe(record);
                writer.append("<tr>");
                int size = this.fields.size();
                for (int index = 0; index < size; ++index) {
                    writer.append("<td>");
                    writer.append(AbstractResult.escapeXML(AbstractResult.format0(record.getValue(index), false, true)));
                    writer.append("</td>");
                }
                writer.append("</tr>");
            }
            writer.append("</tbody>");
            writer.append("</table>");
            writer.flush();
        }
        catch (IOException e) {
            throw new org.jooq.exception.IOException("Exception while writing HTML", e);
        }
    }

    @Override
    public final Document intoXML(XMLFormat format) {
        try {
            DocumentBuilderFactory factory2 = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory2.newDocumentBuilder();
            Document document = builder.newDocument();
            Element eResult = document.createElement("result");
            if (format.xmlns()) {
                eResult.setAttribute("xmlns", "http://www.jooq.org/xsd/jooq-export-3.10.0.xsd");
            }
            document.appendChild(eResult);
            Element eRecordParent = eResult;
            if (format.header()) {
                Element eFields = document.createElement("fields");
                eResult.appendChild(eFields);
                for (Field<?> field2 : this.fields.fields.fields) {
                    TableField f;
                    Table table;
                    Element eField = document.createElement("field");
                    if (field2 instanceof TableField && (table = (f = (TableField)field2).getTable()) != null) {
                        Schema schema = table.getSchema();
                        if (schema != null) {
                            eField.setAttribute("schema", schema.getName());
                        }
                        eField.setAttribute("table", table.getName());
                    }
                    eField.setAttribute("name", field2.getName());
                    eField.setAttribute("type", this.formatTypeName(field2));
                    eFields.appendChild(eField);
                }
                Element eRecords = document.createElement("records");
                eResult.appendChild(eRecords);
                eRecordParent = eRecords;
            }
            for (Record record : this) {
                Element eRecord = document.createElement("record");
                eRecordParent.appendChild(eRecord);
                int size = this.fields.size();
                for (int index = 0; index < size; ++index) {
                    Field<?> field3 = this.fields.field(index);
                    Object value = record.get(index);
                    String tag = format.recordFormat() == XMLFormat.RecordFormat.COLUMN_NAME_ELEMENTS ? AbstractResult.escapeXML(this.fields.field(index).getName()) : "value";
                    Element eValue = document.createElement(tag);
                    if (format.recordFormat() == XMLFormat.RecordFormat.VALUE_ELEMENTS_WITH_FIELD_ATTRIBUTE) {
                        eValue.setAttribute("field", field3.getName());
                    }
                    eRecord.appendChild(eValue);
                    if (value == null) continue;
                    if (value instanceof XML && !format.quoteNested()) {
                        eValue.appendChild(AbstractResult.createContent(builder, document, ((XML)value).data()));
                        continue;
                    }
                    eValue.setTextContent(AbstractResult.format0(value, false, false));
                }
            }
            return document;
        }
        catch (ParserConfigurationException ignore) {
            throw new RuntimeException(ignore);
        }
    }

    static final DocumentFragment createContent(DocumentBuilder builder, Document doc, String text) {
        if (text != null && (text.contains("<") || text.contains("&"))) {
            builder.setErrorHandler(new DefaultHandler());
            try {
                text = text.trim();
                if (text.startsWith("<?xml")) {
                    Document parsed = builder.parse(new InputSource(new StringReader(text)));
                    DocumentFragment fragment = parsed.createDocumentFragment();
                    fragment.appendChild(parsed.getDocumentElement());
                    return (DocumentFragment)doc.importNode(fragment, true);
                }
                String wrapped = "<dummy>" + text + "</dummy>";
                Document parsed = builder.parse(new InputSource(new StringReader(wrapped)));
                DocumentFragment fragment = parsed.createDocumentFragment();
                NodeList children = parsed.getDocumentElement().getChildNodes();
                while (children.getLength() > 0) {
                    fragment.appendChild(children.item(0));
                }
                return (DocumentFragment)doc.importNode(fragment, true);
            }
            catch (IOException iOException) {
            }
            catch (SAXException sAXException) {
                // empty catch block
            }
        }
        return null;
    }

    @Override
    public final <H extends ContentHandler> H intoXML(H handler, XMLFormat format) throws SAXException {
        AttributesImpl empty = new AttributesImpl();
        handler.startDocument();
        if (format.xmlns()) {
            handler.startPrefixMapping("", "http://www.jooq.org/xsd/jooq-export-3.10.0.xsd");
        }
        handler.startElement("", "", "result", empty);
        if (format.header()) {
            handler.startElement("", "", "fields", empty);
            for (Field<?> field2 : this.fields.fields.fields) {
                TableField f;
                Table table;
                AttributesImpl attrs = new AttributesImpl();
                if (field2 instanceof TableField && (table = (f = (TableField)field2).getTable()) != null) {
                    Schema schema = table.getSchema();
                    if (schema != null) {
                        attrs.addAttribute("", "", "schema", "CDATA", schema.getName());
                    }
                    attrs.addAttribute("", "", "table", "CDATA", table.getName());
                }
                attrs.addAttribute("", "", "name", "CDATA", field2.getName());
                attrs.addAttribute("", "", "type", "CDATA", this.formatTypeName(field2));
                handler.startElement("", "", "field", attrs);
                handler.endElement("", "", "field");
            }
            handler.endElement("", "", "fields");
            handler.startElement("", "", "records", empty);
        }
        for (Record record : this) {
            handler.startElement("", "", "record", empty);
            int size = this.fields.size();
            for (int index = 0; index < size; ++index) {
                Field<?> field3 = this.fields.field(index);
                Object value = record.get(index);
                String tag = format.recordFormat() == XMLFormat.RecordFormat.COLUMN_NAME_ELEMENTS ? AbstractResult.escapeXML(this.fields.field(index).getName()) : "value";
                AttributesImpl attrs = new AttributesImpl();
                if (format.recordFormat() == XMLFormat.RecordFormat.VALUE_ELEMENTS_WITH_FIELD_ATTRIBUTE) {
                    attrs.addAttribute("", "", "field", "CDATA", field3.getName());
                }
                handler.startElement("", "", tag, attrs);
                if (value != null) {
                    char[] chars = AbstractResult.format0(value, false, false).toCharArray();
                    handler.characters(chars, 0, chars.length);
                }
                handler.endElement("", "", tag);
            }
            handler.endElement("", "", "record");
        }
        if (format.header()) {
            handler.endElement("", "", "records");
        }
        if (format.xmlns()) {
            handler.endPrefixMapping("");
        }
        handler.endDocument();
        return handler;
    }

    static final String format0(Object value, boolean changed, boolean visual) {
        Object formatted;
        Object object = formatted = changed && visual ? "*" : "";
        if (value == null) {
            formatted = (String)formatted + (visual ? "{null}" : null);
        } else if (value instanceof byte[]) {
            byte[] a = (byte[])value;
            formatted = (String)formatted + Base64.getEncoder().encodeToString(a);
        } else if (value instanceof Object[]) {
            Object[] a = (Object[])value;
            formatted = (String)formatted + Arrays.stream(a).map(f -> AbstractResult.format0(f, false, visual)).collect(Collectors.joining(", ", "[", "]"));
        } else if (value instanceof EnumType) {
            EnumType e = (EnumType)value;
            formatted = (String)formatted + e.getLiteral();
        } else if (value instanceof List) {
            List l = (List)value;
            formatted = (String)formatted + l.stream().map(f -> AbstractResult.format0(f, false, visual)).collect(Collectors.joining(", ", "[", "]"));
        } else if (value instanceof Record) {
            Record r = (Record)value;
            formatted = (String)formatted + Arrays.stream(r.intoArray()).map(f -> AbstractResult.format0(f, false, visual)).collect(Collectors.joining(", ", "(", ")"));
        } else if (value instanceof Param) {
            Param p = (Param)value;
            formatted = (String)formatted + AbstractResult.format0(p.getValue(), false, visual);
        } else if (value instanceof Date) {
            Date d = (Date)value;
            String date = value.toString();
            formatted = Date.valueOf(date).equals(value) ? (String)formatted + date : (String)formatted + String.valueOf(new Timestamp(d.getTime()));
        } else {
            formatted = (String)formatted + value.toString();
        }
        return formatted;
    }

    private static final String escapeXML(String string) {
        return StringUtils.replaceEach(string, new String[]{"\"", "'", "<", ">", "&"}, new String[]{"&quot;", "&apos;", "&lt;", "&gt;", "&amp;"});
    }
}

