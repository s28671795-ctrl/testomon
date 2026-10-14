/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.uri;

import java.util.List;
import java.util.Map;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

public class PatternWithGroups {
    private static final int[] EMPTY_INT_ARRAY = new int[0];
    public static final PatternWithGroups EMPTY = new PatternWithGroups();
    private final String regex;
    private final Pattern regexPattern;
    private final int[] groupIndexes;
    private static final EmptyStringMatchResult EMPTY_STRING_MATCH_RESULT = new EmptyStringMatchResult();

    protected PatternWithGroups() {
        this.regex = "";
        this.regexPattern = null;
        this.groupIndexes = EMPTY_INT_ARRAY;
    }

    public PatternWithGroups(String regex2) throws PatternSyntaxException {
        this(regex2, EMPTY_INT_ARRAY);
    }

    public PatternWithGroups(String regex2, int[] groupIndexes) throws PatternSyntaxException {
        this(PatternWithGroups.compile(regex2), groupIndexes);
    }

    private static Pattern compile(String regex2) throws PatternSyntaxException {
        return regex2 == null || regex2.isEmpty() ? null : Pattern.compile(regex2);
    }

    public PatternWithGroups(Pattern regexPattern) throws IllegalArgumentException {
        this(regexPattern, EMPTY_INT_ARRAY);
    }

    public PatternWithGroups(Pattern regexPattern, int[] groupIndexes) throws IllegalArgumentException {
        if (regexPattern == null) {
            throw new IllegalArgumentException();
        }
        this.regex = regexPattern.toString();
        this.regexPattern = regexPattern;
        this.groupIndexes = (int[])groupIndexes.clone();
    }

    public final String getRegex() {
        return this.regex;
    }

    public final int[] getGroupIndexes() {
        return (int[])this.groupIndexes.clone();
    }

    public final MatchResult match(CharSequence cs) {
        if (cs == null) {
            return this.regexPattern == null ? EMPTY_STRING_MATCH_RESULT : null;
        }
        if (this.regexPattern == null) {
            return null;
        }
        Matcher m4 = this.regexPattern.matcher(cs);
        if (!m4.matches()) {
            return null;
        }
        if (cs.length() == 0) {
            return EMPTY_STRING_MATCH_RESULT;
        }
        return this.groupIndexes.length > 0 ? new GroupIndexMatchResult(m4) : m4;
    }

    public final boolean match(CharSequence cs, List<String> groupValues2) throws IllegalArgumentException {
        if (groupValues2 == null) {
            throw new IllegalArgumentException();
        }
        if (cs == null || cs.length() == 0) {
            return this.regexPattern == null;
        }
        if (this.regexPattern == null) {
            return false;
        }
        Matcher m4 = this.regexPattern.matcher(cs);
        if (!m4.matches()) {
            return false;
        }
        groupValues2.clear();
        if (this.groupIndexes.length > 0) {
            for (int i = 0; i < this.groupIndexes.length; ++i) {
                groupValues2.add(m4.group(this.groupIndexes[i]));
            }
        } else {
            for (int i = 1; i <= m4.groupCount(); ++i) {
                groupValues2.add(m4.group(i));
            }
        }
        return true;
    }

    public final boolean match(CharSequence cs, List<String> groupNames, Map<String, String> groupValues2) throws IllegalArgumentException {
        if (groupValues2 == null) {
            throw new IllegalArgumentException();
        }
        if (cs == null || cs.length() == 0) {
            return this.regexPattern == null;
        }
        if (this.regexPattern == null) {
            return false;
        }
        Matcher m4 = this.regexPattern.matcher(cs);
        if (!m4.matches()) {
            return false;
        }
        groupValues2.clear();
        for (int i = 0; i < groupNames.size(); ++i) {
            String name = groupNames.get(i);
            String currentValue = m4.group(this.groupIndexes.length > 0 ? this.groupIndexes[i] : i + 1);
            String previousValue = groupValues2.get(name);
            if (previousValue != null && !previousValue.equals(currentValue)) {
                return false;
            }
            groupValues2.put(name, currentValue);
        }
        return true;
    }

    public final int hashCode() {
        return this.regex.hashCode();
    }

    public final boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (this.getClass() != obj.getClass()) {
            return false;
        }
        PatternWithGroups that = (PatternWithGroups)obj;
        return this.regex == that.regex || this.regex != null && this.regex.equals(that.regex);
    }

    public final String toString() {
        return this.regex;
    }

    private final class GroupIndexMatchResult
    implements MatchResult {
        private final MatchResult result;

        GroupIndexMatchResult(MatchResult r) {
            this.result = r;
        }

        @Override
        public int start() {
            return this.result.start();
        }

        @Override
        public int start(int group) {
            if (group > this.groupCount()) {
                throw new IndexOutOfBoundsException();
            }
            return group > 0 ? this.result.start(PatternWithGroups.this.groupIndexes[group - 1]) : this.result.start();
        }

        @Override
        public int end() {
            return this.result.end();
        }

        @Override
        public int end(int group) {
            if (group > this.groupCount()) {
                throw new IndexOutOfBoundsException();
            }
            return group > 0 ? this.result.end(PatternWithGroups.this.groupIndexes[group - 1]) : this.result.end();
        }

        @Override
        public String group() {
            return this.result.group();
        }

        @Override
        public String group(int group) {
            if (group > this.groupCount()) {
                throw new IndexOutOfBoundsException();
            }
            return group > 0 ? this.result.group(PatternWithGroups.this.groupIndexes[group - 1]) : this.result.group();
        }

        @Override
        public int groupCount() {
            return PatternWithGroups.this.groupIndexes.length;
        }
    }

    private static final class EmptyStringMatchResult
    implements MatchResult {
        private EmptyStringMatchResult() {
        }

        @Override
        public int start() {
            return 0;
        }

        @Override
        public int start(int group) {
            if (group != 0) {
                throw new IndexOutOfBoundsException();
            }
            return this.start();
        }

        @Override
        public int end() {
            return 0;
        }

        @Override
        public int end(int group) {
            if (group != 0) {
                throw new IndexOutOfBoundsException();
            }
            return this.end();
        }

        @Override
        public String group() {
            return "";
        }

        @Override
        public String group(int group) {
            if (group != 0) {
                throw new IndexOutOfBoundsException();
            }
            return this.group();
        }

        @Override
        public int groupCount() {
            return 0;
        }
    }
}

