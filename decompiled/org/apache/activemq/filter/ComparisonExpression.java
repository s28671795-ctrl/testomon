/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.filter;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;
import javax.jms.JMSException;
import org.apache.activemq.filter.BinaryExpression;
import org.apache.activemq.filter.BooleanExpression;
import org.apache.activemq.filter.ConstantExpression;
import org.apache.activemq.filter.Expression;
import org.apache.activemq.filter.LogicExpression;
import org.apache.activemq.filter.MessageEvaluationContext;
import org.apache.activemq.filter.PropertyExpression;
import org.apache.activemq.filter.UnaryExpression;

public abstract class ComparisonExpression
extends BinaryExpression
implements BooleanExpression {
    public static final ThreadLocal<Boolean> CONVERT_STRING_EXPRESSIONS = new ThreadLocal();
    boolean convertStringExpressions = CONVERT_STRING_EXPRESSIONS.get() != null;
    private static final Set<Character> REGEXP_CONTROL_CHARS = new HashSet<Character>();

    public ComparisonExpression(Expression left, Expression right) {
        super(left, right);
    }

    public static BooleanExpression createBetween(Expression value, Expression left, Expression right) {
        return LogicExpression.createAND(ComparisonExpression.createGreaterThanEqual(value, left), ComparisonExpression.createLessThanEqual(value, right));
    }

    public static BooleanExpression createNotBetween(Expression value, Expression left, Expression right) {
        return LogicExpression.createOR(ComparisonExpression.createLessThan(value, left), ComparisonExpression.createGreaterThan(value, right));
    }

    public static BooleanExpression createLike(Expression left, String right, String escape) {
        if (escape != null && escape.length() != 1) {
            throw new RuntimeException("The ESCAPE string litteral is invalid.  It can only be one character.  Litteral used: " + escape);
        }
        int c = -1;
        if (escape != null) {
            c = 0xFFFF & escape.charAt(0);
        }
        return new LikeExpression(left, right, c);
    }

    public static BooleanExpression createNotLike(Expression left, String right, String escape) {
        return UnaryExpression.createNOT(ComparisonExpression.createLike(left, right, escape));
    }

    public static BooleanExpression createInFilter(Expression left, List elements) {
        if (!(left instanceof PropertyExpression)) {
            throw new RuntimeException("Expected a property for In expression, got: " + left);
        }
        return UnaryExpression.createInExpression((PropertyExpression)left, elements, false);
    }

    public static BooleanExpression createNotInFilter(Expression left, List elements) {
        if (!(left instanceof PropertyExpression)) {
            throw new RuntimeException("Expected a property for In expression, got: " + left);
        }
        return UnaryExpression.createInExpression((PropertyExpression)left, elements, true);
    }

    public static BooleanExpression createIsNull(Expression left) {
        return ComparisonExpression.doCreateEqual(left, ConstantExpression.NULL);
    }

    public static BooleanExpression createIsNotNull(Expression left) {
        return UnaryExpression.createNOT(ComparisonExpression.doCreateEqual(left, ConstantExpression.NULL));
    }

    public static BooleanExpression createNotEqual(Expression left, Expression right) {
        return UnaryExpression.createNOT(ComparisonExpression.createEqual(left, right));
    }

    public static BooleanExpression createEqual(Expression left, Expression right) {
        ComparisonExpression.checkEqualOperand(left);
        ComparisonExpression.checkEqualOperand(right);
        ComparisonExpression.checkEqualOperandCompatability(left, right);
        return ComparisonExpression.doCreateEqual(left, right);
    }

    private static BooleanExpression doCreateEqual(Expression left, Expression right) {
        return new ComparisonExpression(left, right){

            @Override
            public Object evaluate(MessageEvaluationContext message) throws JMSException {
                Object rv;
                Object lv = this.left.evaluate(message);
                if (lv == null ^ (rv = this.right.evaluate(message)) == null) {
                    if (lv == null) {
                        return null;
                    }
                    return Boolean.FALSE;
                }
                if (lv == rv || lv.equals(rv)) {
                    return Boolean.TRUE;
                }
                if (lv instanceof Comparable && rv instanceof Comparable) {
                    return this.compare((Comparable)lv, (Comparable)rv);
                }
                return Boolean.FALSE;
            }

            @Override
            protected boolean asBoolean(int answer) {
                return answer == 0;
            }

            @Override
            public String getExpressionSymbol() {
                return "=";
            }
        };
    }

    public static BooleanExpression createGreaterThan(Expression left, Expression right) {
        ComparisonExpression.checkLessThanOperand(left);
        ComparisonExpression.checkLessThanOperand(right);
        return new ComparisonExpression(left, right){

            @Override
            protected boolean asBoolean(int answer) {
                return answer > 0;
            }

            @Override
            public String getExpressionSymbol() {
                return ">";
            }
        };
    }

    public static BooleanExpression createGreaterThanEqual(Expression left, Expression right) {
        ComparisonExpression.checkLessThanOperand(left);
        ComparisonExpression.checkLessThanOperand(right);
        return new ComparisonExpression(left, right){

            @Override
            protected boolean asBoolean(int answer) {
                return answer >= 0;
            }

            @Override
            public String getExpressionSymbol() {
                return ">=";
            }
        };
    }

    public static BooleanExpression createLessThan(Expression left, Expression right) {
        ComparisonExpression.checkLessThanOperand(left);
        ComparisonExpression.checkLessThanOperand(right);
        return new ComparisonExpression(left, right){

            @Override
            protected boolean asBoolean(int answer) {
                return answer < 0;
            }

            @Override
            public String getExpressionSymbol() {
                return "<";
            }
        };
    }

    public static BooleanExpression createLessThanEqual(Expression left, Expression right) {
        ComparisonExpression.checkLessThanOperand(left);
        ComparisonExpression.checkLessThanOperand(right);
        return new ComparisonExpression(left, right){

            @Override
            protected boolean asBoolean(int answer) {
                return answer <= 0;
            }

            @Override
            public String getExpressionSymbol() {
                return "<=";
            }
        };
    }

    public static void checkLessThanOperand(Expression expr) {
        if (expr instanceof ConstantExpression) {
            Object value = ((ConstantExpression)expr).getValue();
            if (value instanceof Number) {
                return;
            }
            throw new RuntimeException("Value '" + expr + "' cannot be compared.");
        }
        if (expr instanceof BooleanExpression) {
            throw new RuntimeException("Value '" + expr + "' cannot be compared.");
        }
    }

    public static void checkEqualOperand(Expression expr) {
        Object value;
        if (expr instanceof ConstantExpression && (value = ((ConstantExpression)expr).getValue()) == null) {
            throw new RuntimeException("'" + expr + "' cannot be compared.");
        }
    }

    private static void checkEqualOperandCompatability(Expression left, Expression right) {
        if (left instanceof ConstantExpression && right instanceof ConstantExpression && left instanceof BooleanExpression && !(right instanceof BooleanExpression)) {
            throw new RuntimeException("'" + left + "' cannot be compared with '" + right + "'");
        }
    }

    @Override
    public Object evaluate(MessageEvaluationContext message) throws JMSException {
        Comparable lv = (Comparable)this.left.evaluate(message);
        if (lv == null) {
            return null;
        }
        Comparable rv = (Comparable)this.right.evaluate(message);
        if (rv == null) {
            return null;
        }
        return this.compare(lv, rv);
    }

    protected Boolean compare(Comparable lv, Comparable rv) {
        block46: {
            Class<?> rc;
            Class<?> lc = lv.getClass();
            if (lc != (rc = rv.getClass())) {
                try {
                    if (lc == Boolean.class) {
                        if (this.convertStringExpressions && rc == String.class) {
                            lv = Boolean.valueOf((String)((Object)lv));
                            break block46;
                        }
                        return Boolean.FALSE;
                    }
                    if (lc == Byte.class) {
                        if (rc == Short.class) {
                            lv = Short.valueOf(((Number)((Object)lv)).shortValue());
                            break block46;
                        }
                        if (rc == Integer.class) {
                            lv = Integer.valueOf(((Number)((Object)lv)).intValue());
                            break block46;
                        }
                        if (rc == Long.class) {
                            lv = Long.valueOf(((Number)((Object)lv)).longValue());
                            break block46;
                        }
                        if (rc == Float.class) {
                            lv = new Float(((Number)((Object)lv)).floatValue());
                            break block46;
                        }
                        if (rc == Double.class) {
                            lv = new Double(((Number)((Object)lv)).doubleValue());
                            break block46;
                        }
                        if (this.convertStringExpressions && rc == String.class) {
                            rv = Byte.valueOf((String)((Object)rv));
                            break block46;
                        }
                        return Boolean.FALSE;
                    }
                    if (lc == Short.class) {
                        if (rc == Integer.class) {
                            lv = Integer.valueOf(((Number)((Object)lv)).intValue());
                            break block46;
                        }
                        if (rc == Long.class) {
                            lv = Long.valueOf(((Number)((Object)lv)).longValue());
                            break block46;
                        }
                        if (rc == Float.class) {
                            lv = new Float(((Number)((Object)lv)).floatValue());
                            break block46;
                        }
                        if (rc == Double.class) {
                            lv = new Double(((Number)((Object)lv)).doubleValue());
                            break block46;
                        }
                        if (this.convertStringExpressions && rc == String.class) {
                            rv = Short.valueOf((String)((Object)rv));
                            break block46;
                        }
                        return Boolean.FALSE;
                    }
                    if (lc == Integer.class) {
                        if (rc == Long.class) {
                            lv = Long.valueOf(((Number)((Object)lv)).longValue());
                            break block46;
                        }
                        if (rc == Float.class) {
                            lv = new Float(((Number)((Object)lv)).floatValue());
                            break block46;
                        }
                        if (rc == Double.class) {
                            lv = new Double(((Number)((Object)lv)).doubleValue());
                            break block46;
                        }
                        if (this.convertStringExpressions && rc == String.class) {
                            rv = Integer.valueOf((String)((Object)rv));
                            break block46;
                        }
                        return Boolean.FALSE;
                    }
                    if (lc == Long.class) {
                        if (rc == Integer.class) {
                            rv = Long.valueOf(((Number)((Object)rv)).longValue());
                            break block46;
                        }
                        if (rc == Float.class) {
                            lv = new Float(((Number)((Object)lv)).floatValue());
                            break block46;
                        }
                        if (rc == Double.class) {
                            lv = new Double(((Number)((Object)lv)).doubleValue());
                            break block46;
                        }
                        if (this.convertStringExpressions && rc == String.class) {
                            rv = Long.valueOf((String)((Object)rv));
                            break block46;
                        }
                        return Boolean.FALSE;
                    }
                    if (lc == Float.class) {
                        if (rc == Integer.class) {
                            rv = new Float(((Number)((Object)rv)).floatValue());
                            break block46;
                        }
                        if (rc == Long.class) {
                            rv = new Float(((Number)((Object)rv)).floatValue());
                            break block46;
                        }
                        if (rc == Double.class) {
                            lv = new Double(((Number)((Object)lv)).doubleValue());
                            break block46;
                        }
                        if (this.convertStringExpressions && rc == String.class) {
                            rv = Float.valueOf((String)((Object)rv));
                            break block46;
                        }
                        return Boolean.FALSE;
                    }
                    if (lc == Double.class) {
                        if (rc == Integer.class) {
                            rv = new Double(((Number)((Object)rv)).doubleValue());
                            break block46;
                        }
                        if (rc == Long.class) {
                            rv = new Double(((Number)((Object)rv)).doubleValue());
                            break block46;
                        }
                        if (rc == Float.class) {
                            rv = new Float(((Number)((Object)rv)).doubleValue());
                            break block46;
                        }
                        if (this.convertStringExpressions && rc == String.class) {
                            rv = Double.valueOf((String)((Object)rv));
                            break block46;
                        }
                        return Boolean.FALSE;
                    }
                    if (this.convertStringExpressions && lc == String.class) {
                        if (rc == Boolean.class) {
                            lv = Boolean.valueOf((String)((Object)lv));
                            break block46;
                        }
                        if (rc == Byte.class) {
                            lv = Byte.valueOf((String)((Object)lv));
                            break block46;
                        }
                        if (rc == Short.class) {
                            lv = Short.valueOf((String)((Object)lv));
                            break block46;
                        }
                        if (rc == Integer.class) {
                            lv = Integer.valueOf((String)((Object)lv));
                            break block46;
                        }
                        if (rc == Long.class) {
                            lv = Long.valueOf((String)((Object)lv));
                            break block46;
                        }
                        if (rc == Float.class) {
                            lv = Float.valueOf((String)((Object)lv));
                            break block46;
                        }
                        if (rc == Double.class) {
                            lv = Double.valueOf((String)((Object)lv));
                            break block46;
                        }
                        return Boolean.FALSE;
                    }
                    return Boolean.FALSE;
                }
                catch (NumberFormatException e) {
                    return Boolean.FALSE;
                }
            }
        }
        return this.asBoolean(lv.compareTo(rv)) ? Boolean.TRUE : Boolean.FALSE;
    }

    protected abstract boolean asBoolean(int var1);

    @Override
    public boolean matches(MessageEvaluationContext message) throws JMSException {
        Object object = this.evaluate(message);
        return object != null && object == Boolean.TRUE;
    }

    static {
        REGEXP_CONTROL_CHARS.add(Character.valueOf('.'));
        REGEXP_CONTROL_CHARS.add(Character.valueOf('\\'));
        REGEXP_CONTROL_CHARS.add(Character.valueOf('['));
        REGEXP_CONTROL_CHARS.add(Character.valueOf(']'));
        REGEXP_CONTROL_CHARS.add(Character.valueOf('^'));
        REGEXP_CONTROL_CHARS.add(Character.valueOf('$'));
        REGEXP_CONTROL_CHARS.add(Character.valueOf('?'));
        REGEXP_CONTROL_CHARS.add(Character.valueOf('*'));
        REGEXP_CONTROL_CHARS.add(Character.valueOf('+'));
        REGEXP_CONTROL_CHARS.add(Character.valueOf('{'));
        REGEXP_CONTROL_CHARS.add(Character.valueOf('}'));
        REGEXP_CONTROL_CHARS.add(Character.valueOf('|'));
        REGEXP_CONTROL_CHARS.add(Character.valueOf('('));
        REGEXP_CONTROL_CHARS.add(Character.valueOf(')'));
        REGEXP_CONTROL_CHARS.add(Character.valueOf(':'));
        REGEXP_CONTROL_CHARS.add(Character.valueOf('&'));
        REGEXP_CONTROL_CHARS.add(Character.valueOf('<'));
        REGEXP_CONTROL_CHARS.add(Character.valueOf('>'));
        REGEXP_CONTROL_CHARS.add(Character.valueOf('='));
        REGEXP_CONTROL_CHARS.add(Character.valueOf('!'));
    }

    static class LikeExpression
    extends UnaryExpression
    implements BooleanExpression {
        Pattern likePattern;

        public LikeExpression(Expression right, String like, int escape) {
            super(right);
            StringBuffer regexp = new StringBuffer(like.length() * 2);
            regexp.append("\\A");
            for (int i = 0; i < like.length(); ++i) {
                char c = like.charAt(i);
                if (escape == (0xFFFF & c) && this.shouldEscapeNext(like, i, c)) {
                    char t2 = like.charAt(++i);
                    regexp.append("\\x");
                    regexp.append(Integer.toHexString(0xFFFF & t2));
                    continue;
                }
                this.append(regexp, c);
            }
            regexp.append("\\z");
            this.likePattern = Pattern.compile(regexp.toString(), 32);
        }

        private boolean shouldEscapeNext(String selector, int i, char escape) {
            int next = i + 1;
            if (next < selector.length()) {
                char c = selector.charAt(next);
                return c == '_' || c == '%' || c == escape;
            }
            return false;
        }

        private void append(StringBuffer regexp, char c) {
            if (c == '%') {
                regexp.append(".*?");
            } else if (c == '_') {
                regexp.append(".");
            } else if (REGEXP_CONTROL_CHARS.contains(new Character(c))) {
                regexp.append("\\x");
                regexp.append(Integer.toHexString(0xFFFF & c));
            } else {
                regexp.append(c);
            }
        }

        @Override
        public String getExpressionSymbol() {
            return "LIKE";
        }

        @Override
        public Object evaluate(MessageEvaluationContext message) throws JMSException {
            Object rv = this.getRight().evaluate(message);
            if (rv == null) {
                return null;
            }
            if (!(rv instanceof String)) {
                return Boolean.FALSE;
            }
            return this.likePattern.matcher((String)rv).matches() ? Boolean.TRUE : Boolean.FALSE;
        }

        @Override
        public boolean matches(MessageEvaluationContext message) throws JMSException {
            Object object = this.evaluate(message);
            return object != null && object == Boolean.TRUE;
        }
    }
}

