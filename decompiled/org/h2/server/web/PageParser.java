/*
 * Decompiled with CFR 0.152.
 */
package org.h2.server.web;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import org.h2.util.StringUtils;

public class PageParser {
    private static final int TAB_WIDTH = 4;
    private final String page;
    private int pos;
    private final Map<String, Object> settings;
    private final int len;
    private StringBuilder result;

    private PageParser(String string, Map<String, Object> map, int n) {
        this.page = string;
        this.pos = n;
        this.len = string.length();
        this.settings = map;
        this.result = new StringBuilder(this.len);
    }

    public static String parse(String string, Map<String, Object> map) {
        PageParser pageParser = new PageParser(string, map, 0);
        return pageParser.replaceTags();
    }

    private void setError(int n) {
        String string = this.page.substring(0, n) + "####BUG####" + this.page.substring(n);
        string = PageParser.escapeHtml(string);
        this.result = new StringBuilder();
        this.result.append(string);
    }

    private String parseBlockUntil(String string) throws ParseException {
        PageParser pageParser = new PageParser(this.page, this.settings, this.pos);
        pageParser.parseAll();
        if (!pageParser.readIf(string)) {
            throw new ParseException(this.page, pageParser.pos);
        }
        this.pos = pageParser.pos;
        return pageParser.result.toString();
    }

    private String replaceTags() {
        try {
            this.parseAll();
            if (this.pos != this.len) {
                this.setError(this.pos);
            }
        }
        catch (ParseException parseException) {
            this.setError(this.pos);
        }
        return this.result.toString();
    }

    private void parseAll() throws ParseException {
        int n;
        StringBuilder stringBuilder = this.result;
        String string = this.page;
        block4: for (n = this.pos; n < this.len; ++n) {
            char c = string.charAt(n);
            switch (c) {
                case '<': {
                    String string2;
                    if (string.charAt(n + 3) == ':' && string.charAt(n + 1) == '/') {
                        this.pos = n;
                        return;
                    }
                    if (string.charAt(n + 2) == ':') {
                        Object object;
                        Object object2;
                        String string3;
                        this.pos = n;
                        if (this.readIf("<c:forEach")) {
                            string3 = this.readParam("var");
                            string2 = this.readParam("items");
                            this.read(">");
                            int n2 = this.pos;
                            object2 = (ArrayList)this.get(string2);
                            if (object2 == null) {
                                this.result.append("?items?");
                                object2 = new ArrayList();
                            }
                            if (object2.isEmpty()) {
                                this.parseBlockUntil("</c:forEach>");
                            }
                            object = object2.iterator();
                            while (object.hasNext()) {
                                Object e = object.next();
                                this.settings.put(string3, e);
                                this.pos = n2;
                                String string4 = this.parseBlockUntil("</c:forEach>");
                                this.result.append(string4);
                            }
                        } else if (this.readIf("<c:if")) {
                            string3 = this.readParam("test");
                            int n3 = string3.indexOf("=='");
                            if (n3 < 0) {
                                this.setError(n);
                                return;
                            }
                            String string5 = string3.substring(n3 + 3, string3.length() - 1);
                            string3 = string3.substring(0, n3);
                            object2 = (String)this.get(string3);
                            this.read(">");
                            object = this.parseBlockUntil("</c:if>");
                            --this.pos;
                            if (((String)object2).equals(string5)) {
                                this.result.append((String)object);
                            }
                        } else {
                            this.setError(n);
                            return;
                        }
                        n = this.pos;
                        continue block4;
                    }
                    stringBuilder.append(c);
                    continue block4;
                }
                case '$': {
                    String string2;
                    if (string.length() > n + 1 && string.charAt(n + 1) == '{') {
                        int n4 = string.indexOf(125, n += 2);
                        if (n4 < 0) {
                            this.setError(n);
                            return;
                        }
                        string2 = StringUtils.trimSubstring(string, n, n4);
                        n = n4;
                        String string6 = (String)this.get(string2);
                        this.replaceTags(string6);
                        continue block4;
                    }
                    stringBuilder.append(c);
                    continue block4;
                }
                default: {
                    stringBuilder.append(c);
                }
            }
        }
        this.pos = n;
    }

    private Object get(String string) {
        int n = string.indexOf(46);
        if (n >= 0) {
            String string2 = string.substring(n + 1);
            HashMap hashMap = (HashMap)this.settings.get(string = string.substring(0, n));
            if (hashMap == null) {
                return "?" + string + "?";
            }
            return hashMap.get(string2);
        }
        return this.settings.get(string);
    }

    private void replaceTags(String string) {
        if (string != null) {
            this.result.append(PageParser.parse(string, this.settings));
        }
    }

    private String readParam(String string) throws ParseException {
        this.read(string);
        this.read("=");
        this.read("\"");
        int n = this.pos;
        while (this.page.charAt(this.pos) != '\"') {
            ++this.pos;
        }
        int n2 = this.pos;
        this.read("\"");
        String string2 = this.page.substring(n, n2);
        return PageParser.parse(string2, this.settings);
    }

    private void skipSpaces() {
        while (this.page.charAt(this.pos) == ' ') {
            ++this.pos;
        }
    }

    private void read(String string) throws ParseException {
        if (!this.readIf(string)) {
            throw new ParseException(string, this.pos);
        }
    }

    private boolean readIf(String string) {
        this.skipSpaces();
        if (this.page.regionMatches(this.pos, string, 0, string.length())) {
            this.pos += string.length();
            this.skipSpaces();
            return true;
        }
        return false;
    }

    static String escapeHtmlData(String string) {
        return PageParser.escapeHtml(string, false);
    }

    public static String escapeHtml(String string) {
        return PageParser.escapeHtml(string, true);
    }

    private static String escapeHtml(String string, boolean bl) {
        int n;
        if (string == null) {
            return null;
        }
        int n2 = string.length();
        if (bl && n2 == 0) {
            return "&nbsp;";
        }
        StringBuilder stringBuilder = new StringBuilder(n2);
        boolean bl2 = true;
        block9: for (int i = 0; i < n2; i += Character.charCount(n)) {
            n = string.codePointAt(i);
            if (n == 32 || n == 9) {
                for (int j = 0; j < (n == 32 ? 1 : 4); ++j) {
                    if (bl2 && bl) {
                        stringBuilder.append("&nbsp;");
                        continue;
                    }
                    stringBuilder.append(' ');
                    bl2 = true;
                }
                continue;
            }
            bl2 = false;
            switch (n) {
                case 36: {
                    stringBuilder.append("&#36;");
                    continue block9;
                }
                case 60: {
                    stringBuilder.append("&lt;");
                    continue block9;
                }
                case 62: {
                    stringBuilder.append("&gt;");
                    continue block9;
                }
                case 38: {
                    stringBuilder.append("&amp;");
                    continue block9;
                }
                case 34: {
                    stringBuilder.append("&quot;");
                    continue block9;
                }
                case 39: {
                    stringBuilder.append("&#39;");
                    continue block9;
                }
                case 10: {
                    if (bl) {
                        stringBuilder.append("<br />");
                        bl2 = true;
                        continue block9;
                    }
                    stringBuilder.append(n);
                    continue block9;
                }
                default: {
                    if (n >= 128) {
                        stringBuilder.append("&#").append(n).append(';');
                        continue block9;
                    }
                    stringBuilder.append((char)n);
                }
            }
        }
        return stringBuilder.toString();
    }

    static String escapeJavaScript(String string) {
        if (string == null) {
            return null;
        }
        int n = string.length();
        if (n == 0) {
            return "";
        }
        StringBuilder stringBuilder = new StringBuilder(n);
        block8: for (int i = 0; i < n; ++i) {
            char c = string.charAt(i);
            switch (c) {
                case '\"': {
                    stringBuilder.append("\\\"");
                    continue block8;
                }
                case '\'': {
                    stringBuilder.append("\\'");
                    continue block8;
                }
                case '\\': {
                    stringBuilder.append("\\\\");
                    continue block8;
                }
                case '\n': {
                    stringBuilder.append("\\n");
                    continue block8;
                }
                case '\r': {
                    stringBuilder.append("\\r");
                    continue block8;
                }
                case '\t': {
                    stringBuilder.append("\\t");
                    continue block8;
                }
                default: {
                    stringBuilder.append(c);
                }
            }
        }
        return stringBuilder.toString();
    }
}

