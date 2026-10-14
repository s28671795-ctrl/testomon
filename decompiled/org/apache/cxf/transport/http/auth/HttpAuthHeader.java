/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.transport.http.auth;

import java.io.IOException;
import java.io.StreamTokenizer;
import java.io.StringReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class HttpAuthHeader {
    public static final String AUTH_TYPE_BASIC = "Basic";
    public static final String AUTH_TYPE_DIGEST = "Digest";
    public static final String AUTH_TYPE_NEGOTIATE = "Negotiate";
    private String fullHeader;
    private String authType;
    private String fullContent;
    private Map<String, String> params;

    public HttpAuthHeader(String fullHeader) {
        this.fullHeader = fullHeader == null ? "" : fullHeader;
        int spacePos = this.fullHeader.indexOf(32);
        if (spacePos == -1) {
            this.authType = this.fullHeader;
            this.fullContent = "";
        } else {
            this.authType = this.fullHeader.substring(0, spacePos);
            this.fullContent = this.fullHeader.substring(spacePos + 1);
        }
        this.params = this.parseHeader();
    }

    public HttpAuthHeader(List<String> params) {
        boolean first = true;
        for (String s2 : params) {
            if (!first) {
                this.fullHeader = this.fullHeader + ", " + s2;
                continue;
            }
            first = false;
            this.fullHeader = s2;
        }
        int spacePos = this.fullHeader.indexOf(32);
        if (spacePos == -1) {
            this.authType = this.fullHeader;
            this.fullContent = "";
        } else {
            this.authType = this.fullHeader.substring(0, spacePos);
            this.fullContent = this.fullHeader.substring(spacePos + 1);
        }
        this.params = this.parseHeader();
    }

    public HttpAuthHeader(String authType, Map<String, String> params) {
        this.authType = authType;
        this.params = params;
        this.fullContent = this.paramsToString();
        this.fullHeader = authType + " " + this.fullContent;
    }

    private String paramsToString() {
        StringBuilder builder = new StringBuilder();
        boolean first = true;
        for (Map.Entry<String, String> entry : this.params.entrySet()) {
            String param2 = entry.getValue();
            if (param2 == null) continue;
            if (!first) {
                builder.append(", ");
            }
            if ("nc".equals(entry.getKey()) || "qop".equals(entry.getKey()) || "algorithm".equals(entry.getKey())) {
                builder.append(entry.getKey()).append('=').append(param2);
            } else {
                builder.append(entry.getKey()).append("=\"").append(param2).append('\"');
            }
            first = false;
        }
        return builder.toString();
    }

    private Map<String, String> parseHeader() {
        HashMap<String, String> map = new HashMap<String, String>();
        try {
            StreamTokenizer tok = new StreamTokenizer(new StringReader(this.fullContent));
            tok.quoteChar(34);
            tok.quoteChar(39);
            tok.whitespaceChars(61, 61);
            tok.whitespaceChars(44, 44);
            while (tok.nextToken() != -1) {
                String key = tok.sval;
                if (tok.nextToken() == -1) {
                    map.put(key, null);
                    return map;
                }
                String value = null;
                if ("nc".equals(key)) {
                    value = String.valueOf(tok.nval);
                    if (value.indexOf(46) > 0) {
                        value = value.substring(0, value.indexOf(46));
                    }
                    StringBuilder pad = new StringBuilder();
                    pad.append("");
                    for (int i = 0; i < 8 - value.length(); ++i) {
                        pad.append('0');
                    }
                    value = pad.toString() + value;
                } else {
                    value = tok.sval;
                }
                map.put(key, value);
            }
        }
        catch (IOException iOException) {
            // empty catch block
        }
        return map;
    }

    public String getRealm() {
        Map<String, String> map = this.parseHeader();
        return map.get("realm");
    }

    public boolean authTypeIsDigest() {
        return AUTH_TYPE_DIGEST.equals(this.authType);
    }

    public boolean authTypeIsBasic() {
        return AUTH_TYPE_BASIC.equals(this.authType);
    }

    public boolean authTypeIsNegotiate() {
        return AUTH_TYPE_NEGOTIATE.equals(this.authType);
    }

    public String getAuthType() {
        return this.authType;
    }

    public String getFullContent() {
        return this.fullContent;
    }

    public String getFullHeader() {
        return this.fullHeader;
    }

    public Map<String, String> getParams() {
        return this.params;
    }
}

