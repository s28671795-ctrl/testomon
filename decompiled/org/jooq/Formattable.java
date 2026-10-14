/*
 * Decompiled with CFR 0.152.
 */
package org.jooq;

import java.io.OutputStream;
import java.io.Writer;
import org.jetbrains.annotations.NotNull;
import org.jooq.CSVFormat;
import org.jooq.ChartFormat;
import org.jooq.Field;
import org.jooq.JSONFormat;
import org.jooq.TXTFormat;
import org.jooq.Table;
import org.jooq.XMLFormat;
import org.jooq.exception.IOException;
import org.w3c.dom.Document;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;

public interface Formattable {
    @NotNull
    public String format();

    @NotNull
    public String format(int var1);

    @NotNull
    public String format(TXTFormat var1);

    @NotNull
    public String formatHTML();

    @NotNull
    public String formatCSV();

    @NotNull
    public String formatCSV(char var1);

    @NotNull
    public String formatCSV(char var1, String var2);

    @NotNull
    public String formatCSV(boolean var1);

    @NotNull
    public String formatCSV(boolean var1, char var2);

    @NotNull
    public String formatCSV(boolean var1, char var2, String var3);

    @NotNull
    public String formatCSV(CSVFormat var1);

    @NotNull
    public String formatJSON();

    @NotNull
    public String formatJSON(JSONFormat var1);

    @NotNull
    public String formatXML();

    @NotNull
    public String formatXML(XMLFormat var1);

    @NotNull
    public String formatChart();

    @NotNull
    public String formatChart(ChartFormat var1);

    @NotNull
    public String formatInsert();

    @NotNull
    public String formatInsert(Table<?> var1, Field<?> ... var2);

    public void format(OutputStream var1) throws IOException;

    public void format(OutputStream var1, int var2) throws IOException;

    public void format(OutputStream var1, TXTFormat var2) throws IOException;

    public void formatHTML(OutputStream var1) throws IOException;

    public void formatCSV(OutputStream var1) throws IOException;

    public void formatCSV(OutputStream var1, char var2) throws IOException;

    public void formatCSV(OutputStream var1, char var2, String var3) throws IOException;

    public void formatCSV(OutputStream var1, boolean var2) throws IOException;

    public void formatCSV(OutputStream var1, boolean var2, char var3) throws IOException;

    public void formatCSV(OutputStream var1, boolean var2, char var3, String var4) throws IOException;

    public void formatCSV(OutputStream var1, CSVFormat var2) throws IOException;

    public void formatJSON(OutputStream var1) throws IOException;

    public void formatJSON(OutputStream var1, JSONFormat var2) throws IOException;

    public void formatXML(OutputStream var1) throws IOException;

    public void formatXML(OutputStream var1, XMLFormat var2) throws IOException;

    public void formatChart(OutputStream var1) throws IOException;

    public void formatChart(OutputStream var1, ChartFormat var2) throws IOException;

    public void formatInsert(OutputStream var1) throws IOException;

    public void formatInsert(OutputStream var1, Table<?> var2, Field<?> ... var3) throws IOException;

    public void format(Writer var1) throws IOException;

    public void format(Writer var1, int var2) throws IOException;

    public void format(Writer var1, TXTFormat var2) throws IOException;

    public void formatHTML(Writer var1) throws IOException;

    public void formatCSV(Writer var1) throws IOException;

    public void formatCSV(Writer var1, char var2) throws IOException;

    public void formatCSV(Writer var1, char var2, String var3) throws IOException;

    public void formatCSV(Writer var1, boolean var2) throws IOException;

    public void formatCSV(Writer var1, boolean var2, char var3) throws IOException;

    public void formatCSV(Writer var1, boolean var2, char var3, String var4) throws IOException;

    public void formatCSV(Writer var1, CSVFormat var2) throws IOException;

    public void formatJSON(Writer var1) throws IOException;

    public void formatJSON(Writer var1, JSONFormat var2) throws IOException;

    public void formatXML(Writer var1) throws IOException;

    public void formatXML(Writer var1, XMLFormat var2) throws IOException;

    public void formatChart(Writer var1) throws IOException;

    public void formatChart(Writer var1, ChartFormat var2) throws IOException;

    public void formatInsert(Writer var1) throws IOException;

    public void formatInsert(Writer var1, Table<?> var2, Field<?> ... var3) throws IOException;

    @NotNull
    public Document intoXML();

    @NotNull
    public Document intoXML(XMLFormat var1);

    public <H extends ContentHandler> H intoXML(H var1) throws SAXException;

    public <H extends ContentHandler> H intoXML(H var1, XMLFormat var2) throws SAXException;
}

