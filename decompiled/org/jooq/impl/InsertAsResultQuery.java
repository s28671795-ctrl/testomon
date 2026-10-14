/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import java.util.Collection;
import org.jooq.InsertResultStep;
import org.jooq.QueryPart;
import org.jooq.Record;
import org.jooq.Replacer;
import org.jooq.SelectFieldOrAsterisk;
import org.jooq.Traverser;
import org.jooq.impl.AbstractDMLQueryAsResultQuery;
import org.jooq.impl.InsertQueryImpl;
import org.jooq.impl.QOM;
import org.jooq.impl.Tools;

final class InsertAsResultQuery<R extends Record>
extends AbstractDMLQueryAsResultQuery<R, InsertQueryImpl<R>>
implements InsertResultStep<R>,
QOM.InsertReturning<R> {
    InsertAsResultQuery(InsertQueryImpl<R> delegate, boolean returningResult) {
        super(delegate, returningResult);
    }

    @Override
    public final QOM.Insert<?> $insert() {
        return (QOM.Insert)this.getDelegate();
    }

    @Override
    public final QOM.InsertReturning<R> $insert(QOM.Insert<?> newInsert) {
        return new InsertAsResultQuery(Tools.insertQueryImpl(newInsert).copy(i -> i.setReturning(this.$returning())), this.returningResult);
    }

    @Override
    public final QOM.UnmodifiableList<? extends SelectFieldOrAsterisk> $returning() {
        return QOM.unmodifiable(((InsertQueryImpl)this.getDelegate()).returning);
    }

    @Override
    public final QOM.InsertReturning<?> $returning(Collection<? extends SelectFieldOrAsterisk> returning) {
        return new InsertAsResultQuery(((InsertQueryImpl)this.getDelegate()).copy(i -> i.setReturning(returning)), this.returningResult);
    }

    @Override
    public final <R> R $traverse(Traverser<?, R> traverser) {
        return QOM.traverse(traverser, this, this.$insert(), this.$returning());
    }

    @Override
    public final QueryPart $replace(Replacer replacer) {
        return QOM.replace(this, this.$insert(), this.$returning(), (d, ret) -> new InsertAsResultQuery(Tools.insertQueryImpl(d).copy(x -> x.setReturning((Collection<? extends SelectFieldOrAsterisk>)ret)), this.returningResult), replacer);
    }
}

