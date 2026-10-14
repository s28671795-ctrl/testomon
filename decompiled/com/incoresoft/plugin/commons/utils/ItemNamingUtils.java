/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.plugin.commons.utils;

import java.util.List;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ItemNamingUtils {
    public static String getCopyName(String originalName, Function<Pattern, List<String>> namesRetriever) {
        String nameWithoutCopyIndex = originalName.replaceAll(" \\(\\d+\\)", "");
        int latestCopyIndex = ItemNamingUtils.latestCopyIndex(nameWithoutCopyIndex, namesRetriever);
        return String.format("%s (%d)", originalName, latestCopyIndex + 1);
    }

    private static int latestCopyIndex(String name, Function<Pattern, List<String>> namesRetriever) {
        String patternString = "^" + name + " \\((\\d+)\\)$";
        Pattern pattern = Pattern.compile(patternString);
        return namesRetriever.apply(pattern).stream().map(pattern::matcher).filter(Matcher::find).map(matcher -> matcher.group(1)).map(Integer::parseInt).mapToInt(Integer::intValue).max().orElse(0);
    }

    public static String getStreamAnalyticsName(String cameraName, String streamSettingsName) {
        return cameraName + " / " + streamSettingsName;
    }
}

