/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import java.util.Objects;
import org.jooq.Context;
import org.jooq.QueryPart;
import org.jooq.Replacer;
import org.jooq.Traverser;
import org.jooq.impl.AbstractQueryPart;
import org.jooq.impl.QOM;

final class TupleImpl2<Q1 extends QueryPart, Q2 extends QueryPart>
extends AbstractQueryPart
implements QOM.Tuple2<Q1, Q2> {
    private final Q1 part1;
    private final Q2 part2;

    TupleImpl2(Q1 part1, Q2 part2) {
        this.part1 = part1;
        this.part2 = part2;
    }

    @Override
    public final void accept(Context<?> ctx) {
        ctx.sql('(').visit((QueryPart)this.part1).sql(", ").visit((QueryPart)this.part2).sql(')');
    }

    @Override
    public final Q1 $1() {
        return this.part1;
    }

    @Override
    public final Q2 $2() {
        return this.part2;
    }

    @Override
    public final QOM.Tuple2<Q1, Q2> $1(Q1 newPart1) {
        return QOM.tuple(newPart1, this.part2);
    }

    @Override
    public final QOM.Tuple2<Q1, Q2> $2(Q2 newPart2) {
        return QOM.tuple(this.part1, newPart2);
    }

    @Override
    public final <R> R $traverse(Traverser<?, R> traverser) {
        return QOM.traverse(traverser, this, this.$1(), this.$2());
    }

    @Override
    public final QueryPart $replace(Replacer replacer) {
        return QOM.replace(this, this.$1(), this.$2(), (p1, p2) -> QOM.tuple(p1, p2), replacer);
    }

    @Override
    public int hashCode() {
        int prime = 31;
        int result = super.hashCode();
        result = 31 * result + Objects.hash(this.part1, this.part2);
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!super.equals(obj)) {
            return false;
        }
        if (this.getClass() != obj.getClass()) {
            return false;
        }
        TupleImpl2 other = (TupleImpl2)obj;
        return Objects.equals(this.part1, other.part1) && Objects.equals(this.part2, other.part2);
    }
}

