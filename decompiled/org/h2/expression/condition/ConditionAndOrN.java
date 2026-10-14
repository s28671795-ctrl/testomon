/*
 * Decompiled with CFR 0.152.
 */
package org.h2.expression.condition;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import org.h2.engine.SessionLocal;
import org.h2.expression.Expression;
import org.h2.expression.ExpressionVisitor;
import org.h2.expression.condition.Comparison;
import org.h2.expression.condition.Condition;
import org.h2.expression.condition.ConditionAndOr;
import org.h2.expression.condition.ConditionIn;
import org.h2.expression.condition.ConditionInConstantSet;
import org.h2.expression.condition.ConditionNot;
import org.h2.message.DbException;
import org.h2.table.ColumnResolver;
import org.h2.table.TableFilter;
import org.h2.value.Value;
import org.h2.value.ValueBoolean;
import org.h2.value.ValueNull;

public class ConditionAndOrN
extends Condition {
    private final int andOrType;
    private final List<Expression> expressions;
    private List<Expression> added;
    private static final Comparator<Expression> COMPARE_BY_COST = new Comparator<Expression>(){

        @Override
        public int compare(Expression expression, Expression expression2) {
            return expression.getCost() - expression2.getCost();
        }
    };

    public ConditionAndOrN(int n, Expression expression, Expression expression2, Expression expression3) {
        this.andOrType = n;
        this.expressions = new ArrayList<Expression>(3);
        this.expressions.add(expression);
        this.expressions.add(expression2);
        this.expressions.add(expression3);
    }

    public ConditionAndOrN(int n, List<Expression> list) {
        this.andOrType = n;
        this.expressions = list;
    }

    int getAndOrType() {
        return this.andOrType;
    }

    void addFirst(Expression expression) {
        this.expressions.add(0, expression);
    }

    @Override
    public boolean needParentheses() {
        return true;
    }

    @Override
    public StringBuilder getUnenclosedSQL(StringBuilder stringBuilder, int n) {
        Iterator<Expression> iterator2 = this.expressions.iterator();
        iterator2.next().getSQL(stringBuilder, n, 0);
        while (iterator2.hasNext()) {
            switch (this.andOrType) {
                case 0: {
                    stringBuilder.append("\n    AND ");
                    break;
                }
                case 1: {
                    stringBuilder.append("\n    OR ");
                    break;
                }
                default: {
                    throw DbException.getInternalError("andOrType=" + this.andOrType);
                }
            }
            iterator2.next().getSQL(stringBuilder, n, 0);
        }
        return stringBuilder;
    }

    @Override
    public void createIndexConditions(SessionLocal sessionLocal, TableFilter tableFilter) {
        if (this.andOrType == 0) {
            for (Expression expression : this.expressions) {
                expression.createIndexConditions(sessionLocal, tableFilter);
            }
            if (this.added != null) {
                for (Expression expression : this.added) {
                    expression.createIndexConditions(sessionLocal, tableFilter);
                }
            }
        }
    }

    @Override
    public Expression getNotIfPossible(SessionLocal sessionLocal) {
        ArrayList<Expression> arrayList = new ArrayList<Expression>(this.expressions.size());
        for (Expression expression : this.expressions) {
            Expression expression2 = expression.getNotIfPossible(sessionLocal);
            if (expression2 == null) {
                expression2 = new ConditionNot(expression);
            }
            arrayList.add(expression2);
        }
        int n = this.andOrType == 0 ? 1 : 0;
        return new ConditionAndOrN(n, arrayList);
    }

    @Override
    public Value getValue(SessionLocal sessionLocal) {
        boolean bl = false;
        switch (this.andOrType) {
            case 0: {
                for (Expression expression : this.expressions) {
                    Value value = expression.getValue(sessionLocal);
                    if (value == ValueNull.INSTANCE) {
                        bl = true;
                        continue;
                    }
                    if (value.getBoolean()) continue;
                    return ValueBoolean.FALSE;
                }
                return bl ? ValueNull.INSTANCE : ValueBoolean.TRUE;
            }
            case 1: {
                for (Expression expression : this.expressions) {
                    Value value = expression.getValue(sessionLocal);
                    if (value == ValueNull.INSTANCE) {
                        bl = true;
                        continue;
                    }
                    if (!value.getBoolean()) continue;
                    return ValueBoolean.TRUE;
                }
                return bl ? ValueNull.INSTANCE : ValueBoolean.FALSE;
            }
        }
        throw DbException.getInternalError("type=" + this.andOrType);
    }

    @Override
    public Expression optimize(SessionLocal sessionLocal) {
        int n;
        for (n = 0; n < this.expressions.size(); ++n) {
            this.expressions.set(n, this.expressions.get(n).optimize(sessionLocal));
        }
        Collections.sort(this.expressions, COMPARE_BY_COST);
        this.optimizeMerge(0);
        n = 1;
        block5: while (n < this.expressions.size()) {
            Expression expression;
            Expression expression2 = this.expressions.get(n - 1);
            Expression expression3 = this.expressions.get(n);
            switch (this.andOrType) {
                case 0: {
                    if (!sessionLocal.getDatabase().getSettings().optimizeTwoEquals || !(expression2 instanceof Comparison) || !(expression3 instanceof Comparison) || (expression = ((Comparison)expression2).getAdditionalAnd(sessionLocal, (Comparison)expression3)) == null) break;
                    if (this.added == null) {
                        this.added = new ArrayList<Expression>();
                    }
                    this.added.add(expression.optimize(sessionLocal));
                    break;
                }
                case 1: {
                    if (!sessionLocal.getDatabase().getSettings().optimizeOr) break;
                    if (expression2 instanceof Comparison && expression3 instanceof Comparison) {
                        expression = ((Comparison)expression2).optimizeOr(sessionLocal, (Comparison)expression3);
                    } else if (expression2 instanceof ConditionIn && expression3 instanceof Comparison) {
                        expression = ((ConditionIn)expression2).getAdditional((Comparison)expression3);
                    } else if (expression3 instanceof ConditionIn && expression2 instanceof Comparison) {
                        expression = ((ConditionIn)expression3).getAdditional((Comparison)expression2);
                    } else if (expression2 instanceof ConditionInConstantSet && expression3 instanceof Comparison) {
                        expression = ((ConditionInConstantSet)expression2).getAdditional(sessionLocal, (Comparison)expression3);
                    } else if (expression3 instanceof ConditionInConstantSet && expression2 instanceof Comparison) {
                        expression = ((ConditionInConstantSet)expression3).getAdditional(sessionLocal, (Comparison)expression2);
                    } else {
                        if (!(expression2 instanceof ConditionAndOr) || !(expression3 instanceof ConditionAndOr)) break;
                        expression = ConditionAndOr.optimizeConditionAndOr((ConditionAndOr)expression2, (ConditionAndOr)expression3);
                    }
                    if (expression == null) break;
                    this.expressions.remove(n);
                    this.expressions.set(n - 1, expression.optimize(sessionLocal));
                    continue block5;
                }
            }
            expression = ConditionAndOr.optimizeIfConstant(sessionLocal, this.andOrType, expression2, expression3);
            if (expression != null) {
                this.expressions.remove(n);
                this.expressions.set(n - 1, expression);
                continue;
            }
            if (this.optimizeMerge(n)) continue;
            ++n;
        }
        Collections.sort(this.expressions, COMPARE_BY_COST);
        if (this.expressions.size() == 1) {
            return Condition.castToBoolean(sessionLocal, this.expressions.get(0));
        }
        return this;
    }

    private boolean optimizeMerge(int n) {
        ConditionAndOr conditionAndOr;
        Expression expression = this.expressions.get(n);
        if (expression instanceof ConditionAndOrN) {
            ConditionAndOrN conditionAndOrN = (ConditionAndOrN)expression;
            if (this.andOrType == conditionAndOrN.andOrType) {
                this.expressions.remove(n);
                this.expressions.addAll(n, conditionAndOrN.expressions);
                return true;
            }
        } else if (expression instanceof ConditionAndOr && this.andOrType == (conditionAndOr = (ConditionAndOr)expression).getAndOrType()) {
            this.expressions.set(n, conditionAndOr.getSubexpression(0));
            this.expressions.add(n + 1, conditionAndOr.getSubexpression(1));
            return true;
        }
        return false;
    }

    @Override
    public void addFilterConditions(TableFilter tableFilter) {
        if (this.andOrType == 0) {
            for (Expression expression : this.expressions) {
                expression.addFilterConditions(tableFilter);
            }
        } else {
            super.addFilterConditions(tableFilter);
        }
    }

    @Override
    public void mapColumns(ColumnResolver columnResolver, int n, int n2) {
        for (Expression expression : this.expressions) {
            expression.mapColumns(columnResolver, n, n2);
        }
    }

    @Override
    public void setEvaluatable(TableFilter tableFilter, boolean bl) {
        for (Expression expression : this.expressions) {
            expression.setEvaluatable(tableFilter, bl);
        }
    }

    @Override
    public void updateAggregate(SessionLocal sessionLocal, int n) {
        for (Expression expression : this.expressions) {
            expression.updateAggregate(sessionLocal, n);
        }
    }

    @Override
    public boolean isEverything(ExpressionVisitor expressionVisitor) {
        for (Expression expression : this.expressions) {
            if (expression.isEverything(expressionVisitor)) continue;
            return false;
        }
        return true;
    }

    @Override
    public int getCost() {
        int n = 0;
        for (Expression expression : this.expressions) {
            n += expression.getCost();
        }
        return n;
    }

    @Override
    public int getSubexpressionCount() {
        return this.expressions.size();
    }

    @Override
    public Expression getSubexpression(int n) {
        return this.expressions.get(n);
    }
}

