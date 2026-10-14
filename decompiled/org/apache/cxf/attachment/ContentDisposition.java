/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.attachment;

import java.io.UnsupportedEncodingException;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.cxf.attachment.Rfc5987Util;

public class ContentDisposition {
    private static final String CD_HEADER_PARAMS_EXPRESSION = "[\\w-]++( )?\\*?=( )?((\"[^\"]++\")|([^;]+))";
    private static final Pattern CD_HEADER_PARAMS_PATTERN = Pattern.compile("[\\w-]++( )?\\*?=( )?((\"[^\"]++\")|([^;]+))");
    private static final String CD_HEADER_EXT_PARAMS_EXPRESSION = "(?i)(UTF-8|ISO-8859-1)''((?:%[0-9a-f]{2}|\\S)+)";
    private static final Pattern CD_HEADER_EXT_PARAMS_PATTERN = Pattern.compile("(?i)(UTF-8|ISO-8859-1)''((?:%[0-9a-f]{2}|\\S)+)");
    private static final Pattern CODEPOINT_ENCODED_VALUE_PATTERN = Pattern.compile("&#[0-9]{4};|\\S");
    private String value;
    private String type;
    private Map<String, String> params = new LinkedHashMap<String, String>();

    public ContentDisposition(String value) {
        this.value = value;
        String tempValue = value;
        int index = tempValue.indexOf(59);
        if (index > 0 && tempValue.indexOf(61) >= index) {
            this.type = tempValue.substring(0, index).trim();
            tempValue = tempValue.substring(index + 1);
        }
        String extendedFilename = null;
        Matcher m4 = CD_HEADER_PARAMS_PATTERN.matcher(tempValue);
        while (m4.find()) {
            Matcher matcher2;
            String paramName = null;
            String paramValue = "";
            String groupValue = m4.group().trim();
            int eqIndex = groupValue.indexOf(61);
            if (eqIndex > 0) {
                paramName = groupValue.substring(0, eqIndex).trim();
                if (eqIndex + 1 != groupValue.length()) {
                    paramValue = groupValue.substring(eqIndex + 1).trim().replace("\"", "");
                }
            } else {
                paramName = groupValue;
            }
            if ("filename*".equalsIgnoreCase(paramName)) {
                try {
                    matcher2 = CD_HEADER_EXT_PARAMS_PATTERN.matcher(paramValue);
                    if (matcher2.matches()) {
                        String encodingScheme = matcher2.group(1);
                        String encodedValue = matcher2.group(2);
                        extendedFilename = paramValue = Rfc5987Util.decode(encodedValue, encodingScheme);
                    }
                }
                catch (UnsupportedEncodingException matcher2) {}
            } else if ("filename".equalsIgnoreCase(paramName) && paramValue.contains("&#")) {
                matcher2 = CODEPOINT_ENCODED_VALUE_PATTERN.matcher(paramValue);
                StringBuilder sb = new StringBuilder();
                while (matcher2.find()) {
                    String matched = matcher2.group();
                    if (matched.startsWith("&#")) {
                        int codePoint = Integer.parseInt(matched.substring(2, 6));
                        sb.append(Character.toChars(codePoint));
                        continue;
                    }
                    sb.append(matched.charAt(0));
                }
                if (sb.length() > 0) {
                    paramValue = sb.toString();
                }
            }
            this.params.put(paramName.toLowerCase(), paramValue);
        }
        if (extendedFilename != null) {
            this.params.put("filename", extendedFilename);
        }
    }

    public String getType() {
        return this.type;
    }

    public String getFilename() {
        return this.params.get("filename");
    }

    public String getParameter(String name) {
        return this.params.get(name);
    }

    public Map<String, String> getParameters() {
        return Collections.unmodifiableMap(this.params);
    }

    public String toString() {
        return this.value;
    }
}

