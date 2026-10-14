/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.uri.internal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;
import org.glassfish.jersey.internal.LocalizationMessages;
import org.glassfish.jersey.uri.UriComponent;
import org.glassfish.jersey.uri.internal.CharacterIterator;

public class UriTemplateParser {
    static final int[] EMPTY_INT_ARRAY = new int[0];
    private static final Set<Character> RESERVED_REGEX_CHARACTERS = UriTemplateParser.initReserved();
    private static final String[] HEX_TO_UPPERCASE_REGEX = UriTemplateParser.initHexToUpperCaseRegex();
    public static final Pattern TEMPLATE_VALUE_PATTERN = Pattern.compile("[^/]+");
    private final String template;
    private final StringBuffer regex = new StringBuffer();
    private final StringBuffer normalizedTemplate = new StringBuffer();
    private final StringBuffer literalCharactersBuffer = new StringBuffer();
    private final Pattern pattern;
    private final List<String> names = new ArrayList<String>();
    private final List<Integer> groupCounts = new ArrayList<Integer>();
    private final Map<String, Pattern> nameToPattern = new HashMap<String, Pattern>();
    private int numOfExplicitRegexes;
    private int skipGroup;
    private int literalCharacters;

    private static Set<Character> initReserved() {
        char[] reserved = new char[]{'.', '^', '&', '!', '?', '-', ':', '<', '(', '[', '$', '=', ')', ']', ',', '>', '*', '+', '|'};
        HashSet<Character> s2 = new HashSet<Character>(reserved.length);
        for (char c : reserved) {
            s2.add(Character.valueOf(c));
        }
        return s2;
    }

    public UriTemplateParser(String template) throws IllegalArgumentException {
        if (template == null || template.isEmpty()) {
            throw new IllegalArgumentException("Template is null or has zero length");
        }
        this.template = template;
        this.parse(new CharacterIterator(template));
        try {
            this.pattern = Pattern.compile(this.regex.toString());
        }
        catch (PatternSyntaxException ex) {
            throw new IllegalArgumentException("Invalid syntax for the template expression '" + this.regex + "'", ex);
        }
    }

    public final String getTemplate() {
        return this.template;
    }

    public final Pattern getPattern() {
        return this.pattern;
    }

    public final String getNormalizedTemplate() {
        return this.normalizedTemplate.toString();
    }

    public final Map<String, Pattern> getNameToPattern() {
        return this.nameToPattern;
    }

    public final List<String> getNames() {
        return this.names;
    }

    public final List<Integer> getGroupCounts() {
        return this.groupCounts;
    }

    public final int[] getGroupIndexes() {
        if (this.names.isEmpty()) {
            return EMPTY_INT_ARRAY;
        }
        int[] indexes = new int[this.names.size()];
        indexes[0] = 0 + this.groupCounts.get(0);
        for (int i = 1; i < indexes.length; ++i) {
            indexes[i] = indexes[i - 1] + this.groupCounts.get(i);
        }
        return indexes;
    }

    public final int getNumberOfExplicitRegexes() {
        return this.numOfExplicitRegexes;
    }

    public final int getNumberOfRegexGroups() {
        if (this.groupCounts.isEmpty()) {
            return 0;
        }
        int[] groupIndex = this.getGroupIndexes();
        return groupIndex[groupIndex.length - 1] + this.skipGroup;
    }

    public final int getNumberOfLiteralCharacters() {
        return this.literalCharacters;
    }

    protected String encodeLiteralCharacters(String characters) {
        return characters;
    }

    private void parse(CharacterIterator ci) {
        try {
            while (ci.hasNext()) {
                char c = ci.next();
                if (c == '{') {
                    this.processLiteralCharacters();
                    this.skipGroup = this.parseName(ci, this.skipGroup);
                    continue;
                }
                this.literalCharactersBuffer.append(c);
            }
            this.processLiteralCharacters();
        }
        catch (NoSuchElementException ex) {
            throw new IllegalArgumentException(LocalizationMessages.ERROR_TEMPLATE_PARSER_INVALID_SYNTAX_TERMINATED(this.template), ex);
        }
    }

    private void processLiteralCharacters() {
        if (this.literalCharactersBuffer.length() > 0) {
            this.literalCharacters += this.literalCharactersBuffer.length();
            String s2 = this.encodeLiteralCharacters(this.literalCharactersBuffer.toString());
            this.normalizedTemplate.append(s2);
            for (int i = 0; i < s2.length(); ++i) {
                char c = s2.charAt(i);
                if (RESERVED_REGEX_CHARACTERS.contains(Character.valueOf(c))) {
                    this.regex.append("\\");
                    this.regex.append(c);
                    continue;
                }
                if (c == '%') {
                    char c1 = s2.charAt(i + 1);
                    char c2 = s2.charAt(i + 2);
                    if (!UriComponent.isHexCharacter(c1) || !UriComponent.isHexCharacter(c2)) continue;
                    this.regex.append("%").append(HEX_TO_UPPERCASE_REGEX[c1]).append(HEX_TO_UPPERCASE_REGEX[c2]);
                    i += 2;
                    continue;
                }
                this.regex.append(c);
            }
            this.literalCharactersBuffer.setLength(0);
        }
    }

    private static String[] initHexToUpperCaseRegex() {
        char c;
        String[] table = new String[128];
        for (int i = 0; i < table.length; ++i) {
            table[i] = String.valueOf((char)i);
        }
        for (c = 'a'; c <= 'f'; c = (char)(c + '\u0001')) {
            table[c] = "[" + c + (char)(c - 97 + 65) + "]";
        }
        for (c = 'A'; c <= 'F'; c = (char)(c + '\u0001')) {
            table[c] = "[" + (char)(c - 65 + 97) + c + "]";
        }
        return table;
    }

    private int parseName(CharacterIterator ci, int skipGroup) {
        char c = this.consumeWhiteSpace(ci);
        char paramType = 'p';
        StringBuilder nameBuffer = new StringBuilder();
        if (c == '?' || c == ';') {
            paramType = c;
            c = ci.next();
        }
        if (!Character.isLetterOrDigit(c) && c != '_') {
            throw new IllegalArgumentException(LocalizationMessages.ERROR_TEMPLATE_PARSER_ILLEGAL_CHAR_START_NAME(Character.valueOf(c), ci.pos(), this.template));
        }
        nameBuffer.append(c);
        String nameRegexString = "";
        while (true) {
            if (Character.isLetterOrDigit(c = ci.next()) || c == '_' || c == '-' || c == '.') {
                nameBuffer.append(c);
                continue;
            }
            if (c != ',' || paramType == 112) break;
            nameBuffer.append(c);
        }
        if (c == ':' && paramType == 'p') {
            nameRegexString = this.parseRegex(ci);
        } else if (c != '}') {
            if (c == ' ') {
                c = this.consumeWhiteSpace(ci);
                if (c == ':') {
                    nameRegexString = this.parseRegex(ci);
                } else if (c != '}') {
                    throw new IllegalArgumentException(LocalizationMessages.ERROR_TEMPLATE_PARSER_ILLEGAL_CHAR_AFTER_NAME(Character.valueOf(c), ci.pos(), this.template));
                }
            } else {
                throw new IllegalArgumentException(LocalizationMessages.ERROR_TEMPLATE_PARSER_ILLEGAL_CHAR_PART_OF_NAME(Character.valueOf(c), ci.pos(), this.template));
            }
        }
        String name = nameBuffer.toString();
        try {
            Pattern namePattern;
            if (paramType == '?' || paramType == ';') {
                String[] subNames = name.split(",\\s?");
                StringBuilder regexBuilder = new StringBuilder(paramType == '?' ? "\\?" : ";");
                String separator = paramType == '?' ? "\\&" : ";/\\?";
                boolean first = true;
                regexBuilder.append("(");
                for (String subName : subNames) {
                    regexBuilder.append("(&?");
                    regexBuilder.append(subName);
                    regexBuilder.append("(=([^");
                    regexBuilder.append(separator);
                    regexBuilder.append("]*))?");
                    regexBuilder.append(")");
                    if (!first) {
                        regexBuilder.append("|");
                    }
                    this.names.add(subName);
                    this.groupCounts.add(first ? 5 : 3);
                    first = false;
                }
                skipGroup = 1;
                regexBuilder.append(")*");
                namePattern = Pattern.compile(regexBuilder.toString());
                name = paramType + name;
            } else {
                this.names.add(name);
                if (!nameRegexString.isEmpty()) {
                    ++this.numOfExplicitRegexes;
                }
                Pattern pattern = namePattern = nameRegexString.isEmpty() ? TEMPLATE_VALUE_PATTERN : Pattern.compile(nameRegexString);
                if (this.nameToPattern.containsKey(name)) {
                    if (!this.nameToPattern.get(name).equals(namePattern)) {
                        throw new IllegalArgumentException(LocalizationMessages.ERROR_TEMPLATE_PARSER_NAME_MORE_THAN_ONCE(name, this.template));
                    }
                } else {
                    this.nameToPattern.put(name, namePattern);
                }
                Matcher m4 = namePattern.matcher("");
                int g2 = m4.groupCount();
                this.groupCounts.add(1 + skipGroup);
                skipGroup = g2;
            }
            this.regex.append('(').append(namePattern).append(')');
            this.normalizedTemplate.append('{').append(name).append('}');
        }
        catch (PatternSyntaxException ex) {
            throw new IllegalArgumentException(LocalizationMessages.ERROR_TEMPLATE_PARSER_INVALID_SYNTAX(nameRegexString, name, this.template), ex);
        }
        return skipGroup;
    }

    private String parseRegex(CharacterIterator ci) {
        StringBuilder regexBuffer = new StringBuilder();
        int braceCount = 1;
        while (true) {
            char c;
            if ((c = ci.next()) == '{') {
                ++braceCount;
            } else if (c == '}' && --braceCount == 0) break;
            regexBuffer.append(c);
        }
        return regexBuffer.toString().trim();
    }

    private char consumeWhiteSpace(CharacterIterator ci) {
        char c;
        while (Character.isWhitespace(c = ci.next())) {
        }
        return c;
    }
}

