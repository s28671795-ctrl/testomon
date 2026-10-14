/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.tools.json;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import org.jooq.tools.json.JSONValue;

public class JSONArray
extends ArrayList {
    public JSONArray() {
    }

    public JSONArray(Collection c) {
        super(c);
    }

    public static void writeJSONString(List<?> list, Writer out) throws IOException {
        if (list == null) {
            out.write("null");
            return;
        }
        boolean first = true;
        Iterator<?> iter = list.iterator();
        out.write(91);
        while (iter.hasNext()) {
            if (first) {
                first = false;
            } else {
                out.write(44);
            }
            Object value = iter.next();
            if (value == null) {
                out.write("null");
                continue;
            }
            JSONValue.writeJSONString(value, out);
        }
        out.write(93);
    }

    public static String toJSONString(List<?> list) {
        if (list == null) {
            return "null";
        }
        boolean first = true;
        StringBuffer sb = new StringBuffer();
        Iterator<?> iter = list.iterator();
        sb.append('[');
        while (iter.hasNext()) {
            if (first) {
                first = false;
            } else {
                sb.append(',');
            }
            Object value = iter.next();
            if (value == null) {
                sb.append("null");
                continue;
            }
            sb.append(JSONValue.toJSONString(value));
        }
        sb.append(']');
        return sb.toString();
    }

    @Override
    public String toString() {
        return JSONArray.toJSONString(this);
    }
}

