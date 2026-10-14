/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.uri;

import java.util.Comparator;
import org.glassfish.jersey.uri.PathTemplate;
import org.glassfish.jersey.uri.PatternWithGroups;
import org.glassfish.jersey.uri.UriTemplate;

public final class PathPattern
extends PatternWithGroups {
    public static final PathPattern EMPTY_PATTERN = new PathPattern();
    public static final PathPattern END_OF_PATH_PATTERN = new PathPattern("", RightHandPath.capturingZeroSegments);
    public static final PathPattern OPEN_ROOT_PATH_PATTERN = new PathPattern("", RightHandPath.capturingZeroOrMoreSegments);
    public static final Comparator<PathPattern> COMPARATOR = new Comparator<PathPattern>(){

        @Override
        public int compare(PathPattern o1, PathPattern o2) {
            return UriTemplate.COMPARATOR.compare(o1.template, o2.template);
        }
    };
    private final UriTemplate template;

    public static PathPattern asClosed(PathPattern pattern) {
        return new PathPattern(pattern.getTemplate().getTemplate(), RightHandPath.capturingZeroSegments);
    }

    private PathPattern() {
        this.template = UriTemplate.EMPTY;
    }

    public PathPattern(String template) {
        this(new PathTemplate(template));
    }

    public PathPattern(PathTemplate template) {
        super(PathPattern.postfixWithCapturingGroup(template.getPattern().getRegex()), PathPattern.addIndexForRightHandPathCapturingGroup(template.getNumberOfRegexGroups(), template.getPattern().getGroupIndexes()));
        this.template = template;
    }

    public PathPattern(String template, RightHandPath rhpp) {
        this(new PathTemplate(template), rhpp);
    }

    public PathPattern(PathTemplate template, RightHandPath rhpp) {
        super(PathPattern.postfixWithCapturingGroup(template.getPattern().getRegex(), rhpp), PathPattern.addIndexForRightHandPathCapturingGroup(template.getNumberOfRegexGroups(), template.getPattern().getGroupIndexes()));
        this.template = template;
    }

    public UriTemplate getTemplate() {
        return this.template;
    }

    private static String postfixWithCapturingGroup(String regex2) {
        return PathPattern.postfixWithCapturingGroup(regex2, RightHandPath.capturingZeroOrMoreSegments);
    }

    private static String postfixWithCapturingGroup(String regex2, RightHandPath rhpp) {
        if (regex2.endsWith("/")) {
            regex2 = regex2.substring(0, regex2.length() - 1);
        }
        return regex2 + rhpp.getRegex();
    }

    private static int[] addIndexForRightHandPathCapturingGroup(int numberOfGroups, int[] indexes) {
        if (indexes.length == 0) {
            return indexes;
        }
        int[] cgIndexes = new int[indexes.length + 1];
        System.arraycopy(indexes, 0, cgIndexes, 0, indexes.length);
        cgIndexes[indexes.length] = numberOfGroups + 1;
        return cgIndexes;
    }

    public static enum RightHandPath {
        capturingZeroOrMoreSegments("(/.*)?"),
        capturingZeroSegments("(/)?");

        private final String regex;

        private RightHandPath(String regex2) {
            this.regex = regex2;
        }

        private String getRegex() {
            return this.regex;
        }
    }
}

