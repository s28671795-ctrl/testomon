/*
 * Decompiled with CFR 0.152.
 */
package akka.japi.pf;

import akka.japi.pf.CaseStatement;
import scala.PartialFunction;

class AbstractMatch<I, R> {
    protected final PartialFunction<I, R> statements;

    AbstractMatch(PartialFunction<I, R> partialFunction) {
        PartialFunction partialFunction2 = CaseStatement.empty();
        this.statements = partialFunction == null ? partialFunction2 : partialFunction.orElse(partialFunction2);
    }

    public PartialFunction<I, R> asPF() {
        return this.statements;
    }
}

