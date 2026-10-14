/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.common.util;

import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.StringTokenizer;
import org.apache.cxf.helpers.JavaUtils;

public final class PackageUtils {
    private PackageUtils() {
    }

    static String getPackageName(String className) {
        int pos = className.lastIndexOf(46);
        if (pos != -1) {
            return className.substring(0, pos);
        }
        return "";
    }

    public static String getPackageName(Class<?> clazz) {
        String className = clazz.getName();
        if (className.startsWith("[L")) {
            className = className.substring(2);
        }
        return PackageUtils.getPackageName(className);
    }

    public static String getSharedPackageName(List<Class<?>> classes) {
        if (classes.isEmpty()) {
            return "";
        }
        ArrayList<List<String>> lParts = new ArrayList<List<String>>(classes.size());
        ArrayList currentParts = new ArrayList();
        for (Class<?> cls : classes) {
            if (Proxy.isProxyClass(cls)) continue;
            lParts.add(Arrays.asList(PackageUtils.getPackageName(cls).split("\\.")));
        }
        for (int i = 0; i < ((List)lParts.get(0)).size(); ++i) {
            int j;
            for (j = 1; j < lParts.size() && i <= ((List)lParts.get(j)).size() - 1 && ((String)((List)lParts.get(j)).get(i)).equals(((List)lParts.get(0)).get(i)); ++j) {
            }
            if (j != lParts.size()) break;
            currentParts.add(((List)lParts.get(j - 1)).get(i));
        }
        return String.join((CharSequence)".", currentParts);
    }

    public static String parsePackageName(String namespace, String defaultPackageName) {
        return defaultPackageName != null && !defaultPackageName.trim().isEmpty() ? defaultPackageName : PackageUtils.getPackageNameByNameSpaceURI(namespace.trim());
    }

    public static String getPackageNameByNameSpaceURI(String nameSpaceURI) {
        String lastToken;
        List<String> tokens;
        int idx = nameSpaceURI.indexOf(58);
        boolean urnScheme = false;
        if (idx >= 0) {
            String scheme2 = nameSpaceURI.substring(0, idx);
            urnScheme = "urn".equalsIgnoreCase(scheme2);
            if ("http".equalsIgnoreCase(scheme2) || urnScheme) {
                nameSpaceURI = nameSpaceURI.substring(idx + (urnScheme ? 1 : 3));
            }
        }
        if ((tokens = PackageUtils.tokenize(nameSpaceURI, "/:")).isEmpty()) {
            return null;
        }
        if (tokens.size() > 1 && (idx = (lastToken = tokens.get(tokens.size() - 1)).lastIndexOf(46)) > 0) {
            lastToken = lastToken.replace('.', '_');
            tokens.set(tokens.size() - 1, lastToken);
        }
        String domain = tokens.remove(0);
        List<String> r = PackageUtils.tokenize(domain, urnScheme ? ".-" : ".");
        Collections.reverse(r);
        if ("www".equalsIgnoreCase(r.get(r.size() - 1))) {
            r.remove(r.size() - 1);
        }
        tokens.addAll(0, r);
        for (int i = 0; i < tokens.size(); ++i) {
            String token = tokens.get(i);
            token = PackageUtils.removeIllegalIdentifierChars(token);
            if (JavaUtils.isJavaKeyword(token = token.toLowerCase())) {
                token = '_' + token;
            }
            tokens.set(i, token);
        }
        return String.join((CharSequence)".", tokens);
    }

    private static List<String> tokenize(String str, String sep) {
        StringTokenizer tokens = new StringTokenizer(str, sep);
        ArrayList<String> r = new ArrayList<String>();
        while (tokens.hasMoreTokens()) {
            r.add(tokens.nextToken());
        }
        return r;
    }

    private static String removeIllegalIdentifierChars(String token) {
        StringBuilder newToken = new StringBuilder();
        for (int i = 0; i < token.length(); ++i) {
            char c = token.charAt(i);
            if (i == 0 && !Character.isJavaIdentifierStart(c)) {
                newToken.append('_').append(c);
                continue;
            }
            if (!Character.isJavaIdentifierPart(c)) {
                newToken.append('_');
                continue;
            }
            newToken.append(c);
        }
        return newToken.toString();
    }

    public static String getNamespace(String packageName) {
        if (packageName == null || packageName.isEmpty()) {
            return null;
        }
        List<String> parts = Arrays.asList(packageName.split("\\."));
        Collections.reverse(parts);
        return "http://" + String.join((CharSequence)".", parts) + '/';
    }
}

