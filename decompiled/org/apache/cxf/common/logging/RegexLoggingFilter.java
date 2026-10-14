/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.common.logging;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexLoggingFilter {
    public static final String DEFAULT_REPLACEMENT = "*****";
    private String regPattern;
    private int regGroup = 1;
    private String regReplacement = "*****";
    private List<ReplaceRegEx> regexs = new ArrayList<ReplaceRegEx>();

    public CharSequence filter(CharSequence command) {
        if (this.regPattern != null) {
            command = new ReplaceRegEx(this.regPattern, this.regGroup, this.regReplacement).filter(command);
        }
        for (ReplaceRegEx regex2 : this.regexs) {
            command = regex2.filter(command);
        }
        return command;
    }

    public void addRegEx(String pattern) {
        this.addRegEx(pattern, 1);
    }

    public void addRegEx(String pattern, int group) {
        this.addRegEx(pattern, group, DEFAULT_REPLACEMENT);
    }

    public void addRegEx(String pattern, int group, String replacement) {
        this.regexs.add(new ReplaceRegEx(pattern, group, replacement));
    }

    public void addCommandOption(String option, String ... commands) {
        StringBuilder pattern = new StringBuilder("(");
        for (String command : commands) {
            if (pattern.length() > 1) {
                pattern.append('|');
            }
            pattern.append(Pattern.quote(command));
        }
        pattern.append(") +.*?").append(Pattern.quote(option)).append(" +([^ ]+)");
        this.regexs.add(new ReplaceRegEx(pattern.toString(), 2, DEFAULT_REPLACEMENT));
    }

    public String getPattern() {
        return this.regPattern;
    }

    public void setPattern(String pattern) {
        this.regPattern = pattern;
    }

    public String getReplacement() {
        return this.regReplacement;
    }

    public void setReplacement(String replacement) {
        this.regReplacement = replacement;
    }

    public int getGroup() {
        return this.regGroup;
    }

    public void setGroup(int group) {
        this.regGroup = group;
    }

    private static class ReplaceRegEx {
        private Pattern pattern;
        private int group = 1;
        private String replacement;

        ReplaceRegEx(String pattern, int group, String replacement) {
            this.pattern = Pattern.compile(pattern);
            this.group = group;
            this.replacement = replacement;
        }

        public CharSequence filter(CharSequence command) {
            Matcher m4 = this.pattern.matcher(command);
            int offset = 0;
            while (m4.find()) {
                int origLen = command.length();
                command = new StringBuilder(command).replace(m4.start(this.group) + offset, m4.end(this.group) + offset, this.replacement).toString();
                offset += command.length() - origLen;
            }
            return command;
        }
    }
}

