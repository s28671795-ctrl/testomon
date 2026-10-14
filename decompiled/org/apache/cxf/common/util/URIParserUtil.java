/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.common.util;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.StringTokenizer;
import org.apache.cxf.common.classloader.ClassLoaderUtils;

public final class URIParserUtil {
    private static final String EXCLUDED_CHARS = "<>\"{}|\\^`";
    private static final String HEX_DIGITS = "0123456789abcdef";

    private URIParserUtil() {
    }

    private static boolean isExcluded(char ch) {
        return ch <= ' ' || ch >= '\u007f' || EXCLUDED_CHARS.indexOf(ch) != -1;
    }

    public static URL[] pathToURLs(String path) {
        StringTokenizer st = new StringTokenizer(path, File.pathSeparator);
        URL[] urls2 = new URL[st.countTokens()];
        int count = 0;
        while (st.hasMoreTokens()) {
            File file = new File(st.nextToken());
            URL url2 = null;
            try {
                url2 = file.toURI().toURL();
            }
            catch (MalformedURLException e) {
                e.printStackTrace();
            }
            if (url2 == null) continue;
            urls2[count++] = url2;
        }
        if (urls2.length != count) {
            URL[] tmp = new URL[count];
            System.arraycopy(urls2, 0, tmp, 0, count);
            urls2 = tmp;
        }
        return urls2;
    }

    public static String escapeChars(String s2) {
        StringBuilder b = new StringBuilder(s2.length());
        for (int x = 0; x < s2.length(); ++x) {
            char ch = s2.charAt(x);
            if (URIParserUtil.isExcluded(ch)) {
                byte[] bytes = Character.toString(ch).getBytes(StandardCharsets.UTF_8);
                for (int y = 0; y < bytes.length; ++y) {
                    b.append('%');
                    b.append(HEX_DIGITS.charAt((bytes[y] & 0xFF) >> 4));
                    b.append(HEX_DIGITS.charAt(bytes[y] & 0xF));
                }
                continue;
            }
            b.append(ch);
        }
        return b.toString();
    }

    public static String normalize(String uri) {
        URL url2 = null;
        String result = null;
        try {
            url2 = new URL(uri);
            result = URIParserUtil.escapeChars(url2.toURI().normalize().toString().replace("\\", "/"));
        }
        catch (MalformedURLException e1) {
            try {
                if (uri.startsWith("classpath:")) {
                    url2 = ClassLoaderUtils.getResource(uri.substring(10), URIParserUtil.class);
                    return url2 != null ? url2.toExternalForm() : uri;
                }
                File file = new File(uri);
                if (file.exists()) {
                    return file.toURI().normalize().toString();
                }
                String f = uri.indexOf(58) != -1 && !uri.startsWith("/") ? "file:/" + uri : "file:" + uri;
                url2 = new URL(f);
                return URIParserUtil.escapeChars(url2.toString().replace("\\", "/"));
            }
            catch (Exception e2) {
                return URIParserUtil.escapeChars(uri.replace("\\", "/"));
            }
        }
        catch (URISyntaxException e) {
            result = URIParserUtil.escapeChars(url2.toString().replace("\\", "/"));
        }
        return result;
    }

    public static String getAbsoluteURI(String arg) {
        if (arg == null) {
            return null;
        }
        try {
            URI uri = new URI(arg);
            if ("file".equalsIgnoreCase(uri.getScheme())) {
                if (!uri.isOpaque()) {
                    return uri.normalize().toString();
                }
                return new File("").toURI().resolve(uri.getPath()).toString();
            }
            return URIParserUtil.normalize(arg);
        }
        catch (Exception e2) {
            return URIParserUtil.normalize(arg);
        }
    }

    public static String relativize(String base, String toBeRelativized) throws URISyntaxException {
        if (base == null || toBeRelativized == null) {
            return null;
        }
        return URIParserUtil.relativize(new URI(base), new URI(toBeRelativized));
    }

    public static String relativize(URI baseURI, URI toBeRelativizedURI) throws URISyntaxException {
        if (baseURI == null || toBeRelativizedURI == null) {
            return null;
        }
        if (baseURI.isAbsolute() ^ toBeRelativizedURI.isAbsolute()) {
            return null;
        }
        String base = baseURI.getSchemeSpecificPart();
        String toBeRelativized = toBeRelativizedURI.getSchemeSpecificPart();
        int l1 = base.length();
        int l2 = toBeRelativized.length();
        if (l1 == 0) {
            return toBeRelativized;
        }
        int slashes = 0;
        StringBuilder sb = new StringBuilder();
        boolean differenceFound = false;
        for (int i = 0; i < l1; ++i) {
            char c = base.charAt(i);
            if (i < l2) {
                if (!differenceFound && c == toBeRelativized.charAt(i)) {
                    sb.append(c);
                    continue;
                }
                differenceFound = true;
                if (c != '/') continue;
                ++slashes;
                continue;
            }
            if (c != '/') continue;
            ++slashes;
        }
        String rResolved = new URI(URIParserUtil.getRoot(sb.toString())).relativize(new URI(toBeRelativized)).toString();
        StringBuilder relativizedPath = new StringBuilder();
        for (int i = 0; i < slashes; ++i) {
            relativizedPath.append("../");
        }
        relativizedPath.append(rResolved);
        return relativizedPath.toString();
    }

    private static String getRoot(String uri) {
        int idx = uri.lastIndexOf(47);
        if (idx == uri.length() - 1) {
            return uri;
        }
        if (idx == -1) {
            return "";
        }
        return uri.substring(0, idx + 1);
    }
}

