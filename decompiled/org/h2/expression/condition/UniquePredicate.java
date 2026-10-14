/*
 * Decompiled with CFR 0.152.
 */
package org.h2.expression.condition;

import java.util.Arrays;
import org.h2.command.query.Query;
import org.h2.engine.NullsDistinct;
import org.h2.engine.SessionLocal;
import org.h2.expression.Expression;
import org.h2.expression.ValueExpression;
import org.h2.expression.condition.PredicateWithSubquery;
import org.h2.result.LocalResult;
import org.h2.result.ResultTarget;
import org.h2.value.Value;
import org.h2.value.ValueBoolean;
import org.h2.value.ValueNull;

public class UniquePredicate
extends PredicateWithSubquery {
    private final NullsDistinct nullsDistinct;

    public UniquePredicate(Query query, NullsDistinct nullsDistinct) {
        super(query);
        this.nullsDistinct = nullsDistinct;
    }

    @Override
    public Expression optimize(SessionLocal sessionLocal) {
        super.optimize(sessionLocal);
        if (this.query.isStandardDistinct()) {
            return ValueExpression.TRUE;
        }
        return this;
    }

    @Override
    public Value getValue(SessionLocal sessionLocal) {
        this.query.setSession(sessionLocal);
        int n = this.query.getColumnCount();
        LocalResult localResult = new LocalResult(sessionLocal, this.query.getExpressions().toArray(new Expression[0]), n, n);
        localResult.setDistinct();
        Target target = new Target(n, this.nullsDistinct, localResult);
        this.query.query(Integer.MAX_VALUE, target);
        localResult.close();
        return ValueBoolean.get(!target.hasDuplicates);
    }

    @Override
    public StringBuilder getUnenclosedSQL(StringBuilder stringBuilder, int n) {
        stringBuilder.append("UNIQUE");
        if (this.nullsDistinct != NullsDistinct.DISTINCT) {
            this.nullsDistinct.getSQL(stringBuilder.append(' '), 0);
        }
        return super.getUnenclosedSQL(stringBuilder, n);
    }

    private static final class Target
    implements ResultTarget {
        private final int columnCount;
        private final NullsDistinct nullsDistinct;
        private final LocalResult result;
        boolean hasDuplicates;

        Target(int n, NullsDistinct nullsDistinct, LocalResult localResult) {
            this.columnCount = n;
            this.nullsDistinct = nullsDistinct;
            this.result = localResult;
        }

        @Override
        public void limitsWereApplied() {
        }

        @Override
        public long getRowCount() {
            return 0L;
        }

        @Override
        public void addRow(Value ... valueArray) {
            if (this.hasDuplicates) {
                return;
            }
            block0 : switch (this.nullsDistinct) {
                case DISTINCT: {
                    int n;
                    for (n = 0; n < this.columnCount; ++n) {
                        if (valueArray[n] != ValueNull.INSTANCE) continue;
                        return;
                    }
                    break;
                }
                case ALL_DISTINCT: {
                    int n;
                    for (n = 0; n < this.columnCount; ++n) {
                        if (valueArray[n] != ValueNull.INSTANCE) break block0;
                    }
                    return;
                }
            }
            if (valueArray.length != this.columnCount) {
                valueArray = Arrays.copyOf(valueArray, this.columnCount);
            }
            long l = this.result.getRowCount() + 1L;
            this.result.addRow(valueArray);
            if (l != this.result.getRowCount()) {
                this.hasDuplicates = true;
                this.result.close();
            }
        }
    }
}

