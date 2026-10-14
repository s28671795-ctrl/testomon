/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.uri;

import java.net.URI;
import java.util.ArrayDeque;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;
import org.glassfish.jersey.internal.guava.Preconditions;
import org.glassfish.jersey.uri.PatternWithGroups;
import org.glassfish.jersey.uri.UriComponent;
import org.glassfish.jersey.uri.internal.UriTemplateParser;

public class UriTemplate {
    private static final String[] EMPTY_VALUES = new String[0];
    public static final Comparator<UriTemplate> COMPARATOR = new Comparator<UriTemplate>(){

        @Override
        public int compare(UriTemplate o1, UriTemplate o2) {
            if (o1 == null && o2 == null) {
                return 0;
            }
            if (o1 == null) {
                return 1;
            }
            if (o2 == null) {
                return -1;
            }
            if (o1 == EMPTY && o2 == EMPTY) {
                return 0;
            }
            if (o1 == EMPTY) {
                return 1;
            }
            if (o2 == EMPTY) {
                return -1;
            }
            int i = o2.getNumberOfExplicitCharacters() - o1.getNumberOfExplicitCharacters();
            if (i != 0) {
                return i;
            }
            i = o2.getNumberOfTemplateVariables() - o1.getNumberOfTemplateVariables();
            if (i != 0) {
                return i;
            }
            i = o2.getNumberOfExplicitRegexes() - o1.getNumberOfExplicitRegexes();
            if (i != 0) {
                return i;
            }
            return 0;
        }
    };
    private static final Pattern TEMPLATE_NAMES_PATTERN = Pattern.compile("\\{([\\w\\?;][-\\w\\.,]*)\\}");
    public static final UriTemplate EMPTY = new UriTemplate();
    private final String template;
    private final String normalizedTemplate;
    private final PatternWithGroups pattern;
    private final boolean endsWithSlash;
    private final List<String> templateVariables;
    private final int numOfExplicitRegexes;
    private final int numOfRegexGroups;
    private final int numOfCharacters;

    private UriTemplate() {
        this.normalizedTemplate = "";
        this.template = "";
        this.pattern = PatternWithGroups.EMPTY;
        this.endsWithSlash = false;
        this.templateVariables = Collections.emptyList();
        this.numOfRegexGroups = 0;
        this.numOfCharacters = 0;
        this.numOfExplicitRegexes = 0;
    }

    public UriTemplate(String template) throws PatternSyntaxException, IllegalArgumentException {
        this(new UriTemplateParser(template));
    }

    protected UriTemplate(UriTemplateParser templateParser) throws PatternSyntaxException, IllegalArgumentException {
        this.template = templateParser.getTemplate();
        this.normalizedTemplate = templateParser.getNormalizedTemplate();
        this.pattern = UriTemplate.initUriPattern(templateParser);
        this.numOfExplicitRegexes = templateParser.getNumberOfExplicitRegexes();
        this.numOfRegexGroups = templateParser.getNumberOfRegexGroups();
        this.numOfCharacters = templateParser.getNumberOfLiteralCharacters();
        this.endsWithSlash = this.template.charAt(this.template.length() - 1) == '/';
        this.templateVariables = Collections.unmodifiableList(templateParser.getNames());
    }

    private static PatternWithGroups initUriPattern(UriTemplateParser templateParser) {
        return new PatternWithGroups(templateParser.getPattern(), templateParser.getGroupIndexes());
    }

    public static URI resolve(URI baseUri, String refUri) {
        return UriTemplate.resolve(baseUri, URI.create(refUri));
    }

    public static URI resolve(URI baseUri, URI refUri) {
        Preconditions.checkNotNull(baseUri, "Input base URI parameter must not be null.");
        Preconditions.checkNotNull(refUri, "Input reference URI parameter must not be null.");
        String refString = refUri.toString();
        if (refString.isEmpty()) {
            refUri = URI.create("#");
        } else if (refString.startsWith("?")) {
            String baseString = baseUri.toString();
            int qIndex = baseString.indexOf(63);
            baseString = qIndex > -1 ? baseString.substring(0, qIndex) : baseString;
            return URI.create(baseString + refString);
        }
        URI result = baseUri.resolve(refUri);
        if (refString.isEmpty()) {
            String resolvedString = result.toString();
            result = URI.create(resolvedString.substring(0, resolvedString.indexOf(35)));
        }
        return UriTemplate.normalize(result);
    }

    public static URI normalize(String uri) {
        return UriTemplate.normalize(URI.create(uri));
    }

    public static URI normalize(URI uri) {
        Preconditions.checkNotNull(uri, "Input reference URI parameter must not be null.");
        String path = uri.getPath();
        if (path == null || path.isEmpty() || !path.contains("/.")) {
            return uri;
        }
        String[] segments = path.split("/");
        ArrayDeque<String> resolvedSegments = new ArrayDeque<String>(segments.length);
        for (String segment : segments) {
            if (segment.isEmpty() || ".".equals(segment)) continue;
            if ("..".equals(segment)) {
                resolvedSegments.pollLast();
                continue;
            }
            resolvedSegments.offer(segment);
        }
        StringBuilder pathBuilder = new StringBuilder();
        for (String segment : resolvedSegments) {
            pathBuilder.append('/').append(segment);
        }
        String resultString = UriTemplate.createURIWithStringValues(uri.getScheme(), uri.getAuthority(), null, null, null, pathBuilder.toString(), uri.getQuery(), uri.getFragment(), EMPTY_VALUES, false, false);
        return URI.create(resultString);
    }

    public static URI relativize(URI baseUri, URI refUri) {
        Preconditions.checkNotNull(baseUri, "Input base URI parameter must not be null.");
        Preconditions.checkNotNull(refUri, "Input reference URI parameter must not be null.");
        return UriTemplate.normalize(baseUri.relativize(refUri));
    }

    public final String getTemplate() {
        return this.template;
    }

    public final PatternWithGroups getPattern() {
        return this.pattern;
    }

    public final boolean endsWithSlash() {
        return this.endsWithSlash;
    }

    public final List<String> getTemplateVariables() {
        return this.templateVariables;
    }

    public final boolean isTemplateVariablePresent(String name) {
        for (String s2 : this.templateVariables) {
            if (!s2.equals(name)) continue;
            return true;
        }
        return false;
    }

    public final int getNumberOfExplicitRegexes() {
        return this.numOfExplicitRegexes;
    }

    public final int getNumberOfRegexGroups() {
        return this.numOfRegexGroups;
    }

    public final int getNumberOfExplicitCharacters() {
        return this.numOfCharacters;
    }

    public final int getNumberOfTemplateVariables() {
        return this.templateVariables.size();
    }

    public final boolean match(CharSequence uri, Map<String, String> templateVariableToValue) throws IllegalArgumentException {
        if (templateVariableToValue == null) {
            throw new IllegalArgumentException();
        }
        return this.pattern.match(uri, this.templateVariables, templateVariableToValue);
    }

    public final boolean match(CharSequence uri, List<String> groupValues2) throws IllegalArgumentException {
        if (groupValues2 == null) {
            throw new IllegalArgumentException();
        }
        return this.pattern.match(uri, groupValues2);
    }

    public final String createURI(final Map<String, String> values2) {
        StringBuilder sb = new StringBuilder();
        UriTemplate.resolveTemplate(this.normalizedTemplate, sb, new TemplateValueStrategy(){

            @Override
            public String valueFor(String templateVariable, String matchedGroup) {
                return (String)values2.get(templateVariable);
            }
        });
        return sb.toString();
    }

    public final String createURI(String ... values2) {
        return this.createURI(values2, 0, values2.length);
    }

    public final String createURI(final String[] values2, final int offset, final int length) {
        TemplateValueStrategy ns = new TemplateValueStrategy(){
            private final int lengthPlusOffset;
            private int v;
            private final Map<String, String> mapValues;
            {
                this.lengthPlusOffset = length + offset;
                this.v = offset;
                this.mapValues = new HashMap<String, String>();
            }

            @Override
            public String valueFor(String templateVariable, String matchedGroup) {
                String tValue = this.mapValues.get(templateVariable);
                if (tValue == null && this.v < this.lengthPlusOffset && (tValue = values2[this.v++]) != null) {
                    this.mapValues.put(templateVariable, tValue);
                }
                return tValue;
            }
        };
        StringBuilder sb = new StringBuilder();
        UriTemplate.resolveTemplate(this.normalizedTemplate, sb, ns);
        return sb.toString();
    }

    private static void resolveTemplate(String normalizedTemplate, StringBuilder builder, TemplateValueStrategy valueStrategy) {
        Matcher m4 = TEMPLATE_NAMES_PATTERN.matcher(normalizedTemplate);
        int i = 0;
        while (m4.find()) {
            builder.append(normalizedTemplate, i, m4.start());
            String variableName = m4.group(1);
            char firstChar = variableName.charAt(0);
            if (firstChar == '?' || firstChar == ';') {
                String[] variables;
                String emptyValueAssignment;
                char separator;
                char prefix;
                if (firstChar == '?') {
                    prefix = '?';
                    separator = '&';
                    emptyValueAssignment = "=";
                } else {
                    prefix = ';';
                    separator = ';';
                    emptyValueAssignment = "";
                }
                int index = builder.length();
                for (String variable : variables = variableName.substring(1).split(", ?")) {
                    try {
                        String value = valueStrategy.valueFor(variable, m4.group());
                        if (value == null) continue;
                        if (index != builder.length()) {
                            builder.append(separator);
                        }
                        builder.append(variable);
                        if (value.isEmpty()) {
                            builder.append(emptyValueAssignment);
                            continue;
                        }
                        builder.append('=');
                        builder.append(value);
                    }
                    catch (IllegalArgumentException illegalArgumentException) {
                        // empty catch block
                    }
                }
                if (index != builder.length() && (index == 0 || builder.charAt(index - 1) != prefix)) {
                    builder.insert(index, prefix);
                }
            } else {
                String value = valueStrategy.valueFor(variableName, m4.group());
                if (value != null) {
                    builder.append(value);
                }
            }
            i = m4.end();
        }
        builder.append(normalizedTemplate, i, normalizedTemplate.length());
    }

    public final String toString() {
        return this.pattern.toString();
    }

    public final int hashCode() {
        return this.pattern.hashCode();
    }

    public final boolean equals(Object o) {
        if (o instanceof UriTemplate) {
            UriTemplate that = (UriTemplate)o;
            return this.pattern.equals(that.pattern);
        }
        return false;
    }

    public static String createURI(String scheme2, String authority, String userInfo, String host2, String port, String path, String query, String fragment, Map<String, ?> values2, boolean encode, boolean encodeSlashInPath) {
        HashMap<String, String> stringValues = new HashMap<String, String>();
        for (Map.Entry<String, ?> e : values2.entrySet()) {
            if (e.getValue() == null) continue;
            stringValues.put(e.getKey(), e.getValue().toString());
        }
        return UriTemplate.createURIWithStringValues(scheme2, authority, userInfo, host2, port, path, query, fragment, stringValues, encode, encodeSlashInPath);
    }

    public static String createURIWithStringValues(String scheme2, String authority, String userInfo, String host2, String port, String path, String query, String fragment, Map<String, ?> values2, boolean encode, boolean encodeSlashInPath) {
        return UriTemplate.createURIWithStringValues(scheme2, authority, userInfo, host2, port, path, query, fragment, EMPTY_VALUES, encode, encodeSlashInPath, values2);
    }

    public static String createURI(String scheme2, String authority, String userInfo, String host2, String port, String path, String query, String fragment, Object[] values2, boolean encode, boolean encodeSlashInPath) {
        String[] stringValues = new String[values2.length];
        for (int i = 0; i < values2.length; ++i) {
            if (values2[i] == null) continue;
            stringValues[i] = values2[i].toString();
        }
        return UriTemplate.createURIWithStringValues(scheme2, authority, userInfo, host2, port, path, query, fragment, stringValues, encode, encodeSlashInPath);
    }

    public static String createURIWithStringValues(String scheme2, String authority, String userInfo, String host2, String port, String path, String query, String fragment, String[] values2, boolean encode, boolean encodeSlashInPath) {
        HashMap mapValues = new HashMap();
        return UriTemplate.createURIWithStringValues(scheme2, authority, userInfo, host2, port, path, query, fragment, values2, encode, encodeSlashInPath, mapValues);
    }

    private static String createURIWithStringValues(String scheme2, String authority, String userInfo, String host2, String port, String path, String query, String fragment, String[] values2, boolean encode, boolean encodeSlashInPath, Map<String, ?> mapValues) {
        StringBuilder sb = new StringBuilder();
        int offset = 0;
        if (scheme2 != null) {
            offset = UriTemplate.createUriComponent(UriComponent.Type.SCHEME, scheme2, values2, offset, false, mapValues, sb);
            sb.append(':');
        }
        boolean hasAuthority = false;
        if (UriTemplate.notEmpty(userInfo) || UriTemplate.notEmpty(host2) || UriTemplate.notEmpty(port)) {
            hasAuthority = true;
            sb.append("//");
            if (UriTemplate.notEmpty(userInfo)) {
                offset = UriTemplate.createUriComponent(UriComponent.Type.USER_INFO, userInfo, values2, offset, encode, mapValues, sb);
                sb.append('@');
            }
            if (UriTemplate.notEmpty(host2)) {
                offset = UriTemplate.createUriComponent(UriComponent.Type.HOST, host2, values2, offset, encode, mapValues, sb);
            }
            if (UriTemplate.notEmpty(port)) {
                sb.append(':');
                offset = UriTemplate.createUriComponent(UriComponent.Type.PORT, port, values2, offset, false, mapValues, sb);
            }
        } else if (UriTemplate.notEmpty(authority)) {
            hasAuthority = true;
            sb.append("//");
            offset = UriTemplate.createUriComponent(UriComponent.Type.AUTHORITY, authority, values2, offset, encode, mapValues, sb);
        }
        if (UriTemplate.notEmpty(path) || UriTemplate.notEmpty(query) || UriTemplate.notEmpty(fragment)) {
            if (hasAuthority && (path == null || path.isEmpty() || path.charAt(0) != '/')) {
                sb.append('/');
            }
            if (UriTemplate.notEmpty(path)) {
                UriComponent.Type t2 = encodeSlashInPath ? UriComponent.Type.PATH_SEGMENT : UriComponent.Type.PATH;
                offset = UriTemplate.createUriComponent(t2, path, values2, offset, encode, mapValues, sb);
            }
            if (UriTemplate.notEmpty(query)) {
                sb.append('?');
                offset = UriTemplate.createUriComponent(UriComponent.Type.QUERY_PARAM, query, values2, offset, encode, mapValues, sb);
            }
            if (UriTemplate.notEmpty(fragment)) {
                sb.append('#');
                UriTemplate.createUriComponent(UriComponent.Type.FRAGMENT, fragment, values2, offset, encode, mapValues, sb);
            }
        }
        return sb.toString();
    }

    private static boolean notEmpty(String string) {
        return string != null && !string.isEmpty();
    }

    private static int createUriComponent(final UriComponent.Type componentType, String template, final String[] values2, final int valueOffset, final boolean encode, Map<String, ?> _mapValues, StringBuilder b) {
        final Map<String, ?> mapValues = _mapValues;
        if (template.indexOf(123) == -1) {
            b.append(template);
            return valueOffset;
        }
        template = new UriTemplateParser(template).getNormalizedTemplate();
        class ValuesFromArrayStrategy
        implements TemplateValueStrategy {
            private int offset;

            ValuesFromArrayStrategy() {
                this.offset = valueOffset;
            }

            @Override
            public String valueFor(String templateVariable, String matchedGroup) {
                Object value = mapValues.get(templateVariable);
                if (value == null && this.offset < values2.length) {
                    value = values2[this.offset++];
                    mapValues.put(templateVariable, value);
                }
                if (value == null) {
                    throw new IllegalArgumentException(String.format("The template variable '%s' has no value", templateVariable));
                }
                if (encode) {
                    return UriComponent.encode(value.toString(), componentType);
                }
                return UriComponent.contextualEncode(value.toString(), componentType);
            }
        }
        ValuesFromArrayStrategy cs = new ValuesFromArrayStrategy();
        UriTemplate.resolveTemplate(template, b, cs);
        return cs.offset;
    }

    public static String resolveTemplateValues(final UriComponent.Type type, String template, final boolean encode, Map<String, ?> _mapValues) {
        if (template == null || template.isEmpty() || template.indexOf(123) == -1) {
            return template;
        }
        final Map<String, ?> mapValues = _mapValues;
        template = new UriTemplateParser(template).getNormalizedTemplate();
        StringBuilder sb = new StringBuilder();
        UriTemplate.resolveTemplate(template, sb, new TemplateValueStrategy(){

            @Override
            public String valueFor(String templateVariable, String matchedGroup) {
                Object value = mapValues.get(templateVariable);
                if (value != null) {
                    value = encode ? UriComponent.encode(value.toString(), type) : UriComponent.contextualEncode(value.toString(), type);
                    return value.toString();
                }
                if (mapValues.containsKey(templateVariable)) {
                    throw new IllegalArgumentException(String.format("The value associated of the template value map for key '%s' is 'null'.", templateVariable));
                }
                return matchedGroup;
            }
        });
        return sb.toString();
    }

    private static interface TemplateValueStrategy {
        public String valueFor(String var1, String var2);
    }
}

