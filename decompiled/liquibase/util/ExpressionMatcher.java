/*
 * Decompiled with CFR 0.152.
 */
package liquibase.util;

import java.util.Collection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import liquibase.exception.UnexpectedLiquibaseException;
import liquibase.util.StringUtil;

public final class ExpressionMatcher {
    private static final Pattern NESTED_EXPRESSION_PATTERN = Pattern.compile("\\([^()]+\\)");

    private ExpressionMatcher() {
    }

    public static boolean matches(String expression, Collection<String> items) {
        String requiredNotRegex = "^@\\s*(!|not )";
        if ((expression = StringUtil.trimToEmpty(expression)).equals(":TRUE")) {
            return true;
        }
        if (expression.equals(":FALSE")) {
            return false;
        }
        while (expression.contains("(")) {
            Matcher matcher = NESTED_EXPRESSION_PATTERN.matcher(expression);
            if (!matcher.find()) {
                throw new UnexpectedLiquibaseException("Cannot parse expression " + expression);
            }
            String[] left = expression.substring(0, matcher.start());
            String[] right = expression.substring(matcher.end());
            String nestedExpression = expression.substring(matcher.start() + 1, matcher.end() - 1);
            expression = (String)left + " :" + String.valueOf(ExpressionMatcher.matches(nestedExpression, items)).toUpperCase() + " " + (String)right;
        }
        String[] orSplit = (expression = expression.replaceAll("\\s*,\\s*", " or ")).split("\\s+or\\s+");
        if (orSplit.length > 1) {
            for (String split : orSplit) {
                if (!ExpressionMatcher.matches(split, items)) continue;
                return true;
            }
            return false;
        }
        String[] andSplit = expression.split("\\s+and\\s+");
        if (andSplit.length > 1) {
            for (String split : andSplit) {
                if (ExpressionMatcher.matches(split, items)) continue;
                return false;
            }
            return true;
        }
        boolean notExpression = false;
        if (expression.startsWith("!")) {
            notExpression = true;
            expression = expression.substring(1).trim();
        } else if (expression.toLowerCase().startsWith("not ")) {
            notExpression = true;
            expression = expression.substring(4).trim();
        } else if (expression.matches("^@\\s*(!|not ).+")) {
            notExpression = true;
            expression = expression.replaceFirst("^@\\s*(!|not )", "").trim();
        }
        boolean requiredExpression = false;
        if (expression.startsWith("@")) {
            requiredExpression = true;
            expression = expression.substring(1).trim();
        }
        if (!requiredExpression && items.isEmpty()) {
            return true;
        }
        if (expression.trim().equals(":TRUE")) {
            return !notExpression;
        }
        if (expression.trim().equals(":FALSE")) {
            return notExpression;
        }
        for (String item : items) {
            if (!item.equalsIgnoreCase(expression)) continue;
            return !notExpression;
        }
        return notExpression;
    }
}

