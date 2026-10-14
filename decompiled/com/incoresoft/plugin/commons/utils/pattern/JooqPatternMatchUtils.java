/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.plugin.commons.utils.pattern;

import com.incoresoft.plugin.commons.utils.pattern.PatternMatchUtils;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.jooq.Condition;
import org.jooq.DSLContext;
import org.jooq.Field;
import org.jooq.SQLDialect;
import org.jooq.TableField;

public class JooqPatternMatchUtils {
    public static Condition optimizedWildcardsPatternMatch(TableField<?, String> field2, String pattern, DSLContext context) {
        int wildcardsCount = (int)pattern.chars().mapToObj(c -> Character.valueOf((char)c)).filter(JooqPatternMatchUtils::isWildcard).count();
        if (wildcardsCount == 0) {
            return field2.eq(pattern);
        }
        if (wildcardsCount > 2 || pattern.contains(String.valueOf('%'))) {
            return JooqPatternMatchUtils.wildcardsPatternMatch(field2, pattern, context);
        }
        List<String> possibleValues = new ArrayList<String>(List.of(pattern));
        for (int position = 0; position < pattern.length(); ++position) {
            char character = pattern.charAt(position);
            if (character == '$') {
                possibleValues = JooqPatternMatchUtils.replaceCharAtPositionWithDigitsForAllInList(possibleValues, position);
                continue;
            }
            if (character == '@') {
                possibleValues = JooqPatternMatchUtils.replaceCharAtPositionWithLettersForAllInList(possibleValues, position);
                continue;
            }
            if (character != '_') continue;
            possibleValues = JooqPatternMatchUtils.replaceCharAtPositionWithSymbolForAllInlist(possibleValues, position);
        }
        return field2.in((Collection<?>)possibleValues);
    }

    private static List<String> replaceCharAtPositionWithSymbolForAllInlist(List<String> values2, int position) {
        ArrayList<String> result = new ArrayList<String>();
        result.addAll(JooqPatternMatchUtils.replaceCharAtPositionWithDigitsForAllInList(values2, position));
        result.addAll(JooqPatternMatchUtils.replaceCharAtPositionWithLettersForAllInList(values2, position));
        return result;
    }

    private static List<String> replaceCharAtPositionWithLettersForAllInList(List<String> values2, int position) {
        ArrayList<String> result = new ArrayList<String>();
        for (char letter = 'A'; letter <= 'Z'; letter = (char)((char)(letter + 1))) {
            for (String value : values2) {
                String newValue = new StringBuilder(value).replace(position, position + 1, String.valueOf(letter)).toString();
                result.add(newValue);
            }
        }
        return result;
    }

    private static List<String> replaceCharAtPositionWithDigitsForAllInList(List<String> values2, int position) {
        ArrayList<String> result = new ArrayList<String>();
        for (int digit = 0; digit < 10; ++digit) {
            for (String value : values2) {
                String newValue = new StringBuilder(value).replace(position, position + 1, String.valueOf(digit)).toString();
                result.add(newValue);
            }
        }
        return result;
    }

    public static Condition wildcardsPatternMatch(Field<String> field2, String pattern, DSLContext context) {
        if (JooqPatternMatchUtils.notContainWildcards(pattern)) {
            return field2.eq(pattern);
        }
        if (JooqPatternMatchUtils.isSQLServer(context)) {
            return field2.like(PatternMatchUtils.getWildcardsPatternString(pattern));
        }
        return field2.likeRegex(PatternMatchUtils.getWildcardsPatternString(pattern));
    }

    private static boolean isSQLServer(DSLContext context) {
        SQLDialect dialect = context.configuration().dialect();
        return dialect == SQLDialect.SQLSERVER || dialect.family() == SQLDialect.SQLSERVER;
    }

    private static boolean notContainWildcards(String pattern) {
        return pattern.chars().mapToObj(c -> Character.valueOf((char)c)).noneMatch(JooqPatternMatchUtils::isWildcard);
    }

    private static boolean isWildcard(Character c) {
        return c.charValue() == '$' || c.charValue() == '@' || c.charValue() == '_' || c.charValue() == '%';
    }
}

