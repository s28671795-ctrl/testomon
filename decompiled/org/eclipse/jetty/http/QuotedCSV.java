/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.jetty.http;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import org.eclipse.jetty.http.QuotedCSVParser;
import org.eclipse.jetty.util.QuotedStringTokenizer;

public class QuotedCSV
extends QuotedCSVParser
implements Iterable<String> {
    public static final String ABNF_REQUIRED_QUOTING = "\"'\\\n\r\t\f\b%+ ;=,";
    protected final List<String> _values = new ArrayList<String>();

    public static String join(List<String> values2) {
        if (values2 == null) {
            return null;
        }
        int size = values2.size();
        if (size <= 0) {
            return "";
        }
        if (size == 1) {
            return values2.get(0);
        }
        StringBuilder ret = new StringBuilder();
        QuotedCSV.join(ret, values2);
        return ret.toString();
    }

    public static String join(String ... values2) {
        if (values2 == null) {
            return null;
        }
        if (values2.length <= 0) {
            return "";
        }
        if (values2.length == 1) {
            return values2[0];
        }
        StringBuilder ret = new StringBuilder();
        QuotedCSV.join(ret, Arrays.asList(values2));
        return ret.toString();
    }

    public static void join(StringBuilder builder, List<String> values2) {
        if (values2 == null || values2.isEmpty()) {
            return;
        }
        boolean needsDelim = false;
        for (String value : values2) {
            if (needsDelim) {
                builder.append(", ");
            } else {
                needsDelim = true;
            }
            QuotedStringTokenizer.quoteIfNeeded(builder, value, ABNF_REQUIRED_QUOTING);
        }
    }

    public QuotedCSV(String ... values2) {
        this(true, values2);
    }

    public QuotedCSV(boolean keepQuotes, String ... values2) {
        super(keepQuotes);
        for (String v : values2) {
            this.addValue(v);
        }
    }

    @Override
    protected void parsedValueAndParams(StringBuffer buffer) {
        this._values.add(buffer.toString());
    }

    public int size() {
        return this._values.size();
    }

    public boolean isEmpty() {
        return this._values.isEmpty();
    }

    public List<String> getValues() {
        return this._values;
    }

    @Override
    public Iterator<String> iterator() {
        return this._values.iterator();
    }

    public String toString() {
        ArrayList<String> list = new ArrayList<String>();
        for (String s2 : this) {
            list.add(s2);
        }
        return ((Object)list).toString();
    }
}

