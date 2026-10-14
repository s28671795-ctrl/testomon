/*
 * Decompiled with CFR 0.152.
 */
package org.h2.expression.condition;

import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;
import org.h2.engine.Database;
import org.h2.engine.SessionLocal;
import org.h2.expression.Expression;
import org.h2.expression.ExpressionColumn;
import org.h2.expression.ExpressionVisitor;
import org.h2.expression.SearchedCase;
import org.h2.expression.TypedValueExpression;
import org.h2.expression.ValueExpression;
import org.h2.expression.condition.Comparison;
import org.h2.expression.condition.Condition;
import org.h2.expression.condition.NullPredicate;
import org.h2.index.IndexCondition;
import org.h2.message.DbException;
import org.h2.table.ColumnResolver;
import org.h2.table.TableFilter;
import org.h2.value.CompareMode;
import org.h2.value.DataType;
import org.h2.value.TypeInfo;
import org.h2.value.Value;
import org.h2.value.ValueBoolean;
import org.h2.value.ValueNull;
import org.h2.value.ValueVarchar;
import org.h2.value.ValueVarcharIgnoreCase;

public final class CompareLike
extends Condition {
    private static final int MATCH = 0;
    private static final int ONE = 1;
    private static final int ANY = 2;
    private final CompareMode compareMode;
    private final String defaultEscape;
    private final LikeType likeType;
    private Expression left;
    private final boolean not;
    private final boolean whenOperand;
    private Expression right;
    private Expression escape;
    private boolean isInit;
    private char[] patternChars;
    private String patternString;
    private int[] patternTypes;
    private int patternLength;
    private Pattern patternRegexp;
    private boolean ignoreCase;
    private boolean fastCompare;
    private boolean invalidPattern;
    private boolean shortcutToStartsWith;
    private boolean shortcutToEndsWith;
    private boolean shortcutToContains;

    public CompareLike(Database database, Expression expression, boolean bl, boolean bl2, Expression expression2, Expression expression3, LikeType likeType) {
        this(database.getCompareMode(), database.getSettings().defaultEscape, expression, bl, bl2, expression2, expression3, likeType);
    }

    public CompareLike(CompareMode compareMode, String string, Expression expression, boolean bl, boolean bl2, Expression expression2, Expression expression3, LikeType likeType) {
        this.compareMode = compareMode;
        this.defaultEscape = string;
        this.likeType = likeType;
        this.left = expression;
        this.not = bl;
        this.whenOperand = bl2;
        this.right = expression2;
        this.escape = expression3;
    }

    private static Character getEscapeChar(String string) {
        return string == null || string.isEmpty() ? null : Character.valueOf(string.charAt(0));
    }

    @Override
    public boolean needParentheses() {
        return true;
    }

    @Override
    public StringBuilder getUnenclosedSQL(StringBuilder stringBuilder, int n) {
        return this.getWhenSQL(this.left.getSQL(stringBuilder, n, 0), n);
    }

    @Override
    public StringBuilder getWhenSQL(StringBuilder stringBuilder, int n) {
        if (this.not) {
            stringBuilder.append(" NOT");
        }
        switch (this.likeType) {
            case LIKE: 
            case ILIKE: {
                stringBuilder.append(this.likeType == LikeType.LIKE ? " LIKE " : " ILIKE ");
                this.right.getSQL(stringBuilder, n, 0);
                if (this.escape == null) break;
                this.escape.getSQL(stringBuilder.append(" ESCAPE "), n, 0);
                break;
            }
            case REGEXP: {
                stringBuilder.append(" REGEXP ");
                this.right.getSQL(stringBuilder, n, 0);
                break;
            }
            default: {
                throw DbException.getUnsupportedException(this.likeType.name());
            }
        }
        return stringBuilder;
    }

    @Override
    public Expression optimize(SessionLocal sessionLocal) {
        Value value;
        this.left = this.left.optimize(sessionLocal);
        this.right = this.right.optimize(sessionLocal);
        if (this.likeType == LikeType.ILIKE || this.left.getType().getValueType() == 4) {
            this.ignoreCase = true;
        }
        if (this.escape != null) {
            this.escape = this.escape.optimize(sessionLocal);
        }
        if (this.whenOperand) {
            return this;
        }
        if (this.left.isValueSet() && (value = this.left.getValue(sessionLocal)) == ValueNull.INSTANCE) {
            return TypedValueExpression.UNKNOWN;
        }
        if (this.right.isValueSet() && (this.escape == null || this.escape.isValueSet())) {
            Value value2;
            if (this.left.isValueSet()) {
                return ValueExpression.getBoolean(this.getValue(sessionLocal));
            }
            value = this.right.getValue(sessionLocal);
            if (value == ValueNull.INSTANCE) {
                return TypedValueExpression.UNKNOWN;
            }
            Value value3 = value2 = this.escape == null ? null : this.escape.getValue(sessionLocal);
            if (value2 == ValueNull.INSTANCE) {
                return TypedValueExpression.UNKNOWN;
            }
            String string = value.getString();
            this.initPattern(string, this.getEscapeChar(value2));
            if (this.invalidPattern) {
                return TypedValueExpression.UNKNOWN;
            }
            if (this.likeType != LikeType.REGEXP && "%".equals(string)) {
                return new SearchedCase(new Expression[]{new NullPredicate(this.left, true, false), ValueExpression.getBoolean(!this.not), TypedValueExpression.UNKNOWN}).optimize(sessionLocal);
            }
            if (this.isFullMatch()) {
                Value value4 = this.ignoreCase ? ValueVarcharIgnoreCase.get(this.patternString) : ValueVarchar.get(this.patternString);
                ValueExpression valueExpression = ValueExpression.get(value4);
                return new Comparison(this.not ? 1 : 0, this.left, valueExpression, false).optimize(sessionLocal);
            }
            this.isInit = true;
        }
        return this;
    }

    private Character getEscapeChar(Value value) {
        Character c;
        if (value == null) {
            return CompareLike.getEscapeChar(this.defaultEscape);
        }
        String string = value.getString();
        if (string == null) {
            c = CompareLike.getEscapeChar(this.defaultEscape);
        } else if (string.length() == 0) {
            c = null;
        } else {
            if (string.length() > 1) {
                throw DbException.get(22025, string);
            }
            c = Character.valueOf(string.charAt(0));
        }
        return c;
    }

    @Override
    public void createIndexConditions(SessionLocal sessionLocal, TableFilter tableFilter) {
        if (this.not || this.whenOperand || this.likeType == LikeType.REGEXP || !(this.left instanceof ExpressionColumn)) {
            return;
        }
        ExpressionColumn expressionColumn = (ExpressionColumn)this.left;
        if (tableFilter != expressionColumn.getTableFilter() || !TypeInfo.haveSameOrdering(expressionColumn.getType(), this.ignoreCase ? TypeInfo.TYPE_VARCHAR_IGNORECASE : TypeInfo.TYPE_VARCHAR)) {
            return;
        }
        if (!this.right.isEverything(ExpressionVisitor.INDEPENDENT_VISITOR)) {
            return;
        }
        if (this.escape != null && !this.escape.isEverything(ExpressionVisitor.INDEPENDENT_VISITOR)) {
            return;
        }
        String string = this.right.getValue(sessionLocal).getString();
        if (!this.isInit) {
            Value value;
            Value value2 = value = this.escape == null ? null : this.escape.getValue(sessionLocal);
            if (value == ValueNull.INSTANCE) {
                throw DbException.getInternalError();
            }
            this.initPattern(string, this.getEscapeChar(value));
        }
        if (this.invalidPattern) {
            return;
        }
        if (this.patternLength <= 0 || this.patternTypes[0] != 0) {
            return;
        }
        if (!DataType.isStringType(expressionColumn.getColumn().getType().getValueType())) {
            return;
        }
        int n = 0;
        StringBuilder stringBuilder = new StringBuilder();
        while (n < this.patternLength && this.patternTypes[n] == 0) {
            stringBuilder.append(this.patternChars[n++]);
        }
        String string2 = stringBuilder.toString();
        if (n == this.patternLength) {
            tableFilter.addIndexCondition(IndexCondition.get(0, expressionColumn, ValueExpression.get(ValueVarchar.get(string2))));
        } else if (string2.length() > 0) {
            tableFilter.addIndexCondition(IndexCondition.get(5, expressionColumn, ValueExpression.get(ValueVarchar.get(string2))));
            char c = string2.charAt(string2.length() - 1);
            for (int i = 1; i < 2000; ++i) {
                String string3 = string2.substring(0, string2.length() - 1) + (char)(c + i);
                if (this.compareMode.compareString(string2, string3, this.ignoreCase) >= 0) continue;
                tableFilter.addIndexCondition(IndexCondition.get(2, expressionColumn, ValueExpression.get(ValueVarchar.get(string3))));
                break;
            }
        }
    }

    @Override
    public Value getValue(SessionLocal sessionLocal) {
        return this.getValue(sessionLocal, this.left.getValue(sessionLocal));
    }

    @Override
    public boolean getWhenValue(SessionLocal sessionLocal, Value value) {
        if (!this.whenOperand) {
            return super.getWhenValue(sessionLocal, value);
        }
        return this.getValue(sessionLocal, value).isTrue();
    }

    private Value getValue(SessionLocal sessionLocal, Value value) {
        boolean bl;
        Object object;
        Object object2;
        if (value == ValueNull.INSTANCE) {
            return ValueNull.INSTANCE;
        }
        if (!this.isInit) {
            object2 = this.right.getValue(sessionLocal);
            if (object2 == ValueNull.INSTANCE) {
                return ValueNull.INSTANCE;
            }
            String string = ((Value)object2).getString();
            Object object3 = object = this.escape == null ? null : this.escape.getValue(sessionLocal);
            if (object == ValueNull.INSTANCE) {
                return ValueNull.INSTANCE;
            }
            this.initPattern(string, this.getEscapeChar((Value)object));
        }
        if (this.invalidPattern) {
            return ValueNull.INSTANCE;
        }
        object2 = value.getString();
        if (this.likeType == LikeType.REGEXP) {
            bl = this.patternRegexp.matcher((CharSequence)object2).find();
        } else if (this.shortcutToStartsWith) {
            bl = ((String)object2).regionMatches(this.ignoreCase, 0, this.patternString, 0, this.patternLength - 1);
        } else if (this.shortcutToEndsWith) {
            bl = ((String)object2).regionMatches(this.ignoreCase, ((String)object2).length() - this.patternLength + 1, this.patternString, 1, this.patternLength - 1);
        } else if (this.shortcutToContains) {
            object = this.patternString.substring(1, this.patternString.length() - 1);
            bl = this.ignoreCase ? CompareLike.containsIgnoreCase((String)object2, (String)object) : ((String)object2).contains((CharSequence)object);
        } else {
            bl = this.compareAt((String)object2, 0, 0, ((String)object2).length(), this.patternChars, this.patternTypes);
        }
        return ValueBoolean.get(this.not ^ bl);
    }

    private static boolean containsIgnoreCase(String string, String string2) {
        int n = string2.length();
        if (n == 0) {
            return true;
        }
        char c = Character.toLowerCase(string2.charAt(0));
        char c2 = Character.toUpperCase(string2.charAt(0));
        for (int i = string.length() - n; i >= 0; --i) {
            char c3 = string.charAt(i);
            if (c3 != c && c3 != c2 || !string.regionMatches(true, i, string2, 0, n)) continue;
            return true;
        }
        return false;
    }

    private boolean compareAt(String string, int n, int n2, int n3, char[] cArray, int[] nArray) {
        while (n < this.patternLength) {
            switch (nArray[n]) {
                case 0: {
                    if (n2 < n3 && this.compare(cArray, string, n, n2++)) break;
                    return false;
                }
                case 1: {
                    if (n2++ < n3) break;
                    return false;
                }
                case 2: {
                    if (++n >= this.patternLength) {
                        return true;
                    }
                    while (n2 < n3) {
                        if (this.compare(cArray, string, n, n2) && this.compareAt(string, n, n2, n3, cArray, nArray)) {
                            return true;
                        }
                        ++n2;
                    }
                    return false;
                }
                default: {
                    throw DbException.getInternalError(Integer.toString(nArray[n]));
                }
            }
            ++n;
        }
        return n2 == n3;
    }

    private boolean compare(char[] cArray, String string, int n, int n2) {
        return cArray[n] == string.charAt(n2) || !this.fastCompare && this.compareMode.equalsChars(this.patternString, n, string, n2, this.ignoreCase);
    }

    @Override
    public boolean isWhenConditionOperand() {
        return this.whenOperand;
    }

    public boolean test(String string, String string2, char c) {
        this.initPattern(string, Character.valueOf(c));
        return this.test(string2);
    }

    public boolean test(String string) {
        if (this.invalidPattern) {
            return false;
        }
        return this.compareAt(string, 0, 0, string.length(), this.patternChars, this.patternTypes);
    }

    public void initPattern(String string, Character c) {
        int n;
        if (this.compareMode.getName().equals("OFF") && !this.ignoreCase) {
            this.fastCompare = true;
        }
        if (this.likeType == LikeType.REGEXP) {
            this.patternString = string;
            try {
                this.patternRegexp = this.ignoreCase ? Pattern.compile(string, 2) : Pattern.compile(string);
            }
            catch (PatternSyntaxException patternSyntaxException) {
                throw DbException.get(22025, patternSyntaxException, string);
            }
            return;
        }
        this.patternLength = 0;
        if (string == null) {
            this.patternTypes = null;
            this.patternChars = null;
            return;
        }
        int n2 = string.length();
        this.patternChars = new char[n2];
        this.patternTypes = new int[n2];
        boolean bl = false;
        for (n = 0; n < n2; ++n) {
            int n3;
            char c2 = string.charAt(n);
            if (c != null && c.charValue() == c2) {
                if (n >= n2 - 1) {
                    this.invalidPattern = true;
                    return;
                }
                c2 = string.charAt(++n);
                n3 = 0;
                bl = false;
            } else if (c2 == '%') {
                if (bl) continue;
                n3 = 2;
                bl = true;
            } else if (c2 == '_') {
                n3 = 1;
            } else {
                n3 = 0;
                bl = false;
            }
            this.patternTypes[this.patternLength] = n3;
            this.patternChars[this.patternLength++] = c2;
        }
        for (n = 0; n < this.patternLength - 1; ++n) {
            if (this.patternTypes[n] != 2 || this.patternTypes[n + 1] != 1) continue;
            this.patternTypes[n] = 1;
            this.patternTypes[n + 1] = 2;
        }
        this.patternString = new String(this.patternChars, 0, this.patternLength);
        this.shortcutToStartsWith = false;
        this.shortcutToEndsWith = false;
        this.shortcutToContains = false;
        if (this.compareMode.getName().equals("OFF") && this.patternLength > 1) {
            for (n = 0; n < this.patternLength && this.patternTypes[n] == 0; ++n) {
            }
            if (n == this.patternLength - 1 && this.patternTypes[this.patternLength - 1] == 2) {
                this.shortcutToStartsWith = true;
                return;
            }
        }
        if (this.compareMode.getName().equals("OFF") && this.patternLength > 1 && this.patternTypes[0] == 2) {
            for (n = 1; n < this.patternLength && this.patternTypes[n] == 0; ++n) {
            }
            if (n == this.patternLength) {
                this.shortcutToEndsWith = true;
                return;
            }
        }
        if (this.compareMode.getName().equals("OFF") && this.patternLength > 2 && this.patternTypes[0] == 2) {
            for (n = 1; n < this.patternLength && this.patternTypes[n] == 0; ++n) {
            }
            if (n == this.patternLength - 1 && this.patternTypes[this.patternLength - 1] == 2) {
                this.shortcutToContains = true;
            }
        }
    }

    private boolean isFullMatch() {
        if (this.patternTypes == null) {
            return false;
        }
        for (int n : this.patternTypes) {
            if (n == 0) continue;
            return false;
        }
        return true;
    }

    @Override
    public Expression getNotIfPossible(SessionLocal sessionLocal) {
        if (this.whenOperand) {
            return null;
        }
        return new CompareLike(this.compareMode, this.defaultEscape, this.left, !this.not, false, this.right, this.escape, this.likeType);
    }

    @Override
    public void mapColumns(ColumnResolver columnResolver, int n, int n2) {
        this.left.mapColumns(columnResolver, n, n2);
        this.right.mapColumns(columnResolver, n, n2);
        if (this.escape != null) {
            this.escape.mapColumns(columnResolver, n, n2);
        }
    }

    @Override
    public void setEvaluatable(TableFilter tableFilter, boolean bl) {
        this.left.setEvaluatable(tableFilter, bl);
        this.right.setEvaluatable(tableFilter, bl);
        if (this.escape != null) {
            this.escape.setEvaluatable(tableFilter, bl);
        }
    }

    @Override
    public void updateAggregate(SessionLocal sessionLocal, int n) {
        this.left.updateAggregate(sessionLocal, n);
        this.right.updateAggregate(sessionLocal, n);
        if (this.escape != null) {
            this.escape.updateAggregate(sessionLocal, n);
        }
    }

    @Override
    public boolean isEverything(ExpressionVisitor expressionVisitor) {
        return this.left.isEverything(expressionVisitor) && this.right.isEverything(expressionVisitor) && (this.escape == null || this.escape.isEverything(expressionVisitor));
    }

    @Override
    public int getCost() {
        return this.left.getCost() + this.right.getCost() + 3;
    }

    @Override
    public int getSubexpressionCount() {
        return this.escape == null ? 2 : 3;
    }

    @Override
    public Expression getSubexpression(int n) {
        switch (n) {
            case 0: {
                return this.left;
            }
            case 1: {
                return this.right;
            }
            case 2: {
                if (this.escape == null) break;
                return this.escape;
            }
        }
        throw new IndexOutOfBoundsException();
    }

    public static enum LikeType {
        LIKE,
        ILIKE,
        REGEXP;

    }
}

