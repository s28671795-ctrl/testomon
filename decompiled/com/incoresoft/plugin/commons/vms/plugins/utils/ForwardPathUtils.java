/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.plugin.commons.vms.plugins.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ForwardPathUtils {
    public static String buildForwardPath(String requestPath, String matchedPath, String targetPath) {
        boolean sourceHasWildcard = ForwardPathUtils.isSourceHasWildcard(matchedPath, targetPath);
        if (sourceHasWildcard) {
            int sourceWildcardSize = matchedPath.endsWith("/*") ? 2 : 1;
            int targetWildcardSize = matchedPath.endsWith("/*") ? 2 : 1;
            String basePath = matchedPath.substring(0, matchedPath.length() - sourceWildcardSize);
            String trimmedTargetPath = targetPath.substring(0, targetPath.length() - targetWildcardSize);
            if (requestPath.startsWith(basePath)) {
                String remainingPath = requestPath.substring(basePath.length());
                return trimmedTargetPath + remainingPath;
            }
        } else {
            if (matchedPath.contains("{")) {
                return ForwardPathUtils.validateAndReplacePathVariables(requestPath, matchedPath, targetPath);
            }
            if (matchedPath.equals(requestPath)) {
                return targetPath;
            }
        }
        throw new IllegalArgumentException("No matching path forward found for: " + requestPath);
    }

    private static boolean isSourceHasWildcard(String sourcePath, String targetPath) {
        boolean targetHasWildcard;
        boolean sourceHasWildcard = sourcePath.endsWith("/*") || sourcePath.endsWith("*");
        boolean bl = targetHasWildcard = targetPath.endsWith("/*") || targetPath.endsWith("*");
        if (sourceHasWildcard != targetHasWildcard) {
            throw new IllegalArgumentException("Source and target paths must both be wildcard or exact paths.");
        }
        return sourceHasWildcard;
    }

    private static String validateAndReplacePathVariables(String requestPath, String matchedPath, String targetPath) {
        String regex2 = matchedPath.replaceAll("\\{[^/]+}", "([^/]+)");
        Pattern pattern = Pattern.compile(regex2);
        Matcher matcher = pattern.matcher(requestPath);
        if (matcher.matches()) {
            String[] targetParts = targetPath.split("/");
            StringBuilder resolvedTargetPath = new StringBuilder();
            int matchIndex = 1;
            for (String targetPart : targetParts) {
                if (targetPart.startsWith("{") && targetPart.endsWith("}")) {
                    resolvedTargetPath.append(matcher.group(matchIndex++));
                } else {
                    resolvedTargetPath.append(targetPart);
                }
                resolvedTargetPath.append("/");
            }
            return resolvedTargetPath.substring(0, resolvedTargetPath.length() - 1);
        }
        throw new IllegalArgumentException("Request path does not match the source path pattern: " + matchedPath);
    }
}

