/*
 * Decompiled with CFR 0.152.
 */
package io.minio;

import com.google.common.escape.Escaper;
import com.google.common.net.UrlEscapers;

public class S3Escaper {
    private static final Escaper ESCAPER = UrlEscapers.urlPathSegmentEscaper();

    public static String encode(String str) {
        if (str == null) {
            return "";
        }
        StringBuilder builder = new StringBuilder();
        block18: for (char ch : ESCAPER.escape(str).toCharArray()) {
            switch (ch) {
                case '!': {
                    builder.append("%21");
                    continue block18;
                }
                case '$': {
                    builder.append("%24");
                    continue block18;
                }
                case '&': {
                    builder.append("%26");
                    continue block18;
                }
                case '\'': {
                    builder.append("%27");
                    continue block18;
                }
                case '(': {
                    builder.append("%28");
                    continue block18;
                }
                case ')': {
                    builder.append("%29");
                    continue block18;
                }
                case '*': {
                    builder.append("%2A");
                    continue block18;
                }
                case '+': {
                    builder.append("%2B");
                    continue block18;
                }
                case ',': {
                    builder.append("%2C");
                    continue block18;
                }
                case '/': {
                    builder.append("%2F");
                    continue block18;
                }
                case ':': {
                    builder.append("%3A");
                    continue block18;
                }
                case ';': {
                    builder.append("%3B");
                    continue block18;
                }
                case '=': {
                    builder.append("%3D");
                    continue block18;
                }
                case '@': {
                    builder.append("%40");
                    continue block18;
                }
                case '[': {
                    builder.append("%5B");
                    continue block18;
                }
                case ']': {
                    builder.append("%5D");
                    continue block18;
                }
                default: {
                    builder.append(ch);
                }
            }
        }
        return builder.toString();
    }

    public static String encodePath(String path) {
        StringBuilder encodedPath = new StringBuilder();
        for (String pathSegment : path.split("/")) {
            if (pathSegment.isEmpty()) continue;
            if (encodedPath.length() > 0) {
                encodedPath.append("/");
            }
            encodedPath.append(S3Escaper.encode(pathSegment));
        }
        if (path.startsWith("/")) {
            encodedPath.insert(0, "/");
        }
        if (path.endsWith("/")) {
            encodedPath.append("/");
        }
        return encodedPath.toString();
    }
}

