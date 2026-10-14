/*
 * Decompiled with CFR 0.152.
 */
package akka.japi.pf;

import akka.japi.pf.CaseStatement;
import scala.PartialFunction;

abstract class AbstractPFBuilder<F, T> {
    protected PartialFunction<F, T> statements = null;

    AbstractPFBuilder() {
    }

    protected void addStatement(PartialFunction<F, T> partialFunction) {
        this.statements = this.statements == null ? partialFunction : this.statements.orElse(partialFunction);
    }

    public PartialFunction<F, T> build() {
        PartialFunction partialFunction = CaseStatement.empty();
        PartialFunction<F, T> partialFunction2 = this.statements;
        this.statements = null;
        if (partialFunction2 == null) {
            return partialFunction;
        }
        return partialFunction2.orElse(partialFunction);
    }
}

