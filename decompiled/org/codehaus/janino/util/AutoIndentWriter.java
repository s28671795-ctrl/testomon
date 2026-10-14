/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.janino.util;

import java.io.FilterWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.codehaus.commons.nullanalysis.Nullable;

public class AutoIndentWriter
extends FilterWriter {
    public static final char TABULATOR = '\uffff';
    public static final char CLEAR_TABULATORS = '\ufffe';
    public static final char INDENT = '\ufffd';
    public static final char UNINDENT = '\ufffc';
    private final StringBuilder lineBuffer = new StringBuilder();
    private int indentation;
    @Nullable
    private List<StringBuilder> tabulatorBuffer;
    private int tabulatorIndentation;

    public AutoIndentWriter(Writer out) {
        super(out);
    }

    @Override
    public void write(@Nullable char[] cbuf, int off, int len) throws IOException {
        assert (cbuf != null);
        while (len > 0) {
            this.write(cbuf[off++]);
            --len;
        }
    }

    @Override
    public void write(@Nullable String str, int off, int len) throws IOException {
        assert (str != null);
        while (len > 0) {
            this.write(str.charAt(off++));
            --len;
        }
    }

    @Override
    public void write(int c) throws IOException {
        if (c == 10) {
            this.lineBuffer.append('\n');
            this.line(this.lineBuffer.toString());
            this.lineBuffer.setLength(0);
            return;
        }
        if (this.lineBuffer.length() > 0 && this.lineBuffer.charAt(this.lineBuffer.length() - 1) == '\r') {
            this.line(this.lineBuffer.toString());
            this.lineBuffer.setCharAt(0, (char)c);
            this.lineBuffer.setLength(1);
            return;
        }
        this.lineBuffer.append((char)c);
    }

    private void line(String line) throws IOException {
        if (this.tabulatorBuffer != null) {
            this.tabulatorBuffer.add(new StringBuilder(line.length()).append(line));
            if (line.charAt(0) == '\ufffd') {
                ++this.indentation;
                line = line.substring(1);
            }
            if (line.charAt(0) == '\ufffc' && --this.indentation < this.tabulatorIndentation) {
                this.flushTabulatorBuffer();
            }
        } else if (line.indexOf(65535) != -1) {
            if (line.charAt(0) == '\ufffd') {
                ++this.indentation;
                line = line.substring(1);
            }
            if (line.charAt(0) == '\ufffc') {
                --this.indentation;
                line = line.substring(1);
            }
            this.tabulatorBuffer = new ArrayList<StringBuilder>();
            this.tabulatorBuffer.add(new StringBuilder(line.length()).append(line));
            this.tabulatorIndentation = this.indentation;
        } else {
            if (line.charAt(0) == '\ufffe') {
                line = line.substring(1);
            }
            if (line.charAt(0) == '\ufffd') {
                ++this.indentation;
                line = line.substring(1);
            }
            if (line.charAt(0) == '\ufffc') {
                --this.indentation;
                line = line.substring(1);
            }
            if ("\r\n".indexOf(line.charAt(0)) == -1) {
                for (int i = 0; i < this.indentation; ++i) {
                    this.out.write("    ");
                }
            }
            this.out.write(line);
        }
    }

    private void flushTabulatorBuffer() throws IOException {
        ArrayList lineGroups = new ArrayList();
        lineGroups.add(new ArrayList());
        List<StringBuilder> tb = this.tabulatorBuffer;
        assert (tb != null);
        for (StringBuilder stringBuilder : tb) {
            int idx = 0;
            if (stringBuilder.charAt(0) == '\ufffd') {
                lineGroups.add(new ArrayList());
                ++idx;
            }
            if (stringBuilder.charAt(idx) == '\ufffc') {
                AutoIndentWriter.resolveTabs((List)lineGroups.remove(lineGroups.size() - 1));
                ++idx;
            }
            if (stringBuilder.charAt(idx) == '\ufffe') {
                List lg = (List)lineGroups.get(lineGroups.size() - 1);
                AutoIndentWriter.resolveTabs(lg);
                lg.clear();
                stringBuilder.deleteCharAt(idx);
            }
            for (int i = 0; i < stringBuilder.length(); ++i) {
                if (stringBuilder.charAt(i) != '\uffff') continue;
                ((List)lineGroups.get(lineGroups.size() - 1)).add(stringBuilder);
            }
        }
        for (List list : lineGroups) {
            AutoIndentWriter.resolveTabs(list);
        }
        int ind = this.tabulatorIndentation;
        for (StringBuilder sb : tb) {
            String line = sb.toString();
            if (line.charAt(0) == '\ufffd') {
                ++ind;
                line = line.substring(1);
            }
            if (line.charAt(0) == '\ufffc') {
                --ind;
                line = line.substring(1);
            }
            if ("\r\n".indexOf(line.charAt(0)) == -1) {
                for (int i = 0; i < ind; ++i) {
                    this.out.write("    ");
                }
            }
            this.out.write(line.toString());
        }
        this.tabulatorBuffer = null;
    }

    private static void resolveTabs(List<StringBuilder> lineGroup) {
        int tabOffset;
        int i;
        ArrayList<Integer> tabulatorOffsets = new ArrayList<Integer>();
        for (StringBuilder line : lineGroup) {
            int previousTab;
            if (line.charAt(previousTab = 0) == '\ufffd') {
                ++previousTab;
            }
            if (line.charAt(previousTab) == '\ufffc') {
                // empty if block
            }
            int tabCount = 0;
            for (i = ++previousTab; i < line.length(); ++i) {
                if (line.charAt(i) != '\uffff') continue;
                tabOffset = i - previousTab;
                previousTab = i;
                if (tabCount >= tabulatorOffsets.size()) {
                    tabulatorOffsets.add(tabOffset);
                } else if (tabOffset > (Integer)tabulatorOffsets.get(tabCount)) {
                    tabulatorOffsets.set(tabCount, tabOffset);
                }
                ++tabCount;
            }
        }
        for (StringBuilder line : lineGroup) {
            int tabCount = 0;
            int previousTab = 0;
            if (line.charAt(previousTab) == '\ufffd') {
                ++previousTab;
            }
            if (line.charAt(previousTab) == '\ufffc') {
                // empty if block
            }
            for (i = ++previousTab; i < line.length(); ++i) {
                if (line.charAt(i) != '\uffff') continue;
                tabOffset = i - previousTab;
                int n = (Integer)tabulatorOffsets.get(tabCount++) - tabOffset;
                line.replace(i, i + 1, AutoIndentWriter.spaces(n));
                previousTab = i += n - 1;
            }
        }
    }

    private static String spaces(int n) {
        if (n < 30) {
            return "                              ".substring(0, n);
        }
        char[] data = new char[n];
        Arrays.fill(data, ' ');
        return String.valueOf(data);
    }

    @Override
    public void close() throws IOException {
        if (this.tabulatorBuffer != null) {
            this.flushTabulatorBuffer();
        }
        if (this.lineBuffer.length() > 0) {
            this.line(this.lineBuffer.toString());
        }
        this.out.close();
    }

    @Override
    public void flush() throws IOException {
        if (this.tabulatorBuffer != null) {
            this.flushTabulatorBuffer();
        }
        if (this.lineBuffer.length() > 0) {
            this.line(this.lineBuffer.toString());
            this.lineBuffer.setLength(0);
        }
        this.out.flush();
    }
}

