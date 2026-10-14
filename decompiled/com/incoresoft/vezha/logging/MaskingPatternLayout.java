/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.logging;

import ch.qos.logback.classic.PatternLayout;
import ch.qos.logback.classic.spi.ILoggingEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class MaskingPatternLayout
extends PatternLayout {
    private Pattern masksPattern;
    private final List<String> maskPatterns = new ArrayList<String>();

    public void setMaskPattern(String maskPattern) {
        this.maskPatterns.add(maskPattern);
        String multilinePatternString = this.maskPatterns.stream().map(pattern -> String.format("(?:%s)", pattern)).collect(Collectors.joining("|"));
        this.masksPattern = Pattern.compile(multilinePatternString);
    }

    @Override
    public String doLayout(ILoggingEvent event) {
        return this.maskMessage(super.doLayout(event));
    }

    String maskMessage(String message) {
        if (this.masksPattern == null) {
            return message;
        }
        StringBuilder messageBuilder = new StringBuilder(message);
        Matcher matcher = this.masksPattern.matcher(messageBuilder);
        while (matcher.find()) {
            for (int group = 1; group <= matcher.groupCount(); ++group) {
                if (matcher.group(group) == null) continue;
                for (int i = matcher.start(group); i < matcher.end(group); ++i) {
                    messageBuilder.setCharAt(i, '*');
                }
            }
        }
        return messageBuilder.toString();
    }
}

